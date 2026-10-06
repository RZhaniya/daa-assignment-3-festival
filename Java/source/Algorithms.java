import java.util.*;
/** Implement these algorithms. CONTRACTS.md defines each field and limit. */
public final class Algorithms {
    /** Implement find and union, path compression and union by size or rank. Initially n singleton components; largest=0 for n=0. Return a state after every operation. A union within one component does not change the count or size. */
    static Map<String,Object> dsu(Map<String,Object> input) { throw new UnsupportedOperationException("TODO dsu"); }
    /** Implement Kruskal using DSU. Return a minimum spanning forest, including all connected components and isolated vertices; components=0 for n=0. cost is the total forest cost. Return input edge IDs, with no repetitions. Self-loops are excluded. Any optimal forest is accepted. Negative weights represent discounts. */
    static Map<String,Object> kruskal(Map<String,Object> input) { throw new UnsupportedOperationException("TODO kruskal"); }
    /** Implement Prim with adjacency lists and a priority queue. Restart at every unvisited component. Use the same forest contract as Kruskal. Costs and feasibility must agree, but selected edge IDs may differ. Compare algorithms on three input sizes in ANALYSIS.md. */
    static Map<String,Object> prim(Map<String,Object> input) { throw new UnsupportedOperationException("TODO prim"); }
    /** Implement Kosaraju with two DFS passes and a transposed graph. Labels are nonnegative integers; their values and order are unrestricted. Equal labels must mean exactly one SCC. Condensation contains each cross-component pair once and no internal edges. Handle isolated vertices and deep graphs. Condensation is a DAG, but internal dependency cycles still need a real scheduling decision. */
    static Map<String,Object> scc(Map<String,Object> input) { throw new UnsupportedOperationException("TODO scc"); }
    /** Implement DFS with unvisited/active/finished states and reverse postorder. A back edge to an active vertex indicates a cycle. For a cyclic graph return acyclic=false, order=[]. For a DAG list every vertex exactly once, respecting every edge. Any valid order is accepted. The empty graph has acyclic=true, order=[]. */
    static Map<String,Object> topo(Map<String,Object> input) { throw new UnsupportedOperationException("TODO topo"); }
    /** Check acyclicity, then relax edges in topological order. On ANY directed cycle, including an unreachable cycle, return acyclic=false and both arrays empty. Otherwise source has distance=0 and parent_edge=-1. Unreachable vertices have distance=null and parent_edge=-1. Every other parent edge ends at that vertex and reconstructs an optimal source path. Provide a route reconstruction helper and show it in an example. Never relax from infinity. Multiple shortest routes are accepted; MST is unrelated to this objective. */
    static Map<String,Object> dag(Map<String,Object> input) { throw new UnsupportedOperationException("TODO dag"); }
}
