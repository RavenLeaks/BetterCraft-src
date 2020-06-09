/*    */ package javazoom.jl.player;
/*    */ 
/*    */ import javazoom.jl.decoder.JavaLayerException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AudioDeviceFactory
/*    */ {
/*    */   public abstract AudioDevice createAudioDevice() throws JavaLayerException;
/*    */   
/*    */   protected AudioDevice instantiate(ClassLoader loader, String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
/* 70 */     AudioDevice dev = null;
/*    */     
/* 72 */     Class<?> cls = null;
/* 73 */     if (loader == null) {
/*    */       
/* 75 */       cls = Class.forName(name);
/*    */     }
/*    */     else {
/*    */       
/* 79 */       cls = loader.loadClass(name);
/*    */     } 
/*    */     
/* 82 */     Object o = cls.newInstance();
/* 83 */     dev = (AudioDevice)o;
/*    */     
/* 85 */     return dev;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\AudioDeviceFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */