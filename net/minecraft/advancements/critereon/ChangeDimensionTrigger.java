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
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.ICriterionInstance;
/*     */ import net.minecraft.advancements.ICriterionTrigger;
/*     */ import net.minecraft.advancements.PlayerAdvancements;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.DimensionType;
/*     */ 
/*     */ public class ChangeDimensionTrigger implements ICriterionTrigger<ChangeDimensionTrigger.Instance> {
/*  21 */   private static final ResourceLocation field_193144_a = new ResourceLocation("changed_dimension");
/*  22 */   private final Map<PlayerAdvancements, Listeners> field_193145_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  26 */     return field_193144_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  31 */     Listeners changedimensiontrigger$listeners = this.field_193145_b.get(p_192165_1_);
/*     */     
/*  33 */     if (changedimensiontrigger$listeners == null) {
/*     */       
/*  35 */       changedimensiontrigger$listeners = new Listeners(p_192165_1_);
/*  36 */       this.field_193145_b.put(p_192165_1_, changedimensiontrigger$listeners);
/*     */     } 
/*     */     
/*  39 */     changedimensiontrigger$listeners.func_193233_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  44 */     Listeners changedimensiontrigger$listeners = this.field_193145_b.get(p_192164_1_);
/*     */     
/*  46 */     if (changedimensiontrigger$listeners != null) {
/*     */       
/*  48 */       changedimensiontrigger$listeners.func_193231_b(p_192164_2_);
/*     */       
/*  50 */       if (changedimensiontrigger$listeners.func_193232_a())
/*     */       {
/*  52 */         this.field_193145_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  59 */     this.field_193145_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  64 */     DimensionType dimensiontype = p_192166_1_.has("from") ? DimensionType.func_193417_a(JsonUtils.getString(p_192166_1_, "from")) : null;
/*  65 */     DimensionType dimensiontype1 = p_192166_1_.has("to") ? DimensionType.func_193417_a(JsonUtils.getString(p_192166_1_, "to")) : null;
/*  66 */     return new Instance(dimensiontype, dimensiontype1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193143_a(EntityPlayerMP p_193143_1_, DimensionType p_193143_2_, DimensionType p_193143_3_) {
/*  71 */     Listeners changedimensiontrigger$listeners = this.field_193145_b.get(p_193143_1_.func_192039_O());
/*     */     
/*  73 */     if (changedimensiontrigger$listeners != null)
/*     */     {
/*  75 */       changedimensiontrigger$listeners.func_193234_a(p_193143_2_, p_193143_3_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     @Nullable
/*     */     private final DimensionType field_193191_a;
/*     */     @Nullable
/*     */     private final DimensionType field_193192_b;
/*     */     
/*     */     public Instance(@Nullable DimensionType p_i47475_1_, @Nullable DimensionType p_i47475_2_) {
/*  88 */       super(ChangeDimensionTrigger.field_193144_a);
/*  89 */       this.field_193191_a = p_i47475_1_;
/*  90 */       this.field_193192_b = p_i47475_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193190_a(DimensionType p_193190_1_, DimensionType p_193190_2_) {
/*  95 */       if (this.field_193191_a != null && this.field_193191_a != p_193190_1_)
/*     */       {
/*  97 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 101 */       return !(this.field_193192_b != null && this.field_193192_b != p_193190_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_193235_a;
/* 109 */     private final Set<ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance>> field_193236_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47476_1_) {
/* 113 */       this.field_193235_a = p_i47476_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193232_a() {
/* 118 */       return this.field_193236_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193233_a(ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance> p_193233_1_) {
/* 123 */       this.field_193236_b.add(p_193233_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193231_b(ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance> p_193231_1_) {
/* 128 */       this.field_193236_b.remove(p_193231_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193234_a(DimensionType p_193234_1_, DimensionType p_193234_2_) {
/* 133 */       List<ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance>> list = null;
/*     */       
/* 135 */       for (ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance> listener : this.field_193236_b) {
/*     */         
/* 137 */         if (((ChangeDimensionTrigger.Instance)listener.func_192158_a()).func_193190_a(p_193234_1_, p_193234_2_)) {
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
/* 150 */         for (ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance> listener1 : list)
/*     */         {
/* 152 */           listener1.func_192159_a(this.field_193235_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\ChangeDimensionTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */