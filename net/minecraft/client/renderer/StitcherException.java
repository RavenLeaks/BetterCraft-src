/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.client.renderer.texture.Stitcher;
/*    */ 
/*    */ public class StitcherException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final Stitcher.Holder holder;
/*    */   
/*    */   public StitcherException(Stitcher.Holder holderIn, String message) {
/* 11 */     super(message);
/* 12 */     this.holder = holderIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\StitcherException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */