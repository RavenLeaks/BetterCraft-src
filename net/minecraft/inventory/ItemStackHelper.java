/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.util.NonNullList;
/*    */ 
/*    */ public class ItemStackHelper
/*    */ {
/*    */   public static ItemStack getAndSplit(List<ItemStack> stacks, int index, int amount) {
/* 13 */     return (index >= 0 && index < stacks.size() && !((ItemStack)stacks.get(index)).func_190926_b() && amount > 0) ? ((ItemStack)stacks.get(index)).splitStack(amount) : ItemStack.field_190927_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ItemStack getAndRemove(List<ItemStack> stacks, int index) {
/* 18 */     return (index >= 0 && index < stacks.size()) ? stacks.set(index, ItemStack.field_190927_a) : ItemStack.field_190927_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public static NBTTagCompound func_191282_a(NBTTagCompound p_191282_0_, NonNullList<ItemStack> p_191282_1_) {
/* 23 */     return func_191281_a(p_191282_0_, p_191282_1_, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public static NBTTagCompound func_191281_a(NBTTagCompound p_191281_0_, NonNullList<ItemStack> p_191281_1_, boolean p_191281_2_) {
/* 28 */     NBTTagList nbttaglist = new NBTTagList();
/*    */     
/* 30 */     for (int i = 0; i < p_191281_1_.size(); i++) {
/*    */       
/* 32 */       ItemStack itemstack = (ItemStack)p_191281_1_.get(i);
/*    */       
/* 34 */       if (!itemstack.func_190926_b()) {
/*    */         
/* 36 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 37 */         nbttagcompound.setByte("Slot", (byte)i);
/* 38 */         itemstack.writeToNBT(nbttagcompound);
/* 39 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*    */       } 
/*    */     } 
/*    */     
/* 43 */     if (!nbttaglist.hasNoTags() || p_191281_2_)
/*    */     {
/* 45 */       p_191281_0_.setTag("Items", (NBTBase)nbttaglist);
/*    */     }
/*    */     
/* 48 */     return p_191281_0_;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void func_191283_b(NBTTagCompound p_191283_0_, NonNullList<ItemStack> p_191283_1_) {
/* 53 */     NBTTagList nbttaglist = p_191283_0_.getTagList("Items", 10);
/*    */     
/* 55 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*    */       
/* 57 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 58 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*    */       
/* 60 */       if (j >= 0 && j < p_191283_1_.size())
/*    */       {
/* 62 */         p_191283_1_.set(j, new ItemStack(nbttagcompound));
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ItemStackHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */