"""Package a clean committed A3 snapshot for timestamped Moodle submission."""
import argparse
import hashlib
import json
import pathlib
import re
import subprocess
import sys
import zipfile

ROOT = pathlib.Path(__file__).resolve().parents[1]
META = 'submission_metadata.json'
EXTENSIONS = {'Java': {'.java'}, 'Python': {'.py'}, 'Cpp': {'.cpp', '.hpp', '.h'},
              'CSharp': {'.cs', '.csproj'}}


def git(*arguments):
    return subprocess.run(['git', '-C', str(ROOT), *arguments], check=True,
                          capture_output=True).stdout


def package(student_id):
    if not re.fullmatch(r'[0-9]{3,40}', student_id):
        raise ValueError('student ID must contain 3-40 digits')
    if git('status', '--porcelain=v1', '-z', '--untracked-files=all'):
        raise ValueError('commit your source, analysis and own tests first; git status must be clean')
    commit = git('rev-parse', 'HEAD').decode('ascii').strip()
    entries = []
    for record in git('ls-tree', '-r', '-z', commit).split(b'\0'):
        if record:
            attributes, name = record.split(b'\t', 1)
            mode, kind, object_id = attributes.decode('ascii').split()
            entries.append((mode, kind, object_id, name.decode('utf-8')))
    if entries:
        if any(name == META for _, _, _, name in entries):
            raise ValueError(f'{META} is reserved for the generated package; do not commit it')
        manifest = json.loads(git('show', commit + ':submission.json').decode('utf-8'))
        if not isinstance(manifest, dict):
            raise ValueError('submission.json must be a JSON object')
        language = manifest.get('language')
        if not isinstance(language, str) or language not in EXTENSIONS:
            raise ValueError('submission.json must select Java, Python, Cpp or CSharp')
        files = {}
        for mode, kind, object_id, name in entries:
            path = pathlib.PurePosixPath(name)
            if (path.is_absolute() or '..' in path.parts or '\\' in name or ':' in name
                    or name != path.as_posix() or any(ord(character) < 32 for character in name)):
                raise ValueError('unsupported archive path')
            keep = (name in {'README.md', 'ANALYSIS.md', 'SUBMISSION.md', 'submission.json'}
                    or (path.parts[:2] == (language, 'source') and len(path.parts) == 3
                        and path.suffix in EXTENSIONS[language])
                    or (path.parts[:2] == ('tests', 'student')))
            if keep:
                if kind != 'blob' or mode == '120000':
                    raise ValueError(f'symlinks and submodules are not supported: {name}')
                if path.name.startswith('.env') or path.suffix.lower() in {'.pem', '.key', '.p12', '.pfx'}:
                    raise ValueError(f'do not submit credentials or private key files: {name}')
                data = git('cat-file', 'blob', object_id)
                if len(data) > 2 * 1024 * 1024:
                    raise ValueError(f'file exceeds 2 MiB: {name}')
                files[name] = data
    else:
        raise ValueError('HEAD has no committed files')
    required = {'ANALYSIS.md', 'SUBMISSION.md', 'submission.json',
                {'Java': 'Java/source/Main.java', 'Python': 'Python/source/main.py',
                 'Cpp': 'Cpp/source/main.cpp', 'CSharp': 'CSharp/source/Program.cs'}[language]}
    if not required <= files.keys():
        raise ValueError('required files missing: ' + ', '.join(sorted(required - files.keys())))
    if len(files) != len({name.casefold() for name in files}):
        raise ValueError('file names must not differ only by letter case')
    repository = git('remote', 'get-url', 'origin').decode('utf-8').strip()
    if repository.startswith('git@github.com:'):
        repository = 'https://github.com/' + repository.split(':', 1)[1]
    repository = repository.removesuffix('.git')
    if not re.fullmatch(r'https://github\.com/[A-Za-z0-9_.-]+/[A-Za-z0-9_.-]+', repository):
        raise ValueError('origin must be a normal GitHub repository URL without credentials')
    metadata = {'assignment': 3, 'student_id': student_id, 'repository_url': repository,
                'commit': commit, 'language': language,
                'files': {name: hashlib.sha256(data).hexdigest() for name, data in sorted(files.items())}}
    output = ROOT / '.submission'
    output.mkdir(exist_ok=True)
    target = output / f'Assignment3_{student_id}.zip'
    import io
    buffer = io.BytesIO()
    if len(files) > 999 or sum(map(len, files.values())) > 20 * 1024 * 1024:
        raise ValueError('submission has too many files or exceeds 20 MiB uncompressed')
    with zipfile.ZipFile(buffer, 'w', zipfile.ZIP_DEFLATED) as result:
        for name, data in sorted(files.items()):
            result.writestr(name, data)
        result.writestr(META, json.dumps(metadata, ensure_ascii=False, indent=2) + '\n')
    if len(buffer.getvalue()) > 10 * 1024 * 1024:
        raise ValueError('package exceeds the Moodle 10 MiB limit')
    target.write_bytes(buffer.getvalue())
    receipt = f'Repository: {repository}\nCommit: {commit}\nLanguage: {language}\n'
    (output / 'Moodle_submission.txt').write_text(receipt, encoding='utf-8')
    return target, receipt


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--student-id', required=True)
    args = parser.parse_args()
    try:
        target, receipt = package(args.student_id)
        print(receipt + f'ZIP: {target}\nUpload the ZIP and paste the three lines above in Moodle. '
              'Click Submit assignment and check Submitted for grading before the deadline.')
        return 0
    except (ValueError, OSError, KeyError, json.JSONDecodeError, subprocess.CalledProcessError) as error:
        print(f'Cannot package submission: {error}', file=sys.stderr)
        return 1


if __name__ == '__main__':
    sys.exit(main())
