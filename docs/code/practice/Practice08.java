import java.util.*;

public class Practice08 {
static Long solve(int n, int[][] edges, int source, int target, int[] edgeIds) {
    int vertex = source;
    long total = 0;
    for (int id : edgeIds) {
        if (id < 0 || id >= edges.length || edges[id][0] != vertex) return null;
        vertex = edges[id][1];
        total += edges[id][2];
    }
    return vertex == target ? total : null;
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
        Object result = solve(3, new int[][]{{0,1,5},{0,1,2},{1,2,-1},{0,2,7}}, 0, 2, new int[]{1,2});
        String actual = json(result);
        if (!actual.equals("1")) throw new AssertionError("Case 1: " + actual);
        passed++;
    }
    {
        Object result = solve(3, new int[][]{{0,1,5},{0,1,2},{1,2,-1},{0,2,7}}, 0, 2, new int[]{2,1});
        String actual = json(result);
        if (!actual.equals("null")) throw new AssertionError("Case 2: " + actual);
        passed++;
    }
    {
        Object result = solve(1, new int[][]{}, 0, 0, new int[]{});
        String actual = json(result);
        if (!actual.equals("0")) throw new AssertionError("Case 3: " + actual);
        passed++;
    }
    {
        Object result = solve(2, new int[][]{}, 0, 1, new int[]{});
        String actual = json(result);
        if (!actual.equals("null")) throw new AssertionError("Case 4: " + actual);
        passed++;
    }
    {
        Object result = solve(1, new int[][]{{0,0,-2}}, 0, 0, new int[]{0,0});
        String actual = json(result);
        if (!actual.equals("-4")) throw new AssertionError("Case 5: " + actual);
        passed++;
    }
    {
        Object result = solve(2, new int[][]{{0,1,4}}, 0, 1, new int[]{-1});
        String actual = json(result);
        if (!actual.equals("null")) throw new AssertionError("Case 6: " + actual);
        passed++;
    }
    {
        Object result = solve(2, new int[][]{{0,1,4}}, 0, 1, new int[]{1});
        String actual = json(result);
        if (!actual.equals("null")) throw new AssertionError("Case 7: " + actual);
        passed++;
    }
    {
        Object result = solve(4, new int[][]{{0,1,1000000000},{1,2,1000000000},{2,3,1000000000}}, 0, 3, new int[]{0,1,2});
        String actual = json(result);
        if (!actual.equals("3000000000")) throw new AssertionError("Case 8: " + actual);
        passed++;
    }
    System.out.println("Passed " + passed + " cases");
}
}
