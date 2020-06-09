/*    */ package net.minecraft.world;
/*    */ 
/*    */ import javax.annotation.concurrent.Immutable;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class DifficultyInstance
/*    */ {
/*    */   private final EnumDifficulty worldDifficulty;
/*    */   private final float additionalDifficulty;
/*    */   
/*    */   public DifficultyInstance(EnumDifficulty worldDifficulty, long worldTime, long chunkInhabitedTime, float moonPhaseFactor) {
/* 14 */     this.worldDifficulty = worldDifficulty;
/* 15 */     this.additionalDifficulty = calculateAdditionalDifficulty(worldDifficulty, worldTime, chunkInhabitedTime, moonPhaseFactor);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getAdditionalDifficulty() {
/* 20 */     return this.additionalDifficulty;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_193845_a(float p_193845_1_) {
/* 25 */     return (this.additionalDifficulty > p_193845_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getClampedAdditionalDifficulty() {
/* 30 */     if (this.additionalDifficulty < 2.0F)
/*    */     {
/* 32 */       return 0.0F;
/*    */     }
/*    */ 
/*    */     
/* 36 */     return (this.additionalDifficulty > 4.0F) ? 1.0F : ((this.additionalDifficulty - 2.0F) / 2.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private float calculateAdditionalDifficulty(EnumDifficulty difficulty, long worldTime, long chunkInhabitedTime, float moonPhaseFactor) {
/* 42 */     if (difficulty == EnumDifficulty.PEACEFUL)
/*    */     {
/* 44 */       return 0.0F;
/*    */     }
/*    */ 
/*    */     
/* 48 */     boolean flag = (difficulty == EnumDifficulty.HARD);
/* 49 */     float f = 0.75F;
/* 50 */     float f1 = MathHelper.clamp(((float)worldTime + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
/* 51 */     f += f1;
/* 52 */     float f2 = 0.0F;
/* 53 */     f2 += MathHelper.clamp((float)chunkInhabitedTime / 3600000.0F, 0.0F, 1.0F) * (flag ? 1.0F : 0.75F);
/* 54 */     f2 += MathHelper.clamp(moonPhaseFactor * 0.25F, 0.0F, f1);
/*    */     
/* 56 */     if (difficulty == EnumDifficulty.EASY)
/*    */     {
/* 58 */       f2 *= 0.5F;
/*    */     }
/*    */     
/* 61 */     f += f2;
/* 62 */     return difficulty.getDifficultyId() * f;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\DifficultyInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */