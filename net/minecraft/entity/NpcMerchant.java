/*    */ package net.minecraft.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.InventoryMerchant;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.village.MerchantRecipe;
/*    */ import net.minecraft.village.MerchantRecipeList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NpcMerchant
/*    */   implements IMerchant
/*    */ {
/*    */   private final InventoryMerchant theMerchantInventory;
/*    */   private final EntityPlayer customer;
/*    */   private MerchantRecipeList recipeList;
/*    */   private final ITextComponent name;
/*    */   
/*    */   public NpcMerchant(EntityPlayer customerIn, ITextComponent nameIn) {
/* 28 */     this.customer = customerIn;
/* 29 */     this.name = nameIn;
/* 30 */     this.theMerchantInventory = new InventoryMerchant(customerIn, this);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public EntityPlayer getCustomer() {
/* 36 */     return this.customer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCustomer(@Nullable EntityPlayer player) {}
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MerchantRecipeList getRecipes(EntityPlayer player) {
/* 46 */     return this.recipeList;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRecipes(@Nullable MerchantRecipeList recipeList) {
/* 51 */     this.recipeList = recipeList;
/*    */   }
/*    */ 
/*    */   
/*    */   public void useRecipe(MerchantRecipe recipe) {
/* 56 */     recipe.incrementToolUses();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void verifySellingItem(ItemStack stack) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ITextComponent getDisplayName() {
/* 72 */     return (this.name != null) ? this.name : (ITextComponent)new TextComponentTranslation("entity.Villager.name", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public World func_190670_t_() {
/* 77 */     return this.customer.world;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos func_190671_u_() {
/* 82 */     return new BlockPos((Entity)this.customer);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\NpcMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */