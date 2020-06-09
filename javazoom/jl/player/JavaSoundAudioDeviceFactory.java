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
/*    */ public class JavaSoundAudioDeviceFactory
/*    */   extends AudioDeviceFactory
/*    */ {
/*    */   private boolean tested = false;
/*    */   private static final String DEVICE_CLASS_NAME = "javazoom.jl.player.JavaSoundAudioDevice";
/*    */   
/*    */   public synchronized AudioDevice createAudioDevice() throws JavaLayerException {
/* 40 */     if (!this.tested) {
/*    */       
/* 42 */       testAudioDevice();
/* 43 */       this.tested = true;
/*    */     } 
/*    */ 
/*    */     
/*    */     try {
/* 48 */       return createAudioDeviceImpl();
/*    */     }
/* 50 */     catch (Exception ex) {
/*    */       
/* 52 */       throw new JavaLayerException("unable to create JavaSound device: " + ex);
/*    */     }
/* 54 */     catch (LinkageError ex) {
/*    */       
/* 56 */       throw new JavaLayerException("unable to create JavaSound device: " + ex);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected JavaSoundAudioDevice createAudioDeviceImpl() throws JavaLayerException {
/* 63 */     ClassLoader loader = getClass().getClassLoader();
/*    */     
/*    */     try {
/* 66 */       JavaSoundAudioDevice dev = (JavaSoundAudioDevice)instantiate(loader, "javazoom.jl.player.JavaSoundAudioDevice");
/* 67 */       return dev;
/*    */     }
/* 69 */     catch (Exception ex) {
/*    */       
/* 71 */       throw new JavaLayerException("Cannot create JavaSound device", ex);
/*    */     }
/* 73 */     catch (LinkageError ex) {
/*    */       
/* 75 */       throw new JavaLayerException("Cannot create JavaSound device", ex);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void testAudioDevice() throws JavaLayerException {
/* 82 */     JavaSoundAudioDevice dev = createAudioDeviceImpl();
/* 83 */     dev.test();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\JavaSoundAudioDeviceFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */