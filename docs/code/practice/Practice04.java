import java.util.*;

public class Practice04 {
static Long solve(Long current, Long fromDistance, long weight) {
    if (fromDistance == null) return current;
    long candidate = fromDistance + weight;
    if (current == null || candidate < current) return candidate;
    return current;
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
        Object result = solve(6L, 5L, -4L);
        String actual = json(result);
        if (!actual.equals("1")) throw new AssertionError("Case 1: " + actual);
        passed++;
    }
    {
        Object result = solve(null, null, 9L);
        String actual = json(result);
        if (!actual.equals("null")) throw new AssertionError("Case 2: " + actual);
        passed++;
    }
    {
        Object result = solve(null, 5L, -4L);
        String actual = json(result);
        if (!actual.equals("1")) throw new AssertionError("Case 3: " + actual);
        passed++;
    }
    {
        Object result = solve(1L, 2L, 4L);
        String actual = json(result);
        if (!actual.equals("1")) throw new AssertionError("Case 4: " + actual);
        passed++;
    }
    {
        Object result = solve(3L, null, -20L);
        String actual = json(result);
        if (!actual.equals("3")) throw new AssertionError("Case 5: " + actual);
        passed++;
    }
    {
        Object result = solve(5L, 2L, 3L);
        String actual = json(result);
        if (!actual.equals("5")) throw new AssertionError("Case 6: " + actual);
        passed++;
    }
    {
        Object result = solve(null, 3000000000L, 1000000000L);
        String actual = json(result);
        if (!actual.equals("4000000000")) throw new AssertionError("Case 7: " + actual);
        passed++;
    }
    {
        Object result = solve(0L, 4L, -5L);
        String actual = json(result);
        if (!actual.equals("-1")) throw new AssertionError("Case 8: " + actual);
        passed++;
    }
    System.out.println("Passed " + passed + " cases");
}
}
