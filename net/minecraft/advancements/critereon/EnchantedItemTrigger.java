/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.advancements.ICriterionInstance;
/*     */ import net.minecraft.advancements.ICriterionTrigger;
/*     */ import net.minecraft.advancements.PlayerAdvancements;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class EnchantedItemTrigger implements ICriterionTrigger<EnchantedItemTrigger.Instance> {
/*  19 */   private static final ResourceLocation field_192191_a = new ResourceLocation("enchanted_item");
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_192192_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  24 */     return field_192191_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  29 */     Listeners enchanteditemtrigger$listeners = this.field_192192_b.get(p_192165_1_);
/*     */     
/*  31 */     if (enchanteditemtrigger$listeners == null) {
/*     */       
/*  33 */       enchanteditemtrigger$listeners = new Listeners(p_192165_1_);
/*  34 */       this.field_192192_b.put(p_192165_1_, enchanteditemtrigger$listeners);
/*     */     } 
/*     */     
/*  37 */     enchanteditemtrigger$listeners.func_192460_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  42 */     Listeners enchanteditemtrigger$listeners = this.field_192192_b.get(p_192164_1_);
/*     */     
/*  44 */     if (enchanteditemtrigger$listeners != null) {
/*     */       
/*  46 */       enchanteditemtrigger$listeners.func_192457_b(p_192164_2_);
/*     */       
/*  48 */       if (enchanteditemtrigger$listeners.func_192458_a())
/*     */       {
/*  50 */         this.field_192192_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  57 */     this.field_192192_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  62 */     ItemPredicate itempredicate = ItemPredicate.func_192492_a(p_192166_1_.get("item"));
/*  63 */     MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(p_192166_1_.get("levels"));
/*  64 */     return new Instance(itempredicate, minmaxbounds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192190_a(EntityPlayerMP p_192190_1_, ItemStack p_192190_2_, int p_192190_3_) {
/*  69 */     Listeners enchanteditemtrigger$listeners = this.field_192192_b.get(p_192190_1_.func_192039_O());
/*     */     
/*  71 */     if (enchanteditemtrigger$listeners != null)
/*     */     {
/*  73 */       enchanteditemtrigger$listeners.func_192459_a(p_192190_2_, p_192190_3_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final ItemPredicate field_192258_a;
/*     */     private final MinMaxBounds field_192259_b;
/*     */     
/*     */     public Instance(ItemPredicate p_i47376_1_, MinMaxBounds p_i47376_2_) {
/*  84 */       super(EnchantedItemTrigger.field_192191_a);
/*  85 */       this.field_192258_a = p_i47376_1_;
/*  86 */       this.field_192259_b = p_i47376_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192257_a(ItemStack p_192257_1_, int p_192257_2_) {
/*  91 */       if (!this.field_192258_a.func_192493_a(p_192257_1_))
/*     */       {
/*  93 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  97 */       return this.field_192259_b.func_192514_a(p_192257_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192461_a;
/* 105 */     private final Set<ICriterionTrigger.Listener<EnchantedItemTrigger.Instance>> field_192462_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47377_1_) {
/* 109 */       this.field_192461_a = p_i47377_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192458_a() {
/* 114 */       return this.field_192462_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192460_a(ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> p_192460_1_) {
/* 119 */       this.field_192462_b.add(p_192460_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192457_b(ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> p_192457_1_) {
/* 124 */       this.field_192462_b.remove(p_192457_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192459_a(ItemStack p_192459_1_, int p_192459_2_) {
/* 129 */       List<ICriterionTrigger.Listener<EnchantedItemTrigger.Instance>> list = null;
/*     */       
/* 131 */       for (ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> listener : this.field_192462_b) {
/*     */         
/* 133 */         if (((EnchantedItemTrigger.Instance)listener.func_192158_a()).func_192257_a(p_192459_1_, p_192459_2_)) {
/*     */           
/* 135 */           if (list == null)
/*     */           {
/* 137 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 140 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 144 */       if (list != null)
/*     */       {
/* 146 */         for (ICriterionTrigger.Listener<EnchantedItemTrigger.Instance> listener1 : list)
/*     */         {
/* 148 */           listener1.func_192159_a(this.field_192461_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\EnchantedItemTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */