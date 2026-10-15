# Assignment 3 — Practical rubric

The practical work is worth **50 points, entirely from independent automated checks**. The quiz is a separate **50 automatic points**. Assignment 3 retains its existing course contribution. The private grader evaluates the submitted commit; Public checks provide preparation feedback.

## Automated groups: 50 points

The original test-group weights are used directly. There is no 0.8 multiplier and no manual component.

|Task|Algorithm|Group|Maximum|
|---|---|---|---:|
|1|DSU|core|4|
|1|DSU|edge|2|
|1|DSU|scale|2|
|2|Kruskal|core|4|
|2|Prim|core|4|
|2|Kruskal|witness|2|
|2|Prim|witness|2|
|2|Kruskal|scale|1|
|2|Prim|scale|1|
|3|Kosaraju SCC|core|5|
|3|Kosaraju SCC|witness|3|
|3|Kosaraju SCC|scale|2|
|4|DFS topological ordering|core|4|
|4|DFS topological ordering|edge|2|
|4|DFS topological ordering|scale|2|
|5|DAG shortest paths|core|5|
|5|DAG shortest paths|witness|3|
|5|DAG shortest paths|scale|2|
|**Total**|||**50**|


`core` checks results and optimum values; `edge` checks published boundary cases; `witness` checks feasible optimal forests, condensation or parent-edge routes; `scale` checks correct large-instance results within the published limits. Each group earns its maximum times the fraction of checks passed, rounded half-up to 0.01. The group scores are summed, capped at 50. Different valid optimal witnesses are accepted.

## Required learning work

Implement the six required methods and call the route-reconstruction helper. Submit English `ANALYSIS.md`, own `tests/student` and the Kruskal–Prim benchmark described in the specification. These remain required learning deliverables; they receive no separate manual points or discretionary deductions. Automated result tests do not prove the chosen internal method, authorship or the truth of benchmark measurements. The instructor may give feedback; a conversation or oral explanation does not add to or subtract from the automatic score.

Benchmark uses identical graphs, environment and timing scope for both algorithms: three sizes, n/m and seed/generator, at least three measurements per method per size, and medians. Verify correctness before timing and exclude compilation. Timings do not prove asymptotic complexity; give the algorithmic argument in the analysis.

## Quiz and corrections

The quiz contains 30 single-answer questions. Each correct answer earns 1 raw point; a wrong or blank answer earns 0. Final quiz grade = raw / 30 × 50, calculated once. There is no oral defence, oral score recovery or manual bonus. If a question/key or grader test is defective, correct it consistently for all affected work under course rules and rerun/regrade the original evidence. This is correction of an assessment defect, not an individual oral reassessment.

Infrastructure or temporary access failures remain pending for investigation and rerun; they do not become a student zero. No extra penalties are introduced outside this rubric. Follow the syllabus on academic integrity and AI: disclose assistance in writing and take responsibility for the work. Style alone is not evidence of misconduct. Moodle records final submission time; the linked commit records the assessed version.
