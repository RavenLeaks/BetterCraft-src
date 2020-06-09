/*     */ package net.minecraft.creativetab;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.PotionTypes;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ import net.minecraft.util.NonNullList;
/*     */ 
/*     */ public abstract class CreativeTabs {
/*  16 */   public static final CreativeTabs[] CREATIVE_TAB_ARRAY = new CreativeTabs[12];
/*  17 */   public static final CreativeTabs BUILDING_BLOCKS = new CreativeTabs(0, "buildingBlocks")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  21 */         return new ItemStack(Item.getItemFromBlock(Blocks.BRICK_BLOCK));
/*     */       }
/*     */     };
/*  24 */   public static final CreativeTabs DECORATIONS = new CreativeTabs(1, "decorations")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  28 */         return new ItemStack(Item.getItemFromBlock((Block)Blocks.DOUBLE_PLANT), 1, BlockDoublePlant.EnumPlantType.PAEONIA.getMeta());
/*     */       }
/*     */     };
/*  31 */   public static final CreativeTabs REDSTONE = new CreativeTabs(2, "redstone")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  35 */         return new ItemStack(Items.REDSTONE);
/*     */       }
/*     */     };
/*  38 */   public static final CreativeTabs TRANSPORTATION = new CreativeTabs(3, "transportation")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  42 */         return new ItemStack(Item.getItemFromBlock(Blocks.GOLDEN_RAIL));
/*     */       }
/*     */     };
/*  45 */   public static final CreativeTabs MISC = new CreativeTabs(6, "misc")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  49 */         return new ItemStack(Items.LAVA_BUCKET);
/*     */       }
/*     */     };
/*  52 */   public static final CreativeTabs SEARCH = (new CreativeTabs(5, "search")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  56 */         return new ItemStack(Items.COMPASS);
/*     */       }
/*  58 */     }).setBackgroundImageName("item_search.png");
/*  59 */   public static final CreativeTabs FOOD = new CreativeTabs(7, "food")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  63 */         return new ItemStack(Items.APPLE);
/*     */       }
/*     */     };
/*  66 */   public static final CreativeTabs TOOLS = (new CreativeTabs(8, "tools")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  70 */         return new ItemStack(Items.IRON_AXE);
/*     */       }
/*  72 */     }).setRelevantEnchantmentTypes(new EnumEnchantmentType[] { EnumEnchantmentType.ALL, EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE });
/*  73 */   public static final CreativeTabs COMBAT = (new CreativeTabs(9, "combat")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  77 */         return new ItemStack(Items.GOLDEN_SWORD);
/*     */       }
/*  79 */     }).setRelevantEnchantmentTypes(new EnumEnchantmentType[] { EnumEnchantmentType.ALL, EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_CHEST, EnumEnchantmentType.BOW, EnumEnchantmentType.WEAPON, EnumEnchantmentType.WEARABLE, EnumEnchantmentType.BREAKABLE });
/*  80 */   public static final CreativeTabs BREWING = new CreativeTabs(10, "brewing")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  84 */         return PotionUtils.addPotionToItemStack(new ItemStack((Item)Items.POTIONITEM), PotionTypes.WATER);
/*     */       }
/*     */     };
/*  87 */   public static final CreativeTabs MATERIALS = MISC;
/*  88 */   public static final CreativeTabs field_192395_m = new CreativeTabs(4, "hotbar")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/*  92 */         return new ItemStack(Blocks.BOOKSHELF);
/*     */       }
/*     */       
/*     */       public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
/*  96 */         throw new RuntimeException("Implement exception client-side.");
/*     */       }
/*     */       
/*     */       public boolean func_192394_m() {
/* 100 */         return true;
/*     */       }
/*     */     };
/* 103 */   public static final CreativeTabs INVENTORY = (new CreativeTabs(11, "inventory")
/*     */     {
/*     */       public ItemStack getTabIconItem()
/*     */       {
/* 107 */         return new ItemStack(Item.getItemFromBlock((Block)Blocks.CHEST));
/*     */       }
/* 109 */     }).setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
/*     */ 
/*     */   
/*     */   private final int tabIndex;
/*     */   private final String tabLabel;
/* 114 */   private String theTexture = "items.png";
/*     */   
/*     */   private boolean hasScrollbar = true;
/*     */   
/*     */   private boolean drawTitle = true;
/* 119 */   private EnumEnchantmentType[] enchantmentTypes = new EnumEnchantmentType[0];
/*     */   
/*     */   private ItemStack iconItemStack;
/*     */   
/*     */   public CreativeTabs(int index, String label) {
/* 124 */     this.tabIndex = index;
/* 125 */     this.tabLabel = label;
/* 126 */     this.iconItemStack = ItemStack.field_190927_a;
/* 127 */     CREATIVE_TAB_ARRAY[index] = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTabIndex() {
/* 132 */     return this.tabIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTabLabel() {
/* 137 */     return this.tabLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslatedTabLabel() {
/* 145 */     return "itemGroup." + getTabLabel();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getIconItemStack() {
/* 150 */     if (this.iconItemStack.func_190926_b())
/*     */     {
/* 152 */       this.iconItemStack = getTabIconItem();
/*     */     }
/*     */     
/* 155 */     return this.iconItemStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract ItemStack getTabIconItem();
/*     */   
/*     */   public String getBackgroundImageName() {
/* 162 */     return this.theTexture;
/*     */   }
/*     */ 
/*     */   
/*     */   public CreativeTabs setBackgroundImageName(String texture) {
/* 167 */     this.theTexture = texture;
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean drawInForegroundOfTab() {
/* 173 */     return this.drawTitle;
/*     */   }
/*     */ 
/*     */   
/*     */   public CreativeTabs setNoTitle() {
/* 178 */     this.drawTitle = false;
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldHidePlayerInventory() {
/* 184 */     return this.hasScrollbar;
/*     */   }
/*     */ 
/*     */   
/*     */   public CreativeTabs setNoScrollbar() {
/* 189 */     this.hasScrollbar = false;
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTabColumn() {
/* 198 */     return this.tabIndex % 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTabInFirstRow() {
/* 206 */     return (this.tabIndex < 6);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192394_m() {
/* 211 */     return (getTabColumn() == 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
/* 219 */     return this.enchantmentTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs setRelevantEnchantmentTypes(EnumEnchantmentType... types) {
/* 227 */     this.enchantmentTypes = types;
/* 228 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRelevantEnchantmentType(@Nullable EnumEnchantmentType enchantmentType) {
/* 233 */     if (enchantmentType != null) {
/*     */       byte b; int i; EnumEnchantmentType[] arrayOfEnumEnchantmentType;
/* 235 */       for (i = (arrayOfEnumEnchantmentType = this.enchantmentTypes).length, b = 0; b < i; ) { EnumEnchantmentType enumenchantmenttype = arrayOfEnumEnchantmentType[b];
/*     */         
/* 237 */         if (enumenchantmenttype == enchantmentType)
/*     */         {
/* 239 */           return true;
/*     */         }
/*     */         b++; }
/*     */     
/*     */     } 
/* 244 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
/* 252 */     for (Item item : Item.REGISTRY)
/*     */     {
/* 254 */       item.getSubItems(this, p_78018_1_);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\creativetab\CreativeTabs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */