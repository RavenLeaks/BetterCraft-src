/*     */ package optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiVideoSettings;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ public class TooltipManager
/*     */ {
/*  14 */   private GuiScreen guiScreen = null;
/*  15 */   private int lastMouseX = 0;
/*  16 */   private int lastMouseY = 0;
/*  17 */   private long mouseStillTime = 0L;
/*     */ 
/*     */   
/*     */   public TooltipManager(GuiScreen p_i99_1_) {
/*  21 */     this.guiScreen = p_i99_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawTooltips(int p_drawTooltips_1_, int p_drawTooltips_2_, List p_drawTooltips_3_) {
/*  26 */     if (Math.abs(p_drawTooltips_1_ - this.lastMouseX) <= 5 && Math.abs(p_drawTooltips_2_ - this.lastMouseY) <= 5) {
/*     */       
/*  28 */       int i = 700;
/*     */       
/*  30 */       if (System.currentTimeMillis() >= this.mouseStillTime + i) {
/*     */         
/*  32 */         int j = this.guiScreen.width / 2 - 150;
/*  33 */         int k = this.guiScreen.height / 6 - 7;
/*     */         
/*  35 */         if (p_drawTooltips_2_ <= k + 98)
/*     */         {
/*  37 */           k += 105;
/*     */         }
/*     */         
/*  40 */         int l = j + 150 + 150;
/*  41 */         int i1 = k + 84 + 10;
/*  42 */         GuiButton guibutton = getSelectedButton(p_drawTooltips_1_, p_drawTooltips_2_, p_drawTooltips_3_);
/*     */         
/*  44 */         if (guibutton instanceof IOptionControl) {
/*     */           
/*  46 */           IOptionControl ioptioncontrol = (IOptionControl)guibutton;
/*  47 */           GameSettings.Options gamesettings$options = ioptioncontrol.getOption();
/*  48 */           String[] astring = getTooltipLines(gamesettings$options);
/*     */           
/*  50 */           if (astring == null) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*  55 */           GuiVideoSettings.drawRect(j, k, l, i1, -536870912);
/*     */           
/*  57 */           for (int j1 = 0; j1 < astring.length; j1++)
/*     */           {
/*  59 */             String s = astring[j1];
/*  60 */             int k1 = 14540253;
/*     */             
/*  62 */             if (s.endsWith("!"))
/*     */             {
/*  64 */               k1 = 16719904;
/*     */             }
/*     */             
/*  67 */             FontRenderer fontrenderer = (Minecraft.getMinecraft()).fontRendererObj;
/*  68 */             fontrenderer.drawStringWithShadow(s, (j + 5), (k + 5 + j1 * 11), k1);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/*  75 */       this.lastMouseX = p_drawTooltips_1_;
/*  76 */       this.lastMouseY = p_drawTooltips_2_;
/*  77 */       this.mouseStillTime = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiButton getSelectedButton(int p_getSelectedButton_1_, int p_getSelectedButton_2_, List<GuiButton> p_getSelectedButton_3_) {
/*  83 */     for (int i = 0; i < p_getSelectedButton_3_.size(); i++) {
/*     */       
/*  85 */       GuiButton guibutton = p_getSelectedButton_3_.get(i);
/*  86 */       int j = GuiVideoSettings.getButtonWidth(guibutton);
/*  87 */       int k = GuiVideoSettings.getButtonHeight(guibutton);
/*  88 */       boolean flag = (p_getSelectedButton_1_ >= guibutton.xPosition && p_getSelectedButton_2_ >= guibutton.yPosition && p_getSelectedButton_1_ < guibutton.xPosition + j && p_getSelectedButton_2_ < guibutton.yPosition + k);
/*     */       
/*  90 */       if (flag)
/*     */       {
/*  92 */         return guibutton;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] getTooltipLines(GameSettings.Options p_getTooltipLines_0_) {
/* 101 */     return getTooltipLines(p_getTooltipLines_0_.getEnumString());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] getTooltipLines(String p_getTooltipLines_0_) {
/* 106 */     List<String> list = new ArrayList<>();
/*     */     
/* 108 */     for (int i = 0; i < 10; i++) {
/*     */       
/* 110 */       String s = String.valueOf(p_getTooltipLines_0_) + ".tooltip." + (i + 1);
/* 111 */       String s1 = Lang.get(s, null);
/*     */       
/* 113 */       if (s1 == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 118 */       list.add(s1);
/*     */     } 
/*     */     
/* 121 */     if (list.size() <= 0)
/*     */     {
/* 123 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 127 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 128 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\TooltipManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */