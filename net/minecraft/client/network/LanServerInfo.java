/*    */ package net.minecraft.client.network;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LanServerInfo
/*    */ {
/*    */   private final String lanServerMotd;
/*    */   private final String lanServerIpPort;
/*    */   private long timeLastSeen;
/*    */   
/*    */   public LanServerInfo(String p_i47130_1_, String p_i47130_2_) {
/* 15 */     this.lanServerMotd = p_i47130_1_;
/* 16 */     this.lanServerIpPort = p_i47130_2_;
/* 17 */     this.timeLastSeen = Minecraft.getSystemTime();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getServerMotd() {
/* 22 */     return this.lanServerMotd;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getServerIpPort() {
/* 27 */     return this.lanServerIpPort;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateLastSeen() {
/* 35 */     this.timeLastSeen = Minecraft.getSystemTime();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\network\LanServerInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */