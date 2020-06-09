package net.minecraft.util.datafix;

import net.minecraft.nbt.NBTTagCompound;

public interface IFixableData {
  int getFixVersion();
  
  NBTTagCompound fixTagCompound(NBTTagCompound paramNBTTagCompound);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\IFixableData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */