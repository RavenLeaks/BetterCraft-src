/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class MinecartEntityTypes
/*    */   implements IFixableData {
/* 10 */   private static final List<String> MINECART_TYPE_LIST = Lists.newArrayList((Object[])new String[] { "MinecartRideable", "MinecartChest", "MinecartFurnace", "MinecartTNT", "MinecartSpawner", "MinecartHopper", "MinecartCommandBlock" });
/*    */ 
/*    */   
/*    */   public int getFixVersion() {
/* 14 */     return 106;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 19 */     if ("Minecart".equals(compound.getString("id"))) {
/*    */       
/* 21 */       String s = "MinecartRideable";
/* 22 */       int i = compound.getInteger("Type");
/*    */       
/* 24 */       if (i > 0 && i < MINECART_TYPE_LIST.size())
/*    */       {
/* 26 */         s = MINECART_TYPE_LIST.get(i);
/*    */       }
/*    */       
/* 29 */       compound.setString("id", s);
/* 30 */       compound.removeTag("Type");
/*    */     } 
/*    */     
/* 33 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\MinecartEntityTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */