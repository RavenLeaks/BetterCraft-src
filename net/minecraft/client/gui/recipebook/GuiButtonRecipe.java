/*     */ package net.minecraft.client.gui.recipebook;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class GuiButtonRecipe
/*     */   extends GuiButton {
/*  18 */   private static final ResourceLocation field_191780_o = new ResourceLocation("textures/gui/recipe_book.png");
/*     */   
/*     */   private RecipeBook field_193930_p;
/*     */   private RecipeList field_191774_p;
/*     */   private float field_193931_r;
/*     */   private float field_191778_t;
/*     */   private int field_193932_t;
/*     */   
/*     */   public GuiButtonRecipe() {
/*  27 */     super(0, 0, 0, 25, 25, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193928_a(RecipeList p_193928_1_, RecipeBookPage p_193928_2_, RecipeBook p_193928_3_) {
/*  32 */     this.field_191774_p = p_193928_1_;
/*  33 */     this.field_193930_p = p_193928_3_;
/*  34 */     List<IRecipe> list = p_193928_1_.func_194208_a(p_193928_3_.func_192815_c());
/*     */     
/*  36 */     for (IRecipe irecipe : list) {
/*     */       
/*  38 */       if (p_193928_3_.func_194076_e(irecipe)) {
/*     */         
/*  40 */         p_193928_2_.func_194195_a(list);
/*  41 */         this.field_191778_t = 15.0F;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeList func_191771_c() {
/*  49 */     return this.field_191774_p;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191770_c(int p_191770_1_, int p_191770_2_) {
/*  54 */     this.xPosition = p_191770_1_;
/*  55 */     this.yPosition = p_191770_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
/*  60 */     if (this.visible) {
/*     */       
/*  62 */       if (!GuiScreen.isCtrlKeyDown())
/*     */       {
/*  64 */         this.field_193931_r += p_191745_4_;
/*     */       }
/*     */       
/*  67 */       this.hovered = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
/*  68 */       RenderHelper.enableGUIStandardItemLighting();
/*  69 */       p_191745_1_.getTextureManager().bindTexture(field_191780_o);
/*  70 */       GlStateManager.disableLighting();
/*  71 */       int i = 29;
/*     */       
/*  73 */       if (!this.field_191774_p.func_192708_c())
/*     */       {
/*  75 */         i += 25;
/*     */       }
/*     */       
/*  78 */       int j = 206;
/*     */       
/*  80 */       if (this.field_191774_p.func_194208_a(this.field_193930_p.func_192815_c()).size() > 1)
/*     */       {
/*  82 */         j += 25;
/*     */       }
/*     */       
/*  85 */       boolean flag = (this.field_191778_t > 0.0F);
/*     */       
/*  87 */       if (flag) {
/*     */         
/*  89 */         float f = 1.0F + 0.1F * (float)Math.sin((this.field_191778_t / 15.0F * 3.1415927F));
/*  90 */         GlStateManager.pushMatrix();
/*  91 */         GlStateManager.translate((this.xPosition + 8), (this.yPosition + 12), 0.0F);
/*  92 */         GlStateManager.scale(f, f, 1.0F);
/*  93 */         GlStateManager.translate(-(this.xPosition + 8), -(this.yPosition + 12), 0.0F);
/*  94 */         this.field_191778_t -= p_191745_4_;
/*     */       } 
/*     */       
/*  97 */       drawTexturedModalRect(this.xPosition, this.yPosition, i, j, this.width, this.height);
/*  98 */       List<IRecipe> list = func_193927_f();
/*  99 */       this.field_193932_t = MathHelper.floor(this.field_193931_r / 30.0F) % list.size();
/* 100 */       ItemStack itemstack = ((IRecipe)list.get(this.field_193932_t)).getRecipeOutput();
/* 101 */       int k = 4;
/*     */       
/* 103 */       if (this.field_191774_p.func_194211_e() && func_193927_f().size() > 1) {
/*     */         
/* 105 */         p_191745_1_.getRenderItem().renderItemAndEffectIntoGUI(itemstack, this.xPosition + k + 1, this.yPosition + k + 1);
/* 106 */         k--;
/*     */       } 
/*     */       
/* 109 */       p_191745_1_.getRenderItem().renderItemAndEffectIntoGUI(itemstack, this.xPosition + k, this.yPosition + k);
/*     */       
/* 111 */       if (flag)
/*     */       {
/* 113 */         GlStateManager.popMatrix();
/*     */       }
/*     */       
/* 116 */       GlStateManager.enableLighting();
/* 117 */       RenderHelper.disableStandardItemLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<IRecipe> func_193927_f() {
/* 123 */     List<IRecipe> list = this.field_191774_p.func_194207_b(true);
/*     */     
/* 125 */     if (!this.field_193930_p.func_192815_c())
/*     */     {
/* 127 */       list.addAll(this.field_191774_p.func_194207_b(false));
/*     */     }
/*     */     
/* 130 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193929_d() {
/* 135 */     return (func_193927_f().size() == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public IRecipe func_193760_e() {
/* 140 */     List<IRecipe> list = func_193927_f();
/* 141 */     return list.get(this.field_193932_t);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> func_191772_a(GuiScreen p_191772_1_) {
/* 146 */     ItemStack itemstack = ((IRecipe)func_193927_f().get(this.field_193932_t)).getRecipeOutput();
/* 147 */     List<String> list = p_191772_1_.func_191927_a(itemstack);
/*     */     
/* 149 */     if (this.field_191774_p.func_194208_a(this.field_193930_p.func_192815_c()).size() > 1)
/*     */     {
/* 151 */       list.add(I18n.format("gui.recipebook.moreRecipes", new Object[0]));
/*     */     }
/*     */     
/* 154 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getButtonWidth() {
/* 159 */     return 25;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\recipebook\GuiButtonRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */