/*    */ package me.nzxter.bettercraft.mods.checkhost;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class CheckHostServer {
/*    */   private final String name;
/*    */   private final String country;
/*    */   private final String countryCode;
/*    */   private final String city;
/*    */   private final List<String> infos;
/*    */   
/*    */   public CheckHostServer(String name, String country, String countryCode, String city, List<String> infos) {
/* 13 */     this.name = name;
/* 14 */     this.country = country;
/* 15 */     this.countryCode = countryCode;
/* 16 */     this.city = city;
/* 17 */     this.infos = infos;
/*    */   }
/*    */   
/*    */   public String getCity() {
/* 21 */     return this.city;
/*    */   }
/*    */   
/*    */   public String getCountry() {
/* 25 */     return this.country;
/*    */   }
/*    */   
/*    */   public String getCountryCode() {
/* 29 */     return this.countryCode;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 33 */     return this.name;
/*    */   }
/*    */   
/*    */   public List<String> getInfos() {
/* 37 */     return this.infos;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\checkhost\CheckHostServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */