/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.GuiSlot;
/*     */ import optifine.Lang;
/*     */ 
/*     */ class GuiSlotShaders
/*     */   extends GuiSlot {
/*     */   private ArrayList shaderslist;
/*     */   private int selectedIndex;
/*  11 */   private long lastClickedCached = 0L;
/*     */   
/*     */   final GuiShaders shadersGui;
/*     */   
/*     */   public GuiSlotShaders(GuiShaders par1GuiShaders, int width, int height, int top, int bottom, int slotHeight) {
/*  16 */     super(par1GuiShaders.getMc(), width, height, top, bottom, slotHeight);
/*  17 */     this.shadersGui = par1GuiShaders;
/*  18 */     updateList();
/*  19 */     this.amountScrolled = 0.0F;
/*  20 */     int i = this.selectedIndex * slotHeight;
/*  21 */     int j = (bottom - top) / 2;
/*     */     
/*  23 */     if (i > j)
/*     */     {
/*  25 */       scrollBy(i - j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/*  34 */     return this.width - 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateList() {
/*  39 */     this.shaderslist = Shaders.listOfShaders();
/*  40 */     this.selectedIndex = 0;
/*  41 */     int i = 0;
/*     */     
/*  43 */     for (int j = this.shaderslist.size(); i < j; i++) {
/*     */       
/*  45 */       if (((String)this.shaderslist.get(i)).equals(Shaders.currentshadername)) {
/*     */         
/*  47 */         this.selectedIndex = i;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  55 */     return this.shaderslist.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void elementClicked(int index, boolean doubleClicked, int mouseX, int mouseY) {
/*  63 */     if (index != this.selectedIndex || this.lastClicked != this.lastClickedCached) {
/*     */       
/*  65 */       this.selectedIndex = index;
/*  66 */       this.lastClickedCached = this.lastClicked;
/*  67 */       Shaders.setShaderPack(this.shaderslist.get(index));
/*  68 */       Shaders.uninit();
/*  69 */       this.shadersGui.updateButtons();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int index) {
/*  78 */     return (index == this.selectedIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/*  83 */     return this.width - 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/*  91 */     return getSize() * 18;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawBackground() {}
/*     */ 
/*     */   
/*     */   protected void func_192637_a(int index, int posX, int posY, int contentY, int mouseX, int mouseY, float partialTicks) {
/* 100 */     String s = this.shaderslist.get(index);
/*     */     
/* 102 */     if (s.equals(Shaders.packNameNone)) {
/*     */       
/* 104 */       s = Lang.get("of.options.shaders.packNone");
/*     */     }
/* 106 */     else if (s.equals(Shaders.packNameDefault)) {
/*     */       
/* 108 */       s = Lang.get("of.options.shaders.packDefault");
/*     */     } 
/*     */     
/* 111 */     this.shadersGui.drawCenteredString(s, this.width / 2, posY + 1, 16777215);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSelectedIndex() {
/* 116 */     return this.selectedIndex;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\GuiSlotShaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */