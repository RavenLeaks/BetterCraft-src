/*     */ package net.minecraft.client.gui.toasts;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import java.util.Arrays;
/*     */ import java.util.Deque;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class GuiToast
/*     */   extends Gui {
/*     */   private final Minecraft field_191790_f;
/*  17 */   private final ToastInstance<?>[] field_191791_g = (ToastInstance<?>[])new ToastInstance[5];
/*  18 */   private final Deque<IToast> field_191792_h = Queues.newArrayDeque();
/*     */ 
/*     */   
/*     */   public GuiToast(Minecraft p_i47388_1_) {
/*  22 */     this.field_191790_f = p_i47388_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191783_a(ScaledResolution p_191783_1_) {
/*  27 */     if (!this.field_191790_f.gameSettings.hideGUI) {
/*     */       
/*  29 */       RenderHelper.disableStandardItemLighting();
/*     */       
/*  31 */       for (int i = 0; i < this.field_191791_g.length; i++) {
/*     */         
/*  33 */         ToastInstance<?> toastinstance = this.field_191791_g[i];
/*     */         
/*  35 */         if (toastinstance != null && toastinstance.func_193684_a(ScaledResolution.getScaledWidth(), i))
/*     */         {
/*  37 */           this.field_191791_g[i] = null;
/*     */         }
/*     */         
/*  40 */         if (this.field_191791_g[i] == null && !this.field_191792_h.isEmpty())
/*     */         {
/*  42 */           this.field_191791_g[i] = new ToastInstance(this.field_191792_h.removeFirst(), null); } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   @Nullable
/*     */   public <T extends IToast> T func_192990_a(Class<? extends T> p_192990_1_, Object p_192990_2_) {
/*     */     byte b;
/*     */     int i;
/*     */     ToastInstance<?>[] arrayOfToastInstance;
/*  51 */     for (i = (arrayOfToastInstance = this.field_191791_g).length, b = 0; b < i; ) { ToastInstance<?> toastinstance = arrayOfToastInstance[b];
/*     */       
/*  53 */       if (toastinstance != null && p_192990_1_.isAssignableFrom(toastinstance.func_193685_a().getClass()) && ((IToast)toastinstance.func_193685_a()).func_193652_b().equals(p_192990_2_))
/*     */       {
/*  55 */         return (T)toastinstance.func_193685_a();
/*     */       }
/*     */       b++; }
/*     */     
/*  59 */     for (IToast itoast : this.field_191792_h) {
/*     */       
/*  61 */       if (p_192990_1_.isAssignableFrom(itoast.getClass()) && itoast.func_193652_b().equals(p_192990_2_))
/*     */       {
/*  63 */         return (T)itoast;
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191788_b() {
/*  72 */     Arrays.fill((Object[])this.field_191791_g, (Object)null);
/*  73 */     this.field_191792_h.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192988_a(IToast p_192988_1_) {
/*  78 */     this.field_191792_h.add(p_192988_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Minecraft func_192989_b() {
/*  83 */     return this.field_191790_f;
/*     */   }
/*     */ 
/*     */   
/*     */   class ToastInstance<T extends IToast>
/*     */   {
/*     */     private final T field_193688_b;
/*     */     private long field_193689_c;
/*     */     private long field_193690_d;
/*     */     private IToast.Visibility field_193691_e;
/*     */     
/*     */     private ToastInstance(T p_i47483_2_) {
/*  95 */       this.field_193689_c = -1L;
/*  96 */       this.field_193690_d = -1L;
/*  97 */       this.field_193691_e = IToast.Visibility.SHOW;
/*  98 */       this.field_193688_b = p_i47483_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public T func_193685_a() {
/* 103 */       return this.field_193688_b;
/*     */     }
/*     */ 
/*     */     
/*     */     private float func_193686_a(long p_193686_1_) {
/* 108 */       float f = MathHelper.clamp((float)(p_193686_1_ - this.field_193689_c) / 600.0F, 0.0F, 1.0F);
/* 109 */       f *= f;
/* 110 */       return (this.field_193691_e == IToast.Visibility.HIDE) ? (1.0F - f) : f;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193684_a(int p_193684_1_, int p_193684_2_) {
/* 115 */       long i = Minecraft.getSystemTime();
/*     */       
/* 117 */       if (this.field_193689_c == -1L) {
/*     */         
/* 119 */         this.field_193689_c = i;
/* 120 */         this.field_193691_e.func_194169_a(GuiToast.this.field_191790_f.getSoundHandler());
/*     */       } 
/*     */       
/* 123 */       if (this.field_193691_e == IToast.Visibility.SHOW && i - this.field_193689_c <= 600L)
/*     */       {
/* 125 */         this.field_193690_d = i;
/*     */       }
/*     */       
/* 128 */       GlStateManager.pushMatrix();
/* 129 */       GlStateManager.translate(p_193684_1_ - 160.0F * func_193686_a(i), (p_193684_2_ * 32), (500 + p_193684_2_));
/* 130 */       IToast.Visibility itoast$visibility = this.field_193688_b.func_193653_a(GuiToast.this, i - this.field_193690_d);
/* 131 */       GlStateManager.popMatrix();
/*     */       
/* 133 */       if (itoast$visibility != this.field_193691_e) {
/*     */         
/* 135 */         this.field_193689_c = i - (int)((1.0F - func_193686_a(i)) * 600.0F);
/* 136 */         this.field_193691_e = itoast$visibility;
/* 137 */         this.field_193691_e.func_194169_a(GuiToast.this.field_191790_f.getSoundHandler());
/*     */       } 
/*     */       
/* 140 */       return (this.field_193691_e == IToast.Visibility.HIDE && i - this.field_193689_c > 600L);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\toasts\GuiToast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */