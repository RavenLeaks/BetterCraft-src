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
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class EntityHurtPlayerTrigger implements ICriterionTrigger<EntityHurtPlayerTrigger.Instance> {
/*  19 */   private static final ResourceLocation field_192201_a = new ResourceLocation("entity_hurt_player");
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_192202_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  24 */     return field_192201_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  29 */     Listeners entityhurtplayertrigger$listeners = this.field_192202_b.get(p_192165_1_);
/*     */     
/*  31 */     if (entityhurtplayertrigger$listeners == null) {
/*     */       
/*  33 */       entityhurtplayertrigger$listeners = new Listeners(p_192165_1_);
/*  34 */       this.field_192202_b.put(p_192165_1_, entityhurtplayertrigger$listeners);
/*     */     } 
/*     */     
/*  37 */     entityhurtplayertrigger$listeners.func_192477_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  42 */     Listeners entityhurtplayertrigger$listeners = this.field_192202_b.get(p_192164_1_);
/*     */     
/*  44 */     if (entityhurtplayertrigger$listeners != null) {
/*     */       
/*  46 */       entityhurtplayertrigger$listeners.func_192475_b(p_192164_2_);
/*     */       
/*  48 */       if (entityhurtplayertrigger$listeners.func_192476_a())
/*     */       {
/*  50 */         this.field_192202_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  57 */     this.field_192202_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  62 */     DamagePredicate damagepredicate = DamagePredicate.func_192364_a(p_192166_1_.get("damage"));
/*  63 */     return new Instance(damagepredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192200_a(EntityPlayerMP p_192200_1_, DamageSource p_192200_2_, float p_192200_3_, float p_192200_4_, boolean p_192200_5_) {
/*  68 */     Listeners entityhurtplayertrigger$listeners = this.field_192202_b.get(p_192200_1_.func_192039_O());
/*     */     
/*  70 */     if (entityhurtplayertrigger$listeners != null)
/*     */     {
/*  72 */       entityhurtplayertrigger$listeners.func_192478_a(p_192200_1_, p_192200_2_, p_192200_3_, p_192200_4_, p_192200_5_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final DamagePredicate field_192264_a;
/*     */     
/*     */     public Instance(DamagePredicate p_i47438_1_) {
/*  82 */       super(EntityHurtPlayerTrigger.field_192201_a);
/*  83 */       this.field_192264_a = p_i47438_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192263_a(EntityPlayerMP p_192263_1_, DamageSource p_192263_2_, float p_192263_3_, float p_192263_4_, boolean p_192263_5_) {
/*  88 */       return this.field_192264_a.func_192365_a(p_192263_1_, p_192263_2_, p_192263_3_, p_192263_4_, p_192263_5_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192479_a;
/*  95 */     private final Set<ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance>> field_192480_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47439_1_) {
/*  99 */       this.field_192479_a = p_i47439_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192476_a() {
/* 104 */       return this.field_192480_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192477_a(ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> p_192477_1_) {
/* 109 */       this.field_192480_b.add(p_192477_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192475_b(ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> p_192475_1_) {
/* 114 */       this.field_192480_b.remove(p_192475_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192478_a(EntityPlayerMP p_192478_1_, DamageSource p_192478_2_, float p_192478_3_, float p_192478_4_, boolean p_192478_5_) {
/* 119 */       List<ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance>> list = null;
/*     */       
/* 121 */       for (ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> listener : this.field_192480_b) {
/*     */         
/* 123 */         if (((EntityHurtPlayerTrigger.Instance)listener.func_192158_a()).func_192263_a(p_192478_1_, p_192478_2_, p_192478_3_, p_192478_4_, p_192478_5_)) {
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
/* 136 */         for (ICriterionTrigger.Listener<EntityHurtPlayerTrigger.Instance> listener1 : list)
/*     */         {
/* 138 */           listener1.func_192159_a(this.field_192479_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\EntityHurtPlayerTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */