/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import net.minecraft.entity.EnumCreatureAttribute;
/*    */ import net.minecraft.network.datasync.DataParameter;
/*    */ import net.minecraft.network.datasync.DataSerializers;
/*    */ import net.minecraft.network.datasync.EntityDataManager;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class AbstractIllager
/*    */   extends EntityMob {
/* 11 */   protected static final DataParameter<Byte> field_193080_a = EntityDataManager.createKey(AbstractIllager.class, DataSerializers.BYTE);
/*    */ 
/*    */   
/*    */   public AbstractIllager(World p_i47509_1_) {
/* 15 */     super(p_i47509_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void entityInit() {
/* 20 */     super.entityInit();
/* 21 */     this.dataManager.register(field_193080_a, Byte.valueOf((byte)0));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_193078_a(int p_193078_1_) {
/* 26 */     int i = ((Byte)this.dataManager.get(field_193080_a)).byteValue();
/* 27 */     return ((i & p_193078_1_) != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_193079_a(int p_193079_1_, boolean p_193079_2_) {
/* 32 */     int i = ((Byte)this.dataManager.get(field_193080_a)).byteValue();
/*    */     
/* 34 */     if (p_193079_2_) {
/*    */       
/* 36 */       i |= p_193079_1_;
/*    */     }
/*    */     else {
/*    */       
/* 40 */       i &= p_193079_1_ ^ 0xFFFFFFFF;
/*    */     } 
/*    */     
/* 43 */     this.dataManager.set(field_193080_a, Byte.valueOf((byte)(i & 0xFF)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumCreatureAttribute getCreatureAttribute() {
/* 51 */     return EnumCreatureAttribute.ILLAGER;
/*    */   }
/*    */ 
/*    */   
/*    */   public IllagerArmPose func_193077_p() {
/* 56 */     return IllagerArmPose.CROSSED;
/*    */   }
/*    */   
/*    */   public enum IllagerArmPose
/*    */   {
/* 61 */     CROSSED,
/* 62 */     ATTACKING,
/* 63 */     SPELLCASTING,
/* 64 */     BOW_AND_ARROW;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\AbstractIllager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */