/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class BedItemColor
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 11 */     return 1125;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 16 */     if ("minecraft:bed".equals(compound.getString("id")) && compound.getShort("Damage") == 0)
/*    */     {
/* 18 */       compound.setShort("Damage", (short)EnumDyeColor.RED.getMetadata());
/*    */     }
/*    */     
/* 21 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\BedItemColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */