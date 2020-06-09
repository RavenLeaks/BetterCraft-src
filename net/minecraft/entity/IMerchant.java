package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

public interface IMerchant {
  void setCustomer(EntityPlayer paramEntityPlayer);
  
  @Nullable
  EntityPlayer getCustomer();
  
  @Nullable
  MerchantRecipeList getRecipes(EntityPlayer paramEntityPlayer);
  
  void setRecipes(MerchantRecipeList paramMerchantRecipeList);
  
  void useRecipe(MerchantRecipe paramMerchantRecipe);
  
  void verifySellingItem(ItemStack paramItemStack);
  
  ITextComponent getDisplayName();
  
  World func_190670_t_();
  
  BlockPos func_190671_u_();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\IMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */