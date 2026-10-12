"""Preflight feedback on required files and disclosure; this is not an AI detector."""
import json
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parents[1]


def main():
    problems = []
    try:
        manifest = json.loads((ROOT / 'submission.json').read_text(encoding='utf-8'))
        if not isinstance(manifest, dict):
            raise ValueError('manifest must be an object')
        language = manifest['language']
        if language not in ('Java', 'Python', 'Cpp', 'CSharp'):
            problems.append('Select one supported language in submission.json.')
    except (OSError, ValueError, KeyError):
        problems.append('submission.json is missing or invalid.')
    analysis = (ROOT / 'ANALYSIS.md').read_text(encoding='utf-8') if (ROOT / 'ANALYSIS.md').is_file() else ''
    if not analysis.strip() or 'REPLACE_WITH' in analysis:
        problems.append('Complete ANALYSIS.md in your own words, including the assistance disclosure.')
    if not any(path.is_file() and path.name != 'README.md' for path in (ROOT / 'tests/student').rglob('*')):
        problems.append('Add your own test inputs and results under tests/student/.')
    for problem in problems:
        print('CHECK: ' + problem)
    if problems:
        print('These are preparation reminders, not proof of AI use or an automatic grade.')
        return 1
    print('PASS preparation: required documents and own test files are present. '
          'Run algorithm checks too; a human still reviews the content.')
    return 0


if __name__ == '__main__':
    sys.exit(main())
