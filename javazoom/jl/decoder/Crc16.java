/*    */ package javazoom.jl.decoder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Crc16
/*    */ {
/* 32 */   private static short polynomial = -32763;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   private short crc = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add_bits(int bitstring, int length) {
/* 48 */     int bitmask = 1 << length - 1;
/*    */     do {
/* 50 */       if (((((this.crc & 0x8000) == 0) ? 1 : 0) ^ (((bitstring & bitmask) == 0) ? 1 : 0)) != 0)
/*    */       
/* 52 */       { this.crc = (short)(this.crc << 1);
/* 53 */         this.crc = (short)(this.crc ^ polynomial); }
/*    */       else
/*    */       
/* 56 */       { this.crc = (short)(this.crc << 1); } 
/* 57 */     } while ((bitmask >>>= 1) != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short checksum() {
/* 66 */     short sum = this.crc;
/* 67 */     this.crc = -1;
/* 68 */     return sum;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\Crc16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */