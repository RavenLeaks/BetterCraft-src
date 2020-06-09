package net.minecraft.entity;

import java.util.UUID;
import javax.annotation.Nullable;

public interface IEntityOwnable {
  @Nullable
  UUID getOwnerId();
  
  @Nullable
  Entity getOwner();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\IEntityOwnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */