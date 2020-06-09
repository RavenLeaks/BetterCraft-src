/*     */ package optifine;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.chunk.CompiledChunk;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class DynamicLight
/*     */ {
/*  19 */   private Entity entity = null;
/*  20 */   private double offsetY = 0.0D;
/*  21 */   private double lastPosX = -2.147483648E9D;
/*  22 */   private double lastPosY = -2.147483648E9D;
/*  23 */   private double lastPosZ = -2.147483648E9D;
/*  24 */   private int lastLightLevel = 0;
/*     */   private boolean underwater = false;
/*  26 */   private long timeCheckMs = 0L;
/*  27 */   private Set<BlockPos> setLitChunkPos = new HashSet<>();
/*  28 */   private BlockPos.MutableBlockPos blockPosMutable = new BlockPos.MutableBlockPos();
/*     */ 
/*     */   
/*     */   public DynamicLight(Entity p_i36_1_) {
/*  32 */     this.entity = p_i36_1_;
/*  33 */     this.offsetY = p_i36_1_.getEyeHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(RenderGlobal p_update_1_) {
/*  38 */     if (Config.isDynamicLightsFast()) {
/*     */       
/*  40 */       long i = System.currentTimeMillis();
/*     */       
/*  42 */       if (i < this.timeCheckMs + 500L) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  47 */       this.timeCheckMs = i;
/*     */     } 
/*     */     
/*  50 */     double d6 = this.entity.posX - 0.5D;
/*  51 */     double d0 = this.entity.posY - 0.5D + this.offsetY;
/*  52 */     double d1 = this.entity.posZ - 0.5D;
/*  53 */     int j = DynamicLights.getLightLevel(this.entity);
/*  54 */     double d2 = d6 - this.lastPosX;
/*  55 */     double d3 = d0 - this.lastPosY;
/*  56 */     double d4 = d1 - this.lastPosZ;
/*  57 */     double d5 = 0.1D;
/*     */     
/*  59 */     if (Math.abs(d2) > d5 || Math.abs(d3) > d5 || Math.abs(d4) > d5 || this.lastLightLevel != j) {
/*     */       
/*  61 */       this.lastPosX = d6;
/*  62 */       this.lastPosY = d0;
/*  63 */       this.lastPosZ = d1;
/*  64 */       this.lastLightLevel = j;
/*  65 */       this.underwater = false;
/*  66 */       WorldClient worldClient = p_update_1_.getWorld();
/*     */       
/*  68 */       if (worldClient != null) {
/*     */         
/*  70 */         this.blockPosMutable.setPos(MathHelper.floor(d6), MathHelper.floor(d0), MathHelper.floor(d1));
/*  71 */         IBlockState iblockstate = worldClient.getBlockState((BlockPos)this.blockPosMutable);
/*  72 */         Block block = iblockstate.getBlock();
/*  73 */         this.underwater = (block == Blocks.WATER);
/*     */       } 
/*     */       
/*  76 */       Set<BlockPos> set = new HashSet<>();
/*     */       
/*  78 */       if (j > 0) {
/*     */         
/*  80 */         EnumFacing enumfacing2 = ((MathHelper.floor(d6) & 0xF) >= 8) ? EnumFacing.EAST : EnumFacing.WEST;
/*  81 */         EnumFacing enumfacing = ((MathHelper.floor(d0) & 0xF) >= 8) ? EnumFacing.UP : EnumFacing.DOWN;
/*  82 */         EnumFacing enumfacing1 = ((MathHelper.floor(d1) & 0xF) >= 8) ? EnumFacing.SOUTH : EnumFacing.NORTH;
/*  83 */         BlockPos blockpos = new BlockPos(d6, d0, d1);
/*  84 */         RenderChunk renderchunk = p_update_1_.getRenderChunk(blockpos);
/*  85 */         BlockPos blockpos1 = getChunkPos(renderchunk, blockpos, enumfacing2);
/*  86 */         RenderChunk renderchunk1 = p_update_1_.getRenderChunk(blockpos1);
/*  87 */         BlockPos blockpos2 = getChunkPos(renderchunk, blockpos, enumfacing1);
/*  88 */         RenderChunk renderchunk2 = p_update_1_.getRenderChunk(blockpos2);
/*  89 */         BlockPos blockpos3 = getChunkPos(renderchunk1, blockpos1, enumfacing1);
/*  90 */         RenderChunk renderchunk3 = p_update_1_.getRenderChunk(blockpos3);
/*  91 */         BlockPos blockpos4 = getChunkPos(renderchunk, blockpos, enumfacing);
/*  92 */         RenderChunk renderchunk4 = p_update_1_.getRenderChunk(blockpos4);
/*  93 */         BlockPos blockpos5 = getChunkPos(renderchunk4, blockpos4, enumfacing2);
/*  94 */         RenderChunk renderchunk5 = p_update_1_.getRenderChunk(blockpos5);
/*  95 */         BlockPos blockpos6 = getChunkPos(renderchunk4, blockpos4, enumfacing1);
/*  96 */         RenderChunk renderchunk6 = p_update_1_.getRenderChunk(blockpos6);
/*  97 */         BlockPos blockpos7 = getChunkPos(renderchunk5, blockpos5, enumfacing1);
/*  98 */         RenderChunk renderchunk7 = p_update_1_.getRenderChunk(blockpos7);
/*  99 */         updateChunkLight(renderchunk, this.setLitChunkPos, set);
/* 100 */         updateChunkLight(renderchunk1, this.setLitChunkPos, set);
/* 101 */         updateChunkLight(renderchunk2, this.setLitChunkPos, set);
/* 102 */         updateChunkLight(renderchunk3, this.setLitChunkPos, set);
/* 103 */         updateChunkLight(renderchunk4, this.setLitChunkPos, set);
/* 104 */         updateChunkLight(renderchunk5, this.setLitChunkPos, set);
/* 105 */         updateChunkLight(renderchunk6, this.setLitChunkPos, set);
/* 106 */         updateChunkLight(renderchunk7, this.setLitChunkPos, set);
/*     */       } 
/*     */       
/* 109 */       updateLitChunks(p_update_1_);
/* 110 */       this.setLitChunkPos = set;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockPos getChunkPos(RenderChunk p_getChunkPos_1_, BlockPos p_getChunkPos_2_, EnumFacing p_getChunkPos_3_) {
/* 116 */     return (p_getChunkPos_1_ != null) ? p_getChunkPos_1_.getBlockPosOffset16(p_getChunkPos_3_) : p_getChunkPos_2_.offset(p_getChunkPos_3_, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateChunkLight(RenderChunk p_updateChunkLight_1_, Set<BlockPos> p_updateChunkLight_2_, Set<BlockPos> p_updateChunkLight_3_) {
/* 121 */     if (p_updateChunkLight_1_ != null) {
/*     */       
/* 123 */       CompiledChunk compiledchunk = p_updateChunkLight_1_.getCompiledChunk();
/*     */       
/* 125 */       if (compiledchunk != null && !compiledchunk.isEmpty())
/*     */       {
/* 127 */         p_updateChunkLight_1_.setNeedsUpdate(false);
/*     */       }
/*     */       
/* 130 */       BlockPos blockpos = p_updateChunkLight_1_.getPosition().toImmutable();
/*     */       
/* 132 */       if (p_updateChunkLight_2_ != null)
/*     */       {
/* 134 */         p_updateChunkLight_2_.remove(blockpos);
/*     */       }
/*     */       
/* 137 */       if (p_updateChunkLight_3_ != null)
/*     */       {
/* 139 */         p_updateChunkLight_3_.add(blockpos);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateLitChunks(RenderGlobal p_updateLitChunks_1_) {
/* 146 */     for (BlockPos blockpos : this.setLitChunkPos) {
/*     */       
/* 148 */       RenderChunk renderchunk = p_updateLitChunks_1_.getRenderChunk(blockpos);
/* 149 */       updateChunkLight(renderchunk, null, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getEntity() {
/* 155 */     return this.entity;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLastPosX() {
/* 160 */     return this.lastPosX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLastPosY() {
/* 165 */     return this.lastPosY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLastPosZ() {
/* 170 */     return this.lastPosZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLastLightLevel() {
/* 175 */     return this.lastLightLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderwater() {
/* 180 */     return this.underwater;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOffsetY() {
/* 185 */     return this.offsetY;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 190 */     return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\DynamicLight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */