/*     */ package net.minecraft.client.gui.recipebook;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.RenderItem;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class GhostRecipe {
/*     */   private IRecipe field_192687_a;
/*  20 */   private final List<GhostIngredient> field_192688_b = Lists.newArrayList();
/*     */   
/*     */   private float field_194190_c;
/*     */   
/*     */   public void func_192682_a() {
/*  25 */     this.field_192687_a = null;
/*  26 */     this.field_192688_b.clear();
/*  27 */     this.field_194190_c = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194187_a(Ingredient p_194187_1_, int p_194187_2_, int p_194187_3_) {
/*  32 */     this.field_192688_b.add(new GhostIngredient(p_194187_1_, p_194187_2_, p_194187_3_));
/*     */   }
/*     */ 
/*     */   
/*     */   public GhostIngredient func_192681_a(int p_192681_1_) {
/*  37 */     return this.field_192688_b.get(p_192681_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_192684_b() {
/*  42 */     return this.field_192688_b.size();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IRecipe func_192686_c() {
/*  48 */     return this.field_192687_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192685_a(IRecipe p_192685_1_) {
/*  53 */     this.field_192687_a = p_192685_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194188_a(Minecraft p_194188_1_, int p_194188_2_, int p_194188_3_, boolean p_194188_4_, float p_194188_5_) {
/*  58 */     if (!GuiScreen.isCtrlKeyDown())
/*     */     {
/*  60 */       this.field_194190_c += p_194188_5_;
/*     */     }
/*     */     
/*  63 */     RenderHelper.enableGUIStandardItemLighting();
/*  64 */     GlStateManager.disableLighting();
/*     */     
/*  66 */     for (int i = 0; i < this.field_192688_b.size(); i++) {
/*     */       
/*  68 */       GhostIngredient ghostrecipe$ghostingredient = this.field_192688_b.get(i);
/*  69 */       int j = ghostrecipe$ghostingredient.func_193713_b() + p_194188_2_;
/*  70 */       int k = ghostrecipe$ghostingredient.func_193712_c() + p_194188_3_;
/*     */       
/*  72 */       if (i == 0 && p_194188_4_) {
/*     */         
/*  74 */         Gui.drawRect(j - 4, k - 4, j + 20, k + 20, 822018048);
/*     */       }
/*     */       else {
/*     */         
/*  78 */         Gui.drawRect(j, k, j + 16, k + 16, 822018048);
/*     */       } 
/*     */       
/*  81 */       GlStateManager.disableLighting();
/*  82 */       ItemStack itemstack = ghostrecipe$ghostingredient.func_194184_c();
/*  83 */       RenderItem renderitem = p_194188_1_.getRenderItem();
/*  84 */       renderitem.renderItemAndEffectIntoGUI((EntityLivingBase)p_194188_1_.player, itemstack, j, k);
/*  85 */       GlStateManager.depthFunc(516);
/*  86 */       Gui.drawRect(j, k, j + 16, k + 16, 822083583);
/*  87 */       GlStateManager.depthFunc(515);
/*     */       
/*  89 */       if (i == 0)
/*     */       {
/*  91 */         renderitem.renderItemOverlays(p_194188_1_.fontRendererObj, itemstack, j, k);
/*     */       }
/*     */       
/*  94 */       GlStateManager.enableLighting();
/*     */     } 
/*     */     
/*  97 */     RenderHelper.disableStandardItemLighting();
/*     */   }
/*     */ 
/*     */   
/*     */   public class GhostIngredient
/*     */   {
/*     */     private final Ingredient field_194186_b;
/*     */     private final int field_192678_b;
/*     */     private final int field_192679_c;
/*     */     
/*     */     public GhostIngredient(Ingredient p_i47604_2_, int p_i47604_3_, int p_i47604_4_) {
/* 108 */       this.field_194186_b = p_i47604_2_;
/* 109 */       this.field_192678_b = p_i47604_3_;
/* 110 */       this.field_192679_c = p_i47604_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_193713_b() {
/* 115 */       return this.field_192678_b;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_193712_c() {
/* 120 */       return this.field_192679_c;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_194184_c() {
/* 125 */       ItemStack[] aitemstack = this.field_194186_b.func_193365_a();
/* 126 */       return aitemstack[MathHelper.floor(GhostRecipe.this.field_194190_c / 30.0F) % aitemstack.length];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\recipebook\GhostRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */