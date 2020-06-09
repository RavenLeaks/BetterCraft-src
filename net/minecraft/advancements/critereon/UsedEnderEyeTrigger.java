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
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class UsedEnderEyeTrigger implements ICriterionTrigger<UsedEnderEyeTrigger.Instance> {
/*  19 */   private static final ResourceLocation field_192242_a = new ResourceLocation("used_ender_eye");
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_192243_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  24 */     return field_192242_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  29 */     Listeners usedendereyetrigger$listeners = this.field_192243_b.get(p_192165_1_);
/*     */     
/*  31 */     if (usedendereyetrigger$listeners == null) {
/*     */       
/*  33 */       usedendereyetrigger$listeners = new Listeners(p_192165_1_);
/*  34 */       this.field_192243_b.put(p_192165_1_, usedendereyetrigger$listeners);
/*     */     } 
/*     */     
/*  37 */     usedendereyetrigger$listeners.func_192546_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  42 */     Listeners usedendereyetrigger$listeners = this.field_192243_b.get(p_192164_1_);
/*     */     
/*  44 */     if (usedendereyetrigger$listeners != null) {
/*     */       
/*  46 */       usedendereyetrigger$listeners.func_192544_b(p_192164_2_);
/*     */       
/*  48 */       if (usedendereyetrigger$listeners.func_192545_a())
/*     */       {
/*  50 */         this.field_192243_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  57 */     this.field_192243_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  62 */     MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(p_192166_1_.get("distance"));
/*  63 */     return new Instance(minmaxbounds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192239_a(EntityPlayerMP p_192239_1_, BlockPos p_192239_2_) {
/*  68 */     Listeners usedendereyetrigger$listeners = this.field_192243_b.get(p_192239_1_.func_192039_O());
/*     */     
/*  70 */     if (usedendereyetrigger$listeners != null) {
/*     */       
/*  72 */       double d0 = p_192239_1_.posX - p_192239_2_.getX();
/*  73 */       double d1 = p_192239_1_.posZ - p_192239_2_.getZ();
/*  74 */       usedendereyetrigger$listeners.func_192543_a(d0 * d0 + d1 * d1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final MinMaxBounds field_192289_a;
/*     */     
/*     */     public Instance(MinMaxBounds p_i47449_1_) {
/*  84 */       super(UsedEnderEyeTrigger.field_192242_a);
/*  85 */       this.field_192289_a = p_i47449_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192288_a(double p_192288_1_) {
/*  90 */       return this.field_192289_a.func_192513_a(p_192288_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_192547_a;
/*  97 */     private final Set<ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance>> field_192548_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47450_1_) {
/* 101 */       this.field_192547_a = p_i47450_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192545_a() {
/* 106 */       return this.field_192548_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192546_a(ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> p_192546_1_) {
/* 111 */       this.field_192548_b.add(p_192546_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192544_b(ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> p_192544_1_) {
/* 116 */       this.field_192548_b.remove(p_192544_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192543_a(double p_192543_1_) {
/* 121 */       List<ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance>> list = null;
/*     */       
/* 123 */       for (ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> listener : this.field_192548_b) {
/*     */         
/* 125 */         if (((UsedEnderEyeTrigger.Instance)listener.func_192158_a()).func_192288_a(p_192543_1_)) {
/*     */           
/* 127 */           if (list == null)
/*     */           {
/* 129 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 132 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 136 */       if (list != null)
/*     */       {
/* 138 */         for (ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> listener1 : list)
/*     */         {
/* 140 */           listener1.func_192159_a(this.field_192547_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\UsedEnderEyeTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */