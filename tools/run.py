"""Public checks and JSON-lines launcher; official grading uses separate code."""
import argparse
import json
import pathlib
import subprocess
import sys
from public_validate import check

P = pathlib.Path(__file__).resolve().parents[1]
LANGUAGES = ('Java', 'Python', 'Cpp', 'CSharp')

def select_language(override):
    if override:
        return override
    manifest = json.loads((P / 'submission.json').read_text(encoding='utf-8'))
    language = manifest.get('language')
    if language not in LANGUAGES:
        raise ValueError('submission.json language must be Java, Python, Cpp or CSharp')
    return language

def build(language):
    source = P / language / 'source'
    output = P / language / 'build'
    output.mkdir(exist_ok=True)
    if language == 'Python':
        return [sys.executable, str(source / 'main.py')]
    if language == 'Java':
        command = ['javac', '--release', '17', '-encoding', 'UTF-8', '-d', str(output),
                   *map(str, sorted(source.glob('*.java')))]
        executable = ['java', '-Xmx384m', '-cp', str(output), 'Main']
    elif language == 'Cpp':
        binary = output / ('main.exe' if sys.platform == 'win32' else 'main')
        command = ['g++', '-std=c++17', '-O2', '-o', str(binary),
                   *map(str, sorted(source.glob('*.cpp')))]
        executable = [str(binary)]
    else:
        command = ['dotnet', 'build', str(source / 'App.csproj'), '--nologo', '-o', str(output)]
        executable = ['dotnet', str(output / 'App.dll')]
    subprocess.run(command, check=True, timeout=60, stdout=sys.stderr)
    return executable

def read_json(text):
    def invalid_constant(value):
        raise ValueError(f'{value} is not a JSON number')
    return json.loads(text, parse_constant=invalid_constant)

def execute(command, requests):
    data = ''.join(json.dumps(request, separators=(',', ':')) + '\n' for request in requests)
    result = subprocess.run(command, input=data, capture_output=True, text=True,
                            encoding='utf-8', timeout=20)
    if result.returncode:
        detail = (result.stderr or result.stdout).strip()[:4000]
        raise ValueError(f'program exited with code {result.returncode}' + (f'\n{detail}' if detail else ''))
    lines = result.stdout.splitlines()
    if len(lines) != len(requests):
        raise ValueError(f'expected {len(requests)} JSON result lines, received {len(lines)}; '
                         'remove prompts/debug output and answer every request')
    try:
        answers = [read_json(line) for line in lines]
    except ValueError as error:
        detail = result.stderr.strip()[:4000]
        raise ValueError(f'invalid JSON output: {error}' + (f'\n{detail}' if detail else '')) from error
    for answer in answers:
        if not isinstance(answer, dict):
            raise ValueError('each result must be a JSON object')
        if 'error' in answer:
            raise ValueError(f'program reported: {answer["error"]}')
    return answers

def run_cases(command, cases, label):
    try:
        answers = execute(command, [case['input'] for case in cases])
        failures = 0
        for case, answer in zip(cases, answers):
            if not check(case['input'], answer, case['output']):
                failures += 1
                print(f'FAIL {label}: {case.get("name", case["input"]["task"])} '
                      '(answer does not satisfy the published contract)')
        if not failures:
            print(f'PASS {label}')
        return failures
    except subprocess.TimeoutExpired:
        print(f'FAIL {label}: program exceeded 20 seconds')
    except (OSError, ValueError) as error:
        print(f'FAIL {label}: {error}')
    return 1

def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--language', choices=LANGUAGES,
                        help='override submission.json; omitted means use its selected language')
    mode = parser.add_mutually_exclusive_group()
    mode.add_argument('--setup', action='store_true', help='check transport without algorithm implementations')
    mode.add_argument('--check', action='store_true', help='check examples, edge cases and request-state reset')
    mode.add_argument('--input', help='run a UTF-8 JSON-lines request file')
    args = parser.parse_args()
    try:
        language = select_language(args.language)
        command = build(language)
        print(f'Language: {language}', file=sys.stderr)
        if args.input:
            with open(args.input, encoding='utf-8') as stream:
                return subprocess.run(command, input=stream.read(), text=True, encoding='utf-8').returncode
        if args.setup:
            return int(bool(run_cases(command, [{'input': {'task': 'ping'},
                                                 'output': {'status': 'ready'}}], 'setup: ping')))
        cases = json.loads((P / 'tests' / 'public.json').read_text(encoding='utf-8'))
        failures = sum(run_cases(command, [case], f'public {index}: {case.get("name", case["input"]["task"])}')
                       for index, case in enumerate(cases, 1))
        # Check independence of results in a persistent process, including repeated requests.
        failures += run_cases(command, cases + cases, 'state reset: two complete batches in one process')
        return int(bool(failures))
    except subprocess.TimeoutExpired:
        print('Build failed: compilation exceeded 60 seconds', file=sys.stderr)
    except subprocess.CalledProcessError as error:
        print(f'Build failed with exit code {error.returncode}; compiler output is shown above', file=sys.stderr)
    except (OSError, ValueError, TypeError, AttributeError) as error:
        print(f'Cannot run checks: {error}', file=sys.stderr)
    return 1

if __name__ == '__main__':
    sys.exit(main())
