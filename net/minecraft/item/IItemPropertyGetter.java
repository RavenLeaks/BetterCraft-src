package net.minecraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public interface IItemPropertyGetter {
  float apply(ItemStack paramItemStack, World paramWorld, EntityLivingBase paramEntityLivingBase);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\IItemPropertyGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */