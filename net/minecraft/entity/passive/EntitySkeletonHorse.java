/*     */ package net.minecraft.entity.passive;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAISkeletonRiders;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntitySkeletonHorse extends AbstractHorse {
/*  22 */   private final EntityAISkeletonRiders skeletonTrapAI = new EntityAISkeletonRiders(this);
/*     */   
/*     */   private boolean skeletonTrap;
/*     */   private int skeletonTrapTime;
/*     */   
/*     */   public EntitySkeletonHorse(World p_i47295_1_) {
/*  28 */     super(p_i47295_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  33 */     super.applyEntityAttributes();
/*  34 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
/*  35 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
/*  36 */     getEntityAttribute(JUMP_STRENGTH).setBaseValue(getModifiedJumpStrength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  41 */     super.getAmbientSound();
/*  42 */     return SoundEvents.ENTITY_SKELETON_HORSE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  47 */     super.getDeathSound();
/*  48 */     return SoundEvents.ENTITY_SKELETON_HORSE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  53 */     super.getHurtSound(p_184601_1_);
/*  54 */     return SoundEvents.ENTITY_SKELETON_HORSE_HURT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/*  62 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMountedYOffset() {
/*  70 */     return super.getMountedYOffset() - 0.1875D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/*  76 */     return LootTableList.ENTITIES_SKELETON_HORSE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  85 */     super.onLivingUpdate();
/*     */     
/*  87 */     if (func_190690_dh() && this.skeletonTrapTime++ >= 18000)
/*     */     {
/*  89 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190692_b(DataFixer p_190692_0_) {
/*  95 */     AbstractHorse.func_190683_c(p_190692_0_, EntitySkeletonHorse.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 103 */     super.writeEntityToNBT(compound);
/* 104 */     compound.setBoolean("SkeletonTrap", func_190690_dh());
/* 105 */     compound.setInteger("SkeletonTrapTime", this.skeletonTrapTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 113 */     super.readEntityFromNBT(compound);
/* 114 */     func_190691_p(compound.getBoolean("SkeletonTrap"));
/* 115 */     this.skeletonTrapTime = compound.getInteger("SkeletonTrapTime");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190690_dh() {
/* 120 */     return this.skeletonTrap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190691_p(boolean p_190691_1_) {
/* 125 */     if (p_190691_1_ != this.skeletonTrap) {
/*     */       
/* 127 */       this.skeletonTrap = p_190691_1_;
/*     */       
/* 129 */       if (p_190691_1_) {
/*     */         
/* 131 */         this.tasks.addTask(1, (EntityAIBase)this.skeletonTrapAI);
/*     */       }
/*     */       else {
/*     */         
/* 135 */         this.tasks.removeTask((EntityAIBase)this.skeletonTrapAI);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 142 */     ItemStack itemstack = player.getHeldItem(hand);
/* 143 */     boolean flag = !itemstack.func_190926_b();
/*     */     
/* 145 */     if (flag && itemstack.getItem() == Items.SPAWN_EGG)
/*     */     {
/* 147 */       return super.processInteract(player, hand);
/*     */     }
/* 149 */     if (!isTame())
/*     */     {
/* 151 */       return false;
/*     */     }
/* 153 */     if (isChild())
/*     */     {
/* 155 */       return super.processInteract(player, hand);
/*     */     }
/* 157 */     if (player.isSneaking()) {
/*     */       
/* 159 */       openGUI(player);
/* 160 */       return true;
/*     */     } 
/* 162 */     if (isBeingRidden())
/*     */     {
/* 164 */       return super.processInteract(player, hand);
/*     */     }
/*     */ 
/*     */     
/* 168 */     if (flag) {
/*     */       
/* 170 */       if (itemstack.getItem() == Items.SADDLE && !isHorseSaddled()) {
/*     */         
/* 172 */         openGUI(player);
/* 173 */         return true;
/*     */       } 
/*     */       
/* 176 */       if (itemstack.interactWithEntity(player, (EntityLivingBase)this, hand))
/*     */       {
/* 178 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 182 */     mountTo(player);
/* 183 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntitySkeletonHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */