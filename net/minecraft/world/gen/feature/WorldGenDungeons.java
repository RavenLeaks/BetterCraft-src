/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class WorldGenDungeons
/*     */   extends WorldGenerator {
/*  23 */   private static final Logger LOGGER = LogManager.getLogger();
/*  24 */   private static final ResourceLocation[] SPAWNERTYPES = new ResourceLocation[] { EntityList.func_191306_a(EntitySkeleton.class), EntityList.func_191306_a(EntityZombie.class), EntityList.func_191306_a(EntityZombie.class), EntityList.func_191306_a(EntitySpider.class) };
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  28 */     int i = 3;
/*  29 */     int j = rand.nextInt(2) + 2;
/*  30 */     int k = -j - 1;
/*  31 */     int l = j + 1;
/*  32 */     int i1 = -1;
/*  33 */     int j1 = 4;
/*  34 */     int k1 = rand.nextInt(2) + 2;
/*  35 */     int l1 = -k1 - 1;
/*  36 */     int i2 = k1 + 1;
/*  37 */     int j2 = 0;
/*     */     
/*  39 */     for (int k2 = k; k2 <= l; k2++) {
/*     */       
/*  41 */       for (int l2 = -1; l2 <= 4; l2++) {
/*     */         
/*  43 */         for (int i3 = l1; i3 <= i2; i3++) {
/*     */           
/*  45 */           BlockPos blockpos = position.add(k2, l2, i3);
/*  46 */           Material material = worldIn.getBlockState(blockpos).getMaterial();
/*  47 */           boolean flag = material.isSolid();
/*     */           
/*  49 */           if (l2 == -1 && !flag)
/*     */           {
/*  51 */             return false;
/*     */           }
/*     */           
/*  54 */           if (l2 == 4 && !flag)
/*     */           {
/*  56 */             return false;
/*     */           }
/*     */           
/*  59 */           if ((k2 == k || k2 == l || i3 == l1 || i3 == i2) && l2 == 0 && worldIn.isAirBlock(blockpos) && worldIn.isAirBlock(blockpos.up()))
/*     */           {
/*  61 */             j2++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  67 */     if (j2 >= 1 && j2 <= 5) {
/*     */       
/*  69 */       for (int k3 = k; k3 <= l; k3++) {
/*     */         
/*  71 */         for (int i4 = 3; i4 >= -1; i4--) {
/*     */           
/*  73 */           for (int k4 = l1; k4 <= i2; k4++) {
/*     */             
/*  75 */             BlockPos blockpos1 = position.add(k3, i4, k4);
/*     */             
/*  77 */             if (k3 != k && i4 != -1 && k4 != l1 && k3 != l && i4 != 4 && k4 != i2) {
/*     */               
/*  79 */               if (worldIn.getBlockState(blockpos1).getBlock() != Blocks.CHEST)
/*     */               {
/*  81 */                 worldIn.setBlockToAir(blockpos1);
/*     */               }
/*     */             }
/*  84 */             else if (blockpos1.getY() >= 0 && !worldIn.getBlockState(blockpos1.down()).getMaterial().isSolid()) {
/*     */               
/*  86 */               worldIn.setBlockToAir(blockpos1);
/*     */             }
/*  88 */             else if (worldIn.getBlockState(blockpos1).getMaterial().isSolid() && worldIn.getBlockState(blockpos1).getBlock() != Blocks.CHEST) {
/*     */               
/*  90 */               if (i4 == -1 && rand.nextInt(4) != 0) {
/*     */                 
/*  92 */                 worldIn.setBlockState(blockpos1, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
/*     */               }
/*     */               else {
/*     */                 
/*  96 */                 worldIn.setBlockState(blockpos1, Blocks.COBBLESTONE.getDefaultState(), 2);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 103 */       for (int l3 = 0; l3 < 2; l3++) {
/*     */         
/* 105 */         for (int j4 = 0; j4 < 3; j4++) {
/*     */           
/* 107 */           int l4 = position.getX() + rand.nextInt(j * 2 + 1) - j;
/* 108 */           int i5 = position.getY();
/* 109 */           int j5 = position.getZ() + rand.nextInt(k1 * 2 + 1) - k1;
/* 110 */           BlockPos blockpos2 = new BlockPos(l4, i5, j5);
/*     */           
/* 112 */           if (worldIn.isAirBlock(blockpos2)) {
/*     */             
/* 114 */             int j3 = 0;
/*     */             
/* 116 */             for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 118 */               if (worldIn.getBlockState(blockpos2.offset(enumfacing)).getMaterial().isSolid())
/*     */               {
/* 120 */                 j3++;
/*     */               }
/*     */             } 
/*     */             
/* 124 */             if (j3 == 1) {
/*     */               
/* 126 */               worldIn.setBlockState(blockpos2, Blocks.CHEST.correctFacing(worldIn, blockpos2, Blocks.CHEST.getDefaultState()), 2);
/* 127 */               TileEntity tileentity1 = worldIn.getTileEntity(blockpos2);
/*     */               
/* 129 */               if (tileentity1 instanceof TileEntityChest)
/*     */               {
/* 131 */                 ((TileEntityChest)tileentity1).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
/*     */               }
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 140 */       worldIn.setBlockState(position, Blocks.MOB_SPAWNER.getDefaultState(), 2);
/* 141 */       TileEntity tileentity = worldIn.getTileEntity(position);
/*     */       
/* 143 */       if (tileentity instanceof TileEntityMobSpawner) {
/*     */         
/* 145 */         ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().func_190894_a(pickMobSpawner(rand));
/*     */       }
/*     */       else {
/*     */         
/* 149 */         LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", Integer.valueOf(position.getX()), Integer.valueOf(position.getY()), Integer.valueOf(position.getZ()));
/*     */       } 
/*     */       
/* 152 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceLocation pickMobSpawner(Random rand) {
/* 165 */     return SPAWNERTYPES[rand.nextInt(SPAWNERTYPES.length)];
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenDungeons.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */