/*     */ package org.json.simple;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import org.json.simple.parser.JSONParser;
/*     */ import org.json.simple.parser.ParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JSONValue
/*     */ {
/*     */   public static Object parse(Reader in) {
/*     */     try {
/*  46 */       JSONParser parser = new JSONParser();
/*  47 */       return parser.parse(in);
/*     */     }
/*  49 */     catch (Exception e) {
/*  50 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object parse(String s) {
/*  75 */     StringReader in = new StringReader(s);
/*  76 */     return parse(in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object parseWithException(Reader in) throws IOException, ParseException {
/*  97 */     JSONParser parser = new JSONParser();
/*  98 */     return parser.parse(in);
/*     */   }
/*     */   
/*     */   public static Object parseWithException(String s) throws ParseException {
/* 102 */     JSONParser parser = new JSONParser();
/* 103 */     return parser.parse(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeJSONString(Object value, Writer out) throws IOException {
/* 121 */     if (value == null) {
/* 122 */       out.write("null");
/*     */       
/*     */       return;
/*     */     } 
/* 126 */     if (value instanceof String) {
/* 127 */       out.write(34);
/* 128 */       out.write(escape((String)value));
/* 129 */       out.write(34);
/*     */       
/*     */       return;
/*     */     } 
/* 133 */     if (value instanceof Double) {
/* 134 */       if (((Double)value).isInfinite() || ((Double)value).isNaN()) {
/* 135 */         out.write("null");
/*     */       } else {
/* 137 */         out.write(value.toString());
/*     */       } 
/*     */       return;
/*     */     } 
/* 141 */     if (value instanceof Float) {
/* 142 */       if (((Float)value).isInfinite() || ((Float)value).isNaN()) {
/* 143 */         out.write("null");
/*     */       } else {
/* 145 */         out.write(value.toString());
/*     */       } 
/*     */       return;
/*     */     } 
/* 149 */     if (value instanceof Number) {
/* 150 */       out.write(value.toString());
/*     */       
/*     */       return;
/*     */     } 
/* 154 */     if (value instanceof Boolean) {
/* 155 */       out.write(value.toString());
/*     */       
/*     */       return;
/*     */     } 
/* 159 */     if (value instanceof JSONStreamAware) {
/* 160 */       ((JSONStreamAware)value).writeJSONString(out);
/*     */       
/*     */       return;
/*     */     } 
/* 164 */     if (value instanceof JSONAware) {
/* 165 */       out.write(((JSONAware)value).toJSONString());
/*     */       
/*     */       return;
/*     */     } 
/* 169 */     if (value instanceof Map) {
/* 170 */       JSONObject.writeJSONString((Map)value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 174 */     if (value instanceof Collection) {
/* 175 */       JSONArray.writeJSONString((Collection)value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 179 */     if (value instanceof byte[]) {
/* 180 */       JSONArray.writeJSONString((byte[])value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 184 */     if (value instanceof short[]) {
/* 185 */       JSONArray.writeJSONString((short[])value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 189 */     if (value instanceof int[]) {
/* 190 */       JSONArray.writeJSONString((int[])value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 194 */     if (value instanceof long[]) {
/* 195 */       JSONArray.writeJSONString((long[])value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 199 */     if (value instanceof float[]) {
/* 200 */       JSONArray.writeJSONString((float[])value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 204 */     if (value instanceof double[]) {
/* 205 */       JSONArray.writeJSONString((double[])value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 209 */     if (value instanceof boolean[]) {
/* 210 */       JSONArray.writeJSONString((boolean[])value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 214 */     if (value instanceof char[]) {
/* 215 */       JSONArray.writeJSONString((char[])value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 219 */     if (value instanceof Object[]) {
/* 220 */       JSONArray.writeJSONString((Object[])value, out);
/*     */       
/*     */       return;
/*     */     } 
/* 224 */     out.write(value.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toJSONString(Object value) {
/* 242 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 245 */       writeJSONString(value, writer);
/* 246 */       return writer.toString();
/* 247 */     } catch (IOException e) {
/*     */       
/* 249 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String escape(String s) {
/* 259 */     if (s == null)
/* 260 */       return null; 
/* 261 */     StringBuffer sb = new StringBuffer();
/* 262 */     escape(s, sb);
/* 263 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void escape(String s, StringBuffer sb) {
/* 271 */     int len = s.length();
/* 272 */     for (int i = 0; i < len; i++) {
/* 273 */       char ch = s.charAt(i);
/* 274 */       switch (ch) {
/*     */         case '"':
/* 276 */           sb.append("\\\"");
/*     */           break;
/*     */         case '\\':
/* 279 */           sb.append("\\\\");
/*     */           break;
/*     */         case '\b':
/* 282 */           sb.append("\\b");
/*     */           break;
/*     */         case '\f':
/* 285 */           sb.append("\\f");
/*     */           break;
/*     */         case '\n':
/* 288 */           sb.append("\\n");
/*     */           break;
/*     */         case '\r':
/* 291 */           sb.append("\\r");
/*     */           break;
/*     */         case '\t':
/* 294 */           sb.append("\\t");
/*     */           break;
/*     */         case '/':
/* 297 */           sb.append("\\/");
/*     */           break;
/*     */         
/*     */         default:
/* 301 */           if ((ch >= '\000' && ch <= '\037') || (ch >= '' && ch <= '') || (ch >= ' ' && ch <= '⃿')) {
/* 302 */             String ss = Integer.toHexString(ch);
/* 303 */             sb.append("\\u");
/* 304 */             for (int k = 0; k < 4 - ss.length(); k++) {
/* 305 */               sb.append('0');
/*     */             }
/* 307 */             sb.append(ss.toUpperCase());
/*     */             break;
/*     */           } 
/* 310 */           sb.append(ch);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\simple\JSONValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */