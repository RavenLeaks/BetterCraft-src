/*    */ package net.minecraft.util.datafix.walkers;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.DataFixesManager;
/*    */ import net.minecraft.util.datafix.IDataFixer;
/*    */ 
/*    */ public class ItemStackDataLists
/*    */   extends Filtered
/*    */ {
/*    */   private final String[] matchingTags;
/*    */   
/*    */   public ItemStackDataLists(Class<?> p_i47310_1_, String... p_i47310_2_) {
/* 13 */     super(p_i47310_1_);
/* 14 */     this.matchingTags = p_i47310_2_;
/*    */   } NBTTagCompound filteredProcess(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
/*    */     byte b;
/*    */     int i;
/*    */     String[] arrayOfString;
/* 19 */     for (i = (arrayOfString = this.matchingTags).length, b = 0; b < i; ) { String s = arrayOfString[b];
/*    */       
/* 21 */       compound = DataFixesManager.processInventory(fixer, compound, versionIn, s);
/*    */       b++; }
/*    */     
/* 24 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\walkers\ItemStackDataLists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */