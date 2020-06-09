/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemEnchantedBook;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.Util;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnchantmentHelper
/*     */ {
/*  31 */   private static final ModifierDamage ENCHANTMENT_MODIFIER_DAMAGE = new ModifierDamage(null);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   private static final ModifierLiving ENCHANTMENT_MODIFIER_LIVING = new ModifierLiving(null);
/*  37 */   private static final HurtIterator ENCHANTMENT_ITERATOR_HURT = new HurtIterator(null);
/*  38 */   private static final DamageIterator ENCHANTMENT_ITERATOR_DAMAGE = new DamageIterator(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEnchantmentLevel(Enchantment enchID, ItemStack stack) {
/*  45 */     if (stack.func_190926_b())
/*     */     {
/*  47 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  51 */     NBTTagList nbttaglist = stack.getEnchantmentTagList();
/*     */     
/*  53 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/*  55 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  56 */       Enchantment enchantment = Enchantment.getEnchantmentByID(nbttagcompound.getShort("id"));
/*  57 */       int j = nbttagcompound.getShort("lvl");
/*     */       
/*  59 */       if (enchantment == enchID)
/*     */       {
/*  61 */         return j;
/*     */       }
/*     */     } 
/*     */     
/*  65 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<Enchantment, Integer> getEnchantments(ItemStack stack) {
/*  71 */     Map<Enchantment, Integer> map = Maps.newLinkedHashMap();
/*  72 */     NBTTagList nbttaglist = (stack.getItem() == Items.ENCHANTED_BOOK) ? ItemEnchantedBook.getEnchantments(stack) : stack.getEnchantmentTagList();
/*     */     
/*  74 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/*  76 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  77 */       Enchantment enchantment = Enchantment.getEnchantmentByID(nbttagcompound.getShort("id"));
/*  78 */       int j = nbttagcompound.getShort("lvl");
/*  79 */       map.put(enchantment, Integer.valueOf(j));
/*     */     } 
/*     */     
/*  82 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setEnchantments(Map<Enchantment, Integer> enchMap, ItemStack stack) {
/*  90 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  92 */     for (Map.Entry<Enchantment, Integer> entry : enchMap.entrySet()) {
/*     */       
/*  94 */       Enchantment enchantment = entry.getKey();
/*     */       
/*  96 */       if (enchantment != null) {
/*     */         
/*  98 */         int i = ((Integer)entry.getValue()).intValue();
/*  99 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 100 */         nbttagcompound.setShort("id", (short)Enchantment.getEnchantmentID(enchantment));
/* 101 */         nbttagcompound.setShort("lvl", (short)i);
/* 102 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */         
/* 104 */         if (stack.getItem() == Items.ENCHANTED_BOOK)
/*     */         {
/* 106 */           ItemEnchantedBook.addEnchantment(stack, new EnchantmentData(enchantment, i));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     if (nbttaglist.hasNoTags()) {
/*     */       
/* 113 */       if (stack.hasTagCompound())
/*     */       {
/* 115 */         stack.getTagCompound().removeTag("ench");
/*     */       }
/*     */     }
/* 118 */     else if (stack.getItem() != Items.ENCHANTED_BOOK) {
/*     */       
/* 120 */       stack.setTagInfo("ench", (NBTBase)nbttaglist);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyEnchantmentModifier(IModifier modifier, ItemStack stack) {
/* 129 */     if (!stack.func_190926_b()) {
/*     */       
/* 131 */       NBTTagList nbttaglist = stack.getEnchantmentTagList();
/*     */       
/* 133 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 135 */         int j = nbttaglist.getCompoundTagAt(i).getShort("id");
/* 136 */         int k = nbttaglist.getCompoundTagAt(i).getShort("lvl");
/*     */         
/* 138 */         if (Enchantment.getEnchantmentByID(j) != null)
/*     */         {
/* 140 */           modifier.calculateModifier(Enchantment.getEnchantmentByID(j), k);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applyEnchantmentModifierArray(IModifier modifier, Iterable<ItemStack> stacks) {
/* 151 */     for (ItemStack itemstack : stacks)
/*     */     {
/* 153 */       applyEnchantmentModifier(modifier, itemstack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEnchantmentModifierDamage(Iterable<ItemStack> stacks, DamageSource source) {
/* 162 */     ENCHANTMENT_MODIFIER_DAMAGE.damageModifier = 0;
/* 163 */     ENCHANTMENT_MODIFIER_DAMAGE.source = source;
/* 164 */     applyEnchantmentModifierArray(ENCHANTMENT_MODIFIER_DAMAGE, stacks);
/* 165 */     return ENCHANTMENT_MODIFIER_DAMAGE.damageModifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getModifierForCreature(ItemStack stack, EnumCreatureAttribute creatureAttribute) {
/* 170 */     ENCHANTMENT_MODIFIER_LIVING.livingModifier = 0.0F;
/* 171 */     ENCHANTMENT_MODIFIER_LIVING.entityLiving = creatureAttribute;
/* 172 */     applyEnchantmentModifier(ENCHANTMENT_MODIFIER_LIVING, stack);
/* 173 */     return ENCHANTMENT_MODIFIER_LIVING.livingModifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float func_191527_a(EntityLivingBase p_191527_0_) {
/* 178 */     int i = getMaxEnchantmentLevel(Enchantments.field_191530_r, p_191527_0_);
/* 179 */     return (i > 0) ? EnchantmentSweepingEdge.func_191526_e(i) : 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyThornEnchantments(EntityLivingBase p_151384_0_, Entity p_151384_1_) {
/* 184 */     ENCHANTMENT_ITERATOR_HURT.attacker = p_151384_1_;
/* 185 */     ENCHANTMENT_ITERATOR_HURT.user = p_151384_0_;
/*     */     
/* 187 */     if (p_151384_0_ != null)
/*     */     {
/* 189 */       applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_HURT, p_151384_0_.getEquipmentAndArmor());
/*     */     }
/*     */     
/* 192 */     if (p_151384_1_ instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 194 */       applyEnchantmentModifier(ENCHANTMENT_ITERATOR_HURT, p_151384_0_.getHeldItemMainhand());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyArthropodEnchantments(EntityLivingBase p_151385_0_, Entity p_151385_1_) {
/* 200 */     ENCHANTMENT_ITERATOR_DAMAGE.user = p_151385_0_;
/* 201 */     ENCHANTMENT_ITERATOR_DAMAGE.target = p_151385_1_;
/*     */     
/* 203 */     if (p_151385_0_ != null)
/*     */     {
/* 205 */       applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_DAMAGE, p_151385_0_.getEquipmentAndArmor());
/*     */     }
/*     */     
/* 208 */     if (p_151385_0_ instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 210 */       applyEnchantmentModifier(ENCHANTMENT_ITERATOR_DAMAGE, p_151385_0_.getHeldItemMainhand());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getMaxEnchantmentLevel(Enchantment p_185284_0_, EntityLivingBase p_185284_1_) {
/* 216 */     Iterable<ItemStack> iterable = p_185284_0_.getEntityEquipment(p_185284_1_);
/*     */     
/* 218 */     if (iterable == null)
/*     */     {
/* 220 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 224 */     int i = 0;
/*     */     
/* 226 */     for (ItemStack itemstack : iterable) {
/*     */       
/* 228 */       int j = getEnchantmentLevel(p_185284_0_, itemstack);
/*     */       
/* 230 */       if (j > i)
/*     */       {
/* 232 */         i = j;
/*     */       }
/*     */     } 
/*     */     
/* 236 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getKnockbackModifier(EntityLivingBase player) {
/* 245 */     return getMaxEnchantmentLevel(Enchantments.KNOCKBACK, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFireAspectModifier(EntityLivingBase player) {
/* 253 */     return getMaxEnchantmentLevel(Enchantments.FIRE_ASPECT, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getRespirationModifier(EntityLivingBase p_185292_0_) {
/* 258 */     return getMaxEnchantmentLevel(Enchantments.RESPIRATION, p_185292_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getDepthStriderModifier(EntityLivingBase p_185294_0_) {
/* 263 */     return getMaxEnchantmentLevel(Enchantments.DEPTH_STRIDER, p_185294_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getEfficiencyModifier(EntityLivingBase p_185293_0_) {
/* 268 */     return getMaxEnchantmentLevel(Enchantments.EFFICIENCY, p_185293_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_191529_b(ItemStack p_191529_0_) {
/* 273 */     return getEnchantmentLevel(Enchantments.LUCK_OF_THE_SEA, p_191529_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_191528_c(ItemStack p_191528_0_) {
/* 278 */     return getEnchantmentLevel(Enchantments.LURE, p_191528_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getLootingModifier(EntityLivingBase p_185283_0_) {
/* 283 */     return getMaxEnchantmentLevel(Enchantments.LOOTING, p_185283_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getAquaAffinityModifier(EntityLivingBase p_185287_0_) {
/* 288 */     return (getMaxEnchantmentLevel(Enchantments.AQUA_AFFINITY, p_185287_0_) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasFrostWalkerEnchantment(EntityLivingBase player) {
/* 299 */     return (getMaxEnchantmentLevel(Enchantments.FROST_WALKER, player) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean func_190938_b(ItemStack p_190938_0_) {
/* 304 */     return (getEnchantmentLevel(Enchantments.field_190941_k, p_190938_0_) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean func_190939_c(ItemStack p_190939_0_) {
/* 309 */     return (getEnchantmentLevel(Enchantments.field_190940_C, p_190939_0_) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getEnchantedItem(Enchantment p_92099_0_, EntityLivingBase p_92099_1_) {
/* 314 */     List<ItemStack> list = p_92099_0_.getEntityEquipment(p_92099_1_);
/*     */     
/* 316 */     if (list.isEmpty())
/*     */     {
/* 318 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/* 322 */     List<ItemStack> list1 = Lists.newArrayList();
/*     */     
/* 324 */     for (ItemStack itemstack : list) {
/*     */       
/* 326 */       if (!itemstack.func_190926_b() && getEnchantmentLevel(p_92099_0_, itemstack) > 0)
/*     */       {
/* 328 */         list1.add(itemstack);
/*     */       }
/*     */     } 
/*     */     
/* 332 */     return list1.isEmpty() ? ItemStack.field_190927_a : list1.get(p_92099_1_.getRNG().nextInt(list1.size()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcItemStackEnchantability(Random rand, int enchantNum, int power, ItemStack stack) {
/* 342 */     Item item = stack.getItem();
/* 343 */     int i = item.getItemEnchantability();
/*     */     
/* 345 */     if (i <= 0)
/*     */     {
/* 347 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 351 */     if (power > 15)
/*     */     {
/* 353 */       power = 15;
/*     */     }
/*     */     
/* 356 */     int j = rand.nextInt(8) + 1 + (power >> 1) + rand.nextInt(power + 1);
/*     */     
/* 358 */     if (enchantNum == 0)
/*     */     {
/* 360 */       return Math.max(j / 3, 1);
/*     */     }
/*     */ 
/*     */     
/* 364 */     return (enchantNum == 1) ? (j * 2 / 3 + 1) : Math.max(j, power * 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack addRandomEnchantment(Random random, ItemStack p_77504_1_, int p_77504_2_, boolean allowTreasure) {
/* 374 */     List<EnchantmentData> list = buildEnchantmentList(random, p_77504_1_, p_77504_2_, allowTreasure);
/* 375 */     boolean flag = (p_77504_1_.getItem() == Items.BOOK);
/*     */     
/* 377 */     if (flag)
/*     */     {
/* 379 */       p_77504_1_ = new ItemStack(Items.ENCHANTED_BOOK);
/*     */     }
/*     */     
/* 382 */     for (EnchantmentData enchantmentdata : list) {
/*     */       
/* 384 */       if (flag) {
/*     */         
/* 386 */         ItemEnchantedBook.addEnchantment(p_77504_1_, enchantmentdata);
/*     */         
/*     */         continue;
/*     */       } 
/* 390 */       p_77504_1_.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
/*     */     } 
/*     */ 
/*     */     
/* 394 */     return p_77504_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<EnchantmentData> buildEnchantmentList(Random randomIn, ItemStack itemStackIn, int p_77513_2_, boolean allowTreasure) {
/* 399 */     List<EnchantmentData> list = Lists.newArrayList();
/* 400 */     Item item = itemStackIn.getItem();
/* 401 */     int i = item.getItemEnchantability();
/*     */     
/* 403 */     if (i <= 0)
/*     */     {
/* 405 */       return list;
/*     */     }
/*     */ 
/*     */     
/* 409 */     p_77513_2_ = p_77513_2_ + 1 + randomIn.nextInt(i / 4 + 1) + randomIn.nextInt(i / 4 + 1);
/* 410 */     float f = (randomIn.nextFloat() + randomIn.nextFloat() - 1.0F) * 0.15F;
/* 411 */     p_77513_2_ = MathHelper.clamp(Math.round(p_77513_2_ + p_77513_2_ * f), 1, 2147483647);
/* 412 */     List<EnchantmentData> list1 = getEnchantmentDatas(p_77513_2_, itemStackIn, allowTreasure);
/*     */     
/* 414 */     if (!list1.isEmpty()) {
/*     */       
/* 416 */       list.add((EnchantmentData)WeightedRandom.getRandomItem(randomIn, list1));
/*     */       
/* 418 */       while (randomIn.nextInt(50) <= p_77513_2_) {
/*     */         
/* 420 */         removeIncompatible(list1, (EnchantmentData)Util.getLastElement(list));
/*     */         
/* 422 */         if (list1.isEmpty()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 427 */         list.add((EnchantmentData)WeightedRandom.getRandomItem(randomIn, list1));
/* 428 */         p_77513_2_ /= 2;
/*     */       } 
/*     */     } 
/*     */     
/* 432 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeIncompatible(List<EnchantmentData> p_185282_0_, EnchantmentData p_185282_1_) {
/* 438 */     Iterator<EnchantmentData> iterator = p_185282_0_.iterator();
/*     */     
/* 440 */     while (iterator.hasNext()) {
/*     */       
/* 442 */       if (!p_185282_1_.enchantmentobj.func_191560_c(((EnchantmentData)iterator.next()).enchantmentobj))
/*     */       {
/* 444 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<EnchantmentData> getEnchantmentDatas(int p_185291_0_, ItemStack p_185291_1_, boolean allowTreasure) {
/* 451 */     List<EnchantmentData> list = Lists.newArrayList();
/* 452 */     Item item = p_185291_1_.getItem();
/* 453 */     boolean flag = (p_185291_1_.getItem() == Items.BOOK);
/*     */     
/* 455 */     for (Enchantment enchantment : Enchantment.REGISTRY) {
/*     */       
/* 457 */       if ((!enchantment.isTreasureEnchantment() || allowTreasure) && (enchantment.type.canEnchantItem(item) || flag))
/*     */       {
/* 459 */         for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; i--) {
/*     */           
/* 461 */           if (p_185291_0_ >= enchantment.getMinEnchantability(i) && p_185291_0_ <= enchantment.getMaxEnchantability(i)) {
/*     */             
/* 463 */             list.add(new EnchantmentData(enchantment, i));
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 470 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class DamageIterator
/*     */     implements IModifier
/*     */   {
/*     */     public EntityLivingBase user;
/*     */     
/*     */     public Entity target;
/*     */     
/*     */     private DamageIterator() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 484 */       enchantmentIn.onEntityDamaged(this.user, this.target, enchantmentLevel);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class HurtIterator
/*     */     implements IModifier
/*     */   {
/*     */     public EntityLivingBase user;
/*     */     
/*     */     public Entity attacker;
/*     */     
/*     */     private HurtIterator() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 499 */       enchantmentIn.onUserHurt(this.user, this.attacker, enchantmentLevel);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static interface IModifier
/*     */   {
/*     */     void calculateModifier(Enchantment param1Enchantment, int param1Int);
/*     */   }
/*     */ 
/*     */   
/*     */   static final class ModifierDamage
/*     */     implements IModifier
/*     */   {
/*     */     public int damageModifier;
/*     */     public DamageSource source;
/*     */     
/*     */     private ModifierDamage() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 519 */       this.damageModifier += enchantmentIn.calcModifierDamage(enchantmentLevel, this.source);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class ModifierLiving
/*     */     implements IModifier
/*     */   {
/*     */     public float livingModifier;
/*     */     
/*     */     public EnumCreatureAttribute entityLiving;
/*     */     
/*     */     private ModifierLiving() {}
/*     */     
/*     */     public void calculateModifier(Enchantment enchantmentIn, int enchantmentLevel) {
/* 534 */       this.livingModifier += enchantmentIn.calcDamageByCreature(enchantmentLevel, this.entityLiving);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */