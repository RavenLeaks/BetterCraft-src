/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionType;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class ItemPredicate
/*     */ {
/*  20 */   public static final ItemPredicate field_192495_a = new ItemPredicate();
/*     */   
/*     */   private final Item field_192496_b;
/*     */   private final Integer field_192497_c;
/*     */   private final MinMaxBounds field_192498_d;
/*     */   private final MinMaxBounds field_193444_e;
/*     */   private final EnchantmentPredicate[] field_192499_e;
/*     */   private final PotionType field_192500_f;
/*     */   private final NBTPredicate field_193445_h;
/*     */   
/*     */   public ItemPredicate() {
/*  31 */     this.field_192496_b = null;
/*  32 */     this.field_192497_c = null;
/*  33 */     this.field_192500_f = null;
/*  34 */     this.field_192498_d = MinMaxBounds.field_192516_a;
/*  35 */     this.field_193444_e = MinMaxBounds.field_192516_a;
/*  36 */     this.field_192499_e = new EnchantmentPredicate[0];
/*  37 */     this.field_193445_h = NBTPredicate.field_193479_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemPredicate(@Nullable Item p_i47540_1_, @Nullable Integer p_i47540_2_, MinMaxBounds p_i47540_3_, MinMaxBounds p_i47540_4_, EnchantmentPredicate[] p_i47540_5_, @Nullable PotionType p_i47540_6_, NBTPredicate p_i47540_7_) {
/*  42 */     this.field_192496_b = p_i47540_1_;
/*  43 */     this.field_192497_c = p_i47540_2_;
/*  44 */     this.field_192498_d = p_i47540_3_;
/*  45 */     this.field_193444_e = p_i47540_4_;
/*  46 */     this.field_192499_e = p_i47540_5_;
/*  47 */     this.field_192500_f = p_i47540_6_;
/*  48 */     this.field_193445_h = p_i47540_7_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192493_a(ItemStack p_192493_1_) {
/*  53 */     if (this.field_192496_b != null && p_192493_1_.getItem() != this.field_192496_b)
/*     */     {
/*  55 */       return false;
/*     */     }
/*  57 */     if (this.field_192497_c != null && p_192493_1_.getMetadata() != this.field_192497_c.intValue())
/*     */     {
/*  59 */       return false;
/*     */     }
/*  61 */     if (!this.field_192498_d.func_192514_a(p_192493_1_.func_190916_E()))
/*     */     {
/*  63 */       return false;
/*     */     }
/*  65 */     if (this.field_193444_e != MinMaxBounds.field_192516_a && !p_192493_1_.isItemStackDamageable())
/*     */     {
/*  67 */       return false;
/*     */     }
/*  69 */     if (!this.field_193444_e.func_192514_a((p_192493_1_.getMaxDamage() - p_192493_1_.getItemDamage())))
/*     */     {
/*  71 */       return false;
/*     */     }
/*  73 */     if (!this.field_193445_h.func_193478_a(p_192493_1_))
/*     */     {
/*  75 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  79 */     Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(p_192493_1_);
/*     */     
/*  81 */     for (int i = 0; i < this.field_192499_e.length; i++) {
/*     */       
/*  83 */       if (!this.field_192499_e[i].func_192463_a(map))
/*     */       {
/*  85 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  89 */     PotionType potiontype = PotionUtils.getPotionFromItem(p_192493_1_);
/*     */     
/*  91 */     if (this.field_192500_f != null && this.field_192500_f != potiontype)
/*     */     {
/*  93 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemPredicate func_192492_a(@Nullable JsonElement p_192492_0_) {
/* 104 */     if (p_192492_0_ != null && !p_192492_0_.isJsonNull()) {
/*     */       
/* 106 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_192492_0_, "item");
/* 107 */       MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(jsonobject.get("count"));
/* 108 */       MinMaxBounds minmaxbounds1 = MinMaxBounds.func_192515_a(jsonobject.get("durability"));
/* 109 */       Integer integer = jsonobject.has("data") ? Integer.valueOf(JsonUtils.getInt(jsonobject, "data")) : null;
/* 110 */       NBTPredicate nbtpredicate = NBTPredicate.func_193476_a(jsonobject.get("nbt"));
/* 111 */       Item item = null;
/*     */       
/* 113 */       if (jsonobject.has("item")) {
/*     */         
/* 115 */         ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "item"));
/* 116 */         item = (Item)Item.REGISTRY.getObject(resourcelocation);
/*     */         
/* 118 */         if (item == null)
/*     */         {
/* 120 */           throw new JsonSyntaxException("Unknown item id '" + resourcelocation + "'");
/*     */         }
/*     */       } 
/*     */       
/* 124 */       EnchantmentPredicate[] aenchantmentpredicate = EnchantmentPredicate.func_192465_b(jsonobject.get("enchantments"));
/* 125 */       PotionType potiontype = null;
/*     */       
/* 127 */       if (jsonobject.has("potion")) {
/*     */         
/* 129 */         ResourceLocation resourcelocation1 = new ResourceLocation(JsonUtils.getString(jsonobject, "potion"));
/*     */         
/* 131 */         if (!PotionType.REGISTRY.containsKey(resourcelocation1))
/*     */         {
/* 133 */           throw new JsonSyntaxException("Unknown potion '" + resourcelocation1 + "'");
/*     */         }
/*     */         
/* 136 */         potiontype = (PotionType)PotionType.REGISTRY.getObject(resourcelocation1);
/*     */       } 
/*     */       
/* 139 */       return new ItemPredicate(item, integer, minmaxbounds, minmaxbounds1, aenchantmentpredicate, potiontype, nbtpredicate);
/*     */     } 
/*     */ 
/*     */     
/* 143 */     return field_192495_a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemPredicate[] func_192494_b(@Nullable JsonElement p_192494_0_) {
/* 149 */     if (p_192494_0_ != null && !p_192494_0_.isJsonNull()) {
/*     */       
/* 151 */       JsonArray jsonarray = JsonUtils.getJsonArray(p_192494_0_, "items");
/* 152 */       ItemPredicate[] aitempredicate = new ItemPredicate[jsonarray.size()];
/*     */       
/* 154 */       for (int i = 0; i < aitempredicate.length; i++)
/*     */       {
/* 156 */         aitempredicate[i] = func_192492_a(jsonarray.get(i));
/*     */       }
/*     */       
/* 159 */       return aitempredicate;
/*     */     } 
/*     */ 
/*     */     
/* 163 */     return new ItemPredicate[0];
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\ItemPredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */