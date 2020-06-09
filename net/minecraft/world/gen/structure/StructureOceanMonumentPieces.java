/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.BlockPrismarine;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.monster.EntityElderGuardian;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.Vec3i;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*      */ 
/*      */ public class StructureOceanMonumentPieces
/*      */ {
/*      */   public static void registerOceanMonumentPieces() {
/*   24 */     MapGenStructureIO.registerStructureComponent((Class)MonumentBuilding.class, "OMB");
/*   25 */     MapGenStructureIO.registerStructureComponent((Class)MonumentCoreRoom.class, "OMCR");
/*   26 */     MapGenStructureIO.registerStructureComponent((Class)DoubleXRoom.class, "OMDXR");
/*   27 */     MapGenStructureIO.registerStructureComponent((Class)DoubleXYRoom.class, "OMDXYR");
/*   28 */     MapGenStructureIO.registerStructureComponent((Class)DoubleYRoom.class, "OMDYR");
/*   29 */     MapGenStructureIO.registerStructureComponent((Class)DoubleYZRoom.class, "OMDYZR");
/*   30 */     MapGenStructureIO.registerStructureComponent((Class)DoubleZRoom.class, "OMDZR");
/*   31 */     MapGenStructureIO.registerStructureComponent((Class)EntryRoom.class, "OMEntry");
/*   32 */     MapGenStructureIO.registerStructureComponent((Class)Penthouse.class, "OMPenthouse");
/*   33 */     MapGenStructureIO.registerStructureComponent((Class)SimpleRoom.class, "OMSimple");
/*   34 */     MapGenStructureIO.registerStructureComponent((Class)SimpleTopRoom.class, "OMSimpleT");
/*      */   }
/*      */ 
/*      */   
/*      */   public static class DoubleXRoom
/*      */     extends Piece
/*      */   {
/*      */     public DoubleXRoom() {}
/*      */ 
/*      */     
/*      */     public DoubleXRoom(EnumFacing p_i45597_1_, StructureOceanMonumentPieces.RoomDefinition p_i45597_2_, Random p_i45597_3_) {
/*   45 */       super(1, p_i45597_1_, p_i45597_2_, 2, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*   50 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.roomDefinition.connections[EnumFacing.EAST.getIndex()];
/*   51 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.roomDefinition;
/*      */       
/*   53 */       if (this.roomDefinition.index / 25 > 0) {
/*      */         
/*   55 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 8, 0, structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
/*   56 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 0, 0, structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.DOWN.getIndex()]);
/*      */       } 
/*      */       
/*   59 */       if (structureoceanmonumentpieces$roomdefinition1.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/*   61 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 6, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/*   64 */       if (structureoceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/*   66 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 8, 4, 1, 14, 4, 6, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/*   69 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*   70 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 3, 0, 15, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*   71 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 15, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*   72 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 7, 14, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*   73 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*   74 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 2, 0, 15, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*   75 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 15, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*   76 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 7, 14, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*   77 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*   78 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 1, 0, 15, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*   79 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 15, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*   80 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 7, 14, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*   81 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 10, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*   82 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 0, 9, 2, 3, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*   83 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 3, 0, 10, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*   84 */       setBlockState(worldIn, SEA_LANTERN, 6, 2, 3, structureBoundingBoxIn);
/*   85 */       setBlockState(worldIn, SEA_LANTERN, 9, 2, 3, structureBoundingBoxIn);
/*      */       
/*   87 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */       {
/*   89 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/*   92 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */       {
/*   94 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, false);
/*      */       }
/*      */       
/*   97 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.WEST.getIndex()])
/*      */       {
/*   99 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/*      */       
/*  102 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */       {
/*  104 */         generateWaterBox(worldIn, structureBoundingBoxIn, 11, 1, 0, 12, 2, 0, false);
/*      */       }
/*      */       
/*  107 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */       {
/*  109 */         generateWaterBox(worldIn, structureBoundingBoxIn, 11, 1, 7, 12, 2, 7, false);
/*      */       }
/*      */       
/*  112 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.EAST.getIndex()])
/*      */       {
/*  114 */         generateWaterBox(worldIn, structureBoundingBoxIn, 15, 1, 3, 15, 2, 4, false);
/*      */       }
/*      */       
/*  117 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class DoubleXYRoom
/*      */     extends Piece
/*      */   {
/*      */     public DoubleXYRoom() {}
/*      */ 
/*      */     
/*      */     public DoubleXYRoom(EnumFacing p_i45596_1_, StructureOceanMonumentPieces.RoomDefinition p_i45596_2_, Random p_i45596_3_) {
/*  129 */       super(1, p_i45596_1_, p_i45596_2_, 2, 2, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  134 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.roomDefinition.connections[EnumFacing.EAST.getIndex()];
/*  135 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.roomDefinition;
/*  136 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition2 = structureoceanmonumentpieces$roomdefinition1.connections[EnumFacing.UP.getIndex()];
/*  137 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition3 = structureoceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()];
/*      */       
/*  139 */       if (this.roomDefinition.index / 25 > 0) {
/*      */         
/*  141 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 8, 0, structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
/*  142 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 0, 0, structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.DOWN.getIndex()]);
/*      */       } 
/*      */       
/*  145 */       if (structureoceanmonumentpieces$roomdefinition2.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/*  147 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 1, 8, 1, 7, 8, 6, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/*  150 */       if (structureoceanmonumentpieces$roomdefinition3.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/*  152 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 8, 8, 1, 14, 8, 6, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/*  155 */       for (int i = 1; i <= 7; i++) {
/*      */         
/*  157 */         IBlockState iblockstate = BRICKS_PRISMARINE;
/*      */         
/*  159 */         if (i == 2 || i == 6)
/*      */         {
/*  161 */           iblockstate = ROUGH_PRISMARINE;
/*      */         }
/*      */         
/*  164 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i, 0, 0, i, 7, iblockstate, iblockstate, false);
/*  165 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 15, i, 0, 15, i, 7, iblockstate, iblockstate, false);
/*  166 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 0, 15, i, 0, iblockstate, iblockstate, false);
/*  167 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 7, 14, i, 7, iblockstate, iblockstate, false);
/*      */       } 
/*      */       
/*  170 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 3, 2, 7, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  171 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 4, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  172 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 5, 4, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  173 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 1, 3, 13, 7, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  174 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 1, 2, 12, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  175 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 1, 5, 12, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  176 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 3, 5, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  177 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 3, 10, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  178 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 7, 2, 10, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  179 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 2, 5, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  180 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 2, 10, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  181 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 5, 5, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  182 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 5, 10, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  183 */       setBlockState(worldIn, BRICKS_PRISMARINE, 6, 6, 2, structureBoundingBoxIn);
/*  184 */       setBlockState(worldIn, BRICKS_PRISMARINE, 9, 6, 2, structureBoundingBoxIn);
/*  185 */       setBlockState(worldIn, BRICKS_PRISMARINE, 6, 6, 5, structureBoundingBoxIn);
/*  186 */       setBlockState(worldIn, BRICKS_PRISMARINE, 9, 6, 5, structureBoundingBoxIn);
/*  187 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 3, 6, 4, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  188 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 4, 3, 10, 4, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  189 */       setBlockState(worldIn, SEA_LANTERN, 5, 4, 2, structureBoundingBoxIn);
/*  190 */       setBlockState(worldIn, SEA_LANTERN, 5, 4, 5, structureBoundingBoxIn);
/*  191 */       setBlockState(worldIn, SEA_LANTERN, 10, 4, 2, structureBoundingBoxIn);
/*  192 */       setBlockState(worldIn, SEA_LANTERN, 10, 4, 5, structureBoundingBoxIn);
/*      */       
/*  194 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */       {
/*  196 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/*  199 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */       {
/*  201 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, false);
/*      */       }
/*      */       
/*  204 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.WEST.getIndex()])
/*      */       {
/*  206 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/*      */       
/*  209 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */       {
/*  211 */         generateWaterBox(worldIn, structureBoundingBoxIn, 11, 1, 0, 12, 2, 0, false);
/*      */       }
/*      */       
/*  214 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */       {
/*  216 */         generateWaterBox(worldIn, structureBoundingBoxIn, 11, 1, 7, 12, 2, 7, false);
/*      */       }
/*      */       
/*  219 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.EAST.getIndex()])
/*      */       {
/*  221 */         generateWaterBox(worldIn, structureBoundingBoxIn, 15, 1, 3, 15, 2, 4, false);
/*      */       }
/*      */       
/*  224 */       if (structureoceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */       {
/*  226 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 5, 0, 4, 6, 0, false);
/*      */       }
/*      */       
/*  229 */       if (structureoceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */       {
/*  231 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 5, 7, 4, 6, 7, false);
/*      */       }
/*      */       
/*  234 */       if (structureoceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.WEST.getIndex()])
/*      */       {
/*  236 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0, 5, 3, 0, 6, 4, false);
/*      */       }
/*      */       
/*  239 */       if (structureoceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */       {
/*  241 */         generateWaterBox(worldIn, structureBoundingBoxIn, 11, 5, 0, 12, 6, 0, false);
/*      */       }
/*      */       
/*  244 */       if (structureoceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */       {
/*  246 */         generateWaterBox(worldIn, structureBoundingBoxIn, 11, 5, 7, 12, 6, 7, false);
/*      */       }
/*      */       
/*  249 */       if (structureoceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.EAST.getIndex()])
/*      */       {
/*  251 */         generateWaterBox(worldIn, structureBoundingBoxIn, 15, 5, 3, 15, 6, 4, false);
/*      */       }
/*      */       
/*  254 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class DoubleYRoom
/*      */     extends Piece
/*      */   {
/*      */     public DoubleYRoom() {}
/*      */ 
/*      */     
/*      */     public DoubleYRoom(EnumFacing p_i45595_1_, StructureOceanMonumentPieces.RoomDefinition p_i45595_2_, Random p_i45595_3_) {
/*  266 */       super(1, p_i45595_1_, p_i45595_2_, 1, 2, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  271 */       if (this.roomDefinition.index / 25 > 0)
/*      */       {
/*  273 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 0, 0, this.roomDefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
/*      */       }
/*      */       
/*  276 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.roomDefinition.connections[EnumFacing.UP.getIndex()];
/*      */       
/*  278 */       if (structureoceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/*  280 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 1, 8, 1, 6, 8, 6, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/*  283 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 0, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  284 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 0, 7, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  285 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 0, 6, 4, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  286 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 7, 6, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  287 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 4, 1, 2, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  288 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 2, 1, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  289 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 1, 5, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  290 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 4, 2, 6, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  291 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 4, 5, 2, 4, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  292 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 5, 1, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  293 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 5, 5, 4, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  294 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 4, 5, 6, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  295 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.roomDefinition;
/*      */       
/*  297 */       for (int i = 1; i <= 5; i += 4) {
/*      */         
/*  299 */         int j = 0;
/*      */         
/*  301 */         if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.SOUTH.getIndex()]) {
/*      */           
/*  303 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 2, i, j, 2, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  304 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, i, j, 5, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  305 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, i + 2, j, 4, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         else {
/*      */           
/*  309 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i, j, 7, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  310 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i + 1, j, 7, i + 1, j, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         } 
/*      */         
/*  313 */         j = 7;
/*      */         
/*  315 */         if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.NORTH.getIndex()]) {
/*      */           
/*  317 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 2, i, j, 2, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  318 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, i, j, 5, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  319 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, i + 2, j, 4, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         else {
/*      */           
/*  323 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i, j, 7, i + 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  324 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i + 1, j, 7, i + 1, j, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         } 
/*      */         
/*  327 */         int k = 0;
/*      */         
/*  329 */         if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.WEST.getIndex()]) {
/*      */           
/*  331 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 2, k, i + 2, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  332 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 5, k, i + 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  333 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i + 2, 3, k, i + 2, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         else {
/*      */           
/*  337 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 0, k, i + 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  338 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i + 1, 0, k, i + 1, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         } 
/*      */         
/*  341 */         k = 7;
/*      */         
/*  343 */         if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.EAST.getIndex()]) {
/*      */           
/*  345 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 2, k, i + 2, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  346 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 5, k, i + 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  347 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i + 2, 3, k, i + 2, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         else {
/*      */           
/*  351 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i, 0, k, i + 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  352 */           fillWithBlocks(worldIn, structureBoundingBoxIn, k, i + 1, 0, k, i + 1, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         } 
/*      */         
/*  355 */         structureoceanmonumentpieces$roomdefinition1 = structureoceanmonumentpieces$roomdefinition;
/*      */       } 
/*      */       
/*  358 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class DoubleYZRoom
/*      */     extends Piece
/*      */   {
/*      */     public DoubleYZRoom() {}
/*      */ 
/*      */     
/*      */     public DoubleYZRoom(EnumFacing p_i45594_1_, StructureOceanMonumentPieces.RoomDefinition p_i45594_2_, Random p_i45594_3_) {
/*  370 */       super(1, p_i45594_1_, p_i45594_2_, 1, 2, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  375 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.roomDefinition.connections[EnumFacing.NORTH.getIndex()];
/*  376 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.roomDefinition;
/*  377 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition2 = structureoceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()];
/*  378 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition3 = structureoceanmonumentpieces$roomdefinition1.connections[EnumFacing.UP.getIndex()];
/*      */       
/*  380 */       if (this.roomDefinition.index / 25 > 0) {
/*      */         
/*  382 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 0, 8, structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
/*  383 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 0, 0, structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.DOWN.getIndex()]);
/*      */       } 
/*      */       
/*  386 */       if (structureoceanmonumentpieces$roomdefinition3.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/*  388 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 1, 8, 1, 6, 8, 7, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/*  391 */       if (structureoceanmonumentpieces$roomdefinition2.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/*  393 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 1, 8, 8, 6, 8, 14, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/*  396 */       for (int i = 1; i <= 7; i++) {
/*      */         
/*  398 */         IBlockState iblockstate = BRICKS_PRISMARINE;
/*      */         
/*  400 */         if (i == 2 || i == 6)
/*      */         {
/*  402 */           iblockstate = ROUGH_PRISMARINE;
/*      */         }
/*      */         
/*  405 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, i, 0, 0, i, 15, iblockstate, iblockstate, false);
/*  406 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, i, 0, 7, i, 15, iblockstate, iblockstate, false);
/*  407 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 0, 6, i, 0, iblockstate, iblockstate, false);
/*  408 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, i, 15, 6, i, 15, iblockstate, iblockstate, false);
/*      */       } 
/*      */       
/*  411 */       for (int j = 1; j <= 7; j++) {
/*      */         
/*  413 */         IBlockState iblockstate1 = DARK_PRISMARINE;
/*      */         
/*  415 */         if (j == 2 || j == 6)
/*      */         {
/*  417 */           iblockstate1 = SEA_LANTERN;
/*      */         }
/*      */         
/*  420 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, j, 7, 4, j, 8, iblockstate1, iblockstate1, false);
/*      */       } 
/*      */       
/*  423 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */       {
/*  425 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/*  428 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.EAST.getIndex()])
/*      */       {
/*  430 */         generateWaterBox(worldIn, structureBoundingBoxIn, 7, 1, 3, 7, 2, 4, false);
/*      */       }
/*      */       
/*  433 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.WEST.getIndex()])
/*      */       {
/*  435 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/*      */       
/*  438 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */       {
/*  440 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 15, 4, 2, 15, false);
/*      */       }
/*      */       
/*  443 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.WEST.getIndex()])
/*      */       {
/*  445 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0, 1, 11, 0, 2, 12, false);
/*      */       }
/*      */       
/*  448 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.EAST.getIndex()])
/*      */       {
/*  450 */         generateWaterBox(worldIn, structureBoundingBoxIn, 7, 1, 11, 7, 2, 12, false);
/*      */       }
/*      */       
/*  453 */       if (structureoceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */       {
/*  455 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 5, 0, 4, 6, 0, false);
/*      */       }
/*      */       
/*  458 */       if (structureoceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.EAST.getIndex()]) {
/*      */         
/*  460 */         generateWaterBox(worldIn, structureBoundingBoxIn, 7, 5, 3, 7, 6, 4, false);
/*  461 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 2, 6, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  462 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 2, 6, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  463 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 5, 6, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */       } 
/*      */       
/*  466 */       if (structureoceanmonumentpieces$roomdefinition3.hasOpening[EnumFacing.WEST.getIndex()]) {
/*      */         
/*  468 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0, 5, 3, 0, 6, 4, false);
/*  469 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 2, 2, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  470 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 2, 1, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  471 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 1, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */       } 
/*      */       
/*  474 */       if (structureoceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */       {
/*  476 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 5, 15, 4, 6, 15, false);
/*      */       }
/*      */       
/*  479 */       if (structureoceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.WEST.getIndex()]) {
/*      */         
/*  481 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0, 5, 11, 0, 6, 12, false);
/*  482 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 10, 2, 4, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  483 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 10, 1, 3, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  484 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 13, 1, 3, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */       } 
/*      */       
/*  487 */       if (structureoceanmonumentpieces$roomdefinition2.hasOpening[EnumFacing.EAST.getIndex()]) {
/*      */         
/*  489 */         generateWaterBox(worldIn, structureBoundingBoxIn, 7, 5, 11, 7, 6, 12, false);
/*  490 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 4, 10, 6, 4, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  491 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 10, 6, 3, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  492 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 13, 6, 3, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */       } 
/*      */       
/*  495 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class DoubleZRoom
/*      */     extends Piece
/*      */   {
/*      */     public DoubleZRoom() {}
/*      */ 
/*      */     
/*      */     public DoubleZRoom(EnumFacing p_i45593_1_, StructureOceanMonumentPieces.RoomDefinition p_i45593_2_, Random p_i45593_3_) {
/*  507 */       super(1, p_i45593_1_, p_i45593_2_, 1, 1, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  512 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = this.roomDefinition.connections[EnumFacing.NORTH.getIndex()];
/*  513 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = this.roomDefinition;
/*      */       
/*  515 */       if (this.roomDefinition.index / 25 > 0) {
/*      */         
/*  517 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 0, 8, structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
/*  518 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 0, 0, structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.DOWN.getIndex()]);
/*      */       } 
/*      */       
/*  521 */       if (structureoceanmonumentpieces$roomdefinition1.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/*  523 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 1, 4, 1, 6, 4, 7, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/*  526 */       if (structureoceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/*  528 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 1, 4, 8, 6, 4, 14, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/*  531 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 0, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  532 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 7, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  533 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 7, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  534 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 15, 6, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  535 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*  536 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 7, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*  537 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*  538 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 15, 6, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*  539 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  540 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 0, 7, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  541 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 7, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  542 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 15, 6, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  543 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 1, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  544 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 1, 6, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  545 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 1, 1, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  546 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 1, 6, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  547 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 13, 1, 1, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  548 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 13, 6, 1, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  549 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 13, 1, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  550 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 13, 6, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  551 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  552 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 6, 5, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  553 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 9, 2, 3, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  554 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 9, 5, 3, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  555 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 6, 4, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  556 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 9, 4, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  557 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 7, 2, 2, 8, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  558 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 7, 5, 2, 8, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  559 */       setBlockState(worldIn, SEA_LANTERN, 2, 2, 5, structureBoundingBoxIn);
/*  560 */       setBlockState(worldIn, SEA_LANTERN, 5, 2, 5, structureBoundingBoxIn);
/*  561 */       setBlockState(worldIn, SEA_LANTERN, 2, 2, 10, structureBoundingBoxIn);
/*  562 */       setBlockState(worldIn, SEA_LANTERN, 5, 2, 10, structureBoundingBoxIn);
/*  563 */       setBlockState(worldIn, BRICKS_PRISMARINE, 2, 3, 5, structureBoundingBoxIn);
/*  564 */       setBlockState(worldIn, BRICKS_PRISMARINE, 5, 3, 5, structureBoundingBoxIn);
/*  565 */       setBlockState(worldIn, BRICKS_PRISMARINE, 2, 3, 10, structureBoundingBoxIn);
/*  566 */       setBlockState(worldIn, BRICKS_PRISMARINE, 5, 3, 10, structureBoundingBoxIn);
/*      */       
/*  568 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */       {
/*  570 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/*  573 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.EAST.getIndex()])
/*      */       {
/*  575 */         generateWaterBox(worldIn, structureBoundingBoxIn, 7, 1, 3, 7, 2, 4, false);
/*      */       }
/*      */       
/*  578 */       if (structureoceanmonumentpieces$roomdefinition1.hasOpening[EnumFacing.WEST.getIndex()])
/*      */       {
/*  580 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, false);
/*      */       }
/*      */       
/*  583 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */       {
/*  585 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 15, 4, 2, 15, false);
/*      */       }
/*      */       
/*  588 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.WEST.getIndex()])
/*      */       {
/*  590 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0, 1, 11, 0, 2, 12, false);
/*      */       }
/*      */       
/*  593 */       if (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.EAST.getIndex()])
/*      */       {
/*  595 */         generateWaterBox(worldIn, structureBoundingBoxIn, 7, 1, 11, 7, 2, 12, false);
/*      */       }
/*      */       
/*  598 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class EntryRoom
/*      */     extends Piece
/*      */   {
/*      */     public EntryRoom() {}
/*      */ 
/*      */     
/*      */     public EntryRoom(EnumFacing p_i45592_1_, StructureOceanMonumentPieces.RoomDefinition p_i45592_2_) {
/*  610 */       super(1, p_i45592_1_, p_i45592_2_, 1, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  615 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 2, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  616 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  617 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 1, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  618 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 0, 7, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  619 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  620 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  621 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 7, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  622 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  623 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */       
/*  625 */       if (this.roomDefinition.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */       {
/*  627 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, false);
/*      */       }
/*      */       
/*  630 */       if (this.roomDefinition.hasOpening[EnumFacing.WEST.getIndex()])
/*      */       {
/*  632 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0, 1, 3, 1, 2, 4, false);
/*      */       }
/*      */       
/*  635 */       if (this.roomDefinition.hasOpening[EnumFacing.EAST.getIndex()])
/*      */       {
/*  637 */         generateWaterBox(worldIn, structureBoundingBoxIn, 6, 1, 3, 7, 2, 4, false);
/*      */       }
/*      */       
/*  640 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class FitSimpleRoomHelper
/*      */     implements MonumentRoomFitHelper
/*      */   {
/*      */     private FitSimpleRoomHelper() {}
/*      */ 
/*      */     
/*      */     public boolean fits(StructureOceanMonumentPieces.RoomDefinition definition) {
/*  652 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece create(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/*  657 */       p_175968_2_.claimed = true;
/*  658 */       return new StructureOceanMonumentPieces.SimpleRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class FitSimpleRoomTopHelper
/*      */     implements MonumentRoomFitHelper
/*      */   {
/*      */     private FitSimpleRoomTopHelper() {}
/*      */ 
/*      */     
/*      */     public boolean fits(StructureOceanMonumentPieces.RoomDefinition definition) {
/*  670 */       return (!definition.hasOpening[EnumFacing.WEST.getIndex()] && !definition.hasOpening[EnumFacing.EAST.getIndex()] && !definition.hasOpening[EnumFacing.NORTH.getIndex()] && !definition.hasOpening[EnumFacing.SOUTH.getIndex()] && !definition.hasOpening[EnumFacing.UP.getIndex()]);
/*      */     }
/*      */ 
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece create(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/*  675 */       p_175968_2_.claimed = true;
/*  676 */       return new StructureOceanMonumentPieces.SimpleTopRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class MonumentBuilding
/*      */     extends Piece {
/*      */     private StructureOceanMonumentPieces.RoomDefinition sourceRoom;
/*      */     private StructureOceanMonumentPieces.RoomDefinition coreRoom;
/*  684 */     private final List<StructureOceanMonumentPieces.Piece> childPieces = Lists.newArrayList();
/*      */ 
/*      */ 
/*      */     
/*      */     public MonumentBuilding() {}
/*      */ 
/*      */     
/*      */     public MonumentBuilding(Random p_i45599_1_, int p_i45599_2_, int p_i45599_3_, EnumFacing p_i45599_4_) {
/*  692 */       super(0);
/*  693 */       setCoordBaseMode(p_i45599_4_);
/*  694 */       EnumFacing enumfacing = getCoordBaseMode();
/*      */       
/*  696 */       if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
/*      */         
/*  698 */         this.boundingBox = new StructureBoundingBox(p_i45599_2_, 39, p_i45599_3_, p_i45599_2_ + 58 - 1, 61, p_i45599_3_ + 58 - 1);
/*      */       }
/*      */       else {
/*      */         
/*  702 */         this.boundingBox = new StructureBoundingBox(p_i45599_2_, 39, p_i45599_3_, p_i45599_2_ + 58 - 1, 61, p_i45599_3_ + 58 - 1);
/*      */       } 
/*      */       
/*  705 */       List<StructureOceanMonumentPieces.RoomDefinition> list = generateRoomGraph(p_i45599_1_);
/*  706 */       this.sourceRoom.claimed = true;
/*  707 */       this.childPieces.add(new StructureOceanMonumentPieces.EntryRoom(enumfacing, this.sourceRoom));
/*  708 */       this.childPieces.add(new StructureOceanMonumentPieces.MonumentCoreRoom(enumfacing, this.coreRoom, p_i45599_1_));
/*  709 */       List<StructureOceanMonumentPieces.MonumentRoomFitHelper> list1 = Lists.newArrayList();
/*  710 */       list1.add(new StructureOceanMonumentPieces.XYDoubleRoomFitHelper(null));
/*  711 */       list1.add(new StructureOceanMonumentPieces.YZDoubleRoomFitHelper(null));
/*  712 */       list1.add(new StructureOceanMonumentPieces.ZDoubleRoomFitHelper(null));
/*  713 */       list1.add(new StructureOceanMonumentPieces.XDoubleRoomFitHelper(null));
/*  714 */       list1.add(new StructureOceanMonumentPieces.YDoubleRoomFitHelper(null));
/*  715 */       list1.add(new StructureOceanMonumentPieces.FitSimpleRoomTopHelper(null));
/*  716 */       list1.add(new StructureOceanMonumentPieces.FitSimpleRoomHelper(null));
/*      */ 
/*      */       
/*  719 */       for (StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition : list) {
/*      */         
/*  721 */         if (!structureoceanmonumentpieces$roomdefinition.claimed && !structureoceanmonumentpieces$roomdefinition.isSpecial()) {
/*      */           
/*  723 */           Iterator<StructureOceanMonumentPieces.MonumentRoomFitHelper> lvt_10_1_ = list1.iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  728 */           while (lvt_10_1_.hasNext()) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  733 */             StructureOceanMonumentPieces.MonumentRoomFitHelper structureoceanmonumentpieces$monumentroomfithelper = lvt_10_1_.next();
/*      */             
/*  735 */             if (structureoceanmonumentpieces$monumentroomfithelper.fits(structureoceanmonumentpieces$roomdefinition))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  741 */               this.childPieces.add(structureoceanmonumentpieces$monumentroomfithelper.create(enumfacing, structureoceanmonumentpieces$roomdefinition, p_i45599_1_)); } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  745 */       int j = this.boundingBox.minY;
/*  746 */       int k = getXWithOffset(9, 22);
/*  747 */       int l = getZWithOffset(9, 22);
/*      */       
/*  749 */       for (StructureOceanMonumentPieces.Piece structureoceanmonumentpieces$piece : this.childPieces)
/*      */       {
/*  751 */         structureoceanmonumentpieces$piece.getBoundingBox().offset(k, j, l);
/*      */       }
/*      */       
/*  754 */       StructureBoundingBox structureboundingbox1 = StructureBoundingBox.createProper(getXWithOffset(1, 1), getYWithOffset(1), getZWithOffset(1, 1), getXWithOffset(23, 21), getYWithOffset(8), getZWithOffset(23, 21));
/*  755 */       StructureBoundingBox structureboundingbox2 = StructureBoundingBox.createProper(getXWithOffset(34, 1), getYWithOffset(1), getZWithOffset(34, 1), getXWithOffset(56, 21), getYWithOffset(8), getZWithOffset(56, 21));
/*  756 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.createProper(getXWithOffset(22, 22), getYWithOffset(13), getZWithOffset(22, 22), getXWithOffset(35, 35), getYWithOffset(17), getZWithOffset(35, 35));
/*  757 */       int i = p_i45599_1_.nextInt();
/*  758 */       this.childPieces.add(new StructureOceanMonumentPieces.WingRoom(enumfacing, structureboundingbox1, i++));
/*  759 */       this.childPieces.add(new StructureOceanMonumentPieces.WingRoom(enumfacing, structureboundingbox2, i++));
/*  760 */       this.childPieces.add(new StructureOceanMonumentPieces.Penthouse(enumfacing, structureboundingbox));
/*      */     }
/*      */ 
/*      */     
/*      */     private List<StructureOceanMonumentPieces.RoomDefinition> generateRoomGraph(Random p_175836_1_) {
/*  765 */       StructureOceanMonumentPieces.RoomDefinition[] astructureoceanmonumentpieces$roomdefinition = new StructureOceanMonumentPieces.RoomDefinition[75];
/*      */       
/*  767 */       for (int i = 0; i < 5; i++) {
/*      */         
/*  769 */         for (int k = 0; k < 4; k++) {
/*      */           
/*  771 */           int m = 0;
/*  772 */           int l = getRoomIndex(i, 0, k);
/*  773 */           astructureoceanmonumentpieces$roomdefinition[l] = new StructureOceanMonumentPieces.RoomDefinition(l);
/*      */         } 
/*      */       } 
/*      */       
/*  777 */       for (int i2 = 0; i2 < 5; i2++) {
/*      */         
/*  779 */         for (int l2 = 0; l2 < 4; l2++) {
/*      */           
/*  781 */           int k3 = 1;
/*  782 */           int j4 = getRoomIndex(i2, 1, l2);
/*  783 */           astructureoceanmonumentpieces$roomdefinition[j4] = new StructureOceanMonumentPieces.RoomDefinition(j4);
/*      */         } 
/*      */       } 
/*      */       
/*  787 */       for (int j2 = 1; j2 < 4; j2++) {
/*      */         
/*  789 */         for (int i3 = 0; i3 < 2; i3++) {
/*      */           
/*  791 */           int l3 = 2;
/*  792 */           int k4 = getRoomIndex(j2, 2, i3);
/*  793 */           astructureoceanmonumentpieces$roomdefinition[k4] = new StructureOceanMonumentPieces.RoomDefinition(k4);
/*      */         } 
/*      */       } 
/*      */       
/*  797 */       this.sourceRoom = astructureoceanmonumentpieces$roomdefinition[GRIDROOM_SOURCE_INDEX];
/*      */       
/*  799 */       for (int k2 = 0; k2 < 5; k2++) {
/*      */         
/*  801 */         for (int j3 = 0; j3 < 5; j3++) {
/*      */           
/*  803 */           for (int i4 = 0; i4 < 3; i4++) {
/*      */             
/*  805 */             int l4 = getRoomIndex(k2, i4, j3);
/*      */             
/*  807 */             if (astructureoceanmonumentpieces$roomdefinition[l4] != null) {
/*      */               byte b1; int k; EnumFacing[] arrayOfEnumFacing;
/*  809 */               for (k = (arrayOfEnumFacing = EnumFacing.values()).length, b1 = 0; b1 < k; ) { EnumFacing enumfacing = arrayOfEnumFacing[b1];
/*      */                 
/*  811 */                 int i1 = k2 + enumfacing.getFrontOffsetX();
/*  812 */                 int j1 = i4 + enumfacing.getFrontOffsetY();
/*  813 */                 int k1 = j3 + enumfacing.getFrontOffsetZ();
/*      */                 
/*  815 */                 if (i1 >= 0 && i1 < 5 && k1 >= 0 && k1 < 5 && j1 >= 0 && j1 < 3) {
/*      */                   
/*  817 */                   int l1 = getRoomIndex(i1, j1, k1);
/*      */                   
/*  819 */                   if (astructureoceanmonumentpieces$roomdefinition[l1] != null)
/*      */                   {
/*  821 */                     if (k1 == j3) {
/*      */                       
/*  823 */                       astructureoceanmonumentpieces$roomdefinition[l4].setConnection(enumfacing, astructureoceanmonumentpieces$roomdefinition[l1]);
/*      */                     }
/*      */                     else {
/*      */                       
/*  827 */                       astructureoceanmonumentpieces$roomdefinition[l4].setConnection(enumfacing.getOpposite(), astructureoceanmonumentpieces$roomdefinition[l1]);
/*      */                     } 
/*      */                   }
/*      */                 } 
/*      */                 b1++; }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  837 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = new StructureOceanMonumentPieces.RoomDefinition(1003);
/*  838 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition1 = new StructureOceanMonumentPieces.RoomDefinition(1001);
/*  839 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition2 = new StructureOceanMonumentPieces.RoomDefinition(1002);
/*  840 */       astructureoceanmonumentpieces$roomdefinition[GRIDROOM_TOP_CONNECT_INDEX].setConnection(EnumFacing.UP, structureoceanmonumentpieces$roomdefinition);
/*  841 */       astructureoceanmonumentpieces$roomdefinition[GRIDROOM_LEFTWING_CONNECT_INDEX].setConnection(EnumFacing.SOUTH, structureoceanmonumentpieces$roomdefinition1);
/*  842 */       astructureoceanmonumentpieces$roomdefinition[GRIDROOM_RIGHTWING_CONNECT_INDEX].setConnection(EnumFacing.SOUTH, structureoceanmonumentpieces$roomdefinition2);
/*  843 */       structureoceanmonumentpieces$roomdefinition.claimed = true;
/*  844 */       structureoceanmonumentpieces$roomdefinition1.claimed = true;
/*  845 */       structureoceanmonumentpieces$roomdefinition2.claimed = true;
/*  846 */       this.sourceRoom.isSource = true;
/*  847 */       this.coreRoom = astructureoceanmonumentpieces$roomdefinition[getRoomIndex(p_175836_1_.nextInt(4), 0, 2)];
/*  848 */       this.coreRoom.claimed = true;
/*  849 */       (this.coreRoom.connections[EnumFacing.EAST.getIndex()]).claimed = true;
/*  850 */       (this.coreRoom.connections[EnumFacing.NORTH.getIndex()]).claimed = true;
/*  851 */       ((this.coreRoom.connections[EnumFacing.EAST.getIndex()]).connections[EnumFacing.NORTH.getIndex()]).claimed = true;
/*  852 */       (this.coreRoom.connections[EnumFacing.UP.getIndex()]).claimed = true;
/*  853 */       ((this.coreRoom.connections[EnumFacing.EAST.getIndex()]).connections[EnumFacing.UP.getIndex()]).claimed = true;
/*  854 */       ((this.coreRoom.connections[EnumFacing.NORTH.getIndex()]).connections[EnumFacing.UP.getIndex()]).claimed = true;
/*  855 */       (((this.coreRoom.connections[EnumFacing.EAST.getIndex()]).connections[EnumFacing.NORTH.getIndex()]).connections[EnumFacing.UP.getIndex()]).claimed = true;
/*  856 */       List<StructureOceanMonumentPieces.RoomDefinition> list = Lists.newArrayList(); byte b; int j;
/*      */       StructureOceanMonumentPieces.RoomDefinition[] arrayOfRoomDefinition1;
/*  858 */       for (j = (arrayOfRoomDefinition1 = astructureoceanmonumentpieces$roomdefinition).length, b = 0; b < j; ) { StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition4 = arrayOfRoomDefinition1[b];
/*      */         
/*  860 */         if (structureoceanmonumentpieces$roomdefinition4 != null) {
/*      */           
/*  862 */           structureoceanmonumentpieces$roomdefinition4.updateOpenings();
/*  863 */           list.add(structureoceanmonumentpieces$roomdefinition4);
/*      */         } 
/*      */         b++; }
/*      */       
/*  867 */       structureoceanmonumentpieces$roomdefinition.updateOpenings();
/*  868 */       Collections.shuffle(list, p_175836_1_);
/*  869 */       int i5 = 1;
/*      */       
/*  871 */       for (StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition3 : list) {
/*      */         
/*  873 */         int j5 = 0;
/*  874 */         int k5 = 0;
/*      */         
/*  876 */         while (j5 < 2 && k5 < 5) {
/*      */           
/*  878 */           k5++;
/*  879 */           int l5 = p_175836_1_.nextInt(6);
/*      */           
/*  881 */           if (structureoceanmonumentpieces$roomdefinition3.hasOpening[l5]) {
/*      */             
/*  883 */             int i6 = EnumFacing.getFront(l5).getOpposite().getIndex();
/*  884 */             structureoceanmonumentpieces$roomdefinition3.hasOpening[l5] = false;
/*  885 */             (structureoceanmonumentpieces$roomdefinition3.connections[l5]).hasOpening[i6] = false;
/*      */             
/*  887 */             if (structureoceanmonumentpieces$roomdefinition3.findSource(i5++) && structureoceanmonumentpieces$roomdefinition3.connections[l5].findSource(i5++)) {
/*      */               
/*  889 */               j5++;
/*      */               
/*      */               continue;
/*      */             } 
/*  893 */             structureoceanmonumentpieces$roomdefinition3.hasOpening[l5] = true;
/*  894 */             (structureoceanmonumentpieces$roomdefinition3.connections[l5]).hasOpening[i6] = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  900 */       list.add(structureoceanmonumentpieces$roomdefinition);
/*  901 */       list.add(structureoceanmonumentpieces$roomdefinition1);
/*  902 */       list.add(structureoceanmonumentpieces$roomdefinition2);
/*  903 */       return list;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  908 */       int i = Math.max(worldIn.getSeaLevel(), 64) - this.boundingBox.minY;
/*  909 */       generateWaterBox(worldIn, structureBoundingBoxIn, 0, 0, 0, 58, i, 58, false);
/*  910 */       generateWing(false, 0, worldIn, randomIn, structureBoundingBoxIn);
/*  911 */       generateWing(true, 33, worldIn, randomIn, structureBoundingBoxIn);
/*  912 */       generateEntranceArchs(worldIn, randomIn, structureBoundingBoxIn);
/*  913 */       generateEntranceWall(worldIn, randomIn, structureBoundingBoxIn);
/*  914 */       generateRoofPiece(worldIn, randomIn, structureBoundingBoxIn);
/*  915 */       generateLowerWall(worldIn, randomIn, structureBoundingBoxIn);
/*  916 */       generateMiddleWall(worldIn, randomIn, structureBoundingBoxIn);
/*  917 */       generateUpperWall(worldIn, randomIn, structureBoundingBoxIn);
/*      */       
/*  919 */       for (int j = 0; j < 7; j++) {
/*      */         
/*  921 */         int k = 0;
/*      */         
/*  923 */         while (k < 7) {
/*      */           
/*  925 */           if (k == 0 && j == 3)
/*      */           {
/*  927 */             k = 6;
/*      */           }
/*      */           
/*  930 */           int l = j * 9;
/*  931 */           int i1 = k * 9;
/*      */           
/*  933 */           for (int j1 = 0; j1 < 4; j1++) {
/*      */             
/*  935 */             for (int k1 = 0; k1 < 4; k1++) {
/*      */               
/*  937 */               setBlockState(worldIn, BRICKS_PRISMARINE, l + j1, 0, i1 + k1, structureBoundingBoxIn);
/*  938 */               replaceAirAndLiquidDownwards(worldIn, BRICKS_PRISMARINE, l + j1, -1, i1 + k1, structureBoundingBoxIn);
/*      */             } 
/*      */           } 
/*      */           
/*  942 */           if (j != 0 && j != 6) {
/*      */             
/*  944 */             k += 6;
/*      */             
/*      */             continue;
/*      */           } 
/*  948 */           k++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  953 */       for (int l1 = 0; l1 < 5; l1++) {
/*      */         
/*  955 */         generateWaterBox(worldIn, structureBoundingBoxIn, -1 - l1, 0 + l1 * 2, -1 - l1, -1 - l1, 23, 58 + l1, false);
/*  956 */         generateWaterBox(worldIn, structureBoundingBoxIn, 58 + l1, 0 + l1 * 2, -1 - l1, 58 + l1, 23, 58 + l1, false);
/*  957 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0 - l1, 0 + l1 * 2, -1 - l1, 57 + l1, 23, -1 - l1, false);
/*  958 */         generateWaterBox(worldIn, structureBoundingBoxIn, 0 - l1, 0 + l1 * 2, 58 + l1, 57 + l1, 23, 58 + l1, false);
/*      */       } 
/*      */       
/*  961 */       for (StructureOceanMonumentPieces.Piece structureoceanmonumentpieces$piece : this.childPieces) {
/*      */         
/*  963 */         if (structureoceanmonumentpieces$piece.getBoundingBox().intersectsWith(structureBoundingBoxIn))
/*      */         {
/*  965 */           structureoceanmonumentpieces$piece.addComponentParts(worldIn, randomIn, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  969 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     private void generateWing(boolean p_175840_1_, int p_175840_2_, World worldIn, Random p_175840_4_, StructureBoundingBox p_175840_5_) {
/*  974 */       int i = 24;
/*      */       
/*  976 */       if (doesChunkIntersect(p_175840_5_, p_175840_2_, 0, p_175840_2_ + 23, 20)) {
/*      */         
/*  978 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 0, 0, 0, p_175840_2_ + 24, 0, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*  979 */         generateWaterBox(worldIn, p_175840_5_, p_175840_2_ + 0, 1, 0, p_175840_2_ + 24, 10, 20, false);
/*      */         
/*  981 */         for (int j = 0; j < 4; j++) {
/*      */           
/*  983 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + j, j + 1, j, p_175840_2_ + j, j + 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  984 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + j + 7, j + 5, j + 7, p_175840_2_ + j + 7, j + 5, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  985 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 17 - j, j + 5, j + 7, p_175840_2_ + 17 - j, j + 5, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  986 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 24 - j, j + 1, j, p_175840_2_ + 24 - j, j + 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  987 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + j + 1, j + 1, j, p_175840_2_ + 23 - j, j + 1, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*  988 */           fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + j + 8, j + 5, j + 7, p_175840_2_ + 16 - j, j + 5, j + 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         } 
/*      */         
/*  991 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 4, 4, 4, p_175840_2_ + 6, 4, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*  992 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 7, 4, 4, p_175840_2_ + 17, 4, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*  993 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 18, 4, 4, p_175840_2_ + 20, 4, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*  994 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 11, 8, 11, p_175840_2_ + 13, 8, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*  995 */         setBlockState(worldIn, DOT_DECO_DATA, p_175840_2_ + 12, 9, 12, p_175840_5_);
/*  996 */         setBlockState(worldIn, DOT_DECO_DATA, p_175840_2_ + 12, 9, 15, p_175840_5_);
/*  997 */         setBlockState(worldIn, DOT_DECO_DATA, p_175840_2_ + 12, 9, 18, p_175840_5_);
/*  998 */         int j1 = p_175840_2_ + (p_175840_1_ ? 19 : 5);
/*  999 */         int k = p_175840_2_ + (p_175840_1_ ? 5 : 19);
/*      */         
/* 1001 */         for (int l = 20; l >= 5; l -= 3)
/*      */         {
/* 1003 */           setBlockState(worldIn, DOT_DECO_DATA, j1, 5, l, p_175840_5_);
/*      */         }
/*      */         
/* 1006 */         for (int k1 = 19; k1 >= 7; k1 -= 3)
/*      */         {
/* 1008 */           setBlockState(worldIn, DOT_DECO_DATA, k, 5, k1, p_175840_5_);
/*      */         }
/*      */         
/* 1011 */         for (int l1 = 0; l1 < 4; l1++) {
/*      */           
/* 1013 */           int i1 = p_175840_1_ ? (p_175840_2_ + 24 - 17 - l1 * 3) : (p_175840_2_ + 17 - l1 * 3);
/* 1014 */           setBlockState(worldIn, DOT_DECO_DATA, i1, 5, 5, p_175840_5_);
/*      */         } 
/*      */         
/* 1017 */         setBlockState(worldIn, DOT_DECO_DATA, k, 5, 5, p_175840_5_);
/* 1018 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 11, 1, 12, p_175840_2_ + 13, 7, 12, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1019 */         fillWithBlocks(worldIn, p_175840_5_, p_175840_2_ + 12, 1, 11, p_175840_2_ + 12, 7, 13, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void generateEntranceArchs(World worldIn, Random p_175839_2_, StructureBoundingBox p_175839_3_) {
/* 1025 */       if (doesChunkIntersect(p_175839_3_, 22, 5, 35, 17)) {
/*      */         
/* 1027 */         generateWaterBox(worldIn, p_175839_3_, 25, 0, 0, 32, 8, 20, false);
/*      */         
/* 1029 */         for (int i = 0; i < 4; i++) {
/*      */           
/* 1031 */           fillWithBlocks(worldIn, p_175839_3_, 24, 2, 5 + i * 4, 24, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1032 */           fillWithBlocks(worldIn, p_175839_3_, 22, 4, 5 + i * 4, 23, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1033 */           setBlockState(worldIn, BRICKS_PRISMARINE, 25, 5, 5 + i * 4, p_175839_3_);
/* 1034 */           setBlockState(worldIn, BRICKS_PRISMARINE, 26, 6, 5 + i * 4, p_175839_3_);
/* 1035 */           setBlockState(worldIn, SEA_LANTERN, 26, 5, 5 + i * 4, p_175839_3_);
/* 1036 */           fillWithBlocks(worldIn, p_175839_3_, 33, 2, 5 + i * 4, 33, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1037 */           fillWithBlocks(worldIn, p_175839_3_, 34, 4, 5 + i * 4, 35, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1038 */           setBlockState(worldIn, BRICKS_PRISMARINE, 32, 5, 5 + i * 4, p_175839_3_);
/* 1039 */           setBlockState(worldIn, BRICKS_PRISMARINE, 31, 6, 5 + i * 4, p_175839_3_);
/* 1040 */           setBlockState(worldIn, SEA_LANTERN, 31, 5, 5 + i * 4, p_175839_3_);
/* 1041 */           fillWithBlocks(worldIn, p_175839_3_, 27, 6, 5 + i * 4, 30, 6, 5 + i * 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void generateEntranceWall(World worldIn, Random p_175837_2_, StructureBoundingBox p_175837_3_) {
/* 1048 */       if (doesChunkIntersect(p_175837_3_, 15, 20, 42, 21)) {
/*      */         
/* 1050 */         fillWithBlocks(worldIn, p_175837_3_, 15, 0, 21, 42, 0, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1051 */         generateWaterBox(worldIn, p_175837_3_, 26, 1, 21, 31, 3, 21, false);
/* 1052 */         fillWithBlocks(worldIn, p_175837_3_, 21, 12, 21, 36, 12, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1053 */         fillWithBlocks(worldIn, p_175837_3_, 17, 11, 21, 40, 11, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1054 */         fillWithBlocks(worldIn, p_175837_3_, 16, 10, 21, 41, 10, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1055 */         fillWithBlocks(worldIn, p_175837_3_, 15, 7, 21, 42, 9, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1056 */         fillWithBlocks(worldIn, p_175837_3_, 16, 6, 21, 41, 6, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1057 */         fillWithBlocks(worldIn, p_175837_3_, 17, 5, 21, 40, 5, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1058 */         fillWithBlocks(worldIn, p_175837_3_, 21, 4, 21, 36, 4, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1059 */         fillWithBlocks(worldIn, p_175837_3_, 22, 3, 21, 26, 3, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1060 */         fillWithBlocks(worldIn, p_175837_3_, 31, 3, 21, 35, 3, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1061 */         fillWithBlocks(worldIn, p_175837_3_, 23, 2, 21, 25, 2, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1062 */         fillWithBlocks(worldIn, p_175837_3_, 32, 2, 21, 34, 2, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1063 */         fillWithBlocks(worldIn, p_175837_3_, 28, 4, 20, 29, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1064 */         setBlockState(worldIn, BRICKS_PRISMARINE, 27, 3, 21, p_175837_3_);
/* 1065 */         setBlockState(worldIn, BRICKS_PRISMARINE, 30, 3, 21, p_175837_3_);
/* 1066 */         setBlockState(worldIn, BRICKS_PRISMARINE, 26, 2, 21, p_175837_3_);
/* 1067 */         setBlockState(worldIn, BRICKS_PRISMARINE, 31, 2, 21, p_175837_3_);
/* 1068 */         setBlockState(worldIn, BRICKS_PRISMARINE, 25, 1, 21, p_175837_3_);
/* 1069 */         setBlockState(worldIn, BRICKS_PRISMARINE, 32, 1, 21, p_175837_3_);
/*      */         
/* 1071 */         for (int i = 0; i < 7; i++) {
/*      */           
/* 1073 */           setBlockState(worldIn, DARK_PRISMARINE, 28 - i, 6 + i, 21, p_175837_3_);
/* 1074 */           setBlockState(worldIn, DARK_PRISMARINE, 29 + i, 6 + i, 21, p_175837_3_);
/*      */         } 
/*      */         
/* 1077 */         for (int j = 0; j < 4; j++) {
/*      */           
/* 1079 */           setBlockState(worldIn, DARK_PRISMARINE, 28 - j, 9 + j, 21, p_175837_3_);
/* 1080 */           setBlockState(worldIn, DARK_PRISMARINE, 29 + j, 9 + j, 21, p_175837_3_);
/*      */         } 
/*      */         
/* 1083 */         setBlockState(worldIn, DARK_PRISMARINE, 28, 12, 21, p_175837_3_);
/* 1084 */         setBlockState(worldIn, DARK_PRISMARINE, 29, 12, 21, p_175837_3_);
/*      */         
/* 1086 */         for (int k = 0; k < 3; k++) {
/*      */           
/* 1088 */           setBlockState(worldIn, DARK_PRISMARINE, 22 - k * 2, 8, 21, p_175837_3_);
/* 1089 */           setBlockState(worldIn, DARK_PRISMARINE, 22 - k * 2, 9, 21, p_175837_3_);
/* 1090 */           setBlockState(worldIn, DARK_PRISMARINE, 35 + k * 2, 8, 21, p_175837_3_);
/* 1091 */           setBlockState(worldIn, DARK_PRISMARINE, 35 + k * 2, 9, 21, p_175837_3_);
/*      */         } 
/*      */         
/* 1094 */         generateWaterBox(worldIn, p_175837_3_, 15, 13, 21, 42, 15, 21, false);
/* 1095 */         generateWaterBox(worldIn, p_175837_3_, 15, 1, 21, 15, 6, 21, false);
/* 1096 */         generateWaterBox(worldIn, p_175837_3_, 16, 1, 21, 16, 5, 21, false);
/* 1097 */         generateWaterBox(worldIn, p_175837_3_, 17, 1, 21, 20, 4, 21, false);
/* 1098 */         generateWaterBox(worldIn, p_175837_3_, 21, 1, 21, 21, 3, 21, false);
/* 1099 */         generateWaterBox(worldIn, p_175837_3_, 22, 1, 21, 22, 2, 21, false);
/* 1100 */         generateWaterBox(worldIn, p_175837_3_, 23, 1, 21, 24, 1, 21, false);
/* 1101 */         generateWaterBox(worldIn, p_175837_3_, 42, 1, 21, 42, 6, 21, false);
/* 1102 */         generateWaterBox(worldIn, p_175837_3_, 41, 1, 21, 41, 5, 21, false);
/* 1103 */         generateWaterBox(worldIn, p_175837_3_, 37, 1, 21, 40, 4, 21, false);
/* 1104 */         generateWaterBox(worldIn, p_175837_3_, 36, 1, 21, 36, 3, 21, false);
/* 1105 */         generateWaterBox(worldIn, p_175837_3_, 33, 1, 21, 34, 1, 21, false);
/* 1106 */         generateWaterBox(worldIn, p_175837_3_, 35, 1, 21, 35, 2, 21, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void generateRoofPiece(World worldIn, Random p_175841_2_, StructureBoundingBox p_175841_3_) {
/* 1112 */       if (doesChunkIntersect(p_175841_3_, 21, 21, 36, 36)) {
/*      */         
/* 1114 */         fillWithBlocks(worldIn, p_175841_3_, 21, 0, 22, 36, 0, 36, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1115 */         generateWaterBox(worldIn, p_175841_3_, 21, 1, 22, 36, 23, 36, false);
/*      */         
/* 1117 */         for (int i = 0; i < 4; i++) {
/*      */           
/* 1119 */           fillWithBlocks(worldIn, p_175841_3_, 21 + i, 13 + i, 21 + i, 36 - i, 13 + i, 21 + i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1120 */           fillWithBlocks(worldIn, p_175841_3_, 21 + i, 13 + i, 36 - i, 36 - i, 13 + i, 36 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1121 */           fillWithBlocks(worldIn, p_175841_3_, 21 + i, 13 + i, 22 + i, 21 + i, 13 + i, 35 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1122 */           fillWithBlocks(worldIn, p_175841_3_, 36 - i, 13 + i, 22 + i, 36 - i, 13 + i, 35 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         } 
/*      */         
/* 1125 */         fillWithBlocks(worldIn, p_175841_3_, 25, 16, 25, 32, 16, 32, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1126 */         fillWithBlocks(worldIn, p_175841_3_, 25, 17, 25, 25, 19, 25, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1127 */         fillWithBlocks(worldIn, p_175841_3_, 32, 17, 25, 32, 19, 25, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1128 */         fillWithBlocks(worldIn, p_175841_3_, 25, 17, 32, 25, 19, 32, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1129 */         fillWithBlocks(worldIn, p_175841_3_, 32, 17, 32, 32, 19, 32, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1130 */         setBlockState(worldIn, BRICKS_PRISMARINE, 26, 20, 26, p_175841_3_);
/* 1131 */         setBlockState(worldIn, BRICKS_PRISMARINE, 27, 21, 27, p_175841_3_);
/* 1132 */         setBlockState(worldIn, SEA_LANTERN, 27, 20, 27, p_175841_3_);
/* 1133 */         setBlockState(worldIn, BRICKS_PRISMARINE, 26, 20, 31, p_175841_3_);
/* 1134 */         setBlockState(worldIn, BRICKS_PRISMARINE, 27, 21, 30, p_175841_3_);
/* 1135 */         setBlockState(worldIn, SEA_LANTERN, 27, 20, 30, p_175841_3_);
/* 1136 */         setBlockState(worldIn, BRICKS_PRISMARINE, 31, 20, 31, p_175841_3_);
/* 1137 */         setBlockState(worldIn, BRICKS_PRISMARINE, 30, 21, 30, p_175841_3_);
/* 1138 */         setBlockState(worldIn, SEA_LANTERN, 30, 20, 30, p_175841_3_);
/* 1139 */         setBlockState(worldIn, BRICKS_PRISMARINE, 31, 20, 26, p_175841_3_);
/* 1140 */         setBlockState(worldIn, BRICKS_PRISMARINE, 30, 21, 27, p_175841_3_);
/* 1141 */         setBlockState(worldIn, SEA_LANTERN, 30, 20, 27, p_175841_3_);
/* 1142 */         fillWithBlocks(worldIn, p_175841_3_, 28, 21, 27, 29, 21, 27, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1143 */         fillWithBlocks(worldIn, p_175841_3_, 27, 21, 28, 27, 21, 29, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1144 */         fillWithBlocks(worldIn, p_175841_3_, 28, 21, 30, 29, 21, 30, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1145 */         fillWithBlocks(worldIn, p_175841_3_, 30, 21, 28, 30, 21, 29, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void generateLowerWall(World worldIn, Random p_175835_2_, StructureBoundingBox p_175835_3_) {
/* 1151 */       if (doesChunkIntersect(p_175835_3_, 0, 21, 6, 58)) {
/*      */         
/* 1153 */         fillWithBlocks(worldIn, p_175835_3_, 0, 0, 21, 6, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1154 */         generateWaterBox(worldIn, p_175835_3_, 0, 1, 21, 6, 7, 57, false);
/* 1155 */         fillWithBlocks(worldIn, p_175835_3_, 4, 4, 21, 6, 4, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         
/* 1157 */         for (int i = 0; i < 4; i++)
/*      */         {
/* 1159 */           fillWithBlocks(worldIn, p_175835_3_, i, i + 1, 21, i, i + 1, 57 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         
/* 1162 */         for (int j = 23; j < 53; j += 3)
/*      */         {
/* 1164 */           setBlockState(worldIn, DOT_DECO_DATA, 5, 5, j, p_175835_3_);
/*      */         }
/*      */         
/* 1167 */         setBlockState(worldIn, DOT_DECO_DATA, 5, 5, 52, p_175835_3_);
/*      */         
/* 1169 */         for (int k = 0; k < 4; k++)
/*      */         {
/* 1171 */           fillWithBlocks(worldIn, p_175835_3_, k, k + 1, 21, k, k + 1, 57 - k, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         
/* 1174 */         fillWithBlocks(worldIn, p_175835_3_, 4, 1, 52, 6, 3, 52, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1175 */         fillWithBlocks(worldIn, p_175835_3_, 5, 1, 51, 5, 3, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */       } 
/*      */       
/* 1178 */       if (doesChunkIntersect(p_175835_3_, 51, 21, 58, 58)) {
/*      */         
/* 1180 */         fillWithBlocks(worldIn, p_175835_3_, 51, 0, 21, 57, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1181 */         generateWaterBox(worldIn, p_175835_3_, 51, 1, 21, 57, 7, 57, false);
/* 1182 */         fillWithBlocks(worldIn, p_175835_3_, 51, 4, 21, 53, 4, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         
/* 1184 */         for (int l = 0; l < 4; l++)
/*      */         {
/* 1186 */           fillWithBlocks(worldIn, p_175835_3_, 57 - l, l + 1, 21, 57 - l, l + 1, 57 - l, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         
/* 1189 */         for (int i1 = 23; i1 < 53; i1 += 3)
/*      */         {
/* 1191 */           setBlockState(worldIn, DOT_DECO_DATA, 52, 5, i1, p_175835_3_);
/*      */         }
/*      */         
/* 1194 */         setBlockState(worldIn, DOT_DECO_DATA, 52, 5, 52, p_175835_3_);
/* 1195 */         fillWithBlocks(worldIn, p_175835_3_, 51, 1, 52, 53, 3, 52, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1196 */         fillWithBlocks(worldIn, p_175835_3_, 52, 1, 51, 52, 3, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */       } 
/*      */       
/* 1199 */       if (doesChunkIntersect(p_175835_3_, 0, 51, 57, 57)) {
/*      */         
/* 1201 */         fillWithBlocks(worldIn, p_175835_3_, 7, 0, 51, 50, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1202 */         generateWaterBox(worldIn, p_175835_3_, 7, 1, 51, 50, 10, 57, false);
/*      */         
/* 1204 */         for (int j1 = 0; j1 < 4; j1++)
/*      */         {
/* 1206 */           fillWithBlocks(worldIn, p_175835_3_, j1 + 1, j1 + 1, 57 - j1, 56 - j1, j1 + 1, 57 - j1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void generateMiddleWall(World worldIn, Random p_175842_2_, StructureBoundingBox p_175842_3_) {
/* 1213 */       if (doesChunkIntersect(p_175842_3_, 7, 21, 13, 50)) {
/*      */         
/* 1215 */         fillWithBlocks(worldIn, p_175842_3_, 7, 0, 21, 13, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1216 */         generateWaterBox(worldIn, p_175842_3_, 7, 1, 21, 13, 10, 50, false);
/* 1217 */         fillWithBlocks(worldIn, p_175842_3_, 11, 8, 21, 13, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         
/* 1219 */         for (int i = 0; i < 4; i++)
/*      */         {
/* 1221 */           fillWithBlocks(worldIn, p_175842_3_, i + 7, i + 5, 21, i + 7, i + 5, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         
/* 1224 */         for (int j = 21; j <= 45; j += 3)
/*      */         {
/* 1226 */           setBlockState(worldIn, DOT_DECO_DATA, 12, 9, j, p_175842_3_);
/*      */         }
/*      */       } 
/*      */       
/* 1230 */       if (doesChunkIntersect(p_175842_3_, 44, 21, 50, 54)) {
/*      */         
/* 1232 */         fillWithBlocks(worldIn, p_175842_3_, 44, 0, 21, 50, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1233 */         generateWaterBox(worldIn, p_175842_3_, 44, 1, 21, 50, 10, 50, false);
/* 1234 */         fillWithBlocks(worldIn, p_175842_3_, 44, 8, 21, 46, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         
/* 1236 */         for (int k = 0; k < 4; k++)
/*      */         {
/* 1238 */           fillWithBlocks(worldIn, p_175842_3_, 50 - k, k + 5, 21, 50 - k, k + 5, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         
/* 1241 */         for (int l = 21; l <= 45; l += 3)
/*      */         {
/* 1243 */           setBlockState(worldIn, DOT_DECO_DATA, 45, 9, l, p_175842_3_);
/*      */         }
/*      */       } 
/*      */       
/* 1247 */       if (doesChunkIntersect(p_175842_3_, 8, 44, 49, 54)) {
/*      */         
/* 1249 */         fillWithBlocks(worldIn, p_175842_3_, 14, 0, 44, 43, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1250 */         generateWaterBox(worldIn, p_175842_3_, 14, 1, 44, 43, 10, 50, false);
/*      */         
/* 1252 */         for (int i1 = 12; i1 <= 45; i1 += 3) {
/*      */           
/* 1254 */           setBlockState(worldIn, DOT_DECO_DATA, i1, 9, 45, p_175842_3_);
/* 1255 */           setBlockState(worldIn, DOT_DECO_DATA, i1, 9, 52, p_175842_3_);
/*      */           
/* 1257 */           if (i1 == 12 || i1 == 18 || i1 == 24 || i1 == 33 || i1 == 39 || i1 == 45) {
/*      */             
/* 1259 */             setBlockState(worldIn, DOT_DECO_DATA, i1, 9, 47, p_175842_3_);
/* 1260 */             setBlockState(worldIn, DOT_DECO_DATA, i1, 9, 50, p_175842_3_);
/* 1261 */             setBlockState(worldIn, DOT_DECO_DATA, i1, 10, 45, p_175842_3_);
/* 1262 */             setBlockState(worldIn, DOT_DECO_DATA, i1, 10, 46, p_175842_3_);
/* 1263 */             setBlockState(worldIn, DOT_DECO_DATA, i1, 10, 51, p_175842_3_);
/* 1264 */             setBlockState(worldIn, DOT_DECO_DATA, i1, 10, 52, p_175842_3_);
/* 1265 */             setBlockState(worldIn, DOT_DECO_DATA, i1, 11, 47, p_175842_3_);
/* 1266 */             setBlockState(worldIn, DOT_DECO_DATA, i1, 11, 50, p_175842_3_);
/* 1267 */             setBlockState(worldIn, DOT_DECO_DATA, i1, 12, 48, p_175842_3_);
/* 1268 */             setBlockState(worldIn, DOT_DECO_DATA, i1, 12, 49, p_175842_3_);
/*      */           } 
/*      */         } 
/*      */         
/* 1272 */         for (int j1 = 0; j1 < 3; j1++)
/*      */         {
/* 1274 */           fillWithBlocks(worldIn, p_175842_3_, 8 + j1, 5 + j1, 54, 49 - j1, 5 + j1, 54, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         }
/*      */         
/* 1277 */         fillWithBlocks(worldIn, p_175842_3_, 11, 8, 54, 46, 8, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1278 */         fillWithBlocks(worldIn, p_175842_3_, 14, 8, 44, 43, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void generateUpperWall(World worldIn, Random p_175838_2_, StructureBoundingBox p_175838_3_) {
/* 1284 */       if (doesChunkIntersect(p_175838_3_, 14, 21, 20, 43)) {
/*      */         
/* 1286 */         fillWithBlocks(worldIn, p_175838_3_, 14, 0, 21, 20, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1287 */         generateWaterBox(worldIn, p_175838_3_, 14, 1, 22, 20, 14, 43, false);
/* 1288 */         fillWithBlocks(worldIn, p_175838_3_, 18, 12, 22, 20, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1289 */         fillWithBlocks(worldIn, p_175838_3_, 18, 12, 21, 20, 12, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         
/* 1291 */         for (int i = 0; i < 4; i++)
/*      */         {
/* 1293 */           fillWithBlocks(worldIn, p_175838_3_, i + 14, i + 9, 21, i + 14, i + 9, 43 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         
/* 1296 */         for (int j = 23; j <= 39; j += 3)
/*      */         {
/* 1298 */           setBlockState(worldIn, DOT_DECO_DATA, 19, 13, j, p_175838_3_);
/*      */         }
/*      */       } 
/*      */       
/* 1302 */       if (doesChunkIntersect(p_175838_3_, 37, 21, 43, 43)) {
/*      */         
/* 1304 */         fillWithBlocks(worldIn, p_175838_3_, 37, 0, 21, 43, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1305 */         generateWaterBox(worldIn, p_175838_3_, 37, 1, 22, 43, 14, 43, false);
/* 1306 */         fillWithBlocks(worldIn, p_175838_3_, 37, 12, 22, 39, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1307 */         fillWithBlocks(worldIn, p_175838_3_, 37, 12, 21, 39, 12, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         
/* 1309 */         for (int k = 0; k < 4; k++)
/*      */         {
/* 1311 */           fillWithBlocks(worldIn, p_175838_3_, 43 - k, k + 9, 21, 43 - k, k + 9, 43 - k, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         
/* 1314 */         for (int l = 23; l <= 39; l += 3)
/*      */         {
/* 1316 */           setBlockState(worldIn, DOT_DECO_DATA, 38, 13, l, p_175838_3_);
/*      */         }
/*      */       } 
/*      */       
/* 1320 */       if (doesChunkIntersect(p_175838_3_, 15, 37, 42, 43)) {
/*      */         
/* 1322 */         fillWithBlocks(worldIn, p_175838_3_, 21, 0, 37, 36, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1323 */         generateWaterBox(worldIn, p_175838_3_, 21, 1, 37, 36, 14, 43, false);
/* 1324 */         fillWithBlocks(worldIn, p_175838_3_, 21, 12, 37, 36, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */         
/* 1326 */         for (int i1 = 0; i1 < 4; i1++)
/*      */         {
/* 1328 */           fillWithBlocks(worldIn, p_175838_3_, 15 + i1, i1 + 9, 43 - i1, 42 - i1, i1 + 9, 43 - i1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         
/* 1331 */         for (int j1 = 21; j1 <= 36; j1 += 3)
/*      */         {
/* 1333 */           setBlockState(worldIn, DOT_DECO_DATA, j1, 13, 38, p_175838_3_);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class MonumentCoreRoom
/*      */     extends Piece
/*      */   {
/*      */     public MonumentCoreRoom() {}
/*      */ 
/*      */     
/*      */     public MonumentCoreRoom(EnumFacing p_i45598_1_, StructureOceanMonumentPieces.RoomDefinition p_i45598_2_, Random p_i45598_3_) {
/* 1347 */       super(1, p_i45598_1_, p_i45598_2_, 2, 2, 2);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1352 */       generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 1, 8, 0, 14, 8, 14, ROUGH_PRISMARINE);
/* 1353 */       int i = 7;
/* 1354 */       IBlockState iblockstate = BRICKS_PRISMARINE;
/* 1355 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 0, 0, 7, 15, iblockstate, iblockstate, false);
/* 1356 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 7, 0, 15, 7, 15, iblockstate, iblockstate, false);
/* 1357 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, 0, 15, 7, 0, iblockstate, iblockstate, false);
/* 1358 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, 15, 14, 7, 15, iblockstate, iblockstate, false);
/*      */       
/* 1360 */       for (int k = 1; k <= 6; k++) {
/*      */         
/* 1362 */         iblockstate = BRICKS_PRISMARINE;
/*      */         
/* 1364 */         if (k == 2 || k == 6)
/*      */         {
/* 1366 */           iblockstate = ROUGH_PRISMARINE;
/*      */         }
/*      */         
/* 1369 */         for (int j = 0; j <= 15; j += 15) {
/*      */           
/* 1371 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j, k, 0, j, k, 1, iblockstate, iblockstate, false);
/* 1372 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j, k, 6, j, k, 9, iblockstate, iblockstate, false);
/* 1373 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j, k, 14, j, k, 15, iblockstate, iblockstate, false);
/*      */         } 
/*      */         
/* 1376 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, k, 0, 1, k, 0, iblockstate, iblockstate, false);
/* 1377 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, k, 0, 9, k, 0, iblockstate, iblockstate, false);
/* 1378 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 14, k, 0, 14, k, 0, iblockstate, iblockstate, false);
/* 1379 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, k, 15, 14, k, 15, iblockstate, iblockstate, false);
/*      */       } 
/*      */       
/* 1382 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 6, 9, 6, 9, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1383 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState(), false);
/*      */       
/* 1385 */       for (int l = 3; l <= 6; l += 3) {
/*      */         
/* 1387 */         for (int i1 = 6; i1 <= 9; i1 += 3) {
/*      */           
/* 1389 */           setBlockState(worldIn, SEA_LANTERN, i1, l, 6, structureBoundingBoxIn);
/* 1390 */           setBlockState(worldIn, SEA_LANTERN, i1, l, 9, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1394 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 6, 5, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1395 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 9, 5, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1396 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 6, 10, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1397 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 9, 10, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1398 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 5, 6, 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1399 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 5, 9, 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1400 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 10, 6, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1401 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 10, 9, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1402 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 5, 5, 6, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1403 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 10, 5, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1404 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 2, 5, 10, 6, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1405 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 2, 10, 10, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1406 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 7, 1, 5, 7, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1407 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 7, 1, 10, 7, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1408 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 7, 9, 5, 7, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1409 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 7, 9, 10, 7, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1410 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, 5, 6, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1411 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, 10, 6, 7, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1412 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 7, 5, 14, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1413 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 7, 10, 14, 7, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1414 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 2, 2, 1, 3, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1415 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1416 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 1, 2, 13, 1, 3, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1417 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, 2, 12, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1418 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 12, 2, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1419 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 13, 3, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1420 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 1, 12, 13, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1421 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, 13, 12, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1422 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static interface MonumentRoomFitHelper
/*      */   {
/*      */     boolean fits(StructureOceanMonumentPieces.RoomDefinition param1RoomDefinition);
/*      */ 
/*      */     
/*      */     StructureOceanMonumentPieces.Piece create(EnumFacing param1EnumFacing, StructureOceanMonumentPieces.RoomDefinition param1RoomDefinition, Random param1Random);
/*      */   }
/*      */   
/*      */   public static class Penthouse
/*      */     extends Piece
/*      */   {
/*      */     public Penthouse() {}
/*      */     
/*      */     public Penthouse(EnumFacing p_i45591_1_, StructureBoundingBox p_i45591_2_) {
/* 1441 */       super(p_i45591_1_, p_i45591_2_);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1446 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, -1, 2, 11, -1, 11, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1447 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, -1, 0, 1, -1, 11, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1448 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, -1, 0, 13, -1, 11, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1449 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, -1, 0, 11, -1, 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1450 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, -1, 12, 11, -1, 13, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1451 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1452 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 0, 0, 13, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1453 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 12, 0, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1454 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 13, 12, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */       
/* 1456 */       for (int i = 2; i <= 11; i += 3) {
/*      */         
/* 1458 */         setBlockState(worldIn, SEA_LANTERN, 0, 0, i, structureBoundingBoxIn);
/* 1459 */         setBlockState(worldIn, SEA_LANTERN, 13, 0, i, structureBoundingBoxIn);
/* 1460 */         setBlockState(worldIn, SEA_LANTERN, i, 0, 0, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 1463 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 3, 4, 0, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1464 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 3, 11, 0, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1465 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 9, 9, 0, 11, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1466 */       setBlockState(worldIn, BRICKS_PRISMARINE, 5, 0, 8, structureBoundingBoxIn);
/* 1467 */       setBlockState(worldIn, BRICKS_PRISMARINE, 8, 0, 8, structureBoundingBoxIn);
/* 1468 */       setBlockState(worldIn, BRICKS_PRISMARINE, 10, 0, 10, structureBoundingBoxIn);
/* 1469 */       setBlockState(worldIn, BRICKS_PRISMARINE, 3, 0, 10, structureBoundingBoxIn);
/* 1470 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 3, 3, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1471 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 3, 10, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1472 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 10, 7, 0, 10, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1473 */       int l = 3;
/*      */       
/* 1475 */       for (int j = 0; j < 2; j++) {
/*      */         
/* 1477 */         for (int k = 2; k <= 8; k += 3)
/*      */         {
/* 1479 */           fillWithBlocks(worldIn, structureBoundingBoxIn, l, 0, k, l, 2, k, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         
/* 1482 */         l = 10;
/*      */       } 
/*      */       
/* 1485 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 10, 5, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1486 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 10, 8, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1487 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, -1, 7, 7, -1, 8, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1488 */       generateWaterBox(worldIn, structureBoundingBoxIn, 6, -1, 3, 7, -1, 4, false);
/* 1489 */       spawnElder(worldIn, structureBoundingBoxIn, 6, 1, 6);
/* 1490 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static abstract class Piece
/*      */     extends StructureComponent {
/* 1496 */     protected static final IBlockState ROUGH_PRISMARINE = Blocks.PRISMARINE.getStateFromMeta(BlockPrismarine.ROUGH_META);
/* 1497 */     protected static final IBlockState BRICKS_PRISMARINE = Blocks.PRISMARINE.getStateFromMeta(BlockPrismarine.BRICKS_META);
/* 1498 */     protected static final IBlockState DARK_PRISMARINE = Blocks.PRISMARINE.getStateFromMeta(BlockPrismarine.DARK_META);
/* 1499 */     protected static final IBlockState DOT_DECO_DATA = BRICKS_PRISMARINE;
/* 1500 */     protected static final IBlockState SEA_LANTERN = Blocks.SEA_LANTERN.getDefaultState();
/* 1501 */     protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
/* 1502 */     protected static final int GRIDROOM_SOURCE_INDEX = getRoomIndex(2, 0, 0);
/* 1503 */     protected static final int GRIDROOM_TOP_CONNECT_INDEX = getRoomIndex(2, 2, 0);
/* 1504 */     protected static final int GRIDROOM_LEFTWING_CONNECT_INDEX = getRoomIndex(0, 1, 0);
/* 1505 */     protected static final int GRIDROOM_RIGHTWING_CONNECT_INDEX = getRoomIndex(4, 1, 0);
/*      */     
/*      */     protected StructureOceanMonumentPieces.RoomDefinition roomDefinition;
/*      */     
/*      */     protected static final int getRoomIndex(int p_175820_0_, int p_175820_1_, int p_175820_2_) {
/* 1510 */       return p_175820_1_ * 25 + p_175820_2_ * 5 + p_175820_0_;
/*      */     }
/*      */ 
/*      */     
/*      */     public Piece() {
/* 1515 */       super(0);
/*      */     }
/*      */ 
/*      */     
/*      */     public Piece(int p_i45588_1_) {
/* 1520 */       super(p_i45588_1_);
/*      */     }
/*      */ 
/*      */     
/*      */     public Piece(EnumFacing p_i45589_1_, StructureBoundingBox p_i45589_2_) {
/* 1525 */       super(1);
/* 1526 */       setCoordBaseMode(p_i45589_1_);
/* 1527 */       this.boundingBox = p_i45589_2_;
/*      */     }
/*      */ 
/*      */     
/*      */     protected Piece(int p_i45590_1_, EnumFacing p_i45590_2_, StructureOceanMonumentPieces.RoomDefinition p_i45590_3_, int p_i45590_4_, int p_i45590_5_, int p_i45590_6_) {
/* 1532 */       super(p_i45590_1_);
/* 1533 */       setCoordBaseMode(p_i45590_2_);
/* 1534 */       this.roomDefinition = p_i45590_3_;
/* 1535 */       int i = p_i45590_3_.index;
/* 1536 */       int j = i % 5;
/* 1537 */       int k = i / 5 % 5;
/* 1538 */       int l = i / 25;
/*      */       
/* 1540 */       if (p_i45590_2_ != EnumFacing.NORTH && p_i45590_2_ != EnumFacing.SOUTH) {
/*      */         
/* 1542 */         this.boundingBox = new StructureBoundingBox(0, 0, 0, p_i45590_6_ * 8 - 1, p_i45590_5_ * 4 - 1, p_i45590_4_ * 8 - 1);
/*      */       }
/*      */       else {
/*      */         
/* 1546 */         this.boundingBox = new StructureBoundingBox(0, 0, 0, p_i45590_4_ * 8 - 1, p_i45590_5_ * 4 - 1, p_i45590_6_ * 8 - 1);
/*      */       } 
/*      */       
/* 1549 */       switch (p_i45590_2_) {
/*      */         
/*      */         case NORTH:
/* 1552 */           this.boundingBox.offset(j * 8, l * 4, -(k + p_i45590_6_) * 8 + 1);
/*      */           return;
/*      */         
/*      */         case SOUTH:
/* 1556 */           this.boundingBox.offset(j * 8, l * 4, k * 8);
/*      */           return;
/*      */         
/*      */         case WEST:
/* 1560 */           this.boundingBox.offset(-(k + p_i45590_6_) * 8 + 1, l * 4, j * 8);
/*      */           return;
/*      */       } 
/*      */       
/* 1564 */       this.boundingBox.offset(k * 8, l * 4, j * 8);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {}
/*      */ 
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {}
/*      */ 
/*      */ 
/*      */     
/*      */     protected void generateWaterBox(World p_181655_1_, StructureBoundingBox p_181655_2_, int p_181655_3_, int p_181655_4_, int p_181655_5_, int p_181655_6_, int p_181655_7_, int p_181655_8_, boolean p_181655_9_) {
/* 1578 */       for (int i = p_181655_4_; i <= p_181655_7_; i++) {
/*      */         
/* 1580 */         for (int j = p_181655_3_; j <= p_181655_6_; j++) {
/*      */           
/* 1582 */           for (int k = p_181655_5_; k <= p_181655_8_; k++) {
/*      */             
/* 1584 */             if (!p_181655_9_ || getBlockStateFromPos(p_181655_1_, j, i, k, p_181655_2_).getMaterial() != Material.AIR)
/*      */             {
/* 1586 */               if (getYWithOffset(i) >= p_181655_1_.getSeaLevel()) {
/*      */                 
/* 1588 */                 setBlockState(p_181655_1_, Blocks.AIR.getDefaultState(), j, i, k, p_181655_2_);
/*      */               }
/*      */               else {
/*      */                 
/* 1592 */                 setBlockState(p_181655_1_, WATER, j, i, k, p_181655_2_);
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void generateDefaultFloor(World worldIn, StructureBoundingBox p_175821_2_, int p_175821_3_, int p_175821_4_, boolean p_175821_5_) {
/* 1602 */       if (p_175821_5_) {
/*      */         
/* 1604 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 0, 0, p_175821_4_ + 0, p_175821_3_ + 2, 0, p_175821_4_ + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1605 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 5, 0, p_175821_4_ + 0, p_175821_3_ + 8 - 1, 0, p_175821_4_ + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1606 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 0, p_175821_3_ + 4, 0, p_175821_4_ + 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1607 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 5, p_175821_3_ + 4, 0, p_175821_4_ + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1608 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 2, p_175821_3_ + 4, 0, p_175821_4_ + 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1609 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 5, p_175821_3_ + 4, 0, p_175821_4_ + 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1610 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 2, 0, p_175821_4_ + 3, p_175821_3_ + 2, 0, p_175821_4_ + 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1611 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 5, 0, p_175821_4_ + 3, p_175821_3_ + 5, 0, p_175821_4_ + 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */       }
/*      */       else {
/*      */         
/* 1615 */         fillWithBlocks(worldIn, p_175821_2_, p_175821_3_ + 0, 0, p_175821_4_ + 0, p_175821_3_ + 8 - 1, 0, p_175821_4_ + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void generateBoxOnFillOnly(World worldIn, StructureBoundingBox p_175819_2_, int p_175819_3_, int p_175819_4_, int p_175819_5_, int p_175819_6_, int p_175819_7_, int p_175819_8_, IBlockState p_175819_9_) {
/* 1621 */       for (int i = p_175819_4_; i <= p_175819_7_; i++) {
/*      */         
/* 1623 */         for (int j = p_175819_3_; j <= p_175819_6_; j++) {
/*      */           
/* 1625 */           for (int k = p_175819_5_; k <= p_175819_8_; k++) {
/*      */             
/* 1627 */             if (getBlockStateFromPos(worldIn, j, i, k, p_175819_2_) == WATER)
/*      */             {
/* 1629 */               setBlockState(worldIn, p_175819_9_, j, i, k, p_175819_2_);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean doesChunkIntersect(StructureBoundingBox p_175818_1_, int p_175818_2_, int p_175818_3_, int p_175818_4_, int p_175818_5_) {
/* 1638 */       int i = getXWithOffset(p_175818_2_, p_175818_3_);
/* 1639 */       int j = getZWithOffset(p_175818_2_, p_175818_3_);
/* 1640 */       int k = getXWithOffset(p_175818_4_, p_175818_5_);
/* 1641 */       int l = getZWithOffset(p_175818_4_, p_175818_5_);
/* 1642 */       return p_175818_1_.intersectsWith(Math.min(i, k), Math.min(j, l), Math.max(i, k), Math.max(j, l));
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean spawnElder(World worldIn, StructureBoundingBox p_175817_2_, int p_175817_3_, int p_175817_4_, int p_175817_5_) {
/* 1647 */       int i = getXWithOffset(p_175817_3_, p_175817_5_);
/* 1648 */       int j = getYWithOffset(p_175817_4_);
/* 1649 */       int k = getZWithOffset(p_175817_3_, p_175817_5_);
/*      */       
/* 1651 */       if (p_175817_2_.isVecInside((Vec3i)new BlockPos(i, j, k))) {
/*      */         
/* 1653 */         EntityElderGuardian entityelderguardian = new EntityElderGuardian(worldIn);
/* 1654 */         entityelderguardian.heal(entityelderguardian.getMaxHealth());
/* 1655 */         entityelderguardian.setLocationAndAngles(i + 0.5D, j, k + 0.5D, 0.0F, 0.0F);
/* 1656 */         entityelderguardian.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityelderguardian)), null);
/* 1657 */         worldIn.spawnEntityInWorld((Entity)entityelderguardian);
/* 1658 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 1662 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class RoomDefinition
/*      */   {
/*      */     int index;
/* 1670 */     RoomDefinition[] connections = new RoomDefinition[6];
/* 1671 */     boolean[] hasOpening = new boolean[6];
/*      */     
/*      */     boolean claimed;
/*      */     boolean isSource;
/*      */     int scanIndex;
/*      */     
/*      */     public RoomDefinition(int p_i45584_1_) {
/* 1678 */       this.index = p_i45584_1_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setConnection(EnumFacing p_175957_1_, RoomDefinition p_175957_2_) {
/* 1683 */       this.connections[p_175957_1_.getIndex()] = p_175957_2_;
/* 1684 */       p_175957_2_.connections[p_175957_1_.getOpposite().getIndex()] = this;
/*      */     }
/*      */ 
/*      */     
/*      */     public void updateOpenings() {
/* 1689 */       for (int i = 0; i < 6; i++)
/*      */       {
/* 1691 */         this.hasOpening[i] = (this.connections[i] != null);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean findSource(int p_175959_1_) {
/* 1697 */       if (this.isSource)
/*      */       {
/* 1699 */         return true;
/*      */       }
/*      */ 
/*      */       
/* 1703 */       this.scanIndex = p_175959_1_;
/*      */       
/* 1705 */       for (int i = 0; i < 6; i++) {
/*      */         
/* 1707 */         if (this.connections[i] != null && this.hasOpening[i] && (this.connections[i]).scanIndex != p_175959_1_ && this.connections[i].findSource(p_175959_1_))
/*      */         {
/* 1709 */           return true;
/*      */         }
/*      */       } 
/*      */       
/* 1713 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isSpecial() {
/* 1719 */       return (this.index >= 75);
/*      */     }
/*      */ 
/*      */     
/*      */     public int countOpenings() {
/* 1724 */       int i = 0;
/*      */       
/* 1726 */       for (int j = 0; j < 6; j++) {
/*      */         
/* 1728 */         if (this.hasOpening[j])
/*      */         {
/* 1730 */           i++;
/*      */         }
/*      */       } 
/*      */       
/* 1734 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class SimpleRoom
/*      */     extends Piece
/*      */   {
/*      */     private int mainDesign;
/*      */ 
/*      */     
/*      */     public SimpleRoom() {}
/*      */     
/*      */     public SimpleRoom(EnumFacing p_i45587_1_, StructureOceanMonumentPieces.RoomDefinition p_i45587_2_, Random p_i45587_3_) {
/* 1748 */       super(1, p_i45587_1_, p_i45587_2_, 1, 1, 1);
/* 1749 */       this.mainDesign = p_i45587_3_.nextInt(3);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1754 */       if (this.roomDefinition.index / 25 > 0)
/*      */       {
/* 1756 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 0, 0, this.roomDefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
/*      */       }
/*      */       
/* 1759 */       if (this.roomDefinition.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/* 1761 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 1, 4, 1, 6, 4, 6, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/* 1764 */       boolean flag = (this.mainDesign != 0 && randomIn.nextBoolean() && !this.roomDefinition.hasOpening[EnumFacing.DOWN.getIndex()] && !this.roomDefinition.hasOpening[EnumFacing.UP.getIndex()] && this.roomDefinition.countOpenings() > 1);
/*      */       
/* 1766 */       if (this.mainDesign == 0) {
/*      */         
/* 1768 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 2, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1769 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 2, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1770 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 2, 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1771 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 2, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1772 */         setBlockState(worldIn, SEA_LANTERN, 1, 2, 1, structureBoundingBoxIn);
/* 1773 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 7, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1774 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 3, 0, 7, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1775 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 7, 2, 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1776 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 6, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1777 */         setBlockState(worldIn, SEA_LANTERN, 6, 2, 1, structureBoundingBoxIn);
/* 1778 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 5, 2, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1779 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 5, 2, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1780 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1781 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 7, 2, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1782 */         setBlockState(worldIn, SEA_LANTERN, 1, 2, 6, structureBoundingBoxIn);
/* 1783 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 5, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1784 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 3, 5, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1785 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 5, 7, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1786 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 7, 6, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1787 */         setBlockState(worldIn, SEA_LANTERN, 6, 2, 6, structureBoundingBoxIn);
/*      */         
/* 1789 */         if (this.roomDefinition.hasOpening[EnumFacing.SOUTH.getIndex()]) {
/*      */           
/* 1791 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 0, 4, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         else {
/*      */           
/* 1795 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 0, 4, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1796 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 0, 4, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1797 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 1, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         } 
/*      */         
/* 1800 */         if (this.roomDefinition.hasOpening[EnumFacing.NORTH.getIndex()]) {
/*      */           
/* 1802 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 7, 4, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         else {
/*      */           
/* 1806 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 6, 4, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1807 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 7, 4, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1808 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 6, 4, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         } 
/*      */         
/* 1811 */         if (this.roomDefinition.hasOpening[EnumFacing.WEST.getIndex()]) {
/*      */           
/* 1813 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 3, 0, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         else {
/*      */           
/* 1817 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 3, 1, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1818 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 3, 0, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1819 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 3, 1, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         } 
/*      */         
/* 1822 */         if (this.roomDefinition.hasOpening[EnumFacing.EAST.getIndex()])
/*      */         {
/* 1824 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 3, 7, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         else
/*      */         {
/* 1828 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 3, 7, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1829 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 3, 7, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1830 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 3, 7, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */       
/* 1833 */       } else if (this.mainDesign == 1) {
/*      */         
/* 1835 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 2, 2, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1836 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 5, 2, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1837 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 5, 5, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1838 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 2, 5, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1839 */         setBlockState(worldIn, SEA_LANTERN, 2, 2, 2, structureBoundingBoxIn);
/* 1840 */         setBlockState(worldIn, SEA_LANTERN, 2, 2, 5, structureBoundingBoxIn);
/* 1841 */         setBlockState(worldIn, SEA_LANTERN, 5, 2, 5, structureBoundingBoxIn);
/* 1842 */         setBlockState(worldIn, SEA_LANTERN, 5, 2, 2, structureBoundingBoxIn);
/* 1843 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 1, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1844 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1845 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 7, 1, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1846 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1847 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 7, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1848 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 6, 7, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1849 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 0, 7, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1850 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 1, 7, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1851 */         setBlockState(worldIn, ROUGH_PRISMARINE, 1, 2, 0, structureBoundingBoxIn);
/* 1852 */         setBlockState(worldIn, ROUGH_PRISMARINE, 0, 2, 1, structureBoundingBoxIn);
/* 1853 */         setBlockState(worldIn, ROUGH_PRISMARINE, 1, 2, 7, structureBoundingBoxIn);
/* 1854 */         setBlockState(worldIn, ROUGH_PRISMARINE, 0, 2, 6, structureBoundingBoxIn);
/* 1855 */         setBlockState(worldIn, ROUGH_PRISMARINE, 6, 2, 7, structureBoundingBoxIn);
/* 1856 */         setBlockState(worldIn, ROUGH_PRISMARINE, 7, 2, 6, structureBoundingBoxIn);
/* 1857 */         setBlockState(worldIn, ROUGH_PRISMARINE, 6, 2, 0, structureBoundingBoxIn);
/* 1858 */         setBlockState(worldIn, ROUGH_PRISMARINE, 7, 2, 1, structureBoundingBoxIn);
/*      */         
/* 1860 */         if (!this.roomDefinition.hasOpening[EnumFacing.SOUTH.getIndex()]) {
/*      */           
/* 1862 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1863 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 6, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1864 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         } 
/*      */         
/* 1867 */         if (!this.roomDefinition.hasOpening[EnumFacing.NORTH.getIndex()]) {
/*      */           
/* 1869 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1870 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 7, 6, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1871 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         } 
/*      */         
/* 1874 */         if (!this.roomDefinition.hasOpening[EnumFacing.WEST.getIndex()]) {
/*      */           
/* 1876 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1877 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 2, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1878 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 1, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         } 
/*      */         
/* 1881 */         if (!this.roomDefinition.hasOpening[EnumFacing.EAST.getIndex()])
/*      */         {
/* 1883 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 1, 7, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1884 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 1, 7, 2, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1885 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 1, 7, 1, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */       
/* 1888 */       } else if (this.mainDesign == 2) {
/*      */         
/* 1890 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1891 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1892 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1893 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1894 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1895 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1896 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1897 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1898 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1899 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1900 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1901 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1902 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1903 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1904 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1905 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/*      */         
/* 1907 */         if (this.roomDefinition.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */         {
/* 1909 */           generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */         }
/*      */         
/* 1912 */         if (this.roomDefinition.hasOpening[EnumFacing.NORTH.getIndex()])
/*      */         {
/* 1914 */           generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, false);
/*      */         }
/*      */         
/* 1917 */         if (this.roomDefinition.hasOpening[EnumFacing.WEST.getIndex()])
/*      */         {
/* 1919 */           generateWaterBox(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, false);
/*      */         }
/*      */         
/* 1922 */         if (this.roomDefinition.hasOpening[EnumFacing.EAST.getIndex()])
/*      */         {
/* 1924 */           generateWaterBox(worldIn, structureBoundingBoxIn, 7, 1, 3, 7, 2, 4, false);
/*      */         }
/*      */       } 
/*      */       
/* 1928 */       if (flag) {
/*      */         
/* 1930 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 3, 4, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1931 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 3, 4, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, false);
/* 1932 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 3, 4, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */       } 
/*      */       
/* 1935 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class SimpleTopRoom
/*      */     extends Piece
/*      */   {
/*      */     public SimpleTopRoom() {}
/*      */ 
/*      */     
/*      */     public SimpleTopRoom(EnumFacing p_i45586_1_, StructureOceanMonumentPieces.RoomDefinition p_i45586_2_, Random p_i45586_3_) {
/* 1947 */       super(1, p_i45586_1_, p_i45586_2_, 1, 1, 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1952 */       if (this.roomDefinition.index / 25 > 0)
/*      */       {
/* 1954 */         generateDefaultFloor(worldIn, structureBoundingBoxIn, 0, 0, this.roomDefinition.hasOpening[EnumFacing.DOWN.getIndex()]);
/*      */       }
/*      */       
/* 1957 */       if (this.roomDefinition.connections[EnumFacing.UP.getIndex()] == null)
/*      */       {
/* 1959 */         generateBoxOnFillOnly(worldIn, structureBoundingBoxIn, 1, 4, 1, 6, 4, 6, ROUGH_PRISMARINE);
/*      */       }
/*      */       
/* 1962 */       for (int i = 1; i <= 6; i++) {
/*      */         
/* 1964 */         for (int j = 1; j <= 6; j++) {
/*      */           
/* 1966 */           if (randomIn.nextInt(3) != 0) {
/*      */             
/* 1968 */             int k = 2 + ((randomIn.nextInt(4) == 0) ? 0 : 1);
/* 1969 */             fillWithBlocks(worldIn, structureBoundingBoxIn, i, k, j, i, 3, j, Blocks.SPONGE.getStateFromMeta(1), Blocks.SPONGE.getStateFromMeta(1), false);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1974 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1975 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1976 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1977 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1978 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1979 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1980 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1981 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1982 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1983 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1984 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1985 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 1986 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1987 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1988 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 1989 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/*      */       
/* 1991 */       if (this.roomDefinition.hasOpening[EnumFacing.SOUTH.getIndex()])
/*      */       {
/* 1993 */         generateWaterBox(worldIn, structureBoundingBoxIn, 3, 1, 0, 4, 2, 0, false);
/*      */       }
/*      */       
/* 1996 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class WingRoom
/*      */     extends Piece
/*      */   {
/*      */     private int mainDesign;
/*      */ 
/*      */     
/*      */     public WingRoom() {}
/*      */     
/*      */     public WingRoom(EnumFacing p_i45585_1_, StructureBoundingBox p_i45585_2_, int p_i45585_3_) {
/* 2010 */       super(p_i45585_1_, p_i45585_2_);
/* 2011 */       this.mainDesign = p_i45585_3_ & 0x1;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 2016 */       if (this.mainDesign == 0) {
/*      */         
/* 2018 */         for (int i = 0; i < 4; i++)
/*      */         {
/* 2020 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 10 - i, 3 - i, 20 - i, 12 + i, 3 - i, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/*      */         }
/*      */         
/* 2023 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 6, 15, 0, 16, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2024 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 6, 6, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2025 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 16, 0, 6, 16, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2026 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 7, 7, 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2027 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 1, 7, 15, 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2028 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 1, 6, 9, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2029 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 1, 6, 15, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2030 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 7, 9, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2031 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 1, 7, 14, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2032 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 5, 13, 0, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2033 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 7, 12, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 2034 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 10, 8, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 2035 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 14, 0, 10, 14, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, false);
/*      */         
/* 2037 */         for (int i1 = 18; i1 >= 7; i1 -= 3) {
/*      */           
/* 2039 */           setBlockState(worldIn, SEA_LANTERN, 6, 3, i1, structureBoundingBoxIn);
/* 2040 */           setBlockState(worldIn, SEA_LANTERN, 16, 3, i1, structureBoundingBoxIn);
/*      */         } 
/*      */         
/* 2043 */         setBlockState(worldIn, SEA_LANTERN, 10, 0, 10, structureBoundingBoxIn);
/* 2044 */         setBlockState(worldIn, SEA_LANTERN, 12, 0, 10, structureBoundingBoxIn);
/* 2045 */         setBlockState(worldIn, SEA_LANTERN, 10, 0, 12, structureBoundingBoxIn);
/* 2046 */         setBlockState(worldIn, SEA_LANTERN, 12, 0, 12, structureBoundingBoxIn);
/* 2047 */         setBlockState(worldIn, SEA_LANTERN, 8, 3, 6, structureBoundingBoxIn);
/* 2048 */         setBlockState(worldIn, SEA_LANTERN, 14, 3, 6, structureBoundingBoxIn);
/* 2049 */         setBlockState(worldIn, BRICKS_PRISMARINE, 4, 2, 4, structureBoundingBoxIn);
/* 2050 */         setBlockState(worldIn, SEA_LANTERN, 4, 1, 4, structureBoundingBoxIn);
/* 2051 */         setBlockState(worldIn, BRICKS_PRISMARINE, 4, 0, 4, structureBoundingBoxIn);
/* 2052 */         setBlockState(worldIn, BRICKS_PRISMARINE, 18, 2, 4, structureBoundingBoxIn);
/* 2053 */         setBlockState(worldIn, SEA_LANTERN, 18, 1, 4, structureBoundingBoxIn);
/* 2054 */         setBlockState(worldIn, BRICKS_PRISMARINE, 18, 0, 4, structureBoundingBoxIn);
/* 2055 */         setBlockState(worldIn, BRICKS_PRISMARINE, 4, 2, 18, structureBoundingBoxIn);
/* 2056 */         setBlockState(worldIn, SEA_LANTERN, 4, 1, 18, structureBoundingBoxIn);
/* 2057 */         setBlockState(worldIn, BRICKS_PRISMARINE, 4, 0, 18, structureBoundingBoxIn);
/* 2058 */         setBlockState(worldIn, BRICKS_PRISMARINE, 18, 2, 18, structureBoundingBoxIn);
/* 2059 */         setBlockState(worldIn, SEA_LANTERN, 18, 1, 18, structureBoundingBoxIn);
/* 2060 */         setBlockState(worldIn, BRICKS_PRISMARINE, 18, 0, 18, structureBoundingBoxIn);
/* 2061 */         setBlockState(worldIn, BRICKS_PRISMARINE, 9, 7, 20, structureBoundingBoxIn);
/* 2062 */         setBlockState(worldIn, BRICKS_PRISMARINE, 13, 7, 20, structureBoundingBoxIn);
/* 2063 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 21, 7, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2064 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 0, 21, 16, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2065 */         spawnElder(worldIn, structureBoundingBoxIn, 11, 2, 16);
/*      */       }
/* 2067 */       else if (this.mainDesign == 1) {
/*      */         
/* 2069 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 3, 18, 13, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2070 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 18, 9, 2, 18, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2071 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 0, 18, 13, 2, 18, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2072 */         int j1 = 9;
/* 2073 */         int j = 20;
/* 2074 */         int k = 5;
/*      */         
/* 2076 */         for (int l = 0; l < 2; l++) {
/*      */           
/* 2078 */           setBlockState(worldIn, BRICKS_PRISMARINE, j1, 6, 20, structureBoundingBoxIn);
/* 2079 */           setBlockState(worldIn, SEA_LANTERN, j1, 5, 20, structureBoundingBoxIn);
/* 2080 */           setBlockState(worldIn, BRICKS_PRISMARINE, j1, 4, 20, structureBoundingBoxIn);
/* 2081 */           j1 = 13;
/*      */         } 
/*      */         
/* 2084 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 7, 15, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2085 */         j1 = 10;
/*      */         
/* 2087 */         for (int k1 = 0; k1 < 2; k1++) {
/*      */           
/* 2089 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j1, 0, 10, j1, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2090 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j1, 0, 12, j1, 6, 12, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2091 */           setBlockState(worldIn, SEA_LANTERN, j1, 0, 10, structureBoundingBoxIn);
/* 2092 */           setBlockState(worldIn, SEA_LANTERN, j1, 0, 12, structureBoundingBoxIn);
/* 2093 */           setBlockState(worldIn, SEA_LANTERN, j1, 4, 10, structureBoundingBoxIn);
/* 2094 */           setBlockState(worldIn, SEA_LANTERN, j1, 4, 12, structureBoundingBoxIn);
/* 2095 */           j1 = 12;
/*      */         } 
/*      */         
/* 2098 */         j1 = 8;
/*      */         
/* 2100 */         for (int l1 = 0; l1 < 2; l1++) {
/*      */           
/* 2102 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j1, 0, 7, j1, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2103 */           fillWithBlocks(worldIn, structureBoundingBoxIn, j1, 0, 14, j1, 2, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, false);
/* 2104 */           j1 = 14;
/*      */         } 
/*      */         
/* 2107 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 3, 8, 8, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 2108 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 14, 3, 8, 14, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, false);
/* 2109 */         spawnElder(worldIn, structureBoundingBoxIn, 11, 5, 13);
/*      */       } 
/*      */       
/* 2112 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class XDoubleRoomFitHelper
/*      */     implements MonumentRoomFitHelper
/*      */   {
/*      */     private XDoubleRoomFitHelper() {}
/*      */ 
/*      */     
/*      */     public boolean fits(StructureOceanMonumentPieces.RoomDefinition definition) {
/* 2124 */       return (definition.hasOpening[EnumFacing.EAST.getIndex()] && !(definition.connections[EnumFacing.EAST.getIndex()]).claimed);
/*      */     }
/*      */ 
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece create(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/* 2129 */       p_175968_2_.claimed = true;
/* 2130 */       (p_175968_2_.connections[EnumFacing.EAST.getIndex()]).claimed = true;
/* 2131 */       return new StructureOceanMonumentPieces.DoubleXRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class XYDoubleRoomFitHelper
/*      */     implements MonumentRoomFitHelper
/*      */   {
/*      */     private XYDoubleRoomFitHelper() {}
/*      */ 
/*      */     
/*      */     public boolean fits(StructureOceanMonumentPieces.RoomDefinition definition) {
/* 2143 */       if (definition.hasOpening[EnumFacing.EAST.getIndex()] && !(definition.connections[EnumFacing.EAST.getIndex()]).claimed && definition.hasOpening[EnumFacing.UP.getIndex()] && !(definition.connections[EnumFacing.UP.getIndex()]).claimed) {
/*      */         
/* 2145 */         StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = definition.connections[EnumFacing.EAST.getIndex()];
/* 2146 */         return (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.UP.getIndex()] && !(structureoceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()]).claimed);
/*      */       } 
/*      */ 
/*      */       
/* 2150 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece create(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/* 2156 */       p_175968_2_.claimed = true;
/* 2157 */       (p_175968_2_.connections[EnumFacing.EAST.getIndex()]).claimed = true;
/* 2158 */       (p_175968_2_.connections[EnumFacing.UP.getIndex()]).claimed = true;
/* 2159 */       ((p_175968_2_.connections[EnumFacing.EAST.getIndex()]).connections[EnumFacing.UP.getIndex()]).claimed = true;
/* 2160 */       return new StructureOceanMonumentPieces.DoubleXYRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class YDoubleRoomFitHelper
/*      */     implements MonumentRoomFitHelper
/*      */   {
/*      */     private YDoubleRoomFitHelper() {}
/*      */ 
/*      */     
/*      */     public boolean fits(StructureOceanMonumentPieces.RoomDefinition definition) {
/* 2172 */       return (definition.hasOpening[EnumFacing.UP.getIndex()] && !(definition.connections[EnumFacing.UP.getIndex()]).claimed);
/*      */     }
/*      */ 
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece create(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/* 2177 */       p_175968_2_.claimed = true;
/* 2178 */       (p_175968_2_.connections[EnumFacing.UP.getIndex()]).claimed = true;
/* 2179 */       return new StructureOceanMonumentPieces.DoubleYRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class YZDoubleRoomFitHelper
/*      */     implements MonumentRoomFitHelper
/*      */   {
/*      */     private YZDoubleRoomFitHelper() {}
/*      */ 
/*      */     
/*      */     public boolean fits(StructureOceanMonumentPieces.RoomDefinition definition) {
/* 2191 */       if (definition.hasOpening[EnumFacing.NORTH.getIndex()] && !(definition.connections[EnumFacing.NORTH.getIndex()]).claimed && definition.hasOpening[EnumFacing.UP.getIndex()] && !(definition.connections[EnumFacing.UP.getIndex()]).claimed) {
/*      */         
/* 2193 */         StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = definition.connections[EnumFacing.NORTH.getIndex()];
/* 2194 */         return (structureoceanmonumentpieces$roomdefinition.hasOpening[EnumFacing.UP.getIndex()] && !(structureoceanmonumentpieces$roomdefinition.connections[EnumFacing.UP.getIndex()]).claimed);
/*      */       } 
/*      */ 
/*      */       
/* 2198 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece create(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/* 2204 */       p_175968_2_.claimed = true;
/* 2205 */       (p_175968_2_.connections[EnumFacing.NORTH.getIndex()]).claimed = true;
/* 2206 */       (p_175968_2_.connections[EnumFacing.UP.getIndex()]).claimed = true;
/* 2207 */       ((p_175968_2_.connections[EnumFacing.NORTH.getIndex()]).connections[EnumFacing.UP.getIndex()]).claimed = true;
/* 2208 */       return new StructureOceanMonumentPieces.DoubleYZRoom(p_175968_1_, p_175968_2_, p_175968_3_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class ZDoubleRoomFitHelper
/*      */     implements MonumentRoomFitHelper
/*      */   {
/*      */     private ZDoubleRoomFitHelper() {}
/*      */ 
/*      */     
/*      */     public boolean fits(StructureOceanMonumentPieces.RoomDefinition definition) {
/* 2220 */       return (definition.hasOpening[EnumFacing.NORTH.getIndex()] && !(definition.connections[EnumFacing.NORTH.getIndex()]).claimed);
/*      */     }
/*      */ 
/*      */     
/*      */     public StructureOceanMonumentPieces.Piece create(EnumFacing p_175968_1_, StructureOceanMonumentPieces.RoomDefinition p_175968_2_, Random p_175968_3_) {
/* 2225 */       StructureOceanMonumentPieces.RoomDefinition structureoceanmonumentpieces$roomdefinition = p_175968_2_;
/*      */       
/* 2227 */       if (!p_175968_2_.hasOpening[EnumFacing.NORTH.getIndex()] || (p_175968_2_.connections[EnumFacing.NORTH.getIndex()]).claimed)
/*      */       {
/* 2229 */         structureoceanmonumentpieces$roomdefinition = p_175968_2_.connections[EnumFacing.SOUTH.getIndex()];
/*      */       }
/*      */       
/* 2232 */       structureoceanmonumentpieces$roomdefinition.claimed = true;
/* 2233 */       (structureoceanmonumentpieces$roomdefinition.connections[EnumFacing.NORTH.getIndex()]).claimed = true;
/* 2234 */       return new StructureOceanMonumentPieces.DoubleZRoom(p_175968_1_, structureoceanmonumentpieces$roomdefinition, p_175968_3_);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureOceanMonumentPieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */