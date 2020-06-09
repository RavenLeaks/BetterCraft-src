/*     */ package net.minecraft.client.renderer.color;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemFireworkCharge;
/*     */ import net.minecraft.item.ItemMap;
/*     */ import net.minecraft.item.ItemMonsterPlacer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagIntArray;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ import net.minecraft.util.ObjectIntIdentityMap;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemColors
/*     */ {
/*  26 */   private final ObjectIntIdentityMap<IItemColor> mapItemColors = new ObjectIntIdentityMap(32);
/*     */ 
/*     */   
/*     */   public static ItemColors init(final BlockColors p_186729_0_) {
/*  30 */     ItemColors itemcolors = new ItemColors();
/*  31 */     itemcolors.registerItemColorHandler(new IItemColor()
/*     */         {
/*     */           public int getColorFromItemstack(ItemStack stack, int tintIndex)
/*     */           {
/*  35 */             return (tintIndex > 0) ? -1 : ((ItemArmor)stack.getItem()).getColor(stack);
/*     */           }
/*  37 */         },  new Item[] { (Item)Items.LEATHER_HELMET, (Item)Items.LEATHER_CHESTPLATE, (Item)Items.LEATHER_LEGGINGS, (Item)Items.LEATHER_BOOTS });
/*  38 */     itemcolors.registerItemColorHandler(new IItemColor()
/*     */         {
/*     */           public int getColorFromItemstack(ItemStack stack, int tintIndex)
/*     */           {
/*  42 */             BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = BlockDoublePlant.EnumPlantType.byMetadata(stack.getMetadata());
/*  43 */             return (blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.GRASS && blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.FERN) ? -1 : ColorizerGrass.getGrassColor(0.5D, 1.0D);
/*     */           }
/*  45 */         }new Block[] { (Block)Blocks.DOUBLE_PLANT });
/*  46 */     itemcolors.registerItemColorHandler(new IItemColor()
/*     */         {
/*     */           public int getColorFromItemstack(ItemStack stack, int tintIndex)
/*     */           {
/*  50 */             if (tintIndex != 1)
/*     */             {
/*  52 */               return -1;
/*     */             }
/*     */ 
/*     */             
/*  56 */             NBTBase nbtbase = ItemFireworkCharge.getExplosionTag(stack, "Colors");
/*     */             
/*  58 */             if (!(nbtbase instanceof NBTTagIntArray))
/*     */             {
/*  60 */               return 9079434;
/*     */             }
/*     */ 
/*     */             
/*  64 */             int[] aint = ((NBTTagIntArray)nbtbase).getIntArray();
/*     */             
/*  66 */             if (aint.length == 1)
/*     */             {
/*  68 */               return aint[0];
/*     */             }
/*     */ 
/*     */             
/*  72 */             int i = 0;
/*  73 */             int j = 0;
/*  74 */             int k = 0; byte b;
/*     */             int m, arrayOfInt1[];
/*  76 */             for (m = (arrayOfInt1 = aint).length, b = 0; b < m; ) { int l = arrayOfInt1[b];
/*     */               
/*  78 */               i += (l & 0xFF0000) >> 16;
/*  79 */               j += (l & 0xFF00) >> 8;
/*  80 */               k += (l & 0xFF) >> 0;
/*     */               b++; }
/*     */             
/*  83 */             i /= aint.length;
/*  84 */             j /= aint.length;
/*  85 */             k /= aint.length;
/*  86 */             return i << 16 | j << 8 | k;
/*     */           }
/*     */         }new Item[] {
/*     */ 
/*     */           
/*  91 */           Items.FIREWORK_CHARGE });
/*  92 */     itemcolors.registerItemColorHandler(new IItemColor()
/*     */         {
/*     */           public int getColorFromItemstack(ItemStack stack, int tintIndex)
/*     */           {
/*  96 */             return (tintIndex > 0) ? -1 : PotionUtils.func_190932_c(stack);
/*     */           }
/*  98 */         },  new Item[] { (Item)Items.POTIONITEM, (Item)Items.SPLASH_POTION, (Item)Items.LINGERING_POTION });
/*  99 */     itemcolors.registerItemColorHandler(new IItemColor()
/*     */         {
/*     */           public int getColorFromItemstack(ItemStack stack, int tintIndex)
/*     */           {
/* 103 */             EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.ENTITY_EGGS.get(ItemMonsterPlacer.func_190908_h(stack));
/*     */             
/* 105 */             if (entitylist$entityegginfo == null)
/*     */             {
/* 107 */               return -1;
/*     */             }
/*     */ 
/*     */             
/* 111 */             return (tintIndex == 0) ? entitylist$entityegginfo.primaryColor : entitylist$entityegginfo.secondaryColor;
/*     */           }
/*     */         }, 
/* 114 */         new Item[] { Items.SPAWN_EGG });
/* 115 */     itemcolors.registerItemColorHandler(new IItemColor()
/*     */         {
/*     */           public int getColorFromItemstack(ItemStack stack, int tintIndex)
/*     */           {
/* 119 */             IBlockState iblockstate = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
/* 120 */             return p_186729_0_.colorMultiplier(iblockstate, null, null, tintIndex);
/*     */           }
/* 122 */         }new Block[] { (Block)Blocks.GRASS, (Block)Blocks.TALLGRASS, Blocks.VINE, (Block)Blocks.LEAVES, (Block)Blocks.LEAVES2, Blocks.WATERLILY });
/* 123 */     itemcolors.registerItemColorHandler(new IItemColor()
/*     */         {
/*     */           public int getColorFromItemstack(ItemStack stack, int tintIndex)
/*     */           {
/* 127 */             return (tintIndex == 0) ? PotionUtils.func_190932_c(stack) : -1;
/*     */           }
/* 129 */         },  new Item[] { Items.TIPPED_ARROW });
/* 130 */     itemcolors.registerItemColorHandler(new IItemColor()
/*     */         {
/*     */           public int getColorFromItemstack(ItemStack stack, int tintIndex)
/*     */           {
/* 134 */             return (tintIndex == 0) ? -1 : ItemMap.func_190907_h(stack);
/*     */           }
/* 136 */         },  new Item[] { (Item)Items.FILLED_MAP });
/* 137 */     return itemcolors;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromItemstack(ItemStack stack, int tintIndex) {
/* 142 */     IItemColor iitemcolor = (IItemColor)this.mapItemColors.getByValue(Item.REGISTRY.getIDForObject(stack.getItem()));
/* 143 */     return (iitemcolor == null) ? -1 : iitemcolor.getColorFromItemstack(stack, tintIndex);
/*     */   }
/*     */   public void registerItemColorHandler(IItemColor itemColor, Block... blocksIn) { byte b;
/*     */     int i;
/*     */     Block[] arrayOfBlock;
/* 148 */     for (i = (arrayOfBlock = blocksIn).length, b = 0; b < i; ) { Block block = arrayOfBlock[b];
/*     */       
/* 150 */       this.mapItemColors.put(itemColor, Item.getIdFromItem(Item.getItemFromBlock(block)));
/*     */       b++; }
/*     */      } public void registerItemColorHandler(IItemColor itemColor, Item... itemsIn) {
/*     */     byte b;
/*     */     int i;
/*     */     Item[] arrayOfItem;
/* 156 */     for (i = (arrayOfItem = itemsIn).length, b = 0; b < i; ) { Item item = arrayOfItem[b];
/*     */       
/* 158 */       this.mapItemColors.put(itemColor, Item.getIdFromItem(item));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\color\ItemColors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */