# Assignment 3: English video transcript

Original lesson with English synthetic neural narration. Examples follow the public A3 contracts.

## 00:00 Graph algorithms for a campus festival

Welcome. In this lesson, we will turn the six required methods in Assignment Three into concrete actions you can follow. Imagine a campus festival. Some venues need cable connections. Some jobs depend on other jobs. Some deliveries have a cost. These stories produce different graph problems, so we must first decide what answer we need. We will use small examples, follow the changing state, and connect the ideas to short Java fragments. The complete assignment remains your implementation. Keep the contracts beside you, and pause whenever you want to predict the next step.

## 00:36 Vertices, edges and original IDs

A vertex represents an object, such as a venue. An edge connects two vertices. An undirected cable works both ways. A directed dependency has an arrow, and that direction matters. A weight is a number attached to an edge, such as its cost. In Assignment Three, vertices start at zero. An edge identifier is its position in the input array, also starting at zero. Sorting the array must preserve that original identifier. Two parallel edges may connect the same vertices and still have different identifiers. We will need those identifiers in both spanning forests and route reconstruction.

## 01:14 The question determines the method

Here is our route through the lesson. Disjoint set union tracks groups as undirected connections arrive. Kruskal and Prim build a minimum cost spanning forest using two different strategies. Kosaraju finds groups in a directed graph where every member can reach every other member. A depth first topological ordering arranges dependency jobs when no directed cycle exists. Finally, shortest paths in a directed acyclic graph compute costs from one source. A minimum spanning forest and a shortest path tree answer different questions. A cheap total cable network does not necessarily give the cheapest route from a particular source.

## 01:52 Every vertex begins in its own group

Start with five vertices and no connections. There are five groups, each containing one vertex. The parent array represents each group as a rooted tree. A root points to itself. The find operation follows parent links until it reaches that root, which acts as the group's representative. The representative is an internal choice, not an extra venue or a required smallest label. Initially, every vertex represents itself. Set the number of components to five and the largest group size to one. For an empty graph, both quantities are zero. Initialization takes linear time because every vertex needs its own state.

## 02:31 A successful union reduces the group count

Now process the connection between zero and one. Find returns zero for the first vertex and one for the second. The roots differ, so two groups really will merge. With union by size, attach the root of the smaller group to the root of the larger group. Equal sizes can use either consistent choice. Here, one becomes a child of zero. Add the sizes at the surviving root. The merged size is two, the component count decreases to four, and largest becomes two. Record the pair four, two after this operation. The output records a state after every input operation.

## 03:08 Sizes combine at the roots

Next, join two and three. That creates a second group of size two, leaving three components. Largest stays two. Then join one and three. These arguments are not both roots, so find must first reach their representatives, zero and two. Joining those roots creates a size four group containing zero, one, two, and three. Vertex four remains alone. The component count becomes two and largest becomes four. Only root sizes are authoritative. Updating size at the original argument instead of the surviving root would corrupt later unions, because an argument can already be an internal tree vertex.

## 03:47 Repeated and self unions leave the groups unchanged

Consider a self connection between four and four. Both finds return the same root. There is nothing to merge, so components and largest stay unchanged. The same rule applies to zero and two now. Although they are different vertices, their roots already match. Decreasing the component count again would count an imaginary merge. Record a state for each operation even when it changes nothing. Our complete sequence therefore ends with two more copies of two, four. This distinction matters: one output state per operation does not mean one successful union per operation. Always check representatives before changing group statistics.

## 04:26 Path compression shortens the parent chain

Our current parent tree contains the chain three, two, zero. Finding three walks along that chain and discovers root zero. Path compression then redirects visited vertices toward the root. Three can point directly to zero on the next search. The group has not changed, so neither components nor largest changes. If you implement a second iterative pass, save the old next parent before overwriting it. Otherwise you can lose the remainder of the chain. Compression changes the internal representation to make future searches cheaper. It does not mean that the graph acquired a new input edge between three and zero.

## 05:03 Balancing and compression work together

Path compression and balancing serve different purposes. Balancing prevents avoidably tall trees when groups merge. Compression shortens paths during later searches. Assignment Three requires both compression and either union by size or union by rank. If you use rank, maintain actual component sizes separately because rank does not tell you how many vertices a group contains. The fragment shows a safe second compression pass after the root has been found. Notice that next is saved first. Across a sequence of operations, the amortized find and union cost is inverse Ackermann, which grows extremely slowly. It is not a claim that every individual call takes constant time.

## 05:44 DSU result and complexity

Let us close the connectivity part. The answer is a sequence of component counts and largest group sizes, not the parent array. The parent array is internal state, and different valid implementations may use different roots. Initialization costs order n. For q operations with both heuristics, total time is order n plus q times inverse Ackermann of n, amortized. The arrays use order n memory, and storing the requested output adds order q. Test an empty graph with no operations, repeated unions, self unions, and a new request of a different size. Every request must start with fresh state.

## 06:22 The cheapest forest includes isolated vertices

For the next two algorithms, use the same undirected weighted graph. The edge from zero to one costs four and has identifier zero. One to two costs one, identifier one. Zero to two costs six, identifier two. Two to three costs two, identifier three. One to three costs five, identifier four. Vertex four is isolated. We cannot invent a cable to connect it. Therefore the answer is a minimum spanning forest with two components. The connected four vertex part needs three chosen edges. In general, a graph with n vertices and c components needs n minus c forest edges.

## 07:02 Kruskal examines edges in weight order

Kruskal sorts all original edges by increasing weight. On this example, the identifier order is one, three, zero, four, two. Notice that sorted position zero belongs to original edge one. We keep the original identifier. Start a fresh disjoint set structure. For each edge, find its endpoint representatives. Different representatives mean the edge joins two current trees, so accept it and unite those groups. Equal representatives mean it would create a cycle, so skip it. This comparison must be different, not equal. A self loop always has equal representatives and cannot help a spanning forest, even if its weight is negative.

## 07:43 The first two accepted edges form a path

Take edge one, connecting one and two with cost one. The endpoints belong to different groups, so the edge is safe to add. Next take edge three, connecting two and three with cost two. Vertex two already belongs to the group containing one, but vertex three does not. This edge joins those groups and brings the running cost to three. We now have a path from one through two to three. A useful invariant is that the accepted edges always form a forest. The disjoint set structure represents exactly its connected components, which makes the cycle test efficient.

## 08:17 A different representative permits the next edge

Edge zero costs four and connects zero to one. Vertex zero is still outside the current tree, so accept the edge and increase total cost to seven. The four connected venues are now linked with three edges. The remaining candidates, identifiers four and two, connect vertices already in this same group. Both would create cycles, so skip them. Vertex four remains its own component. One accepted result is cost seven, edge identifiers one, three, zero, and components two. The order of returned identifiers is unrestricted. A validator must check the forest's meaning rather than require this particular list ordering.

## 08:56 Why a cheapest crossing edge is safe

Why does choosing a cheap eligible edge preserve an optimal answer? Imagine a cut separating a current component from the other vertices. A lightest edge crossing that cut is safe. If an optimum forest lacks it, add it to that optimum. Within the relevant connected component, this creates a cycle containing another crossing edge. Replacing that edge cannot increase the cost. This exchange argument supports the greedy choice. Ties can therefore yield different correct forests. Negative weights are valid too. Sorting dominates Kruskal's time, and the extra n term handles isolated vertices. Use a signed sixty four bit total, because many legal weights can sum beyond thirty two bits.

## 09:38 Prim grows one tree from a starting vertex

Prim solves the same forest problem through a different state. Instead of sorting all edges globally, it grows a tree from a visited vertex. Start at zero. Offer its outgoing undirected adjacency entries to a priority queue. The candidates are edge zero with weight four and edge two with weight six. Remove the cheapest eligible candidate. Edge zero reaches unvisited vertex one, so add it and mark one visited. The growing tree now contains zero and one. The heap should preserve the original edge identifier alongside the candidate destination and weight. This is a genuinely separate implementation from Kruskal, using adjacency lists and a heap.

## 10:19 New vertices expose new candidate edges

When vertex one enters the tree, offer its edges toward unvisited vertices. New candidates include one to two with cost one and one to three with cost five. The earlier zero to two candidate with cost six stays in the queue. Choose the cost one edge and visit two. Then two exposes its cost two edge to three, which becomes the next cheapest eligible choice. The accepted costs are four, one, and two, totaling seven. The sequence differs from Kruskal's sequence, but it reaches the same optimum cost. The cheapest edge changes as the boundary of the growing tree changes.

## 10:55 Stale heap entries and disconnected components

A lazy priority queue can still contain old entries for vertices that have already entered the tree. For example, the cost six edge toward two becomes stale after the cheaper route adds two. When that entry is removed, skip it. Never charge it again. Once the heap empties, the current connected component is complete. Scan for another unvisited vertex and start a new component. Here that vertex is four. It contributes a component and no edge. If you start only once, your implementation can accidentally ignore disconnected pieces. The final forest must cover every vertex, including all isolated vertices.

## 11:34 Kruskal and Prim share an objective

Kruskal and Prim should agree on minimum cost and feasibility, although ties can produce different edge identifiers. They must remain different algorithms in your submission. For a lazy heap, Prim takes order n plus m log of m plus one time, and order n plus m memory. An indexed heap can give the alternative bound shown in the contracts. Verify the result structurally: no repeated chosen identifier, no loop, no cycle, the correct number of components, and exactly n minus c selected edges. Matching the cost alone is insufficient, because an invalid set of edges can coincidentally have the same sum.

## 12:10 Mutual reachability creates a component

Now switch to a directed graph. Zero points to one, and one points back to zero. Both zero and one also lead toward two. Two and three point to each other, and three points to four. Vertex five is isolated. A strongly connected component is a maximal group where every vertex can reach every other vertex along directed paths. Zero and one form one group. Two and three form another. Four and five each form a singleton group. Having a path from the first group into the second does not merge them, because there is no path back.

## 12:45 First DFS records vertices when they finish

Kosaraju uses two depth first passes and a transposed graph. In this orientation, run the first pass on the original graph. We choose vertices and adjacency entries in the displayed order. Starting at zero, the search follows one, then two, then three, then four. Four has no outgoing work, so it finishes first. Returning through the search finishes three, two, one, and zero. Append each vertex at finish time, not at discovery time. Finally visit isolated five. Its search immediately finishes. A different adjacency order may change this list, but the algorithm's rule is always to record completed work.

## 13:25 The second pass uses decreasing finish order

The recorded finish list is four, three, two, one, zero, five. Reverse it to obtain decreasing finish order: five, zero, one, two, three, four. Build the transpose by reversing every original directed edge. Clear the visited state before the second pass. Then take seeds from this reversed finish list. A new second pass search collects one complete strongly connected component. Reusing the original edge direction for both passes can incorrectly cross between components. Using discovery order instead of finish order also breaks the reasoning. You may swap which graph is used first, provided the second pass uses its transpose with the corresponding finish order.

## 14:08 Each new second-pass search finds one SCC

Follow the second pass carefully. Seed five in the transpose finds only five. Seed zero reaches one and stays within their component, so assign both the same label. Seed one is already visited and starts no new search. Seed two collects two and three. Seed three is already visited. Seed four forms its own singleton. The order is designed to prevent a new search from spilling into a component that should be separate. One implementation may assign labels in discovery order. Another may use different nonnegative integers. The labels are names. Their equality pattern, meaning which vertices share a label, is what matters.

## 14:48 Condensation keeps each cross-component pair once

Use labels seven, seven, four, four, nine, two for vertices zero through five. Inspect every original edge again. An edge whose endpoints share a label stays inside one component and must not appear in the condensation. Edges from zero to two and from one to two both become the same pair, seven to four. Include that pair only once. The edge from three to four becomes four to nine. Isolated label two remains represented by the component array even though it contributes no condensation edge. The condensation is acyclic. It summarizes component dependencies without pretending that the original internal cycles have disappeared.

## 15:27 Kosaraju state, complexity and depth

The key ingredients are two passes, a transpose, and decreasing finish order for the second pass. Building adjacency lists, traversing each graph, and examining original edges all take linear work. With a hash set for condensation pairs, the expected total time is order n plus m. Memory is also order n plus m, including the returned arrays and condensation. Deep chains can exceed a language's recursion stack, so an explicit stack is a robust choice at the published limits. A DFS frame needs enough information to resume the next neighbor and recognize when the vertex has actually finished. A plain stack of vertices alone does not automatically reproduce recursive finish order.

## 16:08 An edge places its source before its target

A topological order lists every vertex exactly once so that every directed edge goes from an earlier position to a later one. In our scheduling example, jobs zero and one both precede job two, and job two precedes job three. Zero and one can exchange positions because neither depends on the other. That means more than one answer can be valid. The task requires a depth first method, so we will use reverse postorder with cycle detection. A vertex without incident edges still belongs in the output. The empty graph also has a valid topological order, which is simply an empty list.

## 16:44 Three states distinguish active and completed work

Give each vertex one of three states. White means the search has never entered it. Gray means it is active on the current depth first path. Black means all its outgoing work has finished. These distinctions are essential. An edge to a white vertex starts more work. An edge to a gray vertex points back into the active path and proves a directed cycle. An edge to a black vertex merely reaches completed work and does not by itself prove a cycle. A single visited Boolean loses the difference between gray and black. Our example will show exactly why that difference matters.

## 17:19 Reverse the finish list to obtain an order

Start depth first search at zero. Visit two and then three. Three finishes first, then two, then zero. The finish list is now three, two, zero. Vertex one is still white, so start another search there. Its edge reaches two, which is already black, so there is no recursive work left. Finish one and append it. Reversing the complete finish list gives one, zero, two, three. Check each dependency: both initial jobs appear before two, and two appears before three. Recording vertices when they are first entered would produce preorder, which is not the required reverse postorder construction.

## 17:58 A BLACK neighbor is not a back edge

Let us isolate the common false alarm. When the search finally starts from one, its neighbor two has already finished in the earlier search. The graph still has a perfectly valid dependency order. Reporting a cycle whenever a neighbor is merely visited would incorrectly reject it. Only a gray neighbor lies on the active path that can close a directed loop. In iterative code, mark a vertex black only when all its neighbors have been examined. If you mark it finished as soon as you push it, cycle detection and finish order can both become incorrect. The state must reflect the actual stage of the search.

## 18:33 A cycle invalidates the complete order

Now add a dependency from three back to zero. During the search path zero, two, three, vertex zero is still gray. The edge from three to zero returns to that active ancestor and closes a directed cycle. No ordering can place zero before two, two before three, and three before zero simultaneously. Return acyclic false and an empty order. Search every component, because a cycle in a disconnected component still invalidates the graph. A self loop is also a cycle. Do not return a partial finish list as though it were a usable schedule after detecting a cycle anywhere in the graph.

## 19:10 A frame remembers where DFS should resume

For a deep graph, an explicit frame can reproduce recursive depth first behavior. Each frame stores a vertex and the index of the next neighbor to examine. If a neighbor remains, advance that index before descending. If no neighbor remains, mark the vertex black, append it to the finish list, and pop the frame. After every component has been checked successfully, reverse the list. Every vertex enters and exits once, and every edge is examined once, giving order n plus m time. The adjacency lists, color array, frame stack, and output fit in order n plus m memory. This also avoids relying on a small native call stack.

## 19:50 Shortest routes start from one source

The final algorithm asks a new question: what is the cheapest directed route from a chosen source to each vertex? Use source zero. The input edges are zero to one with cost three, zero to two with cost eight, one to two with cost minus seven, and two to three with cost four. Their original identifiers are zero, one, two, and three respectively. Vertex four has no route from zero. This graph is acyclic, even though one edge has a negative weight. The negative edge is valid. Acyclicity, rather than nonnegative weights, is what makes topological relaxation sufficient here.

## 20:26 Acyclicity is a property of the whole graph

Before computing the distances, check the entire directed graph for cycles. A search only from the source is insufficient for the contract. Imagine an additional disconnected pair of vertices with arrows in both directions. The source cannot reach that pair, but the input graph still is not a directed acyclic graph. Return acyclic false, an empty distance array, and an empty parent edge array. The same applies to a self loop. This requirement is stronger than asking whether the source's reachable region happens to be acyclic. Reuse a correctly implemented whole graph depth first ordering rather than quietly ignoring unreachable components.

## 21:05 Only the source has a known initial distance

After acyclicity succeeds, initialize the source distance to zero and all other distances to null. Initialize every parent edge to minus one. A null distance means we do not yet have a source route, so no relaxation may start from that vertex. The integer minus one cannot serve as the unreachable distance because negative edges can produce a legitimate shortest distance of minus one. Notice that the distance sentinel and parent sentinel have different roles. Distances use null for unreachable. Parent edges use minus one for the source and for unreachable vertices. Use sufficiently wide integer arithmetic for valid finite sums.

## 21:43 Relaxing an edge offers a cheaper route

Take a valid topological order such as zero, one, two, three, four. Process zero first. Edge zero offers cost three to vertex one, so record distance three and parent edge zero. Edge one offers cost eight to vertex two, so record eight and parent edge one. Now process vertex one. Its distance is already final at three. Following the negative edge to two gives three plus minus seven, which equals minus four. That improves the earlier cost eight. Replace both the distance and the parent edge. The new parent is original edge identifier two, not predecessor vertex one and not a sorted position.

## 22:24 Topological order makes one pass sufficient

Next process two. Its outgoing edge reaches three with candidate cost minus four plus four, which equals zero. Record distance zero and parent edge three. Vertex four remains unreachable. Why is one pass enough? Every predecessor of a vertex appears earlier in a topological order. By the time that vertex is processed, all possible incoming route improvements have already been considered. Its final distance can then safely extend to its successors. This reasoning also handles negative weights, because no later vertex can lead back to an earlier one. With adjacency lists, cycle checking and all relaxations together take order n plus m time and memory.

## 23:06 A parent edge reconstructs the selected route

To reconstruct a route to three, read its parent edge identifier, which is three. In the input, edge three runs from two to three. Move to two. Its parent edge is two, running from one to two. Move to one. Its parent edge is zero, running from zero to one. We have reached the source. The collected vertices run backward as three, two, one, zero, so reverse them to obtain zero, one, two, three. Check the direction and sum the chosen input edges: three plus minus seven plus four equals zero. The helper takes time and output space proportional to the returned route length.

## 23:44 Ties, parallel edges and helper tests

A shortest route need not be unique. In a modified example, add another zero to one edge of cost three. Either of those original identifiers can correctly parent vertex one. Add a direct zero to three edge of cost zero, and the direct route ties with our longer route. A semantic checker should accept either valid optimum. Preserve the actual edge identifier so parallel choices remain unambiguous. Your helper must also return the single vertex source route when target equals source. For unreachable four, use and document a no route result. Actually call all three helper cases in your own tests and record their results in the English analysis.

## 24:24 Small examples need independent checks

Before timing anything, test correctness. Predict small answers independently, rather than copying the implementation's output into an expected file. For a forest, check its original identifiers, connectivity, edge count, and total cost. For strongly connected components, check the partition and the exact deduplicated condensation. For a topological order, build vertex positions and verify every dependency. For shortest paths, validate parent directions and route costs as well as distances. Include tied optima, loops where allowed, empty graphs where the contract allows them, and deep chains. Then send several different requests through one running process to expose state that accidentally leaks between tasks.

## 25:08 The benchmark compares the same work

For the required Kruskal versus Prim benchmark, use the same graph instances and environment for both methods. Verify correct costs and valid forests before comparing speed. Choose three sizes and report their vertex and edge counts, plus a reproducible generator seed or saved inputs. At each size, measure each method at least three times. The illustrative Kruskal values twelve, ten, eleven have median eleven. The Prim values nine, thirteen, ten have median ten. These are teaching numbers, not measurements of your program. Exclude compilation, document warm up, and use the same timing boundary for both methods. A few observed timings do not prove an asymptotic bound.

## 25:51 The repository contains your implementation and evidence

Use the public repository as a template, then choose Java, Python, C plus plus, or C sharp in submission dot JSON. Java is the primary syllabus language, and the assignment keeps its existing four language choice. Implement all six methods in the selected source folder. The supplied adapters already handle the request and result format. Keep standard output limited to one JSON result per input line, with diagnostics on standard error when needed. Add your own tests under tests slash student. Complete the English analysis with algorithm reasoning, complexity, helper calls, benchmark evidence, and written acknowledgement of outside and AI assistance. You remain responsible for the work you submit.

## 26:34 One assessed commit, two Moodle links

Push your implementation and obtain a successful Public checks run for the commit you intend to submit. In Moodle Online text, provide two links: that commit's URL and the successful Public checks run URL for the same commit identifier. Then click Submit assignment. A private repository must remain accessible to the instructor. Use the dates and approved extensions already shown in Moodle. The starter contains intentional unfinished methods, so successful setup followed by failed algorithm cases is expected until you implement them. The practical grade is fifty automatic points, and the quiz is fifty automatic points. The published rubric explains the groups. This lesson does not change those rules.

## 27:16 Six methods, six explainable states

You now have a worked example for every required method. When writing your solution, ask what the state means before writing the next loop. In disjoint set union, it represents groups. In Kruskal, it certifies that accepted edges stay acyclic. In Prim, the heap describes candidate edges leaving a growing tree. In Kosaraju, finish order coordinates the two passes. In topological sorting, gray distinguishes active work from completed work. In directed acyclic shortest paths, the ordering makes every processed distance final. Pause, repeat one trace by hand, and then implement that idea in your own language. Explain each change, verify the result, and keep the exact contracts beside you.

