/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class EnchantmentPredicate
/*     */ {
/*  15 */   public static final EnchantmentPredicate field_192466_a = new EnchantmentPredicate();
/*     */   
/*     */   private final Enchantment field_192467_b;
/*     */   private final MinMaxBounds field_192468_c;
/*     */   
/*     */   public EnchantmentPredicate() {
/*  21 */     this.field_192467_b = null;
/*  22 */     this.field_192468_c = MinMaxBounds.field_192516_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnchantmentPredicate(@Nullable Enchantment p_i47436_1_, MinMaxBounds p_i47436_2_) {
/*  27 */     this.field_192467_b = p_i47436_1_;
/*  28 */     this.field_192468_c = p_i47436_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192463_a(Map<Enchantment, Integer> p_192463_1_) {
/*  33 */     if (this.field_192467_b != null) {
/*     */       
/*  35 */       if (!p_192463_1_.containsKey(this.field_192467_b))
/*     */       {
/*  37 */         return false;
/*     */       }
/*     */       
/*  40 */       int i = ((Integer)p_192463_1_.get(this.field_192467_b)).intValue();
/*     */       
/*  42 */       if (this.field_192468_c != null && !this.field_192468_c.func_192514_a(i))
/*     */       {
/*  44 */         return false;
/*     */       }
/*     */     }
/*  47 */     else if (this.field_192468_c != null) {
/*     */       
/*  49 */       for (Integer integer : p_192463_1_.values()) {
/*     */         
/*  51 */         if (this.field_192468_c.func_192514_a(integer.intValue()))
/*     */         {
/*  53 */           return true;
/*     */         }
/*     */       } 
/*     */       
/*  57 */       return false;
/*     */     } 
/*     */     
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnchantmentPredicate func_192464_a(@Nullable JsonElement p_192464_0_) {
/*  65 */     if (p_192464_0_ != null && !p_192464_0_.isJsonNull()) {
/*     */       
/*  67 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_192464_0_, "enchantment");
/*  68 */       Enchantment enchantment = null;
/*     */       
/*  70 */       if (jsonobject.has("enchantment")) {
/*     */         
/*  72 */         ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "enchantment"));
/*  73 */         enchantment = (Enchantment)Enchantment.REGISTRY.getObject(resourcelocation);
/*     */         
/*  75 */         if (enchantment == null)
/*     */         {
/*  77 */           throw new JsonSyntaxException("Unknown enchantment '" + resourcelocation + "'");
/*     */         }
/*     */       } 
/*     */       
/*  81 */       MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(jsonobject.get("levels"));
/*  82 */       return new EnchantmentPredicate(enchantment, minmaxbounds);
/*     */     } 
/*     */ 
/*     */     
/*  86 */     return field_192466_a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnchantmentPredicate[] func_192465_b(@Nullable JsonElement p_192465_0_) {
/*  92 */     if (p_192465_0_ != null && !p_192465_0_.isJsonNull()) {
/*     */       
/*  94 */       JsonArray jsonarray = JsonUtils.getJsonArray(p_192465_0_, "enchantments");
/*  95 */       EnchantmentPredicate[] aenchantmentpredicate = new EnchantmentPredicate[jsonarray.size()];
/*     */       
/*  97 */       for (int i = 0; i < aenchantmentpredicate.length; i++)
/*     */       {
/*  99 */         aenchantmentpredicate[i] = func_192464_a(jsonarray.get(i));
/*     */       }
/*     */       
/* 102 */       return aenchantmentpredicate;
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return new EnchantmentPredicate[0];
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\EnchantmentPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */