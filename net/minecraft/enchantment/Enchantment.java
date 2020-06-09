/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.registry.RegistryNamespaced;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ 
/*     */ public abstract class Enchantment
/*     */ {
/*  19 */   public static final RegistryNamespaced<ResourceLocation, Enchantment> REGISTRY = new RegistryNamespaced();
/*     */ 
/*     */ 
/*     */   
/*     */   private final EntityEquipmentSlot[] applicableEquipmentTypes;
/*     */ 
/*     */   
/*     */   private final Rarity rarity;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EnumEnchantmentType type;
/*     */ 
/*     */   
/*     */   protected String name;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Enchantment getEnchantmentByID(int id) {
/*  39 */     return (Enchantment)REGISTRY.getObjectById(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEnchantmentID(Enchantment enchantmentIn) {
/*  47 */     return REGISTRY.getIDForObject(enchantmentIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Enchantment getEnchantmentByLocation(String location) {
/*  57 */     return (Enchantment)REGISTRY.getObject(new ResourceLocation(location));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Enchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
/*  62 */     this.rarity = rarityIn;
/*  63 */     this.type = typeIn;
/*  64 */     this.applicableEquipmentTypes = slots;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getEntityEquipment(EntityLivingBase entityIn) {
/*  69 */     List<ItemStack> list = Lists.newArrayList(); byte b; int i;
/*     */     EntityEquipmentSlot[] arrayOfEntityEquipmentSlot;
/*  71 */     for (i = (arrayOfEntityEquipmentSlot = this.applicableEquipmentTypes).length, b = 0; b < i; ) { EntityEquipmentSlot entityequipmentslot = arrayOfEntityEquipmentSlot[b];
/*     */       
/*  73 */       ItemStack itemstack = entityIn.getItemStackFromSlot(entityequipmentslot);
/*     */       
/*  75 */       if (!itemstack.func_190926_b())
/*     */       {
/*  77 */         list.add(itemstack);
/*     */       }
/*     */       b++; }
/*     */     
/*  81 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rarity getRarity() {
/*  90 */     return this.rarity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinLevel() {
/*  98 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/* 106 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinEnchantability(int enchantmentLevel) {
/* 114 */     return 1 + enchantmentLevel * 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnchantability(int enchantmentLevel) {
/* 122 */     return getMinEnchantability(enchantmentLevel) + 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int calcModifierDamage(int level, DamageSource source) {
/* 130 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
/* 139 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean func_191560_c(Enchantment p_191560_1_) {
/* 144 */     return (canApplyTogether(p_191560_1_) && p_191560_1_.canApplyTogether(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canApplyTogether(Enchantment ench) {
/* 152 */     return (this != ench);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enchantment setName(String enchName) {
/* 160 */     this.name = enchName;
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 169 */     return "enchantment." + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslatedName(int level) {
/* 177 */     String s = I18n.translateToLocal(getName());
/*     */     
/* 179 */     if (func_190936_d())
/*     */     {
/* 181 */       s = TextFormatting.RED + s;
/*     */     }
/*     */     
/* 184 */     return (level == 1 && getMaxLevel() == 1) ? s : (String.valueOf(s) + " " + I18n.translateToLocal("enchantment.level." + level));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApply(ItemStack stack) {
/* 192 */     return this.type.canEnchantItem(stack.getItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTreasureEnchantment() {
/* 212 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190936_d() {
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerEnchantments() {
/* 225 */     EntityEquipmentSlot[] aentityequipmentslot = { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };
/* 226 */     REGISTRY.register(0, new ResourceLocation("protection"), new EnchantmentProtection(Rarity.COMMON, EnchantmentProtection.Type.ALL, aentityequipmentslot));
/* 227 */     REGISTRY.register(1, new ResourceLocation("fire_protection"), new EnchantmentProtection(Rarity.UNCOMMON, EnchantmentProtection.Type.FIRE, aentityequipmentslot));
/* 228 */     REGISTRY.register(2, new ResourceLocation("feather_falling"), new EnchantmentProtection(Rarity.UNCOMMON, EnchantmentProtection.Type.FALL, aentityequipmentslot));
/* 229 */     REGISTRY.register(3, new ResourceLocation("blast_protection"), new EnchantmentProtection(Rarity.RARE, EnchantmentProtection.Type.EXPLOSION, aentityequipmentslot));
/* 230 */     REGISTRY.register(4, new ResourceLocation("projectile_protection"), new EnchantmentProtection(Rarity.UNCOMMON, EnchantmentProtection.Type.PROJECTILE, aentityequipmentslot));
/* 231 */     REGISTRY.register(5, new ResourceLocation("respiration"), new EnchantmentOxygen(Rarity.RARE, aentityequipmentslot));
/* 232 */     REGISTRY.register(6, new ResourceLocation("aqua_affinity"), new EnchantmentWaterWorker(Rarity.RARE, aentityequipmentslot));
/* 233 */     REGISTRY.register(7, new ResourceLocation("thorns"), new EnchantmentThorns(Rarity.VERY_RARE, aentityequipmentslot));
/* 234 */     REGISTRY.register(8, new ResourceLocation("depth_strider"), new EnchantmentWaterWalker(Rarity.RARE, aentityequipmentslot));
/* 235 */     REGISTRY.register(9, new ResourceLocation("frost_walker"), new EnchantmentFrostWalker(Rarity.RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.FEET }));
/* 236 */     REGISTRY.register(10, new ResourceLocation("binding_curse"), new EnchantmentBindingCurse(Rarity.VERY_RARE, aentityequipmentslot));
/* 237 */     REGISTRY.register(16, new ResourceLocation("sharpness"), new EnchantmentDamage(Rarity.COMMON, 0, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 238 */     REGISTRY.register(17, new ResourceLocation("smite"), new EnchantmentDamage(Rarity.UNCOMMON, 1, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 239 */     REGISTRY.register(18, new ResourceLocation("bane_of_arthropods"), new EnchantmentDamage(Rarity.UNCOMMON, 2, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 240 */     REGISTRY.register(19, new ResourceLocation("knockback"), new EnchantmentKnockback(Rarity.UNCOMMON, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 241 */     REGISTRY.register(20, new ResourceLocation("fire_aspect"), new EnchantmentFireAspect(Rarity.RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 242 */     REGISTRY.register(21, new ResourceLocation("looting"), new EnchantmentLootBonus(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 243 */     REGISTRY.register(22, new ResourceLocation("sweeping"), new EnchantmentSweepingEdge(Rarity.RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 244 */     REGISTRY.register(32, new ResourceLocation("efficiency"), new EnchantmentDigging(Rarity.COMMON, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 245 */     REGISTRY.register(33, new ResourceLocation("silk_touch"), new EnchantmentUntouching(Rarity.VERY_RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 246 */     REGISTRY.register(34, new ResourceLocation("unbreaking"), new EnchantmentDurability(Rarity.UNCOMMON, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 247 */     REGISTRY.register(35, new ResourceLocation("fortune"), new EnchantmentLootBonus(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 248 */     REGISTRY.register(48, new ResourceLocation("power"), new EnchantmentArrowDamage(Rarity.COMMON, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 249 */     REGISTRY.register(49, new ResourceLocation("punch"), new EnchantmentArrowKnockback(Rarity.RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 250 */     REGISTRY.register(50, new ResourceLocation("flame"), new EnchantmentArrowFire(Rarity.RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 251 */     REGISTRY.register(51, new ResourceLocation("infinity"), new EnchantmentArrowInfinite(Rarity.VERY_RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 252 */     REGISTRY.register(61, new ResourceLocation("luck_of_the_sea"), new EnchantmentLootBonus(Rarity.RARE, EnumEnchantmentType.FISHING_ROD, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 253 */     REGISTRY.register(62, new ResourceLocation("lure"), new EnchantmentFishingSpeed(Rarity.RARE, EnumEnchantmentType.FISHING_ROD, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND }));
/* 254 */     REGISTRY.register(70, new ResourceLocation("mending"), new EnchantmentMending(Rarity.RARE, EntityEquipmentSlot.values()));
/* 255 */     REGISTRY.register(71, new ResourceLocation("vanishing_curse"), new EnchantmentVanishingCurse(Rarity.VERY_RARE, EntityEquipmentSlot.values()));
/*     */   }
/*     */   
/*     */   public enum Rarity
/*     */   {
/* 260 */     COMMON(10),
/* 261 */     UNCOMMON(5),
/* 262 */     RARE(2),
/* 263 */     VERY_RARE(1);
/*     */     
/*     */     private final int weight;
/*     */ 
/*     */     
/*     */     Rarity(int rarityWeight) {
/* 269 */       this.weight = rarityWeight;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getWeight() {
/* 274 */       return this.weight;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\Enchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */