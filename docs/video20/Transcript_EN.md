# Graph Algorithms: From Model to Implementation

English narration · 20:00 · Java 17 learning fragments



## 00:00 Choose the question

A campus needs cables, a preparation schedule, and a delivery route. These are three different questions. Before choosing an algorithm, decide what the answer must describe: an entire network, an order, or one path.


Vertices are numbered from zero. A line connects two vertices; an arrow permits only its direction. A weight gives the cost. Here, original edge identifier one means the second input row, even after sorting.


An adjacency list gives each vertex its outgoing connections. An undirected edge needs entries at both ends, with the same original identifier. This representation lets us visit neighbours without scanning the entire input each time.


## 01:17 Disjoint-set union

Disjoint-set union tracks groups as connections arrive. Each vertex initially represents itself. Parent points to the same vertex, size is one, and there are five components. Only a root stores the authoritative size of its group.


Add the link zero to one. Their roots differ, so two groups merge. Attach the smaller tree to the larger one; on equal sizes, either consistent choice works. Here, root one joins root zero. The number of components drops to four; the largest group now has two vertices.


Next join two and three. This creates a second group of size two. Now join one and three. Find first follows each argument to its root. Roots zero and two have size two, so merge them. Four vertices share a group; vertex four remains separate.


Now add zero to two, then four to itself. Both operations have equal endpoint roots. Do not decrease components or add sizes again. Record a state after every operation, including unchanged states. The last two answers are both two components, largest four.


Finding vertex three follows three, two, zero. Zero is the root. Path compression can redirect three straight to zero. The group still contains the same four vertices. This parent link is internal bookkeeping, not a new cable in the input graph.


Return to the state before compression. Try one compression step yourself. The root is already known. Save the current parent, redirect this vertex to the root, then continue through the saved link. Pause for up to five minutes. When ready, compare your three lines with this fragment.


Balancing prevents avoidably tall trees, while compression shortens searches. Rank is not group size. With both heuristics, the total time for q operations is order n plus q times inverse Ackermann of n, amortized. Output storage adds order q. Ordinary DSU does not support arbitrary deletions.


## 03:49 Forest or route?

The highlighted forest connects all five buildings for total cost ten. But delivery from zero to four uses zero, two, four for cost eight. These totals optimize different objects. A spanning tree need not preserve every shortest route.


If vertex five is isolated, no legal cable can connect it. It is a separate component. A spanning forest on n vertices and c components contains n minus c edges. A tree also has no redundant links; reliability requirements need a richer network model.


## 04:30 Kruskal

Kruskal considers edges by increasing weight. The original identifiers remain attached to them. Here the examination order is two, one, six, zero, three, five, four. Start a fresh disjoint-set structure to represent the forest being built.


Edge two joins one and two for cost one. Their roots differ, so accept it. Edge one then connects zero to that group for cost two. Every accepted edge joins two trees instead of creating a cycle.


Edge six joins three and four for cost two. We now have two trees: zero, one, two; and three, four. A forest can contain several growing pieces. Its running cost is five, and it is still extendable to an optimum.


Should edge zero now be accepted? Its endpoints already have the path zero, two, one. Adding zero to one would close a cycle. Skip it, although its weight is smaller than the next accepted edge. Eligibility matters as well as price.


Edge three connects the two remaining groups for cost five. Total cost becomes ten. The last two edges would create cycles. The answer has four original edge identifiers and one component. On a disconnected graph, process every component, rather than assuming a single tree exists.


A cut separates a current component from the rest. A lightest crossing edge is safe for some optimum containing the current forest. If an optimum lacks it, an exchange along the resulting cycle replaces an edge of no smaller weight. Equal weights can allow different valid optima.


## 06:31 Prim

Prim solves the same objective with different state. Start at vertex zero. Its boundary offers edge zero at cost four and edge one at cost two. Choose edge one to bring in vertex two. A heap candidate must retain destination, weight, and original edge identifier.


Vertex two exposes an edge to one costing one. It is now the cheapest boundary edge, so take it. The accepted weights are two, then one. Prim is not scanning a globally sorted edge list; its available choices depend on the visited region.


The old offer from zero to one still exists in a lazy queue, but one is already visited. Discard that offer when removed. Checking only at insertion is not enough, because the tree can change while an entry waits in the queue.


The cheapest eligible offer now is edge three, reaching vertex three for five. Then edge six reaches four for two. Running totals are two, three, eight, ten. Kruskal and Prim agree on optimum cost, although their selection sequences differ.


When the queue is empty, scan for another unvisited vertex. An isolated vertex starts a new component and contributes no edge. A lazy heap may contain order m entries, giving order n plus m log of m plus one time, and order n plus m memory.


## 08:20 Depth-first search

Now use directed dependencies. Depth-first search follows one unfinished branch, then returns to work it suspended. A recursive call remembers both the vertex and where to resume its neighbour list. An explicit stack must preserve that progress too.


Unvisited means the search has not entered a vertex. Active means it is on the current depth-first path. Finished means every outgoing edge has been examined. Here the active stack is zero, one, three, four. A visited Boolean alone cannot express these distinctions.


Vertex four has no outgoing work, so finish it and pop its frame. Then three finishes, followed by one. Zero still has another neighbour. Notice that finish records completion, not discovery. This distinction is essential for both Kosaraju and topological ordering.


Enter two. Its edge reaches three, which has already finished. This is not a directed cycle. Continue until two and zero finish. For this adjacency order, the finish list is four, three, one, two, zero. Other traversal orders may produce different valid lists.


At one hundred thousand vertices, relying on a small call stack can fail. Store the next-neighbour index explicitly. Advance it before descending, and finish only when no neighbour remains. Every vertex enters and exits once; each edge is inspected once.


## 10:20 Kosaraju SCC

In this graph, zero, one, and two reach one another. Three and four form another mutually reachable group. Vertex five is isolated. The arrow from two to three does not merge the groups, because there is no return path.


Kosaraju first runs depth-first search on the original graph. Cycles are allowed here; finish the pass rather than rejecting them. Use the displayed neighbour order. Finishing from zero gives four, three, two, one, zero. Then isolated five finishes. Include it even though it was never reachable from zero.


Reverse every directed edge, keeping vertex identities fixed. Clear the visited state before the second pass. Use decreasing first-pass finish order for the next seeds. The second graph must be the transpose of the first, rather than a second traversal in the same direction.


Seed five finds only five. Seed zero then collects zero, one, and two in the transpose. The later seed two is already assigned. Seed three collects three and four. Skip assigned vertices. Colours name the groups; numeric component labels need not match this presentation order.


Think of each strongly connected group as one vertex. Their condensation has no directed cycle. The remaining group with greatest first-pass finish time has no outgoing path to another unassigned group in the transpose. The search stays inside that group and reaches all of it.


Return to the original directions. Internal edges disappear when groups are contracted. The edge from two to three becomes A to B. If we add another original edge from one to three, it produces the same pair, not a second condensation edge. The isolated group still exists.


Mutually dependent modules may need coordinated changes. Condensation exposes dependencies between groups, but does not fix their internal cycles. Two passes, the transpose, and condensation need linear work; hash-based removal of duplicate pairs gives an expected linear time bound.


## 13:00 Topological order

Return to the acyclic task graph. An arrow means its source must happen before its target. Reverse the DFS finish list to obtain zero, two, one, three, four. This order respects every dependency. Independent tasks can sometimes exchange places, so a valid answer need not be unique.


Build each vertex position in the proposed order. For edge one to three, position one is two and position three is three: the inequality holds. Check every edge and verify that every vertex occurs exactly once. This is useful for build dependencies, but does not optimize task duration.


Now add four to zero. During the active path zero, one, three, four, this edge returns to active zero. No complete order can respect that cycle. Return acyclic false and an empty order. A cycle anywhere in the graph, including a disconnected part, makes a complete topological order impossible.


## 14:30 DAG shortest paths

A workpiece can take alternative processing routes. Vertices are stages reached; arrows are allowed next transitions; weights are costs. We seek the cheapest route from source zero. These alternatives are not a list of mandatory tasks that must all be performed.


Only the source cost is known initially. Null means no route has yet been discovered. Process vertices in a topological order and never relax from a null distance. After processing zero, costs to one and two are five and two, with original parent edges zero and one.


Without a discount, processing one first offers seven to vertex three. Processing two improves that to six and offers eleven to four. Then three improves four to nine. The route zero, two, three, four costs two plus four plus three, which is nine.


Now change only edge one to three to minus four, representing a discount, and restart the calculation. Negative weights are valid in a DAG. After zero is processed again, the cost to three is still null. We have not processed its incoming alternatives yet.


At vertex one, the candidate is five plus minus four, giving one. Three has no known cost, so store one and original edge identifier two. The parent is an edge identifier, not vertex one. Updating the number without its witness would leave an inconsistent result.


Vertex two later offers six to three. Six is worse than one, so retain the existing distance and parent. It also offers eleven to four. Vertex three then offers four to vertex four. The final route is zero, one, three, four, with total five minus four plus three, equal to four.


An added isolated vertex five finishes with null distance and parent minus one. A finite distance can itself be minus one. Also reject a directed cycle anywhere in the input, even if source zero cannot reach it. That is a whole-graph requirement of this assignment.


## 17:06 Reconstruct a route

To reconstruct the route to four, its parent edge is identifier four: three to four. Move to three. Parent edge two leads from one to three. Parent edge zero leads from zero to one. Reverse the collected vertices to obtain zero, one, three, four.


Try the backward step yourself. Original edge four is the row three, four, three. Which column gives the previous vertex? Pause if needed. It is column zero: vertex three. Column two gives the last edge weight, not the total route cost.


## 17:49 Test and measure

A correct sum is not enough. Check forest identifiers, connectivity, and edge count. For components, verify mutual reachability and deduplicated condensation. For an order, check every edge position. For routes, verify parent direction and total weight. Alternative valid witnesses should be accepted.


Include empty graphs where allowed, isolates, repeated unions, loops, parallel edges, negative weights, and totals beyond the signed thirty-two-bit range. Test deep chains and mixed requests in one process. Benchmark Kruskal and Prim on the same graphs at three sizes. At each size, run each method at least three times and report its median. Exclude compilation and document warm-up.


Here n counts vertices, m input edges, and q unions. DSU is amortized almost linear. Kruskal and lazy Prim include sorting or heap work. Kosaraju, topological ordering, and DAG relaxation are linear; hash deduplication is expected linear. A reconstructed route takes time proportional to its length.


## 19:15 Your assignment

Use the graphs template. Its adapters handle JSON input and output. Write the six required algorithms and your route helper in your chosen language. Keep original edge identifiers, reset state per request, use wide totals, and send only result JSON to standard output.


Save your tests and English analysis. Public checks report pass or fail; they do not calculate the practical grade or transfer marks to Moodle. Submit the exact commit link. An Actions link may support it. Follow the current README and Moodle for the rubric, dates, and submission details.
