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
/*     */ public class ItemDurabilityTrigger implements ICriterionTrigger<ItemDurabilityTrigger.Instance> {
/*  19 */   private static final ResourceLocation field_193159_a = new ResourceLocation("item_durability_changed");
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_193160_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  24 */     return field_193159_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  29 */     Listeners itemdurabilitytrigger$listeners = this.field_193160_b.get(p_192165_1_);
/*     */     
/*  31 */     if (itemdurabilitytrigger$listeners == null) {
/*     */       
/*  33 */       itemdurabilitytrigger$listeners = new Listeners(p_192165_1_);
/*  34 */       this.field_193160_b.put(p_192165_1_, itemdurabilitytrigger$listeners);
/*     */     } 
/*     */     
/*  37 */     itemdurabilitytrigger$listeners.func_193440_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  42 */     Listeners itemdurabilitytrigger$listeners = this.field_193160_b.get(p_192164_1_);
/*     */     
/*  44 */     if (itemdurabilitytrigger$listeners != null) {
/*     */       
/*  46 */       itemdurabilitytrigger$listeners.func_193438_b(p_192164_2_);
/*     */       
/*  48 */       if (itemdurabilitytrigger$listeners.func_193439_a())
/*     */       {
/*  50 */         this.field_193160_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  57 */     this.field_193160_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  62 */     ItemPredicate itempredicate = ItemPredicate.func_192492_a(p_192166_1_.get("item"));
/*  63 */     MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(p_192166_1_.get("durability"));
/*  64 */     MinMaxBounds minmaxbounds1 = MinMaxBounds.func_192515_a(p_192166_1_.get("delta"));
/*  65 */     return new Instance(itempredicate, minmaxbounds, minmaxbounds1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193158_a(EntityPlayerMP p_193158_1_, ItemStack p_193158_2_, int p_193158_3_) {
/*  70 */     Listeners itemdurabilitytrigger$listeners = this.field_193160_b.get(p_193158_1_.func_192039_O());
/*     */     
/*  72 */     if (itemdurabilitytrigger$listeners != null)
/*     */     {
/*  74 */       itemdurabilitytrigger$listeners.func_193441_a(p_193158_2_, p_193158_3_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final ItemPredicate field_193198_a;
/*     */     private final MinMaxBounds field_193199_b;
/*     */     private final MinMaxBounds field_193200_c;
/*     */     
/*     */     public Instance(ItemPredicate p_i47511_1_, MinMaxBounds p_i47511_2_, MinMaxBounds p_i47511_3_) {
/*  86 */       super(ItemDurabilityTrigger.field_193159_a);
/*  87 */       this.field_193198_a = p_i47511_1_;
/*  88 */       this.field_193199_b = p_i47511_2_;
/*  89 */       this.field_193200_c = p_i47511_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193197_a(ItemStack p_193197_1_, int p_193197_2_) {
/*  94 */       if (!this.field_193198_a.func_192493_a(p_193197_1_))
/*     */       {
/*  96 */         return false;
/*     */       }
/*  98 */       if (!this.field_193199_b.func_192514_a((p_193197_1_.getMaxDamage() - p_193197_2_)))
/*     */       {
/* 100 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 104 */       return this.field_193200_c.func_192514_a((p_193197_1_.getItemDamage() - p_193197_2_));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_193442_a;
/* 112 */     private final Set<ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance>> field_193443_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47512_1_) {
/* 116 */       this.field_193442_a = p_i47512_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193439_a() {
/* 121 */       return this.field_193443_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193440_a(ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance> p_193440_1_) {
/* 126 */       this.field_193443_b.add(p_193440_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193438_b(ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance> p_193438_1_) {
/* 131 */       this.field_193443_b.remove(p_193438_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193441_a(ItemStack p_193441_1_, int p_193441_2_) {
/* 136 */       List<ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance>> list = null;
/*     */       
/* 138 */       for (ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance> listener : this.field_193443_b) {
/*     */         
/* 140 */         if (((ItemDurabilityTrigger.Instance)listener.func_192158_a()).func_193197_a(p_193441_1_, p_193441_2_)) {
/*     */           
/* 142 */           if (list == null)
/*     */           {
/* 144 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 147 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 151 */       if (list != null)
/*     */       {
/* 153 */         for (ICriterionTrigger.Listener<ItemDurabilityTrigger.Instance> listener1 : list)
/*     */         {
/* 155 */           listener1.func_192159_a(this.field_193442_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\ItemDurabilityTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */