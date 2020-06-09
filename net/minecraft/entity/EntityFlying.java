/*    */ package net.minecraft.entity;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class EntityFlying
/*    */   extends EntityLiving
/*    */ {
/*    */   public EntityFlying(World worldIn) {
/* 12 */     super(worldIn);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void fall(float distance, float damageMultiplier) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
/*    */ 
/*    */   
/*    */   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
/* 25 */     if (isInWater()) {
/*    */       
/* 27 */       func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, 0.02F);
/* 28 */       moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 29 */       this.motionX *= 0.800000011920929D;
/* 30 */       this.motionY *= 0.800000011920929D;
/* 31 */       this.motionZ *= 0.800000011920929D;
/*    */     }
/* 33 */     else if (isInLava()) {
/*    */       
/* 35 */       func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, 0.02F);
/* 36 */       moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 37 */       this.motionX *= 0.5D;
/* 38 */       this.motionY *= 0.5D;
/* 39 */       this.motionZ *= 0.5D;
/*    */     }
/*    */     else {
/*    */       
/* 43 */       float f = 0.91F;
/*    */       
/* 45 */       if (this.onGround)
/*    */       {
/* 47 */         f = (this.world.getBlockState(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor((getEntityBoundingBox()).minY) - 1, MathHelper.floor(this.posZ))).getBlock()).slipperiness * 0.91F;
/*    */       }
/*    */       
/* 50 */       float f1 = 0.16277136F / f * f * f;
/* 51 */       func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, this.onGround ? (0.1F * f1) : 0.02F);
/* 52 */       f = 0.91F;
/*    */       
/* 54 */       if (this.onGround)
/*    */       {
/* 56 */         f = (this.world.getBlockState(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor((getEntityBoundingBox()).minY) - 1, MathHelper.floor(this.posZ))).getBlock()).slipperiness * 0.91F;
/*    */       }
/*    */       
/* 59 */       moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 60 */       this.motionX *= f;
/* 61 */       this.motionY *= f;
/* 62 */       this.motionZ *= f;
/*    */     } 
/*    */     
/* 65 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 66 */     double d1 = this.posX - this.prevPosX;
/* 67 */     double d0 = this.posZ - this.prevPosZ;
/* 68 */     float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
/*    */     
/* 70 */     if (f2 > 1.0F)
/*    */     {
/* 72 */       f2 = 1.0F;
/*    */     }
/*    */     
/* 75 */     this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
/* 76 */     this.limbSwing += this.limbSwingAmount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOnLadder() {
/* 84 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityFlying.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */