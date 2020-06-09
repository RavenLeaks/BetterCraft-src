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
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class PlayerHurtEntityTrigger implements ICriterionTrigger<PlayerHurtEntityTrigger.Instance> {
/*  20 */   private static final ResourceLocation field_192222_a = new ResourceLocation("player_hurt_entity");
/*  21 */   private final Map<PlayerAdvancements, Listeners> field_192223_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  25 */     return field_192222_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  30 */     Listeners playerhurtentitytrigger$listeners = this.field_192223_b.get(p_192165_1_);
/*     */     
/*  32 */     if (playerhurtentitytrigger$listeners == null) {
/*     */       
/*  34 */       playerhurtentitytrigger$listeners = new Listeners(p_192165_1_);
/*  35 */       this.field_192223_b.put(p_192165_1_, playerhurtentitytrigger$listeners);
/*     */     } 
/*     */     
/*  38 */     playerhurtentitytrigger$listeners.func_192522_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  43 */     Listeners playerhurtentitytrigger$listeners = this.field_192223_b.get(p_192164_1_);
/*     */     
/*  45 */     if (playerhurtentitytrigger$listeners != null) {
/*     */       
/*  47 */       playerhurtentitytrigger$listeners.func_192519_b(p_192164_2_);
/*     */       
/*  49 */       if (playerhurtentitytrigger$listeners.func_192520_a())
/*     */       {
/*  51 */         this.field_192223_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  58 */     this.field_192223_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  63 */     DamagePredicate damagepredicate = DamagePredicate.func_192364_a(p_192166_1_.get("damage"));
/*  64 */     EntityPredicate entitypredicate = EntityPredicate.func_192481_a(p_192166_1_.get("entity"));
/*  65 */     return new Instance(damagepredicate, entitypredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192220_a(EntityPlayerMP p_192220_1_, Entity p_192220_2_, DamageSource p_192220_3_, float p_192220_4_, float p_192220_5_, boolean p_192220_6_) {
/*  70 */     Listeners playerhurtentitytrigger$listeners = this.field_192223_b.get(p_192220_1_.func_192039_O());
/*     */     
/*  72 */     if (playerhurtentitytrigger$listeners != null)
/*     */     {
/*  74 */       playerhurtentitytrigger$listeners.func_192521_a(p_192220_1_, p_192220_2_, p_192220_3_, p_192220_4_, p_192220_5_, p_192220_6_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final DamagePredicate field_192279_a;
/*     */     private final EntityPredicate field_192280_b;
/*     */     
/*     */     public Instance(DamagePredicate p_i47406_1_, EntityPredicate p_i47406_2_) {
/*  85 */       super(PlayerHurtEntityTrigger.field_192222_a);
/*  86 */       this.field_192279_a = p_i47406_1_;
/*  87 */       this.field_192280_b = p_i47406_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192278_a(EntityPlayerMP p_192278_1_, Entity p_192278_2_, DamageSource p_192278_3_, float p_192278_4_, float p_192278_5_, boolean p_192278_6_) {
/*  92 */       if (!this.field_192279_a.func_192365_a(p_192278_1_, p_192278_3_, p_192278_4_, p_192278_5_, p_192278_6_))
/*     */       {
/*  94 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  98 */       return this.field_192280_b.func_192482_a(p_192278_1_, p_192278_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192523_a;
/* 106 */     private final Set<ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance>> field_192524_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47407_1_) {
/* 110 */       this.field_192523_a = p_i47407_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192520_a() {
/* 115 */       return this.field_192524_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192522_a(ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance> p_192522_1_) {
/* 120 */       this.field_192524_b.add(p_192522_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192519_b(ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance> p_192519_1_) {
/* 125 */       this.field_192524_b.remove(p_192519_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192521_a(EntityPlayerMP p_192521_1_, Entity p_192521_2_, DamageSource p_192521_3_, float p_192521_4_, float p_192521_5_, boolean p_192521_6_) {
/* 130 */       List<ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance>> list = null;
/*     */       
/* 132 */       for (ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance> listener : this.field_192524_b) {
/*     */         
/* 134 */         if (((PlayerHurtEntityTrigger.Instance)listener.func_192158_a()).func_192278_a(p_192521_1_, p_192521_2_, p_192521_3_, p_192521_4_, p_192521_5_, p_192521_6_)) {
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
/* 147 */         for (ICriterionTrigger.Listener<PlayerHurtEntityTrigger.Instance> listener1 : list)
/*     */         {
/* 149 */           listener1.func_192159_a(this.field_192523_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\PlayerHurtEntityTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */