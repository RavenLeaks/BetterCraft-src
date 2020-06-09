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
/*     */ public class ConsumeItemTrigger implements ICriterionTrigger<ConsumeItemTrigger.Instance> {
/*  19 */   private static final ResourceLocation field_193149_a = new ResourceLocation("consume_item");
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_193150_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  24 */     return field_193149_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  29 */     Listeners consumeitemtrigger$listeners = this.field_193150_b.get(p_192165_1_);
/*     */     
/*  31 */     if (consumeitemtrigger$listeners == null) {
/*     */       
/*  33 */       consumeitemtrigger$listeners = new Listeners(p_192165_1_);
/*  34 */       this.field_193150_b.put(p_192165_1_, consumeitemtrigger$listeners);
/*     */     } 
/*     */     
/*  37 */     consumeitemtrigger$listeners.func_193239_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  42 */     Listeners consumeitemtrigger$listeners = this.field_193150_b.get(p_192164_1_);
/*     */     
/*  44 */     if (consumeitemtrigger$listeners != null) {
/*     */       
/*  46 */       consumeitemtrigger$listeners.func_193237_b(p_192164_2_);
/*     */       
/*  48 */       if (consumeitemtrigger$listeners.func_193238_a())
/*     */       {
/*  50 */         this.field_193150_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  57 */     this.field_193150_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  62 */     ItemPredicate itempredicate = ItemPredicate.func_192492_a(p_192166_1_.get("item"));
/*  63 */     return new Instance(itempredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193148_a(EntityPlayerMP p_193148_1_, ItemStack p_193148_2_) {
/*  68 */     Listeners consumeitemtrigger$listeners = this.field_193150_b.get(p_193148_1_.func_192039_O());
/*     */     
/*  70 */     if (consumeitemtrigger$listeners != null)
/*     */     {
/*  72 */       consumeitemtrigger$listeners.func_193240_a(p_193148_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final ItemPredicate field_193194_a;
/*     */     
/*     */     public Instance(ItemPredicate p_i47562_1_) {
/*  82 */       super(ConsumeItemTrigger.field_193149_a);
/*  83 */       this.field_193194_a = p_i47562_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193193_a(ItemStack p_193193_1_) {
/*  88 */       return this.field_193194_a.func_192493_a(p_193193_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_193241_a;
/*  95 */     private final Set<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>> field_193242_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47563_1_) {
/*  99 */       this.field_193241_a = p_i47563_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193238_a() {
/* 104 */       return this.field_193242_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193239_a(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> p_193239_1_) {
/* 109 */       this.field_193242_b.add(p_193239_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193237_b(ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> p_193237_1_) {
/* 114 */       this.field_193242_b.remove(p_193237_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193240_a(ItemStack p_193240_1_) {
/* 119 */       List<ICriterionTrigger.Listener<ConsumeItemTrigger.Instance>> list = null;
/*     */       
/* 121 */       for (ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> listener : this.field_193242_b) {
/*     */         
/* 123 */         if (((ConsumeItemTrigger.Instance)listener.func_192158_a()).func_193193_a(p_193240_1_)) {
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
/* 136 */         for (ICriterionTrigger.Listener<ConsumeItemTrigger.Instance> listener1 : list)
/*     */         {
/* 138 */           listener1.func_192159_a(this.field_193241_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\ConsumeItemTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */