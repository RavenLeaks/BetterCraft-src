/*    */ package net.minecraft.block;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.PropertyDirection;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public abstract class BlockHorizontal extends Block {
/* 10 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*    */ 
/*    */   
/*    */   protected BlockHorizontal(Material materialIn) {
/* 14 */     super(materialIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockHorizontal(Material materialIn, MapColor colorIn) {
/* 19 */     super(materialIn, colorIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockHorizontal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */