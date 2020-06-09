/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import net.minecraft.block.BlockShulkerBox;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.model.ModelShulker;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderShulker;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class TileEntityShulkerBoxRenderer extends TileEntitySpecialRenderer<TileEntityShulkerBox> {
/*     */   private final ModelShulker field_191285_a;
/*     */   
/*     */   public TileEntityShulkerBoxRenderer(ModelShulker p_i47216_1_) {
/*  17 */     this.field_191285_a = p_i47216_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192841_a(TileEntityShulkerBox p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/*  22 */     EnumFacing enumfacing = EnumFacing.UP;
/*     */     
/*  24 */     if (p_192841_1_.hasWorldObj()) {
/*     */       
/*  26 */       IBlockState iblockstate = getWorld().getBlockState(p_192841_1_.getPos());
/*     */       
/*  28 */       if (iblockstate.getBlock() instanceof BlockShulkerBox)
/*     */       {
/*  30 */         enumfacing = (EnumFacing)iblockstate.getValue((IProperty)BlockShulkerBox.field_190957_a);
/*     */       }
/*     */     } 
/*     */     
/*  34 */     GlStateManager.enableDepth();
/*  35 */     GlStateManager.depthFunc(515);
/*  36 */     GlStateManager.depthMask(true);
/*  37 */     GlStateManager.disableCull();
/*     */     
/*  39 */     if (p_192841_9_ >= 0) {
/*     */       
/*  41 */       bindTexture(DESTROY_STAGES[p_192841_9_]);
/*  42 */       GlStateManager.matrixMode(5890);
/*  43 */       GlStateManager.pushMatrix();
/*  44 */       GlStateManager.scale(4.0F, 4.0F, 1.0F);
/*  45 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  46 */       GlStateManager.matrixMode(5888);
/*     */     }
/*     */     else {
/*     */       
/*  50 */       bindTexture(RenderShulker.SHULKER_ENDERGOLEM_TEXTURE[p_192841_1_.func_190592_s().getMetadata()]);
/*     */     } 
/*     */     
/*  53 */     GlStateManager.pushMatrix();
/*  54 */     GlStateManager.enableRescaleNormal();
/*     */     
/*  56 */     if (p_192841_9_ < 0)
/*     */     {
/*  58 */       GlStateManager.color(1.0F, 1.0F, 1.0F, p_192841_10_);
/*     */     }
/*     */     
/*  61 */     GlStateManager.translate((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 1.5F, (float)p_192841_6_ + 0.5F);
/*  62 */     GlStateManager.scale(1.0F, -1.0F, -1.0F);
/*  63 */     GlStateManager.translate(0.0F, 1.0F, 0.0F);
/*  64 */     float f = 0.9995F;
/*  65 */     GlStateManager.scale(0.9995F, 0.9995F, 0.9995F);
/*  66 */     GlStateManager.translate(0.0F, -1.0F, 0.0F);
/*     */     
/*  68 */     switch (enumfacing) {
/*     */       
/*     */       case null:
/*  71 */         GlStateManager.translate(0.0F, 2.0F, 0.0F);
/*  72 */         GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case NORTH:
/*  79 */         GlStateManager.translate(0.0F, 1.0F, 1.0F);
/*  80 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*  81 */         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*  85 */         GlStateManager.translate(0.0F, 1.0F, -1.0F);
/*  86 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*     */         break;
/*     */       
/*     */       case WEST:
/*  90 */         GlStateManager.translate(-1.0F, 1.0F, 0.0F);
/*  91 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*  92 */         GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/*     */         break;
/*     */       
/*     */       case EAST:
/*  96 */         GlStateManager.translate(1.0F, 1.0F, 0.0F);
/*  97 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*  98 */         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/*     */         break;
/*     */     } 
/* 101 */     this.field_191285_a.base.render(0.0625F);
/* 102 */     GlStateManager.translate(0.0F, -p_192841_1_.func_190585_a(p_192841_8_) * 0.5F, 0.0F);
/* 103 */     GlStateManager.rotate(270.0F * p_192841_1_.func_190585_a(p_192841_8_), 0.0F, 1.0F, 0.0F);
/* 104 */     this.field_191285_a.lid.render(0.0625F);
/* 105 */     GlStateManager.enableCull();
/* 106 */     GlStateManager.disableRescaleNormal();
/* 107 */     GlStateManager.popMatrix();
/* 108 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 110 */     if (p_192841_9_ >= 0) {
/*     */       
/* 112 */       GlStateManager.matrixMode(5890);
/* 113 */       GlStateManager.popMatrix();
/* 114 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityShulkerBoxRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */