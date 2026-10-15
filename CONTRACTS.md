# Assignment 3 — Graph Algorithms for a Campus Festival

## Exact contracts


One JSON object per input line, one JSON result object per line. One task per request. No prompts or debug output on stdout. Independent requests reset all task state. Provided adapters handle input/output. Inputs satisfy the published domain; malformed JSON is not graded.

UTF-8 JSON lines. Task names are case-sensitive.

All vertices and edges use zero-based indices. An edge ID is its position in the input edge array. All costs, distances, weights and counts use signed 64-bit integers; null denotes unreachable distance. In each vertex pair, 0 <= u,v < n. All input limits below are inclusive. n is the vertex count, m the edge count, q the DSU-operation count; memory bounds include the result. Output object field order, forest edge order and condensation edge order are unrestricted. A valid non-unique witness must be accepted semantically.

## Task 1 - Connectivity / DSU

Track connected components as undirected connections are added.

Input: {task:dsu,n,operations:[[u,v],...]}

Output: {states:[[components,largest],...]}

Limits: 0 <= n <= 100000; 0 <= q <= 250000 operations. If n=0, operations must be empty. Self-union and repeated union are valid.

Implement find and union, path compression and union by size or rank. Initially n singleton components; largest=0 for n=0. Return a state after every operation. A union within one component does not change the count or size. With union by rank, maintain component sizes separately: rank is not size. Initially largest=1 for n>0. Both path compression and one balancing heuristic are required.

Complexity: O(n+q alpha(n)) amortized time, O(n+q) memory including output. Initialisation is O(n); q operations are O(q alpha(n)) amortized. O((n+q) alpha(n)) is also a valid looser upper bound.

Example input:

{"task": "dsu", "n": 4, "operations": [[0, 1], [1, 2], [0, 2], [3, 3]]}

One accepted output:

{"states": [[3, 2], [2, 3], [2, 3], [2, 3]]}

The third operation joins vertices already in one component. The self-union leaves vertex 3 isolated.

## Task 2 - Budget Network / Kruskal

Find a minimum spanning forest of an undirected weighted graph.

Input: {task:kruskal,n,edges:[[u,v,weight],...]}

Output: {cost,edge_ids:[...],components}

Limits: 0 <= n <= 100000; 0 <= m <= 250000; -1000000000 <= weight <= 1000000000. Parallel edges and self-loops allowed.

Implement Kruskal using DSU. Return a minimum spanning forest, including all connected components and isolated vertices; components=0 for n=0. cost is the total forest cost. Return input edge IDs, with no repetitions. Self-loops are excluded. A graph with c connected components requires exactly n-c forest edges. Accept an edge only if find(u) != find(v). Any optimal forest is accepted. Negative weights represent discounts.

Complexity: O(n+m log(m+1)) time, O(n+m) memory.

Example input:

{"task": "kruskal", "n": 4, "edges": [[0, 1, 4], [1, 2, 2], [0, 2, 3]]}

One accepted output:

{"cost": 5, "edge_ids": [1, 2], "components": 2}

Venue 3 is isolated. The best available forest costs 5; it does not connect every venue.

## Task 2 - Budget Network / Prim

Independently build the same minimum-cost forest by expanding one component at a time.

Input: {task:prim,n,edges:[[u,v,weight],...]}

Output: {cost,edge_ids:[...],components}

Limits: Same as Kruskal.

Implement Prim with adjacency lists and a priority queue. Restart at every unvisited component. Use the same forest contract as Kruskal. This must be a separate implementation from Kruskal. Costs and feasibility must agree, but selected edge IDs may differ. Compare algorithms on three input sizes in ANALYSIS.md.

Complexity: O(n+m log(m+1)) for a lazy heap; O((n+m) log(n+1)) for an indexed heap; O(n+m) memory.

Example input:

{"task": "prim", "n": 3, "edges": [[0, 1, 1], [1, 2, 1], [0, 2, 1]]}

One accepted output:

{"cost": 2, "edge_ids": [0, 2], "components": 1}

Several answers are optimal. The validator checks the forest rather than matching one reference edge list.

## Task 3 - Circular Dependencies / SCC

Identify maximal groups of mutually reachable vertices.

Input: {task:scc,n,edges:[[u,v],...]}

Output: {component:[label per vertex],condensation:[[from_label,to_label],...]}

Limits: 0 <= n <= 100000; 0 <= m <= 250000. Directed edges; duplicates and self-loops allowed.

Implement Kosaraju with two DFS passes and a transposed graph. Use decreasing first-pass finish order in the second pass; either graph may be used first if the second pass uses its transpose. Labels are nonnegative integers; their values and order are unrestricted. Equal labels must mean exactly one SCC. Condensation contains each cross-component pair once and no internal edges. Handle isolated vertices and deep graphs. Condensation is a DAG, but internal dependency cycles still need a real scheduling decision.

Complexity: O(n+m) expected time with a hash set for edge deduplication; O(n+m) memory.

Example input:

{"task": "scc", "n": 4, "edges": [[0, 1], [1, 0], [1, 2], [0, 2], [2, 3]]}

One accepted output:

{"component": [5, 5, 7, 9], "condensation": [[5, 7], [7, 9]]}

Vertices 0 and 1 form one SCC. Two original edges become one condensation edge.

## Task 4 - Festival Schedule / Topological Ordering

Determine whether the directed dependency graph admits a topological order.

Input: {task:topo,n,edges:[[u,v],...]}

Output: {acyclic:boolean,order:[...]}

Limits: Same directed graph domain as SCC. Edge u->v means u must finish before v starts.

Implement DFS with unvisited/active/finished states and reverse postorder. A back edge to an active vertex indicates a cycle; an edge to a finished (BLACK) vertex alone does not. For a cyclic graph return acyclic=false, order=[]. For a DAG list every vertex exactly once, respecting every edge. Any valid order is accepted. The empty graph has acyclic=true, order=[].

Complexity: O(n+m) time and memory, including explicit DFS stack.

Example input:

{"task": "topo", "n": 4, "edges": [[0, 2], [1, 2], [2, 3]]}

One accepted output:

{"acyclic": true, "order": [1, 0, 2, 3]}

Preparations 0 and 1 may be swapped; both must precede 2.

## Task 5 - Delivery Routes / DAG Shortest Paths

Find shortest routes in a directed acyclic graph, allowing negative edge weights.

Input: {task:dag,n,edges:[[u,v,weight],...],source}

Output: {acyclic:boolean,distance:[integer or null per vertex],parent_edge:[edge ID or -1 per vertex]}

Limits: 1 <= n <= 100000; 0 <= m <= 250000; -1000000000 <= weight <= 1000000000; 0 <= source < n. Directed graph; parallel edges and self-loops allowed. Cyclic input must be rejected as below.

Check acyclicity, then relax edges in topological order. On ANY directed cycle, including an unreachable cycle, return acyclic=false and both arrays empty. Otherwise source has distance=0 and parent_edge=-1. Unreachable vertices have distance=null and parent_edge=-1. Every other parent edge ends at that vertex and reconstructs an optimal source path. Provide a route reconstruction helper and call it in your own tests for a reachable target, source and unreachable target; record these results in ANALYSIS.md. Return a source-to-target vertex sequence for a reachable target and [source] for the source; document the no-route result for an unreachable target. Follow the selected input edge IDs. This is an implementation helper, not a new JSON task. Never relax from null/unreachable distance. -1 is a valid possible negative distance, so it cannot represent unreachable. parent_edge contains an input edge ID, not a predecessor vertex. Multiple shortest routes are accepted; MST is unrelated to this objective.

Complexity: O(n+m) time and memory. Route reconstruction uses O(route length) time and output space; length is the number of vertices returned.

Example input:

{"task": "dag", "n": 5, "source": 0, "edges": [[0, 1, 3], [0, 2, 8], [1, 2, -7], [2, 3, 4]]}

One accepted output:

{"acyclic": true, "distance": [0, 3, -4, 0, null], "parent_edge": [-1, 0, 2, 3, -1]}

Route to 3 is 0->1->2->3 with cost 0. Venue 4 is unreachable. Negative DAG weights are valid.
