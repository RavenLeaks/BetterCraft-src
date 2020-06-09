/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBed;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBed;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class TileEntityBedRenderer extends TileEntitySpecialRenderer<TileEntityBed> {
/*     */   private static final ResourceLocation[] field_193848_a;
/*  13 */   private ModelBed field_193849_d = new ModelBed();
/*     */   
/*     */   private int field_193850_e;
/*     */   
/*     */   public TileEntityBedRenderer() {
/*  18 */     this.field_193850_e = this.field_193849_d.func_193770_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192841_a(TileEntityBed p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/*  23 */     if (this.field_193850_e != this.field_193849_d.func_193770_a()) {
/*     */       
/*  25 */       this.field_193849_d = new ModelBed();
/*  26 */       this.field_193850_e = this.field_193849_d.func_193770_a();
/*     */     } 
/*     */     
/*  29 */     boolean flag = (p_192841_1_.getWorld() != null);
/*  30 */     boolean flag1 = flag ? p_192841_1_.func_193050_e() : true;
/*  31 */     EnumDyeColor enumdyecolor = (p_192841_1_ != null) ? p_192841_1_.func_193048_a() : EnumDyeColor.RED;
/*  32 */     int i = flag ? (p_192841_1_.getBlockMetadata() & 0x3) : 0;
/*     */     
/*  34 */     if (p_192841_9_ >= 0) {
/*     */       
/*  36 */       bindTexture(DESTROY_STAGES[p_192841_9_]);
/*  37 */       GlStateManager.matrixMode(5890);
/*  38 */       GlStateManager.pushMatrix();
/*  39 */       GlStateManager.scale(4.0F, 4.0F, 1.0F);
/*  40 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  41 */       GlStateManager.matrixMode(5888);
/*     */     }
/*     */     else {
/*     */       
/*  45 */       ResourceLocation resourcelocation = field_193848_a[enumdyecolor.getMetadata()];
/*     */       
/*  47 */       if (resourcelocation != null)
/*     */       {
/*  49 */         bindTexture(resourcelocation);
/*     */       }
/*     */     } 
/*     */     
/*  53 */     if (flag) {
/*     */       
/*  55 */       func_193847_a(flag1, p_192841_2_, p_192841_4_, p_192841_6_, i, p_192841_10_);
/*     */     }
/*     */     else {
/*     */       
/*  59 */       GlStateManager.pushMatrix();
/*  60 */       func_193847_a(true, p_192841_2_, p_192841_4_, p_192841_6_, i, p_192841_10_);
/*  61 */       func_193847_a(false, p_192841_2_, p_192841_4_, p_192841_6_ - 1.0D, i, p_192841_10_);
/*  62 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/*  65 */     if (p_192841_9_ >= 0) {
/*     */       
/*  67 */       GlStateManager.matrixMode(5890);
/*  68 */       GlStateManager.popMatrix();
/*  69 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_193847_a(boolean p_193847_1_, double p_193847_2_, double p_193847_4_, double p_193847_6_, int p_193847_8_, float p_193847_9_) {
/*  75 */     this.field_193849_d.func_193769_a(p_193847_1_);
/*  76 */     GlStateManager.pushMatrix();
/*  77 */     float f = 0.0F;
/*  78 */     float f1 = 0.0F;
/*  79 */     float f2 = 0.0F;
/*     */     
/*  81 */     if (p_193847_8_ == EnumFacing.NORTH.getHorizontalIndex()) {
/*     */       
/*  83 */       f = 0.0F;
/*     */     }
/*  85 */     else if (p_193847_8_ == EnumFacing.SOUTH.getHorizontalIndex()) {
/*     */       
/*  87 */       f = 180.0F;
/*  88 */       f1 = 1.0F;
/*  89 */       f2 = 1.0F;
/*     */     }
/*  91 */     else if (p_193847_8_ == EnumFacing.WEST.getHorizontalIndex()) {
/*     */       
/*  93 */       f = -90.0F;
/*  94 */       f2 = 1.0F;
/*     */     }
/*  96 */     else if (p_193847_8_ == EnumFacing.EAST.getHorizontalIndex()) {
/*     */       
/*  98 */       f = 90.0F;
/*  99 */       f1 = 1.0F;
/*     */     } 
/*     */     
/* 102 */     GlStateManager.translate((float)p_193847_2_ + f1, (float)p_193847_4_ + 0.5625F, (float)p_193847_6_ + f2);
/* 103 */     GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 104 */     GlStateManager.rotate(f, 0.0F, 0.0F, 1.0F);
/* 105 */     GlStateManager.enableRescaleNormal();
/* 106 */     GlStateManager.pushMatrix();
/* 107 */     this.field_193849_d.func_193771_b();
/* 108 */     GlStateManager.popMatrix();
/* 109 */     GlStateManager.color(1.0F, 1.0F, 1.0F, p_193847_9_);
/* 110 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 115 */     EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
/* 116 */     field_193848_a = new ResourceLocation[aenumdyecolor.length]; byte b; int i;
/*     */     EnumDyeColor[] arrayOfEnumDyeColor1;
/* 118 */     for (i = (arrayOfEnumDyeColor1 = aenumdyecolor).length, b = 0; b < i; ) { EnumDyeColor enumdyecolor = arrayOfEnumDyeColor1[b];
/*     */       
/* 120 */       field_193848_a[enumdyecolor.getMetadata()] = new ResourceLocation("textures/entity/bed/" + enumdyecolor.func_192396_c() + ".png");
/*     */       b++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityBedRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */