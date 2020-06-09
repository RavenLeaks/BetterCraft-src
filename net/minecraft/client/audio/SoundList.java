/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundList
/*    */ {
/*    */   private final List<Sound> sounds;
/*    */   private final boolean replaceExisting;
/*    */   private final String subtitle;
/*    */   
/*    */   public SoundList(List<Sound> soundsIn, boolean replceIn, String subtitleIn) {
/* 18 */     this.sounds = soundsIn;
/* 19 */     this.replaceExisting = replceIn;
/* 20 */     this.subtitle = subtitleIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Sound> getSounds() {
/* 25 */     return this.sounds;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canReplaceExisting() {
/* 30 */     return this.replaceExisting;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getSubtitle() {
/* 36 */     return this.subtitle;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\SoundList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */