/*      */ package org.json;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Array;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JSONArray
/*      */   implements Iterable<Object>
/*      */ {
/*      */   private final ArrayList<Object> myArrayList;
/*      */   
/*      */   public JSONArray() {
/*   95 */     this.myArrayList = new ArrayList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(JSONTokener x) throws JSONException {
/*  107 */     this();
/*  108 */     if (x.nextClean() != '[') {
/*  109 */       throw x.syntaxError("A JSONArray text must start with '['");
/*      */     }
/*      */     
/*  112 */     char nextChar = x.nextClean();
/*  113 */     if (nextChar == '\000')
/*      */     {
/*  115 */       throw x.syntaxError("Expected a ',' or ']'");
/*      */     }
/*  117 */     if (nextChar != ']') {
/*  118 */       x.back();
/*      */       while (true) {
/*  120 */         if (x.nextClean() == ',') {
/*  121 */           x.back();
/*  122 */           this.myArrayList.add(JSONObject.NULL);
/*      */         } else {
/*  124 */           x.back();
/*  125 */           this.myArrayList.add(x.nextValue());
/*      */         } 
/*  127 */         switch (x.nextClean()) {
/*      */           
/*      */           case '\000':
/*  130 */             throw x.syntaxError("Expected a ',' or ']'");
/*      */           case ',':
/*  132 */             nextChar = x.nextClean();
/*  133 */             if (nextChar == '\000')
/*      */             {
/*  135 */               throw x.syntaxError("Expected a ',' or ']'");
/*      */             }
/*  137 */             if (nextChar == ']') {
/*      */               return;
/*      */             }
/*  140 */             x.back(); continue;
/*      */           case ']':
/*      */             return;
/*      */         }  break;
/*      */       } 
/*  145 */       throw x.syntaxError("Expected a ',' or ']'");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(String source) throws JSONException {
/*  162 */     this(new JSONTokener(source));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(Collection<?> collection) {
/*  172 */     if (collection == null) {
/*  173 */       this.myArrayList = new ArrayList();
/*      */     } else {
/*  175 */       this.myArrayList = new ArrayList(collection.size());
/*  176 */       for (Object o : collection) {
/*  177 */         this.myArrayList.add(JSONObject.wrap(o));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(Object array) throws JSONException {
/*  195 */     this();
/*  196 */     if (array.getClass().isArray()) {
/*  197 */       int length = Array.getLength(array);
/*  198 */       this.myArrayList.ensureCapacity(length);
/*  199 */       for (int i = 0; i < length; i++) {
/*  200 */         put(JSONObject.wrap(Array.get(array, i)));
/*      */       }
/*      */     } else {
/*  203 */       throw new JSONException(
/*  204 */           "JSONArray initial value should be a string or collection or array.");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator<Object> iterator() {
/*  210 */     return this.myArrayList.iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(int index) throws JSONException {
/*  223 */     Object object = opt(index);
/*  224 */     if (object == null) {
/*  225 */       throw new JSONException("JSONArray[" + index + "] not found.");
/*      */     }
/*  227 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int index) throws JSONException {
/*  242 */     Object object = get(index);
/*  243 */     if (object.equals(Boolean.FALSE) || (
/*  244 */       object instanceof String && ((String)object)
/*  245 */       .equalsIgnoreCase("false")))
/*  246 */       return false; 
/*  247 */     if (object.equals(Boolean.TRUE) || (
/*  248 */       object instanceof String && ((String)object)
/*  249 */       .equalsIgnoreCase("true"))) {
/*  250 */       return true;
/*      */     }
/*  252 */     throw wrongValueFormatException(index, "boolean", null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble(int index) throws JSONException {
/*  266 */     Object object = get(index);
/*  267 */     if (object instanceof Number) {
/*  268 */       return ((Number)object).doubleValue();
/*      */     }
/*      */     try {
/*  271 */       return Double.parseDouble(object.toString());
/*  272 */     } catch (Exception e) {
/*  273 */       throw wrongValueFormatException(index, "double", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloat(int index) throws JSONException {
/*  288 */     Object object = get(index);
/*  289 */     if (object instanceof Number) {
/*  290 */       return ((Float)object).floatValue();
/*      */     }
/*      */     try {
/*  293 */       return Float.parseFloat(object.toString());
/*  294 */     } catch (Exception e) {
/*  295 */       throw wrongValueFormatException(index, "float", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number getNumber(int index) throws JSONException {
/*  310 */     Object object = get(index);
/*      */     try {
/*  312 */       if (object instanceof Number) {
/*  313 */         return (Number)object;
/*      */       }
/*  315 */       return JSONObject.stringToNumber(object.toString());
/*  316 */     } catch (Exception e) {
/*  317 */       throw wrongValueFormatException(index, "number", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E getEnum(Class<E> clazz, int index) throws JSONException {
/*  336 */     E val = optEnum(clazz, index);
/*  337 */     if (val == null)
/*      */     {
/*      */ 
/*      */       
/*  341 */       throw wrongValueFormatException(index, "enum of type " + 
/*  342 */           JSONObject.quote(clazz.getSimpleName()), null);
/*      */     }
/*  344 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(int index) throws JSONException {
/*  361 */     Object object = get(index);
/*  362 */     BigDecimal val = JSONObject.objectToBigDecimal(object, null);
/*  363 */     if (val == null) {
/*  364 */       throw wrongValueFormatException(index, "BigDecimal", object, null);
/*      */     }
/*  366 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger getBigInteger(int index) throws JSONException {
/*  380 */     Object object = get(index);
/*  381 */     BigInteger val = JSONObject.objectToBigInteger(object, null);
/*  382 */     if (val == null) {
/*  383 */       throw wrongValueFormatException(index, "BigInteger", object, null);
/*      */     }
/*  385 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(int index) throws JSONException {
/*  398 */     Object object = get(index);
/*  399 */     if (object instanceof Number) {
/*  400 */       return ((Number)object).intValue();
/*      */     }
/*      */     try {
/*  403 */       return Integer.parseInt(object.toString());
/*  404 */     } catch (Exception e) {
/*  405 */       throw wrongValueFormatException(index, "int", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray getJSONArray(int index) throws JSONException {
/*  420 */     Object object = get(index);
/*  421 */     if (object instanceof JSONArray) {
/*  422 */       return (JSONArray)object;
/*      */     }
/*  424 */     throw wrongValueFormatException(index, "JSONArray", null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject getJSONObject(int index) throws JSONException {
/*  438 */     Object object = get(index);
/*  439 */     if (object instanceof JSONObject) {
/*  440 */       return (JSONObject)object;
/*      */     }
/*  442 */     throw wrongValueFormatException(index, "JSONObject", null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong(int index) throws JSONException {
/*  456 */     Object object = get(index);
/*  457 */     if (object instanceof Number) {
/*  458 */       return ((Number)object).longValue();
/*      */     }
/*      */     try {
/*  461 */       return Long.parseLong(object.toString());
/*  462 */     } catch (Exception e) {
/*  463 */       throw wrongValueFormatException(index, "long", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(int index) throws JSONException {
/*  477 */     Object object = get(index);
/*  478 */     if (object instanceof String) {
/*  479 */       return (String)object;
/*      */     }
/*  481 */     throw wrongValueFormatException(index, "String", null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNull(int index) {
/*  492 */     return JSONObject.NULL.equals(opt(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String join(String separator) throws JSONException {
/*  507 */     int len = length();
/*  508 */     if (len == 0) {
/*  509 */       return "";
/*      */     }
/*      */     
/*  512 */     StringBuilder sb = new StringBuilder(
/*  513 */         JSONObject.valueToString(this.myArrayList.get(0)));
/*      */     
/*  515 */     for (int i = 1; i < len; i++) {
/*  516 */       sb.append(separator)
/*  517 */         .append(JSONObject.valueToString(this.myArrayList.get(i)));
/*      */     }
/*  519 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  528 */     return this.myArrayList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object opt(int index) {
/*  539 */     return (index < 0 || index >= length()) ? null : this.myArrayList
/*  540 */       .get(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean optBoolean(int index) {
/*  553 */     return optBoolean(index, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean optBoolean(int index, boolean defaultValue) {
/*      */     try {
/*  569 */       return getBoolean(index);
/*  570 */     } catch (Exception e) {
/*  571 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double optDouble(int index) {
/*  585 */     return optDouble(index, Double.NaN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double optDouble(int index, double defaultValue) {
/*  600 */     Number val = optNumber(index, null);
/*  601 */     if (val == null) {
/*  602 */       return defaultValue;
/*      */     }
/*  604 */     double doubleValue = val.doubleValue();
/*      */ 
/*      */ 
/*      */     
/*  608 */     return doubleValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float optFloat(int index) {
/*  621 */     return optFloat(index, Float.NaN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float optFloat(int index, float defaultValue) {
/*  636 */     Number val = optNumber(index, null);
/*  637 */     if (val == null) {
/*  638 */       return defaultValue;
/*      */     }
/*  640 */     float floatValue = val.floatValue();
/*      */ 
/*      */ 
/*      */     
/*  644 */     return floatValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int optInt(int index) {
/*  657 */     return optInt(index, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int optInt(int index, int defaultValue) {
/*  672 */     Number val = optNumber(index, null);
/*  673 */     if (val == null) {
/*  674 */       return defaultValue;
/*      */     }
/*  676 */     return val.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, int index) {
/*  691 */     return optEnum(clazz, index, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, int index, E defaultValue) {
/*      */     try {
/*  710 */       Object val = opt(index);
/*  711 */       if (JSONObject.NULL.equals(val)) {
/*  712 */         return defaultValue;
/*      */       }
/*  714 */       if (clazz.isAssignableFrom(val.getClass()))
/*      */       {
/*      */         
/*  717 */         return (E)val;
/*      */       }
/*      */       
/*  720 */       return Enum.valueOf(clazz, val.toString());
/*  721 */     } catch (IllegalArgumentException e) {
/*  722 */       return defaultValue;
/*  723 */     } catch (NullPointerException e) {
/*  724 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger optBigInteger(int index, BigInteger defaultValue) {
/*  740 */     Object val = opt(index);
/*  741 */     return JSONObject.objectToBigInteger(val, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal optBigDecimal(int index, BigDecimal defaultValue) {
/*  759 */     Object val = opt(index);
/*  760 */     return JSONObject.objectToBigDecimal(val, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray optJSONArray(int index) {
/*  772 */     Object o = opt(index);
/*  773 */     return (o instanceof JSONArray) ? (JSONArray)o : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject optJSONObject(int index) {
/*  786 */     Object o = opt(index);
/*  787 */     return (o instanceof JSONObject) ? (JSONObject)o : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long optLong(int index) {
/*  800 */     return optLong(index, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long optLong(int index, long defaultValue) {
/*  815 */     Number val = optNumber(index, null);
/*  816 */     if (val == null) {
/*  817 */       return defaultValue;
/*      */     }
/*  819 */     return val.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number optNumber(int index) {
/*  833 */     return optNumber(index, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number optNumber(int index, Number defaultValue) {
/*  849 */     Object val = opt(index);
/*  850 */     if (JSONObject.NULL.equals(val)) {
/*  851 */       return defaultValue;
/*      */     }
/*  853 */     if (val instanceof Number) {
/*  854 */       return (Number)val;
/*      */     }
/*      */     
/*  857 */     if (val instanceof String) {
/*      */       try {
/*  859 */         return JSONObject.stringToNumber((String)val);
/*  860 */       } catch (Exception e) {
/*  861 */         return defaultValue;
/*      */       } 
/*      */     }
/*  864 */     return defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String optString(int index) {
/*  877 */     return optString(index, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String optString(int index, String defaultValue) {
/*  891 */     Object object = opt(index);
/*  892 */     return JSONObject.NULL.equals(object) ? defaultValue : object
/*  893 */       .toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(boolean value) {
/*  904 */     return put(value ? Boolean.TRUE : Boolean.FALSE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(Collection<?> value) {
/*  918 */     return put(new JSONArray(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(double value) throws JSONException {
/*  931 */     return put(Double.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(float value) throws JSONException {
/*  944 */     return put(Float.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int value) {
/*  955 */     return put(Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(long value) {
/*  966 */     return put(Long.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(Map<?, ?> value) {
/*  982 */     return put(new JSONObject(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(Object value) {
/*  997 */     JSONObject.testValidity(value);
/*  998 */     this.myArrayList.add(value);
/*  999 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, boolean value) throws JSONException {
/* 1016 */     return put(index, value ? Boolean.TRUE : Boolean.FALSE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, Collection<?> value) throws JSONException {
/* 1032 */     return put(index, new JSONArray(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, double value) throws JSONException {
/* 1049 */     return put(index, Double.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, float value) throws JSONException {
/* 1066 */     return put(index, Float.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, int value) throws JSONException {
/* 1083 */     return put(index, Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, long value) throws JSONException {
/* 1100 */     return put(index, Long.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, Map<?, ?> value) throws JSONException {
/* 1119 */     put(index, new JSONObject(value));
/* 1120 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int index, Object value) throws JSONException {
/* 1140 */     if (index < 0) {
/* 1141 */       throw new JSONException("JSONArray[" + index + "] not found.");
/*      */     }
/* 1143 */     if (index < length()) {
/* 1144 */       JSONObject.testValidity(value);
/* 1145 */       this.myArrayList.set(index, value);
/* 1146 */       return this;
/*      */     } 
/* 1148 */     if (index == length())
/*      */     {
/* 1150 */       return put(value);
/*      */     }
/*      */ 
/*      */     
/* 1154 */     this.myArrayList.ensureCapacity(index + 1);
/* 1155 */     while (index != length())
/*      */     {
/* 1157 */       this.myArrayList.add(JSONObject.NULL);
/*      */     }
/* 1159 */     return put(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object query(String jsonPointer) {
/* 1182 */     return query(new JSONPointer(jsonPointer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object query(JSONPointer jsonPointer) {
/* 1205 */     return jsonPointer.queryFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object optQuery(String jsonPointer) {
/* 1217 */     return optQuery(new JSONPointer(jsonPointer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object optQuery(JSONPointer jsonPointer) {
/*      */     try {
/* 1230 */       return jsonPointer.queryFrom(this);
/* 1231 */     } catch (JSONPointerException e) {
/* 1232 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object remove(int index) {
/* 1245 */     return (index >= 0 && index < length()) ? 
/* 1246 */       this.myArrayList.remove(index) : 
/* 1247 */       null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean similar(Object other) {
/* 1258 */     if (!(other instanceof JSONArray)) {
/* 1259 */       return false;
/*      */     }
/* 1261 */     int len = length();
/* 1262 */     if (len != ((JSONArray)other).length()) {
/* 1263 */       return false;
/*      */     }
/* 1265 */     for (int i = 0; i < len; i++) {
/* 1266 */       Object valueThis = this.myArrayList.get(i);
/* 1267 */       Object valueOther = ((JSONArray)other).myArrayList.get(i);
/* 1268 */       if (valueThis != valueOther) {
/*      */ 
/*      */         
/* 1271 */         if (valueThis == null) {
/* 1272 */           return false;
/*      */         }
/* 1274 */         if (valueThis instanceof JSONObject) {
/* 1275 */           if (!((JSONObject)valueThis).similar(valueOther)) {
/* 1276 */             return false;
/*      */           }
/* 1278 */         } else if (valueThis instanceof JSONArray) {
/* 1279 */           if (!((JSONArray)valueThis).similar(valueOther)) {
/* 1280 */             return false;
/*      */           }
/* 1282 */         } else if (!valueThis.equals(valueOther)) {
/* 1283 */           return false;
/*      */         } 
/*      */       } 
/* 1286 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject toJSONObject(JSONArray names) throws JSONException {
/* 1302 */     if (names == null || names.isEmpty() || isEmpty()) {
/* 1303 */       return null;
/*      */     }
/* 1305 */     JSONObject jo = new JSONObject(names.length());
/* 1306 */     for (int i = 0; i < names.length(); i++) {
/* 1307 */       jo.put(names.getString(i), opt(i));
/*      */     }
/* 1309 */     return jo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     try {
/* 1327 */       return toString(0);
/* 1328 */     } catch (Exception e) {
/* 1329 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString(int indentFactor) throws JSONException {
/* 1361 */     StringWriter sw = new StringWriter();
/* 1362 */     synchronized (sw.getBuffer()) {
/* 1363 */       return write(sw, indentFactor, 0).toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer write(Writer writer) throws JSONException {
/* 1378 */     return write(writer, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
/*      */     try {
/* 1412 */       boolean needsComma = false;
/* 1413 */       int length = length();
/* 1414 */       writer.write(91);
/*      */       
/* 1416 */       if (length == 1) {
/*      */         try {
/* 1418 */           JSONObject.writeValue(writer, this.myArrayList.get(0), 
/* 1419 */               indentFactor, indent);
/* 1420 */         } catch (Exception e) {
/* 1421 */           throw new JSONException("Unable to write JSONArray value at index: 0", e);
/*      */         } 
/* 1423 */       } else if (length != 0) {
/* 1424 */         int newIndent = indent + indentFactor;
/*      */         
/* 1426 */         for (int i = 0; i < length; i++) {
/* 1427 */           if (needsComma) {
/* 1428 */             writer.write(44);
/*      */           }
/* 1430 */           if (indentFactor > 0) {
/* 1431 */             writer.write(10);
/*      */           }
/* 1433 */           JSONObject.indent(writer, newIndent);
/*      */           try {
/* 1435 */             JSONObject.writeValue(writer, this.myArrayList.get(i), 
/* 1436 */                 indentFactor, newIndent);
/* 1437 */           } catch (Exception e) {
/* 1438 */             throw new JSONException("Unable to write JSONArray value at index: " + i, e);
/*      */           } 
/* 1440 */           needsComma = true;
/*      */         } 
/* 1442 */         if (indentFactor > 0) {
/* 1443 */           writer.write(10);
/*      */         }
/* 1445 */         JSONObject.indent(writer, indent);
/*      */       } 
/* 1447 */       writer.write(93);
/* 1448 */       return writer;
/* 1449 */     } catch (IOException e) {
/* 1450 */       throw new JSONException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<Object> toList() {
/* 1464 */     List<Object> results = new ArrayList(this.myArrayList.size());
/* 1465 */     for (Object element : this.myArrayList) {
/* 1466 */       if (element == null || JSONObject.NULL.equals(element)) {
/* 1467 */         results.add(null); continue;
/* 1468 */       }  if (element instanceof JSONArray) {
/* 1469 */         results.add(((JSONArray)element).toList()); continue;
/* 1470 */       }  if (element instanceof JSONObject) {
/* 1471 */         results.add(((JSONObject)element).toMap()); continue;
/*      */       } 
/* 1473 */       results.add(element);
/*      */     } 
/*      */     
/* 1476 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 1485 */     return this.myArrayList.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static JSONException wrongValueFormatException(int idx, String valueType, Throwable cause) {
/* 1499 */     return new JSONException(
/* 1500 */         "JSONArray[" + idx + "] is not a " + valueType + ".", 
/* 1501 */         cause);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static JSONException wrongValueFormatException(int idx, String valueType, Object value, Throwable cause) {
/* 1516 */     return new JSONException(
/* 1517 */         "JSONArray[" + idx + "] is not a " + valueType + " (" + value + ").", 
/* 1518 */         cause);
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\JSONArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */