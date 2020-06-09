/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import net.minecraft.nbt.NBTTagIntArray;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.Vec3i;
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
/*     */ 
/*     */ 
/*     */ public class StructureBoundingBox
/*     */ {
/*     */   public int minX;
/*     */   public int minY;
/*     */   public int minZ;
/*     */   public int maxX;
/*     */   public int maxY;
/*     */   public int maxZ;
/*     */   
/*     */   public StructureBoundingBox() {}
/*     */   
/*     */   public StructureBoundingBox(int[] coords) {
/*  34 */     if (coords.length == 6) {
/*     */       
/*  36 */       this.minX = coords[0];
/*  37 */       this.minY = coords[1];
/*  38 */       this.minZ = coords[2];
/*  39 */       this.maxX = coords[3];
/*  40 */       this.maxY = coords[4];
/*  41 */       this.maxZ = coords[5];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructureBoundingBox getNewBoundingBox() {
/*  50 */     return new StructureBoundingBox(2147483647, 2147483647, 2147483647, -2147483648, -2147483648, -2147483648);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructureBoundingBox getComponentToAddBoundingBox(int structureMinX, int structureMinY, int structureMinZ, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, EnumFacing facing) {
/*  59 */     switch (facing) {
/*     */       
/*     */       case NORTH:
/*  62 */         return new StructureBoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ - zMax + 1 + zMin, structureMinX + xMax - 1 + xMin, structureMinY + yMax - 1 + yMin, structureMinZ + zMin);
/*     */       
/*     */       case SOUTH:
/*  65 */         return new StructureBoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ + zMin, structureMinX + xMax - 1 + xMin, structureMinY + yMax - 1 + yMin, structureMinZ + zMax - 1 + zMin);
/*     */       
/*     */       case WEST:
/*  68 */         return new StructureBoundingBox(structureMinX - zMax + 1 + zMin, structureMinY + yMin, structureMinZ + xMin, structureMinX + zMin, structureMinY + yMax - 1 + yMin, structureMinZ + xMax - 1 + xMin);
/*     */       
/*     */       case EAST:
/*  71 */         return new StructureBoundingBox(structureMinX + zMin, structureMinY + yMin, structureMinZ + xMin, structureMinX + zMax - 1 + zMin, structureMinY + yMax - 1 + yMin, structureMinZ + xMax - 1 + xMin);
/*     */     } 
/*     */     
/*  74 */     return new StructureBoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ + zMin, structureMinX + xMax - 1 + xMin, structureMinY + yMax - 1 + yMin, structureMinZ + zMax - 1 + zMin);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructureBoundingBox createProper(int p_175899_0_, int p_175899_1_, int p_175899_2_, int p_175899_3_, int p_175899_4_, int p_175899_5_) {
/*  80 */     return new StructureBoundingBox(Math.min(p_175899_0_, p_175899_3_), Math.min(p_175899_1_, p_175899_4_), Math.min(p_175899_2_, p_175899_5_), Math.max(p_175899_0_, p_175899_3_), Math.max(p_175899_1_, p_175899_4_), Math.max(p_175899_2_, p_175899_5_));
/*     */   }
/*     */ 
/*     */   
/*     */   public StructureBoundingBox(StructureBoundingBox structurebb) {
/*  85 */     this.minX = structurebb.minX;
/*  86 */     this.minY = structurebb.minY;
/*  87 */     this.minZ = structurebb.minZ;
/*  88 */     this.maxX = structurebb.maxX;
/*  89 */     this.maxY = structurebb.maxY;
/*  90 */     this.maxZ = structurebb.maxZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public StructureBoundingBox(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
/*  95 */     this.minX = xMin;
/*  96 */     this.minY = yMin;
/*  97 */     this.minZ = zMin;
/*  98 */     this.maxX = xMax;
/*  99 */     this.maxY = yMax;
/* 100 */     this.maxZ = zMax;
/*     */   }
/*     */ 
/*     */   
/*     */   public StructureBoundingBox(Vec3i vec1, Vec3i vec2) {
/* 105 */     this.minX = Math.min(vec1.getX(), vec2.getX());
/* 106 */     this.minY = Math.min(vec1.getY(), vec2.getY());
/* 107 */     this.minZ = Math.min(vec1.getZ(), vec2.getZ());
/* 108 */     this.maxX = Math.max(vec1.getX(), vec2.getX());
/* 109 */     this.maxY = Math.max(vec1.getY(), vec2.getY());
/* 110 */     this.maxZ = Math.max(vec1.getZ(), vec2.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public StructureBoundingBox(int xMin, int zMin, int xMax, int zMax) {
/* 115 */     this.minX = xMin;
/* 116 */     this.minZ = zMin;
/* 117 */     this.maxX = xMax;
/* 118 */     this.maxZ = zMax;
/* 119 */     this.minY = 1;
/* 120 */     this.maxY = 512;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersectsWith(StructureBoundingBox structurebb) {
/* 128 */     return (this.maxX >= structurebb.minX && this.minX <= structurebb.maxX && this.maxZ >= structurebb.minZ && this.minZ <= structurebb.maxZ && this.maxY >= structurebb.minY && this.minY <= structurebb.maxY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersectsWith(int minXIn, int minZIn, int maxXIn, int maxZIn) {
/* 136 */     return (this.maxX >= minXIn && this.minX <= maxXIn && this.maxZ >= minZIn && this.minZ <= maxZIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expandTo(StructureBoundingBox sbb) {
/* 144 */     this.minX = Math.min(this.minX, sbb.minX);
/* 145 */     this.minY = Math.min(this.minY, sbb.minY);
/* 146 */     this.minZ = Math.min(this.minZ, sbb.minZ);
/* 147 */     this.maxX = Math.max(this.maxX, sbb.maxX);
/* 148 */     this.maxY = Math.max(this.maxY, sbb.maxY);
/* 149 */     this.maxZ = Math.max(this.maxZ, sbb.maxZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void offset(int x, int y, int z) {
/* 157 */     this.minX += x;
/* 158 */     this.minY += y;
/* 159 */     this.minZ += z;
/* 160 */     this.maxX += x;
/* 161 */     this.maxY += y;
/* 162 */     this.maxZ += z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVecInside(Vec3i vec) {
/* 170 */     return (vec.getX() >= this.minX && vec.getX() <= this.maxX && vec.getZ() >= this.minZ && vec.getZ() <= this.maxZ && vec.getY() >= this.minY && vec.getY() <= this.maxY);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3i getLength() {
/* 175 */     return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXSize() {
/* 183 */     return this.maxX - this.minX + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYSize() {
/* 191 */     return this.maxY - this.minY + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZSize() {
/* 199 */     return this.maxZ - this.minZ + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 204 */     return MoreObjects.toStringHelper(this).add("x0", this.minX).add("y0", this.minY).add("z0", this.minZ).add("x1", this.maxX).add("y1", this.maxY).add("z1", this.maxZ).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagIntArray toNBTTagIntArray() {
/* 209 */     return new NBTTagIntArray(new int[] { this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureBoundingBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */