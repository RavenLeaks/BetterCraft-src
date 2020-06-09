/*    */ package net.minecraft.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityHanging;
/*    */ import net.minecraft.entity.item.EntityItemFrame;
/*    */ import net.minecraft.entity.item.EntityPainting;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemHangingEntity
/*    */   extends Item {
/*    */   private final Class<? extends EntityHanging> hangingEntityClass;
/*    */   
/*    */   public ItemHangingEntity(Class<? extends EntityHanging> entityClass) {
/* 21 */     this.hangingEntityClass = entityClass;
/* 22 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/* 30 */     ItemStack itemstack = stack.getHeldItem(pos);
/* 31 */     BlockPos blockpos = worldIn.offset(hand);
/*    */     
/* 33 */     if (hand != EnumFacing.DOWN && hand != EnumFacing.UP && stack.canPlayerEdit(blockpos, hand, itemstack)) {
/*    */       
/* 35 */       EntityHanging entityhanging = createEntity(playerIn, blockpos, hand);
/*    */       
/* 37 */       if (entityhanging != null && entityhanging.onValidSurface()) {
/*    */         
/* 39 */         if (!playerIn.isRemote) {
/*    */           
/* 41 */           entityhanging.playPlaceSound();
/* 42 */           playerIn.spawnEntityInWorld((Entity)entityhanging);
/*    */         } 
/*    */         
/* 45 */         itemstack.func_190918_g(1);
/*    */       } 
/*    */       
/* 48 */       return EnumActionResult.SUCCESS;
/*    */     } 
/*    */ 
/*    */     
/* 52 */     return EnumActionResult.FAIL;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private EntityHanging createEntity(World worldIn, BlockPos pos, EnumFacing clickedSide) {
/* 59 */     if (this.hangingEntityClass == EntityPainting.class)
/*    */     {
/* 61 */       return (EntityHanging)new EntityPainting(worldIn, pos, clickedSide);
/*    */     }
/*    */ 
/*    */     
/* 65 */     return (this.hangingEntityClass == EntityItemFrame.class) ? (EntityHanging)new EntityItemFrame(worldIn, pos, clickedSide) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemHangingEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */