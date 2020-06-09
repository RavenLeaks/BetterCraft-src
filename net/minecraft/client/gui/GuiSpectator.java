/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*     */ import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class GuiSpectator
/*     */   extends Gui implements ISpectatorMenuRecipient {
/*  16 */   private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");
/*  17 */   public static final ResourceLocation SPECTATOR_WIDGETS = new ResourceLocation("textures/gui/spectator_widgets.png");
/*     */   
/*     */   private final Minecraft mc;
/*     */   private long lastSelectionTime;
/*     */   private SpectatorMenu menu;
/*     */   
/*     */   public GuiSpectator(Minecraft mcIn) {
/*  24 */     this.mc = mcIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onHotbarSelected(int p_175260_1_) {
/*  29 */     this.lastSelectionTime = Minecraft.getSystemTime();
/*     */     
/*  31 */     if (this.menu != null) {
/*     */       
/*  33 */       this.menu.selectSlot(p_175260_1_);
/*     */     }
/*     */     else {
/*     */       
/*  37 */       this.menu = new SpectatorMenu(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float getHotbarAlpha() {
/*  43 */     long i = this.lastSelectionTime - Minecraft.getSystemTime() + 5000L;
/*  44 */     return MathHelper.clamp((float)i / 2000.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderTooltip(ScaledResolution p_175264_1_, float p_175264_2_) {
/*  49 */     if (this.menu != null) {
/*     */       
/*  51 */       float f = getHotbarAlpha();
/*     */       
/*  53 */       if (f <= 0.0F) {
/*     */         
/*  55 */         this.menu.exit();
/*     */       }
/*     */       else {
/*     */         
/*  59 */         int i = ScaledResolution.getScaledWidth() / 2;
/*  60 */         float f1 = this.zLevel;
/*  61 */         this.zLevel = -90.0F;
/*  62 */         float f2 = ScaledResolution.getScaledHeight() - 22.0F * f;
/*  63 */         SpectatorDetails spectatordetails = this.menu.getCurrentPage();
/*  64 */         renderPage(p_175264_1_, f, i, f2, spectatordetails);
/*  65 */         this.zLevel = f1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderPage(ScaledResolution p_175258_1_, float p_175258_2_, int p_175258_3_, float p_175258_4_, SpectatorDetails p_175258_5_) {
/*  72 */     GlStateManager.enableRescaleNormal();
/*  73 */     GlStateManager.enableBlend();
/*  74 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  75 */     GlStateManager.color(1.0F, 1.0F, 1.0F, p_175258_2_);
/*  76 */     this.mc.getTextureManager().bindTexture(WIDGETS);
/*  77 */     drawTexturedModalRect((p_175258_3_ - 91), p_175258_4_, 0, 0, 182, 22);
/*     */     
/*  79 */     if (p_175258_5_.getSelectedSlot() >= 0)
/*     */     {
/*  81 */       drawTexturedModalRect((p_175258_3_ - 91 - 1 + p_175258_5_.getSelectedSlot() * 20), p_175258_4_ - 1.0F, 0, 22, 24, 22);
/*     */     }
/*     */     
/*  84 */     RenderHelper.enableGUIStandardItemLighting();
/*     */     
/*  86 */     for (int i = 0; i < 9; i++)
/*     */     {
/*  88 */       renderSlot(i, ScaledResolution.getScaledWidth() / 2 - 90 + i * 20 + 2, p_175258_4_ + 3.0F, p_175258_2_, p_175258_5_.getObject(i));
/*     */     }
/*     */     
/*  91 */     RenderHelper.disableStandardItemLighting();
/*  92 */     GlStateManager.disableRescaleNormal();
/*  93 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderSlot(int p_175266_1_, int p_175266_2_, float p_175266_3_, float p_175266_4_, ISpectatorMenuObject p_175266_5_) {
/*  98 */     this.mc.getTextureManager().bindTexture(SPECTATOR_WIDGETS);
/*     */     
/* 100 */     if (p_175266_5_ != SpectatorMenu.EMPTY_SLOT) {
/*     */       
/* 102 */       int i = (int)(p_175266_4_ * 255.0F);
/* 103 */       GlStateManager.pushMatrix();
/* 104 */       GlStateManager.translate(p_175266_2_, p_175266_3_, 0.0F);
/* 105 */       float f = p_175266_5_.isEnabled() ? 1.0F : 0.25F;
/* 106 */       GlStateManager.color(f, f, f, p_175266_4_);
/* 107 */       p_175266_5_.renderIcon(f, i);
/* 108 */       GlStateManager.popMatrix();
/* 109 */       String s = String.valueOf(GameSettings.getKeyDisplayString(this.mc.gameSettings.keyBindsHotbar[p_175266_1_].getKeyCode()));
/*     */       
/* 111 */       if (i > 3 && p_175266_5_.isEnabled())
/*     */       {
/* 113 */         this.mc.fontRendererObj.drawStringWithShadow(s, (p_175266_2_ + 19 - 2 - this.mc.fontRendererObj.getStringWidth(s)), p_175266_3_ + 6.0F + 3.0F, 16777215 + (i << 24));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderSelectedItem(ScaledResolution p_175263_1_) {
/* 120 */     int i = (int)(getHotbarAlpha() * 255.0F);
/*     */     
/* 122 */     if (i > 3 && this.menu != null) {
/*     */       
/* 124 */       ISpectatorMenuObject ispectatormenuobject = this.menu.getSelectedItem();
/* 125 */       String s = (ispectatormenuobject == SpectatorMenu.EMPTY_SLOT) ? this.menu.getSelectedCategory().getPrompt().getFormattedText() : ispectatormenuobject.getSpectatorName().getFormattedText();
/*     */       
/* 127 */       if (s != null) {
/*     */         
/* 129 */         int j = (ScaledResolution.getScaledWidth() - this.mc.fontRendererObj.getStringWidth(s)) / 2;
/* 130 */         int k = ScaledResolution.getScaledHeight() - 35;
/* 131 */         GlStateManager.pushMatrix();
/* 132 */         GlStateManager.enableBlend();
/* 133 */         GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 134 */         this.mc.fontRendererObj.drawStringWithShadow(s, j, k, 16777215 + (i << 24));
/* 135 */         GlStateManager.disableBlend();
/* 136 */         GlStateManager.popMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSpectatorMenuClosed(SpectatorMenu p_175257_1_) {
/* 143 */     this.menu = null;
/* 144 */     this.lastSelectionTime = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMenuActive() {
/* 149 */     return (this.menu != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onMouseScroll(int p_175259_1_) {
/*     */     int i;
/* 156 */     for (i = this.menu.getSelectedSlot() + p_175259_1_; i >= 0 && i <= 8 && (this.menu.getItem(i) == SpectatorMenu.EMPTY_SLOT || !this.menu.getItem(i).isEnabled()); i += p_175259_1_);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (i >= 0 && i <= 8) {
/*     */       
/* 163 */       this.menu.selectSlot(i);
/* 164 */       this.lastSelectionTime = Minecraft.getSystemTime();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMiddleClick() {
/* 170 */     this.lastSelectionTime = Minecraft.getSystemTime();
/*     */     
/* 172 */     if (isMenuActive()) {
/*     */       
/* 174 */       int i = this.menu.getSelectedSlot();
/*     */       
/* 176 */       if (i != -1)
/*     */       {
/* 178 */         this.menu.selectSlot(i);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 183 */       this.menu = new SpectatorMenu(this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiSpectator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */