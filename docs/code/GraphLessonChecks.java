import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/** Independent checks for GraphLesson; throws on failure, without relying on -ea. */
public final class GraphLessonChecks {
    static int assertions;
    static void check(boolean condition, String message) {
        assertions++;
        if (!condition) throw new AssertionError(message);
    }

    static boolean[][] reach(int n, int[][] rows, boolean undirected) {
        boolean[][] r = new boolean[n][n];
        for (int i = 0; i < n; i++) r[i][i] = true;
        for (int[] edge : rows) {
            r[edge[0]][edge[1]] = true;
            if (undirected) r[edge[1]][edge[0]] = true;
        }
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) r[i][j] |= r[i][k] && r[k][j];
        return r;
    }

    static int componentCount(boolean[][] r) {
        int count = 0;
        for (int i = 0; i < r.length; i++) {
            boolean first = true;
            for (int j = 0; j < i; j++) if (r[i][j]) first = false;
            if (first) count++;
        }
        return count;
    }

    static long bruteForest(int n, int[][] rows) {
        int wanted = componentCount(reach(n, rows, true));
        long best = Long.MAX_VALUE;
        for (int mask = 0; mask < (1 << rows.length); mask++) {
            if (Integer.bitCount(mask) != n - wanted) continue;
            List<int[]> selected = new ArrayList<>();
            long cost = 0;
            for (int i = 0; i < rows.length; i++) if ((mask & (1 << i)) != 0) {
                selected.add(rows[i]); cost += rows[i][2];
            }
            if (componentCount(reach(n, selected.toArray(new int[0][]), true)) == wanted)
                best = Math.min(best, cost);
        }
        return best;
    }

    static boolean validForest(int n, int[][] rows, GraphLesson.Forest forest, long optimum) {
        int expectedComponents = componentCount(reach(n, rows, true));
        if (forest.components != expectedComponents || forest.edgeIds.size() != n - expectedComponents) return false;
        List<int[]> chosen = new ArrayList<>();
        Set<Integer> used = new HashSet<>();
        long sum = 0;
        for (int id : forest.edgeIds) {
            if (id < 0 || id >= rows.length || !used.add(id) || rows[id][0] == rows[id][1]) return false;
            chosen.add(rows[id]); sum += rows[id][2];
        }
        return sum == forest.cost && sum == optimum &&
            componentCount(reach(n, chosen.toArray(new int[0][]), true)) == expectedComponents;
    }

    static boolean kahn(int n, int[][] rows) {
        int[] indegree = new int[n];
        List<List<Integer>> graph = new ArrayList<>();
        for (int v = 0; v < n; v++) graph.add(new ArrayList<>());
        for (int[] e : rows) {graph.get(e[0]).add(e[1]); indegree[e[1]]++;}
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int v = 0; v < n; v++) if (indegree[v] == 0) queue.add(v);
        int count = 0;
        while (!queue.isEmpty()) {
            int v = queue.remove(); count++;
            for (int to : graph.get(v)) if (--indegree[to] == 0) queue.add(to);
        }
        return count == n;
    }

    static void directedChecks(int n, int[][] rows, int source) {
        boolean acyclic = kahn(n, rows);
        GraphLesson.Ordering order = GraphLesson.topological(n, rows);
        check(order.acyclic == acyclic, "Cycle detection disagrees with Kahn");
        check(order.order.size() == (acyclic ? n : 0), "Order length");
        if (acyclic) {
            int[] position = new int[n]; Arrays.fill(position, -1);
            for (int i = 0; i < n; i++) {
                int v = order.order.get(i);
                check(v >= 0 && v < n && position[v] == -1, "Not a permutation");
                position[v] = i;
            }
            for (int[] e : rows) check(position[e[0]] < position[e[1]], "Topological precedence");
        }
        GraphLesson.Components scc = GraphLesson.kosaraju(n, rows);
        boolean[][] r = reach(n, rows, false);
        for (int a = 0; a < n; a++) for (int b = 0; b < n; b++) {
            check(scc.label[a] >= 0 && (scc.label[a] == scc.label[b]) == (r[a][b] && r[b][a]), "SCC membership");
        }
        Set<String> expected = new HashSet<>();
        for (int[] e : rows) if (scc.label[e[0]] != scc.label[e[1]]) expected.add(scc.label[e[0]] + ":" + scc.label[e[1]]);
        Set<String> actual = new HashSet<>();
        for (int[] pair : scc.condensation) check(actual.add(pair[0] + ":" + pair[1]), "Duplicate condensation pair");
        check(actual.equals(expected), "Exact condensation");
        if (n == 0) return;
        GraphLesson.Paths paths = GraphLesson.shortestDAG(n, rows, source);
        check(paths.acyclic == acyclic, "DAG cycle result");
        if (!acyclic) {
            check(paths.distance.length == 0 && paths.parentEdge.length == 0, "Cyclic arrays must be empty");
            return;
        }
        Long[] distances = new Long[n]; distances[source] = 0L;
        // Bellman-Ford-style passes, independent of the tested topological order.
        for (int k = 1; k < n; k++) for (int[] e : rows) {
            if (distances[e[0]] != null) {
                long next = distances[e[0]] + (long)e[2];
                if (distances[e[1]] == null || next < distances[e[1]]) distances[e[1]] = next;
            }
        }
        check(Arrays.equals(paths.distance, distances), "Shortest distances");
        for (int target = 0; target < n; target++) {
            List<Integer> route = GraphLesson.route(rows, paths, source, target);
            if (distances[target] == null) {
                check(route.isEmpty() && paths.parentEdge[target] == -1, "Unreachable route");
                continue;
            }
            int v = target, count = 0; long sum = 0;
            while (v != source) {
                check(++count <= n, "Parent cycle");
                int id = paths.parentEdge[v];
                check(id >= 0 && id < rows.length && rows[id][1] == v, "Invalid parent edge");
                sum += rows[id][2]; v = rows[id][0];
            }
            check(sum == distances[target] && route.size() == count + 1 && route.get(0) == source && route.get(route.size()-1) == target, "Route witness");
            for (int i = 1; i < route.size(); i++) {
                int id = paths.parentEdge[route.get(i)];
                check(rows[id][0] == route.get(i-1) && rows[id][1] == route.get(i), "Route vertices");
            }
        }
        check(paths.parentEdge[source] == -1, "Source parent");
    }

    static void connectivityChecks(int n, int[][] operations) {
        int[][] states = GraphLesson.connectivity(n, operations);
        check(states.length == operations.length, "DSU state count");
        for (int i = 0; i < operations.length; i++) {
            boolean[][] r = reach(n, Arrays.copyOf(operations, i+1), true);
            int largest = 0;
            for (boolean[] row : r) {int size = 0; for (boolean x : row) if (x) size++; largest = Math.max(largest, size);}
            check(states[i][0] == componentCount(r) && states[i][1] == largest, "DSU state");
        }
    }

    public static void main(String[] args) {
        Random random = new Random(20261019L);
        for (int trial = 0; trial < 160; trial++) {
            int n = random.nextInt(8), m = n == 0 ? 0 : random.nextInt(11);
            int[][] rows = new int[m][3];
            for (int[] row : rows) {row[0] = random.nextInt(n); row[1] = random.nextInt(n); row[2] = random.nextInt(15)-7;}
            long optimum = bruteForest(n, rows);
            check(validForest(n, rows, GraphLesson.kruskal(n, GraphLesson.edges(rows)), optimum), "Kruskal forest");
            check(validForest(n, rows, GraphLesson.prim(n, GraphLesson.edges(rows)), optimum), "Prim forest");
            connectivityChecks(n, rows);
            directedChecks(n, rows, n == 0 ? 0 : random.nextInt(n));
            // Acyclic samples ensure distance checks are not dominated by cyclic random graphs.
            List<int[]> dag = new ArrayList<>();
            for (int[] row : rows) if (row[0] < row[1]) dag.add(row);
            directedChecks(n, dag.toArray(new int[0][]), n == 0 ? 0 : random.nextInt(n));
        }
        int[][] tied = {{0,1,1},{1,2,1},{0,2,1}};
        GraphLesson.Forest alternative = new GraphLesson.Forest();
        alternative.cost = 2; alternative.components = 1;
        alternative.edgeIds.add(0); alternative.edgeIds.add(2);
        check(validForest(3,tied,alternative,2),"Accept an alternative optimum");
        alternative.edgeIds.set(1,0);
        check(!validForest(3,tied,alternative,2),"Reject duplicate edge IDs");
        int[][] loops = {{0,0,-10},{0,1,1},{1,2,1}};
        alternative.edgeIds.clear(); alternative.edgeIds.add(0); alternative.edgeIds.add(1);
        check(!validForest(3,loops,alternative,2),"Reject self-loop witness");
        int[][] unreachableCycle = {{0,1,2},{2,3,1},{3,2,1}};
        directedChecks(4,unreachableCycle,0);
        int[][] wide = {{0,1,1000000000},{1,2,1000000000},{2,3,1000000000}};
        check(GraphLesson.kruskal(4,GraphLesson.edges(wide)).cost == 3000000000L,"64-bit forest");
        check(GraphLesson.prim(4,GraphLesson.edges(wide)).cost == 3000000000L,"64-bit Prim");
        directedChecks(4,wide,0);
        int[][] ties = {{0,1,5},{0,2,2},{1,3,-4},{2,3,-1},{3,4,3},{3,4,3}};
        directedChecks(6,ties,0);
        int n = 100000;
        int[][] chain = new int[n-1][3];
        for (int i = 0; i < n-1; i++) chain[i] = new int[]{i,i+1,1};
        check(GraphLesson.topological(n,chain).order.size() == n,"Deep topo");
        GraphLesson.Components components = GraphLesson.kosaraju(n,chain);
        check(components.condensation.size() == n-1,"Deep SCC");
        GraphLesson.Paths paths = GraphLesson.shortestDAG(n,chain,0);
        check(paths.distance[n-1] == n-1L && GraphLesson.route(chain,paths,0,n-1).size() == n,"Deep DAG route");
        check(GraphLesson.connectivity(0,new int[0][]).length == 0,"Empty DSU reset");
        System.out.println("PASS: 160 seeded graph families, boundaries, witnesses and 100000-vertex chain; " + assertions + " checks.");
    }
}
