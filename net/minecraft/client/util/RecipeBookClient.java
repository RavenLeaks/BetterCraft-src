/*    */ package net.minecraft.client.util;
/*    */ 
/*    */ import com.google.common.collect.HashBasedTable;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.gui.recipebook.RecipeList;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.CraftingManager;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.stats.RecipeBook;
/*    */ 
/*    */ 
/*    */ public class RecipeBookClient
/*    */   extends RecipeBook
/*    */ {
/* 20 */   public static final Map<CreativeTabs, List<RecipeList>> field_194086_e = Maps.newHashMap();
/* 21 */   public static final List<RecipeList> field_194087_f = Lists.newArrayList();
/*    */ 
/*    */   
/*    */   private static RecipeList func_194082_a(CreativeTabs p_194082_0_) {
/* 25 */     RecipeList recipelist = new RecipeList();
/* 26 */     field_194087_f.add(recipelist);
/* 27 */     ((List<RecipeList>)field_194086_e.computeIfAbsent(p_194082_0_, p_194085_0_ -> new ArrayList()))
/*    */ 
/*    */       
/* 30 */       .add(recipelist);
/* 31 */     ((List<RecipeList>)field_194086_e.computeIfAbsent(CreativeTabs.SEARCH, p_194083_0_ -> new ArrayList()))
/*    */ 
/*    */       
/* 34 */       .add(recipelist);
/* 35 */     return recipelist;
/*    */   }
/*    */ 
/*    */   
/*    */   private static CreativeTabs func_194084_a(ItemStack p_194084_0_) {
/* 40 */     CreativeTabs creativetabs = p_194084_0_.getItem().getCreativeTab();
/*    */     
/* 42 */     if (creativetabs != CreativeTabs.BUILDING_BLOCKS && creativetabs != CreativeTabs.TOOLS && creativetabs != CreativeTabs.REDSTONE)
/*    */     {
/* 44 */       return (creativetabs == CreativeTabs.COMBAT) ? CreativeTabs.TOOLS : CreativeTabs.MISC;
/*    */     }
/*    */ 
/*    */     
/* 48 */     return creativetabs;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 54 */     HashBasedTable hashBasedTable = HashBasedTable.create();
/*    */     
/* 56 */     for (IRecipe irecipe : CraftingManager.field_193380_a) {
/*    */       
/* 58 */       if (!irecipe.func_192399_d()) {
/*    */         RecipeList recipelist1;
/* 60 */         CreativeTabs creativetabs = func_194084_a(irecipe.getRecipeOutput());
/* 61 */         String s = irecipe.func_193358_e();
/*    */ 
/*    */         
/* 64 */         if (s.isEmpty()) {
/*    */           
/* 66 */           recipelist1 = func_194082_a(creativetabs);
/*    */         }
/*    */         else {
/*    */           
/* 70 */           recipelist1 = (RecipeList)hashBasedTable.get(creativetabs, s);
/*    */           
/* 72 */           if (recipelist1 == null) {
/*    */             
/* 74 */             recipelist1 = func_194082_a(creativetabs);
/* 75 */             hashBasedTable.put(creativetabs, s, recipelist1);
/*    */           } 
/*    */         } 
/*    */         
/* 79 */         recipelist1.func_192709_a(irecipe);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\clien\\util\RecipeBookClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */