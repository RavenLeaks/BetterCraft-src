/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.util.WeightedSpawnerEntity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.storage.AnvilChunkLoader;
/*     */ 
/*     */ 
/*     */ public abstract class MobSpawnerBaseLogic
/*     */ {
/*  24 */   private int spawnDelay = 20;
/*  25 */   private final List<WeightedSpawnerEntity> potentialSpawns = Lists.newArrayList();
/*  26 */   private WeightedSpawnerEntity randomEntity = new WeightedSpawnerEntity();
/*     */ 
/*     */   
/*     */   private double mobRotation;
/*     */   
/*     */   private double prevMobRotation;
/*     */   
/*  33 */   private int minSpawnDelay = 200;
/*  34 */   private int maxSpawnDelay = 800;
/*  35 */   private int spawnCount = 4;
/*     */   
/*     */   private Entity cachedEntity;
/*     */   
/*  39 */   private int maxNearbyEntities = 6;
/*     */ 
/*     */   
/*  42 */   private int activatingRangeFromPlayer = 16;
/*     */ 
/*     */   
/*  45 */   private int spawnRange = 4;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private ResourceLocation func_190895_g() {
/*  50 */     String s = this.randomEntity.getNbt().getString("id");
/*  51 */     return StringUtils.isNullOrEmpty(s) ? null : new ResourceLocation(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190894_a(@Nullable ResourceLocation p_190894_1_) {
/*  56 */     if (p_190894_1_ != null)
/*     */     {
/*  58 */       this.randomEntity.getNbt().setString("id", p_190894_1_.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isActivated() {
/*  67 */     BlockPos blockpos = getSpawnerPosition();
/*  68 */     return getSpawnerWorld().isAnyPlayerWithinRangeAt(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D, this.activatingRangeFromPlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSpawner() {
/*  73 */     if (!isActivated()) {
/*     */       
/*  75 */       this.prevMobRotation = this.mobRotation;
/*     */     }
/*     */     else {
/*     */       
/*  79 */       BlockPos blockpos = getSpawnerPosition();
/*     */       
/*  81 */       if ((getSpawnerWorld()).isRemote) {
/*     */         
/*  83 */         double d3 = (blockpos.getX() + (getSpawnerWorld()).rand.nextFloat());
/*  84 */         double d4 = (blockpos.getY() + (getSpawnerWorld()).rand.nextFloat());
/*  85 */         double d5 = (blockpos.getZ() + (getSpawnerWorld()).rand.nextFloat());
/*  86 */         getSpawnerWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);
/*  87 */         getSpawnerWorld().spawnParticle(EnumParticleTypes.FLAME, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         
/*  89 */         if (this.spawnDelay > 0)
/*     */         {
/*  91 */           this.spawnDelay--;
/*     */         }
/*     */         
/*  94 */         this.prevMobRotation = this.mobRotation;
/*  95 */         this.mobRotation = (this.mobRotation + (1000.0F / (this.spawnDelay + 200.0F))) % 360.0D;
/*     */       }
/*     */       else {
/*     */         
/*  99 */         if (this.spawnDelay == -1)
/*     */         {
/* 101 */           resetTimer();
/*     */         }
/*     */         
/* 104 */         if (this.spawnDelay > 0) {
/*     */           
/* 106 */           this.spawnDelay--;
/*     */           
/*     */           return;
/*     */         } 
/* 110 */         boolean flag = false;
/*     */         
/* 112 */         for (int i = 0; i < this.spawnCount; i++) {
/*     */           
/* 114 */           NBTTagCompound nbttagcompound = this.randomEntity.getNbt();
/* 115 */           NBTTagList nbttaglist = nbttagcompound.getTagList("Pos", 6);
/* 116 */           World world = getSpawnerWorld();
/* 117 */           int j = nbttaglist.tagCount();
/* 118 */           double d0 = (j >= 1) ? nbttaglist.getDoubleAt(0) : (blockpos.getX() + (world.rand.nextDouble() - world.rand.nextDouble()) * this.spawnRange + 0.5D);
/* 119 */           double d1 = (j >= 2) ? nbttaglist.getDoubleAt(1) : (blockpos.getY() + world.rand.nextInt(3) - 1);
/* 120 */           double d2 = (j >= 3) ? nbttaglist.getDoubleAt(2) : (blockpos.getZ() + (world.rand.nextDouble() - world.rand.nextDouble()) * this.spawnRange + 0.5D);
/* 121 */           Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, d0, d1, d2, false);
/*     */           
/* 123 */           if (entity == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 128 */           int k = world.getEntitiesWithinAABB(entity.getClass(), (new AxisAlignedBB(blockpos.getX(), blockpos.getY(), blockpos.getZ(), (blockpos.getX() + 1), (blockpos.getY() + 1), (blockpos.getZ() + 1))).expandXyz(this.spawnRange)).size();
/*     */           
/* 130 */           if (k >= this.maxNearbyEntities) {
/*     */             
/* 132 */             resetTimer();
/*     */             
/*     */             return;
/*     */           } 
/* 136 */           EntityLiving entityliving = (entity instanceof EntityLiving) ? (EntityLiving)entity : null;
/* 137 */           entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, world.rand.nextFloat() * 360.0F, 0.0F);
/*     */           
/* 139 */           if (entityliving == null || (entityliving.getCanSpawnHere() && entityliving.isNotColliding())) {
/*     */             
/* 141 */             if (this.randomEntity.getNbt().getSize() == 1 && this.randomEntity.getNbt().hasKey("id", 8) && entity instanceof EntityLiving)
/*     */             {
/* 143 */               ((EntityLiving)entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), null);
/*     */             }
/*     */             
/* 146 */             AnvilChunkLoader.spawnEntity(entity, world);
/* 147 */             world.playEvent(2004, blockpos, 0);
/*     */             
/* 149 */             if (entityliving != null)
/*     */             {
/* 151 */               entityliving.spawnExplosionParticle();
/*     */             }
/*     */             
/* 154 */             flag = true;
/*     */           } 
/*     */         } 
/*     */         
/* 158 */         if (flag)
/*     */         {
/* 160 */           resetTimer();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetTimer() {
/* 168 */     if (this.maxSpawnDelay <= this.minSpawnDelay) {
/*     */       
/* 170 */       this.spawnDelay = this.minSpawnDelay;
/*     */     }
/*     */     else {
/*     */       
/* 174 */       int i = this.maxSpawnDelay - this.minSpawnDelay;
/* 175 */       this.spawnDelay = this.minSpawnDelay + (getSpawnerWorld()).rand.nextInt(i);
/*     */     } 
/*     */     
/* 178 */     if (!this.potentialSpawns.isEmpty())
/*     */     {
/* 180 */       setNextSpawnData((WeightedSpawnerEntity)WeightedRandom.getRandomItem((getSpawnerWorld()).rand, this.potentialSpawns));
/*     */     }
/*     */     
/* 183 */     broadcastEvent(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 188 */     this.spawnDelay = nbt.getShort("Delay");
/* 189 */     this.potentialSpawns.clear();
/*     */     
/* 191 */     if (nbt.hasKey("SpawnPotentials", 9)) {
/*     */       
/* 193 */       NBTTagList nbttaglist = nbt.getTagList("SpawnPotentials", 10);
/*     */       
/* 195 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */       {
/* 197 */         this.potentialSpawns.add(new WeightedSpawnerEntity(nbttaglist.getCompoundTagAt(i)));
/*     */       }
/*     */     } 
/*     */     
/* 201 */     if (nbt.hasKey("SpawnData", 10)) {
/*     */       
/* 203 */       setNextSpawnData(new WeightedSpawnerEntity(1, nbt.getCompoundTag("SpawnData")));
/*     */     }
/* 205 */     else if (!this.potentialSpawns.isEmpty()) {
/*     */       
/* 207 */       setNextSpawnData((WeightedSpawnerEntity)WeightedRandom.getRandomItem((getSpawnerWorld()).rand, this.potentialSpawns));
/*     */     } 
/*     */     
/* 210 */     if (nbt.hasKey("MinSpawnDelay", 99)) {
/*     */       
/* 212 */       this.minSpawnDelay = nbt.getShort("MinSpawnDelay");
/* 213 */       this.maxSpawnDelay = nbt.getShort("MaxSpawnDelay");
/* 214 */       this.spawnCount = nbt.getShort("SpawnCount");
/*     */     } 
/*     */     
/* 217 */     if (nbt.hasKey("MaxNearbyEntities", 99)) {
/*     */       
/* 219 */       this.maxNearbyEntities = nbt.getShort("MaxNearbyEntities");
/* 220 */       this.activatingRangeFromPlayer = nbt.getShort("RequiredPlayerRange");
/*     */     } 
/*     */     
/* 223 */     if (nbt.hasKey("SpawnRange", 99))
/*     */     {
/* 225 */       this.spawnRange = nbt.getShort("SpawnRange");
/*     */     }
/*     */     
/* 228 */     if (getSpawnerWorld() != null)
/*     */     {
/* 230 */       this.cachedEntity = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound p_189530_1_) {
/* 236 */     ResourceLocation resourcelocation = func_190895_g();
/*     */     
/* 238 */     if (resourcelocation == null)
/*     */     {
/* 240 */       return p_189530_1_;
/*     */     }
/*     */ 
/*     */     
/* 244 */     p_189530_1_.setShort("Delay", (short)this.spawnDelay);
/* 245 */     p_189530_1_.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
/* 246 */     p_189530_1_.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
/* 247 */     p_189530_1_.setShort("SpawnCount", (short)this.spawnCount);
/* 248 */     p_189530_1_.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
/* 249 */     p_189530_1_.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
/* 250 */     p_189530_1_.setShort("SpawnRange", (short)this.spawnRange);
/* 251 */     p_189530_1_.setTag("SpawnData", (NBTBase)this.randomEntity.getNbt().copy());
/* 252 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 254 */     if (this.potentialSpawns.isEmpty()) {
/*     */       
/* 256 */       nbttaglist.appendTag((NBTBase)this.randomEntity.toCompoundTag());
/*     */     }
/*     */     else {
/*     */       
/* 260 */       for (WeightedSpawnerEntity weightedspawnerentity : this.potentialSpawns)
/*     */       {
/* 262 */         nbttaglist.appendTag((NBTBase)weightedspawnerentity.toCompoundTag());
/*     */       }
/*     */     } 
/*     */     
/* 266 */     p_189530_1_.setTag("SpawnPotentials", (NBTBase)nbttaglist);
/* 267 */     return p_189530_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getCachedEntity() {
/* 273 */     if (this.cachedEntity == null) {
/*     */       
/* 275 */       this.cachedEntity = AnvilChunkLoader.readWorldEntity(this.randomEntity.getNbt(), getSpawnerWorld(), false);
/*     */       
/* 277 */       if (this.randomEntity.getNbt().getSize() == 1 && this.randomEntity.getNbt().hasKey("id", 8) && this.cachedEntity instanceof EntityLiving)
/*     */       {
/* 279 */         ((EntityLiving)this.cachedEntity).onInitialSpawn(getSpawnerWorld().getDifficultyForLocation(new BlockPos(this.cachedEntity)), null);
/*     */       }
/*     */     } 
/*     */     
/* 283 */     return this.cachedEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setDelayToMin(int delay) {
/* 291 */     if (delay == 1 && (getSpawnerWorld()).isRemote) {
/*     */       
/* 293 */       this.spawnDelay = this.minSpawnDelay;
/* 294 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 298 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNextSpawnData(WeightedSpawnerEntity p_184993_1_) {
/* 304 */     this.randomEntity = p_184993_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void broadcastEvent(int paramInt);
/*     */   
/*     */   public abstract World getSpawnerWorld();
/*     */   
/*     */   public abstract BlockPos getSpawnerPosition();
/*     */   
/*     */   public double getMobRotation() {
/* 315 */     return this.mobRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getPrevMobRotation() {
/* 320 */     return this.prevMobRotation;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\MobSpawnerBaseLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */