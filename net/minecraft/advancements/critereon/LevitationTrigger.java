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
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class LevitationTrigger implements ICriterionTrigger<LevitationTrigger.Instance> {
/*  19 */   private static final ResourceLocation field_193164_a = new ResourceLocation("levitation");
/*  20 */   private final Map<PlayerAdvancements, Listeners> field_193165_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  24 */     return field_193164_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  29 */     Listeners levitationtrigger$listeners = this.field_193165_b.get(p_192165_1_);
/*     */     
/*  31 */     if (levitationtrigger$listeners == null) {
/*     */       
/*  33 */       levitationtrigger$listeners = new Listeners(p_192165_1_);
/*  34 */       this.field_193165_b.put(p_192165_1_, levitationtrigger$listeners);
/*     */     } 
/*     */     
/*  37 */     levitationtrigger$listeners.func_193449_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  42 */     Listeners levitationtrigger$listeners = this.field_193165_b.get(p_192164_1_);
/*     */     
/*  44 */     if (levitationtrigger$listeners != null) {
/*     */       
/*  46 */       levitationtrigger$listeners.func_193446_b(p_192164_2_);
/*     */       
/*  48 */       if (levitationtrigger$listeners.func_193447_a())
/*     */       {
/*  50 */         this.field_193165_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  57 */     this.field_193165_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  62 */     DistancePredicate distancepredicate = DistancePredicate.func_193421_a(p_192166_1_.get("distance"));
/*  63 */     MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(p_192166_1_.get("duration"));
/*  64 */     return new Instance(distancepredicate, minmaxbounds);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193162_a(EntityPlayerMP p_193162_1_, Vec3d p_193162_2_, int p_193162_3_) {
/*  69 */     Listeners levitationtrigger$listeners = this.field_193165_b.get(p_193162_1_.func_192039_O());
/*     */     
/*  71 */     if (levitationtrigger$listeners != null)
/*     */     {
/*  73 */       levitationtrigger$listeners.func_193448_a(p_193162_1_, p_193162_2_, p_193162_3_);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final DistancePredicate field_193202_a;
/*     */     private final MinMaxBounds field_193203_b;
/*     */     
/*     */     public Instance(DistancePredicate p_i47571_1_, MinMaxBounds p_i47571_2_) {
/*  84 */       super(LevitationTrigger.field_193164_a);
/*  85 */       this.field_193202_a = p_i47571_1_;
/*  86 */       this.field_193203_b = p_i47571_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193201_a(EntityPlayerMP p_193201_1_, Vec3d p_193201_2_, int p_193201_3_) {
/*  91 */       if (!this.field_193202_a.func_193422_a(p_193201_2_.xCoord, p_193201_2_.yCoord, p_193201_2_.zCoord, p_193201_1_.posX, p_193201_1_.posY, p_193201_1_.posZ))
/*     */       {
/*  93 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  97 */       return this.field_193203_b.func_192514_a(p_193201_3_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_193450_a;
/* 105 */     private final Set<ICriterionTrigger.Listener<LevitationTrigger.Instance>> field_193451_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47572_1_) {
/* 109 */       this.field_193450_a = p_i47572_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193447_a() {
/* 114 */       return this.field_193451_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193449_a(ICriterionTrigger.Listener<LevitationTrigger.Instance> p_193449_1_) {
/* 119 */       this.field_193451_b.add(p_193449_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193446_b(ICriterionTrigger.Listener<LevitationTrigger.Instance> p_193446_1_) {
/* 124 */       this.field_193451_b.remove(p_193446_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193448_a(EntityPlayerMP p_193448_1_, Vec3d p_193448_2_, int p_193448_3_) {
/* 129 */       List<ICriterionTrigger.Listener<LevitationTrigger.Instance>> list = null;
/*     */       
/* 131 */       for (ICriterionTrigger.Listener<LevitationTrigger.Instance> listener : this.field_193451_b) {
/*     */         
/* 133 */         if (((LevitationTrigger.Instance)listener.func_192158_a()).func_193201_a(p_193448_1_, p_193448_2_, p_193448_3_)) {
/*     */           
/* 135 */           if (list == null)
/*     */           {
/* 137 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 140 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 144 */       if (list != null)
/*     */       {
/* 146 */         for (ICriterionTrigger.Listener<LevitationTrigger.Instance> listener1 : list)
/*     */         {
/* 148 */           listener1.func_192159_a(this.field_193450_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\LevitationTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */