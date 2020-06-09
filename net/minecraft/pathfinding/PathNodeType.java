/*    */ package net.minecraft.pathfinding;
/*    */ 
/*    */ public enum PathNodeType
/*    */ {
/*  5 */   BLOCKED(-1.0F),
/*  6 */   OPEN(0.0F),
/*  7 */   WALKABLE(0.0F),
/*  8 */   TRAPDOOR(0.0F),
/*  9 */   FENCE(-1.0F),
/* 10 */   LAVA(-1.0F),
/* 11 */   WATER(8.0F),
/* 12 */   RAIL(0.0F),
/* 13 */   DANGER_FIRE(8.0F),
/* 14 */   DAMAGE_FIRE(16.0F),
/* 15 */   DANGER_CACTUS(8.0F),
/* 16 */   DAMAGE_CACTUS(-1.0F),
/* 17 */   DANGER_OTHER(8.0F),
/* 18 */   DAMAGE_OTHER(-1.0F),
/* 19 */   DOOR_OPEN(0.0F),
/* 20 */   DOOR_WOOD_CLOSED(-1.0F),
/* 21 */   DOOR_IRON_CLOSED(-1.0F);
/*    */   
/*    */   private final float priority;
/*    */ 
/*    */   
/*    */   PathNodeType(float priorityIn) {
/* 27 */     this.priority = priorityIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getPriority() {
/* 32 */     return this.priority;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\PathNodeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */