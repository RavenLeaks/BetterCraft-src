/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ 
/*    */ public class BlockBone
/*    */   extends BlockRotatedPillar
/*    */ {
/*    */   public BlockBone() {
/* 11 */     super(Material.ROCK, MapColor.SAND);
/* 12 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/* 13 */     setHardness(2.0F);
/* 14 */     setSoundType(SoundType.STONE);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockBone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */