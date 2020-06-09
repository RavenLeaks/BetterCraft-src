/*     */ package net.minecraft.client.tutorial;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MouseHelper;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentKeybind;
/*     */ import net.minecraft.world.GameType;
/*     */ 
/*     */ 
/*     */ public class Tutorial
/*     */ {
/*     */   private final Minecraft field_193304_a;
/*     */   @Nullable
/*     */   private ITutorialStep field_193305_b;
/*     */   
/*     */   public Tutorial(Minecraft p_i47578_1_) {
/*  24 */     this.field_193304_a = p_i47578_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193293_a(MovementInput p_193293_1_) {
/*  29 */     if (this.field_193305_b != null)
/*     */     {
/*  31 */       this.field_193305_b.func_193247_a(p_193293_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193299_a(MouseHelper p_193299_1_) {
/*  37 */     if (this.field_193305_b != null)
/*     */     {
/*  39 */       this.field_193305_b.func_193249_a(p_193299_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193297_a(@Nullable WorldClient p_193297_1_, @Nullable RayTraceResult p_193297_2_) {
/*  45 */     if (this.field_193305_b != null && p_193297_2_ != null && p_193297_1_ != null)
/*     */     {
/*  47 */       this.field_193305_b.func_193246_a(p_193297_1_, p_193297_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193294_a(WorldClient p_193294_1_, BlockPos p_193294_2_, IBlockState p_193294_3_, float p_193294_4_) {
/*  53 */     if (this.field_193305_b != null)
/*     */     {
/*  55 */       this.field_193305_b.func_193250_a(p_193294_1_, p_193294_2_, p_193294_3_, p_193294_4_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193296_a() {
/*  61 */     if (this.field_193305_b != null)
/*     */     {
/*  63 */       this.field_193305_b.func_193251_c();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193301_a(ItemStack p_193301_1_) {
/*  69 */     if (this.field_193305_b != null)
/*     */     {
/*  71 */       this.field_193305_b.func_193252_a(p_193301_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193300_b() {
/*  77 */     if (this.field_193305_b != null) {
/*     */       
/*  79 */       this.field_193305_b.func_193248_b();
/*  80 */       this.field_193305_b = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193302_c() {
/*  86 */     if (this.field_193305_b != null)
/*     */     {
/*  88 */       func_193300_b();
/*     */     }
/*     */     
/*  91 */     this.field_193305_b = this.field_193304_a.gameSettings.field_193631_S.func_193309_a(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193303_d() {
/*  96 */     if (this.field_193305_b != null) {
/*     */       
/*  98 */       if (this.field_193304_a.world != null)
/*     */       {
/* 100 */         this.field_193305_b.func_193245_a();
/*     */       }
/*     */       else
/*     */       {
/* 104 */         func_193300_b();
/*     */       }
/*     */     
/* 107 */     } else if (this.field_193304_a.world != null) {
/*     */       
/* 109 */       func_193302_c();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193292_a(TutorialSteps p_193292_1_) {
/* 115 */     this.field_193304_a.gameSettings.field_193631_S = p_193292_1_;
/* 116 */     this.field_193304_a.gameSettings.saveOptions();
/*     */     
/* 118 */     if (this.field_193305_b != null) {
/*     */       
/* 120 */       this.field_193305_b.func_193248_b();
/* 121 */       this.field_193305_b = p_193292_1_.func_193309_a(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Minecraft func_193295_e() {
/* 127 */     return this.field_193304_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameType func_194072_f() {
/* 132 */     return (this.field_193304_a.playerController == null) ? GameType.NOT_SET : this.field_193304_a.playerController.getCurrentGameType();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ITextComponent func_193291_a(String p_193291_0_) {
/* 137 */     TextComponentKeybind textcomponentkeybind = new TextComponentKeybind("key." + p_193291_0_);
/* 138 */     textcomponentkeybind.getStyle().setBold(Boolean.valueOf(true));
/* 139 */     return (ITextComponent)textcomponentkeybind;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\tutorial\Tutorial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */