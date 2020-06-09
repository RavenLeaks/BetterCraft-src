/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityMob
/*     */   extends EntityCreature
/*     */   implements IMob
/*     */ {
/*     */   public EntityMob(World worldIn) {
/*  26 */     super(worldIn);
/*  27 */     this.experienceValue = 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundCategory getSoundCategory() {
/*  32 */     return SoundCategory.HOSTILE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  41 */     updateArmSwingProgress();
/*  42 */     float f = getBrightness();
/*     */     
/*  44 */     if (f > 0.5F)
/*     */     {
/*  46 */       this.entityAge += 2;
/*     */     }
/*     */     
/*  49 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  57 */     super.onUpdate();
/*     */     
/*  59 */     if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
/*     */     {
/*  61 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSwimSound() {
/*  67 */     return SoundEvents.ENTITY_HOSTILE_SWIM;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSplashSound() {
/*  72 */     return SoundEvents.ENTITY_HOSTILE_SPLASH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  80 */     return isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  85 */     return SoundEvents.ENTITY_HOSTILE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  90 */     return SoundEvents.ENTITY_HOSTILE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getFallSound(int heightIn) {
/*  95 */     return (heightIn > 4) ? SoundEvents.ENTITY_HOSTILE_BIG_FALL : SoundEvents.ENTITY_HOSTILE_SMALL_FALL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 100 */     float f = (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
/* 101 */     int i = 0;
/*     */     
/* 103 */     if (entityIn instanceof EntityLivingBase) {
/*     */       
/* 105 */       f += EnchantmentHelper.getModifierForCreature(getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
/* 106 */       i += EnchantmentHelper.getKnockbackModifier((EntityLivingBase)this);
/*     */     } 
/*     */     
/* 109 */     boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), f);
/*     */     
/* 111 */     if (flag) {
/*     */       
/* 113 */       if (i > 0 && entityIn instanceof EntityLivingBase) {
/*     */         
/* 115 */         ((EntityLivingBase)entityIn).knockBack((Entity)this, i * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
/* 116 */         this.motionX *= 0.6D;
/* 117 */         this.motionZ *= 0.6D;
/*     */       } 
/*     */       
/* 120 */       int j = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)this);
/*     */       
/* 122 */       if (j > 0)
/*     */       {
/* 124 */         entityIn.setFire(j * 4);
/*     */       }
/*     */       
/* 127 */       if (entityIn instanceof EntityPlayer) {
/*     */         
/* 129 */         EntityPlayer entityplayer = (EntityPlayer)entityIn;
/* 130 */         ItemStack itemstack = getHeldItemMainhand();
/* 131 */         ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.field_190927_a;
/*     */         
/* 133 */         if (!itemstack.func_190926_b() && !itemstack1.func_190926_b() && itemstack.getItem() instanceof net.minecraft.item.ItemAxe && itemstack1.getItem() == Items.SHIELD) {
/*     */           
/* 135 */           float f1 = 0.25F + EnchantmentHelper.getEfficiencyModifier((EntityLivingBase)this) * 0.05F;
/*     */           
/* 137 */           if (this.rand.nextFloat() < f1) {
/*     */             
/* 139 */             entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
/* 140 */             this.world.setEntityState((Entity)entityplayer, (byte)30);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       applyEnchantments((EntityLivingBase)this, entityIn);
/*     */     } 
/*     */     
/* 148 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/* 153 */     return 0.5F - this.world.getLightBrightness(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 161 */     BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ);
/*     */     
/* 163 */     if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32))
/*     */     {
/* 165 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 169 */     int i = this.world.getLightFromNeighbors(blockpos);
/*     */     
/* 171 */     if (this.world.isThundering()) {
/*     */       
/* 173 */       int j = this.world.getSkylightSubtracted();
/* 174 */       this.world.setSkylightSubtracted(10);
/* 175 */       i = this.world.getLightFromNeighbors(blockpos);
/* 176 */       this.world.setSkylightSubtracted(j);
/*     */     } 
/*     */     
/* 179 */     return (i <= this.rand.nextInt(8));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 188 */     return (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && isValidLightLevel() && super.getCanSpawnHere());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 193 */     super.applyEntityAttributes();
/* 194 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDropLoot() {
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191990_c(EntityPlayer p_191990_1_) {
/* 207 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityMob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */