import java.util.*;

public class Practice05 {
static int solve(int[][] edges, boolean[] inside) {
    int best = -1;
    for (int i = 0; i < edges.length; i++) {
        int u = edges[i][0], v = edges[i][1];
        if (inside[u] == inside[v]) continue;
        if (best == -1 || edges[i][2] < edges[best][2]) best = i;
    }
    return best;
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
        Object result = solve(new int[][]{{0,0,-100},{0,1,5},{0,1,-2},{1,2,1},{2,3,0}}, new boolean[]{true,false,false,false});
        String actual = json(result);
        if (!actual.equals("2")) throw new AssertionError("Case 1: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[][]{{0,1,4},{0,2,4},{1,2,-9}}, new boolean[]{true,false,false});
        String actual = json(result);
        if (!actual.equals("0")) throw new AssertionError("Case 2: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[][]{}, new boolean[]{true,false});
        String actual = json(result);
        if (!actual.equals("-1")) throw new AssertionError("Case 3: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[][]{{0,1,-1}}, new boolean[]{true,true});
        String actual = json(result);
        if (!actual.equals("-1")) throw new AssertionError("Case 4: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[][]{{0,1,-1}}, new boolean[]{false,false});
        String actual = json(result);
        if (!actual.equals("-1")) throw new AssertionError("Case 5: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[][]{{0,1,7},{2,1,2}}, new boolean[]{false,true,false});
        String actual = json(result);
        if (!actual.equals("1")) throw new AssertionError("Case 6: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[][]{}, new boolean[]{});
        String actual = json(result);
        if (!actual.equals("-1")) throw new AssertionError("Case 7: " + actual);
        passed++;
    }
    System.out.println("Passed " + passed + " cases");
}
}
