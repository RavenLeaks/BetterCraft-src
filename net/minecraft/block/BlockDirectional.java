/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.PropertyDirection;
/*    */ 
/*    */ public abstract class BlockDirectional
/*    */   extends Block {
/*  8 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*    */ 
/*    */   
/*    */   protected BlockDirectional(Material materialIn) {
/* 12 */     super(materialIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockDirectional.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */