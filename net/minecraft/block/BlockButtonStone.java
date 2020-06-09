/*    */ package net.minecraft.block;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockButtonStone
/*    */   extends BlockButton
/*    */ {
/*    */   protected BlockButtonStone() {
/* 14 */     super(false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void playClickSound(@Nullable EntityPlayer player, World worldIn, BlockPos pos) {
/* 19 */     worldIn.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void playReleaseSound(World worldIn, BlockPos pos) {
/* 24 */     worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockButtonStone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */