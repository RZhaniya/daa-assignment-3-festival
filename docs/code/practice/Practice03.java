import java.util.*;

public class Practice03 {
static int[] solve(int[] finish) {
    int n = finish.length;
    int[] position = new int[n];
    for (int i = 0; i < n; i++) position[finish[n - 1 - i]] = i;
    return position;
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
        Object result = solve(new int[]{3,2,0,1});
        String actual = json(result);
        if (!actual.equals("[1,0,2,3]")) throw new AssertionError("Case 1: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{});
        String actual = json(result);
        if (!actual.equals("[]")) throw new AssertionError("Case 2: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{2,1,0});
        String actual = json(result);
        if (!actual.equals("[0,1,2]")) throw new AssertionError("Case 3: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{0,1,2});
        String actual = json(result);
        if (!actual.equals("[2,1,0]")) throw new AssertionError("Case 4: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{1,0,2});
        String actual = json(result);
        if (!actual.equals("[1,2,0]")) throw new AssertionError("Case 5: " + actual);
        passed++;
    }
    System.out.println("Passed " + passed + " cases");
}
}
