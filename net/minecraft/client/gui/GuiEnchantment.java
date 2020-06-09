/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.model.ModelBook;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerEnchantment;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnchantmentNameParts;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.util.glu.Project;
/*     */ 
/*     */ public class GuiEnchantment
/*     */   extends GuiContainer {
/*  28 */   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static final ModelBook MODEL_BOOK = new ModelBook();
/*     */ 
/*     */   
/*     */   private final InventoryPlayer playerInventory;
/*     */ 
/*     */   
/*  44 */   private final Random random = new Random();
/*     */   private final ContainerEnchantment container;
/*     */   public int ticks;
/*     */   public float flip;
/*     */   public float oFlip;
/*     */   public float flipT;
/*     */   public float flipA;
/*     */   public float open;
/*     */   public float oOpen;
/*  53 */   private ItemStack last = ItemStack.field_190927_a;
/*     */   
/*     */   private final IWorldNameable nameable;
/*     */   
/*     */   public GuiEnchantment(InventoryPlayer inventory, World worldIn, IWorldNameable nameable) {
/*  58 */     super((Container)new ContainerEnchantment(inventory, worldIn));
/*  59 */     this.playerInventory = inventory;
/*  60 */     this.container = (ContainerEnchantment)this.inventorySlots;
/*  61 */     this.nameable = nameable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  69 */     this.fontRendererObj.drawString(this.nameable.getDisplayName().getUnformattedText(), 12, 5, 4210752);
/*  70 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  78 */     super.updateScreen();
/*  79 */     tickBook();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  87 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  88 */     int i = (this.width - this.xSize) / 2;
/*  89 */     int j = (this.height - this.ySize) / 2;
/*     */     
/*  91 */     for (int k = 0; k < 3; k++) {
/*     */       
/*  93 */       int l = mouseX - i + 60;
/*  94 */       int i1 = mouseY - j + 14 + 19 * k;
/*     */       
/*  96 */       if (l >= 0 && i1 >= 0 && l < 108 && i1 < 19 && this.container.enchantItem((EntityPlayer)this.mc.player, k))
/*     */       {
/*  98 */         this.mc.playerController.sendEnchantPacket(this.container.windowId, k);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 108 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 109 */     this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
/* 110 */     int i = (this.width - this.xSize) / 2;
/* 111 */     int j = (this.height - this.ySize) / 2;
/* 112 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 113 */     GlStateManager.pushMatrix();
/* 114 */     GlStateManager.matrixMode(5889);
/* 115 */     GlStateManager.pushMatrix();
/* 116 */     GlStateManager.loadIdentity();
/* 117 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 118 */     GlStateManager.viewport((ScaledResolution.getScaledWidth() - 320) / 2 * scaledresolution.getScaleFactor(), (ScaledResolution.getScaledHeight() - 240) / 2 * scaledresolution.getScaleFactor(), 320 * scaledresolution.getScaleFactor(), 240 * scaledresolution.getScaleFactor());
/* 119 */     GlStateManager.translate(-0.34F, 0.23F, 0.0F);
/* 120 */     Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
/* 121 */     float f = 1.0F;
/* 122 */     GlStateManager.matrixMode(5888);
/* 123 */     GlStateManager.loadIdentity();
/* 124 */     RenderHelper.enableStandardItemLighting();
/* 125 */     GlStateManager.translate(0.0F, 3.3F, -16.0F);
/* 126 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 127 */     float f1 = 5.0F;
/* 128 */     GlStateManager.scale(5.0F, 5.0F, 5.0F);
/* 129 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 130 */     this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
/* 131 */     GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
/* 132 */     float f2 = this.oOpen + (this.open - this.oOpen) * partialTicks;
/* 133 */     GlStateManager.translate((1.0F - f2) * 0.2F, (1.0F - f2) * 0.1F, (1.0F - f2) * 0.25F);
/* 134 */     GlStateManager.rotate(-(1.0F - f2) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
/* 135 */     GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/* 136 */     float f3 = this.oFlip + (this.flip - this.oFlip) * partialTicks + 0.25F;
/* 137 */     float f4 = this.oFlip + (this.flip - this.oFlip) * partialTicks + 0.75F;
/* 138 */     f3 = (f3 - MathHelper.fastFloor(f3)) * 1.6F - 0.3F;
/* 139 */     f4 = (f4 - MathHelper.fastFloor(f4)) * 1.6F - 0.3F;
/*     */     
/* 141 */     if (f3 < 0.0F)
/*     */     {
/* 143 */       f3 = 0.0F;
/*     */     }
/*     */     
/* 146 */     if (f4 < 0.0F)
/*     */     {
/* 148 */       f4 = 0.0F;
/*     */     }
/*     */     
/* 151 */     if (f3 > 1.0F)
/*     */     {
/* 153 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 156 */     if (f4 > 1.0F)
/*     */     {
/* 158 */       f4 = 1.0F;
/*     */     }
/*     */     
/* 161 */     GlStateManager.enableRescaleNormal();
/* 162 */     MODEL_BOOK.render(null, 0.0F, f3, f4, f2, 0.0F, 0.0625F);
/* 163 */     GlStateManager.disableRescaleNormal();
/* 164 */     RenderHelper.disableStandardItemLighting();
/* 165 */     GlStateManager.matrixMode(5889);
/* 166 */     GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 167 */     GlStateManager.popMatrix();
/* 168 */     GlStateManager.matrixMode(5888);
/* 169 */     GlStateManager.popMatrix();
/* 170 */     RenderHelper.disableStandardItemLighting();
/* 171 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 172 */     EnchantmentNameParts.getInstance().reseedRandomGenerator(this.container.xpSeed);
/* 173 */     int k = this.container.getLapisAmount();
/*     */     
/* 175 */     for (int l = 0; l < 3; l++) {
/*     */       
/* 177 */       int i1 = i + 60;
/* 178 */       int j1 = i1 + 20;
/* 179 */       this.zLevel = 0.0F;
/* 180 */       this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
/* 181 */       int k1 = this.container.enchantLevels[l];
/* 182 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 184 */       if (k1 == 0) {
/*     */         
/* 186 */         drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 185, 108, 19);
/*     */       }
/*     */       else {
/*     */         
/* 190 */         int m = k1;
/* 191 */         int l1 = 86 - this.fontRendererObj.getStringWidth(m);
/* 192 */         String s1 = EnchantmentNameParts.getInstance().generateNewRandomName(this.fontRendererObj, l1);
/* 193 */         FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;
/* 194 */         int i2 = 6839882;
/*     */         
/* 196 */         if ((k < l + 1 || this.mc.player.experienceLevel < k1) && !this.mc.player.capabilities.isCreativeMode) {
/*     */           
/* 198 */           drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 185, 108, 19);
/* 199 */           drawTexturedModalRect(i1 + 1, j + 15 + 19 * l, 16 * l, 239, 16, 16);
/* 200 */           fontrenderer.drawSplitString(s1, j1, j + 16 + 19 * l, l1, (i2 & 0xFEFEFE) >> 1);
/* 201 */           i2 = 4226832;
/*     */         }
/*     */         else {
/*     */           
/* 205 */           int j2 = mouseX - i + 60;
/* 206 */           int k2 = mouseY - j + 14 + 19 * l;
/*     */           
/* 208 */           if (j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19) {
/*     */             
/* 210 */             drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 204, 108, 19);
/* 211 */             i2 = 16777088;
/*     */           }
/*     */           else {
/*     */             
/* 215 */             drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 166, 108, 19);
/*     */           } 
/*     */           
/* 218 */           drawTexturedModalRect(i1 + 1, j + 15 + 19 * l, 16 * l, 223, 16, 16);
/* 219 */           fontrenderer.drawSplitString(s1, j1, j + 16 + 19 * l, l1, i2);
/* 220 */           i2 = 8453920;
/*     */         } 
/*     */         
/* 223 */         fontrenderer = this.mc.fontRendererObj;
/* 224 */         fontrenderer.drawStringWithShadow(m, (j1 + 86 - fontrenderer.getStringWidth(m)), (j + 16 + 19 * l + 7), i2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 234 */     partialTicks = this.mc.func_193989_ak();
/* 235 */     drawDefaultBackground();
/* 236 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 237 */     func_191948_b(mouseX, mouseY);
/* 238 */     boolean flag = this.mc.player.capabilities.isCreativeMode;
/* 239 */     int i = this.container.getLapisAmount();
/*     */     
/* 241 */     for (int j = 0; j < 3; j++) {
/*     */       
/* 243 */       int k = this.container.enchantLevels[j];
/* 244 */       Enchantment enchantment = Enchantment.getEnchantmentByID(this.container.enchantClue[j]);
/* 245 */       int l = this.container.worldClue[j];
/* 246 */       int i1 = j + 1;
/*     */       
/* 248 */       if (isPointInRegion(60, 14 + 19 * j, 108, 17, mouseX, mouseY) && k > 0 && l >= 0 && enchantment != null) {
/*     */         
/* 250 */         List<String> list = Lists.newArrayList();
/* 251 */         list.add(TextFormatting.WHITE + TextFormatting.ITALIC + I18n.format("container.enchant.clue", new Object[] { enchantment.getTranslatedName(l) }));
/*     */         
/* 253 */         if (!flag) {
/*     */           
/* 255 */           list.add("");
/*     */           
/* 257 */           if (this.mc.player.experienceLevel < k) {
/*     */             
/* 259 */             list.add(TextFormatting.RED + I18n.format("container.enchant.level.requirement", new Object[] { Integer.valueOf(this.container.enchantLevels[j]) }));
/*     */           } else {
/*     */             String s;
/*     */ 
/*     */ 
/*     */             
/* 265 */             if (i1 == 1) {
/*     */               
/* 267 */               s = I18n.format("container.enchant.lapis.one", new Object[0]);
/*     */             }
/*     */             else {
/*     */               
/* 271 */               s = I18n.format("container.enchant.lapis.many", new Object[] { Integer.valueOf(i1) });
/*     */             } 
/*     */             
/* 274 */             TextFormatting textformatting = (i >= i1) ? TextFormatting.GRAY : TextFormatting.RED;
/* 275 */             list.add(textformatting + s);
/*     */             
/* 277 */             if (i1 == 1) {
/*     */               
/* 279 */               s = I18n.format("container.enchant.level.one", new Object[0]);
/*     */             }
/*     */             else {
/*     */               
/* 283 */               s = I18n.format("container.enchant.level.many", new Object[] { Integer.valueOf(i1) });
/*     */             } 
/*     */             
/* 286 */             list.add(TextFormatting.GRAY + s);
/*     */           } 
/*     */         } 
/*     */         
/* 290 */         drawHoveringText(list, mouseX, mouseY);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tickBook() {
/* 298 */     ItemStack itemstack = this.inventorySlots.getSlot(0).getStack();
/*     */     
/* 300 */     if (!ItemStack.areItemStacksEqual(itemstack, this.last)) {
/*     */       
/* 302 */       this.last = itemstack;
/*     */ 
/*     */       
/*     */       do {
/* 306 */         this.flipT += (this.random.nextInt(4) - this.random.nextInt(4));
/*     */       }
/* 308 */       while (this.flip <= this.flipT + 1.0F && this.flip >= this.flipT - 1.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 315 */     this.ticks++;
/* 316 */     this.oFlip = this.flip;
/* 317 */     this.oOpen = this.open;
/* 318 */     boolean flag = false;
/*     */     
/* 320 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 322 */       if (this.container.enchantLevels[i] != 0)
/*     */       {
/* 324 */         flag = true;
/*     */       }
/*     */     } 
/*     */     
/* 328 */     if (flag) {
/*     */       
/* 330 */       this.open += 0.2F;
/*     */     }
/*     */     else {
/*     */       
/* 334 */       this.open -= 0.2F;
/*     */     } 
/*     */     
/* 337 */     this.open = MathHelper.clamp(this.open, 0.0F, 1.0F);
/* 338 */     float f1 = (this.flipT - this.flip) * 0.4F;
/* 339 */     float f = 0.2F;
/* 340 */     f1 = MathHelper.clamp(f1, -0.2F, 0.2F);
/* 341 */     this.flipA += (f1 - this.flipA) * 0.9F;
/* 342 */     this.flip += this.flipA;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiEnchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */