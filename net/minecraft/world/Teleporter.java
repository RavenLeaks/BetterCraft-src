/*     */ package net.minecraft.world;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockPortal;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class Teleporter
/*     */ {
/*     */   private final WorldServer worldServerInstance;
/*     */   private final Random random;
/*  24 */   private final Long2ObjectMap<PortalPosition> destinationCoordinateCache = (Long2ObjectMap<PortalPosition>)new Long2ObjectOpenHashMap(4096);
/*     */ 
/*     */   
/*     */   public Teleporter(WorldServer worldIn) {
/*  28 */     this.worldServerInstance = worldIn;
/*  29 */     this.random = new Random(worldIn.getSeed());
/*     */   }
/*     */ 
/*     */   
/*     */   public void placeInPortal(Entity entityIn, float rotationYaw) {
/*  34 */     if (this.worldServerInstance.provider.getDimensionType().getId() != 1) {
/*     */       
/*  36 */       if (!placeInExistingPortal(entityIn, rotationYaw))
/*     */       {
/*  38 */         makePortal(entityIn);
/*  39 */         placeInExistingPortal(entityIn, rotationYaw);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  44 */       int i = MathHelper.floor(entityIn.posX);
/*  45 */       int j = MathHelper.floor(entityIn.posY) - 1;
/*  46 */       int k = MathHelper.floor(entityIn.posZ);
/*  47 */       int l = 1;
/*  48 */       int i1 = 0;
/*     */       
/*  50 */       for (int j1 = -2; j1 <= 2; j1++) {
/*     */         
/*  52 */         for (int k1 = -2; k1 <= 2; k1++) {
/*     */           
/*  54 */           for (int l1 = -1; l1 < 3; l1++) {
/*     */             
/*  56 */             int i2 = i + k1 * 1 + j1 * 0;
/*  57 */             int j2 = j + l1;
/*  58 */             int k2 = k + k1 * 0 - j1 * 1;
/*  59 */             boolean flag = (l1 < 0);
/*  60 */             this.worldServerInstance.setBlockState(new BlockPos(i2, j2, k2), flag ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  65 */       entityIn.setLocationAndAngles(i, j, k, entityIn.rotationYaw, 0.0F);
/*  66 */       entityIn.motionX = 0.0D;
/*  67 */       entityIn.motionY = 0.0D;
/*  68 */       entityIn.motionZ = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
/*  74 */     int i = 128;
/*  75 */     double d0 = -1.0D;
/*  76 */     int j = MathHelper.floor(entityIn.posX);
/*  77 */     int k = MathHelper.floor(entityIn.posZ);
/*  78 */     boolean flag = true;
/*  79 */     BlockPos blockpos = BlockPos.ORIGIN;
/*  80 */     long l = ChunkPos.asLong(j, k);
/*     */     
/*  82 */     if (this.destinationCoordinateCache.containsKey(l)) {
/*     */       
/*  84 */       PortalPosition teleporter$portalposition = (PortalPosition)this.destinationCoordinateCache.get(l);
/*  85 */       d0 = 0.0D;
/*  86 */       blockpos = teleporter$portalposition;
/*  87 */       teleporter$portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
/*  88 */       flag = false;
/*     */     }
/*     */     else {
/*     */       
/*  92 */       BlockPos blockpos3 = new BlockPos(entityIn);
/*     */       
/*  94 */       for (int i1 = -128; i1 <= 128; i1++) {
/*     */ 
/*     */ 
/*     */         
/*  98 */         for (int j1 = -128; j1 <= 128; j1++) {
/*     */           
/* 100 */           for (BlockPos blockpos1 = blockpos3.add(i1, this.worldServerInstance.getActualHeight() - 1 - blockpos3.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2) {
/*     */             
/* 102 */             BlockPos blockpos2 = blockpos1.down();
/*     */             
/* 104 */             if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == Blocks.PORTAL) {
/*     */               
/* 106 */               for (blockpos2 = blockpos1.down(); this.worldServerInstance.getBlockState(blockpos2).getBlock() == Blocks.PORTAL; blockpos2 = blockpos2.down())
/*     */               {
/* 108 */                 blockpos1 = blockpos2;
/*     */               }
/*     */               
/* 111 */               double d1 = blockpos1.distanceSq((Vec3i)blockpos3);
/*     */               
/* 113 */               if (d0 < 0.0D || d1 < d0) {
/*     */                 
/* 115 */                 d0 = d1;
/* 116 */                 blockpos = blockpos1;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     if (d0 >= 0.0D) {
/*     */       
/* 126 */       if (flag)
/*     */       {
/* 128 */         this.destinationCoordinateCache.put(l, new PortalPosition(blockpos, this.worldServerInstance.getTotalWorldTime()));
/*     */       }
/*     */       
/* 131 */       double d5 = blockpos.getX() + 0.5D;
/* 132 */       double d7 = blockpos.getZ() + 0.5D;
/* 133 */       BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.PORTAL.createPatternHelper(this.worldServerInstance, blockpos);
/* 134 */       boolean flag1 = (blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE);
/* 135 */       double d2 = (blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X) ? blockpattern$patternhelper.getFrontTopLeft().getZ() : blockpattern$patternhelper.getFrontTopLeft().getX();
/* 136 */       double d6 = (blockpattern$patternhelper.getFrontTopLeft().getY() + 1) - (entityIn.getLastPortalVec()).yCoord * blockpattern$patternhelper.getHeight();
/*     */       
/* 138 */       if (flag1)
/*     */       {
/* 140 */         d2++;
/*     */       }
/*     */       
/* 143 */       if (blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X) {
/*     */         
/* 145 */         d7 = d2 + (1.0D - (entityIn.getLastPortalVec()).xCoord) * blockpattern$patternhelper.getWidth() * blockpattern$patternhelper.getForwards().rotateY().getAxisDirection().getOffset();
/*     */       }
/*     */       else {
/*     */         
/* 149 */         d5 = d2 + (1.0D - (entityIn.getLastPortalVec()).xCoord) * blockpattern$patternhelper.getWidth() * blockpattern$patternhelper.getForwards().rotateY().getAxisDirection().getOffset();
/*     */       } 
/*     */       
/* 152 */       float f = 0.0F;
/* 153 */       float f1 = 0.0F;
/* 154 */       float f2 = 0.0F;
/* 155 */       float f3 = 0.0F;
/*     */       
/* 157 */       if (blockpattern$patternhelper.getForwards().getOpposite() == entityIn.getTeleportDirection()) {
/*     */         
/* 159 */         f = 1.0F;
/* 160 */         f1 = 1.0F;
/*     */       }
/* 162 */       else if (blockpattern$patternhelper.getForwards().getOpposite() == entityIn.getTeleportDirection().getOpposite()) {
/*     */         
/* 164 */         f = -1.0F;
/* 165 */         f1 = -1.0F;
/*     */       }
/* 167 */       else if (blockpattern$patternhelper.getForwards().getOpposite() == entityIn.getTeleportDirection().rotateY()) {
/*     */         
/* 169 */         f2 = 1.0F;
/* 170 */         f3 = -1.0F;
/*     */       }
/*     */       else {
/*     */         
/* 174 */         f2 = -1.0F;
/* 175 */         f3 = 1.0F;
/*     */       } 
/*     */       
/* 178 */       double d3 = entityIn.motionX;
/* 179 */       double d4 = entityIn.motionZ;
/* 180 */       entityIn.motionX = d3 * f + d4 * f3;
/* 181 */       entityIn.motionZ = d3 * f2 + d4 * f1;
/* 182 */       entityIn.rotationYaw = rotationYaw - (entityIn.getTeleportDirection().getOpposite().getHorizontalIndex() * 90) + (blockpattern$patternhelper.getForwards().getHorizontalIndex() * 90);
/*     */       
/* 184 */       if (entityIn instanceof EntityPlayerMP) {
/*     */         
/* 186 */         ((EntityPlayerMP)entityIn).connection.setPlayerLocation(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
/*     */       }
/*     */       else {
/*     */         
/* 190 */         entityIn.setLocationAndAngles(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
/*     */       } 
/*     */       
/* 193 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean makePortal(Entity entityIn) {
/* 203 */     int i = 16;
/* 204 */     double d0 = -1.0D;
/* 205 */     int j = MathHelper.floor(entityIn.posX);
/* 206 */     int k = MathHelper.floor(entityIn.posY);
/* 207 */     int l = MathHelper.floor(entityIn.posZ);
/* 208 */     int i1 = j;
/* 209 */     int j1 = k;
/* 210 */     int k1 = l;
/* 211 */     int l1 = 0;
/* 212 */     int i2 = this.random.nextInt(4);
/* 213 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 215 */     for (int j2 = j - 16; j2 <= j + 16; j2++) {
/*     */       
/* 217 */       double d1 = j2 + 0.5D - entityIn.posX;
/*     */       
/* 219 */       for (int l2 = l - 16; l2 <= l + 16; l2++) {
/*     */         
/* 221 */         double d2 = l2 + 0.5D - entityIn.posZ;
/*     */ 
/*     */         
/* 224 */         for (int j3 = this.worldServerInstance.getActualHeight() - 1; j3 >= 0; j3--) {
/*     */           
/* 226 */           if (this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.setPos(j2, j3, l2))) {
/*     */             
/* 228 */             while (j3 > 0 && this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.setPos(j2, j3 - 1, l2)))
/*     */             {
/* 230 */               j3--;
/*     */             }
/*     */             int k3;
/* 233 */             label168: for (k3 = i2; k3 < i2 + 4; k3++) {
/*     */               
/* 235 */               int l3 = k3 % 2;
/* 236 */               int i4 = 1 - l3;
/*     */               
/* 238 */               if (k3 % 4 >= 2) {
/*     */                 
/* 240 */                 l3 = -l3;
/* 241 */                 i4 = -i4;
/*     */               } 
/*     */               
/* 244 */               for (int j4 = 0; j4 < 3; j4++) {
/*     */                 
/* 246 */                 for (int k4 = 0; k4 < 4; k4++) {
/*     */                   
/* 248 */                   for (int l4 = -1; l4 < 4; ) {
/*     */                     
/* 250 */                     int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
/* 251 */                     int j5 = j3 + l4;
/* 252 */                     int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
/* 253 */                     blockpos$mutableblockpos.setPos(i5, j5, k5);
/*     */                     
/* 255 */                     if (l4 >= 0 || this.worldServerInstance.getBlockState((BlockPos)blockpos$mutableblockpos).getMaterial().isSolid()) { if (l4 >= 0 && !this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos))
/*     */                         break label168; 
/*     */                       l4++; }
/*     */                     
/*     */                     break label168;
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 263 */               double d5 = j3 + 0.5D - entityIn.posY;
/* 264 */               double d7 = d1 * d1 + d5 * d5 + d2 * d2;
/*     */               
/* 266 */               if (d0 < 0.0D || d7 < d0) {
/*     */                 
/* 268 */                 d0 = d7;
/* 269 */                 i1 = j2;
/* 270 */                 j1 = j3;
/* 271 */                 k1 = l2;
/* 272 */                 l1 = k3 % 4;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     if (d0 < 0.0D)
/*     */     {
/* 282 */       for (int l5 = j - 16; l5 <= j + 16; l5++) {
/*     */         
/* 284 */         double d3 = l5 + 0.5D - entityIn.posX;
/*     */         
/* 286 */         for (int j6 = l - 16; j6 <= l + 16; j6++) {
/*     */           
/* 288 */           double d4 = j6 + 0.5D - entityIn.posZ;
/*     */ 
/*     */           
/* 291 */           for (int i7 = this.worldServerInstance.getActualHeight() - 1; i7 >= 0; i7--) {
/*     */             
/* 293 */             if (this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.setPos(l5, i7, j6))) {
/*     */               
/* 295 */               while (i7 > 0 && this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.setPos(l5, i7 - 1, j6)))
/*     */               {
/* 297 */                 i7--;
/*     */               }
/*     */               int k7;
/* 300 */               label167: for (k7 = i2; k7 < i2 + 2; k7++) {
/*     */                 
/* 302 */                 int j8 = k7 % 2;
/* 303 */                 int j9 = 1 - j8;
/*     */                 
/* 305 */                 for (int j10 = 0; j10 < 4; j10++) {
/*     */                   
/* 307 */                   for (int j11 = -1; j11 < 4; ) {
/*     */                     
/* 309 */                     int j12 = l5 + (j10 - 1) * j8;
/* 310 */                     int i13 = i7 + j11;
/* 311 */                     int j13 = j6 + (j10 - 1) * j9;
/* 312 */                     blockpos$mutableblockpos.setPos(j12, i13, j13);
/*     */                     
/* 314 */                     if (j11 >= 0 || this.worldServerInstance.getBlockState((BlockPos)blockpos$mutableblockpos).getMaterial().isSolid()) { if (j11 >= 0 && !this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos))
/*     */                         break label167; 
/*     */                       j11++; }
/*     */                     
/*     */                     break label167;
/*     */                   } 
/*     */                 } 
/* 321 */                 double d6 = i7 + 0.5D - entityIn.posY;
/* 322 */                 double d8 = d3 * d3 + d6 * d6 + d4 * d4;
/*     */                 
/* 324 */                 if (d0 < 0.0D || d8 < d0) {
/*     */                   
/* 326 */                   d0 = d8;
/* 327 */                   i1 = l5;
/* 328 */                   j1 = i7;
/* 329 */                   k1 = j6;
/* 330 */                   l1 = k7 % 2;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 339 */     int i6 = i1;
/* 340 */     int k2 = j1;
/* 341 */     int k6 = k1;
/* 342 */     int l6 = l1 % 2;
/* 343 */     int i3 = 1 - l6;
/*     */     
/* 345 */     if (l1 % 4 >= 2) {
/*     */       
/* 347 */       l6 = -l6;
/* 348 */       i3 = -i3;
/*     */     } 
/*     */     
/* 351 */     if (d0 < 0.0D) {
/*     */       
/* 353 */       j1 = MathHelper.clamp(j1, 70, this.worldServerInstance.getActualHeight() - 10);
/* 354 */       k2 = j1;
/*     */       
/* 356 */       for (int j7 = -1; j7 <= 1; j7++) {
/*     */         
/* 358 */         for (int l7 = 1; l7 < 3; l7++) {
/*     */           
/* 360 */           for (int k8 = -1; k8 < 3; k8++) {
/*     */             
/* 362 */             int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
/* 363 */             int k10 = k2 + k8;
/* 364 */             int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
/* 365 */             boolean flag = (k8 < 0);
/* 366 */             this.worldServerInstance.setBlockState(new BlockPos(k9, k10, k11), flag ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 372 */     IBlockState iblockstate = Blocks.PORTAL.getDefaultState().withProperty((IProperty)BlockPortal.AXIS, (l6 == 0) ? (Comparable)EnumFacing.Axis.Z : (Comparable)EnumFacing.Axis.X);
/*     */     
/* 374 */     for (int i8 = 0; i8 < 4; i8++) {
/*     */       
/* 376 */       for (int l8 = 0; l8 < 4; l8++) {
/*     */         
/* 378 */         for (int l9 = -1; l9 < 4; l9++) {
/*     */           
/* 380 */           int l10 = i6 + (l8 - 1) * l6;
/* 381 */           int l11 = k2 + l9;
/* 382 */           int k12 = k6 + (l8 - 1) * i3;
/* 383 */           boolean flag1 = !(l8 != 0 && l8 != 3 && l9 != -1 && l9 != 3);
/* 384 */           this.worldServerInstance.setBlockState(new BlockPos(l10, l11, k12), flag1 ? Blocks.OBSIDIAN.getDefaultState() : iblockstate, 2);
/*     */         } 
/*     */       } 
/*     */       
/* 388 */       for (int i9 = 0; i9 < 4; i9++) {
/*     */         
/* 390 */         for (int i10 = -1; i10 < 4; i10++) {
/*     */           
/* 392 */           int i11 = i6 + (i9 - 1) * l6;
/* 393 */           int i12 = k2 + i10;
/* 394 */           int l12 = k6 + (i9 - 1) * i3;
/* 395 */           BlockPos blockpos = new BlockPos(i11, i12, l12);
/* 396 */           this.worldServerInstance.notifyNeighborsOfStateChange(blockpos, this.worldServerInstance.getBlockState(blockpos).getBlock(), false);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 401 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeStalePortalLocations(long worldTime) {
/* 410 */     if (worldTime % 100L == 0L) {
/*     */       
/* 412 */       long i = worldTime - 300L;
/* 413 */       ObjectIterator<PortalPosition> objectiterator = this.destinationCoordinateCache.values().iterator();
/*     */       
/* 415 */       while (objectiterator.hasNext()) {
/*     */         
/* 417 */         PortalPosition teleporter$portalposition = (PortalPosition)objectiterator.next();
/*     */         
/* 419 */         if (teleporter$portalposition == null || teleporter$portalposition.lastUpdateTime < i)
/*     */         {
/* 421 */           objectiterator.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public class PortalPosition
/*     */     extends BlockPos
/*     */   {
/*     */     public long lastUpdateTime;
/*     */     
/*     */     public PortalPosition(BlockPos pos, long lastUpdate) {
/* 433 */       super(pos.getX(), pos.getY(), pos.getZ());
/* 434 */       this.lastUpdateTime = lastUpdate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\Teleporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */