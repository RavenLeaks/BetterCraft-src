/*     */ package net.minecraft.client.gui.recipebook;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButtonToggle;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.client.util.RecipeBookClient;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ 
/*     */ public class GuiButtonRecipeTab
/*     */   extends GuiButtonToggle
/*     */ {
/*     */   private final CreativeTabs field_193921_u;
/*     */   private float field_193922_v;
/*     */   
/*     */   public GuiButtonRecipeTab(int p_i47588_1_, CreativeTabs p_i47588_2_) {
/*  23 */     super(p_i47588_1_, 0, 0, 35, 27, false);
/*  24 */     this.field_193921_u = p_i47588_2_;
/*  25 */     func_191751_a(153, 2, 35, 0, GuiRecipeBook.field_191894_a);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193918_a(Minecraft p_193918_1_) {
/*  30 */     RecipeBook recipebook = p_193918_1_.player.func_192035_E();
/*     */ 
/*     */     
/*  33 */     for (RecipeList recipelist : RecipeBookClient.field_194086_e.get(this.field_193921_u)) {
/*     */       
/*  35 */       Iterator<IRecipe> iterator = recipelist.func_194208_a(recipebook.func_192815_c()).iterator();
/*     */ 
/*     */ 
/*     */       
/*  39 */       while (iterator.hasNext()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  44 */         IRecipe irecipe = iterator.next();
/*     */         
/*  46 */         if (recipebook.func_194076_e(irecipe)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  52 */           this.field_193922_v = 15.0F;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
/*  59 */     if (this.visible) {
/*     */       
/*  61 */       if (this.field_193922_v > 0.0F) {
/*     */         
/*  63 */         float f = 1.0F + 0.1F * (float)Math.sin((this.field_193922_v / 15.0F * 3.1415927F));
/*  64 */         GlStateManager.pushMatrix();
/*  65 */         GlStateManager.translate((this.xPosition + 8), (this.yPosition + 12), 0.0F);
/*  66 */         GlStateManager.scale(1.0F, f, 1.0F);
/*  67 */         GlStateManager.translate(-(this.xPosition + 8), -(this.yPosition + 12), 0.0F);
/*     */       } 
/*     */       
/*  70 */       this.hovered = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
/*  71 */       p_191745_1_.getTextureManager().bindTexture(this.field_191760_o);
/*  72 */       GlStateManager.disableDepth();
/*  73 */       int k = this.field_191756_q;
/*  74 */       int i = this.field_191757_r;
/*     */       
/*  76 */       if (this.field_191755_p)
/*     */       {
/*  78 */         k += this.field_191758_s;
/*     */       }
/*     */       
/*  81 */       if (this.hovered)
/*     */       {
/*  83 */         i += this.field_191759_t;
/*     */       }
/*     */       
/*  86 */       int j = this.xPosition;
/*     */       
/*  88 */       if (this.field_191755_p)
/*     */       {
/*  90 */         j -= 2;
/*     */       }
/*     */       
/*  93 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  94 */       drawTexturedModalRect(j, this.yPosition, k, i, this.width, this.height);
/*  95 */       GlStateManager.enableDepth();
/*  96 */       RenderHelper.enableGUIStandardItemLighting();
/*  97 */       GlStateManager.disableLighting();
/*  98 */       func_193920_a(p_191745_1_.getRenderItem());
/*  99 */       GlStateManager.enableLighting();
/* 100 */       RenderHelper.disableStandardItemLighting();
/*     */       
/* 102 */       if (this.field_193922_v > 0.0F) {
/*     */         
/* 104 */         GlStateManager.popMatrix();
/* 105 */         this.field_193922_v -= p_191745_4_;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193920_a(RenderItem p_193920_1_) {
/* 112 */     ItemStack itemstack = this.field_193921_u.getIconItemStack();
/*     */     
/* 114 */     if (this.field_193921_u == CreativeTabs.TOOLS) {
/*     */       
/* 116 */       p_193920_1_.renderItemAndEffectIntoGUI(itemstack, this.xPosition + 3, this.yPosition + 5);
/* 117 */       p_193920_1_.renderItemAndEffectIntoGUI(CreativeTabs.COMBAT.getIconItemStack(), this.xPosition + 14, this.yPosition + 5);
/*     */     }
/* 119 */     else if (this.field_193921_u == CreativeTabs.MISC) {
/*     */       
/* 121 */       p_193920_1_.renderItemAndEffectIntoGUI(itemstack, this.xPosition + 3, this.yPosition + 5);
/* 122 */       p_193920_1_.renderItemAndEffectIntoGUI(CreativeTabs.FOOD.getIconItemStack(), this.xPosition + 14, this.yPosition + 5);
/*     */     }
/*     */     else {
/*     */       
/* 126 */       p_193920_1_.renderItemAndEffectIntoGUI(itemstack, this.xPosition + 9, this.yPosition + 5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CreativeTabs func_191764_e() {
/* 132 */     return this.field_193921_u;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193919_e() {
/* 137 */     List<RecipeList> list = (List<RecipeList>)RecipeBookClient.field_194086_e.get(this.field_193921_u);
/* 138 */     this.visible = false;
/*     */     
/* 140 */     for (RecipeList recipelist : list) {
/*     */       
/* 142 */       if (recipelist.func_194209_a() && recipelist.func_194212_c()) {
/*     */         
/* 144 */         this.visible = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 149 */     return this.visible;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\recipebook\GuiButtonRecipeTab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */