/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSyntaxException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityList;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EntityPredicate
/*    */ {
/* 15 */   public static final EntityPredicate field_192483_a = new EntityPredicate(null, DistancePredicate.field_193423_a, LocationPredicate.field_193455_a, MobEffectsPredicate.field_193473_a, NBTPredicate.field_193479_a);
/*    */   
/*    */   private final ResourceLocation field_192484_b;
/*    */   private final DistancePredicate field_192485_c;
/*    */   private final LocationPredicate field_193435_d;
/*    */   private final MobEffectsPredicate field_193436_e;
/*    */   private final NBTPredicate field_193437_f;
/*    */   
/*    */   public EntityPredicate(@Nullable ResourceLocation p_i47541_1_, DistancePredicate p_i47541_2_, LocationPredicate p_i47541_3_, MobEffectsPredicate p_i47541_4_, NBTPredicate p_i47541_5_) {
/* 24 */     this.field_192484_b = p_i47541_1_;
/* 25 */     this.field_192485_c = p_i47541_2_;
/* 26 */     this.field_193435_d = p_i47541_3_;
/* 27 */     this.field_193436_e = p_i47541_4_;
/* 28 */     this.field_193437_f = p_i47541_5_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_192482_a(EntityPlayerMP p_192482_1_, @Nullable Entity p_192482_2_) {
/* 33 */     if (this == field_192483_a)
/*    */     {
/* 35 */       return true;
/*    */     }
/* 37 */     if (p_192482_2_ == null)
/*    */     {
/* 39 */       return false;
/*    */     }
/* 41 */     if (this.field_192484_b != null && !EntityList.isStringEntityName(p_192482_2_, this.field_192484_b))
/*    */     {
/* 43 */       return false;
/*    */     }
/* 45 */     if (!this.field_192485_c.func_193422_a(p_192482_1_.posX, p_192482_1_.posY, p_192482_1_.posZ, p_192482_2_.posX, p_192482_2_.posY, p_192482_2_.posZ))
/*    */     {
/* 47 */       return false;
/*    */     }
/* 49 */     if (!this.field_193435_d.func_193452_a(p_192482_1_.getServerWorld(), p_192482_2_.posX, p_192482_2_.posY, p_192482_2_.posZ))
/*    */     {
/* 51 */       return false;
/*    */     }
/* 53 */     if (!this.field_193436_e.func_193469_a(p_192482_2_))
/*    */     {
/* 55 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 59 */     return this.field_193437_f.func_193475_a(p_192482_2_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static EntityPredicate func_192481_a(@Nullable JsonElement p_192481_0_) {
/* 65 */     if (p_192481_0_ != null && !p_192481_0_.isJsonNull()) {
/*    */       
/* 67 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_192481_0_, "entity");
/* 68 */       ResourceLocation resourcelocation = null;
/*    */       
/* 70 */       if (jsonobject.has("type")) {
/*    */         
/* 72 */         resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "type"));
/*    */         
/* 74 */         if (!EntityList.isStringValidEntityName(resourcelocation))
/*    */         {
/* 76 */           throw new JsonSyntaxException("Unknown entity type '" + resourcelocation + "', valid types are: " + EntityList.func_192840_b());
/*    */         }
/*    */       } 
/*    */       
/* 80 */       DistancePredicate distancepredicate = DistancePredicate.func_193421_a(jsonobject.get("distance"));
/* 81 */       LocationPredicate locationpredicate = LocationPredicate.func_193454_a(jsonobject.get("location"));
/* 82 */       MobEffectsPredicate mobeffectspredicate = MobEffectsPredicate.func_193471_a(jsonobject.get("effects"));
/* 83 */       NBTPredicate nbtpredicate = NBTPredicate.func_193476_a(jsonobject.get("nbt"));
/* 84 */       return new EntityPredicate(resourcelocation, distancepredicate, locationpredicate, mobeffectspredicate, nbtpredicate);
/*    */     } 
/*    */ 
/*    */     
/* 88 */     return field_192483_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\EntityPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */