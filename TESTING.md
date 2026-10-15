# Testing your algorithms

## GitHub: the default route

Choose your language in submission.json, commit and push. Open Actions → Public checks. The workflow installs the checking environment and runs your chosen language. You do not need to install Python to use this route.

Read each step: Compile and launch checks that the program starts; Algorithm examples runs 36 examples/edge cases and repeats the complete batch twice in one process to check state reset. Submission preparation reminds you to complete ANALYSIS.md and add own examples/results under tests/student/. Public tooling regressions check the supplied testing tools.

Fix failures and push again. Green Public checks for the submitted exact commit is the preparation evidence to link in Moodle; the instructor uses independent deeper checks after the deadline.

A setup success followed by algorithm failures in the untouched starter is expected: its six methods contain intentional TODOs. Check the failing step and diagnostic before deciding whether it is a build, runtime or algorithm problem.

The instructor checks the submitted SHA using independently controlled tooling. The automatic practical score is out of 50, with no manual component or scaling. Methods/helper, English analysis, own tests and benchmark remain learning deliverables; there are no separate manual points or discretionary deductions. An internal checker/environment error requires investigation and rerun, not a student zero. See RUBRIC.md.

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

## Your own tests

Store inputs, explained expected answers, commands and actual results under tests/student/. The public workflow runs the provided algorithm cases; it does not automatically discover arbitrary student files. Run your files explicitly with --input and document how the results were checked.

For non-unique answers, check meaning: MST feasibility and minimum cost, SCC equivalence and exact cross-component pairs, topological precedence, and optimal parent-edge paths. A different optimal edge list, topological order or SCC label numbering is not an error. Include a wrong witness that your own checking rejects and another valid optimum that it accepts where applicable.

Use n=0 only for dsu/kruskal/prim/scc/topo; dag requires n>=1 and a valid source. Exercise repeated/self unions, isolates, loops, parallel/tied edges, negative weights and large totals, a cycle outside the source component, and long chains. Check multiple requests of different tasks and sizes in one process, then repeat them to detect retained state. Exact domains extend to n=100000 and m/q=250000 where applicable. Do not use malformed or out-of-domain inputs as required correctness cases.

Call your reconstruction helper directly from your own test program; --input exercises the six JSON tasks and does not invoke a separate helper task. Record reachable, source, unreachable and tied/parallel-edge cases.

For benchmarks follow ANALYSIS.md: correctness first, same graphs/environment/timing boundary, three sizes, at least three measurements of each method per size and median times.

## Official execution limits

The instructor checker allows 60 seconds for compilation and 20 seconds for each requirement-group batch on one CPU. The container has 768 MiB memory (Java heap up to 512 MiB), 128 processes and 256 MiB tmpfs. UTF-8 stdout is limited to 8 MiB; each source file to 2 MiB. These are resource bounds for published graph sizes, not a speed competition. The optional public launcher also uses a 60-second build and 20-second requested batch timeout; its Java heap is 384 MiB. Public checks and instructor checks use their respective fixed environments.
