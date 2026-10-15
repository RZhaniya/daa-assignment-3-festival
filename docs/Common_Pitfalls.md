# Assignment 3 — Common pitfalls

- DSU needs path compression and union by size or rank. Rank is not component size. Repeated/self unions leave components and largest unchanged.
- Kruskal accepts an edge only if find(u) != find(v). Preserve original edge IDs when sorting.
- Both MST methods return a minimum spanning forest of every component: n-c edges, no selected loops or repeated IDs, and isolated vertices included in c.
- Prim is a separate adjacency-list/priority-queue implementation; restart in every unvisited component and skip stale heap candidates.
- Equal MST cost alone is not a valid witness. Check that the selected original edges form an optimal spanning forest. Ties allow different optimal edge IDs.
- Kosaraju uses two DFS passes, a transpose and decreasing first-pass finish order. SCC labels may differ; condensation must contain every cross-component pair once.
- DFS needs unvisited, active and finished states. A visited/BLACK vertex alone does not prove a cycle. Reverse finishing order only after checking all components.
- DAG shortest paths use topological relaxation. Reject a cycle anywhere, even outside source reachability. Negative weights are valid; an MST does not solve source shortest paths.
- Skip unreachable predecessors. An unreachable distance is null; -1 can be a valid distance. parent_edge is an edge ID, not a predecessor vertex.
- Actually call the route helper and check its source-to-target sequence, weight, source case and unreachable case. Tied/parallel shortest edges may have different valid IDs.
- Use 64-bit costs/distances, stack-safe deep DFS and new state for every request, including consecutive tasks of different sizes.
- Check correctness before timing Kruskal/Prim. Use the same graphs/environment/boundary, three sizes, at least three runs per method per size, and medians.

See CONTRACTS.md, TESTING.md, ANALYSIS.md and RUBRIC.md for exact requirements. Lecture_Clarifications_EN_RU.pdf identifies the lecture 6 slide 9 correction and the A3 unreachable sentinel.
