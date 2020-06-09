/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NextTickListEntry
/*    */   implements Comparable<NextTickListEntry>
/*    */ {
/*    */   private static long nextTickEntryID;
/*    */   private final Block block;
/*    */   public final BlockPos position;
/*    */   public long scheduledTime;
/*    */   public int priority;
/*    */   private final long tickEntryID;
/*    */   
/*    */   public NextTickListEntry(BlockPos positionIn, Block blockIn) {
/* 22 */     this.tickEntryID = nextTickEntryID++;
/* 23 */     this.position = positionIn.toImmutable();
/* 24 */     this.block = blockIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 29 */     if (!(p_equals_1_ instanceof NextTickListEntry))
/*    */     {
/* 31 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 35 */     NextTickListEntry nextticklistentry = (NextTickListEntry)p_equals_1_;
/* 36 */     return (this.position.equals(nextticklistentry.position) && Block.isEqualTo(this.block, nextticklistentry.block));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 42 */     return this.position.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NextTickListEntry setScheduledTime(long scheduledTimeIn) {
/* 50 */     this.scheduledTime = scheduledTimeIn;
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPriority(int priorityIn) {
/* 56 */     this.priority = priorityIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(NextTickListEntry p_compareTo_1_) {
/* 61 */     if (this.scheduledTime < p_compareTo_1_.scheduledTime)
/*    */     {
/* 63 */       return -1;
/*    */     }
/* 65 */     if (this.scheduledTime > p_compareTo_1_.scheduledTime)
/*    */     {
/* 67 */       return 1;
/*    */     }
/* 69 */     if (this.priority != p_compareTo_1_.priority)
/*    */     {
/* 71 */       return this.priority - p_compareTo_1_.priority;
/*    */     }
/* 73 */     if (this.tickEntryID < p_compareTo_1_.tickEntryID)
/*    */     {
/* 75 */       return -1;
/*    */     }
/*    */ 
/*    */     
/* 79 */     return (this.tickEntryID > p_compareTo_1_.tickEntryID) ? 1 : 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 85 */     return String.valueOf(Block.getIdFromBlock(this.block)) + ": " + this.position + ", " + this.scheduledTime + ", " + this.priority + ", " + this.tickEntryID;
/*    */   }
/*    */ 
/*    */   
/*    */   public Block getBlock() {
/* 90 */     return this.block;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\NextTickListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */