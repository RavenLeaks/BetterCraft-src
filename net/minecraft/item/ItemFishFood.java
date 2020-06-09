/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ItemFishFood
/*     */   extends ItemFood
/*     */ {
/*     */   private final boolean cooked;
/*     */   
/*     */   public ItemFishFood(boolean cooked) {
/*  19 */     super(0, 0.0F, false);
/*  20 */     this.cooked = cooked;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHealAmount(ItemStack stack) {
/*  25 */     FishType itemfishfood$fishtype = FishType.byItemStack(stack);
/*  26 */     return (this.cooked && itemfishfood$fishtype.canCook()) ? itemfishfood$fishtype.getCookedHealAmount() : itemfishfood$fishtype.getUncookedHealAmount();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSaturationModifier(ItemStack stack) {
/*  31 */     FishType itemfishfood$fishtype = FishType.byItemStack(stack);
/*  32 */     return (this.cooked && itemfishfood$fishtype.canCook()) ? itemfishfood$fishtype.getCookedSaturationModifier() : itemfishfood$fishtype.getUncookedSaturationModifier();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
/*  37 */     FishType itemfishfood$fishtype = FishType.byItemStack(stack);
/*     */     
/*  39 */     if (itemfishfood$fishtype == FishType.PUFFERFISH) {
/*     */       
/*  41 */       player.addPotionEffect(new PotionEffect(MobEffects.POISON, 1200, 3));
/*  42 */       player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 2));
/*  43 */       player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300, 1));
/*     */     } 
/*     */     
/*  46 */     super.onFoodEaten(stack, worldIn, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/*  54 */     if (func_194125_a(itemIn)) {
/*     */       byte b; int i; FishType[] arrayOfFishType;
/*  56 */       for (i = (arrayOfFishType = FishType.values()).length, b = 0; b < i; ) { FishType itemfishfood$fishtype = arrayOfFishType[b];
/*     */         
/*  58 */         if (!this.cooked || itemfishfood$fishtype.canCook())
/*     */         {
/*  60 */           tab.add(new ItemStack(this, 1, itemfishfood$fishtype.getMetadata()));
/*     */         }
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/*  72 */     FishType itemfishfood$fishtype = FishType.byItemStack(stack);
/*  73 */     return String.valueOf(getUnlocalizedName()) + "." + itemfishfood$fishtype.getUnlocalizedName() + "." + ((this.cooked && itemfishfood$fishtype.canCook()) ? "cooked" : "raw");
/*     */   }
/*     */   
/*     */   public enum FishType
/*     */   {
/*  78 */     COD(0, "cod", 2, 0.1F, 5, 0.6F),
/*  79 */     SALMON(1, "salmon", 2, 0.1F, 6, 0.8F),
/*  80 */     CLOWNFISH(2, "clownfish", 1, 0.1F),
/*  81 */     PUFFERFISH(3, "pufferfish", 1, 0.1F);
/*     */     
/*  83 */     private static final Map<Integer, FishType> META_LOOKUP = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int meta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String unlocalizedName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int uncookedHealAmount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final float uncookedSaturationModifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int cookedHealAmount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final float cookedSaturationModifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean cookable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       FishType[] arrayOfFishType;
/* 161 */       for (i = (arrayOfFishType = values()).length, b = 0; b < i; ) { FishType itemfishfood$fishtype = arrayOfFishType[b];
/*     */         
/* 163 */         META_LOOKUP.put(Integer.valueOf(itemfishfood$fishtype.getMetadata()), itemfishfood$fishtype);
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     FishType(int meta, String unlocalizedName, int uncookedHeal, float uncookedSaturation, int cookedHeal, float cookedSaturation) {
/*     */       this.meta = meta;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */       this.uncookedHealAmount = uncookedHeal;
/*     */       this.uncookedSaturationModifier = uncookedSaturation;
/*     */       this.cookedHealAmount = cookedHeal;
/*     */       this.cookedSaturationModifier = cookedSaturation;
/*     */       this.cookable = true;
/*     */     }
/*     */     
/*     */     FishType(int meta, String unlocalizedName, int uncookedHeal, float uncookedSaturation) {
/*     */       this.meta = meta;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */       this.uncookedHealAmount = uncookedHeal;
/*     */       this.uncookedSaturationModifier = uncookedSaturation;
/*     */       this.cookedHealAmount = 0;
/*     */       this.cookedSaturationModifier = 0.0F;
/*     */       this.cookable = false;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */     
/*     */     public int getUncookedHealAmount() {
/*     */       return this.uncookedHealAmount;
/*     */     }
/*     */     
/*     */     public float getUncookedSaturationModifier() {
/*     */       return this.uncookedSaturationModifier;
/*     */     }
/*     */     
/*     */     public int getCookedHealAmount() {
/*     */       return this.cookedHealAmount;
/*     */     }
/*     */     
/*     */     public float getCookedSaturationModifier() {
/*     */       return this.cookedSaturationModifier;
/*     */     }
/*     */     
/*     */     public boolean canCook() {
/*     */       return this.cookable;
/*     */     }
/*     */     
/*     */     public static FishType byMetadata(int meta) {
/*     */       FishType itemfishfood$fishtype = META_LOOKUP.get(Integer.valueOf(meta));
/*     */       return (itemfishfood$fishtype == null) ? COD : itemfishfood$fishtype;
/*     */     }
/*     */     
/*     */     public static FishType byItemStack(ItemStack stack) {
/*     */       return (stack.getItem() instanceof ItemFishFood) ? byMetadata(stack.getMetadata()) : COD;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemFishFood.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */