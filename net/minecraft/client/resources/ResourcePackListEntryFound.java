/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*    */ 
/*    */ public class ResourcePackListEntryFound
/*    */   extends ResourcePackListEntry
/*    */ {
/*    */   private final ResourcePackRepository.Entry resourcePackEntry;
/*    */   
/*    */   public ResourcePackListEntryFound(GuiScreenResourcePacks resourcePacksGUIIn, ResourcePackRepository.Entry entry) {
/* 11 */     super(resourcePacksGUIIn);
/* 12 */     this.resourcePackEntry = entry;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void bindResourcePackIcon() {
/* 17 */     this.resourcePackEntry.bindTexturePackIcon(this.mc.getTextureManager());
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getResourcePackFormat() {
/* 22 */     return this.resourcePackEntry.getPackFormat();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getResourcePackDescription() {
/* 27 */     return this.resourcePackEntry.getTexturePackDescription();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getResourcePackName() {
/* 32 */     return this.resourcePackEntry.getResourcePackName();
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourcePackRepository.Entry getResourcePackEntry() {
/* 37 */     return this.resourcePackEntry;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\ResourcePackListEntryFound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */