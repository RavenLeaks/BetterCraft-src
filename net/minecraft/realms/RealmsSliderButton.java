/*     */ package net.minecraft.realms;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class RealmsSliderButton
/*     */   extends RealmsButton
/*     */ {
/*     */   public float value;
/*     */   public boolean sliding;
/*     */   private final float minValue;
/*     */   private final float maxValue;
/*     */   private int steps;
/*     */   
/*     */   public RealmsSliderButton(int buttonId, int x, int y, int width, int maxValueIn, int p_i1056_6_) {
/*  17 */     this(buttonId, x, y, width, p_i1056_6_, 0, 1.0F, maxValueIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public RealmsSliderButton(int buttonId, int x, int y, int width, int p_i1057_5_, int valueIn, float minValueIn, float maxValueIn) {
/*  22 */     super(buttonId, x, y, width, 20, "");
/*  23 */     this.value = 1.0F;
/*  24 */     this.minValue = minValueIn;
/*  25 */     this.maxValue = maxValueIn;
/*  26 */     this.value = toPct(valueIn);
/*  27 */     (getProxy()).displayString = getMessage();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  32 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public float toPct(float p_toPct_1_) {
/*  37 */     return MathHelper.clamp((clamp(p_toPct_1_) - this.minValue) / (this.maxValue - this.minValue), 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float toValue(float p_toValue_1_) {
/*  42 */     return clamp(this.minValue + (this.maxValue - this.minValue) * MathHelper.clamp(p_toValue_1_, 0.0F, 1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public float clamp(float p_clamp_1_) {
/*  47 */     p_clamp_1_ = clampSteps(p_clamp_1_);
/*  48 */     return MathHelper.clamp(p_clamp_1_, this.minValue, this.maxValue);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float clampSteps(float p_clampSteps_1_) {
/*  53 */     if (this.steps > 0)
/*     */     {
/*  55 */       p_clampSteps_1_ = (this.steps * Math.round(p_clampSteps_1_ / this.steps));
/*     */     }
/*     */     
/*  58 */     return p_clampSteps_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getYImage(boolean p_getYImage_1_) {
/*  63 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBg(int p_renderBg_1_, int p_renderBg_2_) {
/*  68 */     if ((getProxy()).visible) {
/*     */ 
/*     */ 
/*     */       
/*  72 */       if (this.sliding) {
/*     */         
/*  74 */         this.value = (p_renderBg_1_ - (getProxy()).xPosition + 4) / (getProxy().getButtonWidth() - 8);
/*  75 */         this.value = MathHelper.clamp(this.value, 0.0F, 1.0F);
/*  76 */         float f = toValue(this.value);
/*  77 */         clicked(f);
/*  78 */         this.value = toPct(f);
/*  79 */         (getProxy()).displayString = getMessage();
/*     */       } 
/*     */       
/*  82 */       Minecraft.getMinecraft().getTextureManager().bindTexture(WIDGETS_LOCATION);
/*  83 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  84 */       blit((getProxy()).xPosition + (int)(this.value * (getProxy().getButtonWidth() - 8)), (getProxy()).yPosition, 0, 66, 4, 20);
/*  85 */       blit((getProxy()).xPosition + (int)(this.value * (getProxy().getButtonWidth() - 8)) + 4, (getProxy()).yPosition, 196, 66, 4, 20);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clicked(int p_clicked_1_, int p_clicked_2_) {
/*  91 */     this.value = (p_clicked_1_ - (getProxy()).xPosition + 4) / (getProxy().getButtonWidth() - 8);
/*  92 */     this.value = MathHelper.clamp(this.value, 0.0F, 1.0F);
/*  93 */     clicked(toValue(this.value));
/*  94 */     (getProxy()).displayString = getMessage();
/*  95 */     this.sliding = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clicked(float p_clicked_1_) {}
/*     */ 
/*     */   
/*     */   public void released(int p_released_1_, int p_released_2_) {
/* 104 */     this.sliding = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\realms\RealmsSliderButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */