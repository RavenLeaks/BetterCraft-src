/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Rotations;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemArmorStand
/*     */   extends Item {
/*     */   public ItemArmorStand() {
/*  24 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  32 */     if (hand == EnumFacing.DOWN)
/*     */     {
/*  34 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/*  38 */     boolean flag = playerIn.getBlockState(worldIn).getBlock().isReplaceable((IBlockAccess)playerIn, worldIn);
/*  39 */     BlockPos blockpos = flag ? worldIn : worldIn.offset(hand);
/*  40 */     ItemStack itemstack = stack.getHeldItem(pos);
/*     */     
/*  42 */     if (!stack.canPlayerEdit(blockpos, hand, itemstack))
/*     */     {
/*  44 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/*  48 */     BlockPos blockpos1 = blockpos.up();
/*  49 */     boolean flag1 = (!playerIn.isAirBlock(blockpos) && !playerIn.getBlockState(blockpos).getBlock().isReplaceable((IBlockAccess)playerIn, blockpos));
/*  50 */     int i = flag1 | ((!playerIn.isAirBlock(blockpos1) && !playerIn.getBlockState(blockpos1).getBlock().isReplaceable((IBlockAccess)playerIn, blockpos1)) ? 1 : 0);
/*     */     
/*  52 */     if (i != 0)
/*     */     {
/*  54 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/*  58 */     double d0 = blockpos.getX();
/*  59 */     double d1 = blockpos.getY();
/*  60 */     double d2 = blockpos.getZ();
/*  61 */     List<Entity> list = playerIn.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));
/*     */     
/*  63 */     if (!list.isEmpty())
/*     */     {
/*  65 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/*  69 */     if (!playerIn.isRemote) {
/*     */       
/*  71 */       playerIn.setBlockToAir(blockpos);
/*  72 */       playerIn.setBlockToAir(blockpos1);
/*  73 */       EntityArmorStand entityarmorstand = new EntityArmorStand(playerIn, d0 + 0.5D, d1, d2 + 0.5D);
/*  74 */       float f = MathHelper.floor((MathHelper.wrapDegrees(stack.rotationYaw - 180.0F) + 22.5F) / 45.0F) * 45.0F;
/*  75 */       entityarmorstand.setLocationAndAngles(d0 + 0.5D, d1, d2 + 0.5D, f, 0.0F);
/*  76 */       applyRandomRotations(entityarmorstand, playerIn.rand);
/*  77 */       ItemMonsterPlacer.applyItemEntityDataToEntity(playerIn, stack, itemstack, (Entity)entityarmorstand);
/*  78 */       playerIn.spawnEntityInWorld((Entity)entityarmorstand);
/*  79 */       playerIn.playSound(null, entityarmorstand.posX, entityarmorstand.posY, entityarmorstand.posZ, SoundEvents.ENTITY_ARMORSTAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
/*     */     } 
/*     */     
/*  82 */     itemstack.func_190918_g(1);
/*  83 */     return EnumActionResult.SUCCESS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyRandomRotations(EntityArmorStand armorStand, Random rand) {
/*  92 */     Rotations rotations = armorStand.getHeadRotation();
/*  93 */     float f = rand.nextFloat() * 5.0F;
/*  94 */     float f1 = rand.nextFloat() * 20.0F - 10.0F;
/*  95 */     Rotations rotations1 = new Rotations(rotations.getX() + f, rotations.getY() + f1, rotations.getZ());
/*  96 */     armorStand.setHeadRotation(rotations1);
/*  97 */     rotations = armorStand.getBodyRotation();
/*  98 */     f = rand.nextFloat() * 10.0F - 5.0F;
/*  99 */     rotations1 = new Rotations(rotations.getX(), rotations.getY() + f, rotations.getZ());
/* 100 */     armorStand.setBodyRotation(rotations1);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */