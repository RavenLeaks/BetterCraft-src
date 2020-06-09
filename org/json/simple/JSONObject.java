/*     */ package org.json.simple;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class JSONObject
/*     */   extends HashMap
/*     */   implements Map, JSONAware, JSONStreamAware
/*     */ {
/*     */   private static final long serialVersionUID = -503443796854799292L;
/*     */   
/*     */   public JSONObject() {}
/*     */   
/*     */   public JSONObject(Map<? extends K, ? extends V> map) {
/*  35 */     super(map);
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
/*     */   public static void writeJSONString(Map map, Writer out) throws IOException {
/*  49 */     if (map == null) {
/*  50 */       out.write("null");
/*     */       
/*     */       return;
/*     */     } 
/*  54 */     boolean first = true;
/*  55 */     Iterator<Map.Entry> iter = map.entrySet().iterator();
/*     */     
/*  57 */     out.write(123);
/*  58 */     while (iter.hasNext()) {
/*  59 */       if (first) {
/*  60 */         first = false;
/*     */       } else {
/*  62 */         out.write(44);
/*  63 */       }  Map.Entry entry = iter.next();
/*  64 */       out.write(34);
/*  65 */       out.write(escape(String.valueOf(entry.getKey())));
/*  66 */       out.write(34);
/*  67 */       out.write(58);
/*  68 */       JSONValue.writeJSONString(entry.getValue(), out);
/*     */     } 
/*  70 */     out.write(125);
/*     */   }
/*     */   
/*     */   public void writeJSONString(Writer out) throws IOException {
/*  74 */     writeJSONString(this, out);
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
/*     */   public static String toJSONString(Map map) {
/*  87 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/*  90 */       writeJSONString(map, writer);
/*  91 */       return writer.toString();
/*  92 */     } catch (IOException e) {
/*     */       
/*  94 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toJSONString() {
/*  99 */     return toJSONString(this);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 103 */     return toJSONString();
/*     */   }
/*     */   
/*     */   public static String toString(String key, Object value) {
/* 107 */     StringBuffer sb = new StringBuffer();
/* 108 */     sb.append('"');
/* 109 */     if (key == null) {
/* 110 */       sb.append("null");
/*     */     } else {
/* 112 */       JSONValue.escape(key, sb);
/* 113 */     }  sb.append('"').append(':');
/*     */     
/* 115 */     sb.append(JSONValue.toJSONString(value));
/*     */     
/* 117 */     return sb.toString();
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
/*     */   public static String escape(String s) {
/* 130 */     return JSONValue.escape(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\simple\JSONObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */