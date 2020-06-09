/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ public class ModelShield
/*    */   extends ModelBase
/*    */ {
/*    */   public ModelRenderer plate;
/*    */   public ModelRenderer handle;
/*    */   
/*    */   public ModelShield() {
/* 10 */     this.textureWidth = 64;
/* 11 */     this.textureHeight = 64;
/* 12 */     this.plate = new ModelRenderer(this, 0, 0);
/* 13 */     this.plate.addBox(-6.0F, -11.0F, -2.0F, 12, 22, 1, 0.0F);
/* 14 */     this.handle = new ModelRenderer(this, 26, 0);
/* 15 */     this.handle.addBox(-1.0F, -3.0F, -1.0F, 2, 6, 6, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render() {
/* 20 */     this.plate.render(0.0625F);
/* 21 */     this.handle.render(0.0625F);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelShield.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */