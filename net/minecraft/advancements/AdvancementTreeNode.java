/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AdvancementTreeNode
/*     */ {
/*     */   private final Advancement field_192328_a;
/*     */   private final AdvancementTreeNode field_192329_b;
/*     */   private final AdvancementTreeNode field_192330_c;
/*     */   private final int field_192331_d;
/*  13 */   private final List<AdvancementTreeNode> field_192332_e = Lists.newArrayList();
/*     */   
/*     */   private AdvancementTreeNode field_192333_f;
/*     */   private AdvancementTreeNode field_192334_g;
/*     */   private int field_192335_h;
/*     */   private float field_192336_i;
/*     */   private float field_192337_j;
/*     */   private float field_192338_k;
/*     */   private float field_192339_l;
/*     */   
/*     */   public AdvancementTreeNode(Advancement p_i47466_1_, @Nullable AdvancementTreeNode p_i47466_2_, @Nullable AdvancementTreeNode p_i47466_3_, int p_i47466_4_, int p_i47466_5_) {
/*  24 */     if (p_i47466_1_.func_192068_c() == null)
/*     */     {
/*  26 */       throw new IllegalArgumentException("Can't position an invisible advancement!");
/*     */     }
/*     */ 
/*     */     
/*  30 */     this.field_192328_a = p_i47466_1_;
/*  31 */     this.field_192329_b = p_i47466_2_;
/*  32 */     this.field_192330_c = p_i47466_3_;
/*  33 */     this.field_192331_d = p_i47466_4_;
/*  34 */     this.field_192333_f = this;
/*  35 */     this.field_192335_h = p_i47466_5_;
/*  36 */     this.field_192336_i = -1.0F;
/*  37 */     AdvancementTreeNode advancementtreenode = null;
/*     */     
/*  39 */     for (Advancement advancement : p_i47466_1_.func_192069_e())
/*     */     {
/*  41 */       advancementtreenode = func_192322_a(advancement, advancementtreenode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private AdvancementTreeNode func_192322_a(Advancement p_192322_1_, @Nullable AdvancementTreeNode p_192322_2_) {
/*  49 */     if (p_192322_1_.func_192068_c() != null) {
/*     */       
/*  51 */       p_192322_2_ = new AdvancementTreeNode(p_192322_1_, this, p_192322_2_, this.field_192332_e.size() + 1, this.field_192335_h + 1);
/*  52 */       this.field_192332_e.add(p_192322_2_);
/*     */     }
/*     */     else {
/*     */       
/*  56 */       for (Advancement advancement : p_192322_1_.func_192069_e())
/*     */       {
/*  58 */         p_192322_2_ = func_192322_a(advancement, p_192322_2_);
/*     */       }
/*     */     } 
/*     */     
/*  62 */     return p_192322_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192320_a() {
/*  67 */     if (this.field_192332_e.isEmpty()) {
/*     */       
/*  69 */       if (this.field_192330_c != null)
/*     */       {
/*  71 */         this.field_192330_c.field_192336_i++;
/*     */       }
/*     */       else
/*     */       {
/*  75 */         this.field_192336_i = 0.0F;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  80 */       AdvancementTreeNode advancementtreenode = null;
/*     */       
/*  82 */       for (AdvancementTreeNode advancementtreenode1 : this.field_192332_e) {
/*     */         
/*  84 */         advancementtreenode1.func_192320_a();
/*  85 */         advancementtreenode = advancementtreenode1.func_192324_a((advancementtreenode == null) ? advancementtreenode1 : advancementtreenode);
/*     */       } 
/*     */       
/*  88 */       func_192325_b();
/*  89 */       float f = (((AdvancementTreeNode)this.field_192332_e.get(0)).field_192336_i + ((AdvancementTreeNode)this.field_192332_e.get(this.field_192332_e.size() - 1)).field_192336_i) / 2.0F;
/*     */       
/*  91 */       if (this.field_192330_c != null) {
/*     */         
/*  93 */         this.field_192330_c.field_192336_i++;
/*  94 */         this.field_192337_j = this.field_192336_i - f;
/*     */       }
/*     */       else {
/*     */         
/*  98 */         this.field_192336_i = f;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float func_192319_a(float p_192319_1_, int p_192319_2_, float p_192319_3_) {
/* 105 */     this.field_192336_i += p_192319_1_;
/* 106 */     this.field_192335_h = p_192319_2_;
/*     */     
/* 108 */     if (this.field_192336_i < p_192319_3_)
/*     */     {
/* 110 */       p_192319_3_ = this.field_192336_i;
/*     */     }
/*     */     
/* 113 */     for (AdvancementTreeNode advancementtreenode : this.field_192332_e)
/*     */     {
/* 115 */       p_192319_3_ = advancementtreenode.func_192319_a(p_192319_1_ + this.field_192337_j, p_192319_2_ + 1, p_192319_3_);
/*     */     }
/*     */     
/* 118 */     return p_192319_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192318_a(float p_192318_1_) {
/* 123 */     this.field_192336_i += p_192318_1_;
/*     */     
/* 125 */     for (AdvancementTreeNode advancementtreenode : this.field_192332_e)
/*     */     {
/* 127 */       advancementtreenode.func_192318_a(p_192318_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192325_b() {
/* 133 */     float f = 0.0F;
/* 134 */     float f1 = 0.0F;
/*     */     
/* 136 */     for (int i = this.field_192332_e.size() - 1; i >= 0; i--) {
/*     */       
/* 138 */       AdvancementTreeNode advancementtreenode = this.field_192332_e.get(i);
/* 139 */       advancementtreenode.field_192336_i += f;
/* 140 */       advancementtreenode.field_192337_j += f;
/* 141 */       f1 += advancementtreenode.field_192338_k;
/* 142 */       f += advancementtreenode.field_192339_l + f1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private AdvancementTreeNode func_192321_c() {
/* 149 */     if (this.field_192334_g != null)
/*     */     {
/* 151 */       return this.field_192334_g;
/*     */     }
/*     */ 
/*     */     
/* 155 */     return !this.field_192332_e.isEmpty() ? this.field_192332_e.get(0) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private AdvancementTreeNode func_192317_d() {
/* 162 */     if (this.field_192334_g != null)
/*     */     {
/* 164 */       return this.field_192334_g;
/*     */     }
/*     */ 
/*     */     
/* 168 */     return !this.field_192332_e.isEmpty() ? this.field_192332_e.get(this.field_192332_e.size() - 1) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AdvancementTreeNode func_192324_a(AdvancementTreeNode p_192324_1_) {
/* 174 */     if (this.field_192330_c == null)
/*     */     {
/* 176 */       return p_192324_1_;
/*     */     }
/*     */ 
/*     */     
/* 180 */     AdvancementTreeNode advancementtreenode = this;
/* 181 */     AdvancementTreeNode advancementtreenode1 = this;
/* 182 */     AdvancementTreeNode advancementtreenode2 = this.field_192330_c;
/* 183 */     AdvancementTreeNode advancementtreenode3 = this.field_192329_b.field_192332_e.get(0);
/* 184 */     float f = this.field_192337_j;
/* 185 */     float f1 = this.field_192337_j;
/* 186 */     float f2 = advancementtreenode2.field_192337_j;
/*     */     
/*     */     float f3;
/* 189 */     for (f3 = advancementtreenode3.field_192337_j; advancementtreenode2.func_192317_d() != null && advancementtreenode.func_192321_c() != null; f1 += advancementtreenode1.field_192337_j) {
/*     */       
/* 191 */       advancementtreenode2 = advancementtreenode2.func_192317_d();
/* 192 */       advancementtreenode = advancementtreenode.func_192321_c();
/* 193 */       advancementtreenode3 = advancementtreenode3.func_192321_c();
/* 194 */       advancementtreenode1 = advancementtreenode1.func_192317_d();
/* 195 */       advancementtreenode1.field_192333_f = this;
/* 196 */       float f4 = advancementtreenode2.field_192336_i + f2 - advancementtreenode.field_192336_i + f + 1.0F;
/*     */       
/* 198 */       if (f4 > 0.0F) {
/*     */         
/* 200 */         advancementtreenode2.func_192326_a(this, p_192324_1_).func_192316_a(this, f4);
/* 201 */         f += f4;
/* 202 */         f1 += f4;
/*     */       } 
/*     */       
/* 205 */       f2 += advancementtreenode2.field_192337_j;
/* 206 */       f += advancementtreenode.field_192337_j;
/* 207 */       f3 += advancementtreenode3.field_192337_j;
/*     */     } 
/*     */     
/* 210 */     if (advancementtreenode2.func_192317_d() != null && advancementtreenode1.func_192317_d() == null) {
/*     */       
/* 212 */       advancementtreenode1.field_192334_g = advancementtreenode2.func_192317_d();
/* 213 */       advancementtreenode1.field_192337_j += f2 - f1;
/*     */     }
/*     */     else {
/*     */       
/* 217 */       if (advancementtreenode.func_192321_c() != null && advancementtreenode3.func_192321_c() == null) {
/*     */         
/* 219 */         advancementtreenode3.field_192334_g = advancementtreenode.func_192321_c();
/* 220 */         advancementtreenode3.field_192337_j += f - f3;
/*     */       } 
/*     */       
/* 223 */       p_192324_1_ = this;
/*     */     } 
/*     */     
/* 226 */     return p_192324_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_192316_a(AdvancementTreeNode p_192316_1_, float p_192316_2_) {
/* 232 */     float f = (p_192316_1_.field_192331_d - this.field_192331_d);
/*     */     
/* 234 */     if (f != 0.0F) {
/*     */       
/* 236 */       p_192316_1_.field_192338_k -= p_192316_2_ / f;
/* 237 */       this.field_192338_k += p_192316_2_ / f;
/*     */     } 
/*     */     
/* 240 */     p_192316_1_.field_192339_l += p_192316_2_;
/* 241 */     p_192316_1_.field_192336_i += p_192316_2_;
/* 242 */     p_192316_1_.field_192337_j += p_192316_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   private AdvancementTreeNode func_192326_a(AdvancementTreeNode p_192326_1_, AdvancementTreeNode p_192326_2_) {
/* 247 */     return (this.field_192333_f != null && p_192326_1_.field_192329_b.field_192332_e.contains(this.field_192333_f)) ? this.field_192333_f : p_192326_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192327_e() {
/* 252 */     if (this.field_192328_a.func_192068_c() != null)
/*     */     {
/* 254 */       this.field_192328_a.func_192068_c().func_192292_a(this.field_192335_h, this.field_192336_i);
/*     */     }
/*     */     
/* 257 */     if (!this.field_192332_e.isEmpty())
/*     */     {
/* 259 */       for (AdvancementTreeNode advancementtreenode : this.field_192332_e)
/*     */       {
/* 261 */         advancementtreenode.func_192327_e();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_192323_a(Advancement p_192323_0_) {
/* 268 */     if (p_192323_0_.func_192068_c() == null)
/*     */     {
/* 270 */       throw new IllegalArgumentException("Can't position children of an invisible root!");
/*     */     }
/*     */ 
/*     */     
/* 274 */     AdvancementTreeNode advancementtreenode = new AdvancementTreeNode(p_192323_0_, null, null, 1, 0);
/* 275 */     advancementtreenode.func_192320_a();
/* 276 */     float f = advancementtreenode.func_192319_a(0.0F, 0, advancementtreenode.field_192336_i);
/*     */     
/* 278 */     if (f < 0.0F)
/*     */     {
/* 280 */       advancementtreenode.func_192318_a(-f);
/*     */     }
/*     */     
/* 283 */     advancementtreenode.func_192327_e();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\AdvancementTreeNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */