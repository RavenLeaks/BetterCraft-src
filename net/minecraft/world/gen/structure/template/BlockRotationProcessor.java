/*    */ package net.minecraft.world.gen.structure.template;
/*    */ 
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockRotationProcessor
/*    */   implements ITemplateProcessor
/*    */ {
/*    */   private final float chance;
/*    */   private final Random random;
/*    */   
/*    */   public BlockRotationProcessor(BlockPos pos, PlacementSettings settings) {
/* 15 */     this.chance = settings.getIntegrity();
/* 16 */     this.random = settings.getRandom(pos);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Template.BlockInfo processBlock(World worldIn, BlockPos pos, Template.BlockInfo blockInfoIn) {
/* 22 */     return (this.chance < 1.0F && this.random.nextFloat() > this.chance) ? null : blockInfoIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\template\BlockRotationProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */