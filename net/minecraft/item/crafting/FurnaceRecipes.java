/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockStoneBrick;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFishFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public class FurnaceRecipes
/*     */ {
/*  17 */   private static final FurnaceRecipes SMELTING_BASE = new FurnaceRecipes();
/*  18 */   private final Map<ItemStack, ItemStack> smeltingList = Maps.newHashMap();
/*  19 */   private final Map<ItemStack, Float> experienceList = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FurnaceRecipes instance() {
/*  26 */     return SMELTING_BASE;
/*     */   }
/*     */ 
/*     */   
/*     */   private FurnaceRecipes() {
/*  31 */     addSmeltingRecipeForBlock(Blocks.IRON_ORE, new ItemStack(Items.IRON_INGOT), 0.7F);
/*  32 */     addSmeltingRecipeForBlock(Blocks.GOLD_ORE, new ItemStack(Items.GOLD_INGOT), 1.0F);
/*  33 */     addSmeltingRecipeForBlock(Blocks.DIAMOND_ORE, new ItemStack(Items.DIAMOND), 1.0F);
/*  34 */     addSmeltingRecipeForBlock((Block)Blocks.SAND, new ItemStack(Blocks.GLASS), 0.1F);
/*  35 */     addSmelting(Items.PORKCHOP, new ItemStack(Items.COOKED_PORKCHOP), 0.35F);
/*  36 */     addSmelting(Items.BEEF, new ItemStack(Items.COOKED_BEEF), 0.35F);
/*  37 */     addSmelting(Items.CHICKEN, new ItemStack(Items.COOKED_CHICKEN), 0.35F);
/*  38 */     addSmelting(Items.RABBIT, new ItemStack(Items.COOKED_RABBIT), 0.35F);
/*  39 */     addSmelting(Items.MUTTON, new ItemStack(Items.COOKED_MUTTON), 0.35F);
/*  40 */     addSmeltingRecipeForBlock(Blocks.COBBLESTONE, new ItemStack(Blocks.STONE), 0.1F);
/*  41 */     addSmeltingRecipe(new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.DEFAULT_META), new ItemStack(Blocks.STONEBRICK, 1, BlockStoneBrick.CRACKED_META), 0.1F);
/*  42 */     addSmelting(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.3F);
/*  43 */     addSmeltingRecipeForBlock(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.35F);
/*  44 */     addSmeltingRecipeForBlock((Block)Blocks.CACTUS, new ItemStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage()), 0.2F);
/*  45 */     addSmeltingRecipeForBlock(Blocks.LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
/*  46 */     addSmeltingRecipeForBlock(Blocks.LOG2, new ItemStack(Items.COAL, 1, 1), 0.15F);
/*  47 */     addSmeltingRecipeForBlock(Blocks.EMERALD_ORE, new ItemStack(Items.EMERALD), 1.0F);
/*  48 */     addSmelting(Items.POTATO, new ItemStack(Items.BAKED_POTATO), 0.35F);
/*  49 */     addSmeltingRecipeForBlock(Blocks.NETHERRACK, new ItemStack(Items.NETHERBRICK), 0.1F);
/*  50 */     addSmeltingRecipe(new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE, 1, 0), 0.15F);
/*  51 */     addSmelting(Items.CHORUS_FRUIT, new ItemStack(Items.CHORUS_FRUIT_POPPED), 0.1F); byte b; int i;
/*     */     ItemFishFood.FishType[] arrayOfFishType;
/*  53 */     for (i = (arrayOfFishType = ItemFishFood.FishType.values()).length, b = 0; b < i; ) { ItemFishFood.FishType itemfishfood$fishtype = arrayOfFishType[b];
/*     */       
/*  55 */       if (itemfishfood$fishtype.canCook())
/*     */       {
/*  57 */         addSmeltingRecipe(new ItemStack(Items.FISH, 1, itemfishfood$fishtype.getMetadata()), new ItemStack(Items.COOKED_FISH, 1, itemfishfood$fishtype.getMetadata()), 0.35F);
/*     */       }
/*     */       b++; }
/*     */     
/*  61 */     addSmeltingRecipeForBlock(Blocks.COAL_ORE, new ItemStack(Items.COAL), 0.1F);
/*  62 */     addSmeltingRecipeForBlock(Blocks.REDSTONE_ORE, new ItemStack(Items.REDSTONE), 0.7F);
/*  63 */     addSmeltingRecipeForBlock(Blocks.LAPIS_ORE, new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), 0.2F);
/*  64 */     addSmeltingRecipeForBlock(Blocks.QUARTZ_ORE, new ItemStack(Items.QUARTZ), 0.2F);
/*  65 */     addSmelting((Item)Items.CHAINMAIL_HELMET, new ItemStack(Items.field_191525_da), 0.1F);
/*  66 */     addSmelting((Item)Items.CHAINMAIL_CHESTPLATE, new ItemStack(Items.field_191525_da), 0.1F);
/*  67 */     addSmelting((Item)Items.CHAINMAIL_LEGGINGS, new ItemStack(Items.field_191525_da), 0.1F);
/*  68 */     addSmelting((Item)Items.CHAINMAIL_BOOTS, new ItemStack(Items.field_191525_da), 0.1F);
/*  69 */     addSmelting(Items.IRON_PICKAXE, new ItemStack(Items.field_191525_da), 0.1F);
/*  70 */     addSmelting(Items.IRON_SHOVEL, new ItemStack(Items.field_191525_da), 0.1F);
/*  71 */     addSmelting(Items.IRON_AXE, new ItemStack(Items.field_191525_da), 0.1F);
/*  72 */     addSmelting(Items.IRON_HOE, new ItemStack(Items.field_191525_da), 0.1F);
/*  73 */     addSmelting(Items.IRON_SWORD, new ItemStack(Items.field_191525_da), 0.1F);
/*  74 */     addSmelting((Item)Items.IRON_HELMET, new ItemStack(Items.field_191525_da), 0.1F);
/*  75 */     addSmelting((Item)Items.IRON_CHESTPLATE, new ItemStack(Items.field_191525_da), 0.1F);
/*  76 */     addSmelting((Item)Items.IRON_LEGGINGS, new ItemStack(Items.field_191525_da), 0.1F);
/*  77 */     addSmelting((Item)Items.IRON_BOOTS, new ItemStack(Items.field_191525_da), 0.1F);
/*  78 */     addSmelting(Items.IRON_HORSE_ARMOR, new ItemStack(Items.field_191525_da), 0.1F);
/*  79 */     addSmelting(Items.GOLDEN_PICKAXE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
/*  80 */     addSmelting(Items.GOLDEN_SHOVEL, new ItemStack(Items.GOLD_NUGGET), 0.1F);
/*  81 */     addSmelting(Items.GOLDEN_AXE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
/*  82 */     addSmelting(Items.GOLDEN_HOE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
/*  83 */     addSmelting(Items.GOLDEN_SWORD, new ItemStack(Items.GOLD_NUGGET), 0.1F);
/*  84 */     addSmelting((Item)Items.GOLDEN_HELMET, new ItemStack(Items.GOLD_NUGGET), 0.1F);
/*  85 */     addSmelting((Item)Items.GOLDEN_CHESTPLATE, new ItemStack(Items.GOLD_NUGGET), 0.1F);
/*  86 */     addSmelting((Item)Items.GOLDEN_LEGGINGS, new ItemStack(Items.GOLD_NUGGET), 0.1F);
/*  87 */     addSmelting((Item)Items.GOLDEN_BOOTS, new ItemStack(Items.GOLD_NUGGET), 0.1F);
/*  88 */     addSmelting(Items.GOLDEN_HORSE_ARMOR, new ItemStack(Items.GOLD_NUGGET), 0.1F);
/*  89 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.WHITE.getMetadata()), new ItemStack(Blocks.field_192427_dB), 0.1F);
/*  90 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.ORANGE.getMetadata()), new ItemStack(Blocks.field_192428_dC), 0.1F);
/*  91 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.MAGENTA.getMetadata()), new ItemStack(Blocks.field_192429_dD), 0.1F);
/*  92 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.LIGHT_BLUE.getMetadata()), new ItemStack(Blocks.field_192430_dE), 0.1F);
/*  93 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.YELLOW.getMetadata()), new ItemStack(Blocks.field_192431_dF), 0.1F);
/*  94 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.LIME.getMetadata()), new ItemStack(Blocks.field_192432_dG), 0.1F);
/*  95 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.PINK.getMetadata()), new ItemStack(Blocks.field_192433_dH), 0.1F);
/*  96 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.GRAY.getMetadata()), new ItemStack(Blocks.field_192434_dI), 0.1F);
/*  97 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.SILVER.getMetadata()), new ItemStack(Blocks.field_192435_dJ), 0.1F);
/*  98 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.CYAN.getMetadata()), new ItemStack(Blocks.field_192436_dK), 0.1F);
/*  99 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.PURPLE.getMetadata()), new ItemStack(Blocks.field_192437_dL), 0.1F);
/* 100 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLUE.getMetadata()), new ItemStack(Blocks.field_192438_dM), 0.1F);
/* 101 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BROWN.getMetadata()), new ItemStack(Blocks.field_192439_dN), 0.1F);
/* 102 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.GREEN.getMetadata()), new ItemStack(Blocks.field_192440_dO), 0.1F);
/* 103 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.RED.getMetadata()), new ItemStack(Blocks.field_192441_dP), 0.1F);
/* 104 */     addSmeltingRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLACK.getMetadata()), new ItemStack(Blocks.field_192442_dQ), 0.1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience) {
/* 112 */     addSmelting(Item.getItemFromBlock(input), stack, experience);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSmelting(Item input, ItemStack stack, float experience) {
/* 120 */     addSmeltingRecipe(new ItemStack(input, 1, 32767), stack, experience);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience) {
/* 128 */     this.smeltingList.put(input, stack);
/* 129 */     this.experienceList.put(stack, Float.valueOf(experience));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSmeltingResult(ItemStack stack) {
/* 137 */     for (Map.Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet()) {
/*     */       
/* 139 */       if (compareItemStacks(stack, entry.getKey()))
/*     */       {
/* 141 */         return entry.getValue();
/*     */       }
/*     */     } 
/*     */     
/* 145 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
/* 153 */     return (stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ItemStack, ItemStack> getSmeltingList() {
/* 158 */     return this.smeltingList;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSmeltingExperience(ItemStack stack) {
/* 163 */     for (Map.Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
/*     */       
/* 165 */       if (compareItemStacks(stack, entry.getKey()))
/*     */       {
/* 167 */         return ((Float)entry.getValue()).floatValue();
/*     */       }
/*     */     } 
/*     */     
/* 171 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\FurnaceRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */