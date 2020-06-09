/*     */ package net.minecraft.item;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.BlockRailBase;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemMinecart extends Item {
/*  21 */   private static final IBehaviorDispenseItem MINECART_DISPENSER_BEHAVIOR = (IBehaviorDispenseItem)new BehaviorDefaultDispenseItem()
/*     */     {
/*  23 */       private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
/*     */       public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/*     */         double d3;
/*  26 */         EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING);
/*  27 */         World world = source.getWorld();
/*  28 */         double d0 = source.getX() + enumfacing.getFrontOffsetX() * 1.125D;
/*  29 */         double d1 = Math.floor(source.getY()) + enumfacing.getFrontOffsetY();
/*  30 */         double d2 = source.getZ() + enumfacing.getFrontOffsetZ() * 1.125D;
/*  31 */         BlockPos blockpos = source.getBlockPos().offset(enumfacing);
/*  32 */         IBlockState iblockstate = world.getBlockState(blockpos);
/*  33 */         BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (iblockstate.getBlock() instanceof BlockRailBase) ? (BlockRailBase.EnumRailDirection)iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */ 
/*     */         
/*  36 */         if (BlockRailBase.isRailBlock(iblockstate)) {
/*     */           
/*  38 */           if (blockrailbase$enumraildirection.isAscending())
/*     */           {
/*  40 */             d3 = 0.6D;
/*     */           }
/*     */           else
/*     */           {
/*  44 */             d3 = 0.1D;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/*  49 */           if (iblockstate.getMaterial() != Material.AIR || !BlockRailBase.isRailBlock(world.getBlockState(blockpos.down())))
/*     */           {
/*  51 */             return this.behaviourDefaultDispenseItem.dispense(source, stack);
/*     */           }
/*     */           
/*  54 */           IBlockState iblockstate1 = world.getBlockState(blockpos.down());
/*  55 */           BlockRailBase.EnumRailDirection blockrailbase$enumraildirection1 = (iblockstate1.getBlock() instanceof BlockRailBase) ? (BlockRailBase.EnumRailDirection)iblockstate1.getValue(((BlockRailBase)iblockstate1.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */           
/*  57 */           if (enumfacing != EnumFacing.DOWN && blockrailbase$enumraildirection1.isAscending()) {
/*     */             
/*  59 */             d3 = -0.4D;
/*     */           }
/*     */           else {
/*     */             
/*  63 */             d3 = -0.9D;
/*     */           } 
/*     */         } 
/*     */         
/*  67 */         EntityMinecart entityminecart = EntityMinecart.create(world, d0, d1 + d3, d2, ((ItemMinecart)stack.getItem()).minecartType);
/*     */         
/*  69 */         if (stack.hasDisplayName())
/*     */         {
/*  71 */           entityminecart.setCustomNameTag(stack.getDisplayName());
/*     */         }
/*     */         
/*  74 */         world.spawnEntityInWorld((Entity)entityminecart);
/*  75 */         stack.func_190918_g(1);
/*  76 */         return stack;
/*     */       }
/*     */       
/*     */       protected void playDispenseSound(IBlockSource source) {
/*  80 */         source.getWorld().playEvent(1000, source.getBlockPos(), 0);
/*     */       }
/*     */     };
/*     */   
/*     */   private final EntityMinecart.Type minecartType;
/*     */   
/*     */   public ItemMinecart(EntityMinecart.Type typeIn) {
/*  87 */     this.maxStackSize = 1;
/*  88 */     this.minecartType = typeIn;
/*  89 */     setCreativeTab(CreativeTabs.TRANSPORTATION);
/*  90 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, MINECART_DISPENSER_BEHAVIOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  98 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*     */     
/* 100 */     if (!BlockRailBase.isRailBlock(iblockstate))
/*     */     {
/* 102 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/* 106 */     ItemStack itemstack = stack.getHeldItem(pos);
/*     */     
/* 108 */     if (!playerIn.isRemote) {
/*     */       
/* 110 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (iblockstate.getBlock() instanceof BlockRailBase) ? (BlockRailBase.EnumRailDirection)iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/* 111 */       double d0 = 0.0D;
/*     */       
/* 113 */       if (blockrailbase$enumraildirection.isAscending())
/*     */       {
/* 115 */         d0 = 0.5D;
/*     */       }
/*     */       
/* 118 */       EntityMinecart entityminecart = EntityMinecart.create(playerIn, worldIn.getX() + 0.5D, worldIn.getY() + 0.0625D + d0, worldIn.getZ() + 0.5D, this.minecartType);
/*     */       
/* 120 */       if (itemstack.hasDisplayName())
/*     */       {
/* 122 */         entityminecart.setCustomNameTag(itemstack.getDisplayName());
/*     */       }
/*     */       
/* 125 */       playerIn.spawnEntityInWorld((Entity)entityminecart);
/*     */     } 
/*     */     
/* 128 */     itemstack.func_190918_g(1);
/* 129 */     return EnumActionResult.SUCCESS;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */