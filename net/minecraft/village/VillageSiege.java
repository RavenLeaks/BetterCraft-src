/*     */ package net.minecraft.village;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldEntitySpawner;
/*     */ 
/*     */ public class VillageSiege
/*     */ {
/*     */   private final World worldObj;
/*     */   private boolean hasSetupSiege;
/*  20 */   private int siegeState = -1;
/*     */   
/*     */   private int siegeCount;
/*     */   
/*     */   private int nextSpawnTime;
/*     */   
/*     */   private Village theVillage;
/*     */   private int spawnX;
/*     */   private int spawnY;
/*     */   private int spawnZ;
/*     */   
/*     */   public VillageSiege(World worldIn) {
/*  32 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  40 */     if (this.worldObj.isDaytime()) {
/*     */       
/*  42 */       this.siegeState = 0;
/*     */     }
/*  44 */     else if (this.siegeState != 2) {
/*     */       
/*  46 */       if (this.siegeState == 0) {
/*     */         
/*  48 */         float f = this.worldObj.getCelestialAngle(0.0F);
/*     */         
/*  50 */         if (f < 0.5D || f > 0.501D) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  55 */         this.siegeState = (this.worldObj.rand.nextInt(10) == 0) ? 1 : 2;
/*  56 */         this.hasSetupSiege = false;
/*     */         
/*  58 */         if (this.siegeState == 2) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  64 */       if (this.siegeState != -1) {
/*     */         
/*  66 */         if (!this.hasSetupSiege) {
/*     */           
/*  68 */           if (!trySetupSiege()) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*  73 */           this.hasSetupSiege = true;
/*     */         } 
/*     */         
/*  76 */         if (this.nextSpawnTime > 0) {
/*     */           
/*  78 */           this.nextSpawnTime--;
/*     */         }
/*     */         else {
/*     */           
/*  82 */           this.nextSpawnTime = 2;
/*     */           
/*  84 */           if (this.siegeCount > 0) {
/*     */             
/*  86 */             spawnZombie();
/*  87 */             this.siegeCount--;
/*     */           }
/*     */           else {
/*     */             
/*  91 */             this.siegeState = 2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean trySetupSiege() {
/* 100 */     List<EntityPlayer> list = this.worldObj.playerEntities;
/* 101 */     Iterator<EntityPlayer> iterator = list.iterator();
/*     */ 
/*     */     
/*     */     while (true) {
/* 105 */       if (!iterator.hasNext())
/*     */       {
/* 107 */         return false;
/*     */       }
/*     */       
/* 110 */       EntityPlayer entityplayer = iterator.next();
/*     */       
/* 112 */       if (!entityplayer.isSpectator()) {
/*     */         
/* 114 */         this.theVillage = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos((Entity)entityplayer), 1);
/*     */         
/* 116 */         if (this.theVillage != null && this.theVillage.getNumVillageDoors() >= 10 && this.theVillage.getTicksSinceLastDoorAdding() >= 20 && this.theVillage.getNumVillagers() >= 20) {
/*     */           
/* 118 */           BlockPos blockpos = this.theVillage.getCenter();
/* 119 */           float f = this.theVillage.getVillageRadius();
/* 120 */           boolean flag = false;
/*     */           
/* 122 */           for (int i = 0; i < 10; i++) {
/*     */             
/* 124 */             float f1 = this.worldObj.rand.nextFloat() * 6.2831855F;
/* 125 */             this.spawnX = blockpos.getX() + (int)((MathHelper.cos(f1) * f) * 0.9D);
/* 126 */             this.spawnY = blockpos.getY();
/* 127 */             this.spawnZ = blockpos.getZ() + (int)((MathHelper.sin(f1) * f) * 0.9D);
/* 128 */             flag = false;
/*     */             
/* 130 */             for (Village village : this.worldObj.getVillageCollection().getVillageList()) {
/*     */               
/* 132 */               if (village != this.theVillage && village.isBlockPosWithinSqVillageRadius(new BlockPos(this.spawnX, this.spawnY, this.spawnZ))) {
/*     */                 
/* 134 */                 flag = true;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 139 */             if (!flag) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 145 */           if (flag)
/*     */           {
/* 147 */             return false;
/*     */           }
/*     */           
/* 150 */           Vec3d vec3d = findRandomSpawnPos(new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
/*     */           
/* 152 */           if (vec3d != null) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 160 */     this.nextSpawnTime = 0;
/* 161 */     this.siegeCount = 20;
/* 162 */     return true;
/*     */   }
/*     */   
/*     */   private boolean spawnZombie() {
/*     */     EntityZombie entityzombie;
/* 167 */     Vec3d vec3d = findRandomSpawnPos(new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
/*     */     
/* 169 */     if (vec3d == null)
/*     */     {
/* 171 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 179 */       entityzombie = new EntityZombie(this.worldObj);
/* 180 */       entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityzombie)), null);
/*     */     }
/* 182 */     catch (Exception exception) {
/*     */       
/* 184 */       exception.printStackTrace();
/* 185 */       return false;
/*     */     } 
/*     */     
/* 188 */     entityzombie.setLocationAndAngles(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
/* 189 */     this.worldObj.spawnEntityInWorld((Entity)entityzombie);
/* 190 */     BlockPos blockpos = this.theVillage.getCenter();
/* 191 */     entityzombie.setHomePosAndDistance(blockpos, this.theVillage.getVillageRadius());
/* 192 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Vec3d findRandomSpawnPos(BlockPos pos) {
/* 199 */     for (int i = 0; i < 10; i++) {
/*     */       
/* 201 */       BlockPos blockpos = pos.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
/*     */       
/* 203 */       if (this.theVillage.isBlockPosWithinSqVillageRadius(blockpos) && WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, this.worldObj, blockpos))
/*     */       {
/* 205 */         return new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */       }
/*     */     } 
/*     */     
/* 209 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\village\VillageSiege.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */