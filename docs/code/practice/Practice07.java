import java.util.*;

public class Practice07 {
static int[][] solve(int[] labels, int[][] edges) {
    Map<Integer, Set<Integer>> outgoing = new HashMap<>();
    for (int[] edge : edges) {
        int a = labels[edge[0]], b = labels[edge[1]];
        if (a != b) outgoing.computeIfAbsent(a, key -> new HashSet<>()).add(b);
    }
    List<int[]> pairs = new ArrayList<>();
    for (int a : outgoing.keySet()) for (int b : outgoing.get(a)) pairs.add(new int[]{a, b});
    pairs.sort((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
    return pairs.toArray(new int[0][]);
}

static String json(Object value) {
    if (value == null) return "null";
    if (!value.getClass().isArray()) return value.toString();
    StringJoiner out = new StringJoiner(",", "[", "]");
    for (int i = 0; i < java.lang.reflect.Array.getLength(value); i++)
        out.add(json(java.lang.reflect.Array.get(value, i)));
    return out.toString();
}
public static void main(String[] args) {
    int passed = 0;
    {
        Object result = solve(new int[]{5,5,9,3}, new int[][]{{0,1},{1,0},{1,2},{0,2},{2,3},{3,3}});
        Arrays.sort((int[][]) result, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        String actual = json(result);
        if (!actual.equals("[[5,9],[9,3]]")) throw new AssertionError("Case 1: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{10,10}, new int[][]{{0,1},{1,0}});
        Arrays.sort((int[][]) result, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        String actual = json(result);
        if (!actual.equals("[]")) throw new AssertionError("Case 2: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{}, new int[][]{});
        Arrays.sort((int[][]) result, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        String actual = json(result);
        if (!actual.equals("[]")) throw new AssertionError("Case 3: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{8,2,7}, new int[][]{{0,1},{0,2},{0,1},{1,2}});
        Arrays.sort((int[][]) result, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        String actual = json(result);
        if (!actual.equals("[[2,7],[8,2],[8,7]]")) throw new AssertionError("Case 4: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{0,1,2}, new int[][]{});
        Arrays.sort((int[][]) result, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        String actual = json(result);
        if (!actual.equals("[]")) throw new AssertionError("Case 5: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{0}, new int[][]{{0,0}});
        Arrays.sort((int[][]) result, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
        String actual = json(result);
        if (!actual.equals("[]")) throw new AssertionError("Case 6: " + actual);
        passed++;
    }
    System.out.println("Passed " + passed + " cases");
}
}
