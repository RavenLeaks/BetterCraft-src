package net.minecraft.entity;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public interface IEntityMultiPart {
  World getWorld();
  
  boolean attackEntityFromPart(MultiPartEntityPart paramMultiPartEntityPart, DamageSource paramDamageSource, float paramFloat);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\IEntityMultiPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */