/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.template.PlacementSettings;
/*     */ import net.minecraft.world.gen.structure.template.Template;
/*     */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*     */ 
/*     */ public abstract class StructureComponentTemplate
/*     */   extends StructureComponent
/*     */ {
/*  19 */   private static final PlacementSettings DEFAULT_PLACE_SETTINGS = new PlacementSettings();
/*     */   
/*     */   protected Template template;
/*     */   protected PlacementSettings placeSettings;
/*     */   protected BlockPos templatePosition;
/*     */   
/*     */   public StructureComponentTemplate() {
/*  26 */     this.placeSettings = DEFAULT_PLACE_SETTINGS.setIgnoreEntities(true).setReplacedBlock(Blocks.AIR);
/*     */   }
/*     */ 
/*     */   
/*     */   public StructureComponentTemplate(int p_i46662_1_) {
/*  31 */     super(p_i46662_1_);
/*  32 */     this.placeSettings = DEFAULT_PLACE_SETTINGS.setIgnoreEntities(true).setReplacedBlock(Blocks.AIR);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup(Template p_186173_1_, BlockPos p_186173_2_, PlacementSettings p_186173_3_) {
/*  37 */     this.template = p_186173_1_;
/*  38 */     setCoordBaseMode(EnumFacing.NORTH);
/*  39 */     this.templatePosition = p_186173_2_;
/*  40 */     this.placeSettings = p_186173_3_;
/*  41 */     setBoundingBoxFromTemplate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  49 */     tagCompound.setInteger("TPX", this.templatePosition.getX());
/*  50 */     tagCompound.setInteger("TPY", this.templatePosition.getY());
/*  51 */     tagCompound.setInteger("TPZ", this.templatePosition.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) {
/*  59 */     this.templatePosition = new BlockPos(tagCompound.getInteger("TPX"), tagCompound.getInteger("TPY"), tagCompound.getInteger("TPZ"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  68 */     this.placeSettings.setBoundingBox(structureBoundingBoxIn);
/*  69 */     this.template.addBlocksToWorld(worldIn, this.templatePosition, this.placeSettings, 18);
/*  70 */     Map<BlockPos, String> map = this.template.getDataBlocks(this.templatePosition, this.placeSettings);
/*     */     
/*  72 */     for (Map.Entry<BlockPos, String> entry : map.entrySet()) {
/*     */       
/*  74 */       String s = entry.getValue();
/*  75 */       handleDataMarker(s, entry.getKey(), worldIn, randomIn, structureBoundingBoxIn);
/*     */     } 
/*     */     
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setBoundingBoxFromTemplate() {
/*     */     BlockPos blockpos2, blockpos1;
/*  85 */     Rotation rotation = this.placeSettings.getRotation();
/*  86 */     BlockPos blockpos = this.template.transformedSize(rotation);
/*  87 */     Mirror mirror = this.placeSettings.getMirror();
/*  88 */     this.boundingBox = new StructureBoundingBox(0, 0, 0, blockpos.getX(), blockpos.getY() - 1, blockpos.getZ());
/*     */     
/*  90 */     switch (rotation) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case CLOCKWISE_90:
/*  97 */         this.boundingBox.offset(-blockpos.getX(), 0, 0);
/*     */         break;
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 101 */         this.boundingBox.offset(0, 0, -blockpos.getZ());
/*     */         break;
/*     */       
/*     */       case null:
/* 105 */         this.boundingBox.offset(-blockpos.getX(), 0, -blockpos.getZ());
/*     */         break;
/*     */     } 
/* 108 */     switch (mirror) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case null:
/* 115 */         blockpos2 = BlockPos.ORIGIN;
/*     */         
/* 117 */         if (rotation != Rotation.CLOCKWISE_90 && rotation != Rotation.COUNTERCLOCKWISE_90) {
/*     */           
/* 119 */           if (rotation == Rotation.CLOCKWISE_180)
/*     */           {
/* 121 */             blockpos2 = blockpos2.offset(EnumFacing.EAST, blockpos.getX());
/*     */           }
/*     */           else
/*     */           {
/* 125 */             blockpos2 = blockpos2.offset(EnumFacing.WEST, blockpos.getX());
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 130 */           blockpos2 = blockpos2.offset(rotation.rotate(EnumFacing.WEST), blockpos.getZ());
/*     */         } 
/*     */         
/* 133 */         this.boundingBox.offset(blockpos2.getX(), 0, blockpos2.getZ());
/*     */         break;
/*     */       
/*     */       case LEFT_RIGHT:
/* 137 */         blockpos1 = BlockPos.ORIGIN;
/*     */         
/* 139 */         if (rotation != Rotation.CLOCKWISE_90 && rotation != Rotation.COUNTERCLOCKWISE_90) {
/*     */           
/* 141 */           if (rotation == Rotation.CLOCKWISE_180)
/*     */           {
/* 143 */             blockpos1 = blockpos1.offset(EnumFacing.SOUTH, blockpos.getZ());
/*     */           }
/*     */           else
/*     */           {
/* 147 */             blockpos1 = blockpos1.offset(EnumFacing.NORTH, blockpos.getZ());
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 152 */           blockpos1 = blockpos1.offset(rotation.rotate(EnumFacing.NORTH), blockpos.getX());
/*     */         } 
/*     */         
/* 155 */         this.boundingBox.offset(blockpos1.getX(), 0, blockpos1.getZ());
/*     */         break;
/*     */     } 
/* 158 */     this.boundingBox.offset(this.templatePosition.getX(), this.templatePosition.getY(), this.templatePosition.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public void offset(int x, int y, int z) {
/* 163 */     super.offset(x, y, z);
/* 164 */     this.templatePosition = this.templatePosition.add(x, y, z);
/*     */   }
/*     */   
/*     */   protected abstract void handleDataMarker(String paramString, BlockPos paramBlockPos, World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox);
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureComponentTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */