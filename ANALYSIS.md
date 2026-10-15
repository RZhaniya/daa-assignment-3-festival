# How my solution works

Write in English. Replace each REPLACE_WITH with your own explanation and evidence. This report is a required learning deliverable. The practical grade is entirely automatic out of 50, with no separate manual points or discretionary deductions; see RUBRIC.md.

## Algorithms

For each method, use 3–5 sentences to explain the state/invariant, main steps and correctness, important edge cases, and time/memory including output. State how every request resets its state and how deep DFS avoids call-stack failure. Distinguish the two MST methods and explain why their edges are safe.

- DSU (path compression + union by size/rank, component size/largest): REPLACE_WITH
- Kruskal (sorted original edges + DSU, forest of all components): REPLACE_WITH
- Prim (adjacency lists + heap, restart for each component): REPLACE_WITH
- Kosaraju SCC and exact condensation (two passes, transpose, finish order): REPLACE_WITH
- DFS topological order (three states, reverse postorder, cycles anywhere): REPLACE_WITH
- DAG shortest paths (topological relaxation, negative weights, null) and route helper: REPLACE_WITH

## Kruskal versus Prim

Verify correct costs and valid forests **before** timing. Use the same graph instances and environment for both algorithms. Compare **three sizes**, giving n and m, generator/seed or stored input, commands and runtime/hardware details. At **each size**, record **at least three timings per method** and their medians. Exclude compilation from algorithm timing. Record any warm-up and use the same timing boundary: explicitly include/exclude graph construction, parsing, I/O and warm-up equally for both methods. IDs may differ between optimal forests.

|Size|n|m|Kruskal measurements (ms)|Median (ms)|Prim measurements (ms)|Median (ms)|
|---|---:|---:|---|---:|---|---:|
|Small|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|
|Medium|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|
|Large|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|REPLACE_WITH|

Explain observations and limits of the experiment. A few runs are not proof of asymptotic complexity; do not rank correctness by timing alone.

REPLACE_WITH

## My tests and route reconstruction

Link reproducible inputs and expected/actual results in tests/student/. Explain your expected answers independently; accept different valid optima and component labels. Include relevant edge cases, large/deep input and consecutive requests of different tasks/sizes in one process.

Actually call your route helper for a reachable target, the source ([source]) and an unreachable target (state your no-route representation). Show the calls, resulting vertex sequences and the selected parent_edge IDs. Include a tied/parallel-edge example and verify direction and total weight.

REPLACE_WITH

## Assistance and sources

Follow the syllabus: acknowledge AI tools and other resources in writing, state their contribution, your changes and your own verification. AI may assist with brainstorming, debugging and code suggestions; you remain responsible for correctness, originality and quality. If none were used, say so. Do not claim work or checks you did not perform.

REPLACE_WITH
