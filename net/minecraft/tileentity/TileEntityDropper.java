/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.util.datafix.FixTypes;
/*    */ import net.minecraft.util.datafix.IDataWalker;
/*    */ import net.minecraft.util.datafix.walkers.ItemStackDataLists;
/*    */ 
/*    */ public class TileEntityDropper
/*    */   extends TileEntityDispenser {
/*    */   public static void registerFixesDropper(DataFixer fixer) {
/* 11 */     fixer.registerWalker(FixTypes.BLOCK_ENTITY, (IDataWalker)new ItemStackDataLists(TileEntityDropper.class, new String[] { "Items" }));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 19 */     return hasCustomName() ? this.field_190577_o : "container.dropper";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getGuiID() {
/* 24 */     return "minecraft:dropper";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityDropper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */