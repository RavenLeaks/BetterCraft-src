/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class ItemStackUtils
/*    */ {
/* 14 */   public static final ItemStack empty = new ItemStack(Blocks.AIR);
/*    */   
/*    */   public static void addEmpty(List<ItemStack> stacks, int num) {
/* 17 */     int i = 0;
/* 18 */     while (i < num) {
/* 19 */       stacks.add(empty);
/* 20 */       i++;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void fillEmpty(List<ItemStack> stacks) {
/* 25 */     addEmpty(stacks, 9 - stacks.size() % 9);
/*    */   }
/*    */   
/*    */   public static void addEmpty(List<ItemStack> stacks) {
/* 29 */     stacks.add(empty);
/*    */   }
/*    */   
/*    */   public static ItemStack stringtostack(String Sargs) {
/*    */     try {
/* 34 */       Sargs = Sargs.replace('&', '§');
/* 35 */       Item item = new Item();
/* 36 */       String[] args = null;
/* 37 */       int i = 1;
/* 38 */       int j = 0;
/* 39 */       args = Sargs.split(" ");
/* 40 */       ResourceLocation resourcelocation = new ResourceLocation(args[0]);
/* 41 */       item = (Item)Item.REGISTRY.getObject(resourcelocation);
/* 42 */       if (args.length >= 2 && args[1].matches("\\d+")) {
/* 43 */         i = Integer.parseInt(args[1]);
/*    */       }
/* 45 */       if (args.length >= 3 && args[2].matches("\\d+")) {
/* 46 */         j = Integer.parseInt(args[2]);
/*    */       }
/* 48 */       ItemStack itemstack = new ItemStack(item, i, j);
/* 49 */       if (args.length >= 4) {
/* 50 */         String NBT = "";
/* 51 */         int nbtcount = 3;
/* 52 */         while (nbtcount < args.length) {
/* 53 */           NBT = String.valueOf(String.valueOf(NBT)) + " " + args[nbtcount];
/* 54 */           nbtcount++;
/*    */         } 
/* 56 */         itemstack.setTagCompound(JsonToNBT.getTagFromJson(NBT));
/*    */       } 
/* 58 */       return itemstack;
/*    */     }
/* 60 */     catch (Exception ex) {
/* 61 */       ex.printStackTrace();
/* 62 */       return new ItemStack(Blocks.BARRIER);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void removeSuspiciousTags(ItemStack item, boolean force, boolean display, boolean hideFlags) {
/* 68 */     NBTTagCompound tag = item.hasTagCompound() ? item.getTagCompound() : new NBTTagCompound(), nBTTagCompound = tag;
/* 69 */     if (force || !tag.hasKey("Exploit")) {
/* 70 */       tag.setByte("Exploit", (byte)((display ? 1 : 0) + (hideFlags ? 2 : 0)));
/*    */     }
/* 72 */     item.setTagCompound(tag);
/*    */   }
/*    */   
/*    */   public static void removeSuspiciousTags(List<ItemStack> itemList, boolean display, boolean hideFlags) {
/* 76 */     for (ItemStack item : itemList) {
/* 77 */       removeSuspiciousTags(item, false, display, hideFlags);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void removeSuspiciousTags(List<ItemStack> itemList) {
/* 82 */     removeSuspiciousTags(itemList, true, true);
/*    */   }
/*    */   
/*    */   public static void modify(ItemStack stack) {
/* 86 */     if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("Exploit")) {
/* 87 */       byte state = stack.getTagCompound().getByte("Exploit");
/* 88 */       stack.getTagCompound().removeTag("Exploit");
/* 89 */       if (state % 2 == 1 && stack.getTagCompound().hasKey("display", 10)) {
/* 90 */         stack.getTagCompound().removeTag("display");
/*    */       }
/* 92 */       if (state % 4 == 1)
/* 93 */         stack.getTagCompound().setByte("HideFlags", (byte)63); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\ItemStackUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */