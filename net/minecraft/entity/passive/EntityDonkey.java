/*    */ package net.minecraft.entity.passive;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.EntityAgeable;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.storage.loot.LootTableList;
/*    */ 
/*    */ public class EntityDonkey
/*    */   extends AbstractChestHorse
/*    */ {
/*    */   public EntityDonkey(World p_i47298_1_) {
/* 17 */     super(p_i47298_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void func_190699_b(DataFixer p_190699_0_) {
/* 22 */     AbstractChestHorse.func_190694_b(p_190699_0_, EntityDonkey.class);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getLootTable() {
/* 28 */     return LootTableList.field_191190_H;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 33 */     super.getAmbientSound();
/* 34 */     return SoundEvents.ENTITY_DONKEY_AMBIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 39 */     super.getDeathSound();
/* 40 */     return SoundEvents.ENTITY_DONKEY_DEATH;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 45 */     super.getHurtSound(p_184601_1_);
/* 46 */     return SoundEvents.ENTITY_DONKEY_HURT;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 54 */     if (otherAnimal == this)
/*    */     {
/* 56 */       return false;
/*    */     }
/* 58 */     if (!(otherAnimal instanceof EntityDonkey) && !(otherAnimal instanceof EntityHorse))
/*    */     {
/* 60 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 64 */     return (canMate() && ((AbstractHorse)otherAnimal).canMate());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityAgeable createChild(EntityAgeable ageable) {
/* 70 */     AbstractHorse abstracthorse = (ageable instanceof EntityHorse) ? new EntityMule(this.world) : new EntityDonkey(this.world);
/* 71 */     func_190681_a(ageable, abstracthorse);
/* 72 */     return abstracthorse;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityDonkey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */