/*     */ package net.minecraft.world.gen.structure;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockRail;
/*     */ import net.minecraft.block.BlockRailBase;
/*     */ import net.minecraft.block.BlockTorch;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.item.EntityMinecartChest;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class StructureMineshaftPieces {
/*     */   public static void registerStructurePieces() {
/*  32 */     MapGenStructureIO.registerStructureComponent((Class)Corridor.class, "MSCorridor");
/*  33 */     MapGenStructureIO.registerStructureComponent((Class)Cross.class, "MSCrossing");
/*  34 */     MapGenStructureIO.registerStructureComponent((Class)Room.class, "MSRoom");
/*  35 */     MapGenStructureIO.registerStructureComponent((Class)Stairs.class, "MSStairs");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Peice func_189940_a(List<StructureComponent> p_189940_0_, Random p_189940_1_, int p_189940_2_, int p_189940_3_, int p_189940_4_, @Nullable EnumFacing p_189940_5_, int p_189940_6_, MapGenMineshaft.Type p_189940_7_) {
/*  40 */     int i = p_189940_1_.nextInt(100);
/*     */     
/*  42 */     if (i >= 80) {
/*     */       
/*  44 */       StructureBoundingBox structureboundingbox = Cross.findCrossing(p_189940_0_, p_189940_1_, p_189940_2_, p_189940_3_, p_189940_4_, p_189940_5_);
/*     */       
/*  46 */       if (structureboundingbox != null)
/*     */       {
/*  48 */         return new Cross(p_189940_6_, p_189940_1_, structureboundingbox, p_189940_5_, p_189940_7_);
/*     */       }
/*     */     }
/*  51 */     else if (i >= 70) {
/*     */       
/*  53 */       StructureBoundingBox structureboundingbox1 = Stairs.findStairs(p_189940_0_, p_189940_1_, p_189940_2_, p_189940_3_, p_189940_4_, p_189940_5_);
/*     */       
/*  55 */       if (structureboundingbox1 != null)
/*     */       {
/*  57 */         return new Stairs(p_189940_6_, p_189940_1_, structureboundingbox1, p_189940_5_, p_189940_7_);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  62 */       StructureBoundingBox structureboundingbox2 = Corridor.findCorridorSize(p_189940_0_, p_189940_1_, p_189940_2_, p_189940_3_, p_189940_4_, p_189940_5_);
/*     */       
/*  64 */       if (structureboundingbox2 != null)
/*     */       {
/*  66 */         return new Corridor(p_189940_6_, p_189940_1_, structureboundingbox2, p_189940_5_, p_189940_7_);
/*     */       }
/*     */     } 
/*     */     
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Peice func_189938_b(StructureComponent p_189938_0_, List<StructureComponent> p_189938_1_, Random p_189938_2_, int p_189938_3_, int p_189938_4_, int p_189938_5_, EnumFacing p_189938_6_, int p_189938_7_) {
/*  75 */     if (p_189938_7_ > 8)
/*     */     {
/*  77 */       return null;
/*     */     }
/*  79 */     if (Math.abs(p_189938_3_ - (p_189938_0_.getBoundingBox()).minX) <= 80 && Math.abs(p_189938_5_ - (p_189938_0_.getBoundingBox()).minZ) <= 80) {
/*     */       
/*  81 */       MapGenMineshaft.Type mapgenmineshaft$type = ((Peice)p_189938_0_).mineShaftType;
/*  82 */       Peice structuremineshaftpieces$peice = func_189940_a(p_189938_1_, p_189938_2_, p_189938_3_, p_189938_4_, p_189938_5_, p_189938_6_, p_189938_7_ + 1, mapgenmineshaft$type);
/*     */       
/*  84 */       if (structuremineshaftpieces$peice != null) {
/*     */         
/*  86 */         p_189938_1_.add(structuremineshaftpieces$peice);
/*  87 */         structuremineshaftpieces$peice.buildComponent(p_189938_0_, p_189938_1_, p_189938_2_);
/*     */       } 
/*     */       
/*  90 */       return structuremineshaftpieces$peice;
/*     */     } 
/*     */ 
/*     */     
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Corridor
/*     */     extends Peice
/*     */   {
/*     */     private boolean hasRails;
/*     */     
/*     */     private boolean hasSpiders;
/*     */     
/*     */     private boolean spawnerPlaced;
/*     */     private int sectionCount;
/*     */     
/*     */     public Corridor() {}
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 111 */       super.writeStructureToNBT(tagCompound);
/* 112 */       tagCompound.setBoolean("hr", this.hasRails);
/* 113 */       tagCompound.setBoolean("sc", this.hasSpiders);
/* 114 */       tagCompound.setBoolean("hps", this.spawnerPlaced);
/* 115 */       tagCompound.setInteger("Num", this.sectionCount);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 120 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 121 */       this.hasRails = tagCompound.getBoolean("hr");
/* 122 */       this.hasSpiders = tagCompound.getBoolean("sc");
/* 123 */       this.spawnerPlaced = tagCompound.getBoolean("hps");
/* 124 */       this.sectionCount = tagCompound.getInteger("Num");
/*     */     }
/*     */ 
/*     */     
/*     */     public Corridor(int p_i47140_1_, Random p_i47140_2_, StructureBoundingBox p_i47140_3_, EnumFacing p_i47140_4_, MapGenMineshaft.Type p_i47140_5_) {
/* 129 */       super(p_i47140_1_, p_i47140_5_);
/* 130 */       setCoordBaseMode(p_i47140_4_);
/* 131 */       this.boundingBox = p_i47140_3_;
/* 132 */       this.hasRails = (p_i47140_2_.nextInt(3) == 0);
/* 133 */       this.hasSpiders = (!this.hasRails && p_i47140_2_.nextInt(23) == 0);
/*     */       
/* 135 */       if (getCoordBaseMode().getAxis() == EnumFacing.Axis.Z) {
/*     */         
/* 137 */         this.sectionCount = p_i47140_3_.getZSize() / 5;
/*     */       }
/*     */       else {
/*     */         
/* 141 */         this.sectionCount = p_i47140_3_.getXSize() / 5;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public static StructureBoundingBox findCorridorSize(List<StructureComponent> p_175814_0_, Random rand, int x, int y, int z, EnumFacing facing) {
/* 147 */       StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);
/*     */       
/*     */       int i;
/* 150 */       for (i = rand.nextInt(3) + 2; i > 0; i--) {
/*     */         
/* 152 */         int j = i * 5;
/*     */         
/* 154 */         switch (facing) {
/*     */ 
/*     */           
/*     */           default:
/* 158 */             structureboundingbox.maxX = x + 2;
/* 159 */             structureboundingbox.minZ = z - j - 1;
/*     */             break;
/*     */           
/*     */           case SOUTH:
/* 163 */             structureboundingbox.maxX = x + 2;
/* 164 */             structureboundingbox.maxZ = z + j - 1;
/*     */             break;
/*     */           
/*     */           case WEST:
/* 168 */             structureboundingbox.minX = x - j - 1;
/* 169 */             structureboundingbox.maxZ = z + 2;
/*     */             break;
/*     */           
/*     */           case EAST:
/* 173 */             structureboundingbox.maxX = x + j - 1;
/* 174 */             structureboundingbox.maxZ = z + 2;
/*     */             break;
/*     */         } 
/* 177 */         if (StructureComponent.findIntersecting(p_175814_0_, structureboundingbox) == null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 183 */       return (i > 0) ? structureboundingbox : null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 188 */       int i = getComponentType();
/* 189 */       int j = rand.nextInt(4);
/* 190 */       EnumFacing enumfacing = getCoordBaseMode();
/*     */       
/* 192 */       if (enumfacing != null)
/*     */       {
/* 194 */         switch (enumfacing) {
/*     */ 
/*     */           
/*     */           default:
/* 198 */             if (j <= 1) {
/*     */               
/* 200 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, enumfacing, i); break;
/*     */             } 
/* 202 */             if (j == 2) {
/*     */               
/* 204 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, EnumFacing.WEST, i);
/*     */               
/*     */               break;
/*     */             } 
/* 208 */             StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, EnumFacing.EAST, i);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case SOUTH:
/* 214 */             if (j <= 1) {
/*     */               
/* 216 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, enumfacing, i); break;
/*     */             } 
/* 218 */             if (j == 2) {
/*     */               
/* 220 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.WEST, i);
/*     */               
/*     */               break;
/*     */             } 
/* 224 */             StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.EAST, i);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case WEST:
/* 230 */             if (j <= 1) {
/*     */               
/* 232 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, enumfacing, i); break;
/*     */             } 
/* 234 */             if (j == 2) {
/*     */               
/* 236 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */               
/*     */               break;
/*     */             } 
/* 240 */             StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case EAST:
/* 246 */             if (j <= 1) {
/*     */               
/* 248 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, enumfacing, i); break;
/*     */             } 
/* 250 */             if (j == 2) {
/*     */               
/* 252 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */               
/*     */               break;
/*     */             } 
/* 256 */             StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */             break;
/*     */         } 
/*     */       
/*     */       }
/* 261 */       if (i < 8)
/*     */       {
/* 263 */         if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.SOUTH) {
/*     */           
/* 265 */           for (int i1 = this.boundingBox.minX + 3; i1 + 3 <= this.boundingBox.maxX; i1 += 5) {
/*     */             
/* 267 */             int j1 = rand.nextInt(5);
/*     */             
/* 269 */             if (j1 == 0)
/*     */             {
/* 271 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, i1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i + 1);
/*     */             }
/* 273 */             else if (j1 == 1)
/*     */             {
/* 275 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, i1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i + 1);
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/* 281 */           for (int k = this.boundingBox.minZ + 3; k + 3 <= this.boundingBox.maxZ; k += 5) {
/*     */             
/* 283 */             int l = rand.nextInt(5);
/*     */             
/* 285 */             if (l == 0) {
/*     */               
/* 287 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, k, EnumFacing.WEST, i + 1);
/*     */             }
/* 289 */             else if (l == 1) {
/*     */               
/* 291 */               StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, k, EnumFacing.EAST, i + 1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean generateChest(World worldIn, StructureBoundingBox structurebb, Random randomIn, int x, int y, int z, ResourceLocation loot) {
/* 300 */       BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */       
/* 302 */       if (structurebb.isVecInside((Vec3i)blockpos) && worldIn.getBlockState(blockpos).getMaterial() == Material.AIR && worldIn.getBlockState(blockpos.down()).getMaterial() != Material.AIR) {
/*     */         
/* 304 */         IBlockState iblockstate = Blocks.RAIL.getDefaultState().withProperty((IProperty)BlockRail.SHAPE, randomIn.nextBoolean() ? (Comparable)BlockRailBase.EnumRailDirection.NORTH_SOUTH : (Comparable)BlockRailBase.EnumRailDirection.EAST_WEST);
/* 305 */         setBlockState(worldIn, iblockstate, x, y, z, structurebb);
/* 306 */         EntityMinecartChest entityminecartchest = new EntityMinecartChest(worldIn, (blockpos.getX() + 0.5F), (blockpos.getY() + 0.5F), (blockpos.getZ() + 0.5F));
/* 307 */         entityminecartchest.setLootTable(loot, randomIn.nextLong());
/* 308 */         worldIn.spawnEntityInWorld((Entity)entityminecartchest);
/* 309 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 313 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 319 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*     */       {
/* 321 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 325 */       int i = 0;
/* 326 */       int j = 2;
/* 327 */       int k = 0;
/* 328 */       int l = 2;
/* 329 */       int i1 = this.sectionCount * 5 - 1;
/* 330 */       IBlockState iblockstate = func_189917_F_();
/* 331 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 1, i1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 332 */       func_189914_a(worldIn, structureBoundingBoxIn, randomIn, 0.8F, 0, 2, 0, 2, 2, i1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false, 0);
/*     */       
/* 334 */       if (this.hasSpiders)
/*     */       {
/* 336 */         func_189914_a(worldIn, structureBoundingBoxIn, randomIn, 0.6F, 0, 0, 0, 2, 1, i1, Blocks.WEB.getDefaultState(), Blocks.AIR.getDefaultState(), false, 8);
/*     */       }
/*     */       
/* 339 */       for (int j1 = 0; j1 < this.sectionCount; j1++) {
/*     */         
/* 341 */         int k1 = 2 + j1 * 5;
/* 342 */         func_189921_a(worldIn, structureBoundingBoxIn, 0, 0, k1, 2, 2, randomIn);
/* 343 */         func_189922_a(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 0, 2, k1 - 1);
/* 344 */         func_189922_a(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 2, 2, k1 - 1);
/* 345 */         func_189922_a(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 0, 2, k1 + 1);
/* 346 */         func_189922_a(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 2, 2, k1 + 1);
/* 347 */         func_189922_a(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 0, 2, k1 - 2);
/* 348 */         func_189922_a(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 2, 2, k1 - 2);
/* 349 */         func_189922_a(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 0, 2, k1 + 2);
/* 350 */         func_189922_a(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 2, 2, k1 + 2);
/*     */         
/* 352 */         if (randomIn.nextInt(100) == 0)
/*     */         {
/* 354 */           generateChest(worldIn, structureBoundingBoxIn, randomIn, 2, 0, k1 - 1, LootTableList.CHESTS_ABANDONED_MINESHAFT);
/*     */         }
/*     */         
/* 357 */         if (randomIn.nextInt(100) == 0)
/*     */         {
/* 359 */           generateChest(worldIn, structureBoundingBoxIn, randomIn, 0, 0, k1 + 1, LootTableList.CHESTS_ABANDONED_MINESHAFT);
/*     */         }
/*     */         
/* 362 */         if (this.hasSpiders && !this.spawnerPlaced) {
/*     */           
/* 364 */           int l1 = getYWithOffset(0);
/* 365 */           int i2 = k1 - 1 + randomIn.nextInt(3);
/* 366 */           int j2 = getXWithOffset(1, i2);
/* 367 */           int k2 = getZWithOffset(1, i2);
/* 368 */           BlockPos blockpos = new BlockPos(j2, l1, k2);
/*     */           
/* 370 */           if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos) && func_189916_b(worldIn, 1, 0, i2, structureBoundingBoxIn) < 8) {
/*     */             
/* 372 */             this.spawnerPlaced = true;
/* 373 */             worldIn.setBlockState(blockpos, Blocks.MOB_SPAWNER.getDefaultState(), 2);
/* 374 */             TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */             
/* 376 */             if (tileentity instanceof TileEntityMobSpawner)
/*     */             {
/* 378 */               ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().func_190894_a(EntityList.func_191306_a(EntityCaveSpider.class));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 384 */       for (int l2 = 0; l2 <= 2; l2++) {
/*     */         
/* 386 */         for (int i3 = 0; i3 <= i1; i3++) {
/*     */           
/* 388 */           int k3 = -1;
/* 389 */           IBlockState iblockstate3 = getBlockStateFromPos(worldIn, l2, -1, i3, structureBoundingBoxIn);
/*     */           
/* 391 */           if (iblockstate3.getMaterial() == Material.AIR && func_189916_b(worldIn, l2, -1, i3, structureBoundingBoxIn) < 8) {
/*     */             
/* 393 */             int l3 = -1;
/* 394 */             setBlockState(worldIn, iblockstate, l2, -1, i3, structureBoundingBoxIn);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 399 */       if (this.hasRails) {
/*     */         
/* 401 */         IBlockState iblockstate1 = Blocks.RAIL.getDefaultState().withProperty((IProperty)BlockRail.SHAPE, (Comparable)BlockRailBase.EnumRailDirection.NORTH_SOUTH);
/*     */         
/* 403 */         for (int j3 = 0; j3 <= i1; j3++) {
/*     */           
/* 405 */           IBlockState iblockstate2 = getBlockStateFromPos(worldIn, 1, -1, j3, structureBoundingBoxIn);
/*     */           
/* 407 */           if (iblockstate2.getMaterial() != Material.AIR && iblockstate2.isFullBlock()) {
/*     */             
/* 409 */             float f = (func_189916_b(worldIn, 1, 0, j3, structureBoundingBoxIn) > 8) ? 0.9F : 0.7F;
/* 410 */             randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, f, 1, 0, j3, iblockstate1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 415 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void func_189921_a(World p_189921_1_, StructureBoundingBox p_189921_2_, int p_189921_3_, int p_189921_4_, int p_189921_5_, int p_189921_6_, int p_189921_7_, Random p_189921_8_) {
/* 421 */       if (func_189918_a(p_189921_1_, p_189921_2_, p_189921_3_, p_189921_7_, p_189921_6_, p_189921_5_)) {
/*     */         
/* 423 */         IBlockState iblockstate = func_189917_F_();
/* 424 */         IBlockState iblockstate1 = func_189919_b();
/* 425 */         IBlockState iblockstate2 = Blocks.AIR.getDefaultState();
/* 426 */         fillWithBlocks(p_189921_1_, p_189921_2_, p_189921_3_, p_189921_4_, p_189921_5_, p_189921_3_, p_189921_6_ - 1, p_189921_5_, iblockstate1, iblockstate2, false);
/* 427 */         fillWithBlocks(p_189921_1_, p_189921_2_, p_189921_7_, p_189921_4_, p_189921_5_, p_189921_7_, p_189921_6_ - 1, p_189921_5_, iblockstate1, iblockstate2, false);
/*     */         
/* 429 */         if (p_189921_8_.nextInt(4) == 0) {
/*     */           
/* 431 */           fillWithBlocks(p_189921_1_, p_189921_2_, p_189921_3_, p_189921_6_, p_189921_5_, p_189921_3_, p_189921_6_, p_189921_5_, iblockstate, iblockstate2, false);
/* 432 */           fillWithBlocks(p_189921_1_, p_189921_2_, p_189921_7_, p_189921_6_, p_189921_5_, p_189921_7_, p_189921_6_, p_189921_5_, iblockstate, iblockstate2, false);
/*     */         }
/*     */         else {
/*     */           
/* 436 */           fillWithBlocks(p_189921_1_, p_189921_2_, p_189921_3_, p_189921_6_, p_189921_5_, p_189921_7_, p_189921_6_, p_189921_5_, iblockstate, iblockstate2, false);
/* 437 */           randomlyPlaceBlock(p_189921_1_, p_189921_2_, p_189921_8_, 0.05F, p_189921_3_ + 1, p_189921_6_, p_189921_5_ - 1, Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.NORTH));
/* 438 */           randomlyPlaceBlock(p_189921_1_, p_189921_2_, p_189921_8_, 0.05F, p_189921_3_ + 1, p_189921_6_, p_189921_5_ + 1, Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.SOUTH));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_189922_a(World p_189922_1_, StructureBoundingBox p_189922_2_, Random p_189922_3_, float p_189922_4_, int p_189922_5_, int p_189922_6_, int p_189922_7_) {
/* 445 */       if (func_189916_b(p_189922_1_, p_189922_5_, p_189922_6_, p_189922_7_, p_189922_2_) < 8)
/*     */       {
/* 447 */         randomlyPlaceBlock(p_189922_1_, p_189922_2_, p_189922_3_, p_189922_4_, p_189922_5_, p_189922_6_, p_189922_7_, Blocks.WEB.getDefaultState());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Cross
/*     */     extends Peice
/*     */   {
/*     */     private EnumFacing corridorDirection;
/*     */     
/*     */     private boolean isMultipleFloors;
/*     */     
/*     */     public Cross() {}
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 463 */       super.writeStructureToNBT(tagCompound);
/* 464 */       tagCompound.setBoolean("tf", this.isMultipleFloors);
/* 465 */       tagCompound.setInteger("D", this.corridorDirection.getHorizontalIndex());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 470 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 471 */       this.isMultipleFloors = tagCompound.getBoolean("tf");
/* 472 */       this.corridorDirection = EnumFacing.getHorizontal(tagCompound.getInteger("D"));
/*     */     }
/*     */ 
/*     */     
/*     */     public Cross(int p_i47139_1_, Random p_i47139_2_, StructureBoundingBox p_i47139_3_, @Nullable EnumFacing p_i47139_4_, MapGenMineshaft.Type p_i47139_5_) {
/* 477 */       super(p_i47139_1_, p_i47139_5_);
/* 478 */       this.corridorDirection = p_i47139_4_;
/* 479 */       this.boundingBox = p_i47139_3_;
/* 480 */       this.isMultipleFloors = (p_i47139_3_.getYSize() > 3);
/*     */     }
/*     */ 
/*     */     
/*     */     public static StructureBoundingBox findCrossing(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing) {
/* 485 */       StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);
/*     */       
/* 487 */       if (rand.nextInt(4) == 0)
/*     */       {
/* 489 */         structureboundingbox.maxY += 4;
/*     */       }
/*     */       
/* 492 */       switch (facing) {
/*     */ 
/*     */         
/*     */         default:
/* 496 */           structureboundingbox.minX = x - 1;
/* 497 */           structureboundingbox.maxX = x + 3;
/* 498 */           structureboundingbox.minZ = z - 4;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 502 */           structureboundingbox.minX = x - 1;
/* 503 */           structureboundingbox.maxX = x + 3;
/* 504 */           structureboundingbox.maxZ = z + 3 + 1;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 508 */           structureboundingbox.minX = x - 4;
/* 509 */           structureboundingbox.minZ = z - 1;
/* 510 */           structureboundingbox.maxZ = z + 3;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 514 */           structureboundingbox.maxX = x + 3 + 1;
/* 515 */           structureboundingbox.minZ = z - 1;
/* 516 */           structureboundingbox.maxZ = z + 3;
/*     */           break;
/*     */       } 
/* 519 */       return (StructureComponent.findIntersecting(listIn, structureboundingbox) != null) ? null : structureboundingbox;
/*     */     }
/*     */ 
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 524 */       int i = getComponentType();
/*     */       
/* 526 */       switch (this.corridorDirection) {
/*     */ 
/*     */         
/*     */         default:
/* 530 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/* 531 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/* 532 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 536 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/* 537 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/* 538 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */           break;
/*     */         
/*     */         case WEST:
/* 542 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/* 543 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/* 544 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 548 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/* 549 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/* 550 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */           break;
/*     */       } 
/* 553 */       if (this.isMultipleFloors) {
/*     */         
/* 555 */         if (rand.nextBoolean())
/*     */         {
/* 557 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */         }
/*     */         
/* 560 */         if (rand.nextBoolean())
/*     */         {
/* 562 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/*     */         }
/*     */         
/* 565 */         if (rand.nextBoolean())
/*     */         {
/* 567 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */         }
/*     */         
/* 570 */         if (rand.nextBoolean())
/*     */         {
/* 572 */           StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 579 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*     */       {
/* 581 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 585 */       IBlockState iblockstate = func_189917_F_();
/*     */       
/* 587 */       if (this.isMultipleFloors) {
/*     */         
/* 589 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 590 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 591 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 592 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 593 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*     */       }
/*     */       else {
/*     */         
/* 597 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 598 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*     */       } 
/*     */       
/* 601 */       func_189923_b(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
/* 602 */       func_189923_b(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);
/* 603 */       func_189923_b(worldIn, structureBoundingBoxIn, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
/* 604 */       func_189923_b(worldIn, structureBoundingBoxIn, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);
/*     */       
/* 606 */       for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; i++) {
/*     */         
/* 608 */         for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; j++) {
/*     */           
/* 610 */           if (getBlockStateFromPos(worldIn, i, this.boundingBox.minY - 1, j, structureBoundingBoxIn).getMaterial() == Material.AIR && func_189916_b(worldIn, i, this.boundingBox.minY - 1, j, structureBoundingBoxIn) < 8)
/*     */           {
/* 612 */             setBlockState(worldIn, iblockstate, i, this.boundingBox.minY - 1, j, structureBoundingBoxIn);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 617 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void func_189923_b(World p_189923_1_, StructureBoundingBox p_189923_2_, int p_189923_3_, int p_189923_4_, int p_189923_5_, int p_189923_6_) {
/* 623 */       if (getBlockStateFromPos(p_189923_1_, p_189923_3_, p_189923_6_ + 1, p_189923_5_, p_189923_2_).getMaterial() != Material.AIR)
/*     */       {
/* 625 */         fillWithBlocks(p_189923_1_, p_189923_2_, p_189923_3_, p_189923_4_, p_189923_5_, p_189923_3_, p_189923_6_, p_189923_5_, func_189917_F_(), Blocks.AIR.getDefaultState(), false);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class Peice
/*     */     extends StructureComponent
/*     */   {
/*     */     protected MapGenMineshaft.Type mineShaftType;
/*     */ 
/*     */     
/*     */     public Peice() {}
/*     */     
/*     */     public Peice(int p_i47138_1_, MapGenMineshaft.Type p_i47138_2_) {
/* 640 */       super(p_i47138_1_);
/* 641 */       this.mineShaftType = p_i47138_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 646 */       tagCompound.setInteger("MST", this.mineShaftType.ordinal());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 651 */       this.mineShaftType = MapGenMineshaft.Type.byId(tagCompound.getInteger("MST"));
/*     */     }
/*     */ 
/*     */     
/*     */     protected IBlockState func_189917_F_() {
/* 656 */       switch (this.mineShaftType) {
/*     */ 
/*     */         
/*     */         default:
/* 660 */           return Blocks.PLANKS.getDefaultState();
/*     */         case null:
/*     */           break;
/* 663 */       }  return Blocks.PLANKS.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, (Comparable)BlockPlanks.EnumType.DARK_OAK);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected IBlockState func_189919_b() {
/* 669 */       switch (this.mineShaftType) {
/*     */ 
/*     */         
/*     */         default:
/* 673 */           return Blocks.OAK_FENCE.getDefaultState();
/*     */         case null:
/*     */           break;
/* 676 */       }  return Blocks.DARK_OAK_FENCE.getDefaultState();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean func_189918_a(World p_189918_1_, StructureBoundingBox p_189918_2_, int p_189918_3_, int p_189918_4_, int p_189918_5_, int p_189918_6_) {
/* 682 */       for (int i = p_189918_3_; i <= p_189918_4_; i++) {
/*     */         
/* 684 */         if (getBlockStateFromPos(p_189918_1_, i, p_189918_5_ + 1, p_189918_6_, p_189918_2_).getMaterial() == Material.AIR)
/*     */         {
/* 686 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 690 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Room
/*     */     extends Peice {
/* 696 */     private final List<StructureBoundingBox> roomsLinkedToTheRoom = Lists.newLinkedList();
/*     */ 
/*     */ 
/*     */     
/*     */     public Room() {}
/*     */ 
/*     */     
/*     */     public Room(int p_i47137_1_, Random p_i47137_2_, int p_i47137_3_, int p_i47137_4_, MapGenMineshaft.Type p_i47137_5_) {
/* 704 */       super(p_i47137_1_, p_i47137_5_);
/* 705 */       this.mineShaftType = p_i47137_5_;
/* 706 */       this.boundingBox = new StructureBoundingBox(p_i47137_3_, 50, p_i47137_4_, p_i47137_3_ + 7 + p_i47137_2_.nextInt(6), 54 + p_i47137_2_.nextInt(6), p_i47137_4_ + 7 + p_i47137_2_.nextInt(6));
/*     */     }
/*     */ 
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 711 */       int i = getComponentType();
/* 712 */       int j = this.boundingBox.getYSize() - 3 - 1;
/*     */       
/* 714 */       if (j <= 0)
/*     */       {
/* 716 */         j = 1;
/*     */       }
/*     */       
/*     */       int k;
/*     */       
/* 721 */       for (k = 0; k < this.boundingBox.getXSize(); k += 4) {
/*     */         
/* 723 */         k += rand.nextInt(this.boundingBox.getXSize());
/*     */         
/* 725 */         if (k + 3 > this.boundingBox.getXSize()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 730 */         StructureMineshaftPieces.Peice structuremineshaftpieces$peice = StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX + k, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */         
/* 732 */         if (structuremineshaftpieces$peice != null) {
/*     */           
/* 734 */           StructureBoundingBox structureboundingbox = structuremineshaftpieces$peice.getBoundingBox();
/* 735 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(structureboundingbox.minX, structureboundingbox.minY, this.boundingBox.minZ, structureboundingbox.maxX, structureboundingbox.maxY, this.boundingBox.minZ + 1));
/*     */         } 
/*     */       } 
/*     */       
/* 739 */       for (k = 0; k < this.boundingBox.getXSize(); k += 4) {
/*     */         
/* 741 */         k += rand.nextInt(this.boundingBox.getXSize());
/*     */         
/* 743 */         if (k + 3 > this.boundingBox.getXSize()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 748 */         StructureMineshaftPieces.Peice structuremineshaftpieces$peice1 = StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX + k, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */         
/* 750 */         if (structuremineshaftpieces$peice1 != null) {
/*     */           
/* 752 */           StructureBoundingBox structureboundingbox1 = structuremineshaftpieces$peice1.getBoundingBox();
/* 753 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(structureboundingbox1.minX, structureboundingbox1.minY, this.boundingBox.maxZ - 1, structureboundingbox1.maxX, structureboundingbox1.maxY, this.boundingBox.maxZ));
/*     */         } 
/*     */       } 
/*     */       
/* 757 */       for (k = 0; k < this.boundingBox.getZSize(); k += 4) {
/*     */         
/* 759 */         k += rand.nextInt(this.boundingBox.getZSize());
/*     */         
/* 761 */         if (k + 3 > this.boundingBox.getZSize()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 766 */         StructureMineshaftPieces.Peice structuremineshaftpieces$peice2 = StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ + k, EnumFacing.WEST, i);
/*     */         
/* 768 */         if (structuremineshaftpieces$peice2 != null) {
/*     */           
/* 770 */           StructureBoundingBox structureboundingbox2 = structuremineshaftpieces$peice2.getBoundingBox();
/* 771 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, structureboundingbox2.minY, structureboundingbox2.minZ, this.boundingBox.minX + 1, structureboundingbox2.maxY, structureboundingbox2.maxZ));
/*     */         } 
/*     */       } 
/*     */       
/* 775 */       for (k = 0; k < this.boundingBox.getZSize(); k += 4) {
/*     */         
/* 777 */         k += rand.nextInt(this.boundingBox.getZSize());
/*     */         
/* 779 */         if (k + 3 > this.boundingBox.getZSize()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 784 */         StructureComponent structurecomponent = StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ + k, EnumFacing.EAST, i);
/*     */         
/* 786 */         if (structurecomponent != null) {
/*     */           
/* 788 */           StructureBoundingBox structureboundingbox3 = structurecomponent.getBoundingBox();
/* 789 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - 1, structureboundingbox3.minY, structureboundingbox3.minZ, this.boundingBox.maxX, structureboundingbox3.maxY, structureboundingbox3.maxZ));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 796 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*     */       {
/* 798 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 802 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.DIRT.getDefaultState(), Blocks.AIR.getDefaultState(), true);
/* 803 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*     */       
/* 805 */       for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom)
/*     */       {
/* 807 */         fillWithBlocks(worldIn, structureBoundingBoxIn, structureboundingbox.minX, structureboundingbox.maxY - 2, structureboundingbox.minZ, structureboundingbox.maxX, structureboundingbox.maxY, structureboundingbox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*     */       }
/*     */       
/* 810 */       randomlyRareFillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.AIR.getDefaultState(), false);
/* 811 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void offset(int x, int y, int z) {
/* 817 */       super.offset(x, y, z);
/*     */       
/* 819 */       for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom)
/*     */       {
/* 821 */         structureboundingbox.offset(x, y, z);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 827 */       super.writeStructureToNBT(tagCompound);
/* 828 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 830 */       for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom)
/*     */       {
/* 832 */         nbttaglist.appendTag((NBTBase)structureboundingbox.toNBTTagIntArray());
/*     */       }
/*     */       
/* 835 */       tagCompound.setTag("Entrances", (NBTBase)nbttaglist);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 840 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 841 */       NBTTagList nbttaglist = tagCompound.getTagList("Entrances", 11);
/*     */       
/* 843 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */       {
/* 845 */         this.roomsLinkedToTheRoom.add(new StructureBoundingBox(nbttaglist.getIntArrayAt(i)));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Stairs
/*     */     extends Peice
/*     */   {
/*     */     public Stairs() {}
/*     */ 
/*     */     
/*     */     public Stairs(int p_i47136_1_, Random p_i47136_2_, StructureBoundingBox p_i47136_3_, EnumFacing p_i47136_4_, MapGenMineshaft.Type p_i47136_5_) {
/* 858 */       super(p_i47136_1_, p_i47136_5_);
/* 859 */       setCoordBaseMode(p_i47136_4_);
/* 860 */       this.boundingBox = p_i47136_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public static StructureBoundingBox findStairs(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing) {
/* 865 */       StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y - 5, z, x, y + 2, z);
/*     */       
/* 867 */       switch (facing) {
/*     */ 
/*     */         
/*     */         default:
/* 871 */           structureboundingbox.maxX = x + 2;
/* 872 */           structureboundingbox.minZ = z - 8;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 876 */           structureboundingbox.maxX = x + 2;
/* 877 */           structureboundingbox.maxZ = z + 8;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 881 */           structureboundingbox.minX = x - 8;
/* 882 */           structureboundingbox.maxZ = z + 2;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 886 */           structureboundingbox.maxX = x + 8;
/* 887 */           structureboundingbox.maxZ = z + 2;
/*     */           break;
/*     */       } 
/* 890 */       return (StructureComponent.findIntersecting(listIn, structureboundingbox) != null) ? null : structureboundingbox;
/*     */     }
/*     */ 
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 895 */       int i = getComponentType();
/* 896 */       EnumFacing enumfacing = getCoordBaseMode();
/*     */       
/* 898 */       if (enumfacing != null) {
/*     */         
/* 900 */         switch (enumfacing) {
/*     */ 
/*     */           
/*     */           default:
/* 904 */             StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */             return;
/*     */           
/*     */           case SOUTH:
/* 908 */             StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */             return;
/*     */           
/*     */           case WEST:
/* 912 */             StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, i); return;
/*     */           case EAST:
/*     */             break;
/*     */         } 
/* 916 */         StructureMineshaftPieces.func_189938_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, i);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 923 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*     */       {
/* 925 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 929 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 2, 7, 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 930 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 7, 2, 2, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*     */       
/* 932 */       for (int i = 0; i < 5; i++)
/*     */       {
/* 934 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5 - i - ((i < 4) ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*     */       }
/*     */       
/* 937 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureMineshaftPieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */