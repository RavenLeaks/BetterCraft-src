/*    */ package com.darkmagician6.eventapi.events;
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
/*    */ public abstract class EventStoppable
/*    */   implements Event
/*    */ {
/*    */   private boolean stopped;
/*    */   
/*    */   public void stop() {
/* 25 */     this.stopped = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isStopped() {
/* 35 */     return this.stopped;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\darkmagician6\eventapi\events\EventStoppable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */