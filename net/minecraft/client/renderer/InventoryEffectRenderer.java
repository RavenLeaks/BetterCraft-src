/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Ordering;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ 
/*     */ 
/*     */ public abstract class InventoryEffectRenderer
/*     */   extends GuiContainer
/*     */ {
/*     */   protected boolean hasActivePotionEffects;
/*     */   
/*     */   public InventoryEffectRenderer(Container inventorySlotsIn) {
/*  18 */     super(inventorySlotsIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  27 */     super.initGui();
/*  28 */     updateActivePotionEffects();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateActivePotionEffects() {
/*  33 */     if (this.mc.player.getActivePotionEffects().isEmpty()) {
/*     */       
/*  35 */       this.guiLeft = (this.width - this.xSize) / 2;
/*  36 */       this.hasActivePotionEffects = false;
/*     */     }
/*     */     else {
/*     */       
/*  40 */       this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
/*  41 */       this.hasActivePotionEffects = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  50 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/*  52 */     if (this.hasActivePotionEffects)
/*     */     {
/*  54 */       drawActivePotionEffects();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawActivePotionEffects() {
/*  63 */     int i = this.guiLeft - 124;
/*  64 */     int j = this.guiTop;
/*  65 */     int k = 166;
/*  66 */     Collection<PotionEffect> collection = this.mc.player.getActivePotionEffects();
/*     */     
/*  68 */     if (!collection.isEmpty()) {
/*     */       
/*  70 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  71 */       GlStateManager.disableLighting();
/*  72 */       int l = 33;
/*     */       
/*  74 */       if (collection.size() > 5)
/*     */       {
/*  76 */         l = 132 / (collection.size() - 1);
/*     */       }
/*     */       
/*  79 */       for (PotionEffect potioneffect : Ordering.natural().sortedCopy(collection)) {
/*     */         
/*  81 */         Potion potion = potioneffect.getPotion();
/*  82 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  83 */         this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
/*  84 */         drawTexturedModalRect(i, j, 0, 166, 140, 32);
/*     */         
/*  86 */         if (potion.hasStatusIcon()) {
/*     */           
/*  88 */           int i1 = potion.getStatusIconIndex();
/*  89 */           drawTexturedModalRect(i + 6, j + 7, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
/*     */         } 
/*     */         
/*  92 */         String s1 = I18n.format(potion.getName(), new Object[0]);
/*     */         
/*  94 */         if (potioneffect.getAmplifier() == 1) {
/*     */           
/*  96 */           s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.2", new Object[0]);
/*     */         }
/*  98 */         else if (potioneffect.getAmplifier() == 2) {
/*     */           
/* 100 */           s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.3", new Object[0]);
/*     */         }
/* 102 */         else if (potioneffect.getAmplifier() == 3) {
/*     */           
/* 104 */           s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.4", new Object[0]);
/*     */         } 
/*     */         
/* 107 */         this.fontRendererObj.drawStringWithShadow(s1, (i + 10 + 18), (j + 6), 16777215);
/* 108 */         String s = Potion.getPotionDurationString(potioneffect, 1.0F);
/* 109 */         this.fontRendererObj.drawStringWithShadow(s, (i + 10 + 18), (j + 6 + 10), 8355711);
/* 110 */         j += l;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\InventoryEffectRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */