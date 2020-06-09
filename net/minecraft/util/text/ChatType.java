/*    */ package net.minecraft.util.text;
/*    */ 
/*    */ public enum ChatType
/*    */ {
/*  5 */   CHAT((byte)0),
/*  6 */   SYSTEM((byte)1),
/*  7 */   GAME_INFO((byte)2);
/*    */   
/*    */   private final byte field_192588_d;
/*    */ 
/*    */   
/*    */   ChatType(byte p_i47429_3_) {
/* 13 */     this.field_192588_d = p_i47429_3_;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte func_192583_a() {
/* 18 */     return this.field_192588_d;
/*    */   } public static ChatType func_192582_a(byte p_192582_0_) {
/*    */     byte b;
/*    */     int i;
/*    */     ChatType[] arrayOfChatType;
/* 23 */     for (i = (arrayOfChatType = values()).length, b = 0; b < i; ) { ChatType chattype = arrayOfChatType[b];
/*    */       
/* 25 */       if (p_192582_0_ == chattype.field_192588_d)
/*    */       {
/* 27 */         return chattype;
/*    */       }
/*    */       b++; }
/*    */     
/* 31 */     return CHAT;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\ChatType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */