/*     */ package net.minecraft.client.gui.advancements;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.advancements.DisplayInfo;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class GuiAdvancement
/*     */   extends Gui
/*     */ {
/*  22 */   private static final ResourceLocation field_191827_a = new ResourceLocation("textures/gui/advancements/widgets.png");
/*  23 */   private static final Pattern field_192996_f = Pattern.compile("(.+) \\S+");
/*     */   private final GuiAdvancementTab field_191828_f;
/*     */   private final Advancement field_191829_g;
/*     */   private final DisplayInfo field_191830_h;
/*     */   private final String field_191831_i;
/*     */   private final int field_191832_j;
/*     */   private final List<String> field_192997_l;
/*     */   private final Minecraft field_191833_k;
/*     */   private GuiAdvancement field_191834_l;
/*  32 */   private final List<GuiAdvancement> field_191835_m = Lists.newArrayList();
/*     */   
/*     */   private AdvancementProgress field_191836_n;
/*     */   private final int field_191837_o;
/*     */   private final int field_191826_p;
/*     */   
/*     */   public GuiAdvancement(GuiAdvancementTab p_i47385_1_, Minecraft p_i47385_2_, Advancement p_i47385_3_, DisplayInfo p_i47385_4_) {
/*  39 */     this.field_191828_f = p_i47385_1_;
/*  40 */     this.field_191829_g = p_i47385_3_;
/*  41 */     this.field_191830_h = p_i47385_4_;
/*  42 */     this.field_191833_k = p_i47385_2_;
/*  43 */     this.field_191831_i = p_i47385_2_.fontRendererObj.trimStringToWidth(p_i47385_4_.func_192297_a().getFormattedText(), 163);
/*  44 */     this.field_191837_o = MathHelper.floor(p_i47385_4_.func_192299_e() * 28.0F);
/*  45 */     this.field_191826_p = MathHelper.floor(p_i47385_4_.func_192296_f() * 27.0F);
/*  46 */     int i = p_i47385_3_.func_193124_g();
/*  47 */     int j = String.valueOf(i).length();
/*  48 */     int k = (i > 1) ? (p_i47385_2_.fontRendererObj.getStringWidth("  ") + p_i47385_2_.fontRendererObj.getStringWidth("0") * j * 2 + p_i47385_2_.fontRendererObj.getStringWidth("/")) : 0;
/*  49 */     int l = 29 + p_i47385_2_.fontRendererObj.getStringWidth(this.field_191831_i) + k;
/*  50 */     String s = p_i47385_4_.func_193222_b().getFormattedText();
/*  51 */     this.field_192997_l = func_192995_a(s, l);
/*     */     
/*  53 */     for (String s1 : this.field_192997_l)
/*     */     {
/*  55 */       l = Math.max(l, p_i47385_2_.fontRendererObj.getStringWidth(s1));
/*     */     }
/*     */     
/*  58 */     this.field_191832_j = l + 3 + 5;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> func_192995_a(String p_192995_1_, int p_192995_2_) {
/*  63 */     if (p_192995_1_.isEmpty())
/*     */     {
/*  65 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/*  69 */     List<String> list = this.field_191833_k.fontRendererObj.listFormattedStringToWidth(p_192995_1_, p_192995_2_);
/*     */     
/*  71 */     if (list.size() < 2)
/*     */     {
/*  73 */       return list;
/*     */     }
/*     */ 
/*     */     
/*  77 */     String s = list.get(0);
/*  78 */     String s1 = list.get(1);
/*  79 */     int i = this.field_191833_k.fontRendererObj.getStringWidth(String.valueOf(s) + ' ' + s1.split(" ")[0]);
/*     */     
/*  81 */     if (i - p_192995_2_ <= 10)
/*     */     {
/*  83 */       return this.field_191833_k.fontRendererObj.listFormattedStringToWidth(p_192995_1_, i);
/*     */     }
/*     */ 
/*     */     
/*  87 */     Matcher matcher = field_192996_f.matcher(s);
/*     */     
/*  89 */     if (matcher.matches()) {
/*     */       
/*  91 */       int j = this.field_191833_k.fontRendererObj.getStringWidth(matcher.group(1));
/*     */       
/*  93 */       if (p_192995_2_ - j <= 10)
/*     */       {
/*  95 */         return this.field_191833_k.fontRendererObj.listFormattedStringToWidth(p_192995_1_, j);
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private GuiAdvancement func_191818_a(Advancement p_191818_1_) {
/*     */     do {
/* 110 */       p_191818_1_ = p_191818_1_.func_192070_b();
/*     */     }
/* 112 */     while (p_191818_1_ != null && p_191818_1_.func_192068_c() == null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     if (p_191818_1_ != null && p_191818_1_.func_192068_c() != null)
/*     */     {
/* 120 */       return this.field_191828_f.func_191794_b(p_191818_1_);
/*     */     }
/*     */ 
/*     */     
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_191819_a(int p_191819_1_, int p_191819_2_, boolean p_191819_3_) {
/* 130 */     if (this.field_191834_l != null) {
/*     */       
/* 132 */       int i = p_191819_1_ + this.field_191834_l.field_191837_o + 13;
/* 133 */       int j = p_191819_1_ + this.field_191834_l.field_191837_o + 26 + 4;
/* 134 */       int k = p_191819_2_ + this.field_191834_l.field_191826_p + 13;
/* 135 */       int l = p_191819_1_ + this.field_191837_o + 13;
/* 136 */       int i1 = p_191819_2_ + this.field_191826_p + 13;
/* 137 */       int j1 = p_191819_3_ ? -16777216 : -1;
/*     */       
/* 139 */       if (p_191819_3_) {
/*     */         
/* 141 */         drawHorizontalLine(j, i, k - 1, j1);
/* 142 */         drawHorizontalLine(j + 1, i, k, j1);
/* 143 */         drawHorizontalLine(j, i, k + 1, j1);
/* 144 */         drawHorizontalLine(l, j - 1, i1 - 1, j1);
/* 145 */         drawHorizontalLine(l, j - 1, i1, j1);
/* 146 */         drawHorizontalLine(l, j - 1, i1 + 1, j1);
/* 147 */         drawVerticalLine(j - 1, i1, k, j1);
/* 148 */         drawVerticalLine(j + 1, i1, k, j1);
/*     */       }
/*     */       else {
/*     */         
/* 152 */         drawHorizontalLine(j, i, k, j1);
/* 153 */         drawHorizontalLine(l, j, i1, j1);
/* 154 */         drawVerticalLine(j, i1, k, j1);
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     for (GuiAdvancement guiadvancement : this.field_191835_m)
/*     */     {
/* 160 */       guiadvancement.func_191819_a(p_191819_1_, p_191819_2_, p_191819_3_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191817_b(int p_191817_1_, int p_191817_2_) {
/* 166 */     if (!this.field_191830_h.func_193224_j() || (this.field_191836_n != null && this.field_191836_n.func_192105_a())) {
/*     */       AdvancementState advancementstate;
/* 168 */       float f = (this.field_191836_n == null) ? 0.0F : this.field_191836_n.func_192103_c();
/*     */ 
/*     */       
/* 171 */       if (f >= 1.0F) {
/*     */         
/* 173 */         advancementstate = AdvancementState.OBTAINED;
/*     */       }
/*     */       else {
/*     */         
/* 177 */         advancementstate = AdvancementState.UNOBTAINED;
/*     */       } 
/*     */       
/* 180 */       this.field_191833_k.getTextureManager().bindTexture(field_191827_a);
/* 181 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 182 */       GlStateManager.enableBlend();
/* 183 */       drawTexturedModalRect(p_191817_1_ + this.field_191837_o + 3, p_191817_2_ + this.field_191826_p, this.field_191830_h.func_192291_d().func_192309_b(), 128 + advancementstate.func_192667_a() * 26, 26, 26);
/* 184 */       RenderHelper.enableGUIStandardItemLighting();
/* 185 */       this.field_191833_k.getRenderItem().renderItemAndEffectIntoGUI(null, this.field_191830_h.func_192298_b(), p_191817_1_ + this.field_191837_o + 8, p_191817_2_ + this.field_191826_p + 5);
/*     */     } 
/*     */     
/* 188 */     for (GuiAdvancement guiadvancement : this.field_191835_m)
/*     */     {
/* 190 */       guiadvancement.func_191817_b(p_191817_1_, p_191817_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191824_a(AdvancementProgress p_191824_1_) {
/* 196 */     this.field_191836_n = p_191824_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191822_a(GuiAdvancement p_191822_1_) {
/* 201 */     this.field_191835_m.add(p_191822_1_);
/*     */   }
/*     */   public void func_191821_a(int p_191821_1_, int p_191821_2_, float p_191821_3_, int p_191821_4_, int p_191821_5_) {
/*     */     AdvancementState advancementstate, advancementstate1, advancementstate2;
/*     */     int i1;
/* 206 */     boolean flag = (p_191821_4_ + p_191821_1_ + this.field_191837_o + this.field_191832_j + 26 >= (this.field_191828_f.func_193934_g()).width);
/* 207 */     String s = (this.field_191836_n == null) ? null : this.field_191836_n.func_193126_d();
/* 208 */     int i = (s == null) ? 0 : this.field_191833_k.fontRendererObj.getStringWidth(s);
/* 209 */     boolean flag1 = (113 - p_191821_2_ - this.field_191826_p - 26 <= 6 + this.field_192997_l.size() * this.field_191833_k.fontRendererObj.FONT_HEIGHT);
/* 210 */     float f = (this.field_191836_n == null) ? 0.0F : this.field_191836_n.func_192103_c();
/* 211 */     int j = MathHelper.floor(f * this.field_191832_j);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     if (f >= 1.0F) {
/*     */       
/* 218 */       j = this.field_191832_j / 2;
/* 219 */       advancementstate = AdvancementState.OBTAINED;
/* 220 */       advancementstate1 = AdvancementState.OBTAINED;
/* 221 */       advancementstate2 = AdvancementState.OBTAINED;
/*     */     }
/* 223 */     else if (j < 2) {
/*     */       
/* 225 */       j = this.field_191832_j / 2;
/* 226 */       advancementstate = AdvancementState.UNOBTAINED;
/* 227 */       advancementstate1 = AdvancementState.UNOBTAINED;
/* 228 */       advancementstate2 = AdvancementState.UNOBTAINED;
/*     */     }
/* 230 */     else if (j > this.field_191832_j - 2) {
/*     */       
/* 232 */       j = this.field_191832_j / 2;
/* 233 */       advancementstate = AdvancementState.OBTAINED;
/* 234 */       advancementstate1 = AdvancementState.OBTAINED;
/* 235 */       advancementstate2 = AdvancementState.UNOBTAINED;
/*     */     }
/*     */     else {
/*     */       
/* 239 */       advancementstate = AdvancementState.OBTAINED;
/* 240 */       advancementstate1 = AdvancementState.UNOBTAINED;
/* 241 */       advancementstate2 = AdvancementState.UNOBTAINED;
/*     */     } 
/*     */     
/* 244 */     int k = this.field_191832_j - j;
/* 245 */     this.field_191833_k.getTextureManager().bindTexture(field_191827_a);
/* 246 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 247 */     GlStateManager.enableBlend();
/* 248 */     int l = p_191821_2_ + this.field_191826_p;
/*     */ 
/*     */     
/* 251 */     if (flag) {
/*     */       
/* 253 */       i1 = p_191821_1_ + this.field_191837_o - this.field_191832_j + 26 + 6;
/*     */     }
/*     */     else {
/*     */       
/* 257 */       i1 = p_191821_1_ + this.field_191837_o;
/*     */     } 
/*     */     
/* 260 */     int j1 = 32 + this.field_192997_l.size() * this.field_191833_k.fontRendererObj.FONT_HEIGHT;
/*     */     
/* 262 */     if (!this.field_192997_l.isEmpty())
/*     */     {
/* 264 */       if (flag1) {
/*     */         
/* 266 */         func_192994_a(i1, l + 26 - j1, this.field_191832_j, j1, 10, 200, 26, 0, 52);
/*     */       }
/*     */       else {
/*     */         
/* 270 */         func_192994_a(i1, l, this.field_191832_j, j1, 10, 200, 26, 0, 52);
/*     */       } 
/*     */     }
/*     */     
/* 274 */     drawTexturedModalRect(i1, l, 0, advancementstate.func_192667_a() * 26, j, 26);
/* 275 */     drawTexturedModalRect(i1 + j, l, 200 - k, advancementstate1.func_192667_a() * 26, k, 26);
/* 276 */     drawTexturedModalRect(p_191821_1_ + this.field_191837_o + 3, p_191821_2_ + this.field_191826_p, this.field_191830_h.func_192291_d().func_192309_b(), 128 + advancementstate2.func_192667_a() * 26, 26, 26);
/*     */     
/* 278 */     if (flag) {
/*     */       
/* 280 */       this.field_191833_k.fontRendererObj.drawString(this.field_191831_i, (i1 + 5), (p_191821_2_ + this.field_191826_p + 9), -1, true);
/*     */       
/* 282 */       if (s != null)
/*     */       {
/* 284 */         this.field_191833_k.fontRendererObj.drawString(s, (p_191821_1_ + this.field_191837_o - i), (p_191821_2_ + this.field_191826_p + 9), -1, true);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 289 */       this.field_191833_k.fontRendererObj.drawString(this.field_191831_i, (p_191821_1_ + this.field_191837_o + 32), (p_191821_2_ + this.field_191826_p + 9), -1, true);
/*     */       
/* 291 */       if (s != null)
/*     */       {
/* 293 */         this.field_191833_k.fontRendererObj.drawString(s, (p_191821_1_ + this.field_191837_o + this.field_191832_j - i - 5), (p_191821_2_ + this.field_191826_p + 9), -1, true);
/*     */       }
/*     */     } 
/*     */     
/* 297 */     if (flag1) {
/*     */       
/* 299 */       for (int k1 = 0; k1 < this.field_192997_l.size(); k1++)
/*     */       {
/* 301 */         this.field_191833_k.fontRendererObj.drawString(this.field_192997_l.get(k1), (i1 + 5), (l + 26 - j1 + 7 + k1 * this.field_191833_k.fontRendererObj.FONT_HEIGHT), -5592406, false);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 306 */       for (int l1 = 0; l1 < this.field_192997_l.size(); l1++)
/*     */       {
/* 308 */         this.field_191833_k.fontRendererObj.drawString(this.field_192997_l.get(l1), (i1 + 5), (p_191821_2_ + this.field_191826_p + 9 + 17 + l1 * this.field_191833_k.fontRendererObj.FONT_HEIGHT), -5592406, false);
/*     */       }
/*     */     } 
/*     */     
/* 312 */     RenderHelper.enableGUIStandardItemLighting();
/* 313 */     this.field_191833_k.getRenderItem().renderItemAndEffectIntoGUI(null, this.field_191830_h.func_192298_b(), p_191821_1_ + this.field_191837_o + 8, p_191821_2_ + this.field_191826_p + 5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_192994_a(int p_192994_1_, int p_192994_2_, int p_192994_3_, int p_192994_4_, int p_192994_5_, int p_192994_6_, int p_192994_7_, int p_192994_8_, int p_192994_9_) {
/* 318 */     drawTexturedModalRect(p_192994_1_, p_192994_2_, p_192994_8_, p_192994_9_, p_192994_5_, p_192994_5_);
/* 319 */     func_192993_a(p_192994_1_ + p_192994_5_, p_192994_2_, p_192994_3_ - p_192994_5_ - p_192994_5_, p_192994_5_, p_192994_8_ + p_192994_5_, p_192994_9_, p_192994_6_ - p_192994_5_ - p_192994_5_, p_192994_7_);
/* 320 */     drawTexturedModalRect(p_192994_1_ + p_192994_3_ - p_192994_5_, p_192994_2_, p_192994_8_ + p_192994_6_ - p_192994_5_, p_192994_9_, p_192994_5_, p_192994_5_);
/* 321 */     drawTexturedModalRect(p_192994_1_, p_192994_2_ + p_192994_4_ - p_192994_5_, p_192994_8_, p_192994_9_ + p_192994_7_ - p_192994_5_, p_192994_5_, p_192994_5_);
/* 322 */     func_192993_a(p_192994_1_ + p_192994_5_, p_192994_2_ + p_192994_4_ - p_192994_5_, p_192994_3_ - p_192994_5_ - p_192994_5_, p_192994_5_, p_192994_8_ + p_192994_5_, p_192994_9_ + p_192994_7_ - p_192994_5_, p_192994_6_ - p_192994_5_ - p_192994_5_, p_192994_7_);
/* 323 */     drawTexturedModalRect(p_192994_1_ + p_192994_3_ - p_192994_5_, p_192994_2_ + p_192994_4_ - p_192994_5_, p_192994_8_ + p_192994_6_ - p_192994_5_, p_192994_9_ + p_192994_7_ - p_192994_5_, p_192994_5_, p_192994_5_);
/* 324 */     func_192993_a(p_192994_1_, p_192994_2_ + p_192994_5_, p_192994_5_, p_192994_4_ - p_192994_5_ - p_192994_5_, p_192994_8_, p_192994_9_ + p_192994_5_, p_192994_6_, p_192994_7_ - p_192994_5_ - p_192994_5_);
/* 325 */     func_192993_a(p_192994_1_ + p_192994_5_, p_192994_2_ + p_192994_5_, p_192994_3_ - p_192994_5_ - p_192994_5_, p_192994_4_ - p_192994_5_ - p_192994_5_, p_192994_8_ + p_192994_5_, p_192994_9_ + p_192994_5_, p_192994_6_ - p_192994_5_ - p_192994_5_, p_192994_7_ - p_192994_5_ - p_192994_5_);
/* 326 */     func_192993_a(p_192994_1_ + p_192994_3_ - p_192994_5_, p_192994_2_ + p_192994_5_, p_192994_5_, p_192994_4_ - p_192994_5_ - p_192994_5_, p_192994_8_ + p_192994_6_ - p_192994_5_, p_192994_9_ + p_192994_5_, p_192994_6_, p_192994_7_ - p_192994_5_ - p_192994_5_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_192993_a(int p_192993_1_, int p_192993_2_, int p_192993_3_, int p_192993_4_, int p_192993_5_, int p_192993_6_, int p_192993_7_, int p_192993_8_) {
/* 331 */     for (int i = 0; i < p_192993_3_; i += p_192993_7_) {
/*     */       
/* 333 */       int j = p_192993_1_ + i;
/* 334 */       int k = Math.min(p_192993_7_, p_192993_3_ - i);
/*     */       
/* 336 */       for (int l = 0; l < p_192993_4_; l += p_192993_8_) {
/*     */         
/* 338 */         int i1 = p_192993_2_ + l;
/* 339 */         int j1 = Math.min(p_192993_8_, p_192993_4_ - l);
/* 340 */         drawTexturedModalRect(j, i1, p_192993_5_, p_192993_6_, k, j1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191816_c(int p_191816_1_, int p_191816_2_, int p_191816_3_, int p_191816_4_) {
/* 347 */     if (!this.field_191830_h.func_193224_j() || (this.field_191836_n != null && this.field_191836_n.func_192105_a())) {
/*     */       
/* 349 */       int i = p_191816_1_ + this.field_191837_o;
/* 350 */       int j = i + 26;
/* 351 */       int k = p_191816_2_ + this.field_191826_p;
/* 352 */       int l = k + 26;
/* 353 */       return (p_191816_3_ >= i && p_191816_3_ <= j && p_191816_4_ >= k && p_191816_4_ <= l);
/*     */     } 
/*     */ 
/*     */     
/* 357 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_191825_b() {
/* 363 */     if (this.field_191834_l == null && this.field_191829_g.func_192070_b() != null) {
/*     */       
/* 365 */       this.field_191834_l = func_191818_a(this.field_191829_g);
/*     */       
/* 367 */       if (this.field_191834_l != null)
/*     */       {
/* 369 */         this.field_191834_l.func_191822_a(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_191820_c() {
/* 376 */     return this.field_191826_p;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_191823_d() {
/* 381 */     return this.field_191837_o;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\advancements\GuiAdvancement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */