/*     */ package org.json.simple;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ public class JSONArray
/*     */   extends ArrayList
/*     */   implements JSONAware, JSONStreamAware
/*     */ {
/*     */   private static final long serialVersionUID = 3957988303675231981L;
/*     */   
/*     */   public JSONArray() {}
/*     */   
/*     */   public JSONArray(Collection<? extends E> c) {
/*  36 */     super(c);
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
/*     */   public static void writeJSONString(Collection collection, Writer out) throws IOException {
/*  49 */     if (collection == null) {
/*  50 */       out.write("null");
/*     */       
/*     */       return;
/*     */     } 
/*  54 */     boolean first = true;
/*  55 */     Iterator iter = collection.iterator();
/*     */     
/*  57 */     out.write(91);
/*  58 */     while (iter.hasNext()) {
/*  59 */       if (first) {
/*  60 */         first = false;
/*     */       } else {
/*  62 */         out.write(44);
/*     */       } 
/*  64 */       Object value = iter.next();
/*  65 */       if (value == null) {
/*  66 */         out.write("null");
/*     */         
/*     */         continue;
/*     */       } 
/*  70 */       JSONValue.writeJSONString(value, out);
/*     */     } 
/*  72 */     out.write(93);
/*     */   }
/*     */   
/*     */   public void writeJSONString(Writer out) throws IOException {
/*  76 */     writeJSONString(this, out);
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
/*     */   public static String toJSONString(Collection collection) {
/*  89 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/*  92 */       writeJSONString(collection, writer);
/*  93 */       return writer.toString();
/*  94 */     } catch (IOException e) {
/*     */       
/*  96 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeJSONString(byte[] array, Writer out) throws IOException {
/* 101 */     if (array == null) {
/* 102 */       out.write("null");
/* 103 */     } else if (array.length == 0) {
/* 104 */       out.write("[]");
/*     */     } else {
/* 106 */       out.write("[");
/* 107 */       out.write(String.valueOf(array[0]));
/*     */       
/* 109 */       for (int i = 1; i < array.length; i++) {
/* 110 */         out.write(",");
/* 111 */         out.write(String.valueOf(array[i]));
/*     */       } 
/*     */       
/* 114 */       out.write("]");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toJSONString(byte[] array) {
/* 119 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 122 */       writeJSONString(array, writer);
/* 123 */       return writer.toString();
/* 124 */     } catch (IOException e) {
/*     */       
/* 126 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeJSONString(short[] array, Writer out) throws IOException {
/* 131 */     if (array == null) {
/* 132 */       out.write("null");
/* 133 */     } else if (array.length == 0) {
/* 134 */       out.write("[]");
/*     */     } else {
/* 136 */       out.write("[");
/* 137 */       out.write(String.valueOf(array[0]));
/*     */       
/* 139 */       for (int i = 1; i < array.length; i++) {
/* 140 */         out.write(",");
/* 141 */         out.write(String.valueOf(array[i]));
/*     */       } 
/*     */       
/* 144 */       out.write("]");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toJSONString(short[] array) {
/* 149 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 152 */       writeJSONString(array, writer);
/* 153 */       return writer.toString();
/* 154 */     } catch (IOException e) {
/*     */       
/* 156 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeJSONString(int[] array, Writer out) throws IOException {
/* 161 */     if (array == null) {
/* 162 */       out.write("null");
/* 163 */     } else if (array.length == 0) {
/* 164 */       out.write("[]");
/*     */     } else {
/* 166 */       out.write("[");
/* 167 */       out.write(String.valueOf(array[0]));
/*     */       
/* 169 */       for (int i = 1; i < array.length; i++) {
/* 170 */         out.write(",");
/* 171 */         out.write(String.valueOf(array[i]));
/*     */       } 
/*     */       
/* 174 */       out.write("]");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toJSONString(int[] array) {
/* 179 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 182 */       writeJSONString(array, writer);
/* 183 */       return writer.toString();
/* 184 */     } catch (IOException e) {
/*     */       
/* 186 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeJSONString(long[] array, Writer out) throws IOException {
/* 191 */     if (array == null) {
/* 192 */       out.write("null");
/* 193 */     } else if (array.length == 0) {
/* 194 */       out.write("[]");
/*     */     } else {
/* 196 */       out.write("[");
/* 197 */       out.write(String.valueOf(array[0]));
/*     */       
/* 199 */       for (int i = 1; i < array.length; i++) {
/* 200 */         out.write(",");
/* 201 */         out.write(String.valueOf(array[i]));
/*     */       } 
/*     */       
/* 204 */       out.write("]");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toJSONString(long[] array) {
/* 209 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 212 */       writeJSONString(array, writer);
/* 213 */       return writer.toString();
/* 214 */     } catch (IOException e) {
/*     */       
/* 216 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeJSONString(float[] array, Writer out) throws IOException {
/* 221 */     if (array == null) {
/* 222 */       out.write("null");
/* 223 */     } else if (array.length == 0) {
/* 224 */       out.write("[]");
/*     */     } else {
/* 226 */       out.write("[");
/* 227 */       out.write(String.valueOf(array[0]));
/*     */       
/* 229 */       for (int i = 1; i < array.length; i++) {
/* 230 */         out.write(",");
/* 231 */         out.write(String.valueOf(array[i]));
/*     */       } 
/*     */       
/* 234 */       out.write("]");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toJSONString(float[] array) {
/* 239 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 242 */       writeJSONString(array, writer);
/* 243 */       return writer.toString();
/* 244 */     } catch (IOException e) {
/*     */       
/* 246 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeJSONString(double[] array, Writer out) throws IOException {
/* 251 */     if (array == null) {
/* 252 */       out.write("null");
/* 253 */     } else if (array.length == 0) {
/* 254 */       out.write("[]");
/*     */     } else {
/* 256 */       out.write("[");
/* 257 */       out.write(String.valueOf(array[0]));
/*     */       
/* 259 */       for (int i = 1; i < array.length; i++) {
/* 260 */         out.write(",");
/* 261 */         out.write(String.valueOf(array[i]));
/*     */       } 
/*     */       
/* 264 */       out.write("]");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toJSONString(double[] array) {
/* 269 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 272 */       writeJSONString(array, writer);
/* 273 */       return writer.toString();
/* 274 */     } catch (IOException e) {
/*     */       
/* 276 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeJSONString(boolean[] array, Writer out) throws IOException {
/* 281 */     if (array == null) {
/* 282 */       out.write("null");
/* 283 */     } else if (array.length == 0) {
/* 284 */       out.write("[]");
/*     */     } else {
/* 286 */       out.write("[");
/* 287 */       out.write(String.valueOf(array[0]));
/*     */       
/* 289 */       for (int i = 1; i < array.length; i++) {
/* 290 */         out.write(",");
/* 291 */         out.write(String.valueOf(array[i]));
/*     */       } 
/*     */       
/* 294 */       out.write("]");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toJSONString(boolean[] array) {
/* 299 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 302 */       writeJSONString(array, writer);
/* 303 */       return writer.toString();
/* 304 */     } catch (IOException e) {
/*     */       
/* 306 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeJSONString(char[] array, Writer out) throws IOException {
/* 311 */     if (array == null) {
/* 312 */       out.write("null");
/* 313 */     } else if (array.length == 0) {
/* 314 */       out.write("[]");
/*     */     } else {
/* 316 */       out.write("[\"");
/* 317 */       out.write(String.valueOf(array[0]));
/*     */       
/* 319 */       for (int i = 1; i < array.length; i++) {
/* 320 */         out.write("\",\"");
/* 321 */         out.write(String.valueOf(array[i]));
/*     */       } 
/*     */       
/* 324 */       out.write("\"]");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toJSONString(char[] array) {
/* 329 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 332 */       writeJSONString(array, writer);
/* 333 */       return writer.toString();
/* 334 */     } catch (IOException e) {
/*     */       
/* 336 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void writeJSONString(Object[] array, Writer out) throws IOException {
/* 341 */     if (array == null) {
/* 342 */       out.write("null");
/* 343 */     } else if (array.length == 0) {
/* 344 */       out.write("[]");
/*     */     } else {
/* 346 */       out.write("[");
/* 347 */       JSONValue.writeJSONString(array[0], out);
/*     */       
/* 349 */       for (int i = 1; i < array.length; i++) {
/* 350 */         out.write(",");
/* 351 */         JSONValue.writeJSONString(array[i], out);
/*     */       } 
/*     */       
/* 354 */       out.write("]");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toJSONString(Object[] array) {
/* 359 */     StringWriter writer = new StringWriter();
/*     */     
/*     */     try {
/* 362 */       writeJSONString(array, writer);
/* 363 */       return writer.toString();
/* 364 */     } catch (IOException e) {
/*     */       
/* 366 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toJSONString() {
/* 371 */     return toJSONString(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 379 */     return toJSONString();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\simple\JSONArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */