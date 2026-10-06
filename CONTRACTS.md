# Assignment 3 - exact contracts

Version 1.0.0

One JSON object per input line, one JSON result object per line. One task per request. No prompts or debug output on stdout. Independent requests reset all task state. Provided adapters handle input/output. Inputs satisfy the published domain; malformed JSON is not graded.

UTF-8; strings contain printable ASCII characters (32..126), case-sensitive; no Unicode normalization

All vertices, edges, characters and original blocks use zero-based indices. An edge ID is its position in the input edge array. All costs, distances, weights and counts use signed 64-bit integers; null denotes unreachable distance.

## Task 1 - Connectivity / DSU

Connect the waffle tent, quiet stage and emergency tea station, one cable at a time.

Input: {task:dsu,n,operations:[[u,v],...]}

Output: {states:[[components,largest],...]}

Limits: 0 <= n <= 100000; 0 <= operations <= 250000. If n=0, operations must be empty. Self-union and repeated union are valid.

Implement find and union, path compression and union by size or rank. Initially n singleton components; largest=0 for n=0. Return a state after every operation. A union within one component does not change the count or size.

Complexity: O((n+q) alpha(n)) amortized time, O(n+q) memory including output.

Example input:

{"task": "dsu", "n": 4, "operations": [[0, 1], [1, 2], [0, 2], [3, 3]]}

One accepted output:

{"states": [[3, 2], [2, 3], [2, 3], [2, 3]]}

The third cable is redundant. The tea station remains alone; connecting it to itself does not help.

## Task 2 - Budget Network / Kruskal

Build the cheapest undirected cable network; finance would prefer cables to an inspirational speech.

Input: {task:kruskal,n,edges:[[u,v,weight],...]}

Output: {cost,edge_ids:[...],components}

Limits: 0 <= n <= 100000; 0 <= m <= 250000; -1000000000 <= weight <= 1000000000. Parallel edges and self-loops allowed.

Implement Kruskal using DSU. Return a minimum spanning forest, including all connected components and isolated vertices; components=0 for n=0. cost is the total forest cost. Return input edge IDs, with no repetitions. Self-loops are excluded. Any optimal forest is accepted. Negative weights represent discounts.

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

Implement Prim with adjacency lists and a priority queue. Restart at every unvisited component. Use the same forest contract as Kruskal. Costs and feasibility must agree, but selected edge IDs may differ. Compare algorithms on three input sizes in ANALYSIS.md.

Complexity: O(n+m log(m+1)) for a lazy heap; O((n+m) log(n+1)) for an indexed heap; O(n+m) memory.

Example input:

{"task": "prim", "n": 3, "edges": [[0, 1, 1], [1, 2, 1], [0, 2, 1]]}

One accepted output:

{"cost": 2, "edge_ids": [0, 2], "components": 1}

Several answers are optimal. The validator checks the forest rather than matching one reference edge list.

## Task 3 - Circular Dependencies / SCC

Find groups such as 'print tickets before opening the printer room' and 'open the printer room with a printed ticket'.

Input: {task:scc,n,edges:[[u,v],...]}

Output: {component:[label per vertex],condensation:[[from_label,to_label],...]}

Limits: 0 <= n <= 100000; 0 <= m <= 250000. Directed edges; duplicates and self-loops allowed.

Implement Kosaraju with two DFS passes and a transposed graph. Labels are nonnegative integers; their values and order are unrestricted. Equal labels must mean exactly one SCC. Condensation contains each cross-component pair once and no internal edges. Handle isolated vertices and deep graphs. Condensation is a DAG, but internal dependency cycles still need a real scheduling decision.

Complexity: O(n+m) expected time with a hash set for edge deduplication; O(n+m) memory.

Example input:

{"task": "scc", "n": 4, "edges": [[0, 1], [1, 0], [1, 2], [0, 2], [2, 3]]}

One accepted output:

{"component": [5, 5, 7, 9], "condensation": [[5, 7], [7, 9]]}

Vertices 0 and 1 form one SCC. Two original edges become one condensation edge.

## Task 4 - Festival Schedule / Topological Ordering

Schedule preparations before the band arrives to discover that the stage is still a diagram.

Input: {task:topo,n,edges:[[u,v],...]}

Output: {acyclic:boolean,order:[...]}

Limits: Same directed graph domain as SCC. Edge u->v means u must finish before v starts.

Implement DFS with unvisited/active/finished states and reverse postorder. A back edge to an active vertex indicates a cycle. For a cyclic graph return acyclic=false, order=[]. For a DAG list every vertex exactly once, respecting every edge. Any valid order is accepted. The empty graph has acyclic=true, order=[].

Complexity: O(n+m) time and memory, including explicit DFS stack.

Example input:

{"task": "topo", "n": 4, "edges": [[0, 2], [1, 2], [2, 3]]}

One accepted output:

{"acyclic": true, "order": [1, 0, 2, 3]}

Preparations 0 and 1 may be swapped; both must precede 2.

## Task 5 - Delivery Routes / DAG Shortest Paths

Route supplies through a directed acyclic delivery graph. A downhill conveyor can give a negative edge cost.

Input: {task:dag,n,edges:[[u,v,weight],...],source}

Output: {acyclic:boolean,distance:[integer or null per vertex],parent_edge:[edge ID or -1 per vertex]}

Limits: 1 <= n <= 100000; 0 <= m <= 250000; -1000000000 <= weight <= 1000000000; 0 <= source < n. Directed graph; parallel edges and self-loops allowed. Cyclic input must be rejected as below.

Check acyclicity, then relax edges in topological order. On ANY directed cycle, including an unreachable cycle, return acyclic=false and both arrays empty. Otherwise source has distance=0 and parent_edge=-1. Unreachable vertices have distance=null and parent_edge=-1. Every other parent edge ends at that vertex and reconstructs an optimal source path. Provide a route reconstruction helper and show it in an example. Never relax from infinity. Multiple shortest routes are accepted; MST is unrelated to this objective.

Complexity: O(n+m) time and memory. Route reconstruction costs O(route length).

Example input:

{"task": "dag", "n": 5, "source": 0, "edges": [[0, 1, 3], [0, 2, 8], [1, 2, -7], [2, 3, 4]]}

One accepted output:

{"acyclic": true, "distance": [0, 3, -4, 0, null], "parent_edge": [-1, 0, 2, 3, -1]}

Route to 3 is 0->1->2->3 with cost 0. Venue 4 is unreachable. Negative DAG weights are valid.