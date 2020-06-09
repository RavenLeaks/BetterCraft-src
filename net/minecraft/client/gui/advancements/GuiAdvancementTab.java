/*     */ package net.minecraft.client.gui.advancements;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.DisplayInfo;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class GuiAdvancementTab
/*     */   extends Gui {
/*     */   private final Minecraft field_191802_a;
/*     */   private final GuiScreenAdvancements field_193938_f;
/*     */   private final AdvancementTabType field_191803_f;
/*     */   private final int field_191804_g;
/*     */   private final Advancement field_191805_h;
/*     */   private final DisplayInfo field_191806_i;
/*     */   private final ItemStack field_191807_j;
/*     */   private final String field_191808_k;
/*     */   private final GuiAdvancement field_191809_l;
/*  28 */   private final Map<Advancement, GuiAdvancement> field_191810_m = Maps.newLinkedHashMap();
/*     */   private int field_191811_n;
/*     */   private int field_191812_o;
/*  31 */   private int field_193939_q = Integer.MAX_VALUE;
/*  32 */   private int field_193940_r = Integer.MAX_VALUE;
/*  33 */   private int field_191813_p = Integer.MIN_VALUE;
/*  34 */   private int field_191814_q = Integer.MIN_VALUE;
/*     */   
/*     */   private float field_191815_r;
/*     */   private boolean field_192992_s;
/*     */   
/*     */   public GuiAdvancementTab(Minecraft p_i47589_1_, GuiScreenAdvancements p_i47589_2_, AdvancementTabType p_i47589_3_, int p_i47589_4_, Advancement p_i47589_5_, DisplayInfo p_i47589_6_) {
/*  40 */     this.field_191802_a = p_i47589_1_;
/*  41 */     this.field_193938_f = p_i47589_2_;
/*  42 */     this.field_191803_f = p_i47589_3_;
/*  43 */     this.field_191804_g = p_i47589_4_;
/*  44 */     this.field_191805_h = p_i47589_5_;
/*  45 */     this.field_191806_i = p_i47589_6_;
/*  46 */     this.field_191807_j = p_i47589_6_.func_192298_b();
/*  47 */     this.field_191808_k = p_i47589_6_.func_192297_a().getFormattedText();
/*  48 */     this.field_191809_l = new GuiAdvancement(this, p_i47589_1_, p_i47589_5_, p_i47589_6_);
/*  49 */     func_193937_a(this.field_191809_l, p_i47589_5_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Advancement func_193935_c() {
/*  54 */     return this.field_191805_h;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_191795_d() {
/*  59 */     return this.field_191808_k;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191798_a(int p_191798_1_, int p_191798_2_, boolean p_191798_3_) {
/*  64 */     this.field_191803_f.func_192651_a(this, p_191798_1_, p_191798_2_, p_191798_3_, this.field_191804_g);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191796_a(int p_191796_1_, int p_191796_2_, RenderItem p_191796_3_) {
/*  69 */     this.field_191803_f.func_192652_a(p_191796_1_, p_191796_2_, this.field_191804_g, p_191796_3_, this.field_191807_j);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191799_a() {
/*  74 */     if (!this.field_192992_s) {
/*     */       
/*  76 */       this.field_191811_n = 117 - (this.field_191813_p + this.field_193939_q) / 2;
/*  77 */       this.field_191812_o = 56 - (this.field_191814_q + this.field_193940_r) / 2;
/*  78 */       this.field_192992_s = true;
/*     */     } 
/*     */     
/*  81 */     GlStateManager.depthFunc(518);
/*  82 */     drawRect(0, 0, 234, 113, -16777216);
/*  83 */     GlStateManager.depthFunc(515);
/*  84 */     ResourceLocation resourcelocation = this.field_191806_i.func_192293_c();
/*     */     
/*  86 */     if (resourcelocation != null) {
/*     */       
/*  88 */       this.field_191802_a.getTextureManager().bindTexture(resourcelocation);
/*     */     }
/*     */     else {
/*     */       
/*  92 */       this.field_191802_a.getTextureManager().bindTexture(TextureManager.field_194008_a);
/*     */     } 
/*     */     
/*  95 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  96 */     int i = this.field_191811_n % 16;
/*  97 */     int j = this.field_191812_o % 16;
/*     */     
/*  99 */     for (int k = -1; k <= 15; k++) {
/*     */       
/* 101 */       for (int l = -1; l <= 8; l++)
/*     */       {
/* 103 */         drawModalRectWithCustomSizedTexture(i + 16 * k, j + 16 * l, 0.0F, 0.0F, 16, 16, 16.0F, 16.0F);
/*     */       }
/*     */     } 
/*     */     
/* 107 */     this.field_191809_l.func_191819_a(this.field_191811_n, this.field_191812_o, true);
/* 108 */     this.field_191809_l.func_191819_a(this.field_191811_n, this.field_191812_o, false);
/* 109 */     this.field_191809_l.func_191817_b(this.field_191811_n, this.field_191812_o);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192991_a(int p_192991_1_, int p_192991_2_, int p_192991_3_, int p_192991_4_) {
/* 114 */     GlStateManager.pushMatrix();
/* 115 */     GlStateManager.translate(0.0F, 0.0F, 200.0F);
/* 116 */     drawRect(0, 0, 234, 113, MathHelper.floor(this.field_191815_r * 255.0F) << 24);
/* 117 */     boolean flag = false;
/*     */     
/* 119 */     if (p_192991_1_ > 0 && p_192991_1_ < 234 && p_192991_2_ > 0 && p_192991_2_ < 113)
/*     */     {
/* 121 */       for (GuiAdvancement guiadvancement : this.field_191810_m.values()) {
/*     */         
/* 123 */         if (guiadvancement.func_191816_c(this.field_191811_n, this.field_191812_o, p_192991_1_, p_192991_2_)) {
/*     */           
/* 125 */           flag = true;
/* 126 */           guiadvancement.func_191821_a(this.field_191811_n, this.field_191812_o, this.field_191815_r, p_192991_3_, p_192991_4_);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 132 */     GlStateManager.popMatrix();
/*     */     
/* 134 */     if (flag) {
/*     */       
/* 136 */       this.field_191815_r = MathHelper.clamp(this.field_191815_r + 0.02F, 0.0F, 0.3F);
/*     */     }
/*     */     else {
/*     */       
/* 140 */       this.field_191815_r = MathHelper.clamp(this.field_191815_r - 0.04F, 0.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191793_c(int p_191793_1_, int p_191793_2_, int p_191793_3_, int p_191793_4_) {
/* 146 */     return this.field_191803_f.func_192654_a(p_191793_1_, p_191793_2_, this.field_191804_g, p_191793_3_, p_191793_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static GuiAdvancementTab func_193936_a(Minecraft p_193936_0_, GuiScreenAdvancements p_193936_1_, int p_193936_2_, Advancement p_193936_3_) {
/* 152 */     if (p_193936_3_.func_192068_c() == null)
/*     */     {
/* 154 */       return null; } 
/*     */     byte b;
/*     */     int i;
/*     */     AdvancementTabType[] arrayOfAdvancementTabType;
/* 158 */     for (i = (arrayOfAdvancementTabType = AdvancementTabType.values()).length, b = 0; b < i; ) { AdvancementTabType advancementtabtype = arrayOfAdvancementTabType[b];
/*     */       
/* 160 */       if (p_193936_2_ < advancementtabtype.func_192650_a())
/*     */       {
/* 162 */         return new GuiAdvancementTab(p_193936_0_, p_193936_1_, advancementtabtype, p_193936_2_, p_193936_3_, p_193936_3_.func_192068_c());
/*     */       }
/*     */       
/* 165 */       p_193936_2_ -= advancementtabtype.func_192650_a();
/*     */       b++; }
/*     */     
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_191797_b(int p_191797_1_, int p_191797_2_) {
/* 174 */     if (this.field_191813_p - this.field_193939_q > 234)
/*     */     {
/* 176 */       this.field_191811_n = MathHelper.clamp(this.field_191811_n + p_191797_1_, -(this.field_191813_p - 234), 0);
/*     */     }
/*     */     
/* 179 */     if (this.field_191814_q - this.field_193940_r > 113)
/*     */     {
/* 181 */       this.field_191812_o = MathHelper.clamp(this.field_191812_o + p_191797_2_, -(this.field_191814_q - 113), 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191800_a(Advancement p_191800_1_) {
/* 187 */     if (p_191800_1_.func_192068_c() != null) {
/*     */       
/* 189 */       GuiAdvancement guiadvancement = new GuiAdvancement(this, this.field_191802_a, p_191800_1_, p_191800_1_.func_192068_c());
/* 190 */       func_193937_a(guiadvancement, p_191800_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193937_a(GuiAdvancement p_193937_1_, Advancement p_193937_2_) {
/* 196 */     this.field_191810_m.put(p_193937_2_, p_193937_1_);
/* 197 */     int i = p_193937_1_.func_191823_d();
/* 198 */     int j = i + 28;
/* 199 */     int k = p_193937_1_.func_191820_c();
/* 200 */     int l = k + 27;
/* 201 */     this.field_193939_q = Math.min(this.field_193939_q, i);
/* 202 */     this.field_191813_p = Math.max(this.field_191813_p, j);
/* 203 */     this.field_193940_r = Math.min(this.field_193940_r, k);
/* 204 */     this.field_191814_q = Math.max(this.field_191814_q, l);
/*     */     
/* 206 */     for (GuiAdvancement guiadvancement : this.field_191810_m.values())
/*     */     {
/* 208 */       guiadvancement.func_191825_b();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GuiAdvancement func_191794_b(Advancement p_191794_1_) {
/* 215 */     return this.field_191810_m.get(p_191794_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiScreenAdvancements func_193934_g() {
/* 220 */     return this.field_193938_f;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\advancements\GuiAdvancementTab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */