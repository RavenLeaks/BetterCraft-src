/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSilverfish;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackMelee;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntitySilverfish
/*     */   extends EntityMob {
/*     */   private AISummonSilverfish summonSilverfish;
/*     */   
/*     */   public EntitySilverfish(World worldIn) {
/*  36 */     super(worldIn);
/*  37 */     setSize(0.4F, 0.3F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesSilverfish(DataFixer fixer) {
/*  42 */     EntityLiving.registerFixesMob(fixer, EntitySilverfish.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  47 */     this.summonSilverfish = new AISummonSilverfish(this);
/*  48 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  49 */     this.tasks.addTask(3, this.summonSilverfish);
/*  50 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackMelee(this, 1.0D, false));
/*  51 */     this.tasks.addTask(5, (EntityAIBase)new AIHideInStone(this));
/*  52 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[0]));
/*  53 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/*  61 */     return 0.1D;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/*  66 */     return 0.1F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  71 */     super.applyEntityAttributes();
/*  72 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
/*  73 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
/*  74 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  88 */     return SoundEvents.ENTITY_SILVERFISH_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  93 */     return SoundEvents.ENTITY_SILVERFISH_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  98 */     return SoundEvents.ENTITY_SILVERFISH_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 103 */     playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 111 */     if (isEntityInvulnerable(source))
/*     */     {
/* 113 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 117 */     if ((source instanceof net.minecraft.util.EntityDamageSource || source == DamageSource.magic) && this.summonSilverfish != null)
/*     */     {
/* 119 */       this.summonSilverfish.notifyHurt();
/*     */     }
/*     */     
/* 122 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 129 */     return LootTableList.ENTITIES_SILVERFISH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 137 */     this.renderYawOffset = this.rotationYaw;
/* 138 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderYawOffset(float offset) {
/* 146 */     this.rotationYaw = offset;
/* 147 */     super.setRenderYawOffset(offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/* 152 */     return (this.world.getBlockState(pos.down()).getBlock() == Blocks.STONE) ? 10.0F : super.getBlockPathWeight(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 168 */     if (super.getCanSpawnHere()) {
/*     */       
/* 170 */       EntityPlayer entityplayer = this.world.getNearestPlayerNotCreative((Entity)this, 5.0D);
/* 171 */       return (entityplayer == null);
/*     */     } 
/*     */ 
/*     */     
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 184 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */   
/*     */   static class AIHideInStone
/*     */     extends EntityAIWander
/*     */   {
/*     */     private EnumFacing facing;
/*     */     private boolean doMerge;
/*     */     
/*     */     public AIHideInStone(EntitySilverfish silverfishIn) {
/* 194 */       super(silverfishIn, 1.0D, 10);
/* 195 */       setMutexBits(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 200 */       if (this.entity.getAttackTarget() != null)
/*     */       {
/* 202 */         return false;
/*     */       }
/* 204 */       if (!this.entity.getNavigator().noPath())
/*     */       {
/* 206 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 210 */       Random random = this.entity.getRNG();
/*     */       
/* 212 */       if (this.entity.world.getGameRules().getBoolean("mobGriefing") && random.nextInt(10) == 0) {
/*     */         
/* 214 */         this.facing = EnumFacing.random(random);
/* 215 */         BlockPos blockpos = (new BlockPos(this.entity.posX, this.entity.posY + 0.5D, this.entity.posZ)).offset(this.facing);
/* 216 */         IBlockState iblockstate = this.entity.world.getBlockState(blockpos);
/*     */         
/* 218 */         if (BlockSilverfish.canContainSilverfish(iblockstate)) {
/*     */           
/* 220 */           this.doMerge = true;
/* 221 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 225 */       this.doMerge = false;
/* 226 */       return super.shouldExecute();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 232 */       return this.doMerge ? false : super.continueExecuting();
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 237 */       if (!this.doMerge) {
/*     */         
/* 239 */         super.startExecuting();
/*     */       }
/*     */       else {
/*     */         
/* 243 */         World world = this.entity.world;
/* 244 */         BlockPos blockpos = (new BlockPos(this.entity.posX, this.entity.posY + 0.5D, this.entity.posZ)).offset(this.facing);
/* 245 */         IBlockState iblockstate = world.getBlockState(blockpos);
/*     */         
/* 247 */         if (BlockSilverfish.canContainSilverfish(iblockstate)) {
/*     */           
/* 249 */           world.setBlockState(blockpos, Blocks.MONSTER_EGG.getDefaultState().withProperty((IProperty)BlockSilverfish.VARIANT, (Comparable)BlockSilverfish.EnumType.forModelBlock(iblockstate)), 3);
/* 250 */           this.entity.spawnExplosionParticle();
/* 251 */           this.entity.setDead();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISummonSilverfish
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySilverfish silverfish;
/*     */     private int lookForFriends;
/*     */     
/*     */     public AISummonSilverfish(EntitySilverfish silverfishIn) {
/* 264 */       this.silverfish = silverfishIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void notifyHurt() {
/* 269 */       if (this.lookForFriends == 0)
/*     */       {
/* 271 */         this.lookForFriends = 20;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 277 */       return (this.lookForFriends > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 282 */       this.lookForFriends--;
/*     */       
/* 284 */       if (this.lookForFriends <= 0) {
/*     */         
/* 286 */         World world = this.silverfish.world;
/* 287 */         Random random = this.silverfish.getRNG();
/* 288 */         BlockPos blockpos = new BlockPos((Entity)this.silverfish);
/*     */         
/* 290 */         for (int i = 0; i <= 5 && i >= -5; i = ((i <= 0) ? 1 : 0) - i) {
/*     */           
/* 292 */           for (int j = 0; j <= 10 && j >= -10; j = ((j <= 0) ? 1 : 0) - j) {
/*     */             
/* 294 */             for (int k = 0; k <= 10 && k >= -10; k = ((k <= 0) ? 1 : 0) - k) {
/*     */               
/* 296 */               BlockPos blockpos1 = blockpos.add(j, i, k);
/* 297 */               IBlockState iblockstate = world.getBlockState(blockpos1);
/*     */               
/* 299 */               if (iblockstate.getBlock() == Blocks.MONSTER_EGG) {
/*     */                 
/* 301 */                 if (world.getGameRules().getBoolean("mobGriefing")) {
/*     */                   
/* 303 */                   world.destroyBlock(blockpos1, true);
/*     */                 }
/*     */                 else {
/*     */                   
/* 307 */                   world.setBlockState(blockpos1, ((BlockSilverfish.EnumType)iblockstate.getValue((IProperty)BlockSilverfish.VARIANT)).getModelBlock(), 3);
/*     */                 } 
/*     */                 
/* 310 */                 if (random.nextBoolean())
/*     */                   return; 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntitySilverfish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */