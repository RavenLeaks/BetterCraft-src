/*     */ package net.minecraft.client.audio;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PositionedSound
/*     */   implements ISound
/*     */ {
/*     */   protected Sound sound;
/*     */   @Nullable
/*     */   private SoundEventAccessor soundEvent;
/*     */   protected SoundCategory category;
/*     */   protected ResourceLocation positionedSoundLocation;
/*     */   protected float volume;
/*     */   protected float pitch;
/*     */   protected float xPosF;
/*     */   protected float yPosF;
/*     */   protected float zPosF;
/*     */   protected boolean repeat;
/*     */   protected int repeatDelay;
/*     */   protected ISound.AttenuationType attenuationType;
/*     */   
/*     */   protected PositionedSound(SoundEvent soundIn, SoundCategory categoryIn) {
/*  28 */     this(soundIn.getSoundName(), categoryIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PositionedSound(ResourceLocation soundId, SoundCategory categoryIn) {
/*  33 */     this.volume = 1.0F;
/*  34 */     this.pitch = 1.0F;
/*  35 */     this.attenuationType = ISound.AttenuationType.LINEAR;
/*  36 */     this.positionedSoundLocation = soundId;
/*  37 */     this.category = categoryIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getSoundLocation() {
/*  42 */     return this.positionedSoundLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEventAccessor createAccessor(SoundHandler handler) {
/*  47 */     this.soundEvent = handler.getAccessor(this.positionedSoundLocation);
/*     */     
/*  49 */     if (this.soundEvent == null) {
/*     */       
/*  51 */       this.sound = SoundHandler.MISSING_SOUND;
/*     */     }
/*     */     else {
/*     */       
/*  55 */       this.sound = this.soundEvent.cloneEntry();
/*     */     } 
/*     */     
/*  58 */     return this.soundEvent;
/*     */   }
/*     */ 
/*     */   
/*     */   public Sound getSound() {
/*  63 */     return this.sound;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundCategory getCategory() {
/*  68 */     return this.category;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRepeat() {
/*  73 */     return this.repeat;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRepeatDelay() {
/*  78 */     return this.repeatDelay;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVolume() {
/*  83 */     return this.volume * this.sound.getVolume();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPitch() {
/*  88 */     return this.pitch * this.sound.getPitch();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getXPosF() {
/*  93 */     return this.xPosF;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getYPosF() {
/*  98 */     return this.yPosF;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getZPosF() {
/* 103 */     return this.zPosF;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISound.AttenuationType getAttenuationType() {
/* 108 */     return this.attenuationType;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\PositionedSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */