/*     */ package net.minecraft.world.end;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.biome.BiomeEndDecorator;
/*     */ import net.minecraft.world.gen.feature.WorldGenSpikes;
/*     */ 
/*     */ public enum DragonSpawnManager {
/*  14 */   START
/*     */   {
/*     */     public void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos) {
/*  17 */       BlockPos blockpos = new BlockPos(0, 128, 0);
/*     */       
/*  19 */       for (EntityEnderCrystal entityendercrystal : crystals)
/*     */       {
/*  21 */         entityendercrystal.setBeamTarget(blockpos);
/*     */       }
/*     */       
/*  24 */       manager.setRespawnState(PREPARING_TO_SUMMON_PILLARS);
/*     */     }
/*     */   },
/*  27 */   PREPARING_TO_SUMMON_PILLARS
/*     */   {
/*     */     public void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos) {
/*  30 */       if (ticks < 100) {
/*     */         
/*  32 */         if (ticks == 0 || ticks == 50 || ticks == 51 || ticks == 52 || ticks >= 95)
/*     */         {
/*  34 */           worldIn.playEvent(3001, new BlockPos(0, 128, 0), 0);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/*  39 */         manager.setRespawnState(SUMMONING_PILLARS);
/*     */       } 
/*     */     }
/*     */   },
/*  43 */   SUMMONING_PILLARS
/*     */   {
/*     */     public void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos) {
/*  46 */       int i = 40;
/*  47 */       boolean flag = (ticks % 40 == 0);
/*  48 */       boolean flag1 = (ticks % 40 == 39);
/*     */       
/*  50 */       if (flag || flag1) {
/*     */         
/*  52 */         WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = BiomeEndDecorator.getSpikesForWorld((World)worldIn);
/*  53 */         int j = ticks / 40;
/*     */         
/*  55 */         if (j < aworldgenspikes$endspike.length) {
/*     */           
/*  57 */           WorldGenSpikes.EndSpike worldgenspikes$endspike = aworldgenspikes$endspike[j];
/*     */           
/*  59 */           if (flag)
/*     */           {
/*  61 */             for (EntityEnderCrystal entityendercrystal : crystals)
/*     */             {
/*  63 */               entityendercrystal.setBeamTarget(new BlockPos(worldgenspikes$endspike.getCenterX(), worldgenspikes$endspike.getHeight() + 1, worldgenspikes$endspike.getCenterZ()));
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/*  68 */             int k = 10;
/*     */             
/*  70 */             for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(new BlockPos(worldgenspikes$endspike.getCenterX() - 10, worldgenspikes$endspike.getHeight() - 10, worldgenspikes$endspike.getCenterZ() - 10), new BlockPos(worldgenspikes$endspike.getCenterX() + 10, worldgenspikes$endspike.getHeight() + 10, worldgenspikes$endspike.getCenterZ() + 10)))
/*     */             {
/*  72 */               worldIn.setBlockToAir((BlockPos)blockpos$mutableblockpos);
/*     */             }
/*     */             
/*  75 */             worldIn.createExplosion(null, (worldgenspikes$endspike.getCenterX() + 0.5F), worldgenspikes$endspike.getHeight(), (worldgenspikes$endspike.getCenterZ() + 0.5F), 5.0F, true);
/*  76 */             WorldGenSpikes worldgenspikes = new WorldGenSpikes();
/*  77 */             worldgenspikes.setSpike(worldgenspikes$endspike);
/*  78 */             worldgenspikes.setCrystalInvulnerable(true);
/*  79 */             worldgenspikes.setBeamTarget(new BlockPos(0, 128, 0));
/*  80 */             worldgenspikes.generate((World)worldIn, new Random(), new BlockPos(worldgenspikes$endspike.getCenterX(), 45, worldgenspikes$endspike.getCenterZ()));
/*     */           }
/*     */         
/*  83 */         } else if (flag) {
/*     */           
/*  85 */           manager.setRespawnState(SUMMONING_DRAGON);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   },
/*  90 */   SUMMONING_DRAGON
/*     */   {
/*     */     public void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos) {
/*  93 */       if (ticks >= 100) {
/*     */         
/*  95 */         manager.setRespawnState(END);
/*  96 */         manager.resetSpikeCrystals();
/*     */         
/*  98 */         for (EntityEnderCrystal entityendercrystal : crystals)
/*     */         {
/* 100 */           entityendercrystal.setBeamTarget(null);
/* 101 */           worldIn.createExplosion((Entity)entityendercrystal, entityendercrystal.posX, entityendercrystal.posY, entityendercrystal.posZ, 6.0F, false);
/* 102 */           entityendercrystal.setDead();
/*     */         }
/*     */       
/* 105 */       } else if (ticks >= 80) {
/*     */         
/* 107 */         worldIn.playEvent(3001, new BlockPos(0, 128, 0), 0);
/*     */       }
/* 109 */       else if (ticks == 0) {
/*     */         
/* 111 */         for (EntityEnderCrystal entityendercrystal1 : crystals)
/*     */         {
/* 113 */           entityendercrystal1.setBeamTarget(new BlockPos(0, 128, 0));
/*     */         }
/*     */       }
/* 116 */       else if (ticks < 5) {
/*     */         
/* 118 */         worldIn.playEvent(3001, new BlockPos(0, 128, 0), 0);
/*     */       } 
/*     */     }
/*     */   },
/* 122 */   END {
/*     */     public void process(WorldServer worldIn, DragonFightManager manager, List<EntityEnderCrystal> crystals, int ticks, BlockPos pos) {}
/*     */   };
/*     */   
/*     */   public abstract void process(WorldServer paramWorldServer, DragonFightManager paramDragonFightManager, List<EntityEnderCrystal> paramList, int paramInt, BlockPos paramBlockPos);
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\end\DragonSpawnManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */