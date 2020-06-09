/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.SPacketCooldown;
/*    */ 
/*    */ public class CooldownTrackerServer
/*    */   extends CooldownTracker {
/*    */   private final EntityPlayerMP player;
/*    */   
/*    */   public CooldownTrackerServer(EntityPlayerMP playerIn) {
/* 13 */     this.player = playerIn;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void notifyOnSet(Item itemIn, int ticksIn) {
/* 18 */     super.notifyOnSet(itemIn, ticksIn);
/* 19 */     this.player.connection.sendPacket((Packet)new SPacketCooldown(itemIn, ticksIn));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void notifyOnRemove(Item itemIn) {
/* 24 */     super.notifyOnRemove(itemIn);
/* 25 */     this.player.connection.sendPacket((Packet)new SPacketCooldown(itemIn, 0));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\CooldownTrackerServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */