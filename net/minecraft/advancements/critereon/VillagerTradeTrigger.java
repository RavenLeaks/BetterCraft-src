/*     */ package net.minecraft.advancements.critereon;
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
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class VillagerTradeTrigger implements ICriterionTrigger<VillagerTradeTrigger.Instance> {
/*  20 */   private static final ResourceLocation field_192237_a = new ResourceLocation("villager_trade");
/*  21 */   private final Map<PlayerAdvancements, Listeners> field_192238_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  25 */     return field_192237_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  30 */     Listeners villagertradetrigger$listeners = this.field_192238_b.get(p_192165_1_);
/*     */     
/*  32 */     if (villagertradetrigger$listeners == null) {
/*     */       
/*  34 */       villagertradetrigger$listeners = new Listeners(p_192165_1_);
/*  35 */       this.field_192238_b.put(p_192165_1_, villagertradetrigger$listeners);
/*     */     } 
/*     */     
/*  38 */     villagertradetrigger$listeners.func_192540_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  43 */     Listeners villagertradetrigger$listeners = this.field_192238_b.get(p_192164_1_);
/*     */     
/*  45 */     if (villagertradetrigger$listeners != null) {
/*     */       
/*  47 */       villagertradetrigger$listeners.func_192538_b(p_192164_2_);
/*     */       
/*  49 */       if (villagertradetrigger$listeners.func_192539_a())
/*     */       {
/*  51 */         this.field_192238_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  58 */     this.field_192238_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  63 */     EntityPredicate entitypredicate = EntityPredicate.func_192481_a(p_192166_1_.get("villager"));
/*  64 */     ItemPredicate itempredicate = ItemPredicate.func_192492_a(p_192166_1_.get("item"));
/*  65 */     return new Instance(entitypredicate, itempredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192234_a(EntityPlayerMP p_192234_1_, EntityVillager p_192234_2_, ItemStack p_192234_3_) {
/*  70 */     Listeners villagertradetrigger$listeners = this.field_192238_b.get(p_192234_1_.func_192039_O());
/*     */     
/*  72 */     if (villagertradetrigger$listeners != null)
/*     */     {
/*  74 */       villagertradetrigger$listeners.func_192537_a(p_192234_1_, p_192234_2_, p_192234_3_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final EntityPredicate field_192286_a;
/*     */     private final ItemPredicate field_192287_b;
/*     */     
/*     */     public Instance(EntityPredicate p_i47457_1_, ItemPredicate p_i47457_2_) {
/*  85 */       super(VillagerTradeTrigger.field_192237_a);
/*  86 */       this.field_192286_a = p_i47457_1_;
/*  87 */       this.field_192287_b = p_i47457_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192285_a(EntityPlayerMP p_192285_1_, EntityVillager p_192285_2_, ItemStack p_192285_3_) {
/*  92 */       if (!this.field_192286_a.func_192482_a(p_192285_1_, (Entity)p_192285_2_))
/*     */       {
/*  94 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  98 */       return this.field_192287_b.func_192493_a(p_192285_3_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192541_a;
/* 106 */     private final Set<ICriterionTrigger.Listener<VillagerTradeTrigger.Instance>> field_192542_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47458_1_) {
/* 110 */       this.field_192541_a = p_i47458_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192539_a() {
/* 115 */       return this.field_192542_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192540_a(ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> p_192540_1_) {
/* 120 */       this.field_192542_b.add(p_192540_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192538_b(ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> p_192538_1_) {
/* 125 */       this.field_192542_b.remove(p_192538_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192537_a(EntityPlayerMP p_192537_1_, EntityVillager p_192537_2_, ItemStack p_192537_3_) {
/* 130 */       List<ICriterionTrigger.Listener<VillagerTradeTrigger.Instance>> list = null;
/*     */       
/* 132 */       for (ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> listener : this.field_192542_b) {
/*     */         
/* 134 */         if (((VillagerTradeTrigger.Instance)listener.func_192158_a()).func_192285_a(p_192537_1_, p_192537_2_, p_192537_3_)) {
/*     */           
/* 136 */           if (list == null)
/*     */           {
/* 138 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 141 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       if (list != null)
/*     */       {
/* 147 */         for (ICriterionTrigger.Listener<VillagerTradeTrigger.Instance> listener1 : list)
/*     */         {
/* 149 */           listener1.func_192159_a(this.field_192541_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\VillagerTradeTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */