/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumEnchantmentType
/*    */ {
/* 17 */   ALL { public boolean canEnchantItem(Item itemIn) { byte b;
/*    */       int i;
/*    */       EnumEnchantmentType[] arrayOfEnumEnchantmentType;
/* 20 */       for (i = (arrayOfEnumEnchantmentType = values()).length, b = 0; b < i; ) { EnumEnchantmentType enumenchantmenttype = arrayOfEnumEnchantmentType[b];
/*    */         
/* 22 */         if (enumenchantmenttype != EnumEnchantmentType.ALL && enumenchantmenttype.canEnchantItem(itemIn))
/*    */         {
/* 24 */           return true;
/*    */         }
/*    */         b++; }
/*    */       
/* 28 */       return false; }
/*    */      }
/*    */   ,
/* 31 */   ARMOR
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 34 */       return itemIn instanceof ItemArmor;
/*    */     }
/*    */   },
/* 37 */   ARMOR_FEET
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 40 */       return (itemIn instanceof ItemArmor && ((ItemArmor)itemIn).armorType == EntityEquipmentSlot.FEET);
/*    */     }
/*    */   },
/* 43 */   ARMOR_LEGS
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 46 */       return (itemIn instanceof ItemArmor && ((ItemArmor)itemIn).armorType == EntityEquipmentSlot.LEGS);
/*    */     }
/*    */   },
/* 49 */   ARMOR_CHEST
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 52 */       return (itemIn instanceof ItemArmor && ((ItemArmor)itemIn).armorType == EntityEquipmentSlot.CHEST);
/*    */     }
/*    */   },
/* 55 */   ARMOR_HEAD
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 58 */       return (itemIn instanceof ItemArmor && ((ItemArmor)itemIn).armorType == EntityEquipmentSlot.HEAD);
/*    */     }
/*    */   },
/* 61 */   WEAPON
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 64 */       return itemIn instanceof net.minecraft.item.ItemSword;
/*    */     }
/*    */   },
/* 67 */   DIGGER
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 70 */       return itemIn instanceof net.minecraft.item.ItemTool;
/*    */     }
/*    */   },
/* 73 */   FISHING_ROD
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 76 */       return itemIn instanceof net.minecraft.item.ItemFishingRod;
/*    */     }
/*    */   },
/* 79 */   BREAKABLE
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 82 */       return itemIn.isDamageable();
/*    */     }
/*    */   },
/* 85 */   BOW
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 88 */       return itemIn instanceof net.minecraft.item.ItemBow;
/*    */     }
/*    */   },
/* 91 */   WEARABLE
/*    */   {
/*    */     public boolean canEnchantItem(Item itemIn) {
/* 94 */       boolean flag = (itemIn instanceof ItemBlock && ((ItemBlock)itemIn).getBlock() instanceof net.minecraft.block.BlockPumpkin);
/* 95 */       return !(!(itemIn instanceof ItemArmor) && !(itemIn instanceof net.minecraft.item.ItemElytra) && !(itemIn instanceof net.minecraft.item.ItemSkull) && !flag);
/*    */     }
/*    */   };
/*    */   
/*    */   public abstract boolean canEnchantItem(Item paramItem);
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\enchantment\EnumEnchantmentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */