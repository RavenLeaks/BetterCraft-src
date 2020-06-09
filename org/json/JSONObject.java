/*      */ package org.json;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JSONObject
/*      */ {
/*      */   private static final class Null
/*      */   {
/*      */     private Null() {}
/*      */     
/*      */     protected final Object clone() {
/*  119 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object object) {
/*  132 */       return !(object != null && object != this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  141 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  151 */       return "null";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  159 */   static final Pattern NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Map<String, Object> map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  172 */   public static final Object NULL = new Null(null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject() {
/*  184 */     this.map = new HashMap<>();
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
/*      */   public JSONObject(JSONObject jo, String... names) {
/*  198 */     this(names.length);
/*  199 */     for (int i = 0; i < names.length; i++) {
/*      */       try {
/*  201 */         putOnce(names[i], jo.opt(names[i]));
/*  202 */       } catch (Exception exception) {}
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
/*      */   public JSONObject(JSONTokener x) throws JSONException {
/*  217 */     this();
/*      */ 
/*      */ 
/*      */     
/*  221 */     if (x.nextClean() != '{') {
/*  222 */       throw x.syntaxError("A JSONObject text must begin with '{'");
/*      */     }
/*      */     while (true) {
/*  225 */       char c = x.nextClean();
/*  226 */       switch (c) {
/*      */         case '\000':
/*  228 */           throw x.syntaxError("A JSONObject text must end with '}'");
/*      */         case '}':
/*      */           return;
/*      */       } 
/*  232 */       x.back();
/*  233 */       String key = x.nextValue().toString();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  238 */       c = x.nextClean();
/*  239 */       if (c != ':') {
/*  240 */         throw x.syntaxError("Expected a ':' after a key");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  245 */       if (key != null) {
/*      */         
/*  247 */         if (opt(key) != null)
/*      */         {
/*  249 */           throw x.syntaxError("Duplicate key \"" + key + "\"");
/*      */         }
/*      */         
/*  252 */         Object value = x.nextValue();
/*  253 */         if (value != null) {
/*  254 */           put(key, value);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  260 */       switch (x.nextClean()) {
/*      */         case ',':
/*      */         case ';':
/*  263 */           if (x.nextClean() == '}') {
/*      */             return;
/*      */           }
/*  266 */           x.back(); continue;
/*      */         case '}':
/*      */           return;
/*      */       }  break;
/*      */     } 
/*  271 */     throw x.syntaxError("Expected a ',' or '}'");
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
/*      */   public JSONObject(Map<?, ?> m) {
/*  288 */     if (m == null) {
/*  289 */       this.map = new HashMap<>();
/*      */     } else {
/*  291 */       this.map = new HashMap<>(m.size());
/*  292 */       for (Map.Entry<?, ?> e : m.entrySet()) {
/*  293 */         if (e.getKey() == null) {
/*  294 */           throw new NullPointerException("Null key.");
/*      */         }
/*  296 */         Object value = e.getValue();
/*  297 */         if (value != null) {
/*  298 */           this.map.put(String.valueOf(e.getKey()), wrap(value));
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(Object bean) {
/*  363 */     this();
/*  364 */     populateMap(bean);
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
/*      */   public JSONObject(Object object, String... names) {
/*  382 */     this(names.length);
/*  383 */     Class<?> c = object.getClass();
/*  384 */     for (int i = 0; i < names.length; i++) {
/*  385 */       String name = names[i];
/*      */       try {
/*  387 */         putOpt(name, c.getField(name).get(object));
/*  388 */       } catch (Exception exception) {}
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
/*      */   public JSONObject(String source) throws JSONException {
/*  406 */     this(new JSONTokener(source));
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
/*      */   public JSONObject(String baseName, Locale locale) throws JSONException {
/*  420 */     this();
/*  421 */     ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, 
/*  422 */         Thread.currentThread().getContextClassLoader());
/*      */ 
/*      */ 
/*      */     
/*  426 */     Enumeration<String> keys = bundle.getKeys();
/*  427 */     while (keys.hasMoreElements()) {
/*  428 */       Object key = keys.nextElement();
/*  429 */       if (key != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  435 */         String[] path = ((String)key).split("\\.");
/*  436 */         int last = path.length - 1;
/*  437 */         JSONObject target = this;
/*  438 */         for (int i = 0; i < last; i++) {
/*  439 */           String segment = path[i];
/*  440 */           JSONObject nextTarget = target.optJSONObject(segment);
/*  441 */           if (nextTarget == null) {
/*  442 */             nextTarget = new JSONObject();
/*  443 */             target.put(segment, nextTarget);
/*      */           } 
/*  445 */           target = nextTarget;
/*      */         } 
/*  447 */         target.put(path[last], bundle.getString((String)key));
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
/*      */   protected JSONObject(int initialCapacity) {
/*  460 */     this.map = new HashMap<>(initialCapacity);
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
/*      */   public JSONObject accumulate(String key, Object value) throws JSONException {
/*  485 */     testValidity(value);
/*  486 */     Object object = opt(key);
/*  487 */     if (object == null) {
/*  488 */       put(key, 
/*  489 */           (value instanceof JSONArray) ? (new JSONArray()).put(value) : 
/*  490 */           value);
/*  491 */     } else if (object instanceof JSONArray) {
/*  492 */       ((JSONArray)object).put(value);
/*      */     } else {
/*  494 */       put(key, (new JSONArray()).put(object).put(value));
/*      */     } 
/*  496 */     return this;
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
/*      */   public JSONObject append(String key, Object value) throws JSONException {
/*  517 */     testValidity(value);
/*  518 */     Object object = opt(key);
/*  519 */     if (object == null) {
/*  520 */       put(key, (new JSONArray()).put(value));
/*  521 */     } else if (object instanceof JSONArray) {
/*  522 */       put(key, ((JSONArray)object).put(value));
/*      */     } else {
/*  524 */       throw wrongValueFormatException(key, "JSONArray", null, null);
/*      */     } 
/*  526 */     return this;
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
/*      */   public static String doubleToString(double d) {
/*  538 */     if (Double.isInfinite(d) || Double.isNaN(d)) {
/*  539 */       return "null";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  544 */     String string = Double.toString(d);
/*  545 */     if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && 
/*  546 */       string.indexOf('E') < 0) {
/*  547 */       while (string.endsWith("0")) {
/*  548 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*  550 */       if (string.endsWith(".")) {
/*  551 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*      */     } 
/*  554 */     return string;
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
/*      */   public Object get(String key) throws JSONException {
/*  567 */     if (key == null) {
/*  568 */       throw new JSONException("Null key.");
/*      */     }
/*  570 */     Object object = opt(key);
/*  571 */     if (object == null) {
/*  572 */       throw new JSONException("JSONObject[" + quote(key) + "] not found.");
/*      */     }
/*  574 */     return object;
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
/*      */   public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) throws JSONException {
/*  592 */     E val = optEnum(clazz, key);
/*  593 */     if (val == null)
/*      */     {
/*      */ 
/*      */       
/*  597 */       throw wrongValueFormatException(key, "enum of type " + quote(clazz.getSimpleName()), null);
/*      */     }
/*  599 */     return val;
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
/*      */   public boolean getBoolean(String key) throws JSONException {
/*  613 */     Object object = get(key);
/*  614 */     if (object.equals(Boolean.FALSE) || (
/*  615 */       object instanceof String && ((String)object)
/*  616 */       .equalsIgnoreCase("false")))
/*  617 */       return false; 
/*  618 */     if (object.equals(Boolean.TRUE) || (
/*  619 */       object instanceof String && ((String)object)
/*  620 */       .equalsIgnoreCase("true"))) {
/*  621 */       return true;
/*      */     }
/*  623 */     throw wrongValueFormatException(key, "Boolean", null);
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
/*      */   public BigInteger getBigInteger(String key) throws JSONException {
/*  637 */     Object object = get(key);
/*  638 */     BigInteger ret = objectToBigInteger(object, null);
/*  639 */     if (ret != null) {
/*  640 */       return ret;
/*      */     }
/*  642 */     throw wrongValueFormatException(key, "BigInteger", object, null);
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
/*      */   public BigDecimal getBigDecimal(String key) throws JSONException {
/*  659 */     Object object = get(key);
/*  660 */     BigDecimal ret = objectToBigDecimal(object, null);
/*  661 */     if (ret != null) {
/*  662 */       return ret;
/*      */     }
/*  664 */     throw wrongValueFormatException(key, "BigDecimal", object, null);
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
/*      */   public double getDouble(String key) throws JSONException {
/*  678 */     Object object = get(key);
/*  679 */     if (object instanceof Number) {
/*  680 */       return ((Number)object).doubleValue();
/*      */     }
/*      */     try {
/*  683 */       return Double.parseDouble(object.toString());
/*  684 */     } catch (Exception e) {
/*  685 */       throw wrongValueFormatException(key, "double", e);
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
/*      */   public float getFloat(String key) throws JSONException {
/*  700 */     Object object = get(key);
/*  701 */     if (object instanceof Number) {
/*  702 */       return ((Number)object).floatValue();
/*      */     }
/*      */     try {
/*  705 */       return Float.parseFloat(object.toString());
/*  706 */     } catch (Exception e) {
/*  707 */       throw wrongValueFormatException(key, "float", e);
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
/*      */   public Number getNumber(String key) throws JSONException {
/*  722 */     Object object = get(key);
/*      */     try {
/*  724 */       if (object instanceof Number) {
/*  725 */         return (Number)object;
/*      */       }
/*  727 */       return stringToNumber(object.toString());
/*  728 */     } catch (Exception e) {
/*  729 */       throw wrongValueFormatException(key, "number", e);
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
/*      */   public int getInt(String key) throws JSONException {
/*  744 */     Object object = get(key);
/*  745 */     if (object instanceof Number) {
/*  746 */       return ((Number)object).intValue();
/*      */     }
/*      */     try {
/*  749 */       return Integer.parseInt(object.toString());
/*  750 */     } catch (Exception e) {
/*  751 */       throw wrongValueFormatException(key, "int", e);
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
/*      */   public JSONArray getJSONArray(String key) throws JSONException {
/*  765 */     Object object = get(key);
/*  766 */     if (object instanceof JSONArray) {
/*  767 */       return (JSONArray)object;
/*      */     }
/*  769 */     throw wrongValueFormatException(key, "JSONArray", null);
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
/*      */   public JSONObject getJSONObject(String key) throws JSONException {
/*  782 */     Object object = get(key);
/*  783 */     if (object instanceof JSONObject) {
/*  784 */       return (JSONObject)object;
/*      */     }
/*  786 */     throw wrongValueFormatException(key, "JSONObject", null);
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
/*      */   public long getLong(String key) throws JSONException {
/*  800 */     Object object = get(key);
/*  801 */     if (object instanceof Number) {
/*  802 */       return ((Number)object).longValue();
/*      */     }
/*      */     try {
/*  805 */       return Long.parseLong(object.toString());
/*  806 */     } catch (Exception e) {
/*  807 */       throw wrongValueFormatException(key, "long", e);
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
/*      */   public static String[] getNames(JSONObject jo) {
/*  819 */     if (jo.isEmpty()) {
/*  820 */       return null;
/*      */     }
/*  822 */     return jo.keySet().<String>toArray(new String[jo.length()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getNames(Object object) {
/*  833 */     if (object == null) {
/*  834 */       return null;
/*      */     }
/*  836 */     Class<?> klass = object.getClass();
/*  837 */     Field[] fields = klass.getFields();
/*  838 */     int length = fields.length;
/*  839 */     if (length == 0) {
/*  840 */       return null;
/*      */     }
/*  842 */     String[] names = new String[length];
/*  843 */     for (int i = 0; i < length; i++) {
/*  844 */       names[i] = fields[i].getName();
/*      */     }
/*  846 */     return names;
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
/*      */   public String getString(String key) throws JSONException {
/*  859 */     Object object = get(key);
/*  860 */     if (object instanceof String) {
/*  861 */       return (String)object;
/*      */     }
/*  863 */     throw wrongValueFormatException(key, "string", null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean has(String key) {
/*  874 */     return this.map.containsKey(key);
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
/*      */   public JSONObject increment(String key) throws JSONException {
/*  893 */     Object value = opt(key);
/*  894 */     if (value == null) {
/*  895 */       put(key, 1);
/*  896 */     } else if (value instanceof Integer) {
/*  897 */       put(key, ((Integer)value).intValue() + 1);
/*  898 */     } else if (value instanceof Long) {
/*  899 */       put(key, ((Long)value).longValue() + 1L);
/*  900 */     } else if (value instanceof BigInteger) {
/*  901 */       put(key, ((BigInteger)value).add(BigInteger.ONE));
/*  902 */     } else if (value instanceof Float) {
/*  903 */       put(key, ((Float)value).floatValue() + 1.0F);
/*  904 */     } else if (value instanceof Double) {
/*  905 */       put(key, ((Double)value).doubleValue() + 1.0D);
/*  906 */     } else if (value instanceof BigDecimal) {
/*  907 */       put(key, ((BigDecimal)value).add(BigDecimal.ONE));
/*      */     } else {
/*  909 */       throw new JSONException("Unable to increment [" + quote(key) + "].");
/*      */     } 
/*  911 */     return this;
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
/*      */   public boolean isNull(String key) {
/*  924 */     return NULL.equals(opt(key));
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
/*      */   public Iterator<String> keys() {
/*  936 */     return keySet().iterator();
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
/*      */   public Set<String> keySet() {
/*  948 */     return this.map.keySet();
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
/*      */   protected Set<Map.Entry<String, Object>> entrySet() {
/*  964 */     return this.map.entrySet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  973 */     return this.map.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  982 */     return this.map.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray names() {
/*  993 */     if (this.map.isEmpty()) {
/*  994 */       return null;
/*      */     }
/*  996 */     return new JSONArray(this.map.keySet());
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
/*      */   public static String numberToString(Number number) throws JSONException {
/* 1009 */     if (number == null) {
/* 1010 */       throw new JSONException("Null pointer");
/*      */     }
/* 1012 */     testValidity(number);
/*      */ 
/*      */ 
/*      */     
/* 1016 */     String string = number.toString();
/* 1017 */     if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && 
/* 1018 */       string.indexOf('E') < 0) {
/* 1019 */       while (string.endsWith("0")) {
/* 1020 */         string = string.substring(0, string.length() - 1);
/*      */       }
/* 1022 */       if (string.endsWith(".")) {
/* 1023 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*      */     } 
/* 1026 */     return string;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object opt(String key) {
/* 1037 */     return (key == null) ? null : this.map.get(key);
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
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, String key) {
/* 1052 */     return optEnum(clazz, key, null);
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
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, String key, E defaultValue) {
/*      */     try {
/* 1071 */       Object val = opt(key);
/* 1072 */       if (NULL.equals(val)) {
/* 1073 */         return defaultValue;
/*      */       }
/* 1075 */       if (clazz.isAssignableFrom(val.getClass()))
/*      */       {
/*      */         
/* 1078 */         return (E)val;
/*      */       }
/*      */       
/* 1081 */       return Enum.valueOf(clazz, val.toString());
/* 1082 */     } catch (IllegalArgumentException e) {
/* 1083 */       return defaultValue;
/* 1084 */     } catch (NullPointerException e) {
/* 1085 */       return defaultValue;
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
/*      */   public boolean optBoolean(String key) {
/* 1098 */     return optBoolean(key, false);
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
/*      */   public boolean optBoolean(String key, boolean defaultValue) {
/* 1113 */     Object val = opt(key);
/* 1114 */     if (NULL.equals(val)) {
/* 1115 */       return defaultValue;
/*      */     }
/* 1117 */     if (val instanceof Boolean) {
/* 1118 */       return ((Boolean)val).booleanValue();
/*      */     }
/*      */     
/*      */     try {
/* 1122 */       return getBoolean(key);
/* 1123 */     } catch (Exception e) {
/* 1124 */       return defaultValue;
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
/*      */   public BigDecimal optBigDecimal(String key, BigDecimal defaultValue) {
/* 1143 */     Object val = opt(key);
/* 1144 */     return objectToBigDecimal(val, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigDecimal objectToBigDecimal(Object val, BigDecimal defaultValue) {
/* 1154 */     if (NULL.equals(val)) {
/* 1155 */       return defaultValue;
/*      */     }
/* 1157 */     if (val instanceof BigDecimal) {
/* 1158 */       return (BigDecimal)val;
/*      */     }
/* 1160 */     if (val instanceof BigInteger) {
/* 1161 */       return new BigDecimal((BigInteger)val);
/*      */     }
/* 1163 */     if (val instanceof Double || val instanceof Float) {
/* 1164 */       double d = ((Number)val).doubleValue();
/* 1165 */       if (Double.isNaN(d)) {
/* 1166 */         return defaultValue;
/*      */       }
/* 1168 */       return new BigDecimal(((Number)val).doubleValue());
/*      */     } 
/* 1170 */     if (val instanceof Long || val instanceof Integer || 
/* 1171 */       val instanceof Short || val instanceof Byte) {
/* 1172 */       return new BigDecimal(((Number)val).longValue());
/*      */     }
/*      */     
/*      */     try {
/* 1176 */       return new BigDecimal(val.toString());
/* 1177 */     } catch (Exception e) {
/* 1178 */       return defaultValue;
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
/*      */   public BigInteger optBigInteger(String key, BigInteger defaultValue) {
/* 1194 */     Object val = opt(key);
/* 1195 */     return objectToBigInteger(val, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigInteger objectToBigInteger(Object val, BigInteger defaultValue) {
/* 1205 */     if (NULL.equals(val)) {
/* 1206 */       return defaultValue;
/*      */     }
/* 1208 */     if (val instanceof BigInteger) {
/* 1209 */       return (BigInteger)val;
/*      */     }
/* 1211 */     if (val instanceof BigDecimal) {
/* 1212 */       return ((BigDecimal)val).toBigInteger();
/*      */     }
/* 1214 */     if (val instanceof Double || val instanceof Float) {
/* 1215 */       double d = ((Number)val).doubleValue();
/* 1216 */       if (Double.isNaN(d)) {
/* 1217 */         return defaultValue;
/*      */       }
/* 1219 */       return (new BigDecimal(d)).toBigInteger();
/*      */     } 
/* 1221 */     if (val instanceof Long || val instanceof Integer || 
/* 1222 */       val instanceof Short || val instanceof Byte) {
/* 1223 */       return BigInteger.valueOf(((Number)val).longValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1232 */       String valStr = val.toString();
/* 1233 */       if (isDecimalNotation(valStr)) {
/* 1234 */         return (new BigDecimal(valStr)).toBigInteger();
/*      */       }
/* 1236 */       return new BigInteger(valStr);
/* 1237 */     } catch (Exception e) {
/* 1238 */       return defaultValue;
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
/*      */   public double optDouble(String key) {
/* 1252 */     return optDouble(key, Double.NaN);
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
/*      */   public double optDouble(String key, double defaultValue) {
/* 1267 */     Number val = optNumber(key);
/* 1268 */     if (val == null) {
/* 1269 */       return defaultValue;
/*      */     }
/* 1271 */     double doubleValue = val.doubleValue();
/*      */ 
/*      */ 
/*      */     
/* 1275 */     return doubleValue;
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
/*      */   public float optFloat(String key) {
/* 1288 */     return optFloat(key, Float.NaN);
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
/*      */   public float optFloat(String key, float defaultValue) {
/* 1303 */     Number val = optNumber(key);
/* 1304 */     if (val == null) {
/* 1305 */       return defaultValue;
/*      */     }
/* 1307 */     float floatValue = val.floatValue();
/*      */ 
/*      */ 
/*      */     
/* 1311 */     return floatValue;
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
/*      */   public int optInt(String key) {
/* 1324 */     return optInt(key, 0);
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
/*      */   public int optInt(String key, int defaultValue) {
/* 1339 */     Number val = optNumber(key, null);
/* 1340 */     if (val == null) {
/* 1341 */       return defaultValue;
/*      */     }
/* 1343 */     return val.intValue();
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
/*      */   public JSONArray optJSONArray(String key) {
/* 1355 */     Object o = opt(key);
/* 1356 */     return (o instanceof JSONArray) ? (JSONArray)o : null;
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
/*      */   public JSONObject optJSONObject(String key) {
/* 1368 */     Object object = opt(key);
/* 1369 */     return (object instanceof JSONObject) ? (JSONObject)object : null;
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
/*      */   public long optLong(String key) {
/* 1382 */     return optLong(key, 0L);
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
/*      */   public long optLong(String key, long defaultValue) {
/* 1397 */     Number val = optNumber(key, null);
/* 1398 */     if (val == null) {
/* 1399 */       return defaultValue;
/*      */     }
/*      */     
/* 1402 */     return val.longValue();
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
/*      */   public Number optNumber(String key) {
/* 1416 */     return optNumber(key, null);
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
/*      */   public Number optNumber(String key, Number defaultValue) {
/* 1432 */     Object val = opt(key);
/* 1433 */     if (NULL.equals(val)) {
/* 1434 */       return defaultValue;
/*      */     }
/* 1436 */     if (val instanceof Number) {
/* 1437 */       return (Number)val;
/*      */     }
/*      */     
/*      */     try {
/* 1441 */       return stringToNumber(val.toString());
/* 1442 */     } catch (Exception e) {
/* 1443 */       return defaultValue;
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
/*      */   public String optString(String key) {
/* 1457 */     return optString(key, "");
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
/*      */   public String optString(String key, String defaultValue) {
/* 1471 */     Object object = opt(key);
/* 1472 */     return NULL.equals(object) ? defaultValue : object.toString();
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
/*      */   private void populateMap(Object bean) {
/* 1485 */     Class<?> klass = bean.getClass();
/*      */ 
/*      */ 
/*      */     
/* 1489 */     boolean includeSuperClass = (klass.getClassLoader() != null);
/*      */     
/* 1491 */     Method[] methods = includeSuperClass ? klass.getMethods() : klass.getDeclaredMethods(); byte b; int i; Method[] arrayOfMethod1;
/* 1492 */     for (i = (arrayOfMethod1 = methods).length, b = 0; b < i; ) { Method method = arrayOfMethod1[b];
/* 1493 */       int modifiers = method.getModifiers();
/* 1494 */       if (Modifier.isPublic(modifiers) && 
/* 1495 */         !Modifier.isStatic(modifiers) && (
/* 1496 */         method.getParameterTypes()).length == 0 && 
/* 1497 */         !method.isBridge() && 
/* 1498 */         method.getReturnType() != void.class && 
/* 1499 */         isValidMethodName(method.getName())) {
/* 1500 */         String key = getKeyNameFromMethod(method);
/* 1501 */         if (key != null && !key.isEmpty()) {
/*      */           
/* 1503 */           try { Object result = method.invoke(bean, new Object[0]);
/* 1504 */             if (result != null) {
/* 1505 */               this.map.put(key, wrap(result));
/*      */ 
/*      */ 
/*      */               
/* 1509 */               if (result instanceof Closeable) {
/*      */                 try {
/* 1511 */                   ((Closeable)result).close();
/* 1512 */                 } catch (IOException iOException) {}
/*      */               }
/*      */             }
/*      */              }
/* 1516 */           catch (IllegalAccessException illegalAccessException) {  }
/* 1517 */           catch (IllegalArgumentException illegalArgumentException) {  }
/* 1518 */           catch (InvocationTargetException invocationTargetException) {}
/*      */         }
/*      */       } 
/*      */       b++; }
/*      */   
/*      */   }
/*      */   
/*      */   private static boolean isValidMethodName(String name) {
/* 1526 */     return (!"getClass".equals(name) && !"getDeclaringClass".equals(name));
/*      */   }
/*      */   private static String getKeyNameFromMethod(Method method) {
/*      */     String key;
/* 1530 */     int ignoreDepth = getAnnotationDepth(method, (Class)JSONPropertyIgnore.class);
/* 1531 */     if (ignoreDepth > 0) {
/* 1532 */       int forcedNameDepth = getAnnotationDepth(method, (Class)JSONPropertyName.class);
/* 1533 */       if (forcedNameDepth < 0 || ignoreDepth <= forcedNameDepth)
/*      */       {
/*      */         
/* 1536 */         return null;
/*      */       }
/*      */     } 
/* 1539 */     JSONPropertyName annotation = getAnnotation(method, JSONPropertyName.class);
/* 1540 */     if (annotation != null && annotation.value() != null && !annotation.value().isEmpty()) {
/* 1541 */       return annotation.value();
/*      */     }
/*      */     
/* 1544 */     String name = method.getName();
/* 1545 */     if (name.startsWith("get") && name.length() > 3) {
/* 1546 */       key = name.substring(3);
/* 1547 */     } else if (name.startsWith("is") && name.length() > 2) {
/* 1548 */       key = name.substring(2);
/*      */     } else {
/* 1550 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1555 */     if (Character.isLowerCase(key.charAt(0))) {
/* 1556 */       return null;
/*      */     }
/* 1558 */     if (key.length() == 1) {
/* 1559 */       key = key.toLowerCase(Locale.ROOT);
/* 1560 */     } else if (!Character.isUpperCase(key.charAt(1))) {
/* 1561 */       key = String.valueOf(key.substring(0, 1).toLowerCase(Locale.ROOT)) + key.substring(1);
/*      */     } 
/* 1563 */     return key;
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
/*      */   private static <A extends Annotation> A getAnnotation(Method m, Class<A> annotationClass) {
/* 1582 */     if (m == null || annotationClass == null) {
/* 1583 */       return null;
/*      */     }
/*      */     
/* 1586 */     if (m.isAnnotationPresent(annotationClass)) {
/* 1587 */       return m.getAnnotation(annotationClass);
/*      */     }
/*      */ 
/*      */     
/* 1591 */     Class<?> c = m.getDeclaringClass();
/* 1592 */     if (c.getSuperclass() == null)
/* 1593 */       return null; 
/*      */     byte b;
/*      */     int i;
/*      */     Class[] arrayOfClass;
/* 1597 */     for (i = (arrayOfClass = c.getInterfaces()).length, b = 0; b < i; ) { Class<?> clazz = arrayOfClass[b];
/*      */       try {
/* 1599 */         Method im = clazz.getMethod(m.getName(), m.getParameterTypes());
/* 1600 */         return getAnnotation(im, annotationClass);
/* 1601 */       } catch (SecurityException ex) {
/*      */       
/* 1603 */       } catch (NoSuchMethodException noSuchMethodException) {}
/*      */       
/*      */       b++; }
/*      */ 
/*      */     
/*      */     try {
/* 1609 */       return getAnnotation(
/* 1610 */           c.getSuperclass().getMethod(m.getName(), m.getParameterTypes()), 
/* 1611 */           annotationClass);
/* 1612 */     } catch (SecurityException ex) {
/* 1613 */       return null;
/* 1614 */     } catch (NoSuchMethodException ex) {
/* 1615 */       return null;
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
/*      */   private static int getAnnotationDepth(Method m, Class<? extends Annotation> annotationClass) {
/* 1635 */     if (m == null || annotationClass == null) {
/* 1636 */       return -1;
/*      */     }
/*      */     
/* 1639 */     if (m.isAnnotationPresent(annotationClass)) {
/* 1640 */       return 1;
/*      */     }
/*      */ 
/*      */     
/* 1644 */     Class<?> c = m.getDeclaringClass();
/* 1645 */     if (c.getSuperclass() == null)
/* 1646 */       return -1; 
/*      */     byte b;
/*      */     int i;
/*      */     Class[] arrayOfClass;
/* 1650 */     for (i = (arrayOfClass = c.getInterfaces()).length, b = 0; b < i; ) { Class<?> clazz = arrayOfClass[b];
/*      */       try {
/* 1652 */         Method im = clazz.getMethod(m.getName(), m.getParameterTypes());
/* 1653 */         int d = getAnnotationDepth(im, annotationClass);
/* 1654 */         if (d > 0)
/*      */         {
/* 1656 */           return d + 1;
/*      */         }
/* 1658 */       } catch (SecurityException ex) {
/*      */       
/* 1660 */       } catch (NoSuchMethodException noSuchMethodException) {}
/*      */       
/*      */       b++; }
/*      */ 
/*      */     
/*      */     try {
/* 1666 */       int d = getAnnotationDepth(
/* 1667 */           c.getSuperclass().getMethod(m.getName(), m.getParameterTypes()), 
/* 1668 */           annotationClass);
/* 1669 */       if (d > 0)
/*      */       {
/* 1671 */         return d + 1;
/*      */       }
/* 1673 */       return -1;
/* 1674 */     } catch (SecurityException ex) {
/* 1675 */       return -1;
/* 1676 */     } catch (NoSuchMethodException ex) {
/* 1677 */       return -1;
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
/*      */   public JSONObject put(String key, boolean value) throws JSONException {
/* 1695 */     return put(key, value ? Boolean.TRUE : Boolean.FALSE);
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
/*      */   public JSONObject put(String key, Collection<?> value) throws JSONException {
/* 1713 */     return put(key, new JSONArray(value));
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
/*      */   public JSONObject put(String key, double value) throws JSONException {
/* 1730 */     return put(key, Double.valueOf(value));
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
/*      */   public JSONObject put(String key, float value) throws JSONException {
/* 1747 */     return put(key, Float.valueOf(value));
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
/*      */   public JSONObject put(String key, int value) throws JSONException {
/* 1764 */     return put(key, Integer.valueOf(value));
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
/*      */   public JSONObject put(String key, long value) throws JSONException {
/* 1781 */     return put(key, Long.valueOf(value));
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
/*      */   public JSONObject put(String key, Map<?, ?> value) throws JSONException {
/* 1799 */     return put(key, new JSONObject(value));
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
/*      */   public JSONObject put(String key, Object value) throws JSONException {
/* 1819 */     if (key == null) {
/* 1820 */       throw new NullPointerException("Null key.");
/*      */     }
/* 1822 */     if (value != null) {
/* 1823 */       testValidity(value);
/* 1824 */       this.map.put(key, value);
/*      */     } else {
/* 1826 */       remove(key);
/*      */     } 
/* 1828 */     return this;
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
/*      */   public JSONObject putOnce(String key, Object value) throws JSONException {
/* 1845 */     if (key != null && value != null) {
/* 1846 */       if (opt(key) != null) {
/* 1847 */         throw new JSONException("Duplicate key \"" + key + "\"");
/*      */       }
/* 1849 */       return put(key, value);
/*      */     } 
/* 1851 */     return this;
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
/*      */   public JSONObject putOpt(String key, Object value) throws JSONException {
/* 1869 */     if (key != null && value != null) {
/* 1870 */       return put(key, value);
/*      */     }
/* 1872 */     return this;
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
/* 1895 */     return query(new JSONPointer(jsonPointer));
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
/*      */   public Object query(JSONPointer jsonPointer) {
/* 1917 */     return jsonPointer.queryFrom(this);
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
/* 1929 */     return optQuery(new JSONPointer(jsonPointer));
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
/* 1942 */       return jsonPointer.queryFrom(this);
/* 1943 */     } catch (JSONPointerException e) {
/* 1944 */       return null;
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
/*      */   public static String quote(String string) {
/* 1960 */     StringWriter sw = new StringWriter();
/* 1961 */     synchronized (sw.getBuffer()) {
/*      */       
/* 1963 */       return quote(string, sw).toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Writer quote(String string, Writer w) throws IOException {
/* 1972 */     if (string == null || string.isEmpty()) {
/* 1973 */       w.write("\"\"");
/* 1974 */       return w;
/*      */     } 
/*      */ 
/*      */     
/* 1978 */     char c = Character.MIN_VALUE;
/*      */ 
/*      */     
/* 1981 */     int len = string.length();
/*      */     
/* 1983 */     w.write(34);
/* 1984 */     for (int i = 0; i < len; i++) {
/* 1985 */       char b = c;
/* 1986 */       c = string.charAt(i);
/* 1987 */       switch (c) {
/*      */         case '"':
/*      */         case '\\':
/* 1990 */           w.write(92);
/* 1991 */           w.write(c);
/*      */           break;
/*      */         case '/':
/* 1994 */           if (b == '<') {
/* 1995 */             w.write(92);
/*      */           }
/* 1997 */           w.write(c);
/*      */           break;
/*      */         case '\b':
/* 2000 */           w.write("\\b");
/*      */           break;
/*      */         case '\t':
/* 2003 */           w.write("\\t");
/*      */           break;
/*      */         case '\n':
/* 2006 */           w.write("\\n");
/*      */           break;
/*      */         case '\f':
/* 2009 */           w.write("\\f");
/*      */           break;
/*      */         case '\r':
/* 2012 */           w.write("\\r");
/*      */           break;
/*      */         default:
/* 2015 */           if (c < ' ' || (c >= '' && c < ' ') || (
/* 2016 */             c >= ' ' && c < '℀')) {
/* 2017 */             w.write("\\u");
/* 2018 */             String hhhh = Integer.toHexString(c);
/* 2019 */             w.write("0000", 0, 4 - hhhh.length());
/* 2020 */             w.write(hhhh); break;
/*      */           } 
/* 2022 */           w.write(c);
/*      */           break;
/*      */       } 
/*      */     } 
/* 2026 */     w.write(34);
/* 2027 */     return w;
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
/*      */   public Object remove(String key) {
/* 2039 */     return this.map.remove(key);
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
/*      */   public boolean similar(Object other) {
/*      */     try {
/* 2052 */       if (!(other instanceof JSONObject)) {
/* 2053 */         return false;
/*      */       }
/* 2055 */       if (!keySet().equals(((JSONObject)other).keySet())) {
/* 2056 */         return false;
/*      */       }
/* 2058 */       for (Map.Entry<String, ?> entry : entrySet()) {
/* 2059 */         String name = entry.getKey();
/* 2060 */         Object valueThis = entry.getValue();
/* 2061 */         Object valueOther = ((JSONObject)other).get(name);
/* 2062 */         if (valueThis == valueOther) {
/*      */           continue;
/*      */         }
/* 2065 */         if (valueThis == null) {
/* 2066 */           return false;
/*      */         }
/* 2068 */         if (valueThis instanceof JSONObject) {
/* 2069 */           if (!((JSONObject)valueThis).similar(valueOther))
/* 2070 */             return false;  continue;
/*      */         } 
/* 2072 */         if (valueThis instanceof JSONArray) {
/* 2073 */           if (!((JSONArray)valueThis).similar(valueOther))
/* 2074 */             return false;  continue;
/*      */         } 
/* 2076 */         if (!valueThis.equals(valueOther)) {
/* 2077 */           return false;
/*      */         }
/*      */       } 
/* 2080 */       return true;
/* 2081 */     } catch (Throwable exception) {
/* 2082 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static boolean isDecimalNotation(String val) {
/* 2093 */     return !(val.indexOf('.') <= -1 && val.indexOf('e') <= -1 && 
/* 2094 */       val.indexOf('E') <= -1 && !"-0".equals(val));
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
/*      */   protected static Number stringToNumber(String val) throws NumberFormatException {
/* 2108 */     char initial = val.charAt(0);
/* 2109 */     if ((initial >= '0' && initial <= '9') || initial == '-') {
/*      */       
/* 2111 */       if (isDecimalNotation(val)) {
/*      */ 
/*      */         
/* 2114 */         if (val.length() > 14) {
/* 2115 */           return new BigDecimal(val);
/*      */         }
/* 2117 */         Double d = Double.valueOf(val);
/* 2118 */         if (d.isInfinite() || d.isNaN())
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2123 */           return new BigDecimal(val);
/*      */         }
/* 2125 */         return d;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2149 */       BigInteger bi = new BigInteger(val);
/* 2150 */       if (bi.bitLength() <= 31) {
/* 2151 */         return Integer.valueOf(bi.intValue());
/*      */       }
/* 2153 */       if (bi.bitLength() <= 63) {
/* 2154 */         return Long.valueOf(bi.longValue());
/*      */       }
/* 2156 */       return bi;
/*      */     } 
/* 2158 */     throw new NumberFormatException("val [" + val + "] is not a valid number.");
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
/*      */   public static Object stringToValue(String string) {
/* 2174 */     if ("".equals(string)) {
/* 2175 */       return string;
/*      */     }
/*      */ 
/*      */     
/* 2179 */     if ("true".equalsIgnoreCase(string)) {
/* 2180 */       return Boolean.TRUE;
/*      */     }
/* 2182 */     if ("false".equalsIgnoreCase(string)) {
/* 2183 */       return Boolean.FALSE;
/*      */     }
/* 2185 */     if ("null".equalsIgnoreCase(string)) {
/* 2186 */       return NULL;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2194 */     char initial = string.charAt(0);
/* 2195 */     if ((initial >= '0' && initial <= '9') || initial == '-') {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 2200 */         if (isDecimalNotation(string)) {
/* 2201 */           Double d = Double.valueOf(string);
/* 2202 */           if (!d.isInfinite() && !d.isNaN()) {
/* 2203 */             return d;
/*      */           }
/*      */         } else {
/* 2206 */           Long myLong = Long.valueOf(string);
/* 2207 */           if (string.equals(myLong.toString())) {
/* 2208 */             if (myLong.longValue() == myLong.intValue()) {
/* 2209 */               return Integer.valueOf(myLong.intValue());
/*      */             }
/* 2211 */             return myLong;
/*      */           } 
/*      */         } 
/* 2214 */       } catch (Exception exception) {}
/*      */     }
/*      */     
/* 2217 */     return string;
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
/*      */   public static void testValidity(Object o) throws JSONException {
/* 2229 */     if (o != null) {
/* 2230 */       if (o instanceof Double) {
/* 2231 */         if (((Double)o).isInfinite() || ((Double)o).isNaN()) {
/* 2232 */           throw new JSONException(
/* 2233 */               "JSON does not allow non-finite numbers.");
/*      */         }
/* 2235 */       } else if (o instanceof Float && ((
/* 2236 */         (Float)o).isInfinite() || ((Float)o).isNaN())) {
/* 2237 */         throw new JSONException(
/* 2238 */             "JSON does not allow non-finite numbers.");
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
/*      */   public JSONArray toJSONArray(JSONArray names) throws JSONException {
/* 2256 */     if (names == null || names.isEmpty()) {
/* 2257 */       return null;
/*      */     }
/* 2259 */     JSONArray ja = new JSONArray();
/* 2260 */     for (int i = 0; i < names.length(); i++) {
/* 2261 */       ja.put(opt(names.getString(i)));
/*      */     }
/* 2263 */     return ja;
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
/*      */   public String toString() {
/*      */     try {
/* 2282 */       return toString(0);
/* 2283 */     } catch (Exception e) {
/* 2284 */       return null;
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
/*      */   public String toString(int indentFactor) throws JSONException {
/* 2315 */     StringWriter w = new StringWriter();
/* 2316 */     synchronized (w.getBuffer()) {
/* 2317 */       return write(w, indentFactor, 0).toString();
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
/*      */   
/*      */   public static String valueToString(Object value) throws JSONException {
/* 2350 */     return JSONWriter.valueToString(value);
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
/*      */   public static Object wrap(Object object) {
/*      */     try {
/* 2367 */       if (object == null) {
/* 2368 */         return NULL;
/*      */       }
/* 2370 */       if (object instanceof JSONObject || object instanceof JSONArray || 
/* 2371 */         NULL.equals(object) || object instanceof JSONString || 
/* 2372 */         object instanceof Byte || object instanceof Character || 
/* 2373 */         object instanceof Short || object instanceof Integer || 
/* 2374 */         object instanceof Long || object instanceof Boolean || 
/* 2375 */         object instanceof Float || object instanceof Double || 
/* 2376 */         object instanceof String || object instanceof BigInteger || 
/* 2377 */         object instanceof BigDecimal || object instanceof Enum) {
/* 2378 */         return object;
/*      */       }
/*      */       
/* 2381 */       if (object instanceof Collection) {
/* 2382 */         Collection<?> coll = (Collection)object;
/* 2383 */         return new JSONArray(coll);
/*      */       } 
/* 2385 */       if (object.getClass().isArray()) {
/* 2386 */         return new JSONArray(object);
/*      */       }
/* 2388 */       if (object instanceof Map) {
/* 2389 */         Map<?, ?> map = (Map<?, ?>)object;
/* 2390 */         return new JSONObject(map);
/*      */       } 
/* 2392 */       Package objectPackage = object.getClass().getPackage();
/* 2393 */       String objectPackageName = (objectPackage != null) ? objectPackage
/* 2394 */         .getName() : "";
/* 2395 */       if (objectPackageName.startsWith("java.") || 
/* 2396 */         objectPackageName.startsWith("javax.") || 
/* 2397 */         object.getClass().getClassLoader() == null) {
/* 2398 */         return object.toString();
/*      */       }
/* 2400 */       return new JSONObject(object);
/* 2401 */     } catch (Exception exception) {
/* 2402 */       return null;
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
/* 2417 */     return write(writer, 0, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   static final Writer writeValue(Writer writer, Object value, int indentFactor, int indent) throws JSONException, IOException {
/* 2422 */     if (value == null || value.equals(null)) {
/* 2423 */       writer.write("null");
/* 2424 */     } else if (value instanceof JSONString) {
/*      */       Object o;
/*      */       try {
/* 2427 */         o = ((JSONString)value).toJSONString();
/* 2428 */       } catch (Exception e) {
/* 2429 */         throw new JSONException(e);
/*      */       } 
/* 2431 */       writer.write((o != null) ? o.toString() : quote(value.toString()));
/* 2432 */     } else if (value instanceof Number) {
/*      */       
/* 2434 */       String numberAsString = numberToString((Number)value);
/* 2435 */       if (NUMBER_PATTERN.matcher(numberAsString).matches()) {
/* 2436 */         writer.write(numberAsString);
/*      */       }
/*      */       else {
/*      */         
/* 2440 */         quote(numberAsString, writer);
/*      */       } 
/* 2442 */     } else if (value instanceof Boolean) {
/* 2443 */       writer.write(value.toString());
/* 2444 */     } else if (value instanceof Enum) {
/* 2445 */       writer.write(quote(((Enum)value).name()));
/* 2446 */     } else if (value instanceof JSONObject) {
/* 2447 */       ((JSONObject)value).write(writer, indentFactor, indent);
/* 2448 */     } else if (value instanceof JSONArray) {
/* 2449 */       ((JSONArray)value).write(writer, indentFactor, indent);
/* 2450 */     } else if (value instanceof Map) {
/* 2451 */       Map<?, ?> map = (Map<?, ?>)value;
/* 2452 */       (new JSONObject(map)).write(writer, indentFactor, indent);
/* 2453 */     } else if (value instanceof Collection) {
/* 2454 */       Collection<?> coll = (Collection)value;
/* 2455 */       (new JSONArray(coll)).write(writer, indentFactor, indent);
/* 2456 */     } else if (value.getClass().isArray()) {
/* 2457 */       (new JSONArray(value)).write(writer, indentFactor, indent);
/*      */     } else {
/* 2459 */       quote(value.toString(), writer);
/*      */     } 
/* 2461 */     return writer;
/*      */   }
/*      */   
/*      */   static final void indent(Writer writer, int indent) throws IOException {
/* 2465 */     for (int i = 0; i < indent; i++) {
/* 2466 */       writer.write(32);
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
/*      */   public Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
/*      */     try {
/* 2499 */       boolean needsComma = false;
/* 2500 */       int length = length();
/* 2501 */       writer.write(123);
/*      */       
/* 2503 */       if (length == 1) {
/* 2504 */         Map.Entry<String, ?> entry = entrySet().iterator().next();
/* 2505 */         String key = entry.getKey();
/* 2506 */         writer.write(quote(key));
/* 2507 */         writer.write(58);
/* 2508 */         if (indentFactor > 0) {
/* 2509 */           writer.write(32);
/*      */         }
/*      */         try {
/* 2512 */           writeValue(writer, entry.getValue(), indentFactor, indent);
/* 2513 */         } catch (Exception e) {
/* 2514 */           throw new JSONException("Unable to write JSONObject value for key: " + key, e);
/*      */         } 
/* 2516 */       } else if (length != 0) {
/* 2517 */         int newIndent = indent + indentFactor;
/* 2518 */         for (Map.Entry<String, ?> entry : entrySet()) {
/* 2519 */           if (needsComma) {
/* 2520 */             writer.write(44);
/*      */           }
/* 2522 */           if (indentFactor > 0) {
/* 2523 */             writer.write(10);
/*      */           }
/* 2525 */           indent(writer, newIndent);
/* 2526 */           String key = entry.getKey();
/* 2527 */           writer.write(quote(key));
/* 2528 */           writer.write(58);
/* 2529 */           if (indentFactor > 0) {
/* 2530 */             writer.write(32);
/*      */           }
/*      */           try {
/* 2533 */             writeValue(writer, entry.getValue(), indentFactor, newIndent);
/* 2534 */           } catch (Exception e) {
/* 2535 */             throw new JSONException("Unable to write JSONObject value for key: " + key, e);
/*      */           } 
/* 2537 */           needsComma = true;
/*      */         } 
/* 2539 */         if (indentFactor > 0) {
/* 2540 */           writer.write(10);
/*      */         }
/* 2542 */         indent(writer, indent);
/*      */       } 
/* 2544 */       writer.write(125);
/* 2545 */       return writer;
/* 2546 */     } catch (IOException exception) {
/* 2547 */       throw new JSONException(exception);
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
/*      */   public Map<String, Object> toMap() {
/* 2561 */     Map<String, Object> results = new HashMap<>();
/* 2562 */     for (Map.Entry<String, Object> entry : entrySet()) {
/*      */       Object value;
/* 2564 */       if (entry.getValue() == null || NULL.equals(entry.getValue())) {
/* 2565 */         value = null;
/* 2566 */       } else if (entry.getValue() instanceof JSONObject) {
/* 2567 */         value = ((JSONObject)entry.getValue()).toMap();
/* 2568 */       } else if (entry.getValue() instanceof JSONArray) {
/* 2569 */         value = ((JSONArray)entry.getValue()).toList();
/*      */       } else {
/* 2571 */         value = entry.getValue();
/*      */       } 
/* 2573 */       results.put(entry.getKey(), value);
/*      */     } 
/* 2575 */     return results;
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
/*      */   private static JSONException wrongValueFormatException(String key, String valueType, Throwable cause) {
/* 2589 */     return new JSONException(
/* 2590 */         "JSONObject[" + quote(key) + "] is not a " + valueType + ".", 
/* 2591 */         cause);
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
/*      */   private static JSONException wrongValueFormatException(String key, String valueType, Object value, Throwable cause) {
/* 2606 */     return new JSONException(
/* 2607 */         "JSONObject[" + quote(key) + "] is not a " + valueType + " (" + value + ").", 
/* 2608 */         cause);
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\json\JSONObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */