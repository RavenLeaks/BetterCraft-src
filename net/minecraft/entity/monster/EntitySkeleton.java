/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.entity.projectile.EntitySpectralArrow;
/*    */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.storage.loot.LootTableList;
/*    */ 
/*    */ public class EntitySkeleton
/*    */   extends AbstractSkeleton {
/*    */   public EntitySkeleton(World worldIn) {
/* 23 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesSkeleton(DataFixer fixer) {
/* 28 */     EntityLiving.registerFixesMob(fixer, EntitySkeleton.class);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getLootTable() {
/* 34 */     return LootTableList.ENTITIES_SKELETON;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getAmbientSound() {
/* 39 */     return SoundEvents.ENTITY_SKELETON_AMBIENT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 44 */     return SoundEvents.ENTITY_SKELETON_HURT;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getDeathSound() {
/* 49 */     return SoundEvents.ENTITY_SKELETON_DEATH;
/*    */   }
/*    */ 
/*    */   
/*    */   SoundEvent func_190727_o() {
/* 54 */     return SoundEvents.ENTITY_SKELETON_STEP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDeath(DamageSource cause) {
/* 62 */     super.onDeath(cause);
/*    */     
/* 64 */     if (cause.getEntity() instanceof EntityCreeper) {
/*    */       
/* 66 */       EntityCreeper entitycreeper = (EntityCreeper)cause.getEntity();
/*    */       
/* 68 */       if (entitycreeper.getPowered() && entitycreeper.isAIEnabled()) {
/*    */         
/* 70 */         entitycreeper.incrementDroppedSkulls();
/* 71 */         entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0.0F);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected EntityArrow func_190726_a(float p_190726_1_) {
/* 78 */     ItemStack itemstack = getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
/*    */     
/* 80 */     if (itemstack.getItem() == Items.SPECTRAL_ARROW) {
/*    */       
/* 82 */       EntitySpectralArrow entityspectralarrow = new EntitySpectralArrow(this.world, (EntityLivingBase)this);
/* 83 */       entityspectralarrow.func_190547_a((EntityLivingBase)this, p_190726_1_);
/* 84 */       return (EntityArrow)entityspectralarrow;
/*    */     } 
/*    */ 
/*    */     
/* 88 */     EntityArrow entityarrow = super.func_190726_a(p_190726_1_);
/*    */     
/* 90 */     if (itemstack.getItem() == Items.TIPPED_ARROW && entityarrow instanceof EntityTippedArrow)
/*    */     {
/* 92 */       ((EntityTippedArrow)entityarrow).setPotionEffect(itemstack);
/*    */     }
/*    */     
/* 95 */     return entityarrow;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntitySkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */