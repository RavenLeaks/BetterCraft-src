/*    */ package shadersmod.client;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ 
/*    */ public class SVertexFormatElement
/*    */   extends VertexFormatElement
/*    */ {
/*    */   int sUsage;
/*    */   
/*    */   public SVertexFormatElement(int sUsage, VertexFormatElement.EnumType type, int count) {
/* 11 */     super(0, type, VertexFormatElement.EnumUsage.PADDING, count);
/* 12 */     this.sUsage = sUsage;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\SVertexFormatElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */