/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.BlockSlab;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.color.BlockColors;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import optifine.Config;
/*     */ import optifine.CustomColors;
/*     */ import optifine.RenderEnv;
/*     */ import shadersmod.client.SVertexBuilder;
/*     */ 
/*     */ public class BlockFluidRenderer {
/*     */   private final BlockColors blockColors;
/*  25 */   private final TextureAtlasSprite[] atlasSpritesLava = new TextureAtlasSprite[2];
/*  26 */   private final TextureAtlasSprite[] atlasSpritesWater = new TextureAtlasSprite[2];
/*     */   
/*     */   private TextureAtlasSprite atlasSpriteWaterOverlay;
/*     */   
/*     */   public BlockFluidRenderer(BlockColors blockColorsIn) {
/*  31 */     this.blockColors = blockColorsIn;
/*  32 */     initAtlasSprites();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initAtlasSprites() {
/*  37 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/*  38 */     this.atlasSpritesLava[0] = texturemap.getAtlasSprite("minecraft:blocks/lava_still");
/*  39 */     this.atlasSpritesLava[1] = texturemap.getAtlasSprite("minecraft:blocks/lava_flow");
/*  40 */     this.atlasSpritesWater[0] = texturemap.getAtlasSprite("minecraft:blocks/water_still");
/*  41 */     this.atlasSpritesWater[1] = texturemap.getAtlasSprite("minecraft:blocks/water_flow");
/*  42 */     this.atlasSpriteWaterOverlay = texturemap.getAtlasSprite("minecraft:blocks/water_overlay");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderFluid(IBlockAccess blockAccess, IBlockState blockStateIn, BlockPos blockPosIn, BufferBuilder worldRendererIn) {
/*     */     boolean flag3;
/*     */     
/*  51 */     try { if (Config.isShaders())
/*     */       {
/*  53 */         SVertexBuilder.pushEntity(blockStateIn, blockPosIn, blockAccess, worldRendererIn);
/*     */       }
/*     */       
/*  56 */       BlockLiquid blockliquid = (BlockLiquid)blockStateIn.getBlock();
/*  57 */       boolean flag = (blockStateIn.getMaterial() == Material.LAVA);
/*  58 */       TextureAtlasSprite[] atextureatlassprite = flag ? this.atlasSpritesLava : this.atlasSpritesWater;
/*  59 */       RenderEnv renderenv = worldRendererIn.getRenderEnv(blockAccess, blockStateIn, blockPosIn);
/*  60 */       int i = CustomColors.getFluidColor(blockAccess, blockStateIn, blockPosIn, renderenv);
/*  61 */       float f = (i >> 16 & 0xFF) / 255.0F;
/*  62 */       float f1 = (i >> 8 & 0xFF) / 255.0F;
/*  63 */       float f2 = (i & 0xFF) / 255.0F;
/*  64 */       boolean flag1 = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.UP);
/*  65 */       boolean flag2 = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.DOWN);
/*  66 */       boolean[] aboolean = renderenv.getBorderFlags();
/*  67 */       aboolean[0] = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.NORTH);
/*  68 */       aboolean[1] = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.SOUTH);
/*  69 */       aboolean[2] = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.WEST);
/*  70 */       aboolean[3] = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.EAST);
/*     */       
/*  72 */       if (flag1 || flag2 || aboolean[0] || aboolean[1] || aboolean[2] || aboolean[3])
/*     */       {
/*  74 */         boolean bool1 = false;
/*  75 */         float f3 = 0.5F;
/*  76 */         float f4 = 1.0F;
/*  77 */         float f5 = 0.8F;
/*  78 */         float f6 = 0.6F;
/*  79 */         Material material = blockStateIn.getMaterial();
/*  80 */         float f7 = getFluidHeight(blockAccess, blockPosIn, material);
/*  81 */         float f8 = getFluidHeight(blockAccess, blockPosIn.south(), material);
/*  82 */         float f9 = getFluidHeight(blockAccess, blockPosIn.east().south(), material);
/*  83 */         float f10 = getFluidHeight(blockAccess, blockPosIn.east(), material);
/*  84 */         double d0 = blockPosIn.getX();
/*  85 */         double d1 = blockPosIn.getY();
/*  86 */         double d2 = blockPosIn.getZ();
/*  87 */         float f11 = 0.001F;
/*     */         
/*  89 */         if (flag1) {
/*     */           float f13, f14, f15, f16, f17, f18, f19, f20;
/*  91 */           bool1 = true;
/*  92 */           float f12 = BlockLiquid.getSlopeAngle(blockAccess, blockPosIn, material, blockStateIn);
/*  93 */           TextureAtlasSprite textureatlassprite = (f12 > -999.0F) ? atextureatlassprite[1] : atextureatlassprite[0];
/*  94 */           worldRendererIn.setSprite(textureatlassprite);
/*  95 */           f7 -= 0.001F;
/*  96 */           f8 -= 0.001F;
/*  97 */           f9 -= 0.001F;
/*  98 */           f10 -= 0.001F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 108 */           if (f12 < -999.0F) {
/*     */             
/* 110 */             f13 = textureatlassprite.getInterpolatedU(0.0D);
/* 111 */             f17 = textureatlassprite.getInterpolatedV(0.0D);
/* 112 */             f14 = f13;
/* 113 */             f18 = textureatlassprite.getInterpolatedV(16.0D);
/* 114 */             f15 = textureatlassprite.getInterpolatedU(16.0D);
/* 115 */             f19 = f18;
/* 116 */             f16 = f15;
/* 117 */             f20 = f17;
/*     */           }
/*     */           else {
/*     */             
/* 121 */             float f21 = MathHelper.sin(f12) * 0.25F;
/* 122 */             float f22 = MathHelper.cos(f12) * 0.25F;
/* 123 */             float f23 = 8.0F;
/* 124 */             f13 = textureatlassprite.getInterpolatedU((8.0F + (-f22 - f21) * 16.0F));
/* 125 */             f17 = textureatlassprite.getInterpolatedV((8.0F + (-f22 + f21) * 16.0F));
/* 126 */             f14 = textureatlassprite.getInterpolatedU((8.0F + (-f22 + f21) * 16.0F));
/* 127 */             f18 = textureatlassprite.getInterpolatedV((8.0F + (f22 + f21) * 16.0F));
/* 128 */             f15 = textureatlassprite.getInterpolatedU((8.0F + (f22 + f21) * 16.0F));
/* 129 */             f19 = textureatlassprite.getInterpolatedV((8.0F + (f22 - f21) * 16.0F));
/* 130 */             f16 = textureatlassprite.getInterpolatedU((8.0F + (f22 - f21) * 16.0F));
/* 131 */             f20 = textureatlassprite.getInterpolatedV((8.0F + (-f22 - f21) * 16.0F));
/*     */           } 
/*     */           
/* 134 */           int k2 = blockStateIn.getPackedLightmapCoords(blockAccess, blockPosIn);
/* 135 */           int l2 = k2 >> 16 & 0xFFFF;
/* 136 */           int i3 = k2 & 0xFFFF;
/* 137 */           float f24 = 1.0F * f;
/* 138 */           float f25 = 1.0F * f1;
/* 139 */           float f26 = 1.0F * f2;
/* 140 */           worldRendererIn.pos(d0 + 0.0D, d1 + f7, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f13, f17).lightmap(l2, i3).endVertex();
/* 141 */           worldRendererIn.pos(d0 + 0.0D, d1 + f8, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f14, f18).lightmap(l2, i3).endVertex();
/* 142 */           worldRendererIn.pos(d0 + 1.0D, d1 + f9, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f15, f19).lightmap(l2, i3).endVertex();
/* 143 */           worldRendererIn.pos(d0 + 1.0D, d1 + f10, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f16, f20).lightmap(l2, i3).endVertex();
/*     */           
/* 145 */           if (blockliquid.shouldRenderSides(blockAccess, blockPosIn.up())) {
/*     */             
/* 147 */             worldRendererIn.pos(d0 + 0.0D, d1 + f7, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f13, f17).lightmap(l2, i3).endVertex();
/* 148 */             worldRendererIn.pos(d0 + 1.0D, d1 + f10, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex(f16, f20).lightmap(l2, i3).endVertex();
/* 149 */             worldRendererIn.pos(d0 + 1.0D, d1 + f9, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f15, f19).lightmap(l2, i3).endVertex();
/* 150 */             worldRendererIn.pos(d0 + 0.0D, d1 + f8, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex(f14, f18).lightmap(l2, i3).endVertex();
/*     */           } 
/*     */         } 
/*     */         
/* 154 */         if (flag2) {
/*     */           
/* 156 */           float f38 = atextureatlassprite[0].getMinU();
/* 157 */           float f39 = atextureatlassprite[0].getMaxU();
/* 158 */           float f40 = atextureatlassprite[0].getMinV();
/* 159 */           float f41 = atextureatlassprite[0].getMaxV();
/* 160 */           int l1 = blockStateIn.getPackedLightmapCoords(blockAccess, blockPosIn.down());
/* 161 */           int i2 = l1 >> 16 & 0xFFFF;
/* 162 */           int j2 = l1 & 0xFFFF;
/* 163 */           worldRendererIn.pos(d0, d1, d2 + 1.0D).color(f * 0.5F, f1 * 0.5F, f2 * 0.5F, 1.0F).tex(f38, f41).lightmap(i2, j2).endVertex();
/* 164 */           worldRendererIn.pos(d0, d1, d2).color(f * 0.5F, f1 * 0.5F, f2 * 0.5F, 1.0F).tex(f38, f40).lightmap(i2, j2).endVertex();
/* 165 */           worldRendererIn.pos(d0 + 1.0D, d1, d2).color(f * 0.5F, f1 * 0.5F, f2 * 0.5F, 1.0F).tex(f39, f40).lightmap(i2, j2).endVertex();
/* 166 */           worldRendererIn.pos(d0 + 1.0D, d1, d2 + 1.0D).color(f * 0.5F, f1 * 0.5F, f2 * 0.5F, 1.0F).tex(f39, f41).lightmap(i2, j2).endVertex();
/* 167 */           bool1 = true;
/*     */         } 
/*     */         
/* 170 */         for (int i1 = 0; i1 < 4; i1++) {
/*     */           
/* 172 */           int j1 = 0;
/* 173 */           int k1 = 0;
/*     */           
/* 175 */           if (i1 == 0)
/*     */           {
/* 177 */             k1--;
/*     */           }
/*     */           
/* 180 */           if (i1 == 1)
/*     */           {
/* 182 */             k1++;
/*     */           }
/*     */           
/* 185 */           if (i1 == 2)
/*     */           {
/* 187 */             j1--;
/*     */           }
/*     */           
/* 190 */           if (i1 == 3)
/*     */           {
/* 192 */             j1++;
/*     */           }
/*     */           
/* 195 */           BlockPos blockpos = blockPosIn.add(j1, 0, k1);
/* 196 */           TextureAtlasSprite textureatlassprite1 = atextureatlassprite[1];
/* 197 */           worldRendererIn.setSprite(textureatlassprite1);
/* 198 */           float f42 = 0.0F;
/* 199 */           float f43 = 0.0F;
/*     */           
/* 201 */           if (!flag) {
/*     */             
/* 203 */             IBlockState iblockstate = blockAccess.getBlockState(blockpos);
/* 204 */             Block block = iblockstate.getBlock();
/*     */             
/* 206 */             if (block == Blocks.GLASS || block == Blocks.STAINED_GLASS || block == Blocks.BEACON || block == Blocks.SLIME_BLOCK) {
/*     */               
/* 208 */               textureatlassprite1 = this.atlasSpriteWaterOverlay;
/* 209 */               worldRendererIn.setSprite(textureatlassprite1);
/*     */             } 
/*     */             
/* 212 */             if (block == Blocks.FARMLAND || block == Blocks.GRASS_PATH) {
/*     */               
/* 214 */               f42 = 0.9375F;
/* 215 */               f43 = 0.9375F;
/*     */             } 
/*     */             
/* 218 */             if (block instanceof BlockSlab) {
/*     */               
/* 220 */               BlockSlab blockslab = (BlockSlab)block;
/*     */               
/* 222 */               if (!blockslab.isDouble() && iblockstate.getValue((IProperty)BlockSlab.HALF) == BlockSlab.EnumBlockHalf.BOTTOM) {
/*     */                 
/* 224 */                 f42 = 0.5F;
/* 225 */                 f43 = 0.5F;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 230 */           if (aboolean[i1]) {
/*     */             float f44;
/*     */             
/*     */             float f45;
/*     */             
/*     */             double d3;
/*     */             double d4;
/*     */             double d5;
/*     */             double d6;
/* 239 */             if (i1 == 0) {
/*     */               
/* 241 */               f44 = f7;
/* 242 */               f45 = f10;
/* 243 */               d3 = d0;
/* 244 */               d5 = d0 + 1.0D;
/* 245 */               d4 = d2 + 0.0010000000474974513D;
/* 246 */               d6 = d2 + 0.0010000000474974513D;
/*     */             }
/* 248 */             else if (i1 == 1) {
/*     */               
/* 250 */               f44 = f9;
/* 251 */               f45 = f8;
/* 252 */               d3 = d0 + 1.0D;
/* 253 */               d5 = d0;
/* 254 */               d4 = d2 + 1.0D - 0.0010000000474974513D;
/* 255 */               d6 = d2 + 1.0D - 0.0010000000474974513D;
/*     */             }
/* 257 */             else if (i1 == 2) {
/*     */               
/* 259 */               f44 = f8;
/* 260 */               f45 = f7;
/* 261 */               d3 = d0 + 0.0010000000474974513D;
/* 262 */               d5 = d0 + 0.0010000000474974513D;
/* 263 */               d4 = d2 + 1.0D;
/* 264 */               d6 = d2;
/*     */             }
/*     */             else {
/*     */               
/* 268 */               f44 = f10;
/* 269 */               f45 = f9;
/* 270 */               d3 = d0 + 1.0D - 0.0010000000474974513D;
/* 271 */               d5 = d0 + 1.0D - 0.0010000000474974513D;
/* 272 */               d4 = d2;
/* 273 */               d6 = d2 + 1.0D;
/*     */             } 
/*     */             
/* 276 */             if (f44 > f42 || f45 > f43) {
/*     */               
/* 278 */               f42 = Math.min(f42, f44);
/* 279 */               f43 = Math.min(f43, f45);
/*     */               
/* 281 */               if (f42 > f11)
/*     */               {
/* 283 */                 f42 -= f11;
/*     */               }
/*     */               
/* 286 */               if (f43 > f11)
/*     */               {
/* 288 */                 f43 -= f11;
/*     */               }
/*     */               
/* 291 */               bool1 = true;
/* 292 */               float f27 = textureatlassprite1.getInterpolatedU(0.0D);
/* 293 */               float f28 = textureatlassprite1.getInterpolatedU(8.0D);
/* 294 */               float f29 = textureatlassprite1.getInterpolatedV(((1.0F - f44) * 16.0F * 0.5F));
/* 295 */               float f30 = textureatlassprite1.getInterpolatedV(((1.0F - f45) * 16.0F * 0.5F));
/* 296 */               float f31 = textureatlassprite1.getInterpolatedV(8.0D);
/* 297 */               float f32 = textureatlassprite1.getInterpolatedV(((1.0F - f42) * 16.0F * 0.5F));
/* 298 */               float f33 = textureatlassprite1.getInterpolatedV(((1.0F - f43) * 16.0F * 0.5F));
/* 299 */               int j = blockStateIn.getPackedLightmapCoords(blockAccess, blockpos);
/* 300 */               int k = j >> 16 & 0xFFFF;
/* 301 */               int l = j & 0xFFFF;
/* 302 */               float f34 = (i1 < 2) ? 0.8F : 0.6F;
/* 303 */               float f35 = 1.0F * f34 * f;
/* 304 */               float f36 = 1.0F * f34 * f1;
/* 305 */               float f37 = 1.0F * f34 * f2;
/* 306 */               worldRendererIn.pos(d3, d1 + f44, d4).color(f35, f36, f37, 1.0F).tex(f27, f29).lightmap(k, l).endVertex();
/* 307 */               worldRendererIn.pos(d5, d1 + f45, d6).color(f35, f36, f37, 1.0F).tex(f28, f30).lightmap(k, l).endVertex();
/* 308 */               worldRendererIn.pos(d5, d1 + f43, d6).color(f35, f36, f37, 1.0F).tex(f28, f33).lightmap(k, l).endVertex();
/* 309 */               worldRendererIn.pos(d3, d1 + f42, d4).color(f35, f36, f37, 1.0F).tex(f27, f32).lightmap(k, l).endVertex();
/*     */               
/* 311 */               if (textureatlassprite1 != this.atlasSpriteWaterOverlay) {
/*     */                 
/* 313 */                 worldRendererIn.pos(d3, d1 + f42, d4).color(f35, f36, f37, 1.0F).tex(f27, f32).lightmap(k, l).endVertex();
/* 314 */                 worldRendererIn.pos(d5, d1 + f43, d6).color(f35, f36, f37, 1.0F).tex(f28, f33).lightmap(k, l).endVertex();
/* 315 */                 worldRendererIn.pos(d5, d1 + f45, d6).color(f35, f36, f37, 1.0F).tex(f28, f30).lightmap(k, l).endVertex();
/* 316 */                 worldRendererIn.pos(d3, d1 + f44, d4).color(f35, f36, f37, 1.0F).tex(f27, f29).lightmap(k, l).endVertex();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 322 */         worldRendererIn.setSprite(null);
/* 323 */         boolean flag4 = bool1;
/* 324 */         return flag4;
/*     */       
/*     */       }
/*     */        }
/*     */     
/*     */     finally
/*     */     
/* 331 */     { if (Config.isShaders())
/*     */       {
/* 333 */         SVertexBuilder.popEntity(worldRendererIn); }  }  if (Config.isShaders()) SVertexBuilder.popEntity(worldRendererIn);
/*     */ 
/*     */ 
/*     */     
/* 337 */     return flag3;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getFluidHeight(IBlockAccess blockAccess, BlockPos blockPosIn, Material blockMaterial) {
/* 342 */     int i = 0;
/* 343 */     float f = 0.0F;
/*     */     
/* 345 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 347 */       BlockPos blockpos = blockPosIn.add(-(j & 0x1), 0, -(j >> 1 & 0x1));
/*     */       
/* 349 */       if (blockAccess.getBlockState(blockpos.up()).getMaterial() == blockMaterial)
/*     */       {
/* 351 */         return 1.0F;
/*     */       }
/*     */       
/* 354 */       IBlockState iblockstate = blockAccess.getBlockState(blockpos);
/* 355 */       Material material = iblockstate.getMaterial();
/*     */       
/* 357 */       if (material != blockMaterial) {
/*     */         
/* 359 */         if (!material.isSolid())
/*     */         {
/* 361 */           f++;
/* 362 */           i++;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 367 */         int k = ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue();
/*     */         
/* 369 */         if (k >= 8 || k == 0) {
/*     */           
/* 371 */           f += BlockLiquid.getLiquidHeightPercent(k) * 10.0F;
/* 372 */           i += 10;
/*     */         } 
/*     */         
/* 375 */         f += BlockLiquid.getLiquidHeightPercent(k);
/* 376 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 380 */     return 1.0F - f / i;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\BlockFluidRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */