/*     */ package net.minecraft.client.tutorial;
/*     */ 
/*     */ import net.minecraft.client.gui.toasts.IToast;
/*     */ import net.minecraft.client.gui.toasts.TutorialToast;
/*     */ import net.minecraft.util.MouseHelper;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.GameType;
/*     */ 
/*     */ public class MovementStep implements ITutorialStep {
/*  13 */   private static final ITextComponent field_193254_a = (ITextComponent)new TextComponentTranslation("tutorial.move.title", new Object[] { Tutorial.func_193291_a("forward"), Tutorial.func_193291_a("left"), Tutorial.func_193291_a("back"), Tutorial.func_193291_a("right") });
/*  14 */   private static final ITextComponent field_193255_b = (ITextComponent)new TextComponentTranslation("tutorial.move.description", new Object[] { Tutorial.func_193291_a("jump") });
/*  15 */   private static final ITextComponent field_193256_c = (ITextComponent)new TextComponentTranslation("tutorial.look.title", new Object[0]);
/*  16 */   private static final ITextComponent field_193257_d = (ITextComponent)new TextComponentTranslation("tutorial.look.description", new Object[0]);
/*     */   private final Tutorial field_193258_e;
/*     */   private TutorialToast field_193259_f;
/*     */   private TutorialToast field_193260_g;
/*     */   private int field_193261_h;
/*     */   private int field_193262_i;
/*     */   private int field_193263_j;
/*     */   private boolean field_193264_k;
/*     */   private boolean field_193265_l;
/*  25 */   private int field_193266_m = -1;
/*  26 */   private int field_193267_n = -1;
/*     */ 
/*     */   
/*     */   public MovementStep(Tutorial p_i47581_1_) {
/*  30 */     this.field_193258_e = p_i47581_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193245_a() {
/*  35 */     this.field_193261_h++;
/*     */     
/*  37 */     if (this.field_193264_k) {
/*     */       
/*  39 */       this.field_193262_i++;
/*  40 */       this.field_193264_k = false;
/*     */     } 
/*     */     
/*  43 */     if (this.field_193265_l) {
/*     */       
/*  45 */       this.field_193263_j++;
/*  46 */       this.field_193265_l = false;
/*     */     } 
/*     */     
/*  49 */     if (this.field_193266_m == -1 && this.field_193262_i > 40) {
/*     */       
/*  51 */       if (this.field_193259_f != null) {
/*     */         
/*  53 */         this.field_193259_f.func_193670_a();
/*  54 */         this.field_193259_f = null;
/*     */       } 
/*     */       
/*  57 */       this.field_193266_m = this.field_193261_h;
/*     */     } 
/*     */     
/*  60 */     if (this.field_193267_n == -1 && this.field_193263_j > 40) {
/*     */       
/*  62 */       if (this.field_193260_g != null) {
/*     */         
/*  64 */         this.field_193260_g.func_193670_a();
/*  65 */         this.field_193260_g = null;
/*     */       } 
/*     */       
/*  68 */       this.field_193267_n = this.field_193261_h;
/*     */     } 
/*     */     
/*  71 */     if (this.field_193266_m != -1 && this.field_193267_n != -1)
/*     */     {
/*  73 */       if (this.field_193258_e.func_194072_f() == GameType.SURVIVAL) {
/*     */         
/*  75 */         this.field_193258_e.func_193292_a(TutorialSteps.FIND_TREE);
/*     */       }
/*     */       else {
/*     */         
/*  79 */         this.field_193258_e.func_193292_a(TutorialSteps.NONE);
/*     */       } 
/*     */     }
/*     */     
/*  83 */     if (this.field_193259_f != null)
/*     */     {
/*  85 */       this.field_193259_f.func_193669_a(this.field_193262_i / 40.0F);
/*     */     }
/*     */     
/*  88 */     if (this.field_193260_g != null)
/*     */     {
/*  90 */       this.field_193260_g.func_193669_a(this.field_193263_j / 40.0F);
/*     */     }
/*     */     
/*  93 */     if (this.field_193261_h >= 100)
/*     */     {
/*  95 */       if (this.field_193266_m == -1 && this.field_193259_f == null) {
/*     */         
/*  97 */         this.field_193259_f = new TutorialToast(TutorialToast.Icons.MOVEMENT_KEYS, field_193254_a, field_193255_b, true);
/*  98 */         this.field_193258_e.func_193295_e().func_193033_an().func_192988_a((IToast)this.field_193259_f);
/*     */       }
/* 100 */       else if (this.field_193266_m != -1 && this.field_193261_h - this.field_193266_m >= 20 && this.field_193267_n == -1 && this.field_193260_g == null) {
/*     */         
/* 102 */         this.field_193260_g = new TutorialToast(TutorialToast.Icons.MOUSE, field_193256_c, field_193257_d, true);
/* 103 */         this.field_193258_e.func_193295_e().func_193033_an().func_192988_a((IToast)this.field_193260_g);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193248_b() {
/* 110 */     if (this.field_193259_f != null) {
/*     */       
/* 112 */       this.field_193259_f.func_193670_a();
/* 113 */       this.field_193259_f = null;
/*     */     } 
/*     */     
/* 116 */     if (this.field_193260_g != null) {
/*     */       
/* 118 */       this.field_193260_g.func_193670_a();
/* 119 */       this.field_193260_g = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193247_a(MovementInput p_193247_1_) {
/* 125 */     if (p_193247_1_.forwardKeyDown || p_193247_1_.backKeyDown || p_193247_1_.leftKeyDown || p_193247_1_.rightKeyDown || p_193247_1_.jump)
/*     */     {
/* 127 */       this.field_193264_k = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193249_a(MouseHelper p_193249_1_) {
/* 133 */     if (MathHelper.abs(p_193249_1_.deltaX) > 0.01D || MathHelper.abs(p_193249_1_.deltaY) > 0.01D)
/*     */     {
/* 135 */       this.field_193265_l = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\tutorial\MovementStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */