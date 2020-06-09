/*      */ package net.minecraft.init;
/*      */ 
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SoundEvents
/*      */ {
/*      */   private static SoundEvent getRegisteredSoundEvent(String id) {
/*  560 */     SoundEvent soundevent = (SoundEvent)SoundEvent.REGISTRY.getObject(new ResourceLocation(id));
/*      */     
/*  562 */     if (soundevent == null)
/*      */     {
/*  564 */       throw new IllegalStateException("Invalid Sound requested: " + id);
/*      */     }
/*      */ 
/*      */     
/*  568 */     return soundevent;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  574 */     if (!Bootstrap.isRegistered())
/*      */     {
/*  576 */       throw new RuntimeException("Accessed Sounds before Bootstrap!");
/*      */     }
/*      */   }
/*      */   
/*  580 */   public static final SoundEvent AMBIENT_CAVE = getRegisteredSoundEvent("ambient.cave");
/*  581 */   public static final SoundEvent BLOCK_ANVIL_BREAK = getRegisteredSoundEvent("block.anvil.break");
/*  582 */   public static final SoundEvent BLOCK_ANVIL_DESTROY = getRegisteredSoundEvent("block.anvil.destroy");
/*  583 */   public static final SoundEvent BLOCK_ANVIL_FALL = getRegisteredSoundEvent("block.anvil.fall");
/*  584 */   public static final SoundEvent BLOCK_ANVIL_HIT = getRegisteredSoundEvent("block.anvil.hit");
/*  585 */   public static final SoundEvent BLOCK_ANVIL_LAND = getRegisteredSoundEvent("block.anvil.land");
/*  586 */   public static final SoundEvent BLOCK_ANVIL_PLACE = getRegisteredSoundEvent("block.anvil.place");
/*  587 */   public static final SoundEvent BLOCK_ANVIL_STEP = getRegisteredSoundEvent("block.anvil.step");
/*  588 */   public static final SoundEvent BLOCK_ANVIL_USE = getRegisteredSoundEvent("block.anvil.use");
/*  589 */   public static final SoundEvent ENTITY_ARMORSTAND_BREAK = getRegisteredSoundEvent("entity.armorstand.break");
/*  590 */   public static final SoundEvent ENTITY_ARMORSTAND_FALL = getRegisteredSoundEvent("entity.armorstand.fall");
/*  591 */   public static final SoundEvent ENTITY_ARMORSTAND_HIT = getRegisteredSoundEvent("entity.armorstand.hit");
/*  592 */   public static final SoundEvent ENTITY_ARMORSTAND_PLACE = getRegisteredSoundEvent("entity.armorstand.place");
/*  593 */   public static final SoundEvent ITEM_ARMOR_EQUIP_CHAIN = getRegisteredSoundEvent("item.armor.equip_chain");
/*  594 */   public static final SoundEvent ITEM_ARMOR_EQUIP_DIAMOND = getRegisteredSoundEvent("item.armor.equip_diamond");
/*  595 */   public static final SoundEvent field_191258_p = getRegisteredSoundEvent("item.armor.equip_elytra");
/*  596 */   public static final SoundEvent ITEM_ARMOR_EQUIP_GENERIC = getRegisteredSoundEvent("item.armor.equip_generic");
/*  597 */   public static final SoundEvent ITEM_ARMOR_EQUIP_GOLD = getRegisteredSoundEvent("item.armor.equip_gold");
/*  598 */   public static final SoundEvent ITEM_ARMOR_EQUIP_IRON = getRegisteredSoundEvent("item.armor.equip_iron");
/*  599 */   public static final SoundEvent ITEM_ARMOR_EQUIP_LEATHER = getRegisteredSoundEvent("item.armor.equip_leather");
/*  600 */   public static final SoundEvent ENTITY_ARROW_HIT = getRegisteredSoundEvent("entity.arrow.hit");
/*  601 */   public static final SoundEvent ENTITY_ARROW_HIT_PLAYER = getRegisteredSoundEvent("entity.arrow.hit_player");
/*  602 */   public static final SoundEvent ENTITY_ARROW_SHOOT = getRegisteredSoundEvent("entity.arrow.shoot");
/*  603 */   public static final SoundEvent ENTITY_BAT_AMBIENT = getRegisteredSoundEvent("entity.bat.ambient");
/*  604 */   public static final SoundEvent ENTITY_BAT_DEATH = getRegisteredSoundEvent("entity.bat.death");
/*  605 */   public static final SoundEvent ENTITY_BAT_HURT = getRegisteredSoundEvent("entity.bat.hurt");
/*  606 */   public static final SoundEvent ENTITY_BAT_LOOP = getRegisteredSoundEvent("entity.bat.loop");
/*  607 */   public static final SoundEvent ENTITY_BAT_TAKEOFF = getRegisteredSoundEvent("entity.bat.takeoff");
/*  608 */   public static final SoundEvent ENTITY_BLAZE_AMBIENT = getRegisteredSoundEvent("entity.blaze.ambient");
/*  609 */   public static final SoundEvent ENTITY_BLAZE_BURN = getRegisteredSoundEvent("entity.blaze.burn");
/*  610 */   public static final SoundEvent ENTITY_BLAZE_DEATH = getRegisteredSoundEvent("entity.blaze.death");
/*  611 */   public static final SoundEvent ENTITY_BLAZE_HURT = getRegisteredSoundEvent("entity.blaze.hurt");
/*  612 */   public static final SoundEvent ENTITY_BLAZE_SHOOT = getRegisteredSoundEvent("entity.blaze.shoot");
/*  613 */   public static final SoundEvent field_193778_H = getRegisteredSoundEvent("entity.boat.paddle_land");
/*  614 */   public static final SoundEvent field_193779_I = getRegisteredSoundEvent("entity.boat.paddle_water");
/*  615 */   public static final SoundEvent field_193780_J = getRegisteredSoundEvent("entity.bobber.retrieve");
/*  616 */   public static final SoundEvent ENTITY_BOBBER_SPLASH = getRegisteredSoundEvent("entity.bobber.splash");
/*  617 */   public static final SoundEvent ENTITY_BOBBER_THROW = getRegisteredSoundEvent("entity.bobber.throw");
/*  618 */   public static final SoundEvent field_191241_J = getRegisteredSoundEvent("item.bottle.empty");
/*  619 */   public static final SoundEvent ITEM_BOTTLE_FILL = getRegisteredSoundEvent("item.bottle.fill");
/*  620 */   public static final SoundEvent ITEM_BOTTLE_FILL_DRAGONBREATH = getRegisteredSoundEvent("item.bottle.fill_dragonbreath");
/*  621 */   public static final SoundEvent BLOCK_BREWING_STAND_BREW = getRegisteredSoundEvent("block.brewing_stand.brew");
/*  622 */   public static final SoundEvent ITEM_BUCKET_EMPTY = getRegisteredSoundEvent("item.bucket.empty");
/*  623 */   public static final SoundEvent ITEM_BUCKET_EMPTY_LAVA = getRegisteredSoundEvent("item.bucket.empty_lava");
/*  624 */   public static final SoundEvent ITEM_BUCKET_FILL = getRegisteredSoundEvent("item.bucket.fill");
/*  625 */   public static final SoundEvent ITEM_BUCKET_FILL_LAVA = getRegisteredSoundEvent("item.bucket.fill_lava");
/*  626 */   public static final SoundEvent ENTITY_CAT_AMBIENT = getRegisteredSoundEvent("entity.cat.ambient");
/*  627 */   public static final SoundEvent ENTITY_CAT_DEATH = getRegisteredSoundEvent("entity.cat.death");
/*  628 */   public static final SoundEvent ENTITY_CAT_HISS = getRegisteredSoundEvent("entity.cat.hiss");
/*  629 */   public static final SoundEvent ENTITY_CAT_HURT = getRegisteredSoundEvent("entity.cat.hurt");
/*  630 */   public static final SoundEvent ENTITY_CAT_PURR = getRegisteredSoundEvent("entity.cat.purr");
/*  631 */   public static final SoundEvent ENTITY_CAT_PURREOW = getRegisteredSoundEvent("entity.cat.purreow");
/*  632 */   public static final SoundEvent BLOCK_CHEST_CLOSE = getRegisteredSoundEvent("block.chest.close");
/*  633 */   public static final SoundEvent BLOCK_CHEST_LOCKED = getRegisteredSoundEvent("block.chest.locked");
/*  634 */   public static final SoundEvent BLOCK_CHEST_OPEN = getRegisteredSoundEvent("block.chest.open");
/*  635 */   public static final SoundEvent ENTITY_CHICKEN_AMBIENT = getRegisteredSoundEvent("entity.chicken.ambient");
/*  636 */   public static final SoundEvent ENTITY_CHICKEN_DEATH = getRegisteredSoundEvent("entity.chicken.death");
/*  637 */   public static final SoundEvent ENTITY_CHICKEN_EGG = getRegisteredSoundEvent("entity.chicken.egg");
/*  638 */   public static final SoundEvent ENTITY_CHICKEN_HURT = getRegisteredSoundEvent("entity.chicken.hurt");
/*  639 */   public static final SoundEvent ENTITY_CHICKEN_STEP = getRegisteredSoundEvent("entity.chicken.step");
/*  640 */   public static final SoundEvent BLOCK_CHORUS_FLOWER_DEATH = getRegisteredSoundEvent("block.chorus_flower.death");
/*  641 */   public static final SoundEvent BLOCK_CHORUS_FLOWER_GROW = getRegisteredSoundEvent("block.chorus_flower.grow");
/*  642 */   public static final SoundEvent ITEM_CHORUS_FRUIT_TELEPORT = getRegisteredSoundEvent("item.chorus_fruit.teleport");
/*  643 */   public static final SoundEvent BLOCK_CLOTH_BREAK = getRegisteredSoundEvent("block.cloth.break");
/*  644 */   public static final SoundEvent BLOCK_CLOTH_FALL = getRegisteredSoundEvent("block.cloth.fall");
/*  645 */   public static final SoundEvent BLOCK_CLOTH_HIT = getRegisteredSoundEvent("block.cloth.hit");
/*  646 */   public static final SoundEvent BLOCK_CLOTH_PLACE = getRegisteredSoundEvent("block.cloth.place");
/*  647 */   public static final SoundEvent BLOCK_CLOTH_STEP = getRegisteredSoundEvent("block.cloth.step");
/*  648 */   public static final SoundEvent BLOCK_COMPARATOR_CLICK = getRegisteredSoundEvent("block.comparator.click");
/*  649 */   public static final SoundEvent ENTITY_COW_AMBIENT = getRegisteredSoundEvent("entity.cow.ambient");
/*  650 */   public static final SoundEvent ENTITY_COW_DEATH = getRegisteredSoundEvent("entity.cow.death");
/*  651 */   public static final SoundEvent ENTITY_COW_HURT = getRegisteredSoundEvent("entity.cow.hurt");
/*  652 */   public static final SoundEvent ENTITY_COW_MILK = getRegisteredSoundEvent("entity.cow.milk");
/*  653 */   public static final SoundEvent ENTITY_COW_STEP = getRegisteredSoundEvent("entity.cow.step");
/*  654 */   public static final SoundEvent ENTITY_CREEPER_DEATH = getRegisteredSoundEvent("entity.creeper.death");
/*  655 */   public static final SoundEvent ENTITY_CREEPER_HURT = getRegisteredSoundEvent("entity.creeper.hurt");
/*  656 */   public static final SoundEvent ENTITY_CREEPER_PRIMED = getRegisteredSoundEvent("entity.creeper.primed");
/*  657 */   public static final SoundEvent BLOCK_DISPENSER_DISPENSE = getRegisteredSoundEvent("block.dispenser.dispense");
/*  658 */   public static final SoundEvent BLOCK_DISPENSER_FAIL = getRegisteredSoundEvent("block.dispenser.fail");
/*  659 */   public static final SoundEvent BLOCK_DISPENSER_LAUNCH = getRegisteredSoundEvent("block.dispenser.launch");
/*  660 */   public static final SoundEvent ENTITY_DONKEY_AMBIENT = getRegisteredSoundEvent("entity.donkey.ambient");
/*  661 */   public static final SoundEvent ENTITY_DONKEY_ANGRY = getRegisteredSoundEvent("entity.donkey.angry");
/*  662 */   public static final SoundEvent ENTITY_DONKEY_CHEST = getRegisteredSoundEvent("entity.donkey.chest");
/*  663 */   public static final SoundEvent ENTITY_DONKEY_DEATH = getRegisteredSoundEvent("entity.donkey.death");
/*  664 */   public static final SoundEvent ENTITY_DONKEY_HURT = getRegisteredSoundEvent("entity.donkey.hurt");
/*  665 */   public static final SoundEvent ENTITY_EGG_THROW = getRegisteredSoundEvent("entity.egg.throw");
/*  666 */   public static final SoundEvent ENTITY_ELDER_GUARDIAN_AMBIENT = getRegisteredSoundEvent("entity.elder_guardian.ambient");
/*  667 */   public static final SoundEvent ENTITY_ELDERGUARDIAN_AMBIENTLAND = getRegisteredSoundEvent("entity.elder_guardian.ambient_land");
/*  668 */   public static final SoundEvent ENTITY_ELDER_GUARDIAN_CURSE = getRegisteredSoundEvent("entity.elder_guardian.curse");
/*  669 */   public static final SoundEvent ENTITY_ELDER_GUARDIAN_DEATH = getRegisteredSoundEvent("entity.elder_guardian.death");
/*  670 */   public static final SoundEvent ENTITY_ELDER_GUARDIAN_DEATH_LAND = getRegisteredSoundEvent("entity.elder_guardian.death_land");
/*  671 */   public static final SoundEvent field_191240_aK = getRegisteredSoundEvent("entity.elder_guardian.flop");
/*  672 */   public static final SoundEvent ENTITY_ELDER_GUARDIAN_HURT = getRegisteredSoundEvent("entity.elder_guardian.hurt");
/*  673 */   public static final SoundEvent ENTITY_ELDER_GUARDIAN_HURT_LAND = getRegisteredSoundEvent("entity.elder_guardian.hurt_land");
/*  674 */   public static final SoundEvent ITEM_ELYTRA_FLYING = getRegisteredSoundEvent("item.elytra.flying");
/*  675 */   public static final SoundEvent BLOCK_ENCHANTMENT_TABLE_USE = getRegisteredSoundEvent("block.enchantment_table.use");
/*  676 */   public static final SoundEvent BLOCK_ENDERCHEST_CLOSE = getRegisteredSoundEvent("block.enderchest.close");
/*  677 */   public static final SoundEvent BLOCK_ENDERCHEST_OPEN = getRegisteredSoundEvent("block.enderchest.open");
/*  678 */   public static final SoundEvent ENTITY_ENDERDRAGON_AMBIENT = getRegisteredSoundEvent("entity.enderdragon.ambient");
/*  679 */   public static final SoundEvent ENTITY_ENDERDRAGON_DEATH = getRegisteredSoundEvent("entity.enderdragon.death");
/*  680 */   public static final SoundEvent ENTITY_ENDERDRAGON_FIREBALL_EPLD = getRegisteredSoundEvent("entity.enderdragon_fireball.explode");
/*  681 */   public static final SoundEvent ENTITY_ENDERDRAGON_FLAP = getRegisteredSoundEvent("entity.enderdragon.flap");
/*  682 */   public static final SoundEvent ENTITY_ENDERDRAGON_GROWL = getRegisteredSoundEvent("entity.enderdragon.growl");
/*  683 */   public static final SoundEvent ENTITY_ENDERDRAGON_HURT = getRegisteredSoundEvent("entity.enderdragon.hurt");
/*  684 */   public static final SoundEvent ENTITY_ENDERDRAGON_SHOOT = getRegisteredSoundEvent("entity.enderdragon.shoot");
/*  685 */   public static final SoundEvent field_193777_bb = getRegisteredSoundEvent("entity.endereye.death");
/*  686 */   public static final SoundEvent ENTITY_ENDEREYE_LAUNCH = getRegisteredSoundEvent("entity.endereye.launch");
/*  687 */   public static final SoundEvent ENTITY_ENDERMEN_AMBIENT = getRegisteredSoundEvent("entity.endermen.ambient");
/*  688 */   public static final SoundEvent ENTITY_ENDERMEN_DEATH = getRegisteredSoundEvent("entity.endermen.death");
/*  689 */   public static final SoundEvent ENTITY_ENDERMEN_HURT = getRegisteredSoundEvent("entity.endermen.hurt");
/*  690 */   public static final SoundEvent ENTITY_ENDERMEN_SCREAM = getRegisteredSoundEvent("entity.endermen.scream");
/*  691 */   public static final SoundEvent ENTITY_ENDERMEN_STARE = getRegisteredSoundEvent("entity.endermen.stare");
/*  692 */   public static final SoundEvent ENTITY_ENDERMEN_TELEPORT = getRegisteredSoundEvent("entity.endermen.teleport");
/*  693 */   public static final SoundEvent ENTITY_ENDERMITE_AMBIENT = getRegisteredSoundEvent("entity.endermite.ambient");
/*  694 */   public static final SoundEvent ENTITY_ENDERMITE_DEATH = getRegisteredSoundEvent("entity.endermite.death");
/*  695 */   public static final SoundEvent ENTITY_ENDERMITE_HURT = getRegisteredSoundEvent("entity.endermite.hurt");
/*  696 */   public static final SoundEvent ENTITY_ENDERMITE_STEP = getRegisteredSoundEvent("entity.endermite.step");
/*  697 */   public static final SoundEvent ENTITY_ENDERPEARL_THROW = getRegisteredSoundEvent("entity.enderpearl.throw");
/*  698 */   public static final SoundEvent BLOCK_END_GATEWAY_SPAWN = getRegisteredSoundEvent("block.end_gateway.spawn");
/*  699 */   public static final SoundEvent field_193781_bp = getRegisteredSoundEvent("block.end_portal_frame.fill");
/*  700 */   public static final SoundEvent field_193782_bq = getRegisteredSoundEvent("block.end_portal.spawn");
/*  701 */   public static final SoundEvent field_191242_bl = getRegisteredSoundEvent("entity.evocation_fangs.attack");
/*  702 */   public static final SoundEvent field_191243_bm = getRegisteredSoundEvent("entity.evocation_illager.ambient");
/*  703 */   public static final SoundEvent field_191244_bn = getRegisteredSoundEvent("entity.evocation_illager.cast_spell");
/*  704 */   public static final SoundEvent field_191245_bo = getRegisteredSoundEvent("entity.evocation_illager.death");
/*  705 */   public static final SoundEvent field_191246_bp = getRegisteredSoundEvent("entity.evocation_illager.hurt");
/*  706 */   public static final SoundEvent field_191247_bq = getRegisteredSoundEvent("entity.evocation_illager.prepare_attack");
/*  707 */   public static final SoundEvent field_191248_br = getRegisteredSoundEvent("entity.evocation_illager.prepare_summon");
/*  708 */   public static final SoundEvent field_191249_bs = getRegisteredSoundEvent("entity.evocation_illager.prepare_wololo");
/*  709 */   public static final SoundEvent ENTITY_EXPERIENCE_BOTTLE_THROW = getRegisteredSoundEvent("entity.experience_bottle.throw");
/*  710 */   public static final SoundEvent ENTITY_EXPERIENCE_ORB_PICKUP = getRegisteredSoundEvent("entity.experience_orb.pickup");
/*  711 */   public static final SoundEvent BLOCK_FENCE_GATE_CLOSE = getRegisteredSoundEvent("block.fence_gate.close");
/*  712 */   public static final SoundEvent BLOCK_FENCE_GATE_OPEN = getRegisteredSoundEvent("block.fence_gate.open");
/*  713 */   public static final SoundEvent ITEM_FIRECHARGE_USE = getRegisteredSoundEvent("item.firecharge.use");
/*  714 */   public static final SoundEvent ENTITY_FIREWORK_BLAST = getRegisteredSoundEvent("entity.firework.blast");
/*  715 */   public static final SoundEvent ENTITY_FIREWORK_BLAST_FAR = getRegisteredSoundEvent("entity.firework.blast_far");
/*  716 */   public static final SoundEvent ENTITY_FIREWORK_LARGE_BLAST = getRegisteredSoundEvent("entity.firework.large_blast");
/*  717 */   public static final SoundEvent ENTITY_FIREWORK_LARGE_BLAST_FAR = getRegisteredSoundEvent("entity.firework.large_blast_far");
/*  718 */   public static final SoundEvent ENTITY_FIREWORK_LAUNCH = getRegisteredSoundEvent("entity.firework.launch");
/*  719 */   public static final SoundEvent ENTITY_FIREWORK_SHOOT = getRegisteredSoundEvent("entity.firework.shoot");
/*  720 */   public static final SoundEvent ENTITY_FIREWORK_TWINKLE = getRegisteredSoundEvent("entity.firework.twinkle");
/*  721 */   public static final SoundEvent ENTITY_FIREWORK_TWINKLE_FAR = getRegisteredSoundEvent("entity.firework.twinkle_far");
/*  722 */   public static final SoundEvent BLOCK_FIRE_AMBIENT = getRegisteredSoundEvent("block.fire.ambient");
/*  723 */   public static final SoundEvent BLOCK_FIRE_EXTINGUISH = getRegisteredSoundEvent("block.fire.extinguish");
/*  724 */   public static final SoundEvent ITEM_FLINTANDSTEEL_USE = getRegisteredSoundEvent("item.flintandsteel.use");
/*  725 */   public static final SoundEvent BLOCK_FURNACE_FIRE_CRACKLE = getRegisteredSoundEvent("block.furnace.fire_crackle");
/*  726 */   public static final SoundEvent ENTITY_GENERIC_BIG_FALL = getRegisteredSoundEvent("entity.generic.big_fall");
/*  727 */   public static final SoundEvent ENTITY_GENERIC_BURN = getRegisteredSoundEvent("entity.generic.burn");
/*  728 */   public static final SoundEvent ENTITY_GENERIC_DEATH = getRegisteredSoundEvent("entity.generic.death");
/*  729 */   public static final SoundEvent ENTITY_GENERIC_DRINK = getRegisteredSoundEvent("entity.generic.drink");
/*  730 */   public static final SoundEvent ENTITY_GENERIC_EAT = getRegisteredSoundEvent("entity.generic.eat");
/*  731 */   public static final SoundEvent ENTITY_GENERIC_EXPLODE = getRegisteredSoundEvent("entity.generic.explode");
/*  732 */   public static final SoundEvent ENTITY_GENERIC_EXTINGUISH_FIRE = getRegisteredSoundEvent("entity.generic.extinguish_fire");
/*  733 */   public static final SoundEvent ENTITY_GENERIC_HURT = getRegisteredSoundEvent("entity.generic.hurt");
/*  734 */   public static final SoundEvent ENTITY_GENERIC_SMALL_FALL = getRegisteredSoundEvent("entity.generic.small_fall");
/*  735 */   public static final SoundEvent ENTITY_GENERIC_SPLASH = getRegisteredSoundEvent("entity.generic.splash");
/*  736 */   public static final SoundEvent ENTITY_GENERIC_SWIM = getRegisteredSoundEvent("entity.generic.swim");
/*  737 */   public static final SoundEvent ENTITY_GHAST_AMBIENT = getRegisteredSoundEvent("entity.ghast.ambient");
/*  738 */   public static final SoundEvent ENTITY_GHAST_DEATH = getRegisteredSoundEvent("entity.ghast.death");
/*  739 */   public static final SoundEvent ENTITY_GHAST_HURT = getRegisteredSoundEvent("entity.ghast.hurt");
/*  740 */   public static final SoundEvent ENTITY_GHAST_SCREAM = getRegisteredSoundEvent("entity.ghast.scream");
/*  741 */   public static final SoundEvent ENTITY_GHAST_SHOOT = getRegisteredSoundEvent("entity.ghast.shoot");
/*  742 */   public static final SoundEvent ENTITY_GHAST_WARN = getRegisteredSoundEvent("entity.ghast.warn");
/*  743 */   public static final SoundEvent BLOCK_GLASS_BREAK = getRegisteredSoundEvent("block.glass.break");
/*  744 */   public static final SoundEvent BLOCK_GLASS_FALL = getRegisteredSoundEvent("block.glass.fall");
/*  745 */   public static final SoundEvent BLOCK_GLASS_HIT = getRegisteredSoundEvent("block.glass.hit");
/*  746 */   public static final SoundEvent BLOCK_GLASS_PLACE = getRegisteredSoundEvent("block.glass.place");
/*  747 */   public static final SoundEvent BLOCK_GLASS_STEP = getRegisteredSoundEvent("block.glass.step");
/*  748 */   public static final SoundEvent BLOCK_GRASS_BREAK = getRegisteredSoundEvent("block.grass.break");
/*  749 */   public static final SoundEvent BLOCK_GRASS_FALL = getRegisteredSoundEvent("block.grass.fall");
/*  750 */   public static final SoundEvent BLOCK_GRASS_HIT = getRegisteredSoundEvent("block.grass.hit");
/*  751 */   public static final SoundEvent BLOCK_GRASS_PLACE = getRegisteredSoundEvent("block.grass.place");
/*  752 */   public static final SoundEvent BLOCK_GRASS_STEP = getRegisteredSoundEvent("block.grass.step");
/*  753 */   public static final SoundEvent BLOCK_GRAVEL_BREAK = getRegisteredSoundEvent("block.gravel.break");
/*  754 */   public static final SoundEvent BLOCK_GRAVEL_FALL = getRegisteredSoundEvent("block.gravel.fall");
/*  755 */   public static final SoundEvent BLOCK_GRAVEL_HIT = getRegisteredSoundEvent("block.gravel.hit");
/*  756 */   public static final SoundEvent BLOCK_GRAVEL_PLACE = getRegisteredSoundEvent("block.gravel.place");
/*  757 */   public static final SoundEvent BLOCK_GRAVEL_STEP = getRegisteredSoundEvent("block.gravel.step");
/*  758 */   public static final SoundEvent ENTITY_GUARDIAN_AMBIENT = getRegisteredSoundEvent("entity.guardian.ambient");
/*  759 */   public static final SoundEvent ENTITY_GUARDIAN_AMBIENT_LAND = getRegisteredSoundEvent("entity.guardian.ambient_land");
/*  760 */   public static final SoundEvent ENTITY_GUARDIAN_ATTACK = getRegisteredSoundEvent("entity.guardian.attack");
/*  761 */   public static final SoundEvent ENTITY_GUARDIAN_DEATH = getRegisteredSoundEvent("entity.guardian.death");
/*  762 */   public static final SoundEvent ENTITY_GUARDIAN_DEATH_LAND = getRegisteredSoundEvent("entity.guardian.death_land");
/*  763 */   public static final SoundEvent ENTITY_GUARDIAN_FLOP = getRegisteredSoundEvent("entity.guardian.flop");
/*  764 */   public static final SoundEvent ENTITY_GUARDIAN_HURT = getRegisteredSoundEvent("entity.guardian.hurt");
/*  765 */   public static final SoundEvent ENTITY_GUARDIAN_HURT_LAND = getRegisteredSoundEvent("entity.guardian.hurt_land");
/*  766 */   public static final SoundEvent ITEM_HOE_TILL = getRegisteredSoundEvent("item.hoe.till");
/*  767 */   public static final SoundEvent ENTITY_HORSE_AMBIENT = getRegisteredSoundEvent("entity.horse.ambient");
/*  768 */   public static final SoundEvent ENTITY_HORSE_ANGRY = getRegisteredSoundEvent("entity.horse.angry");
/*  769 */   public static final SoundEvent ENTITY_HORSE_ARMOR = getRegisteredSoundEvent("entity.horse.armor");
/*  770 */   public static final SoundEvent ENTITY_HORSE_BREATHE = getRegisteredSoundEvent("entity.horse.breathe");
/*  771 */   public static final SoundEvent ENTITY_HORSE_DEATH = getRegisteredSoundEvent("entity.horse.death");
/*  772 */   public static final SoundEvent ENTITY_HORSE_EAT = getRegisteredSoundEvent("entity.horse.eat");
/*  773 */   public static final SoundEvent ENTITY_HORSE_GALLOP = getRegisteredSoundEvent("entity.horse.gallop");
/*  774 */   public static final SoundEvent ENTITY_HORSE_HURT = getRegisteredSoundEvent("entity.horse.hurt");
/*  775 */   public static final SoundEvent ENTITY_HORSE_JUMP = getRegisteredSoundEvent("entity.horse.jump");
/*  776 */   public static final SoundEvent ENTITY_HORSE_LAND = getRegisteredSoundEvent("entity.horse.land");
/*  777 */   public static final SoundEvent ENTITY_HORSE_SADDLE = getRegisteredSoundEvent("entity.horse.saddle");
/*  778 */   public static final SoundEvent ENTITY_HORSE_STEP = getRegisteredSoundEvent("entity.horse.step");
/*  779 */   public static final SoundEvent ENTITY_HORSE_STEP_WOOD = getRegisteredSoundEvent("entity.horse.step_wood");
/*  780 */   public static final SoundEvent ENTITY_HOSTILE_BIG_FALL = getRegisteredSoundEvent("entity.hostile.big_fall");
/*  781 */   public static final SoundEvent ENTITY_HOSTILE_DEATH = getRegisteredSoundEvent("entity.hostile.death");
/*  782 */   public static final SoundEvent ENTITY_HOSTILE_HURT = getRegisteredSoundEvent("entity.hostile.hurt");
/*  783 */   public static final SoundEvent ENTITY_HOSTILE_SMALL_FALL = getRegisteredSoundEvent("entity.hostile.small_fall");
/*  784 */   public static final SoundEvent ENTITY_HOSTILE_SPLASH = getRegisteredSoundEvent("entity.hostile.splash");
/*  785 */   public static final SoundEvent ENTITY_HOSTILE_SWIM = getRegisteredSoundEvent("entity.hostile.swim");
/*  786 */   public static final SoundEvent ENTITY_HUSK_AMBIENT = getRegisteredSoundEvent("entity.husk.ambient");
/*  787 */   public static final SoundEvent ENTITY_HUSK_DEATH = getRegisteredSoundEvent("entity.husk.death");
/*  788 */   public static final SoundEvent ENTITY_HUSK_HURT = getRegisteredSoundEvent("entity.husk.hurt");
/*  789 */   public static final SoundEvent ENTITY_HUSK_STEP = getRegisteredSoundEvent("entity.husk.step");
/*  790 */   public static final SoundEvent field_193783_dc = getRegisteredSoundEvent("entity.illusion_illager.ambient");
/*  791 */   public static final SoundEvent field_193784_dd = getRegisteredSoundEvent("entity.illusion_illager.cast_spell");
/*  792 */   public static final SoundEvent field_193786_de = getRegisteredSoundEvent("entity.illusion_illager.death");
/*  793 */   public static final SoundEvent field_193787_df = getRegisteredSoundEvent("entity.illusion_illager.hurt");
/*  794 */   public static final SoundEvent field_193788_dg = getRegisteredSoundEvent("entity.illusion_illager.mirror_move");
/*  795 */   public static final SoundEvent field_193789_dh = getRegisteredSoundEvent("entity.illusion_illager.prepare_blindness");
/*  796 */   public static final SoundEvent field_193790_di = getRegisteredSoundEvent("entity.illusion_illager.prepare_mirror");
/*  797 */   public static final SoundEvent ENTITY_IRONGOLEM_ATTACK = getRegisteredSoundEvent("entity.irongolem.attack");
/*  798 */   public static final SoundEvent ENTITY_IRONGOLEM_DEATH = getRegisteredSoundEvent("entity.irongolem.death");
/*  799 */   public static final SoundEvent ENTITY_IRONGOLEM_HURT = getRegisteredSoundEvent("entity.irongolem.hurt");
/*  800 */   public static final SoundEvent ENTITY_IRONGOLEM_STEP = getRegisteredSoundEvent("entity.irongolem.step");
/*  801 */   public static final SoundEvent BLOCK_IRON_DOOR_CLOSE = getRegisteredSoundEvent("block.iron_door.close");
/*  802 */   public static final SoundEvent BLOCK_IRON_DOOR_OPEN = getRegisteredSoundEvent("block.iron_door.open");
/*  803 */   public static final SoundEvent BLOCK_IRON_TRAPDOOR_CLOSE = getRegisteredSoundEvent("block.iron_trapdoor.close");
/*  804 */   public static final SoundEvent BLOCK_IRON_TRAPDOOR_OPEN = getRegisteredSoundEvent("block.iron_trapdoor.open");
/*  805 */   public static final SoundEvent ENTITY_ITEMFRAME_ADD_ITEM = getRegisteredSoundEvent("entity.itemframe.add_item");
/*  806 */   public static final SoundEvent ENTITY_ITEMFRAME_BREAK = getRegisteredSoundEvent("entity.itemframe.break");
/*  807 */   public static final SoundEvent ENTITY_ITEMFRAME_PLACE = getRegisteredSoundEvent("entity.itemframe.place");
/*  808 */   public static final SoundEvent ENTITY_ITEMFRAME_REMOVE_ITEM = getRegisteredSoundEvent("entity.itemframe.remove_item");
/*  809 */   public static final SoundEvent ENTITY_ITEMFRAME_ROTATE_ITEM = getRegisteredSoundEvent("entity.itemframe.rotate_item");
/*  810 */   public static final SoundEvent ENTITY_ITEM_BREAK = getRegisteredSoundEvent("entity.item.break");
/*  811 */   public static final SoundEvent ENTITY_ITEM_PICKUP = getRegisteredSoundEvent("entity.item.pickup");
/*  812 */   public static final SoundEvent BLOCK_LADDER_BREAK = getRegisteredSoundEvent("block.ladder.break");
/*  813 */   public static final SoundEvent BLOCK_LADDER_FALL = getRegisteredSoundEvent("block.ladder.fall");
/*  814 */   public static final SoundEvent BLOCK_LADDER_HIT = getRegisteredSoundEvent("block.ladder.hit");
/*  815 */   public static final SoundEvent BLOCK_LADDER_PLACE = getRegisteredSoundEvent("block.ladder.place");
/*  816 */   public static final SoundEvent BLOCK_LADDER_STEP = getRegisteredSoundEvent("block.ladder.step");
/*  817 */   public static final SoundEvent BLOCK_LAVA_AMBIENT = getRegisteredSoundEvent("block.lava.ambient");
/*  818 */   public static final SoundEvent BLOCK_LAVA_EXTINGUISH = getRegisteredSoundEvent("block.lava.extinguish");
/*  819 */   public static final SoundEvent BLOCK_LAVA_POP = getRegisteredSoundEvent("block.lava.pop");
/*  820 */   public static final SoundEvent ENTITY_LEASHKNOT_BREAK = getRegisteredSoundEvent("entity.leashknot.break");
/*  821 */   public static final SoundEvent ENTITY_LEASHKNOT_PLACE = getRegisteredSoundEvent("entity.leashknot.place");
/*  822 */   public static final SoundEvent BLOCK_LEVER_CLICK = getRegisteredSoundEvent("block.lever.click");
/*  823 */   public static final SoundEvent ENTITY_LIGHTNING_IMPACT = getRegisteredSoundEvent("entity.lightning.impact");
/*  824 */   public static final SoundEvent ENTITY_LIGHTNING_THUNDER = getRegisteredSoundEvent("entity.lightning.thunder");
/*  825 */   public static final SoundEvent ENTITY_LINGERINGPOTION_THROW = getRegisteredSoundEvent("entity.lingeringpotion.throw");
/*  826 */   public static final SoundEvent field_191260_dz = getRegisteredSoundEvent("entity.llama.ambient");
/*  827 */   public static final SoundEvent field_191250_dA = getRegisteredSoundEvent("entity.llama.angry");
/*  828 */   public static final SoundEvent field_191251_dB = getRegisteredSoundEvent("entity.llama.chest");
/*  829 */   public static final SoundEvent field_191252_dC = getRegisteredSoundEvent("entity.llama.death");
/*  830 */   public static final SoundEvent field_191253_dD = getRegisteredSoundEvent("entity.llama.eat");
/*  831 */   public static final SoundEvent field_191254_dE = getRegisteredSoundEvent("entity.llama.hurt");
/*  832 */   public static final SoundEvent field_191255_dF = getRegisteredSoundEvent("entity.llama.spit");
/*  833 */   public static final SoundEvent field_191256_dG = getRegisteredSoundEvent("entity.llama.step");
/*  834 */   public static final SoundEvent field_191257_dH = getRegisteredSoundEvent("entity.llama.swag");
/*  835 */   public static final SoundEvent ENTITY_MAGMACUBE_DEATH = getRegisteredSoundEvent("entity.magmacube.death");
/*  836 */   public static final SoundEvent ENTITY_MAGMACUBE_HURT = getRegisteredSoundEvent("entity.magmacube.hurt");
/*  837 */   public static final SoundEvent ENTITY_MAGMACUBE_JUMP = getRegisteredSoundEvent("entity.magmacube.jump");
/*  838 */   public static final SoundEvent ENTITY_MAGMACUBE_SQUISH = getRegisteredSoundEvent("entity.magmacube.squish");
/*  839 */   public static final SoundEvent BLOCK_METAL_BREAK = getRegisteredSoundEvent("block.metal.break");
/*  840 */   public static final SoundEvent BLOCK_METAL_FALL = getRegisteredSoundEvent("block.metal.fall");
/*  841 */   public static final SoundEvent BLOCK_METAL_HIT = getRegisteredSoundEvent("block.metal.hit");
/*  842 */   public static final SoundEvent BLOCK_METAL_PLACE = getRegisteredSoundEvent("block.metal.place");
/*  843 */   public static final SoundEvent BLOCK_METAL_PRESSPLATE_CLICK_OFF = getRegisteredSoundEvent("block.metal_pressureplate.click_off");
/*  844 */   public static final SoundEvent BLOCK_METAL_PRESSPLATE_CLICK_ON = getRegisteredSoundEvent("block.metal_pressureplate.click_on");
/*  845 */   public static final SoundEvent BLOCK_METAL_STEP = getRegisteredSoundEvent("block.metal.step");
/*  846 */   public static final SoundEvent ENTITY_MINECART_INSIDE = getRegisteredSoundEvent("entity.minecart.inside");
/*  847 */   public static final SoundEvent ENTITY_MINECART_RIDING = getRegisteredSoundEvent("entity.minecart.riding");
/*  848 */   public static final SoundEvent ENTITY_MOOSHROOM_SHEAR = getRegisteredSoundEvent("entity.mooshroom.shear");
/*  849 */   public static final SoundEvent ENTITY_MULE_AMBIENT = getRegisteredSoundEvent("entity.mule.ambient");
/*  850 */   public static final SoundEvent field_191259_dX = getRegisteredSoundEvent("entity.mule.chest");
/*  851 */   public static final SoundEvent ENTITY_MULE_DEATH = getRegisteredSoundEvent("entity.mule.death");
/*  852 */   public static final SoundEvent ENTITY_MULE_HURT = getRegisteredSoundEvent("entity.mule.hurt");
/*  853 */   public static final SoundEvent MUSIC_CREATIVE = getRegisteredSoundEvent("music.creative");
/*  854 */   public static final SoundEvent MUSIC_CREDITS = getRegisteredSoundEvent("music.credits");
/*  855 */   public static final SoundEvent MUSIC_DRAGON = getRegisteredSoundEvent("music.dragon");
/*  856 */   public static final SoundEvent MUSIC_END = getRegisteredSoundEvent("music.end");
/*  857 */   public static final SoundEvent MUSIC_GAME = getRegisteredSoundEvent("music.game");
/*  858 */   public static final SoundEvent MUSIC_MENU = getRegisteredSoundEvent("music.menu");
/*  859 */   public static final SoundEvent MUSIC_NETHER = getRegisteredSoundEvent("music.nether");
/*  860 */   public static final SoundEvent BLOCK_NOTE_BASEDRUM = getRegisteredSoundEvent("block.note.basedrum");
/*  861 */   public static final SoundEvent BLOCK_NOTE_BASS = getRegisteredSoundEvent("block.note.bass");
/*  862 */   public static final SoundEvent field_193807_ew = getRegisteredSoundEvent("block.note.bell");
/*  863 */   public static final SoundEvent field_193808_ex = getRegisteredSoundEvent("block.note.chime");
/*  864 */   public static final SoundEvent field_193809_ey = getRegisteredSoundEvent("block.note.flute");
/*  865 */   public static final SoundEvent field_193810_ez = getRegisteredSoundEvent("block.note.guitar");
/*  866 */   public static final SoundEvent BLOCK_NOTE_HARP = getRegisteredSoundEvent("block.note.harp");
/*  867 */   public static final SoundEvent BLOCK_NOTE_HAT = getRegisteredSoundEvent("block.note.hat");
/*  868 */   public static final SoundEvent BLOCK_NOTE_PLING = getRegisteredSoundEvent("block.note.pling");
/*  869 */   public static final SoundEvent BLOCK_NOTE_SNARE = getRegisteredSoundEvent("block.note.snare");
/*  870 */   public static final SoundEvent field_193785_eE = getRegisteredSoundEvent("block.note.xylophone");
/*  871 */   public static final SoundEvent ENTITY_PAINTING_BREAK = getRegisteredSoundEvent("entity.painting.break");
/*  872 */   public static final SoundEvent ENTITY_PAINTING_PLACE = getRegisteredSoundEvent("entity.painting.place");
/*  873 */   public static final SoundEvent field_192792_ep = getRegisteredSoundEvent("entity.parrot.ambient");
/*  874 */   public static final SoundEvent field_192793_eq = getRegisteredSoundEvent("entity.parrot.death");
/*  875 */   public static final SoundEvent field_192797_eu = getRegisteredSoundEvent("entity.parrot.eat");
/*  876 */   public static final SoundEvent field_192796_et = getRegisteredSoundEvent("entity.parrot.fly");
/*  877 */   public static final SoundEvent field_192794_er = getRegisteredSoundEvent("entity.parrot.hurt");
/*  878 */   public static final SoundEvent field_193791_eM = getRegisteredSoundEvent("entity.parrot.imitate.blaze");
/*  879 */   public static final SoundEvent field_193792_eN = getRegisteredSoundEvent("entity.parrot.imitate.creeper");
/*  880 */   public static final SoundEvent field_193793_eO = getRegisteredSoundEvent("entity.parrot.imitate.elder_guardian");
/*  881 */   public static final SoundEvent field_193794_eP = getRegisteredSoundEvent("entity.parrot.imitate.enderdragon");
/*  882 */   public static final SoundEvent field_193795_eQ = getRegisteredSoundEvent("entity.parrot.imitate.enderman");
/*  883 */   public static final SoundEvent field_193796_eR = getRegisteredSoundEvent("entity.parrot.imitate.endermite");
/*  884 */   public static final SoundEvent field_193797_eS = getRegisteredSoundEvent("entity.parrot.imitate.evocation_illager");
/*  885 */   public static final SoundEvent field_193798_eT = getRegisteredSoundEvent("entity.parrot.imitate.ghast");
/*  886 */   public static final SoundEvent field_193799_eU = getRegisteredSoundEvent("entity.parrot.imitate.husk");
/*  887 */   public static final SoundEvent field_193800_eV = getRegisteredSoundEvent("entity.parrot.imitate.illusion_illager");
/*  888 */   public static final SoundEvent field_193801_eW = getRegisteredSoundEvent("entity.parrot.imitate.magmacube");
/*  889 */   public static final SoundEvent field_193802_eX = getRegisteredSoundEvent("entity.parrot.imitate.polar_bear");
/*  890 */   public static final SoundEvent field_193803_eY = getRegisteredSoundEvent("entity.parrot.imitate.shulker");
/*  891 */   public static final SoundEvent field_193804_eZ = getRegisteredSoundEvent("entity.parrot.imitate.silverfish");
/*  892 */   public static final SoundEvent field_193811_fa = getRegisteredSoundEvent("entity.parrot.imitate.skeleton");
/*  893 */   public static final SoundEvent field_193812_fb = getRegisteredSoundEvent("entity.parrot.imitate.slime");
/*  894 */   public static final SoundEvent field_193813_fc = getRegisteredSoundEvent("entity.parrot.imitate.spider");
/*  895 */   public static final SoundEvent field_193814_fd = getRegisteredSoundEvent("entity.parrot.imitate.stray");
/*  896 */   public static final SoundEvent field_193815_fe = getRegisteredSoundEvent("entity.parrot.imitate.vex");
/*  897 */   public static final SoundEvent field_193816_ff = getRegisteredSoundEvent("entity.parrot.imitate.vindication_illager");
/*  898 */   public static final SoundEvent field_193817_fg = getRegisteredSoundEvent("entity.parrot.imitate.witch");
/*  899 */   public static final SoundEvent field_193818_fh = getRegisteredSoundEvent("entity.parrot.imitate.wither");
/*  900 */   public static final SoundEvent field_193819_fi = getRegisteredSoundEvent("entity.parrot.imitate.wither_skeleton");
/*  901 */   public static final SoundEvent field_193820_fj = getRegisteredSoundEvent("entity.parrot.imitate.wolf");
/*  902 */   public static final SoundEvent field_193821_fk = getRegisteredSoundEvent("entity.parrot.imitate.zombie");
/*  903 */   public static final SoundEvent field_193822_fl = getRegisteredSoundEvent("entity.parrot.imitate.zombie_pigman");
/*  904 */   public static final SoundEvent field_193823_fm = getRegisteredSoundEvent("entity.parrot.imitate.zombie_villager");
/*  905 */   public static final SoundEvent field_192795_es = getRegisteredSoundEvent("entity.parrot.step");
/*  906 */   public static final SoundEvent ENTITY_PIG_AMBIENT = getRegisteredSoundEvent("entity.pig.ambient");
/*  907 */   public static final SoundEvent ENTITY_PIG_DEATH = getRegisteredSoundEvent("entity.pig.death");
/*  908 */   public static final SoundEvent ENTITY_PIG_HURT = getRegisteredSoundEvent("entity.pig.hurt");
/*  909 */   public static final SoundEvent ENTITY_PIG_SADDLE = getRegisteredSoundEvent("entity.pig.saddle");
/*  910 */   public static final SoundEvent ENTITY_PIG_STEP = getRegisteredSoundEvent("entity.pig.step");
/*  911 */   public static final SoundEvent BLOCK_PISTON_CONTRACT = getRegisteredSoundEvent("block.piston.contract");
/*  912 */   public static final SoundEvent BLOCK_PISTON_EXTEND = getRegisteredSoundEvent("block.piston.extend");
/*  913 */   public static final SoundEvent ENTITY_PLAYER_ATTACK_CRIT = getRegisteredSoundEvent("entity.player.attack.crit");
/*  914 */   public static final SoundEvent ENTITY_PLAYER_ATTACK_KNOCKBACK = getRegisteredSoundEvent("entity.player.attack.knockback");
/*  915 */   public static final SoundEvent ENTITY_PLAYER_ATTACK_NODAMAGE = getRegisteredSoundEvent("entity.player.attack.nodamage");
/*  916 */   public static final SoundEvent ENTITY_PLAYER_ATTACK_STRONG = getRegisteredSoundEvent("entity.player.attack.strong");
/*  917 */   public static final SoundEvent ENTITY_PLAYER_ATTACK_SWEEP = getRegisteredSoundEvent("entity.player.attack.sweep");
/*  918 */   public static final SoundEvent ENTITY_PLAYER_ATTACK_WEAK = getRegisteredSoundEvent("entity.player.attack.weak");
/*  919 */   public static final SoundEvent ENTITY_PLAYER_BIG_FALL = getRegisteredSoundEvent("entity.player.big_fall");
/*  920 */   public static final SoundEvent ENTITY_PLAYER_BREATH = getRegisteredSoundEvent("entity.player.breath");
/*  921 */   public static final SoundEvent ENTITY_PLAYER_BURP = getRegisteredSoundEvent("entity.player.burp");
/*  922 */   public static final SoundEvent ENTITY_PLAYER_DEATH = getRegisteredSoundEvent("entity.player.death");
/*  923 */   public static final SoundEvent ENTITY_PLAYER_HURT = getRegisteredSoundEvent("entity.player.hurt");
/*  924 */   public static final SoundEvent field_193805_fG = getRegisteredSoundEvent("entity.player.hurt_drown");
/*  925 */   public static final SoundEvent field_193806_fH = getRegisteredSoundEvent("entity.player.hurt_on_fire");
/*  926 */   public static final SoundEvent ENTITY_PLAYER_LEVELUP = getRegisteredSoundEvent("entity.player.levelup");
/*  927 */   public static final SoundEvent ENTITY_PLAYER_SMALL_FALL = getRegisteredSoundEvent("entity.player.small_fall");
/*  928 */   public static final SoundEvent ENTITY_PLAYER_SPLASH = getRegisteredSoundEvent("entity.player.splash");
/*  929 */   public static final SoundEvent ENTITY_PLAYER_SWIM = getRegisteredSoundEvent("entity.player.swim");
/*  930 */   public static final SoundEvent ENTITY_POLAR_BEAR_AMBIENT = getRegisteredSoundEvent("entity.polar_bear.ambient");
/*  931 */   public static final SoundEvent ENTITY_POLAR_BEAR_BABY_AMBIENT = getRegisteredSoundEvent("entity.polar_bear.baby_ambient");
/*  932 */   public static final SoundEvent ENTITY_POLAR_BEAR_DEATH = getRegisteredSoundEvent("entity.polar_bear.death");
/*  933 */   public static final SoundEvent ENTITY_POLAR_BEAR_HURT = getRegisteredSoundEvent("entity.polar_bear.hurt");
/*  934 */   public static final SoundEvent ENTITY_POLAR_BEAR_STEP = getRegisteredSoundEvent("entity.polar_bear.step");
/*  935 */   public static final SoundEvent ENTITY_POLAR_BEAR_WARNING = getRegisteredSoundEvent("entity.polar_bear.warning");
/*  936 */   public static final SoundEvent BLOCK_PORTAL_AMBIENT = getRegisteredSoundEvent("block.portal.ambient");
/*  937 */   public static final SoundEvent BLOCK_PORTAL_TRAVEL = getRegisteredSoundEvent("block.portal.travel");
/*  938 */   public static final SoundEvent BLOCK_PORTAL_TRIGGER = getRegisteredSoundEvent("block.portal.trigger");
/*  939 */   public static final SoundEvent ENTITY_RABBIT_AMBIENT = getRegisteredSoundEvent("entity.rabbit.ambient");
/*  940 */   public static final SoundEvent ENTITY_RABBIT_ATTACK = getRegisteredSoundEvent("entity.rabbit.attack");
/*  941 */   public static final SoundEvent ENTITY_RABBIT_DEATH = getRegisteredSoundEvent("entity.rabbit.death");
/*  942 */   public static final SoundEvent ENTITY_RABBIT_HURT = getRegisteredSoundEvent("entity.rabbit.hurt");
/*  943 */   public static final SoundEvent ENTITY_RABBIT_JUMP = getRegisteredSoundEvent("entity.rabbit.jump");
/*  944 */   public static final SoundEvent RECORD_11 = getRegisteredSoundEvent("record.11");
/*  945 */   public static final SoundEvent RECORD_13 = getRegisteredSoundEvent("record.13");
/*  946 */   public static final SoundEvent RECORD_BLOCKS = getRegisteredSoundEvent("record.blocks");
/*  947 */   public static final SoundEvent RECORD_CAT = getRegisteredSoundEvent("record.cat");
/*  948 */   public static final SoundEvent RECORD_CHIRP = getRegisteredSoundEvent("record.chirp");
/*  949 */   public static final SoundEvent RECORD_FAR = getRegisteredSoundEvent("record.far");
/*  950 */   public static final SoundEvent RECORD_MALL = getRegisteredSoundEvent("record.mall");
/*  951 */   public static final SoundEvent RECORD_MELLOHI = getRegisteredSoundEvent("record.mellohi");
/*  952 */   public static final SoundEvent RECORD_STAL = getRegisteredSoundEvent("record.stal");
/*  953 */   public static final SoundEvent RECORD_STRAD = getRegisteredSoundEvent("record.strad");
/*  954 */   public static final SoundEvent RECORD_WAIT = getRegisteredSoundEvent("record.wait");
/*  955 */   public static final SoundEvent RECORD_WARD = getRegisteredSoundEvent("record.ward");
/*  956 */   public static final SoundEvent BLOCK_REDSTONE_TORCH_BURNOUT = getRegisteredSoundEvent("block.redstone_torch.burnout");
/*  957 */   public static final SoundEvent BLOCK_SAND_BREAK = getRegisteredSoundEvent("block.sand.break");
/*  958 */   public static final SoundEvent BLOCK_SAND_FALL = getRegisteredSoundEvent("block.sand.fall");
/*  959 */   public static final SoundEvent BLOCK_SAND_HIT = getRegisteredSoundEvent("block.sand.hit");
/*  960 */   public static final SoundEvent BLOCK_SAND_PLACE = getRegisteredSoundEvent("block.sand.place");
/*  961 */   public static final SoundEvent BLOCK_SAND_STEP = getRegisteredSoundEvent("block.sand.step");
/*  962 */   public static final SoundEvent ENTITY_SHEEP_AMBIENT = getRegisteredSoundEvent("entity.sheep.ambient");
/*  963 */   public static final SoundEvent ENTITY_SHEEP_DEATH = getRegisteredSoundEvent("entity.sheep.death");
/*  964 */   public static final SoundEvent ENTITY_SHEEP_HURT = getRegisteredSoundEvent("entity.sheep.hurt");
/*  965 */   public static final SoundEvent ENTITY_SHEEP_SHEAR = getRegisteredSoundEvent("entity.sheep.shear");
/*  966 */   public static final SoundEvent ENTITY_SHEEP_STEP = getRegisteredSoundEvent("entity.sheep.step");
/*  967 */   public static final SoundEvent ITEM_SHIELD_BLOCK = getRegisteredSoundEvent("item.shield.block");
/*  968 */   public static final SoundEvent ITEM_SHIELD_BREAK = getRegisteredSoundEvent("item.shield.break");
/*  969 */   public static final SoundEvent ITEM_SHOVEL_FLATTEN = getRegisteredSoundEvent("item.shovel.flatten");
/*  970 */   public static final SoundEvent ENTITY_SHULKER_AMBIENT = getRegisteredSoundEvent("entity.shulker.ambient");
/*  971 */   public static final SoundEvent field_191261_fA = getRegisteredSoundEvent("block.shulker_box.close");
/*  972 */   public static final SoundEvent field_191262_fB = getRegisteredSoundEvent("block.shulker_box.open");
/*  973 */   public static final SoundEvent ENTITY_SHULKER_BULLET_HIT = getRegisteredSoundEvent("entity.shulker_bullet.hit");
/*  974 */   public static final SoundEvent ENTITY_SHULKER_BULLET_HURT = getRegisteredSoundEvent("entity.shulker_bullet.hurt");
/*  975 */   public static final SoundEvent ENTITY_SHULKER_CLOSE = getRegisteredSoundEvent("entity.shulker.close");
/*  976 */   public static final SoundEvent ENTITY_SHULKER_DEATH = getRegisteredSoundEvent("entity.shulker.death");
/*  977 */   public static final SoundEvent ENTITY_SHULKER_HURT = getRegisteredSoundEvent("entity.shulker.hurt");
/*  978 */   public static final SoundEvent ENTITY_SHULKER_HURT_CLOSED = getRegisteredSoundEvent("entity.shulker.hurt_closed");
/*  979 */   public static final SoundEvent ENTITY_SHULKER_OPEN = getRegisteredSoundEvent("entity.shulker.open");
/*  980 */   public static final SoundEvent ENTITY_SHULKER_SHOOT = getRegisteredSoundEvent("entity.shulker.shoot");
/*  981 */   public static final SoundEvent ENTITY_SHULKER_TELEPORT = getRegisteredSoundEvent("entity.shulker.teleport");
/*  982 */   public static final SoundEvent ENTITY_SILVERFISH_AMBIENT = getRegisteredSoundEvent("entity.silverfish.ambient");
/*  983 */   public static final SoundEvent ENTITY_SILVERFISH_DEATH = getRegisteredSoundEvent("entity.silverfish.death");
/*  984 */   public static final SoundEvent ENTITY_SILVERFISH_HURT = getRegisteredSoundEvent("entity.silverfish.hurt");
/*  985 */   public static final SoundEvent ENTITY_SILVERFISH_STEP = getRegisteredSoundEvent("entity.silverfish.step");
/*  986 */   public static final SoundEvent ENTITY_SKELETON_AMBIENT = getRegisteredSoundEvent("entity.skeleton.ambient");
/*  987 */   public static final SoundEvent ENTITY_SKELETON_DEATH = getRegisteredSoundEvent("entity.skeleton.death");
/*  988 */   public static final SoundEvent ENTITY_SKELETON_HORSE_AMBIENT = getRegisteredSoundEvent("entity.skeleton_horse.ambient");
/*  989 */   public static final SoundEvent ENTITY_SKELETON_HORSE_DEATH = getRegisteredSoundEvent("entity.skeleton_horse.death");
/*  990 */   public static final SoundEvent ENTITY_SKELETON_HORSE_HURT = getRegisteredSoundEvent("entity.skeleton_horse.hurt");
/*  991 */   public static final SoundEvent ENTITY_SKELETON_HURT = getRegisteredSoundEvent("entity.skeleton.hurt");
/*  992 */   public static final SoundEvent ENTITY_SKELETON_SHOOT = getRegisteredSoundEvent("entity.skeleton.shoot");
/*  993 */   public static final SoundEvent ENTITY_SKELETON_STEP = getRegisteredSoundEvent("entity.skeleton.step");
/*  994 */   public static final SoundEvent ENTITY_SLIME_ATTACK = getRegisteredSoundEvent("entity.slime.attack");
/*  995 */   public static final SoundEvent BLOCK_SLIME_BREAK = getRegisteredSoundEvent("block.slime.break");
/*  996 */   public static final SoundEvent ENTITY_SLIME_DEATH = getRegisteredSoundEvent("entity.slime.death");
/*  997 */   public static final SoundEvent BLOCK_SLIME_FALL = getRegisteredSoundEvent("block.slime.fall");
/*  998 */   public static final SoundEvent BLOCK_SLIME_HIT = getRegisteredSoundEvent("block.slime.hit");
/*  999 */   public static final SoundEvent ENTITY_SLIME_HURT = getRegisteredSoundEvent("entity.slime.hurt");
/* 1000 */   public static final SoundEvent ENTITY_SLIME_JUMP = getRegisteredSoundEvent("entity.slime.jump");
/* 1001 */   public static final SoundEvent BLOCK_SLIME_PLACE = getRegisteredSoundEvent("block.slime.place");
/* 1002 */   public static final SoundEvent ENTITY_SLIME_SQUISH = getRegisteredSoundEvent("entity.slime.squish");
/* 1003 */   public static final SoundEvent BLOCK_SLIME_STEP = getRegisteredSoundEvent("block.slime.step");
/* 1004 */   public static final SoundEvent ENTITY_SMALL_MAGMACUBE_DEATH = getRegisteredSoundEvent("entity.small_magmacube.death");
/* 1005 */   public static final SoundEvent ENTITY_SMALL_MAGMACUBE_HURT = getRegisteredSoundEvent("entity.small_magmacube.hurt");
/* 1006 */   public static final SoundEvent ENTITY_SMALL_MAGMACUBE_SQUISH = getRegisteredSoundEvent("entity.small_magmacube.squish");
/* 1007 */   public static final SoundEvent ENTITY_SMALL_SLIME_DEATH = getRegisteredSoundEvent("entity.small_slime.death");
/* 1008 */   public static final SoundEvent ENTITY_SMALL_SLIME_HURT = getRegisteredSoundEvent("entity.small_slime.hurt");
/* 1009 */   public static final SoundEvent ENTITY_SMALL_SLIME_JUMP = getRegisteredSoundEvent("entity.small_slime.jump");
/* 1010 */   public static final SoundEvent ENTITY_SMALL_SLIME_SQUISH = getRegisteredSoundEvent("entity.small_slime.squish");
/* 1011 */   public static final SoundEvent ENTITY_SNOWBALL_THROW = getRegisteredSoundEvent("entity.snowball.throw");
/* 1012 */   public static final SoundEvent ENTITY_SNOWMAN_AMBIENT = getRegisteredSoundEvent("entity.snowman.ambient");
/* 1013 */   public static final SoundEvent ENTITY_SNOWMAN_DEATH = getRegisteredSoundEvent("entity.snowman.death");
/* 1014 */   public static final SoundEvent ENTITY_SNOWMAN_HURT = getRegisteredSoundEvent("entity.snowman.hurt");
/* 1015 */   public static final SoundEvent ENTITY_SNOWMAN_SHOOT = getRegisteredSoundEvent("entity.snowman.shoot");
/* 1016 */   public static final SoundEvent BLOCK_SNOW_BREAK = getRegisteredSoundEvent("block.snow.break");
/* 1017 */   public static final SoundEvent BLOCK_SNOW_FALL = getRegisteredSoundEvent("block.snow.fall");
/* 1018 */   public static final SoundEvent BLOCK_SNOW_HIT = getRegisteredSoundEvent("block.snow.hit");
/* 1019 */   public static final SoundEvent BLOCK_SNOW_PLACE = getRegisteredSoundEvent("block.snow.place");
/* 1020 */   public static final SoundEvent BLOCK_SNOW_STEP = getRegisteredSoundEvent("block.snow.step");
/* 1021 */   public static final SoundEvent ENTITY_SPIDER_AMBIENT = getRegisteredSoundEvent("entity.spider.ambient");
/* 1022 */   public static final SoundEvent ENTITY_SPIDER_DEATH = getRegisteredSoundEvent("entity.spider.death");
/* 1023 */   public static final SoundEvent ENTITY_SPIDER_HURT = getRegisteredSoundEvent("entity.spider.hurt");
/* 1024 */   public static final SoundEvent ENTITY_SPIDER_STEP = getRegisteredSoundEvent("entity.spider.step");
/* 1025 */   public static final SoundEvent ENTITY_SPLASH_POTION_BREAK = getRegisteredSoundEvent("entity.splash_potion.break");
/* 1026 */   public static final SoundEvent ENTITY_SPLASH_POTION_THROW = getRegisteredSoundEvent("entity.splash_potion.throw");
/* 1027 */   public static final SoundEvent ENTITY_SQUID_AMBIENT = getRegisteredSoundEvent("entity.squid.ambient");
/* 1028 */   public static final SoundEvent ENTITY_SQUID_DEATH = getRegisteredSoundEvent("entity.squid.death");
/* 1029 */   public static final SoundEvent ENTITY_SQUID_HURT = getRegisteredSoundEvent("entity.squid.hurt");
/* 1030 */   public static final SoundEvent BLOCK_STONE_BREAK = getRegisteredSoundEvent("block.stone.break");
/* 1031 */   public static final SoundEvent BLOCK_STONE_BUTTON_CLICK_OFF = getRegisteredSoundEvent("block.stone_button.click_off");
/* 1032 */   public static final SoundEvent BLOCK_STONE_BUTTON_CLICK_ON = getRegisteredSoundEvent("block.stone_button.click_on");
/* 1033 */   public static final SoundEvent BLOCK_STONE_FALL = getRegisteredSoundEvent("block.stone.fall");
/* 1034 */   public static final SoundEvent BLOCK_STONE_HIT = getRegisteredSoundEvent("block.stone.hit");
/* 1035 */   public static final SoundEvent BLOCK_STONE_PLACE = getRegisteredSoundEvent("block.stone.place");
/* 1036 */   public static final SoundEvent BLOCK_STONE_PRESSPLATE_CLICK_OFF = getRegisteredSoundEvent("block.stone_pressureplate.click_off");
/* 1037 */   public static final SoundEvent BLOCK_STONE_PRESSPLATE_CLICK_ON = getRegisteredSoundEvent("block.stone_pressureplate.click_on");
/* 1038 */   public static final SoundEvent BLOCK_STONE_STEP = getRegisteredSoundEvent("block.stone.step");
/* 1039 */   public static final SoundEvent ENTITY_STRAY_AMBIENT = getRegisteredSoundEvent("entity.stray.ambient");
/* 1040 */   public static final SoundEvent ENTITY_STRAY_DEATH = getRegisteredSoundEvent("entity.stray.death");
/* 1041 */   public static final SoundEvent ENTITY_STRAY_HURT = getRegisteredSoundEvent("entity.stray.hurt");
/* 1042 */   public static final SoundEvent ENTITY_STRAY_STEP = getRegisteredSoundEvent("entity.stray.step");
/* 1043 */   public static final SoundEvent ENCHANT_THORNS_HIT = getRegisteredSoundEvent("enchant.thorns.hit");
/* 1044 */   public static final SoundEvent ENTITY_TNT_PRIMED = getRegisteredSoundEvent("entity.tnt.primed");
/* 1045 */   public static final SoundEvent field_191263_gW = getRegisteredSoundEvent("item.totem.use");
/* 1046 */   public static final SoundEvent BLOCK_TRIPWIRE_ATTACH = getRegisteredSoundEvent("block.tripwire.attach");
/* 1047 */   public static final SoundEvent BLOCK_TRIPWIRE_CLICK_OFF = getRegisteredSoundEvent("block.tripwire.click_off");
/* 1048 */   public static final SoundEvent BLOCK_TRIPWIRE_CLICK_ON = getRegisteredSoundEvent("block.tripwire.click_on");
/* 1049 */   public static final SoundEvent BLOCK_TRIPWIRE_DETACH = getRegisteredSoundEvent("block.tripwire.detach");
/* 1050 */   public static final SoundEvent UI_BUTTON_CLICK = getRegisteredSoundEvent("ui.button.click");
/* 1051 */   public static final SoundEvent field_194226_id = getRegisteredSoundEvent("ui.toast.in");
/* 1052 */   public static final SoundEvent field_194227_ie = getRegisteredSoundEvent("ui.toast.out");
/* 1053 */   public static final SoundEvent field_194228_if = getRegisteredSoundEvent("ui.toast.challenge_complete");
/* 1054 */   public static final SoundEvent field_191264_hc = getRegisteredSoundEvent("entity.vex.ambient");
/* 1055 */   public static final SoundEvent field_191265_hd = getRegisteredSoundEvent("entity.vex.charge");
/* 1056 */   public static final SoundEvent field_191266_he = getRegisteredSoundEvent("entity.vex.death");
/* 1057 */   public static final SoundEvent field_191267_hf = getRegisteredSoundEvent("entity.vex.hurt");
/* 1058 */   public static final SoundEvent ENTITY_VILLAGER_AMBIENT = getRegisteredSoundEvent("entity.villager.ambient");
/* 1059 */   public static final SoundEvent ENTITY_VILLAGER_DEATH = getRegisteredSoundEvent("entity.villager.death");
/* 1060 */   public static final SoundEvent ENTITY_VILLAGER_HURT = getRegisteredSoundEvent("entity.villager.hurt");
/* 1061 */   public static final SoundEvent ENTITY_VILLAGER_NO = getRegisteredSoundEvent("entity.villager.no");
/* 1062 */   public static final SoundEvent ENTITY_VILLAGER_TRADING = getRegisteredSoundEvent("entity.villager.trading");
/* 1063 */   public static final SoundEvent ENTITY_VILLAGER_YES = getRegisteredSoundEvent("entity.villager.yes");
/* 1064 */   public static final SoundEvent field_191268_hm = getRegisteredSoundEvent("entity.vindication_illager.ambient");
/* 1065 */   public static final SoundEvent field_191269_hn = getRegisteredSoundEvent("entity.vindication_illager.death");
/* 1066 */   public static final SoundEvent field_191270_ho = getRegisteredSoundEvent("entity.vindication_illager.hurt");
/* 1067 */   public static final SoundEvent BLOCK_WATERLILY_PLACE = getRegisteredSoundEvent("block.waterlily.place");
/* 1068 */   public static final SoundEvent BLOCK_WATER_AMBIENT = getRegisteredSoundEvent("block.water.ambient");
/* 1069 */   public static final SoundEvent WEATHER_RAIN = getRegisteredSoundEvent("weather.rain");
/* 1070 */   public static final SoundEvent WEATHER_RAIN_ABOVE = getRegisteredSoundEvent("weather.rain.above");
/* 1071 */   public static final SoundEvent ENTITY_WITCH_AMBIENT = getRegisteredSoundEvent("entity.witch.ambient");
/* 1072 */   public static final SoundEvent ENTITY_WITCH_DEATH = getRegisteredSoundEvent("entity.witch.death");
/* 1073 */   public static final SoundEvent ENTITY_WITCH_DRINK = getRegisteredSoundEvent("entity.witch.drink");
/* 1074 */   public static final SoundEvent ENTITY_WITCH_HURT = getRegisteredSoundEvent("entity.witch.hurt");
/* 1075 */   public static final SoundEvent ENTITY_WITCH_THROW = getRegisteredSoundEvent("entity.witch.throw");
/* 1076 */   public static final SoundEvent ENTITY_WITHER_AMBIENT = getRegisteredSoundEvent("entity.wither.ambient");
/* 1077 */   public static final SoundEvent ENTITY_WITHER_BREAK_BLOCK = getRegisteredSoundEvent("entity.wither.break_block");
/* 1078 */   public static final SoundEvent ENTITY_WITHER_DEATH = getRegisteredSoundEvent("entity.wither.death");
/* 1079 */   public static final SoundEvent ENTITY_WITHER_HURT = getRegisteredSoundEvent("entity.wither.hurt");
/* 1080 */   public static final SoundEvent ENTITY_WITHER_SHOOT = getRegisteredSoundEvent("entity.wither.shoot");
/* 1081 */   public static final SoundEvent ENTITY_WITHER_SKELETON_AMBIENT = getRegisteredSoundEvent("entity.wither_skeleton.ambient");
/* 1082 */   public static final SoundEvent ENTITY_WITHER_SKELETON_DEATH = getRegisteredSoundEvent("entity.wither_skeleton.death");
/* 1083 */   public static final SoundEvent ENTITY_WITHER_SKELETON_HURT = getRegisteredSoundEvent("entity.wither_skeleton.hurt");
/* 1084 */   public static final SoundEvent ENTITY_WITHER_SKELETON_STEP = getRegisteredSoundEvent("entity.wither_skeleton.step");
/* 1085 */   public static final SoundEvent ENTITY_WITHER_SPAWN = getRegisteredSoundEvent("entity.wither.spawn");
/* 1086 */   public static final SoundEvent ENTITY_WOLF_AMBIENT = getRegisteredSoundEvent("entity.wolf.ambient");
/* 1087 */   public static final SoundEvent ENTITY_WOLF_DEATH = getRegisteredSoundEvent("entity.wolf.death");
/* 1088 */   public static final SoundEvent ENTITY_WOLF_GROWL = getRegisteredSoundEvent("entity.wolf.growl");
/* 1089 */   public static final SoundEvent ENTITY_WOLF_HOWL = getRegisteredSoundEvent("entity.wolf.howl");
/* 1090 */   public static final SoundEvent ENTITY_WOLF_HURT = getRegisteredSoundEvent("entity.wolf.hurt");
/* 1091 */   public static final SoundEvent ENTITY_WOLF_PANT = getRegisteredSoundEvent("entity.wolf.pant");
/* 1092 */   public static final SoundEvent ENTITY_WOLF_SHAKE = getRegisteredSoundEvent("entity.wolf.shake");
/* 1093 */   public static final SoundEvent ENTITY_WOLF_STEP = getRegisteredSoundEvent("entity.wolf.step");
/* 1094 */   public static final SoundEvent ENTITY_WOLF_WHINE = getRegisteredSoundEvent("entity.wolf.whine");
/* 1095 */   public static final SoundEvent BLOCK_WOODEN_DOOR_CLOSE = getRegisteredSoundEvent("block.wooden_door.close");
/* 1096 */   public static final SoundEvent BLOCK_WOODEN_DOOR_OPEN = getRegisteredSoundEvent("block.wooden_door.open");
/* 1097 */   public static final SoundEvent BLOCK_WOODEN_TRAPDOOR_CLOSE = getRegisteredSoundEvent("block.wooden_trapdoor.close");
/* 1098 */   public static final SoundEvent BLOCK_WOODEN_TRAPDOOR_OPEN = getRegisteredSoundEvent("block.wooden_trapdoor.open");
/* 1099 */   public static final SoundEvent BLOCK_WOOD_BREAK = getRegisteredSoundEvent("block.wood.break");
/* 1100 */   public static final SoundEvent BLOCK_WOOD_BUTTON_CLICK_OFF = getRegisteredSoundEvent("block.wood_button.click_off");
/* 1101 */   public static final SoundEvent BLOCK_WOOD_BUTTON_CLICK_ON = getRegisteredSoundEvent("block.wood_button.click_on");
/* 1102 */   public static final SoundEvent BLOCK_WOOD_FALL = getRegisteredSoundEvent("block.wood.fall");
/* 1103 */   public static final SoundEvent BLOCK_WOOD_HIT = getRegisteredSoundEvent("block.wood.hit");
/* 1104 */   public static final SoundEvent BLOCK_WOOD_PLACE = getRegisteredSoundEvent("block.wood.place");
/* 1105 */   public static final SoundEvent BLOCK_WOOD_PRESSPLATE_CLICK_OFF = getRegisteredSoundEvent("block.wood_pressureplate.click_off");
/* 1106 */   public static final SoundEvent BLOCK_WOOD_PRESSPLATE_CLICK_ON = getRegisteredSoundEvent("block.wood_pressureplate.click_on");
/* 1107 */   public static final SoundEvent BLOCK_WOOD_STEP = getRegisteredSoundEvent("block.wood.step");
/* 1108 */   public static final SoundEvent ENTITY_ZOMBIE_AMBIENT = getRegisteredSoundEvent("entity.zombie.ambient");
/* 1109 */   public static final SoundEvent ENTITY_ZOMBIE_ATTACK_DOOR_WOOD = getRegisteredSoundEvent("entity.zombie.attack_door_wood");
/* 1110 */   public static final SoundEvent ENTITY_ZOMBIE_ATTACK_IRON_DOOR = getRegisteredSoundEvent("entity.zombie.attack_iron_door");
/* 1111 */   public static final SoundEvent ENTITY_ZOMBIE_BREAK_DOOR_WOOD = getRegisteredSoundEvent("entity.zombie.break_door_wood");
/* 1112 */   public static final SoundEvent ENTITY_ZOMBIE_DEATH = getRegisteredSoundEvent("entity.zombie.death");
/* 1113 */   public static final SoundEvent ENTITY_ZOMBIE_HORSE_AMBIENT = getRegisteredSoundEvent("entity.zombie_horse.ambient");
/* 1114 */   public static final SoundEvent ENTITY_ZOMBIE_HORSE_DEATH = getRegisteredSoundEvent("entity.zombie_horse.death");
/* 1115 */   public static final SoundEvent ENTITY_ZOMBIE_HORSE_HURT = getRegisteredSoundEvent("entity.zombie_horse.hurt");
/* 1116 */   public static final SoundEvent ENTITY_ZOMBIE_HURT = getRegisteredSoundEvent("entity.zombie.hurt");
/* 1117 */   public static final SoundEvent ENTITY_ZOMBIE_INFECT = getRegisteredSoundEvent("entity.zombie.infect");
/* 1118 */   public static final SoundEvent ENTITY_ZOMBIE_PIG_AMBIENT = getRegisteredSoundEvent("entity.zombie_pig.ambient");
/* 1119 */   public static final SoundEvent ENTITY_ZOMBIE_PIG_ANGRY = getRegisteredSoundEvent("entity.zombie_pig.angry");
/* 1120 */   public static final SoundEvent ENTITY_ZOMBIE_PIG_DEATH = getRegisteredSoundEvent("entity.zombie_pig.death");
/* 1121 */   public static final SoundEvent ENTITY_ZOMBIE_PIG_HURT = getRegisteredSoundEvent("entity.zombie_pig.hurt");
/* 1122 */   public static final SoundEvent ENTITY_ZOMBIE_STEP = getRegisteredSoundEvent("entity.zombie.step");
/* 1123 */   public static final SoundEvent ENTITY_ZOMBIE_VILLAGER_AMBIENT = getRegisteredSoundEvent("entity.zombie_villager.ambient");
/* 1124 */   public static final SoundEvent ENTITY_ZOMBIE_VILLAGER_CONVERTED = getRegisteredSoundEvent("entity.zombie_villager.converted");
/* 1125 */   public static final SoundEvent ENTITY_ZOMBIE_VILLAGER_CURE = getRegisteredSoundEvent("entity.zombie_villager.cure");
/* 1126 */   public static final SoundEvent ENTITY_ZOMBIE_VILLAGER_DEATH = getRegisteredSoundEvent("entity.zombie_villager.death");
/* 1127 */   public static final SoundEvent ENTITY_ZOMBIE_VILLAGER_HURT = getRegisteredSoundEvent("entity.zombie_villager.hurt");
/* 1128 */   public static final SoundEvent ENTITY_ZOMBIE_VILLAGER_STEP = getRegisteredSoundEvent("entity.zombie_villager.step");
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\init\SoundEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */