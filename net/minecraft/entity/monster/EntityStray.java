/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.storage.loot.LootTableList;
/*    */ 
/*    */ public class EntityStray
/*    */   extends AbstractSkeleton {
/*    */   public EntityStray(World p_i47281_1_) {
/* 22 */     super(p_i47281_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void func_190728_b(DataFixer p_190728_0_) {
/* 27 */     EntityLiving.registerFixesMob(p_190728_0_, EntityStray.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getCanSpawnHere() {
/* 35 */     return (super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos((Entity)this)));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getLootTable() {
/* 41 */     return LootTableList.ENTITIES_STRAY;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 46 */     return SoundEvents.ENTITY_STRAY_AMBIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 51 */     return SoundEvents.ENTITY_STRAY_HURT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 56 */     return SoundEvents.ENTITY_STRAY_DEATH;
/*    */   }
/*    */ 
/*    */   
/*    */   SoundEvent func_190727_o() {
/* 61 */     return SoundEvents.ENTITY_STRAY_STEP;
/*    */   }
/*    */ 
/*    */   
/*    */   protected EntityArrow func_190726_a(float p_190726_1_) {
/* 66 */     EntityArrow entityarrow = super.func_190726_a(p_190726_1_);
/*    */     
/* 68 */     if (entityarrow instanceof EntityTippedArrow)
/*    */     {
/* 70 */       ((EntityTippedArrow)entityarrow).addEffect(new PotionEffect(MobEffects.SLOWNESS, 600));
/*    */     }
/*    */     
/* 73 */     return entityarrow;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityStray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */