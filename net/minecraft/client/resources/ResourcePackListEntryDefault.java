/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*    */ 
/*    */ public class ResourcePackListEntryDefault
/*    */   extends ResourcePackListEntryServer
/*    */ {
/*    */   public ResourcePackListEntryDefault(GuiScreenResourcePacks resourcePacksGUIIn) {
/* 10 */     super(resourcePacksGUIIn, (Minecraft.getMinecraft().getResourcePackRepository()).rprDefaultResourcePack);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getResourcePackName() {
/* 15 */     return "Default";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isServerPack() {
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\ResourcePackListEntryDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */