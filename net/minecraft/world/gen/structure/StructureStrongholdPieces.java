/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.BlockButton;
/*      */ import net.minecraft.block.BlockDoor;
/*      */ import net.minecraft.block.BlockEndPortalFrame;
/*      */ import net.minecraft.block.BlockLadder;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStairs;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.block.BlockStoneSlab;
/*      */ import net.minecraft.block.BlockTorch;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.monster.EntitySilverfish;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.Vec3i;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*      */ import net.minecraft.world.storage.loot.LootTableList;
/*      */ 
/*      */ public class StructureStrongholdPieces {
/*   32 */   private static final PieceWeight[] PIECE_WEIGHTS = new PieceWeight[] { new PieceWeight((Class)Straight.class, 40, 0), new PieceWeight((Class)Prison.class, 5, 5), new PieceWeight((Class)LeftTurn.class, 20, 0), new PieceWeight((Class)RightTurn.class, 20, 0), new PieceWeight((Class)RoomCrossing.class, 10, 6), new PieceWeight((Class)StairsStraight.class, 5, 5), new PieceWeight((Class)Stairs.class, 5, 5), new PieceWeight((Class)Crossing.class, 5, 4), new PieceWeight((Class)ChestCorridor.class, 5, 4), new PieceWeight(Library.class, 10, 2)
/*      */       {
/*      */         public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
/*      */         {
/*   36 */           return (super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 4); }
/*      */       }, 
/*   38 */       new PieceWeight(PortalRoom.class, 20, 1)
/*      */       {
/*      */         public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
/*      */         {
/*   42 */           return (super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 5);
/*      */         }
/*      */       } };
/*      */   
/*      */   private static List<PieceWeight> structurePieceList;
/*      */   private static Class<? extends Stronghold> strongComponentType;
/*      */   static int totalWeight;
/*   49 */   private static final Stones STRONGHOLD_STONES = new Stones(null);
/*      */ 
/*      */   
/*      */   public static void registerStrongholdPieces() {
/*   53 */     MapGenStructureIO.registerStructureComponent((Class)ChestCorridor.class, "SHCC");
/*   54 */     MapGenStructureIO.registerStructureComponent((Class)Corridor.class, "SHFC");
/*   55 */     MapGenStructureIO.registerStructureComponent((Class)Crossing.class, "SH5C");
/*   56 */     MapGenStructureIO.registerStructureComponent((Class)LeftTurn.class, "SHLT");
/*   57 */     MapGenStructureIO.registerStructureComponent((Class)Library.class, "SHLi");
/*   58 */     MapGenStructureIO.registerStructureComponent((Class)PortalRoom.class, "SHPR");
/*   59 */     MapGenStructureIO.registerStructureComponent((Class)Prison.class, "SHPH");
/*   60 */     MapGenStructureIO.registerStructureComponent((Class)RightTurn.class, "SHRT");
/*   61 */     MapGenStructureIO.registerStructureComponent((Class)RoomCrossing.class, "SHRC");
/*   62 */     MapGenStructureIO.registerStructureComponent((Class)Stairs.class, "SHSD");
/*   63 */     MapGenStructureIO.registerStructureComponent((Class)Stairs2.class, "SHStart");
/*   64 */     MapGenStructureIO.registerStructureComponent((Class)Straight.class, "SHS");
/*   65 */     MapGenStructureIO.registerStructureComponent((Class)StairsStraight.class, "SHSSD");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void prepareStructurePieces() {
/*   73 */     structurePieceList = Lists.newArrayList(); byte b; int i;
/*      */     PieceWeight[] arrayOfPieceWeight;
/*   75 */     for (i = (arrayOfPieceWeight = PIECE_WEIGHTS).length, b = 0; b < i; ) { PieceWeight structurestrongholdpieces$pieceweight = arrayOfPieceWeight[b];
/*      */       
/*   77 */       structurestrongholdpieces$pieceweight.instancesSpawned = 0;
/*   78 */       structurePieceList.add(structurestrongholdpieces$pieceweight);
/*      */       b++; }
/*      */     
/*   81 */     strongComponentType = null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean canAddStructurePieces() {
/*   86 */     boolean flag = false;
/*   87 */     totalWeight = 0;
/*      */     
/*   89 */     for (PieceWeight structurestrongholdpieces$pieceweight : structurePieceList) {
/*      */       
/*   91 */       if (structurestrongholdpieces$pieceweight.instancesLimit > 0 && structurestrongholdpieces$pieceweight.instancesSpawned < structurestrongholdpieces$pieceweight.instancesLimit)
/*      */       {
/*   93 */         flag = true;
/*      */       }
/*      */       
/*   96 */       totalWeight += structurestrongholdpieces$pieceweight.pieceWeight;
/*      */     } 
/*      */     
/*   99 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Stronghold findAndCreatePieceFactory(Class<? extends Stronghold> clazz, List<StructureComponent> p_175954_1_, Random p_175954_2_, int p_175954_3_, int p_175954_4_, int p_175954_5_, @Nullable EnumFacing p_175954_6_, int p_175954_7_) {
/*  104 */     Stronghold structurestrongholdpieces$stronghold = null;
/*      */     
/*  106 */     if (clazz == Straight.class) {
/*      */       
/*  108 */       structurestrongholdpieces$stronghold = Straight.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  110 */     else if (clazz == Prison.class) {
/*      */       
/*  112 */       structurestrongholdpieces$stronghold = Prison.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  114 */     else if (clazz == LeftTurn.class) {
/*      */       
/*  116 */       structurestrongholdpieces$stronghold = LeftTurn.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  118 */     else if (clazz == RightTurn.class) {
/*      */       
/*  120 */       structurestrongholdpieces$stronghold = RightTurn.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  122 */     else if (clazz == RoomCrossing.class) {
/*      */       
/*  124 */       structurestrongholdpieces$stronghold = RoomCrossing.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  126 */     else if (clazz == StairsStraight.class) {
/*      */       
/*  128 */       structurestrongholdpieces$stronghold = StairsStraight.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  130 */     else if (clazz == Stairs.class) {
/*      */       
/*  132 */       structurestrongholdpieces$stronghold = Stairs.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  134 */     else if (clazz == Crossing.class) {
/*      */       
/*  136 */       structurestrongholdpieces$stronghold = Crossing.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  138 */     else if (clazz == ChestCorridor.class) {
/*      */       
/*  140 */       structurestrongholdpieces$stronghold = ChestCorridor.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  142 */     else if (clazz == Library.class) {
/*      */       
/*  144 */       structurestrongholdpieces$stronghold = Library.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     }
/*  146 */     else if (clazz == PortalRoom.class) {
/*      */       
/*  148 */       structurestrongholdpieces$stronghold = PortalRoom.createPiece(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     } 
/*      */     
/*  151 */     return structurestrongholdpieces$stronghold;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Stronghold generatePieceFromSmallDoor(Stairs2 p_175955_0_, List<StructureComponent> p_175955_1_, Random p_175955_2_, int p_175955_3_, int p_175955_4_, int p_175955_5_, EnumFacing p_175955_6_, int p_175955_7_) {
/*  156 */     if (!canAddStructurePieces())
/*      */     {
/*  158 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  162 */     if (strongComponentType != null) {
/*      */       
/*  164 */       Stronghold structurestrongholdpieces$stronghold = findAndCreatePieceFactory(strongComponentType, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
/*  165 */       strongComponentType = null;
/*      */       
/*  167 */       if (structurestrongholdpieces$stronghold != null)
/*      */       {
/*  169 */         return structurestrongholdpieces$stronghold;
/*      */       }
/*      */     } 
/*      */     
/*  173 */     int j = 0;
/*      */     
/*  175 */     while (j < 5) {
/*      */       
/*  177 */       j++;
/*  178 */       int i = p_175955_2_.nextInt(totalWeight);
/*      */       
/*  180 */       for (PieceWeight structurestrongholdpieces$pieceweight : structurePieceList) {
/*      */         
/*  182 */         i -= structurestrongholdpieces$pieceweight.pieceWeight;
/*      */         
/*  184 */         if (i < 0) {
/*      */           
/*  186 */           if (!structurestrongholdpieces$pieceweight.canSpawnMoreStructuresOfType(p_175955_7_) || structurestrongholdpieces$pieceweight == p_175955_0_.strongholdPieceWeight) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/*  191 */           Stronghold structurestrongholdpieces$stronghold1 = findAndCreatePieceFactory(structurestrongholdpieces$pieceweight.pieceClass, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
/*      */           
/*  193 */           if (structurestrongholdpieces$stronghold1 != null) {
/*      */             
/*  195 */             structurestrongholdpieces$pieceweight.instancesSpawned++;
/*  196 */             p_175955_0_.strongholdPieceWeight = structurestrongholdpieces$pieceweight;
/*      */             
/*  198 */             if (!structurestrongholdpieces$pieceweight.canSpawnMoreStructures())
/*      */             {
/*  200 */               structurePieceList.remove(structurestrongholdpieces$pieceweight);
/*      */             }
/*      */             
/*  203 */             return structurestrongholdpieces$stronghold1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  209 */     StructureBoundingBox structureboundingbox = Corridor.findPieceBox(p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_);
/*      */     
/*  211 */     if (structureboundingbox != null && structureboundingbox.minY > 1)
/*      */     {
/*  213 */       return new Corridor(p_175955_7_, p_175955_2_, structureboundingbox, p_175955_6_);
/*      */     }
/*      */ 
/*      */     
/*  217 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static StructureComponent generateAndAddPiece(Stairs2 p_175953_0_, List<StructureComponent> p_175953_1_, Random p_175953_2_, int p_175953_3_, int p_175953_4_, int p_175953_5_, @Nullable EnumFacing p_175953_6_, int p_175953_7_) {
/*  224 */     if (p_175953_7_ > 50)
/*      */     {
/*  226 */       return null;
/*      */     }
/*  228 */     if (Math.abs(p_175953_3_ - (p_175953_0_.getBoundingBox()).minX) <= 112 && Math.abs(p_175953_5_ - (p_175953_0_.getBoundingBox()).minZ) <= 112) {
/*      */       
/*  230 */       StructureComponent structurecomponent = generatePieceFromSmallDoor(p_175953_0_, p_175953_1_, p_175953_2_, p_175953_3_, p_175953_4_, p_175953_5_, p_175953_6_, p_175953_7_ + 1);
/*      */       
/*  232 */       if (structurecomponent != null) {
/*      */         
/*  234 */         p_175953_1_.add(structurecomponent);
/*  235 */         p_175953_0_.pendingChildren.add(structurecomponent);
/*      */       } 
/*      */       
/*  238 */       return structurecomponent;
/*      */     } 
/*      */ 
/*      */     
/*  242 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ChestCorridor
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean hasMadeChest;
/*      */ 
/*      */     
/*      */     public ChestCorridor() {}
/*      */ 
/*      */     
/*      */     public ChestCorridor(int p_i45582_1_, Random p_i45582_2_, StructureBoundingBox p_i45582_3_, EnumFacing p_i45582_4_) {
/*  256 */       super(p_i45582_1_);
/*  257 */       setCoordBaseMode(p_i45582_4_);
/*  258 */       this.entryDoor = getRandomDoor(p_i45582_2_);
/*  259 */       this.boundingBox = p_i45582_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  264 */       super.writeStructureToNBT(tagCompound);
/*  265 */       tagCompound.setBoolean("Chest", this.hasMadeChest);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  270 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  271 */       this.hasMadeChest = tagCompound.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  276 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public static ChestCorridor createPiece(List<StructureComponent> p_175868_0_, Random p_175868_1_, int p_175868_2_, int p_175868_3_, int p_175868_4_, EnumFacing p_175868_5_, int p_175868_6_) {
/*  281 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175868_2_, p_175868_3_, p_175868_4_, -1, -1, 0, 5, 5, 7, p_175868_5_);
/*  282 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175868_0_, structureboundingbox) == null) ? new ChestCorridor(p_175868_6_, p_175868_1_, structureboundingbox, p_175868_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  287 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  289 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  293 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, true, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  294 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
/*  295 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
/*  296 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 1, 4, Blocks.STONEBRICK.getDefaultState(), Blocks.STONEBRICK.getDefaultState(), false);
/*  297 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 1, structureBoundingBoxIn);
/*  298 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 5, structureBoundingBoxIn);
/*  299 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 2, structureBoundingBoxIn);
/*  300 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 4, structureBoundingBoxIn);
/*      */       
/*  302 */       for (int i = 2; i <= 4; i++)
/*      */       {
/*  304 */         setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 2, 1, i, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  307 */       if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(3, 3), getYWithOffset(2), getZWithOffset(3, 3)))) {
/*      */         
/*  309 */         this.hasMadeChest = true;
/*  310 */         generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 2, 3, LootTableList.CHESTS_STRONGHOLD_CORRIDOR);
/*      */       } 
/*      */       
/*  313 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor
/*      */     extends Stronghold
/*      */   {
/*      */     private int steps;
/*      */ 
/*      */     
/*      */     public Corridor() {}
/*      */ 
/*      */     
/*      */     public Corridor(int p_i45581_1_, Random p_i45581_2_, StructureBoundingBox p_i45581_3_, EnumFacing p_i45581_4_) {
/*  328 */       super(p_i45581_1_);
/*  329 */       setCoordBaseMode(p_i45581_4_);
/*  330 */       this.boundingBox = p_i45581_3_;
/*  331 */       this.steps = (p_i45581_4_ != EnumFacing.NORTH && p_i45581_4_ != EnumFacing.SOUTH) ? p_i45581_3_.getXSize() : p_i45581_3_.getZSize();
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  336 */       super.writeStructureToNBT(tagCompound);
/*  337 */       tagCompound.setInteger("Steps", this.steps);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  342 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  343 */       this.steps = tagCompound.getInteger("Steps");
/*      */     }
/*      */ 
/*      */     
/*      */     public static StructureBoundingBox findPieceBox(List<StructureComponent> p_175869_0_, Random p_175869_1_, int p_175869_2_, int p_175869_3_, int p_175869_4_, EnumFacing p_175869_5_) {
/*  348 */       int i = 3;
/*  349 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, 4, p_175869_5_);
/*  350 */       StructureComponent structurecomponent = StructureComponent.findIntersecting(p_175869_0_, structureboundingbox);
/*      */       
/*  352 */       if (structurecomponent == null)
/*      */       {
/*  354 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  358 */       if ((structurecomponent.getBoundingBox()).minY == structureboundingbox.minY)
/*      */       {
/*  360 */         for (int j = 3; j >= 1; j--) {
/*      */           
/*  362 */           structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j - 1, p_175869_5_);
/*      */           
/*  364 */           if (!structurecomponent.getBoundingBox().intersectsWith(structureboundingbox))
/*      */           {
/*  366 */             return StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j, p_175869_5_);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  371 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  377 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  379 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  383 */       for (int i = 0; i < this.steps; i++) {
/*      */         
/*  385 */         setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 0, 0, i, structureBoundingBoxIn);
/*  386 */         setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 0, i, structureBoundingBoxIn);
/*  387 */         setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 0, i, structureBoundingBoxIn);
/*  388 */         setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 0, i, structureBoundingBoxIn);
/*  389 */         setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 4, 0, i, structureBoundingBoxIn);
/*      */         
/*  391 */         for (int j = 1; j <= 3; j++) {
/*      */           
/*  393 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 0, j, i, structureBoundingBoxIn);
/*  394 */           setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, j, i, structureBoundingBoxIn);
/*  395 */           setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, j, i, structureBoundingBoxIn);
/*  396 */           setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, j, i, structureBoundingBoxIn);
/*  397 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 4, j, i, structureBoundingBoxIn);
/*      */         } 
/*      */         
/*  400 */         setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 0, 4, i, structureBoundingBoxIn);
/*  401 */         setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 4, i, structureBoundingBoxIn);
/*  402 */         setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 4, i, structureBoundingBoxIn);
/*  403 */         setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 4, i, structureBoundingBoxIn);
/*  404 */         setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 4, 4, i, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  407 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Crossing
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean leftLow;
/*      */     
/*      */     private boolean leftHigh;
/*      */     
/*      */     private boolean rightLow;
/*      */     private boolean rightHigh;
/*      */     
/*      */     public Crossing() {}
/*      */     
/*      */     public Crossing(int p_i45580_1_, Random p_i45580_2_, StructureBoundingBox p_i45580_3_, EnumFacing p_i45580_4_) {
/*  425 */       super(p_i45580_1_);
/*  426 */       setCoordBaseMode(p_i45580_4_);
/*  427 */       this.entryDoor = getRandomDoor(p_i45580_2_);
/*  428 */       this.boundingBox = p_i45580_3_;
/*  429 */       this.leftLow = p_i45580_2_.nextBoolean();
/*  430 */       this.leftHigh = p_i45580_2_.nextBoolean();
/*  431 */       this.rightLow = p_i45580_2_.nextBoolean();
/*  432 */       this.rightHigh = (p_i45580_2_.nextInt(3) > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  437 */       super.writeStructureToNBT(tagCompound);
/*  438 */       tagCompound.setBoolean("leftLow", this.leftLow);
/*  439 */       tagCompound.setBoolean("leftHigh", this.leftHigh);
/*  440 */       tagCompound.setBoolean("rightLow", this.rightLow);
/*  441 */       tagCompound.setBoolean("rightHigh", this.rightHigh);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  446 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  447 */       this.leftLow = tagCompound.getBoolean("leftLow");
/*  448 */       this.leftHigh = tagCompound.getBoolean("leftHigh");
/*  449 */       this.rightLow = tagCompound.getBoolean("rightLow");
/*  450 */       this.rightHigh = tagCompound.getBoolean("rightHigh");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  455 */       int i = 3;
/*  456 */       int j = 5;
/*  457 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/*  459 */       if (enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.NORTH) {
/*      */         
/*  461 */         i = 8 - i;
/*  462 */         j = 8 - j;
/*      */       } 
/*      */       
/*  465 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 5, 1);
/*      */       
/*  467 */       if (this.leftLow)
/*      */       {
/*  469 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, i, 1);
/*      */       }
/*      */       
/*  472 */       if (this.leftHigh)
/*      */       {
/*  474 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, j, 7);
/*      */       }
/*      */       
/*  477 */       if (this.rightLow)
/*      */       {
/*  479 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, i, 1);
/*      */       }
/*      */       
/*  482 */       if (this.rightHigh)
/*      */       {
/*  484 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, j, 7);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public static Crossing createPiece(List<StructureComponent> p_175866_0_, Random p_175866_1_, int p_175866_2_, int p_175866_3_, int p_175866_4_, EnumFacing p_175866_5_, int p_175866_6_) {
/*  490 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175866_2_, p_175866_3_, p_175866_4_, -4, -3, 0, 10, 9, 11, p_175866_5_);
/*  491 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175866_0_, structureboundingbox) == null) ? new Crossing(p_175866_6_, p_175866_1_, structureboundingbox, p_175866_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  496 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  498 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  502 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 8, 10, true, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  503 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 4, 3, 0);
/*      */       
/*  505 */       if (this.leftLow)
/*      */       {
/*  507 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 5, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       }
/*      */       
/*  510 */       if (this.rightLow)
/*      */       {
/*  512 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 3, 1, 9, 5, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       }
/*      */       
/*  515 */       if (this.leftHigh)
/*      */       {
/*  517 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 7, 0, 7, 9, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       }
/*      */       
/*  520 */       if (this.rightHigh)
/*      */       {
/*  522 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 7, 9, 7, 9, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       }
/*      */       
/*  525 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 10, 7, 3, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  526 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 2, 1, 8, 2, 6, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  527 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 4, 4, 9, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  528 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 5, 8, 4, 9, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  529 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 4, 7, 3, 4, 9, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  530 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 5, 3, 3, 6, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  531 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 3, 3, 4, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
/*  532 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 6, 3, 4, 6, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
/*  533 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 1, 7, 7, 1, 8, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  534 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 9, 7, 1, 9, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
/*  535 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 7, 7, 2, 7, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
/*  536 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 7, 4, 5, 9, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
/*  537 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 7, 8, 5, 9, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
/*  538 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 7, 7, 5, 9, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), Blocks.DOUBLE_STONE_SLAB.getDefaultState(), false);
/*  539 */       setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.SOUTH), 6, 5, 6, structureBoundingBoxIn);
/*  540 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LeftTurn
/*      */     extends Stronghold
/*      */   {
/*      */     public LeftTurn() {}
/*      */ 
/*      */     
/*      */     public LeftTurn(int p_i45579_1_, Random p_i45579_2_, StructureBoundingBox p_i45579_3_, EnumFacing p_i45579_4_) {
/*  553 */       super(p_i45579_1_);
/*  554 */       setCoordBaseMode(p_i45579_4_);
/*  555 */       this.entryDoor = getRandomDoor(p_i45579_2_);
/*  556 */       this.boundingBox = p_i45579_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  561 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/*  563 */       if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST) {
/*      */         
/*  565 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       }
/*      */       else {
/*      */         
/*  569 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public static LeftTurn createPiece(List<StructureComponent> p_175867_0_, Random p_175867_1_, int p_175867_2_, int p_175867_3_, int p_175867_4_, EnumFacing p_175867_5_, int p_175867_6_) {
/*  575 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175867_2_, p_175867_3_, p_175867_4_, -1, -1, 0, 5, 5, 5, p_175867_5_);
/*  576 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175867_0_, structureboundingbox) == null) ? new LeftTurn(p_175867_6_, p_175867_1_, structureboundingbox, p_175867_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  581 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  583 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  587 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, true, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  588 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
/*  589 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/*  591 */       if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST) {
/*      */         
/*  593 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       }
/*      */       else {
/*      */         
/*  597 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       } 
/*      */       
/*  600 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Library
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean isLargeRoom;
/*      */ 
/*      */     
/*      */     public Library() {}
/*      */ 
/*      */     
/*      */     public Library(int p_i45578_1_, Random p_i45578_2_, StructureBoundingBox p_i45578_3_, EnumFacing p_i45578_4_) {
/*  615 */       super(p_i45578_1_);
/*  616 */       setCoordBaseMode(p_i45578_4_);
/*  617 */       this.entryDoor = getRandomDoor(p_i45578_2_);
/*  618 */       this.boundingBox = p_i45578_3_;
/*  619 */       this.isLargeRoom = (p_i45578_3_.getYSize() > 6);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  624 */       super.writeStructureToNBT(tagCompound);
/*  625 */       tagCompound.setBoolean("Tall", this.isLargeRoom);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  630 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  631 */       this.isLargeRoom = tagCompound.getBoolean("Tall");
/*      */     }
/*      */ 
/*      */     
/*      */     public static Library createPiece(List<StructureComponent> p_175864_0_, Random p_175864_1_, int p_175864_2_, int p_175864_3_, int p_175864_4_, EnumFacing p_175864_5_, int p_175864_6_) {
/*  636 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 11, 15, p_175864_5_);
/*      */       
/*  638 */       if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(p_175864_0_, structureboundingbox) != null) {
/*      */         
/*  640 */         structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 6, 15, p_175864_5_);
/*      */         
/*  642 */         if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(p_175864_0_, structureboundingbox) != null)
/*      */         {
/*  644 */           return null;
/*      */         }
/*      */       } 
/*      */       
/*  648 */       return new Library(p_175864_6_, p_175864_1_, structureboundingbox, p_175864_5_);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  653 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  655 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  659 */       int i = 11;
/*      */       
/*  661 */       if (!this.isLargeRoom)
/*      */       {
/*  663 */         i = 6;
/*      */       }
/*      */       
/*  666 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 13, i - 1, 14, true, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  667 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 4, 1, 0);
/*  668 */       func_189914_a(worldIn, structureBoundingBoxIn, randomIn, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.WEB.getDefaultState(), Blocks.WEB.getDefaultState(), false, 0);
/*  669 */       int j = 1;
/*  670 */       int k = 12;
/*      */       
/*  672 */       for (int l = 1; l <= 13; l++) {
/*      */         
/*  674 */         if ((l - 1) % 4 == 0) {
/*      */           
/*  676 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, l, 1, 4, l, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
/*  677 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, l, 12, 4, l, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
/*  678 */           setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.EAST), 2, 3, l, structureBoundingBoxIn);
/*  679 */           setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.WEST), 11, 3, l, structureBoundingBoxIn);
/*      */           
/*  681 */           if (this.isLargeRoom)
/*      */           {
/*  683 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, l, 1, 9, l, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
/*  684 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 6, l, 12, 9, l, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  689 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, l, 1, 4, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
/*  690 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, l, 12, 4, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
/*      */           
/*  692 */           if (this.isLargeRoom) {
/*      */             
/*  694 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, l, 1, 9, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
/*  695 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 6, l, 12, 9, l, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  700 */       for (int k1 = 3; k1 < 12; k1 += 2) {
/*      */         
/*  702 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, k1, 4, 3, k1, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
/*  703 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, k1, 7, 3, k1, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
/*  704 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, k1, 10, 3, k1, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
/*      */       } 
/*      */       
/*  707 */       if (this.isLargeRoom) {
/*      */         
/*  709 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 5, 13, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
/*  710 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 1, 12, 5, 13, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
/*  711 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 1, 9, 5, 2, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
/*  712 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 12, 9, 5, 13, Blocks.PLANKS.getDefaultState(), Blocks.PLANKS.getDefaultState(), false);
/*  713 */         setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 9, 5, 11, structureBoundingBoxIn);
/*  714 */         setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 8, 5, 11, structureBoundingBoxIn);
/*  715 */         setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 9, 5, 10, structureBoundingBoxIn);
/*  716 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 6, 2, 3, 6, 12, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
/*  717 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 6, 2, 10, 6, 10, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
/*  718 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 2, 9, 6, 2, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
/*  719 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 12, 8, 6, 12, Blocks.OAK_FENCE.getDefaultState(), Blocks.OAK_FENCE.getDefaultState(), false);
/*  720 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 9, 6, 11, structureBoundingBoxIn);
/*  721 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 8, 6, 11, structureBoundingBoxIn);
/*  722 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 9, 6, 10, structureBoundingBoxIn);
/*  723 */         IBlockState iblockstate1 = Blocks.LADDER.getDefaultState().withProperty((IProperty)BlockLadder.FACING, (Comparable)EnumFacing.SOUTH);
/*  724 */         setBlockState(worldIn, iblockstate1, 10, 1, 13, structureBoundingBoxIn);
/*  725 */         setBlockState(worldIn, iblockstate1, 10, 2, 13, structureBoundingBoxIn);
/*  726 */         setBlockState(worldIn, iblockstate1, 10, 3, 13, structureBoundingBoxIn);
/*  727 */         setBlockState(worldIn, iblockstate1, 10, 4, 13, structureBoundingBoxIn);
/*  728 */         setBlockState(worldIn, iblockstate1, 10, 5, 13, structureBoundingBoxIn);
/*  729 */         setBlockState(worldIn, iblockstate1, 10, 6, 13, structureBoundingBoxIn);
/*  730 */         setBlockState(worldIn, iblockstate1, 10, 7, 13, structureBoundingBoxIn);
/*  731 */         int i1 = 7;
/*  732 */         int j1 = 7;
/*  733 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 9, 7, structureBoundingBoxIn);
/*  734 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 7, 9, 7, structureBoundingBoxIn);
/*  735 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 8, 7, structureBoundingBoxIn);
/*  736 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 7, 8, 7, structureBoundingBoxIn);
/*  737 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 7, 7, structureBoundingBoxIn);
/*  738 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 7, 7, 7, structureBoundingBoxIn);
/*  739 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 5, 7, 7, structureBoundingBoxIn);
/*  740 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 8, 7, 7, structureBoundingBoxIn);
/*  741 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 7, 6, structureBoundingBoxIn);
/*  742 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 7, 8, structureBoundingBoxIn);
/*  743 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 7, 7, 6, structureBoundingBoxIn);
/*  744 */         setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 7, 7, 8, structureBoundingBoxIn);
/*  745 */         IBlockState iblockstate = Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.UP);
/*  746 */         setBlockState(worldIn, iblockstate, 5, 8, 7, structureBoundingBoxIn);
/*  747 */         setBlockState(worldIn, iblockstate, 8, 8, 7, structureBoundingBoxIn);
/*  748 */         setBlockState(worldIn, iblockstate, 6, 8, 6, structureBoundingBoxIn);
/*  749 */         setBlockState(worldIn, iblockstate, 6, 8, 8, structureBoundingBoxIn);
/*  750 */         setBlockState(worldIn, iblockstate, 7, 8, 6, structureBoundingBoxIn);
/*  751 */         setBlockState(worldIn, iblockstate, 7, 8, 8, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  754 */       generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 3, 5, LootTableList.CHESTS_STRONGHOLD_LIBRARY);
/*      */       
/*  756 */       if (this.isLargeRoom) {
/*      */         
/*  758 */         setBlockState(worldIn, Blocks.AIR.getDefaultState(), 12, 9, 1, structureBoundingBoxIn);
/*  759 */         generateChest(worldIn, structureBoundingBoxIn, randomIn, 12, 8, 1, LootTableList.CHESTS_STRONGHOLD_LIBRARY);
/*      */       } 
/*      */       
/*  762 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class PieceWeight
/*      */   {
/*      */     public Class<? extends StructureStrongholdPieces.Stronghold> pieceClass;
/*      */     
/*      */     public final int pieceWeight;
/*      */     public int instancesSpawned;
/*      */     public int instancesLimit;
/*      */     
/*      */     public PieceWeight(Class<? extends StructureStrongholdPieces.Stronghold> p_i2076_1_, int p_i2076_2_, int p_i2076_3_) {
/*  776 */       this.pieceClass = p_i2076_1_;
/*  777 */       this.pieceWeight = p_i2076_2_;
/*  778 */       this.instancesLimit = p_i2076_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canSpawnMoreStructuresOfType(int p_75189_1_) {
/*  783 */       return !(this.instancesLimit != 0 && this.instancesSpawned >= this.instancesLimit);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canSpawnMoreStructures() {
/*  788 */       return !(this.instancesLimit != 0 && this.instancesSpawned >= this.instancesLimit);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class PortalRoom
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean hasSpawner;
/*      */ 
/*      */     
/*      */     public PortalRoom() {}
/*      */     
/*      */     public PortalRoom(int p_i45577_1_, Random p_i45577_2_, StructureBoundingBox p_i45577_3_, EnumFacing p_i45577_4_) {
/*  802 */       super(p_i45577_1_);
/*  803 */       setCoordBaseMode(p_i45577_4_);
/*  804 */       this.boundingBox = p_i45577_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  809 */       super.writeStructureToNBT(tagCompound);
/*  810 */       tagCompound.setBoolean("Mob", this.hasSpawner);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  815 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  816 */       this.hasSpawner = tagCompound.getBoolean("Mob");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  821 */       if (componentIn != null)
/*      */       {
/*  823 */         ((StructureStrongholdPieces.Stairs2)componentIn).strongholdPortalRoom = this;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public static PortalRoom createPiece(List<StructureComponent> p_175865_0_, Random p_175865_1_, int p_175865_2_, int p_175865_3_, int p_175865_4_, EnumFacing p_175865_5_, int p_175865_6_) {
/*  829 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175865_2_, p_175865_3_, p_175865_4_, -4, -1, 0, 11, 8, 16, p_175865_5_);
/*  830 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175865_0_, structureboundingbox) == null) ? new PortalRoom(p_175865_6_, p_175865_1_, structureboundingbox, p_175865_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  835 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 7, 15, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  836 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
/*  837 */       int i = 6;
/*  838 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, i, 1, 1, i, 14, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  839 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, i, 1, 9, i, 14, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  840 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 1, 8, i, 2, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  841 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 14, 8, i, 14, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  842 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 2, 1, 4, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  843 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 1, 9, 1, 4, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  844 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 1, 1, 3, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.FLOWING_LAVA.getDefaultState(), false);
/*  845 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 1, 9, 1, 3, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.FLOWING_LAVA.getDefaultState(), false);
/*  846 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 3, 1, 8, 7, 1, 12, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  847 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 6, 1, 11, Blocks.FLOWING_LAVA.getDefaultState(), Blocks.FLOWING_LAVA.getDefaultState(), false);
/*      */       
/*  849 */       for (int j = 3; j < 14; j += 2) {
/*      */         
/*  851 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, j, 0, 4, j, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
/*  852 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 3, j, 10, 4, j, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
/*      */       } 
/*      */       
/*  855 */       for (int i1 = 2; i1 < 9; i1 += 2)
/*      */       {
/*  857 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i1, 3, 15, i1, 4, 15, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
/*      */       }
/*      */       
/*  860 */       IBlockState iblockstate3 = Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH);
/*  861 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 6, 1, 7, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  862 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 2, 6, 6, 2, 7, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  863 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 3, 7, 6, 3, 7, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*      */       
/*  865 */       for (int k = 4; k <= 6; k++) {
/*      */         
/*  867 */         setBlockState(worldIn, iblockstate3, k, 1, 4, structureBoundingBoxIn);
/*  868 */         setBlockState(worldIn, iblockstate3, k, 2, 5, structureBoundingBoxIn);
/*  869 */         setBlockState(worldIn, iblockstate3, k, 3, 6, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  872 */       IBlockState iblockstate4 = Blocks.END_PORTAL_FRAME.getDefaultState().withProperty((IProperty)BlockEndPortalFrame.FACING, (Comparable)EnumFacing.NORTH);
/*  873 */       IBlockState iblockstate = Blocks.END_PORTAL_FRAME.getDefaultState().withProperty((IProperty)BlockEndPortalFrame.FACING, (Comparable)EnumFacing.SOUTH);
/*  874 */       IBlockState iblockstate1 = Blocks.END_PORTAL_FRAME.getDefaultState().withProperty((IProperty)BlockEndPortalFrame.FACING, (Comparable)EnumFacing.EAST);
/*  875 */       IBlockState iblockstate2 = Blocks.END_PORTAL_FRAME.getDefaultState().withProperty((IProperty)BlockEndPortalFrame.FACING, (Comparable)EnumFacing.WEST);
/*  876 */       boolean flag = true;
/*  877 */       boolean[] aboolean = new boolean[12];
/*      */       
/*  879 */       for (int l = 0; l < aboolean.length; l++) {
/*      */         
/*  881 */         aboolean[l] = (randomIn.nextFloat() > 0.9F);
/*  882 */         flag &= aboolean[l];
/*      */       } 
/*      */       
/*  885 */       setBlockState(worldIn, iblockstate4.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[0])), 4, 3, 8, structureBoundingBoxIn);
/*  886 */       setBlockState(worldIn, iblockstate4.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[1])), 5, 3, 8, structureBoundingBoxIn);
/*  887 */       setBlockState(worldIn, iblockstate4.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[2])), 6, 3, 8, structureBoundingBoxIn);
/*  888 */       setBlockState(worldIn, iblockstate.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[3])), 4, 3, 12, structureBoundingBoxIn);
/*  889 */       setBlockState(worldIn, iblockstate.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[4])), 5, 3, 12, structureBoundingBoxIn);
/*  890 */       setBlockState(worldIn, iblockstate.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[5])), 6, 3, 12, structureBoundingBoxIn);
/*  891 */       setBlockState(worldIn, iblockstate1.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[6])), 3, 3, 9, structureBoundingBoxIn);
/*  892 */       setBlockState(worldIn, iblockstate1.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[7])), 3, 3, 10, structureBoundingBoxIn);
/*  893 */       setBlockState(worldIn, iblockstate1.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[8])), 3, 3, 11, structureBoundingBoxIn);
/*  894 */       setBlockState(worldIn, iblockstate2.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[9])), 7, 3, 9, structureBoundingBoxIn);
/*  895 */       setBlockState(worldIn, iblockstate2.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[10])), 7, 3, 10, structureBoundingBoxIn);
/*  896 */       setBlockState(worldIn, iblockstate2.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(aboolean[11])), 7, 3, 11, structureBoundingBoxIn);
/*      */       
/*  898 */       if (flag) {
/*      */         
/*  900 */         IBlockState iblockstate5 = Blocks.END_PORTAL.getDefaultState();
/*  901 */         setBlockState(worldIn, iblockstate5, 4, 3, 9, structureBoundingBoxIn);
/*  902 */         setBlockState(worldIn, iblockstate5, 5, 3, 9, structureBoundingBoxIn);
/*  903 */         setBlockState(worldIn, iblockstate5, 6, 3, 9, structureBoundingBoxIn);
/*  904 */         setBlockState(worldIn, iblockstate5, 4, 3, 10, structureBoundingBoxIn);
/*  905 */         setBlockState(worldIn, iblockstate5, 5, 3, 10, structureBoundingBoxIn);
/*  906 */         setBlockState(worldIn, iblockstate5, 6, 3, 10, structureBoundingBoxIn);
/*  907 */         setBlockState(worldIn, iblockstate5, 4, 3, 11, structureBoundingBoxIn);
/*  908 */         setBlockState(worldIn, iblockstate5, 5, 3, 11, structureBoundingBoxIn);
/*  909 */         setBlockState(worldIn, iblockstate5, 6, 3, 11, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  912 */       if (!this.hasSpawner) {
/*      */         
/*  914 */         i = getYWithOffset(3);
/*  915 */         BlockPos blockpos = new BlockPos(getXWithOffset(5, 6), i, getZWithOffset(5, 6));
/*      */         
/*  917 */         if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/*      */           
/*  919 */           this.hasSpawner = true;
/*  920 */           worldIn.setBlockState(blockpos, Blocks.MOB_SPAWNER.getDefaultState(), 2);
/*  921 */           TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*      */           
/*  923 */           if (tileentity instanceof TileEntityMobSpawner)
/*      */           {
/*  925 */             ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().func_190894_a(EntityList.func_191306_a(EntitySilverfish.class));
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  930 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Prison
/*      */     extends Stronghold
/*      */   {
/*      */     public Prison() {}
/*      */ 
/*      */     
/*      */     public Prison(int p_i45576_1_, Random p_i45576_2_, StructureBoundingBox p_i45576_3_, EnumFacing p_i45576_4_) {
/*  942 */       super(p_i45576_1_);
/*  943 */       setCoordBaseMode(p_i45576_4_);
/*  944 */       this.entryDoor = getRandomDoor(p_i45576_2_);
/*  945 */       this.boundingBox = p_i45576_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  950 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Prison createPiece(List<StructureComponent> p_175860_0_, Random p_175860_1_, int p_175860_2_, int p_175860_3_, int p_175860_4_, EnumFacing p_175860_5_, int p_175860_6_) {
/*  955 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175860_2_, p_175860_3_, p_175860_4_, -1, -1, 0, 9, 5, 11, p_175860_5_);
/*  956 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175860_0_, structureboundingbox) == null) ? new Prison(p_175860_6_, p_175860_1_, structureboundingbox, p_175860_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  961 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/*  963 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  967 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 4, 10, true, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  968 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
/*  969 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 10, 3, 3, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  970 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 1, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  971 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 4, 3, 3, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  972 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 7, 4, 3, 7, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  973 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 4, 3, 9, false, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/*  974 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 4, 4, 3, 6, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
/*  975 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 5, 7, 3, 5, Blocks.IRON_BARS.getDefaultState(), Blocks.IRON_BARS.getDefaultState(), false);
/*  976 */       setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
/*  977 */       setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 4, 3, 8, structureBoundingBoxIn);
/*  978 */       IBlockState iblockstate = Blocks.IRON_DOOR.getDefaultState().withProperty((IProperty)BlockDoor.FACING, (Comparable)EnumFacing.WEST);
/*  979 */       IBlockState iblockstate1 = Blocks.IRON_DOOR.getDefaultState().withProperty((IProperty)BlockDoor.FACING, (Comparable)EnumFacing.WEST).withProperty((IProperty)BlockDoor.HALF, (Comparable)BlockDoor.EnumDoorHalf.UPPER);
/*  980 */       setBlockState(worldIn, iblockstate, 4, 1, 2, structureBoundingBoxIn);
/*  981 */       setBlockState(worldIn, iblockstate1, 4, 2, 2, structureBoundingBoxIn);
/*  982 */       setBlockState(worldIn, iblockstate, 4, 1, 8, structureBoundingBoxIn);
/*  983 */       setBlockState(worldIn, iblockstate1, 4, 2, 8, structureBoundingBoxIn);
/*  984 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class RightTurn
/*      */     extends LeftTurn
/*      */   {
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  993 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/*  995 */       if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST) {
/*      */         
/*  997 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       }
/*      */       else {
/*      */         
/* 1001 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1007 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/* 1009 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1013 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, true, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/* 1014 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
/* 1015 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/* 1017 */       if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.EAST) {
/*      */         
/* 1019 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       }
/*      */       else {
/*      */         
/* 1023 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       } 
/*      */       
/* 1026 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class RoomCrossing
/*      */     extends Stronghold
/*      */   {
/*      */     protected int roomType;
/*      */ 
/*      */     
/*      */     public RoomCrossing() {}
/*      */ 
/*      */     
/*      */     public RoomCrossing(int p_i45575_1_, Random p_i45575_2_, StructureBoundingBox p_i45575_3_, EnumFacing p_i45575_4_) {
/* 1041 */       super(p_i45575_1_);
/* 1042 */       setCoordBaseMode(p_i45575_4_);
/* 1043 */       this.entryDoor = getRandomDoor(p_i45575_2_);
/* 1044 */       this.boundingBox = p_i45575_3_;
/* 1045 */       this.roomType = p_i45575_2_.nextInt(5);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1050 */       super.writeStructureToNBT(tagCompound);
/* 1051 */       tagCompound.setInteger("Type", this.roomType);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 1056 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 1057 */       this.roomType = tagCompound.getInteger("Type");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1062 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 4, 1);
/* 1063 */       getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 4);
/* 1064 */       getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 4);
/*      */     }
/*      */ 
/*      */     
/*      */     public static RoomCrossing createPiece(List<StructureComponent> p_175859_0_, Random p_175859_1_, int p_175859_2_, int p_175859_3_, int p_175859_4_, EnumFacing p_175859_5_, int p_175859_6_) {
/* 1069 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175859_2_, p_175859_3_, p_175859_4_, -4, -1, 0, 11, 7, 11, p_175859_5_);
/* 1070 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175859_0_, structureboundingbox) == null) ? new RoomCrossing(p_175859_6_, p_175859_1_, structureboundingbox, p_175859_5_) : null;
/*      */     }
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*      */       int i1, i, j, k, l;
/*      */       IBlockState iblockstate;
/* 1075 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/* 1077 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1081 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 6, 10, true, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/* 1082 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 4, 1, 0);
/* 1083 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 10, 6, 3, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 1084 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 1085 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 4, 10, 3, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       
/* 1087 */       switch (this.roomType) {
/*      */         
/*      */         case 0:
/* 1090 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
/* 1091 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/* 1092 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
/* 1093 */           setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.WEST), 4, 3, 5, structureBoundingBoxIn);
/* 1094 */           setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.EAST), 6, 3, 5, structureBoundingBoxIn);
/* 1095 */           setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.SOUTH), 5, 3, 4, structureBoundingBoxIn);
/* 1096 */           setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.NORTH), 5, 3, 6, structureBoundingBoxIn);
/* 1097 */           setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
/* 1098 */           setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
/* 1099 */           setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 4, 1, 6, structureBoundingBoxIn);
/* 1100 */           setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 6, 1, 4, structureBoundingBoxIn);
/* 1101 */           setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
/* 1102 */           setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 6, 1, 6, structureBoundingBoxIn);
/* 1103 */           setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
/* 1104 */           setBlockState(worldIn, Blocks.STONE_SLAB.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
/*      */           break;
/*      */         
/*      */         case 1:
/* 1108 */           for (i1 = 0; i1 < 5; i1++) {
/*      */             
/* 1110 */             setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 1, 3 + i1, structureBoundingBoxIn);
/* 1111 */             setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 7, 1, 3 + i1, structureBoundingBoxIn);
/* 1112 */             setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3 + i1, 1, 3, structureBoundingBoxIn);
/* 1113 */             setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3 + i1, 1, 7, structureBoundingBoxIn);
/*      */           } 
/*      */           
/* 1116 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
/* 1117 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/* 1118 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
/* 1119 */           setBlockState(worldIn, Blocks.FLOWING_WATER.getDefaultState(), 5, 4, 5, structureBoundingBoxIn);
/*      */           break;
/*      */         
/*      */         case 2:
/* 1123 */           for (i = 1; i <= 9; i++) {
/*      */             
/* 1125 */             setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 1, 3, i, structureBoundingBoxIn);
/* 1126 */             setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 9, 3, i, structureBoundingBoxIn);
/*      */           } 
/*      */           
/* 1129 */           for (j = 1; j <= 9; j++) {
/*      */             
/* 1131 */             setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), j, 3, 1, structureBoundingBoxIn);
/* 1132 */             setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), j, 3, 9, structureBoundingBoxIn);
/*      */           } 
/*      */           
/* 1135 */           setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
/* 1136 */           setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
/* 1137 */           setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
/* 1138 */           setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 6, structureBoundingBoxIn);
/* 1139 */           setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
/* 1140 */           setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
/* 1141 */           setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, 3, 5, structureBoundingBoxIn);
/* 1142 */           setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, 3, 5, structureBoundingBoxIn);
/*      */           
/* 1144 */           for (k = 1; k <= 3; k++) {
/*      */             
/* 1146 */             setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, k, 4, structureBoundingBoxIn);
/* 1147 */             setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, k, 4, structureBoundingBoxIn);
/* 1148 */             setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 4, k, 6, structureBoundingBoxIn);
/* 1149 */             setBlockState(worldIn, Blocks.COBBLESTONE.getDefaultState(), 6, k, 6, structureBoundingBoxIn);
/*      */           } 
/*      */           
/* 1152 */           setBlockState(worldIn, Blocks.TORCH.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
/*      */           
/* 1154 */           for (l = 2; l <= 8; l++) {
/*      */             
/* 1156 */             setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 2, 3, l, structureBoundingBoxIn);
/* 1157 */             setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 3, 3, l, structureBoundingBoxIn);
/*      */             
/* 1159 */             if (l <= 3 || l >= 7) {
/*      */               
/* 1161 */               setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 4, 3, l, structureBoundingBoxIn);
/* 1162 */               setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 5, 3, l, structureBoundingBoxIn);
/* 1163 */               setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 6, 3, l, structureBoundingBoxIn);
/*      */             } 
/*      */             
/* 1166 */             setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 7, 3, l, structureBoundingBoxIn);
/* 1167 */             setBlockState(worldIn, Blocks.PLANKS.getDefaultState(), 8, 3, l, structureBoundingBoxIn);
/*      */           } 
/*      */           
/* 1170 */           iblockstate = Blocks.LADDER.getDefaultState().withProperty((IProperty)BlockLadder.FACING, (Comparable)EnumFacing.WEST);
/* 1171 */           setBlockState(worldIn, iblockstate, 9, 1, 3, structureBoundingBoxIn);
/* 1172 */           setBlockState(worldIn, iblockstate, 9, 2, 3, structureBoundingBoxIn);
/* 1173 */           setBlockState(worldIn, iblockstate, 9, 3, 3, structureBoundingBoxIn);
/* 1174 */           generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 4, 8, LootTableList.CHESTS_STRONGHOLD_CROSSING);
/*      */           break;
/*      */       } 
/* 1177 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Stairs
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean source;
/*      */ 
/*      */     
/*      */     public Stairs() {}
/*      */ 
/*      */     
/*      */     public Stairs(int p_i2081_1_, Random p_i2081_2_, int p_i2081_3_, int p_i2081_4_) {
/* 1192 */       super(p_i2081_1_);
/* 1193 */       this.source = true;
/* 1194 */       setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(p_i2081_2_));
/* 1195 */       this.entryDoor = StructureStrongholdPieces.Stronghold.Door.OPENING;
/*      */       
/* 1197 */       if (getCoordBaseMode().getAxis() == EnumFacing.Axis.Z) {
/*      */         
/* 1199 */         this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
/*      */       }
/*      */       else {
/*      */         
/* 1203 */         this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public Stairs(int p_i45574_1_, Random p_i45574_2_, StructureBoundingBox p_i45574_3_, EnumFacing p_i45574_4_) {
/* 1209 */       super(p_i45574_1_);
/* 1210 */       this.source = false;
/* 1211 */       setCoordBaseMode(p_i45574_4_);
/* 1212 */       this.entryDoor = getRandomDoor(p_i45574_2_);
/* 1213 */       this.boundingBox = p_i45574_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1218 */       super.writeStructureToNBT(tagCompound);
/* 1219 */       tagCompound.setBoolean("Source", this.source);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 1224 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 1225 */       this.source = tagCompound.getBoolean("Source");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1230 */       if (this.source)
/*      */       {
/* 1232 */         StructureStrongholdPieces.strongComponentType = (Class)StructureStrongholdPieces.Crossing.class;
/*      */       }
/*      */       
/* 1235 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Stairs createPiece(List<StructureComponent> p_175863_0_, Random p_175863_1_, int p_175863_2_, int p_175863_3_, int p_175863_4_, EnumFacing p_175863_5_, int p_175863_6_) {
/* 1240 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175863_2_, p_175863_3_, p_175863_4_, -1, -7, 0, 5, 11, 5, p_175863_5_);
/* 1241 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175863_0_, structureboundingbox) == null) ? new Stairs(p_175863_6_, p_175863_1_, structureboundingbox, p_175863_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1246 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/* 1248 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1252 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 4, true, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/* 1253 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 7, 0);
/* 1254 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
/* 1255 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 6, 1, structureBoundingBoxIn);
/* 1256 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 5, 1, structureBoundingBoxIn);
/* 1257 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 6, 1, structureBoundingBoxIn);
/* 1258 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 5, 2, structureBoundingBoxIn);
/* 1259 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 4, 3, structureBoundingBoxIn);
/* 1260 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 5, 3, structureBoundingBoxIn);
/* 1261 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 4, 3, structureBoundingBoxIn);
/* 1262 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 3, 3, structureBoundingBoxIn);
/* 1263 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 4, 3, structureBoundingBoxIn);
/* 1264 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 3, 2, structureBoundingBoxIn);
/* 1265 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 2, 1, structureBoundingBoxIn);
/* 1266 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 3, 1, structureBoundingBoxIn);
/* 1267 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 2, 1, structureBoundingBoxIn);
/* 1268 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 1, 1, structureBoundingBoxIn);
/* 1269 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 2, 1, structureBoundingBoxIn);
/* 1270 */       setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 1, 2, structureBoundingBoxIn);
/* 1271 */       setBlockState(worldIn, Blocks.STONE_SLAB.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 1, 3, structureBoundingBoxIn);
/* 1272 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Stairs2
/*      */     extends Stairs
/*      */   {
/*      */     public StructureStrongholdPieces.PieceWeight strongholdPieceWeight;
/*      */     public StructureStrongholdPieces.PortalRoom strongholdPortalRoom;
/* 1281 */     public List<StructureComponent> pendingChildren = Lists.newArrayList();
/*      */ 
/*      */ 
/*      */     
/*      */     public Stairs2() {}
/*      */ 
/*      */     
/*      */     public Stairs2(int p_i2083_1_, Random p_i2083_2_, int p_i2083_3_, int p_i2083_4_) {
/* 1289 */       super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class StairsStraight
/*      */     extends Stronghold
/*      */   {
/*      */     public StairsStraight() {}
/*      */ 
/*      */     
/*      */     public StairsStraight(int p_i45572_1_, Random p_i45572_2_, StructureBoundingBox p_i45572_3_, EnumFacing p_i45572_4_) {
/* 1301 */       super(p_i45572_1_);
/* 1302 */       setCoordBaseMode(p_i45572_4_);
/* 1303 */       this.entryDoor = getRandomDoor(p_i45572_2_);
/* 1304 */       this.boundingBox = p_i45572_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1309 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public static StairsStraight createPiece(List<StructureComponent> p_175861_0_, Random p_175861_1_, int p_175861_2_, int p_175861_3_, int p_175861_4_, EnumFacing p_175861_5_, int p_175861_6_) {
/* 1314 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175861_2_, p_175861_3_, p_175861_4_, -1, -7, 0, 5, 11, 8, p_175861_5_);
/* 1315 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175861_0_, structureboundingbox) == null) ? new StairsStraight(p_175861_6_, p_175861_1_, structureboundingbox, p_175861_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1320 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/* 1322 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1326 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 7, true, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/* 1327 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 7, 0);
/* 1328 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
/* 1329 */       IBlockState iblockstate = Blocks.STONE_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.SOUTH);
/*      */       
/* 1331 */       for (int i = 0; i < 6; i++) {
/*      */         
/* 1333 */         setBlockState(worldIn, iblockstate, 1, 6 - i, 1 + i, structureBoundingBoxIn);
/* 1334 */         setBlockState(worldIn, iblockstate, 2, 6 - i, 1 + i, structureBoundingBoxIn);
/* 1335 */         setBlockState(worldIn, iblockstate, 3, 6 - i, 1 + i, structureBoundingBoxIn);
/*      */         
/* 1337 */         if (i < 5) {
/*      */           
/* 1339 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 1, 5 - i, 1 + i, structureBoundingBoxIn);
/* 1340 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 2, 5 - i, 1 + i, structureBoundingBoxIn);
/* 1341 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), 3, 5 - i, 1 + i, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1345 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class Stones
/*      */     extends StructureComponent.BlockSelector
/*      */   {
/*      */     private Stones() {}
/*      */ 
/*      */     
/*      */     public void selectBlocks(Random rand, int x, int y, int z, boolean p_75062_5_) {
/* 1358 */       if (p_75062_5_) {
/*      */         
/* 1360 */         float f = rand.nextFloat();
/*      */         
/* 1362 */         if (f < 0.2F)
/*      */         {
/* 1364 */           this.blockstate = Blocks.STONEBRICK.getStateFromMeta(BlockStoneBrick.CRACKED_META);
/*      */         }
/* 1366 */         else if (f < 0.5F)
/*      */         {
/* 1368 */           this.blockstate = Blocks.STONEBRICK.getStateFromMeta(BlockStoneBrick.MOSSY_META);
/*      */         }
/* 1370 */         else if (f < 0.55F)
/*      */         {
/* 1372 */           this.blockstate = Blocks.MONSTER_EGG.getStateFromMeta(BlockSilverfish.EnumType.STONEBRICK.getMetadata());
/*      */         }
/*      */         else
/*      */         {
/* 1376 */           this.blockstate = Blocks.STONEBRICK.getDefaultState();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1381 */         this.blockstate = Blocks.AIR.getDefaultState();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Straight
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean expandsX;
/*      */     
/*      */     private boolean expandsZ;
/*      */     
/*      */     public Straight() {}
/*      */     
/*      */     public Straight(int p_i45573_1_, Random p_i45573_2_, StructureBoundingBox p_i45573_3_, EnumFacing p_i45573_4_) {
/* 1397 */       super(p_i45573_1_);
/* 1398 */       setCoordBaseMode(p_i45573_4_);
/* 1399 */       this.entryDoor = getRandomDoor(p_i45573_2_);
/* 1400 */       this.boundingBox = p_i45573_3_;
/* 1401 */       this.expandsX = (p_i45573_2_.nextInt(2) == 0);
/* 1402 */       this.expandsZ = (p_i45573_2_.nextInt(2) == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1407 */       super.writeStructureToNBT(tagCompound);
/* 1408 */       tagCompound.setBoolean("Left", this.expandsX);
/* 1409 */       tagCompound.setBoolean("Right", this.expandsZ);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 1414 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 1415 */       this.expandsX = tagCompound.getBoolean("Left");
/* 1416 */       this.expandsZ = tagCompound.getBoolean("Right");
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1421 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       
/* 1423 */       if (this.expandsX)
/*      */       {
/* 1425 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 2);
/*      */       }
/*      */       
/* 1428 */       if (this.expandsZ)
/*      */       {
/* 1430 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 2);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public static Straight createPiece(List<StructureComponent> p_175862_0_, Random p_175862_1_, int p_175862_2_, int p_175862_3_, int p_175862_4_, EnumFacing p_175862_5_, int p_175862_6_) {
/* 1436 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175862_2_, p_175862_3_, p_175862_4_, -1, -1, 0, 5, 5, 7, p_175862_5_);
/* 1437 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175862_0_, structureboundingbox) == null) ? new Straight(p_175862_6_, p_175862_1_, structureboundingbox, p_175862_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1442 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
/*      */       {
/* 1444 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1448 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, true, randomIn, StructureStrongholdPieces.STRONGHOLD_STONES);
/* 1449 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.entryDoor, 1, 1, 0);
/* 1450 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
/* 1451 */       IBlockState iblockstate = Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.EAST);
/* 1452 */       IBlockState iblockstate1 = Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)EnumFacing.WEST);
/* 1453 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 1, iblockstate);
/* 1454 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 1, iblockstate1);
/* 1455 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 5, iblockstate);
/* 1456 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 5, iblockstate1);
/*      */       
/* 1458 */       if (this.expandsX)
/*      */       {
/* 1460 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 2, 0, 3, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       }
/*      */       
/* 1463 */       if (this.expandsZ)
/*      */       {
/* 1465 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 2, 4, 3, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */       }
/*      */       
/* 1468 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Stronghold
/*      */     extends StructureComponent
/*      */   {
/* 1475 */     protected Door entryDoor = Door.OPENING;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Stronghold(int p_i2087_1_) {
/* 1483 */       super(p_i2087_1_);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1488 */       tagCompound.setString("EntryDoor", this.entryDoor.name());
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 1493 */       this.entryDoor = Door.valueOf(tagCompound.getString("EntryDoor"));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void placeDoor(World worldIn, Random p_74990_2_, StructureBoundingBox p_74990_3_, Door p_74990_4_, int p_74990_5_, int p_74990_6_, int p_74990_7_) {
/* 1498 */       switch (p_74990_4_) {
/*      */         
/*      */         case OPENING:
/* 1501 */           fillWithBlocks(worldIn, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */           break;
/*      */         
/*      */         case WOOD_DOOR:
/* 1505 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1506 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1507 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1508 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1509 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1510 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1511 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1512 */           setBlockState(worldIn, Blocks.OAK_DOOR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1513 */           setBlockState(worldIn, Blocks.OAK_DOOR.getDefaultState().withProperty((IProperty)BlockDoor.HALF, (Comparable)BlockDoor.EnumDoorHalf.UPPER), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/*      */           break;
/*      */         
/*      */         case null:
/* 1517 */           setBlockState(worldIn, Blocks.AIR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1518 */           setBlockState(worldIn, Blocks.AIR.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1519 */           setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1520 */           setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1521 */           setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1522 */           setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1523 */           setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1524 */           setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1525 */           setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
/*      */           break;
/*      */         
/*      */         case IRON_DOOR:
/* 1529 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1530 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1531 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1532 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1533 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1534 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1535 */           setBlockState(worldIn, Blocks.STONEBRICK.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1536 */           setBlockState(worldIn, Blocks.IRON_DOOR.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1537 */           setBlockState(worldIn, Blocks.IRON_DOOR.getDefaultState().withProperty((IProperty)BlockDoor.HALF, (Comparable)BlockDoor.EnumDoorHalf.UPPER), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1538 */           setBlockState(worldIn, Blocks.STONE_BUTTON.getDefaultState().withProperty((IProperty)BlockButton.FACING, (Comparable)EnumFacing.NORTH), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
/* 1539 */           setBlockState(worldIn, Blocks.STONE_BUTTON.getDefaultState().withProperty((IProperty)BlockButton.FACING, (Comparable)EnumFacing.SOUTH), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
/*      */           break;
/*      */       } 
/*      */     }
/*      */     
/*      */     protected Door getRandomDoor(Random p_74988_1_) {
/* 1545 */       int i = p_74988_1_.nextInt(5);
/*      */       
/* 1547 */       switch (i) {
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/* 1552 */           return Door.OPENING;
/*      */         
/*      */         case 2:
/* 1555 */           return Door.WOOD_DOOR;
/*      */         
/*      */         case 3:
/* 1558 */           return Door.GRATES;
/*      */         case 4:
/*      */           break;
/* 1561 */       }  return Door.IRON_DOOR;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     protected StructureComponent getNextComponentNormal(StructureStrongholdPieces.Stairs2 p_74986_1_, List<StructureComponent> p_74986_2_, Random p_74986_3_, int p_74986_4_, int p_74986_5_) {
/* 1568 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/* 1570 */       if (enumfacing != null)
/*      */       {
/* 1572 */         switch (enumfacing) {
/*      */           
/*      */           case NORTH:
/* 1575 */             return StructureStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ - 1, enumfacing, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1578 */             return StructureStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.maxZ + 1, enumfacing, getComponentType());
/*      */           
/*      */           case WEST:
/* 1581 */             return StructureStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, enumfacing, getComponentType());
/*      */           
/*      */           case EAST:
/* 1584 */             return StructureStrongholdPieces.generateAndAddPiece(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, enumfacing, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1588 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     protected StructureComponent getNextComponentX(StructureStrongholdPieces.Stairs2 p_74989_1_, List<StructureComponent> p_74989_2_, Random p_74989_3_, int p_74989_4_, int p_74989_5_) {
/* 1594 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/* 1596 */       if (enumfacing != null)
/*      */       {
/* 1598 */         switch (enumfacing) {
/*      */           
/*      */           case NORTH:
/* 1601 */             return StructureStrongholdPieces.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1604 */             return StructureStrongholdPieces.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case WEST:
/* 1607 */             return StructureStrongholdPieces.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */           
/*      */           case EAST:
/* 1610 */             return StructureStrongholdPieces.generateAndAddPiece(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1614 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     protected StructureComponent getNextComponentZ(StructureStrongholdPieces.Stairs2 p_74987_1_, List<StructureComponent> p_74987_2_, Random p_74987_3_, int p_74987_4_, int p_74987_5_) {
/* 1620 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/* 1622 */       if (enumfacing != null)
/*      */       {
/* 1624 */         switch (enumfacing) {
/*      */           
/*      */           case NORTH:
/* 1627 */             return StructureStrongholdPieces.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1630 */             return StructureStrongholdPieces.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case WEST:
/* 1633 */             return StructureStrongholdPieces.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */           
/*      */           case EAST:
/* 1636 */             return StructureStrongholdPieces.generateAndAddPiece(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1640 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected static boolean canStrongholdGoDeeper(StructureBoundingBox p_74991_0_) {
/* 1645 */       return (p_74991_0_ != null && p_74991_0_.minY > 10);
/*      */     }
/*      */     
/*      */     public Stronghold() {}
/*      */     
/* 1650 */     public enum Door { OPENING,
/* 1651 */       WOOD_DOOR,
/* 1652 */       GRATES,
/* 1653 */       IRON_DOOR; }
/*      */   
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureStrongholdPieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */