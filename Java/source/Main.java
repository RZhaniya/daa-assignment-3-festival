import java.io.*;import java.util.*;
public final class Main {
 @SuppressWarnings("unchecked") static Object handle(Map<String,Object>x) {
  switch((String)x.get("task")) {
   case "ping": return Map.of("status","ready");
            case "dsu": return Algorithms.dsu(x);
            case "kruskal": return Algorithms.kruskal(x);
            case "prim": return Algorithms.prim(x);
            case "scc": return Algorithms.scc(x);
            case "topo": return Algorithms.topo(x);
            case "dag": return Algorithms.dag(x);
   default: throw new IllegalArgumentException("Unknown task");
  }
 }
 public static void main(String[]args)throws Exception {BufferedReader r=new BufferedReader(new InputStreamReader(System.in,java.nio.charset.StandardCharsets.UTF_8));String line;while((line=r.readLine())!=null){try{System.out.println(Json.write(handle((Map<String,Object>)Json.parse(line))));}catch(UnsupportedOperationException e){System.out.println(Json.write(Map.of("error",e.getMessage())));}}}
}
