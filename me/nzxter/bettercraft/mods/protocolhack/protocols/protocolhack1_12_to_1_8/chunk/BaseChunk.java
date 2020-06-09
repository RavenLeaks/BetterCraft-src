/*     */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.chunk;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ public class BaseChunk
/*     */   implements IChunk
/*     */ {
/*     */   protected int x;
/*     */   protected int z;
/*     */   protected boolean groundUp;
/*     */   protected int bitmask;
/*     */   protected ChunkSection[] sections;
/*     */   protected int[] biomeData;
/*     */   protected NBTTagCompound heightMap;
/*     */   protected List<NBTTagCompound> blockEntities;
/*     */   
/*     */   public BaseChunk(int x, int z, boolean groundUp, int bitmask, ChunkSection[] sections, int[] biomeData, List<NBTTagCompound> blockEntities) {
/*  21 */     this.x = x;
/*  22 */     this.z = z;
/*  23 */     this.groundUp = groundUp;
/*  24 */     this.bitmask = bitmask;
/*  25 */     this.sections = sections;
/*  26 */     this.biomeData = biomeData;
/*  27 */     this.blockEntities = blockEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasBiomeData() {
/*  32 */     return (this.biomeData != null);
/*     */   }
/*     */   
/*     */   public BaseChunk(int x, int z, boolean groundUp, int bitmask, ChunkSection[] sections, int[] biomeData, NBTTagCompound heightMap, List<NBTTagCompound> blockEntities) {
/*  36 */     this.x = x;
/*  37 */     this.z = z;
/*  38 */     this.groundUp = groundUp;
/*  39 */     this.bitmask = bitmask;
/*  40 */     this.sections = sections;
/*  41 */     this.biomeData = biomeData;
/*  42 */     this.heightMap = heightMap;
/*  43 */     this.blockEntities = blockEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkX() {
/*  48 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkZ() {
/*  53 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGroundUp() {
/*  58 */     return this.groundUp;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBitmask() {
/*  63 */     return this.bitmask;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkSection[] getChunkSections() {
/*  68 */     return this.sections;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getBiomeData() {
/*  73 */     return this.biomeData;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getHeightMap() {
/*  78 */     return this.heightMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<NBTTagCompound> getTileEntities() {
/*  83 */     return this.blockEntities;
/*     */   }
/*     */   
/*     */   public void setX(int x) {
/*  87 */     this.x = x;
/*     */   }
/*     */   
/*     */   public void setZ(int z) {
/*  91 */     this.z = z;
/*     */   }
/*     */   
/*     */   public void setGroundUp(boolean groundUp) {
/*  95 */     this.groundUp = groundUp;
/*     */   }
/*     */   
/*     */   public void setBitmask(int bitmask) {
/*  99 */     this.bitmask = bitmask;
/*     */   }
/*     */   
/*     */   public void setSections(ChunkSection[] sections) {
/* 103 */     this.sections = sections;
/*     */   }
/*     */   
/*     */   public void setBiomeData(int[] biomeData) {
/* 107 */     this.biomeData = biomeData;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeightMap(NBTTagCompound heightMap) {
/* 112 */     this.heightMap = heightMap;
/*     */   }
/*     */   
/*     */   public void setBlockEntities(List<NBTTagCompound> blockEntities) {
/* 116 */     this.blockEntities = blockEntities;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 120 */     if (o == this) {
/* 121 */       return true;
/*     */     }
/* 123 */     if (!(o instanceof BaseChunk)) {
/* 124 */       return false;
/*     */     }
/* 126 */     BaseChunk other = (BaseChunk)o;
/* 127 */     if (!other.canEqual(this)) {
/* 128 */       return false;
/*     */     }
/* 130 */     if (getChunkX() != other.getChunkX()) {
/* 131 */       return false;
/*     */     }
/* 133 */     if (getChunkZ() != other.getChunkZ()) {
/* 134 */       return false;
/*     */     }
/* 136 */     if (isGroundUp() != other.isGroundUp()) {
/* 137 */       return false;
/*     */     }
/* 139 */     if (getBitmask() != other.getBitmask()) {
/* 140 */       return false;
/*     */     }
/* 142 */     if (!Arrays.deepEquals((Object[])getChunkSections(), (Object[])other.getChunkSections())) {
/* 143 */       return false;
/*     */     }
/* 145 */     if (!Arrays.equals(getBiomeData(), other.getBiomeData())) {
/* 146 */       return false;
/*     */     }
/* 148 */     NBTTagCompound this$heightMap = getHeightMap();
/* 149 */     NBTTagCompound other$heightMap = other.getHeightMap();
/* 150 */     if ((this$heightMap == null) ? (other$heightMap != null) : !this$heightMap.equals(other$heightMap)) {
/* 151 */       return false;
/*     */     }
/* 153 */     List<NBTTagCompound> this$blockEntities = getTileEntities();
/* 154 */     List<NBTTagCompound> other$blockEntities = other.getTileEntities();
/* 155 */     return (this$blockEntities == null) ? ((other$blockEntities == null)) : this$blockEntities.equals(other$blockEntities);
/*     */   }
/*     */   
/*     */   protected boolean canEqual(Object other) {
/* 159 */     return other instanceof BaseChunk;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 163 */     int PRIME = 59;
/* 164 */     int result2 = 1;
/* 165 */     result2 = result2 * 59 + getChunkX();
/* 166 */     result2 = result2 * 59 + getChunkZ();
/* 167 */     result2 = result2 * 59 + (isGroundUp() ? 79 : 97);
/* 168 */     result2 = result2 * 59 + getBitmask();
/* 169 */     result2 = result2 * 59 + Arrays.deepHashCode((Object[])getChunkSections());
/* 170 */     result2 = result2 * 59 + Arrays.hashCode(getBiomeData());
/* 171 */     NBTTagCompound $heightMap = getHeightMap();
/* 172 */     result2 = result2 * 59 + (($heightMap == null) ? 43 : $heightMap.hashCode());
/* 173 */     List<NBTTagCompound> $blockEntities = getTileEntities();
/* 174 */     result2 = result2 * 59 + (($blockEntities == null) ? 43 : $blockEntities.hashCode());
/* 175 */     return result2;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 179 */     return "BaseChunk(x=" + getChunkX() + ", z=" + getChunkZ() + ", groundUp=" + isGroundUp() + ", bitmask=" + getBitmask() + ", sections=" + Arrays.deepToString((Object[])getChunkSections()) + ", biomeData=" + Arrays.toString(getBiomeData()) + ", heightMap=" + getHeightMap() + ", blockEntities=" + getTileEntities() + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\chunk\BaseChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */