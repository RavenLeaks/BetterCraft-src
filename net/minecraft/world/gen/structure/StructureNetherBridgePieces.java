/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.BlockStairs;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.monster.EntityBlaze;
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
/*      */ public class StructureNetherBridgePieces {
/*   24 */   private static final PieceWeight[] PRIMARY_COMPONENTS = new PieceWeight[] { new PieceWeight((Class)Straight.class, 30, 0, true), new PieceWeight((Class)Crossing3.class, 10, 4), new PieceWeight((Class)Crossing.class, 10, 4), new PieceWeight((Class)Stairs.class, 10, 3), new PieceWeight((Class)Throne.class, 5, 2), new PieceWeight((Class)Entrance.class, 5, 1) };
/*   25 */   private static final PieceWeight[] SECONDARY_COMPONENTS = new PieceWeight[] { new PieceWeight((Class)Corridor5.class, 25, 0, true), new PieceWeight((Class)Crossing2.class, 15, 5), new PieceWeight((Class)Corridor2.class, 5, 10), new PieceWeight((Class)Corridor.class, 5, 10), new PieceWeight((Class)Corridor3.class, 10, 3, true), new PieceWeight((Class)Corridor4.class, 7, 2), new PieceWeight((Class)NetherStalkRoom.class, 5, 2) };
/*      */ 
/*      */   
/*      */   public static void registerNetherFortressPieces() {
/*   29 */     MapGenStructureIO.registerStructureComponent((Class)Crossing3.class, "NeBCr");
/*   30 */     MapGenStructureIO.registerStructureComponent((Class)End.class, "NeBEF");
/*   31 */     MapGenStructureIO.registerStructureComponent((Class)Straight.class, "NeBS");
/*   32 */     MapGenStructureIO.registerStructureComponent((Class)Corridor3.class, "NeCCS");
/*   33 */     MapGenStructureIO.registerStructureComponent((Class)Corridor4.class, "NeCTB");
/*   34 */     MapGenStructureIO.registerStructureComponent((Class)Entrance.class, "NeCE");
/*   35 */     MapGenStructureIO.registerStructureComponent((Class)Crossing2.class, "NeSCSC");
/*   36 */     MapGenStructureIO.registerStructureComponent((Class)Corridor.class, "NeSCLT");
/*   37 */     MapGenStructureIO.registerStructureComponent((Class)Corridor5.class, "NeSC");
/*   38 */     MapGenStructureIO.registerStructureComponent((Class)Corridor2.class, "NeSCRT");
/*   39 */     MapGenStructureIO.registerStructureComponent((Class)NetherStalkRoom.class, "NeCSR");
/*   40 */     MapGenStructureIO.registerStructureComponent((Class)Throne.class, "NeMT");
/*   41 */     MapGenStructureIO.registerStructureComponent((Class)Crossing.class, "NeRC");
/*   42 */     MapGenStructureIO.registerStructureComponent((Class)Stairs.class, "NeSR");
/*   43 */     MapGenStructureIO.registerStructureComponent((Class)Start.class, "NeStart");
/*      */   }
/*      */ 
/*      */   
/*      */   private static Piece findAndCreateBridgePieceFactory(PieceWeight p_175887_0_, List<StructureComponent> p_175887_1_, Random p_175887_2_, int p_175887_3_, int p_175887_4_, int p_175887_5_, EnumFacing p_175887_6_, int p_175887_7_) {
/*   48 */     Class<? extends Piece> oclass = p_175887_0_.weightClass;
/*   49 */     Piece structurenetherbridgepieces$piece = null;
/*      */     
/*   51 */     if (oclass == Straight.class) {
/*      */       
/*   53 */       structurenetherbridgepieces$piece = Straight.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   55 */     else if (oclass == Crossing3.class) {
/*      */       
/*   57 */       structurenetherbridgepieces$piece = Crossing3.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   59 */     else if (oclass == Crossing.class) {
/*      */       
/*   61 */       structurenetherbridgepieces$piece = Crossing.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   63 */     else if (oclass == Stairs.class) {
/*      */       
/*   65 */       structurenetherbridgepieces$piece = Stairs.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_7_, p_175887_6_);
/*      */     }
/*   67 */     else if (oclass == Throne.class) {
/*      */       
/*   69 */       structurenetherbridgepieces$piece = Throne.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_7_, p_175887_6_);
/*      */     }
/*   71 */     else if (oclass == Entrance.class) {
/*      */       
/*   73 */       structurenetherbridgepieces$piece = Entrance.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   75 */     else if (oclass == Corridor5.class) {
/*      */       
/*   77 */       structurenetherbridgepieces$piece = Corridor5.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   79 */     else if (oclass == Corridor2.class) {
/*      */       
/*   81 */       structurenetherbridgepieces$piece = Corridor2.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   83 */     else if (oclass == Corridor.class) {
/*      */       
/*   85 */       structurenetherbridgepieces$piece = Corridor.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   87 */     else if (oclass == Corridor3.class) {
/*      */       
/*   89 */       structurenetherbridgepieces$piece = Corridor3.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   91 */     else if (oclass == Corridor4.class) {
/*      */       
/*   93 */       structurenetherbridgepieces$piece = Corridor4.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   95 */     else if (oclass == Crossing2.class) {
/*      */       
/*   97 */       structurenetherbridgepieces$piece = Crossing2.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     }
/*   99 */     else if (oclass == NetherStalkRoom.class) {
/*      */       
/*  101 */       structurenetherbridgepieces$piece = NetherStalkRoom.createPiece(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     } 
/*      */     
/*  104 */     return structurenetherbridgepieces$piece;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor
/*      */     extends Piece
/*      */   {
/*      */     private boolean chest;
/*      */ 
/*      */     
/*      */     public Corridor() {}
/*      */     
/*      */     public Corridor(int p_i45615_1_, Random p_i45615_2_, StructureBoundingBox p_i45615_3_, EnumFacing p_i45615_4_) {
/*  117 */       super(p_i45615_1_);
/*  118 */       setCoordBaseMode(p_i45615_4_);
/*  119 */       this.boundingBox = p_i45615_3_;
/*  120 */       this.chest = (p_i45615_2_.nextInt(3) == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  125 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  126 */       this.chest = tagCompound.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  131 */       super.writeStructureToNBT(tagCompound);
/*  132 */       tagCompound.setBoolean("Chest", this.chest);
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  137 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Corridor createPiece(List<StructureComponent> p_175879_0_, Random p_175879_1_, int p_175879_2_, int p_175879_3_, int p_175879_4_, EnumFacing p_175879_5_, int p_175879_6_) {
/*  142 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175879_2_, p_175879_3_, p_175879_4_, -1, 0, 0, 5, 7, 5, p_175879_5_);
/*  143 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175879_0_, structureboundingbox) == null) ? new Corridor(p_175879_6_, p_175879_1_, structureboundingbox, p_175879_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  148 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  149 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  150 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  151 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 1, 4, 4, 1, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  152 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 3, 4, 4, 3, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  153 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  154 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 3, 5, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  155 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 1, 4, 4, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  156 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 4, 3, 4, 4, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  158 */       if (this.chest && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(3, 3), getYWithOffset(2), getZWithOffset(3, 3)))) {
/*      */         
/*  160 */         this.chest = false;
/*  161 */         generateChest(worldIn, structureBoundingBoxIn, randomIn, 3, 2, 3, LootTableList.CHESTS_NETHER_BRIDGE);
/*      */       } 
/*      */       
/*  164 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  166 */       for (int i = 0; i <= 4; i++) {
/*      */         
/*  168 */         for (int j = 0; j <= 4; j++)
/*      */         {
/*  170 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  174 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor2
/*      */     extends Piece
/*      */   {
/*      */     private boolean chest;
/*      */ 
/*      */     
/*      */     public Corridor2() {}
/*      */     
/*      */     public Corridor2(int p_i45613_1_, Random p_i45613_2_, StructureBoundingBox p_i45613_3_, EnumFacing p_i45613_4_) {
/*  188 */       super(p_i45613_1_);
/*  189 */       setCoordBaseMode(p_i45613_4_);
/*  190 */       this.boundingBox = p_i45613_3_;
/*  191 */       this.chest = (p_i45613_2_.nextInt(3) == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  196 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  197 */       this.chest = tagCompound.getBoolean("Chest");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  202 */       super.writeStructureToNBT(tagCompound);
/*  203 */       tagCompound.setBoolean("Chest", this.chest);
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  208 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Corridor2 createPiece(List<StructureComponent> p_175876_0_, Random p_175876_1_, int p_175876_2_, int p_175876_3_, int p_175876_4_, EnumFacing p_175876_5_, int p_175876_6_) {
/*  213 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175876_2_, p_175876_3_, p_175876_4_, -1, 0, 0, 5, 7, 5, p_175876_5_);
/*  214 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175876_0_, structureboundingbox) == null) ? new Corridor2(p_175876_6_, p_175876_1_, structureboundingbox, p_175876_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  219 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  220 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  221 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  222 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 4, 1, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  223 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 3, 0, 4, 3, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  224 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  225 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 4, 4, 5, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  226 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 1, 4, 4, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  227 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 4, 3, 4, 4, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  229 */       if (this.chest && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(1, 3), getYWithOffset(2), getZWithOffset(1, 3)))) {
/*      */         
/*  231 */         this.chest = false;
/*  232 */         generateChest(worldIn, structureBoundingBoxIn, randomIn, 1, 2, 3, LootTableList.CHESTS_NETHER_BRIDGE);
/*      */       } 
/*      */       
/*  235 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  237 */       for (int i = 0; i <= 4; i++) {
/*      */         
/*  239 */         for (int j = 0; j <= 4; j++)
/*      */         {
/*  241 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  245 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor3
/*      */     extends Piece
/*      */   {
/*      */     public Corridor3() {}
/*      */ 
/*      */     
/*      */     public Corridor3(int p_i45619_1_, Random p_i45619_2_, StructureBoundingBox p_i45619_3_, EnumFacing p_i45619_4_) {
/*  257 */       super(p_i45619_1_);
/*  258 */       setCoordBaseMode(p_i45619_4_);
/*  259 */       this.boundingBox = p_i45619_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  264 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Corridor3 createPiece(List<StructureComponent> p_175883_0_, Random p_175883_1_, int p_175883_2_, int p_175883_3_, int p_175883_4_, EnumFacing p_175883_5_, int p_175883_6_) {
/*  269 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175883_2_, p_175883_3_, p_175883_4_, -1, -7, 0, 5, 14, 10, p_175883_5_);
/*  270 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175883_0_, structureboundingbox) == null) ? new Corridor3(p_175883_6_, p_175883_1_, structureboundingbox, p_175883_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  275 */       IBlockState iblockstate = Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.SOUTH);
/*      */       
/*  277 */       for (int i = 0; i <= 9; i++) {
/*      */         
/*  279 */         int j = Math.max(1, 7 - i);
/*  280 */         int k = Math.min(Math.max(j + 5, 14 - i), 13);
/*  281 */         int l = i;
/*  282 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, i, 4, j, i, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  283 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, j + 1, i, 3, k - 1, i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */         
/*  285 */         if (i <= 6) {
/*      */           
/*  287 */           setBlockState(worldIn, iblockstate, 1, j + 1, i, structureBoundingBoxIn);
/*  288 */           setBlockState(worldIn, iblockstate, 2, j + 1, i, structureBoundingBoxIn);
/*  289 */           setBlockState(worldIn, iblockstate, 3, j + 1, i, structureBoundingBoxIn);
/*      */         } 
/*      */         
/*  292 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, k, i, 4, k, i, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  293 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, j + 1, i, 0, k - 1, i, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  294 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, j + 1, i, 4, k - 1, i, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */         
/*  296 */         if ((i & 0x1) == 0) {
/*      */           
/*  298 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, j + 2, i, 0, j + 3, i, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  299 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 4, j + 2, i, 4, j + 3, i, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*      */         } 
/*      */         
/*  302 */         for (int i1 = 0; i1 <= 4; i1++)
/*      */         {
/*  304 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i1, -1, l, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  308 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor4
/*      */     extends Piece
/*      */   {
/*      */     public Corridor4() {}
/*      */ 
/*      */     
/*      */     public Corridor4(int p_i45618_1_, Random p_i45618_2_, StructureBoundingBox p_i45618_3_, EnumFacing p_i45618_4_) {
/*  320 */       super(p_i45618_1_);
/*  321 */       setCoordBaseMode(p_i45618_4_);
/*  322 */       this.boundingBox = p_i45618_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  327 */       int i = 1;
/*  328 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/*  330 */       if (enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.NORTH)
/*      */       {
/*  332 */         i = 5;
/*      */       }
/*      */       
/*  335 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, i, (rand.nextInt(8) > 0));
/*  336 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, i, (rand.nextInt(8) > 0));
/*      */     }
/*      */ 
/*      */     
/*      */     public static Corridor4 createPiece(List<StructureComponent> p_175880_0_, Random p_175880_1_, int p_175880_2_, int p_175880_3_, int p_175880_4_, EnumFacing p_175880_5_, int p_175880_6_) {
/*  341 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175880_2_, p_175880_3_, p_175880_4_, -3, 0, 0, 9, 7, 9, p_175880_5_);
/*  342 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175880_0_, structureboundingbox) == null) ? new Corridor4(p_175880_6_, p_175880_1_, structureboundingbox, p_175880_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  347 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 1, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  348 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 8, 5, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  349 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 8, 6, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  350 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 2, 5, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  351 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 0, 8, 5, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  352 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 1, 4, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  353 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 7, 4, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  354 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 8, 2, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  355 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 2, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  356 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 7, 2, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  357 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 8, 8, 3, 8, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  358 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 6, 0, 3, 7, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  359 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 3, 6, 8, 3, 7, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  360 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 4, 0, 5, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  361 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 3, 4, 8, 5, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  362 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 5, 2, 5, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  363 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 5, 7, 5, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  364 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 5, 1, 5, 5, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  365 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 5, 7, 5, 5, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*      */       
/*  367 */       for (int i = 0; i <= 5; i++) {
/*      */         
/*  369 */         for (int j = 0; j <= 8; j++)
/*      */         {
/*  371 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), j, -1, i, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  375 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Corridor5
/*      */     extends Piece
/*      */   {
/*      */     public Corridor5() {}
/*      */ 
/*      */     
/*      */     public Corridor5(int p_i45614_1_, Random p_i45614_2_, StructureBoundingBox p_i45614_3_, EnumFacing p_i45614_4_) {
/*  387 */       super(p_i45614_1_);
/*  388 */       setCoordBaseMode(p_i45614_4_);
/*  389 */       this.boundingBox = p_i45614_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  394 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Corridor5 createPiece(List<StructureComponent> p_175877_0_, Random p_175877_1_, int p_175877_2_, int p_175877_3_, int p_175877_4_, EnumFacing p_175877_5_, int p_175877_6_) {
/*  399 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175877_2_, p_175877_3_, p_175877_4_, -1, 0, 0, 5, 7, 5, p_175877_5_);
/*  400 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175877_0_, structureboundingbox) == null) ? new Corridor5(p_175877_6_, p_175877_1_, structureboundingbox, p_175877_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  405 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  406 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  407 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  408 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  409 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 4, 1, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  410 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 3, 0, 4, 3, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  411 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 1, 4, 4, 1, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  412 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 3, 4, 4, 3, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  413 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  415 */       for (int i = 0; i <= 4; i++) {
/*      */         
/*  417 */         for (int j = 0; j <= 4; j++)
/*      */         {
/*  419 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  423 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Crossing
/*      */     extends Piece
/*      */   {
/*      */     public Crossing() {}
/*      */ 
/*      */     
/*      */     public Crossing(int p_i45610_1_, Random p_i45610_2_, StructureBoundingBox p_i45610_3_, EnumFacing p_i45610_4_) {
/*  435 */       super(p_i45610_1_);
/*  436 */       setCoordBaseMode(p_i45610_4_);
/*  437 */       this.boundingBox = p_i45610_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  442 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 2, 0, false);
/*  443 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 2, false);
/*  444 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 2, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Crossing createPiece(List<StructureComponent> p_175873_0_, Random p_175873_1_, int p_175873_2_, int p_175873_3_, int p_175873_4_, EnumFacing p_175873_5_, int p_175873_6_) {
/*  449 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175873_2_, p_175873_3_, p_175873_4_, -2, 0, 0, 7, 9, 7, p_175873_5_);
/*  450 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175873_0_, structureboundingbox) == null) ? new Crossing(p_175873_6_, p_175873_1_, structureboundingbox, p_175873_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  455 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  456 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 7, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  457 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 1, 6, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  458 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 6, 1, 6, 6, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  459 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 6, 6, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  460 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 6, 6, 6, 6, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  461 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 6, 1, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  462 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 6, 6, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  463 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 0, 6, 6, 1, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  464 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 5, 6, 6, 6, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  465 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 6, 0, 4, 6, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  466 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 5, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  467 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 6, 6, 4, 6, 6, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  468 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 6, 4, 5, 6, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  469 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 2, 0, 6, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  470 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 0, 5, 4, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  471 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 6, 2, 6, 6, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  472 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 2, 6, 5, 4, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*      */       
/*  474 */       for (int i = 0; i <= 6; i++) {
/*      */         
/*  476 */         for (int j = 0; j <= 6; j++)
/*      */         {
/*  478 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  482 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Crossing2
/*      */     extends Piece
/*      */   {
/*      */     public Crossing2() {}
/*      */ 
/*      */     
/*      */     public Crossing2(int p_i45616_1_, Random p_i45616_2_, StructureBoundingBox p_i45616_3_, EnumFacing p_i45616_4_) {
/*  494 */       super(p_i45616_1_);
/*  495 */       setCoordBaseMode(p_i45616_4_);
/*  496 */       this.boundingBox = p_i45616_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  501 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
/*  502 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*  503 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Crossing2 createPiece(List<StructureComponent> p_175878_0_, Random p_175878_1_, int p_175878_2_, int p_175878_3_, int p_175878_4_, EnumFacing p_175878_5_, int p_175878_6_) {
/*  508 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175878_2_, p_175878_3_, p_175878_4_, -1, 0, 0, 5, 7, 5, p_175878_5_);
/*  509 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175878_0_, structureboundingbox) == null) ? new Crossing2(p_175878_6_, p_175878_1_, structureboundingbox, p_175878_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  514 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  515 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  516 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  517 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  518 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 0, 5, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  519 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 4, 4, 5, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  520 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  522 */       for (int i = 0; i <= 4; i++) {
/*      */         
/*  524 */         for (int j = 0; j <= 4; j++)
/*      */         {
/*  526 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  530 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Crossing3
/*      */     extends Piece
/*      */   {
/*      */     public Crossing3() {}
/*      */ 
/*      */     
/*      */     public Crossing3(int p_i45622_1_, Random p_i45622_2_, StructureBoundingBox p_i45622_3_, EnumFacing p_i45622_4_) {
/*  542 */       super(p_i45622_1_);
/*  543 */       setCoordBaseMode(p_i45622_4_);
/*  544 */       this.boundingBox = p_i45622_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Crossing3(Random p_i2042_1_, int p_i2042_2_, int p_i2042_3_) {
/*  549 */       super(0);
/*  550 */       setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(p_i2042_1_));
/*      */       
/*  552 */       if (getCoordBaseMode().getAxis() == EnumFacing.Axis.Z) {
/*      */         
/*  554 */         this.boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
/*      */       }
/*      */       else {
/*      */         
/*  558 */         this.boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  564 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 8, 3, false);
/*  565 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 3, 8, false);
/*  566 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 3, 8, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Crossing3 createPiece(List<StructureComponent> p_175885_0_, Random p_175885_1_, int p_175885_2_, int p_175885_3_, int p_175885_4_, EnumFacing p_175885_5_, int p_175885_6_) {
/*  571 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175885_2_, p_175885_3_, p_175885_4_, -8, -3, 0, 19, 10, 19, p_175885_5_);
/*  572 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175885_0_, structureboundingbox) == null) ? new Crossing3(p_175885_6_, p_175885_1_, structureboundingbox, p_175885_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  577 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 11, 4, 18, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  578 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 7, 18, 4, 11, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  579 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 7, 18, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  580 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 8, 18, 7, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  581 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 5, 0, 7, 5, 7, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  582 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 5, 11, 7, 5, 18, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  583 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 0, 11, 5, 7, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  584 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 11, 11, 5, 18, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  585 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 7, 7, 5, 7, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  586 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 7, 18, 5, 7, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  587 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 11, 7, 5, 11, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  588 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 11, 18, 5, 11, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  589 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 11, 2, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  590 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 13, 11, 2, 18, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  591 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 0, 11, 1, 3, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  592 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 15, 11, 1, 18, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  594 */       for (int i = 7; i <= 11; i++) {
/*      */         
/*  596 */         for (int j = 0; j <= 2; j++) {
/*      */           
/*  598 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*  599 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, 18 - j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  603 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 7, 5, 2, 11, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  604 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 2, 7, 18, 2, 11, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  605 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 7, 3, 1, 11, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  606 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 0, 7, 18, 1, 11, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  608 */       for (int k = 0; k <= 2; k++) {
/*      */         
/*  610 */         for (int l = 7; l <= 11; l++) {
/*      */           
/*  612 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), k, -1, l, structureBoundingBoxIn);
/*  613 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), 18 - k, -1, l, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  617 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class End
/*      */     extends Piece
/*      */   {
/*      */     private int fillSeed;
/*      */ 
/*      */     
/*      */     public End() {}
/*      */     
/*      */     public End(int p_i45621_1_, Random p_i45621_2_, StructureBoundingBox p_i45621_3_, EnumFacing p_i45621_4_) {
/*  631 */       super(p_i45621_1_);
/*  632 */       setCoordBaseMode(p_i45621_4_);
/*  633 */       this.boundingBox = p_i45621_3_;
/*  634 */       this.fillSeed = p_i45621_2_.nextInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public static End createPiece(List<StructureComponent> p_175884_0_, Random p_175884_1_, int p_175884_2_, int p_175884_3_, int p_175884_4_, EnumFacing p_175884_5_, int p_175884_6_) {
/*  639 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175884_2_, p_175884_3_, p_175884_4_, -1, -3, 0, 5, 10, 8, p_175884_5_);
/*  640 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175884_0_, structureboundingbox) == null) ? new End(p_175884_6_, p_175884_1_, structureboundingbox, p_175884_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  645 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/*  646 */       this.fillSeed = tagCompound.getInteger("Seed");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  651 */       super.writeStructureToNBT(tagCompound);
/*  652 */       tagCompound.setInteger("Seed", this.fillSeed);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  657 */       Random random = new Random(this.fillSeed);
/*      */       
/*  659 */       for (int i = 0; i <= 4; i++) {
/*      */         
/*  661 */         for (int j = 3; j <= 4; j++) {
/*      */           
/*  663 */           int k = random.nextInt(8);
/*  664 */           fillWithBlocks(worldIn, structureBoundingBoxIn, i, j, 0, i, j, k, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */         } 
/*      */       } 
/*      */       
/*  668 */       int l = random.nextInt(8);
/*  669 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 0, 5, l, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  670 */       l = random.nextInt(8);
/*  671 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 0, 4, 5, l, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  673 */       for (int i1 = 0; i1 <= 4; i1++) {
/*      */         
/*  675 */         int k1 = random.nextInt(5);
/*  676 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i1, 2, 0, i1, 2, k1, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       } 
/*      */       
/*  679 */       for (int j1 = 0; j1 <= 4; j1++) {
/*      */         
/*  681 */         for (int l1 = 0; l1 <= 1; l1++) {
/*      */           
/*  683 */           int i2 = random.nextInt(3);
/*  684 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j1, l1, 0, j1, l1, i2, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */         } 
/*      */       } 
/*      */       
/*  688 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Entrance
/*      */     extends Piece
/*      */   {
/*      */     public Entrance() {}
/*      */ 
/*      */     
/*      */     public Entrance(int p_i45617_1_, Random p_i45617_2_, StructureBoundingBox p_i45617_3_, EnumFacing p_i45617_4_) {
/*  700 */       super(p_i45617_1_);
/*  701 */       setCoordBaseMode(p_i45617_4_);
/*  702 */       this.boundingBox = p_i45617_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  707 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 3, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Entrance createPiece(List<StructureComponent> p_175881_0_, Random p_175881_1_, int p_175881_2_, int p_175881_3_, int p_175881_4_, EnumFacing p_175881_5_, int p_175881_6_) {
/*  712 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175881_2_, p_175881_3_, p_175881_4_, -5, -3, 0, 13, 14, 13, p_175881_5_);
/*  713 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175881_0_, structureboundingbox) == null) ? new Entrance(p_175881_6_, p_175881_1_, structureboundingbox, p_175881_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  718 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  719 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 12, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  720 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  721 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  722 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  723 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  724 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  725 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  726 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  727 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  728 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  729 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 8, 0, 7, 8, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*      */       
/*  731 */       for (int i = 1; i <= 11; i += 2) {
/*      */         
/*  733 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 0, i, 11, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  734 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 12, i, 11, 12, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  735 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 10, i, 0, 11, i, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  736 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 10, i, 12, 11, i, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  737 */         setBlockState(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, 13, 0, structureBoundingBoxIn);
/*  738 */         setBlockState(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, 13, 12, structureBoundingBoxIn);
/*  739 */         setBlockState(worldIn, Blocks.NETHER_BRICK.getDefaultState(), 0, 13, i, structureBoundingBoxIn);
/*  740 */         setBlockState(worldIn, Blocks.NETHER_BRICK.getDefaultState(), 12, 13, i, structureBoundingBoxIn);
/*  741 */         setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), i + 1, 13, 0, structureBoundingBoxIn);
/*  742 */         setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), i + 1, 13, 12, structureBoundingBoxIn);
/*  743 */         setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 0, 13, i + 1, structureBoundingBoxIn);
/*  744 */         setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 12, 13, i + 1, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  747 */       setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  748 */       setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 0, 13, 12, structureBoundingBoxIn);
/*  749 */       setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  750 */       setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 12, 13, 0, structureBoundingBoxIn);
/*      */       
/*  752 */       for (int k = 3; k <= 9; k += 2) {
/*      */         
/*  754 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, k, 1, 8, k, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  755 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 7, k, 11, 8, k, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*      */       } 
/*      */       
/*  758 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  759 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  760 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  761 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  762 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  763 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  765 */       for (int l = 4; l <= 8; l++) {
/*      */         
/*  767 */         for (int j = 0; j <= 2; j++) {
/*      */           
/*  769 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), l, -1, j, structureBoundingBoxIn);
/*  770 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), l, -1, 12 - j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  774 */       for (int i1 = 0; i1 <= 2; i1++) {
/*      */         
/*  776 */         for (int j1 = 4; j1 <= 8; j1++) {
/*      */           
/*  778 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i1, -1, j1, structureBoundingBoxIn);
/*  779 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), 12 - i1, -1, j1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  783 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 5, 7, 5, 7, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  784 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 6, 6, 4, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  785 */       setBlockState(worldIn, Blocks.NETHER_BRICK.getDefaultState(), 6, 0, 6, structureBoundingBoxIn);
/*  786 */       IBlockState iblockstate = Blocks.FLOWING_LAVA.getDefaultState();
/*  787 */       setBlockState(worldIn, iblockstate, 6, 5, 6, structureBoundingBoxIn);
/*  788 */       BlockPos blockpos = new BlockPos(getXWithOffset(6, 6), getYWithOffset(5), getZWithOffset(6, 6));
/*      */       
/*  790 */       if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos))
/*      */       {
/*  792 */         worldIn.immediateBlockTick(blockpos, iblockstate, randomIn);
/*      */       }
/*      */       
/*  795 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class NetherStalkRoom
/*      */     extends Piece
/*      */   {
/*      */     public NetherStalkRoom() {}
/*      */ 
/*      */     
/*      */     public NetherStalkRoom(int p_i45612_1_, Random p_i45612_2_, StructureBoundingBox p_i45612_3_, EnumFacing p_i45612_4_) {
/*  807 */       super(p_i45612_1_);
/*  808 */       setCoordBaseMode(p_i45612_4_);
/*  809 */       this.boundingBox = p_i45612_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  814 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 3, true);
/*  815 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 11, true);
/*      */     }
/*      */ 
/*      */     
/*      */     public static NetherStalkRoom createPiece(List<StructureComponent> p_175875_0_, Random p_175875_1_, int p_175875_2_, int p_175875_3_, int p_175875_4_, EnumFacing p_175875_5_, int p_175875_6_) {
/*  820 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175875_2_, p_175875_3_, p_175875_4_, -5, -3, 0, 13, 14, 13, p_175875_5_);
/*  821 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175875_0_, structureboundingbox) == null) ? new NetherStalkRoom(p_175875_6_, p_175875_1_, structureboundingbox, p_175875_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  826 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  827 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 12, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  828 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  829 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  830 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  831 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  832 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  833 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  834 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  835 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  836 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  838 */       for (int i = 1; i <= 11; i += 2) {
/*      */         
/*  840 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 0, i, 11, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  841 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 12, i, 11, 12, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  842 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 10, i, 0, 11, i, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  843 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 10, i, 12, 11, i, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  844 */         setBlockState(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, 13, 0, structureBoundingBoxIn);
/*  845 */         setBlockState(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, 13, 12, structureBoundingBoxIn);
/*  846 */         setBlockState(worldIn, Blocks.NETHER_BRICK.getDefaultState(), 0, 13, i, structureBoundingBoxIn);
/*  847 */         setBlockState(worldIn, Blocks.NETHER_BRICK.getDefaultState(), 12, 13, i, structureBoundingBoxIn);
/*  848 */         setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), i + 1, 13, 0, structureBoundingBoxIn);
/*  849 */         setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), i + 1, 13, 12, structureBoundingBoxIn);
/*  850 */         setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 0, 13, i + 1, structureBoundingBoxIn);
/*  851 */         setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 12, 13, i + 1, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  854 */       setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  855 */       setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 0, 13, 12, structureBoundingBoxIn);
/*  856 */       setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  857 */       setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 12, 13, 0, structureBoundingBoxIn);
/*      */       
/*  859 */       for (int j1 = 3; j1 <= 9; j1 += 2) {
/*      */         
/*  861 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, j1, 1, 8, j1, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  862 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 7, j1, 11, 8, j1, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*      */       } 
/*      */       
/*  865 */       IBlockState iblockstate = Blocks.NETHER_BRICK_STAIRS.getDefaultState().withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.NORTH);
/*      */       
/*  867 */       for (int j = 0; j <= 6; j++) {
/*      */         
/*  869 */         int k = j + 4;
/*      */         
/*  871 */         for (int l = 5; l <= 7; l++)
/*      */         {
/*  873 */           setBlockState(worldIn, iblockstate, l, 5 + j, k, structureBoundingBoxIn);
/*      */         }
/*      */         
/*  876 */         if (k >= 5 && k <= 8) {
/*      */           
/*  878 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, k, 7, j + 4, k, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */         }
/*  880 */         else if (k >= 9 && k <= 10) {
/*      */           
/*  882 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 8, k, 7, j + 4, k, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */         } 
/*      */         
/*  885 */         if (j >= 1)
/*      */         {
/*  887 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6 + j, k, 7, 9 + j, k, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*      */         }
/*      */       } 
/*      */       
/*  891 */       for (int k1 = 5; k1 <= 7; k1++)
/*      */       {
/*  893 */         setBlockState(worldIn, iblockstate, k1, 12, 11, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  896 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 7, 5, 7, 7, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  897 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 6, 7, 7, 7, 7, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*  898 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 13, 12, 7, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/*  899 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 2, 3, 5, 3, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  900 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 9, 3, 5, 10, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  901 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 4, 2, 5, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  902 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 2, 10, 5, 3, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  903 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 9, 10, 5, 10, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  904 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 4, 10, 5, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  905 */       IBlockState iblockstate1 = iblockstate.withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.EAST);
/*  906 */       IBlockState iblockstate2 = iblockstate.withProperty((IProperty)BlockStairs.FACING, (Comparable)EnumFacing.WEST);
/*  907 */       setBlockState(worldIn, iblockstate2, 4, 5, 2, structureBoundingBoxIn);
/*  908 */       setBlockState(worldIn, iblockstate2, 4, 5, 3, structureBoundingBoxIn);
/*  909 */       setBlockState(worldIn, iblockstate2, 4, 5, 9, structureBoundingBoxIn);
/*  910 */       setBlockState(worldIn, iblockstate2, 4, 5, 10, structureBoundingBoxIn);
/*  911 */       setBlockState(worldIn, iblockstate1, 8, 5, 2, structureBoundingBoxIn);
/*  912 */       setBlockState(worldIn, iblockstate1, 8, 5, 3, structureBoundingBoxIn);
/*  913 */       setBlockState(worldIn, iblockstate1, 8, 5, 9, structureBoundingBoxIn);
/*  914 */       setBlockState(worldIn, iblockstate1, 8, 5, 10, structureBoundingBoxIn);
/*  915 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND.getDefaultState(), Blocks.SOUL_SAND.getDefaultState(), false);
/*  916 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND.getDefaultState(), Blocks.SOUL_SAND.getDefaultState(), false);
/*  917 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART.getDefaultState(), Blocks.NETHER_WART.getDefaultState(), false);
/*  918 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART.getDefaultState(), Blocks.NETHER_WART.getDefaultState(), false);
/*  919 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  920 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  921 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  922 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  923 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*  924 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/*  926 */       for (int l1 = 4; l1 <= 8; l1++) {
/*      */         
/*  928 */         for (int i1 = 0; i1 <= 2; i1++) {
/*      */           
/*  930 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), l1, -1, i1, structureBoundingBoxIn);
/*  931 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), l1, -1, 12 - i1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  935 */       for (int i2 = 0; i2 <= 2; i2++) {
/*      */         
/*  937 */         for (int j2 = 4; j2 <= 8; j2++) {
/*      */           
/*  939 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i2, -1, j2, structureBoundingBoxIn);
/*  940 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), 12 - i2, -1, j2, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  944 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static abstract class Piece
/*      */     extends StructureComponent
/*      */   {
/*      */     public Piece() {}
/*      */ 
/*      */     
/*      */     protected Piece(int p_i2054_1_) {
/*  956 */       super(p_i2054_1_);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {}
/*      */ 
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {}
/*      */ 
/*      */     
/*      */     private int getTotalWeight(List<StructureNetherBridgePieces.PieceWeight> p_74960_1_) {
/*  969 */       boolean flag = false;
/*  970 */       int i = 0;
/*      */       
/*  972 */       for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : p_74960_1_) {
/*      */         
/*  974 */         if (structurenetherbridgepieces$pieceweight.maxPlaceCount > 0 && structurenetherbridgepieces$pieceweight.placeCount < structurenetherbridgepieces$pieceweight.maxPlaceCount)
/*      */         {
/*  976 */           flag = true;
/*      */         }
/*      */         
/*  979 */         i += structurenetherbridgepieces$pieceweight.weight;
/*      */       } 
/*      */       
/*  982 */       return flag ? i : -1;
/*      */     }
/*      */ 
/*      */     
/*      */     private Piece generatePiece(StructureNetherBridgePieces.Start p_175871_1_, List<StructureNetherBridgePieces.PieceWeight> p_175871_2_, List<StructureComponent> p_175871_3_, Random p_175871_4_, int p_175871_5_, int p_175871_6_, int p_175871_7_, EnumFacing p_175871_8_, int p_175871_9_) {
/*  987 */       int i = getTotalWeight(p_175871_2_);
/*  988 */       boolean flag = (i > 0 && p_175871_9_ <= 30);
/*  989 */       int j = 0;
/*      */       
/*  991 */       while (j < 5 && flag) {
/*      */         
/*  993 */         j++;
/*  994 */         int k = p_175871_4_.nextInt(i);
/*      */         
/*  996 */         for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : p_175871_2_) {
/*      */           
/*  998 */           k -= structurenetherbridgepieces$pieceweight.weight;
/*      */           
/* 1000 */           if (k < 0) {
/*      */             
/* 1002 */             if (!structurenetherbridgepieces$pieceweight.doPlace(p_175871_9_) || (structurenetherbridgepieces$pieceweight == p_175871_1_.theNetherBridgePieceWeight && !structurenetherbridgepieces$pieceweight.allowInRow)) {
/*      */               break;
/*      */             }
/*      */ 
/*      */             
/* 1007 */             Piece structurenetherbridgepieces$piece = StructureNetherBridgePieces.findAndCreateBridgePieceFactory(structurenetherbridgepieces$pieceweight, p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
/*      */             
/* 1009 */             if (structurenetherbridgepieces$piece != null) {
/*      */               
/* 1011 */               structurenetherbridgepieces$pieceweight.placeCount++;
/* 1012 */               p_175871_1_.theNetherBridgePieceWeight = structurenetherbridgepieces$pieceweight;
/*      */               
/* 1014 */               if (!structurenetherbridgepieces$pieceweight.isValid())
/*      */               {
/* 1016 */                 p_175871_2_.remove(structurenetherbridgepieces$pieceweight);
/*      */               }
/*      */               
/* 1019 */               return structurenetherbridgepieces$piece;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1025 */       return StructureNetherBridgePieces.End.createPiece(p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
/*      */     }
/*      */ 
/*      */     
/*      */     private StructureComponent generateAndAddPiece(StructureNetherBridgePieces.Start p_175870_1_, List<StructureComponent> p_175870_2_, Random p_175870_3_, int p_175870_4_, int p_175870_5_, int p_175870_6_, @Nullable EnumFacing p_175870_7_, int p_175870_8_, boolean p_175870_9_) {
/* 1030 */       if (Math.abs(p_175870_4_ - (p_175870_1_.getBoundingBox()).minX) <= 112 && Math.abs(p_175870_6_ - (p_175870_1_.getBoundingBox()).minZ) <= 112) {
/*      */         
/* 1032 */         List<StructureNetherBridgePieces.PieceWeight> list = p_175870_1_.primaryWeights;
/*      */         
/* 1034 */         if (p_175870_9_)
/*      */         {
/* 1036 */           list = p_175870_1_.secondaryWeights;
/*      */         }
/*      */         
/* 1039 */         StructureComponent structurecomponent = generatePiece(p_175870_1_, list, p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_ + 1);
/*      */         
/* 1041 */         if (structurecomponent != null) {
/*      */           
/* 1043 */           p_175870_2_.add(structurecomponent);
/* 1044 */           p_175870_1_.pendingChildren.add(structurecomponent);
/*      */         } 
/*      */         
/* 1047 */         return structurecomponent;
/*      */       } 
/*      */ 
/*      */       
/* 1051 */       return StructureNetherBridgePieces.End.createPiece(p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     protected StructureComponent getNextComponentNormal(StructureNetherBridgePieces.Start p_74963_1_, List<StructureComponent> p_74963_2_, Random p_74963_3_, int p_74963_4_, int p_74963_5_, boolean p_74963_6_) {
/* 1058 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/* 1060 */       if (enumfacing != null)
/*      */       {
/* 1062 */         switch (enumfacing) {
/*      */           
/*      */           case NORTH:
/* 1065 */             return generateAndAddPiece(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX + p_74963_4_, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ - 1, enumfacing, getComponentType(), p_74963_6_);
/*      */           
/*      */           case SOUTH:
/* 1068 */             return generateAndAddPiece(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX + p_74963_4_, this.boundingBox.minY + p_74963_5_, this.boundingBox.maxZ + 1, enumfacing, getComponentType(), p_74963_6_);
/*      */           
/*      */           case WEST:
/* 1071 */             return generateAndAddPiece(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ + p_74963_4_, enumfacing, getComponentType(), p_74963_6_);
/*      */           
/*      */           case EAST:
/* 1074 */             return generateAndAddPiece(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ + p_74963_4_, enumfacing, getComponentType(), p_74963_6_);
/*      */         } 
/*      */       
/*      */       }
/* 1078 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     protected StructureComponent getNextComponentX(StructureNetherBridgePieces.Start p_74961_1_, List<StructureComponent> p_74961_2_, Random p_74961_3_, int p_74961_4_, int p_74961_5_, boolean p_74961_6_) {
/* 1084 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/* 1086 */       if (enumfacing != null)
/*      */       {
/* 1088 */         switch (enumfacing) {
/*      */           
/*      */           case NORTH:
/* 1091 */             return generateAndAddPiece(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ + p_74961_5_, EnumFacing.WEST, getComponentType(), p_74961_6_);
/*      */           
/*      */           case SOUTH:
/* 1094 */             return generateAndAddPiece(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ + p_74961_5_, EnumFacing.WEST, getComponentType(), p_74961_6_);
/*      */           
/*      */           case WEST:
/* 1097 */             return generateAndAddPiece(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX + p_74961_5_, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType(), p_74961_6_);
/*      */           
/*      */           case EAST:
/* 1100 */             return generateAndAddPiece(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX + p_74961_5_, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType(), p_74961_6_);
/*      */         } 
/*      */       
/*      */       }
/* 1104 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     protected StructureComponent getNextComponentZ(StructureNetherBridgePieces.Start p_74965_1_, List<StructureComponent> p_74965_2_, Random p_74965_3_, int p_74965_4_, int p_74965_5_, boolean p_74965_6_) {
/* 1110 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/* 1112 */       if (enumfacing != null)
/*      */       {
/* 1114 */         switch (enumfacing) {
/*      */           
/*      */           case NORTH:
/* 1117 */             return generateAndAddPiece(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74965_4_, this.boundingBox.minZ + p_74965_5_, EnumFacing.EAST, getComponentType(), p_74965_6_);
/*      */           
/*      */           case SOUTH:
/* 1120 */             return generateAndAddPiece(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74965_4_, this.boundingBox.minZ + p_74965_5_, EnumFacing.EAST, getComponentType(), p_74965_6_);
/*      */           
/*      */           case WEST:
/* 1123 */             return generateAndAddPiece(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.minX + p_74965_5_, this.boundingBox.minY + p_74965_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType(), p_74965_6_);
/*      */           
/*      */           case EAST:
/* 1126 */             return generateAndAddPiece(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.minX + p_74965_5_, this.boundingBox.minY + p_74965_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType(), p_74965_6_);
/*      */         } 
/*      */       
/*      */       }
/* 1130 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     protected static boolean isAboveGround(StructureBoundingBox p_74964_0_) {
/* 1135 */       return (p_74964_0_ != null && p_74964_0_.minY > 10);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class PieceWeight
/*      */   {
/*      */     public Class<? extends StructureNetherBridgePieces.Piece> weightClass;
/*      */     public final int weight;
/*      */     public int placeCount;
/*      */     public int maxPlaceCount;
/*      */     public boolean allowInRow;
/*      */     
/*      */     public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> p_i2055_1_, int p_i2055_2_, int p_i2055_3_, boolean p_i2055_4_) {
/* 1149 */       this.weightClass = p_i2055_1_;
/* 1150 */       this.weight = p_i2055_2_;
/* 1151 */       this.maxPlaceCount = p_i2055_3_;
/* 1152 */       this.allowInRow = p_i2055_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> p_i2056_1_, int p_i2056_2_, int p_i2056_3_) {
/* 1157 */       this(p_i2056_1_, p_i2056_2_, p_i2056_3_, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean doPlace(int p_78822_1_) {
/* 1162 */       return !(this.maxPlaceCount != 0 && this.placeCount >= this.maxPlaceCount);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isValid() {
/* 1167 */       return !(this.maxPlaceCount != 0 && this.placeCount >= this.maxPlaceCount);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Stairs
/*      */     extends Piece
/*      */   {
/*      */     public Stairs() {}
/*      */ 
/*      */     
/*      */     public Stairs(int p_i45609_1_, Random p_i45609_2_, StructureBoundingBox p_i45609_3_, EnumFacing p_i45609_4_) {
/* 1179 */       super(p_i45609_1_);
/* 1180 */       setCoordBaseMode(p_i45609_4_);
/* 1181 */       this.boundingBox = p_i45609_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1186 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 6, 2, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Stairs createPiece(List<StructureComponent> p_175872_0_, Random p_175872_1_, int p_175872_2_, int p_175872_3_, int p_175872_4_, int p_175872_5_, EnumFacing p_175872_6_) {
/* 1191 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175872_2_, p_175872_3_, p_175872_4_, -2, 0, 0, 7, 11, 7, p_175872_6_);
/* 1192 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175872_0_, structureboundingbox) == null) ? new Stairs(p_175872_5_, p_175872_1_, structureboundingbox, p_175872_6_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1197 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1198 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 10, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 1199 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 1, 8, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1200 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 6, 8, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1201 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 8, 6, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1202 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 1, 6, 8, 6, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1203 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 6, 5, 8, 6, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1204 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 2, 0, 5, 4, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1205 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 2, 6, 5, 2, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1206 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 4, 6, 5, 4, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1207 */       setBlockState(worldIn, Blocks.NETHER_BRICK.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/* 1208 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 5, 4, 3, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1209 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 5, 3, 4, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1210 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 5, 2, 5, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1211 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 1, 6, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1212 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, 1, 5, 7, 4, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1213 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 8, 2, 6, 8, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 1214 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 6, 0, 4, 8, 0, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1215 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 5, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*      */       
/* 1217 */       for (int i = 0; i <= 6; i++) {
/*      */         
/* 1219 */         for (int j = 0; j <= 6; j++)
/*      */         {
/* 1221 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1225 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Start
/*      */     extends Crossing3 {
/*      */     public StructureNetherBridgePieces.PieceWeight theNetherBridgePieceWeight;
/*      */     public List<StructureNetherBridgePieces.PieceWeight> primaryWeights;
/*      */     public List<StructureNetherBridgePieces.PieceWeight> secondaryWeights;
/* 1234 */     public List<StructureComponent> pendingChildren = Lists.newArrayList();
/*      */ 
/*      */ 
/*      */     
/*      */     public Start() {}
/*      */ 
/*      */     
/*      */     public Start(Random p_i2059_1_, int p_i2059_2_, int p_i2059_3_) {
/* 1242 */       super(p_i2059_1_, p_i2059_2_, p_i2059_3_);
/* 1243 */       this.primaryWeights = Lists.newArrayList(); byte b; int i;
/*      */       StructureNetherBridgePieces.PieceWeight[] arrayOfPieceWeight;
/* 1245 */       for (i = (arrayOfPieceWeight = StructureNetherBridgePieces.PRIMARY_COMPONENTS).length, b = 0; b < i; ) { StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight = arrayOfPieceWeight[b];
/*      */         
/* 1247 */         structurenetherbridgepieces$pieceweight.placeCount = 0;
/* 1248 */         this.primaryWeights.add(structurenetherbridgepieces$pieceweight);
/*      */         b++; }
/*      */       
/* 1251 */       this.secondaryWeights = Lists.newArrayList();
/*      */       
/* 1253 */       for (i = (arrayOfPieceWeight = StructureNetherBridgePieces.SECONDARY_COMPONENTS).length, b = 0; b < i; ) { StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight1 = arrayOfPieceWeight[b];
/*      */         
/* 1255 */         structurenetherbridgepieces$pieceweight1.placeCount = 0;
/* 1256 */         this.secondaryWeights.add(structurenetherbridgepieces$pieceweight1);
/*      */         b++; }
/*      */     
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Straight
/*      */     extends Piece
/*      */   {
/*      */     public Straight() {}
/*      */     
/*      */     public Straight(int p_i45620_1_, Random p_i45620_2_, StructureBoundingBox p_i45620_3_, EnumFacing p_i45620_4_) {
/* 1269 */       super(p_i45620_1_);
/* 1270 */       setCoordBaseMode(p_i45620_4_);
/* 1271 */       this.boundingBox = p_i45620_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1276 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 3, false);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Straight createPiece(List<StructureComponent> p_175882_0_, Random p_175882_1_, int p_175882_2_, int p_175882_3_, int p_175882_4_, EnumFacing p_175882_5_, int p_175882_6_) {
/* 1281 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175882_2_, p_175882_3_, p_175882_4_, -1, -3, 0, 5, 10, 19, p_175882_5_);
/* 1282 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175882_0_, structureboundingbox) == null) ? new Straight(p_175882_6_, p_175882_1_, structureboundingbox, p_175882_5_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1287 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1288 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 0, 3, 7, 18, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 1289 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1290 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1291 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1292 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1293 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1294 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/*      */       
/* 1296 */       for (int i = 0; i <= 4; i++) {
/*      */         
/* 1298 */         for (int j = 0; j <= 2; j++) {
/*      */           
/* 1300 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/* 1301 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, 18 - j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1305 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 4, 1, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1306 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 4, 0, 4, 4, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1307 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 14, 0, 4, 14, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1308 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 17, 0, 4, 17, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1309 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 4, 1, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1310 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 4, 4, 4, 4, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1311 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 14, 4, 4, 14, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1312 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 17, 4, 4, 17, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1313 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class Throne
/*      */     extends Piece
/*      */   {
/*      */     private boolean hasSpawner;
/*      */ 
/*      */     
/*      */     public Throne() {}
/*      */     
/*      */     public Throne(int p_i45611_1_, Random p_i45611_2_, StructureBoundingBox p_i45611_3_, EnumFacing p_i45611_4_) {
/* 1327 */       super(p_i45611_1_);
/* 1328 */       setCoordBaseMode(p_i45611_4_);
/* 1329 */       this.boundingBox = p_i45611_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/* 1334 */       super.readStructureFromNBT(tagCompound, p_143011_2_);
/* 1335 */       this.hasSpawner = tagCompound.getBoolean("Mob");
/*      */     }
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1340 */       super.writeStructureToNBT(tagCompound);
/* 1341 */       tagCompound.setBoolean("Mob", this.hasSpawner);
/*      */     }
/*      */ 
/*      */     
/*      */     public static Throne createPiece(List<StructureComponent> p_175874_0_, Random p_175874_1_, int p_175874_2_, int p_175874_3_, int p_175874_4_, int p_175874_5_, EnumFacing p_175874_6_) {
/* 1346 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175874_2_, p_175874_3_, p_175874_4_, -2, 0, 0, 7, 8, 9, p_175874_6_);
/* 1347 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175874_0_, structureboundingbox) == null) ? new Throne(p_175874_5_, p_175874_1_, structureboundingbox, p_175874_6_) : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1352 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 7, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
/* 1353 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 1, 7, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1354 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 1, 5, 2, 7, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1355 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 2, 5, 3, 7, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1356 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 3, 5, 4, 7, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1357 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 1, 4, 2, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1358 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 5, 4, 2, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1359 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 2, 1, 5, 3, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1360 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 2, 5, 5, 3, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1361 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 3, 0, 5, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1362 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 3, 6, 5, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1363 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 8, 5, 5, 8, Blocks.NETHER_BRICK.getDefaultState(), Blocks.NETHER_BRICK.getDefaultState(), false);
/* 1364 */       setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 1, 6, 3, structureBoundingBoxIn);
/* 1365 */       setBlockState(worldIn, Blocks.NETHER_BRICK_FENCE.getDefaultState(), 5, 6, 3, structureBoundingBoxIn);
/* 1366 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 3, 0, 6, 8, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1367 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 6, 3, 6, 6, 8, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1368 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, 8, 5, 7, 8, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/* 1369 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 8, 8, 4, 8, 8, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), false);
/*      */       
/* 1371 */       if (!this.hasSpawner) {
/*      */         
/* 1373 */         BlockPos blockpos = new BlockPos(getXWithOffset(3, 5), getYWithOffset(5), getZWithOffset(3, 5));
/*      */         
/* 1375 */         if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/*      */           
/* 1377 */           this.hasSpawner = true;
/* 1378 */           worldIn.setBlockState(blockpos, Blocks.MOB_SPAWNER.getDefaultState(), 2);
/* 1379 */           TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*      */           
/* 1381 */           if (tileentity instanceof TileEntityMobSpawner)
/*      */           {
/* 1383 */             ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().func_190894_a(EntityList.func_191306_a(EntityBlaze.class));
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1388 */       for (int i = 0; i <= 6; i++) {
/*      */         
/* 1390 */         for (int j = 0; j <= 6; j++)
/*      */         {
/* 1392 */           replaceAirAndLiquidDownwards(worldIn, Blocks.NETHER_BRICK.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1396 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureNetherBridgePieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */