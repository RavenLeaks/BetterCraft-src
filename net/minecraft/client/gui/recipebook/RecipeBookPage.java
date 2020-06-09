/*     */ package net.minecraft.client.gui.recipebook;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButtonToggle;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ 
/*     */ public class RecipeBookPage
/*     */ {
/*  14 */   private List<GuiButtonRecipe> field_193743_h = Lists.newArrayListWithCapacity(20);
/*     */   private GuiButtonRecipe field_194201_b;
/*  16 */   private GuiRecipeOverlay field_194202_c = new GuiRecipeOverlay();
/*     */   private Minecraft field_193754_s;
/*  18 */   private List<IRecipeUpdateListener> field_193757_v = Lists.newArrayList();
/*     */   
/*     */   private List<RecipeList> field_194203_f;
/*     */   private GuiButtonToggle field_193740_e;
/*     */   private GuiButtonToggle field_193741_f;
/*     */   private int field_193737_b;
/*     */   private int field_193738_c;
/*     */   private RecipeBook field_194204_k;
/*     */   private IRecipe field_194205_l;
/*     */   private RecipeList field_194206_m;
/*     */   
/*     */   public RecipeBookPage() {
/*  30 */     for (int i = 0; i < 20; i++)
/*     */     {
/*  32 */       this.field_193743_h.add(new GuiButtonRecipe());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194194_a(Minecraft p_194194_1_, int p_194194_2_, int p_194194_3_) {
/*  38 */     this.field_193754_s = p_194194_1_;
/*  39 */     this.field_194204_k = p_194194_1_.player.func_192035_E();
/*     */     
/*  41 */     for (int i = 0; i < this.field_193743_h.size(); i++)
/*     */     {
/*  43 */       ((GuiButtonRecipe)this.field_193743_h.get(i)).func_191770_c(p_194194_2_ + 11 + 25 * i % 5, p_194194_3_ + 31 + 25 * i / 5);
/*     */     }
/*     */     
/*  46 */     this.field_193740_e = new GuiButtonToggle(0, p_194194_2_ + 93, p_194194_3_ + 137, 12, 17, false);
/*  47 */     this.field_193740_e.func_191751_a(1, 208, 13, 18, GuiRecipeBook.field_191894_a);
/*  48 */     this.field_193741_f = new GuiButtonToggle(0, p_194194_2_ + 38, p_194194_3_ + 137, 12, 17, true);
/*  49 */     this.field_193741_f.func_191751_a(1, 208, 13, 18, GuiRecipeBook.field_191894_a);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193732_a(GuiRecipeBook p_193732_1_) {
/*  54 */     this.field_193757_v.remove(p_193732_1_);
/*  55 */     this.field_193757_v.add(p_193732_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194192_a(List<RecipeList> p_194192_1_, boolean p_194192_2_) {
/*  60 */     this.field_194203_f = p_194192_1_;
/*  61 */     this.field_193737_b = (int)Math.ceil(p_194192_1_.size() / 20.0D);
/*     */     
/*  63 */     if (this.field_193737_b <= this.field_193738_c || p_194192_2_)
/*     */     {
/*  65 */       this.field_193738_c = 0;
/*     */     }
/*     */     
/*  68 */     func_194198_d();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_194198_d() {
/*  73 */     int i = 20 * this.field_193738_c;
/*     */     
/*  75 */     for (int j = 0; j < this.field_193743_h.size(); j++) {
/*     */       
/*  77 */       GuiButtonRecipe guibuttonrecipe = this.field_193743_h.get(j);
/*     */       
/*  79 */       if (i + j < this.field_194203_f.size()) {
/*     */         
/*  81 */         RecipeList recipelist = this.field_194203_f.get(i + j);
/*  82 */         guibuttonrecipe.func_193928_a(recipelist, this, this.field_194204_k);
/*  83 */         guibuttonrecipe.visible = true;
/*     */       }
/*     */       else {
/*     */         
/*  87 */         guibuttonrecipe.visible = false;
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     func_194197_e();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_194197_e() {
/*  96 */     this.field_193740_e.visible = (this.field_193737_b > 1 && this.field_193738_c < this.field_193737_b - 1);
/*  97 */     this.field_193741_f.visible = (this.field_193737_b > 1 && this.field_193738_c > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194191_a(int p_194191_1_, int p_194191_2_, int p_194191_3_, int p_194191_4_, float p_194191_5_) {
/* 102 */     if (this.field_193737_b > 1) {
/*     */       
/* 104 */       String s = String.valueOf(this.field_193738_c + 1) + "/" + this.field_193737_b;
/* 105 */       int i = this.field_193754_s.fontRendererObj.getStringWidth(s);
/* 106 */       this.field_193754_s.fontRendererObj.drawString(s, p_194191_1_ - i / 2 + 73, p_194191_2_ + 141, -1);
/*     */     } 
/*     */     
/* 109 */     RenderHelper.disableStandardItemLighting();
/* 110 */     this.field_194201_b = null;
/*     */     
/* 112 */     for (GuiButtonRecipe guibuttonrecipe : this.field_193743_h) {
/*     */       
/* 114 */       guibuttonrecipe.func_191745_a(this.field_193754_s, p_194191_3_, p_194191_4_, p_194191_5_);
/*     */       
/* 116 */       if (guibuttonrecipe.visible && guibuttonrecipe.isMouseOver())
/*     */       {
/* 118 */         this.field_194201_b = guibuttonrecipe;
/*     */       }
/*     */     } 
/*     */     
/* 122 */     this.field_193741_f.func_191745_a(this.field_193754_s, p_194191_3_, p_194191_4_, p_194191_5_);
/* 123 */     this.field_193740_e.func_191745_a(this.field_193754_s, p_194191_3_, p_194191_4_, p_194191_5_);
/* 124 */     this.field_194202_c.func_191842_a(p_194191_3_, p_194191_4_, p_194191_5_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193721_a(int p_193721_1_, int p_193721_2_) {
/* 129 */     if (this.field_193754_s.currentScreen != null && this.field_194201_b != null && !this.field_194202_c.func_191839_a())
/*     */     {
/* 131 */       this.field_193754_s.currentScreen.drawHoveringText(this.field_194201_b.func_191772_a(this.field_193754_s.currentScreen), p_193721_1_, p_193721_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IRecipe func_194193_a() {
/* 138 */     return this.field_194205_l;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RecipeList func_194199_b() {
/* 144 */     return this.field_194206_m;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194200_c() {
/* 149 */     this.field_194202_c.func_192999_a(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194196_a(int p_194196_1_, int p_194196_2_, int p_194196_3_, int p_194196_4_, int p_194196_5_, int p_194196_6_, int p_194196_7_) {
/* 154 */     this.field_194205_l = null;
/* 155 */     this.field_194206_m = null;
/*     */     
/* 157 */     if (this.field_194202_c.func_191839_a()) {
/*     */       
/* 159 */       if (this.field_194202_c.func_193968_a(p_194196_1_, p_194196_2_, p_194196_3_)) {
/*     */         
/* 161 */         this.field_194205_l = this.field_194202_c.func_193967_b();
/* 162 */         this.field_194206_m = this.field_194202_c.func_193971_a();
/*     */       }
/*     */       else {
/*     */         
/* 166 */         this.field_194202_c.func_192999_a(false);
/*     */       } 
/*     */       
/* 169 */       return true;
/*     */     } 
/* 171 */     if (this.field_193740_e.mousePressed(this.field_193754_s, p_194196_1_, p_194196_2_) && p_194196_3_ == 0) {
/*     */       
/* 173 */       this.field_193740_e.playPressSound(this.field_193754_s.getSoundHandler());
/* 174 */       this.field_193738_c++;
/* 175 */       func_194198_d();
/* 176 */       return true;
/*     */     } 
/* 178 */     if (this.field_193741_f.mousePressed(this.field_193754_s, p_194196_1_, p_194196_2_) && p_194196_3_ == 0) {
/*     */       
/* 180 */       this.field_193741_f.playPressSound(this.field_193754_s.getSoundHandler());
/* 181 */       this.field_193738_c--;
/* 182 */       func_194198_d();
/* 183 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 187 */     for (GuiButtonRecipe guibuttonrecipe : this.field_193743_h) {
/*     */       
/* 189 */       if (guibuttonrecipe.mousePressed(this.field_193754_s, p_194196_1_, p_194196_2_)) {
/*     */         
/* 191 */         guibuttonrecipe.playPressSound(this.field_193754_s.getSoundHandler());
/*     */         
/* 193 */         if (p_194196_3_ == 0) {
/*     */           
/* 195 */           this.field_194205_l = guibuttonrecipe.func_193760_e();
/* 196 */           this.field_194206_m = guibuttonrecipe.func_191771_c();
/*     */         }
/* 198 */         else if (!this.field_194202_c.func_191839_a() && !guibuttonrecipe.func_193929_d()) {
/*     */           
/* 200 */           this.field_194202_c.func_191845_a(this.field_193754_s, guibuttonrecipe.func_191771_c(), guibuttonrecipe.xPosition, guibuttonrecipe.yPosition, p_194196_4_ + p_194196_6_ / 2, p_194196_5_ + 13 + p_194196_7_ / 2, guibuttonrecipe.getButtonWidth(), this.field_194204_k);
/*     */         } 
/*     */         
/* 203 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_194195_a(List<IRecipe> p_194195_1_) {
/* 213 */     for (IRecipeUpdateListener irecipeupdatelistener : this.field_193757_v)
/*     */     {
/* 215 */       irecipeupdatelistener.func_193001_a(p_194195_1_);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\recipebook\RecipeBookPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */