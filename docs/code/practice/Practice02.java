import java.util.*;

public class Practice02 {
static int[] solve(int[] labels) {
    Map<Integer, Integer> count = new HashMap<>();
    for (int label : labels) count.put(label, count.getOrDefault(label, 0) + 1);
    int[] answer = new int[labels.length];
    for (int v = 0; v < labels.length; v++) answer[v] = count.get(labels[v]);
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
        Object result = solve(new int[]{7,7,2,9,2});
        String actual = json(result);
        if (!actual.equals("[2,2,2,1,2]")) throw new AssertionError("Case 1: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{});
        String actual = json(result);
        if (!actual.equals("[]")) throw new AssertionError("Case 2: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{0});
        String actual = json(result);
        if (!actual.equals("[1]")) throw new AssertionError("Case 3: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{4,4,4});
        String actual = json(result);
        if (!actual.equals("[3,3,3]")) throw new AssertionError("Case 4: " + actual);
        passed++;
    }
    {
        Object result = solve(new int[]{8,3,6});
        String actual = json(result);
        if (!actual.equals("[1,1,1]")) throw new AssertionError("Case 5: " + actual);
        passed++;
    }
    System.out.println("Passed " + passed + " cases");
}
}
