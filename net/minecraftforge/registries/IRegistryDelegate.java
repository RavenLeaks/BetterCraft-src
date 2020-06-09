package net.minecraftforge.registries;

import net.minecraft.util.ResourceLocation;

public interface IRegistryDelegate<T> {
  T get();
  
  ResourceLocation name();
  
  Class<T> type();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraftforge\registries\IRegistryDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */