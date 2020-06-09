package net.minecraft.util.datafix;

import net.minecraft.nbt.NBTTagCompound;

public interface IDataFixer {
  NBTTagCompound process(IFixType paramIFixType, NBTTagCompound paramNBTTagCompound, int paramInt);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\IDataFixer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */