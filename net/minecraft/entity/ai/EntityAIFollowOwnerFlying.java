/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.passive.EntityTameable;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class EntityAIFollowOwnerFlying
/*    */   extends EntityAIFollowOwner
/*    */ {
/*    */   public EntityAIFollowOwnerFlying(EntityTameable p_i47416_1_, double p_i47416_2_, float p_i47416_4_, float p_i47416_5_) {
/* 12 */     super(p_i47416_1_, p_i47416_2_, p_i47416_4_, p_i47416_5_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_192381_a(int p_192381_1_, int p_192381_2_, int p_192381_3_, int p_192381_4_, int p_192381_5_) {
/* 17 */     IBlockState iblockstate = this.theWorld.getBlockState(new BlockPos(p_192381_1_ + p_192381_4_, p_192381_3_ - 1, p_192381_2_ + p_192381_5_));
/* 18 */     return ((iblockstate.isFullyOpaque() || iblockstate.getMaterial() == Material.LEAVES) && this.theWorld.isAirBlock(new BlockPos(p_192381_1_ + p_192381_4_, p_192381_3_, p_192381_2_ + p_192381_5_)) && this.theWorld.isAirBlock(new BlockPos(p_192381_1_ + p_192381_4_, p_192381_3_ + 1, p_192381_2_ + p_192381_5_)));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIFollowOwnerFlying.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */