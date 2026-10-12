# Running and understanding public checks

1. Set the selected language in submission.json: Java, Python, Cpp or CSharp.
2. Run `python tools/run.py --setup`. This checks only compilation and JSON transport with ping.
3. Implement the TODO methods, then run `python tools/run.py --check`.
4. Run your own request file with `python tools/run.py --input examples/dsu.jsonl`.

All commands use submission.json unless you pass `--language` explicitly. The launcher prints the selected language and compiler messages to stderr; program JSON results from --input remain on stdout. It reports TODO messages, runtime errors, malformed output and timeouts directly. On Windows, `py -3` may replace `python`.

## Coverage

There are 36 named public cases: the original examples; empty and isolated graphs; repeated/self union; negative weights; self-loops and parallel edges; tied forests/paths; 64-bit sums; SCC condensation deduplication; directed and unreachable cycles; distance -1 versus unreachable null; and 2000-vertex chains for SCC, topo and DAG.

Each case runs in a fresh process. Then the complete suite runs twice in one process, including larger and smaller graphs and repeated requests. This catches state left over from earlier requests. Answer each input line exactly once, with one JSON object and no prompts/debug lines.

The semantic validator accepts alternative optimal forests, SCC labels, topological orders and shortest-route predecessors. JSON integer fields must be integers, not booleans or decimal numbers. Malformed answers fail normally.

These checks are learning feedback, not the official grade and not an exhaustive test suite. Add your own randomized, boundary and larger-scale checks. Official grading uses the instructor's fixed checker and the uploaded Moodle ZIP, with the unchanged published 50-point rubric. Before implementation, algorithm failures and a failing algorithm CI step are expected; setup and tool regression checks should pass.

## Tool regression checks

`python -m unittest discover -s tools -p "test_*.py"` checks launcher and validator behaviour without requiring completed student algorithms. These tests do not implement the assignment algorithms.

## Your own tests and submission

Add your own inputs and expected/actual results under tests/student/. Run python tools/check_submission.py for preparation reminders. Before packaging, commit and push the files and confirm git status is clean. Use python tools/package_submission.py --student-id YOUR_NUMERIC_STUDENT_ID. Upload its ZIP and receipt text in Moodle, then finalize the submission before the deadline. Public checks are feedback; the uploaded ZIP is used for official checking.
