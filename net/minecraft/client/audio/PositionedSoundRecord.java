/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class PositionedSoundRecord
/*    */   extends PositionedSound
/*    */ {
/*    */   public PositionedSoundRecord(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, BlockPos pos) {
/* 12 */     this(soundIn, categoryIn, volumeIn, pitchIn, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static PositionedSoundRecord getMasterRecord(SoundEvent soundIn, float pitchIn) {
/* 17 */     return func_194007_a(soundIn, pitchIn, 0.25F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static PositionedSoundRecord func_194007_a(SoundEvent p_194007_0_, float p_194007_1_, float p_194007_2_) {
/* 22 */     return new PositionedSoundRecord(p_194007_0_, SoundCategory.MASTER, p_194007_2_, p_194007_1_, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static PositionedSoundRecord getMusicRecord(SoundEvent soundIn) {
/* 27 */     return new PositionedSoundRecord(soundIn, SoundCategory.MUSIC, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public static PositionedSoundRecord getRecordSoundRecord(SoundEvent soundIn, float xIn, float yIn, float zIn) {
/* 32 */     return new PositionedSoundRecord(soundIn, SoundCategory.RECORDS, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, xIn, yIn, zIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public PositionedSoundRecord(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, float xIn, float yIn, float zIn) {
/* 37 */     this(soundIn, categoryIn, volumeIn, pitchIn, false, 0, ISound.AttenuationType.LINEAR, xIn, yIn, zIn);
/*    */   }
/*    */ 
/*    */   
/*    */   private PositionedSoundRecord(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, boolean repeatIn, int repeatDelayIn, ISound.AttenuationType attenuationTypeIn, float xIn, float yIn, float zIn) {
/* 42 */     this(soundIn.getSoundName(), categoryIn, volumeIn, pitchIn, repeatIn, repeatDelayIn, attenuationTypeIn, xIn, yIn, zIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public PositionedSoundRecord(ResourceLocation soundId, SoundCategory categoryIn, float volumeIn, float pitchIn, boolean repeatIn, int repeatDelayIn, ISound.AttenuationType attenuationTypeIn, float xIn, float yIn, float zIn) {
/* 47 */     super(soundId, categoryIn);
/* 48 */     this.volume = volumeIn;
/* 49 */     this.pitch = pitchIn;
/* 50 */     this.xPosF = xIn;
/* 51 */     this.yPosF = yIn;
/* 52 */     this.zPosF = zIn;
/* 53 */     this.repeat = repeatIn;
/* 54 */     this.repeatDelay = repeatDelayIn;
/* 55 */     this.attenuationType = attenuationTypeIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\PositionedSoundRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */