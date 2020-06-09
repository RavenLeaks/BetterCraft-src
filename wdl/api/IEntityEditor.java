package wdl.api;

import net.minecraft.entity.Entity;

public interface IEntityEditor extends IWDLMod {
  boolean shouldEdit(Entity paramEntity);
  
  void editEntity(Entity paramEntity);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\IEntityEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */