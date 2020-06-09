/*     */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.sound;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ public enum SoundEffect
/*     */ {
/*   9 */   MOB_HORSE_ZOMBIE_IDLE("mob.horse.zombie.idle", "entity.zombie_horse.ambient", SoundCategory.NEUTRAL),
/*  10 */   NOTE_SNARE("note.snare", "block.note.snare", SoundCategory.RECORD),
/*  11 */   RANDOM_WOOD_CLICK("random.wood_click", "block.wood_button.click_on", SoundCategory.BLOCK),
/*  12 */   DIG_GRAVEL("dig.gravel", "block.gravel.place", SoundCategory.BLOCK),
/*  13 */   RANDOM_BOWHIT("random.bowhit", "block.tripwire.detach", SoundCategory.NEUTRAL),
/*  14 */   DIG_GLASS("dig.glass", "block.glass.break", SoundCategory.BLOCK),
/*  15 */   MOB_ZOMBIE_SAY("mob.zombie.say", "entity.zombie.ambient", SoundCategory.HOSTILE),
/*  16 */   MOB_PIG_DEATH("mob.pig.death", "entity.pig.death", SoundCategory.NEUTRAL),
/*  17 */   MOB_HORSE_DONKEY_HIT("mob.horse.donkey.hit", "entity.donkey.hurt", SoundCategory.NEUTRAL),
/*  18 */   GAME_NEUTRAL_SWIM("game.neutral.swim", "entity.player.swim", SoundCategory.NEUTRAL),
/*  19 */   GAME_PLAYER_SWIM("game.player.swim", "entity.player.swim", SoundCategory.PLAYER),
/*  20 */   MOB_ENDERMEN_IDLE("mob.endermen.idle", "entity.endermen.ambient", SoundCategory.HOSTILE),
/*  21 */   PORTAL_PORTAL("portal.portal", "block.portal.ambient", SoundCategory.BLOCK),
/*  22 */   RANDOM_FIZZ("random.fizz", "entity.generic.extinguish_fire", SoundCategory.BLOCK),
/*  23 */   NOTE_HARP("note.harp", "block.note.harp", SoundCategory.RECORD),
/*  24 */   STEP_SNOW("step.snow", "block.snow.step", SoundCategory.NEUTRAL),
/*  25 */   RANDOM_SUCCESSFUL_HIT("random.successful_hit", "entity.arrow.hit_player", SoundCategory.PLAYER),
/*  26 */   MOB_ZOMBIEPIG_ZPIGHURT("mob.zombiepig.zpighurt", "entity.zombie_pig.hurt", SoundCategory.HOSTILE),
/*  27 */   MOB_WOLF_HOWL("mob.wolf.howl", "entity.wolf.howl", SoundCategory.NEUTRAL),
/*  28 */   FIREWORKS_LAUNCH("fireworks.launch", "entity.firework.launch", SoundCategory.AMBIENT),
/*  29 */   MOB_COW_HURT("mob.cow.hurt", "entity.cow.death", SoundCategory.NEUTRAL),
/*  30 */   FIREWORKS_LARGEBLAST("fireworks.largeBlast", "entity.firework.large_blast", SoundCategory.AMBIENT),
/*  31 */   MOB_BLAZE_HIT("mob.blaze.hit", "entity.blaze.hurt", SoundCategory.HOSTILE),
/*  32 */   MOB_VILLAGER_DEATH("mob.villager.death", "entity.villager.death", SoundCategory.NEUTRAL),
/*  33 */   MOB_BLAZE_DEATH("mob.blaze.death", "entity.blaze.death", SoundCategory.HOSTILE),
/*  34 */   MOB_HORSE_ZOMBIE_DEATH("mob.horse.zombie.death", "entity.zombie_horse.death", SoundCategory.NEUTRAL),
/*  35 */   MOB_SILVERFISH_KILL("mob.silverfish.kill", "entity.endermite.death", SoundCategory.HOSTILE),
/*  36 */   MOB_WOLF_PANTING("mob.wolf.panting", "entity.wolf.pant", SoundCategory.NEUTRAL),
/*  37 */   NOTE_BASS("note.bass", "block.note.bass", SoundCategory.RECORD),
/*  38 */   DIG_STONE("dig.stone", "block.glass.place", SoundCategory.BLOCK),
/*  39 */   MOB_ENDERMEN_STARE("mob.endermen.stare", "entity.endermen.stare", SoundCategory.HOSTILE),
/*  40 */   GAME_PLAYER_SWIM_SPLASH("game.player.swim.splash", "entity.generic.splash", SoundCategory.BLOCK),
/*  41 */   MOB_SLIME_SMALL("mob.slime.small", "block.slime.hit", SoundCategory.HOSTILE),
/*  42 */   MOB_GHAST_DEATH("mob.ghast.death", "entity.ghast.death", SoundCategory.HOSTILE),
/*  43 */   MOB_GUARDIAN_ATTACK("mob.guardian.attack", "entity.guardian.attack", SoundCategory.HOSTILE),
/*  44 */   RANDOM_CLICK("random.click", "block.wood_pressureplate.click_on", SoundCategory.BLOCK),
/*  45 */   MOB_ZOMBIEPIG_ZPIG("mob.zombiepig.zpig", "entity.zombie_pig.ambient", SoundCategory.HOSTILE),
/*  46 */   GAME_PLAYER_DIE("game.player.die", "entity.player.death", SoundCategory.PLAYER),
/*  47 */   FIREWORKS_TWINKLE_FAR("fireworks.twinkle_far", "entity.firework.twinkle_far", SoundCategory.AMBIENT),
/*  48 */   MOB_GUARDIAN_LAND_IDLE("mob.guardian.land.idle", "entity.guardian.ambient_land", SoundCategory.HOSTILE),
/*  49 */   DIG_GRASS("dig.grass", "block.grass.place", SoundCategory.BLOCK),
/*  50 */   MOB_SKELETON_STEP("mob.skeleton.step", "entity.skeleton.step", SoundCategory.HOSTILE),
/*  51 */   MOB_WITHER_DEATH("mob.wither.death", "entity.wither.death", SoundCategory.HOSTILE),
/*  52 */   MOB_WOLF_HURT("mob.wolf.hurt", "entity.wolf.hurt", SoundCategory.NEUTRAL),
/*  53 */   MOB_HORSE_LEATHER("mob.horse.leather", "entity.horse.saddle", SoundCategory.NEUTRAL),
/*  54 */   MOB_BAT_LOOP("mob.bat.loop", "entity.bat.loop", SoundCategory.NEUTRAL),
/*  55 */   MOB_GHAST_SCREAM("mob.ghast.scream", "entity.ghast.hurt", SoundCategory.HOSTILE),
/*  56 */   GAME_PLAYER_HURT("game.player.hurt", "entity.player.death", SoundCategory.PLAYER),
/*  57 */   GAME_NEUTRAL_DIE("game.neutral.die", "entity.player.death", SoundCategory.NEUTRAL),
/*  58 */   MOB_CREEPER_DEATH("mob.creeper.death", "entity.creeper.death", SoundCategory.HOSTILE),
/*  59 */   MOB_HORSE_GALLOP("mob.horse.gallop", "entity.horse.gallop", SoundCategory.NEUTRAL),
/*  60 */   MOB_WITHER_SPAWN("mob.wither.spawn", "entity.wither.spawn", SoundCategory.HOSTILE),
/*  61 */   MOB_ENDERMEN_HIT("mob.endermen.hit", "entity.endermen.hurt", SoundCategory.HOSTILE),
/*  62 */   MOB_CREEPER_SAY("mob.creeper.say", "entity.creeper.hurt", SoundCategory.HOSTILE),
/*  63 */   MOB_HORSE_WOOD("mob.horse.wood", "entity.horse.step_wood", SoundCategory.NEUTRAL),
/*  64 */   MOB_ZOMBIE_UNFECT("mob.zombie.unfect", "entity.zombie_villager.converted", SoundCategory.HOSTILE),
/*  65 */   RANDOM_ANVIL_USE("random.anvil_use", "block.anvil.use", SoundCategory.BLOCK),
/*  66 */   RANDOM_CHESTCLOSED("random.chestclosed", "block.chest.close", SoundCategory.BLOCK),
/*  67 */   MOB_SHEEP_SHEAR("mob.sheep.shear", "entity.sheep.shear", SoundCategory.NEUTRAL),
/*  68 */   RANDOM_POP("random.pop", "entity.item.pickup", SoundCategory.PLAYER),
/*  69 */   MOB_BAT_DEATH("mob.bat.death", "entity.bat.death", SoundCategory.NEUTRAL),
/*  70 */   DIG_WOOD("dig.wood", "block.ladder.break", SoundCategory.BLOCK),
/*  71 */   MOB_HORSE_DONKEY_DEATH("mob.horse.donkey.death", "entity.donkey.death", SoundCategory.NEUTRAL),
/*  72 */   FIREWORKS_BLAST("fireworks.blast", "entity.firework.blast", SoundCategory.AMBIENT),
/*  73 */   MOB_ZOMBIEPIG_ZPIGANGRY("mob.zombiepig.zpigangry", "entity.zombie_pig.angry", SoundCategory.HOSTILE),
/*  74 */   GAME_HOSTILE_SWIM("game.hostile.swim", "entity.player.swim", SoundCategory.HOSTILE),
/*  75 */   MOB_GUARDIAN_FLOP("mob.guardian.flop", "entity.guardian.flop", SoundCategory.HOSTILE),
/*  76 */   MOB_VILLAGER_YES("mob.villager.yes", "entity.villager.yes", SoundCategory.NEUTRAL),
/*  77 */   MOB_GHAST_CHARGE("mob.ghast.charge", "entity.ghast.warn", SoundCategory.HOSTILE),
/*  78 */   CREEPER_PRIMED("creeper.primed", "entity.creeper.primed", SoundCategory.HOSTILE),
/*  79 */   DIG_SAND("dig.sand", "block.sand.break", SoundCategory.BLOCK),
/*  80 */   MOB_CHICKEN_SAY("mob.chicken.say", "entity.chicken.ambient", SoundCategory.NEUTRAL),
/*  81 */   RANDOM_DOOR_CLOSE("random.door_close", "block.wooden_door.close", SoundCategory.BLOCK),
/*  82 */   MOB_GUARDIAN_ELDER_DEATH("mob.guardian.elder.death", "entity.elder_guardian.death", SoundCategory.HOSTILE),
/*  83 */   FIREWORKS_TWINKLE("fireworks.twinkle", "entity.firework.twinkle", SoundCategory.AMBIENT),
/*  84 */   MOB_HORSE_SKELETON_DEATH("mob.horse.skeleton.death", "entity.skeleton_horse.death", SoundCategory.NEUTRAL),
/*  85 */   AMBIENT_WEATHER_RAIN("ambient.weather.rain", "weather.rain.above", SoundCategory.WEATHER),
/*  86 */   PORTAL_TRIGGER("portal.trigger", "block.portal.trigger", SoundCategory.BLOCK),
/*  87 */   RANDOM_CHESTOPEN("random.chestopen", "block.chest.open", SoundCategory.BLOCK),
/*  88 */   MOB_HORSE_LAND("mob.horse.land", "entity.horse.land", SoundCategory.NEUTRAL),
/*  89 */   MOB_SILVERFISH_STEP("mob.silverfish.step", "entity.silverfish.step", SoundCategory.HOSTILE),
/*  90 */   MOB_BAT_TAKEOFF("mob.bat.takeoff", "entity.bat.takeoff", SoundCategory.NEUTRAL),
/*  91 */   MOB_VILLAGER_NO("mob.villager.no", "entity.villager.no", SoundCategory.NEUTRAL),
/*  92 */   GAME_HOSTILE_HURT_FALL_BIG("game.hostile.hurt.fall.big", "entity.hostile.big_fall", SoundCategory.HOSTILE),
/*  93 */   MOB_IRONGOLEM_WALK("mob.irongolem.walk", "entity.irongolem.step", SoundCategory.NEUTRAL),
/*  94 */   NOTE_HAT("note.hat", "block.note.hat", SoundCategory.RECORD),
/*  95 */   MOB_ZOMBIE_METAL("mob.zombie.metal", "entity.zombie.attack_iron_door", SoundCategory.HOSTILE),
/*  96 */   MOB_VILLAGER_HAGGLE("mob.villager.haggle", "entity.villager.trading", SoundCategory.NEUTRAL),
/*  97 */   MOB_GHAST_FIREBALL("mob.ghast.fireball", "entity.blaze.shoot", SoundCategory.HOSTILE),
/*  98 */   MOB_IRONGOLEM_DEATH("mob.irongolem.death", "entity.irongolem.death", SoundCategory.NEUTRAL),
/*  99 */   RANDOM_BREAK("random.break", "item.shield.break", SoundCategory.PLAYER),
/* 100 */   MOB_ZOMBIE_REMEDY("mob.zombie.remedy", "entity.zombie_villager.cure", SoundCategory.HOSTILE),
/* 101 */   RANDOM_BOW("random.bow", "entity.splash_potion.throw", SoundCategory.NEUTRAL),
/* 102 */   MOB_VILLAGER_IDLE("mob.villager.idle", "entity.villager.ambient", SoundCategory.NEUTRAL),
/* 103 */   STEP_CLOTH("step.cloth", "block.cloth.fall", SoundCategory.NEUTRAL),
/* 104 */   MOB_SILVERFISH_HIT("mob.silverfish.hit", "entity.endermite.hurt", SoundCategory.HOSTILE),
/* 105 */   LIQUID_LAVA("liquid.lava", "block.lava.ambient", SoundCategory.BLOCK),
/* 106 */   GAME_NEUTRAL_HURT_FALL_BIG("game.neutral.hurt.fall.big", "entity.hostile.big_fall", SoundCategory.NEUTRAL),
/* 107 */   FIRE_FIRE("fire.fire", "block.fire.ambient", SoundCategory.BLOCK),
/* 108 */   MOB_ZOMBIE_WOOD("mob.zombie.wood", "entity.zombie.attack_door_wood", SoundCategory.HOSTILE),
/* 109 */   MOB_CHICKEN_STEP("mob.chicken.step", "entity.chicken.step", SoundCategory.NEUTRAL),
/* 110 */   MOB_GUARDIAN_LAND_HIT("mob.guardian.land.hit", "entity.guardian.hurt_land", SoundCategory.HOSTILE),
/* 111 */   MOB_CHICKEN_PLOP("mob.chicken.plop", "entity.donkey.chest", SoundCategory.NEUTRAL),
/* 112 */   MOB_ENDERDRAGON_WINGS("mob.enderdragon.wings", "entity.enderdragon.flap", SoundCategory.HOSTILE),
/* 113 */   STEP_GRASS("step.grass", "block.grass.hit", SoundCategory.NEUTRAL),
/* 114 */   MOB_HORSE_BREATHE("mob.horse.breathe", "entity.horse.breathe", SoundCategory.NEUTRAL),
/* 115 */   GAME_PLAYER_HURT_FALL_BIG("game.player.hurt.fall.big", "entity.hostile.big_fall", SoundCategory.PLAYER),
/* 116 */   MOB_HORSE_DONKEY_IDLE("mob.horse.donkey.idle", "entity.donkey.ambient", SoundCategory.NEUTRAL),
/* 117 */   MOB_SPIDER_STEP("mob.spider.step", "entity.spider.step", SoundCategory.HOSTILE),
/* 118 */   GAME_NEUTRAL_HURT("game.neutral.hurt", "entity.player.death", SoundCategory.NEUTRAL),
/* 119 */   MOB_COW_SAY("mob.cow.say", "entity.cow.ambient", SoundCategory.NEUTRAL),
/* 120 */   MOB_HORSE_JUMP("mob.horse.jump", "entity.horse.jump", SoundCategory.NEUTRAL),
/* 121 */   MOB_HORSE_SOFT("mob.horse.soft", "entity.horse.step", SoundCategory.NEUTRAL),
/* 122 */   GAME_NEUTRAL_SWIM_SPLASH("game.neutral.swim.splash", "entity.generic.splash", SoundCategory.NEUTRAL),
/* 123 */   MOB_GUARDIAN_HIT("mob.guardian.hit", "entity.guardian.hurt", SoundCategory.HOSTILE),
/* 124 */   MOB_ENDERDRAGON_END("mob.enderdragon.end", "entity.enderdragon.death", SoundCategory.HOSTILE),
/* 125 */   MOB_ZOMBIE_STEP("mob.zombie.step", "entity.zombie.step", SoundCategory.HOSTILE),
/* 126 */   MOB_ENDERDRAGON_GROWL("mob.enderdragon.growl", "entity.enderdragon.growl", SoundCategory.HOSTILE),
/* 127 */   MOB_WOLF_SHAKE("mob.wolf.shake", "entity.wolf.shake", SoundCategory.NEUTRAL),
/* 128 */   MOB_ENDERMEN_DEATH("mob.endermen.death", "entity.endermen.death", SoundCategory.HOSTILE),
/* 129 */   RANDOM_ANVIL_LAND("random.anvil_land", "block.anvil.land", SoundCategory.BLOCK),
/* 130 */   GAME_HOSTILE_HURT("game.hostile.hurt", "entity.player.death", SoundCategory.HOSTILE),
/* 131 */   MINECART_INSIDE("minecart.inside", "entity.minecart.inside", SoundCategory.PLAYER),
/* 132 */   MOB_SLIME_BIG("mob.slime.big", "entity.slime.death", SoundCategory.HOSTILE),
/* 133 */   LIQUID_WATER("liquid.water", "block.water.ambient", SoundCategory.BLOCK),
/* 134 */   MOB_PIG_SAY("mob.pig.say", "entity.pig.ambient", SoundCategory.NEUTRAL),
/* 135 */   MOB_WITHER_SHOOT("mob.wither.shoot", "entity.wither.shoot", SoundCategory.HOSTILE),
/* 136 */   ITEM_FIRECHARGE_USE("item.fireCharge.use", "entity.blaze.shoot", SoundCategory.BLOCK),
/* 137 */   STEP_SAND("step.sand", "block.sand.fall", SoundCategory.NEUTRAL),
/* 138 */   MOB_IRONGOLEM_HIT("mob.irongolem.hit", "entity.irongolem.hurt", SoundCategory.NEUTRAL),
/* 139 */   MOB_HORSE_DEATH("mob.horse.death", "entity.horse.death", SoundCategory.NEUTRAL),
/* 140 */   MOB_BAT_HURT("mob.bat.hurt", "entity.bat.hurt", SoundCategory.NEUTRAL),
/* 141 */   MOB_GHAST_AFFECTIONATE_SCREAM("mob.ghast.affectionate_scream", "entity.ghast.scream", SoundCategory.HOSTILE),
/* 142 */   MOB_GUARDIAN_ELDER_IDLE("mob.guardian.elder.idle", "entity.elder_guardian.ambient", SoundCategory.HOSTILE),
/* 143 */   MOB_ZOMBIEPIG_ZPIGDEATH("mob.zombiepig.zpigdeath", "entity.zombie_pig.death", SoundCategory.HOSTILE),
/* 144 */   AMBIENT_WEATHER_THUNDER("ambient.weather.thunder", "entity.lightning.thunder", SoundCategory.WEATHER),
/* 145 */   MINECART_BASE("minecart.base", "entity.minecart.riding", SoundCategory.NEUTRAL),
/* 146 */   STEP_LADDER("step.ladder", "block.ladder.hit", SoundCategory.NEUTRAL),
/* 147 */   MOB_HORSE_DONKEY_ANGRY("mob.horse.donkey.angry", "entity.donkey.angry", SoundCategory.NEUTRAL),
/* 148 */   AMBIENT_CAVE_CAVE("ambient.cave.cave", "ambient.cave", SoundCategory.AMBIENT),
/* 149 */   FIREWORKS_BLAST_FAR("fireworks.blast_far", "entity.firework.blast_far", SoundCategory.AMBIENT),
/* 150 */   GAME_NEUTRAL_HURT_FALL_SMALL("game.neutral.hurt.fall.small", "entity.generic.small_fall", SoundCategory.NEUTRAL),
/* 151 */   GAME_HOSTILE_SWIM_SPLASH("game.hostile.swim.splash", "entity.generic.splash", SoundCategory.HOSTILE),
/* 152 */   RANDOM_DRINK("random.drink", "entity.generic.drink", SoundCategory.PLAYER),
/* 153 */   GAME_HOSTILE_DIE("game.hostile.die", "entity.player.death", SoundCategory.HOSTILE),
/* 154 */   MOB_CAT_HISS("mob.cat.hiss", "entity.cat.hiss", SoundCategory.NEUTRAL),
/* 155 */   NOTE_BD("note.bd", "block.note.basedrum", SoundCategory.RECORD),
/* 156 */   MOB_SPIDER_SAY("mob.spider.say", "entity.spider.hurt", SoundCategory.HOSTILE),
/* 157 */   STEP_STONE("step.stone", "block.anvil.hit", SoundCategory.NEUTRAL, true),
/* 158 */   RANDOM_LEVELUP("random.levelup", "entity.player.levelup", SoundCategory.PLAYER),
/* 159 */   LIQUID_LAVAPOP("liquid.lavapop", "block.lava.pop", SoundCategory.BLOCK),
/* 160 */   MOB_SHEEP_SAY("mob.sheep.say", "entity.sheep.ambient", SoundCategory.NEUTRAL),
/* 161 */   MOB_SKELETON_SAY("mob.skeleton.say", "entity.skeleton.ambient", SoundCategory.HOSTILE),
/* 162 */   MOB_BLAZE_BREATHE("mob.blaze.breathe", "entity.blaze.ambient", SoundCategory.HOSTILE),
/* 163 */   MOB_BAT_IDLE("mob.bat.idle", "entity.bat.ambient", SoundCategory.NEUTRAL),
/* 164 */   MOB_MAGMACUBE_BIG("mob.magmacube.big", "entity.magmacube.squish", SoundCategory.HOSTILE),
/* 165 */   MOB_HORSE_IDLE("mob.horse.idle", "entity.horse.ambient", SoundCategory.NEUTRAL),
/* 166 */   GAME_HOSTILE_HURT_FALL_SMALL("game.hostile.hurt.fall.small", "entity.generic.small_fall", SoundCategory.HOSTILE),
/* 167 */   MOB_HORSE_ZOMBIE_HIT("mob.horse.zombie.hit", "entity.zombie_horse.hurt", SoundCategory.NEUTRAL),
/* 168 */   MOB_IRONGOLEM_THROW("mob.irongolem.throw", "entity.irongolem.attack", SoundCategory.NEUTRAL),
/* 169 */   DIG_CLOTH("dig.cloth", "block.cloth.place", SoundCategory.BLOCK),
/* 170 */   STEP_GRAVEL("step.gravel", "block.gravel.hit", SoundCategory.NEUTRAL),
/* 171 */   MOB_SILVERFISH_SAY("mob.silverfish.say", "entity.silverfish.ambient", SoundCategory.HOSTILE),
/* 172 */   MOB_CAT_PURR("mob.cat.purr", "entity.cat.purr", SoundCategory.NEUTRAL),
/* 173 */   MOB_ZOMBIE_INFECT("mob.zombie.infect", "entity.zombie.infect", SoundCategory.HOSTILE),
/* 174 */   RANDOM_EAT("random.eat", "entity.generic.eat", SoundCategory.PLAYER),
/* 175 */   MOB_WOLF_BARK("mob.wolf.bark", "entity.wolf.ambient", SoundCategory.NEUTRAL),
/* 176 */   GAME_TNT_PRIMED("game.tnt.primed", "entity.creeper.primed", SoundCategory.BLOCK),
/* 177 */   MOB_SHEEP_STEP("mob.sheep.step", "entity.sheep.step", SoundCategory.NEUTRAL),
/* 178 */   MOB_ZOMBIE_DEATH("mob.zombie.death", "entity.zombie.death", SoundCategory.HOSTILE),
/* 179 */   RANDOM_DOOR_OPEN("random.door_open", "block.wooden_door.open", SoundCategory.BLOCK),
/* 180 */   MOB_ENDERMEN_PORTAL("mob.endermen.portal", "entity.endermen.teleport", SoundCategory.HOSTILE),
/* 181 */   MOB_HORSE_ANGRY("mob.horse.angry", "entity.horse.angry", SoundCategory.NEUTRAL),
/* 182 */   MOB_WOLF_GROWL("mob.wolf.growl", "entity.wolf.growl", SoundCategory.NEUTRAL),
/* 183 */   DIG_SNOW("dig.snow", "block.snow.place", SoundCategory.BLOCK),
/* 184 */   TILE_PISTON_OUT("tile.piston.out", "block.piston.extend", SoundCategory.BLOCK),
/* 185 */   RANDOM_BURP("random.burp", "entity.player.burp", SoundCategory.PLAYER),
/* 186 */   MOB_COW_STEP("mob.cow.step", "entity.cow.step", SoundCategory.NEUTRAL),
/* 187 */   MOB_WITHER_HURT("mob.wither.hurt", "entity.wither.hurt", SoundCategory.HOSTILE),
/* 188 */   MOB_GUARDIAN_LAND_DEATH("mob.guardian.land.death", "entity.elder_guardian.death_land", SoundCategory.HOSTILE),
/* 189 */   MOB_CHICKEN_HURT("mob.chicken.hurt", "entity.chicken.death", SoundCategory.NEUTRAL),
/* 190 */   MOB_WOLF_STEP("mob.wolf.step", "entity.wolf.step", SoundCategory.NEUTRAL),
/* 191 */   MOB_WOLF_DEATH("mob.wolf.death", "entity.wolf.death", SoundCategory.NEUTRAL),
/* 192 */   MOB_WOLF_WHINE("mob.wolf.whine", "entity.wolf.whine", SoundCategory.NEUTRAL),
/* 193 */   NOTE_PLING("note.pling", "block.note.pling", SoundCategory.RECORD),
/* 194 */   GAME_PLAYER_HURT_FALL_SMALL("game.player.hurt.fall.small", "entity.generic.small_fall", SoundCategory.PLAYER),
/* 195 */   MOB_CAT_PURREOW("mob.cat.purreow", "entity.cat.purreow", SoundCategory.NEUTRAL),
/* 196 */   FIREWORKS_LARGEBLAST_FAR("fireworks.largeBlast_far", "entity.firework.large_blast_far", SoundCategory.AMBIENT),
/* 197 */   MOB_SKELETON_HURT("mob.skeleton.hurt", "entity.skeleton.hurt", SoundCategory.HOSTILE),
/* 198 */   MOB_SPIDER_DEATH("mob.spider.death", "entity.spider.death", SoundCategory.HOSTILE),
/* 199 */   RANDOM_ANVIL_BREAK("random.anvil_break", "block.anvil.destroy", SoundCategory.BLOCK),
/* 200 */   MOB_WITHER_IDLE("mob.wither.idle", "entity.wither.ambient", SoundCategory.HOSTILE),
/* 201 */   MOB_GUARDIAN_ELDER_HIT("mob.guardian.elder.hit", "entity.elder_guardian.hurt", SoundCategory.HOSTILE),
/* 202 */   MOB_ENDERMEN_SCREAM("mob.endermen.scream", "entity.endermen.scream", SoundCategory.HOSTILE),
/* 203 */   MOB_CAT_HITT("mob.cat.hitt", "entity.cat.hurt", SoundCategory.NEUTRAL),
/* 204 */   MOB_MAGMACUBE_SMALL("mob.magmacube.small", "entity.small_magmacube.squish", SoundCategory.HOSTILE),
/* 205 */   FIRE_IGNITE("fire.ignite", "item.flintandsteel.use", SoundCategory.BLOCK, true),
/* 206 */   MOB_ENDERDRAGON_HIT("mob.enderdragon.hit", "entity.enderdragon.hurt", SoundCategory.HOSTILE),
/* 207 */   MOB_ZOMBIE_HURT("mob.zombie.hurt", "entity.zombie.hurt", SoundCategory.HOSTILE),
/* 208 */   RANDOM_EXPLODE("random.explode", "block.end_gateway.spawn", SoundCategory.BLOCK),
/* 209 */   MOB_SLIME_ATTACK("mob.slime.attack", "entity.slime.attack", SoundCategory.HOSTILE),
/* 210 */   MOB_MAGMACUBE_JUMP("mob.magmacube.jump", "entity.magmacube.jump", SoundCategory.HOSTILE),
/* 211 */   RANDOM_SPLASH("random.splash", "entity.bobber.splash", SoundCategory.PLAYER),
/* 212 */   MOB_HORSE_SKELETON_HIT("mob.horse.skeleton.hit", "entity.skeleton_horse.hurt", SoundCategory.NEUTRAL),
/* 213 */   MOB_GHAST_MOAN("mob.ghast.moan", "entity.ghast.ambient", SoundCategory.HOSTILE),
/* 214 */   MOB_GUARDIAN_CURSE("mob.guardian.curse", "entity.elder_guardian.curse", SoundCategory.HOSTILE),
/* 215 */   GAME_POTION_SMASH("game.potion.smash", "block.glass.break", SoundCategory.NEUTRAL),
/* 216 */   NOTE_BASSATTACK("note.bassattack", "block.note.bass", SoundCategory.RECORD),
/* 217 */   GUI_BUTTON_PRESS("gui.button.press", "block.wood_pressureplate.click_on", SoundCategory.MASTER),
/* 218 */   RANDOM_ORB("random.orb", "entity.experience_orb.pickup", SoundCategory.PLAYER),
/* 219 */   MOB_ZOMBIE_WOODBREAK("mob.zombie.woodbreak", "entity.zombie.break_door_wood", SoundCategory.HOSTILE),
/* 220 */   MOB_HORSE_ARMOR("mob.horse.armor", "entity.horse.armor", SoundCategory.NEUTRAL),
/* 221 */   TILE_PISTON_IN("tile.piston.in", "block.piston.contract", SoundCategory.BLOCK),
/* 222 */   MOB_CAT_MEOW("mob.cat.meow", "entity.cat.ambient", SoundCategory.NEUTRAL),
/* 223 */   MOB_PIG_STEP("mob.pig.step", "entity.pig.step", SoundCategory.NEUTRAL),
/* 224 */   STEP_WOOD("step.wood", "block.wood.step", SoundCategory.NEUTRAL),
/* 225 */   PORTAL_TRAVEL("portal.travel", "block.portal.travel", SoundCategory.PLAYER),
/* 226 */   MOB_GUARDIAN_DEATH("mob.guardian.death", "entity.guardian.death", SoundCategory.HOSTILE),
/* 227 */   MOB_SKELETON_DEATH("mob.skeleton.death", "entity.skeleton.death", SoundCategory.HOSTILE),
/* 228 */   MOB_HORSE_HIT("mob.horse.hit", "entity.horse.hurt", SoundCategory.NEUTRAL),
/* 229 */   MOB_VILLAGER_HIT("mob.villager.hit", "entity.villager.hurt", SoundCategory.NEUTRAL),
/* 230 */   MOB_HORSE_SKELETON_IDLE("mob.horse.skeleton.idle", "entity.skeleton_horse.ambient", SoundCategory.NEUTRAL),
/* 231 */   RECORDS_CHIRP("records.chirp", "record.chirp", SoundCategory.RECORD),
/* 232 */   MOB_RABBIT_HURT("mob.rabbit.hurt", "entity.rabbit.hurt", SoundCategory.NEUTRAL),
/* 233 */   RECORDS_STAL("records.stal", "record.stal", SoundCategory.RECORD),
/* 234 */   MUSIC_GAME_NETHER("music.game.nether", "music.nether", SoundCategory.MUSIC),
/* 235 */   MUSIC_MENU("music.menu", "music.menu", SoundCategory.MUSIC),
/* 236 */   RECORDS_MELLOHI("records.mellohi", "record.mellohi", SoundCategory.RECORD),
/* 237 */   RECORDS_CAT("records.cat", "record.cat", SoundCategory.RECORD),
/* 238 */   RECORDS_FAR("records.far", "record.far", SoundCategory.RECORD),
/* 239 */   MUSIC_GAME_END_DRAGON("music.game.end.dragon", "music.dragon", SoundCategory.MUSIC),
/* 240 */   MOB_RABBIT_DEATH("mob.rabbit.death", "entity.rabbit.death", SoundCategory.NEUTRAL),
/* 241 */   MOB_RABBIT_IDLE("mob.rabbit.idle", "entity.rabbit.ambient", SoundCategory.NEUTRAL),
/* 242 */   MUSIC_GAME_END("music.game.end", "music.end", SoundCategory.MUSIC),
/* 243 */   MUSIC_GAME("music.game", "music.game", SoundCategory.MUSIC),
/* 244 */   MOB_GUARDIAN_IDLE("mob.guardian.idle", "entity.elder_guardian.ambient", SoundCategory.HOSTILE),
/* 245 */   RECORDS_WARD("records.ward", "record.ward", SoundCategory.RECORD),
/* 246 */   RECORDS_13("records.13", "record.13", SoundCategory.RECORD),
/* 247 */   MOB_RABBIT_HOP("mob.rabbit.hop", "entity.rabbit.jump", SoundCategory.NEUTRAL),
/* 248 */   RECORDS_STRAD("records.strad", "record.strad", SoundCategory.RECORD),
/* 249 */   RECORDS_11("records.11", "record.11", SoundCategory.RECORD),
/* 250 */   RECORDS_MALL("records.mall", "record.mall", SoundCategory.RECORD),
/* 251 */   RECORDS_BLOCKS("records.blocks", "record.blocks", SoundCategory.RECORD),
/* 252 */   RECORDS_WAIT("records.wait", "record.wait", SoundCategory.RECORD),
/* 253 */   MUSIC_GAME_END_CREDITS("music.game.end.credits", "music.credits", SoundCategory.MUSIC),
/* 254 */   MUSIC_GAME_CREATIVE("music.game.creative", "music.creative", SoundCategory.MUSIC);
/*     */   
/*     */   private final String name;
/*     */   private final String newName;
/*     */   private final SoundCategory category;
/*     */   private final boolean breaksound;
/*     */   private static Map effects;
/*     */   
/*     */   static {
/* 263 */     effects = new HashMap<>(); byte b; int i; SoundEffect[] arrayOfSoundEffect;
/* 264 */     for (i = (arrayOfSoundEffect = values()).length, b = 0; b < i; ) { SoundEffect e = arrayOfSoundEffect[b];
/* 265 */       effects.put(e.getName(), e);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   public String getName() {
/* 270 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getNewName() {
/* 274 */     return this.newName;
/*     */   }
/*     */   
/*     */   public SoundCategory getCategory() {
/* 278 */     return this.category;
/*     */   }
/*     */   
/*     */   public boolean isBreaksound() {
/* 282 */     return this.breaksound;
/*     */   }
/*     */   
/*     */   SoundEffect(String name, String newname, SoundCategory cat2) {
/* 286 */     this.category = cat2;
/* 287 */     this.newName = newname;
/* 288 */     this.name = name;
/* 289 */     this.breaksound = name.startsWith("dig.");
/*     */   }
/*     */   
/*     */   SoundEffect(String name, String newname, SoundCategory cat2, boolean shouldIgnore) {
/* 293 */     this.category = cat2;
/* 294 */     this.newName = newname;
/* 295 */     this.name = name;
/* 296 */     this.breaksound = !(!name.startsWith("dig.") && !shouldIgnore);
/*     */   }
/*     */   
/*     */   public static SoundEffect getByName(String name) {
/* 300 */     name = name.toLowerCase(Locale.ROOT);
/* 301 */     return (SoundEffect)effects.get(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\sound\SoundEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */