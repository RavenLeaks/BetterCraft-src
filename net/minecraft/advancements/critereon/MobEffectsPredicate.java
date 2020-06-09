/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class MobEffectsPredicate
/*     */ {
/*  20 */   public static final MobEffectsPredicate field_193473_a = new MobEffectsPredicate(Collections.emptyMap());
/*     */   
/*     */   private final Map<Potion, InstancePredicate> field_193474_b;
/*     */   
/*     */   public MobEffectsPredicate(Map<Potion, InstancePredicate> p_i47538_1_) {
/*  25 */     this.field_193474_b = p_i47538_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193469_a(Entity p_193469_1_) {
/*  30 */     if (this == field_193473_a)
/*     */     {
/*  32 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  36 */     return (p_193469_1_ instanceof EntityLivingBase) ? func_193470_a(((EntityLivingBase)p_193469_1_).func_193076_bZ()) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_193472_a(EntityLivingBase p_193472_1_) {
/*  42 */     return (this == field_193473_a) ? true : func_193470_a(p_193472_1_.func_193076_bZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193470_a(Map<Potion, PotionEffect> p_193470_1_) {
/*  47 */     if (this == field_193473_a)
/*     */     {
/*  49 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  53 */     for (Map.Entry<Potion, InstancePredicate> entry : this.field_193474_b.entrySet()) {
/*     */       
/*  55 */       PotionEffect potioneffect = p_193470_1_.get(entry.getKey());
/*     */       
/*  57 */       if (!((InstancePredicate)entry.getValue()).func_193463_a(potioneffect))
/*     */       {
/*  59 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static MobEffectsPredicate func_193471_a(@Nullable JsonElement p_193471_0_) {
/*  69 */     if (p_193471_0_ != null && !p_193471_0_.isJsonNull()) {
/*     */       
/*  71 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_193471_0_, "effects");
/*  72 */       Map<Potion, InstancePredicate> map = Maps.newHashMap();
/*     */       
/*  74 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/*     */         
/*  76 */         ResourceLocation resourcelocation = new ResourceLocation(entry.getKey());
/*  77 */         Potion potion = (Potion)Potion.REGISTRY.getObject(resourcelocation);
/*     */         
/*  79 */         if (potion == null)
/*     */         {
/*  81 */           throw new JsonSyntaxException("Unknown effect '" + resourcelocation + "'");
/*     */         }
/*     */         
/*  84 */         InstancePredicate mobeffectspredicate$instancepredicate = InstancePredicate.func_193464_a(JsonUtils.getJsonObject(entry.getValue(), entry.getKey()));
/*  85 */         map.put(potion, mobeffectspredicate$instancepredicate);
/*     */       } 
/*     */       
/*  88 */       return new MobEffectsPredicate(map);
/*     */     } 
/*     */ 
/*     */     
/*  92 */     return field_193473_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class InstancePredicate
/*     */   {
/*     */     private final MinMaxBounds field_193465_a;
/*     */     
/*     */     private final MinMaxBounds field_193466_b;
/*     */     @Nullable
/*     */     private final Boolean field_193467_c;
/*     */     @Nullable
/*     */     private final Boolean field_193468_d;
/*     */     
/*     */     public InstancePredicate(MinMaxBounds p_i47497_1_, MinMaxBounds p_i47497_2_, @Nullable Boolean p_i47497_3_, @Nullable Boolean p_i47497_4_) {
/* 107 */       this.field_193465_a = p_i47497_1_;
/* 108 */       this.field_193466_b = p_i47497_2_;
/* 109 */       this.field_193467_c = p_i47497_3_;
/* 110 */       this.field_193468_d = p_i47497_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193463_a(@Nullable PotionEffect p_193463_1_) {
/* 115 */       if (p_193463_1_ == null)
/*     */       {
/* 117 */         return false;
/*     */       }
/* 119 */       if (!this.field_193465_a.func_192514_a(p_193463_1_.getAmplifier()))
/*     */       {
/* 121 */         return false;
/*     */       }
/* 123 */       if (!this.field_193466_b.func_192514_a(p_193463_1_.getDuration()))
/*     */       {
/* 125 */         return false;
/*     */       }
/* 127 */       if (this.field_193467_c != null && this.field_193467_c.booleanValue() != p_193463_1_.getIsAmbient())
/*     */       {
/* 129 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 133 */       return !(this.field_193468_d != null && this.field_193468_d.booleanValue() != p_193463_1_.doesShowParticles());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static InstancePredicate func_193464_a(JsonObject p_193464_0_) {
/* 139 */       MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(p_193464_0_.get("amplifier"));
/* 140 */       MinMaxBounds minmaxbounds1 = MinMaxBounds.func_192515_a(p_193464_0_.get("duration"));
/* 141 */       Boolean obool = p_193464_0_.has("ambient") ? Boolean.valueOf(JsonUtils.getBoolean(p_193464_0_, "ambient")) : null;
/* 142 */       Boolean obool1 = p_193464_0_.has("visible") ? Boolean.valueOf(JsonUtils.getBoolean(p_193464_0_, "visible")) : null;
/* 143 */       return new InstancePredicate(minmaxbounds, minmaxbounds1, obool, obool1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\MobEffectsPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */