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
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class TameAnimalTrigger implements ICriterionTrigger<TameAnimalTrigger.Instance> {
/*  19 */   private static final ResourceLocation field_193179_a = new ResourceLocation("tame_animal");
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_193180_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  24 */     return field_193179_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  29 */     Listeners tameanimaltrigger$listeners = this.field_193180_b.get(p_192165_1_);
/*     */     
/*  31 */     if (tameanimaltrigger$listeners == null) {
/*     */       
/*  33 */       tameanimaltrigger$listeners = new Listeners(p_192165_1_);
/*  34 */       this.field_193180_b.put(p_192165_1_, tameanimaltrigger$listeners);
/*     */     } 
/*     */     
/*  37 */     tameanimaltrigger$listeners.func_193496_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  42 */     Listeners tameanimaltrigger$listeners = this.field_193180_b.get(p_192164_1_);
/*     */     
/*  44 */     if (tameanimaltrigger$listeners != null) {
/*     */       
/*  46 */       tameanimaltrigger$listeners.func_193494_b(p_192164_2_);
/*     */       
/*  48 */       if (tameanimaltrigger$listeners.func_193495_a())
/*     */       {
/*  50 */         this.field_193180_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  57 */     this.field_193180_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  62 */     EntityPredicate entitypredicate = EntityPredicate.func_192481_a(p_192166_1_.get("entity"));
/*  63 */     return new Instance(entitypredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193178_a(EntityPlayerMP p_193178_1_, EntityAnimal p_193178_2_) {
/*  68 */     Listeners tameanimaltrigger$listeners = this.field_193180_b.get(p_193178_1_.func_192039_O());
/*     */     
/*  70 */     if (tameanimaltrigger$listeners != null)
/*     */     {
/*  72 */       tameanimaltrigger$listeners.func_193497_a(p_193178_1_, p_193178_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final EntityPredicate field_193217_a;
/*     */     
/*     */     public Instance(EntityPredicate p_i47513_1_) {
/*  82 */       super(TameAnimalTrigger.field_193179_a);
/*  83 */       this.field_193217_a = p_i47513_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193216_a(EntityPlayerMP p_193216_1_, EntityAnimal p_193216_2_) {
/*  88 */       return this.field_193217_a.func_192482_a(p_193216_1_, (Entity)p_193216_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_193498_a;
/*  95 */     private final Set<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>> field_193499_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47514_1_) {
/*  99 */       this.field_193498_a = p_i47514_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193495_a() {
/* 104 */       return this.field_193499_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193496_a(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> p_193496_1_) {
/* 109 */       this.field_193499_b.add(p_193496_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193494_b(ICriterionTrigger.Listener<TameAnimalTrigger.Instance> p_193494_1_) {
/* 114 */       this.field_193499_b.remove(p_193494_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193497_a(EntityPlayerMP p_193497_1_, EntityAnimal p_193497_2_) {
/* 119 */       List<ICriterionTrigger.Listener<TameAnimalTrigger.Instance>> list = null;
/*     */       
/* 121 */       for (ICriterionTrigger.Listener<TameAnimalTrigger.Instance> listener : this.field_193499_b) {
/*     */         
/* 123 */         if (((TameAnimalTrigger.Instance)listener.func_192158_a()).func_193216_a(p_193497_1_, p_193497_2_)) {
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
/* 136 */         for (ICriterionTrigger.Listener<TameAnimalTrigger.Instance> listener1 : list)
/*     */         {
/* 138 */           listener1.func_192159_a(this.field_193498_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\TameAnimalTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */