/*    */ package net.minecraft.entity.passive;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public enum HorseArmorType
/*    */ {
/* 10 */   NONE(0),
/* 11 */   IRON(5, "iron", "meo"),
/* 12 */   GOLD(7, "gold", "goo"),
/* 13 */   DIAMOND(11, "diamond", "dio");
/*    */   
/*    */   private final String textureName;
/*    */   
/*    */   private final String hash;
/*    */   private final int protection;
/*    */   
/*    */   HorseArmorType(int armorStrengthIn) {
/* 21 */     this.protection = armorStrengthIn;
/* 22 */     this.textureName = null;
/* 23 */     this.hash = "";
/*    */   }
/*    */ 
/*    */   
/*    */   HorseArmorType(int armorStrengthIn, String p_i46800_4_, String p_i46800_5_) {
/* 28 */     this.protection = armorStrengthIn;
/* 29 */     this.textureName = "textures/entity/horse/armor/horse_armor_" + p_i46800_4_ + ".png";
/* 30 */     this.hash = p_i46800_5_;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOrdinal() {
/* 35 */     return ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHash() {
/* 40 */     return this.hash;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getProtection() {
/* 45 */     return this.protection;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getTextureName() {
/* 51 */     return this.textureName;
/*    */   }
/*    */ 
/*    */   
/*    */   public static HorseArmorType getByOrdinal(int ordinal) {
/* 56 */     return values()[ordinal];
/*    */   }
/*    */ 
/*    */   
/*    */   public static HorseArmorType getByItemStack(ItemStack stack) {
/* 61 */     return stack.func_190926_b() ? NONE : getByItem(stack.getItem());
/*    */   }
/*    */ 
/*    */   
/*    */   public static HorseArmorType getByItem(Item itemIn) {
/* 66 */     if (itemIn == Items.IRON_HORSE_ARMOR)
/*    */     {
/* 68 */       return IRON;
/*    */     }
/* 70 */     if (itemIn == Items.GOLDEN_HORSE_ARMOR)
/*    */     {
/* 72 */       return GOLD;
/*    */     }
/*    */ 
/*    */     
/* 76 */     return (itemIn == Items.DIAMOND_HORSE_ARMOR) ? DIAMOND : NONE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isHorseArmor(Item itemIn) {
/* 82 */     return (getByItem(itemIn) != NONE);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\HorseArmorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */