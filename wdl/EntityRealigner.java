/*    */ package wdl;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import wdl.api.IEntityEditor;
/*    */ import wdl.api.IWDLModDescripted;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityRealigner
/*    */   implements IEntityEditor, IWDLModDescripted
/*    */ {
/*    */   public boolean isValidEnvironment(String version) {
/* 18 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getEnvironmentErrorMessage(String version) {
/* 23 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 28 */     return "Entity realigner";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMainAuthor() {
/* 33 */     return "Pokechu22";
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAuthors() {
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getURL() {
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 48 */     return "Realigns entities to their serverside position to deal with entities that drift clientside (for example, boats).";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldEdit(Entity e) {
/* 63 */     return !(e.serverPosX == 0L && e.serverPosY == 0L && e.serverPosZ == 0L);
/*    */   }
/*    */ 
/*    */   
/*    */   public void editEntity(Entity e) {
/* 68 */     e.posX = convertServerPos(e.serverPosX);
/* 69 */     e.posY = convertServerPos(e.serverPosY);
/* 70 */     e.posZ = convertServerPos(e.serverPosZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static double convertServerPos(long serverPos) {
/* 85 */     return serverPos / 4096.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\EntityRealigner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */