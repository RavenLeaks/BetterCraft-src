/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ public class ModelBed
/*    */   extends ModelBase {
/*    */   public ModelRenderer field_193772_a;
/*    */   public ModelRenderer field_193773_b;
/*  7 */   public ModelRenderer[] field_193774_c = new ModelRenderer[4];
/*    */ 
/*    */   
/*    */   public ModelBed() {
/* 11 */     this.textureWidth = 64;
/* 12 */     this.textureHeight = 64;
/* 13 */     this.field_193772_a = new ModelRenderer(this, 0, 0);
/* 14 */     this.field_193772_a.addBox(0.0F, 0.0F, 0.0F, 16, 16, 6, 0.0F);
/* 15 */     this.field_193773_b = new ModelRenderer(this, 0, 22);
/* 16 */     this.field_193773_b.addBox(0.0F, 0.0F, 0.0F, 16, 16, 6, 0.0F);
/* 17 */     this.field_193774_c[0] = new ModelRenderer(this, 50, 0);
/* 18 */     this.field_193774_c[1] = new ModelRenderer(this, 50, 6);
/* 19 */     this.field_193774_c[2] = new ModelRenderer(this, 50, 12);
/* 20 */     this.field_193774_c[3] = new ModelRenderer(this, 50, 18);
/* 21 */     this.field_193774_c[0].addBox(0.0F, 6.0F, -16.0F, 3, 3, 3);
/* 22 */     this.field_193774_c[1].addBox(0.0F, 6.0F, 0.0F, 3, 3, 3);
/* 23 */     this.field_193774_c[2].addBox(-16.0F, 6.0F, -16.0F, 3, 3, 3);
/* 24 */     this.field_193774_c[3].addBox(-16.0F, 6.0F, 0.0F, 3, 3, 3);
/* 25 */     (this.field_193774_c[0]).rotateAngleX = 1.5707964F;
/* 26 */     (this.field_193774_c[1]).rotateAngleX = 1.5707964F;
/* 27 */     (this.field_193774_c[2]).rotateAngleX = 1.5707964F;
/* 28 */     (this.field_193774_c[3]).rotateAngleX = 1.5707964F;
/* 29 */     (this.field_193774_c[0]).rotateAngleZ = 0.0F;
/* 30 */     (this.field_193774_c[1]).rotateAngleZ = 1.5707964F;
/* 31 */     (this.field_193774_c[2]).rotateAngleZ = 4.712389F;
/* 32 */     (this.field_193774_c[3]).rotateAngleZ = 3.1415927F;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_193770_a() {
/* 37 */     return 51;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193771_b() {
/* 42 */     this.field_193772_a.render(0.0625F);
/* 43 */     this.field_193773_b.render(0.0625F);
/* 44 */     this.field_193774_c[0].render(0.0625F);
/* 45 */     this.field_193774_c[1].render(0.0625F);
/* 46 */     this.field_193774_c[2].render(0.0625F);
/* 47 */     this.field_193774_c[3].render(0.0625F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_193769_a(boolean p_193769_1_) {
/* 52 */     this.field_193772_a.showModel = p_193769_1_;
/* 53 */     this.field_193773_b.showModel = !p_193769_1_;
/* 54 */     (this.field_193774_c[0]).showModel = !p_193769_1_;
/* 55 */     (this.field_193774_c[1]).showModel = p_193769_1_;
/* 56 */     (this.field_193774_c[2]).showModel = !p_193769_1_;
/* 57 */     (this.field_193774_c[3]).showModel = p_193769_1_;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */