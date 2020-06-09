/*    */ package me.nzxter.bettercraft.events;
/*    */ 
/*    */ import com.darkmagician6.eventapi.events.Event;
/*    */ 
/*    */ public class ChatMessageSendEvent
/*    */   implements Event {
/*    */   private String message;
/*    */   private boolean cancelled;
/*    */   
/*    */   public ChatMessageSendEvent(String message) {
/* 11 */     this.message = message;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 15 */     return this.message;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 19 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   public void setMessage(String message) {
/* 23 */     this.message = message;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 27 */     return this.cancelled;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\events\ChatMessageSendEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */