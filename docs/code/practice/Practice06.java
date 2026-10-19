import java.util.*;

public class Practice06 {
static boolean solve(int n, int[][] edges, int[] order) {
    if (order.length != n) return false;
    int[] position = new int[n];
    Arrays.fill(position, -1);
    for (int i = 0; i < n; i++) {
        int v = order[i];
        if (v < 0 || v >= n || position[v] != -1) return false;
        position[v] = i;
    }
    for (int[] edge : edges) if (position[edge[0]] >= position[edge[1]]) return false;
    return true;
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
        Object result = solve(4, new int[][]{{0,2},{1,2},{2,3}}, new int[]{1,0,2,3});
        String actual = json(result);
        if (!actual.equals("true")) throw new AssertionError("Case 1: " + actual);
        passed++;
    }
    {
        Object result = solve(4, new int[][]{{0,2},{1,2},{2,3}}, new int[]{0,2,1,3});
        String actual = json(result);
        if (!actual.equals("false")) throw new AssertionError("Case 2: " + actual);
        passed++;
    }
    {
        Object result = solve(4, new int[][]{{0,2},{1,2},{2,3}}, new int[]{0,1,2,3});
        String actual = json(result);
        if (!actual.equals("true")) throw new AssertionError("Case 3: " + actual);
        passed++;
    }
    {
        Object result = solve(3, new int[][]{}, new int[]{0,0,2});
        String actual = json(result);
        if (!actual.equals("false")) throw new AssertionError("Case 4: " + actual);
        passed++;
    }
    {
        Object result = solve(3, new int[][]{}, new int[]{0,1});
        String actual = json(result);
        if (!actual.equals("false")) throw new AssertionError("Case 5: " + actual);
        passed++;
    }
    {
        Object result = solve(1, new int[][]{{0,0}}, new int[]{0});
        String actual = json(result);
        if (!actual.equals("false")) throw new AssertionError("Case 6: " + actual);
        passed++;
    }
    {
        Object result = solve(0, new int[][]{}, new int[]{});
        String actual = json(result);
        if (!actual.equals("true")) throw new AssertionError("Case 7: " + actual);
        passed++;
    }
    {
        Object result = solve(4, new int[][]{{0,1},{2,3},{3,2}}, new int[]{0,1,2,3});
        String actual = json(result);
        if (!actual.equals("false")) throw new AssertionError("Case 8: " + actual);
        passed++;
    }
    {
        Object result = solve(3, new int[][]{{0,1},{0,1}}, new int[]{0,1,2});
        String actual = json(result);
        if (!actual.equals("true")) throw new AssertionError("Case 9: " + actual);
        passed++;
    }
    {
        Object result = solve(2, new int[][]{}, new int[]{0,2});
        String actual = json(result);
        if (!actual.equals("false")) throw new AssertionError("Case 10: " + actual);
        passed++;
    }
    {
        Object result = solve(2, new int[][]{}, new int[]{-1,0});
        String actual = json(result);
        if (!actual.equals("false")) throw new AssertionError("Case 11: " + actual);
        passed++;
    }
    System.out.println("Passed " + passed + " cases");
}
}
