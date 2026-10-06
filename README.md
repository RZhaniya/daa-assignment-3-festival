# Assignment 3: Save the Campus Festival

Version 1.0.0.

The campus festival opens soon. The waffle tent has power, the stage has a plan, and the emergency tea station has neither. Your console toolkit will connect venues, find circular prerequisites, schedule preparations and route supplies. Each graph has its own purpose: undirected cables, directed dependencies, and directed acyclic delivery routes.

## Assessment: exactly 50 + 50
Each assignment has practical work worth 50 and an individual in-class quiz worth 50. Their sum is the assignment score out of 100. Each assignment contributes 20% of the second attestation; the separate Quiz II and Endterm keep their syllabus weights of 20% and 40%. These new quizzes are part of the assignments, not replacements for Quiz II.

Practical points belong to published requirement groups. Within each group: group points * passed checks / total checks, rounded to 0.01 with half-up rounding. The practical sum is capped at 50. Correct optimum values can earn core points even when a witness is invalid; reconstruction points require a valid optimal witness. Scale groups require correct output on published large input sizes within generous resource limits. A failed build prevents checks from earning points. A timeout loses the affected group checks. An internal checker error produces no grade and must be investigated and rerun.

Official checks use the submitted commit and a fixed version of the instructor checker, not your repository's workflow. Additional tests do not change a group's maximum points. Test data may be private, but all tested requirements are published here. Timing medians are for analysis; you are not ranked by milliseconds. The checker records functional correctness and scalability. Black-box tests alone cannot prove which algorithm you used; the stated implementation methods and individual understanding remain required.

## Individual understanding quiz
20 questions, 2.5 points each, maximum 50; 30 minutes, one individual attempt in class without AI. Questions use small traces, states, invariants, counterexamples, complexity, reconstruction and changed conditions. Every attempt takes one question from each of 20 balanced categories. Four substantive variants per category are available. Single-choice and exact integer numerical answers use no negative marks or guessing penalty. An incorrect answer earns 0 for that question; ordinary automatic quiz marking has no partial credit. Dates are announced in Moodle; unpublished activities stay hidden. Practice quizzes A and B do not affect your grade.

## Reconsidering specific quiz errors
You may ask to reconsider points lost on a particular question. This is optional, not a new compulsory defence. The instructor identifies the skill, gives a new equivalent example and asks you to explain the reasoning. Fully demonstrated understanding restores the points lost on that question; a correct main method with one significant unfinished part restores half of the lost points; otherwise the original score remains. Repeating a published answer without explanation is insufficient. The quiz maximum remains 50. Quiz reconsideration does not automatically change practical points. An erroneous question/key is corrected for all affected students.

## Copy, run and submit
Open the official template link in Moodle and select Use this template -> Create a new repository. Choose any repository name. Public or private copies are allowed; if private, provide the instructor with access, or use the authorised local checking route. No payment or private repository is required. Do not fork an instructor checker or copy its private content.

Install your chosen runtime: Java JDK 17+, Python 3.12+, C++17 compiler, or .NET SDK 8. The launcher needs Python 3.12+ for all languages, matching Assignment 2. From the repository root run: python tools/run.py --language Java --setup. Replace Java with Python, Cpp or CSharp as appropriate. setup should pass before algorithm implementation. Run python tools/run.py --language Java --check after working on the algorithms; TODO methods intentionally fail their examples. Run a request file with python tools/run.py --language Java --input examples/dsu.jsonl (A3) or examples/lcs.jsonl (A4). Windows may use py -3 in place of python.

Implement the method bodies in your selected language's source folder. Keep JSON transport separate; it is provided. Add helper classes/functions in that source folder. No external algorithm libraries or built-in substring search may replace required implementations. Standard collections, sorting and priority queues are allowed. C++ targets Linux GCC for official checks; the provided header uses the GCC standard-library convenience header.

Complete ANALYSIS.md with 3-5 sentences per algorithm, three reproducible comparison input sizes, runtime/seed/commands and observations. Explain your states/invariants and reconstruction. This short report supports learning; it has no separate hidden marking group. Record AI tools and other resources used, their contribution, and what you verified. If none, say so. You must understand your submitted implementation; the quiz checks individual understanding. This project does not claim to prevent AI use.

Commit the finished source, your own tests, ANALYSIS.md and SUBMISSION.md. Set the selected language in submission.json. In Moodle submit your repository URL, full 40-character commit SHA and selected language. Record the SHA in the Moodle submission rather than making a self-referential commit. The official grade refers to that fixed commit, not a changing branch. Do not submit build binaries, credentials, private grader files or quiz keys. Dates and submission availability are provided in Moodle, not invented in this document.

## Resource limits
Official compilation: 60 seconds. Each requirement group's published batch: 20 seconds, one CPU, 768 MiB container memory (Java heap up to 512 MiB), 128 processes, at most 8 MiB UTF-8 output. Large checks use up to 50000 graph/tree vertices, 100000 DSU operations, 60000 MST edges, 600x600 LCS states, 120 merge blocks, 100001 KMP text characters, 30000 suffix characters, and 600 alarm patterns over two 100000-character texts. These are representative scale checks inside the full published input domains. Small RK tests respect its honest worst-case bounds. Limits apply with headroom; output correctness and asymptotic analysis matter, not a tiny timing difference on shared CI.


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
