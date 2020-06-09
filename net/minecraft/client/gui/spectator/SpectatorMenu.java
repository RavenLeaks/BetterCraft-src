/*     */ package net.minecraft.client.gui.spectator;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiSpectator;
/*     */ import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ public class SpectatorMenu
/*     */ {
/*  16 */   private static final ISpectatorMenuObject CLOSE_ITEM = new EndSpectatorObject(null);
/*  17 */   private static final ISpectatorMenuObject SCROLL_LEFT = new MoveMenuObject(-1, true);
/*  18 */   private static final ISpectatorMenuObject SCROLL_RIGHT_ENABLED = new MoveMenuObject(1, true);
/*  19 */   private static final ISpectatorMenuObject SCROLL_RIGHT_DISABLED = new MoveMenuObject(1, false);
/*  20 */   public static final ISpectatorMenuObject EMPTY_SLOT = new ISpectatorMenuObject()
/*     */     {
/*     */       public void selectItem(SpectatorMenu menu) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public ITextComponent getSpectatorName() {
/*  27 */         return (ITextComponent)new TextComponentString("");
/*     */       }
/*     */ 
/*     */       
/*     */       public void renderIcon(float p_178663_1_, int alpha) {}
/*     */       
/*     */       public boolean isEnabled() {
/*  34 */         return false;
/*     */       }
/*     */     };
/*     */   private final ISpectatorMenuRecipient listener;
/*  38 */   private final List<SpectatorDetails> previousCategories = Lists.newArrayList();
/*  39 */   private ISpectatorMenuView category = new BaseSpectatorGroup();
/*  40 */   private int selectedSlot = -1;
/*     */   
/*     */   private int page;
/*     */   
/*     */   public SpectatorMenu(ISpectatorMenuRecipient p_i45497_1_) {
/*  45 */     this.listener = p_i45497_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISpectatorMenuObject getItem(int p_178643_1_) {
/*  50 */     int i = p_178643_1_ + this.page * 6;
/*     */     
/*  52 */     if (this.page > 0 && p_178643_1_ == 0)
/*     */     {
/*  54 */       return SCROLL_LEFT;
/*     */     }
/*  56 */     if (p_178643_1_ == 7)
/*     */     {
/*  58 */       return (i < this.category.getItems().size()) ? SCROLL_RIGHT_ENABLED : SCROLL_RIGHT_DISABLED;
/*     */     }
/*  60 */     if (p_178643_1_ == 8)
/*     */     {
/*  62 */       return CLOSE_ITEM;
/*     */     }
/*     */ 
/*     */     
/*  66 */     return (i >= 0 && i < this.category.getItems().size()) ? (ISpectatorMenuObject)MoreObjects.firstNonNull(this.category.getItems().get(i), EMPTY_SLOT) : EMPTY_SLOT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ISpectatorMenuObject> getItems() {
/*  72 */     List<ISpectatorMenuObject> list = Lists.newArrayList();
/*     */     
/*  74 */     for (int i = 0; i <= 8; i++)
/*     */     {
/*  76 */       list.add(getItem(i));
/*     */     }
/*     */     
/*  79 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public ISpectatorMenuObject getSelectedItem() {
/*  84 */     return getItem(this.selectedSlot);
/*     */   }
/*     */ 
/*     */   
/*     */   public ISpectatorMenuView getSelectedCategory() {
/*  89 */     return this.category;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectSlot(int slotIn) {
/*  94 */     ISpectatorMenuObject ispectatormenuobject = getItem(slotIn);
/*     */     
/*  96 */     if (ispectatormenuobject != EMPTY_SLOT)
/*     */     {
/*  98 */       if (this.selectedSlot == slotIn && ispectatormenuobject.isEnabled()) {
/*     */         
/* 100 */         ispectatormenuobject.selectItem(this);
/*     */       }
/*     */       else {
/*     */         
/* 104 */         this.selectedSlot = slotIn;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void exit() {
/* 111 */     this.listener.onSpectatorMenuClosed(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSelectedSlot() {
/* 116 */     return this.selectedSlot;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectCategory(ISpectatorMenuView menuView) {
/* 121 */     this.previousCategories.add(getCurrentPage());
/* 122 */     this.category = menuView;
/* 123 */     this.selectedSlot = -1;
/* 124 */     this.page = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public SpectatorDetails getCurrentPage() {
/* 129 */     return new SpectatorDetails(this.category, getItems(), this.selectedSlot);
/*     */   }
/*     */ 
/*     */   
/*     */   static class EndSpectatorObject
/*     */     implements ISpectatorMenuObject
/*     */   {
/*     */     private EndSpectatorObject() {}
/*     */ 
/*     */     
/*     */     public void selectItem(SpectatorMenu menu) {
/* 140 */       menu.exit();
/*     */     }
/*     */ 
/*     */     
/*     */     public ITextComponent getSpectatorName() {
/* 145 */       return (ITextComponent)new TextComponentTranslation("spectatorMenu.close", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderIcon(float p_178663_1_, int alpha) {
/* 150 */       Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.SPECTATOR_WIDGETS);
/* 151 */       Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEnabled() {
/* 156 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   static class MoveMenuObject
/*     */     implements ISpectatorMenuObject
/*     */   {
/*     */     private final int direction;
/*     */     private final boolean enabled;
/*     */     
/*     */     public MoveMenuObject(int p_i45495_1_, boolean p_i45495_2_) {
/* 167 */       this.direction = p_i45495_1_;
/* 168 */       this.enabled = p_i45495_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void selectItem(SpectatorMenu menu) {
/* 173 */       menu.page = menu.page + this.direction;
/*     */     }
/*     */ 
/*     */     
/*     */     public ITextComponent getSpectatorName() {
/* 178 */       return (this.direction < 0) ? (ITextComponent)new TextComponentTranslation("spectatorMenu.previous_page", new Object[0]) : (ITextComponent)new TextComponentTranslation("spectatorMenu.next_page", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderIcon(float p_178663_1_, int alpha) {
/* 183 */       Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.SPECTATOR_WIDGETS);
/*     */       
/* 185 */       if (this.direction < 0) {
/*     */         
/* 187 */         Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*     */       }
/*     */       else {
/*     */         
/* 191 */         Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEnabled() {
/* 197 */       return this.enabled;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\spectator\SpectatorMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */