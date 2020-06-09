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
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class SummonedEntityTrigger implements ICriterionTrigger<SummonedEntityTrigger.Instance> {
/*  19 */   private static final ResourceLocation field_192232_a = new ResourceLocation("summoned_entity");
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_192233_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  24 */     return field_192232_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  29 */     Listeners summonedentitytrigger$listeners = this.field_192233_b.get(p_192165_1_);
/*     */     
/*  31 */     if (summonedentitytrigger$listeners == null) {
/*     */       
/*  33 */       summonedentitytrigger$listeners = new Listeners(p_192165_1_);
/*  34 */       this.field_192233_b.put(p_192165_1_, summonedentitytrigger$listeners);
/*     */     } 
/*     */     
/*  37 */     summonedentitytrigger$listeners.func_192534_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  42 */     Listeners summonedentitytrigger$listeners = this.field_192233_b.get(p_192164_1_);
/*     */     
/*  44 */     if (summonedentitytrigger$listeners != null) {
/*     */       
/*  46 */       summonedentitytrigger$listeners.func_192531_b(p_192164_2_);
/*     */       
/*  48 */       if (summonedentitytrigger$listeners.func_192532_a())
/*     */       {
/*  50 */         this.field_192233_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  57 */     this.field_192233_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  62 */     EntityPredicate entitypredicate = EntityPredicate.func_192481_a(p_192166_1_.get("entity"));
/*  63 */     return new Instance(entitypredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192229_a(EntityPlayerMP p_192229_1_, Entity p_192229_2_) {
/*  68 */     Listeners summonedentitytrigger$listeners = this.field_192233_b.get(p_192229_1_.func_192039_O());
/*     */     
/*  70 */     if (summonedentitytrigger$listeners != null)
/*     */     {
/*  72 */       summonedentitytrigger$listeners.func_192533_a(p_192229_1_, p_192229_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final EntityPredicate field_192284_a;
/*     */     
/*     */     public Instance(EntityPredicate p_i47371_1_) {
/*  82 */       super(SummonedEntityTrigger.field_192232_a);
/*  83 */       this.field_192284_a = p_i47371_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192283_a(EntityPlayerMP p_192283_1_, Entity p_192283_2_) {
/*  88 */       return this.field_192284_a.func_192482_a(p_192283_1_, p_192283_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192535_a;
/*  95 */     private final Set<ICriterionTrigger.Listener<SummonedEntityTrigger.Instance>> field_192536_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47372_1_) {
/*  99 */       this.field_192535_a = p_i47372_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192532_a() {
/* 104 */       return this.field_192536_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192534_a(ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> p_192534_1_) {
/* 109 */       this.field_192536_b.add(p_192534_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192531_b(ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> p_192531_1_) {
/* 114 */       this.field_192536_b.remove(p_192531_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192533_a(EntityPlayerMP p_192533_1_, Entity p_192533_2_) {
/* 119 */       List<ICriterionTrigger.Listener<SummonedEntityTrigger.Instance>> list = null;
/*     */       
/* 121 */       for (ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> listener : this.field_192536_b) {
/*     */         
/* 123 */         if (((SummonedEntityTrigger.Instance)listener.func_192158_a()).func_192283_a(p_192533_1_, p_192533_2_)) {
/*     */           
/* 125 */           if (list == null)
/*     */           {
/* 127 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 130 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 134 */       if (list != null)
/*     */       {
/* 136 */         for (ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> listener1 : list)
/*     */         {
/* 138 */           listener1.func_192159_a(this.field_192535_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\SummonedEntityTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */