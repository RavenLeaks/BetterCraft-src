/*     */ package org.json;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
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
/*     */ public class JSONTokener
/*     */ {
/*     */   private long character;
/*     */   private boolean eof;
/*     */   private long index;
/*     */   private long line;
/*     */   private char previous;
/*     */   private final Reader reader;
/*     */   private boolean usePrevious;
/*     */   private long characterPreviousLine;
/*     */   
/*     */   public JSONTokener(Reader reader) {
/*  66 */     this.reader = reader.markSupported() ? 
/*  67 */       reader : 
/*  68 */       new BufferedReader(reader);
/*  69 */     this.eof = false;
/*  70 */     this.usePrevious = false;
/*  71 */     this.previous = Character.MIN_VALUE;
/*  72 */     this.index = 0L;
/*  73 */     this.character = 1L;
/*  74 */     this.characterPreviousLine = 0L;
/*  75 */     this.line = 1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONTokener(InputStream inputStream) {
/*  84 */     this(new InputStreamReader(inputStream));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONTokener(String s) {
/*  94 */     this(new StringReader(s));
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
/*     */   public void back() throws JSONException {
/* 106 */     if (this.usePrevious || this.index <= 0L) {
/* 107 */       throw new JSONException("Stepping back two steps is not supported");
/*     */     }
/* 109 */     decrementIndexes();
/* 110 */     this.usePrevious = true;
/* 111 */     this.eof = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decrementIndexes() {
/* 118 */     this.index--;
/* 119 */     if (this.previous == '\r' || this.previous == '\n') {
/* 120 */       this.line--;
/* 121 */       this.character = this.characterPreviousLine;
/* 122 */     } else if (this.character > 0L) {
/* 123 */       this.character--;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int dehexchar(char c) {
/* 134 */     if (c >= '0' && c <= '9') {
/* 135 */       return c - 48;
/*     */     }
/* 137 */     if (c >= 'A' && c <= 'F') {
/* 138 */       return c - 55;
/*     */     }
/* 140 */     if (c >= 'a' && c <= 'f') {
/* 141 */       return c - 87;
/*     */     }
/* 143 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean end() {
/* 152 */     return (this.eof && !this.usePrevious);
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
/*     */   public boolean more() throws JSONException {
/* 164 */     if (this.usePrevious) {
/* 165 */       return true;
/*     */     }
/*     */     try {
/* 168 */       this.reader.mark(1);
/* 169 */     } catch (IOException e) {
/* 170 */       throw new JSONException("Unable to preserve stream position", e);
/*     */     } 
/*     */     
/*     */     try {
/* 174 */       if (this.reader.read() <= 0) {
/* 175 */         this.eof = true;
/* 176 */         return false;
/*     */       } 
/* 178 */       this.reader.reset();
/* 179 */     } catch (IOException e) {
/* 180 */       throw new JSONException("Unable to read the next character from the stream", e);
/*     */     } 
/* 182 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char next() throws JSONException {
/*     */     int c;
/* 194 */     if (this.usePrevious) {
/* 195 */       this.usePrevious = false;
/* 196 */       c = this.previous;
/*     */     } else {
/*     */       try {
/* 199 */         c = this.reader.read();
/* 200 */       } catch (IOException exception) {
/* 201 */         throw new JSONException(exception);
/*     */       } 
/*     */     } 
/* 204 */     if (c <= 0) {
/* 205 */       this.eof = true;
/* 206 */       return Character.MIN_VALUE;
/*     */     } 
/* 208 */     incrementIndexes(c);
/* 209 */     this.previous = (char)c;
/* 210 */     return this.previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void incrementIndexes(int c) {
/* 219 */     if (c > 0) {
/* 220 */       this.index++;
/* 221 */       if (c == 13) {
/* 222 */         this.line++;
/* 223 */         this.characterPreviousLine = this.character;
/* 224 */         this.character = 0L;
/* 225 */       } else if (c == 10) {
/* 226 */         if (this.previous != '\r') {
/* 227 */           this.line++;
/* 228 */           this.characterPreviousLine = this.character;
/*     */         } 
/* 230 */         this.character = 0L;
/*     */       } else {
/* 232 */         this.character++;
/*     */       } 
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
/*     */   public char next(char c) throws JSONException {
/* 245 */     char n = next();
/* 246 */     if (n != c) {
/* 247 */       if (n > '\000') {
/* 248 */         throw syntaxError("Expected '" + c + "' and instead saw '" + 
/* 249 */             n + "'");
/*     */       }
/* 251 */       throw syntaxError("Expected '" + c + "' and instead saw ''");
/*     */     } 
/* 253 */     return n;
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
/*     */   public String next(int n) throws JSONException {
/* 267 */     if (n == 0) {
/* 268 */       return "";
/*     */     }
/*     */     
/* 271 */     char[] chars = new char[n];
/* 272 */     int pos = 0;
/*     */     
/* 274 */     while (pos < n) {
/* 275 */       chars[pos] = next();
/* 276 */       if (end()) {
/* 277 */         throw syntaxError("Substring bounds error");
/*     */       }
/* 279 */       pos++;
/*     */     } 
/* 281 */     return new String(chars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char nextClean() throws JSONException {
/*     */     char c;
/*     */     do {
/* 292 */       c = next();
/* 293 */     } while (c != '\000' && c <= ' ');
/* 294 */     return c;
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
/*     */   public String nextString(char quote) throws JSONException {
/* 313 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 315 */       char c = next();
/* 316 */       switch (c) {
/*     */         case '\000':
/*     */         case '\n':
/*     */         case '\r':
/* 320 */           throw syntaxError("Unterminated string");
/*     */         case '\\':
/* 322 */           c = next();
/* 323 */           switch (c) {
/*     */             case 'b':
/* 325 */               sb.append('\b');
/*     */               continue;
/*     */             case 't':
/* 328 */               sb.append('\t');
/*     */               continue;
/*     */             case 'n':
/* 331 */               sb.append('\n');
/*     */               continue;
/*     */             case 'f':
/* 334 */               sb.append('\f');
/*     */               continue;
/*     */             case 'r':
/* 337 */               sb.append('\r');
/*     */               continue;
/*     */             case 'u':
/*     */               try {
/* 341 */                 sb.append((char)Integer.parseInt(next(4), 16));
/* 342 */               } catch (NumberFormatException e) {
/* 343 */                 throw syntaxError("Illegal escape.", e);
/*     */               } 
/*     */               continue;
/*     */             case '"':
/*     */             case '\'':
/*     */             case '/':
/*     */             case '\\':
/* 350 */               sb.append(c);
/*     */               continue;
/*     */           } 
/* 353 */           throw syntaxError("Illegal escape.");
/*     */       } 
/*     */ 
/*     */       
/* 357 */       if (c == quote) {
/* 358 */         return sb.toString();
/*     */       }
/* 360 */       sb.append(c);
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
/*     */   public String nextTo(char delimiter) throws JSONException {
/* 375 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 377 */       char c = next();
/* 378 */       if (c == delimiter || c == '\000' || c == '\n' || c == '\r') {
/* 379 */         if (c != '\000') {
/* 380 */           back();
/*     */         }
/* 382 */         return sb.toString().trim();
/*     */       } 
/* 384 */       sb.append(c);
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
/*     */   public String nextTo(String delimiters) throws JSONException {
/* 399 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 401 */       char c = next();
/* 402 */       if (delimiters.indexOf(c) >= 0 || c == '\000' || 
/* 403 */         c == '\n' || c == '\r') {
/* 404 */         if (c != '\000') {
/* 405 */           back();
/*     */         }
/* 407 */         return sb.toString().trim();
/*     */       } 
/* 409 */       sb.append(c);
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
/*     */   public Object nextValue() throws JSONException {
/* 422 */     char c = nextClean();
/*     */ 
/*     */     
/* 425 */     switch (c) {
/*     */       case '"':
/*     */       case '\'':
/* 428 */         return nextString(c);
/*     */       case '{':
/* 430 */         back();
/* 431 */         return new JSONObject(this);
/*     */       case '[':
/* 433 */         back();
/* 434 */         return new JSONArray(this);
/*     */     } 
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
/* 446 */     StringBuilder sb = new StringBuilder();
/* 447 */     while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
/* 448 */       sb.append(c);
/* 449 */       c = next();
/*     */     } 
/* 451 */     if (!this.eof) {
/* 452 */       back();
/*     */     }
/*     */     
/* 455 */     String string = sb.toString().trim();
/* 456 */     if ("".equals(string)) {
/* 457 */       throw syntaxError("Missing value");
/*     */     }
/* 459 */     return JSONObject.stringToValue(string);
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
/*     */   public char skipTo(char to) throws JSONException {
/*     */     try {
/* 475 */       long startIndex = this.index;
/* 476 */       long startCharacter = this.character;
/* 477 */       long startLine = this.line;
/* 478 */       this.reader.mark(1000000);
/*     */       while (true) {
/* 480 */         char c = next();
/* 481 */         if (c == '\000') {
/*     */ 
/*     */ 
/*     */           
/* 485 */           this.reader.reset();
/* 486 */           this.index = startIndex;
/* 487 */           this.character = startCharacter;
/* 488 */           this.line = startLine;
/* 489 */           return Character.MIN_VALUE;
/*     */         } 
/* 491 */         if (c == to) {
/* 492 */           this.reader.mark(1);
/*     */ 
/*     */ 
/*     */           
/* 496 */           back();
/* 497 */           return c;
/*     */         } 
/*     */       } 
/*     */     } catch (IOException exception) {
/*     */       throw new JSONException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JSONException syntaxError(String message) {
/* 507 */     return new JSONException(String.valueOf(message) + toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONException syntaxError(String message, Throwable causedBy) {
/* 518 */     return new JSONException(String.valueOf(message) + toString(), causedBy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 528 */     return " at " + this.index + " [character " + this.character + " line " + 
/* 529 */       this.line + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\JSONTokener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */