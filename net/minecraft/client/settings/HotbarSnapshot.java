/*    */ package net.minecraft.client.settings;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ 
/*    */ public class HotbarSnapshot extends ArrayList<ItemStack> {
/* 11 */   public static final int field_192835_a = InventoryPlayer.getHotbarSize();
/*    */ 
/*    */   
/*    */   public HotbarSnapshot() {
/* 15 */     ensureCapacity(field_192835_a);
/*    */     
/* 17 */     for (int i = 0; i < field_192835_a; i++)
/*    */     {
/* 19 */       add(ItemStack.field_190927_a);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagList func_192834_a() {
/* 25 */     NBTTagList nbttaglist = new NBTTagList();
/*    */     
/* 27 */     for (int i = 0; i < field_192835_a; i++)
/*    */     {
/* 29 */       nbttaglist.appendTag((NBTBase)get(i).writeToNBT(new NBTTagCompound()));
/*    */     }
/*    */     
/* 32 */     return nbttaglist;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_192833_a(NBTTagList p_192833_1_) {
/* 37 */     for (int i = 0; i < field_192835_a; i++)
/*    */     {
/* 39 */       set(i, new ItemStack(p_192833_1_.getCompoundTagAt(i)));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 45 */     for (int i = 0; i < field_192835_a; i++) {
/*    */       
/* 47 */       if (!get(i).func_190926_b())
/*    */       {
/* 49 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\settings\HotbarSnapshot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */