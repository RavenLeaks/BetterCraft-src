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
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class EffectsChangedTrigger implements ICriterionTrigger<EffectsChangedTrigger.Instance> {
/*  18 */   private static final ResourceLocation field_193154_a = new ResourceLocation("effects_changed");
/*  19 */   private final Map<PlayerAdvancements, Listeners> field_193155_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  23 */     return field_193154_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  28 */     Listeners effectschangedtrigger$listeners = this.field_193155_b.get(p_192165_1_);
/*     */     
/*  30 */     if (effectschangedtrigger$listeners == null) {
/*     */       
/*  32 */       effectschangedtrigger$listeners = new Listeners(p_192165_1_);
/*  33 */       this.field_193155_b.put(p_192165_1_, effectschangedtrigger$listeners);
/*     */     } 
/*     */     
/*  36 */     effectschangedtrigger$listeners.func_193431_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  41 */     Listeners effectschangedtrigger$listeners = this.field_193155_b.get(p_192164_1_);
/*     */     
/*  43 */     if (effectschangedtrigger$listeners != null) {
/*     */       
/*  45 */       effectschangedtrigger$listeners.func_193429_b(p_192164_2_);
/*     */       
/*  47 */       if (effectschangedtrigger$listeners.func_193430_a())
/*     */       {
/*  49 */         this.field_193155_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  56 */     this.field_193155_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  61 */     MobEffectsPredicate mobeffectspredicate = MobEffectsPredicate.func_193471_a(p_192166_1_.get("effects"));
/*  62 */     return new Instance(mobeffectspredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193153_a(EntityPlayerMP p_193153_1_) {
/*  67 */     Listeners effectschangedtrigger$listeners = this.field_193155_b.get(p_193153_1_.func_192039_O());
/*     */     
/*  69 */     if (effectschangedtrigger$listeners != null)
/*     */     {
/*  71 */       effectschangedtrigger$listeners.func_193432_a(p_193153_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final MobEffectsPredicate field_193196_a;
/*     */     
/*     */     public Instance(MobEffectsPredicate p_i47545_1_) {
/*  81 */       super(EffectsChangedTrigger.field_193154_a);
/*  82 */       this.field_193196_a = p_i47545_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193195_a(EntityPlayerMP p_193195_1_) {
/*  87 */       return this.field_193196_a.func_193472_a((EntityLivingBase)p_193195_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_193433_a;
/*  94 */     private final Set<ICriterionTrigger.Listener<EffectsChangedTrigger.Instance>> field_193434_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47546_1_) {
/*  98 */       this.field_193433_a = p_i47546_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193430_a() {
/* 103 */       return this.field_193434_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193431_a(ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> p_193431_1_) {
/* 108 */       this.field_193434_b.add(p_193431_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193429_b(ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> p_193429_1_) {
/* 113 */       this.field_193434_b.remove(p_193429_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193432_a(EntityPlayerMP p_193432_1_) {
/* 118 */       List<ICriterionTrigger.Listener<EffectsChangedTrigger.Instance>> list = null;
/*     */       
/* 120 */       for (ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> listener : this.field_193434_b) {
/*     */         
/* 122 */         if (((EffectsChangedTrigger.Instance)listener.func_192158_a()).func_193195_a(p_193432_1_)) {
/*     */           
/* 124 */           if (list == null)
/*     */           {
/* 126 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 129 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 133 */       if (list != null)
/*     */       {
/* 135 */         for (ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> listener1 : list)
/*     */         {
/* 137 */           listener1.func_192159_a(this.field_193433_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\EffectsChangedTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */