/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class BlockSlime
/*    */   extends BlockBreakable
/*    */ {
/*    */   public BlockSlime() {
/* 16 */     super(Material.CLAY, false, MapColor.GRASS);
/* 17 */     setCreativeTab(CreativeTabs.DECORATIONS);
/* 18 */     this.slipperiness = 0.8F;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockRenderLayer getBlockLayer() {
/* 23 */     return BlockRenderLayer.TRANSLUCENT;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/* 31 */     if (entityIn.isSneaking()) {
/*    */       
/* 33 */       super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
/*    */     }
/*    */     else {
/*    */       
/* 37 */       entityIn.fall(fallDistance, 0.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onLanded(World worldIn, Entity entityIn) {
/* 47 */     if (entityIn.isSneaking()) {
/*    */       
/* 49 */       super.onLanded(worldIn, entityIn);
/*    */     }
/* 51 */     else if (entityIn.motionY < 0.0D) {
/*    */       
/* 53 */       entityIn.motionY = -entityIn.motionY;
/*    */       
/* 55 */       if (!(entityIn instanceof net.minecraft.entity.EntityLivingBase))
/*    */       {
/* 57 */         entityIn.motionY *= 0.8D;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
/* 67 */     if (Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking()) {
/*    */       
/* 69 */       double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
/* 70 */       entityIn.motionX *= d0;
/* 71 */       entityIn.motionZ *= d0;
/*    */     } 
/*    */     
/* 74 */     super.onEntityWalk(worldIn, pos, entityIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockSlime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */