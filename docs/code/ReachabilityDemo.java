import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Breadth-first traversal of an undirected graph.
 * Vertices are 0..n-1; require 0 <= source < n and valid link endpoints.
 * Returns discovery order, which depends on the order of links, not sorted IDs.
 * The visited mark is set before enqueueing, so each vertex is queued once.
 * Time: O(n + m); memory including adjacency lists and output: O(n + m).
 */
public final class ReachabilityDemo {
    static List<Integer> reachable(int n, int[][] links, int source) {
        List<List<Integer>> neighbours = new ArrayList<>();
        for (int v = 0; v < n; v++) neighbours.add(new ArrayList<>());
        for (int[] link : links) {
            neighbours.get(link[0]).add(link[1]);
            neighbours.get(link[1]).add(link[0]);
        }
        boolean[] visited = new boolean[n];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        List<Integer> order = new ArrayList<>();
        visited[source] = true;
        queue.addLast(source);
        while (!queue.isEmpty()) {
            int v = queue.removeFirst();
            order.add(v);
            for (int next : neighbours.get(v)) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.addLast(next);
                }
            }
        }
        return order;
    }

    public static void main(String[] args) {
        int[][] links = {{0, 1}, {0, 2}, {1, 3}};
        List<Integer> rooms = reachable(5, links, 0);
        System.out.println("Reachable from 0: " + rooms);
        System.out.println("vertex 4 reachable: " + rooms.contains(4));
        // Try: add {3,4}; add a repeated edge; change the source to 4.
        // Explain why a vertex is marked before it is added to the queue.
    }
}
