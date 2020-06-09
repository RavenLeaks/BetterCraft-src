/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class ItemAxe
/*    */   extends ItemTool {
/* 12 */   private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet((Object[])new Block[] { Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.LOG2, (Block)Blocks.CHEST, Blocks.PUMPKIN, Blocks.LIT_PUMPKIN, Blocks.MELON_BLOCK, Blocks.LADDER, Blocks.WOODEN_BUTTON, Blocks.WOODEN_PRESSURE_PLATE });
/* 13 */   private static final float[] ATTACK_DAMAGES = new float[] { 6.0F, 8.0F, 8.0F, 8.0F, 6.0F };
/* 14 */   private static final float[] ATTACK_SPEEDS = new float[] { -3.2F, -3.2F, -3.1F, -3.0F, -3.0F };
/*    */ 
/*    */   
/*    */   protected ItemAxe(Item.ToolMaterial material) {
/* 18 */     super(material, EFFECTIVE_ON);
/* 19 */     this.damageVsEntity = ATTACK_DAMAGES[material.ordinal()];
/* 20 */     this.attackSpeed = ATTACK_SPEEDS[material.ordinal()];
/*    */   }
/*    */ 
/*    */   
/*    */   public float getStrVsBlock(ItemStack stack, IBlockState state) {
/* 25 */     Material material = state.getMaterial();
/* 26 */     return (material != Material.WOOD && material != Material.PLANTS && material != Material.VINE) ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemAxe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */