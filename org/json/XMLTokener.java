/*     */ package org.json;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.util.HashMap;
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
/*     */ public class XMLTokener
/*     */   extends JSONTokener
/*     */ {
/*  44 */   public static final HashMap<String, Character> entity = new HashMap<>(8); static {
/*  45 */     entity.put("amp", XML.AMP);
/*  46 */     entity.put("apos", XML.APOS);
/*  47 */     entity.put("gt", XML.GT);
/*  48 */     entity.put("lt", XML.LT);
/*  49 */     entity.put("quot", XML.QUOT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLTokener(Reader r) {
/*  57 */     super(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLTokener(String s) {
/*  65 */     super(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nextCDATA() throws JSONException {
/*  76 */     StringBuilder sb = new StringBuilder();
/*  77 */     while (more()) {
/*  78 */       char c = next();
/*  79 */       sb.append(c);
/*  80 */       int i = sb.length() - 3;
/*  81 */       if (i >= 0 && sb.charAt(i) == ']' && 
/*  82 */         sb.charAt(i + 1) == ']' && sb.charAt(i + 2) == '>') {
/*  83 */         sb.setLength(i);
/*  84 */         return sb.toString();
/*     */       } 
/*     */     } 
/*  87 */     throw syntaxError("Unclosed CDATA");
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
/*     */   public Object nextContent() throws JSONException {
/*     */     while (true) {
/* 104 */       char c = next();
/* 105 */       if (!Character.isWhitespace(c)) {
/* 106 */         if (c == '\000') {
/* 107 */           return null;
/*     */         }
/* 109 */         if (c == '<') {
/* 110 */           return XML.LT;
/*     */         }
/* 112 */         StringBuilder sb = new StringBuilder();
/*     */         while (true) {
/* 114 */           if (c == '\000') {
/* 115 */             return sb.toString().trim();
/*     */           }
/* 117 */           if (c == '<') {
/* 118 */             back();
/* 119 */             return sb.toString().trim();
/*     */           } 
/* 121 */           if (c == '&') {
/* 122 */             sb.append(nextEntity(c));
/*     */           } else {
/* 124 */             sb.append(c);
/*     */           } 
/* 126 */           c = next();
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object nextEntity(char ampersand) throws JSONException {
/*     */     char c;
/* 139 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 141 */       c = next();
/* 142 */       if (Character.isLetterOrDigit(c) || c == '#')
/* 143 */       { sb.append(Character.toLowerCase(c)); continue; }  break;
/* 144 */     }  if (c == ';') {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       String string = sb.toString();
/* 151 */       return unescapeEntity(string);
/*     */     } 
/*     */     throw syntaxError("Missing ';' in XML entity: &" + sb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String unescapeEntity(String e) {
/* 161 */     if (e == null || e.isEmpty()) {
/* 162 */       return "";
/*     */     }
/*     */     
/* 165 */     if (e.charAt(0) == '#') {
/*     */       int cp;
/* 167 */       if (e.charAt(1) == 'x') {
/*     */         
/* 169 */         cp = Integer.parseInt(e.substring(2), 16);
/*     */       } else {
/*     */         
/* 172 */         cp = Integer.parseInt(e.substring(1));
/*     */       } 
/* 174 */       return new String(new int[] { cp }, 0, 1);
/*     */     } 
/* 176 */     Character knownEntity = entity.get(e);
/* 177 */     if (knownEntity == null)
/*     */     {
/* 179 */       return String.valueOf('&') + e + ';';
/*     */     }
/* 181 */     return knownEntity.toString();
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
/*     */   public Object nextMeta() throws JSONException {
/*     */     char c, q;
/*     */     do {
/* 198 */       c = next();
/* 199 */     } while (Character.isWhitespace(c));
/* 200 */     switch (c) {
/*     */       case '\000':
/* 202 */         throw syntaxError("Misshaped meta tag");
/*     */       case '<':
/* 204 */         return XML.LT;
/*     */       case '>':
/* 206 */         return XML.GT;
/*     */       case '/':
/* 208 */         return XML.SLASH;
/*     */       case '=':
/* 210 */         return XML.EQ;
/*     */       case '!':
/* 212 */         return XML.BANG;
/*     */       case '?':
/* 214 */         return XML.QUEST;
/*     */       case '"':
/*     */       case '\'':
/* 217 */         q = c;
/*     */         while (true) {
/* 219 */           c = next();
/* 220 */           if (c == '\000') {
/* 221 */             throw syntaxError("Unterminated string");
/*     */           }
/* 223 */           if (c == q) {
/* 224 */             return Boolean.TRUE;
/*     */           }
/*     */         } 
/*     */     } 
/*     */     while (true)
/* 229 */     { c = next();
/* 230 */       if (Character.isWhitespace(c)) {
/* 231 */         return Boolean.TRUE;
/*     */       }
/* 233 */       switch (c)
/*     */       { case '\000':
/* 235 */           throw syntaxError("Unterminated string");
/*     */         case '!':
/*     */         case '"':
/*     */         case '\'':
/*     */         case '/':
/*     */         case '<':
/*     */         case '=':
/*     */         case '>':
/*     */         case '?':
/* 244 */           break; }  }  back();
/* 245 */     return Boolean.TRUE;
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
/*     */   public Object nextToken() throws JSONException {
/*     */     char c, q;
/*     */     do {
/* 265 */       c = next();
/* 266 */     } while (Character.isWhitespace(c));
/* 267 */     switch (c) {
/*     */       case '\000':
/* 269 */         throw syntaxError("Misshaped element");
/*     */       case '<':
/* 271 */         throw syntaxError("Misplaced '<'");
/*     */       case '>':
/* 273 */         return XML.GT;
/*     */       case '/':
/* 275 */         return XML.SLASH;
/*     */       case '=':
/* 277 */         return XML.EQ;
/*     */       case '!':
/* 279 */         return XML.BANG;
/*     */       case '?':
/* 281 */         return XML.QUEST;
/*     */ 
/*     */ 
/*     */       
/*     */       case '"':
/*     */       case '\'':
/* 287 */         q = c;
/* 288 */         sb = new StringBuilder();
/*     */         while (true) {
/* 290 */           c = next();
/* 291 */           if (c == '\000') {
/* 292 */             throw syntaxError("Unterminated string");
/*     */           }
/* 294 */           if (c == q) {
/* 295 */             return sb.toString();
/*     */           }
/* 297 */           if (c == '&') {
/* 298 */             sb.append(nextEntity(c)); continue;
/*     */           } 
/* 300 */           sb.append(c);
/*     */         } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     StringBuilder sb = new StringBuilder();
/*     */     while (true)
/* 309 */     { sb.append(c);
/* 310 */       c = next();
/* 311 */       if (Character.isWhitespace(c)) {
/* 312 */         return sb.toString();
/*     */       }
/* 314 */       switch (c)
/*     */       { case '\000':
/* 316 */           return sb.toString();
/*     */         case '!':
/*     */         case '/':
/*     */         case '=':
/*     */         case '>':
/*     */         case '?':
/*     */         case '[':
/*     */         case ']':
/* 324 */           back();
/* 325 */           return sb.toString();
/*     */         case '"':
/*     */         case '\'':
/*     */         case '<':
/* 329 */           break; }  }  throw syntaxError("Bad character in a name");
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
/*     */   public void skipPast(String to) {
/* 349 */     int offset = 0;
/* 350 */     int length = to.length();
/* 351 */     char[] circle = new char[length];
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */     
/* 358 */     for (i = 0; i < length; i++) {
/* 359 */       char c = next();
/* 360 */       if (c == '\000') {
/*     */         return;
/*     */       }
/* 363 */       circle[i] = c;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 369 */       int j = offset;
/* 370 */       boolean b = true;
/*     */ 
/*     */ 
/*     */       
/* 374 */       for (i = 0; i < length; i++) {
/* 375 */         if (circle[j] != to.charAt(i)) {
/* 376 */           b = false;
/*     */           break;
/*     */         } 
/* 379 */         j++;
/* 380 */         if (j >= length) {
/* 381 */           j -= length;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 387 */       if (b) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 393 */       char c = next();
/* 394 */       if (c == '\000') {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 401 */       circle[offset] = c;
/* 402 */       offset++;
/* 403 */       if (offset >= length)
/* 404 */         offset -= length; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\XMLTokener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */