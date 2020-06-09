/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class Sound
/*    */   implements ISoundEventAccessor<Sound>
/*    */ {
/*    */   private final ResourceLocation name;
/*    */   private final float volume;
/*    */   private final float pitch;
/*    */   private final int weight;
/*    */   private final Type type;
/*    */   private final boolean streaming;
/*    */   
/*    */   public Sound(String nameIn, float volumeIn, float pitchIn, int weightIn, Type typeIn, boolean p_i46526_6_) {
/* 16 */     this.name = new ResourceLocation(nameIn);
/* 17 */     this.volume = volumeIn;
/* 18 */     this.pitch = pitchIn;
/* 19 */     this.weight = weightIn;
/* 20 */     this.type = typeIn;
/* 21 */     this.streaming = p_i46526_6_;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getSoundLocation() {
/* 26 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getSoundAsOggLocation() {
/* 31 */     return new ResourceLocation(this.name.getResourceDomain(), "sounds/" + this.name.getResourcePath() + ".ogg");
/*    */   }
/*    */ 
/*    */   
/*    */   public float getVolume() {
/* 36 */     return this.volume;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getPitch() {
/* 41 */     return this.pitch;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeight() {
/* 46 */     return this.weight;
/*    */   }
/*    */ 
/*    */   
/*    */   public Sound cloneEntry() {
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Type getType() {
/* 56 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStreaming() {
/* 61 */     return this.streaming;
/*    */   }
/*    */   
/*    */   public enum Type
/*    */   {
/* 66 */     FILE("file"),
/* 67 */     SOUND_EVENT("event");
/*    */     
/*    */     private final String name;
/*    */ 
/*    */     
/*    */     Type(String nameIn) {
/* 73 */       this.name = nameIn;
/*    */     } public static Type getByName(String nameIn) {
/*    */       byte b;
/*    */       int i;
/*    */       Type[] arrayOfType;
/* 78 */       for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type sound$type = arrayOfType[b];
/*    */         
/* 80 */         if (sound$type.name.equals(nameIn))
/*    */         {
/* 82 */           return sound$type;
/*    */         }
/*    */         b++; }
/*    */       
/* 86 */       return null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\Sound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */