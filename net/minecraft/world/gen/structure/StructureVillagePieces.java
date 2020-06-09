/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockCrops;
/*      */ import net.minecraft.block.BlockDoor;
/*      */ import net.minecraft.block.BlockLadder;
/*      */ import net.minecraft.block.BlockLog;
/*      */ import net.minecraft.block.BlockNewLog;
/*      */ import net.minecraft.block.BlockOldLog;
/*      */ import net.minecraft.block.BlockPlanks;
/*      */ import net.minecraft.block.BlockSandStone;
/*      */ import net.minecraft.block.BlockStairs;
/*      */ import net.minecraft.block.BlockTorch;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.monster.EntityZombieVillager;
/*      */ import net.minecraft.entity.passive.EntityVillager;
/*      */ import net.minecraft.init.Biomes;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3i;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ import net.minecraft.world.biome.BiomeProvider;
/*      */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*      */ import net.minecraft.world.storage.loot.LootTableList;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StructureVillagePieces
/*      */ {
/*      */   public static void registerVillagePieces() {
/*   44 */     MapGenStructureIO.registerStructureComponent((Class)House1.class, "ViBH");
/*   45 */     MapGenStructureIO.registerStructureComponent((Class)Field1.class, "ViDF");
/*   46 */     MapGenStructureIO.registerStructureComponent((Class)Field2.class, "ViF");
/*   47 */     MapGenStructureIO.registerStructureComponent((Class)Torch.class, "ViL");
/*   48 */     MapGenStructureIO.registerStructureComponent((Class)Hall.class, "ViPH");
/*   49 */     MapGenStructureIO.registerStructureComponent((Class)House4Garden.class, "ViSH");
/*   50 */     MapGenStructureIO.registerStructureComponent((Class)WoodHut.class, "ViSmH");
/*   51 */     MapGenStructureIO.registerStructureComponent((Class)Church.class, "ViST");
/*   52 */     MapGenStructureIO.registerStructureComponent((Class)House2.class, "ViS");
/*   53 */     MapGenStructureIO.registerStructureComponent((Class)Start.class, "ViStart");
/*   54 */     MapGenStructureIO.registerStructureComponent((Class)Path.class, "ViSR");
/*   55 */     MapGenStructureIO.registerStructureComponent((Class)House3.class, "ViTRH");
/*   56 */     MapGenStructureIO.registerStructureComponent((Class)Well.class, "ViW");
/*      */   }
/*      */ 
/*      */   
/*      */   public static List<PieceWeight> getStructureVillageWeightedPieceList(Random random, int size) {
/*   61 */     List<PieceWeight> list = Lists.newArrayList();
/*   62 */     list.add(new PieceWeight((Class)House4Garden.class, 4, MathHelper.getInt(random, 2 + size, 4 + size * 2)));
/*   63 */     list.add(new PieceWeight((Class)Church.class, 20, MathHelper.getInt(random, 0 + size, 1 + size)));
/*   64 */     list.add(new PieceWeight((Class)House1.class, 20, MathHelper.getInt(random, 0 + size, 2 + size)));
/*   65 */     list.add(new PieceWeight((Class)WoodHut.class, 3, MathHelper.getInt(random, 2 + size, 5 + size * 3)));
/*   66 */     list.add(new PieceWeight((Class)Hall.class, 15, MathHelper.getInt(random, 0 + size, 2 + size)));
/*   67 */     list.add(new PieceWeight((Class)Field1.class, 3, MathHelper.getInt(random, 1 + size, 4 + size)));
/*   68 */     list.add(new PieceWeight((Class)Field2.class, 3, MathHelper.getInt(random, 2 + size, 4 + size * 2)));
/*   69 */     list.add(new PieceWeight((Class)House2.class, 15, MathHelper.getInt(random, 0, 1 + size)));
/*   70 */     list.add(new PieceWeight((Class)House3.class, 8, MathHelper.getInt(random, 0 + size, 3 + size * 2)));
/*   71 */     Iterator<PieceWeight> iterator = list.iterator();
/*      */     
/*   73 */     while (iterator.hasNext()) {
/*      */       
/*   75 */       if (((PieceWeight)iterator.next()).villagePiecesLimit == 0)
/*      */       {
/*   77 */         iterator.remove();
/*      */       }
/*      */     } 
/*      */     
/*   81 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int updatePieceWeight(List<PieceWeight> p_75079_0_) {
/*   86 */     boolean flag = false;
/*   87 */     int i = 0;
/*      */     
/*   89 */     for (PieceWeight structurevillagepieces$pieceweight : p_75079_0_) {
/*      */       
/*   91 */       if (structurevillagepieces$pieceweight.villagePiecesLimit > 0 && structurevillagepieces$pieceweight.villagePiecesSpawned < structurevillagepieces$pieceweight.villagePiecesLimit)
/*      */       {
/*   93 */         flag = true;
/*      */       }
/*      */       
/*   96 */       i += structurevillagepieces$pieceweight.villagePieceWeight;
/*      */     } 
/*      */     
/*   99 */     return flag ? i : -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Village findAndCreateComponentFactory(Start start, PieceWeight weight, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType) {
/*  104 */     Class<? extends Village> oclass = weight.villagePieceClass;
/*  105 */     Village structurevillagepieces$village = null;
/*      */     
/*  107 */     if (oclass == House4Garden.class) {
/*      */       
/*  109 */       structurevillagepieces$village = House4Garden.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
/*      */     }
/*  111 */     else if (oclass == Church.class) {
/*      */       
/*  113 */       structurevillagepieces$village = Church.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
/*      */     }
/*  115 */     else if (oclass == House1.class) {
/*      */       
/*  117 */       structurevillagepieces$village = House1.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
/*      */     }
/*  119 */     else if (oclass == WoodHut.class) {
/*      */       
/*  121 */       structurevillagepieces$village = WoodHut.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
/*      */     }
/*  123 */     else if (oclass == Hall.class) {
/*      */       
/*  125 */       structurevillagepieces$village = Hall.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
/*      */     }
/*  127 */     else if (oclass == Field1.class) {
/*      */       
/*  129 */       structurevillagepieces$village = Field1.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
/*      */     }
/*  131 */     else if (oclass == Field2.class) {
/*      */       
/*  133 */       structurevillagepieces$village = Field2.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
/*      */     }
/*  135 */     else if (oclass == House2.class) {
/*      */       
/*  137 */       structurevillagepieces$village = House2.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
/*      */     }
/*  139 */     else if (oclass == House3.class) {
/*      */       
/*  141 */       structurevillagepieces$village = House3.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
/*      */     } 
/*      */     
/*  144 */     return structurevillagepieces$village;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Village generateComponent(Start start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType) {
/*  149 */     int i = updatePieceWeight(start.structureVillageWeightedPieceList);
/*      */     
/*  151 */     if (i <= 0)
/*      */     {
/*  153 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  157 */     int j = 0;
/*      */     
/*  159 */     while (j < 5) {
/*      */       
/*  161 */       j++;
/*  162 */       int k = rand.nextInt(i);
/*      */       
/*  164 */       for (PieceWeight structurevillagepieces$pieceweight : start.structureVillageWeightedPieceList) {
/*      */         
/*  166 */         k -= structurevillagepieces$pieceweight.villagePieceWeight;
/*      */         
/*  168 */         if (k < 0) {
/*      */           
/*  170 */           if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePiecesOfType(componentType) || (structurevillagepieces$pieceweight == start.structVillagePieceWeight && start.structureVillageWeightedPieceList.size() > 1)) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/*  175 */           Village structurevillagepieces$village = findAndCreateComponentFactory(start, structurevillagepieces$pieceweight, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
/*      */           
/*  177 */           if (structurevillagepieces$village != null) {
/*      */             
/*  179 */             structurevillagepieces$pieceweight.villagePiecesSpawned++;
/*  180 */             start.structVillagePieceWeight = structurevillagepieces$pieceweight;
/*      */             
/*  182 */             if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePieces())
/*      */             {
/*  184 */               start.structureVillageWeightedPieceList.remove(structurevillagepieces$pieceweight);
/*      */             }
/*      */             
/*  187 */             return structurevillagepieces$village;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  193 */     StructureBoundingBox structureboundingbox = Torch.findPieceBox(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing);
/*      */     
/*  195 */     if (structureboundingbox != null)
/*      */     {
/*  197 */       return new Torch(start, componentType, rand, structureboundingbox, facing);
/*      */     }
/*      */ 
/*      */     
/*  201 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static StructureComponent generateAndAddComponent(Start start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType) {
/*  208 */     if (componentType > 50)
/*      */     {
/*  210 */       return null;
/*      */     }
/*  212 */     if (Math.abs(structureMinX - (start.getBoundingBox()).minX) <= 112 && Math.abs(structureMinZ - (start.getBoundingBox()).minZ) <= 112) {
/*      */       
/*  214 */       StructureComponent structurecomponent = generateComponent(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType + 1);
/*      */       
/*  216 */       if (structurecomponent != null) {
/*      */         
/*  218 */         structureComponents.add(structurecomponent);
/*  219 */         start.pendingHouses.add(structurecomponent);
/*  220 */         return structurecomponent;
/*      */       } 
/*      */ 
/*      */       
/*  224 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  229 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static StructureComponent generateAndAddRoadPiece(Start start, List<StructureComponent> p_176069_1_, Random rand, int p_176069_3_, int p_176069_4_, int p_176069_5_, EnumFacing facing, int p_176069_7_) {
/*  235 */     if (p_176069_7_ > 3 + start.terrainType)
/*      */     {
/*  237 */       return null;
/*      */     }
/*  239 */     if (Math.abs(p_176069_3_ - (start.getBoundingBox()).minX) <= 112 && Math.abs(p_176069_5_ - (start.getBoundingBox()).minZ) <= 112) {
/*      */       
/*  241 */       StructureBoundingBox structureboundingbox = Path.findPieceBox(start, p_176069_1_, rand, p_176069_3_, p_176069_4_, p_176069_5_, facing);
/*      */       
/*  243 */       if (structureboundingbox != null && structureboundingbox.minY > 10) {
/*      */         
/*  245 */         StructureComponent structurecomponent = new Path(start, p_176069_7_, rand, structureboundingbox, facing);
/*  246 */         p_176069_1_.add(structurecomponent);
/*  247 */         start.pendingRoads.add(structurecomponent);
/*  248 */         return structurecomponent;
/*      */       } 
/*      */ 
/*      */       
/*  252 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  257 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Church
/*      */     extends Village
/*      */   {
/*      */     public Church() {}
/*      */ 
/*      */     
/*      */     public Church(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox p_i45564_4_, EnumFacing facing) {
/*  269 */       super(start, type);
/*  270 */       setCoordBaseMode(facing);
/*  271 */       this.boundingBox = p_i45564_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static Church createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175854_1_, Random rand, int p_175854_3_, int p_175854_4_, int p_175854_5_, EnumFacing facing, int p_175854_7_) {
/*  276 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175854_3_, p_175854_4_, p_175854_5_, 0, 0, 0, 5, 12, 9, facing);
/*  277 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175854_1_, structureboundingbox) == null) ? new Church(start, p_175854_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  282 */       if (this.averageGroundLvl < 0) {
/*      */         
/*  284 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  286 */         if (this.averageGroundLvl < 0)
/*      */         {
/*  288 */           return true;
/*      */         }
/*      */         
/*  291 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 12 - 1, 0);
/*      */       } 
/*      */       
/*  294 */       IBlockState iblockstate = Blocks.COBBLESTONE.getDefaultState();
/*  295 */       IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH));
/*  296 */       IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.WEST));
/*  297 */       IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.EAST));
/*  298 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  299 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  300 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 3, 0, 8, iblockstate, iblockstate, false);
/*  301 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 3, 10, 0, iblockstate, iblockstate, false);
/*  302 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 10, 3, iblockstate, iblockstate, false);
/*  303 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 10, 3, iblockstate, iblockstate, false);
/*  304 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 0, 4, 7, iblockstate, iblockstate, false);
/*  305 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 4, 4, 4, 7, iblockstate, iblockstate, false);
/*  306 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 8, 3, 4, 8, iblockstate, iblockstate, false);
/*  307 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 4, 3, 10, 4, iblockstate, iblockstate, false);
/*  308 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 5, 3, 5, 7, iblockstate, iblockstate, false);
/*  309 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, iblockstate, iblockstate, false);
/*  310 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, iblockstate, iblockstate, false);
/*  311 */       setBlockState(worldIn, iblockstate, 0, 11, 2, structureBoundingBoxIn);
/*  312 */       setBlockState(worldIn, iblockstate, 4, 11, 2, structureBoundingBoxIn);
/*  313 */       setBlockState(worldIn, iblockstate, 2, 11, 0, structureBoundingBoxIn);
/*  314 */       setBlockState(worldIn, iblockstate, 2, 11, 4, structureBoundingBoxIn);
/*  315 */       setBlockState(worldIn, iblockstate, 1, 1, 6, structureBoundingBoxIn);
/*  316 */       setBlockState(worldIn, iblockstate, 1, 1, 7, structureBoundingBoxIn);
/*  317 */       setBlockState(worldIn, iblockstate, 2, 1, 7, structureBoundingBoxIn);
/*  318 */       setBlockState(worldIn, iblockstate, 3, 1, 6, structureBoundingBoxIn);
/*  319 */       setBlockState(worldIn, iblockstate, 3, 1, 7, structureBoundingBoxIn);
/*  320 */       setBlockState(worldIn, iblockstate1, 1, 1, 5, structureBoundingBoxIn);
/*  321 */       setBlockState(worldIn, iblockstate1, 2, 1, 6, structureBoundingBoxIn);
/*  322 */       setBlockState(worldIn, iblockstate1, 3, 1, 5, structureBoundingBoxIn);
/*  323 */       setBlockState(worldIn, iblockstate2, 1, 2, 7, structureBoundingBoxIn);
/*  324 */       setBlockState(worldIn, iblockstate3, 3, 2, 7, structureBoundingBoxIn);
/*  325 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  326 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
/*  327 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
/*  328 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
/*  329 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 6, 2, structureBoundingBoxIn);
/*  330 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 7, 2, structureBoundingBoxIn);
/*  331 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 6, 2, structureBoundingBoxIn);
/*  332 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 7, 2, structureBoundingBoxIn);
/*  333 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 6, 0, structureBoundingBoxIn);
/*  334 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 0, structureBoundingBoxIn);
/*  335 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 6, 4, structureBoundingBoxIn);
/*  336 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 4, structureBoundingBoxIn);
/*  337 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 6, structureBoundingBoxIn);
/*  338 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 6, structureBoundingBoxIn);
/*  339 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 3, 8, structureBoundingBoxIn);
/*  340 */       func_189926_a(worldIn, EnumFacing.SOUTH, 2, 4, 7, structureBoundingBoxIn);
/*  341 */       func_189926_a(worldIn, EnumFacing.EAST, 1, 4, 6, structureBoundingBoxIn);
/*  342 */       func_189926_a(worldIn, EnumFacing.WEST, 3, 4, 6, structureBoundingBoxIn);
/*  343 */       func_189926_a(worldIn, EnumFacing.NORTH, 2, 4, 5, structureBoundingBoxIn);
/*  344 */       IBlockState iblockstate4 = Blocks.LADDER.getDefaultState().withProperty((IProperty)BlockLadder.FACING, (Comparable)EnumFacing.WEST);
/*      */       
/*  346 */       for (int i = 1; i <= 9; i++)
/*      */       {
/*  348 */         setBlockState(worldIn, iblockstate4, 3, i, 3, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  351 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
/*  352 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
/*  353 */       func_189927_a(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);
/*      */       
/*  355 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
/*      */         
/*  357 */         setBlockState(worldIn, iblockstate1, 2, 0, -1, structureBoundingBoxIn);
/*      */         
/*  359 */         if (getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
/*      */         {
/*  361 */           setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  365 */       for (int k = 0; k < 9; k++) {
/*      */         
/*  367 */         for (int j = 0; j < 5; j++) {
/*      */           
/*  369 */           clearCurrentPositionBlocksUpwards(worldIn, j, 12, k, structureBoundingBoxIn);
/*  370 */           replaceAirAndLiquidDownwards(worldIn, iblockstate, j, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  374 */       spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
/*  375 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession) {
/*  380 */       return 2;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Field1
/*      */     extends Village
/*      */   {
/*      */     private Block cropTypeA;
/*      */     
/*      */     private Block cropTypeB;
/*      */     private Block cropTypeC;
/*      */     private Block cropTypeD;
/*      */     
/*      */     public Field1() {}
/*      */     
/*      */     public Field1(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox p_i45570_4_, EnumFacing facing) {
/*  397 */       super(start, type);
/*  398 */       setCoordBaseMode(facing);
/*  399 */       this.boundingBox = p_i45570_4_;
/*  400 */       this.cropTypeA = getRandomCropType(rand);
/*  401 */       this.cropTypeB = getRandomCropType(rand);
/*  402 */       this.cropTypeC = getRandomCropType(rand);
/*  403 */       this.cropTypeD = getRandomCropType(rand);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  408 */       super.writeStructureToNBT(tagCompound);
/*  409 */       tagCompound.setInteger("CA", Block.REGISTRY.getIDForObject(this.cropTypeA));
/*  410 */       tagCompound.setInteger("CB", Block.REGISTRY.getIDForObject(this.cropTypeB));
/*  411 */       tagCompound.setInteger("CC", Block.REGISTRY.getIDForObject(this.cropTypeC));
/*  412 */       tagCompound.setInteger("CD", Block.REGISTRY.getIDForObject(this.cropTypeD));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  417 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  418 */       this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
/*  419 */       this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
/*  420 */       this.cropTypeC = Block.getBlockById(tagCompound.getInteger("CC"));
/*  421 */       this.cropTypeD = Block.getBlockById(tagCompound.getInteger("CD"));
/*      */       
/*  423 */       if (!(this.cropTypeA instanceof BlockCrops))
/*      */       {
/*  425 */         this.cropTypeA = Blocks.WHEAT;
/*      */       }
/*      */       
/*  428 */       if (!(this.cropTypeB instanceof BlockCrops))
/*      */       {
/*  430 */         this.cropTypeB = Blocks.CARROTS;
/*      */       }
/*      */       
/*  433 */       if (!(this.cropTypeC instanceof BlockCrops))
/*      */       {
/*  435 */         this.cropTypeC = Blocks.POTATOES;
/*      */       }
/*      */       
/*  438 */       if (!(this.cropTypeD instanceof BlockCrops))
/*      */       {
/*  440 */         this.cropTypeD = Blocks.BEETROOTS;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private Block getRandomCropType(Random rand) {
/*  446 */       switch (rand.nextInt(10)) {
/*      */         
/*      */         case 0:
/*      */         case 1:
/*  450 */           return Blocks.CARROTS;
/*      */         
/*      */         case 2:
/*      */         case 3:
/*  454 */           return Blocks.POTATOES;
/*      */         
/*      */         case 4:
/*  457 */           return Blocks.BEETROOTS;
/*      */       } 
/*      */       
/*  460 */       return Blocks.WHEAT;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static Field1 createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175851_1_, Random rand, int p_175851_3_, int p_175851_4_, int p_175851_5_, EnumFacing facing, int p_175851_7_) {
/*  466 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 13, 4, 9, facing);
/*  467 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175851_1_, structureboundingbox) == null) ? new Field1(start, p_175851_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  472 */       if (this.averageGroundLvl < 0) {
/*      */         
/*  474 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  476 */         if (this.averageGroundLvl < 0)
/*      */         {
/*  478 */           return true;
/*      */         }
/*      */         
/*  481 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
/*      */       } 
/*      */       
/*  484 */       IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
/*  485 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  486 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
/*  487 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
/*  488 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 1, 8, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
/*  489 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 1, 11, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
/*  490 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, iblockstate, iblockstate, false);
/*  491 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, iblockstate, iblockstate, false);
/*  492 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 0, 0, 12, 0, 8, iblockstate, iblockstate, false);
/*  493 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 11, 0, 0, iblockstate, iblockstate, false);
/*  494 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 11, 0, 8, iblockstate, iblockstate, false);
/*  495 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);
/*  496 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 1, 9, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);
/*      */       
/*  498 */       for (int i = 1; i <= 7; i++) {
/*      */         
/*  500 */         int j = ((BlockCrops)this.cropTypeA).getMaxAge();
/*  501 */         int k = j / 3;
/*  502 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, k, j)), 1, 1, i, structureBoundingBoxIn);
/*  503 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, k, j)), 2, 1, i, structureBoundingBoxIn);
/*  504 */         int l = ((BlockCrops)this.cropTypeB).getMaxAge();
/*  505 */         int i1 = l / 3;
/*  506 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, i1, l)), 4, 1, i, structureBoundingBoxIn);
/*  507 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, i1, l)), 5, 1, i, structureBoundingBoxIn);
/*  508 */         int j1 = ((BlockCrops)this.cropTypeC).getMaxAge();
/*  509 */         int k1 = j1 / 3;
/*  510 */         setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getInt(randomIn, k1, j1)), 7, 1, i, structureBoundingBoxIn);
/*  511 */         setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getInt(randomIn, k1, j1)), 8, 1, i, structureBoundingBoxIn);
/*  512 */         int l1 = ((BlockCrops)this.cropTypeD).getMaxAge();
/*  513 */         int i2 = l1 / 3;
/*  514 */         setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getInt(randomIn, i2, l1)), 10, 1, i, structureBoundingBoxIn);
/*  515 */         setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getInt(randomIn, i2, l1)), 11, 1, i, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  518 */       for (int j2 = 0; j2 < 9; j2++) {
/*      */         
/*  520 */         for (int k2 = 0; k2 < 13; k2++) {
/*      */           
/*  522 */           clearCurrentPositionBlocksUpwards(worldIn, k2, 4, j2, structureBoundingBoxIn);
/*  523 */           replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), k2, -1, j2, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  527 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Field2
/*      */     extends Village
/*      */   {
/*      */     private Block cropTypeA;
/*      */     
/*      */     private Block cropTypeB;
/*      */     
/*      */     public Field2() {}
/*      */     
/*      */     public Field2(StructureVillagePieces.Start start, int p_i45569_2_, Random rand, StructureBoundingBox p_i45569_4_, EnumFacing facing) {
/*  542 */       super(start, p_i45569_2_);
/*  543 */       setCoordBaseMode(facing);
/*  544 */       this.boundingBox = p_i45569_4_;
/*  545 */       this.cropTypeA = getRandomCropType(rand);
/*  546 */       this.cropTypeB = getRandomCropType(rand);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  551 */       super.writeStructureToNBT(tagCompound);
/*  552 */       tagCompound.setInteger("CA", Block.REGISTRY.getIDForObject(this.cropTypeA));
/*  553 */       tagCompound.setInteger("CB", Block.REGISTRY.getIDForObject(this.cropTypeB));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  558 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  559 */       this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
/*  560 */       this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
/*      */     }
/*      */ 
/*      */     
/*      */     private Block getRandomCropType(Random rand) {
/*  565 */       switch (rand.nextInt(10)) {
/*      */         
/*      */         case 0:
/*      */         case 1:
/*  569 */           return Blocks.CARROTS;
/*      */         
/*      */         case 2:
/*      */         case 3:
/*  573 */           return Blocks.POTATOES;
/*      */         
/*      */         case 4:
/*  576 */           return Blocks.BEETROOTS;
/*      */       } 
/*      */       
/*  579 */       return Blocks.WHEAT;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static Field2 createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175852_1_, Random rand, int p_175852_3_, int p_175852_4_, int p_175852_5_, EnumFacing facing, int p_175852_7_) {
/*  585 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175852_3_, p_175852_4_, p_175852_5_, 0, 0, 0, 7, 4, 9, facing);
/*  586 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175852_1_, structureboundingbox) == null) ? new Field2(start, p_175852_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  591 */       if (this.averageGroundLvl < 0) {
/*      */         
/*  593 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  595 */         if (this.averageGroundLvl < 0)
/*      */         {
/*  597 */           return true;
/*      */         }
/*      */         
/*  600 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
/*      */       } 
/*      */       
/*  603 */       IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
/*  604 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 6, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  605 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
/*  606 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
/*  607 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, iblockstate, iblockstate, false);
/*  608 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, iblockstate, iblockstate, false);
/*  609 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 0, 0, iblockstate, iblockstate, false);
/*  610 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 5, 0, 8, iblockstate, iblockstate, false);
/*  611 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);
/*      */       
/*  613 */       for (int i = 1; i <= 7; i++) {
/*      */         
/*  615 */         int j = ((BlockCrops)this.cropTypeA).getMaxAge();
/*  616 */         int k = j / 3;
/*  617 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, k, j)), 1, 1, i, structureBoundingBoxIn);
/*  618 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, k, j)), 2, 1, i, structureBoundingBoxIn);
/*  619 */         int l = ((BlockCrops)this.cropTypeB).getMaxAge();
/*  620 */         int i1 = l / 3;
/*  621 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, i1, l)), 4, 1, i, structureBoundingBoxIn);
/*  622 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, i1, l)), 5, 1, i, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  625 */       for (int j1 = 0; j1 < 9; j1++) {
/*      */         
/*  627 */         for (int k1 = 0; k1 < 7; k1++) {
/*      */           
/*  629 */           clearCurrentPositionBlocksUpwards(worldIn, k1, 4, j1, structureBoundingBoxIn);
/*  630 */           replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), k1, -1, j1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  634 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Hall
/*      */     extends Village
/*      */   {
/*      */     public Hall() {}
/*      */ 
/*      */     
/*      */     public Hall(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox p_i45567_4_, EnumFacing facing) {
/*  646 */       super(start, type);
/*  647 */       setCoordBaseMode(facing);
/*  648 */       this.boundingBox = p_i45567_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static Hall createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175857_1_, Random rand, int p_175857_3_, int p_175857_4_, int p_175857_5_, EnumFacing facing, int p_175857_7_) {
/*  653 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175857_3_, p_175857_4_, p_175857_5_, 0, 0, 0, 9, 7, 11, facing);
/*  654 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175857_1_, structureboundingbox) == null) ? new Hall(start, p_175857_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  659 */       if (this.averageGroundLvl < 0) {
/*      */         
/*  661 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  663 */         if (this.averageGroundLvl < 0)
/*      */         {
/*  665 */           return true;
/*      */         }
/*      */         
/*  668 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 7 - 1, 0);
/*      */       } 
/*      */       
/*  671 */       IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
/*  672 */       IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH));
/*  673 */       IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.SOUTH));
/*  674 */       IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.WEST));
/*  675 */       IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
/*  676 */       IBlockState iblockstate5 = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
/*  677 */       IBlockState iblockstate6 = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
/*  678 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  679 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  680 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 8, 0, 10, Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), false);
/*  681 */       setBlockState(worldIn, iblockstate, 6, 0, 6, structureBoundingBoxIn);
/*  682 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 1, 10, iblockstate6, iblockstate6, false);
/*  683 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 6, 8, 1, 10, iblockstate6, iblockstate6, false);
/*  684 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 10, 7, 1, 10, iblockstate6, iblockstate6, false);
/*  685 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, iblockstate4, iblockstate4, false);
/*  686 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, iblockstate, iblockstate, false);
/*  687 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 5, iblockstate, iblockstate, false);
/*  688 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 1, 0, iblockstate, iblockstate, false);
/*  689 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 7, 1, 5, iblockstate, iblockstate, false);
/*  690 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, iblockstate4, iblockstate4, false);
/*  691 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 3, 5, iblockstate4, iblockstate4, false);
/*  692 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, iblockstate4, iblockstate4, false);
/*  693 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 8, 4, 4, iblockstate4, iblockstate4, false);
/*  694 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, iblockstate4, iblockstate4, false);
/*  695 */       setBlockState(worldIn, iblockstate4, 0, 4, 2, structureBoundingBoxIn);
/*  696 */       setBlockState(worldIn, iblockstate4, 0, 4, 3, structureBoundingBoxIn);
/*  697 */       setBlockState(worldIn, iblockstate4, 8, 4, 2, structureBoundingBoxIn);
/*  698 */       setBlockState(worldIn, iblockstate4, 8, 4, 3, structureBoundingBoxIn);
/*  699 */       IBlockState iblockstate7 = iblockstate1;
/*  700 */       IBlockState iblockstate8 = iblockstate2;
/*      */       
/*  702 */       for (int i = -1; i <= 2; i++) {
/*      */         
/*  704 */         for (int j = 0; j <= 8; j++) {
/*      */           
/*  706 */           setBlockState(worldIn, iblockstate7, j, 4 + i, i, structureBoundingBoxIn);
/*  707 */           setBlockState(worldIn, iblockstate8, j, 4 + i, 5 - i, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  711 */       setBlockState(worldIn, iblockstate5, 0, 2, 1, structureBoundingBoxIn);
/*  712 */       setBlockState(worldIn, iblockstate5, 0, 2, 4, structureBoundingBoxIn);
/*  713 */       setBlockState(worldIn, iblockstate5, 8, 2, 1, structureBoundingBoxIn);
/*  714 */       setBlockState(worldIn, iblockstate5, 8, 2, 4, structureBoundingBoxIn);
/*  715 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  716 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
/*  717 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
/*  718 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
/*  719 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
/*  720 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
/*  721 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
/*  722 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
/*  723 */       setBlockState(worldIn, iblockstate6, 2, 1, 3, structureBoundingBoxIn);
/*  724 */       setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 2, 2, 3, structureBoundingBoxIn);
/*  725 */       setBlockState(worldIn, iblockstate4, 1, 1, 4, structureBoundingBoxIn);
/*  726 */       setBlockState(worldIn, iblockstate7, 2, 1, 4, structureBoundingBoxIn);
/*  727 */       setBlockState(worldIn, iblockstate3, 1, 1, 3, structureBoundingBoxIn);
/*  728 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 1, 7, 0, 3, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), Blocks.DOUBLE_STONE_SLAB.getDefaultState(), false);
/*  729 */       setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 6, 1, 1, structureBoundingBoxIn);
/*  730 */       setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 6, 1, 2, structureBoundingBoxIn);
/*  731 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
/*  732 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
/*  733 */       func_189926_a(worldIn, EnumFacing.NORTH, 2, 3, 1, structureBoundingBoxIn);
/*  734 */       func_189927_a(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);
/*      */       
/*  736 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
/*      */         
/*  738 */         setBlockState(worldIn, iblockstate7, 2, 0, -1, structureBoundingBoxIn);
/*      */         
/*  740 */         if (getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
/*      */         {
/*  742 */           setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  746 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
/*  747 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
/*  748 */       func_189926_a(worldIn, EnumFacing.SOUTH, 6, 3, 4, structureBoundingBoxIn);
/*  749 */       func_189927_a(worldIn, structureBoundingBoxIn, randomIn, 6, 1, 5, EnumFacing.SOUTH);
/*      */       
/*  751 */       for (int k = 0; k < 5; k++) {
/*      */         
/*  753 */         for (int l = 0; l < 9; l++) {
/*      */           
/*  755 */           clearCurrentPositionBlocksUpwards(worldIn, l, 7, k, structureBoundingBoxIn);
/*  756 */           replaceAirAndLiquidDownwards(worldIn, iblockstate, l, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  760 */       spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
/*  761 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession) {
/*  766 */       return (villagersSpawnedIn == 0) ? 4 : super.chooseProfession(villagersSpawnedIn, currentVillagerProfession);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class House1
/*      */     extends Village
/*      */   {
/*      */     public House1() {}
/*      */ 
/*      */     
/*      */     public House1(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox p_i45571_4_, EnumFacing facing) {
/*  778 */       super(start, type);
/*  779 */       setCoordBaseMode(facing);
/*  780 */       this.boundingBox = p_i45571_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static House1 createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175850_1_, Random rand, int p_175850_3_, int p_175850_4_, int p_175850_5_, EnumFacing facing, int p_175850_7_) {
/*  785 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175850_3_, p_175850_4_, p_175850_5_, 0, 0, 0, 9, 9, 6, facing);
/*  786 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175850_1_, structureboundingbox) == null) ? new House1(start, p_175850_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  791 */       if (this.averageGroundLvl < 0) {
/*      */         
/*  793 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  795 */         if (this.averageGroundLvl < 0)
/*      */         {
/*  797 */           return true;
/*      */         }
/*      */         
/*  800 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 9 - 1, 0);
/*      */       } 
/*      */       
/*  803 */       IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
/*  804 */       IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH));
/*  805 */       IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.SOUTH));
/*  806 */       IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.EAST));
/*  807 */       IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
/*  808 */       IBlockState iblockstate5 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH));
/*  809 */       IBlockState iblockstate6 = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
/*  810 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  811 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, iblockstate, iblockstate, false);
/*  812 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, iblockstate, iblockstate, false);
/*  813 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, iblockstate, iblockstate, false);
/*  814 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, iblockstate, iblockstate, false);
/*      */       
/*  816 */       for (int i = -1; i <= 2; i++) {
/*      */         
/*  818 */         for (int j = 0; j <= 8; j++) {
/*      */           
/*  820 */           setBlockState(worldIn, iblockstate1, j, 6 + i, i, structureBoundingBoxIn);
/*  821 */           setBlockState(worldIn, iblockstate2, j, 6 + i, 5 - i, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  825 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, iblockstate, iblockstate, false);
/*  826 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, iblockstate, iblockstate, false);
/*  827 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, iblockstate, iblockstate, false);
/*  828 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, iblockstate, iblockstate, false);
/*  829 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, iblockstate, iblockstate, false);
/*  830 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, iblockstate, iblockstate, false);
/*  831 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, iblockstate, iblockstate, false);
/*  832 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, iblockstate, iblockstate, false);
/*  833 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, iblockstate4, iblockstate4, false);
/*  834 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, iblockstate4, iblockstate4, false);
/*  835 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, iblockstate4, iblockstate4, false);
/*  836 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, iblockstate4, iblockstate4, false);
/*  837 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
/*  838 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
/*  839 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
/*  840 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
/*  841 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 3, 0, structureBoundingBoxIn);
/*  842 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 3, 0, structureBoundingBoxIn);
/*  843 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  844 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
/*  845 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
/*  846 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 3, structureBoundingBoxIn);
/*  847 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
/*  848 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
/*  849 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 2, structureBoundingBoxIn);
/*  850 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 3, structureBoundingBoxIn);
/*  851 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
/*  852 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
/*  853 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/*  854 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
/*  855 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, iblockstate4, iblockstate4, false);
/*  856 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, iblockstate4, iblockstate4, false);
/*  857 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
/*  858 */       setBlockState(worldIn, iblockstate4, 7, 1, 4, structureBoundingBoxIn);
/*  859 */       setBlockState(worldIn, iblockstate3, 7, 1, 3, structureBoundingBoxIn);
/*  860 */       setBlockState(worldIn, iblockstate1, 6, 1, 4, structureBoundingBoxIn);
/*  861 */       setBlockState(worldIn, iblockstate1, 5, 1, 4, structureBoundingBoxIn);
/*  862 */       setBlockState(worldIn, iblockstate1, 4, 1, 4, structureBoundingBoxIn);
/*  863 */       setBlockState(worldIn, iblockstate1, 3, 1, 4, structureBoundingBoxIn);
/*  864 */       setBlockState(worldIn, iblockstate6, 6, 1, 3, structureBoundingBoxIn);
/*  865 */       setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
/*  866 */       setBlockState(worldIn, iblockstate6, 4, 1, 3, structureBoundingBoxIn);
/*  867 */       setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 4, 2, 3, structureBoundingBoxIn);
/*  868 */       setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 7, 1, 1, structureBoundingBoxIn);
/*  869 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
/*  870 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
/*  871 */       func_189927_a(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.NORTH);
/*      */       
/*  873 */       if (getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
/*      */         
/*  875 */         setBlockState(worldIn, iblockstate5, 1, 0, -1, structureBoundingBoxIn);
/*      */         
/*  877 */         if (getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
/*      */         {
/*  879 */           setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 1, -1, -1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  883 */       for (int l = 0; l < 6; l++) {
/*      */         
/*  885 */         for (int k = 0; k < 9; k++) {
/*      */           
/*  887 */           clearCurrentPositionBlocksUpwards(worldIn, k, 9, l, structureBoundingBoxIn);
/*  888 */           replaceAirAndLiquidDownwards(worldIn, iblockstate, k, -1, l, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  892 */       spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
/*  893 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession) {
/*  898 */       return 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class House2
/*      */     extends Village
/*      */   {
/*      */     private boolean hasMadeChest;
/*      */ 
/*      */     
/*      */     public House2() {}
/*      */     
/*      */     public House2(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox p_i45563_4_, EnumFacing facing) {
/*  912 */       super(start, type);
/*  913 */       setCoordBaseMode(facing);
/*  914 */       this.boundingBox = p_i45563_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static House2 createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175855_1_, Random rand, int p_175855_3_, int p_175855_4_, int p_175855_5_, EnumFacing facing, int p_175855_7_) {
/*  919 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175855_3_, p_175855_4_, p_175855_5_, 0, 0, 0, 10, 6, 7, facing);
/*  920 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175855_1_, structureboundingbox) == null) ? new House2(start, p_175855_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  925 */       super.writeStructureToNBT(tagCompound);
/*  926 */       tagCompound.setBoolean("Chest", this.hasMadeChest);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  931 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  932 */       this.hasMadeChest = tagCompound.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  937 */       if (this.averageGroundLvl < 0) {
/*      */         
/*  939 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  941 */         if (this.averageGroundLvl < 0)
/*      */         {
/*  943 */           return true;
/*      */         }
/*      */         
/*  946 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
/*      */       } 
/*      */       
/*  949 */       IBlockState iblockstate = Blocks.COBBLESTONE.getDefaultState();
/*  950 */       IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH));
/*  951 */       IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.WEST));
/*  952 */       IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
/*  953 */       IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH));
/*  954 */       IBlockState iblockstate5 = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
/*  955 */       IBlockState iblockstate6 = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
/*  956 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 9, 4, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  957 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 0, 6, iblockstate, iblockstate, false);
/*  958 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 9, 4, 6, iblockstate, iblockstate, false);
/*  959 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 9, 5, 6, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
/*  960 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 8, 5, 5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  961 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, iblockstate3, iblockstate3, false);
/*  962 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 4, 0, iblockstate5, iblockstate5, false);
/*  963 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 4, 0, iblockstate5, iblockstate5, false);
/*  964 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 4, 6, iblockstate5, iblockstate5, false);
/*  965 */       setBlockState(worldIn, iblockstate3, 3, 3, 1, structureBoundingBoxIn);
/*  966 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 3, 2, iblockstate3, iblockstate3, false);
/*  967 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 5, 3, 3, iblockstate3, iblockstate3, false);
/*  968 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 5, iblockstate3, iblockstate3, false);
/*  969 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 6, 5, 3, 6, iblockstate3, iblockstate3, false);
/*  970 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 5, 3, 0, iblockstate6, iblockstate6, false);
/*  971 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 0, 9, 3, 0, iblockstate6, iblockstate6, false);
/*  972 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 9, 4, 6, iblockstate, iblockstate, false);
/*  973 */       setBlockState(worldIn, Blocks.FLOWING_LAVA.getDefaultState(), 7, 1, 5, structureBoundingBoxIn);
/*  974 */       setBlockState(worldIn, Blocks.FLOWING_LAVA.getDefaultState(), 8, 1, 5, structureBoundingBoxIn);
/*  975 */       setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 9, 2, 5, structureBoundingBoxIn);
/*  976 */       setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 9, 2, 4, structureBoundingBoxIn);
/*  977 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 4, 8, 2, 5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  978 */       setBlockState(worldIn, iblockstate, 6, 1, 3, structureBoundingBoxIn);
/*  979 */       setBlockState(worldIn, Blocks.FURNACE.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
/*  980 */       setBlockState(worldIn, Blocks.FURNACE.getDefaultState(), 6, 3, 3, structureBoundingBoxIn);
/*  981 */       setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 8, 1, 1, structureBoundingBoxIn);
/*  982 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  983 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
/*  984 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
/*  985 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 6, structureBoundingBoxIn);
/*  986 */       setBlockState(worldIn, iblockstate6, 2, 1, 4, structureBoundingBoxIn);
/*  987 */       setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
/*  988 */       setBlockState(worldIn, iblockstate3, 1, 1, 5, structureBoundingBoxIn);
/*  989 */       setBlockState(worldIn, iblockstate1, 2, 1, 5, structureBoundingBoxIn);
/*  990 */       setBlockState(worldIn, iblockstate2, 1, 1, 4, structureBoundingBoxIn);
/*      */       
/*  992 */       if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(5, 5), getYWithOffset(1), getZWithOffset(5, 5)))) {
/*      */         
/*  994 */         this.hasMadeChest = true;
/*  995 */         generateChest(worldIn, structureBoundingBoxIn, randomIn, 5, 1, 5, LootTableList.CHESTS_VILLAGE_BLACKSMITH);
/*      */       } 
/*      */       
/*  998 */       for (int i = 6; i <= 8; i++) {
/*      */         
/* 1000 */         if (getBlockStateFromPos(worldIn, i, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, i, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
/*      */           
/* 1002 */           setBlockState(worldIn, iblockstate4, i, 0, -1, structureBoundingBoxIn);
/*      */           
/* 1004 */           if (getBlockStateFromPos(worldIn, i, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
/*      */           {
/* 1006 */             setBlockState(worldIn, Blocks.GRASS.getDefaultState(), i, -1, -1, structureBoundingBoxIn);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1011 */       for (int k = 0; k < 7; k++) {
/*      */         
/* 1013 */         for (int j = 0; j < 10; j++) {
/*      */           
/* 1015 */           clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
/* 1016 */           replaceAirAndLiquidDownwards(worldIn, iblockstate, j, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1020 */       spawnVillagers(worldIn, structureBoundingBoxIn, 7, 1, 1, 1);
/* 1021 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession) {
/* 1026 */       return 3;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class House3
/*      */     extends Village
/*      */   {
/*      */     public House3() {}
/*      */ 
/*      */     
/*      */     public House3(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox p_i45561_4_, EnumFacing facing) {
/* 1038 */       super(start, type);
/* 1039 */       setCoordBaseMode(facing);
/* 1040 */       this.boundingBox = p_i45561_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static House3 createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175849_1_, Random rand, int p_175849_3_, int p_175849_4_, int p_175849_5_, EnumFacing facing, int p_175849_7_) {
/* 1045 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175849_3_, p_175849_4_, p_175849_5_, 0, 0, 0, 9, 7, 12, facing);
/* 1046 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175849_1_, structureboundingbox) == null) ? new House3(start, p_175849_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1051 */       if (this.averageGroundLvl < 0) {
/*      */         
/* 1053 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1055 */         if (this.averageGroundLvl < 0)
/*      */         {
/* 1057 */           return true;
/*      */         }
/*      */         
/* 1060 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 7 - 1, 0);
/*      */       } 
/*      */       
/* 1063 */       IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
/* 1064 */       IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH));
/* 1065 */       IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.SOUTH));
/* 1066 */       IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.EAST));
/* 1067 */       IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.WEST));
/* 1068 */       IBlockState iblockstate5 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
/* 1069 */       IBlockState iblockstate6 = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
/* 1070 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 1071 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 1072 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, iblockstate5, iblockstate5, false);
/* 1073 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, iblockstate5, iblockstate5, false);
/* 1074 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, iblockstate, iblockstate, false);
/* 1075 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, iblockstate, iblockstate, false);
/* 1076 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, iblockstate, iblockstate, false);
/* 1077 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, iblockstate, iblockstate, false);
/* 1078 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, iblockstate, iblockstate, false);
/* 1079 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, iblockstate, iblockstate, false);
/* 1080 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, iblockstate5, iblockstate5, false);
/* 1081 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, iblockstate5, iblockstate5, false);
/* 1082 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, iblockstate5, iblockstate5, false);
/* 1083 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, iblockstate5, iblockstate5, false);
/* 1084 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, iblockstate5, iblockstate5, false);
/* 1085 */       setBlockState(worldIn, iblockstate5, 0, 4, 2, structureBoundingBoxIn);
/* 1086 */       setBlockState(worldIn, iblockstate5, 0, 4, 3, structureBoundingBoxIn);
/* 1087 */       setBlockState(worldIn, iblockstate5, 8, 4, 2, structureBoundingBoxIn);
/* 1088 */       setBlockState(worldIn, iblockstate5, 8, 4, 3, structureBoundingBoxIn);
/* 1089 */       setBlockState(worldIn, iblockstate5, 8, 4, 4, structureBoundingBoxIn);
/* 1090 */       IBlockState iblockstate7 = iblockstate1;
/* 1091 */       IBlockState iblockstate8 = iblockstate2;
/* 1092 */       IBlockState iblockstate9 = iblockstate4;
/* 1093 */       IBlockState iblockstate10 = iblockstate3;
/*      */       
/* 1095 */       for (int i = -1; i <= 2; i++) {
/*      */         
/* 1097 */         for (int j = 0; j <= 8; j++) {
/*      */           
/* 1099 */           setBlockState(worldIn, iblockstate7, j, 4 + i, i, structureBoundingBoxIn);
/*      */           
/* 1101 */           if ((i > -1 || j <= 1) && (i > 0 || j <= 3) && (i > 1 || j <= 4 || j >= 6))
/*      */           {
/* 1103 */             setBlockState(worldIn, iblockstate8, j, 4 + i, 5 - i, structureBoundingBoxIn);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1108 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, iblockstate5, iblockstate5, false);
/* 1109 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, iblockstate5, iblockstate5, false);
/* 1110 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, iblockstate5, iblockstate5, false);
/* 1111 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, iblockstate5, iblockstate5, false);
/* 1112 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, iblockstate5, iblockstate5, false);
/*      */       
/* 1114 */       for (int k = 4; k >= 1; k--) {
/*      */         
/* 1116 */         setBlockState(worldIn, iblockstate5, k, 2 + k, 7 - k, structureBoundingBoxIn);
/*      */         
/* 1118 */         for (int k1 = 8 - k; k1 <= 10; k1++)
/*      */         {
/* 1120 */           setBlockState(worldIn, iblockstate10, k, 2 + k, k1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1124 */       setBlockState(worldIn, iblockstate5, 6, 6, 3, structureBoundingBoxIn);
/* 1125 */       setBlockState(worldIn, iblockstate5, 7, 5, 4, structureBoundingBoxIn);
/* 1126 */       setBlockState(worldIn, iblockstate4, 6, 6, 4, structureBoundingBoxIn);
/*      */       
/* 1128 */       for (int l = 6; l <= 8; l++) {
/*      */         
/* 1130 */         for (int l1 = 5; l1 <= 10; l1++)
/*      */         {
/* 1132 */           setBlockState(worldIn, iblockstate9, l, 12 - l, l1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1136 */       setBlockState(worldIn, iblockstate6, 0, 2, 1, structureBoundingBoxIn);
/* 1137 */       setBlockState(worldIn, iblockstate6, 0, 2, 4, structureBoundingBoxIn);
/* 1138 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/* 1139 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
/* 1140 */       setBlockState(worldIn, iblockstate6, 4, 2, 0, structureBoundingBoxIn);
/* 1141 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
/* 1142 */       setBlockState(worldIn, iblockstate6, 6, 2, 0, structureBoundingBoxIn);
/* 1143 */       setBlockState(worldIn, iblockstate6, 8, 2, 1, structureBoundingBoxIn);
/* 1144 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
/* 1145 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
/* 1146 */       setBlockState(worldIn, iblockstate6, 8, 2, 4, structureBoundingBoxIn);
/* 1147 */       setBlockState(worldIn, iblockstate5, 8, 2, 5, structureBoundingBoxIn);
/* 1148 */       setBlockState(worldIn, iblockstate6, 8, 2, 6, structureBoundingBoxIn);
/* 1149 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 7, structureBoundingBoxIn);
/* 1150 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 8, structureBoundingBoxIn);
/* 1151 */       setBlockState(worldIn, iblockstate6, 8, 2, 9, structureBoundingBoxIn);
/* 1152 */       setBlockState(worldIn, iblockstate6, 2, 2, 6, structureBoundingBoxIn);
/* 1153 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 7, structureBoundingBoxIn);
/* 1154 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 8, structureBoundingBoxIn);
/* 1155 */       setBlockState(worldIn, iblockstate6, 2, 2, 9, structureBoundingBoxIn);
/* 1156 */       setBlockState(worldIn, iblockstate6, 4, 4, 10, structureBoundingBoxIn);
/* 1157 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 4, 10, structureBoundingBoxIn);
/* 1158 */       setBlockState(worldIn, iblockstate6, 6, 4, 10, structureBoundingBoxIn);
/* 1159 */       setBlockState(worldIn, iblockstate5, 5, 5, 10, structureBoundingBoxIn);
/* 1160 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
/* 1161 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
/* 1162 */       func_189926_a(worldIn, EnumFacing.NORTH, 2, 3, 1, structureBoundingBoxIn);
/* 1163 */       func_189927_a(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);
/* 1164 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       
/* 1166 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
/*      */         
/* 1168 */         setBlockState(worldIn, iblockstate7, 2, 0, -1, structureBoundingBoxIn);
/*      */         
/* 1170 */         if (getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
/*      */         {
/* 1172 */           setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1176 */       for (int i1 = 0; i1 < 5; i1++) {
/*      */         
/* 1178 */         for (int i2 = 0; i2 < 9; i2++) {
/*      */           
/* 1180 */           clearCurrentPositionBlocksUpwards(worldIn, i2, 7, i1, structureBoundingBoxIn);
/* 1181 */           replaceAirAndLiquidDownwards(worldIn, iblockstate, i2, -1, i1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1185 */       for (int j1 = 5; j1 < 11; j1++) {
/*      */         
/* 1187 */         for (int j2 = 2; j2 < 9; j2++) {
/*      */           
/* 1189 */           clearCurrentPositionBlocksUpwards(worldIn, j2, 7, j1, structureBoundingBoxIn);
/* 1190 */           replaceAirAndLiquidDownwards(worldIn, iblockstate, j2, -1, j1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1194 */       spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
/* 1195 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class House4Garden
/*      */     extends Village
/*      */   {
/*      */     private boolean isRoofAccessible;
/*      */ 
/*      */     
/*      */     public House4Garden() {}
/*      */     
/*      */     public House4Garden(StructureVillagePieces.Start start, int p_i45566_2_, Random rand, StructureBoundingBox p_i45566_4_, EnumFacing facing) {
/* 1209 */       super(start, p_i45566_2_);
/* 1210 */       setCoordBaseMode(facing);
/* 1211 */       this.boundingBox = p_i45566_4_;
/* 1212 */       this.isRoofAccessible = rand.nextBoolean();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1217 */       super.writeStructureToNBT(tagCompound);
/* 1218 */       tagCompound.setBoolean("Terrace", this.isRoofAccessible);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 1223 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 1224 */       this.isRoofAccessible = tagCompound.getBoolean("Terrace");
/*      */     }
/*      */ 
/*      */     
/*      */     public static House4Garden createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, EnumFacing facing, int p_175858_7_) {
/* 1229 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, facing);
/* 1230 */       return (StructureComponent.findIntersecting(p_175858_1_, structureboundingbox) != null) ? null : new House4Garden(start, p_175858_7_, rand, structureboundingbox, facing);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1235 */       if (this.averageGroundLvl < 0) {
/*      */         
/* 1237 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1239 */         if (this.averageGroundLvl < 0)
/*      */         {
/* 1241 */           return true;
/*      */         }
/*      */         
/* 1244 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
/*      */       } 
/*      */       
/* 1247 */       IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
/* 1248 */       IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
/* 1249 */       IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH));
/* 1250 */       IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
/* 1251 */       IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
/* 1252 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, iblockstate, iblockstate, false);
/* 1253 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, iblockstate3, iblockstate3, false);
/* 1254 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, iblockstate1, iblockstate1, false);
/* 1255 */       setBlockState(worldIn, iblockstate, 0, 1, 0, structureBoundingBoxIn);
/* 1256 */       setBlockState(worldIn, iblockstate, 0, 2, 0, structureBoundingBoxIn);
/* 1257 */       setBlockState(worldIn, iblockstate, 0, 3, 0, structureBoundingBoxIn);
/* 1258 */       setBlockState(worldIn, iblockstate, 4, 1, 0, structureBoundingBoxIn);
/* 1259 */       setBlockState(worldIn, iblockstate, 4, 2, 0, structureBoundingBoxIn);
/* 1260 */       setBlockState(worldIn, iblockstate, 4, 3, 0, structureBoundingBoxIn);
/* 1261 */       setBlockState(worldIn, iblockstate, 0, 1, 4, structureBoundingBoxIn);
/* 1262 */       setBlockState(worldIn, iblockstate, 0, 2, 4, structureBoundingBoxIn);
/* 1263 */       setBlockState(worldIn, iblockstate, 0, 3, 4, structureBoundingBoxIn);
/* 1264 */       setBlockState(worldIn, iblockstate, 4, 1, 4, structureBoundingBoxIn);
/* 1265 */       setBlockState(worldIn, iblockstate, 4, 2, 4, structureBoundingBoxIn);
/* 1266 */       setBlockState(worldIn, iblockstate, 4, 3, 4, structureBoundingBoxIn);
/* 1267 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, iblockstate1, iblockstate1, false);
/* 1268 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, iblockstate1, iblockstate1, false);
/* 1269 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, iblockstate1, iblockstate1, false);
/* 1270 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/* 1271 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
/* 1272 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
/* 1273 */       setBlockState(worldIn, iblockstate1, 1, 1, 0, structureBoundingBoxIn);
/* 1274 */       setBlockState(worldIn, iblockstate1, 1, 2, 0, structureBoundingBoxIn);
/* 1275 */       setBlockState(worldIn, iblockstate1, 1, 3, 0, structureBoundingBoxIn);
/* 1276 */       setBlockState(worldIn, iblockstate1, 2, 3, 0, structureBoundingBoxIn);
/* 1277 */       setBlockState(worldIn, iblockstate1, 3, 3, 0, structureBoundingBoxIn);
/* 1278 */       setBlockState(worldIn, iblockstate1, 3, 2, 0, structureBoundingBoxIn);
/* 1279 */       setBlockState(worldIn, iblockstate1, 3, 1, 0, structureBoundingBoxIn);
/*      */       
/* 1281 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
/*      */         
/* 1283 */         setBlockState(worldIn, iblockstate2, 2, 0, -1, structureBoundingBoxIn);
/*      */         
/* 1285 */         if (getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
/*      */         {
/* 1287 */           setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1291 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       
/* 1293 */       if (this.isRoofAccessible) {
/*      */         
/* 1295 */         setBlockState(worldIn, iblockstate4, 0, 5, 0, structureBoundingBoxIn);
/* 1296 */         setBlockState(worldIn, iblockstate4, 1, 5, 0, structureBoundingBoxIn);
/* 1297 */         setBlockState(worldIn, iblockstate4, 2, 5, 0, structureBoundingBoxIn);
/* 1298 */         setBlockState(worldIn, iblockstate4, 3, 5, 0, structureBoundingBoxIn);
/* 1299 */         setBlockState(worldIn, iblockstate4, 4, 5, 0, structureBoundingBoxIn);
/* 1300 */         setBlockState(worldIn, iblockstate4, 0, 5, 4, structureBoundingBoxIn);
/* 1301 */         setBlockState(worldIn, iblockstate4, 1, 5, 4, structureBoundingBoxIn);
/* 1302 */         setBlockState(worldIn, iblockstate4, 2, 5, 4, structureBoundingBoxIn);
/* 1303 */         setBlockState(worldIn, iblockstate4, 3, 5, 4, structureBoundingBoxIn);
/* 1304 */         setBlockState(worldIn, iblockstate4, 4, 5, 4, structureBoundingBoxIn);
/* 1305 */         setBlockState(worldIn, iblockstate4, 4, 5, 1, structureBoundingBoxIn);
/* 1306 */         setBlockState(worldIn, iblockstate4, 4, 5, 2, structureBoundingBoxIn);
/* 1307 */         setBlockState(worldIn, iblockstate4, 4, 5, 3, structureBoundingBoxIn);
/* 1308 */         setBlockState(worldIn, iblockstate4, 0, 5, 1, structureBoundingBoxIn);
/* 1309 */         setBlockState(worldIn, iblockstate4, 0, 5, 2, structureBoundingBoxIn);
/* 1310 */         setBlockState(worldIn, iblockstate4, 0, 5, 3, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 1313 */       if (this.isRoofAccessible) {
/*      */         
/* 1315 */         IBlockState iblockstate5 = Blocks.LADDER.getDefaultState().withProperty((IProperty)BlockLadder.FACING, (Comparable)EnumFacing.SOUTH);
/* 1316 */         setBlockState(worldIn, iblockstate5, 3, 1, 3, structureBoundingBoxIn);
/* 1317 */         setBlockState(worldIn, iblockstate5, 3, 2, 3, structureBoundingBoxIn);
/* 1318 */         setBlockState(worldIn, iblockstate5, 3, 3, 3, structureBoundingBoxIn);
/* 1319 */         setBlockState(worldIn, iblockstate5, 3, 4, 3, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 1322 */       func_189926_a(worldIn, EnumFacing.NORTH, 2, 3, 1, structureBoundingBoxIn);
/*      */       
/* 1324 */       for (int j = 0; j < 5; j++) {
/*      */         
/* 1326 */         for (int i = 0; i < 5; i++) {
/*      */           
/* 1328 */           clearCurrentPositionBlocksUpwards(worldIn, i, 6, j, structureBoundingBoxIn);
/* 1329 */           replaceAirAndLiquidDownwards(worldIn, iblockstate, i, -1, j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1333 */       spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
/* 1334 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Path
/*      */     extends Road
/*      */   {
/*      */     private int length;
/*      */ 
/*      */     
/*      */     public Path() {}
/*      */     
/*      */     public Path(StructureVillagePieces.Start start, int p_i45562_2_, Random rand, StructureBoundingBox p_i45562_4_, EnumFacing facing) {
/* 1348 */       super(start, p_i45562_2_);
/* 1349 */       setCoordBaseMode(facing);
/* 1350 */       this.boundingBox = p_i45562_4_;
/* 1351 */       this.length = Math.max(p_i45562_4_.getXSize(), p_i45562_4_.getZSize());
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1356 */       super.writeStructureToNBT(tagCompound);
/* 1357 */       tagCompound.setInteger("Length", this.length);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 1362 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 1363 */       this.length = tagCompound.getInteger("Length");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1368 */       boolean flag = false;
/*      */       
/* 1370 */       for (int i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5)) {
/*      */         
/* 1372 */         StructureComponent structurecomponent = getNextComponentNN((StructureVillagePieces.Start)componentIn, listIn, rand, 0, i);
/*      */         
/* 1374 */         if (structurecomponent != null) {
/*      */           
/* 1376 */           i += Math.max(structurecomponent.boundingBox.getXSize(), structurecomponent.boundingBox.getZSize());
/* 1377 */           flag = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1381 */       for (int j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5)) {
/*      */         
/* 1383 */         StructureComponent structurecomponent1 = getNextComponentPP((StructureVillagePieces.Start)componentIn, listIn, rand, 0, j);
/*      */         
/* 1385 */         if (structurecomponent1 != null) {
/*      */           
/* 1387 */           j += Math.max(structurecomponent1.boundingBox.getXSize(), structurecomponent1.boundingBox.getZSize());
/* 1388 */           flag = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1392 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/* 1394 */       if (flag && rand.nextInt(3) > 0 && enumfacing != null)
/*      */       {
/* 1396 */         switch (enumfacing) {
/*      */ 
/*      */           
/*      */           default:
/* 1400 */             StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, getComponentType());
/*      */             break;
/*      */           
/*      */           case SOUTH:
/* 1404 */             StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, getComponentType());
/*      */             break;
/*      */           
/*      */           case WEST:
/* 1408 */             StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */             break;
/*      */           
/*      */           case EAST:
/* 1412 */             StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */             break;
/*      */         } 
/*      */       }
/* 1416 */       if (flag && rand.nextInt(3) > 0 && enumfacing != null) {
/*      */         
/* 1418 */         switch (enumfacing) {
/*      */ 
/*      */           
/*      */           default:
/* 1422 */             StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, getComponentType());
/*      */             return;
/*      */           
/*      */           case SOUTH:
/* 1426 */             StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, getComponentType());
/*      */             return;
/*      */           
/*      */           case WEST:
/* 1430 */             StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType()); return;
/*      */           case EAST:
/*      */             break;
/*      */         } 
/* 1434 */         StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public static StructureBoundingBox findPieceBox(StructureVillagePieces.Start start, List<StructureComponent> p_175848_1_, Random rand, int p_175848_3_, int p_175848_4_, int p_175848_5_, EnumFacing facing) {
/* 1441 */       for (int i = 7 * MathHelper.getInt(rand, 3, 5); i >= 7; i -= 7) {
/*      */         
/* 1443 */         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, i, facing);
/*      */         
/* 1445 */         if (StructureComponent.findIntersecting(p_175848_1_, structureboundingbox) == null)
/*      */         {
/* 1447 */           return structureboundingbox;
/*      */         }
/*      */       } 
/*      */       
/* 1451 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1456 */       IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.GRASS_PATH.getDefaultState());
/* 1457 */       IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
/* 1458 */       IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.GRAVEL.getDefaultState());
/* 1459 */       IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
/*      */       
/* 1461 */       for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; i++) {
/*      */         
/* 1463 */         for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; j++) {
/*      */           
/* 1465 */           BlockPos blockpos = new BlockPos(i, 64, j);
/*      */           
/* 1467 */           if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/*      */             
/* 1469 */             blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos).down();
/*      */             
/* 1471 */             if (blockpos.getY() < worldIn.getSeaLevel())
/*      */             {
/* 1473 */               blockpos = new BlockPos(blockpos.getX(), worldIn.getSeaLevel() - 1, blockpos.getZ());
/*      */             }
/*      */             
/* 1476 */             while (blockpos.getY() >= worldIn.getSeaLevel() - 1) {
/*      */               
/* 1478 */               IBlockState iblockstate4 = worldIn.getBlockState(blockpos);
/*      */               
/* 1480 */               if (iblockstate4.getBlock() == Blocks.GRASS && worldIn.isAirBlock(blockpos.up())) {
/*      */                 
/* 1482 */                 worldIn.setBlockState(blockpos, iblockstate, 2);
/*      */                 
/*      */                 break;
/*      */               } 
/* 1486 */               if (iblockstate4.getMaterial().isLiquid()) {
/*      */                 
/* 1488 */                 worldIn.setBlockState(blockpos, iblockstate1, 2);
/*      */                 
/*      */                 break;
/*      */               } 
/* 1492 */               if (iblockstate4.getBlock() == Blocks.SAND || iblockstate4.getBlock() == Blocks.SANDSTONE || iblockstate4.getBlock() == Blocks.RED_SANDSTONE) {
/*      */                 
/* 1494 */                 worldIn.setBlockState(blockpos, iblockstate2, 2);
/* 1495 */                 worldIn.setBlockState(blockpos.down(), iblockstate3, 2);
/*      */                 
/*      */                 break;
/*      */               } 
/* 1499 */               blockpos = blockpos.down();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1505 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class PieceWeight
/*      */   {
/*      */     public Class<? extends StructureVillagePieces.Village> villagePieceClass;
/*      */     public final int villagePieceWeight;
/*      */     public int villagePiecesSpawned;
/*      */     public int villagePiecesLimit;
/*      */     
/*      */     public PieceWeight(Class<? extends StructureVillagePieces.Village> p_i2098_1_, int p_i2098_2_, int p_i2098_3_) {
/* 1518 */       this.villagePieceClass = p_i2098_1_;
/* 1519 */       this.villagePieceWeight = p_i2098_2_;
/* 1520 */       this.villagePiecesLimit = p_i2098_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canSpawnMoreVillagePiecesOfType(int componentType) {
/* 1525 */       return !(this.villagePiecesLimit != 0 && this.villagePiecesSpawned >= this.villagePiecesLimit);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canSpawnMoreVillagePieces() {
/* 1530 */       return !(this.villagePiecesLimit != 0 && this.villagePiecesSpawned >= this.villagePiecesLimit);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static abstract class Road
/*      */     extends Village
/*      */   {
/*      */     public Road() {}
/*      */ 
/*      */     
/*      */     protected Road(StructureVillagePieces.Start start, int type) {
/* 1542 */       super(start, type);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Start
/*      */     extends Well {
/*      */     public BiomeProvider worldChunkMngr;
/*      */     public int terrainType;
/*      */     public StructureVillagePieces.PieceWeight structVillagePieceWeight;
/*      */     public List<StructureVillagePieces.PieceWeight> structureVillageWeightedPieceList;
/* 1552 */     public List<StructureComponent> pendingHouses = Lists.newArrayList();
/* 1553 */     public List<StructureComponent> pendingRoads = Lists.newArrayList();
/*      */ 
/*      */ 
/*      */     
/*      */     public Start() {}
/*      */ 
/*      */     
/*      */     public Start(BiomeProvider chunkManagerIn, int p_i2104_2_, Random rand, int p_i2104_4_, int p_i2104_5_, List<StructureVillagePieces.PieceWeight> p_i2104_6_, int p_i2104_7_) {
/* 1561 */       super((Start)null, 0, rand, p_i2104_4_, p_i2104_5_);
/* 1562 */       this.worldChunkMngr = chunkManagerIn;
/* 1563 */       this.structureVillageWeightedPieceList = p_i2104_6_;
/* 1564 */       this.terrainType = p_i2104_7_;
/* 1565 */       Biome biome = chunkManagerIn.getBiome(new BlockPos(p_i2104_4_, 0, p_i2104_5_), Biomes.DEFAULT);
/*      */       
/* 1567 */       if (biome instanceof net.minecraft.world.biome.BiomeDesert) {
/*      */         
/* 1569 */         this.structureType = 1;
/*      */       }
/* 1571 */       else if (biome instanceof net.minecraft.world.biome.BiomeSavanna) {
/*      */         
/* 1573 */         this.structureType = 2;
/*      */       }
/* 1575 */       else if (biome instanceof net.minecraft.world.biome.BiomeTaiga) {
/*      */         
/* 1577 */         this.structureType = 3;
/*      */       } 
/*      */       
/* 1580 */       func_189924_a(this.structureType);
/* 1581 */       this.isZombieInfested = (rand.nextInt(50) == 0);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Torch
/*      */     extends Village
/*      */   {
/*      */     public Torch() {}
/*      */ 
/*      */     
/*      */     public Torch(StructureVillagePieces.Start start, int p_i45568_2_, Random rand, StructureBoundingBox p_i45568_4_, EnumFacing facing) {
/* 1593 */       super(start, p_i45568_2_);
/* 1594 */       setCoordBaseMode(facing);
/* 1595 */       this.boundingBox = p_i45568_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public static StructureBoundingBox findPieceBox(StructureVillagePieces.Start start, List<StructureComponent> p_175856_1_, Random rand, int p_175856_3_, int p_175856_4_, int p_175856_5_, EnumFacing facing) {
/* 1600 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175856_3_, p_175856_4_, p_175856_5_, 0, 0, 0, 3, 4, 2, facing);
/* 1601 */       return (StructureComponent.findIntersecting(p_175856_1_, structureboundingbox) != null) ? null : structureboundingbox;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1606 */       if (this.averageGroundLvl < 0) {
/*      */         
/* 1608 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1610 */         if (this.averageGroundLvl < 0)
/*      */         {
/* 1612 */           return true;
/*      */         }
/*      */         
/* 1615 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
/*      */       } 
/*      */       
/* 1618 */       IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
/* 1619 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 3, 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 1620 */       setBlockState(worldIn, iblockstate, 1, 0, 0, structureBoundingBoxIn);
/* 1621 */       setBlockState(worldIn, iblockstate, 1, 1, 0, structureBoundingBoxIn);
/* 1622 */       setBlockState(worldIn, iblockstate, 1, 2, 0, structureBoundingBoxIn);
/* 1623 */       setBlockState(worldIn, Blocks.WOOL.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), 1, 3, 0, structureBoundingBoxIn);
/* 1624 */       func_189926_a(worldIn, EnumFacing.EAST, 2, 3, 0, structureBoundingBoxIn);
/* 1625 */       func_189926_a(worldIn, EnumFacing.NORTH, 1, 3, 1, structureBoundingBoxIn);
/* 1626 */       func_189926_a(worldIn, EnumFacing.WEST, 0, 3, 0, structureBoundingBoxIn);
/* 1627 */       func_189926_a(worldIn, EnumFacing.SOUTH, 1, 3, -1, structureBoundingBoxIn);
/* 1628 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Village
/*      */     extends StructureComponent {
/* 1634 */     protected int averageGroundLvl = -1;
/*      */ 
/*      */     
/*      */     private int villagersSpawned;
/*      */     
/*      */     protected int structureType;
/*      */     
/*      */     protected boolean isZombieInfested;
/*      */ 
/*      */     
/*      */     protected Village(StructureVillagePieces.Start start, int type) {
/* 1645 */       super(type);
/*      */       
/* 1647 */       if (start != null) {
/*      */         
/* 1649 */         this.structureType = start.structureType;
/* 1650 */         this.isZombieInfested = start.isZombieInfested;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1656 */       tagCompound.setInteger("HPos", this.averageGroundLvl);
/* 1657 */       tagCompound.setInteger("VCount", this.villagersSpawned);
/* 1658 */       tagCompound.setByte("Type", (byte)this.structureType);
/* 1659 */       tagCompound.setBoolean("Zombie", this.isZombieInfested);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 1664 */       this.averageGroundLvl = tagCompound.getInteger("HPos");
/* 1665 */       this.villagersSpawned = tagCompound.getInteger("VCount");
/* 1666 */       this.structureType = tagCompound.getByte("Type");
/*      */       
/* 1668 */       if (tagCompound.getBoolean("Desert"))
/*      */       {
/* 1670 */         this.structureType = 1;
/*      */       }
/*      */       
/* 1673 */       this.isZombieInfested = tagCompound.getBoolean("Zombie");
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     protected StructureComponent getNextComponentNN(StructureVillagePieces.Start start, List<StructureComponent> structureComponents, Random rand, int p_74891_4_, int p_74891_5_) {
/* 1679 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/* 1681 */       if (enumfacing != null) {
/*      */         
/* 1683 */         switch (enumfacing) {
/*      */ 
/*      */           
/*      */           default:
/* 1687 */             return StructureVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1690 */             return StructureVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case WEST:
/* 1693 */             return StructureVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */           case EAST:
/*      */             break;
/* 1696 */         }  return StructureVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1701 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     protected StructureComponent getNextComponentPP(StructureVillagePieces.Start start, List<StructureComponent> structureComponents, Random rand, int p_74894_4_, int p_74894_5_) {
/* 1708 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/* 1710 */       if (enumfacing != null) {
/*      */         
/* 1712 */         switch (enumfacing) {
/*      */ 
/*      */           
/*      */           default:
/* 1716 */             return StructureVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1719 */             return StructureVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case WEST:
/* 1722 */             return StructureVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */           case EAST:
/*      */             break;
/* 1725 */         }  return StructureVillagePieces.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1730 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getAverageGroundLevel(World worldIn, StructureBoundingBox structurebb) {
/* 1736 */       int i = 0;
/* 1737 */       int j = 0;
/* 1738 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 1740 */       for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; k++) {
/*      */         
/* 1742 */         for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; l++) {
/*      */           
/* 1744 */           blockpos$mutableblockpos.setPos(l, 64, k);
/*      */           
/* 1746 */           if (structurebb.isVecInside((Vec3i)blockpos$mutableblockpos)) {
/*      */             
/* 1748 */             i += Math.max(worldIn.getTopSolidOrLiquidBlock((BlockPos)blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel() - 1);
/* 1749 */             j++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1754 */       if (j == 0)
/*      */       {
/* 1756 */         return -1;
/*      */       }
/*      */ 
/*      */       
/* 1760 */       return i / j;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected static boolean canVillageGoDeeper(StructureBoundingBox structurebb) {
/* 1766 */       return (structurebb != null && structurebb.minY > 10);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void spawnVillagers(World worldIn, StructureBoundingBox structurebb, int x, int y, int z, int count) {
/* 1771 */       if (this.villagersSpawned < count)
/*      */       {
/* 1773 */         for (int i = this.villagersSpawned; i < count; i++) {
/*      */           
/* 1775 */           int j = getXWithOffset(x + i, z);
/* 1776 */           int k = getYWithOffset(y);
/* 1777 */           int l = getZWithOffset(x + i, z);
/*      */           
/* 1779 */           if (!structurebb.isVecInside((Vec3i)new BlockPos(j, k, l))) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/* 1784 */           this.villagersSpawned++;
/*      */           
/* 1786 */           if (this.isZombieInfested) {
/*      */             
/* 1788 */             EntityZombieVillager entityzombievillager = new EntityZombieVillager(worldIn);
/* 1789 */             entityzombievillager.setLocationAndAngles(j + 0.5D, k, l + 0.5D, 0.0F, 0.0F);
/* 1790 */             entityzombievillager.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityzombievillager)), null);
/* 1791 */             entityzombievillager.func_190733_a(chooseProfession(i, 0));
/* 1792 */             entityzombievillager.enablePersistence();
/* 1793 */             worldIn.spawnEntityInWorld((Entity)entityzombievillager);
/*      */           }
/*      */           else {
/*      */             
/* 1797 */             EntityVillager entityvillager = new EntityVillager(worldIn);
/* 1798 */             entityvillager.setLocationAndAngles(j + 0.5D, k, l + 0.5D, 0.0F, 0.0F);
/* 1799 */             entityvillager.setProfession(chooseProfession(i, worldIn.rand.nextInt(6)));
/* 1800 */             entityvillager.func_190672_a(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), null, false);
/* 1801 */             worldIn.spawnEntityInWorld((Entity)entityvillager);
/*      */           } 
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession) {
/* 1809 */       return currentVillagerProfession;
/*      */     }
/*      */ 
/*      */     
/*      */     protected IBlockState getBiomeSpecificBlockState(IBlockState blockstateIn) {
/* 1814 */       if (this.structureType == 1) {
/*      */         
/* 1816 */         if (blockstateIn.getBlock() == Blocks.LOG || blockstateIn.getBlock() == Blocks.LOG2)
/*      */         {
/* 1818 */           return Blocks.SANDSTONE.getDefaultState();
/*      */         }
/*      */         
/* 1821 */         if (blockstateIn.getBlock() == Blocks.COBBLESTONE)
/*      */         {
/* 1823 */           return Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
/*      */         }
/*      */         
/* 1826 */         if (blockstateIn.getBlock() == Blocks.PLANKS)
/*      */         {
/* 1828 */           return Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
/*      */         }
/*      */         
/* 1831 */         if (blockstateIn.getBlock() == Blocks.OAK_STAIRS)
/*      */         {
/* 1833 */           return Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, blockstateIn.getValue((IProperty)BlockStairs.FACING));
/*      */         }
/*      */         
/* 1836 */         if (blockstateIn.getBlock() == Blocks.STONE_STAIRS)
/*      */         {
/* 1838 */           return Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, blockstateIn.getValue((IProperty)BlockStairs.FACING));
/*      */         }
/*      */         
/* 1841 */         if (blockstateIn.getBlock() == Blocks.GRAVEL)
/*      */         {
/* 1843 */           return Blocks.SANDSTONE.getDefaultState();
/*      */         }
/*      */       }
/* 1846 */       else if (this.structureType == 3) {
/*      */         
/* 1848 */         if (blockstateIn.getBlock() == Blocks.LOG || blockstateIn.getBlock() == Blocks.LOG2)
/*      */         {
/* 1850 */           return Blocks.LOG.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE).withProperty((IProperty)BlockLog.LOG_AXIS, blockstateIn.getValue((IProperty)BlockLog.LOG_AXIS));
/*      */         }
/*      */         
/* 1853 */         if (blockstateIn.getBlock() == Blocks.PLANKS)
/*      */         {
/* 1855 */           return Blocks.PLANKS.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE);
/*      */         }
/*      */         
/* 1858 */         if (blockstateIn.getBlock() == Blocks.OAK_STAIRS)
/*      */         {
/* 1860 */           return Blocks.SPRUCE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, blockstateIn.getValue((IProperty)BlockStairs.FACING));
/*      */         }
/*      */         
/* 1863 */         if (blockstateIn.getBlock() == Blocks.OAK_FENCE)
/*      */         {
/* 1865 */           return Blocks.SPRUCE_FENCE.getDefaultState();
/*      */         }
/*      */       }
/* 1868 */       else if (this.structureType == 2) {
/*      */         
/* 1870 */         if (blockstateIn.getBlock() == Blocks.LOG || blockstateIn.getBlock() == Blocks.LOG2)
/*      */         {
/* 1872 */           return Blocks.LOG2.getDefaultState().withProperty((IProperty)BlockNewLog.VARIANT, (Comparable)BlockPlanks.EnumType.ACACIA).withProperty((IProperty)BlockLog.LOG_AXIS, blockstateIn.getValue((IProperty)BlockLog.LOG_AXIS));
/*      */         }
/*      */         
/* 1875 */         if (blockstateIn.getBlock() == Blocks.PLANKS)
/*      */         {
/* 1877 */           return Blocks.PLANKS.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, (Comparable)BlockPlanks.EnumType.ACACIA);
/*      */         }
/*      */         
/* 1880 */         if (blockstateIn.getBlock() == Blocks.OAK_STAIRS)
/*      */         {
/* 1882 */           return Blocks.ACACIA_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, blockstateIn.getValue((IProperty)BlockStairs.FACING));
/*      */         }
/*      */         
/* 1885 */         if (blockstateIn.getBlock() == Blocks.COBBLESTONE)
/*      */         {
/* 1887 */           return Blocks.LOG2.getDefaultState().withProperty((IProperty)BlockNewLog.VARIANT, (Comparable)BlockPlanks.EnumType.ACACIA).withProperty((IProperty)BlockLog.LOG_AXIS, (Comparable)BlockLog.EnumAxis.Y);
/*      */         }
/*      */         
/* 1890 */         if (blockstateIn.getBlock() == Blocks.OAK_FENCE)
/*      */         {
/* 1892 */           return Blocks.ACACIA_FENCE.getDefaultState();
/*      */         }
/*      */       } 
/*      */       
/* 1896 */       return blockstateIn;
/*      */     }
/*      */ 
/*      */     
/*      */     protected BlockDoor func_189925_i() {
/* 1901 */       switch (this.structureType) {
/*      */         
/*      */         case 2:
/* 1904 */           return Blocks.ACACIA_DOOR;
/*      */         
/*      */         case 3:
/* 1907 */           return Blocks.SPRUCE_DOOR;
/*      */       } 
/*      */       
/* 1910 */       return Blocks.OAK_DOOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void func_189927_a(World p_189927_1_, StructureBoundingBox p_189927_2_, Random p_189927_3_, int p_189927_4_, int p_189927_5_, int p_189927_6_, EnumFacing p_189927_7_) {
/* 1916 */       if (!this.isZombieInfested)
/*      */       {
/* 1918 */         func_189915_a(p_189927_1_, p_189927_2_, p_189927_3_, p_189927_4_, p_189927_5_, p_189927_6_, EnumFacing.NORTH, func_189925_i());
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected void func_189926_a(World p_189926_1_, EnumFacing p_189926_2_, int p_189926_3_, int p_189926_4_, int p_189926_5_, StructureBoundingBox p_189926_6_) {
/* 1924 */       if (!this.isZombieInfested)
/*      */       {
/* 1926 */         setBlockState(p_189926_1_, Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)p_189926_2_), p_189926_3_, p_189926_4_, p_189926_5_, p_189926_6_);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 1932 */       IBlockState iblockstate = getBiomeSpecificBlockState(blockstateIn);
/* 1933 */       super.replaceAirAndLiquidDownwards(worldIn, iblockstate, x, y, z, boundingboxIn);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void func_189924_a(int p_189924_1_) {
/* 1938 */       this.structureType = p_189924_1_;
/*      */     }
/*      */     
/*      */     public Village() {}
/*      */   }
/*      */   
/*      */   public static class Well
/*      */     extends Village
/*      */   {
/*      */     public Well() {}
/*      */     
/*      */     public Well(StructureVillagePieces.Start start, int type, Random rand, int x, int z) {
/* 1950 */       super(start, type);
/* 1951 */       setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));
/*      */       
/* 1953 */       if (getCoordBaseMode().getAxis() == EnumFacing.Axis.Z) {
/*      */         
/* 1955 */         this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
/*      */       }
/*      */       else {
/*      */         
/* 1959 */         this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1965 */       StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, getComponentType());
/* 1966 */       StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, getComponentType());
/* 1967 */       StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/* 1968 */       StructureVillagePieces.generateAndAddRoadPiece((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1973 */       if (this.averageGroundLvl < 0) {
/*      */         
/* 1975 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1977 */         if (this.averageGroundLvl < 0)
/*      */         {
/* 1979 */           return true;
/*      */         }
/*      */         
/* 1982 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 3, 0);
/*      */       } 
/*      */       
/* 1985 */       IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
/* 1986 */       IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
/* 1987 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 4, 12, 4, iblockstate, Blocks.FLOWING_WATER.getDefaultState(), false);
/* 1988 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 12, 2, structureBoundingBoxIn);
/* 1989 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, 12, 2, structureBoundingBoxIn);
/* 1990 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 12, 3, structureBoundingBoxIn);
/* 1991 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, 12, 3, structureBoundingBoxIn);
/* 1992 */       setBlockState(worldIn, iblockstate1, 1, 13, 1, structureBoundingBoxIn);
/* 1993 */       setBlockState(worldIn, iblockstate1, 1, 14, 1, structureBoundingBoxIn);
/* 1994 */       setBlockState(worldIn, iblockstate1, 4, 13, 1, structureBoundingBoxIn);
/* 1995 */       setBlockState(worldIn, iblockstate1, 4, 14, 1, structureBoundingBoxIn);
/* 1996 */       setBlockState(worldIn, iblockstate1, 1, 13, 4, structureBoundingBoxIn);
/* 1997 */       setBlockState(worldIn, iblockstate1, 1, 14, 4, structureBoundingBoxIn);
/* 1998 */       setBlockState(worldIn, iblockstate1, 4, 13, 4, structureBoundingBoxIn);
/* 1999 */       setBlockState(worldIn, iblockstate1, 4, 14, 4, structureBoundingBoxIn);
/* 2000 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 15, 1, 4, 15, 4, iblockstate, iblockstate, false);
/*      */       
/* 2002 */       for (int i = 0; i <= 5; i++) {
/*      */         
/* 2004 */         for (int j = 0; j <= 5; j++) {
/*      */           
/* 2006 */           if (j == 0 || j == 5 || i == 0 || i == 5) {
/*      */             
/* 2008 */             setBlockState(worldIn, iblockstate, j, 11, i, structureBoundingBoxIn);
/* 2009 */             clearCurrentPositionBlocksUpwards(worldIn, j, 12, i, structureBoundingBoxIn);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2014 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class WoodHut
/*      */     extends Village
/*      */   {
/*      */     private boolean isTallHouse;
/*      */     
/*      */     private int tablePosition;
/*      */     
/*      */     public WoodHut() {}
/*      */     
/*      */     public WoodHut(StructureVillagePieces.Start start, int type, Random rand, StructureBoundingBox structurebb, EnumFacing facing) {
/* 2029 */       super(start, type);
/* 2030 */       setCoordBaseMode(facing);
/* 2031 */       this.boundingBox = structurebb;
/* 2032 */       this.isTallHouse = rand.nextBoolean();
/* 2033 */       this.tablePosition = rand.nextInt(3);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 2038 */       super.writeStructureToNBT(tagCompound);
/* 2039 */       tagCompound.setInteger("T", this.tablePosition);
/* 2040 */       tagCompound.setBoolean("C", this.isTallHouse);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 2045 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 2046 */       this.tablePosition = tagCompound.getInteger("T");
/* 2047 */       this.isTallHouse = tagCompound.getBoolean("C");
/*      */     }
/*      */ 
/*      */     
/*      */     public static WoodHut createPiece(StructureVillagePieces.Start start, List<StructureComponent> p_175853_1_, Random rand, int p_175853_3_, int p_175853_4_, int p_175853_5_, EnumFacing facing, int p_175853_7_) {
/* 2052 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175853_3_, p_175853_4_, p_175853_5_, 0, 0, 0, 4, 6, 5, facing);
/* 2053 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175853_1_, structureboundingbox) == null) ? new WoodHut(start, p_175853_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 2058 */       if (this.averageGroundLvl < 0) {
/*      */         
/* 2060 */         this.averageGroundLvl = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 2062 */         if (this.averageGroundLvl < 0)
/*      */         {
/* 2064 */           return true;
/*      */         }
/*      */         
/* 2067 */         this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
/*      */       } 
/*      */       
/* 2070 */       IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
/* 2071 */       IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
/* 2072 */       IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH));
/* 2073 */       IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
/* 2074 */       IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
/* 2075 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 2076 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 3, 0, 4, iblockstate, iblockstate, false);
/* 2077 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 3, Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), false);
/*      */       
/* 2079 */       if (this.isTallHouse) {
/*      */         
/* 2081 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 2, 4, 3, iblockstate3, iblockstate3, false);
/*      */       }
/*      */       else {
/*      */         
/* 2085 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 2, 5, 3, iblockstate3, iblockstate3, false);
/*      */       } 
/*      */       
/* 2088 */       setBlockState(worldIn, iblockstate3, 1, 4, 0, structureBoundingBoxIn);
/* 2089 */       setBlockState(worldIn, iblockstate3, 2, 4, 0, structureBoundingBoxIn);
/* 2090 */       setBlockState(worldIn, iblockstate3, 1, 4, 4, structureBoundingBoxIn);
/* 2091 */       setBlockState(worldIn, iblockstate3, 2, 4, 4, structureBoundingBoxIn);
/* 2092 */       setBlockState(worldIn, iblockstate3, 0, 4, 1, structureBoundingBoxIn);
/* 2093 */       setBlockState(worldIn, iblockstate3, 0, 4, 2, structureBoundingBoxIn);
/* 2094 */       setBlockState(worldIn, iblockstate3, 0, 4, 3, structureBoundingBoxIn);
/* 2095 */       setBlockState(worldIn, iblockstate3, 3, 4, 1, structureBoundingBoxIn);
/* 2096 */       setBlockState(worldIn, iblockstate3, 3, 4, 2, structureBoundingBoxIn);
/* 2097 */       setBlockState(worldIn, iblockstate3, 3, 4, 3, structureBoundingBoxIn);
/* 2098 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0, iblockstate3, iblockstate3, false);
/* 2099 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, iblockstate3, iblockstate3, false);
/* 2100 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 4, iblockstate3, iblockstate3, false);
/* 2101 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 4, 3, 3, 4, iblockstate3, iblockstate3, false);
/* 2102 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, iblockstate1, iblockstate1, false);
/* 2103 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 1, 3, 3, 3, iblockstate1, iblockstate1, false);
/* 2104 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, iblockstate1, iblockstate1, false);
/* 2105 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 3, 4, iblockstate1, iblockstate1, false);
/* 2106 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/* 2107 */       setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 2, structureBoundingBoxIn);
/*      */       
/* 2109 */       if (this.tablePosition > 0) {
/*      */         
/* 2111 */         setBlockState(worldIn, iblockstate4, this.tablePosition, 1, 3, structureBoundingBoxIn);
/* 2112 */         setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), this.tablePosition, 2, 3, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 2115 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
/* 2116 */       setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
/* 2117 */       func_189927_a(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.NORTH);
/*      */       
/* 2119 */       if (getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR) {
/*      */         
/* 2121 */         setBlockState(worldIn, iblockstate2, 1, 0, -1, structureBoundingBoxIn);
/*      */         
/* 2123 */         if (getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
/*      */         {
/* 2125 */           setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 1, -1, -1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 2129 */       for (int i = 0; i < 5; i++) {
/*      */         
/* 2131 */         for (int j = 0; j < 4; j++) {
/*      */           
/* 2133 */           clearCurrentPositionBlocksUpwards(worldIn, j, 6, i, structureBoundingBoxIn);
/* 2134 */           replaceAirAndLiquidDownwards(worldIn, iblockstate, j, -1, i, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 2138 */       spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
/* 2139 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureVillagePieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */