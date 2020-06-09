/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemEnchantedBook
/*     */   extends Item {
/*     */   public boolean hasEffect(ItemStack stack) {
/*  19 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemTool(ItemStack stack) {
/*  27 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumRarity getRarity(ItemStack stack) {
/*  35 */     return getEnchantments(stack).hasNoTags() ? super.getRarity(stack) : EnumRarity.UNCOMMON;
/*     */   }
/*     */ 
/*     */   
/*     */   public static NBTTagList getEnchantments(ItemStack p_92110_0_) {
/*  40 */     NBTTagCompound nbttagcompound = p_92110_0_.getTagCompound();
/*  41 */     return (nbttagcompound != null) ? nbttagcompound.getTagList("StoredEnchantments", 10) : new NBTTagList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced) {
/*  49 */     super.addInformation(stack, playerIn, tooltip, advanced);
/*  50 */     NBTTagList nbttaglist = getEnchantments(stack);
/*     */     
/*  52 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/*  54 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  55 */       int j = nbttagcompound.getShort("id");
/*  56 */       Enchantment enchantment = Enchantment.getEnchantmentByID(j);
/*     */       
/*  58 */       if (enchantment != null)
/*     */       {
/*  60 */         tooltip.add(enchantment.getTranslatedName(nbttagcompound.getShort("lvl")));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addEnchantment(ItemStack p_92115_0_, EnchantmentData stack) {
/*  70 */     NBTTagList nbttaglist = getEnchantments(p_92115_0_);
/*  71 */     boolean flag = true;
/*     */     
/*  73 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/*  75 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*     */       
/*  77 */       if (Enchantment.getEnchantmentByID(nbttagcompound.getShort("id")) == stack.enchantmentobj) {
/*     */         
/*  79 */         if (nbttagcompound.getShort("lvl") < stack.enchantmentLevel)
/*     */         {
/*  81 */           nbttagcompound.setShort("lvl", (short)stack.enchantmentLevel);
/*     */         }
/*     */         
/*  84 */         flag = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  89 */     if (flag) {
/*     */       
/*  91 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  92 */       nbttagcompound1.setShort("id", (short)Enchantment.getEnchantmentID(stack.enchantmentobj));
/*  93 */       nbttagcompound1.setShort("lvl", (short)stack.enchantmentLevel);
/*  94 */       nbttaglist.appendTag((NBTBase)nbttagcompound1);
/*     */     } 
/*     */     
/*  97 */     if (!p_92115_0_.hasTagCompound())
/*     */     {
/*  99 */       p_92115_0_.setTagCompound(new NBTTagCompound());
/*     */     }
/*     */     
/* 102 */     p_92115_0_.getTagCompound().setTag("StoredEnchantments", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack getEnchantedItemStack(EnchantmentData p_92111_0_) {
/* 110 */     ItemStack itemstack = new ItemStack(Items.ENCHANTED_BOOK);
/* 111 */     addEnchantment(itemstack, p_92111_0_);
/* 112 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 120 */     if (itemIn == CreativeTabs.SEARCH) {
/*     */       
/* 122 */       for (Enchantment enchantment : Enchantment.REGISTRY)
/*     */       {
/* 124 */         if (enchantment.type != null)
/*     */         {
/* 126 */           for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++)
/*     */           {
/* 128 */             tab.add(getEnchantedItemStack(new EnchantmentData(enchantment, i)));
/*     */           }
/*     */         }
/*     */       }
/*     */     
/* 133 */     } else if ((itemIn.getRelevantEnchantmentTypes()).length != 0) {
/*     */       
/* 135 */       for (Enchantment enchantment1 : Enchantment.REGISTRY) {
/*     */         
/* 137 */         if (itemIn.hasRelevantEnchantmentType(enchantment1.type))
/*     */         {
/* 139 */           tab.add(getEnchantedItemStack(new EnchantmentData(enchantment1, enchantment1.getMaxLevel())));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemEnchantedBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */