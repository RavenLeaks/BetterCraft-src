/*     */ package net.minecraft.client.gui.recipebook;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ import net.minecraft.item.crafting.ShapedRecipes;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class GuiRecipeOverlay
/*     */   extends Gui {
/*  22 */   private static final ResourceLocation field_191847_a = new ResourceLocation("textures/gui/recipe_book.png");
/*  23 */   private final List<Button> field_193972_f = Lists.newArrayList();
/*     */   
/*     */   private boolean field_191850_h;
/*     */   private int field_191851_i;
/*     */   private int field_191852_j;
/*     */   private Minecraft field_191853_k;
/*     */   private RecipeList field_191848_f;
/*     */   private IRecipe field_193973_l;
/*     */   private float field_193974_m;
/*     */   
/*     */   public void func_191845_a(Minecraft p_191845_1_, RecipeList p_191845_2_, int p_191845_3_, int p_191845_4_, int p_191845_5_, int p_191845_6_, float p_191845_7_, RecipeBook p_191845_8_) {
/*  34 */     this.field_191853_k = p_191845_1_;
/*  35 */     this.field_191848_f = p_191845_2_;
/*  36 */     boolean flag = p_191845_8_.func_192815_c();
/*  37 */     List<IRecipe> list = p_191845_2_.func_194207_b(true);
/*  38 */     List<IRecipe> list1 = flag ? Collections.<IRecipe>emptyList() : p_191845_2_.func_194207_b(false);
/*  39 */     int i = list.size();
/*  40 */     int j = i + list1.size();
/*  41 */     int k = (j <= 16) ? 4 : 5;
/*  42 */     int l = (int)Math.ceil((j / k));
/*  43 */     this.field_191851_i = p_191845_3_;
/*  44 */     this.field_191852_j = p_191845_4_;
/*  45 */     int i1 = 25;
/*  46 */     float f = (this.field_191851_i + Math.min(j, k) * 25);
/*  47 */     float f1 = (p_191845_5_ + 50);
/*     */     
/*  49 */     if (f > f1)
/*     */     {
/*  51 */       this.field_191851_i = (int)(this.field_191851_i - p_191845_7_ * (int)((f - f1) / p_191845_7_));
/*     */     }
/*     */     
/*  54 */     float f2 = (this.field_191852_j + l * 25);
/*  55 */     float f3 = (p_191845_6_ + 50);
/*     */     
/*  57 */     if (f2 > f3)
/*     */     {
/*  59 */       this.field_191852_j = (int)(this.field_191852_j - p_191845_7_ * MathHelper.ceil((f2 - f3) / p_191845_7_));
/*     */     }
/*     */     
/*  62 */     float f4 = this.field_191852_j;
/*  63 */     float f5 = (p_191845_6_ - 100);
/*     */     
/*  65 */     if (f4 < f5)
/*     */     {
/*  67 */       this.field_191852_j = (int)(this.field_191852_j - p_191845_7_ * MathHelper.ceil((f4 - f5) / p_191845_7_));
/*     */     }
/*     */     
/*  70 */     this.field_191850_h = true;
/*  71 */     this.field_193972_f.clear();
/*     */     
/*  73 */     for (int j1 = 0; j1 < j; j1++) {
/*     */       
/*  75 */       boolean flag1 = (j1 < i);
/*  76 */       this.field_193972_f.add(new Button(this.field_191851_i + 4 + 25 * j1 % k, this.field_191852_j + 5 + 25 * j1 / k, flag1 ? list.get(j1) : list1.get(j1 - i), flag1));
/*     */     } 
/*     */     
/*  79 */     this.field_193973_l = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeList func_193971_a() {
/*  84 */     return this.field_191848_f;
/*     */   }
/*     */ 
/*     */   
/*     */   public IRecipe func_193967_b() {
/*  89 */     return this.field_193973_l;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193968_a(int p_193968_1_, int p_193968_2_, int p_193968_3_) {
/*  94 */     if (p_193968_3_ != 0)
/*     */     {
/*  96 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 100 */     for (Button guirecipeoverlay$button : this.field_193972_f) {
/*     */       
/* 102 */       if (guirecipeoverlay$button.mousePressed(this.field_191853_k, p_193968_1_, p_193968_2_)) {
/*     */         
/* 104 */         this.field_193973_l = guirecipeoverlay$button.field_193924_p;
/* 105 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_191842_a(int p_191842_1_, int p_191842_2_, float p_191842_3_) {
/* 115 */     if (this.field_191850_h) {
/*     */       
/* 117 */       this.field_193974_m += p_191842_3_;
/* 118 */       RenderHelper.enableGUIStandardItemLighting();
/* 119 */       GlStateManager.enableBlend();
/* 120 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 121 */       this.field_191853_k.getTextureManager().bindTexture(field_191847_a);
/* 122 */       GlStateManager.pushMatrix();
/* 123 */       GlStateManager.translate(0.0F, 0.0F, 170.0F);
/* 124 */       int i = (this.field_193972_f.size() <= 16) ? 4 : 5;
/* 125 */       int j = Math.min(this.field_193972_f.size(), i);
/* 126 */       int k = MathHelper.ceil(this.field_193972_f.size() / i);
/* 127 */       int l = 24;
/* 128 */       int i1 = 4;
/* 129 */       int j1 = 82;
/* 130 */       int k1 = 208;
/* 131 */       func_191846_c(j, k, 24, 4, 82, 208);
/* 132 */       GlStateManager.disableBlend();
/* 133 */       RenderHelper.disableStandardItemLighting();
/*     */       
/* 135 */       for (Button guirecipeoverlay$button : this.field_193972_f)
/*     */       {
/* 137 */         guirecipeoverlay$button.func_191745_a(this.field_191853_k, p_191842_1_, p_191842_2_, p_191842_3_);
/*     */       }
/*     */       
/* 140 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_191846_c(int p_191846_1_, int p_191846_2_, int p_191846_3_, int p_191846_4_, int p_191846_5_, int p_191846_6_) {
/* 146 */     drawTexturedModalRect(this.field_191851_i, this.field_191852_j, p_191846_5_, p_191846_6_, p_191846_4_, p_191846_4_);
/* 147 */     drawTexturedModalRect(this.field_191851_i + p_191846_4_ * 2 + p_191846_1_ * p_191846_3_, this.field_191852_j, p_191846_5_ + p_191846_3_ + p_191846_4_, p_191846_6_, p_191846_4_, p_191846_4_);
/* 148 */     drawTexturedModalRect(this.field_191851_i, this.field_191852_j + p_191846_4_ * 2 + p_191846_2_ * p_191846_3_, p_191846_5_, p_191846_6_ + p_191846_3_ + p_191846_4_, p_191846_4_, p_191846_4_);
/* 149 */     drawTexturedModalRect(this.field_191851_i + p_191846_4_ * 2 + p_191846_1_ * p_191846_3_, this.field_191852_j + p_191846_4_ * 2 + p_191846_2_ * p_191846_3_, p_191846_5_ + p_191846_3_ + p_191846_4_, p_191846_6_ + p_191846_3_ + p_191846_4_, p_191846_4_, p_191846_4_);
/*     */     
/* 151 */     for (int i = 0; i < p_191846_1_; i++) {
/*     */       
/* 153 */       drawTexturedModalRect(this.field_191851_i + p_191846_4_ + i * p_191846_3_, this.field_191852_j, p_191846_5_ + p_191846_4_, p_191846_6_, p_191846_3_, p_191846_4_);
/* 154 */       drawTexturedModalRect(this.field_191851_i + p_191846_4_ + (i + 1) * p_191846_3_, this.field_191852_j, p_191846_5_ + p_191846_4_, p_191846_6_, p_191846_4_, p_191846_4_);
/*     */       
/* 156 */       for (int j = 0; j < p_191846_2_; j++) {
/*     */         
/* 158 */         if (i == 0) {
/*     */           
/* 160 */           drawTexturedModalRect(this.field_191851_i, this.field_191852_j + p_191846_4_ + j * p_191846_3_, p_191846_5_, p_191846_6_ + p_191846_4_, p_191846_4_, p_191846_3_);
/* 161 */           drawTexturedModalRect(this.field_191851_i, this.field_191852_j + p_191846_4_ + (j + 1) * p_191846_3_, p_191846_5_, p_191846_6_ + p_191846_4_, p_191846_4_, p_191846_4_);
/*     */         } 
/*     */         
/* 164 */         drawTexturedModalRect(this.field_191851_i + p_191846_4_ + i * p_191846_3_, this.field_191852_j + p_191846_4_ + j * p_191846_3_, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_3_, p_191846_3_);
/* 165 */         drawTexturedModalRect(this.field_191851_i + p_191846_4_ + (i + 1) * p_191846_3_, this.field_191852_j + p_191846_4_ + j * p_191846_3_, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_4_, p_191846_3_);
/* 166 */         drawTexturedModalRect(this.field_191851_i + p_191846_4_ + i * p_191846_3_, this.field_191852_j + p_191846_4_ + (j + 1) * p_191846_3_, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_3_, p_191846_4_);
/* 167 */         drawTexturedModalRect(this.field_191851_i + p_191846_4_ + (i + 1) * p_191846_3_ - 1, this.field_191852_j + p_191846_4_ + (j + 1) * p_191846_3_ - 1, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_4_ + 1, p_191846_4_ + 1);
/*     */         
/* 169 */         if (i == p_191846_1_ - 1) {
/*     */           
/* 171 */           drawTexturedModalRect(this.field_191851_i + p_191846_4_ * 2 + p_191846_1_ * p_191846_3_, this.field_191852_j + p_191846_4_ + j * p_191846_3_, p_191846_5_ + p_191846_3_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_4_, p_191846_3_);
/* 172 */           drawTexturedModalRect(this.field_191851_i + p_191846_4_ * 2 + p_191846_1_ * p_191846_3_, this.field_191852_j + p_191846_4_ + (j + 1) * p_191846_3_, p_191846_5_ + p_191846_3_ + p_191846_4_, p_191846_6_ + p_191846_4_, p_191846_4_, p_191846_4_);
/*     */         } 
/*     */       } 
/*     */       
/* 176 */       drawTexturedModalRect(this.field_191851_i + p_191846_4_ + i * p_191846_3_, this.field_191852_j + p_191846_4_ * 2 + p_191846_2_ * p_191846_3_, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_3_ + p_191846_4_, p_191846_3_, p_191846_4_);
/* 177 */       drawTexturedModalRect(this.field_191851_i + p_191846_4_ + (i + 1) * p_191846_3_, this.field_191852_j + p_191846_4_ * 2 + p_191846_2_ * p_191846_3_, p_191846_5_ + p_191846_4_, p_191846_6_ + p_191846_3_ + p_191846_4_, p_191846_4_, p_191846_4_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192999_a(boolean p_192999_1_) {
/* 183 */     this.field_191850_h = p_192999_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191839_a() {
/* 188 */     return this.field_191850_h;
/*     */   }
/*     */   
/*     */   class Button
/*     */     extends GuiButton
/*     */   {
/*     */     private final IRecipe field_193924_p;
/*     */     private final boolean field_193925_q;
/*     */     
/*     */     public Button(int p_i47594_2_, int p_i47594_3_, IRecipe p_i47594_4_, boolean p_i47594_5_) {
/* 198 */       super(0, p_i47594_2_, p_i47594_3_, "");
/* 199 */       this.width = 24;
/* 200 */       this.height = 24;
/* 201 */       this.field_193924_p = p_i47594_4_;
/* 202 */       this.field_193925_q = p_i47594_5_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
/* 207 */       RenderHelper.enableGUIStandardItemLighting();
/* 208 */       GlStateManager.enableAlpha();
/* 209 */       p_191745_1_.getTextureManager().bindTexture(GuiRecipeOverlay.field_191847_a);
/* 210 */       this.hovered = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
/* 211 */       int i = 152;
/*     */       
/* 213 */       if (!this.field_193925_q)
/*     */       {
/* 215 */         i += 26;
/*     */       }
/*     */       
/* 218 */       int j = 78;
/*     */       
/* 220 */       if (this.hovered)
/*     */       {
/* 222 */         j += 26;
/*     */       }
/*     */       
/* 225 */       drawTexturedModalRect(this.xPosition, this.yPosition, i, j, this.width, this.height);
/* 226 */       int k = 3;
/* 227 */       int l = 3;
/*     */       
/* 229 */       if (this.field_193924_p instanceof ShapedRecipes) {
/*     */         
/* 231 */         ShapedRecipes shapedrecipes = (ShapedRecipes)this.field_193924_p;
/* 232 */         k = shapedrecipes.func_192403_f();
/* 233 */         l = shapedrecipes.func_192404_g();
/*     */       } 
/*     */       
/* 236 */       Iterator<Ingredient> iterator = this.field_193924_p.func_192400_c().iterator();
/*     */       
/* 238 */       for (int i1 = 0; i1 < l; i1++) {
/*     */         
/* 240 */         int j1 = 3 + i1 * 7;
/*     */         
/* 242 */         for (int k1 = 0; k1 < k; k1++) {
/*     */           
/* 244 */           if (iterator.hasNext()) {
/*     */             
/* 246 */             ItemStack[] aitemstack = ((Ingredient)iterator.next()).func_193365_a();
/*     */             
/* 248 */             if (aitemstack.length != 0) {
/*     */               
/* 250 */               int l1 = 3 + k1 * 7;
/* 251 */               GlStateManager.pushMatrix();
/* 252 */               float f = 0.42F;
/* 253 */               int i2 = (int)((this.xPosition + l1) / 0.42F - 3.0F);
/* 254 */               int j2 = (int)((this.yPosition + j1) / 0.42F - 3.0F);
/* 255 */               GlStateManager.scale(0.42F, 0.42F, 1.0F);
/* 256 */               GlStateManager.enableLighting();
/* 257 */               p_191745_1_.getRenderItem().renderItemAndEffectIntoGUI(aitemstack[MathHelper.floor(GuiRecipeOverlay.this.field_193974_m / 30.0F) % aitemstack.length], i2, j2);
/* 258 */               GlStateManager.disableLighting();
/* 259 */               GlStateManager.popMatrix();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 265 */       GlStateManager.disableAlpha();
/* 266 */       RenderHelper.disableStandardItemLighting();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\recipebook\GuiRecipeOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */