/*    */ package javazoom.jl.player.advanced;
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
/*    */ public class PlaybackEvent
/*    */ {
/* 28 */   public static int STOPPED = 1;
/* 29 */   public static int STARTED = 2;
/*    */   
/*    */   private AdvancedPlayer source;
/*    */   
/*    */   private int frame;
/*    */   private int id;
/*    */   
/*    */   public PlaybackEvent(AdvancedPlayer source, int id, int frame) {
/* 37 */     this.id = id;
/* 38 */     this.source = source;
/* 39 */     this.frame = frame;
/*    */   }
/*    */   
/* 42 */   public int getId() { return this.id; } public void setId(int id) {
/* 43 */     this.id = id;
/*    */   }
/* 45 */   public int getFrame() { return this.frame; } public void setFrame(int frame) {
/* 46 */     this.frame = frame;
/*    */   }
/* 48 */   public AdvancedPlayer getSource() { return this.source; } public void setSource(AdvancedPlayer source) {
/* 49 */     this.source = source;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\advanced\PlaybackEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */