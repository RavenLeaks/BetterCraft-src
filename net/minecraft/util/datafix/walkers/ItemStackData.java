/*    */ package net.minecraft.util.datafix.walkers;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.DataFixesManager;
/*    */ import net.minecraft.util.datafix.IDataFixer;
/*    */ 
/*    */ public class ItemStackData
/*    */   extends Filtered
/*    */ {
/*    */   private final String[] matchingTags;
/*    */   
/*    */   public ItemStackData(Class<?> p_i47311_1_, String... p_i47311_2_) {
/* 13 */     super(p_i47311_1_);
/* 14 */     this.matchingTags = p_i47311_2_;
/*    */   } NBTTagCompound filteredProcess(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
/*    */     byte b;
/*    */     int i;
/*    */     String[] arrayOfString;
/* 19 */     for (i = (arrayOfString = this.matchingTags).length, b = 0; b < i; ) { String s = arrayOfString[b];
/*    */       
/* 21 */       compound = DataFixesManager.processItemStack(fixer, compound, versionIn, s);
/*    */       b++; }
/*    */     
/* 24 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\walkers\ItemStackData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */