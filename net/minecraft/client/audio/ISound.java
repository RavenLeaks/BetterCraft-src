/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ 
/*    */ public interface ISound
/*    */ {
/*    */   ResourceLocation getSoundLocation();
/*    */   
/*    */   @Nullable
/*    */   SoundEventAccessor createAccessor(SoundHandler paramSoundHandler);
/*    */   
/*    */   Sound getSound();
/*    */   
/*    */   SoundCategory getCategory();
/*    */   
/*    */   boolean canRepeat();
/*    */   
/*    */   int getRepeatDelay();
/*    */   
/*    */   float getVolume();
/*    */   
/*    */   float getPitch();
/*    */   
/*    */   float getXPosF();
/*    */   
/*    */   float getYPosF();
/*    */   
/*    */   float getZPosF();
/*    */   
/*    */   AttenuationType getAttenuationType();
/*    */   
/*    */   public enum AttenuationType
/*    */   {
/* 36 */     NONE(0),
/* 37 */     LINEAR(2);
/*    */     
/*    */     private final int type;
/*    */ 
/*    */     
/*    */     AttenuationType(int typeIn) {
/* 43 */       this.type = typeIn;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getTypeInt() {
/* 48 */       return this.type;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\ISound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */