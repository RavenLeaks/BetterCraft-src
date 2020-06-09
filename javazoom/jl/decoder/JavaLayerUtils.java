/*     */ package javazoom.jl.decoder;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InvalidClassException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Array;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JavaLayerUtils
/*     */ {
/*  40 */   private static JavaLayerHook hook = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object deserialize(InputStream in, Class cls) throws IOException {
/*  50 */     if (cls == null) {
/*  51 */       throw new NullPointerException("cls");
/*     */     }
/*  53 */     Object obj = deserialize(in, cls);
/*  54 */     if (!cls.isInstance(obj))
/*     */     {
/*  56 */       throw new InvalidObjectException("type of deserialized instance not of required class.");
/*     */     }
/*     */     
/*  59 */     return obj;
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
/*     */   public static Object deserialize(InputStream in) throws IOException {
/*     */     Object obj;
/*  80 */     if (in == null) {
/*  81 */       throw new NullPointerException("in");
/*     */     }
/*  83 */     ObjectInputStream objIn = new ObjectInputStream(in);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  89 */       obj = objIn.readObject();
/*     */     }
/*  91 */     catch (ClassNotFoundException ex) {
/*     */       
/*  93 */       throw new InvalidClassException(ex.toString());
/*     */     } 
/*     */     
/*  96 */     return obj;
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
/*     */   public static Object deserializeArray(InputStream in, Class<?> elemType, int length) throws IOException {
/* 113 */     if (elemType == null) {
/* 114 */       throw new NullPointerException("elemType");
/*     */     }
/* 116 */     if (length < -1) {
/* 117 */       throw new IllegalArgumentException("length");
/*     */     }
/* 119 */     Object obj = deserialize(in);
/*     */     
/* 121 */     Class<?> cls = obj.getClass();
/*     */ 
/*     */     
/* 124 */     if (!cls.isArray()) {
/* 125 */       throw new InvalidObjectException("object is not an array");
/*     */     }
/* 127 */     Class<?> arrayElemType = cls.getComponentType();
/* 128 */     if (arrayElemType != elemType) {
/* 129 */       throw new InvalidObjectException("unexpected array component type");
/*     */     }
/* 131 */     if (length != -1) {
/*     */       
/* 133 */       int arrayLength = Array.getLength(obj);
/* 134 */       if (arrayLength != length) {
/* 135 */         throw new InvalidObjectException("array length mismatch");
/*     */       }
/*     */     } 
/* 138 */     return obj;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object deserializeArrayResource(String name, Class elemType, int length) throws IOException {
/* 144 */     InputStream str = getResourceAsStream(name);
/* 145 */     if (str == null) {
/* 146 */       throw new IOException("unable to load resource '" + name + "'");
/*     */     }
/* 148 */     Object obj = deserializeArray(str, elemType, length);
/*     */     
/* 150 */     return obj;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void serialize(OutputStream out, Object obj) throws IOException {
/* 156 */     if (out == null) {
/* 157 */       throw new NullPointerException("out");
/*     */     }
/* 159 */     if (obj == null) {
/* 160 */       throw new NullPointerException("obj");
/*     */     }
/* 162 */     ObjectOutputStream objOut = new ObjectOutputStream(out);
/* 163 */     objOut.writeObject(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setHook(JavaLayerHook hook0) {
/* 172 */     hook = hook0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized JavaLayerHook getHook() {
/* 177 */     return hook;
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
/*     */   public static synchronized InputStream getResourceAsStream(String name) {
/* 193 */     InputStream is = null;
/*     */     
/* 195 */     if (hook != null) {
/*     */       
/* 197 */       is = hook.getResourceAsStream(name);
/*     */     }
/*     */     else {
/*     */       
/* 201 */       Class<JavaLayerUtils> cls = JavaLayerUtils.class;
/* 202 */       is = cls.getResourceAsStream(name);
/*     */     } 
/*     */     
/* 205 */     return is;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\JavaLayerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */