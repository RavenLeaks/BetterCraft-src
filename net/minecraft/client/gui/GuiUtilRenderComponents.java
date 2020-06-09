/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ 
/*     */ 
/*     */ public class GuiUtilRenderComponents
/*     */ {
/*     */   public static String removeTextColorsIfConfigured(String text, boolean forceColor) {
/*  14 */     return (!forceColor && !(Minecraft.getMinecraft()).gameSettings.chatColours) ? TextFormatting.getTextWithoutFormattingCodes(text) : text;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<ITextComponent> splitText(ITextComponent textComponent, int maxTextLenght, FontRenderer fontRendererIn, boolean p_178908_3_, boolean forceTextColor) {
/*  19 */     int i = 0;
/*  20 */     TextComponentString textComponentString = new TextComponentString("");
/*  21 */     List<ITextComponent> list = Lists.newArrayList();
/*  22 */     List<ITextComponent> list1 = Lists.newArrayList((Iterable)textComponent);
/*     */     
/*  24 */     for (int j = 0; j < list1.size(); j++) {
/*     */       
/*  26 */       ITextComponent itextcomponent1 = list1.get(j);
/*  27 */       String s = itextcomponent1.getUnformattedComponentText();
/*  28 */       boolean flag = false;
/*     */       
/*  30 */       if (s.contains("\n")) {
/*     */         
/*  32 */         int k = s.indexOf('\n');
/*  33 */         String s1 = s.substring(k + 1);
/*  34 */         s = s.substring(0, k + 1);
/*  35 */         TextComponentString textComponentString1 = new TextComponentString(s1);
/*  36 */         textComponentString1.setStyle(itextcomponent1.getStyle().createShallowCopy());
/*  37 */         list1.add(j + 1, textComponentString1);
/*  38 */         flag = true;
/*     */       } 
/*     */       
/*  41 */       String s4 = removeTextColorsIfConfigured(String.valueOf(itextcomponent1.getStyle().getFormattingCode()) + s, forceTextColor);
/*  42 */       String s5 = s4.endsWith("\n") ? s4.substring(0, s4.length() - 1) : s4;
/*  43 */       int i1 = fontRendererIn.getStringWidth(s5);
/*  44 */       TextComponentString textcomponentstring = new TextComponentString(s5);
/*  45 */       textcomponentstring.setStyle(itextcomponent1.getStyle().createShallowCopy());
/*     */       
/*  47 */       if (i + i1 > maxTextLenght) {
/*     */         
/*  49 */         String s2 = fontRendererIn.trimStringToWidth(s4, maxTextLenght - i, false);
/*  50 */         String s3 = (s2.length() < s4.length()) ? s4.substring(s2.length()) : null;
/*     */         
/*  52 */         if (s3 != null && !s3.isEmpty()) {
/*     */           
/*  54 */           int l = s2.lastIndexOf(' ');
/*     */           
/*  56 */           if (l >= 0 && fontRendererIn.getStringWidth(s4.substring(0, l)) > 0) {
/*     */             
/*  58 */             s2 = s4.substring(0, l);
/*     */             
/*  60 */             if (p_178908_3_)
/*     */             {
/*  62 */               l++;
/*     */             }
/*     */             
/*  65 */             s3 = s4.substring(l);
/*     */           }
/*  67 */           else if (i > 0 && !s4.contains(" ")) {
/*     */             
/*  69 */             s2 = "";
/*  70 */             s3 = s4;
/*     */           } 
/*     */           
/*  73 */           TextComponentString textcomponentstring1 = new TextComponentString(s3);
/*  74 */           textcomponentstring1.setStyle(itextcomponent1.getStyle().createShallowCopy());
/*  75 */           list1.add(j + 1, textcomponentstring1);
/*     */         } 
/*     */         
/*  78 */         i1 = fontRendererIn.getStringWidth(s2);
/*  79 */         textcomponentstring = new TextComponentString(s2);
/*  80 */         textcomponentstring.setStyle(itextcomponent1.getStyle().createShallowCopy());
/*  81 */         flag = true;
/*     */       } 
/*     */       
/*  84 */       if (i + i1 <= maxTextLenght) {
/*     */         
/*  86 */         i += i1;
/*  87 */         textComponentString.appendSibling((ITextComponent)textcomponentstring);
/*     */       }
/*     */       else {
/*     */         
/*  91 */         flag = true;
/*     */       } 
/*     */       
/*  94 */       if (flag) {
/*     */         
/*  96 */         list.add(textComponentString);
/*  97 */         i = 0;
/*  98 */         textComponentString = new TextComponentString("");
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     list.add(textComponentString);
/* 103 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiUtilRenderComponents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */