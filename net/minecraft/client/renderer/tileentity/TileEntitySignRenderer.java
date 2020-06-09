/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiUtilRenderComponents;
/*     */ import net.minecraft.client.model.ModelSign;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import optifine.Config;
/*     */ import optifine.CustomColors;
/*     */ 
/*     */ public class TileEntitySignRenderer extends TileEntitySpecialRenderer<TileEntitySign> {
/*  18 */   private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
/*     */ 
/*     */   
/*  21 */   private final ModelSign model = new ModelSign();
/*     */ 
/*     */   
/*     */   public void func_192841_a(TileEntitySign p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/*  25 */     Block block = p_192841_1_.getBlockType();
/*  26 */     GlStateManager.pushMatrix();
/*  27 */     float f = 0.6666667F;
/*     */     
/*  29 */     if (block == Blocks.STANDING_SIGN) {
/*     */       
/*  31 */       GlStateManager.translate((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 0.5F, (float)p_192841_6_ + 0.5F);
/*  32 */       float f1 = (p_192841_1_.getBlockMetadata() * 360) / 16.0F;
/*  33 */       GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
/*  34 */       this.model.signStick.showModel = true;
/*     */     }
/*     */     else {
/*     */       
/*  38 */       int k = p_192841_1_.getBlockMetadata();
/*  39 */       float f2 = 0.0F;
/*     */       
/*  41 */       if (k == 2)
/*     */       {
/*  43 */         f2 = 180.0F;
/*     */       }
/*     */       
/*  46 */       if (k == 4)
/*     */       {
/*  48 */         f2 = 90.0F;
/*     */       }
/*     */       
/*  51 */       if (k == 5)
/*     */       {
/*  53 */         f2 = -90.0F;
/*     */       }
/*     */       
/*  56 */       GlStateManager.translate((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 0.5F, (float)p_192841_6_ + 0.5F);
/*  57 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  58 */       GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
/*  59 */       this.model.signStick.showModel = false;
/*     */     } 
/*     */     
/*  62 */     if (p_192841_9_ >= 0) {
/*     */       
/*  64 */       bindTexture(DESTROY_STAGES[p_192841_9_]);
/*  65 */       GlStateManager.matrixMode(5890);
/*  66 */       GlStateManager.pushMatrix();
/*  67 */       GlStateManager.scale(4.0F, 2.0F, 1.0F);
/*  68 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  69 */       GlStateManager.matrixMode(5888);
/*     */     }
/*     */     else {
/*     */       
/*  73 */       bindTexture(SIGN_TEXTURE);
/*     */     } 
/*     */     
/*  76 */     GlStateManager.enableRescaleNormal();
/*  77 */     GlStateManager.pushMatrix();
/*  78 */     GlStateManager.scale(0.6666667F, -0.6666667F, -0.6666667F);
/*  79 */     this.model.renderSign();
/*  80 */     GlStateManager.popMatrix();
/*  81 */     FontRenderer fontrenderer = getFontRenderer();
/*  82 */     float f3 = 0.010416667F;
/*  83 */     GlStateManager.translate(0.0F, 0.33333334F, 0.046666667F);
/*  84 */     GlStateManager.scale(0.010416667F, -0.010416667F, 0.010416667F);
/*  85 */     GlStateManager.glNormal3f(0.0F, 0.0F, -0.010416667F);
/*  86 */     GlStateManager.depthMask(false);
/*  87 */     int i = 0;
/*     */     
/*  89 */     if (Config.isCustomColors())
/*     */     {
/*  91 */       i = CustomColors.getSignTextColor(i);
/*     */     }
/*     */     
/*  94 */     if (p_192841_9_ < 0)
/*     */     {
/*  96 */       for (int j = 0; j < p_192841_1_.signText.length; j++) {
/*     */         
/*  98 */         if (p_192841_1_.signText[j] != null) {
/*     */           
/* 100 */           ITextComponent itextcomponent = p_192841_1_.signText[j];
/* 101 */           List<ITextComponent> list = GuiUtilRenderComponents.splitText(itextcomponent, 90, fontrenderer, false, true);
/* 102 */           String s = (list != null && !list.isEmpty()) ? ((ITextComponent)list.get(0)).getFormattedText() : "";
/*     */           
/* 104 */           if (j == p_192841_1_.lineBeingEdited) {
/*     */             
/* 106 */             s = "> " + s + " <";
/* 107 */             fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - p_192841_1_.signText.length * 5, i);
/*     */           }
/*     */           else {
/*     */             
/* 111 */             fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - p_192841_1_.signText.length * 5, i);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 117 */     GlStateManager.depthMask(true);
/* 118 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 119 */     GlStateManager.popMatrix();
/*     */     
/* 121 */     if (p_192841_9_ >= 0) {
/*     */       
/* 123 */       GlStateManager.matrixMode(5890);
/* 124 */       GlStateManager.popMatrix();
/* 125 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntitySignRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */