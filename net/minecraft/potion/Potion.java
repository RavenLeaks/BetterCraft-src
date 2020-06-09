/*     */ package net.minecraft.potion;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.registry.RegistryNamespaced;
/*     */ 
/*     */ 
/*     */ public class Potion
/*     */ {
/*  25 */   public static final RegistryNamespaced<ResourceLocation, Potion> REGISTRY = new RegistryNamespaced();
/*  26 */   private final Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isBadEffect;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int liquidColor;
/*     */ 
/*     */   
/*  37 */   private String name = "";
/*     */ 
/*     */   
/*  40 */   private int statusIconIndex = -1;
/*     */ 
/*     */   
/*     */   private double effectiveness;
/*     */ 
/*     */   
/*     */   private boolean beneficial;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Potion getPotionById(int potionID) {
/*  51 */     return (Potion)REGISTRY.getObjectById(potionID);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIdFromPotion(Potion potionIn) {
/*  59 */     return REGISTRY.getIDForObject(potionIn);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Potion getPotionFromResourceLocation(String location) {
/*  65 */     return (Potion)REGISTRY.getObject(new ResourceLocation(location));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Potion(boolean isBadEffectIn, int liquidColorIn) {
/*  70 */     this.isBadEffect = isBadEffectIn;
/*     */     
/*  72 */     if (isBadEffectIn) {
/*     */       
/*  74 */       this.effectiveness = 0.5D;
/*     */     }
/*     */     else {
/*     */       
/*  78 */       this.effectiveness = 1.0D;
/*     */     } 
/*     */     
/*  81 */     this.liquidColor = liquidColorIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Potion setIconIndex(int p_76399_1_, int p_76399_2_) {
/*  89 */     this.statusIconIndex = p_76399_1_ + p_76399_2_ * 8;
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_) {
/*  95 */     if (this == MobEffects.REGENERATION) {
/*     */       
/*  97 */       if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth())
/*     */       {
/*  99 */         entityLivingBaseIn.heal(1.0F);
/*     */       }
/*     */     }
/* 102 */     else if (this == MobEffects.POISON) {
/*     */       
/* 104 */       if (entityLivingBaseIn.getHealth() > 1.0F)
/*     */       {
/* 106 */         entityLivingBaseIn.attackEntityFrom(DamageSource.magic, 1.0F);
/*     */       }
/*     */     }
/* 109 */     else if (this == MobEffects.WITHER) {
/*     */       
/* 111 */       entityLivingBaseIn.attackEntityFrom(DamageSource.wither, 1.0F);
/*     */     }
/* 113 */     else if (this == MobEffects.HUNGER && entityLivingBaseIn instanceof EntityPlayer) {
/*     */       
/* 115 */       ((EntityPlayer)entityLivingBaseIn).addExhaustion(0.005F * (p_76394_2_ + 1));
/*     */     }
/* 117 */     else if (this == MobEffects.SATURATION && entityLivingBaseIn instanceof EntityPlayer) {
/*     */       
/* 119 */       if (!entityLivingBaseIn.world.isRemote)
/*     */       {
/* 121 */         ((EntityPlayer)entityLivingBaseIn).getFoodStats().addStats(p_76394_2_ + 1, 1.0F);
/*     */       }
/*     */     }
/* 124 */     else if ((this != MobEffects.INSTANT_HEALTH || entityLivingBaseIn.isEntityUndead()) && (this != MobEffects.INSTANT_DAMAGE || !entityLivingBaseIn.isEntityUndead())) {
/*     */       
/* 126 */       if ((this == MobEffects.INSTANT_DAMAGE && !entityLivingBaseIn.isEntityUndead()) || (this == MobEffects.INSTANT_HEALTH && entityLivingBaseIn.isEntityUndead()))
/*     */       {
/* 128 */         entityLivingBaseIn.attackEntityFrom(DamageSource.magic, (6 << p_76394_2_));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 133 */       entityLivingBaseIn.heal(Math.max(4 << p_76394_2_, 0));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
/* 139 */     if ((this != MobEffects.INSTANT_HEALTH || entityLivingBaseIn.isEntityUndead()) && (this != MobEffects.INSTANT_DAMAGE || !entityLivingBaseIn.isEntityUndead())) {
/*     */       
/* 141 */       if ((this == MobEffects.INSTANT_DAMAGE && !entityLivingBaseIn.isEntityUndead()) || (this == MobEffects.INSTANT_HEALTH && entityLivingBaseIn.isEntityUndead())) {
/*     */         
/* 143 */         int j = (int)(health * (6 << amplifier) + 0.5D);
/*     */         
/* 145 */         if (source == null)
/*     */         {
/* 147 */           entityLivingBaseIn.attackEntityFrom(DamageSource.magic, j);
/*     */         }
/*     */         else
/*     */         {
/* 151 */           entityLivingBaseIn.attackEntityFrom(DamageSource.causeIndirectMagicDamage(source, indirectSource), j);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 157 */       int i = (int)(health * (4 << amplifier) + 0.5D);
/* 158 */       entityLivingBaseIn.heal(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReady(int duration, int amplifier) {
/* 167 */     if (this == MobEffects.REGENERATION) {
/*     */       
/* 169 */       int k = 50 >> amplifier;
/*     */       
/* 171 */       if (k > 0)
/*     */       {
/* 173 */         return (duration % k == 0);
/*     */       }
/*     */ 
/*     */       
/* 177 */       return true;
/*     */     } 
/*     */     
/* 180 */     if (this == MobEffects.POISON) {
/*     */       
/* 182 */       int j = 25 >> amplifier;
/*     */       
/* 184 */       if (j > 0)
/*     */       {
/* 186 */         return (duration % j == 0);
/*     */       }
/*     */ 
/*     */       
/* 190 */       return true;
/*     */     } 
/*     */     
/* 193 */     if (this == MobEffects.WITHER) {
/*     */       
/* 195 */       int i = 40 >> amplifier;
/*     */       
/* 197 */       if (i > 0)
/*     */       {
/* 199 */         return (duration % i == 0);
/*     */       }
/*     */ 
/*     */       
/* 203 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 208 */     return (this == MobEffects.HUNGER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInstant() {
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Potion setPotionName(String nameIn) {
/* 225 */     this.name = nameIn;
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 234 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasStatusIcon() {
/* 242 */     return (this.statusIconIndex >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStatusIconIndex() {
/* 250 */     return this.statusIconIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBadEffect() {
/* 258 */     return this.isBadEffect;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getPotionDurationString(PotionEffect p_188410_0_, float p_188410_1_) {
/* 263 */     if (p_188410_0_.getIsPotionDurationMax())
/*     */     {
/* 265 */       return "**:**";
/*     */     }
/*     */ 
/*     */     
/* 269 */     int i = MathHelper.floor(p_188410_0_.getDuration() * p_188410_1_);
/* 270 */     return StringUtils.ticksToElapsedTime(i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Potion setEffectiveness(double effectivenessIn) {
/* 276 */     this.effectiveness = effectivenessIn;
/* 277 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLiquidColor() {
/* 285 */     return this.liquidColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Potion registerPotionAttributeModifier(IAttribute attribute, String uniqueId, double ammount, int operation) {
/* 293 */     AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uniqueId), getName(), ammount, operation);
/* 294 */     this.attributeModifierMap.put(attribute, attributemodifier);
/* 295 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<IAttribute, AttributeModifier> getAttributeModifierMap() {
/* 300 */     return this.attributeModifierMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
/* 305 */     for (Map.Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
/*     */       
/* 307 */       IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance(entry.getKey());
/*     */       
/* 309 */       if (iattributeinstance != null)
/*     */       {
/* 311 */         iattributeinstance.removeModifier(entry.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
/* 318 */     for (Map.Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
/*     */       
/* 320 */       IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance(entry.getKey());
/*     */       
/* 322 */       if (iattributeinstance != null) {
/*     */         
/* 324 */         AttributeModifier attributemodifier = entry.getValue();
/* 325 */         iattributeinstance.removeModifier(attributemodifier);
/* 326 */         iattributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), String.valueOf(getName()) + " " + amplifier, getAttributeModifierAmount(amplifier, attributemodifier), attributemodifier.getOperation()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
/* 333 */     return modifier.getAmount() * (amplifier + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBeneficial() {
/* 341 */     return this.beneficial;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Potion setBeneficial() {
/* 349 */     this.beneficial = true;
/* 350 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerPotions() {
/* 355 */     REGISTRY.register(1, new ResourceLocation("speed"), (new Potion(false, 8171462)).setPotionName("effect.moveSpeed").setIconIndex(0, 0).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2).setBeneficial());
/* 356 */     REGISTRY.register(2, new ResourceLocation("slowness"), (new Potion(true, 5926017)).setPotionName("effect.moveSlowdown").setIconIndex(1, 0).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2));
/* 357 */     REGISTRY.register(3, new ResourceLocation("haste"), (new Potion(false, 14270531)).setPotionName("effect.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5D).setBeneficial().registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.10000000149011612D, 2));
/* 358 */     REGISTRY.register(4, new ResourceLocation("mining_fatigue"), (new Potion(true, 4866583)).setPotionName("effect.digSlowDown").setIconIndex(3, 0).registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", -0.10000000149011612D, 2));
/* 359 */     REGISTRY.register(5, new ResourceLocation("strength"), (new PotionAttackDamage(false, 9643043, 3.0D)).setPotionName("effect.damageBoost").setIconIndex(4, 0).registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D, 0).setBeneficial());
/* 360 */     REGISTRY.register(6, new ResourceLocation("instant_health"), (new PotionHealth(false, 16262179)).setPotionName("effect.heal").setBeneficial());
/* 361 */     REGISTRY.register(7, new ResourceLocation("instant_damage"), (new PotionHealth(true, 4393481)).setPotionName("effect.harm").setBeneficial());
/* 362 */     REGISTRY.register(8, new ResourceLocation("jump_boost"), (new Potion(false, 2293580)).setPotionName("effect.jump").setIconIndex(2, 1).setBeneficial());
/* 363 */     REGISTRY.register(9, new ResourceLocation("nausea"), (new Potion(true, 5578058)).setPotionName("effect.confusion").setIconIndex(3, 1).setEffectiveness(0.25D));
/* 364 */     REGISTRY.register(10, new ResourceLocation("regeneration"), (new Potion(false, 13458603)).setPotionName("effect.regeneration").setIconIndex(7, 0).setEffectiveness(0.25D).setBeneficial());
/* 365 */     REGISTRY.register(11, new ResourceLocation("resistance"), (new Potion(false, 10044730)).setPotionName("effect.resistance").setIconIndex(6, 1).setBeneficial());
/* 366 */     REGISTRY.register(12, new ResourceLocation("fire_resistance"), (new Potion(false, 14981690)).setPotionName("effect.fireResistance").setIconIndex(7, 1).setBeneficial());
/* 367 */     REGISTRY.register(13, new ResourceLocation("water_breathing"), (new Potion(false, 3035801)).setPotionName("effect.waterBreathing").setIconIndex(0, 2).setBeneficial());
/* 368 */     REGISTRY.register(14, new ResourceLocation("invisibility"), (new Potion(false, 8356754)).setPotionName("effect.invisibility").setIconIndex(0, 1).setBeneficial());
/* 369 */     REGISTRY.register(15, new ResourceLocation("blindness"), (new Potion(true, 2039587)).setPotionName("effect.blindness").setIconIndex(5, 1).setEffectiveness(0.25D));
/* 370 */     REGISTRY.register(16, new ResourceLocation("night_vision"), (new Potion(false, 2039713)).setPotionName("effect.nightVision").setIconIndex(4, 1).setBeneficial());
/* 371 */     REGISTRY.register(17, new ResourceLocation("hunger"), (new Potion(true, 5797459)).setPotionName("effect.hunger").setIconIndex(1, 1));
/* 372 */     REGISTRY.register(18, new ResourceLocation("weakness"), (new PotionAttackDamage(true, 4738376, -4.0D)).setPotionName("effect.weakness").setIconIndex(5, 0).registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, 0));
/* 373 */     REGISTRY.register(19, new ResourceLocation("poison"), (new Potion(true, 5149489)).setPotionName("effect.poison").setIconIndex(6, 0).setEffectiveness(0.25D));
/* 374 */     REGISTRY.register(20, new ResourceLocation("wither"), (new Potion(true, 3484199)).setPotionName("effect.wither").setIconIndex(1, 2).setEffectiveness(0.25D));
/* 375 */     REGISTRY.register(21, new ResourceLocation("health_boost"), (new PotionHealthBoost(false, 16284963)).setPotionName("effect.healthBoost").setIconIndex(7, 2).registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0).setBeneficial());
/* 376 */     REGISTRY.register(22, new ResourceLocation("absorption"), (new PotionAbsorption(false, 2445989)).setPotionName("effect.absorption").setIconIndex(2, 2).setBeneficial());
/* 377 */     REGISTRY.register(23, new ResourceLocation("saturation"), (new PotionHealth(false, 16262179)).setPotionName("effect.saturation").setBeneficial());
/* 378 */     REGISTRY.register(24, new ResourceLocation("glowing"), (new Potion(false, 9740385)).setPotionName("effect.glowing").setIconIndex(4, 2));
/* 379 */     REGISTRY.register(25, new ResourceLocation("levitation"), (new Potion(true, 13565951)).setPotionName("effect.levitation").setIconIndex(3, 2));
/* 380 */     REGISTRY.register(26, new ResourceLocation("luck"), (new Potion(false, 3381504)).setPotionName("effect.luck").setIconIndex(5, 2).setBeneficial().registerPotionAttributeModifier(SharedMonsterAttributes.LUCK, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 1.0D, 0));
/* 381 */     REGISTRY.register(27, new ResourceLocation("unluck"), (new Potion(true, 12624973)).setPotionName("effect.unluck").setIconIndex(6, 2).registerPotionAttributeModifier(SharedMonsterAttributes.LUCK, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0D, 0));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\potion\Potion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */