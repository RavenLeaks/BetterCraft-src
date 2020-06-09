/*     */ package org.json;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JSONWriter
/*     */ {
/*     */   private static final int maxdepth = 200;
/*     */   private boolean comma;
/*     */   protected char mode;
/*     */   private final JSONObject[] stack;
/*     */   private int top;
/*     */   protected Appendable writer;
/*     */   
/*     */   public JSONWriter(Appendable w) {
/*  98 */     this.comma = false;
/*  99 */     this.mode = 'i';
/* 100 */     this.stack = new JSONObject[200];
/* 101 */     this.top = 0;
/* 102 */     this.writer = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JSONWriter append(String string) throws JSONException {
/* 112 */     if (string == null) {
/* 113 */       throw new JSONException("Null pointer");
/*     */     }
/* 115 */     if (this.mode == 'o' || this.mode == 'a') {
/*     */       try {
/* 117 */         if (this.comma && this.mode == 'a') {
/* 118 */           this.writer.append(',');
/*     */         }
/* 120 */         this.writer.append(string);
/* 121 */       } catch (IOException e) {
/*     */ 
/*     */ 
/*     */         
/* 125 */         throw new JSONException(e);
/*     */       } 
/* 127 */       if (this.mode == 'o') {
/* 128 */         this.mode = 'k';
/*     */       }
/* 130 */       this.comma = true;
/* 131 */       return this;
/*     */     } 
/* 133 */     throw new JSONException("Value out of sequence.");
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
/*     */   public JSONWriter array() throws JSONException {
/* 146 */     if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
/* 147 */       push(null);
/* 148 */       append("[");
/* 149 */       this.comma = false;
/* 150 */       return this;
/*     */     } 
/* 152 */     throw new JSONException("Misplaced array.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JSONWriter end(char m, char c) throws JSONException {
/* 163 */     if (this.mode != m) {
/* 164 */       throw new JSONException((m == 'a') ? 
/* 165 */           "Misplaced endArray." : 
/* 166 */           "Misplaced endObject.");
/*     */     }
/* 168 */     pop(m);
/*     */     try {
/* 170 */       this.writer.append(c);
/* 171 */     } catch (IOException e) {
/*     */ 
/*     */ 
/*     */       
/* 175 */       throw new JSONException(e);
/*     */     } 
/* 177 */     this.comma = true;
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter endArray() throws JSONException {
/* 188 */     return end('a', ']');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter endObject() throws JSONException {
/* 198 */     return end('k', '}');
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
/*     */   public JSONWriter key(String string) throws JSONException {
/* 210 */     if (string == null) {
/* 211 */       throw new JSONException("Null key.");
/*     */     }
/* 213 */     if (this.mode == 'k') {
/*     */       try {
/* 215 */         JSONObject topObject = this.stack[this.top - 1];
/*     */         
/* 217 */         if (topObject.has(string)) {
/* 218 */           throw new JSONException("Duplicate key \"" + string + "\"");
/*     */         }
/* 220 */         topObject.put(string, true);
/* 221 */         if (this.comma) {
/* 222 */           this.writer.append(',');
/*     */         }
/* 224 */         this.writer.append(JSONObject.quote(string));
/* 225 */         this.writer.append(':');
/* 226 */         this.comma = false;
/* 227 */         this.mode = 'o';
/* 228 */         return this;
/* 229 */       } catch (IOException e) {
/*     */ 
/*     */ 
/*     */         
/* 233 */         throw new JSONException(e);
/*     */       } 
/*     */     }
/* 236 */     throw new JSONException("Misplaced key.");
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
/*     */   public JSONWriter object() throws JSONException {
/* 250 */     if (this.mode == 'i') {
/* 251 */       this.mode = 'o';
/*     */     }
/* 253 */     if (this.mode == 'o' || this.mode == 'a') {
/* 254 */       append("{");
/* 255 */       push(new JSONObject());
/* 256 */       this.comma = false;
/* 257 */       return this;
/*     */     } 
/* 259 */     throw new JSONException("Misplaced object.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pop(char c) throws JSONException {
/* 270 */     if (this.top <= 0) {
/* 271 */       throw new JSONException("Nesting error.");
/*     */     }
/* 273 */     char m = (this.stack[this.top - 1] == null) ? 'a' : 'k';
/* 274 */     if (m != c) {
/* 275 */       throw new JSONException("Nesting error.");
/*     */     }
/* 277 */     this.top--;
/* 278 */     this.mode = (this.top == 0) ? 
/* 279 */       'd' : (
/* 280 */       (this.stack[this.top - 1] == null) ? 
/* 281 */       'a' : 
/* 282 */       'k');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void push(JSONObject jo) throws JSONException {
/* 291 */     if (this.top >= 200) {
/* 292 */       throw new JSONException("Nesting too deep.");
/*     */     }
/* 294 */     this.stack[this.top] = jo;
/* 295 */     this.mode = (jo == null) ? 'a' : 'k';
/* 296 */     this.top++;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String valueToString(Object value) throws JSONException {
/* 324 */     if (value == null || value.equals(null)) {
/* 325 */       return "null";
/*     */     }
/* 327 */     if (value instanceof JSONString) {
/*     */       String object;
/*     */       try {
/* 330 */         object = ((JSONString)value).toJSONString();
/* 331 */       } catch (Exception e) {
/* 332 */         throw new JSONException(e);
/*     */       } 
/* 334 */       if (object != null) {
/* 335 */         return object;
/*     */       }
/* 337 */       throw new JSONException("Bad value from toJSONString: " + object);
/*     */     } 
/* 339 */     if (value instanceof Number) {
/*     */       
/* 341 */       String numberAsString = JSONObject.numberToString((Number)value);
/* 342 */       if (JSONObject.NUMBER_PATTERN.matcher(numberAsString).matches())
/*     */       {
/* 344 */         return numberAsString;
/*     */       }
/*     */ 
/*     */       
/* 348 */       return JSONObject.quote(numberAsString);
/*     */     } 
/* 350 */     if (value instanceof Boolean || value instanceof JSONObject || 
/* 351 */       value instanceof JSONArray) {
/* 352 */       return value.toString();
/*     */     }
/* 354 */     if (value instanceof Map) {
/* 355 */       Map<?, ?> map = (Map<?, ?>)value;
/* 356 */       return (new JSONObject(map)).toString();
/*     */     } 
/* 358 */     if (value instanceof Collection) {
/* 359 */       Collection<?> coll = (Collection)value;
/* 360 */       return (new JSONArray(coll)).toString();
/*     */     } 
/* 362 */     if (value.getClass().isArray()) {
/* 363 */       return (new JSONArray(value)).toString();
/*     */     }
/* 365 */     if (value instanceof Enum) {
/* 366 */       return JSONObject.quote(((Enum)value).name());
/*     */     }
/* 368 */     return JSONObject.quote(value.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter value(boolean b) throws JSONException {
/* 379 */     return append(b ? "true" : "false");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter value(double d) throws JSONException {
/* 389 */     return value(Double.valueOf(d));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter value(long l) throws JSONException {
/* 399 */     return append(Long.toString(l));
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
/*     */   public JSONWriter value(Object object) throws JSONException {
/* 411 */     return append(valueToString(object));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\JSONWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */