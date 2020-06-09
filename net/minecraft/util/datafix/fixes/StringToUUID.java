/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class StringToUUID
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 11 */     return 108;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 16 */     if (compound.hasKey("UUID", 8))
/*    */     {
/* 18 */       compound.setUniqueId("UUID", UUID.fromString(compound.getString("UUID")));
/*    */     }
/*    */     
/* 21 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\StringToUUID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */