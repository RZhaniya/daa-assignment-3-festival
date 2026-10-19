"""Run the public micro-problem cases. This is practice feedback, not grading."""
import copy
import contextlib
import io
import json
import traceback

def same_answer(problem_id, actual, expected):
    if problem_id == 'pairs':
        if not isinstance(actual, list):
            return False
        if not all(isinstance(p, list) and len(p) == 2 and all(type(x) is int for x in p) for p in actual):
            return False
        pairs = [tuple(p) for p in actual]
        return len(set(pairs)) == len(pairs) and sorted(pairs) == sorted(map(tuple, expected))
    if type(actual) is not type(expected):
        return False
    if isinstance(expected, list):
        return len(actual) == len(expected) and all(same_answer('', a, e) for a, e in zip(actual, expected))
    return actual == expected

class LimitedOutput(io.StringIO):
    def write(self, text):
        if self.tell() + len(text) > 6000:
            raise RuntimeError('Output exceeded 6000 characters')
        return super().write(text)

def run_practice(source, problem_id, cases):
    output = LimitedOutput()
    namespace = {'__name__': '__practice__'}
    reports = []
    with contextlib.redirect_stdout(output), contextlib.redirect_stderr(output):
        try:
            exec(compile(source, 'solution.py', 'exec'), namespace)
            solve = namespace.get('solve')
            if not callable(solve):
                raise TypeError('Define a callable solve function')
        except Exception:
            return {'error': traceback.format_exc(limit=4), 'cases': [], 'stdout': output.getvalue()}
        for index, case in enumerate(cases):
            report = {'index': index, 'args': case['args'], 'expected': case['expected']}
            try:
                result = solve(*copy.deepcopy(case['args']))
                # Normalize JSON-compatible tuples, reject NaN and unserializable objects.
                encoded = json.dumps(result, allow_nan=False)
                if len(encoded) > 100000:
                    raise ValueError('Result exceeds the practice output limit')
                actual = json.loads(encoded)
                report.update(actual=actual, passed=same_answer(problem_id, actual, case['expected']))
            except Exception:
                report.update(passed=False, error=traceback.format_exc(limit=3))
            reports.append(report)
    return {'cases': reports, 'stdout': output.getvalue()}
