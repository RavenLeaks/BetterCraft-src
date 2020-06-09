/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.client.model.ModelChest;
/*     */ import net.minecraft.client.model.ModelLargeChest;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class TileEntityChestRenderer extends TileEntitySpecialRenderer<TileEntityChest> {
/*  14 */   private static final ResourceLocation TEXTURE_TRAPPED_DOUBLE = new ResourceLocation("textures/entity/chest/trapped_double.png");
/*  15 */   private static final ResourceLocation TEXTURE_CHRISTMAS_DOUBLE = new ResourceLocation("textures/entity/chest/christmas_double.png");
/*  16 */   private static final ResourceLocation TEXTURE_NORMAL_DOUBLE = new ResourceLocation("textures/entity/chest/normal_double.png");
/*  17 */   private static final ResourceLocation TEXTURE_TRAPPED = new ResourceLocation("textures/entity/chest/trapped.png");
/*  18 */   private static final ResourceLocation TEXTURE_CHRISTMAS = new ResourceLocation("textures/entity/chest/christmas.png");
/*  19 */   private static final ResourceLocation TEXTURE_NORMAL = new ResourceLocation("textures/entity/chest/normal.png");
/*  20 */   private final ModelChest simpleChest = new ModelChest();
/*  21 */   private final ModelChest largeChest = (ModelChest)new ModelLargeChest();
/*     */   
/*     */   private boolean isChristmas;
/*     */   
/*     */   public TileEntityChestRenderer() {
/*  26 */     Calendar calendar = Calendar.getInstance();
/*     */     
/*  28 */     if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
/*     */     {
/*  30 */       this.isChristmas = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_192841_a(TileEntityChest p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/*     */     int i;
/*  36 */     GlStateManager.enableDepth();
/*  37 */     GlStateManager.depthFunc(515);
/*  38 */     GlStateManager.depthMask(true);
/*     */ 
/*     */     
/*  41 */     if (p_192841_1_.hasWorldObj()) {
/*     */       
/*  43 */       Block block = p_192841_1_.getBlockType();
/*  44 */       i = p_192841_1_.getBlockMetadata();
/*     */       
/*  46 */       if (block instanceof BlockChest && i == 0) {
/*     */         
/*  48 */         ((BlockChest)block).checkForSurroundingChests(p_192841_1_.getWorld(), p_192841_1_.getPos(), p_192841_1_.getWorld().getBlockState(p_192841_1_.getPos()));
/*  49 */         i = p_192841_1_.getBlockMetadata();
/*     */       } 
/*     */       
/*  52 */       p_192841_1_.checkForAdjacentChests();
/*     */     }
/*     */     else {
/*     */       
/*  56 */       i = 0;
/*     */     } 
/*     */     
/*  59 */     if (p_192841_1_.adjacentChestZNeg == null && p_192841_1_.adjacentChestXNeg == null) {
/*     */       ModelChest modelchest;
/*     */ 
/*     */       
/*  63 */       if (p_192841_1_.adjacentChestXPos == null && p_192841_1_.adjacentChestZPos == null) {
/*     */         
/*  65 */         modelchest = this.simpleChest;
/*     */         
/*  67 */         if (p_192841_9_ >= 0)
/*     */         {
/*  69 */           bindTexture(DESTROY_STAGES[p_192841_9_]);
/*  70 */           GlStateManager.matrixMode(5890);
/*  71 */           GlStateManager.pushMatrix();
/*  72 */           GlStateManager.scale(4.0F, 4.0F, 1.0F);
/*  73 */           GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  74 */           GlStateManager.matrixMode(5888);
/*     */         }
/*  76 */         else if (this.isChristmas)
/*     */         {
/*  78 */           bindTexture(TEXTURE_CHRISTMAS);
/*     */         }
/*  80 */         else if (p_192841_1_.getChestType() == BlockChest.Type.TRAP)
/*     */         {
/*  82 */           bindTexture(TEXTURE_TRAPPED);
/*     */         }
/*     */         else
/*     */         {
/*  86 */           bindTexture(TEXTURE_NORMAL);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  91 */         modelchest = this.largeChest;
/*     */         
/*  93 */         if (p_192841_9_ >= 0) {
/*     */           
/*  95 */           bindTexture(DESTROY_STAGES[p_192841_9_]);
/*  96 */           GlStateManager.matrixMode(5890);
/*  97 */           GlStateManager.pushMatrix();
/*  98 */           GlStateManager.scale(8.0F, 4.0F, 1.0F);
/*  99 */           GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/* 100 */           GlStateManager.matrixMode(5888);
/*     */         }
/* 102 */         else if (this.isChristmas) {
/*     */           
/* 104 */           bindTexture(TEXTURE_CHRISTMAS_DOUBLE);
/*     */         }
/* 106 */         else if (p_192841_1_.getChestType() == BlockChest.Type.TRAP) {
/*     */           
/* 108 */           bindTexture(TEXTURE_TRAPPED_DOUBLE);
/*     */         }
/*     */         else {
/*     */           
/* 112 */           bindTexture(TEXTURE_NORMAL_DOUBLE);
/*     */         } 
/*     */       } 
/*     */       
/* 116 */       GlStateManager.pushMatrix();
/* 117 */       GlStateManager.enableRescaleNormal();
/*     */       
/* 119 */       if (p_192841_9_ < 0)
/*     */       {
/* 121 */         GlStateManager.color(1.0F, 1.0F, 1.0F, p_192841_10_);
/*     */       }
/*     */       
/* 124 */       GlStateManager.translate((float)p_192841_2_, (float)p_192841_4_ + 1.0F, (float)p_192841_6_ + 1.0F);
/* 125 */       GlStateManager.scale(1.0F, -1.0F, -1.0F);
/* 126 */       GlStateManager.translate(0.5F, 0.5F, 0.5F);
/* 127 */       int j = 0;
/*     */       
/* 129 */       if (i == 2)
/*     */       {
/* 131 */         j = 180;
/*     */       }
/*     */       
/* 134 */       if (i == 3)
/*     */       {
/* 136 */         j = 0;
/*     */       }
/*     */       
/* 139 */       if (i == 4)
/*     */       {
/* 141 */         j = 90;
/*     */       }
/*     */       
/* 144 */       if (i == 5)
/*     */       {
/* 146 */         j = -90;
/*     */       }
/*     */       
/* 149 */       if (i == 2 && p_192841_1_.adjacentChestXPos != null)
/*     */       {
/* 151 */         GlStateManager.translate(1.0F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 154 */       if (i == 5 && p_192841_1_.adjacentChestZPos != null)
/*     */       {
/* 156 */         GlStateManager.translate(0.0F, 0.0F, -1.0F);
/*     */       }
/*     */       
/* 159 */       GlStateManager.rotate(j, 0.0F, 1.0F, 0.0F);
/* 160 */       GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/* 161 */       float f = p_192841_1_.prevLidAngle + (p_192841_1_.lidAngle - p_192841_1_.prevLidAngle) * p_192841_8_;
/*     */       
/* 163 */       if (p_192841_1_.adjacentChestZNeg != null) {
/*     */         
/* 165 */         float f1 = p_192841_1_.adjacentChestZNeg.prevLidAngle + (p_192841_1_.adjacentChestZNeg.lidAngle - p_192841_1_.adjacentChestZNeg.prevLidAngle) * p_192841_8_;
/*     */         
/* 167 */         if (f1 > f)
/*     */         {
/* 169 */           f = f1;
/*     */         }
/*     */       } 
/*     */       
/* 173 */       if (p_192841_1_.adjacentChestXNeg != null) {
/*     */         
/* 175 */         float f2 = p_192841_1_.adjacentChestXNeg.prevLidAngle + (p_192841_1_.adjacentChestXNeg.lidAngle - p_192841_1_.adjacentChestXNeg.prevLidAngle) * p_192841_8_;
/*     */         
/* 177 */         if (f2 > f)
/*     */         {
/* 179 */           f = f2;
/*     */         }
/*     */       } 
/*     */       
/* 183 */       f = 1.0F - f;
/* 184 */       f = 1.0F - f * f * f;
/* 185 */       modelchest.chestLid.rotateAngleX = -(f * 1.5707964F);
/* 186 */       modelchest.renderAll();
/* 187 */       GlStateManager.disableRescaleNormal();
/* 188 */       GlStateManager.popMatrix();
/* 189 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 191 */       if (p_192841_9_ >= 0) {
/*     */         
/* 193 */         GlStateManager.matrixMode(5890);
/* 194 */         GlStateManager.popMatrix();
/* 195 */         GlStateManager.matrixMode(5888);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */