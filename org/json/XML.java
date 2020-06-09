/*     */ package org.json;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XML
/*     */ {
/*  42 */   public static final Character AMP = Character.valueOf('&');
/*     */ 
/*     */   
/*  45 */   public static final Character APOS = Character.valueOf('\'');
/*     */ 
/*     */   
/*  48 */   public static final Character BANG = Character.valueOf('!');
/*     */ 
/*     */   
/*  51 */   public static final Character EQ = Character.valueOf('=');
/*     */ 
/*     */   
/*  54 */   public static final Character GT = Character.valueOf('>');
/*     */ 
/*     */   
/*  57 */   public static final Character LT = Character.valueOf('<');
/*     */ 
/*     */   
/*  60 */   public static final Character QUEST = Character.valueOf('?');
/*     */ 
/*     */   
/*  63 */   public static final Character QUOT = Character.valueOf('"');
/*     */ 
/*     */   
/*  66 */   public static final Character SLASH = Character.valueOf('/');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String NULL_ATTR = "xsi:nil";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Iterable<Integer> codePointIterator(final String string) {
/*  85 */     return new Iterable<Integer>()
/*     */       {
/*     */         public Iterator<Integer> iterator() {
/*  88 */           return new Iterator<Integer>()
/*     */             {
/*     */               private int nextIndex;
/*     */               private int length;
/*     */               
/*     */               public boolean hasNext() {
/*  94 */                 return (this.nextIndex < this.length);
/*     */               }
/*     */ 
/*     */               
/*     */               public Integer next() {
/*  99 */                 int result = string.codePointAt(this.nextIndex);
/* 100 */                 this.nextIndex += Character.charCount(result);
/* 101 */                 return Integer.valueOf(result);
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 106 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
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
/*     */   public static String escape(String string) {
/* 129 */     StringBuilder sb = new StringBuilder(string.length());
/* 130 */     for (Iterator<Integer> iterator = codePointIterator(string).iterator(); iterator.hasNext(); ) { int cp = ((Integer)iterator.next()).intValue();
/* 131 */       switch (cp) {
/*     */         case 38:
/* 133 */           sb.append("&amp;");
/*     */           continue;
/*     */         case 60:
/* 136 */           sb.append("&lt;");
/*     */           continue;
/*     */         case 62:
/* 139 */           sb.append("&gt;");
/*     */           continue;
/*     */         case 34:
/* 142 */           sb.append("&quot;");
/*     */           continue;
/*     */         case 39:
/* 145 */           sb.append("&apos;");
/*     */           continue;
/*     */       } 
/* 148 */       if (mustEscape(cp)) {
/* 149 */         sb.append("&#x");
/* 150 */         sb.append(Integer.toHexString(cp));
/* 151 */         sb.append(';'); continue;
/*     */       } 
/* 153 */       sb.appendCodePoint(cp); }
/*     */ 
/*     */ 
/*     */     
/* 157 */     return sb.toString();
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
/*     */   private static boolean mustEscape(int cp) {
/* 173 */     if (!Character.isISOControl(cp) || 
/* 174 */       cp == 9 || 
/* 175 */       cp == 10 || 
/* 176 */       cp == 13)
/*     */     {
/*     */       
/* 179 */       if ((cp >= 32 && cp <= 55295) || (
/* 180 */         cp >= 57344 && cp <= 65533) || (
/* 181 */         cp >= 65536 && cp <= 1114111)) {
/*     */         return false;
/*     */       }
/*     */     }
/*     */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String unescape(String string) {
/* 194 */     StringBuilder sb = new StringBuilder(string.length());
/* 195 */     for (int i = 0, length = string.length(); i < length; i++) {
/* 196 */       char c = string.charAt(i);
/* 197 */       if (c == '&') {
/* 198 */         int semic = string.indexOf(';', i);
/* 199 */         if (semic > i) {
/* 200 */           String entity = string.substring(i + 1, semic);
/* 201 */           sb.append(XMLTokener.unescapeEntity(entity));
/*     */           
/* 203 */           i += entity.length() + 1;
/*     */         }
/*     */         else {
/*     */           
/* 207 */           sb.append(c);
/*     */         } 
/*     */       } else {
/*     */         
/* 211 */         sb.append(c);
/*     */       } 
/*     */     } 
/* 214 */     return sb.toString();
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
/*     */   public static void noSpace(String string) throws JSONException {
/* 226 */     int length = string.length();
/* 227 */     if (length == 0) {
/* 228 */       throw new JSONException("Empty string.");
/*     */     }
/* 230 */     for (int i = 0; i < length; i++) {
/* 231 */       if (Character.isWhitespace(string.charAt(i))) {
/* 232 */         throw new JSONException("'" + string + 
/* 233 */             "' contains a space character.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean parse(XMLTokener x, JSONObject context, String name, XMLParserConfiguration config) throws JSONException {
/* 254 */     JSONObject jsonObject = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     Object token = x.nextToken();
/*     */ 
/*     */ 
/*     */     
/* 273 */     if (token == BANG) {
/* 274 */       char c = x.next();
/* 275 */       if (c == '-') {
/* 276 */         if (x.next() == '-') {
/* 277 */           x.skipPast("-->");
/* 278 */           return false;
/*     */         } 
/* 280 */         x.back();
/* 281 */       } else if (c == '[') {
/* 282 */         token = x.nextToken();
/* 283 */         if ("CDATA".equals(token) && 
/* 284 */           x.next() == '[') {
/* 285 */           String string = x.nextCDATA();
/* 286 */           if (string.length() > 0) {
/* 287 */             context.accumulate(config.cDataTagName, string);
/*     */           }
/* 289 */           return false;
/*     */         } 
/*     */         
/* 292 */         throw x.syntaxError("Expected 'CDATA['");
/*     */       } 
/* 294 */       int i = 1;
/*     */       do {
/* 296 */         token = x.nextMeta();
/* 297 */         if (token == null)
/* 298 */           throw x.syntaxError("Missing '>' after '<!'."); 
/* 299 */         if (token == LT) {
/* 300 */           i++;
/* 301 */         } else if (token == GT) {
/* 302 */           i--;
/*     */         } 
/* 304 */       } while (i > 0);
/* 305 */       return false;
/* 306 */     }  if (token == QUEST) {
/*     */ 
/*     */       
/* 309 */       x.skipPast("?>");
/* 310 */       return false;
/* 311 */     }  if (token == SLASH) {
/*     */ 
/*     */ 
/*     */       
/* 315 */       token = x.nextToken();
/* 316 */       if (name == null) {
/* 317 */         throw x.syntaxError("Mismatched close tag " + token);
/*     */       }
/* 319 */       if (!token.equals(name)) {
/* 320 */         throw x.syntaxError("Mismatched " + name + " and " + token);
/*     */       }
/* 322 */       if (x.nextToken() != GT) {
/* 323 */         throw x.syntaxError("Misshaped close tag");
/*     */       }
/* 325 */       return true;
/*     */     } 
/* 327 */     if (token instanceof Character) {
/* 328 */       throw x.syntaxError("Misshaped tag");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 333 */     String tagName = (String)token;
/* 334 */     token = null;
/* 335 */     jsonObject = new JSONObject();
/* 336 */     boolean nilAttributeFound = false;
/*     */     while (true) {
/* 338 */       if (token == null) {
/* 339 */         token = x.nextToken();
/*     */       }
/*     */       
/* 342 */       if (token instanceof String) {
/* 343 */         String string = (String)token;
/* 344 */         token = x.nextToken();
/* 345 */         if (token == EQ) {
/* 346 */           token = x.nextToken();
/* 347 */           if (!(token instanceof String)) {
/* 348 */             throw x.syntaxError("Missing value");
/*     */           }
/*     */           
/* 351 */           if (config.convertNilAttributeToNull && 
/* 352 */             "xsi:nil".equals(string) && 
/* 353 */             Boolean.parseBoolean((String)token)) {
/* 354 */             nilAttributeFound = true;
/* 355 */           } else if (!nilAttributeFound) {
/* 356 */             jsonObject.accumulate(string, 
/* 357 */                 config.keepStrings ? 
/* 358 */                 token : 
/* 359 */                 stringToValue((String)token));
/*     */           } 
/* 361 */           token = null; continue;
/*     */         } 
/* 363 */         jsonObject.accumulate(string, ""); continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 367 */     if (token == SLASH) {
/*     */       
/* 369 */       if (x.nextToken() != GT) {
/* 370 */         throw x.syntaxError("Misshaped tag");
/*     */       }
/* 372 */       if (nilAttributeFound) {
/* 373 */         context.accumulate(tagName, JSONObject.NULL);
/* 374 */       } else if (jsonObject.length() > 0) {
/* 375 */         context.accumulate(tagName, jsonObject);
/*     */       } else {
/* 377 */         context.accumulate(tagName, "");
/*     */       } 
/* 379 */       return false;
/*     */     } 
/* 381 */     if (token == GT) {
/*     */       while (true) {
/*     */         
/* 384 */         token = x.nextContent();
/* 385 */         if (token == null) {
/* 386 */           if (tagName != null) {
/* 387 */             throw x.syntaxError("Unclosed tag " + tagName);
/*     */           }
/* 389 */           return false;
/* 390 */         }  if (token instanceof String) {
/* 391 */           String string = (String)token;
/* 392 */           if (string.length() > 0)
/* 393 */             jsonObject.accumulate(config.cDataTagName, 
/* 394 */                 config.keepStrings ? string : stringToValue(string)); 
/*     */           continue;
/*     */         } 
/* 397 */         if (token == LT)
/*     */         {
/* 399 */           if (parse(x, jsonObject, tagName, config)) {
/* 400 */             if (jsonObject.length() == 0) {
/* 401 */               context.accumulate(tagName, "");
/* 402 */             } else if (jsonObject.length() == 1 && 
/* 403 */               jsonObject.opt(config.cDataTagName) != null) {
/* 404 */               context.accumulate(tagName, jsonObject.opt(config.cDataTagName));
/*     */             } else {
/* 406 */               context.accumulate(tagName, jsonObject);
/*     */             } 
/* 408 */             return false;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 413 */     throw x.syntaxError("Misshaped tag");
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
/*     */   public static Object stringToValue(String string) {
/* 428 */     if (string.equals("")) {
/* 429 */       return string;
/*     */     }
/* 431 */     if (string.equalsIgnoreCase("true")) {
/* 432 */       return Boolean.TRUE;
/*     */     }
/* 434 */     if (string.equalsIgnoreCase("false")) {
/* 435 */       return Boolean.FALSE;
/*     */     }
/* 437 */     if (string.equalsIgnoreCase("null")) {
/* 438 */       return JSONObject.NULL;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 446 */     char initial = string.charAt(0);
/* 447 */     if ((initial >= '0' && initial <= '9') || initial == '-') {
/*     */       
/*     */       try {
/*     */         
/* 451 */         if (string.indexOf('.') > -1 || string.indexOf('e') > -1 || 
/* 452 */           string.indexOf('E') > -1 || "-0".equals(string)) {
/* 453 */           Double d = Double.valueOf(string);
/* 454 */           if (!d.isInfinite() && !d.isNaN()) {
/* 455 */             return d;
/*     */           }
/*     */         } else {
/* 458 */           Long myLong = Long.valueOf(string);
/* 459 */           if (string.equals(myLong.toString())) {
/* 460 */             if (myLong.longValue() == myLong.intValue()) {
/* 461 */               return Integer.valueOf(myLong.intValue());
/*     */             }
/* 463 */             return myLong;
/*     */           } 
/*     */         } 
/* 466 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 469 */     return string;
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
/*     */   public static JSONObject toJSONObject(String string) throws JSONException {
/* 489 */     return toJSONObject(string, XMLParserConfiguration.ORIGINAL);
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
/*     */   public static JSONObject toJSONObject(Reader reader) throws JSONException {
/* 508 */     return toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
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
/*     */   public static JSONObject toJSONObject(Reader reader, boolean keepStrings) throws JSONException {
/* 532 */     if (keepStrings) {
/* 533 */       return toJSONObject(reader, XMLParserConfiguration.KEEP_STRINGS);
/*     */     }
/* 535 */     return toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
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
/*     */   public static JSONObject toJSONObject(Reader reader, XMLParserConfiguration config) throws JSONException {
/* 558 */     JSONObject jo = new JSONObject();
/* 559 */     XMLTokener x = new XMLTokener(reader);
/* 560 */     while (x.more()) {
/* 561 */       x.skipPast("<");
/* 562 */       if (x.more()) {
/* 563 */         parse(x, jo, null, config);
/*     */       }
/*     */     } 
/* 566 */     return jo;
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
/*     */   public static JSONObject toJSONObject(String string, boolean keepStrings) throws JSONException {
/* 591 */     return toJSONObject(new StringReader(string), keepStrings);
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
/*     */   public static JSONObject toJSONObject(String string, XMLParserConfiguration config) throws JSONException {
/* 615 */     return toJSONObject(new StringReader(string), config);
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
/*     */   public static String toString(Object object) throws JSONException {
/* 627 */     return toString(object, null, XMLParserConfiguration.ORIGINAL);
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
/*     */   public static String toString(Object object, String tagName) {
/* 641 */     return toString(object, tagName, XMLParserConfiguration.ORIGINAL);
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
/*     */   public static String toString(Object object, String tagName, XMLParserConfiguration config) throws JSONException {
/* 658 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 663 */     if (object instanceof JSONObject) {
/*     */ 
/*     */       
/* 666 */       if (tagName != null) {
/* 667 */         sb.append('<');
/* 668 */         sb.append(tagName);
/* 669 */         sb.append('>');
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 674 */       JSONObject jo = (JSONObject)object;
/* 675 */       for (String key : jo.keySet()) {
/* 676 */         Object value = jo.opt(key);
/* 677 */         if (value == null) {
/* 678 */           value = "";
/* 679 */         } else if (value.getClass().isArray()) {
/* 680 */           value = new JSONArray(value);
/*     */         } 
/*     */ 
/*     */         
/* 684 */         if (key.equals(config.cDataTagName)) {
/* 685 */           if (value instanceof JSONArray) {
/* 686 */             JSONArray ja = (JSONArray)value;
/* 687 */             int jaLength = ja.length();
/*     */             
/* 689 */             for (int i = 0; i < jaLength; i++) {
/* 690 */               if (i > 0) {
/* 691 */                 sb.append('\n');
/*     */               }
/* 693 */               Object val = ja.opt(i);
/* 694 */               sb.append(escape(val.toString()));
/*     */             }  continue;
/*     */           } 
/* 697 */           sb.append(escape(value.toString()));
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 702 */         if (value instanceof JSONArray) {
/* 703 */           JSONArray ja = (JSONArray)value;
/* 704 */           int jaLength = ja.length();
/*     */           
/* 706 */           for (int i = 0; i < jaLength; i++) {
/* 707 */             Object val = ja.opt(i);
/* 708 */             if (val instanceof JSONArray) {
/* 709 */               sb.append('<');
/* 710 */               sb.append(key);
/* 711 */               sb.append('>');
/* 712 */               sb.append(toString(val, null, config));
/* 713 */               sb.append("</");
/* 714 */               sb.append(key);
/* 715 */               sb.append('>');
/*     */             } else {
/* 717 */               sb.append(toString(val, key, config));
/*     */             } 
/*     */           }  continue;
/* 720 */         }  if ("".equals(value)) {
/* 721 */           sb.append('<');
/* 722 */           sb.append(key);
/* 723 */           sb.append("/>");
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 728 */         sb.append(toString(value, key, config));
/*     */       } 
/*     */       
/* 731 */       if (tagName != null) {
/*     */ 
/*     */         
/* 734 */         sb.append("</");
/* 735 */         sb.append(tagName);
/* 736 */         sb.append('>');
/*     */       } 
/* 738 */       return sb.toString();
/*     */     } 
/*     */ 
/*     */     
/* 742 */     if (object != null && (object instanceof JSONArray || object.getClass().isArray())) {
/* 743 */       JSONArray ja; if (object.getClass().isArray()) {
/* 744 */         ja = new JSONArray(object);
/*     */       } else {
/* 746 */         ja = (JSONArray)object;
/*     */       } 
/* 748 */       int jaLength = ja.length();
/*     */       
/* 750 */       for (int i = 0; i < jaLength; i++) {
/* 751 */         Object val = ja.opt(i);
/*     */ 
/*     */ 
/*     */         
/* 755 */         sb.append(toString(val, (tagName == null) ? "array" : tagName, config));
/*     */       } 
/* 757 */       return sb.toString();
/*     */     } 
/*     */     
/* 760 */     String string = (object == null) ? "null" : escape(object.toString());
/* 761 */     return (tagName == null) ? ("\"" + string + "\"") : (
/* 762 */       (string.length() == 0) ? ("<" + tagName + "/>") : ("<" + tagName + 
/* 763 */       ">" + string + "</" + tagName + ">"));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\XML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */