package net.minecraft.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IContainerListener {
  void updateCraftingInventory(Container paramContainer, NonNullList<ItemStack> paramNonNullList);
  
  void sendSlotContents(Container paramContainer, int paramInt, ItemStack paramItemStack);
  
  void sendProgressBarUpdate(Container paramContainer, int paramInt1, int paramInt2);
  
  void sendAllWindowProperties(Container paramContainer, IInventory paramIInventory);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\IContainerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */