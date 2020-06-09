/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBoat
/*     */   extends Item
/*     */ {
/*     */   private final EntityBoat.Type type;
/*     */   
/*     */   public ItemBoat(EntityBoat.Type typeIn) {
/*  26 */     this.type = typeIn;
/*  27 */     this.maxStackSize = 1;
/*  28 */     setCreativeTab(CreativeTabs.TRANSPORTATION);
/*  29 */     setUnlocalizedName("boat." + typeIn.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/*  34 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*  35 */     float f = 1.0F;
/*  36 */     float f1 = worldIn.prevRotationPitch + (worldIn.rotationPitch - worldIn.prevRotationPitch) * 1.0F;
/*  37 */     float f2 = worldIn.prevRotationYaw + (worldIn.rotationYaw - worldIn.prevRotationYaw) * 1.0F;
/*  38 */     double d0 = worldIn.prevPosX + (worldIn.posX - worldIn.prevPosX) * 1.0D;
/*  39 */     double d1 = worldIn.prevPosY + (worldIn.posY - worldIn.prevPosY) * 1.0D + worldIn.getEyeHeight();
/*  40 */     double d2 = worldIn.prevPosZ + (worldIn.posZ - worldIn.prevPosZ) * 1.0D;
/*  41 */     Vec3d vec3d = new Vec3d(d0, d1, d2);
/*  42 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/*  43 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/*  44 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/*  45 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/*  46 */     float f7 = f4 * f5;
/*  47 */     float f8 = f3 * f5;
/*  48 */     double d3 = 5.0D;
/*  49 */     Vec3d vec3d1 = vec3d.addVector(f7 * 5.0D, f6 * 5.0D, f8 * 5.0D);
/*  50 */     RayTraceResult raytraceresult = itemStackIn.rayTraceBlocks(vec3d, vec3d1, true);
/*     */     
/*  52 */     if (raytraceresult == null)
/*     */     {
/*  54 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */ 
/*     */     
/*  58 */     Vec3d vec3d2 = worldIn.getLook(1.0F);
/*  59 */     boolean flag = false;
/*  60 */     List<Entity> list = itemStackIn.getEntitiesWithinAABBExcludingEntity((Entity)worldIn, worldIn.getEntityBoundingBox().addCoord(vec3d2.xCoord * 5.0D, vec3d2.yCoord * 5.0D, vec3d2.zCoord * 5.0D).expandXyz(1.0D));
/*     */     
/*  62 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/*  64 */       Entity entity = list.get(i);
/*     */       
/*  66 */       if (entity.canBeCollidedWith()) {
/*     */         
/*  68 */         AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expandXyz(entity.getCollisionBorderSize());
/*     */         
/*  70 */         if (axisalignedbb.isVecInside(vec3d))
/*     */         {
/*  72 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     if (flag)
/*     */     {
/*  79 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*  81 */     if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
/*     */     {
/*  83 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */ 
/*     */     
/*  87 */     Block block = itemStackIn.getBlockState(raytraceresult.getBlockPos()).getBlock();
/*  88 */     boolean flag1 = !(block != Blocks.WATER && block != Blocks.FLOWING_WATER);
/*  89 */     EntityBoat entityboat = new EntityBoat(itemStackIn, raytraceresult.hitVec.xCoord, flag1 ? (raytraceresult.hitVec.yCoord - 0.12D) : raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
/*  90 */     entityboat.setBoatType(this.type);
/*  91 */     entityboat.rotationYaw = worldIn.rotationYaw;
/*     */     
/*  93 */     if (!itemStackIn.getCollisionBoxes((Entity)entityboat, entityboat.getEntityBoundingBox().expandXyz(-0.1D)).isEmpty())
/*     */     {
/*  95 */       return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */     }
/*     */ 
/*     */     
/*  99 */     if (!itemStackIn.isRemote)
/*     */     {
/* 101 */       itemStackIn.spawnEntityInWorld((Entity)entityboat);
/*     */     }
/*     */     
/* 104 */     if (!worldIn.capabilities.isCreativeMode)
/*     */     {
/* 106 */       itemstack.func_190918_g(1);
/*     */     }
/*     */     
/* 109 */     worldIn.addStat(StatList.getObjectUseStats(this));
/* 110 */     return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */