/*     */ package com.darkmagician6.eventapi;
/*     */ 
/*     */ import com.darkmagician6.eventapi.events.Event;
/*     */ import com.darkmagician6.eventapi.events.EventStoppable;
/*     */ import com.darkmagician6.eventapi.types.Priority;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EventManager
/*     */ {
/*  26 */   private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(Object object) {
/*     */     byte b;
/*     */     int i;
/*     */     Method[] arrayOfMethod;
/*  41 */     for (i = (arrayOfMethod = object.getClass().getDeclaredMethods()).length, b = 0; b < i; ) { Method method = arrayOfMethod[b];
/*  42 */       if (!isMethodBad(method))
/*     */       {
/*     */ 
/*     */         
/*  46 */         register(method, object);
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(Object object, Class<? extends Event> eventClass) {
/*     */     byte b;
/*     */     int i;
/*     */     Method[] arrayOfMethod;
/*  60 */     for (i = (arrayOfMethod = object.getClass().getDeclaredMethods()).length, b = 0; b < i; ) { Method method = arrayOfMethod[b];
/*  61 */       if (!isMethodBad(method, eventClass))
/*     */       {
/*     */ 
/*     */         
/*  65 */         register(method, object);
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregister(Object object) {
/*  76 */     for (List<MethodData> dataList : REGISTRY_MAP.values()) {
/*  77 */       for (MethodData data : dataList) {
/*  78 */         if (data.getSource().equals(object)) {
/*  79 */           dataList.remove(data);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     cleanMap(true);
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
/*     */   public static void unregister(Object object, Class<? extends Event> eventClass) {
/*  96 */     if (REGISTRY_MAP.containsKey(eventClass)) {
/*  97 */       for (MethodData data : REGISTRY_MAP.get(eventClass)) {
/*  98 */         if (data.getSource().equals(object)) {
/*  99 */           ((List)REGISTRY_MAP.get(eventClass)).remove(data);
/*     */         }
/*     */       } 
/*     */       
/* 103 */       cleanMap(true);
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
/*     */   private static void register(Method method, Object object) {
/* 120 */     Class<? extends Event> indexClass = (Class)method.getParameterTypes()[0];
/*     */     
/* 122 */     MethodData data = new MethodData(object, method, ((EventTarget)method.<EventTarget>getAnnotation(EventTarget.class)).value());
/*     */ 
/*     */     
/* 125 */     if (!data.getTarget().isAccessible()) {
/* 126 */       data.getTarget().setAccessible(true);
/*     */     }
/*     */     
/* 129 */     if (REGISTRY_MAP.containsKey(indexClass)) {
/* 130 */       if (!((List)REGISTRY_MAP.get(indexClass)).contains(data)) {
/* 131 */         ((List<MethodData>)REGISTRY_MAP.get(indexClass)).add(data);
/* 132 */         sortListValue(indexClass);
/*     */       } 
/*     */     } else {
/* 135 */       REGISTRY_MAP.put(indexClass, new CopyOnWriteArrayList<MethodData>(data)
/*     */           {
/*     */             private static final long serialVersionUID = 666L;
/*     */           });
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
/*     */   public static void removeEntry(Class<? extends Event> indexClass) {
/* 151 */     Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
/*     */     
/* 153 */     while (mapIterator.hasNext()) {
/* 154 */       if (((Class)((Map.Entry)mapIterator.next()).getKey()).equals(indexClass)) {
/* 155 */         mapIterator.remove();
/*     */         break;
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
/*     */   public static void cleanMap(boolean onlyEmptyEntries) {
/* 169 */     Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();
/*     */     
/* 171 */     while (mapIterator.hasNext()) {
/* 172 */       if (!onlyEmptyEntries || ((List)((Map.Entry)mapIterator.next()).getValue()).isEmpty()) {
/* 173 */         mapIterator.remove();
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
/*     */   private static void sortListValue(Class<? extends Event> indexClass) {
/* 185 */     List<MethodData> sortedList = new CopyOnWriteArrayList<>(); byte b; int i;
/*     */     byte[] arrayOfByte;
/* 187 */     for (i = (arrayOfByte = Priority.VALUE_ARRAY).length, b = 0; b < i; ) { byte priority = arrayOfByte[b];
/* 188 */       for (MethodData data : REGISTRY_MAP.get(indexClass)) {
/* 189 */         if (data.getPriority() == priority) {
/* 190 */           sortedList.add(data);
/*     */         }
/*     */       } 
/*     */       
/*     */       b++; }
/*     */     
/* 196 */     REGISTRY_MAP.put(indexClass, sortedList);
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
/*     */   private static boolean isMethodBad(Method method) {
/* 211 */     return !((method.getParameterTypes()).length == 1 && method.isAnnotationPresent((Class)EventTarget.class));
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
/*     */   private static boolean isMethodBad(Method method, Class<? extends Event> eventClass) {
/* 228 */     return !(!isMethodBad(method) && method.getParameterTypes()[0].equals(eventClass));
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
/*     */   public static final Event call(Event event) {
/* 247 */     List<MethodData> dataList = REGISTRY_MAP.get(event.getClass());
/*     */     
/* 249 */     if (dataList != null) {
/* 250 */       if (event instanceof EventStoppable) {
/* 251 */         EventStoppable stoppable = (EventStoppable)event;
/*     */         
/* 253 */         for (MethodData data : dataList) {
/* 254 */           invoke(data, event);
/*     */           
/* 256 */           if (stoppable.isStopped()) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } else {
/* 261 */         for (MethodData data : dataList) {
/* 262 */           invoke(data, event);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 267 */     return event;
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
/*     */   private static void invoke(MethodData data, Event argument) {
/*     */     
/* 282 */     try { data.getTarget().invoke(data.getSource(), new Object[] { argument }); }
/* 283 */     catch (IllegalAccessException illegalAccessException) {  }
/* 284 */     catch (IllegalArgumentException illegalArgumentException) {  }
/* 285 */     catch (InvocationTargetException invocationTargetException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class MethodData
/*     */   {
/*     */     private final Object source;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Method target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final byte priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MethodData(Object source, Method target, byte priority) {
/* 315 */       this.source = source;
/* 316 */       this.target = target;
/* 317 */       this.priority = priority;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getSource() {
/* 326 */       return this.source;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Method getTarget() {
/* 335 */       return this.target;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte getPriority() {
/* 344 */       return this.priority;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\darkmagician6\eventapi\EventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */