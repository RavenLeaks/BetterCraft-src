/*     */ package net.minecraft.entity.effect;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityLightningBolt
/*     */   extends EntityWeatherEffect
/*     */ {
/*     */   private int lightningState;
/*     */   public long boltVertex;
/*     */   private int boltLivingTime;
/*     */   private final boolean effectOnly;
/*     */   
/*     */   public EntityLightningBolt(World worldIn, double x, double y, double z, boolean effectOnlyIn) {
/*  36 */     super(worldIn);
/*  37 */     setLocationAndAngles(x, y, z, 0.0F, 0.0F);
/*  38 */     this.lightningState = 2;
/*  39 */     this.boltVertex = this.rand.nextLong();
/*  40 */     this.boltLivingTime = this.rand.nextInt(3) + 1;
/*  41 */     this.effectOnly = effectOnlyIn;
/*  42 */     BlockPos blockpos = new BlockPos(this);
/*     */     
/*  44 */     if (!effectOnlyIn && !worldIn.isRemote && worldIn.getGameRules().getBoolean("doFireTick") && (worldIn.getDifficulty() == EnumDifficulty.NORMAL || worldIn.getDifficulty() == EnumDifficulty.HARD) && worldIn.isAreaLoaded(blockpos, 10)) {
/*     */       
/*  46 */       if (worldIn.getBlockState(blockpos).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(worldIn, blockpos))
/*     */       {
/*  48 */         worldIn.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
/*     */       }
/*     */       
/*  51 */       for (int i = 0; i < 4; i++) {
/*     */         
/*  53 */         BlockPos blockpos1 = blockpos.add(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);
/*     */         
/*  55 */         if (worldIn.getBlockState(blockpos1).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(worldIn, blockpos1))
/*     */         {
/*  57 */           worldIn.setBlockState(blockpos1, Blocks.FIRE.getDefaultState());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundCategory getSoundCategory() {
/*  65 */     return SoundCategory.WEATHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  73 */     super.onUpdate();
/*     */     
/*  75 */     if (this.lightningState == 2) {
/*     */       
/*  77 */       this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
/*  78 */       this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.WEATHER, 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
/*     */     } 
/*     */     
/*  81 */     this.lightningState--;
/*     */     
/*  83 */     if (this.lightningState < 0)
/*     */     {
/*  85 */       if (this.boltLivingTime == 0) {
/*     */         
/*  87 */         setDead();
/*     */       }
/*  89 */       else if (this.lightningState < -this.rand.nextInt(10)) {
/*     */         
/*  91 */         this.boltLivingTime--;
/*  92 */         this.lightningState = 1;
/*     */         
/*  94 */         if (!this.effectOnly && !this.world.isRemote) {
/*     */           
/*  96 */           this.boltVertex = this.rand.nextLong();
/*  97 */           BlockPos blockpos = new BlockPos(this);
/*     */           
/*  99 */           if (this.world.getGameRules().getBoolean("doFireTick") && this.world.isAreaLoaded(blockpos, 10) && this.world.getBlockState(blockpos).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(this.world, blockpos))
/*     */           {
/* 101 */             this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 107 */     if (this.lightningState >= 0)
/*     */     {
/* 109 */       if (this.world.isRemote) {
/*     */         
/* 111 */         this.world.setLastLightningBolt(2);
/*     */       }
/* 113 */       else if (!this.effectOnly) {
/*     */         
/* 115 */         double d0 = 3.0D;
/* 116 */         List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(this.posX - 3.0D, this.posY - 3.0D, this.posZ - 3.0D, this.posX + 3.0D, this.posY + 6.0D + 3.0D, this.posZ + 3.0D));
/*     */         
/* 118 */         for (int i = 0; i < list.size(); i++) {
/*     */           
/* 120 */           Entity entity = list.get(i);
/* 121 */           entity.onStruckByLightning(this);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void entityInit() {}
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {}
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\effect\EntityLightningBolt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */