# Assignment 3: Save the Campus Festival

Version 1.0.1. Maintenance update; task contracts and scoring weights are unchanged.

The campus festival opens soon. The waffle tent has power, the stage has a plan, and the emergency tea station has neither. Your console toolkit will connect venues, find circular prerequisites, schedule preparations and route supplies. Each graph has its own purpose: undirected cables, directed dependencies, and directed acyclic delivery routes.

## Assessment: exactly 50 + 50
Each assignment has practical work worth 50 and an individual in-class quiz worth 50. Their sum is the assignment score out of 100. Each assignment contributes 20% of the second attestation; the separate Quiz II and Endterm keep their syllabus weights of 20% and 40%. These new quizzes are part of the assignments, not replacements for Quiz II.

Practical points belong to published requirement groups. Within each group: group points * passed checks / total checks, rounded to 0.01 with half-up rounding. The practical sum is capped at 50. Correct optimum values can earn core points even when a witness is invalid; reconstruction points require a valid optimal witness. Scale groups require correct output on published large input sizes within generous resource limits. A failed build prevents checks from earning points. A timeout loses the affected group checks. An internal checker error produces no grade and must be investigated and rerun.

Official checks use the submitted commit and a fixed version of the instructor checker, not your repository's workflow. Additional tests do not change a group's maximum points. Test data may be private, but all tested requirements are published here. Timing medians are for analysis; you are not ranked by milliseconds. The checker records functional correctness and scalability. Black-box tests alone cannot prove which algorithm you used; the stated implementation methods and individual understanding remain required.

## Individual understanding quiz
The individual quiz is a separate part worth up to 50 points. Its question bank and final arrangements are deferred and will be announced before the activity opens. Question count, duration, attempts, question formats and category balance will be confirmed by the instructor. The graded quiz remains hidden during preparation. Practice quizzes A and B are optional preparation and do not affect your grade.

## Reconsidering specific quiz errors
You may ask to reconsider points lost on a particular question. This is optional, not a new compulsory defence. The instructor identifies the skill, gives a new equivalent example and asks you to explain the reasoning. Fully demonstrated understanding restores the points lost on that question; a correct main method with one significant unfinished part restores half of the lost points; otherwise the original score remains. Repeating a published answer without explanation is insufficient. The quiz maximum remains 50. Quiz reconsideration does not automatically change practical points. An erroneous question/key is corrected for all affected students.

## Copy, run and submit
Open the official template link in Moodle and select Use this template -> Create a new repository. Choose any repository name. Public or private copies are allowed; if private, provide the instructor with access, or use the authorised local checking route. No payment or private repository is required. Do not fork an instructor checker or copy its private content.

Install your chosen runtime: Java JDK 17+, Python 3.12+, C++17 compiler, or .NET SDK 8. Python 3.12+ is needed for the launcher in every language. Set language in submission.json, then run python tools/run.py --setup. After implementing the algorithms, run python tools/run.py --check. Run a UTF-8 request file with python tools/run.py --input examples/dsu.jsonl. All commands use submission.json by default; --language Java/Python/Cpp/CSharp overrides it for that run. Windows may use py -3 in place of python. Setup checks transport only; TODO algorithms intentionally fail --check. See TESTING.md for public cases and reset checks.

Implement the method bodies in your selected language's source folder. Keep the provided JSON transport separate. Add helper files at the top level of that source folder: .java, .py, .cpp/.hpp/.h or .cs, depending on your language. C++ builds all top-level .cpp files; headers are included normally. Nested source folders are not part of the build contract. Standard collections, sorting and priority queues are allowed; external algorithm libraries cannot replace required implementations. C++ targets Linux GCC for official checks. Keep the required methods visible in your source and explain them in ANALYSIS.md so the instructor can inspect that you used DSU with both heuristics, distinct Kruskal/Prim implementations, Kosaraju, DFS topological ordering and DAG relaxation. The public launcher does not prove algorithm choice and introduces no extra scoring group or penalty.

Complete ANALYSIS.md with 3-5 sentences per algorithm, three reproducible comparison input sizes, runtime/seed/commands and observations. Explain your states/invariants and reconstruction. This short report supports learning; it has no separate hidden marking group. Record AI tools and other resources used, their contribution, and what you verified. If none, say so. You must understand your submitted implementation; the quiz checks individual understanding. This project does not claim to prevent AI use.

Commit the finished source, your own tests, ANALYSIS.md and SUBMISSION.md. Set the selected language in submission.json. In Moodle submit your repository URL, full 40-character commit SHA and selected language. Record the SHA in the Moodle submission rather than making a self-referential commit. The official grade refers to that fixed commit, not a changing branch. Do not submit build binaries, credentials, private grader files or quiz keys. Dates and submission availability are provided in Moodle, not invented in this document.

## Resource limits
Official compilation: 60 seconds. Each requirement-group batch: 20 seconds, one CPU, 768 MiB container memory (Java heap up to 512 MiB), 128 processes, at most 8 MiB UTF-8 output. Representative large checks use up to 50000 graph vertices, 100000 DSU operations and 60000 MST edges, inside the full input domains in CONTRACTS.md. Limits apply with headroom; output correctness and asymptotic analysis matter, not tiny timing differences on shared CI.

## Practical rubric (50 points)

|Task|Algorithm|Requirement group|Points|
|---|---|---|---|
|1|dsu|core|4|
|1|dsu|edge|2|
|1|dsu|scale|2|
|2|kruskal|core|4|
|2|prim|core|4|
|2|kruskal|witness|2|
|2|prim|witness|2|
|2|kruskal|scale|1|
|2|prim|scale|1|
|3|scc|core|5|
|3|scc|witness|3|
|3|scc|scale|2|
|4|topo|core|4|
|4|topo|edge|2|
|4|topo|scale|2|
|5|dag|core|5|
|5|dag|witness|3|
|5|dag|scale|2|

core: optimum values/basic behaviour; edge: published edge cases; witness: feasible optimal reconstruction/condensation; scale: correct large-instance results. Each group has a fixed maximum. The sum is 50.

## Exact task specification

Read CONTRACTS.md and the PDF in docs/. Expected examples are in tests/public.json and examples/.
