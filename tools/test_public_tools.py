"""Regression checks for public tooling, not student algorithm implementations."""
import copy
import json
import pathlib
import shutil
import subprocess
import sys
import tempfile
import unittest
from unittest.mock import patch

import public_validate
import run


class ValidatorTests(unittest.TestCase):
    def test_published_answers(self):
        cases = json.loads((run.P / 'tests/public.json').read_text(encoding='utf-8'))
        for case in cases:
            with self.subTest(case=case['name']):
                self.assertTrue(public_validate.check(case['input'], case['output'], case['output']))

    def test_alternative_topological_order_and_labels(self):
        request = {'task': 'topo', 'n': 3, 'edges': [[0, 2], [1, 2]]}
        self.assertTrue(public_validate.check(request, {'acyclic': True, 'order': [1, 0, 2]},
                                             {'acyclic': True, 'order': [0, 1, 2]}))
        request = {'task': 'scc', 'n': 3, 'edges': [[0, 1], [1, 0], [1, 2]]}
        self.assertTrue(public_validate.check(request, {'component': [7, 7, 20], 'condensation': [[7, 20]]},
                                             {'component': [0, 0, 1], 'condensation': [[0, 1]]}))

    def test_bad_types_missing_fields_and_witnesses(self):
        examples = [
            ({'task': 'dsu', 'n': 2, 'operations': [[0, 1]]}, {'states': [[1, 2]]}),
            ({'task': 'prim', 'n': 2, 'edges': [[0, 1, 2]]}, {'cost': 2, 'components': 1, 'edge_ids': [0]}),
            ({'task': 'topo', 'n': 2, 'edges': [[0, 1]]}, {'acyclic': True, 'order': [0, 1]}),
            ({'task': 'dag', 'n': 2, 'source': 0, 'edges': [[0, 1, 1]]},
             {'acyclic': True, 'distance': [0, 1], 'parent_edge': [-1, 0]}),
        ]
        for request, expected in examples:
            for malformed in [None, [], {}, {'error': 'TODO'}]:
                self.assertFalse(public_validate.check(request, malformed, expected))
            for key, value in expected.items():
                malformed = copy.deepcopy(expected)
                del malformed[key]
                self.assertFalse(public_validate.check(request, malformed, expected))
                if type(value) is bool:
                    malformed = copy.deepcopy(expected)
                    malformed[key] = int(value)
                    self.assertFalse(public_validate.check(request, malformed, expected))
                elif type(value) is int:
                    malformed = copy.deepcopy(expected)
                    malformed[key] = float(value)
                    self.assertFalse(public_validate.check(request, malformed, expected))
        self.assertFalse(public_validate.check(examples[2][0], {'acyclic': True, 'order': [False, True]}, examples[2][1]))
        self.assertFalse(public_validate.check(examples[3][0], {'acyclic': True, 'distance': [0, 1], 'parent_edge': [-1, -1]}, examples[3][1]))


class LauncherTests(unittest.TestCase):
    def test_manifest_and_explicit_override(self):
        with tempfile.TemporaryDirectory() as folder, patch.object(run, 'P', pathlib.Path(folder)):
            manifest = pathlib.Path(folder) / 'submission.json'
            manifest.write_text('{"language":"Python"}', encoding='utf-8')
            self.assertEqual(run.select_language(None), 'Python')
            self.assertEqual(run.select_language('Java'), 'Java')
            manifest.write_text('{"language":"Unknown"}', encoding='utf-8')
            with self.assertRaises(ValueError):
                run.select_language(None)

    def test_protocol_and_runtime_diagnostics(self):
        with tempfile.TemporaryDirectory() as folder:
            script = pathlib.Path(folder) / 'probe.py'
            command = [sys.executable, str(script)]
            script.write_text('import sys\nfor line in sys.stdin: print("{\\"status\\":\\"ready\\"}")\n', encoding='utf-8')
            self.assertEqual(run.execute(command, [{'task': 'ping'}] * 2), [{'status': 'ready'}] * 2)
            for text, message in [
                ('print("debug")\n', 'expected 2'),
                ('print("{}")\nprint("{}")\nprint("{}")\n', 'expected 2'),
                ('print("{\\"error\\":\\"TODO dsu\\"}")\n', 'TODO dsu'),
                ('print("[]")\n', 'JSON object'),
                ('print("{\\"cost\\":NaN}")\n', 'not a JSON number'),
                ('import sys\nprint("trace detail",file=sys.stderr)\nsys.exit(4)\n', 'trace detail'),
            ]:
                script.write_text(text, encoding='utf-8')
                requests = [{'task': 'ping'}] * (2 if message.startswith('expected') else 1)
                with self.subTest(message=message), self.assertRaisesRegex(ValueError, message):
                    run.execute(command, requests)

    @unittest.skipUnless(shutil.which('g++'), 'C++ compiler not installed in this environment')
    def test_cpp_helper_translation_unit(self):
        with tempfile.TemporaryDirectory() as folder, patch.object(run, 'P', pathlib.Path(folder)):
            source = pathlib.Path(folder) / 'Cpp/source'
            source.mkdir(parents=True)
            (source / 'main.cpp').write_text('int helper(); int main(){return helper()==42?0:1;}\n')
            (source / 'helper.cpp').write_text('int helper(){return 42;}\n')
            command = run.build('Cpp')
            self.assertEqual(subprocess.run(command, timeout=20).returncode, 0)


if __name__ == '__main__':
    unittest.main()
