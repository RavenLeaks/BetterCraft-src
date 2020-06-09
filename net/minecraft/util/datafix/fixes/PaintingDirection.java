/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class PaintingDirection
/*    */   implements IFixableData
/*    */ {
/*    */   public int getFixVersion() {
/* 11 */     return 111;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 16 */     String s = compound.getString("id");
/* 17 */     boolean flag = "Painting".equals(s);
/* 18 */     boolean flag1 = "ItemFrame".equals(s);
/*    */     
/* 20 */     if ((flag || flag1) && !compound.hasKey("Facing", 99)) {
/*    */       EnumFacing enumfacing;
/*    */ 
/*    */       
/* 24 */       if (compound.hasKey("Direction", 99)) {
/*    */         
/* 26 */         enumfacing = EnumFacing.getHorizontal(compound.getByte("Direction"));
/* 27 */         compound.setInteger("TileX", compound.getInteger("TileX") + enumfacing.getFrontOffsetX());
/* 28 */         compound.setInteger("TileY", compound.getInteger("TileY") + enumfacing.getFrontOffsetY());
/* 29 */         compound.setInteger("TileZ", compound.getInteger("TileZ") + enumfacing.getFrontOffsetZ());
/* 30 */         compound.removeTag("Direction");
/*    */         
/* 32 */         if (flag1 && compound.hasKey("ItemRotation", 99))
/*    */         {
/* 34 */           compound.setByte("ItemRotation", (byte)(compound.getByte("ItemRotation") * 2));
/*    */         }
/*    */       }
/*    */       else {
/*    */         
/* 39 */         enumfacing = EnumFacing.getHorizontal(compound.getByte("Dir"));
/* 40 */         compound.removeTag("Dir");
/*    */       } 
/*    */       
/* 43 */       compound.setByte("Facing", (byte)enumfacing.getHorizontalIndex());
/*    */     } 
/*    */     
/* 46 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\PaintingDirection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */