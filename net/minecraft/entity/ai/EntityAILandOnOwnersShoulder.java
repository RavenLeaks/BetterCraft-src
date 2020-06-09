/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityShoulderRiding;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class EntityAILandOnOwnersShoulder
/*    */   extends EntityAIBase
/*    */ {
/*    */   private final EntityShoulderRiding field_192382_a;
/*    */   private EntityPlayer field_192383_b;
/*    */   private boolean field_192384_c;
/*    */   
/*    */   public EntityAILandOnOwnersShoulder(EntityShoulderRiding p_i47415_1_) {
/* 15 */     this.field_192382_a = p_i47415_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 23 */     EntityLivingBase entitylivingbase = this.field_192382_a.getOwner();
/* 24 */     boolean flag = (entitylivingbase != null && !((EntityPlayer)entitylivingbase).isSpectator() && !((EntityPlayer)entitylivingbase).capabilities.isFlying && !entitylivingbase.isInWater());
/* 25 */     return (!this.field_192382_a.isSitting() && flag && this.field_192382_a.func_191995_du());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInterruptible() {
/* 34 */     return !this.field_192384_c;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 42 */     this.field_192383_b = (EntityPlayer)this.field_192382_a.getOwner();
/* 43 */     this.field_192384_c = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 51 */     if (!this.field_192384_c && !this.field_192382_a.isSitting() && !this.field_192382_a.getLeashed())
/*    */     {
/* 53 */       if (this.field_192382_a.getEntityBoundingBox().intersectsWith(this.field_192383_b.getEntityBoundingBox()))
/*    */       {
/* 55 */         this.field_192384_c = this.field_192382_a.func_191994_f(this.field_192383_b);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAILandOnOwnersShoulder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */