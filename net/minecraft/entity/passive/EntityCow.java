/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityCow extends EntityAnimal {
/*     */   public EntityCow(World worldIn) {
/*  33 */     super(worldIn);
/*  34 */     setSize(0.9F, 1.4F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesCow(DataFixer fixer) {
/*  39 */     EntityLiving.registerFixesMob(fixer, EntityCow.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  44 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  45 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 2.0D));
/*  46 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  47 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.25D, Items.WHEAT, false));
/*  48 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.25D));
/*  49 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
/*  50 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  51 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  56 */     super.applyEntityAttributes();
/*  57 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
/*  58 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  63 */     return SoundEvents.ENTITY_COW_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  68 */     return SoundEvents.ENTITY_COW_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  73 */     return SoundEvents.ENTITY_COW_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  78 */     playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  86 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/*  92 */     return LootTableList.ENTITIES_COW;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/*  97 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/*  99 */     if (itemstack.getItem() == Items.BUCKET && !player.capabilities.isCreativeMode && !isChild()) {
/*     */       
/* 101 */       player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
/* 102 */       itemstack.func_190918_g(1);
/*     */       
/* 104 */       if (itemstack.func_190926_b()) {
/*     */         
/* 106 */         player.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
/*     */       }
/* 108 */       else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET))) {
/*     */         
/* 110 */         player.dropItem(new ItemStack(Items.MILK_BUCKET), false);
/*     */       } 
/*     */       
/* 113 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     return super.processInteract(player, hand);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityCow createChild(EntityAgeable ageable) {
/* 123 */     return new EntityCow(this.world);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 128 */     return isChild() ? this.height : 1.3F;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityCow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */