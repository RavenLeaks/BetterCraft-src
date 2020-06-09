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
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class NetherTravelTrigger implements ICriterionTrigger<NetherTravelTrigger.Instance> {
/*  20 */   private static final ResourceLocation field_193169_a = new ResourceLocation("nether_travel");
/*  21 */   private final Map<PlayerAdvancements, Listeners> field_193170_b = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public ResourceLocation func_192163_a() {
/*  25 */     return field_193169_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {
/*  30 */     Listeners nethertraveltrigger$listeners = this.field_193170_b.get(p_192165_1_);
/*     */     
/*  32 */     if (nethertraveltrigger$listeners == null) {
/*     */       
/*  34 */       nethertraveltrigger$listeners = new Listeners(p_192165_1_);
/*  35 */       this.field_193170_b.put(p_192165_1_, nethertraveltrigger$listeners);
/*     */     } 
/*     */     
/*  38 */     nethertraveltrigger$listeners.func_193484_a(p_192165_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {
/*  43 */     Listeners nethertraveltrigger$listeners = this.field_193170_b.get(p_192164_1_);
/*     */     
/*  45 */     if (nethertraveltrigger$listeners != null) {
/*     */       
/*  47 */       nethertraveltrigger$listeners.func_193481_b(p_192164_2_);
/*     */       
/*  49 */       if (nethertraveltrigger$listeners.func_193482_a())
/*     */       {
/*  51 */         this.field_193170_b.remove(p_192164_1_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192167_a(PlayerAdvancements p_192167_1_) {
/*  58 */     this.field_193170_b.remove(p_192167_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/*  63 */     LocationPredicate locationpredicate = LocationPredicate.func_193454_a(p_192166_1_.get("entered"));
/*  64 */     LocationPredicate locationpredicate1 = LocationPredicate.func_193454_a(p_192166_1_.get("exited"));
/*  65 */     DistancePredicate distancepredicate = DistancePredicate.func_193421_a(p_192166_1_.get("distance"));
/*  66 */     return new Instance(locationpredicate, locationpredicate1, distancepredicate);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193168_a(EntityPlayerMP p_193168_1_, Vec3d p_193168_2_) {
/*  71 */     Listeners nethertraveltrigger$listeners = this.field_193170_b.get(p_193168_1_.func_192039_O());
/*     */     
/*  73 */     if (nethertraveltrigger$listeners != null)
/*     */     {
/*  75 */       nethertraveltrigger$listeners.func_193483_a(p_193168_1_.getServerWorld(), p_193168_2_, p_193168_1_.posX, p_193168_1_.posY, p_193168_1_.posZ);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Instance
/*     */     extends AbstractCriterionInstance
/*     */   {
/*     */     private final LocationPredicate field_193207_a;
/*     */     private final LocationPredicate field_193208_b;
/*     */     private final DistancePredicate field_193209_c;
/*     */     
/*     */     public Instance(LocationPredicate p_i47574_1_, LocationPredicate p_i47574_2_, DistancePredicate p_i47574_3_) {
/*  87 */       super(NetherTravelTrigger.field_193169_a);
/*  88 */       this.field_193207_a = p_i47574_1_;
/*  89 */       this.field_193208_b = p_i47574_2_;
/*  90 */       this.field_193209_c = p_i47574_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193206_a(WorldServer p_193206_1_, Vec3d p_193206_2_, double p_193206_3_, double p_193206_5_, double p_193206_7_) {
/*  95 */       if (!this.field_193207_a.func_193452_a(p_193206_1_, p_193206_2_.xCoord, p_193206_2_.yCoord, p_193206_2_.zCoord))
/*     */       {
/*  97 */         return false;
/*     */       }
/*  99 */       if (!this.field_193208_b.func_193452_a(p_193206_1_, p_193206_3_, p_193206_5_, p_193206_7_))
/*     */       {
/* 101 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 105 */       return this.field_193209_c.func_193422_a(p_193206_2_.xCoord, p_193206_2_.yCoord, p_193206_2_.zCoord, p_193206_3_, p_193206_5_, p_193206_7_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Listeners
/*     */   {
/*     */     private final PlayerAdvancements field_193485_a;
/* 113 */     private final Set<ICriterionTrigger.Listener<NetherTravelTrigger.Instance>> field_193486_b = Sets.newHashSet();
/*     */ 
/*     */     
/*     */     public Listeners(PlayerAdvancements p_i47575_1_) {
/* 117 */       this.field_193485_a = p_i47575_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_193482_a() {
/* 122 */       return this.field_193486_b.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193484_a(ICriterionTrigger.Listener<NetherTravelTrigger.Instance> p_193484_1_) {
/* 127 */       this.field_193486_b.add(p_193484_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193481_b(ICriterionTrigger.Listener<NetherTravelTrigger.Instance> p_193481_1_) {
/* 132 */       this.field_193486_b.remove(p_193481_1_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_193483_a(WorldServer p_193483_1_, Vec3d p_193483_2_, double p_193483_3_, double p_193483_5_, double p_193483_7_) {
/* 137 */       List<ICriterionTrigger.Listener<NetherTravelTrigger.Instance>> list = null;
/*     */       
/* 139 */       for (ICriterionTrigger.Listener<NetherTravelTrigger.Instance> listener : this.field_193486_b) {
/*     */         
/* 141 */         if (((NetherTravelTrigger.Instance)listener.func_192158_a()).func_193206_a(p_193483_1_, p_193483_2_, p_193483_3_, p_193483_5_, p_193483_7_)) {
/*     */           
/* 143 */           if (list == null)
/*     */           {
/* 145 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 148 */           list.add(listener);
/*     */         } 
/*     */       } 
/*     */       
/* 152 */       if (list != null)
/*     */       {
/* 154 */         for (ICriterionTrigger.Listener<NetherTravelTrigger.Instance> listener1 : list)
/*     */         {
/* 156 */           listener1.func_192159_a(this.field_193485_a);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\NetherTravelTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */