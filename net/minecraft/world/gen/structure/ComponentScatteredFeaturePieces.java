/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockFlowerPot;
/*     */ import net.minecraft.block.BlockLever;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockRedstoneRepeater;
/*     */ import net.minecraft.block.BlockSandStone;
/*     */ import net.minecraft.block.BlockStairs;
/*     */ import net.minecraft.block.BlockStoneBrick;
/*     */ import net.minecraft.block.BlockStoneSlab;
/*     */ import net.minecraft.block.BlockTripWire;
/*     */ import net.minecraft.block.BlockTripWireHook;
/*     */ import net.minecraft.block.BlockVine;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.template.PlacementSettings;
/*     */ import net.minecraft.world.gen.structure.template.Template;
/*     */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class ComponentScatteredFeaturePieces
/*     */ {
/*     */   public static void registerScatteredFeaturePieces() {
/*  41 */     MapGenStructureIO.registerStructureComponent((Class)DesertPyramid.class, "TeDP");
/*  42 */     MapGenStructureIO.registerStructureComponent((Class)JunglePyramid.class, "TeJP");
/*  43 */     MapGenStructureIO.registerStructureComponent((Class)SwampHut.class, "TeSH");
/*  44 */     MapGenStructureIO.registerStructureComponent((Class)Igloo.class, "Iglu");
/*     */   }
/*     */   
/*     */   public static class DesertPyramid
/*     */     extends Feature {
/*  49 */     private final boolean[] hasPlacedChest = new boolean[4];
/*     */ 
/*     */ 
/*     */     
/*     */     public DesertPyramid() {}
/*     */ 
/*     */     
/*     */     public DesertPyramid(Random p_i2062_1_, int p_i2062_2_, int p_i2062_3_) {
/*  57 */       super(p_i2062_1_, p_i2062_2_, 64, p_i2062_3_, 21, 15, 21);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  62 */       super.writeStructureToNBT(tagCompound);
/*  63 */       tagCompound.setBoolean("hasPlacedChest0", this.hasPlacedChest[0]);
/*  64 */       tagCompound.setBoolean("hasPlacedChest1", this.hasPlacedChest[1]);
/*  65 */       tagCompound.setBoolean("hasPlacedChest2", this.hasPlacedChest[2]);
/*  66 */       tagCompound.setBoolean("hasPlacedChest3", this.hasPlacedChest[3]);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  71 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  72 */       this.hasPlacedChest[0] = tagCompound.getBoolean("hasPlacedChest0");
/*  73 */       this.hasPlacedChest[1] = tagCompound.getBoolean("hasPlacedChest1");
/*  74 */       this.hasPlacedChest[2] = tagCompound.getBoolean("hasPlacedChest2");
/*  75 */       this.hasPlacedChest[3] = tagCompound.getBoolean("hasPlacedChest3");
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  80 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, -4, 0, this.scatteredFeatureSizeX - 1, 0, this.scatteredFeatureSizeZ - 1, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/*     */       
/*  82 */       for (int i = 1; i <= 9; i++) {
/*     */         
/*  84 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, i, i, this.scatteredFeatureSizeX - 1 - i, i, this.scatteredFeatureSizeZ - 1 - i, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/*  85 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i + 1, i, i + 1, this.scatteredFeatureSizeX - 2 - i, i, this.scatteredFeatureSizeZ - 2 - i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*     */       } 
/*     */       
/*  88 */       for (int i2 = 0; i2 < this.scatteredFeatureSizeX; i2++) {
/*     */         
/*  90 */         for (int j = 0; j < this.scatteredFeatureSizeZ; j++) {
/*     */           
/*  92 */           int k = -5;
/*  93 */           replaceAirAndLiquidDownwards(worldIn, Blocks.SANDSTONE.getDefaultState(), i2, -5, j, structureBoundingBoxIn);
/*     */         } 
/*     */       } 
/*     */       
/*  97 */       IBlockState iblockstate1 = Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH);
/*  98 */       IBlockState iblockstate2 = Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.SOUTH);
/*  99 */       IBlockState iblockstate3 = Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.EAST);
/* 100 */       IBlockState iblockstate = Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.WEST);
/* 101 */       int l = (EnumDyeColor.ORANGE.getDyeDamage() ^ 0xFFFFFFFF) & 0xF;
/* 102 */       int i1 = (EnumDyeColor.BLUE.getDyeDamage() ^ 0xFFFFFFFF) & 0xF;
/* 103 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 9, 4, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 104 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 10, 1, 3, 10, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 105 */       setBlockState(worldIn, iblockstate1, 2, 10, 0, structureBoundingBoxIn);
/* 106 */       setBlockState(worldIn, iblockstate2, 2, 10, 4, structureBoundingBoxIn);
/* 107 */       setBlockState(worldIn, iblockstate3, 0, 10, 2, structureBoundingBoxIn);
/* 108 */       setBlockState(worldIn, iblockstate, 4, 10, 2, structureBoundingBoxIn);
/* 109 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.scatteredFeatureSizeX - 5, 0, 0, this.scatteredFeatureSizeX - 1, 9, 4, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 110 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.scatteredFeatureSizeX - 4, 10, 1, this.scatteredFeatureSizeX - 2, 10, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 111 */       setBlockState(worldIn, iblockstate1, this.scatteredFeatureSizeX - 3, 10, 0, structureBoundingBoxIn);
/* 112 */       setBlockState(worldIn, iblockstate2, this.scatteredFeatureSizeX - 3, 10, 4, structureBoundingBoxIn);
/* 113 */       setBlockState(worldIn, iblockstate3, this.scatteredFeatureSizeX - 5, 10, 2, structureBoundingBoxIn);
/* 114 */       setBlockState(worldIn, iblockstate, this.scatteredFeatureSizeX - 1, 10, 2, structureBoundingBoxIn);
/* 115 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 12, 4, 4, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 116 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 0, 11, 3, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 117 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 9, 1, 1, structureBoundingBoxIn);
/* 118 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 9, 2, 1, structureBoundingBoxIn);
/* 119 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 9, 3, 1, structureBoundingBoxIn);
/* 120 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 10, 3, 1, structureBoundingBoxIn);
/* 121 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 11, 3, 1, structureBoundingBoxIn);
/* 122 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 11, 2, 1, structureBoundingBoxIn);
/* 123 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 11, 1, 1, structureBoundingBoxIn);
/* 124 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 8, 3, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 125 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 2, 8, 2, 2, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 126 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, 1, 16, 3, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 127 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, 2, 16, 2, 2, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 128 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 5, this.scatteredFeatureSizeX - 6, 4, this.scatteredFeatureSizeZ - 6, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 129 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 4, 9, 11, 4, 11, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 130 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 8, 8, 3, 8, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), false);
/* 131 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, 8, 12, 3, 8, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), false);
/* 132 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 12, 8, 3, 12, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), false);
/* 133 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, 12, 12, 3, 12, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), false);
/* 134 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 4, 4, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 135 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.scatteredFeatureSizeX - 5, 1, 5, this.scatteredFeatureSizeX - 2, 4, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 136 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 7, 9, 6, 7, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 137 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.scatteredFeatureSizeX - 7, 7, 9, this.scatteredFeatureSizeX - 7, 7, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 138 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 9, 5, 7, 11, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), false);
/* 139 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.scatteredFeatureSizeX - 6, 5, 9, this.scatteredFeatureSizeX - 6, 7, 11, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), false);
/* 140 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 5, 5, 10, structureBoundingBoxIn);
/* 141 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 5, 6, 10, structureBoundingBoxIn);
/* 142 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 6, 6, 10, structureBoundingBoxIn);
/* 143 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), this.scatteredFeatureSizeX - 6, 5, 10, structureBoundingBoxIn);
/* 144 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), this.scatteredFeatureSizeX - 6, 6, 10, structureBoundingBoxIn);
/* 145 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), this.scatteredFeatureSizeX - 7, 6, 10, structureBoundingBoxIn);
/* 146 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 4, 4, 2, 6, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 147 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.scatteredFeatureSizeX - 3, 4, 4, this.scatteredFeatureSizeX - 3, 6, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 148 */       setBlockState(worldIn, iblockstate1, 2, 4, 5, structureBoundingBoxIn);
/* 149 */       setBlockState(worldIn, iblockstate1, 2, 3, 4, structureBoundingBoxIn);
/* 150 */       setBlockState(worldIn, iblockstate1, this.scatteredFeatureSizeX - 3, 4, 5, structureBoundingBoxIn);
/* 151 */       setBlockState(worldIn, iblockstate1, this.scatteredFeatureSizeX - 3, 3, 4, structureBoundingBoxIn);
/* 152 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 3, 2, 2, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 153 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.scatteredFeatureSizeX - 3, 1, 3, this.scatteredFeatureSizeX - 2, 2, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 154 */       setBlockState(worldIn, Blocks.SANDSTONE.getDefaultState(), 1, 1, 2, structureBoundingBoxIn);
/* 155 */       setBlockState(worldIn, Blocks.SANDSTONE.getDefaultState(), this.scatteredFeatureSizeX - 2, 1, 2, structureBoundingBoxIn);
/* 156 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SAND.getMetadata()), 1, 2, 2, structureBoundingBoxIn);
/* 157 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SAND.getMetadata()), this.scatteredFeatureSizeX - 2, 2, 2, structureBoundingBoxIn);
/* 158 */       setBlockState(worldIn, iblockstate, 2, 1, 2, structureBoundingBoxIn);
/* 159 */       setBlockState(worldIn, iblockstate3, this.scatteredFeatureSizeX - 3, 1, 2, structureBoundingBoxIn);
/* 160 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 5, 4, 3, 18, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 161 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.scatteredFeatureSizeX - 5, 3, 5, this.scatteredFeatureSizeX - 5, 3, 17, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 162 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 5, 4, 2, 16, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 163 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.scatteredFeatureSizeX - 6, 1, 5, this.scatteredFeatureSizeX - 5, 2, 16, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*     */       
/* 165 */       for (int j1 = 5; j1 <= 17; j1 += 2) {
/*     */         
/* 167 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 4, 1, j1, structureBoundingBoxIn);
/* 168 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 4, 2, j1, structureBoundingBoxIn);
/* 169 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), this.scatteredFeatureSizeX - 5, 1, j1, structureBoundingBoxIn);
/* 170 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), this.scatteredFeatureSizeX - 5, 2, j1, structureBoundingBoxIn);
/*     */       } 
/*     */       
/* 173 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 10, 0, 7, structureBoundingBoxIn);
/* 174 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 10, 0, 8, structureBoundingBoxIn);
/* 175 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 9, 0, 9, structureBoundingBoxIn);
/* 176 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 11, 0, 9, structureBoundingBoxIn);
/* 177 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 8, 0, 10, structureBoundingBoxIn);
/* 178 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 12, 0, 10, structureBoundingBoxIn);
/* 179 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 7, 0, 10, structureBoundingBoxIn);
/* 180 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 13, 0, 10, structureBoundingBoxIn);
/* 181 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 9, 0, 11, structureBoundingBoxIn);
/* 182 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 11, 0, 11, structureBoundingBoxIn);
/* 183 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 10, 0, 12, structureBoundingBoxIn);
/* 184 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 10, 0, 13, structureBoundingBoxIn);
/* 185 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(i1), 10, 0, 10, structureBoundingBoxIn);
/*     */       
/* 187 */       for (int j2 = 0; j2 <= this.scatteredFeatureSizeX - 1; j2 += this.scatteredFeatureSizeX - 1) {
/*     */         
/* 189 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), j2, 2, 1, structureBoundingBoxIn);
/* 190 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), j2, 2, 2, structureBoundingBoxIn);
/* 191 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), j2, 2, 3, structureBoundingBoxIn);
/* 192 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), j2, 3, 1, structureBoundingBoxIn);
/* 193 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), j2, 3, 2, structureBoundingBoxIn);
/* 194 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), j2, 3, 3, structureBoundingBoxIn);
/* 195 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), j2, 4, 1, structureBoundingBoxIn);
/* 196 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), j2, 4, 2, structureBoundingBoxIn);
/* 197 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), j2, 4, 3, structureBoundingBoxIn);
/* 198 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), j2, 5, 1, structureBoundingBoxIn);
/* 199 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), j2, 5, 2, structureBoundingBoxIn);
/* 200 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), j2, 5, 3, structureBoundingBoxIn);
/* 201 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), j2, 6, 1, structureBoundingBoxIn);
/* 202 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), j2, 6, 2, structureBoundingBoxIn);
/* 203 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), j2, 6, 3, structureBoundingBoxIn);
/* 204 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), j2, 7, 1, structureBoundingBoxIn);
/* 205 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), j2, 7, 2, structureBoundingBoxIn);
/* 206 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), j2, 7, 3, structureBoundingBoxIn);
/* 207 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), j2, 8, 1, structureBoundingBoxIn);
/* 208 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), j2, 8, 2, structureBoundingBoxIn);
/* 209 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), j2, 8, 3, structureBoundingBoxIn);
/*     */       } 
/*     */       
/* 212 */       for (int k2 = 2; k2 <= this.scatteredFeatureSizeX - 3; k2 += this.scatteredFeatureSizeX - 3 - 2) {
/*     */         
/* 214 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), k2 - 1, 2, 0, structureBoundingBoxIn);
/* 215 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), k2, 2, 0, structureBoundingBoxIn);
/* 216 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), k2 + 1, 2, 0, structureBoundingBoxIn);
/* 217 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), k2 - 1, 3, 0, structureBoundingBoxIn);
/* 218 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), k2, 3, 0, structureBoundingBoxIn);
/* 219 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), k2 + 1, 3, 0, structureBoundingBoxIn);
/* 220 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), k2 - 1, 4, 0, structureBoundingBoxIn);
/* 221 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), k2, 4, 0, structureBoundingBoxIn);
/* 222 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), k2 + 1, 4, 0, structureBoundingBoxIn);
/* 223 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), k2 - 1, 5, 0, structureBoundingBoxIn);
/* 224 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), k2, 5, 0, structureBoundingBoxIn);
/* 225 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), k2 + 1, 5, 0, structureBoundingBoxIn);
/* 226 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), k2 - 1, 6, 0, structureBoundingBoxIn);
/* 227 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), k2, 6, 0, structureBoundingBoxIn);
/* 228 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), k2 + 1, 6, 0, structureBoundingBoxIn);
/* 229 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), k2 - 1, 7, 0, structureBoundingBoxIn);
/* 230 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), k2, 7, 0, structureBoundingBoxIn);
/* 231 */         setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), k2 + 1, 7, 0, structureBoundingBoxIn);
/* 232 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), k2 - 1, 8, 0, structureBoundingBoxIn);
/* 233 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), k2, 8, 0, structureBoundingBoxIn);
/* 234 */         setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), k2 + 1, 8, 0, structureBoundingBoxIn);
/*     */       } 
/*     */       
/* 237 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 4, 0, 12, 6, 0, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), false);
/* 238 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 8, 6, 0, structureBoundingBoxIn);
/* 239 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 12, 6, 0, structureBoundingBoxIn);
/* 240 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 9, 5, 0, structureBoundingBoxIn);
/* 241 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 10, 5, 0, structureBoundingBoxIn);
/* 242 */       setBlockState(worldIn, Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(l), 11, 5, 0, structureBoundingBoxIn);
/* 243 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, -14, 8, 12, -11, 12, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), false);
/* 244 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, -10, 8, 12, -10, 12, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), false);
/* 245 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, -9, 8, 12, -9, 12, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), false);
/* 246 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, -8, 8, 12, -1, 12, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), false);
/* 247 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, -11, 9, 11, -1, 11, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 248 */       setBlockState(worldIn, Blocks.STONE_PRESSURE_PLATE.getDefaultState(), 10, -11, 10, structureBoundingBoxIn);
/* 249 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, -13, 9, 11, -13, 11, Blocks.TNT.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 250 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 8, -11, 10, structureBoundingBoxIn);
/* 251 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 8, -10, 10, structureBoundingBoxIn);
/* 252 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 7, -10, 10, structureBoundingBoxIn);
/* 253 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 7, -11, 10, structureBoundingBoxIn);
/* 254 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 12, -11, 10, structureBoundingBoxIn);
/* 255 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 12, -10, 10, structureBoundingBoxIn);
/* 256 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 13, -10, 10, structureBoundingBoxIn);
/* 257 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 13, -11, 10, structureBoundingBoxIn);
/* 258 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 10, -11, 8, structureBoundingBoxIn);
/* 259 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 10, -10, 8, structureBoundingBoxIn);
/* 260 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 10, -10, 7, structureBoundingBoxIn);
/* 261 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 10, -11, 7, structureBoundingBoxIn);
/* 262 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 10, -11, 12, structureBoundingBoxIn);
/* 263 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 10, -10, 12, structureBoundingBoxIn);
/* 264 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 10, -10, 13, structureBoundingBoxIn);
/* 265 */       setBlockState(worldIn, Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 10, -11, 13, structureBoundingBoxIn);
/*     */       
/* 267 */       for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 269 */         if (!this.hasPlacedChest[enumfacing.getHorizontalIndex()]) {
/*     */           
/* 271 */           int k1 = enumfacing.getFrontOffsetX() * 2;
/* 272 */           int l1 = enumfacing.getFrontOffsetZ() * 2;
/* 273 */           this.hasPlacedChest[enumfacing.getHorizontalIndex()] = generateChest(worldIn, structureBoundingBoxIn, randomIn, 10 + k1, -11, 10 + l1, LootTableList.CHESTS_DESERT_PYRAMID);
/*     */         } 
/*     */       } 
/*     */       
/* 277 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract class Feature
/*     */     extends StructureComponent {
/*     */     protected int scatteredFeatureSizeX;
/*     */     protected int scatteredFeatureSizeY;
/*     */     protected int scatteredFeatureSizeZ;
/* 286 */     protected int horizontalPos = -1;
/*     */ 
/*     */ 
/*     */     
/*     */     public Feature() {}
/*     */ 
/*     */     
/*     */     protected Feature(Random rand, int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
/* 294 */       super(0);
/* 295 */       this.scatteredFeatureSizeX = sizeX;
/* 296 */       this.scatteredFeatureSizeY = sizeY;
/* 297 */       this.scatteredFeatureSizeZ = sizeZ;
/* 298 */       setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));
/*     */       
/* 300 */       if (getCoordBaseMode().getAxis() == EnumFacing.Axis.Z) {
/*     */         
/* 302 */         this.boundingBox = new StructureBoundingBox(x, y, z, x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1);
/*     */       }
/*     */       else {
/*     */         
/* 306 */         this.boundingBox = new StructureBoundingBox(x, y, z, x + sizeZ - 1, y + sizeY - 1, z + sizeX - 1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 312 */       tagCompound.setInteger("Width", this.scatteredFeatureSizeX);
/* 313 */       tagCompound.setInteger("Height", this.scatteredFeatureSizeY);
/* 314 */       tagCompound.setInteger("Depth", this.scatteredFeatureSizeZ);
/* 315 */       tagCompound.setInteger("HPos", this.horizontalPos);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 320 */       this.scatteredFeatureSizeX = tagCompound.getInteger("Width");
/* 321 */       this.scatteredFeatureSizeY = tagCompound.getInteger("Height");
/* 322 */       this.scatteredFeatureSizeZ = tagCompound.getInteger("Depth");
/* 323 */       this.horizontalPos = tagCompound.getInteger("HPos");
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean offsetToAverageGroundLevel(World worldIn, StructureBoundingBox structurebb, int yOffset) {
/* 328 */       if (this.horizontalPos >= 0)
/*     */       {
/* 330 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 334 */       int i = 0;
/* 335 */       int j = 0;
/* 336 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */       
/* 338 */       for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; k++) {
/*     */         
/* 340 */         for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; l++) {
/*     */           
/* 342 */           blockpos$mutableblockpos.setPos(l, 64, k);
/*     */           
/* 344 */           if (structurebb.isVecInside((Vec3i)blockpos$mutableblockpos)) {
/*     */             
/* 346 */             i += Math.max(worldIn.getTopSolidOrLiquidBlock((BlockPos)blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel());
/* 347 */             j++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 352 */       if (j == 0)
/*     */       {
/* 354 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 358 */       this.horizontalPos = i / j;
/* 359 */       this.boundingBox.offset(0, this.horizontalPos - this.boundingBox.minY + yOffset, 0);
/* 360 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Igloo
/*     */     extends Feature
/*     */   {
/* 368 */     private static final ResourceLocation IGLOO_TOP_ID = new ResourceLocation("igloo/igloo_top");
/* 369 */     private static final ResourceLocation IGLOO_MIDDLE_ID = new ResourceLocation("igloo/igloo_middle");
/* 370 */     private static final ResourceLocation IGLOO_BOTTOM_ID = new ResourceLocation("igloo/igloo_bottom");
/*     */ 
/*     */ 
/*     */     
/*     */     public Igloo() {}
/*     */ 
/*     */     
/*     */     public Igloo(Random rand, int x, int z) {
/* 378 */       super(rand, x, 64, z, 7, 5, 8);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 383 */       if (!offsetToAverageGroundLevel(worldIn, structureBoundingBoxIn, -1))
/*     */       {
/* 385 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 389 */       StructureBoundingBox structureboundingbox = getBoundingBox();
/* 390 */       BlockPos blockpos = new BlockPos(structureboundingbox.minX, structureboundingbox.minY, structureboundingbox.minZ);
/* 391 */       Rotation[] arotation = Rotation.values();
/* 392 */       MinecraftServer minecraftserver = worldIn.getMinecraftServer();
/* 393 */       TemplateManager templatemanager = worldIn.getSaveHandler().getStructureTemplateManager();
/* 394 */       PlacementSettings placementsettings = (new PlacementSettings()).setRotation(arotation[randomIn.nextInt(arotation.length)]).setReplacedBlock(Blocks.STRUCTURE_VOID).setBoundingBox(structureboundingbox);
/* 395 */       Template template = templatemanager.getTemplate(minecraftserver, IGLOO_TOP_ID);
/* 396 */       template.addBlocksToWorldChunk(worldIn, blockpos, placementsettings);
/*     */       
/* 398 */       if (randomIn.nextDouble() < 0.5D) {
/*     */         
/* 400 */         Template template1 = templatemanager.getTemplate(minecraftserver, IGLOO_MIDDLE_ID);
/* 401 */         Template template2 = templatemanager.getTemplate(minecraftserver, IGLOO_BOTTOM_ID);
/* 402 */         int i = randomIn.nextInt(8) + 4;
/*     */         
/* 404 */         for (int j = 0; j < i; j++) {
/*     */           
/* 406 */           BlockPos blockpos1 = template.calculateConnectedPos(placementsettings, new BlockPos(3, -1 - j * 3, 5), placementsettings, new BlockPos(1, 2, 1));
/* 407 */           template1.addBlocksToWorldChunk(worldIn, blockpos.add((Vec3i)blockpos1), placementsettings);
/*     */         } 
/*     */         
/* 410 */         BlockPos blockpos4 = blockpos.add((Vec3i)template.calculateConnectedPos(placementsettings, new BlockPos(3, -1 - i * 3, 5), placementsettings, new BlockPos(3, 5, 7)));
/* 411 */         template2.addBlocksToWorldChunk(worldIn, blockpos4, placementsettings);
/* 412 */         Map<BlockPos, String> map = template2.getDataBlocks(blockpos4, placementsettings);
/*     */         
/* 414 */         for (Map.Entry<BlockPos, String> entry : map.entrySet()) {
/*     */           
/* 416 */           if ("chest".equals(entry.getValue()))
/*     */           {
/* 418 */             BlockPos blockpos2 = entry.getKey();
/* 419 */             worldIn.setBlockState(blockpos2, Blocks.AIR.getDefaultState(), 3);
/* 420 */             TileEntity tileentity = worldIn.getTileEntity(blockpos2.down());
/*     */             
/* 422 */             if (tileentity instanceof TileEntityChest)
/*     */             {
/* 424 */               ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_IGLOO_CHEST, randomIn.nextLong());
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 431 */         BlockPos blockpos3 = Template.transformedBlockPos(placementsettings, new BlockPos(3, 0, 5));
/* 432 */         worldIn.setBlockState(blockpos.add((Vec3i)blockpos3), Blocks.SNOW.getDefaultState(), 3);
/*     */       } 
/*     */       
/* 435 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class JunglePyramid
/*     */     extends Feature
/*     */   {
/*     */     private boolean placedMainChest;
/*     */     private boolean placedHiddenChest;
/*     */     private boolean placedTrap1;
/*     */     private boolean placedTrap2;
/* 446 */     private static final Stones junglePyramidsRandomScatteredStones = new Stones(null);
/*     */ 
/*     */ 
/*     */     
/*     */     public JunglePyramid() {}
/*     */ 
/*     */     
/*     */     public JunglePyramid(Random rand, int x, int z) {
/* 454 */       super(rand, x, 64, z, 12, 10, 15);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 459 */       super.writeStructureToNBT(tagCompound);
/* 460 */       tagCompound.setBoolean("placedMainChest", this.placedMainChest);
/* 461 */       tagCompound.setBoolean("placedHiddenChest", this.placedHiddenChest);
/* 462 */       tagCompound.setBoolean("placedTrap1", this.placedTrap1);
/* 463 */       tagCompound.setBoolean("placedTrap2", this.placedTrap2);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 468 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 469 */       this.placedMainChest = tagCompound.getBoolean("placedMainChest");
/* 470 */       this.placedHiddenChest = tagCompound.getBoolean("placedHiddenChest");
/* 471 */       this.placedTrap1 = tagCompound.getBoolean("placedTrap1");
/* 472 */       this.placedTrap2 = tagCompound.getBoolean("placedTrap2");
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 477 */       if (!offsetToAverageGroundLevel(worldIn, structureBoundingBoxIn, 0))
/*     */       {
/* 479 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 483 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, -4, 0, this.scatteredFeatureSizeX - 1, 0, this.scatteredFeatureSizeZ - 1, false, randomIn, junglePyramidsRandomScatteredStones);
/* 484 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 1, 2, 9, 2, 2, false, randomIn, junglePyramidsRandomScatteredStones);
/* 485 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 1, 12, 9, 2, 12, false, randomIn, junglePyramidsRandomScatteredStones);
/* 486 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 1, 3, 2, 2, 11, false, randomIn, junglePyramidsRandomScatteredStones);
/* 487 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, 1, 3, 9, 2, 11, false, randomIn, junglePyramidsRandomScatteredStones);
/* 488 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 1, 10, 6, 1, false, randomIn, junglePyramidsRandomScatteredStones);
/* 489 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 13, 10, 6, 13, false, randomIn, junglePyramidsRandomScatteredStones);
/* 490 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 2, 1, 6, 12, false, randomIn, junglePyramidsRandomScatteredStones);
/* 491 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 10, 3, 2, 10, 6, 12, false, randomIn, junglePyramidsRandomScatteredStones);
/* 492 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 3, 2, 9, 3, 12, false, randomIn, junglePyramidsRandomScatteredStones);
/* 493 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 6, 2, 9, 6, 12, false, randomIn, junglePyramidsRandomScatteredStones);
/* 494 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 3, 7, 3, 8, 7, 11, false, randomIn, junglePyramidsRandomScatteredStones);
/* 495 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 8, 4, 7, 8, 10, false, randomIn, junglePyramidsRandomScatteredStones);
/* 496 */       fillWithAir(worldIn, structureBoundingBoxIn, 3, 1, 3, 8, 2, 11);
/* 497 */       fillWithAir(worldIn, structureBoundingBoxIn, 4, 3, 6, 7, 3, 9);
/* 498 */       fillWithAir(worldIn, structureBoundingBoxIn, 2, 4, 2, 9, 5, 12);
/* 499 */       fillWithAir(worldIn, structureBoundingBoxIn, 4, 6, 5, 7, 6, 9);
/* 500 */       fillWithAir(worldIn, structureBoundingBoxIn, 5, 7, 6, 6, 7, 8);
/* 501 */       fillWithAir(worldIn, structureBoundingBoxIn, 5, 1, 2, 6, 2, 2);
/* 502 */       fillWithAir(worldIn, structureBoundingBoxIn, 5, 2, 12, 6, 2, 12);
/* 503 */       fillWithAir(worldIn, structureBoundingBoxIn, 5, 5, 1, 6, 5, 1);
/* 504 */       fillWithAir(worldIn, structureBoundingBoxIn, 5, 5, 13, 6, 5, 13);
/* 505 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 5, 5, structureBoundingBoxIn);
/* 506 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 10, 5, 5, structureBoundingBoxIn);
/* 507 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 5, 9, structureBoundingBoxIn);
/* 508 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 10, 5, 9, structureBoundingBoxIn);
/*     */       
/* 510 */       for (int i = 0; i <= 14; i += 14) {
/*     */         
/* 512 */         fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 4, i, 2, 5, i, false, randomIn, junglePyramidsRandomScatteredStones);
/* 513 */         fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 4, i, 4, 5, i, false, randomIn, junglePyramidsRandomScatteredStones);
/* 514 */         fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 7, 4, i, 7, 5, i, false, randomIn, junglePyramidsRandomScatteredStones);
/* 515 */         fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, 4, i, 9, 5, i, false, randomIn, junglePyramidsRandomScatteredStones);
/*     */       } 
/*     */       
/* 518 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 6, 0, 6, 6, 0, false, randomIn, junglePyramidsRandomScatteredStones);
/*     */       
/* 520 */       for (int l = 0; l <= 11; l += 11) {
/*     */         
/* 522 */         for (int j = 2; j <= 12; j += 2)
/*     */         {
/* 524 */           fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, l, 4, j, l, 5, j, false, randomIn, junglePyramidsRandomScatteredStones);
/*     */         }
/*     */         
/* 527 */         fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, l, 6, 5, l, 6, 5, false, randomIn, junglePyramidsRandomScatteredStones);
/* 528 */         fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, l, 6, 9, l, 6, 9, false, randomIn, junglePyramidsRandomScatteredStones);
/*     */       } 
/*     */       
/* 531 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 7, 2, 2, 9, 2, false, randomIn, junglePyramidsRandomScatteredStones);
/* 532 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, 7, 2, 9, 9, 2, false, randomIn, junglePyramidsRandomScatteredStones);
/* 533 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, 7, 12, 2, 9, 12, false, randomIn, junglePyramidsRandomScatteredStones);
/* 534 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, 7, 12, 9, 9, 12, false, randomIn, junglePyramidsRandomScatteredStones);
/* 535 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 9, 4, 4, 9, 4, false, randomIn, junglePyramidsRandomScatteredStones);
/* 536 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 7, 9, 4, 7, 9, 4, false, randomIn, junglePyramidsRandomScatteredStones);
/* 537 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 9, 10, 4, 9, 10, false, randomIn, junglePyramidsRandomScatteredStones);
/* 538 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 7, 9, 10, 7, 9, 10, false, randomIn, junglePyramidsRandomScatteredStones);
/* 539 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 9, 7, 6, 9, 7, false, randomIn, junglePyramidsRandomScatteredStones);
/* 540 */       IBlockState iblockstate2 = Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.EAST);
/* 541 */       IBlockState iblockstate3 = Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.WEST);
/* 542 */       IBlockState iblockstate = Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.SOUTH);
/* 543 */       IBlockState iblockstate1 = Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH);
/* 544 */       setBlockState(worldIn, iblockstate1, 5, 9, 6, structureBoundingBoxIn);
/* 545 */       setBlockState(worldIn, iblockstate1, 6, 9, 6, structureBoundingBoxIn);
/* 546 */       setBlockState(worldIn, iblockstate, 5, 9, 8, structureBoundingBoxIn);
/* 547 */       setBlockState(worldIn, iblockstate, 6, 9, 8, structureBoundingBoxIn);
/* 548 */       setBlockState(worldIn, iblockstate1, 4, 0, 0, structureBoundingBoxIn);
/* 549 */       setBlockState(worldIn, iblockstate1, 5, 0, 0, structureBoundingBoxIn);
/* 550 */       setBlockState(worldIn, iblockstate1, 6, 0, 0, structureBoundingBoxIn);
/* 551 */       setBlockState(worldIn, iblockstate1, 7, 0, 0, structureBoundingBoxIn);
/* 552 */       setBlockState(worldIn, iblockstate1, 4, 1, 8, structureBoundingBoxIn);
/* 553 */       setBlockState(worldIn, iblockstate1, 4, 2, 9, structureBoundingBoxIn);
/* 554 */       setBlockState(worldIn, iblockstate1, 4, 3, 10, structureBoundingBoxIn);
/* 555 */       setBlockState(worldIn, iblockstate1, 7, 1, 8, structureBoundingBoxIn);
/* 556 */       setBlockState(worldIn, iblockstate1, 7, 2, 9, structureBoundingBoxIn);
/* 557 */       setBlockState(worldIn, iblockstate1, 7, 3, 10, structureBoundingBoxIn);
/* 558 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 4, 1, 9, false, randomIn, junglePyramidsRandomScatteredStones);
/* 559 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 7, 1, 9, 7, 1, 9, false, randomIn, junglePyramidsRandomScatteredStones);
/* 560 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 10, 7, 2, 10, false, randomIn, junglePyramidsRandomScatteredStones);
/* 561 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 4, 5, 6, 4, 5, false, randomIn, junglePyramidsRandomScatteredStones);
/* 562 */       setBlockState(worldIn, iblockstate2, 4, 4, 5, structureBoundingBoxIn);
/* 563 */       setBlockState(worldIn, iblockstate3, 7, 4, 5, structureBoundingBoxIn);
/*     */       
/* 565 */       for (int k = 0; k < 4; k++) {
/*     */         
/* 567 */         setBlockState(worldIn, iblockstate, 5, 0 - k, 6 + k, structureBoundingBoxIn);
/* 568 */         setBlockState(worldIn, iblockstate, 6, 0 - k, 6 + k, structureBoundingBoxIn);
/* 569 */         fillWithAir(worldIn, structureBoundingBoxIn, 5, 0 - k, 7 + k, 6, 0 - k, 9 + k);
/*     */       } 
/*     */       
/* 572 */       fillWithAir(worldIn, structureBoundingBoxIn, 1, -3, 12, 10, -1, 13);
/* 573 */       fillWithAir(worldIn, structureBoundingBoxIn, 1, -3, 1, 3, -1, 13);
/* 574 */       fillWithAir(worldIn, structureBoundingBoxIn, 1, -3, 1, 9, -1, 5);
/*     */       
/* 576 */       for (int i1 = 1; i1 <= 13; i1 += 2)
/*     */       {
/* 578 */         fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, -3, i1, 1, -2, i1, false, randomIn, junglePyramidsRandomScatteredStones);
/*     */       }
/*     */       
/* 581 */       for (int j1 = 2; j1 <= 12; j1 += 2)
/*     */       {
/* 583 */         fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, -1, j1, 3, -1, j1, false, randomIn, junglePyramidsRandomScatteredStones);
/*     */       }
/*     */       
/* 586 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, -2, 1, 5, -2, 1, false, randomIn, junglePyramidsRandomScatteredStones);
/* 587 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 7, -2, 1, 9, -2, 1, false, randomIn, junglePyramidsRandomScatteredStones);
/* 588 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 6, -3, 1, 6, -3, 1, false, randomIn, junglePyramidsRandomScatteredStones);
/* 589 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 6, -1, 1, 6, -1, 1, false, randomIn, junglePyramidsRandomScatteredStones);
/* 590 */       setBlockState(worldIn, Blocks.TRIPWIRE_HOOK.getDefaultState().withProperty((IProperty)BlockTripWireHook.FACING, (Comparable)EnumFacing.EAST).withProperty((IProperty)BlockTripWireHook.ATTACHED, Boolean.valueOf(true)), 1, -3, 8, structureBoundingBoxIn);
/* 591 */       setBlockState(worldIn, Blocks.TRIPWIRE_HOOK.getDefaultState().withProperty((IProperty)BlockTripWireHook.FACING, (Comparable)EnumFacing.WEST).withProperty((IProperty)BlockTripWireHook.ATTACHED, Boolean.valueOf(true)), 4, -3, 8, structureBoundingBoxIn);
/* 592 */       setBlockState(worldIn, Blocks.TRIPWIRE.getDefaultState().withProperty((IProperty)BlockTripWire.ATTACHED, Boolean.valueOf(true)), 2, -3, 8, structureBoundingBoxIn);
/* 593 */       setBlockState(worldIn, Blocks.TRIPWIRE.getDefaultState().withProperty((IProperty)BlockTripWire.ATTACHED, Boolean.valueOf(true)), 3, -3, 8, structureBoundingBoxIn);
/* 594 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 5, -3, 7, structureBoundingBoxIn);
/* 595 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 5, -3, 6, structureBoundingBoxIn);
/* 596 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 5, -3, 5, structureBoundingBoxIn);
/* 597 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 5, -3, 4, structureBoundingBoxIn);
/* 598 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 5, -3, 3, structureBoundingBoxIn);
/* 599 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 5, -3, 2, structureBoundingBoxIn);
/* 600 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 5, -3, 1, structureBoundingBoxIn);
/* 601 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 4, -3, 1, structureBoundingBoxIn);
/* 602 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 3, -3, 1, structureBoundingBoxIn);
/*     */       
/* 604 */       if (!this.placedTrap1)
/*     */       {
/* 606 */         this.placedTrap1 = createDispenser(worldIn, structureBoundingBoxIn, randomIn, 3, -2, 1, EnumFacing.NORTH, LootTableList.CHESTS_JUNGLE_TEMPLE_DISPENSER);
/*     */       }
/*     */       
/* 609 */       setBlockState(worldIn, Blocks.VINE.getDefaultState().withProperty((IProperty)BlockVine.SOUTH, Boolean.valueOf(true)), 3, -2, 2, structureBoundingBoxIn);
/* 610 */       setBlockState(worldIn, Blocks.TRIPWIRE_HOOK.getDefaultState().withProperty((IProperty)BlockTripWireHook.FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)BlockTripWireHook.ATTACHED, Boolean.valueOf(true)), 7, -3, 1, structureBoundingBoxIn);
/* 611 */       setBlockState(worldIn, Blocks.TRIPWIRE_HOOK.getDefaultState().withProperty((IProperty)BlockTripWireHook.FACING, (Comparable)EnumFacing.SOUTH).withProperty((IProperty)BlockTripWireHook.ATTACHED, Boolean.valueOf(true)), 7, -3, 5, structureBoundingBoxIn);
/* 612 */       setBlockState(worldIn, Blocks.TRIPWIRE.getDefaultState().withProperty((IProperty)BlockTripWire.ATTACHED, Boolean.valueOf(true)), 7, -3, 2, structureBoundingBoxIn);
/* 613 */       setBlockState(worldIn, Blocks.TRIPWIRE.getDefaultState().withProperty((IProperty)BlockTripWire.ATTACHED, Boolean.valueOf(true)), 7, -3, 3, structureBoundingBoxIn);
/* 614 */       setBlockState(worldIn, Blocks.TRIPWIRE.getDefaultState().withProperty((IProperty)BlockTripWire.ATTACHED, Boolean.valueOf(true)), 7, -3, 4, structureBoundingBoxIn);
/* 615 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 8, -3, 6, structureBoundingBoxIn);
/* 616 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 9, -3, 6, structureBoundingBoxIn);
/* 617 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 9, -3, 5, structureBoundingBoxIn);
/* 618 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 9, -3, 4, structureBoundingBoxIn);
/* 619 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 9, -2, 4, structureBoundingBoxIn);
/*     */       
/* 621 */       if (!this.placedTrap2)
/*     */       {
/* 623 */         this.placedTrap2 = createDispenser(worldIn, structureBoundingBoxIn, randomIn, 9, -2, 3, EnumFacing.WEST, LootTableList.CHESTS_JUNGLE_TEMPLE_DISPENSER);
/*     */       }
/*     */       
/* 626 */       setBlockState(worldIn, Blocks.VINE.getDefaultState().withProperty((IProperty)BlockVine.EAST, Boolean.valueOf(true)), 8, -1, 3, structureBoundingBoxIn);
/* 627 */       setBlockState(worldIn, Blocks.VINE.getDefaultState().withProperty((IProperty)BlockVine.EAST, Boolean.valueOf(true)), 8, -2, 3, structureBoundingBoxIn);
/*     */       
/* 629 */       if (!this.placedMainChest)
/*     */       {
/* 631 */         this.placedMainChest = generateChest(worldIn, structureBoundingBoxIn, randomIn, 8, -3, 3, LootTableList.CHESTS_JUNGLE_TEMPLE);
/*     */       }
/*     */       
/* 634 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 9, -3, 2, structureBoundingBoxIn);
/* 635 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 8, -3, 1, structureBoundingBoxIn);
/* 636 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 4, -3, 5, structureBoundingBoxIn);
/* 637 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5, -2, 5, structureBoundingBoxIn);
/* 638 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5, -1, 5, structureBoundingBoxIn);
/* 639 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 6, -3, 5, structureBoundingBoxIn);
/* 640 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 7, -2, 5, structureBoundingBoxIn);
/* 641 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 7, -1, 5, structureBoundingBoxIn);
/* 642 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 8, -3, 5, structureBoundingBoxIn);
/* 643 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, -1, 1, 9, -1, 5, false, randomIn, junglePyramidsRandomScatteredStones);
/* 644 */       fillWithAir(worldIn, structureBoundingBoxIn, 8, -3, 8, 10, -1, 10);
/* 645 */       setBlockState(worldIn, Blocks.STONEBRICK.getStateFromMeta(BlockStoneBrick.CHISELED_META), 8, -2, 11, structureBoundingBoxIn);
/* 646 */       setBlockState(worldIn, Blocks.STONEBRICK.getStateFromMeta(BlockStoneBrick.CHISELED_META), 9, -2, 11, structureBoundingBoxIn);
/* 647 */       setBlockState(worldIn, Blocks.STONEBRICK.getStateFromMeta(BlockStoneBrick.CHISELED_META), 10, -2, 11, structureBoundingBoxIn);
/* 648 */       IBlockState iblockstate4 = Blocks.LEVER.getDefaultState().withProperty((IProperty)BlockLever.FACING, (Comparable)BlockLever.EnumOrientation.NORTH);
/* 649 */       setBlockState(worldIn, iblockstate4, 8, -2, 12, structureBoundingBoxIn);
/* 650 */       setBlockState(worldIn, iblockstate4, 9, -2, 12, structureBoundingBoxIn);
/* 651 */       setBlockState(worldIn, iblockstate4, 10, -2, 12, structureBoundingBoxIn);
/* 652 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, -3, 8, 8, -3, 10, false, randomIn, junglePyramidsRandomScatteredStones);
/* 653 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 10, -3, 8, 10, -3, 10, false, randomIn, junglePyramidsRandomScatteredStones);
/* 654 */       setBlockState(worldIn, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 10, -2, 9, structureBoundingBoxIn);
/* 655 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 8, -2, 9, structureBoundingBoxIn);
/* 656 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 8, -2, 10, structureBoundingBoxIn);
/* 657 */       setBlockState(worldIn, Blocks.REDSTONE_WIRE.getDefaultState(), 10, -1, 9, structureBoundingBoxIn);
/* 658 */       setBlockState(worldIn, Blocks.STICKY_PISTON.getDefaultState().withProperty((IProperty)BlockPistonBase.FACING, (Comparable)EnumFacing.UP), 9, -2, 8, structureBoundingBoxIn);
/* 659 */       setBlockState(worldIn, Blocks.STICKY_PISTON.getDefaultState().withProperty((IProperty)BlockPistonBase.FACING, (Comparable)EnumFacing.WEST), 10, -2, 8, structureBoundingBoxIn);
/* 660 */       setBlockState(worldIn, Blocks.STICKY_PISTON.getDefaultState().withProperty((IProperty)BlockPistonBase.FACING, (Comparable)EnumFacing.WEST), 10, -1, 8, structureBoundingBoxIn);
/* 661 */       setBlockState(worldIn, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty((IProperty)BlockRedstoneRepeater.FACING, (Comparable)EnumFacing.NORTH), 10, -2, 10, structureBoundingBoxIn);
/*     */       
/* 663 */       if (!this.placedHiddenChest)
/*     */       {
/* 665 */         this.placedHiddenChest = generateChest(worldIn, structureBoundingBoxIn, randomIn, 9, -3, 10, LootTableList.CHESTS_JUNGLE_TEMPLE);
/*     */       }
/*     */       
/* 668 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     static class Stones
/*     */       extends StructureComponent.BlockSelector
/*     */     {
/*     */       private Stones() {}
/*     */ 
/*     */       
/*     */       public void selectBlocks(Random rand, int x, int y, int z, boolean p_75062_5_) {
/* 680 */         if (rand.nextFloat() < 0.4F) {
/*     */           
/* 682 */           this.blockstate = Blocks.COBBLESTONE.getDefaultState();
/*     */         }
/*     */         else {
/*     */           
/* 686 */           this.blockstate = Blocks.MOSSY_COBBLESTONE.getDefaultState();
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SwampHut
/*     */     extends Feature
/*     */   {
/*     */     private boolean hasWitch;
/*     */ 
/*     */     
/*     */     public SwampHut() {}
/*     */     
/*     */     public SwampHut(Random p_i2066_1_, int p_i2066_2_, int p_i2066_3_) {
/* 702 */       super(p_i2066_1_, p_i2066_2_, 64, p_i2066_3_, 7, 7, 9);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 707 */       super.writeStructureToNBT(tagCompound);
/* 708 */       tagCompound.setBoolean("Witch", this.hasWitch);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 713 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 714 */       this.hasWitch = tagCompound.getBoolean("Witch");
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 719 */       if (!offsetToAverageGroundLevel(worldIn, structureBoundingBoxIn, 0))
/*     */       {
/* 721 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 725 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 5, 1, 7, Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
/* 726 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 2, 5, 4, 7, Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
/* 727 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 4, 1, 0, Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
/* 728 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 2, 3, 3, 2, Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
/* 729 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 3, 1, 3, 6, Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
/* 730 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 3, 5, 3, 6, Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
/* 731 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 7, 4, 3, 7, Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), Blocks.PLANKS.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()), false);
/* 732 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 2, 1, 3, 2, Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState(), false);
/* 733 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 2, 5, 3, 2, Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState(), false);
/* 734 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 7, 1, 3, 7, Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState(), false);
/* 735 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 7, 5, 3, 7, Blocks.LOG.getDefaultState(), Blocks.LOG.getDefaultState(), false);
/* 736 */       setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 2, 3, 2, structureBoundingBoxIn);
/* 737 */       setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 3, 3, 7, structureBoundingBoxIn);
/* 738 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 3, 4, structureBoundingBoxIn);
/* 739 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
/* 740 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
/* 741 */       setBlockState(worldIn, Blocks.FLOWER_POT.getDefaultState().withProperty((IProperty)BlockFlowerPot.CONTENTS, (Comparable)BlockFlowerPot.EnumFlowerType.MUSHROOM_RED), 1, 3, 5, structureBoundingBoxIn);
/* 742 */       setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 3, 2, 6, structureBoundingBoxIn);
/* 743 */       setBlockState(worldIn, Blocks.CAULDRON.getDefaultState(), 4, 2, 6, structureBoundingBoxIn);
/* 744 */       setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 1, 2, 1, structureBoundingBoxIn);
/* 745 */       setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 5, 2, 1, structureBoundingBoxIn);
/* 746 */       IBlockState iblockstate = Blocks.SPRUCE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH);
/* 747 */       IBlockState iblockstate1 = Blocks.SPRUCE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.EAST);
/* 748 */       IBlockState iblockstate2 = Blocks.SPRUCE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.WEST);
/* 749 */       IBlockState iblockstate3 = Blocks.SPRUCE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.SOUTH);
/* 750 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 6, 4, 1, iblockstate, iblockstate, false);
/* 751 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 2, 0, 4, 7, iblockstate1, iblockstate1, false);
/* 752 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 4, 2, 6, 4, 7, iblockstate2, iblockstate2, false);
/* 753 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 8, 6, 4, 8, iblockstate3, iblockstate3, false);
/*     */       
/* 755 */       for (int i = 2; i <= 7; i += 5) {
/*     */         
/* 757 */         for (int j = 1; j <= 5; j += 4)
/*     */         {
/* 759 */           replaceAirAndLiquidDownwards(worldIn, Blocks.LOG.getDefaultState(), j, -1, i, structureBoundingBoxIn);
/*     */         }
/*     */       } 
/*     */       
/* 763 */       if (!this.hasWitch) {
/*     */         
/* 765 */         int l = getXWithOffset(2, 5);
/* 766 */         int i1 = getYWithOffset(2);
/* 767 */         int k = getZWithOffset(2, 5);
/*     */         
/* 769 */         if (structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(l, i1, k))) {
/*     */           
/* 771 */           this.hasWitch = true;
/* 772 */           EntityWitch entitywitch = new EntityWitch(worldIn);
/* 773 */           entitywitch.enablePersistence();
/* 774 */           entitywitch.setLocationAndAngles(l + 0.5D, i1, k + 0.5D, 0.0F, 0.0F);
/* 775 */           entitywitch.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(l, i1, k)), null);
/* 776 */           worldIn.spawnEntityInWorld((Entity)entitywitch);
/*     */         } 
/*     */       } 
/*     */       
/* 780 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\ComponentScatteredFeaturePieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */