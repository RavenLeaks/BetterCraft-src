/*    */ package me.nzxter.bettercraft.mods.checkhost;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class CheckHostResult<T>
/*    */ {
/*    */   private final CheckHostType type;
/*    */   private final List<CheckHostServer> servers;
/*    */   private final String requestId;
/*    */   private T result;
/*    */   
/*    */   public CheckHostResult(CheckHostType type, String requestId, List<CheckHostServer> servers) throws IOException {
/* 15 */     this.type = type;
/* 16 */     this.requestId = requestId;
/* 17 */     this.servers = servers;
/* 18 */     update();
/*    */   }
/*    */   
/*    */   public String getRequestId() {
/* 22 */     return this.requestId;
/*    */   }
/*    */   
/*    */   public T getResult() {
/* 26 */     return this.result;
/*    */   }
/*    */   
/*    */   public CheckHostType getType() {
/* 30 */     return this.type;
/*    */   }
/*    */   
/*    */   public List<CheckHostServer> getServers() {
/* 34 */     return this.servers;
/*    */   }
/*    */   
/*    */   public void update() throws IOException {
/* 38 */     Map.Entry<String, List<CheckHostServer>> entry = new Map.Entry<String, List<CheckHostServer>>()
/*    */       {
/*    */         public String getKey() {
/* 41 */           return CheckHostResult.this.requestId;
/*    */         }
/*    */ 
/*    */         
/*    */         public List<CheckHostServer> getValue() {
/* 46 */           return CheckHostResult.this.servers;
/*    */         }
/*    */ 
/*    */         
/*    */         public List<CheckHostServer> setValue(List<CheckHostServer> value) {
/* 51 */           return CheckHostResult.this.servers;
/*    */         }
/*    */       };
/* 54 */     switch (this.type) {
/*    */       case PING:
/* 56 */         this.result = (T)CheckHostAPI.ping(entry);
/*    */         break;
/*    */       
/*    */       case null:
/* 60 */         this.result = (T)CheckHostAPI.dns(entry);
/*    */         break;
/*    */       
/*    */       case HTTP:
/* 64 */         this.result = (T)CheckHostAPI.http(entry);
/*    */         break;
/*    */       
/*    */       case UDP:
/* 68 */         this.result = (T)CheckHostAPI.udp(entry);
/*    */         break;
/*    */       
/*    */       case TCP:
/* 72 */         this.result = (T)CheckHostAPI.tcp(entry);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\checkhost\CheckHostResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */