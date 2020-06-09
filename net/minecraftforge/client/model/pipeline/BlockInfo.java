/*     */ package net.minecraftforge.client.model.pipeline;
/*     */ 
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.color.BlockColors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockInfo
/*     */ {
/*  31 */   private static final EnumFacing[] SIDES = EnumFacing.values();
/*     */   
/*     */   private final BlockColors colors;
/*     */   
/*     */   private IBlockAccess world;
/*     */   private IBlockState state;
/*     */   private BlockPos blockPos;
/*  38 */   private final boolean[][][] t = new boolean[3][3][3];
/*  39 */   private final int[][][] s = new int[3][3][3];
/*  40 */   private final int[][][] b = new int[3][3][3];
/*  41 */   private final float[][][][] skyLight = new float[3][2][2][2];
/*  42 */   private final float[][][][] blockLight = new float[3][2][2][2];
/*  43 */   private final float[][][] ao = new float[3][3][3];
/*     */   
/*  45 */   private final int[] packed = new int[7];
/*     */   
/*     */   private boolean full;
/*     */   
/*  49 */   private float shx = 0.0F; private float shy = 0.0F; private float shz = 0.0F;
/*     */   
/*  51 */   private int cachedTint = -1;
/*  52 */   private int cachedMultiplier = -1;
/*     */ 
/*     */   
/*     */   public BlockInfo(BlockColors colors) {
/*  56 */     this.colors = colors;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorMultiplier(int tint) {
/*  61 */     if (this.cachedTint == tint) return this.cachedMultiplier; 
/*  62 */     this.cachedTint = tint;
/*  63 */     this.cachedMultiplier = this.colors.colorMultiplier(this.state, this.world, this.blockPos, tint);
/*  64 */     return this.cachedMultiplier;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShift() {
/*  69 */     Vec3d offset = this.state.func_191059_e(this.world, this.blockPos);
/*  70 */     this.shx = (float)offset.xCoord;
/*  71 */     this.shy = (float)offset.yCoord;
/*  72 */     this.shz = (float)offset.zCoord;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorld(IBlockAccess world) {
/*  77 */     this.world = world;
/*  78 */     this.cachedTint = -1;
/*  79 */     this.cachedMultiplier = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(IBlockState state) {
/*  84 */     this.state = state;
/*  85 */     this.cachedTint = -1;
/*  86 */     this.cachedMultiplier = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockPos(BlockPos blockPos) {
/*  91 */     this.blockPos = blockPos;
/*  92 */     this.cachedTint = -1;
/*  93 */     this.cachedMultiplier = -1;
/*  94 */     this.shx = this.shy = this.shz = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  99 */     this.world = null;
/* 100 */     this.state = null;
/* 101 */     this.blockPos = null;
/* 102 */     this.cachedTint = -1;
/* 103 */     this.cachedMultiplier = -1;
/* 104 */     this.shx = this.shy = this.shz = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private float combine(int c, int s1, int s2, int s3, boolean t0, boolean t1, boolean t2, boolean t3) {
/* 109 */     if (c == 0 && !t0) c = Math.max(0, Math.max(s1, s2) - 1); 
/* 110 */     if (s1 == 0 && !t1) s1 = Math.max(0, c - 1); 
/* 111 */     if (s2 == 0 && !t2) s2 = Math.max(0, c - 1); 
/* 112 */     if (s3 == 0 && !t3) s3 = Math.max(0, Math.max(s1, s2) - 1); 
/* 113 */     return (c + s1 + s2 + s3) * 32.0F / 262140.0F;
/*     */   }
/*     */   
/*     */   public void updateLightMatrix() {
/*     */     int x;
/* 118 */     for (x = 0; x <= 2; x++) {
/*     */       
/* 120 */       for (int y = 0; y <= 2; y++) {
/*     */         
/* 122 */         for (int z = 0; z <= 2; z++) {
/*     */           
/* 124 */           BlockPos pos = this.blockPos.add(x - 1, y - 1, z - 1);
/* 125 */           IBlockState state = this.world.getBlockState(pos);
/* 126 */           this.t[x][y][z] = (state.getLightOpacity() < 15);
/* 127 */           int brightness = state.getPackedLightmapCoords(this.world, pos);
/* 128 */           this.s[x][y][z] = brightness >> 20 & 0xF;
/* 129 */           this.b[x][y][z] = brightness >> 4 & 0xF;
/* 130 */           this.ao[x][y][z] = state.getAmbientOcclusionLightValue();
/*     */         } 
/*     */       } 
/*     */     }  byte b; int i; EnumFacing[] arrayOfEnumFacing;
/* 134 */     for (i = (arrayOfEnumFacing = SIDES).length, b = 0; b < i; ) { EnumFacing side = arrayOfEnumFacing[b];
/*     */       
/* 136 */       if (!this.state.func_191057_i()) {
/*     */         
/* 138 */         int j = side.getFrontOffsetX() + 1;
/* 139 */         int y = side.getFrontOffsetY() + 1;
/* 140 */         int z = side.getFrontOffsetZ() + 1;
/* 141 */         this.s[j][y][z] = Math.max(this.s[1][1][1] - 1, this.s[j][y][z]);
/* 142 */         this.b[j][y][z] = Math.max(this.b[1][1][1] - 1, this.b[j][y][z]);
/*     */       }  b++; }
/*     */     
/* 145 */     for (x = 0; x < 2; x++) {
/*     */       
/* 147 */       for (int y = 0; y < 2; y++) {
/*     */         
/* 149 */         for (int z = 0; z < 2; z++) {
/*     */           
/* 151 */           int x1 = x * 2;
/* 152 */           int y1 = y * 2;
/* 153 */           int z1 = z * 2;
/*     */           
/* 155 */           int sxyz = this.s[x1][y1][z1];
/* 156 */           int bxyz = this.b[x1][y1][z1];
/* 157 */           boolean txyz = this.t[x1][y1][z1];
/*     */           
/* 159 */           int sxz = this.s[x1][1][z1], sxy = this.s[x1][y1][1], syz = this.s[1][y1][z1];
/* 160 */           int bxz = this.b[x1][1][z1], bxy = this.b[x1][y1][1], byz = this.b[1][y1][z1];
/* 161 */           boolean txz = this.t[x1][1][z1], txy = this.t[x1][y1][1], tyz = this.t[1][y1][z1];
/*     */           
/* 163 */           int sx = this.s[x1][1][1], sy = this.s[1][y1][1], sz = this.s[1][1][z1];
/* 164 */           int bx = this.b[x1][1][1], by = this.b[1][y1][1], bz = this.b[1][1][z1];
/* 165 */           boolean tx = this.t[x1][1][1], ty = this.t[1][y1][1], tz = this.t[1][1][z1];
/*     */           
/* 167 */           this.skyLight[0][x][y][z] = combine(sx, sxz, sxy, (txz || txy) ? sxyz : sx, 
/* 168 */               tx, txz, txy, (txz || txy) ? txyz : tx);
/* 169 */           this.blockLight[0][x][y][z] = combine(bx, bxz, bxy, (txz || txy) ? bxyz : bx, 
/* 170 */               tx, txz, txy, (txz || txy) ? txyz : tx);
/*     */           
/* 172 */           this.skyLight[1][x][y][z] = combine(sy, sxy, syz, (txy || tyz) ? sxyz : sy, 
/* 173 */               ty, txy, tyz, (txy || tyz) ? txyz : ty);
/* 174 */           this.blockLight[1][x][y][z] = combine(by, bxy, byz, (txy || tyz) ? bxyz : by, 
/* 175 */               ty, txy, tyz, (txy || tyz) ? txyz : ty);
/*     */           
/* 177 */           this.skyLight[2][x][y][z] = combine(sz, syz, sxz, (tyz || txz) ? sxyz : sz, 
/* 178 */               tz, tyz, txz, (tyz || txz) ? txyz : tz);
/* 179 */           this.blockLight[2][x][y][z] = combine(bz, byz, bxz, (tyz || txz) ? bxyz : bz, 
/* 180 */               tz, tyz, txz, (tyz || txz) ? txyz : tz);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateFlatLighting() {
/* 188 */     this.full = this.state.isFullCube();
/* 189 */     this.packed[0] = this.state.getPackedLightmapCoords(this.world, this.blockPos); byte b; int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 191 */     for (i = (arrayOfEnumFacing = SIDES).length, b = 0; b < i; ) { EnumFacing side = arrayOfEnumFacing[b];
/*     */       
/* 193 */       int j = side.ordinal() + 1;
/* 194 */       this.packed[j] = this.state.getPackedLightmapCoords(this.world, this.blockPos.offset(side));
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public IBlockAccess getWorld() {
/* 200 */     return this.world;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getState() {
/* 205 */     return this.state;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getBlockPos() {
/* 210 */     return this.blockPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[][][] getTranslucent() {
/* 215 */     return this.t;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[][][][] getSkyLight() {
/* 220 */     return this.skyLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[][][][] getBlockLight() {
/* 225 */     return this.blockLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[][][] getAo() {
/* 230 */     return this.ao;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getPackedLight() {
/* 235 */     return this.packed;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 240 */     return this.full;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getShx() {
/* 245 */     return this.shx;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getShy() {
/* 250 */     return this.shy;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getShz() {
/* 255 */     return this.shz;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCachedTint() {
/* 260 */     return this.cachedTint;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCachedMultiplier() {
/* 265 */     return this.cachedMultiplier;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraftforge\client\model\pipeline\BlockInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */