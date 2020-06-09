/*     */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.sound;
/*     */ 
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.registry.RegistryNamespaced;
/*     */ 
/*     */ public class SoundRegistry {
/*   8 */   public static final RegistryNamespaced REGISTRY = new RegistryNamespaced();
/*     */   private final ResourceLocation soundName;
/*  10 */   private static int soundEventId = 0;
/*     */   private static boolean registered;
/*     */   
/*     */   public SoundRegistry(ResourceLocation soundNameIn) {
/*  14 */     this.soundName = soundNameIn;
/*     */   }
/*     */   
/*     */   public ResourceLocation getSoundName() {
/*  18 */     return this.soundName;
/*     */   }
/*     */   
/*     */   public static void registerSounds() {
/*  22 */     if (registered) {
/*     */       return;
/*     */     }
/*  25 */     registered = true;
/*  26 */     registerSound("ambient.cave");
/*  27 */     registerSound("block.anvil.break");
/*  28 */     registerSound("block.anvil.destroy");
/*  29 */     registerSound("block.anvil.fall");
/*  30 */     registerSound("block.anvil.hit");
/*  31 */     registerSound("block.anvil.land");
/*  32 */     registerSound("block.anvil.place");
/*  33 */     registerSound("block.anvil.step");
/*  34 */     registerSound("block.anvil.use");
/*  35 */     registerSound("block.brewing_stand.brew");
/*  36 */     registerSound("block.chest.close");
/*  37 */     registerSound("block.chest.locked");
/*  38 */     registerSound("block.chest.open");
/*  39 */     registerSound("block.chorus_flower.death");
/*  40 */     registerSound("block.chorus_flower.grow");
/*  41 */     registerSound("block.cloth.break");
/*  42 */     registerSound("block.cloth.fall");
/*  43 */     registerSound("block.cloth.hit");
/*  44 */     registerSound("block.cloth.place");
/*  45 */     registerSound("block.cloth.step");
/*  46 */     registerSound("block.comparator.click");
/*  47 */     registerSound("block.dispenser.dispense");
/*  48 */     registerSound("block.dispenser.fail");
/*  49 */     registerSound("block.dispenser.launch");
/*  50 */     registerSound("block.end_gateway.spawn");
/*  51 */     registerSound("block.enderchest.close");
/*  52 */     registerSound("block.enderchest.open");
/*  53 */     registerSound("block.fence_gate.close");
/*  54 */     registerSound("block.fence_gate.open");
/*  55 */     registerSound("block.fire.ambient");
/*  56 */     registerSound("block.fire.extinguish");
/*  57 */     registerSound("block.furnace.fire_crackle");
/*  58 */     registerSound("block.glass.break");
/*  59 */     registerSound("block.glass.fall");
/*  60 */     registerSound("block.glass.hit");
/*  61 */     registerSound("block.glass.place");
/*  62 */     registerSound("block.glass.step");
/*  63 */     registerSound("block.grass.break");
/*  64 */     registerSound("block.grass.fall");
/*  65 */     registerSound("block.grass.hit");
/*  66 */     registerSound("block.grass.place");
/*  67 */     registerSound("block.grass.step");
/*  68 */     registerSound("block.gravel.break");
/*  69 */     registerSound("block.gravel.fall");
/*  70 */     registerSound("block.gravel.hit");
/*  71 */     registerSound("block.gravel.place");
/*  72 */     registerSound("block.gravel.step");
/*  73 */     registerSound("block.iron_door.close");
/*  74 */     registerSound("block.iron_door.open");
/*  75 */     registerSound("block.iron_trapdoor.close");
/*  76 */     registerSound("block.iron_trapdoor.open");
/*  77 */     registerSound("block.ladder.break");
/*  78 */     registerSound("block.ladder.fall");
/*  79 */     registerSound("block.ladder.hit");
/*  80 */     registerSound("block.ladder.place");
/*  81 */     registerSound("block.ladder.step");
/*  82 */     registerSound("block.lava.ambient");
/*  83 */     registerSound("block.lava.extinguish");
/*  84 */     registerSound("block.lava.pop");
/*  85 */     registerSound("block.lever.click");
/*  86 */     registerSound("block.metal.break");
/*  87 */     registerSound("block.metal.fall");
/*  88 */     registerSound("block.metal.hit");
/*  89 */     registerSound("block.metal.place");
/*  90 */     registerSound("block.metal.step");
/*  91 */     registerSound("block.metal_pressureplate.click_off");
/*  92 */     registerSound("block.metal_pressureplate.click_on");
/*  93 */     registerSound("block.note.basedrum");
/*  94 */     registerSound("block.note.bass");
/*  95 */     registerSound("block.note.harp");
/*  96 */     registerSound("block.note.hat");
/*  97 */     registerSound("block.note.pling");
/*  98 */     registerSound("block.note.snare");
/*  99 */     registerSound("block.piston.contract");
/* 100 */     registerSound("block.piston.extend");
/* 101 */     registerSound("block.portal.ambient");
/* 102 */     registerSound("block.portal.travel");
/* 103 */     registerSound("block.portal.trigger");
/* 104 */     registerSound("block.redstone_torch.burnout");
/* 105 */     registerSound("block.sand.break");
/* 106 */     registerSound("block.sand.fall");
/* 107 */     registerSound("block.sand.hit");
/* 108 */     registerSound("block.sand.place");
/* 109 */     registerSound("block.sand.step");
/* 110 */     registerSound("block.slime.break");
/* 111 */     registerSound("block.slime.fall");
/* 112 */     registerSound("block.slime.hit");
/* 113 */     registerSound("block.slime.place");
/* 114 */     registerSound("block.slime.step");
/* 115 */     registerSound("block.snow.break");
/* 116 */     registerSound("block.snow.fall");
/* 117 */     registerSound("block.snow.hit");
/* 118 */     registerSound("block.snow.place");
/* 119 */     registerSound("block.snow.step");
/* 120 */     registerSound("block.stone.break");
/* 121 */     registerSound("block.stone.fall");
/* 122 */     registerSound("block.stone.hit");
/* 123 */     registerSound("block.stone.place");
/* 124 */     registerSound("block.stone.step");
/* 125 */     registerSound("block.stone_button.click_off");
/* 126 */     registerSound("block.stone_button.click_on");
/* 127 */     registerSound("block.stone_pressureplate.click_off");
/* 128 */     registerSound("block.stone_pressureplate.click_on");
/* 129 */     registerSound("block.tripwire.attach");
/* 130 */     registerSound("block.tripwire.click_off");
/* 131 */     registerSound("block.tripwire.click_on");
/* 132 */     registerSound("block.tripwire.detach");
/* 133 */     registerSound("block.water.ambient");
/* 134 */     registerSound("block.waterlily.place");
/* 135 */     registerSound("block.wood.break");
/* 136 */     registerSound("block.wood.fall");
/* 137 */     registerSound("block.wood.hit");
/* 138 */     registerSound("block.wood.place");
/* 139 */     registerSound("block.wood.step");
/* 140 */     registerSound("block.wood_button.click_off");
/* 141 */     registerSound("block.wood_button.click_on");
/* 142 */     registerSound("block.wood_pressureplate.click_off");
/* 143 */     registerSound("block.wood_pressureplate.click_on");
/* 144 */     registerSound("block.wooden_door.close");
/* 145 */     registerSound("block.wooden_door.open");
/* 146 */     registerSound("block.wooden_trapdoor.close");
/* 147 */     registerSound("block.wooden_trapdoor.open");
/* 148 */     registerSound("enchant.thorns.hit");
/* 149 */     registerSound("entity.armorstand.break");
/* 150 */     registerSound("entity.armorstand.fall");
/* 151 */     registerSound("entity.armorstand.hit");
/* 152 */     registerSound("entity.armorstand.place");
/* 153 */     registerSound("entity.arrow.hit");
/* 154 */     registerSound("entity.arrow.hit_player");
/* 155 */     registerSound("entity.arrow.shoot");
/* 156 */     registerSound("entity.bat.ambient");
/* 157 */     registerSound("entity.bat.death");
/* 158 */     registerSound("entity.bat.hurt");
/* 159 */     registerSound("entity.bat.loop");
/* 160 */     registerSound("entity.bat.takeoff");
/* 161 */     registerSound("entity.blaze.ambient");
/* 162 */     registerSound("entity.blaze.burn");
/* 163 */     registerSound("entity.blaze.death");
/* 164 */     registerSound("entity.blaze.hurt");
/* 165 */     registerSound("entity.blaze.shoot");
/* 166 */     registerSound("entity.bobber.splash");
/* 167 */     registerSound("entity.bobber.throw");
/* 168 */     registerSound("entity.cat.ambient");
/* 169 */     registerSound("entity.cat.death");
/* 170 */     registerSound("entity.cat.hiss");
/* 171 */     registerSound("entity.cat.hurt");
/* 172 */     registerSound("entity.cat.purr");
/* 173 */     registerSound("entity.cat.purreow");
/* 174 */     registerSound("entity.chicken.ambient");
/* 175 */     registerSound("entity.chicken.death");
/* 176 */     registerSound("entity.chicken.egg");
/* 177 */     registerSound("entity.chicken.hurt");
/* 178 */     registerSound("entity.chicken.step");
/* 179 */     registerSound("entity.cow.ambient");
/* 180 */     registerSound("entity.cow.death");
/* 181 */     registerSound("entity.cow.hurt");
/* 182 */     registerSound("entity.cow.milk");
/* 183 */     registerSound("entity.cow.step");
/* 184 */     registerSound("entity.creeper.death");
/* 185 */     registerSound("entity.creeper.hurt");
/* 186 */     registerSound("entity.creeper.primed");
/* 187 */     registerSound("entity.donkey.ambient");
/* 188 */     registerSound("entity.donkey.angry");
/* 189 */     registerSound("entity.donkey.chest");
/* 190 */     registerSound("entity.donkey.death");
/* 191 */     registerSound("entity.donkey.hurt");
/* 192 */     registerSound("entity.egg.throw");
/* 193 */     registerSound("entity.elder_guardian.ambient");
/* 194 */     registerSound("entity.elder_guardian.ambient_land");
/* 195 */     registerSound("entity.elder_guardian.curse");
/* 196 */     registerSound("entity.elder_guardian.death");
/* 197 */     registerSound("entity.elder_guardian.death_land");
/* 198 */     registerSound("entity.elder_guardian.hurt");
/* 199 */     registerSound("entity.elder_guardian.hurt_land");
/* 200 */     registerSound("entity.enderdragon.ambient");
/* 201 */     registerSound("entity.enderdragon.death");
/* 202 */     registerSound("entity.enderdragon.flap");
/* 203 */     registerSound("entity.enderdragon.growl");
/* 204 */     registerSound("entity.enderdragon.hurt");
/* 205 */     registerSound("entity.enderdragon.shoot");
/* 206 */     registerSound("entity.enderdragon_fireball.explode");
/* 207 */     registerSound("entity.endereye.launch");
/* 208 */     registerSound("entity.endermen.ambient");
/* 209 */     registerSound("entity.endermen.death");
/* 210 */     registerSound("entity.endermen.hurt");
/* 211 */     registerSound("entity.endermen.scream");
/* 212 */     registerSound("entity.endermen.stare");
/* 213 */     registerSound("entity.endermen.teleport");
/* 214 */     registerSound("entity.endermite.ambient");
/* 215 */     registerSound("entity.endermite.death");
/* 216 */     registerSound("entity.endermite.hurt");
/* 217 */     registerSound("entity.endermite.step");
/* 218 */     registerSound("entity.enderpearl.throw");
/* 219 */     registerSound("entity.experience_bottle.throw");
/* 220 */     registerSound("entity.experience_orb.pickup");
/* 221 */     registerSound("entity.experience_orb.touch");
/* 222 */     registerSound("entity.firework.blast");
/* 223 */     registerSound("entity.firework.blast_far");
/* 224 */     registerSound("entity.firework.large_blast");
/* 225 */     registerSound("entity.firework.large_blast_far");
/* 226 */     registerSound("entity.firework.launch");
/* 227 */     registerSound("entity.firework.shoot");
/* 228 */     registerSound("entity.firework.twinkle");
/* 229 */     registerSound("entity.firework.twinkle_far");
/* 230 */     registerSound("entity.generic.big_fall");
/* 231 */     registerSound("entity.generic.burn");
/* 232 */     registerSound("entity.generic.death");
/* 233 */     registerSound("entity.generic.drink");
/* 234 */     registerSound("entity.generic.eat");
/* 235 */     registerSound("entity.generic.explode");
/* 236 */     registerSound("entity.generic.extinguish_fire");
/* 237 */     registerSound("entity.generic.hurt");
/* 238 */     registerSound("entity.generic.small_fall");
/* 239 */     registerSound("entity.generic.splash");
/* 240 */     registerSound("entity.generic.swim");
/* 241 */     registerSound("entity.ghast.ambient");
/* 242 */     registerSound("entity.ghast.death");
/* 243 */     registerSound("entity.ghast.hurt");
/* 244 */     registerSound("entity.ghast.scream");
/* 245 */     registerSound("entity.ghast.shoot");
/* 246 */     registerSound("entity.ghast.warn");
/* 247 */     registerSound("entity.guardian.ambient");
/* 248 */     registerSound("entity.guardian.ambient_land");
/* 249 */     registerSound("entity.guardian.attack");
/* 250 */     registerSound("entity.guardian.death");
/* 251 */     registerSound("entity.guardian.death_land");
/* 252 */     registerSound("entity.guardian.flop");
/* 253 */     registerSound("entity.guardian.hurt");
/* 254 */     registerSound("entity.guardian.hurt_land");
/* 255 */     registerSound("entity.horse.ambient");
/* 256 */     registerSound("entity.horse.angry");
/* 257 */     registerSound("entity.horse.armor");
/* 258 */     registerSound("entity.horse.breathe");
/* 259 */     registerSound("entity.horse.death");
/* 260 */     registerSound("entity.horse.eat");
/* 261 */     registerSound("entity.horse.gallop");
/* 262 */     registerSound("entity.horse.hurt");
/* 263 */     registerSound("entity.horse.jump");
/* 264 */     registerSound("entity.horse.land");
/* 265 */     registerSound("entity.horse.saddle");
/* 266 */     registerSound("entity.horse.step");
/* 267 */     registerSound("entity.horse.step_wood");
/* 268 */     registerSound("entity.hostile.big_fall");
/* 269 */     registerSound("entity.hostile.death");
/* 270 */     registerSound("entity.hostile.hurt");
/* 271 */     registerSound("entity.hostile.small_fall");
/* 272 */     registerSound("entity.hostile.splash");
/* 273 */     registerSound("entity.hostile.swim");
/* 274 */     registerSound("entity.irongolem.attack");
/* 275 */     registerSound("entity.irongolem.death");
/* 276 */     registerSound("entity.irongolem.hurt");
/* 277 */     registerSound("entity.irongolem.step");
/* 278 */     registerSound("entity.item.break");
/* 279 */     registerSound("entity.item.pickup");
/* 280 */     registerSound("entity.itemframe.add_item");
/* 281 */     registerSound("entity.itemframe.break");
/* 282 */     registerSound("entity.itemframe.place");
/* 283 */     registerSound("entity.itemframe.remove_item");
/* 284 */     registerSound("entity.itemframe.rotate_item");
/* 285 */     registerSound("entity.leashknot.break");
/* 286 */     registerSound("entity.leashknot.place");
/* 287 */     registerSound("entity.lightning.impact");
/* 288 */     registerSound("entity.lightning.thunder");
/* 289 */     registerSound("entity.lingeringpotion.throw");
/* 290 */     registerSound("entity.magmacube.death");
/* 291 */     registerSound("entity.magmacube.hurt");
/* 292 */     registerSound("entity.magmacube.jump");
/* 293 */     registerSound("entity.magmacube.squish");
/* 294 */     registerSound("entity.minecart.inside");
/* 295 */     registerSound("entity.minecart.riding");
/* 296 */     registerSound("entity.mooshroom.shear");
/* 297 */     registerSound("entity.mule.ambient");
/* 298 */     registerSound("entity.mule.death");
/* 299 */     registerSound("entity.mule.hurt");
/* 300 */     registerSound("entity.painting.break");
/* 301 */     registerSound("entity.painting.place");
/* 302 */     registerSound("entity.pig.ambient");
/* 303 */     registerSound("entity.pig.death");
/* 304 */     registerSound("entity.pig.hurt");
/* 305 */     registerSound("entity.pig.saddle");
/* 306 */     registerSound("entity.pig.step");
/* 307 */     registerSound("entity.player.attack.crit");
/* 308 */     registerSound("entity.player.attack.knockback");
/* 309 */     registerSound("entity.player.attack.nodamage");
/* 310 */     registerSound("entity.player.attack.strong");
/* 311 */     registerSound("entity.player.attack.sweep");
/* 312 */     registerSound("entity.player.attack.weak");
/* 313 */     registerSound("entity.player.big_fall");
/* 314 */     registerSound("entity.player.breath");
/* 315 */     registerSound("entity.player.burp");
/* 316 */     registerSound("entity.player.death");
/* 317 */     registerSound("entity.player.hurt");
/* 318 */     registerSound("entity.player.levelup");
/* 319 */     registerSound("entity.player.small_fall");
/* 320 */     registerSound("entity.player.splash");
/* 321 */     registerSound("entity.player.swim");
/* 322 */     registerSound("entity.rabbit.ambient");
/* 323 */     registerSound("entity.rabbit.attack");
/* 324 */     registerSound("entity.rabbit.death");
/* 325 */     registerSound("entity.rabbit.hurt");
/* 326 */     registerSound("entity.rabbit.jump");
/* 327 */     registerSound("entity.sheep.ambient");
/* 328 */     registerSound("entity.sheep.death");
/* 329 */     registerSound("entity.sheep.hurt");
/* 330 */     registerSound("entity.sheep.shear");
/* 331 */     registerSound("entity.sheep.step");
/* 332 */     registerSound("entity.shulker.ambient");
/* 333 */     registerSound("entity.shulker.close");
/* 334 */     registerSound("entity.shulker.death");
/* 335 */     registerSound("entity.shulker.hurt");
/* 336 */     registerSound("entity.shulker.hurt_closed");
/* 337 */     registerSound("entity.shulker.open");
/* 338 */     registerSound("entity.shulker.shoot");
/* 339 */     registerSound("entity.shulker.teleport");
/* 340 */     registerSound("entity.shulker_bullet.hit");
/* 341 */     registerSound("entity.shulker_bullet.hurt");
/* 342 */     registerSound("entity.silverfish.ambient");
/* 343 */     registerSound("entity.silverfish.death");
/* 344 */     registerSound("entity.silverfish.hurt");
/* 345 */     registerSound("entity.silverfish.step");
/* 346 */     registerSound("entity.skeleton.ambient");
/* 347 */     registerSound("entity.skeleton.death");
/* 348 */     registerSound("entity.skeleton.hurt");
/* 349 */     registerSound("entity.skeleton.shoot");
/* 350 */     registerSound("entity.skeleton.step");
/* 351 */     registerSound("entity.skeleton_horse.ambient");
/* 352 */     registerSound("entity.skeleton_horse.death");
/* 353 */     registerSound("entity.skeleton_horse.hurt");
/* 354 */     registerSound("entity.slime.attack");
/* 355 */     registerSound("entity.slime.death");
/* 356 */     registerSound("entity.slime.hurt");
/* 357 */     registerSound("entity.slime.jump");
/* 358 */     registerSound("entity.slime.squish");
/* 359 */     registerSound("entity.small_magmacube.death");
/* 360 */     registerSound("entity.small_magmacube.hurt");
/* 361 */     registerSound("entity.small_magmacube.squish");
/* 362 */     registerSound("entity.small_slime.death");
/* 363 */     registerSound("entity.small_slime.hurt");
/* 364 */     registerSound("entity.small_slime.jump");
/* 365 */     registerSound("entity.small_slime.squish");
/* 366 */     registerSound("entity.snowball.throw");
/* 367 */     registerSound("entity.snowman.ambient");
/* 368 */     registerSound("entity.snowman.death");
/* 369 */     registerSound("entity.snowman.hurt");
/* 370 */     registerSound("entity.snowman.shoot");
/* 371 */     registerSound("entity.spider.ambient");
/* 372 */     registerSound("entity.spider.death");
/* 373 */     registerSound("entity.spider.hurt");
/* 374 */     registerSound("entity.spider.step");
/* 375 */     registerSound("entity.splash_potion.break");
/* 376 */     registerSound("entity.splash_potion.throw");
/* 377 */     registerSound("entity.squid.ambient");
/* 378 */     registerSound("entity.squid.death");
/* 379 */     registerSound("entity.squid.hurt");
/* 380 */     registerSound("entity.tnt.primed");
/* 381 */     registerSound("entity.villager.ambient");
/* 382 */     registerSound("entity.villager.death");
/* 383 */     registerSound("entity.villager.hurt");
/* 384 */     registerSound("entity.villager.no");
/* 385 */     registerSound("entity.villager.trading");
/* 386 */     registerSound("entity.villager.yes");
/* 387 */     registerSound("entity.witch.ambient");
/* 388 */     registerSound("entity.witch.death");
/* 389 */     registerSound("entity.witch.drink");
/* 390 */     registerSound("entity.witch.hurt");
/* 391 */     registerSound("entity.witch.throw");
/* 392 */     registerSound("entity.wither.ambient");
/* 393 */     registerSound("entity.wither.break_block");
/* 394 */     registerSound("entity.wither.death");
/* 395 */     registerSound("entity.wither.hurt");
/* 396 */     registerSound("entity.wither.shoot");
/* 397 */     registerSound("entity.wither.spawn");
/* 398 */     registerSound("entity.wolf.ambient");
/* 399 */     registerSound("entity.wolf.death");
/* 400 */     registerSound("entity.wolf.growl");
/* 401 */     registerSound("entity.wolf.howl");
/* 402 */     registerSound("entity.wolf.hurt");
/* 403 */     registerSound("entity.wolf.pant");
/* 404 */     registerSound("entity.wolf.shake");
/* 405 */     registerSound("entity.wolf.step");
/* 406 */     registerSound("entity.wolf.whine");
/* 407 */     registerSound("entity.zombie.ambient");
/* 408 */     registerSound("entity.zombie.attack_door_wood");
/* 409 */     registerSound("entity.zombie.attack_iron_door");
/* 410 */     registerSound("entity.zombie.break_door_wood");
/* 411 */     registerSound("entity.zombie.death");
/* 412 */     registerSound("entity.zombie.hurt");
/* 413 */     registerSound("entity.zombie.infect");
/* 414 */     registerSound("entity.zombie.step");
/* 415 */     registerSound("entity.zombie_horse.ambient");
/* 416 */     registerSound("entity.zombie_horse.death");
/* 417 */     registerSound("entity.zombie_horse.hurt");
/* 418 */     registerSound("entity.zombie_pig.ambient");
/* 419 */     registerSound("entity.zombie_pig.angry");
/* 420 */     registerSound("entity.zombie_pig.death");
/* 421 */     registerSound("entity.zombie_pig.hurt");
/* 422 */     registerSound("entity.zombie_villager.ambient");
/* 423 */     registerSound("entity.zombie_villager.converted");
/* 424 */     registerSound("entity.zombie_villager.cure");
/* 425 */     registerSound("entity.zombie_villager.death");
/* 426 */     registerSound("entity.zombie_villager.hurt");
/* 427 */     registerSound("entity.zombie_villager.step");
/* 428 */     registerSound("item.armor.equip_chain");
/* 429 */     registerSound("item.armor.equip_diamond");
/* 430 */     registerSound("item.armor.equip_generic");
/* 431 */     registerSound("item.armor.equip_gold");
/* 432 */     registerSound("item.armor.equip_iron");
/* 433 */     registerSound("item.armor.equip_leather");
/* 434 */     registerSound("item.bottle.fill");
/* 435 */     registerSound("item.bottle.fill_dragonbreath");
/* 436 */     registerSound("item.bucket.empty");
/* 437 */     registerSound("item.bucket.empty_lava");
/* 438 */     registerSound("item.bucket.fill");
/* 439 */     registerSound("item.bucket.fill_lava");
/* 440 */     registerSound("item.chorus_fruit.teleport");
/* 441 */     registerSound("item.elytra.flying");
/* 442 */     registerSound("item.firecharge.use");
/* 443 */     registerSound("item.flintandsteel.use");
/* 444 */     registerSound("item.hoe.till");
/* 445 */     registerSound("item.shield.block");
/* 446 */     registerSound("item.shield.break");
/* 447 */     registerSound("item.shovel.flatten");
/* 448 */     registerSound("music.creative");
/* 449 */     registerSound("music.credits");
/* 450 */     registerSound("music.dragon");
/* 451 */     registerSound("music.end");
/* 452 */     registerSound("music.game");
/* 453 */     registerSound("music.menu");
/* 454 */     registerSound("music.nether");
/* 455 */     registerSound("record.11");
/* 456 */     registerSound("record.13");
/* 457 */     registerSound("record.blocks");
/* 458 */     registerSound("record.cat");
/* 459 */     registerSound("record.chirp");
/* 460 */     registerSound("record.far");
/* 461 */     registerSound("record.mall");
/* 462 */     registerSound("record.mellohi");
/* 463 */     registerSound("record.stal");
/* 464 */     registerSound("record.strad");
/* 465 */     registerSound("record.wait");
/* 466 */     registerSound("record.ward");
/* 467 */     registerSound("ui.button.click");
/* 468 */     registerSound("weather.rain");
/* 469 */     registerSound("weather.rain.above");
/*     */   }
/*     */   
/*     */   private static void registerSound(String soundNameIn) {
/* 473 */     ResourceLocation resourcelocation = new ResourceLocation(soundNameIn);
/* 474 */     REGISTRY.register(soundEventId++, resourcelocation, new SoundEvent(resourcelocation));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\sound\SoundRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */