/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ public enum EntityEquipmentSlot
/*    */ {
/*  5 */   MAINHAND(Type.HAND, 0, 0, "mainhand"),
/*  6 */   OFFHAND(Type.HAND, 1, 5, "offhand"),
/*  7 */   FEET(Type.ARMOR, 0, 1, "feet"),
/*  8 */   LEGS(Type.ARMOR, 1, 2, "legs"),
/*  9 */   CHEST(Type.ARMOR, 2, 3, "chest"),
/* 10 */   HEAD(Type.ARMOR, 3, 4, "head");
/*    */   
/*    */   private final Type slotType;
/*    */   
/*    */   private final int index;
/*    */   private final int slotIndex;
/*    */   private final String name;
/*    */   
/*    */   EntityEquipmentSlot(Type slotTypeIn, int indexIn, int slotIndexIn, String nameIn) {
/* 19 */     this.slotType = slotTypeIn;
/* 20 */     this.index = indexIn;
/* 21 */     this.slotIndex = slotIndexIn;
/* 22 */     this.name = nameIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public Type getSlotType() {
/* 27 */     return this.slotType;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getIndex() {
/* 32 */     return this.index;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSlotIndex() {
/* 40 */     return this.slotIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 45 */     return this.name;
/*    */   } public static EntityEquipmentSlot fromString(String targetName) {
/*    */     byte b;
/*    */     int i;
/*    */     EntityEquipmentSlot[] arrayOfEntityEquipmentSlot;
/* 50 */     for (i = (arrayOfEntityEquipmentSlot = values()).length, b = 0; b < i; ) { EntityEquipmentSlot entityequipmentslot = arrayOfEntityEquipmentSlot[b];
/*    */       
/* 52 */       if (entityequipmentslot.getName().equals(targetName))
/*    */       {
/* 54 */         return entityequipmentslot;
/*    */       }
/*    */       b++; }
/*    */     
/* 58 */     throw new IllegalArgumentException("Invalid slot '" + targetName + "'");
/*    */   }
/*    */   
/*    */   public enum Type {
/* 62 */     HAND,
/* 63 */     ARMOR;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\EntityEquipmentSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */