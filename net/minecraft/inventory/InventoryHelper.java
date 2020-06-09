/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class InventoryHelper
/*    */ {
/* 12 */   private static final Random RANDOM = new Random();
/*    */ 
/*    */   
/*    */   public static void dropInventoryItems(World worldIn, BlockPos pos, IInventory inventory) {
/* 16 */     dropInventoryItems(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void dropInventoryItems(World worldIn, Entity entityAt, IInventory inventory) {
/* 21 */     dropInventoryItems(worldIn, entityAt.posX, entityAt.posY, entityAt.posZ, inventory);
/*    */   }
/*    */ 
/*    */   
/*    */   private static void dropInventoryItems(World worldIn, double x, double y, double z, IInventory inventory) {
/* 26 */     for (int i = 0; i < inventory.getSizeInventory(); i++) {
/*    */       
/* 28 */       ItemStack itemstack = inventory.getStackInSlot(i);
/*    */       
/* 30 */       if (!itemstack.func_190926_b())
/*    */       {
/* 32 */         spawnItemStack(worldIn, x, y, z, itemstack);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack) {
/* 39 */     float f = RANDOM.nextFloat() * 0.8F + 0.1F;
/* 40 */     float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
/* 41 */     float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;
/*    */     
/* 43 */     while (!stack.func_190926_b()) {
/*    */       
/* 45 */       EntityItem entityitem = new EntityItem(worldIn, x + f, y + f1, z + f2, stack.splitStack(RANDOM.nextInt(21) + 10));
/* 46 */       float f3 = 0.05F;
/* 47 */       entityitem.motionX = RANDOM.nextGaussian() * 0.05000000074505806D;
/* 48 */       entityitem.motionY = RANDOM.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
/* 49 */       entityitem.motionZ = RANDOM.nextGaussian() * 0.05000000074505806D;
/* 50 */       worldIn.spawnEntityInWorld((Entity)entityitem);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\InventoryHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */