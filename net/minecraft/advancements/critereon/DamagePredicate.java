/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ public class DamagePredicate
/*    */ {
/* 12 */   public static DamagePredicate field_192366_a = new DamagePredicate();
/*    */   
/*    */   private final MinMaxBounds field_192367_b;
/*    */   private final MinMaxBounds field_192368_c;
/*    */   private final EntityPredicate field_192369_d;
/*    */   private final Boolean field_192370_e;
/*    */   private final DamageSourcePredicate field_192371_f;
/*    */   
/*    */   public DamagePredicate() {
/* 21 */     this.field_192367_b = MinMaxBounds.field_192516_a;
/* 22 */     this.field_192368_c = MinMaxBounds.field_192516_a;
/* 23 */     this.field_192369_d = EntityPredicate.field_192483_a;
/* 24 */     this.field_192370_e = null;
/* 25 */     this.field_192371_f = DamageSourcePredicate.field_192449_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public DamagePredicate(MinMaxBounds p_i47464_1_, MinMaxBounds p_i47464_2_, EntityPredicate p_i47464_3_, @Nullable Boolean p_i47464_4_, DamageSourcePredicate p_i47464_5_) {
/* 30 */     this.field_192367_b = p_i47464_1_;
/* 31 */     this.field_192368_c = p_i47464_2_;
/* 32 */     this.field_192369_d = p_i47464_3_;
/* 33 */     this.field_192370_e = p_i47464_4_;
/* 34 */     this.field_192371_f = p_i47464_5_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_192365_a(EntityPlayerMP p_192365_1_, DamageSource p_192365_2_, float p_192365_3_, float p_192365_4_, boolean p_192365_5_) {
/* 39 */     if (this == field_192366_a)
/*    */     {
/* 41 */       return true;
/*    */     }
/* 43 */     if (!this.field_192367_b.func_192514_a(p_192365_3_))
/*    */     {
/* 45 */       return false;
/*    */     }
/* 47 */     if (!this.field_192368_c.func_192514_a(p_192365_4_))
/*    */     {
/* 49 */       return false;
/*    */     }
/* 51 */     if (!this.field_192369_d.func_192482_a(p_192365_1_, p_192365_2_.getEntity()))
/*    */     {
/* 53 */       return false;
/*    */     }
/* 55 */     if (this.field_192370_e != null && this.field_192370_e.booleanValue() != p_192365_5_)
/*    */     {
/* 57 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 61 */     return this.field_192371_f.func_193418_a(p_192365_1_, p_192365_2_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DamagePredicate func_192364_a(@Nullable JsonElement p_192364_0_) {
/* 67 */     if (p_192364_0_ != null && !p_192364_0_.isJsonNull()) {
/*    */       
/* 69 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_192364_0_, "damage");
/* 70 */       MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(jsonobject.get("dealt"));
/* 71 */       MinMaxBounds minmaxbounds1 = MinMaxBounds.func_192515_a(jsonobject.get("taken"));
/* 72 */       Boolean obool = jsonobject.has("blocked") ? Boolean.valueOf(JsonUtils.getBoolean(jsonobject, "blocked")) : null;
/* 73 */       EntityPredicate entitypredicate = EntityPredicate.func_192481_a(jsonobject.get("source_entity"));
/* 74 */       DamageSourcePredicate damagesourcepredicate = DamageSourcePredicate.func_192447_a(jsonobject.get("type"));
/* 75 */       return new DamagePredicate(minmaxbounds, minmaxbounds1, entitypredicate, obool, damagesourcepredicate);
/*    */     } 
/*    */ 
/*    */     
/* 79 */     return field_192366_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\DamagePredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */