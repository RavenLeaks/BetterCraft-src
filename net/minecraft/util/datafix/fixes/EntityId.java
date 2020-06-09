/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.datafix.IFixableData;
/*     */ 
/*     */ public class EntityId
/*     */   implements IFixableData {
/*  10 */   private static final Map<String, String> field_191276_a = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public int getFixVersion() {
/*  14 */     return 704;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/*  19 */     String s = field_191276_a.get(compound.getString("id"));
/*     */     
/*  21 */     if (s != null)
/*     */     {
/*  23 */       compound.setString("id", s);
/*     */     }
/*     */     
/*  26 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*  31 */     field_191276_a.put("AreaEffectCloud", "minecraft:area_effect_cloud");
/*  32 */     field_191276_a.put("ArmorStand", "minecraft:armor_stand");
/*  33 */     field_191276_a.put("Arrow", "minecraft:arrow");
/*  34 */     field_191276_a.put("Bat", "minecraft:bat");
/*  35 */     field_191276_a.put("Blaze", "minecraft:blaze");
/*  36 */     field_191276_a.put("Boat", "minecraft:boat");
/*  37 */     field_191276_a.put("CaveSpider", "minecraft:cave_spider");
/*  38 */     field_191276_a.put("Chicken", "minecraft:chicken");
/*  39 */     field_191276_a.put("Cow", "minecraft:cow");
/*  40 */     field_191276_a.put("Creeper", "minecraft:creeper");
/*  41 */     field_191276_a.put("Donkey", "minecraft:donkey");
/*  42 */     field_191276_a.put("DragonFireball", "minecraft:dragon_fireball");
/*  43 */     field_191276_a.put("ElderGuardian", "minecraft:elder_guardian");
/*  44 */     field_191276_a.put("EnderCrystal", "minecraft:ender_crystal");
/*  45 */     field_191276_a.put("EnderDragon", "minecraft:ender_dragon");
/*  46 */     field_191276_a.put("Enderman", "minecraft:enderman");
/*  47 */     field_191276_a.put("Endermite", "minecraft:endermite");
/*  48 */     field_191276_a.put("EyeOfEnderSignal", "minecraft:eye_of_ender_signal");
/*  49 */     field_191276_a.put("FallingSand", "minecraft:falling_block");
/*  50 */     field_191276_a.put("Fireball", "minecraft:fireball");
/*  51 */     field_191276_a.put("FireworksRocketEntity", "minecraft:fireworks_rocket");
/*  52 */     field_191276_a.put("Ghast", "minecraft:ghast");
/*  53 */     field_191276_a.put("Giant", "minecraft:giant");
/*  54 */     field_191276_a.put("Guardian", "minecraft:guardian");
/*  55 */     field_191276_a.put("Horse", "minecraft:horse");
/*  56 */     field_191276_a.put("Husk", "minecraft:husk");
/*  57 */     field_191276_a.put("Item", "minecraft:item");
/*  58 */     field_191276_a.put("ItemFrame", "minecraft:item_frame");
/*  59 */     field_191276_a.put("LavaSlime", "minecraft:magma_cube");
/*  60 */     field_191276_a.put("LeashKnot", "minecraft:leash_knot");
/*  61 */     field_191276_a.put("MinecartChest", "minecraft:chest_minecart");
/*  62 */     field_191276_a.put("MinecartCommandBlock", "minecraft:commandblock_minecart");
/*  63 */     field_191276_a.put("MinecartFurnace", "minecraft:furnace_minecart");
/*  64 */     field_191276_a.put("MinecartHopper", "minecraft:hopper_minecart");
/*  65 */     field_191276_a.put("MinecartRideable", "minecraft:minecart");
/*  66 */     field_191276_a.put("MinecartSpawner", "minecraft:spawner_minecart");
/*  67 */     field_191276_a.put("MinecartTNT", "minecraft:tnt_minecart");
/*  68 */     field_191276_a.put("Mule", "minecraft:mule");
/*  69 */     field_191276_a.put("MushroomCow", "minecraft:mooshroom");
/*  70 */     field_191276_a.put("Ozelot", "minecraft:ocelot");
/*  71 */     field_191276_a.put("Painting", "minecraft:painting");
/*  72 */     field_191276_a.put("Pig", "minecraft:pig");
/*  73 */     field_191276_a.put("PigZombie", "minecraft:zombie_pigman");
/*  74 */     field_191276_a.put("PolarBear", "minecraft:polar_bear");
/*  75 */     field_191276_a.put("PrimedTnt", "minecraft:tnt");
/*  76 */     field_191276_a.put("Rabbit", "minecraft:rabbit");
/*  77 */     field_191276_a.put("Sheep", "minecraft:sheep");
/*  78 */     field_191276_a.put("Shulker", "minecraft:shulker");
/*  79 */     field_191276_a.put("ShulkerBullet", "minecraft:shulker_bullet");
/*  80 */     field_191276_a.put("Silverfish", "minecraft:silverfish");
/*  81 */     field_191276_a.put("Skeleton", "minecraft:skeleton");
/*  82 */     field_191276_a.put("SkeletonHorse", "minecraft:skeleton_horse");
/*  83 */     field_191276_a.put("Slime", "minecraft:slime");
/*  84 */     field_191276_a.put("SmallFireball", "minecraft:small_fireball");
/*  85 */     field_191276_a.put("SnowMan", "minecraft:snowman");
/*  86 */     field_191276_a.put("Snowball", "minecraft:snowball");
/*  87 */     field_191276_a.put("SpectralArrow", "minecraft:spectral_arrow");
/*  88 */     field_191276_a.put("Spider", "minecraft:spider");
/*  89 */     field_191276_a.put("Squid", "minecraft:squid");
/*  90 */     field_191276_a.put("Stray", "minecraft:stray");
/*  91 */     field_191276_a.put("ThrownEgg", "minecraft:egg");
/*  92 */     field_191276_a.put("ThrownEnderpearl", "minecraft:ender_pearl");
/*  93 */     field_191276_a.put("ThrownExpBottle", "minecraft:xp_bottle");
/*  94 */     field_191276_a.put("ThrownPotion", "minecraft:potion");
/*  95 */     field_191276_a.put("Villager", "minecraft:villager");
/*  96 */     field_191276_a.put("VillagerGolem", "minecraft:villager_golem");
/*  97 */     field_191276_a.put("Witch", "minecraft:witch");
/*  98 */     field_191276_a.put("WitherBoss", "minecraft:wither");
/*  99 */     field_191276_a.put("WitherSkeleton", "minecraft:wither_skeleton");
/* 100 */     field_191276_a.put("WitherSkull", "minecraft:wither_skull");
/* 101 */     field_191276_a.put("Wolf", "minecraft:wolf");
/* 102 */     field_191276_a.put("XPOrb", "minecraft:xp_orb");
/* 103 */     field_191276_a.put("Zombie", "minecraft:zombie");
/* 104 */     field_191276_a.put("ZombieHorse", "minecraft:zombie_horse");
/* 105 */     field_191276_a.put("ZombieVillager", "minecraft:zombie_villager");
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\EntityId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */