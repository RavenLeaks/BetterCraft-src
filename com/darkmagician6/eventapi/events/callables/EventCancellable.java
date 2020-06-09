/*    */ package com.darkmagician6.eventapi.events.callables;
/*    */ 
/*    */ import com.darkmagician6.eventapi.events.Cancellable;
/*    */ import com.darkmagician6.eventapi.events.Event;
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
/*    */ public abstract class EventCancellable
/*    */   implements Event, Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   
/*    */   public boolean isCancelled() {
/* 24 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean state) {
/* 32 */     this.cancelled = state;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\darkmagician6\eventapi\events\callables\EventCancellable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */