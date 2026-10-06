import java.util.*;
/** Provided JSON transport; students implement Algorithms, not this parser. */
final class Json {
 private final String s; private int i=0;
 Json(String s){this.s=s;}
 static Object parse(String s){return new Json(s).value();}
 void ws(){while(i<s.length()&&Character.isWhitespace(s.charAt(i)))i++;}
 Object value(){ws();char c=s.charAt(i);
  if(c=='"')return str();
  if(c=='{'){i++;Map<String,Object> m=new LinkedHashMap<>();ws();if(s.charAt(i)=='}'){i++;return m;}do{String k=str();ws();i++;m.put(k,value());ws();char z=s.charAt(i++);if(z=='}')return m;ws();}while(true);}
  if(c=='['){i++;List<Object> a=new ArrayList<>();ws();if(s.charAt(i)==']'){i++;return a;}do{a.add(value());ws();char z=s.charAt(i++);if(z==']')return a;}while(true);}
  if(s.startsWith("null",i)){i+=4;return null;}if(s.startsWith("true",i)){i+=4;return true;}if(s.startsWith("false",i)){i+=5;return false;}
  int j=i;if(c=='-')i++;while(i<s.length()&&Character.isDigit(s.charAt(i)))i++;return Long.parseLong(s.substring(j,i));
 }
 String str(){ws();i++;StringBuilder b=new StringBuilder();while(true){char c=s.charAt(i++);if(c=='"')return b.toString();if(c=='\\'){c=s.charAt(i++);switch(c){case 'n':c='\n';break;case 'r':c='\r';break;case 't':c='\t';break;case 'b':c='\b';break;case 'f':c='\f';break;case 'u':c=(char)Integer.parseInt(s.substring(i,i+4),16);i+=4;break;}}b.append(c);}}
 static String write(Object x){if(x==null)return "null";if(x instanceof String){String s=(String)x;StringBuilder b=new StringBuilder("\"");for(char c:s.toCharArray()){if(c=='"'||c=='\\')b.append('\\').append(c);else if(c<32)b.append(String.format("\\u%04x",(int)c));else b.append(c);}return b.append('"').toString();}if(x instanceof Map){StringJoiner j=new StringJoiner(",","{","}");for(Object eo:((Map<?,?>)x).entrySet()){Map.Entry<?,?> e=(Map.Entry<?,?>)eo;j.add(write(e.getKey().toString())+":"+write(e.getValue()));}return j.toString();}if(x instanceof Iterable){StringJoiner j=new StringJoiner(",","[","]");for(Object e:(Iterable<?>)x)j.add(write(e));return j.toString();}if(x.getClass().isArray()){StringJoiner j=new StringJoiner(",","[","]");for(int i=0;i<java.lang.reflect.Array.getLength(x);i++)j.add(write(java.lang.reflect.Array.get(x,i)));return j.toString();}return x.toString();}
}
