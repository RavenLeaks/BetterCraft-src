/*    */ package net.minecraftforge.client.model.pipeline;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
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
/*    */ public class VertexBufferConsumer
/*    */   implements IVertexConsumer
/*    */ {
/* 34 */   private static final float[] dummyColor = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
/*    */   
/*    */   private BufferBuilder renderer;
/*    */   private int[] quadData;
/* 38 */   private int v = 0;
/* 39 */   private BlockPos offset = BlockPos.ORIGIN;
/*    */ 
/*    */   
/*    */   public VertexBufferConsumer() {}
/*    */   
/*    */   public VertexBufferConsumer(BufferBuilder buffer) {
/* 45 */     setBuffer(buffer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public VertexFormat getVertexFormat() {
/* 51 */     return this.renderer.getVertexFormat();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void put(int e, float... data) {
/* 57 */     VertexFormat format = getVertexFormat();
/* 58 */     if (this.renderer.isColorDisabled() && format.getElement(e).getUsage() == VertexFormatElement.EnumUsage.COLOR)
/*    */     {
/* 60 */       data = dummyColor;
/*    */     }
/* 62 */     LightUtil.pack(data, this.quadData, format, this.v, e);
/* 63 */     if (e == format.getElementCount() - 1) {
/*    */       
/* 65 */       this.v++;
/* 66 */       if (this.v == 4) {
/*    */         
/* 68 */         this.renderer.addVertexData(this.quadData);
/* 69 */         this.renderer.putPosition(this.offset.getX(), this.offset.getY(), this.offset.getZ());
/*    */         
/* 71 */         this.v = 0;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void checkVertexFormat() {
/* 78 */     if (this.quadData == null || this.renderer.getVertexFormat().getNextOffset() != this.quadData.length)
/*    */     {
/* 80 */       this.quadData = new int[this.renderer.getVertexFormat().getNextOffset()];
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBuffer(BufferBuilder buffer) {
/* 86 */     this.renderer = buffer;
/* 87 */     checkVertexFormat();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOffset(BlockPos offset) {
/* 92 */     this.offset = new BlockPos((Vec3i)offset);
/*    */   }
/*    */   
/*    */   public void setQuadTint(int tint) {}
/*    */   
/*    */   public void setQuadOrientation(EnumFacing orientation) {}
/*    */   
/*    */   public void setQuadColored() {}
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraftforge\client\model\pipeline\VertexBufferConsumer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */