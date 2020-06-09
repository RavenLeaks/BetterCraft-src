/*    */ package net.minecraft.entity.passive;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.storage.loot.LootTableList;
/*    */ 
/*    */ public class EntityMule
/*    */   extends AbstractChestHorse
/*    */ {
/*    */   public EntityMule(World p_i47296_1_) {
/* 16 */     super(p_i47296_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void func_190700_b(DataFixer p_190700_0_) {
/* 21 */     AbstractChestHorse.func_190694_b(p_190700_0_, EntityMule.class);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getLootTable() {
/* 27 */     return LootTableList.field_191191_I;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 32 */     super.getAmbientSound();
/* 33 */     return SoundEvents.ENTITY_MULE_AMBIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 38 */     super.getDeathSound();
/* 39 */     return SoundEvents.ENTITY_MULE_DEATH;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 44 */     super.getHurtSound(p_184601_1_);
/* 45 */     return SoundEvents.ENTITY_MULE_HURT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_190697_dk() {
/* 50 */     playSound(SoundEvents.field_191259_dX, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityMule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */