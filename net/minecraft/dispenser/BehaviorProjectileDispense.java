/*    */ package net.minecraft.dispenser;
/*    */ 
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.IProjectile;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BehaviorProjectileDispense
/*    */   extends BehaviorDefaultDispenseItem
/*    */ {
/*    */   public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 17 */     World world = source.getWorld();
/* 18 */     IPosition iposition = BlockDispenser.getDispensePosition(source);
/* 19 */     EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING);
/* 20 */     IProjectile iprojectile = getProjectileEntity(world, iposition, stack);
/* 21 */     iprojectile.setThrowableHeading(enumfacing.getFrontOffsetX(), (enumfacing.getFrontOffsetY() + 0.1F), enumfacing.getFrontOffsetZ(), getProjectileVelocity(), getProjectileInaccuracy());
/* 22 */     world.spawnEntityInWorld((Entity)iprojectile);
/* 23 */     stack.func_190918_g(1);
/* 24 */     return stack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void playDispenseSound(IBlockSource source) {
/* 32 */     source.getWorld().playEvent(1002, source.getBlockPos(), 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract IProjectile getProjectileEntity(World paramWorld, IPosition paramIPosition, ItemStack paramItemStack);
/*    */ 
/*    */ 
/*    */   
/*    */   protected float getProjectileInaccuracy() {
/* 42 */     return 6.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getProjectileVelocity() {
/* 47 */     return 1.1F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\dispenser\BehaviorProjectileDispense.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */