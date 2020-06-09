/*     */ package net.minecraft.client.gui.advancements;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.multiplayer.ClientAdvancementManager;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketSeenAdvancements;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiScreenAdvancements extends GuiScreen implements ClientAdvancementManager.IListener {
/*  21 */   private static final ResourceLocation field_191943_f = new ResourceLocation("textures/gui/advancements/window.png");
/*  22 */   private static final ResourceLocation field_191945_g = new ResourceLocation("textures/gui/advancements/tabs.png");
/*     */   private final ClientAdvancementManager field_191946_h;
/*  24 */   private final Map<Advancement, GuiAdvancementTab> field_191947_i = Maps.newLinkedHashMap();
/*     */   
/*     */   private GuiAdvancementTab field_191940_s;
/*     */   private int field_191941_t;
/*     */   private int field_191942_u;
/*     */   private boolean field_191944_v;
/*     */   
/*     */   public GuiScreenAdvancements(ClientAdvancementManager p_i47383_1_) {
/*  32 */     this.field_191946_h = p_i47383_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  41 */     this.field_191947_i.clear();
/*  42 */     this.field_191940_s = null;
/*  43 */     this.field_191946_h.func_192798_a(this);
/*     */     
/*  45 */     if (this.field_191940_s == null && !this.field_191947_i.isEmpty()) {
/*     */       
/*  47 */       this.field_191946_h.func_194230_a(((GuiAdvancementTab)this.field_191947_i.values().iterator().next()).func_193935_c(), true);
/*     */     }
/*     */     else {
/*     */       
/*  51 */       this.field_191946_h.func_194230_a((this.field_191940_s == null) ? null : this.field_191940_s.func_193935_c(), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  60 */     this.field_191946_h.func_192798_a(null);
/*  61 */     NetHandlerPlayClient nethandlerplayclient = this.mc.getConnection();
/*     */     
/*  63 */     if (nethandlerplayclient != null)
/*     */     {
/*  65 */       nethandlerplayclient.sendPacket((Packet)CPacketSeenAdvancements.func_194164_a());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  74 */     if (mouseButton == 0) {
/*     */       
/*  76 */       int i = (this.width - 252) / 2;
/*  77 */       int j = (this.height - 140) / 2;
/*     */       
/*  79 */       for (GuiAdvancementTab guiadvancementtab : this.field_191947_i.values()) {
/*     */         
/*  81 */         if (guiadvancementtab.func_191793_c(i, j, mouseX, mouseY)) {
/*     */           
/*  83 */           this.field_191946_h.func_194230_a(guiadvancementtab.func_193935_c(), true);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  89 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  98 */     if (keyCode == this.mc.gameSettings.field_194146_ao.getKeyCode()) {
/*     */       
/* 100 */       this.mc.displayGuiScreen(null);
/* 101 */       this.mc.setIngameFocus();
/*     */     }
/*     */     else {
/*     */       
/* 105 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 114 */     int i = (this.width - 252) / 2;
/* 115 */     int j = (this.height - 140) / 2;
/*     */     
/* 117 */     if (Mouse.isButtonDown(0)) {
/*     */       
/* 119 */       if (!this.field_191944_v) {
/*     */         
/* 121 */         this.field_191944_v = true;
/*     */       }
/* 123 */       else if (this.field_191940_s != null) {
/*     */         
/* 125 */         this.field_191940_s.func_191797_b(mouseX - this.field_191941_t, mouseY - this.field_191942_u);
/*     */       } 
/*     */       
/* 128 */       this.field_191941_t = mouseX;
/* 129 */       this.field_191942_u = mouseY;
/*     */     }
/*     */     else {
/*     */       
/* 133 */       this.field_191944_v = false;
/*     */     } 
/*     */     
/* 136 */     drawDefaultBackground();
/* 137 */     func_191936_c(mouseX, mouseY, i, j);
/* 138 */     func_191934_b(i, j);
/* 139 */     func_191937_d(mouseX, mouseY, i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_191936_c(int p_191936_1_, int p_191936_2_, int p_191936_3_, int p_191936_4_) {
/* 144 */     GuiAdvancementTab guiadvancementtab = this.field_191940_s;
/*     */     
/* 146 */     if (guiadvancementtab == null) {
/*     */       
/* 148 */       drawRect(p_191936_3_ + 9, p_191936_4_ + 18, p_191936_3_ + 9 + 234, p_191936_4_ + 18 + 113, -16777216);
/* 149 */       String s = I18n.format("advancements.empty", new Object[0]);
/* 150 */       int i = this.fontRendererObj.getStringWidth(s);
/* 151 */       this.fontRendererObj.drawString(s, p_191936_3_ + 9 + 117 - i / 2, p_191936_4_ + 18 + 56 - this.fontRendererObj.FONT_HEIGHT / 2, -1);
/* 152 */       this.fontRendererObj.drawString(":(", p_191936_3_ + 9 + 117 - this.fontRendererObj.getStringWidth(":(") / 2, p_191936_4_ + 18 + 113 - this.fontRendererObj.FONT_HEIGHT, -1);
/*     */     }
/*     */     else {
/*     */       
/* 156 */       GlStateManager.pushMatrix();
/* 157 */       GlStateManager.translate((p_191936_3_ + 9), (p_191936_4_ + 18), -400.0F);
/* 158 */       GlStateManager.enableDepth();
/* 159 */       guiadvancementtab.func_191799_a();
/* 160 */       GlStateManager.popMatrix();
/* 161 */       GlStateManager.depthFunc(515);
/* 162 */       GlStateManager.disableDepth();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191934_b(int p_191934_1_, int p_191934_2_) {
/* 168 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 169 */     GlStateManager.enableBlend();
/* 170 */     RenderHelper.disableStandardItemLighting();
/* 171 */     this.mc.getTextureManager().bindTexture(field_191943_f);
/* 172 */     drawTexturedModalRect(p_191934_1_, p_191934_2_, 0, 0, 252, 140);
/*     */     
/* 174 */     if (this.field_191947_i.size() > 1) {
/*     */       
/* 176 */       this.mc.getTextureManager().bindTexture(field_191945_g);
/*     */       
/* 178 */       for (GuiAdvancementTab guiadvancementtab : this.field_191947_i.values())
/*     */       {
/* 180 */         guiadvancementtab.func_191798_a(p_191934_1_, p_191934_2_, (guiadvancementtab == this.field_191940_s));
/*     */       }
/*     */       
/* 183 */       GlStateManager.enableRescaleNormal();
/* 184 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 185 */       RenderHelper.enableGUIStandardItemLighting();
/*     */       
/* 187 */       for (GuiAdvancementTab guiadvancementtab1 : this.field_191947_i.values())
/*     */       {
/* 189 */         guiadvancementtab1.func_191796_a(p_191934_1_, p_191934_2_, this.itemRender);
/*     */       }
/*     */       
/* 192 */       GlStateManager.disableBlend();
/*     */     } 
/*     */     
/* 195 */     this.fontRendererObj.drawString(I18n.format("gui.advancements", new Object[0]), p_191934_1_ + 8, p_191934_2_ + 6, 4210752);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_191937_d(int p_191937_1_, int p_191937_2_, int p_191937_3_, int p_191937_4_) {
/* 200 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 202 */     if (this.field_191940_s != null) {
/*     */       
/* 204 */       GlStateManager.pushMatrix();
/* 205 */       GlStateManager.enableDepth();
/* 206 */       GlStateManager.translate((p_191937_3_ + 9), (p_191937_4_ + 18), 400.0F);
/* 207 */       this.field_191940_s.func_192991_a(p_191937_1_ - p_191937_3_ - 9, p_191937_2_ - p_191937_4_ - 18, p_191937_3_, p_191937_4_);
/* 208 */       GlStateManager.disableDepth();
/* 209 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/* 212 */     if (this.field_191947_i.size() > 1)
/*     */     {
/* 214 */       for (GuiAdvancementTab guiadvancementtab : this.field_191947_i.values()) {
/*     */         
/* 216 */         if (guiadvancementtab.func_191793_c(p_191937_3_, p_191937_4_, p_191937_1_, p_191937_2_))
/*     */         {
/* 218 */           drawCreativeTabHoveringText(guiadvancementtab.func_191795_d(), p_191937_1_, p_191937_2_);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191931_a(Advancement p_191931_1_) {
/* 226 */     GuiAdvancementTab guiadvancementtab = GuiAdvancementTab.func_193936_a(this.mc, this, this.field_191947_i.size(), p_191931_1_);
/*     */     
/* 228 */     if (guiadvancementtab != null)
/*     */     {
/* 230 */       this.field_191947_i.put(p_191931_1_, guiadvancementtab);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_191928_b(Advancement p_191928_1_) {}
/*     */ 
/*     */   
/*     */   public void func_191932_c(Advancement p_191932_1_) {
/* 240 */     GuiAdvancementTab guiadvancementtab = func_191935_f(p_191932_1_);
/*     */     
/* 242 */     if (guiadvancementtab != null)
/*     */     {
/* 244 */       guiadvancementtab.func_191800_a(p_191932_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_191929_d(Advancement p_191929_1_) {}
/*     */ 
/*     */   
/*     */   public void func_191933_a(Advancement p_191933_1_, AdvancementProgress p_191933_2_) {
/* 254 */     GuiAdvancement guiadvancement = func_191938_e(p_191933_1_);
/*     */     
/* 256 */     if (guiadvancement != null)
/*     */     {
/* 258 */       guiadvancement.func_191824_a(p_191933_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193982_e(@Nullable Advancement p_193982_1_) {
/* 264 */     this.field_191940_s = this.field_191947_i.get(p_193982_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191930_a() {
/* 269 */     this.field_191947_i.clear();
/* 270 */     this.field_191940_s = null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GuiAdvancement func_191938_e(Advancement p_191938_1_) {
/* 276 */     GuiAdvancementTab guiadvancementtab = func_191935_f(p_191938_1_);
/* 277 */     return (guiadvancementtab == null) ? null : guiadvancementtab.func_191794_b(p_191938_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private GuiAdvancementTab func_191935_f(Advancement p_191935_1_) {
/* 283 */     while (p_191935_1_.func_192070_b() != null)
/*     */     {
/* 285 */       p_191935_1_ = p_191935_1_.func_192070_b();
/*     */     }
/*     */     
/* 288 */     return this.field_191947_i.get(p_191935_1_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\advancements\GuiScreenAdvancements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */