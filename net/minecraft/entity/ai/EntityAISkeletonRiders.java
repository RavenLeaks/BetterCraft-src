/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.effect.EntityLightningBolt;
/*    */ import net.minecraft.entity.monster.EntitySkeleton;
/*    */ import net.minecraft.entity.passive.AbstractHorse;
/*    */ import net.minecraft.entity.passive.EntitySkeletonHorse;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.DifficultyInstance;
/*    */ 
/*    */ public class EntityAISkeletonRiders
/*    */   extends EntityAIBase {
/*    */   private final EntitySkeletonHorse horse;
/*    */   
/*    */   public EntityAISkeletonRiders(EntitySkeletonHorse horseIn) {
/* 21 */     this.horse = horseIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 29 */     return this.horse.world.isAnyPlayerWithinRangeAt(this.horse.posX, this.horse.posY, this.horse.posZ, 10.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 37 */     DifficultyInstance difficultyinstance = this.horse.world.getDifficultyForLocation(new BlockPos((Entity)this.horse));
/* 38 */     this.horse.func_190691_p(false);
/* 39 */     this.horse.setHorseTamed(true);
/* 40 */     this.horse.setGrowingAge(0);
/* 41 */     this.horse.world.addWeatherEffect((Entity)new EntityLightningBolt(this.horse.world, this.horse.posX, this.horse.posY, this.horse.posZ, true));
/* 42 */     EntitySkeleton entityskeleton = createSkeleton(difficultyinstance, (AbstractHorse)this.horse);
/* 43 */     entityskeleton.startRiding((Entity)this.horse);
/*    */     
/* 45 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 47 */       AbstractHorse abstracthorse = createHorse(difficultyinstance);
/* 48 */       EntitySkeleton entityskeleton1 = createSkeleton(difficultyinstance, abstracthorse);
/* 49 */       entityskeleton1.startRiding((Entity)abstracthorse);
/* 50 */       abstracthorse.addVelocity(this.horse.getRNG().nextGaussian() * 0.5D, 0.0D, this.horse.getRNG().nextGaussian() * 0.5D);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private AbstractHorse createHorse(DifficultyInstance p_188515_1_) {
/* 56 */     EntitySkeletonHorse entityskeletonhorse = new EntitySkeletonHorse(this.horse.world);
/* 57 */     entityskeletonhorse.onInitialSpawn(p_188515_1_, null);
/* 58 */     entityskeletonhorse.setPosition(this.horse.posX, this.horse.posY, this.horse.posZ);
/* 59 */     entityskeletonhorse.hurtResistantTime = 60;
/* 60 */     entityskeletonhorse.enablePersistence();
/* 61 */     entityskeletonhorse.setHorseTamed(true);
/* 62 */     entityskeletonhorse.setGrowingAge(0);
/* 63 */     entityskeletonhorse.world.spawnEntityInWorld((Entity)entityskeletonhorse);
/* 64 */     return (AbstractHorse)entityskeletonhorse;
/*    */   }
/*    */ 
/*    */   
/*    */   private EntitySkeleton createSkeleton(DifficultyInstance p_188514_1_, AbstractHorse p_188514_2_) {
/* 69 */     EntitySkeleton entityskeleton = new EntitySkeleton(p_188514_2_.world);
/* 70 */     entityskeleton.onInitialSpawn(p_188514_1_, null);
/* 71 */     entityskeleton.setPosition(p_188514_2_.posX, p_188514_2_.posY, p_188514_2_.posZ);
/* 72 */     entityskeleton.hurtResistantTime = 60;
/* 73 */     entityskeleton.enablePersistence();
/*    */     
/* 75 */     if (entityskeleton.getItemStackFromSlot(EntityEquipmentSlot.HEAD).func_190926_b())
/*    */     {
/* 77 */       entityskeleton.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((Item)Items.IRON_HELMET));
/*    */     }
/*    */     
/* 80 */     entityskeleton.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, EnchantmentHelper.addRandomEnchantment(entityskeleton.getRNG(), entityskeleton.getHeldItemMainhand(), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * entityskeleton.getRNG().nextInt(18)), false));
/* 81 */     entityskeleton.setItemStackToSlot(EntityEquipmentSlot.HEAD, EnchantmentHelper.addRandomEnchantment(entityskeleton.getRNG(), entityskeleton.getItemStackFromSlot(EntityEquipmentSlot.HEAD), (int)(5.0F + p_188514_1_.getClampedAdditionalDifficulty() * entityskeleton.getRNG().nextInt(18)), false));
/* 82 */     entityskeleton.world.spawnEntityInWorld((Entity)entityskeleton);
/* 83 */     return entityskeleton;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAISkeletonRiders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */