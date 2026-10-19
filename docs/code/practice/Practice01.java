import java.util.*;

public class Practice01 {
static int[] solve(int n, int[][] edges, int vertex) {
    List<Integer> ids = new ArrayList<>();
    for (int i = 0; i < edges.length; i++) {
        if (edges[i][0] == vertex) ids.add(i);
    }
    int[] answer = new int[ids.size()];
    for (int i = 0; i < answer.length; i++) answer[i] = ids.get(i);
    return answer;
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
        Object result = solve(4, new int[][]{{0,1,7},{2,0,1},{0,0,-2},{0,1,3}}, 0);
        String actual = json(result);
        if (!actual.equals("[0,2,3]")) throw new AssertionError("Case 1: " + actual);
        passed++;
    }
    {
        Object result = solve(1, new int[][]{}, 0);
        String actual = json(result);
        if (!actual.equals("[]")) throw new AssertionError("Case 2: " + actual);
        passed++;
    }
    {
        Object result = solve(3, new int[][]{{2,1,4}}, 0);
        String actual = json(result);
        if (!actual.equals("[]")) throw new AssertionError("Case 3: " + actual);
        passed++;
    }
    {
        Object result = solve(2, new int[][]{{1,0,-1},{1,0,-1}}, 1);
        String actual = json(result);
        if (!actual.equals("[0,1]")) throw new AssertionError("Case 4: " + actual);
        passed++;
    }
    {
        Object result = solve(1, new int[][]{{0,0,0}}, 0);
        String actual = json(result);
        if (!actual.equals("[0]")) throw new AssertionError("Case 5: " + actual);
        passed++;
    }
    System.out.println("Passed " + passed + " cases");
}
}
