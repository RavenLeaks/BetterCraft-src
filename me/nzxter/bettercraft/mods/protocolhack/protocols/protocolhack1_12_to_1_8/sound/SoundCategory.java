/*    */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.sound;
/*    */ 
/*    */ public enum SoundCategory {
/*  4 */   MASTER("master", 0),
/*  5 */   MUSIC("music", 1),
/*  6 */   RECORD("record", 2),
/*  7 */   WEATHER("weather", 3),
/*  8 */   BLOCK("block", 4),
/*  9 */   HOSTILE("hostile", 5),
/* 10 */   NEUTRAL("neutral", 6),
/* 11 */   PLAYER("player", 7),
/* 12 */   AMBIENT("ambient", 8),
/* 13 */   VOICE("voice", 9);
/*    */   
/*    */   private final String name;
/*    */   private final int id;
/*    */   
/*    */   SoundCategory(String a, int i) {
/* 19 */     this.name = a;
/* 20 */     this.id = i;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 24 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 28 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\sound\SoundCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */