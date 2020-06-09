/*     */ package net.minecraft.item;
/*     */ import com.google.common.collect.HashMultiset;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Multiset;
/*     */ import com.google.common.collect.Multisets;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockStone;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.minecraft.world.storage.WorldSavedData;
/*     */ 
/*     */ public class ItemMap extends ItemMapBase {
/*     */   protected ItemMap() {
/*  32 */     setHasSubtypes(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack func_190906_a(World p_190906_0_, double p_190906_1_, double p_190906_3_, byte p_190906_5_, boolean p_190906_6_, boolean p_190906_7_) {
/*  37 */     ItemStack itemstack = new ItemStack(Items.FILLED_MAP, 1, p_190906_0_.getUniqueDataId("map"));
/*  38 */     String s = "map_" + itemstack.getMetadata();
/*  39 */     MapData mapdata = new MapData(s);
/*  40 */     p_190906_0_.setItemData(s, (WorldSavedData)mapdata);
/*  41 */     mapdata.scale = p_190906_5_;
/*  42 */     mapdata.calculateMapCenter(p_190906_1_, p_190906_3_, mapdata.scale);
/*  43 */     mapdata.dimension = (byte)p_190906_0_.provider.getDimensionType().getId();
/*  44 */     mapdata.trackingPosition = p_190906_6_;
/*  45 */     mapdata.field_191096_f = p_190906_7_;
/*  46 */     mapdata.markDirty();
/*  47 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static MapData loadMapData(int mapId, World worldIn) {
/*  53 */     String s = "map_" + mapId;
/*  54 */     return (MapData)worldIn.loadItemData(MapData.class, s);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MapData getMapData(ItemStack stack, World worldIn) {
/*  60 */     String s = "map_" + stack.getMetadata();
/*  61 */     MapData mapdata = (MapData)worldIn.loadItemData(MapData.class, s);
/*     */     
/*  63 */     if (mapdata == null && !worldIn.isRemote) {
/*     */       
/*  65 */       stack.setItemDamage(worldIn.getUniqueDataId("map"));
/*  66 */       s = "map_" + stack.getMetadata();
/*  67 */       mapdata = new MapData(s);
/*  68 */       mapdata.scale = 3;
/*  69 */       mapdata.calculateMapCenter(worldIn.getWorldInfo().getSpawnX(), worldIn.getWorldInfo().getSpawnZ(), mapdata.scale);
/*  70 */       mapdata.dimension = (byte)worldIn.provider.getDimensionType().getId();
/*  71 */       mapdata.markDirty();
/*  72 */       worldIn.setItemData(s, (WorldSavedData)mapdata);
/*     */     } 
/*     */     
/*  75 */     return mapdata;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateMapData(World worldIn, Entity viewer, MapData data) {
/*  80 */     if (worldIn.provider.getDimensionType().getId() == data.dimension && viewer instanceof EntityPlayer) {
/*     */       
/*  82 */       int i = 1 << data.scale;
/*  83 */       int j = data.xCenter;
/*  84 */       int k = data.zCenter;
/*  85 */       int l = MathHelper.floor(viewer.posX - j) / i + 64;
/*  86 */       int i1 = MathHelper.floor(viewer.posZ - k) / i + 64;
/*  87 */       int j1 = 128 / i;
/*     */       
/*  89 */       if (worldIn.provider.getHasNoSky())
/*     */       {
/*  91 */         j1 /= 2;
/*     */       }
/*     */       
/*  94 */       MapData.MapInfo mapdata$mapinfo = data.getMapInfo((EntityPlayer)viewer);
/*  95 */       mapdata$mapinfo.step++;
/*  96 */       boolean flag = false;
/*     */       
/*  98 */       for (int k1 = l - j1 + 1; k1 < l + j1; k1++) {
/*     */         
/* 100 */         if ((k1 & 0xF) == (mapdata$mapinfo.step & 0xF) || flag) {
/*     */           
/* 102 */           flag = false;
/* 103 */           double d0 = 0.0D;
/*     */           
/* 105 */           for (int l1 = i1 - j1 - 1; l1 < i1 + j1; l1++) {
/*     */             
/* 107 */             if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128) {
/*     */               
/* 109 */               int i2 = k1 - l;
/* 110 */               int j2 = l1 - i1;
/* 111 */               boolean flag1 = (i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2));
/* 112 */               int k2 = (j / i + k1 - 64) * i;
/* 113 */               int l2 = (k / i + l1 - 64) * i;
/* 114 */               HashMultiset hashMultiset = HashMultiset.create();
/* 115 */               Chunk chunk = worldIn.getChunkFromBlockCoords(new BlockPos(k2, 0, l2));
/*     */               
/* 117 */               if (!chunk.isEmpty()) {
/*     */                 
/* 119 */                 int i3 = k2 & 0xF;
/* 120 */                 int j3 = l2 & 0xF;
/* 121 */                 int k3 = 0;
/* 122 */                 double d1 = 0.0D;
/*     */                 
/* 124 */                 if (worldIn.provider.getHasNoSky()) {
/*     */                   
/* 126 */                   int l3 = k2 + l2 * 231871;
/* 127 */                   l3 = l3 * l3 * 31287121 + l3 * 11;
/*     */                   
/* 129 */                   if ((l3 >> 20 & 0x1) == 0) {
/*     */                     
/* 131 */                     hashMultiset.add(Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.DIRT).getMapColor((IBlockAccess)worldIn, BlockPos.ORIGIN), 10);
/*     */                   }
/*     */                   else {
/*     */                     
/* 135 */                     hashMultiset.add(Blocks.STONE.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.STONE).getMapColor((IBlockAccess)worldIn, BlockPos.ORIGIN), 100);
/*     */                   } 
/*     */                   
/* 138 */                   d1 = 100.0D;
/*     */                 }
/*     */                 else {
/*     */                   
/* 142 */                   BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */                   
/* 144 */                   for (int i4 = 0; i4 < i; i4++) {
/*     */                     
/* 146 */                     for (int j4 = 0; j4 < i; j4++) {
/*     */                       
/* 148 */                       int k4 = chunk.getHeightValue(i4 + i3, j4 + j3) + 1;
/* 149 */                       IBlockState iblockstate = Blocks.AIR.getDefaultState();
/*     */                       
/* 151 */                       if (k4 <= 1) {
/*     */                         
/* 153 */                         iblockstate = Blocks.BEDROCK.getDefaultState();
/*     */                       } else {
/*     */                         
/*     */                         do
/*     */                         {
/*     */ 
/*     */ 
/*     */                           
/* 161 */                           k4--;
/* 162 */                           iblockstate = chunk.getBlockState(i4 + i3, k4, j4 + j3);
/* 163 */                           blockpos$mutableblockpos.setPos((chunk.xPosition << 4) + i4 + i3, k4, (chunk.zPosition << 4) + j4 + j3);
/*     */                         }
/* 165 */                         while (iblockstate.getMapColor((IBlockAccess)worldIn, (BlockPos)blockpos$mutableblockpos) == MapColor.AIR && k4 > 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                         
/* 171 */                         if (k4 > 0 && iblockstate.getMaterial().isLiquid()) {
/*     */                           IBlockState iblockstate1;
/* 173 */                           int l4 = k4 - 1;
/*     */ 
/*     */                           
/*     */                           do {
/* 177 */                             iblockstate1 = chunk.getBlockState(i4 + i3, l4--, j4 + j3);
/* 178 */                             k3++;
/*     */                           }
/* 180 */                           while (l4 > 0 && iblockstate1.getMaterial().isLiquid());
/*     */                         } 
/* 182 */                       }  d1 += 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                         
/* 189 */                         k4 / (i * i);
/* 190 */                       hashMultiset.add(iblockstate.getMapColor((IBlockAccess)worldIn, (BlockPos)blockpos$mutableblockpos));
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 
/* 195 */                 k3 /= i * i;
/* 196 */                 double d2 = (d1 - d0) * 4.0D / (i + 4) + ((k1 + l1 & 0x1) - 0.5D) * 0.4D;
/* 197 */                 int i5 = 1;
/*     */                 
/* 199 */                 if (d2 > 0.6D)
/*     */                 {
/* 201 */                   i5 = 2;
/*     */                 }
/*     */                 
/* 204 */                 if (d2 < -0.6D)
/*     */                 {
/* 206 */                   i5 = 0;
/*     */                 }
/*     */                 
/* 209 */                 MapColor mapcolor = (MapColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)hashMultiset), MapColor.AIR);
/*     */                 
/* 211 */                 if (mapcolor == MapColor.WATER) {
/*     */                   
/* 213 */                   d2 = k3 * 0.1D + (k1 + l1 & 0x1) * 0.2D;
/* 214 */                   i5 = 1;
/*     */                   
/* 216 */                   if (d2 < 0.5D)
/*     */                   {
/* 218 */                     i5 = 2;
/*     */                   }
/*     */                   
/* 221 */                   if (d2 > 0.9D)
/*     */                   {
/* 223 */                     i5 = 0;
/*     */                   }
/*     */                 } 
/*     */                 
/* 227 */                 d0 = d1;
/*     */                 
/* 229 */                 if (l1 >= 0 && i2 * i2 + j2 * j2 < j1 * j1 && (!flag1 || (k1 + l1 & 0x1) != 0)) {
/*     */                   
/* 231 */                   byte b0 = data.colors[k1 + l1 * 128];
/* 232 */                   byte b1 = (byte)(mapcolor.colorIndex * 4 + i5);
/*     */                   
/* 234 */                   if (b0 != b1) {
/*     */                     
/* 236 */                     data.colors[k1 + l1 * 128] = b1;
/* 237 */                     data.updateMapData(k1, l1);
/* 238 */                     flag = true;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190905_a(World p_190905_0_, ItemStack p_190905_1_) {
/* 251 */     if (p_190905_1_.getItem() == Items.FILLED_MAP) {
/*     */       
/* 253 */       MapData mapdata = Items.FILLED_MAP.getMapData(p_190905_1_, p_190905_0_);
/*     */       
/* 255 */       if (mapdata != null)
/*     */       {
/* 257 */         if (p_190905_0_.provider.getDimensionType().getId() == mapdata.dimension) {
/*     */           
/* 259 */           int i = 1 << mapdata.scale;
/* 260 */           int j = mapdata.xCenter;
/* 261 */           int k = mapdata.zCenter;
/* 262 */           Biome[] abiome = p_190905_0_.getBiomeProvider().getBiomes(null, (j / i - 64) * i, (k / i - 64) * i, 128 * i, 128 * i, false);
/*     */           
/* 264 */           for (int l = 0; l < 128; l++) {
/*     */             
/* 266 */             for (int i1 = 0; i1 < 128; i1++) {
/*     */               
/* 268 */               int j1 = l * i;
/* 269 */               int k1 = i1 * i;
/* 270 */               Biome biome = abiome[j1 + k1 * 128 * i];
/* 271 */               MapColor mapcolor = MapColor.AIR;
/* 272 */               int l1 = 3;
/* 273 */               int i2 = 8;
/*     */               
/* 275 */               if (l > 0 && i1 > 0 && l < 127 && i1 < 127) {
/*     */                 
/* 277 */                 if (abiome[(l - 1) * i + (i1 - 1) * i * 128 * i].getBaseHeight() >= 0.0F)
/*     */                 {
/* 279 */                   i2--;
/*     */                 }
/*     */                 
/* 282 */                 if (abiome[(l - 1) * i + (i1 + 1) * i * 128 * i].getBaseHeight() >= 0.0F)
/*     */                 {
/* 284 */                   i2--;
/*     */                 }
/*     */                 
/* 287 */                 if (abiome[(l - 1) * i + i1 * i * 128 * i].getBaseHeight() >= 0.0F)
/*     */                 {
/* 289 */                   i2--;
/*     */                 }
/*     */                 
/* 292 */                 if (abiome[(l + 1) * i + (i1 - 1) * i * 128 * i].getBaseHeight() >= 0.0F)
/*     */                 {
/* 294 */                   i2--;
/*     */                 }
/*     */                 
/* 297 */                 if (abiome[(l + 1) * i + (i1 + 1) * i * 128 * i].getBaseHeight() >= 0.0F)
/*     */                 {
/* 299 */                   i2--;
/*     */                 }
/*     */                 
/* 302 */                 if (abiome[(l + 1) * i + i1 * i * 128 * i].getBaseHeight() >= 0.0F)
/*     */                 {
/* 304 */                   i2--;
/*     */                 }
/*     */                 
/* 307 */                 if (abiome[l * i + (i1 - 1) * i * 128 * i].getBaseHeight() >= 0.0F)
/*     */                 {
/* 309 */                   i2--;
/*     */                 }
/*     */                 
/* 312 */                 if (abiome[l * i + (i1 + 1) * i * 128 * i].getBaseHeight() >= 0.0F)
/*     */                 {
/* 314 */                   i2--;
/*     */                 }
/*     */                 
/* 317 */                 if (biome.getBaseHeight() < 0.0F) {
/*     */                   
/* 319 */                   mapcolor = MapColor.ADOBE;
/*     */                   
/* 321 */                   if (i2 > 7 && i1 % 2 == 0) {
/*     */                     
/* 323 */                     l1 = (l + (int)(MathHelper.sin(i1 + 0.0F) * 7.0F)) / 8 % 5;
/*     */                     
/* 325 */                     if (l1 == 3)
/*     */                     {
/* 327 */                       l1 = 1;
/*     */                     }
/* 329 */                     else if (l1 == 4)
/*     */                     {
/* 331 */                       l1 = 0;
/*     */                     }
/*     */                   
/* 334 */                   } else if (i2 > 7) {
/*     */                     
/* 336 */                     mapcolor = MapColor.AIR;
/*     */                   }
/* 338 */                   else if (i2 > 5) {
/*     */                     
/* 340 */                     l1 = 1;
/*     */                   }
/* 342 */                   else if (i2 > 3) {
/*     */                     
/* 344 */                     l1 = 0;
/*     */                   }
/* 346 */                   else if (i2 > 1) {
/*     */                     
/* 348 */                     l1 = 0;
/*     */                   }
/*     */                 
/* 351 */                 } else if (i2 > 0) {
/*     */                   
/* 353 */                   mapcolor = MapColor.BROWN;
/*     */                   
/* 355 */                   if (i2 > 3) {
/*     */                     
/* 357 */                     l1 = 1;
/*     */                   }
/*     */                   else {
/*     */                     
/* 361 */                     l1 = 3;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */               
/* 366 */               if (mapcolor != MapColor.AIR) {
/*     */                 
/* 368 */                 mapdata.colors[l + i1 * 128] = (byte)(mapcolor.colorIndex * 4 + l1);
/* 369 */                 mapdata.updateMapData(l, i1);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
/* 384 */     if (!worldIn.isRemote) {
/*     */       
/* 386 */       MapData mapdata = getMapData(stack, worldIn);
/*     */       
/* 388 */       if (entityIn instanceof EntityPlayer) {
/*     */         
/* 390 */         EntityPlayer entityplayer = (EntityPlayer)entityIn;
/* 391 */         mapdata.updateVisiblePlayers(entityplayer, stack);
/*     */       } 
/*     */       
/* 394 */       if (isSelected || (entityIn instanceof EntityPlayer && ((EntityPlayer)entityIn).getHeldItemOffhand() == stack))
/*     */       {
/* 396 */         updateMapData(worldIn, entityIn, mapdata);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Packet<?> createMapDataPacket(ItemStack stack, World worldIn, EntityPlayer player) {
/* 404 */     return getMapData(stack, worldIn).getMapPacket(stack, worldIn, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/* 412 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */     
/* 414 */     if (nbttagcompound != null)
/*     */     {
/* 416 */       if (nbttagcompound.hasKey("map_scale_direction", 99)) {
/*     */         
/* 418 */         scaleMap(stack, worldIn, nbttagcompound.getInteger("map_scale_direction"));
/* 419 */         nbttagcompound.removeTag("map_scale_direction");
/*     */       }
/* 421 */       else if (nbttagcompound.getBoolean("map_tracking_position")) {
/*     */         
/* 423 */         enableMapTracking(stack, worldIn);
/* 424 */         nbttagcompound.removeTag("map_tracking_position");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void scaleMap(ItemStack p_185063_0_, World p_185063_1_, int p_185063_2_) {
/* 431 */     MapData mapdata = Items.FILLED_MAP.getMapData(p_185063_0_, p_185063_1_);
/* 432 */     p_185063_0_.setItemDamage(p_185063_1_.getUniqueDataId("map"));
/* 433 */     MapData mapdata1 = new MapData("map_" + p_185063_0_.getMetadata());
/*     */     
/* 435 */     if (mapdata != null) {
/*     */       
/* 437 */       mapdata1.scale = (byte)MathHelper.clamp(mapdata.scale + p_185063_2_, 0, 4);
/* 438 */       mapdata1.trackingPosition = mapdata.trackingPosition;
/* 439 */       mapdata1.calculateMapCenter(mapdata.xCenter, mapdata.zCenter, mapdata1.scale);
/* 440 */       mapdata1.dimension = mapdata.dimension;
/* 441 */       mapdata1.markDirty();
/* 442 */       p_185063_1_.setItemData("map_" + p_185063_0_.getMetadata(), (WorldSavedData)mapdata1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void enableMapTracking(ItemStack p_185064_0_, World p_185064_1_) {
/* 448 */     MapData mapdata = Items.FILLED_MAP.getMapData(p_185064_0_, p_185064_1_);
/* 449 */     p_185064_0_.setItemDamage(p_185064_1_.getUniqueDataId("map"));
/* 450 */     MapData mapdata1 = new MapData("map_" + p_185064_0_.getMetadata());
/* 451 */     mapdata1.trackingPosition = true;
/*     */     
/* 453 */     if (mapdata != null) {
/*     */       
/* 455 */       mapdata1.xCenter = mapdata.xCenter;
/* 456 */       mapdata1.zCenter = mapdata.zCenter;
/* 457 */       mapdata1.scale = mapdata.scale;
/* 458 */       mapdata1.dimension = mapdata.dimension;
/* 459 */       mapdata1.markDirty();
/* 460 */       p_185064_1_.setItemData("map_" + p_185064_0_.getMetadata(), (WorldSavedData)mapdata1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/* 469 */     if (advanced.func_194127_a()) {
/*     */       
/* 471 */       MapData mapdata = (playerIn == null) ? null : getMapData(stack, playerIn);
/*     */       
/* 473 */       if (mapdata != null) {
/*     */         
/* 475 */         tooltip.add(I18n.translateToLocalFormatted("filled_map.scale", new Object[] { Integer.valueOf(1 << mapdata.scale) }));
/* 476 */         tooltip.add(I18n.translateToLocalFormatted("filled_map.level", new Object[] { Byte.valueOf(mapdata.scale), Integer.valueOf(4) }));
/*     */       }
/*     */       else {
/*     */         
/* 480 */         tooltip.add(I18n.translateToLocal("filled_map.unknown"));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_190907_h(ItemStack p_190907_0_) {
/* 487 */     NBTTagCompound nbttagcompound = p_190907_0_.getSubCompound("display");
/*     */     
/* 489 */     if (nbttagcompound != null && nbttagcompound.hasKey("MapColor", 99)) {
/*     */       
/* 491 */       int i = nbttagcompound.getInteger("MapColor");
/* 492 */       return 0xFF000000 | i & 0xFFFFFF;
/*     */     } 
/*     */ 
/*     */     
/* 496 */     return -12173266;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */