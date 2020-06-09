/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Functions;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AdvancementList
/*     */ {
/*  18 */   private static final Logger field_192091_a = LogManager.getLogger();
/*  19 */   private final Map<ResourceLocation, Advancement> field_192092_b = Maps.newHashMap();
/*  20 */   private final Set<Advancement> field_192093_c = Sets.newLinkedHashSet();
/*  21 */   private final Set<Advancement> field_192094_d = Sets.newLinkedHashSet();
/*     */   
/*     */   private Listener field_192095_e;
/*     */   
/*     */   private void func_192090_a(Advancement p_192090_1_) {
/*  26 */     for (Advancement advancement : p_192090_1_.func_192069_e())
/*     */     {
/*  28 */       func_192090_a(advancement);
/*     */     }
/*     */     
/*  31 */     field_192091_a.info("Forgot about advancement " + p_192090_1_.func_192067_g());
/*  32 */     this.field_192092_b.remove(p_192090_1_.func_192067_g());
/*     */     
/*  34 */     if (p_192090_1_.func_192070_b() == null) {
/*     */       
/*  36 */       this.field_192093_c.remove(p_192090_1_);
/*     */       
/*  38 */       if (this.field_192095_e != null)
/*     */       {
/*  40 */         this.field_192095_e.func_191928_b(p_192090_1_);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  45 */       this.field_192094_d.remove(p_192090_1_);
/*     */       
/*  47 */       if (this.field_192095_e != null)
/*     */       {
/*  49 */         this.field_192095_e.func_191929_d(p_192090_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192085_a(Set<ResourceLocation> p_192085_1_) {
/*  56 */     for (ResourceLocation resourcelocation : p_192085_1_) {
/*     */       
/*  58 */       Advancement advancement = this.field_192092_b.get(resourcelocation);
/*     */       
/*  60 */       if (advancement == null) {
/*     */         
/*  62 */         field_192091_a.warn("Told to remove advancement " + resourcelocation + " but I don't know what that is");
/*     */         
/*     */         continue;
/*     */       } 
/*  66 */       func_192090_a(advancement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_192083_a(Map<ResourceLocation, Advancement.Builder> p_192083_1_) {
/*  73 */     Function function = Functions.forMap(this.field_192092_b, null);
/*     */ 
/*     */     
/*  76 */     while (!p_192083_1_.isEmpty()) {
/*     */       
/*  78 */       boolean flag = false;
/*  79 */       Iterator<Map.Entry<ResourceLocation, Advancement.Builder>> iterator = p_192083_1_.entrySet().iterator();
/*     */       
/*  81 */       while (iterator.hasNext()) {
/*     */         
/*  83 */         Map.Entry<ResourceLocation, Advancement.Builder> entry = iterator.next();
/*  84 */         ResourceLocation resourcelocation = entry.getKey();
/*  85 */         Advancement.Builder advancement$builder = entry.getValue();
/*     */         
/*  87 */         if (advancement$builder.func_192058_a((Function<ResourceLocation, Advancement>)function)) {
/*     */           
/*  89 */           Advancement advancement = advancement$builder.func_192056_a(resourcelocation);
/*  90 */           this.field_192092_b.put(resourcelocation, advancement);
/*  91 */           flag = true;
/*  92 */           iterator.remove();
/*     */           
/*  94 */           if (advancement.func_192070_b() == null) {
/*     */             
/*  96 */             this.field_192093_c.add(advancement);
/*     */             
/*  98 */             if (this.field_192095_e != null)
/*     */             {
/* 100 */               this.field_192095_e.func_191931_a(advancement);
/*     */             }
/*     */             
/*     */             continue;
/*     */           } 
/* 105 */           this.field_192094_d.add(advancement);
/*     */           
/* 107 */           if (this.field_192095_e != null)
/*     */           {
/* 109 */             this.field_192095_e.func_191932_c(advancement);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 115 */       if (!flag) {
/*     */         
/* 117 */         iterator = p_192083_1_.entrySet().iterator();
/*     */ 
/*     */ 
/*     */         
/* 121 */         while (iterator.hasNext()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 126 */           Map.Entry<ResourceLocation, Advancement.Builder> entry1 = iterator.next();
/* 127 */           field_192091_a.error("Couldn't load advancement " + entry1.getKey() + ": " + entry1.getValue());
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/* 132 */     field_192091_a.info("Loaded " + this.field_192092_b.size() + " advancements");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192087_a() {
/* 137 */     this.field_192092_b.clear();
/* 138 */     this.field_192093_c.clear();
/* 139 */     this.field_192094_d.clear();
/*     */     
/* 141 */     if (this.field_192095_e != null)
/*     */     {
/* 143 */       this.field_192095_e.func_191930_a();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<Advancement> func_192088_b() {
/* 149 */     return this.field_192093_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<Advancement> func_192089_c() {
/* 154 */     return this.field_192092_b.values();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Advancement func_192084_a(ResourceLocation p_192084_1_) {
/* 160 */     return this.field_192092_b.get(p_192084_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192086_a(@Nullable Listener p_192086_1_) {
/* 165 */     this.field_192095_e = p_192086_1_;
/*     */     
/* 167 */     if (p_192086_1_ != null) {
/*     */       
/* 169 */       for (Advancement advancement : this.field_192093_c)
/*     */       {
/* 171 */         p_192086_1_.func_191931_a(advancement);
/*     */       }
/*     */       
/* 174 */       for (Advancement advancement1 : this.field_192094_d)
/*     */       {
/* 176 */         p_192086_1_.func_191932_c(advancement1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface Listener {
/*     */     void func_191931_a(Advancement param1Advancement);
/*     */     
/*     */     void func_191928_b(Advancement param1Advancement);
/*     */     
/*     */     void func_191932_c(Advancement param1Advancement);
/*     */     
/*     */     void func_191929_d(Advancement param1Advancement);
/*     */     
/*     */     void func_191930_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\AdvancementList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */