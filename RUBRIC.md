# Assignment 3 — Practical rubric

The practical work is worth **50 points: 40 from independent automated checks and 10 from instructor review**. The quiz is a separate 50 points. The Assignment 3 course contribution is unchanged. Public checks give feedback; the instructor assesses the submitted commit using the private grader and the criteria below.

## Automated checks: 40 points

The diagnostic test groups below retain their original relative weights. They produce a diagnostic total out of 50. The automatic contribution is `round_half_up(0.8 × diagnostic_total, 2)` out of 40, calculated once after summing the diagnostic groups.

|Task|Algorithm|Group|Diagnostic maximum|Contribution maximum|
|---|---|---|---:|---:|
|1|DSU|core|4|3.2|
|1|DSU|edge|2|1.6|
|1|DSU|scale|2|1.6|
|2|Kruskal|core|4|3.2|
|2|Prim|core|4|3.2|
|2|Kruskal|witness|2|1.6|
|2|Prim|witness|2|1.6|
|2|Kruskal|scale|1|0.8|
|2|Prim|scale|1|0.8|
|3|Kosaraju SCC|core|5|4.0|
|3|Kosaraju SCC|witness|3|2.4|
|3|Kosaraju SCC|scale|2|1.6|
|4|DFS topological ordering|core|4|3.2|
|4|DFS topological ordering|edge|2|1.6|
|4|DFS topological ordering|scale|2|1.6|
|5|DAG shortest paths|core|5|4.0|
|5|DAG shortest paths|witness|3|2.4|
|5|DAG shortest paths|scale|2|1.6|
|**Total**|||**50**|**40**|

`core` checks basic results and optimum values; `edge` checks published boundary cases; `witness` checks feasible optimal forests, condensation or parent-edge routes; `scale` checks correct large-instance results within the published limits. A diagnostic group earns its maximum multiplied by the fraction of checks passed, rounded half-up to 0.01. Different valid optimal witnesses are accepted. No unpublished input domain or additional algorithm is required.

## Instructor review: 10 points

The instructor records evidence from the same commit: source paths/lines, an executed helper/test call, or the relevant section of `ANALYSIS.md`. The quiz does not substitute for this review.

|Criterion|Maximum|Evidence required|
|---|---:|---|
|DSU method|1|Path compression and union by size or rank, with separate component-size/statistics maintenance.|
|Kruskal method|1|Sort original edges and use DSU; accept an edge only between different representatives.|
|Prim method|1|A separate adjacency-list and heap implementation; discard stale entries and restart in every component.|
|Kosaraju method|1|Two DFS passes, transpose, and the correct finishing order.|
|DFS topological method|1|WHITE/GRAY/BLACK states, cycle detection by GRAY edges, reverse postorder over all components.|
|DAG method and reconstruction helper|1|0.5 for topological relaxation that skips unreachable vertices; 0.5 for an implemented and actually exercised helper using `parent_edge`.|
|English `ANALYSIS.md`|1|0.5 for invariants/correctness arguments for the six methods; 0.5 for time and memory including returned results, sources and disclosure of assistance.|
|Own `tests/student`|1|0.5 for reproducible ordinary and boundary cases; 0.5 for meaningful witness/alternative-optimum and independent-request checks with justified expected results.|
|Kruskal–Prim benchmark|2|0.5 each: common graphs/environment with n, m and seed/generator; three sizes; at least three measurements of each method per size with medians; equal timing boundaries and a reasoned interpretation after checking correctness.|
|**Total**|**10**||

For each of the first five method criteria, award 1 for the complete required method, 0.5 for an identifiable implementation of that method with one substantial incomplete part, and 0 for an absent or substituted method. DAG and the remaining criteria use the half-point components shown in the table. Correct library data structures are allowed as specified in the assignment; the six required algorithms must be implemented by the student.

The final practical score is `automatic_contribution + instructor_review`, at most 50. Until instructor review is complete, the private automated report shows an automatic contribution and a pending practical result. A missing helper loses its stated half-point review component and may also fail relevant result checks; it does not create an additional whole-assignment penalty. There are no extra deductions outside this rubric. Infrastructure or temporary access failures remain pending for investigation and rerun; they do not automatically become a student zero. A faulty test/key is corrected consistently for all affected work.

Benchmark measurements must use the same input graphs, environment and timing scope for both algorithms. State whether parsing, graph construction and I/O are included; exclude compilation from algorithm timing. Correctness is checked before comparing runtimes. One run or three medians do not prove asymptotic complexity: supply the algorithmic argument separately.

Follow the syllabus on academic integrity and AI: acknowledge assistance in writing and take responsibility for correctness, originality and quality. Style alone is not evidence of misconduct. Moodle records final submission time; the linked commit records the assessed version.
