/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ 
/*     */ public class DamageSourcePredicate
/*     */ {
/*  12 */   public static DamageSourcePredicate field_192449_a = new DamageSourcePredicate();
/*     */   
/*     */   private final Boolean field_192450_b;
/*     */   private final Boolean field_192451_c;
/*     */   private final Boolean field_192452_d;
/*     */   private final Boolean field_192453_e;
/*     */   private final Boolean field_192454_f;
/*     */   private final Boolean field_192455_g;
/*     */   private final Boolean field_192456_h;
/*     */   private final EntityPredicate field_193419_i;
/*     */   private final EntityPredicate field_193420_j;
/*     */   
/*     */   public DamageSourcePredicate() {
/*  25 */     this.field_192450_b = null;
/*  26 */     this.field_192451_c = null;
/*  27 */     this.field_192452_d = null;
/*  28 */     this.field_192453_e = null;
/*  29 */     this.field_192454_f = null;
/*  30 */     this.field_192455_g = null;
/*  31 */     this.field_192456_h = null;
/*  32 */     this.field_193419_i = EntityPredicate.field_192483_a;
/*  33 */     this.field_193420_j = EntityPredicate.field_192483_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public DamageSourcePredicate(@Nullable Boolean p_i47543_1_, @Nullable Boolean p_i47543_2_, @Nullable Boolean p_i47543_3_, @Nullable Boolean p_i47543_4_, @Nullable Boolean p_i47543_5_, @Nullable Boolean p_i47543_6_, @Nullable Boolean p_i47543_7_, EntityPredicate p_i47543_8_, EntityPredicate p_i47543_9_) {
/*  38 */     this.field_192450_b = p_i47543_1_;
/*  39 */     this.field_192451_c = p_i47543_2_;
/*  40 */     this.field_192452_d = p_i47543_3_;
/*  41 */     this.field_192453_e = p_i47543_4_;
/*  42 */     this.field_192454_f = p_i47543_5_;
/*  43 */     this.field_192455_g = p_i47543_6_;
/*  44 */     this.field_192456_h = p_i47543_7_;
/*  45 */     this.field_193419_i = p_i47543_8_;
/*  46 */     this.field_193420_j = p_i47543_9_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193418_a(EntityPlayerMP p_193418_1_, DamageSource p_193418_2_) {
/*  51 */     if (this == field_192449_a)
/*     */     {
/*  53 */       return true;
/*     */     }
/*  55 */     if (this.field_192450_b != null && this.field_192450_b.booleanValue() != p_193418_2_.isProjectile())
/*     */     {
/*  57 */       return false;
/*     */     }
/*  59 */     if (this.field_192451_c != null && this.field_192451_c.booleanValue() != p_193418_2_.isExplosion())
/*     */     {
/*  61 */       return false;
/*     */     }
/*  63 */     if (this.field_192452_d != null && this.field_192452_d.booleanValue() != p_193418_2_.isUnblockable())
/*     */     {
/*  65 */       return false;
/*     */     }
/*  67 */     if (this.field_192453_e != null && this.field_192453_e.booleanValue() != p_193418_2_.canHarmInCreative())
/*     */     {
/*  69 */       return false;
/*     */     }
/*  71 */     if (this.field_192454_f != null && this.field_192454_f.booleanValue() != p_193418_2_.isDamageAbsolute())
/*     */     {
/*  73 */       return false;
/*     */     }
/*  75 */     if (this.field_192455_g != null && this.field_192455_g.booleanValue() != p_193418_2_.isFireDamage())
/*     */     {
/*  77 */       return false;
/*     */     }
/*  79 */     if (this.field_192456_h != null && this.field_192456_h.booleanValue() != p_193418_2_.isMagicDamage())
/*     */     {
/*  81 */       return false;
/*     */     }
/*  83 */     if (!this.field_193419_i.func_192482_a(p_193418_1_, p_193418_2_.getSourceOfDamage()))
/*     */     {
/*  85 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  89 */     return this.field_193420_j.func_192482_a(p_193418_1_, p_193418_2_.getEntity());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSourcePredicate func_192447_a(@Nullable JsonElement p_192447_0_) {
/*  95 */     if (p_192447_0_ != null && !p_192447_0_.isJsonNull()) {
/*     */       
/*  97 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_192447_0_, "damage type");
/*  98 */       Boolean obool = func_192448_a(jsonobject, "is_projectile");
/*  99 */       Boolean obool1 = func_192448_a(jsonobject, "is_explosion");
/* 100 */       Boolean obool2 = func_192448_a(jsonobject, "bypasses_armor");
/* 101 */       Boolean obool3 = func_192448_a(jsonobject, "bypasses_invulnerability");
/* 102 */       Boolean obool4 = func_192448_a(jsonobject, "bypasses_magic");
/* 103 */       Boolean obool5 = func_192448_a(jsonobject, "is_fire");
/* 104 */       Boolean obool6 = func_192448_a(jsonobject, "is_magic");
/* 105 */       EntityPredicate entitypredicate = EntityPredicate.func_192481_a(jsonobject.get("direct_entity"));
/* 106 */       EntityPredicate entitypredicate1 = EntityPredicate.func_192481_a(jsonobject.get("source_entity"));
/* 107 */       return new DamageSourcePredicate(obool, obool1, obool2, obool3, obool4, obool5, obool6, entitypredicate, entitypredicate1);
/*     */     } 
/*     */ 
/*     */     
/* 111 */     return field_192449_a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Boolean func_192448_a(JsonObject p_192448_0_, String p_192448_1_) {
/* 118 */     return p_192448_0_.has(p_192448_1_) ? Boolean.valueOf(JsonUtils.getBoolean(p_192448_0_, p_192448_1_)) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\DamageSourcePredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */