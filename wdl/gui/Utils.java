/*     */ package wdl.gui;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class Utils
/*     */ {
/*  32 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*  33 */   private static final Logger logger = LogManager.getLogger();
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
/*     */ 
/*     */   
/*     */   public static void drawGuiInfoBox(String text, int guiWidth, int guiHeight, int bottomPadding) {
/*  49 */     drawGuiInfoBox(text, 300, 100, guiWidth, guiHeight, bottomPadding);
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawGuiInfoBox(String text, int infoBoxWidth, int infoBoxHeight, int guiWidth, int guiHeight, int bottomPadding) {
/*  70 */     if (text == null) {
/*     */       return;
/*     */     }
/*     */     
/*  74 */     int infoX = guiWidth / 2 - infoBoxWidth / 2;
/*  75 */     int infoY = guiHeight - bottomPadding - infoBoxHeight;
/*  76 */     int y = infoY + 5;
/*     */     
/*  78 */     GuiScreen.drawRect(infoX, infoY, infoX + infoBoxWidth, infoY + 
/*  79 */         infoBoxHeight, 2130706432);
/*     */     
/*  81 */     List<String> lines = wordWrap(text, infoBoxWidth - 10);
/*     */     
/*  83 */     for (String s : lines) {
/*  84 */       mc.fontRendererObj.drawString(s, infoX + 5, y, 16777215);
/*  85 */       y += mc.fontRendererObj.FONT_HEIGHT;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> wordWrap(String s, int width) {
/*  98 */     s = s.replace("\r", "");
/*     */     
/* 100 */     List<String> lines = mc.fontRendererObj.listFormattedStringToWidth(s, width);
/*     */     
/* 102 */     return lines;
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawListBackground(int topMargin, int bottomMargin, int top, int left, int bottom, int right) {
/* 121 */     drawDarkBackground(top, left, bottom, right);
/* 122 */     drawBorder(topMargin, bottomMargin, top, left, bottom, right);
/*     */   }
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
/*     */   public static void drawDarkBackground(int top, int left, int bottom, int right) {
/* 135 */     GlStateManager.disableLighting();
/* 136 */     GlStateManager.disableFog();
/*     */     
/* 138 */     Tessellator t = Tessellator.getInstance();
/* 139 */     BufferBuilder b = t.getBuffer();
/*     */     
/* 141 */     mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
/* 142 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 144 */     float textureSize = 32.0F;
/* 145 */     b.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 146 */     b.pos(0.0D, bottom, 0.0D).tex((0.0F / textureSize), (
/* 147 */         bottom / textureSize)).color(32, 32, 32, 255).endVertex();
/* 148 */     b.pos(right, bottom, 0.0D).tex((right / textureSize), (
/* 149 */         bottom / textureSize)).color(32, 32, 32, 255).endVertex();
/* 150 */     b.pos(right, top, 0.0D).tex((right / textureSize), (
/* 151 */         top / textureSize)).color(32, 32, 32, 255).endVertex();
/* 152 */     b.pos(left, top, 0.0D).tex((left / textureSize), (
/* 153 */         top / textureSize)).color(32, 32, 32, 255).endVertex();
/* 154 */     t.draw();
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawBorder(int topMargin, int bottomMargin, int top, int left, int bottom, int right) {
/* 173 */     GlStateManager.disableLighting();
/* 174 */     GlStateManager.disableFog();
/* 175 */     GlStateManager.disableDepth();
/* 176 */     byte padding = 4;
/*     */     
/* 178 */     mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
/* 179 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 181 */     float textureSize = 32.0F;
/*     */     
/* 183 */     Tessellator t = Tessellator.getInstance();
/* 184 */     BufferBuilder b = t.getBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     int upperBoxEnd = top + topMargin;
/*     */     
/* 193 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 194 */     b.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 195 */     b.pos(left, upperBoxEnd, 0.0D).tex(0.0D, (upperBoxEnd / 
/* 196 */         textureSize)).color(64, 64, 64, 255).endVertex();
/* 197 */     b.pos(right, upperBoxEnd, 0.0D).tex((right / textureSize), (
/* 198 */         upperBoxEnd / textureSize)).color(64, 64, 64, 255).endVertex();
/* 199 */     b.pos(right, top, 0.0D).tex((right / textureSize), (top / textureSize))
/* 200 */       .color(64, 64, 64, 255).endVertex();
/* 201 */     b.pos(left, top, 0.0D).tex(0.0D, (top / textureSize))
/* 202 */       .color(64, 64, 64, 255).endVertex();
/* 203 */     t.draw();
/*     */ 
/*     */     
/* 206 */     int lowerBoxStart = bottom - bottomMargin;
/*     */     
/* 208 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 209 */     b.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 210 */     b.pos(left, bottom, 0.0D).tex(0.0D, (bottom / textureSize))
/* 211 */       .color(64, 64, 64, 255).endVertex();
/* 212 */     b.pos(right, bottom, 0.0D).tex((right / textureSize), (bottom / 
/* 213 */         textureSize)).color(64, 64, 64, 255).endVertex();
/* 214 */     b.pos(right, lowerBoxStart, 0.0D)
/* 215 */       .tex((right / textureSize), (lowerBoxStart / textureSize))
/* 216 */       .color(64, 64, 64, 255).endVertex();
/* 217 */     b.pos(left, lowerBoxStart, 0.0D).tex(0.0D, (lowerBoxStart / 
/* 218 */         textureSize)).color(64, 64, 64, 255).endVertex();
/* 219 */     t.draw();
/*     */ 
/*     */     
/* 222 */     GlStateManager.enableBlend();
/* 223 */     GlStateManager.tryBlendFuncSeparate(770, 
/* 224 */         771, 0, 1);
/* 225 */     GlStateManager.disableAlpha();
/* 226 */     GlStateManager.shadeModel(7425);
/* 227 */     GlStateManager.disableTexture2D();
/* 228 */     b.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 229 */     b.pos(left, (upperBoxEnd + padding), 0.0D).tex(0.0D, 1.0D)
/* 230 */       .color(0, 0, 0, 0).endVertex();
/* 231 */     b.pos(right, (upperBoxEnd + padding), 0.0D).tex(1.0D, 1.0D)
/* 232 */       .color(0, 0, 0, 0).endVertex();
/* 233 */     b.pos(right, upperBoxEnd, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255)
/* 234 */       .endVertex();
/* 235 */     b.pos(left, upperBoxEnd, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255)
/* 236 */       .endVertex();
/* 237 */     t.draw();
/* 238 */     b.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 239 */     b.pos(left, lowerBoxStart, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255)
/* 240 */       .endVertex();
/* 241 */     b.pos(right, lowerBoxStart, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255)
/* 242 */       .endVertex();
/* 243 */     b.pos(right, (lowerBoxStart - padding), 0.0D).tex(1.0D, 0.0D)
/* 244 */       .color(0, 0, 0, 0).endVertex();
/* 245 */     b.pos(left, (lowerBoxStart - padding), 0.0D).tex(0.0D, 0.0D)
/* 246 */       .color(0, 0, 0, 0).endVertex();
/* 247 */     t.draw();
/*     */     
/* 249 */     GlStateManager.enableTexture2D();
/* 250 */     GlStateManager.shadeModel(7424);
/* 251 */     GlStateManager.enableAlpha();
/* 252 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMouseOverTextBox(int mouseX, int mouseY, GuiTextField textBox) {
/* 264 */     int scaledX = mouseX - textBox.xPosition;
/* 265 */     int scaledY = mouseY - textBox.yPosition;
/*     */ 
/*     */ 
/*     */     
/* 269 */     int height = 20;
/*     */     
/* 271 */     return (scaledX >= 0 && scaledX < textBox.getWidth() && scaledY >= 0 && 
/* 272 */       scaledY < 20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openLink(String path) {
/*     */     try {
/* 281 */       Class<?> desktopClass = Class.forName("java.awt.Desktop");
/* 282 */       Object desktop = desktopClass.getMethod("getDesktop", new Class[0]).invoke(
/* 283 */           null, new Object[0]);
/* 284 */       desktopClass.getMethod("browse", new Class[] { URI.class }).invoke(desktop, new Object[] {
/* 285 */             new URI(path) });
/* 286 */     } catch (Throwable e) {
/* 287 */       logger.error("Couldn't open link", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawStringWithShadow(String s, int x, int y, int color) {
/* 298 */     mc.fontRendererObj.drawStringWithShadow(s, x, y, color);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */