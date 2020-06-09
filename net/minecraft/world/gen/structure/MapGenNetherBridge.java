/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntityWitherSkeleton;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ public class MapGenNetherBridge
/*     */   extends MapGenStructure {
/*  17 */   private final List<Biome.SpawnListEntry> spawnList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public MapGenNetherBridge() {
/*  21 */     this.spawnList.add(new Biome.SpawnListEntry(EntityBlaze.class, 10, 2, 3));
/*  22 */     this.spawnList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
/*  23 */     this.spawnList.add(new Biome.SpawnListEntry(EntityWitherSkeleton.class, 8, 5, 5));
/*  24 */     this.spawnList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 2, 5, 5));
/*  25 */     this.spawnList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  30 */     return "Fortress";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Biome.SpawnListEntry> getSpawnList() {
/*  35 */     return this.spawnList;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  40 */     int i = chunkX >> 4;
/*  41 */     int j = chunkZ >> 4;
/*  42 */     this.rand.setSeed((i ^ j << 4) ^ this.worldObj.getSeed());
/*  43 */     this.rand.nextInt();
/*     */     
/*  45 */     if (this.rand.nextInt(3) != 0)
/*     */     {
/*  47 */       return false;
/*     */     }
/*  49 */     if (chunkX != (i << 4) + 4 + this.rand.nextInt(8))
/*     */     {
/*  51 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  55 */     return (chunkZ == (j << 4) + 4 + this.rand.nextInt(8));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*  61 */     return new Start(this.worldObj, this.rand, chunkX, chunkZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_) {
/*  66 */     int i = 1000;
/*  67 */     int j = pos.getX() >> 4;
/*  68 */     int k = pos.getZ() >> 4;
/*     */     
/*  70 */     for (int l = 0; l <= 1000; l++) {
/*     */       
/*  72 */       for (int i1 = -l; i1 <= l; i1++) {
/*     */         
/*  74 */         boolean flag = !(i1 != -l && i1 != l);
/*     */         
/*  76 */         for (int j1 = -l; j1 <= l; j1++) {
/*     */           
/*  78 */           boolean flag1 = !(j1 != -l && j1 != l);
/*     */           
/*  80 */           if (flag || flag1) {
/*     */             
/*  82 */             int k1 = j + i1;
/*  83 */             int l1 = k + j1;
/*     */             
/*  85 */             if (canSpawnStructureAtCoords(k1, l1) && (!p_180706_3_ || !worldIn.func_190526_b(k1, l1)))
/*     */             {
/*  87 */               return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart
/*     */   {
/*     */     public Start() {}
/*     */ 
/*     */     
/*     */     public Start(World worldIn, Random random, int chunkX, int chunkZ) {
/* 105 */       super(chunkX, chunkZ);
/* 106 */       StructureNetherBridgePieces.Start structurenetherbridgepieces$start = new StructureNetherBridgePieces.Start(random, (chunkX << 4) + 2, (chunkZ << 4) + 2);
/* 107 */       this.components.add(structurenetherbridgepieces$start);
/* 108 */       structurenetherbridgepieces$start.buildComponent(structurenetherbridgepieces$start, this.components, random);
/* 109 */       List<StructureComponent> list = structurenetherbridgepieces$start.pendingChildren;
/*     */       
/* 111 */       while (!list.isEmpty()) {
/*     */         
/* 113 */         int i = random.nextInt(list.size());
/* 114 */         StructureComponent structurecomponent = list.remove(i);
/* 115 */         structurecomponent.buildComponent(structurenetherbridgepieces$start, this.components, random);
/*     */       } 
/*     */       
/* 118 */       updateBoundingBox();
/* 119 */       setRandomHeight(worldIn, random, 48, 70);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\MapGenNetherBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */