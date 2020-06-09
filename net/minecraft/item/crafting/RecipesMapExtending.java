/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.minecraft.world.storage.MapDecoration;
/*     */ 
/*     */ public class RecipesMapExtending
/*     */   extends ShapedRecipes {
/*     */   public RecipesMapExtending() {
/*  16 */     super("", 3, 3, NonNullList.func_193580_a(Ingredient.field_193370_a, (Object[])new Ingredient[] { Ingredient.func_193368_a(new Item[] { Items.PAPER }), Ingredient.func_193368_a(new Item[] { Items.PAPER }), Ingredient.func_193368_a(new Item[] { Items.PAPER }), Ingredient.func_193368_a(new Item[] { Items.PAPER }), Ingredient.func_193367_a((Item)Items.FILLED_MAP), Ingredient.func_193368_a(new Item[] { Items.PAPER }), Ingredient.func_193368_a(new Item[] { Items.PAPER }), Ingredient.func_193368_a(new Item[] { Items.PAPER }), Ingredient.func_193368_a(new Item[] { Items.PAPER }) }), new ItemStack((Item)Items.MAP));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  24 */     if (!super.matches(inv, worldIn))
/*     */     {
/*  26 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  30 */     ItemStack itemstack = ItemStack.field_190927_a;
/*     */     
/*  32 */     for (int i = 0; i < inv.getSizeInventory() && itemstack.func_190926_b(); i++) {
/*     */       
/*  34 */       ItemStack itemstack1 = inv.getStackInSlot(i);
/*     */       
/*  36 */       if (itemstack1.getItem() == Items.FILLED_MAP)
/*     */       {
/*  38 */         itemstack = itemstack1;
/*     */       }
/*     */     } 
/*     */     
/*  42 */     if (itemstack.func_190926_b())
/*     */     {
/*  44 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  48 */     MapData mapdata = Items.FILLED_MAP.getMapData(itemstack, worldIn);
/*     */     
/*  50 */     if (mapdata == null)
/*     */     {
/*  52 */       return false;
/*     */     }
/*  54 */     if (func_190934_a(mapdata))
/*     */     {
/*  56 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  60 */     return (mapdata.scale < 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean func_190934_a(MapData p_190934_1_) {
/*  68 */     if (p_190934_1_.mapDecorations != null)
/*     */     {
/*  70 */       for (MapDecoration mapdecoration : p_190934_1_.mapDecorations.values()) {
/*     */         
/*  72 */         if (mapdecoration.func_191179_b() == MapDecoration.Type.MANSION || mapdecoration.func_191179_b() == MapDecoration.Type.MONUMENT)
/*     */         {
/*  74 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  87 */     ItemStack itemstack = ItemStack.field_190927_a;
/*     */     
/*  89 */     for (int i = 0; i < inv.getSizeInventory() && itemstack.func_190926_b(); i++) {
/*     */       
/*  91 */       ItemStack itemstack1 = inv.getStackInSlot(i);
/*     */       
/*  93 */       if (itemstack1.getItem() == Items.FILLED_MAP)
/*     */       {
/*  95 */         itemstack = itemstack1;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     itemstack = itemstack.copy();
/* 100 */     itemstack.func_190920_e(1);
/*     */     
/* 102 */     if (itemstack.getTagCompound() == null)
/*     */     {
/* 104 */       itemstack.setTagCompound(new NBTTagCompound());
/*     */     }
/*     */     
/* 107 */     itemstack.getTagCompound().setInteger("map_scale_direction", 1);
/* 108 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192399_d() {
/* 113 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\RecipesMapExtending.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */