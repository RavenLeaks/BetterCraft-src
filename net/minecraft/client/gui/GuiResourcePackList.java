/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.resources.ResourcePackListEntry;
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ 
/*    */ public abstract class GuiResourcePackList
/*    */   extends GuiListExtended
/*    */ {
/*    */   protected final Minecraft mc;
/*    */   protected final List<ResourcePackListEntry> resourcePackEntries;
/*    */   
/*    */   public GuiResourcePackList(Minecraft mcIn, int p_i45055_2_, int p_i45055_3_, List<ResourcePackListEntry> p_i45055_4_) {
/* 16 */     super(mcIn, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
/* 17 */     this.mc = mcIn;
/* 18 */     this.resourcePackEntries = p_i45055_4_;
/* 19 */     this.centerListVertically = false;
/* 20 */     setHasListHeader(true, (int)(mcIn.fontRendererObj.FONT_HEIGHT * 1.5F));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawListHeader(int insideLeft, int insideTop, Tessellator tessellatorIn) {
/* 28 */     String s = TextFormatting.UNDERLINE + TextFormatting.BOLD + getListHeader();
/* 29 */     this.mc.fontRendererObj.drawString(s, insideLeft + this.width / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2, Math.min(this.top + 3, insideTop), 16777215);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ResourcePackListEntry> getList() {
/* 36 */     return this.resourcePackEntries;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSize() {
/* 41 */     return getList().size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourcePackListEntry getListEntry(int index) {
/* 49 */     return getList().get(index);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getListWidth() {
/* 57 */     return this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getScrollBarX() {
/* 62 */     return this.right - 6;
/*    */   }
/*    */   
/*    */   protected abstract String getListHeader();
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiResourcePackList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */