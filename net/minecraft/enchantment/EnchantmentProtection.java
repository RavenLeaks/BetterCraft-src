/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnchantmentProtection
/*     */   extends Enchantment
/*     */ {
/*     */   public final Type protectionType;
/*     */   
/*     */   public EnchantmentProtection(Enchantment.Rarity rarityIn, Type protectionTypeIn, EntityEquipmentSlot... slots) {
/*  19 */     super(rarityIn, EnumEnchantmentType.ARMOR, slots);
/*  20 */     this.protectionType = protectionTypeIn;
/*     */     
/*  22 */     if (protectionTypeIn == Type.FALL)
/*     */     {
/*  24 */       this.type = EnumEnchantmentType.ARMOR_FEET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinEnchantability(int enchantmentLevel) {
/*  33 */     return this.protectionType.getMinimalEnchantability() + (enchantmentLevel - 1) * this.protectionType.getEnchantIncreasePerLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnchantability(int enchantmentLevel) {
/*  41 */     return getMinEnchantability(enchantmentLevel) + this.protectionType.getEnchantIncreasePerLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/*  49 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int calcModifierDamage(int level, DamageSource source) {
/*  57 */     if (source.canHarmInCreative())
/*     */     {
/*  59 */       return 0;
/*     */     }
/*  61 */     if (this.protectionType == Type.ALL)
/*     */     {
/*  63 */       return level;
/*     */     }
/*  65 */     if (this.protectionType == Type.FIRE && source.isFireDamage())
/*     */     {
/*  67 */       return level * 2;
/*     */     }
/*  69 */     if (this.protectionType == Type.FALL && source == DamageSource.fall)
/*     */     {
/*  71 */       return level * 3;
/*     */     }
/*  73 */     if (this.protectionType == Type.EXPLOSION && source.isExplosion())
/*     */     {
/*  75 */       return level * 2;
/*     */     }
/*     */ 
/*     */     
/*  79 */     return (this.protectionType == Type.PROJECTILE && source.isProjectile()) ? (level * 2) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  88 */     return "enchantment.protect." + this.protectionType.getTypeName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApplyTogether(Enchantment ench) {
/*  96 */     if (ench instanceof EnchantmentProtection) {
/*     */       
/*  98 */       EnchantmentProtection enchantmentprotection = (EnchantmentProtection)ench;
/*     */       
/* 100 */       if (this.protectionType == enchantmentprotection.protectionType)
/*     */       {
/* 102 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 106 */       return !(this.protectionType != Type.FALL && enchantmentprotection.protectionType != Type.FALL);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 111 */     return super.canApplyTogether(ench);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFireTimeForEntity(EntityLivingBase p_92093_0_, int p_92093_1_) {
/* 120 */     int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FIRE_PROTECTION, p_92093_0_);
/*     */     
/* 122 */     if (i > 0)
/*     */     {
/* 124 */       p_92093_1_ -= MathHelper.floor(p_92093_1_ * i * 0.15F);
/*     */     }
/*     */     
/* 127 */     return p_92093_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getBlastDamageReduction(EntityLivingBase entityLivingBaseIn, double damage) {
/* 132 */     int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.BLAST_PROTECTION, entityLivingBaseIn);
/*     */     
/* 134 */     if (i > 0)
/*     */     {
/* 136 */       damage -= MathHelper.floor(damage * (i * 0.15F));
/*     */     }
/*     */     
/* 139 */     return damage;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 144 */     ALL("all", 1, 11, 20),
/* 145 */     FIRE("fire", 10, 8, 12),
/* 146 */     FALL("fall", 5, 6, 10),
/* 147 */     EXPLOSION("explosion", 5, 8, 12),
/* 148 */     PROJECTILE("projectile", 3, 6, 15);
/*     */     
/*     */     private final String typeName;
/*     */     
/*     */     private final int minEnchantability;
/*     */     private final int levelCost;
/*     */     private final int levelCostSpan;
/*     */     
/*     */     Type(String name, int minimal, int perLevelEnchantability, int p_i47051_6_) {
/* 157 */       this.typeName = name;
/* 158 */       this.minEnchantability = minimal;
/* 159 */       this.levelCost = perLevelEnchantability;
/* 160 */       this.levelCostSpan = p_i47051_6_;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTypeName() {
/* 165 */       return this.typeName;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMinimalEnchantability() {
/* 170 */       return this.minEnchantability;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getEnchantIncreasePerLevel() {
/* 175 */       return this.levelCost;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentProtection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */