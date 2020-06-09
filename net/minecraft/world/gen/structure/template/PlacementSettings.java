/*     */ package net.minecraft.world.gen.structure.template;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ public class PlacementSettings
/*     */ {
/*  14 */   private Mirror mirror = Mirror.NONE;
/*  15 */   private Rotation rotation = Rotation.NONE;
/*     */ 
/*     */   
/*     */   private boolean ignoreEntities;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Block replacedBlock;
/*     */   
/*     */   @Nullable
/*     */   private ChunkPos chunk;
/*     */   
/*     */   @Nullable
/*     */   private StructureBoundingBox boundingBox;
/*     */   
/*     */   private boolean ignoreStructureBlock = true;
/*     */   
/*  32 */   private float integrity = 1.0F;
/*     */   
/*     */   @Nullable
/*     */   private Random random;
/*     */   @Nullable
/*     */   private Long setSeed;
/*     */   
/*     */   public PlacementSettings copy() {
/*  40 */     PlacementSettings placementsettings = new PlacementSettings();
/*  41 */     placementsettings.mirror = this.mirror;
/*  42 */     placementsettings.rotation = this.rotation;
/*  43 */     placementsettings.ignoreEntities = this.ignoreEntities;
/*  44 */     placementsettings.replacedBlock = this.replacedBlock;
/*  45 */     placementsettings.chunk = this.chunk;
/*  46 */     placementsettings.boundingBox = this.boundingBox;
/*  47 */     placementsettings.ignoreStructureBlock = this.ignoreStructureBlock;
/*  48 */     placementsettings.integrity = this.integrity;
/*  49 */     placementsettings.random = this.random;
/*  50 */     placementsettings.setSeed = this.setSeed;
/*  51 */     return placementsettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlacementSettings setMirror(Mirror mirrorIn) {
/*  56 */     this.mirror = mirrorIn;
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlacementSettings setRotation(Rotation rotationIn) {
/*  62 */     this.rotation = rotationIn;
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlacementSettings setIgnoreEntities(boolean ignoreEntitiesIn) {
/*  68 */     this.ignoreEntities = ignoreEntitiesIn;
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlacementSettings setReplacedBlock(Block replacedBlockIn) {
/*  74 */     this.replacedBlock = replacedBlockIn;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlacementSettings setChunk(ChunkPos chunkPosIn) {
/*  80 */     this.chunk = chunkPosIn;
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlacementSettings setBoundingBox(StructureBoundingBox boundingBoxIn) {
/*  86 */     this.boundingBox = boundingBoxIn;
/*  87 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlacementSettings setSeed(@Nullable Long p_189949_1_) {
/*  92 */     this.setSeed = p_189949_1_;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlacementSettings setRandom(@Nullable Random p_189950_1_) {
/*  98 */     this.random = p_189950_1_;
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlacementSettings setIntegrity(float p_189946_1_) {
/* 104 */     this.integrity = p_189946_1_;
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mirror getMirror() {
/* 110 */     return this.mirror;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlacementSettings setIgnoreStructureBlock(boolean ignoreStructureBlockIn) {
/* 115 */     this.ignoreStructureBlock = ignoreStructureBlockIn;
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotation getRotation() {
/* 121 */     return this.rotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Random getRandom(@Nullable BlockPos p_189947_1_) {
/* 126 */     if (this.random != null)
/*     */     {
/* 128 */       return this.random;
/*     */     }
/* 130 */     if (this.setSeed != null)
/*     */     {
/* 132 */       return (this.setSeed.longValue() == 0L) ? new Random(System.currentTimeMillis()) : new Random(this.setSeed.longValue());
/*     */     }
/* 134 */     if (p_189947_1_ == null)
/*     */     {
/* 136 */       return new Random(System.currentTimeMillis());
/*     */     }
/*     */ 
/*     */     
/* 140 */     int i = p_189947_1_.getX();
/* 141 */     int j = p_189947_1_.getZ();
/* 142 */     return new Random((i * i * 4987142 + i * 5947611) + (j * j) * 4392871L + (j * 389711) ^ 0x3AD8025FL);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getIntegrity() {
/* 148 */     return this.integrity;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIgnoreEntities() {
/* 153 */     return this.ignoreEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Block getReplacedBlock() {
/* 159 */     return this.replacedBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public StructureBoundingBox getBoundingBox() {
/* 165 */     if (this.boundingBox == null && this.chunk != null)
/*     */     {
/* 167 */       setBoundingBoxFromChunk();
/*     */     }
/*     */     
/* 170 */     return this.boundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIgnoreStructureBlock() {
/* 175 */     return this.ignoreStructureBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   void setBoundingBoxFromChunk() {
/* 180 */     this.boundingBox = getBoundingBoxFromChunk(this.chunk);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private StructureBoundingBox getBoundingBoxFromChunk(@Nullable ChunkPos pos) {
/* 186 */     if (pos == null)
/*     */     {
/* 188 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 192 */     int i = pos.chunkXPos * 16;
/* 193 */     int j = pos.chunkZPos * 16;
/* 194 */     return new StructureBoundingBox(i, 0, j, i + 16 - 1, 255, j + 16 - 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\template\PlacementSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */