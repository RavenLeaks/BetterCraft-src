/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ 
/*     */ 
/*     */ public class EnchantmentDamage
/*     */   extends Enchantment
/*     */ {
/*  15 */   private static final String[] PROTECTION_NAME = new String[] { "all", "undead", "arthropods" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  20 */   private static final int[] BASE_ENCHANTABILITY = new int[] { 1, 5, 5 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   private static final int[] LEVEL_ENCHANTABILITY = new int[] { 11, 8, 8 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final int[] THRESHOLD_ENCHANTABILITY = new int[] { 20, 20, 20 };
/*     */ 
/*     */ 
/*     */   
/*     */   public final int damageType;
/*     */ 
/*     */ 
/*     */   
/*     */   public EnchantmentDamage(Enchantment.Rarity rarityIn, int damageTypeIn, EntityEquipmentSlot... slots) {
/*  40 */     super(rarityIn, EnumEnchantmentType.WEAPON, slots);
/*  41 */     this.damageType = damageTypeIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinEnchantability(int enchantmentLevel) {
/*  49 */     return BASE_ENCHANTABILITY[this.damageType] + (enchantmentLevel - 1) * LEVEL_ENCHANTABILITY[this.damageType];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnchantability(int enchantmentLevel) {
/*  57 */     return getMinEnchantability(enchantmentLevel) + THRESHOLD_ENCHANTABILITY[this.damageType];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/*  65 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
/*  74 */     if (this.damageType == 0)
/*     */     {
/*  76 */       return 1.0F + Math.max(0, level - 1) * 0.5F;
/*     */     }
/*  78 */     if (this.damageType == 1 && creatureType == EnumCreatureAttribute.UNDEAD)
/*     */     {
/*  80 */       return level * 2.5F;
/*     */     }
/*     */ 
/*     */     
/*  84 */     return (this.damageType == 2 && creatureType == EnumCreatureAttribute.ARTHROPOD) ? (level * 2.5F) : 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  93 */     return "enchantment.damage." + PROTECTION_NAME[this.damageType];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApplyTogether(Enchantment ench) {
/* 101 */     return !(ench instanceof EnchantmentDamage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApply(ItemStack stack) {
/* 109 */     return (stack.getItem() instanceof net.minecraft.item.ItemAxe) ? true : super.canApply(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
/* 117 */     if (target instanceof EntityLivingBase) {
/*     */       
/* 119 */       EntityLivingBase entitylivingbase = (EntityLivingBase)target;
/*     */       
/* 121 */       if (this.damageType == 2 && entitylivingbase.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
/*     */         
/* 123 */         int i = 20 + user.getRNG().nextInt(10 * level);
/* 124 */         entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, i, 3));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentDamage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */