/*    */ package wdl;
/*    */ 
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import wdl.api.ISpecialEntityHandler;
/*    */ import wdl.api.IWDLModDescripted;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HologramHandler
/*    */   implements ISpecialEntityHandler, IWDLModDescripted
/*    */ {
/*    */   public boolean isValidEnvironment(String version) {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getEnvironmentErrorMessage(String version) {
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 41 */     return "Hologram support";
/*    */   }
/*    */ 
/*    */   
/*    */   public Multimap<String, String> getSpecialEntities() {
/* 46 */     HashMultimap hashMultimap = HashMultimap.create();
/*    */     
/* 48 */     hashMultimap.put("ArmorStand", "Hologram");
/*    */     
/* 50 */     return (Multimap<String, String>)hashMultimap;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSpecialEntityName(Entity entity) {
/* 55 */     if (entity instanceof net.minecraft.entity.item.EntityArmorStand && 
/* 56 */       entity.isInvisible() && 
/* 57 */       entity.hasCustomName()) {
/* 58 */       return "Hologram";
/*    */     }
/*    */     
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSpecialEntityCategory(String name) {
/* 66 */     if (name.equals("Hologram")) {
/* 67 */       return "Other";
/*    */     }
/* 69 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSpecialEntityTrackDistance(String name) {
/* 74 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMainAuthor() {
/* 79 */     return "Pokechu22";
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAuthors() {
/* 84 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getURL() {
/* 89 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 94 */     return "Provides basic support for disabling holograms.";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\HologramHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */