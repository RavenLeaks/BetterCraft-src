/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class CookedFishIDTypo
/*    */   implements IFixableData {
/*  9 */   private static final ResourceLocation WRONG = new ResourceLocation("cooked_fished");
/*    */ 
/*    */   
/*    */   public int getFixVersion() {
/* 13 */     return 502;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 18 */     if (compound.hasKey("id", 8) && WRONG.equals(new ResourceLocation(compound.getString("id"))))
/*    */     {
/* 20 */       compound.setString("id", "minecraft:cooked_fish");
/*    */     }
/*    */     
/* 23 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\CookedFishIDTypo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */