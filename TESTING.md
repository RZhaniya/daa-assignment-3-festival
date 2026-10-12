# Testing your algorithms

## GitHub: the default route

Choose your language in submission.json, commit and push. Open Actions → Public checks. The workflow installs the checking environment and runs your chosen language. You do not need to install Python to use this route.

Read each step: Compile and launch checks that the program starts; Algorithm examples runs 36 examples/edge cases and repeats the complete batch twice in one process to check state reset. Submission preparation reminds you to complete ANALYSIS.md and add own examples/results under tests/student/. Public tooling regressions check the supplied testing tools.

Fix failures and push again. Green Public checks for the submitted exact commit is the preparation evidence to link in Moodle; the instructor uses independent deeper checks after the deadline.

## Local checking: optional

If you prefer local checks, install Python 3.12+ for the launcher **and** your selected runtime: JDK17+, Python3.12+, a C++17 compiler, or .NET SDK8 (the SDK, not only the runtime). Run from the repository folder:

```sh
python tools/run.py --setup
python tools/run.py --check
```

The launcher uses submission.json and compiles/runs that language. Python here is the tool language, not a restriction on your solution. Windows may use py -3 instead of python. --language Java/Python/Cpp/CSharp overrides the language for one run.

For your own JSON-lines input file:

```sh
python tools/run.py --input tests/student/my_cases.jsonl
```

Each line holds one request; each output line is its JSON result. Keep prompts/debugging out of stdout. --setup checks transport; --check fails on TODOs until they are implemented. Public tests supplement your own examples, not replace them. Exact fields and full input bounds are in CONTRACTS.md.
