import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/** Run: javac GraphLesson.java && java GraphLesson. Java 17; no dependencies. */
public final class GraphLesson {
    // region dsu
    static final class DSU {
        final int[] parent, size;
        int components, largest;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            components = n;
            largest = n == 0 ? 0 : 1;
            for (int v = 0; v < n; v++) {
                parent[v] = v;
                size[v] = 1;
            }
        }

        int find(int v) {
            int root = v;
            while (parent[root] != root) root = parent[root];
            while (parent[v] != v) {
                int next = parent[v];
                parent[v] = root;
                v = next;
            }
            return root;
        }

        boolean union(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) return false;
            if (size[a] < size[b]) {
                int temp = a; a = b; b = temp;
            }
            parent[b] = a;
            size[a] += size[b];
            largest = Math.max(largest, size[a]);
            components--;
            return true;
        }
    }

    static int[][] connectivity(int n, int[][] operations) {
        DSU dsu = new DSU(n);
        int[][] states = new int[operations.length][2];
        for (int i = 0; i < operations.length; i++) {
            dsu.union(operations[i][0], operations[i][1]);
            states[i][0] = dsu.components;
            states[i][1] = dsu.largest;
        }
        return states;
    }
    // endregion

    // region graph
    static final class Edge implements Comparable<Edge> {
        final int from, to, id;
        final long weight;
        Edge(int from, int to, long weight, int id) {
            this.from = from; this.to = to; this.weight = weight; this.id = id;
        }
        public int compareTo(Edge other) {
            int byWeight = Long.compare(weight, other.weight);
            return byWeight != 0 ? byWeight : Integer.compare(id, other.id);
        }
    }

    static List<Edge> edges(int[][] rows) {
        List<Edge> result = new ArrayList<>();
        for (int i = 0; i < rows.length; i++) {
            result.add(new Edge(rows[i][0], rows[i][1], rows[i][2], i));
        }
        return result;
    }

    static List<List<Edge>> weightedGraph(int n, List<Edge> edges, boolean undirected) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int v = 0; v < n; v++) graph.add(new ArrayList<>());
        for (Edge e : edges) {
            graph.get(e.from).add(e);
            if (undirected) {
                graph.get(e.to).add(new Edge(e.to, e.from, e.weight, e.id));
            }
        }
        return graph;
    }

    static List<List<Integer>> digraph(int n, int[][] edges, boolean transpose) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int v = 0; v < n; v++) graph.add(new ArrayList<>());
        for (int[] e : edges) {
            int from = transpose ? e[1] : e[0];
            int to = transpose ? e[0] : e[1];
            graph.get(from).add(to);
        }
        return graph;
    }

    static final class Forest {
        long cost;
        int components;
        final List<Integer> edgeIds = new ArrayList<>();
    }
    // endregion

    // region kruskal
    static Forest kruskal(int n, List<Edge> input) {
        List<Edge> sorted = new ArrayList<>(input);
        Collections.sort(sorted);
        DSU dsu = new DSU(n);
        Forest result = new Forest();
        for (Edge e : sorted) {
            if (dsu.union(e.from, e.to)) {
                result.cost += e.weight;
                result.edgeIds.add(e.id);
            }
        }
        result.components = dsu.components;
        return result;
    }
    // endregion

    // region prim
    static Forest prim(int n, List<Edge> edges) {
        List<List<Edge>> graph = weightedGraph(n, edges, true);
        boolean[] used = new boolean[n];
        PriorityQueue<Edge> heap = new PriorityQueue<>();
        Forest result = new Forest();
        for (int start = 0; start < n; start++) {
            if (used[start]) continue;
            result.components++;
            used[start] = true;
            heap.addAll(graph.get(start));
            while (!heap.isEmpty()) {
                Edge e = heap.remove();
                if (used[e.to]) continue;
                used[e.to] = true;
                result.cost += e.weight;
                result.edgeIds.add(e.id);
                for (Edge next : graph.get(e.to)) {
                    if (!used[next.to]) heap.add(next);
                }
            }
        }
        return result;
    }
    // endregion

    // region dfs
    static final class Traversal {
        boolean acyclic = true;
        final List<Integer> finish = new ArrayList<>();
    }

    static Traversal dfs(List<List<Integer>> graph) {
        int n = graph.size();
        int[] state = new int[n]; // 0 = unvisited, 1 = active, 2 = finished
        int[] cursor = new int[n];
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        Traversal result = new Traversal();
        for (int start = 0; start < n; start++) {
            if (state[start] != 0) continue;
            state[start] = 1;
            stack.push(start);
            while (!stack.isEmpty()) {
                int v = stack.peek();
                if (cursor[v] == graph.get(v).size()) {
                    stack.pop();
                    state[v] = 2;
                    result.finish.add(v);
                    continue;
                }
                int to = graph.get(v).get(cursor[v]++);
                if (state[to] == 1) result.acyclic = false;
                if (state[to] == 0) {
                    state[to] = 1;
                    stack.push(to);
                }
            }
        }
        return result;
    }
    // endregion

    // region scc
    static final class Components {
        int[] label;
        final List<int[]> condensation = new ArrayList<>();
    }

    static Components kosaraju(int n, int[][] edges) {
        List<List<Integer>> graph = digraph(n, edges, false);
        List<List<Integer>> reverse = digraph(n, edges, true);
        List<Integer> finish = dfs(graph).finish;
        Components result = new Components();
        result.label = new int[n];
        Arrays.fill(result.label, -1);
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int count = 0;
        for (int i = finish.size() - 1; i >= 0; i--) {
            int start = finish.get(i);
            if (result.label[start] != -1) continue;
            result.label[start] = count;
            stack.push(start);
            while (!stack.isEmpty()) {
                int v = stack.pop();
                for (int to : reverse.get(v)) {
                    if (result.label[to] == -1) {
                        result.label[to] = count;
                        stack.push(to);
                    }
                }
            }
            count++;
        }
        // Linear deduplication for compact labels: visit each target at most once per source SCC.
        List<List<Integer>> members = new ArrayList<>();
        for (int c = 0; c < count; c++) members.add(new ArrayList<>());
        for (int v = 0; v < n; v++) members.get(result.label[v]).add(v);
        int[] seenFrom = new int[count];
        Arrays.fill(seenFrom, -1);
        for (int from = 0; from < count; from++) {
            for (int v : members.get(from)) {
                for (int to : graph.get(v)) {
                    int target = result.label[to];
                    if (target != from && seenFrom[target] != from) {
                        seenFrom[target] = from;
                        result.condensation.add(new int[]{from, target});
                    }
                }
            }
        }
        return result;
    }
    // endregion

    // region topo
    static final class Ordering {
        boolean acyclic;
        final List<Integer> order = new ArrayList<>();
    }

    static Ordering topological(int n, int[][] edges) {
        Traversal traversal = dfs(digraph(n, edges, false));
        Ordering result = new Ordering();
        result.acyclic = traversal.acyclic;
        if (result.acyclic) {
            for (int i = traversal.finish.size() - 1; i >= 0; i--) {
                result.order.add(traversal.finish.get(i));
            }
        }
        return result;
    }
    // endregion

    // region dag
    static final class Paths {
        boolean acyclic;
        Long[] distance;
        int[] parentEdge;
    }

    static Paths shortestDAG(int n, int[][] rows, int source) {
        Ordering topo = topological(n, rows);
        Paths result = new Paths();
        result.acyclic = topo.acyclic;
        result.distance = new Long[topo.acyclic ? n : 0];
        result.parentEdge = new int[topo.acyclic ? n : 0];
        if (!topo.acyclic) return result;
        Arrays.fill(result.parentEdge, -1);
        result.distance[source] = 0L;
        List<List<Edge>> graph = weightedGraph(n, edges(rows), false);
        for (int v : topo.order) {
            if (result.distance[v] == null) continue;
            for (Edge e : graph.get(v)) {
                long candidate = result.distance[v] + e.weight;
                if (result.distance[e.to] == null || candidate < result.distance[e.to]) {
                    result.distance[e.to] = candidate;
                    result.parentEdge[e.to] = e.id;
                }
            }
        }
        return result;
    }
    // endregion

    // region route
    // Preconditions: an acyclic result from shortestDAG and the same original edge rows.
    // Empty list means no route to target. An absent DAG is an invalid helper call.
    static List<Integer> route(int[][] edges, Paths paths, int source, int target) {
        if (!paths.acyclic) throw new IllegalArgumentException("Graph has a cycle");
        List<Integer> result = new ArrayList<>();
        if (paths.distance[target] == null) return result;
        int v = target;
        while (v != source) {
            result.add(v);
            int id = paths.parentEdge[v];
            v = edges[id][0];
        }
        result.add(source);
        Collections.reverse(result);
        return result;
    }
    // endregion

    // region demo
    public static void main(String[] args) {
        int[][] cables = {{0,1,6},{0,2,2},{1,2,3},{1,3,5},{2,3,4},{3,4,-1},{0,1,6},{2,2,-8}};
        Forest a = kruskal(6, edges(cables));
        Forest b = prim(6, edges(cables));
        System.out.println("Kruskal: cost=" + a.cost + ", edges=" + a.edgeIds + ", components=" + a.components);
        System.out.println("Prim: cost=" + b.cost + ", edges=" + b.edgeIds + ", components=" + b.components);
        int[][] roads = {{0,1,5},{0,2,2},{1,3,-4},{2,3,2},{3,4,3},{2,4,8}};
        Paths paths = shortestDAG(6, roads, 0);
        System.out.println("Distances: " + Arrays.toString(paths.distance));
        System.out.println("Parent edges: " + Arrays.toString(paths.parentEdge));
        System.out.println("Route to 4: " + route(roads, paths, 0, 4));
        System.out.println("Route to source: " + route(roads, paths, 0, 0));
        System.out.println("Route to 5: " + route(roads, paths, 0, 5));
    }
    // endregion
}
