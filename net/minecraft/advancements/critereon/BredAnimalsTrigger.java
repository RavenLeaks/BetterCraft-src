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
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class BredAnimalsTrigger implements ICriterionTrigger<BredAnimalsTrigger.Instance> {
/*  20 */   private static final ResourceLocation field_192171_a = new ResourceLocation("bred_animals");
/*  21 */   private final Map<PlayerAdvancements, Listeners> field_192172_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  25 */     return field_192171_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  30 */     Listeners bredanimalstrigger$listeners = this.field_192172_b.get(p_192165_1_);
/*     */     
/*  32 */     if (bredanimalstrigger$listeners == null) {
/*     */       
/*  34 */       bredanimalstrigger$listeners = new Listeners(p_192165_1_);
/*  35 */       this.field_192172_b.put(p_192165_1_, bredanimalstrigger$listeners);
/*     */     } 
/*     */     
/*  38 */     bredanimalstrigger$listeners.func_192343_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  43 */     Listeners bredanimalstrigger$listeners = this.field_192172_b.get(p_192164_1_);
/*     */     
/*  45 */     if (bredanimalstrigger$listeners != null) {
/*     */       
/*  47 */       bredanimalstrigger$listeners.func_192340_b(p_192164_2_);
/*     */       
/*  49 */       if (bredanimalstrigger$listeners.func_192341_a())
/*     */       {
/*  51 */         this.field_192172_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  58 */     this.field_192172_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  63 */     EntityPredicate entitypredicate = EntityPredicate.func_192481_a(p_192166_1_.get("parent"));
/*  64 */     EntityPredicate entitypredicate1 = EntityPredicate.func_192481_a(p_192166_1_.get("partner"));
/*  65 */     EntityPredicate entitypredicate2 = EntityPredicate.func_192481_a(p_192166_1_.get("child"));
/*  66 */     return new Instance(entitypredicate, entitypredicate1, entitypredicate2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192168_a(EntityPlayerMP p_192168_1_, EntityAnimal p_192168_2_, EntityAnimal p_192168_3_, EntityAgeable p_192168_4_) {
/*  71 */     Listeners bredanimalstrigger$listeners = this.field_192172_b.get(p_192168_1_.func_192039_O());
/*     */     
/*  73 */     if (bredanimalstrigger$listeners != null)
/*     */     {
/*  75 */       bredanimalstrigger$listeners.func_192342_a(p_192168_1_, p_192168_2_, p_192168_3_, p_192168_4_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final EntityPredicate field_192247_a;
/*     */     private final EntityPredicate field_192248_b;
/*     */     private final EntityPredicate field_192249_c;
/*     */     
/*     */     public Instance(EntityPredicate p_i47408_1_, EntityPredicate p_i47408_2_, EntityPredicate p_i47408_3_) {
/*  87 */       super(BredAnimalsTrigger.field_192171_a);
/*  88 */       this.field_192247_a = p_i47408_1_;
/*  89 */       this.field_192248_b = p_i47408_2_;
/*  90 */       this.field_192249_c = p_i47408_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192246_a(EntityPlayerMP p_192246_1_, EntityAnimal p_192246_2_, EntityAnimal p_192246_3_, EntityAgeable p_192246_4_) {
/*  95 */       if (!this.field_192249_c.func_192482_a(p_192246_1_, (Entity)p_192246_4_))
/*     */       {
/*  97 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 101 */       return !((!this.field_192247_a.func_192482_a(p_192246_1_, (Entity)p_192246_2_) || !this.field_192248_b.func_192482_a(p_192246_1_, (Entity)p_192246_3_)) && (!this.field_192247_a.func_192482_a(p_192246_1_, (Entity)p_192246_3_) || !this.field_192248_b.func_192482_a(p_192246_1_, (Entity)p_192246_2_)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192344_a;
/* 109 */     private final Set<ICriterionTrigger.Listener<BredAnimalsTrigger.Instance>> field_192345_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47409_1_) {
/* 113 */       this.field_192344_a = p_i47409_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192341_a() {
/* 118 */       return this.field_192345_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192343_a(ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> p_192343_1_) {
/* 123 */       this.field_192345_b.add(p_192343_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192340_b(ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> p_192340_1_) {
/* 128 */       this.field_192345_b.remove(p_192340_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192342_a(EntityPlayerMP p_192342_1_, EntityAnimal p_192342_2_, EntityAnimal p_192342_3_, EntityAgeable p_192342_4_) {
/* 133 */       List<ICriterionTrigger.Listener<BredAnimalsTrigger.Instance>> list = null;
/*     */       
/* 135 */       for (ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> listener : this.field_192345_b) {
/*     */         
/* 137 */         if (((BredAnimalsTrigger.Instance)listener.func_192158_a()).func_192246_a(p_192342_1_, p_192342_2_, p_192342_3_, p_192342_4_)) {
/*     */           
/* 139 */           if (list == null)
/*     */           {
/* 141 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 144 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 148 */       if (list != null)
/*     */       {
/* 150 */         for (ICriterionTrigger.Listener<BredAnimalsTrigger.Instance> listener1 : list)
/*     */         {
/* 152 */           listener1.func_192159_a(this.field_192344_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\BredAnimalsTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */