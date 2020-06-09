/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityEvokerFangs;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityEvoker
/*     */   extends EntitySpellcasterIllager {
/*     */   public EntityEvoker(World p_i47287_1_) {
/*  42 */     super(p_i47287_1_);
/*  43 */     setSize(0.6F, 1.95F);
/*  44 */     this.experienceValue = 10;
/*     */   }
/*     */   private EntitySheep field_190763_bw;
/*     */   
/*     */   protected void initEntityAI() {
/*  49 */     super.initEntityAI();
/*  50 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  51 */     this.tasks.addTask(1, new AICastingSpell(null));
/*  52 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 0.6D, 1.0D));
/*  53 */     this.tasks.addTask(4, new AISummonSpell(null));
/*  54 */     this.tasks.addTask(5, new AIAttackSpell(null));
/*  55 */     this.tasks.addTask(6, new AIWololoSpell());
/*  56 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWander(this, 0.6D));
/*  57 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 3.0F, 1.0F));
/*  58 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
/*  59 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[] { EntityEvoker.class }));
/*  60 */     this.targetTasks.addTask(2, (EntityAIBase)(new EntityAINearestAttackableTarget(this, EntityPlayer.class, true)).func_190882_b(300));
/*  61 */     this.targetTasks.addTask(3, (EntityAIBase)(new EntityAINearestAttackableTarget(this, EntityVillager.class, false)).func_190882_b(300));
/*  62 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityIronGolem.class, false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  67 */     super.applyEntityAttributes();
/*  68 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
/*  69 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(12.0D);
/*  70 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  75 */     super.entityInit();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190759_b(DataFixer p_190759_0_) {
/*  80 */     EntityLiving.registerFixesMob(p_190759_0_, EntityEvoker.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  88 */     super.readEntityFromNBT(compound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  96 */     super.writeEntityToNBT(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation getLootTable() {
/* 101 */     return LootTableList.field_191185_au;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 106 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 114 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnSameTeam(Entity entityIn) {
/* 122 */     if (entityIn == null)
/*     */     {
/* 124 */       return false;
/*     */     }
/* 126 */     if (entityIn == this)
/*     */     {
/* 128 */       return true;
/*     */     }
/* 130 */     if (super.isOnSameTeam(entityIn))
/*     */     {
/* 132 */       return true;
/*     */     }
/* 134 */     if (entityIn instanceof EntityVex)
/*     */     {
/* 136 */       return isOnSameTeam((Entity)((EntityVex)entityIn).func_190645_o());
/*     */     }
/* 138 */     if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER)
/*     */     {
/* 140 */       return (getTeam() == null && entityIn.getTeam() == null);
/*     */     }
/*     */ 
/*     */     
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 150 */     return SoundEvents.field_191243_bm;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 155 */     return SoundEvents.field_191245_bo;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 160 */     return SoundEvents.field_191246_bp;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190748_a(@Nullable EntitySheep p_190748_1_) {
/* 165 */     this.field_190763_bw = p_190748_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private EntitySheep func_190751_dj() {
/* 171 */     return this.field_190763_bw;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent func_193086_dk() {
/* 176 */     return SoundEvents.field_191244_bn;
/*     */   }
/*     */ 
/*     */   
/*     */   class AIAttackSpell
/*     */     extends EntitySpellcasterIllager.AIUseSpell
/*     */   {
/*     */     private AIAttackSpell() {}
/*     */ 
/*     */     
/*     */     protected int func_190869_f() {
/* 187 */       return 40;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int func_190872_i() {
/* 192 */       return 100;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_190868_j() {
/* 197 */       EntityLivingBase entitylivingbase = EntityEvoker.this.getAttackTarget();
/* 198 */       double d0 = Math.min(entitylivingbase.posY, EntityEvoker.this.posY);
/* 199 */       double d1 = Math.max(entitylivingbase.posY, EntityEvoker.this.posY) + 1.0D;
/* 200 */       float f = (float)MathHelper.atan2(entitylivingbase.posZ - EntityEvoker.this.posZ, entitylivingbase.posX - EntityEvoker.this.posX);
/*     */       
/* 202 */       if (EntityEvoker.this.getDistanceSqToEntity((Entity)entitylivingbase) < 9.0D) {
/*     */         
/* 204 */         for (int i = 0; i < 5; i++) {
/*     */           
/* 206 */           float f1 = f + i * 3.1415927F * 0.4F;
/* 207 */           func_190876_a(EntityEvoker.this.posX + MathHelper.cos(f1) * 1.5D, EntityEvoker.this.posZ + MathHelper.sin(f1) * 1.5D, d0, d1, f1, 0);
/*     */         } 
/*     */         
/* 210 */         for (int k = 0; k < 8; k++)
/*     */         {
/* 212 */           float f2 = f + k * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
/* 213 */           func_190876_a(EntityEvoker.this.posX + MathHelper.cos(f2) * 2.5D, EntityEvoker.this.posZ + MathHelper.sin(f2) * 2.5D, d0, d1, f2, 3);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 218 */         for (int l = 0; l < 16; l++) {
/*     */           
/* 220 */           double d2 = 1.25D * (l + 1);
/* 221 */           int j = 1 * l;
/* 222 */           func_190876_a(EntityEvoker.this.posX + MathHelper.cos(f) * d2, EntityEvoker.this.posZ + MathHelper.sin(f) * d2, d0, d1, f, j);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_190876_a(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_, float p_190876_9_, int p_190876_10_) {
/* 229 */       BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
/* 230 */       boolean flag = false;
/* 231 */       double d0 = 0.0D;
/*     */ 
/*     */       
/*     */       do {
/* 235 */         if (!EntityEvoker.this.world.isBlockNormalCube(blockpos, true) && EntityEvoker.this.world.isBlockNormalCube(blockpos.down(), true)) {
/*     */           
/* 237 */           if (!EntityEvoker.this.world.isAirBlock(blockpos)) {
/*     */             
/* 239 */             IBlockState iblockstate = EntityEvoker.this.world.getBlockState(blockpos);
/* 240 */             AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox((IBlockAccess)EntityEvoker.this.world, blockpos);
/*     */             
/* 242 */             if (axisalignedbb != null)
/*     */             {
/* 244 */               d0 = axisalignedbb.maxY;
/*     */             }
/*     */           } 
/*     */           
/* 248 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/* 252 */         blockpos = blockpos.down();
/*     */       }
/* 254 */       while (blockpos.getY() >= MathHelper.floor(p_190876_5_) - 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 260 */       if (flag) {
/*     */         
/* 262 */         EntityEvokerFangs entityevokerfangs = new EntityEvokerFangs(EntityEvoker.this.world, p_190876_1_, blockpos.getY() + d0, p_190876_3_, p_190876_9_, p_190876_10_, (EntityLivingBase)EntityEvoker.this);
/* 263 */         EntityEvoker.this.world.spawnEntityInWorld((Entity)entityevokerfangs);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected SoundEvent func_190871_k() {
/* 269 */       return SoundEvents.field_191247_bq;
/*     */     }
/*     */ 
/*     */     
/*     */     protected EntitySpellcasterIllager.SpellType func_193320_l() {
/* 274 */       return EntitySpellcasterIllager.SpellType.FANGS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class AICastingSpell
/*     */     extends EntitySpellcasterIllager.AICastingApell
/*     */   {
/*     */     private AICastingSpell() {}
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 286 */       if (EntityEvoker.this.getAttackTarget() != null) {
/*     */         
/* 288 */         EntityEvoker.this.getLookHelper().setLookPositionWithEntity((Entity)EntityEvoker.this.getAttackTarget(), EntityEvoker.this.getHorizontalFaceSpeed(), EntityEvoker.this.getVerticalFaceSpeed());
/*     */       }
/* 290 */       else if (EntityEvoker.this.func_190751_dj() != null) {
/*     */         
/* 292 */         EntityEvoker.this.getLookHelper().setLookPositionWithEntity((Entity)EntityEvoker.this.func_190751_dj(), EntityEvoker.this.getHorizontalFaceSpeed(), EntityEvoker.this.getVerticalFaceSpeed());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class AISummonSpell
/*     */     extends EntitySpellcasterIllager.AIUseSpell
/*     */   {
/*     */     private AISummonSpell() {}
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 305 */       if (!super.shouldExecute())
/*     */       {
/* 307 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 311 */       int i = EntityEvoker.this.world.getEntitiesWithinAABB(EntityVex.class, EntityEvoker.this.getEntityBoundingBox().expandXyz(16.0D)).size();
/* 312 */       return (EntityEvoker.this.rand.nextInt(8) + 1 > i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected int func_190869_f() {
/* 318 */       return 100;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int func_190872_i() {
/* 323 */       return 340;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_190868_j() {
/* 328 */       for (int i = 0; i < 3; i++) {
/*     */         
/* 330 */         BlockPos blockpos = (new BlockPos((Entity)EntityEvoker.this)).add(-2 + EntityEvoker.this.rand.nextInt(5), 1, -2 + EntityEvoker.this.rand.nextInt(5));
/* 331 */         EntityVex entityvex = new EntityVex(EntityEvoker.this.world);
/* 332 */         entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
/* 333 */         entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
/* 334 */         entityvex.func_190658_a((EntityLiving)EntityEvoker.this);
/* 335 */         entityvex.func_190651_g(blockpos);
/* 336 */         entityvex.func_190653_a(20 * (30 + EntityEvoker.this.rand.nextInt(90)));
/* 337 */         EntityEvoker.this.world.spawnEntityInWorld((Entity)entityvex);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected SoundEvent func_190871_k() {
/* 343 */       return SoundEvents.field_191248_br;
/*     */     }
/*     */ 
/*     */     
/*     */     protected EntitySpellcasterIllager.SpellType func_193320_l() {
/* 348 */       return EntitySpellcasterIllager.SpellType.SUMMON_VEX;
/*     */     }
/*     */   }
/*     */   
/*     */   public class AIWololoSpell
/*     */     extends EntitySpellcasterIllager.AIUseSpell {
/* 354 */     final Predicate<EntitySheep> field_190879_a = new Predicate<EntitySheep>()
/*     */       {
/*     */         public boolean apply(EntitySheep p_apply_1_)
/*     */         {
/* 358 */           return (p_apply_1_.getFleeceColor() == EnumDyeColor.BLUE);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 368 */       if (EntityEvoker.this.getAttackTarget() != null)
/*     */       {
/* 370 */         return false;
/*     */       }
/* 372 */       if (EntityEvoker.this.func_193082_dl())
/*     */       {
/* 374 */         return false;
/*     */       }
/* 376 */       if (EntityEvoker.this.ticksExisted < this.field_193322_d)
/*     */       {
/* 378 */         return false;
/*     */       }
/* 380 */       if (!EntityEvoker.this.world.getGameRules().getBoolean("mobGriefing"))
/*     */       {
/* 382 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 386 */       List<EntitySheep> list = EntityEvoker.this.world.getEntitiesWithinAABB(EntitySheep.class, EntityEvoker.this.getEntityBoundingBox().expand(16.0D, 4.0D, 16.0D), this.field_190879_a);
/*     */       
/* 388 */       if (list.isEmpty())
/*     */       {
/* 390 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 394 */       EntityEvoker.this.func_190748_a(list.get(EntityEvoker.this.rand.nextInt(list.size())));
/* 395 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 402 */       return (EntityEvoker.this.func_190751_dj() != null && this.field_193321_c > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 407 */       super.resetTask();
/* 408 */       EntityEvoker.this.func_190748_a((EntitySheep)null);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_190868_j() {
/* 413 */       EntitySheep entitysheep = EntityEvoker.this.func_190751_dj();
/*     */       
/* 415 */       if (entitysheep != null && entitysheep.isEntityAlive())
/*     */       {
/* 417 */         entitysheep.setFleeceColor(EnumDyeColor.RED);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected int func_190867_m() {
/* 423 */       return 40;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int func_190869_f() {
/* 428 */       return 60;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int func_190872_i() {
/* 433 */       return 140;
/*     */     }
/*     */ 
/*     */     
/*     */     protected SoundEvent func_190871_k() {
/* 438 */       return SoundEvents.field_191249_bs;
/*     */     }
/*     */ 
/*     */     
/*     */     protected EntitySpellcasterIllager.SpellType func_193320_l() {
/* 443 */       return EntitySpellcasterIllager.SpellType.WOLOLO;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityEvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */