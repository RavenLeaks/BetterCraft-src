/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StructureComponent
/*     */ {
/*     */   protected StructureBoundingBox boundingBox;
/*     */   @Nullable
/*     */   private EnumFacing coordBaseMode;
/*     */   private Mirror mirror;
/*     */   private Rotation rotation;
/*     */   protected int componentType;
/*     */   
/*     */   public StructureComponent() {}
/*     */   
/*     */   protected StructureComponent(int type) {
/*  43 */     this.componentType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final NBTTagCompound createStructureBaseNBT() {
/*  54 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  55 */     nbttagcompound.setString("id", MapGenStructureIO.getStructureComponentName(this));
/*  56 */     nbttagcompound.setTag("BB", (NBTBase)this.boundingBox.toNBTTagIntArray());
/*  57 */     EnumFacing enumfacing = getCoordBaseMode();
/*  58 */     nbttagcompound.setInteger("O", (enumfacing == null) ? -1 : enumfacing.getHorizontalIndex());
/*  59 */     nbttagcompound.setInteger("GD", this.componentType);
/*  60 */     writeStructureToNBT(nbttagcompound);
/*  61 */     return nbttagcompound;
/*     */   }
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
/*     */   public void readStructureBaseNBT(World worldIn, NBTTagCompound tagCompound) {
/*  76 */     if (tagCompound.hasKey("BB"))
/*     */     {
/*  78 */       this.boundingBox = new StructureBoundingBox(tagCompound.getIntArray("BB"));
/*     */     }
/*     */     
/*  81 */     int i = tagCompound.getInteger("O");
/*  82 */     setCoordBaseMode((i == -1) ? null : EnumFacing.getHorizontal(i));
/*  83 */     this.componentType = tagCompound.getInteger("GD");
/*  84 */     readStructureFromNBT(tagCompound, worldIn.getSaveHandler().getStructureTemplateManager());
/*     */   }
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
/*     */   public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureBoundingBox getBoundingBox() {
/* 107 */     return this.boundingBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getComponentType() {
/* 115 */     return this.componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructureComponent findIntersecting(List<StructureComponent> listIn, StructureBoundingBox boundingboxIn) {
/* 123 */     for (StructureComponent structurecomponent : listIn) {
/*     */       
/* 125 */       if (structurecomponent.getBoundingBox() != null && structurecomponent.getBoundingBox().intersectsWith(boundingboxIn))
/*     */       {
/* 127 */         return structurecomponent;
/*     */       }
/*     */     } 
/*     */     
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isLiquidInStructureBoundingBox(World worldIn, StructureBoundingBox boundingboxIn) {
/* 139 */     int i = Math.max(this.boundingBox.minX - 1, boundingboxIn.minX);
/* 140 */     int j = Math.max(this.boundingBox.minY - 1, boundingboxIn.minY);
/* 141 */     int k = Math.max(this.boundingBox.minZ - 1, boundingboxIn.minZ);
/* 142 */     int l = Math.min(this.boundingBox.maxX + 1, boundingboxIn.maxX);
/* 143 */     int i1 = Math.min(this.boundingBox.maxY + 1, boundingboxIn.maxY);
/* 144 */     int j1 = Math.min(this.boundingBox.maxZ + 1, boundingboxIn.maxZ);
/* 145 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 147 */     for (int k1 = i; k1 <= l; k1++) {
/*     */       
/* 149 */       for (int l1 = k; l1 <= j1; l1++) {
/*     */         
/* 151 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(k1, j, l1)).getMaterial().isLiquid())
/*     */         {
/* 153 */           return true;
/*     */         }
/*     */         
/* 156 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(k1, i1, l1)).getMaterial().isLiquid())
/*     */         {
/* 158 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     for (int i2 = i; i2 <= l; i2++) {
/*     */       
/* 165 */       for (int k2 = j; k2 <= i1; k2++) {
/*     */         
/* 167 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(i2, k2, k)).getMaterial().isLiquid())
/*     */         {
/* 169 */           return true;
/*     */         }
/*     */         
/* 172 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(i2, k2, j1)).getMaterial().isLiquid())
/*     */         {
/* 174 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 179 */     for (int j2 = k; j2 <= j1; j2++) {
/*     */       
/* 181 */       for (int l2 = j; l2 <= i1; l2++) {
/*     */         
/* 183 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(i, l2, j2)).getMaterial().isLiquid())
/*     */         {
/* 185 */           return true;
/*     */         }
/*     */         
/* 188 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(l, l2, j2)).getMaterial().isLiquid())
/*     */         {
/* 190 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getXWithOffset(int x, int z) {
/* 200 */     EnumFacing enumfacing = getCoordBaseMode();
/*     */     
/* 202 */     if (enumfacing == null)
/*     */     {
/* 204 */       return x;
/*     */     }
/*     */ 
/*     */     
/* 208 */     switch (enumfacing) {
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/* 212 */         return this.boundingBox.minX + x;
/*     */       
/*     */       case WEST:
/* 215 */         return this.boundingBox.maxX - z;
/*     */       
/*     */       case EAST:
/* 218 */         return this.boundingBox.minX + z;
/*     */     } 
/*     */     
/* 221 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getYWithOffset(int y) {
/* 228 */     return (getCoordBaseMode() == null) ? y : (y + this.boundingBox.minY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getZWithOffset(int x, int z) {
/* 233 */     EnumFacing enumfacing = getCoordBaseMode();
/*     */     
/* 235 */     if (enumfacing == null)
/*     */     {
/* 237 */       return z;
/*     */     }
/*     */ 
/*     */     
/* 241 */     switch (enumfacing) {
/*     */       
/*     */       case NORTH:
/* 244 */         return this.boundingBox.maxZ - z;
/*     */       
/*     */       case SOUTH:
/* 247 */         return this.boundingBox.minZ + z;
/*     */       
/*     */       case WEST:
/*     */       case EAST:
/* 251 */         return this.boundingBox.minZ + x;
/*     */     } 
/*     */     
/* 254 */     return z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 261 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 263 */     if (boundingboxIn.isVecInside((Vec3i)blockpos)) {
/*     */       
/* 265 */       if (this.mirror != Mirror.NONE)
/*     */       {
/* 267 */         blockstateIn = blockstateIn.withMirror(this.mirror);
/*     */       }
/*     */       
/* 270 */       if (this.rotation != Rotation.NONE)
/*     */       {
/* 272 */         blockstateIn = blockstateIn.withRotation(this.rotation);
/*     */       }
/*     */       
/* 275 */       worldIn.setBlockState(blockpos, blockstateIn, 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState getBlockStateFromPos(World worldIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 281 */     int i = getXWithOffset(x, z);
/* 282 */     int j = getYWithOffset(y);
/* 283 */     int k = getZWithOffset(x, z);
/* 284 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 285 */     return !boundingboxIn.isVecInside((Vec3i)blockpos) ? Blocks.AIR.getDefaultState() : worldIn.getBlockState(blockpos);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int func_189916_b(World p_189916_1_, int p_189916_2_, int p_189916_3_, int p_189916_4_, StructureBoundingBox p_189916_5_) {
/* 290 */     int i = getXWithOffset(p_189916_2_, p_189916_4_);
/* 291 */     int j = getYWithOffset(p_189916_3_ + 1);
/* 292 */     int k = getZWithOffset(p_189916_2_, p_189916_4_);
/* 293 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 294 */     return !p_189916_5_.isVecInside((Vec3i)blockpos) ? EnumSkyBlock.SKY.defaultLightValue : p_189916_1_.getLightFor(EnumSkyBlock.SKY, blockpos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillWithAir(World worldIn, StructureBoundingBox structurebb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/* 303 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 305 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 307 */         for (int k = minZ; k <= maxZ; k++)
/*     */         {
/* 309 */           setBlockState(worldIn, Blocks.AIR.getDefaultState(), j, i, k, structurebb);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly) {
/* 320 */     for (int i = yMin; i <= yMax; i++) {
/*     */       
/* 322 */       for (int j = xMin; j <= xMax; j++) {
/*     */         
/* 324 */         for (int k = zMin; k <= zMax; k++) {
/*     */           
/* 326 */           if (!existingOnly || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getMaterial() != Material.AIR)
/*     */           {
/* 328 */             if (i != yMin && i != yMax && j != xMin && j != xMax && k != zMin && k != zMax) {
/*     */               
/* 330 */               setBlockState(worldIn, insideBlockState, j, i, k, boundingboxIn);
/*     */             }
/*     */             else {
/*     */               
/* 334 */               setBlockState(worldIn, boundaryBlockState, j, i, k, boundingboxIn);
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
/*     */   protected void fillWithRandomizedBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand, BlockSelector blockselector) {
/* 348 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 350 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 352 */         for (int k = minZ; k <= maxZ; k++) {
/*     */           
/* 354 */           if (!alwaysReplace || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getMaterial() != Material.AIR) {
/*     */             
/* 356 */             blockselector.selectBlocks(rand, j, i, k, !(i != minY && i != maxY && j != minX && j != maxX && k != minZ && k != maxZ));
/* 357 */             setBlockState(worldIn, blockselector.getBlockState(), j, i, k, boundingboxIn);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_189914_a(World p_189914_1_, StructureBoundingBox p_189914_2_, Random p_189914_3_, float p_189914_4_, int p_189914_5_, int p_189914_6_, int p_189914_7_, int p_189914_8_, int p_189914_9_, int p_189914_10_, IBlockState p_189914_11_, IBlockState p_189914_12_, boolean p_189914_13_, int p_189914_14_) {
/* 366 */     for (int i = p_189914_6_; i <= p_189914_9_; i++) {
/*     */       
/* 368 */       for (int j = p_189914_5_; j <= p_189914_8_; j++) {
/*     */         
/* 370 */         for (int k = p_189914_7_; k <= p_189914_10_; k++) {
/*     */           
/* 372 */           if (p_189914_3_.nextFloat() <= p_189914_4_ && (!p_189914_13_ || getBlockStateFromPos(p_189914_1_, j, i, k, p_189914_2_).getMaterial() != Material.AIR) && (p_189914_14_ <= 0 || func_189916_b(p_189914_1_, j, i, k, p_189914_2_) < p_189914_14_))
/*     */           {
/* 374 */             if (i != p_189914_6_ && i != p_189914_9_ && j != p_189914_5_ && j != p_189914_8_ && k != p_189914_7_ && k != p_189914_10_) {
/*     */               
/* 376 */               setBlockState(p_189914_1_, p_189914_12_, j, i, k, p_189914_2_);
/*     */             }
/*     */             else {
/*     */               
/* 380 */               setBlockState(p_189914_1_, p_189914_11_, j, i, k, p_189914_2_);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void randomlyPlaceBlock(World worldIn, StructureBoundingBox boundingboxIn, Random rand, float chance, int x, int y, int z, IBlockState blockstateIn) {
/* 390 */     if (rand.nextFloat() < chance)
/*     */     {
/* 392 */       setBlockState(worldIn, blockstateIn, x, y, z, boundingboxIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void randomlyRareFillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState blockstateIn, boolean excludeAir) {
/* 398 */     float f = (maxX - minX + 1);
/* 399 */     float f1 = (maxY - minY + 1);
/* 400 */     float f2 = (maxZ - minZ + 1);
/* 401 */     float f3 = minX + f / 2.0F;
/* 402 */     float f4 = minZ + f2 / 2.0F;
/*     */     
/* 404 */     for (int i = minY; i <= maxY; i++) {
/*     */       
/* 406 */       float f5 = (i - minY) / f1;
/*     */       
/* 408 */       for (int j = minX; j <= maxX; j++) {
/*     */         
/* 410 */         float f6 = (j - f3) / f * 0.5F;
/*     */         
/* 412 */         for (int k = minZ; k <= maxZ; k++) {
/*     */           
/* 414 */           float f7 = (k - f4) / f2 * 0.5F;
/*     */           
/* 416 */           if (!excludeAir || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getMaterial() != Material.AIR) {
/*     */             
/* 418 */             float f8 = f6 * f6 + f5 * f5 + f7 * f7;
/*     */             
/* 420 */             if (f8 <= 1.05F)
/*     */             {
/* 422 */               setBlockState(worldIn, blockstateIn, j, i, k, boundingboxIn);
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
/*     */   protected void clearCurrentPositionBlocksUpwards(World worldIn, int x, int y, int z, StructureBoundingBox structurebb) {
/* 435 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 437 */     if (structurebb.isVecInside((Vec3i)blockpos))
/*     */     {
/* 439 */       while (!worldIn.isAirBlock(blockpos) && blockpos.getY() < 255) {
/*     */         
/* 441 */         worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
/* 442 */         blockpos = blockpos.up();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 452 */     int i = getXWithOffset(x, z);
/* 453 */     int j = getYWithOffset(y);
/* 454 */     int k = getZWithOffset(x, z);
/*     */     
/* 456 */     if (boundingboxIn.isVecInside((Vec3i)new BlockPos(i, j, k)))
/*     */     {
/* 458 */       while ((worldIn.isAirBlock(new BlockPos(i, j, k)) || worldIn.getBlockState(new BlockPos(i, j, k)).getMaterial().isLiquid()) && j > 1) {
/*     */         
/* 460 */         worldIn.setBlockState(new BlockPos(i, j, k), blockstateIn, 2);
/* 461 */         j--;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean generateChest(World worldIn, StructureBoundingBox structurebb, Random randomIn, int x, int y, int z, ResourceLocation loot) {
/* 471 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/* 472 */     return func_191080_a(worldIn, structurebb, randomIn, blockpos, loot, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_191080_a(World p_191080_1_, StructureBoundingBox p_191080_2_, Random p_191080_3_, BlockPos p_191080_4_, ResourceLocation p_191080_5_, @Nullable IBlockState p_191080_6_) {
/* 477 */     if (p_191080_2_.isVecInside((Vec3i)p_191080_4_) && p_191080_1_.getBlockState(p_191080_4_).getBlock() != Blocks.CHEST) {
/*     */       
/* 479 */       if (p_191080_6_ == null)
/*     */       {
/* 481 */         p_191080_6_ = Blocks.CHEST.correctFacing(p_191080_1_, p_191080_4_, Blocks.CHEST.getDefaultState());
/*     */       }
/*     */       
/* 484 */       p_191080_1_.setBlockState(p_191080_4_, p_191080_6_, 2);
/* 485 */       TileEntity tileentity = p_191080_1_.getTileEntity(p_191080_4_);
/*     */       
/* 487 */       if (tileentity instanceof TileEntityChest)
/*     */       {
/* 489 */         ((TileEntityChest)tileentity).setLootTable(p_191080_5_, p_191080_3_.nextLong());
/*     */       }
/*     */       
/* 492 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 496 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean createDispenser(World p_189419_1_, StructureBoundingBox p_189419_2_, Random p_189419_3_, int p_189419_4_, int p_189419_5_, int p_189419_6_, EnumFacing p_189419_7_, ResourceLocation p_189419_8_) {
/* 502 */     BlockPos blockpos = new BlockPos(getXWithOffset(p_189419_4_, p_189419_6_), getYWithOffset(p_189419_5_), getZWithOffset(p_189419_4_, p_189419_6_));
/*     */     
/* 504 */     if (p_189419_2_.isVecInside((Vec3i)blockpos) && p_189419_1_.getBlockState(blockpos).getBlock() != Blocks.DISPENSER) {
/*     */       
/* 506 */       setBlockState(p_189419_1_, Blocks.DISPENSER.getDefaultState().withProperty((IProperty)BlockDispenser.FACING, (Comparable)p_189419_7_), p_189419_4_, p_189419_5_, p_189419_6_, p_189419_2_);
/* 507 */       TileEntity tileentity = p_189419_1_.getTileEntity(blockpos);
/*     */       
/* 509 */       if (tileentity instanceof TileEntityDispenser)
/*     */       {
/* 511 */         ((TileEntityDispenser)tileentity).setLootTable(p_189419_8_, p_189419_3_.nextLong());
/*     */       }
/*     */       
/* 514 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 518 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_189915_a(World p_189915_1_, StructureBoundingBox p_189915_2_, Random p_189915_3_, int p_189915_4_, int p_189915_5_, int p_189915_6_, EnumFacing p_189915_7_, BlockDoor p_189915_8_) {
/* 524 */     setBlockState(p_189915_1_, p_189915_8_.getDefaultState().withProperty((IProperty)BlockDoor.FACING, (Comparable)p_189915_7_), p_189915_4_, p_189915_5_, p_189915_6_, p_189915_2_);
/* 525 */     setBlockState(p_189915_1_, p_189915_8_.getDefaultState().withProperty((IProperty)BlockDoor.FACING, (Comparable)p_189915_7_).withProperty((IProperty)BlockDoor.HALF, (Comparable)BlockDoor.EnumDoorHalf.UPPER), p_189915_4_, p_189915_5_ + 1, p_189915_6_, p_189915_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void offset(int x, int y, int z) {
/* 530 */     this.boundingBox.offset(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EnumFacing getCoordBaseMode() {
/* 536 */     return this.coordBaseMode;
/*     */   }
/*     */   protected abstract void writeStructureToNBT(NBTTagCompound paramNBTTagCompound);
/*     */   
/*     */   public void setCoordBaseMode(@Nullable EnumFacing facing) {
/* 541 */     this.coordBaseMode = facing;
/*     */     
/* 543 */     if (facing == null) {
/*     */       
/* 545 */       this.rotation = Rotation.NONE;
/* 546 */       this.mirror = Mirror.NONE;
/*     */     }
/*     */     else {
/*     */       
/* 550 */       switch (facing) {
/*     */         
/*     */         case SOUTH:
/* 553 */           this.mirror = Mirror.LEFT_RIGHT;
/* 554 */           this.rotation = Rotation.NONE;
/*     */           return;
/*     */         
/*     */         case WEST:
/* 558 */           this.mirror = Mirror.LEFT_RIGHT;
/* 559 */           this.rotation = Rotation.CLOCKWISE_90;
/*     */           return;
/*     */         
/*     */         case EAST:
/* 563 */           this.mirror = Mirror.NONE;
/* 564 */           this.rotation = Rotation.CLOCKWISE_90;
/*     */           return;
/*     */       } 
/*     */       
/* 568 */       this.mirror = Mirror.NONE;
/* 569 */       this.rotation = Rotation.NONE;
/*     */     } 
/*     */   }
/*     */   protected abstract void readStructureFromNBT(NBTTagCompound paramNBTTagCompound, TemplateManager paramTemplateManager);
/*     */   
/*     */   public abstract boolean addComponentParts(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox);
/*     */   
/* 576 */   public static abstract class BlockSelector { protected IBlockState blockstate = Blocks.AIR.getDefaultState();
/*     */ 
/*     */     
/*     */     public abstract void selectBlocks(Random param1Random, int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean);
/*     */     
/*     */     public IBlockState getBlockState() {
/* 582 */       return this.blockstate;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */