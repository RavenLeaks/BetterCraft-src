/*    */ package net.minecraft.entity;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.DamageSource;
/*    */ 
/*    */ 
/*    */ public class MultiPartEntityPart
/*    */   extends Entity
/*    */ {
/*    */   public final IEntityMultiPart entityDragonObj;
/*    */   public final String partName;
/*    */   
/*    */   public MultiPartEntityPart(IEntityMultiPart parent, String partName, float base, float sizeHeight) {
/* 14 */     super(parent.getWorld());
/* 15 */     setSize(base, sizeHeight);
/* 16 */     this.entityDragonObj = parent;
/* 17 */     this.partName = partName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void entityInit() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readEntityFromNBT(NBTTagCompound compound) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeEntityToNBT(NBTTagCompound compound) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeCollidedWith() {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 51 */     return isEntityInvulnerable(source) ? false : this.entityDragonObj.attackEntityFromPart(this, source, amount);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEntityEqual(Entity entityIn) {
/* 59 */     return !(this != entityIn && this.entityDragonObj != entityIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\MultiPartEntityPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */