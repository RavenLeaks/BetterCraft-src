/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiListExtended;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*     */ import net.minecraft.client.gui.GuiYesNo;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry {
/*  17 */   private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
/*  18 */   private static final ITextComponent INCOMPATIBLE = (ITextComponent)new TextComponentTranslation("resourcePack.incompatible", new Object[0]);
/*  19 */   private static final ITextComponent INCOMPATIBLE_OLD = (ITextComponent)new TextComponentTranslation("resourcePack.incompatible.old", new Object[0]);
/*  20 */   private static final ITextComponent INCOMPATIBLE_NEW = (ITextComponent)new TextComponentTranslation("resourcePack.incompatible.new", new Object[0]);
/*     */   
/*     */   protected final Minecraft mc;
/*     */   protected final GuiScreenResourcePacks resourcePacksGUI;
/*     */   
/*     */   public ResourcePackListEntry(GuiScreenResourcePacks resourcePacksGUIIn) {
/*  26 */     this.resourcePacksGUI = resourcePacksGUIIn;
/*  27 */     this.mc = Minecraft.getMinecraft();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
/*  32 */     int i = getResourcePackFormat();
/*     */     
/*  34 */     if (i != 3) {
/*     */       
/*  36 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  37 */       Gui.drawRect(p_192634_2_ - 1, p_192634_3_ - 1, p_192634_2_ + p_192634_4_ - 9, p_192634_3_ + p_192634_5_ + 1, -8978432);
/*     */     } 
/*     */     
/*  40 */     bindResourcePackIcon();
/*  41 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  42 */     Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
/*  43 */     String s = getResourcePackName();
/*  44 */     String s1 = getResourcePackDescription();
/*     */     
/*  46 */     if (showHoverOverlay() && (this.mc.gameSettings.touchscreen || p_192634_8_)) {
/*     */       
/*  48 */       this.mc.getTextureManager().bindTexture(RESOURCE_PACKS_TEXTURE);
/*  49 */       Gui.drawRect(p_192634_2_, p_192634_3_, p_192634_2_ + 32, p_192634_3_ + 32, -1601138544);
/*  50 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  51 */       int j = p_192634_6_ - p_192634_2_;
/*  52 */       int k = p_192634_7_ - p_192634_3_;
/*     */       
/*  54 */       if (i < 3) {
/*     */         
/*  56 */         s = INCOMPATIBLE.getFormattedText();
/*  57 */         s1 = INCOMPATIBLE_OLD.getFormattedText();
/*     */       }
/*  59 */       else if (i > 3) {
/*     */         
/*  61 */         s = INCOMPATIBLE.getFormattedText();
/*  62 */         s1 = INCOMPATIBLE_NEW.getFormattedText();
/*     */       } 
/*     */       
/*  65 */       if (canMoveRight()) {
/*     */         
/*  67 */         if (j < 32)
/*     */         {
/*  69 */           Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */         else
/*     */         {
/*  73 */           Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  78 */         if (canMoveLeft())
/*     */         {
/*  80 */           if (j < 16) {
/*     */             
/*  82 */             Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           }
/*     */           else {
/*     */             
/*  86 */             Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */         
/*  90 */         if (canMoveUp())
/*     */         {
/*  92 */           if (j < 32 && j > 16 && k < 16) {
/*     */             
/*  94 */             Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           }
/*     */           else {
/*     */             
/*  98 */             Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */         
/* 102 */         if (canMoveDown())
/*     */         {
/* 104 */           if (j < 32 && j > 16 && k > 16) {
/*     */             
/* 106 */             Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           }
/*     */           else {
/*     */             
/* 110 */             Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     int i1 = this.mc.fontRendererObj.getStringWidth(s);
/*     */     
/* 118 */     if (i1 > 157)
/*     */     {
/* 120 */       s = String.valueOf(this.mc.fontRendererObj.trimStringToWidth(s, 157 - this.mc.fontRendererObj.getStringWidth("..."))) + "...";
/*     */     }
/*     */     
/* 123 */     this.mc.fontRendererObj.drawStringWithShadow(s, (p_192634_2_ + 32 + 2), (p_192634_3_ + 1), 16777215);
/* 124 */     List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(s1, 157);
/*     */     
/* 126 */     for (int l = 0; l < 2 && l < list.size(); l++)
/*     */     {
/* 128 */       this.mc.fontRendererObj.drawStringWithShadow(list.get(l), (p_192634_2_ + 32 + 2), (p_192634_3_ + 12 + 10 * l), 8421504);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract int getResourcePackFormat();
/*     */   
/*     */   protected abstract String getResourcePackDescription();
/*     */   
/*     */   protected abstract String getResourcePackName();
/*     */   
/*     */   protected abstract void bindResourcePackIcon();
/*     */   
/*     */   protected boolean showHoverOverlay() {
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canMoveRight() {
/* 147 */     return !this.resourcePacksGUI.hasResourcePackEntry(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canMoveLeft() {
/* 152 */     return this.resourcePacksGUI.hasResourcePackEntry(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canMoveUp() {
/* 157 */     List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
/* 158 */     int i = list.indexOf(this);
/* 159 */     return (i > 0 && ((ResourcePackListEntry)list.get(i - 1)).showHoverOverlay());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canMoveDown() {
/* 164 */     List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
/* 165 */     int i = list.indexOf(this);
/* 166 */     return (i >= 0 && i < list.size() - 1 && ((ResourcePackListEntry)list.get(i + 1)).showHoverOverlay());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
/* 175 */     if (showHoverOverlay() && relativeX <= 32) {
/*     */       
/* 177 */       if (canMoveRight()) {
/*     */         
/* 179 */         this.resourcePacksGUI.markChanged();
/* 180 */         final int j = ((ResourcePackListEntry)this.resourcePacksGUI.getSelectedResourcePacks().get(0)).isServerPack() ? 1 : 0;
/* 181 */         int l = getResourcePackFormat();
/*     */         
/* 183 */         if (l == 3) {
/*     */           
/* 185 */           this.resourcePacksGUI.getListContaining(this).remove(this);
/* 186 */           this.resourcePacksGUI.getSelectedResourcePacks().add(j, this);
/*     */         }
/*     */         else {
/*     */           
/* 190 */           String s = I18n.format("resourcePack.incompatible.confirm.title", new Object[0]);
/* 191 */           String s1 = I18n.format("resourcePack.incompatible.confirm." + ((l > 3) ? "new" : "old"), new Object[0]);
/* 192 */           this.mc.displayGuiScreen((GuiScreen)new GuiYesNo(new GuiYesNoCallback()
/*     */                 {
/*     */                   public void confirmClicked(boolean result, int id)
/*     */                   {
/* 196 */                     List<ResourcePackListEntry> list2 = ResourcePackListEntry.this.resourcePacksGUI.getListContaining(ResourcePackListEntry.this);
/* 197 */                     ResourcePackListEntry.this.mc.displayGuiScreen((GuiScreen)ResourcePackListEntry.this.resourcePacksGUI);
/*     */                     
/* 199 */                     if (result) {
/*     */                       
/* 201 */                       list2.remove(ResourcePackListEntry.this);
/* 202 */                       ResourcePackListEntry.this.resourcePacksGUI.getSelectedResourcePacks().add(j, ResourcePackListEntry.this);
/*     */                     } 
/*     */                   }
/* 205 */                 }s, s1, 0));
/*     */         } 
/*     */         
/* 208 */         return true;
/*     */       } 
/*     */       
/* 211 */       if (relativeX < 16 && canMoveLeft()) {
/*     */         
/* 213 */         this.resourcePacksGUI.getListContaining(this).remove(this);
/* 214 */         this.resourcePacksGUI.getAvailableResourcePacks().add(0, this);
/* 215 */         this.resourcePacksGUI.markChanged();
/* 216 */         return true;
/*     */       } 
/*     */       
/* 219 */       if (relativeX > 16 && relativeY < 16 && canMoveUp()) {
/*     */         
/* 221 */         List<ResourcePackListEntry> list1 = this.resourcePacksGUI.getListContaining(this);
/* 222 */         int k = list1.indexOf(this);
/* 223 */         list1.remove(this);
/* 224 */         list1.add(k - 1, this);
/* 225 */         this.resourcePacksGUI.markChanged();
/* 226 */         return true;
/*     */       } 
/*     */       
/* 229 */       if (relativeX > 16 && relativeY > 16 && canMoveDown()) {
/*     */         
/* 231 */         List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
/* 232 */         int i = list.indexOf(this);
/* 233 */         list.remove(this);
/* 234 */         list.add(i + 1, this);
/* 235 */         this.resourcePacksGUI.markChanged();
/* 236 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 240 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isServerPack() {
/* 256 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\ResourcePackListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */