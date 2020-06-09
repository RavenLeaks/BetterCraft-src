/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class ShulkerBoxTileColor
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 10 */     return 813;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 15 */     if ("minecraft:shulker".equals(compound.getString("id")))
/*    */     {
/* 17 */       compound.removeTag("Color");
/*    */     }
/*    */     
/* 20 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\ShulkerBoxTileColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */