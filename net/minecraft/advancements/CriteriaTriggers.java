/*    */ package net.minecraft.advancements;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.advancements.critereon.BredAnimalsTrigger;
/*    */ import net.minecraft.advancements.critereon.BrewedPotionTrigger;
/*    */ import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
/*    */ import net.minecraft.advancements.critereon.ConstructBeaconTrigger;
/*    */ import net.minecraft.advancements.critereon.ConsumeItemTrigger;
/*    */ import net.minecraft.advancements.critereon.CuredZombieVillagerTrigger;
/*    */ import net.minecraft.advancements.critereon.EffectsChangedTrigger;
/*    */ import net.minecraft.advancements.critereon.EnchantedItemTrigger;
/*    */ import net.minecraft.advancements.critereon.EnterBlockTrigger;
/*    */ import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
/*    */ import net.minecraft.advancements.critereon.ImpossibleTrigger;
/*    */ import net.minecraft.advancements.critereon.InventoryChangeTrigger;
/*    */ import net.minecraft.advancements.critereon.ItemDurabilityTrigger;
/*    */ import net.minecraft.advancements.critereon.KilledTrigger;
/*    */ import net.minecraft.advancements.critereon.LevitationTrigger;
/*    */ import net.minecraft.advancements.critereon.NetherTravelTrigger;
/*    */ import net.minecraft.advancements.critereon.PlacedBlockTrigger;
/*    */ import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
/*    */ import net.minecraft.advancements.critereon.PositionTrigger;
/*    */ import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
/*    */ import net.minecraft.advancements.critereon.SummonedEntityTrigger;
/*    */ import net.minecraft.advancements.critereon.TameAnimalTrigger;
/*    */ import net.minecraft.advancements.critereon.TickTrigger;
/*    */ import net.minecraft.advancements.critereon.UsedEnderEyeTrigger;
/*    */ import net.minecraft.advancements.critereon.UsedTotemTrigger;
/*    */ import net.minecraft.advancements.critereon.VillagerTradeTrigger;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class CriteriaTriggers
/*    */ {
/* 36 */   private static final Map<ResourceLocation, ICriterionTrigger<?>> field_192139_s = Maps.newHashMap();
/* 37 */   public static final ImpossibleTrigger field_192121_a = func_192118_a(new ImpossibleTrigger());
/* 38 */   public static final KilledTrigger field_192122_b = func_192118_a(new KilledTrigger(new ResourceLocation("player_killed_entity")));
/* 39 */   public static final KilledTrigger field_192123_c = func_192118_a(new KilledTrigger(new ResourceLocation("entity_killed_player")));
/* 40 */   public static final EnterBlockTrigger field_192124_d = func_192118_a(new EnterBlockTrigger());
/* 41 */   public static final InventoryChangeTrigger field_192125_e = func_192118_a(new InventoryChangeTrigger());
/* 42 */   public static final RecipeUnlockedTrigger field_192126_f = func_192118_a(new RecipeUnlockedTrigger());
/* 43 */   public static final PlayerHurtEntityTrigger field_192127_g = func_192118_a(new PlayerHurtEntityTrigger());
/* 44 */   public static final EntityHurtPlayerTrigger field_192128_h = func_192118_a(new EntityHurtPlayerTrigger());
/* 45 */   public static final EnchantedItemTrigger field_192129_i = func_192118_a(new EnchantedItemTrigger());
/* 46 */   public static final BrewedPotionTrigger field_192130_j = func_192118_a(new BrewedPotionTrigger());
/* 47 */   public static final ConstructBeaconTrigger field_192131_k = func_192118_a(new ConstructBeaconTrigger());
/* 48 */   public static final UsedEnderEyeTrigger field_192132_l = func_192118_a(new UsedEnderEyeTrigger());
/* 49 */   public static final SummonedEntityTrigger field_192133_m = func_192118_a(new SummonedEntityTrigger());
/* 50 */   public static final BredAnimalsTrigger field_192134_n = func_192118_a(new BredAnimalsTrigger());
/* 51 */   public static final PositionTrigger field_192135_o = func_192118_a(new PositionTrigger(new ResourceLocation("location")));
/* 52 */   public static final PositionTrigger field_192136_p = func_192118_a(new PositionTrigger(new ResourceLocation("slept_in_bed")));
/* 53 */   public static final CuredZombieVillagerTrigger field_192137_q = func_192118_a(new CuredZombieVillagerTrigger());
/* 54 */   public static final VillagerTradeTrigger field_192138_r = func_192118_a(new VillagerTradeTrigger());
/* 55 */   public static final ItemDurabilityTrigger field_193132_s = func_192118_a(new ItemDurabilityTrigger());
/* 56 */   public static final LevitationTrigger field_193133_t = func_192118_a(new LevitationTrigger());
/* 57 */   public static final ChangeDimensionTrigger field_193134_u = func_192118_a(new ChangeDimensionTrigger());
/* 58 */   public static final TickTrigger field_193135_v = func_192118_a(new TickTrigger());
/* 59 */   public static final TameAnimalTrigger field_193136_w = func_192118_a(new TameAnimalTrigger());
/* 60 */   public static final PlacedBlockTrigger field_193137_x = func_192118_a(new PlacedBlockTrigger());
/* 61 */   public static final ConsumeItemTrigger field_193138_y = func_192118_a(new ConsumeItemTrigger());
/* 62 */   public static final EffectsChangedTrigger field_193139_z = func_192118_a(new EffectsChangedTrigger());
/* 63 */   public static final UsedTotemTrigger field_193130_A = func_192118_a(new UsedTotemTrigger());
/* 64 */   public static final NetherTravelTrigger field_193131_B = func_192118_a(new NetherTravelTrigger());
/*    */ 
/*    */   
/*    */   private static <T extends ICriterionTrigger> T func_192118_a(T p_192118_0_) {
/* 68 */     if (field_192139_s.containsKey(p_192118_0_.func_192163_a()))
/*    */     {
/* 70 */       throw new IllegalArgumentException("Duplicate criterion id " + p_192118_0_.func_192163_a());
/*    */     }
/*    */ 
/*    */     
/* 74 */     field_192139_s.put(p_192118_0_.func_192163_a(), (ICriterionTrigger<?>)p_192118_0_);
/* 75 */     return p_192118_0_;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static <T extends ICriterionInstance> ICriterionTrigger<T> func_192119_a(ResourceLocation p_192119_0_) {
/* 82 */     return (ICriterionTrigger<T>)field_192139_s.get(p_192119_0_);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Iterable<? extends ICriterionTrigger<?>> func_192120_a() {
/* 87 */     return field_192139_s.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\CriteriaTriggers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */