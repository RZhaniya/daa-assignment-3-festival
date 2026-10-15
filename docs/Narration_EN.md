# Assignment 3 — English narration transcript

English synthetic narration: Microsoft Zira Desktop. The slides and video explain learning examples. Follow the A3 specification and contracts for the assessed interface.

## 1. Graph algorithms for a campus festival (00:00)

Welcome to Assignment 3. We will use graph algorithms to connect festival venues, understand preparation dependencies and plan delivery routes. This lesson explains the reasoning and the implementation steps. Keep the assignment contracts open as you work. The examples here are learning examples. You will complete the six methods in your own selected language and test your own implementation.

## 2. What you will build (00:29)

There are six required methods. Kruskal and Prim solve the same optimization problem but must be separate implementations. Kosaraju is the required strongly connected components method. Topological ordering must use depth first search. Shortest paths use topological relaxation in a directed acyclic graph, and your helper reconstructs a route from parent edge IDs. Do not add unrelated algorithms to the assignment.

## 3. Vertices, edges and original IDs (01:02)

A vertex number identifies a venue or task. An edge ID identifies the original input record. Do not replace an edge ID by its position after sorting. Two parallel edges can join the same endpoints and still have different IDs. For undirected algorithms both endpoints can be traversed. For directed algorithms the direction is essential. An isolated vertex still belongs to the graph.

## 4. One request, one JSON result (01:33)

Use the supplied launcher and input output framework. Select a language using the exact manifest value. Java is the syllabus primary language, and the existing assignment choice of four languages remains available. Avoid debug messages on standard output because they corrupt the JSON lines protocol. Multiple requests can run in one process, so initialize algorithm state separately for every request.

## 5. DSU: maintain a partition (02:04)

A disjoint set union structure maintains a partition into components. Initially each vertex is a singleton. Find gives a representative of the component. Union must first find both representatives. If they are equal, no component is merged. The output still needs a state after that operation. With zero vertices and no operations, the output states list is empty.

## 6. Path compression: preserve the next link (02:34)

Path compression changes the parent structure without changing component membership. In the example, vertex three is the root. Once the root has been found, every vertex on the path can point directly to it. The important implementation detail is saving the previous parent before overwriting it. Otherwise you can lose the next vertex and compress only the first link.

## 7. Union by size and component statistics (03:02)

Use path compression together with a union heuristic. This fragment uses union by size. If you choose rank instead, rank is a tree height estimate, not a component size; keep size separately for the statistics. Repeated and self unions must not reduce the component count. Initialization costs linear time, while a sequence of operations has the inverse Ackermann amortized bound.

## 8. Minimum spanning forest (03:32)

A minimum spanning tree exists only when the graph is connected. Assignment 3 requires a minimum spanning forest, covering every connected component. Here vertex four is isolated, so the graph has two components. The forest has five minus two, or three edges. Its cost is seven. A self loop cannot help connect different vertices. Negative edge weights are allowed.

## 9. Kruskal: sort, then use DSU (04:02)

Kruskal processes edges in nondecreasing weight order. In this example it accepts edge one, then edge three, then edge zero. These connect vertices one, two, three and zero at cost one plus two plus four, which is seven. The later edges join vertices already connected and are rejected. Vertex four remains an isolated component. The comparison is not equal, as clarified for lecture six slide nine.

## 10. Why a light crossing edge is safe (04:35)

The cut property explains why the greedy choice is safe. Consider a cut that does not split any already chosen tree. An edge of minimum weight crossing that cut can be added while preserving the possibility of an optimal forest. The exchange argument replaces a crossing edge in an optimal solution without increasing cost. This does not mean the minimum edge or the entire optimum must be unique.

## 11. Prim: grow a forest through a frontier (05:03)

Prim uses a frontier rather than sorting the entire input list. Starting from zero, it first reaches one with weight four, then two with weight one, then three with weight two. The total is again seven, although the order differs from Kruskal. Once this heap is exhausted, the outer loop still visits isolated vertex four and counts its component.

## 12. Prim: stale entries and parallel edges (05:29)

A lazy heap implementation does not remove every obsolete candidate immediately. When a candidate reaches the top, check whether its target has already been visited. If so, skip it before adding cost or recording an edge. Parallel edges are distinct candidates, each carrying its own original ID. The initial root is not an input edge and must not be recorded as one.

## 13. Kruskal and Prim: compare valid results (05:57)

A correct validator must accept another feasible optimum. Checking only the expected edge list can reject a correct solution when weights tie. On the other hand, the correct minimum cost alone is not enough: a response might repeat an ID, include a cycle or omit a component. Validate the actual forest witness. Then compare Kruskal and Prim timing on exactly the same graph instances.

## 14. Strongly connected components (06:27)

Strong connectivity is a directed concept. In this teaching example, zero and one form one component, two and three form another, and four is isolated. Components can be named differently while describing the same partition. The condensation graph contains one edge between the first two components. Duplicate original edges must not create duplicate condensation edges, and edges inside a component disappear.

## 15. Kosaraju: finish, transpose, assign (06:59)

Kosaraju needs two complete depth first search passes and a transposed graph. Finishing order means the order in which vertices are completed, not discovered. Reverse this list before choosing the roots of the second pass. The second pass must traverse the transpose. For a simple chain zero to one to two and the stated traversal order, finishing gives two, one, zero, so the reversed root order begins at zero.

## 16. Kosaraju: exact output and reset (07:30)

Both DFS passes must include vertices in disconnected components. A new request must not reuse labels or visited values from a previous graph. Once labels are assigned, scan the original edges and retain only pairs whose labels differ. Use a set or an equivalent deduplication method. The full algorithm takes linear time in vertices and edges; the transpose and traversal arrays also fit in linear total memory.

## 17. DFS topological ordering (08:02)

A topological order places every prerequisite before its dependent task. DFS needs three states. A gray target is on the active ancestor path, so this edge closes a directed cycle. A black target has already finished and does not by itself prove a cycle. Search every component. If no cycle exists, reverse the finishing order and include every vertex exactly once.

## 18. Iterative DFS: retain the neighbour index (08:32)

An iterative depth first search must preserve where each suspended search will continue. A frame contains the current vertex and the next outgoing edge index. Advance that index before pushing a child. When no neighbours remain, color the vertex black, append it to the finishing list and pop the frame. Restart at every white root. This avoids recursion stack failure on deep chains without rescanning adjacency lists.

## 19. DAG shortest paths: a numeric trace (09:04)

Use the directed edges shown here, with source zero. After processing zero, distances to one and two are three and eight. Processing one improves distance to two to minus two. Processing two sets the distance to three to two. Vertex four stays unreachable. A negative distance such as minus two is valid; that is why minus one cannot represent unreachable distance in this assignment.

## 20. Why one topological pass is sufficient (09:33)

Topological order is the reason one relaxation pass is enough, even with negative weights. When a vertex is processed, every possible predecessor has already been processed, so no later edge can improve its distance. Do not relax from an unreachable vertex. A disconnected cycle still makes the whole input invalid as a DAG. Return the specified empty arrays when a cycle is detected.

## 21. Route reconstruction from parent_edge (10:03)

For the preceding DAG, parent edges for vertices one, two and three are zero, two and three. Starting at target three, follow edge three to vertex two, edge two to one, and edge zero to source zero. Reverse the collected vertices to obtain the route zero, one, two, three. The route to the source is the one-element list containing the source. An unreachable target has no route.

## 22. Ties, original IDs and 64-bit arithmetic (10:34)

A valid optimum does not always have a unique witness. A shortest path validator must accept another tied route when its parent edges are real and the distances are correct. With parallel edges, recording just the predecessor vertex loses the identity required by the contract. In Java, assign a long expression, not an already overflowed int expression. Cast an operand before multiplication or addition when necessary.

## 23. Time and space, including the result (11:05)

State the bound for the representation you actually implement. DSU initialization is linear and the operations have an inverse Ackermann amortized term; storing every returned state adds space proportional to the operation count. Sorting and lazy heaps determine the minimum forest bounds. Kosaraju, DFS topological ordering and DAG relaxation are linear with adjacency lists. Reconstruction is linear in the returned route length.

## 24. Test the witness, not only the number (11:38)

A plausible number can accompany an invalid witness. For a forest, repeated IDs are invalid even if the listed cost equals the optimum. SCC labels may be renamed, but merging distinct components is wrong. A topological list with a duplicated vertex is not valid. For DAG paths, check edge endpoints, distance equality and that reachable parent chains terminate at the source. Include a good alternative optimum and a bad witness in your own tests.

## 25. Own tests and independent requests (12:13)

Your tests should explain why the expected result is right. Include empty or disconnected graphs where allowed, repeated operations, isolates, loops, parallel edges, negative weights and unreachable vertices. Then issue a different request in the same process. A large DSU request followed by a small DAG request must not leak arrays or counters. Large input checks complement small independent examples; they do not replace them.

## 26. Kruskal–Prim benchmark: illustrative data (12:47)

For each of three sizes, use exactly the same input graph for both algorithms. Record vertices, edges, random seed or generator, runtime and hardware. Exclude compilation from timing and say whether graph construction, parsing and output are included. Use at least three measurements for each method. These illustrative samples have medians ten and seven at the first size. They illustrate observations, not proof of an asymptotic bound.

## 27. What belongs in ANALYSIS.md (13:21)

Write the analysis in English. For every required method, explain what is maintained, why the steps are correct, and how the chosen representation determines time and memory. Include your actual route helper calls and benchmark evidence, not a list of intended tests. Follow the syllabus on assistance disclosure: identify tools or sources, their contribution and your own verification. You remain responsible for the submitted work.

## 28. Implement and run the selected language (13:55)

Start from the official template and select one language. The input output framework is already present. Implement the methods, add tests, and push a commit. Inspect the Public checks workflow for that commit. Successful compilation with algorithm failures is normal while TODO methods remain. Once implementation is complete, the run used for submission must be successful. Do not replace the required algorithms with a library implementation.

## 29. Submit two links for the same SHA (14:29)

The submitted commit identifies the version to assess. A link to a changing main branch is not an exact version. The successful Actions run must use that same commit SHA. Paste both links into Moodle Online text, then finish the Submit assignment step. If your repository is private, the instructor needs access. Follow the actual dates and approved extensions in Moodle. No ZIP upload or student ID is required.

## 30. Assessment: 50 automatic + 50 automatic (15:02)

The current assessment is entirely automatic. Practical work contributes fifty points from the original test group weights. The quiz contributes another fifty. There are no manual points, oral defence, oral bonuses or oral score recovery. Required learning materials still belong in the work. Confirmed defects in a test or question are corrected consistently for everyone affected, using original evidence rather than an individual oral task.

## 31. Ready to submit (15:35)

Before submitting, trace one small example by hand for every algorithm. Test an invalid witness and an alternative correct result. Check a deep graph and a sequence of different requests. Confirm your helper was actually called. Read the contracts again for field names and sentinels. Finally check the analysis, your measurements, the exact commit and the successful workflow run. This is a reliable route from understanding the method to submitting your own implementation.

## 32. Sources and further study (16:10)

The assignment specification is the authority for the JSON contracts and the required methods. Read the separate clarification of the Kruskal condition in lecture six and the unreachable sentinel used in lecture seven: Assignment 3 uses null. The textbooks support the invariants, proofs and complexity analysis. Use them for understanding and acknowledge assistance appropriately. The next slide contains the narrated walkthrough for revision.

