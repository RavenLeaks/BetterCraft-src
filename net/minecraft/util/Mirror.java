/*    */ package net.minecraft.util;
/*    */ 
/*    */ public enum Mirror
/*    */ {
/*  5 */   NONE("no_mirror"),
/*  6 */   LEFT_RIGHT("mirror_left_right"),
/*  7 */   FRONT_BACK("mirror_front_back"); private final String name;
/*    */   
/*    */   static {
/* 10 */     mirrorNames = new String[(values()).length];
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
/* 91 */     int i = 0; byte b; int j;
/*    */     Mirror[] arrayOfMirror;
/* 93 */     for (j = (arrayOfMirror = values()).length, b = 0; b < j; ) { Mirror mirror = arrayOfMirror[b];
/*    */       
/* 95 */       mirrorNames[i++] = mirror.name;
/*    */       b++; }
/*    */   
/*    */   }
/*    */   
/*    */   private static final String[] mirrorNames;
/*    */   
/*    */   Mirror(String nameIn) {
/*    */     this.name = nameIn;
/*    */   }
/*    */   
/*    */   public int mirrorRotation(int rotationIn, int rotationCount) {
/*    */     int i = rotationCount / 2;
/*    */     int j = (rotationIn > i) ? (rotationIn - rotationCount) : rotationIn;
/*    */     switch (this) {
/*    */       case null:
/*    */         return (rotationCount - j) % rotationCount;
/*    */       case LEFT_RIGHT:
/*    */         return (i - j + rotationCount) % rotationCount;
/*    */     } 
/*    */     return rotationIn;
/*    */   }
/*    */   
/*    */   public Rotation toRotation(EnumFacing facing) {
/*    */     EnumFacing.Axis enumfacing$axis = facing.getAxis();
/*    */     return ((this != LEFT_RIGHT || enumfacing$axis != EnumFacing.Axis.Z) && (this != FRONT_BACK || enumfacing$axis != EnumFacing.Axis.X)) ? Rotation.NONE : Rotation.CLOCKWISE_180;
/*    */   }
/*    */   
/*    */   public EnumFacing mirror(EnumFacing facing) {
/*    */     switch (this) {
/*    */       case null:
/*    */         if (facing == EnumFacing.WEST)
/*    */           return EnumFacing.EAST; 
/*    */         if (facing == EnumFacing.EAST)
/*    */           return EnumFacing.WEST; 
/*    */         return facing;
/*    */       case LEFT_RIGHT:
/*    */         if (facing == EnumFacing.NORTH)
/*    */           return EnumFacing.SOUTH; 
/*    */         if (facing == EnumFacing.SOUTH)
/*    */           return EnumFacing.NORTH; 
/*    */         return facing;
/*    */     } 
/*    */     return facing;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\Mirror.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */