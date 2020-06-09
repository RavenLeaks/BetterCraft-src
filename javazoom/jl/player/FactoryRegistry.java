/*     */ package javazoom.jl.player;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import javazoom.jl.decoder.JavaLayerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FactoryRegistry
/*     */   extends AudioDeviceFactory
/*     */ {
/*  40 */   private static FactoryRegistry instance = null;
/*     */ 
/*     */   
/*     */   public static synchronized FactoryRegistry systemRegistry() {
/*  44 */     if (instance == null) {
/*     */       
/*  46 */       instance = new FactoryRegistry();
/*  47 */       instance.registerDefaultFactories();
/*     */     } 
/*  49 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*  53 */   protected Hashtable factories = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFactory(AudioDeviceFactory factory) {
/*  61 */     this.factories.put(factory.getClass(), factory);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFactoryType(Class cls) {
/*  66 */     this.factories.remove(cls);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFactory(AudioDeviceFactory factory) {
/*  71 */     this.factories.remove(factory.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioDevice createAudioDevice() throws JavaLayerException {
/*  76 */     AudioDevice device = null;
/*  77 */     AudioDeviceFactory[] factories = getFactoriesPriority();
/*     */     
/*  79 */     if (factories == null) {
/*  80 */       throw new JavaLayerException(this + ": no factories registered");
/*     */     }
/*  82 */     JavaLayerException lastEx = null;
/*  83 */     for (int i = 0; device == null && i < factories.length; i++) {
/*     */ 
/*     */       
/*     */       try {
/*  87 */         device = factories[i].createAudioDevice();
/*     */       }
/*  89 */       catch (JavaLayerException ex) {
/*     */         
/*  91 */         lastEx = ex;
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     if (device == null && lastEx != null)
/*     */     {
/*  97 */       throw new JavaLayerException("Cannot create AudioDevice", lastEx);
/*     */     }
/*     */     
/* 100 */     return device;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AudioDeviceFactory[] getFactoriesPriority() {
/* 106 */     AudioDeviceFactory[] fa = null;
/* 107 */     synchronized (this.factories) {
/*     */       
/* 109 */       int size = this.factories.size();
/* 110 */       if (size != 0) {
/*     */         
/* 112 */         fa = new AudioDeviceFactory[size];
/* 113 */         int idx = 0;
/* 114 */         Enumeration<AudioDeviceFactory> e = this.factories.elements();
/* 115 */         while (e.hasMoreElements()) {
/*     */           
/* 117 */           AudioDeviceFactory factory = e.nextElement();
/* 118 */           fa[idx++] = factory;
/*     */         } 
/*     */       } 
/*     */     } 
/* 122 */     return fa;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerDefaultFactories() {
/* 127 */     addFactory(new JavaSoundAudioDeviceFactory());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\FactoryRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */