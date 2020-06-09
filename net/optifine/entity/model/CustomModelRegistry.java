/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import optifine.Config;
/*     */ 
/*     */ 
/*     */ public class CustomModelRegistry
/*     */ {
/*  11 */   private static Map<String, ModelAdapter> mapModelAdapters = makeMapModelAdapters();
/*     */ 
/*     */   
/*     */   private static Map<String, ModelAdapter> makeMapModelAdapters() {
/*  15 */     Map<String, ModelAdapter> map = new LinkedHashMap<>();
/*  16 */     addModelAdapter(map, new ModelAdapterArmorStand());
/*  17 */     addModelAdapter(map, new ModelAdapterBat());
/*  18 */     addModelAdapter(map, new ModelAdapterBlaze());
/*  19 */     addModelAdapter(map, new ModelAdapterBoat());
/*  20 */     addModelAdapter(map, new ModelAdapterCaveSpider());
/*  21 */     addModelAdapter(map, new ModelAdapterChicken());
/*  22 */     addModelAdapter(map, new ModelAdapterCow());
/*  23 */     addModelAdapter(map, new ModelAdapterCreeper());
/*  24 */     addModelAdapter(map, new ModelAdapterDragon());
/*  25 */     addModelAdapter(map, new ModelAdapterDonkey());
/*  26 */     addModelAdapter(map, new ModelAdapterEnderCrystal());
/*  27 */     addModelAdapter(map, new ModelAdapterEnderCrystalNoBase());
/*  28 */     addModelAdapter(map, new ModelAdapterEnderman());
/*  29 */     addModelAdapter(map, new ModelAdapterEndermite());
/*  30 */     addModelAdapter(map, new ModelAdapterEvoker());
/*  31 */     addModelAdapter(map, new ModelAdapterEvokerFangs());
/*  32 */     addModelAdapter(map, new ModelAdapterGhast());
/*  33 */     addModelAdapter(map, new ModelAdapterGuardian());
/*  34 */     addModelAdapter(map, new ModelAdapterHorse());
/*  35 */     addModelAdapter(map, new ModelAdapterHusk());
/*  36 */     addModelAdapter(map, new ModelAdapterIronGolem());
/*  37 */     addModelAdapter(map, new ModelAdapterLeadKnot());
/*  38 */     addModelAdapter(map, new ModelAdapterLlama());
/*  39 */     addModelAdapter(map, new ModelAdapterMagmaCube());
/*  40 */     addModelAdapter(map, new ModelAdapterMinecart());
/*  41 */     addModelAdapter(map, new ModelAdapterMinecartTnt());
/*  42 */     addModelAdapter(map, new ModelAdapterMinecartMobSpawner());
/*  43 */     addModelAdapter(map, new ModelAdapterMooshroom());
/*  44 */     addModelAdapter(map, new ModelAdapterMule());
/*  45 */     addModelAdapter(map, new ModelAdapterOcelot());
/*  46 */     addModelAdapter(map, new ModelAdapterPig());
/*  47 */     addModelAdapter(map, new ModelAdapterPigZombie());
/*  48 */     addModelAdapter(map, new ModelAdapterPolarBear());
/*  49 */     addModelAdapter(map, new ModelAdapterRabbit());
/*  50 */     addModelAdapter(map, new ModelAdapterSheep());
/*  51 */     addModelAdapter(map, new ModelAdapterShulker());
/*  52 */     addModelAdapter(map, new ModelAdapterShulkerBullet());
/*  53 */     addModelAdapter(map, new ModelAdapterSilverfish());
/*  54 */     addModelAdapter(map, new ModelAdapterSkeleton());
/*  55 */     addModelAdapter(map, new ModelAdapterSkeletonHorse());
/*  56 */     addModelAdapter(map, new ModelAdapterSlime());
/*  57 */     addModelAdapter(map, new ModelAdapterSnowman());
/*  58 */     addModelAdapter(map, new ModelAdapterSpider());
/*  59 */     addModelAdapter(map, new ModelAdapterSquid());
/*  60 */     addModelAdapter(map, new ModelAdapterStray());
/*  61 */     addModelAdapter(map, new ModelAdapterVex());
/*  62 */     addModelAdapter(map, new ModelAdapterVillager());
/*  63 */     addModelAdapter(map, new ModelAdapterVindicator());
/*  64 */     addModelAdapter(map, new ModelAdapterWitch());
/*  65 */     addModelAdapter(map, new ModelAdapterWither());
/*  66 */     addModelAdapter(map, new ModelAdapterWitherSkeleton());
/*  67 */     addModelAdapter(map, new ModelAdapterWitherSkull());
/*  68 */     addModelAdapter(map, new ModelAdapterWolf());
/*  69 */     addModelAdapter(map, new ModelAdapterZombie());
/*  70 */     addModelAdapter(map, new ModelAdapterZombieHorse());
/*  71 */     addModelAdapter(map, new ModelAdapterZombieVillager());
/*  72 */     addModelAdapter(map, new ModelAdapterSheepWool());
/*  73 */     addModelAdapter(map, new ModelAdapterBanner());
/*  74 */     addModelAdapter(map, new ModelAdapterBook());
/*  75 */     addModelAdapter(map, new ModelAdapterChest());
/*  76 */     addModelAdapter(map, new ModelAdapterChestLarge());
/*  77 */     addModelAdapter(map, new ModelAdapterEnderChest());
/*  78 */     addModelAdapter(map, new ModelAdapterHeadDragon());
/*  79 */     addModelAdapter(map, new ModelAdapterHeadHumanoid());
/*  80 */     addModelAdapter(map, new ModelAdapterHeadSkeleton());
/*  81 */     addModelAdapter(map, new ModelAdapterShulkerBox());
/*  82 */     addModelAdapter(map, new ModelAdapterSign());
/*  83 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter) {
/*  88 */     if (map.containsKey(modelAdapter.getName()))
/*     */     {
/*  90 */       Config.warn("Model adapter already registered for id: " + modelAdapter.getName() + ", class: " + modelAdapter.getEntityClass().getName());
/*     */     }
/*     */     
/*  93 */     map.put(modelAdapter.getName(), modelAdapter);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ModelAdapter getModelAdapter(String name) {
/*  98 */     return mapModelAdapters.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getModelNames() {
/* 103 */     Set<String> set = mapModelAdapters.keySet();
/* 104 */     String[] astring = set.<String>toArray(new String[set.size()]);
/* 105 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\CustomModelRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */