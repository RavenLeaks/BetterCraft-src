/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiNewChat
/*     */   extends Gui
/*     */ {
/*  29 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final Minecraft mc;
/*  31 */   private final List<String> sentMessages = Lists.newArrayList();
/*  32 */   private final List<ChatLine> chatLines = Lists.newArrayList();
/*  33 */   private final List<ChatLine> drawnChatLines = Lists.newArrayList();
/*     */   private int scrollPos;
/*     */   private boolean isScrolled;
/*     */   
/*     */   public GuiNewChat(Minecraft mcIn) {
/*  38 */     this.mc = mcIn;
/*     */   }
/*     */   
/*     */   public void drawChat(int updateCounter) {
/*  42 */     if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
/*  43 */       int i = getLineCount();
/*  44 */       int j = this.drawnChatLines.size();
/*  45 */       float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
/*  46 */       if (j > 0) {
/*  47 */         boolean flag = false;
/*  48 */         if (getChatOpen()) {
/*  49 */           flag = true;
/*     */         }
/*  51 */         float f1 = getChatScale();
/*  52 */         int k = MathHelper.ceil(getChatWidth() / f1);
/*  53 */         GlStateManager.pushMatrix();
/*  54 */         GlStateManager.translate(2.0F, 8.0F, 0.0F);
/*  55 */         GlStateManager.scale(f1, f1, 1.0F);
/*  56 */         int l = 0;
/*  57 */         for (int i1 = 0; i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i; i1++) {
/*     */           
/*  59 */           ChatLine chatline = this.drawnChatLines.get(i1 + this.scrollPos); int j1;
/*  60 */           if (chatline != null && ((j1 = updateCounter - chatline.getUpdatedCounter()) < 200 || flag)) {
/*  61 */             double d0 = j1 / 200.0D;
/*  62 */             d0 = 1.0D - d0;
/*  63 */             d0 *= 10.0D;
/*  64 */             d0 = MathHelper.clamp(d0, 0.0D, 1.0D);
/*  65 */             d0 *= d0;
/*  66 */             int l1 = (int)(255.0D * d0);
/*  67 */             if (flag) {
/*  68 */               l1 = 255;
/*     */             }
/*  70 */             l1 = (int)(l1 * f);
/*  71 */             l++;
/*  72 */             if (l1 > 3)
/*  73 */             { boolean i2 = false;
/*  74 */               int j2 = -i1 * 9 + 7;
/*  75 */               drawRect(-2, j2 - 9, 0 + k + 10, j2, l1 / 2 << 24);
/*  76 */               String s2 = chatline.getChatComponent().getFormattedText();
/*  77 */               GlStateManager.enableBlend();
/*  78 */               if (chatline.slide < 0) {
/*  79 */                 chatline.slide += 5;
/*     */               }
/*  81 */               this.mc.fontRendererObj.drawStringWithShadow(s2, chatline.slide, (j2 - 8), 16777215);
/*  82 */               GlStateManager.disableAlpha();
/*  83 */               GlStateManager.disableBlend(); } 
/*     */           } 
/*  85 */         }  if (flag) {
/*  86 */           int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
/*  87 */           GlStateManager.translate(-3.0F, 0.0F, 0.0F);
/*  88 */           int l2 = j * k2 + j;
/*  89 */           int i3 = l * k2 + l;
/*  90 */           int j3 = this.scrollPos * i3 / j;
/*  91 */           int m = i3 * i3 / l2;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  96 */         GlStateManager.popMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearChatMessages(boolean p_146231_1_) {
/* 102 */     this.drawnChatLines.clear();
/* 103 */     this.chatLines.clear();
/* 104 */     if (p_146231_1_) {
/* 105 */       this.sentMessages.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public void printChatMessage(ITextComponent chatComponent) {
/* 110 */     printChatMessageWithOptionalDeletion(chatComponent, 0);
/*     */   }
/*     */   
/*     */   public void printChatMessageWithOptionalDeletion(ITextComponent chatComponent, int chatLineId) {
/* 114 */     setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
/* 115 */     LOGGER.info("[CHAT] {}", chatComponent.getUnformattedText().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
/*     */   }
/*     */   
/*     */   private void setChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
/* 119 */     if (chatLineId != 0) {
/* 120 */       deleteChatLine(chatLineId);
/*     */     }
/* 122 */     int i = MathHelper.floor(getChatWidth() / getChatScale());
/* 123 */     List<ITextComponent> list2 = GuiUtilRenderComponents.splitText(chatComponent, i, this.mc.fontRendererObj, false, false);
/* 124 */     boolean flag = getChatOpen();
/* 125 */     for (ITextComponent itextcomponent : list2) {
/* 126 */       if (flag && this.scrollPos > 0) {
/* 127 */         this.isScrolled = true;
/* 128 */         scroll(1);
/*     */       } 
/* 130 */       this.drawnChatLines.add(0, new ChatLine(updateCounter, itextcomponent, chatLineId));
/*     */     } 
/* 132 */     while (this.drawnChatLines.size() > 100) {
/* 133 */       this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
/*     */     }
/* 135 */     if (!displayOnly) {
/* 136 */       this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
/* 137 */       while (this.chatLines.size() > 100) {
/* 138 */         this.chatLines.remove(this.chatLines.size() - 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refreshChat() {
/* 144 */     this.drawnChatLines.clear();
/* 145 */     resetScroll();
/* 146 */     for (int i = this.chatLines.size() - 1; i >= 0; i--) {
/* 147 */       ChatLine chatline = this.chatLines.get(i);
/* 148 */       setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String> getSentMessages() {
/* 153 */     return this.sentMessages;
/*     */   }
/*     */   
/*     */   public void addToSentMessages(String message) {
/* 157 */     if (this.sentMessages.isEmpty() || !((String)this.sentMessages.get(this.sentMessages.size() - 1)).equals(message)) {
/* 158 */       this.sentMessages.add(message);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetScroll() {
/* 163 */     this.scrollPos = 0;
/* 164 */     this.isScrolled = false;
/*     */   }
/*     */   
/*     */   public void scroll(int amount) {
/* 168 */     this.scrollPos += amount;
/* 169 */     int i = this.drawnChatLines.size();
/* 170 */     if (this.scrollPos > i - getLineCount()) {
/* 171 */       this.scrollPos = i - getLineCount();
/*     */     }
/* 173 */     if (this.scrollPos <= 0) {
/* 174 */       this.scrollPos = 0;
/* 175 */       this.isScrolled = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent getChatComponent(int mouseX, int mouseY) {
/* 181 */     if (!getChatOpen())
/*     */     {
/* 183 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 187 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 188 */     int i = scaledresolution.getScaleFactor();
/* 189 */     float f = getChatScale();
/* 190 */     int j = mouseX / i - 2;
/*     */     
/* 192 */     int k = mouseY / i - 62;
/*     */     
/* 194 */     j = MathHelper.floor(j / f);
/* 195 */     k = MathHelper.floor(k / f);
/*     */     
/* 197 */     if (j >= 0 && k >= 0) {
/*     */       
/* 199 */       int l = Math.min(getLineCount(), this.drawnChatLines.size());
/*     */       
/* 201 */       if (j <= MathHelper.floor(getChatWidth() / getChatScale()) && k < this.mc.fontRendererObj.FONT_HEIGHT * l + l) {
/*     */         
/* 203 */         int i1 = k / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;
/*     */         
/* 205 */         if (i1 >= 0 && i1 < this.drawnChatLines.size()) {
/*     */           
/* 207 */           ChatLine chatline = this.drawnChatLines.get(i1);
/* 208 */           int j1 = 0;
/*     */           
/* 210 */           for (ITextComponent itextcomponent : chatline.getChatComponent()) {
/*     */             
/* 212 */             if (itextcomponent instanceof TextComponentString) {
/*     */               
/* 214 */               j1 += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.removeTextColorsIfConfigured(((TextComponentString)itextcomponent).getText(), false));
/*     */               
/* 216 */               if (j1 > j)
/*     */               {
/* 218 */                 return itextcomponent;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 224 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 228 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 233 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getChatOpen() {
/* 239 */     return this.mc.currentScreen instanceof GuiChat;
/*     */   }
/*     */   
/*     */   public void deleteChatLine(int id) {
/* 243 */     Iterator<ChatLine> iterator2 = this.drawnChatLines.iterator();
/* 244 */     while (iterator2.hasNext()) {
/* 245 */       ChatLine chatline = iterator2.next();
/* 246 */       if (chatline.getChatLineID() != id)
/* 247 */         continue;  iterator2.remove();
/*     */     } 
/* 249 */     iterator2 = this.chatLines.iterator();
/* 250 */     while (iterator2.hasNext()) {
/* 251 */       ChatLine chatline1 = iterator2.next();
/* 252 */       if (chatline1.getChatLineID() != id)
/* 253 */         continue;  iterator2.remove();
/*     */       break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getChatWidth() {
/* 259 */     return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
/*     */   }
/*     */   
/*     */   public int getChatHeight() {
/* 263 */     return calculateChatboxHeight(getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
/*     */   }
/*     */   
/*     */   public float getChatScale() {
/* 267 */     return this.mc.gameSettings.chatScale;
/*     */   }
/*     */   
/*     */   public static int calculateChatboxWidth(float scale) {
/* 271 */     int i = 320;
/* 272 */     int j = 40;
/* 273 */     return MathHelper.floor(scale * 280.0F + 40.0F);
/*     */   }
/*     */   
/*     */   public static int calculateChatboxHeight(float scale) {
/* 277 */     int i = 180;
/* 278 */     int j = 20;
/* 279 */     return MathHelper.floor(scale * 160.0F + 20.0F);
/*     */   }
/*     */   
/*     */   public int getLineCount() {
/* 283 */     return getChatHeight() / 9;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiNewChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */