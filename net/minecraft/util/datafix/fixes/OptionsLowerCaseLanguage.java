/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class OptionsLowerCaseLanguage
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 11 */     return 816;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 16 */     if (compound.hasKey("lang", 8))
/*    */     {
/* 18 */       compound.setString("lang", compound.getString("lang").toLowerCase(Locale.ROOT));
/*    */     }
/*    */     
/* 21 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\OptionsLowerCaseLanguage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */