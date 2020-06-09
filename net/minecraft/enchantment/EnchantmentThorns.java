/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.Enchantments;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.DamageSource;
/*    */ 
/*    */ 
/*    */ public class EnchantmentThorns
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentThorns(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
/* 16 */     super(rarityIn, EnumEnchantmentType.ARMOR_CHEST, slots);
/* 17 */     setName("thorns");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 25 */     return 10 + 20 * (enchantmentLevel - 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 33 */     return super.getMinEnchantability(enchantmentLevel) + 50;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 41 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canApply(ItemStack stack) {
/* 49 */     return (stack.getItem() instanceof net.minecraft.item.ItemArmor) ? true : super.canApply(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
/* 58 */     Random random = user.getRNG();
/* 59 */     ItemStack itemstack = EnchantmentHelper.getEnchantedItem(Enchantments.THORNS, user);
/*    */     
/* 61 */     if (shouldHit(level, random)) {
/*    */       
/* 63 */       if (attacker != null)
/*    */       {
/* 65 */         attacker.attackEntityFrom(DamageSource.causeThornsDamage((Entity)user), getDamage(level, random));
/*    */       }
/*    */       
/* 68 */       if (!itemstack.func_190926_b())
/*    */       {
/* 70 */         itemstack.damageItem(3, user);
/*    */       }
/*    */     }
/* 73 */     else if (!itemstack.func_190926_b()) {
/*    */       
/* 75 */       itemstack.damageItem(1, user);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean shouldHit(int level, Random rnd) {
/* 81 */     if (level <= 0)
/*    */     {
/* 83 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 87 */     return (rnd.nextFloat() < 0.15F * level);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getDamage(int level, Random rnd) {
/* 93 */     return (level > 10) ? (level - 10) : (1 + rnd.nextInt(4));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnchantmentThorns.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */