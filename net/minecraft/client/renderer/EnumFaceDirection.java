/*    */ package net.minecraft.client.renderer;public enum EnumFaceDirection { private static final EnumFaceDirection[] FACINGS; private final VertexInformation[] vertexInfos;
/*    */   
/*    */   public static EnumFaceDirection getFacing(EnumFacing facing) {
/*    */     return FACINGS[facing.getIndex()];
/*    */   }
/*    */   
/*  7 */   DOWN(new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null) }),
/*  8 */   UP(new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null) }),
/*  9 */   NORTH(new VertexInformation[] { new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null) }),
/* 10 */   SOUTH(new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null) }),
/* 11 */   WEST(new VertexInformation[] { new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.WEST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null) }),
/* 12 */   EAST(new VertexInformation[] { new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.SOUTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.DOWN_INDEX, Constants.NORTH_INDEX, null), new VertexInformation(Constants.EAST_INDEX, Constants.UP_INDEX, Constants.NORTH_INDEX, null) });
/*    */   EnumFaceDirection(VertexInformation[] vertexInfosIn) { this.vertexInfos = vertexInfosIn; } static {
/* 14 */     FACINGS = new EnumFaceDirection[6];
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
/* 33 */     FACINGS[Constants.DOWN_INDEX] = DOWN;
/* 34 */     FACINGS[Constants.UP_INDEX] = UP;
/* 35 */     FACINGS[Constants.NORTH_INDEX] = NORTH;
/* 36 */     FACINGS[Constants.SOUTH_INDEX] = SOUTH;
/* 37 */     FACINGS[Constants.WEST_INDEX] = WEST;
/* 38 */     FACINGS[Constants.EAST_INDEX] = EAST;
/*    */   } public VertexInformation getVertexInformation(int index) {
/*    */     return this.vertexInfos[index];
/*    */   }
/* 42 */   public static final class Constants { public static final int SOUTH_INDEX = EnumFacing.SOUTH.getIndex();
/* 43 */     public static final int UP_INDEX = EnumFacing.UP.getIndex();
/* 44 */     public static final int EAST_INDEX = EnumFacing.EAST.getIndex();
/* 45 */     public static final int NORTH_INDEX = EnumFacing.NORTH.getIndex();
/* 46 */     public static final int DOWN_INDEX = EnumFacing.DOWN.getIndex();
/* 47 */     public static final int WEST_INDEX = EnumFacing.WEST.getIndex(); }
/*    */ 
/*    */   
/*    */   public static class VertexInformation
/*    */   {
/*    */     public final int xIndex;
/*    */     public final int yIndex;
/*    */     public final int zIndex;
/*    */     
/*    */     private VertexInformation(int xIndexIn, int yIndexIn, int zIndexIn) {
/* 57 */       this.xIndex = xIndexIn;
/* 58 */       this.yIndex = yIndexIn;
/* 59 */       this.zIndex = zIndexIn;
/*    */     }
/*    */   } }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\EnumFaceDirection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */