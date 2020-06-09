/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.item.EntityEnderPearl;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityMinecartChest;
/*     */ import net.minecraft.entity.item.EntityMinecartCommandBlock;
/*     */ import net.minecraft.entity.item.EntityMinecartEmpty;
/*     */ import net.minecraft.entity.item.EntityMinecartFurnace;
/*     */ import net.minecraft.entity.item.EntityMinecartHopper;
/*     */ import net.minecraft.entity.item.EntityMinecartMobSpawner;
/*     */ import net.minecraft.entity.item.EntityMinecartTNT;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityElderGuardian;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityEndermite;
/*     */ import net.minecraft.entity.monster.EntityEvoker;
/*     */ import net.minecraft.entity.monster.EntityGhast;
/*     */ import net.minecraft.entity.monster.EntityGiantZombie;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.entity.monster.EntityHusk;
/*     */ import net.minecraft.entity.monster.EntityIllusionIllager;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntityPolarBear;
/*     */ import net.minecraft.entity.monster.EntityShulker;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySnowman;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityStray;
/*     */ import net.minecraft.entity.monster.EntityVex;
/*     */ import net.minecraft.entity.monster.EntityVindicator;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityWitherSkeleton;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.monster.EntityZombieVillager;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityDonkey;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityLlama;
/*     */ import net.minecraft.entity.passive.EntityMooshroom;
/*     */ import net.minecraft.entity.passive.EntityMule;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.passive.EntityParrot;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntitySkeletonHorse;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.passive.EntityZombieHorse;
/*     */ import net.minecraft.entity.projectile.EntityDragonFireball;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityEvokerFangs;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.entity.projectile.EntityLlamaSpit;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntityShulkerBullet;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.entity.projectile.EntitySpectralArrow;
/*     */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.registry.RegistryNamespaced;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityList
/*     */ {
/* 105 */   public static final ResourceLocation field_191307_a = new ResourceLocation("lightning_bolt");
/* 106 */   private static final ResourceLocation field_191310_e = new ResourceLocation("player");
/* 107 */   private static final Logger LOGGER = LogManager.getLogger();
/* 108 */   public static final RegistryNamespaced<ResourceLocation, Class<? extends Entity>> field_191308_b = new RegistryNamespaced();
/* 109 */   public static final Map<ResourceLocation, EntityEggInfo> ENTITY_EGGS = Maps.newLinkedHashMap();
/* 110 */   public static final Set<ResourceLocation> field_191309_d = Sets.newHashSet();
/* 111 */   private static final List<String> field_191311_g = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ResourceLocation func_191301_a(Entity p_191301_0_) {
/* 116 */     return func_191306_a((Class)p_191301_0_.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ResourceLocation func_191306_a(Class<? extends Entity> p_191306_0_) {
/* 122 */     return (ResourceLocation)field_191308_b.getNameForObject(p_191306_0_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String getEntityString(Entity entityIn) {
/* 132 */     int i = field_191308_b.getIDForObject(entityIn.getClass());
/* 133 */     return (i == -1) ? null : field_191311_g.get(i);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String func_191302_a(@Nullable ResourceLocation p_191302_0_) {
/* 139 */     int i = field_191308_b.getIDForObject(field_191308_b.getObject(p_191302_0_));
/* 140 */     return (i == -1) ? null : field_191311_g.get(i);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Class<? extends Entity> getClassFromID(int entityID) {
/* 146 */     return (Class<? extends Entity>)field_191308_b.getObjectById(entityID);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Class<? extends Entity> func_192839_a(String p_192839_0_) {
/* 152 */     return (Class<? extends Entity>)field_191308_b.getObject(new ResourceLocation(p_192839_0_));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Entity func_191304_a(@Nullable Class<? extends Entity> p_191304_0_, World p_191304_1_) {
/* 158 */     if (p_191304_0_ == null)
/*     */     {
/* 160 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 166 */       return p_191304_0_.getConstructor(new Class[] { World.class }).newInstance(new Object[] { p_191304_1_ });
/*     */     }
/* 168 */     catch (Exception exception) {
/*     */       
/* 170 */       exception.printStackTrace();
/* 171 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Entity createEntityByID(int entityID, World worldIn) {
/* 183 */     return func_191304_a(getClassFromID(entityID), worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Entity createEntityByIDFromName(ResourceLocation name, World worldIn) {
/* 189 */     return func_191304_a((Class<? extends Entity>)field_191308_b.getObject(name), worldIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Entity createEntityFromNBT(NBTTagCompound nbt, World worldIn) {
/* 199 */     ResourceLocation resourcelocation = new ResourceLocation(nbt.getString("id"));
/* 200 */     Entity entity = createEntityByIDFromName(resourcelocation, worldIn);
/*     */     
/* 202 */     if (entity == null) {
/*     */       
/* 204 */       LOGGER.warn("Skipping Entity with id {}", resourcelocation);
/*     */     }
/*     */     else {
/*     */       
/* 208 */       entity.readFromNBT(nbt);
/*     */     } 
/*     */     
/* 211 */     return entity;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set<ResourceLocation> getEntityNameList() {
/* 216 */     return field_191309_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isStringEntityName(Entity entityIn, ResourceLocation entityName) {
/* 221 */     ResourceLocation resourcelocation = func_191306_a((Class)entityIn.getClass());
/*     */     
/* 223 */     if (resourcelocation != null)
/*     */     {
/* 225 */       return resourcelocation.equals(entityName);
/*     */     }
/* 227 */     if (entityIn instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 229 */       return field_191310_e.equals(entityName);
/*     */     }
/*     */ 
/*     */     
/* 233 */     return (entityIn instanceof net.minecraft.entity.effect.EntityLightningBolt) ? field_191307_a.equals(entityName) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStringValidEntityName(ResourceLocation entityName) {
/* 239 */     return !(!field_191310_e.equals(entityName) && !getEntityNameList().contains(entityName));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String func_192840_b() {
/* 244 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 246 */     for (ResourceLocation resourcelocation : getEntityNameList())
/*     */     {
/* 248 */       stringbuilder.append(resourcelocation).append(", ");
/*     */     }
/*     */     
/* 251 */     stringbuilder.append(field_191310_e);
/* 252 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/* 257 */     func_191303_a(1, "item", (Class)EntityItem.class, "Item");
/* 258 */     func_191303_a(2, "xp_orb", (Class)EntityXPOrb.class, "XPOrb");
/* 259 */     func_191303_a(3, "area_effect_cloud", (Class)EntityAreaEffectCloud.class, "AreaEffectCloud");
/* 260 */     func_191303_a(4, "elder_guardian", (Class)EntityElderGuardian.class, "ElderGuardian");
/* 261 */     func_191303_a(5, "wither_skeleton", (Class)EntityWitherSkeleton.class, "WitherSkeleton");
/* 262 */     func_191303_a(6, "stray", (Class)EntityStray.class, "Stray");
/* 263 */     func_191303_a(7, "egg", (Class)EntityEgg.class, "ThrownEgg");
/* 264 */     func_191303_a(8, "leash_knot", (Class)EntityLeashKnot.class, "LeashKnot");
/* 265 */     func_191303_a(9, "painting", (Class)EntityPainting.class, "Painting");
/* 266 */     func_191303_a(10, "arrow", (Class)EntityTippedArrow.class, "Arrow");
/* 267 */     func_191303_a(11, "snowball", (Class)EntitySnowball.class, "Snowball");
/* 268 */     func_191303_a(12, "fireball", (Class)EntityLargeFireball.class, "Fireball");
/* 269 */     func_191303_a(13, "small_fireball", (Class)EntitySmallFireball.class, "SmallFireball");
/* 270 */     func_191303_a(14, "ender_pearl", (Class)EntityEnderPearl.class, "ThrownEnderpearl");
/* 271 */     func_191303_a(15, "eye_of_ender_signal", (Class)EntityEnderEye.class, "EyeOfEnderSignal");
/* 272 */     func_191303_a(16, "potion", (Class)EntityPotion.class, "ThrownPotion");
/* 273 */     func_191303_a(17, "xp_bottle", (Class)EntityExpBottle.class, "ThrownExpBottle");
/* 274 */     func_191303_a(18, "item_frame", (Class)EntityItemFrame.class, "ItemFrame");
/* 275 */     func_191303_a(19, "wither_skull", (Class)EntityWitherSkull.class, "WitherSkull");
/* 276 */     func_191303_a(20, "tnt", (Class)EntityTNTPrimed.class, "PrimedTnt");
/* 277 */     func_191303_a(21, "falling_block", (Class)EntityFallingBlock.class, "FallingSand");
/* 278 */     func_191303_a(22, "fireworks_rocket", (Class)EntityFireworkRocket.class, "FireworksRocketEntity");
/* 279 */     func_191303_a(23, "husk", (Class)EntityHusk.class, "Husk");
/* 280 */     func_191303_a(24, "spectral_arrow", (Class)EntitySpectralArrow.class, "SpectralArrow");
/* 281 */     func_191303_a(25, "shulker_bullet", (Class)EntityShulkerBullet.class, "ShulkerBullet");
/* 282 */     func_191303_a(26, "dragon_fireball", (Class)EntityDragonFireball.class, "DragonFireball");
/* 283 */     func_191303_a(27, "zombie_villager", (Class)EntityZombieVillager.class, "ZombieVillager");
/* 284 */     func_191303_a(28, "skeleton_horse", (Class)EntitySkeletonHorse.class, "SkeletonHorse");
/* 285 */     func_191303_a(29, "zombie_horse", (Class)EntityZombieHorse.class, "ZombieHorse");
/* 286 */     func_191303_a(30, "armor_stand", (Class)EntityArmorStand.class, "ArmorStand");
/* 287 */     func_191303_a(31, "donkey", (Class)EntityDonkey.class, "Donkey");
/* 288 */     func_191303_a(32, "mule", (Class)EntityMule.class, "Mule");
/* 289 */     func_191303_a(33, "evocation_fangs", (Class)EntityEvokerFangs.class, "EvocationFangs");
/* 290 */     func_191303_a(34, "evocation_illager", (Class)EntityEvoker.class, "EvocationIllager");
/* 291 */     func_191303_a(35, "vex", (Class)EntityVex.class, "Vex");
/* 292 */     func_191303_a(36, "vindication_illager", (Class)EntityVindicator.class, "VindicationIllager");
/* 293 */     func_191303_a(37, "illusion_illager", (Class)EntityIllusionIllager.class, "IllusionIllager");
/* 294 */     func_191303_a(40, "commandblock_minecart", (Class)EntityMinecartCommandBlock.class, EntityMinecart.Type.COMMAND_BLOCK.getName());
/* 295 */     func_191303_a(41, "boat", (Class)EntityBoat.class, "Boat");
/* 296 */     func_191303_a(42, "minecart", (Class)EntityMinecartEmpty.class, EntityMinecart.Type.RIDEABLE.getName());
/* 297 */     func_191303_a(43, "chest_minecart", (Class)EntityMinecartChest.class, EntityMinecart.Type.CHEST.getName());
/* 298 */     func_191303_a(44, "furnace_minecart", (Class)EntityMinecartFurnace.class, EntityMinecart.Type.FURNACE.getName());
/* 299 */     func_191303_a(45, "tnt_minecart", (Class)EntityMinecartTNT.class, EntityMinecart.Type.TNT.getName());
/* 300 */     func_191303_a(46, "hopper_minecart", (Class)EntityMinecartHopper.class, EntityMinecart.Type.HOPPER.getName());
/* 301 */     func_191303_a(47, "spawner_minecart", (Class)EntityMinecartMobSpawner.class, EntityMinecart.Type.SPAWNER.getName());
/* 302 */     func_191303_a(50, "creeper", (Class)EntityCreeper.class, "Creeper");
/* 303 */     func_191303_a(51, "skeleton", (Class)EntitySkeleton.class, "Skeleton");
/* 304 */     func_191303_a(52, "spider", (Class)EntitySpider.class, "Spider");
/* 305 */     func_191303_a(53, "giant", (Class)EntityGiantZombie.class, "Giant");
/* 306 */     func_191303_a(54, "zombie", (Class)EntityZombie.class, "Zombie");
/* 307 */     func_191303_a(55, "slime", (Class)EntitySlime.class, "Slime");
/* 308 */     func_191303_a(56, "ghast", (Class)EntityGhast.class, "Ghast");
/* 309 */     func_191303_a(57, "zombie_pigman", (Class)EntityPigZombie.class, "PigZombie");
/* 310 */     func_191303_a(58, "enderman", (Class)EntityEnderman.class, "Enderman");
/* 311 */     func_191303_a(59, "cave_spider", (Class)EntityCaveSpider.class, "CaveSpider");
/* 312 */     func_191303_a(60, "silverfish", (Class)EntitySilverfish.class, "Silverfish");
/* 313 */     func_191303_a(61, "blaze", (Class)EntityBlaze.class, "Blaze");
/* 314 */     func_191303_a(62, "magma_cube", (Class)EntityMagmaCube.class, "LavaSlime");
/* 315 */     func_191303_a(63, "ender_dragon", (Class)EntityDragon.class, "EnderDragon");
/* 316 */     func_191303_a(64, "wither", (Class)EntityWither.class, "WitherBoss");
/* 317 */     func_191303_a(65, "bat", (Class)EntityBat.class, "Bat");
/* 318 */     func_191303_a(66, "witch", (Class)EntityWitch.class, "Witch");
/* 319 */     func_191303_a(67, "endermite", (Class)EntityEndermite.class, "Endermite");
/* 320 */     func_191303_a(68, "guardian", (Class)EntityGuardian.class, "Guardian");
/* 321 */     func_191303_a(69, "shulker", (Class)EntityShulker.class, "Shulker");
/* 322 */     func_191303_a(90, "pig", (Class)EntityPig.class, "Pig");
/* 323 */     func_191303_a(91, "sheep", (Class)EntitySheep.class, "Sheep");
/* 324 */     func_191303_a(92, "cow", (Class)EntityCow.class, "Cow");
/* 325 */     func_191303_a(93, "chicken", (Class)EntityChicken.class, "Chicken");
/* 326 */     func_191303_a(94, "squid", (Class)EntitySquid.class, "Squid");
/* 327 */     func_191303_a(95, "wolf", (Class)EntityWolf.class, "Wolf");
/* 328 */     func_191303_a(96, "mooshroom", (Class)EntityMooshroom.class, "MushroomCow");
/* 329 */     func_191303_a(97, "snowman", (Class)EntitySnowman.class, "SnowMan");
/* 330 */     func_191303_a(98, "ocelot", (Class)EntityOcelot.class, "Ozelot");
/* 331 */     func_191303_a(99, "villager_golem", (Class)EntityIronGolem.class, "VillagerGolem");
/* 332 */     func_191303_a(100, "horse", (Class)EntityHorse.class, "Horse");
/* 333 */     func_191303_a(101, "rabbit", (Class)EntityRabbit.class, "Rabbit");
/* 334 */     func_191303_a(102, "polar_bear", (Class)EntityPolarBear.class, "PolarBear");
/* 335 */     func_191303_a(103, "llama", (Class)EntityLlama.class, "Llama");
/* 336 */     func_191303_a(104, "llama_spit", (Class)EntityLlamaSpit.class, "LlamaSpit");
/* 337 */     func_191303_a(105, "parrot", (Class)EntityParrot.class, "Parrot");
/* 338 */     func_191303_a(120, "villager", (Class)EntityVillager.class, "Villager");
/* 339 */     func_191303_a(200, "ender_crystal", (Class)EntityEnderCrystal.class, "EnderCrystal");
/* 340 */     func_191305_a("bat", 4996656, 986895);
/* 341 */     func_191305_a("blaze", 16167425, 16775294);
/* 342 */     func_191305_a("cave_spider", 803406, 11013646);
/* 343 */     func_191305_a("chicken", 10592673, 16711680);
/* 344 */     func_191305_a("cow", 4470310, 10592673);
/* 345 */     func_191305_a("creeper", 894731, 0);
/* 346 */     func_191305_a("donkey", 5457209, 8811878);
/* 347 */     func_191305_a("elder_guardian", 13552826, 7632531);
/* 348 */     func_191305_a("enderman", 1447446, 0);
/* 349 */     func_191305_a("endermite", 1447446, 7237230);
/* 350 */     func_191305_a("evocation_illager", 9804699, 1973274);
/* 351 */     func_191305_a("ghast", 16382457, 12369084);
/* 352 */     func_191305_a("guardian", 5931634, 15826224);
/* 353 */     func_191305_a("horse", 12623485, 15656192);
/* 354 */     func_191305_a("husk", 7958625, 15125652);
/* 355 */     func_191305_a("llama", 12623485, 10051392);
/* 356 */     func_191305_a("magma_cube", 3407872, 16579584);
/* 357 */     func_191305_a("mooshroom", 10489616, 12040119);
/* 358 */     func_191305_a("mule", 1769984, 5321501);
/* 359 */     func_191305_a("ocelot", 15720061, 5653556);
/* 360 */     func_191305_a("parrot", 894731, 16711680);
/* 361 */     func_191305_a("pig", 15771042, 14377823);
/* 362 */     func_191305_a("polar_bear", 15921906, 9803152);
/* 363 */     func_191305_a("rabbit", 10051392, 7555121);
/* 364 */     func_191305_a("sheep", 15198183, 16758197);
/* 365 */     func_191305_a("shulker", 9725844, 5060690);
/* 366 */     func_191305_a("silverfish", 7237230, 3158064);
/* 367 */     func_191305_a("skeleton", 12698049, 4802889);
/* 368 */     func_191305_a("skeleton_horse", 6842447, 15066584);
/* 369 */     func_191305_a("slime", 5349438, 8306542);
/* 370 */     func_191305_a("spider", 3419431, 11013646);
/* 371 */     func_191305_a("squid", 2243405, 7375001);
/* 372 */     func_191305_a("stray", 6387319, 14543594);
/* 373 */     func_191305_a("vex", 8032420, 15265265);
/* 374 */     func_191305_a("villager", 5651507, 12422002);
/* 375 */     func_191305_a("vindication_illager", 9804699, 2580065);
/* 376 */     func_191305_a("witch", 3407872, 5349438);
/* 377 */     func_191305_a("wither_skeleton", 1315860, 4672845);
/* 378 */     func_191305_a("wolf", 14144467, 13545366);
/* 379 */     func_191305_a("zombie", 44975, 7969893);
/* 380 */     func_191305_a("zombie_horse", 3232308, 9945732);
/* 381 */     func_191305_a("zombie_pigman", 15373203, 5009705);
/* 382 */     func_191305_a("zombie_villager", 5651507, 7969893);
/* 383 */     field_191309_d.add(field_191307_a);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void func_191303_a(int p_191303_0_, String p_191303_1_, Class<? extends Entity> p_191303_2_, String p_191303_3_) {
/*     */     try {
/* 390 */       p_191303_2_.getConstructor(new Class[] { World.class });
/*     */     }
/* 392 */     catch (NoSuchMethodException var5) {
/*     */       
/* 394 */       throw new RuntimeException("Invalid class " + p_191303_2_ + " no constructor taking " + World.class.getName());
/*     */     } 
/*     */     
/* 397 */     if ((p_191303_2_.getModifiers() & 0x400) == 1024)
/*     */     {
/* 399 */       throw new RuntimeException("Invalid abstract class " + p_191303_2_);
/*     */     }
/*     */ 
/*     */     
/* 403 */     ResourceLocation resourcelocation = new ResourceLocation(p_191303_1_);
/* 404 */     field_191308_b.register(p_191303_0_, resourcelocation, p_191303_2_);
/* 405 */     field_191309_d.add(resourcelocation);
/*     */     
/* 407 */     while (field_191311_g.size() <= p_191303_0_)
/*     */     {
/* 409 */       field_191311_g.add(null);
/*     */     }
/*     */     
/* 412 */     field_191311_g.set(p_191303_0_, p_191303_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static EntityEggInfo func_191305_a(String p_191305_0_, int p_191305_1_, int p_191305_2_) {
/* 418 */     ResourceLocation resourcelocation = new ResourceLocation(p_191305_0_);
/* 419 */     return ENTITY_EGGS.put(resourcelocation, new EntityEggInfo(resourcelocation, p_191305_1_, p_191305_2_));
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EntityEggInfo
/*     */   {
/*     */     public final ResourceLocation spawnedID;
/*     */     public final int primaryColor;
/*     */     public final int secondaryColor;
/*     */     public final StatBase killEntityStat;
/*     */     public final StatBase entityKilledByStat;
/*     */     
/*     */     public EntityEggInfo(ResourceLocation p_i47341_1_, int p_i47341_2_, int p_i47341_3_) {
/* 432 */       this.spawnedID = p_i47341_1_;
/* 433 */       this.primaryColor = p_i47341_2_;
/* 434 */       this.secondaryColor = p_i47341_3_;
/* 435 */       this.killEntityStat = StatList.getStatKillEntity(this);
/* 436 */       this.entityKilledByStat = StatList.getStatEntityKilledBy(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */