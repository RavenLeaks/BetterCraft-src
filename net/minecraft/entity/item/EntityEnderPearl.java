/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityEndermite;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEndGateway;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class EntityEnderPearl
/*     */   extends EntityThrowable
/*     */ {
/*     */   private EntityLivingBase thrower;
/*     */   
/*     */   public EntityEnderPearl(World worldIn) {
/*  26 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityEnderPearl(World worldIn, EntityLivingBase throwerIn) {
/*  31 */     super(worldIn, throwerIn);
/*  32 */     this.thrower = throwerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityEnderPearl(World worldIn, double x, double y, double z) {
/*  37 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesEnderPearl(DataFixer fixer) {
/*  42 */     EntityThrowable.registerFixesThrowable(fixer, "ThrownEnderpearl");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult result) {
/*  50 */     EntityLivingBase entitylivingbase = getThrower();
/*     */     
/*  52 */     if (result.entityHit != null) {
/*     */       
/*  54 */       if (result.entityHit == this.thrower) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  59 */       result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)entitylivingbase), 0.0F);
/*     */     } 
/*     */     
/*  62 */     if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
/*     */       
/*  64 */       BlockPos blockpos = result.getBlockPos();
/*  65 */       TileEntity tileentity = this.world.getTileEntity(blockpos);
/*     */       
/*  67 */       if (tileentity instanceof TileEntityEndGateway) {
/*     */         
/*  69 */         TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;
/*     */         
/*  71 */         if (entitylivingbase != null) {
/*     */           
/*  73 */           if (entitylivingbase instanceof EntityPlayerMP)
/*     */           {
/*  75 */             CriteriaTriggers.field_192124_d.func_192193_a((EntityPlayerMP)entitylivingbase, this.world.getBlockState(blockpos));
/*     */           }
/*     */           
/*  78 */           tileentityendgateway.teleportEntity((Entity)entitylivingbase);
/*  79 */           setDead();
/*     */           
/*     */           return;
/*     */         } 
/*  83 */         tileentityendgateway.teleportEntity((Entity)this);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  88 */     for (int i = 0; i < 32; i++)
/*     */     {
/*  90 */       this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian(), new int[0]);
/*     */     }
/*     */     
/*  93 */     if (!this.world.isRemote) {
/*     */       
/*  95 */       if (entitylivingbase instanceof EntityPlayerMP) {
/*     */         
/*  97 */         EntityPlayerMP entityplayermp = (EntityPlayerMP)entitylivingbase;
/*     */         
/*  99 */         if (entityplayermp.connection.getNetworkManager().isChannelOpen() && entityplayermp.world == this.world && !entityplayermp.isPlayerSleeping())
/*     */         {
/* 101 */           if (this.rand.nextFloat() < 0.05F && this.world.getGameRules().getBoolean("doMobSpawning")) {
/*     */             
/* 103 */             EntityEndermite entityendermite = new EntityEndermite(this.world);
/* 104 */             entityendermite.setSpawnedByPlayer(true);
/* 105 */             entityendermite.setLocationAndAngles(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, entitylivingbase.rotationYaw, entitylivingbase.rotationPitch);
/* 106 */             this.world.spawnEntityInWorld((Entity)entityendermite);
/*     */           } 
/*     */           
/* 109 */           if (entitylivingbase.isRiding())
/*     */           {
/* 111 */             entitylivingbase.dismountRidingEntity();
/*     */           }
/*     */           
/* 114 */           entitylivingbase.setPositionAndUpdate(this.posX, this.posY, this.posZ);
/* 115 */           entitylivingbase.fallDistance = 0.0F;
/* 116 */           entitylivingbase.attackEntityFrom(DamageSource.fall, 5.0F);
/*     */         }
/*     */       
/* 119 */       } else if (entitylivingbase != null) {
/*     */         
/* 121 */         entitylivingbase.setPositionAndUpdate(this.posX, this.posY, this.posZ);
/* 122 */         entitylivingbase.fallDistance = 0.0F;
/*     */       } 
/*     */       
/* 125 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 134 */     EntityLivingBase entitylivingbase = getThrower();
/*     */     
/* 136 */     if (entitylivingbase != null && entitylivingbase instanceof net.minecraft.entity.player.EntityPlayer && !entitylivingbase.isEntityAlive()) {
/*     */       
/* 138 */       setDead();
/*     */     }
/*     */     else {
/*     */       
/* 142 */       super.onUpdate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity changeDimension(int dimensionIn) {
/* 149 */     if (this.thrower.dimension != dimensionIn)
/*     */     {
/* 151 */       this.thrower = null;
/*     */     }
/*     */     
/* 154 */     return super.changeDimension(dimensionIn);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityEnderPearl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */