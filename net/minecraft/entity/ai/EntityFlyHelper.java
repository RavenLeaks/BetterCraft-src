/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class EntityFlyHelper
/*    */   extends EntityMoveHelper
/*    */ {
/*    */   public EntityFlyHelper(EntityLiving p_i47418_1_) {
/* 11 */     super(p_i47418_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdateMoveHelper() {
/* 16 */     if (this.action == EntityMoveHelper.Action.MOVE_TO) {
/*    */       float f1;
/* 18 */       this.action = EntityMoveHelper.Action.WAIT;
/* 19 */       this.entity.setNoGravity(true);
/* 20 */       double d0 = this.posX - this.entity.posX;
/* 21 */       double d1 = this.posY - this.entity.posY;
/* 22 */       double d2 = this.posZ - this.entity.posZ;
/* 23 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*    */       
/* 25 */       if (d3 < 2.500000277905201E-7D) {
/*    */         
/* 27 */         this.entity.setMoveForward(0.0F);
/* 28 */         this.entity.func_191989_p(0.0F);
/*    */         
/*    */         return;
/*    */       } 
/* 32 */       float f = (float)(MathHelper.atan2(d2, d0) * 57.29577951308232D) - 90.0F;
/* 33 */       this.entity.rotationYaw = limitAngle(this.entity.rotationYaw, f, 10.0F);
/*    */ 
/*    */       
/* 36 */       if (this.entity.onGround) {
/*    */         
/* 38 */         f1 = (float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
/*    */       }
/*    */       else {
/*    */         
/* 42 */         f1 = (float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.field_193334_e).getAttributeValue());
/*    */       } 
/*    */       
/* 45 */       this.entity.setAIMoveSpeed(f1);
/* 46 */       double d4 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/* 47 */       float f2 = (float)-(MathHelper.atan2(d1, d4) * 57.29577951308232D);
/* 48 */       this.entity.rotationPitch = limitAngle(this.entity.rotationPitch, f2, 10.0F);
/* 49 */       this.entity.setMoveForward((d1 > 0.0D) ? f1 : -f1);
/*    */     }
/*    */     else {
/*    */       
/* 53 */       this.entity.setNoGravity(false);
/* 54 */       this.entity.setMoveForward(0.0F);
/* 55 */       this.entity.func_191989_p(0.0F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityFlyHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */