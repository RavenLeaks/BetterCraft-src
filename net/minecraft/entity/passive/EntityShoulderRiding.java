/*    */ package net.minecraft.entity.passive;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class EntityShoulderRiding
/*    */   extends EntityTameable {
/*    */   private int field_191996_bB;
/*    */   
/*    */   public EntityShoulderRiding(World p_i47410_1_) {
/* 13 */     super(p_i47410_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_191994_f(EntityPlayer p_191994_1_) {
/* 18 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 19 */     nbttagcompound.setString("id", getEntityString());
/* 20 */     writeToNBT(nbttagcompound);
/*    */     
/* 22 */     if (p_191994_1_.func_192027_g(nbttagcompound)) {
/*    */       
/* 24 */       this.world.removeEntity((Entity)this);
/* 25 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 38 */     this.field_191996_bB++;
/* 39 */     super.onUpdate();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_191995_du() {
/* 44 */     return (this.field_191996_bB > 100);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityShoulderRiding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */