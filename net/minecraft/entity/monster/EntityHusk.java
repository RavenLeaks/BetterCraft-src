/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.storage.loot.LootTableList;
/*    */ 
/*    */ public class EntityHusk
/*    */   extends EntityZombie
/*    */ {
/*    */   public EntityHusk(World p_i47286_1_) {
/* 23 */     super(p_i47286_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void func_190740_b(DataFixer p_190740_0_) {
/* 28 */     EntityLiving.registerFixesMob(p_190740_0_, EntityHusk.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getCanSpawnHere() {
/* 36 */     return (super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos((Entity)this)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_190730_o() {
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 46 */     return SoundEvents.ENTITY_HUSK_AMBIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 51 */     return SoundEvents.ENTITY_HUSK_HURT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 56 */     return SoundEvents.ENTITY_HUSK_DEATH;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent func_190731_di() {
/* 61 */     return SoundEvents.ENTITY_HUSK_STEP;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getLootTable() {
/* 67 */     return LootTableList.field_191182_ar;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean attackEntityAsMob(Entity entityIn) {
/* 72 */     boolean flag = super.attackEntityAsMob(entityIn);
/*    */     
/* 74 */     if (flag && getHeldItemMainhand().func_190926_b() && entityIn instanceof EntityLivingBase) {
/*    */       
/* 76 */       float f = this.world.getDifficultyForLocation(new BlockPos((Entity)this)).getAdditionalDifficulty();
/* 77 */       ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 140 * (int)f));
/*    */     } 
/*    */     
/* 80 */     return flag;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemStack func_190732_dj() {
/* 85 */     return ItemStack.field_190927_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityHusk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */