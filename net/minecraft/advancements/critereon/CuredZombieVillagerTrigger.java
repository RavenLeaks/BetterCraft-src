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
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class CuredZombieVillagerTrigger implements ICriterionTrigger<CuredZombieVillagerTrigger.Instance> {
/*  20 */   private static final ResourceLocation field_192186_a = new ResourceLocation("cured_zombie_villager");
/*  21 */   private final Map<PlayerAdvancements, Listeners> field_192187_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  25 */     return field_192186_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  30 */     Listeners curedzombievillagertrigger$listeners = this.field_192187_b.get(p_192165_1_);
/*     */     
/*  32 */     if (curedzombievillagertrigger$listeners == null) {
/*     */       
/*  34 */       curedzombievillagertrigger$listeners = new Listeners(p_192165_1_);
/*  35 */       this.field_192187_b.put(p_192165_1_, curedzombievillagertrigger$listeners);
/*     */     } 
/*     */     
/*  38 */     curedzombievillagertrigger$listeners.func_192360_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  43 */     Listeners curedzombievillagertrigger$listeners = this.field_192187_b.get(p_192164_1_);
/*     */     
/*  45 */     if (curedzombievillagertrigger$listeners != null) {
/*     */       
/*  47 */       curedzombievillagertrigger$listeners.func_192358_b(p_192164_2_);
/*     */       
/*  49 */       if (curedzombievillagertrigger$listeners.func_192359_a())
/*     */       {
/*  51 */         this.field_192187_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  58 */     this.field_192187_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  63 */     EntityPredicate entitypredicate = EntityPredicate.func_192481_a(p_192166_1_.get("zombie"));
/*  64 */     EntityPredicate entitypredicate1 = EntityPredicate.func_192481_a(p_192166_1_.get("villager"));
/*  65 */     return new Instance(entitypredicate, entitypredicate1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192183_a(EntityPlayerMP p_192183_1_, EntityZombie p_192183_2_, EntityVillager p_192183_3_) {
/*  70 */     Listeners curedzombievillagertrigger$listeners = this.field_192187_b.get(p_192183_1_.func_192039_O());
/*     */     
/*  72 */     if (curedzombievillagertrigger$listeners != null)
/*     */     {
/*  74 */       curedzombievillagertrigger$listeners.func_192361_a(p_192183_1_, p_192183_2_, p_192183_3_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final EntityPredicate field_192255_a;
/*     */     private final EntityPredicate field_192256_b;
/*     */     
/*     */     public Instance(EntityPredicate p_i47459_1_, EntityPredicate p_i47459_2_) {
/*  85 */       super(CuredZombieVillagerTrigger.field_192186_a);
/*  86 */       this.field_192255_a = p_i47459_1_;
/*  87 */       this.field_192256_b = p_i47459_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192254_a(EntityPlayerMP p_192254_1_, EntityZombie p_192254_2_, EntityVillager p_192254_3_) {
/*  92 */       if (!this.field_192255_a.func_192482_a(p_192254_1_, (Entity)p_192254_2_))
/*     */       {
/*  94 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  98 */       return this.field_192256_b.func_192482_a(p_192254_1_, (Entity)p_192254_3_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192362_a;
/* 106 */     private final Set<ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance>> field_192363_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47460_1_) {
/* 110 */       this.field_192362_a = p_i47460_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192359_a() {
/* 115 */       return this.field_192363_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192360_a(ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance> p_192360_1_) {
/* 120 */       this.field_192363_b.add(p_192360_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192358_b(ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance> p_192358_1_) {
/* 125 */       this.field_192363_b.remove(p_192358_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192361_a(EntityPlayerMP p_192361_1_, EntityZombie p_192361_2_, EntityVillager p_192361_3_) {
/* 130 */       List<ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance>> list = null;
/*     */       
/* 132 */       for (ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance> listener : this.field_192363_b) {
/*     */         
/* 134 */         if (((CuredZombieVillagerTrigger.Instance)listener.func_192158_a()).func_192254_a(p_192361_1_, p_192361_2_, p_192361_3_)) {
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
/* 147 */         for (ICriterionTrigger.Listener<CuredZombieVillagerTrigger.Instance> listener1 : list)
/*     */         {
/* 149 */           listener1.func_192159_a(this.field_192362_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\CuredZombieVillagerTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */