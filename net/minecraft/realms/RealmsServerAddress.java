/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ServerAddress;
/*    */ 
/*    */ 
/*    */ public class RealmsServerAddress
/*    */ {
/*    */   private final String host;
/*    */   private final int port;
/*    */   
/*    */   protected RealmsServerAddress(String hostIn, int portIn) {
/* 12 */     this.host = hostIn;
/* 13 */     this.port = portIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHost() {
/* 18 */     return this.host;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPort() {
/* 23 */     return this.port;
/*    */   }
/*    */ 
/*    */   
/*    */   public static RealmsServerAddress parseString(String p_parseString_0_) {
/* 28 */     ServerAddress serveraddress = ServerAddress.fromString(p_parseString_0_);
/* 29 */     return new RealmsServerAddress(serveraddress.getIP(), serveraddress.getPort());
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\realms\RealmsServerAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */