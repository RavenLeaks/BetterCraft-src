/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntityPiston
/*     */   extends TileEntity implements ITickable {
/*     */   private IBlockState pistonState;
/*     */   private EnumFacing pistonFacing;
/*     */   private boolean extending;
/*     */   private boolean shouldHeadBeRendered;
/*     */   
/*  31 */   private static final ThreadLocal<EnumFacing> field_190613_i = new ThreadLocal<EnumFacing>()
/*     */     {
/*     */       protected EnumFacing initialValue()
/*     */       {
/*  35 */         return null;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private float progress;
/*     */   
/*     */   private float lastProgress;
/*     */ 
/*     */   
/*     */   public TileEntityPiston() {}
/*     */ 
/*     */   
/*     */   public TileEntityPiston(IBlockState pistonStateIn, EnumFacing pistonFacingIn, boolean extendingIn, boolean shouldHeadBeRenderedIn) {
/*  49 */     this.pistonState = pistonStateIn;
/*  50 */     this.pistonFacing = pistonFacingIn;
/*  51 */     this.extending = extendingIn;
/*  52 */     this.shouldHeadBeRendered = shouldHeadBeRenderedIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getPistonState() {
/*  57 */     return this.pistonState;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getUpdateTag() {
/*  62 */     return writeToNBT(new NBTTagCompound());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockMetadata() {
/*  67 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExtending() {
/*  75 */     return this.extending;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing getFacing() {
/*  80 */     return this.pistonFacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldPistonHeadBeRendered() {
/*  85 */     return this.shouldHeadBeRendered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getProgress(float ticks) {
/*  94 */     if (ticks > 1.0F)
/*     */     {
/*  96 */       ticks = 1.0F;
/*     */     }
/*     */     
/*  99 */     return this.lastProgress + (this.progress - this.lastProgress) * ticks;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getOffsetX(float ticks) {
/* 104 */     return this.pistonFacing.getFrontOffsetX() * getExtendedProgress(getProgress(ticks));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getOffsetY(float ticks) {
/* 109 */     return this.pistonFacing.getFrontOffsetY() * getExtendedProgress(getProgress(ticks));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getOffsetZ(float ticks) {
/* 114 */     return this.pistonFacing.getFrontOffsetZ() * getExtendedProgress(getProgress(ticks));
/*     */   }
/*     */ 
/*     */   
/*     */   private float getExtendedProgress(float p_184320_1_) {
/* 119 */     return this.extending ? (p_184320_1_ - 1.0F) : (1.0F - p_184320_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getAABB(IBlockAccess p_184321_1_, BlockPos p_184321_2_) {
/* 124 */     return getAABB(p_184321_1_, p_184321_2_, this.progress).union(getAABB(p_184321_1_, p_184321_2_, this.lastProgress));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getAABB(IBlockAccess p_184319_1_, BlockPos p_184319_2_, float p_184319_3_) {
/* 129 */     p_184319_3_ = getExtendedProgress(p_184319_3_);
/* 130 */     IBlockState iblockstate = func_190606_j();
/* 131 */     return iblockstate.getBoundingBox(p_184319_1_, p_184319_2_).offset((p_184319_3_ * this.pistonFacing.getFrontOffsetX()), (p_184319_3_ * this.pistonFacing.getFrontOffsetY()), (p_184319_3_ * this.pistonFacing.getFrontOffsetZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState func_190606_j() {
/* 136 */     return (!isExtending() && shouldPistonHeadBeRendered()) ? Blocks.PISTON_HEAD.getDefaultState().withProperty((IProperty)BlockPistonExtension.TYPE, (this.pistonState.getBlock() == Blocks.STICKY_PISTON) ? (Comparable)BlockPistonExtension.EnumPistonType.STICKY : (Comparable)BlockPistonExtension.EnumPistonType.DEFAULT).withProperty((IProperty)BlockPistonExtension.FACING, this.pistonState.getValue((IProperty)BlockPistonBase.FACING)) : this.pistonState;
/*     */   }
/*     */ 
/*     */   
/*     */   private void moveCollidedEntities(float p_184322_1_) {
/* 141 */     EnumFacing enumfacing = this.extending ? this.pistonFacing : this.pistonFacing.getOpposite();
/* 142 */     double d0 = (p_184322_1_ - this.progress);
/* 143 */     List<AxisAlignedBB> list = Lists.newArrayList();
/* 144 */     func_190606_j().addCollisionBoxToList(this.world, BlockPos.ORIGIN, new AxisAlignedBB(BlockPos.ORIGIN), list, null, true);
/*     */     
/* 146 */     if (!list.isEmpty()) {
/*     */       
/* 148 */       AxisAlignedBB axisalignedbb = func_190607_a(func_191515_a(list));
/* 149 */       List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(null, func_190610_a(axisalignedbb, enumfacing, d0).union(axisalignedbb));
/*     */       
/* 151 */       if (!list1.isEmpty()) {
/*     */         
/* 153 */         boolean flag = (this.pistonState.getBlock() == Blocks.SLIME_BLOCK);
/*     */         
/* 155 */         for (int i = 0; i < list1.size(); i++) {
/*     */           
/* 157 */           Entity entity = list1.get(i);
/*     */           
/* 159 */           if (entity.getPushReaction() != EnumPushReaction.IGNORE) {
/*     */             
/* 161 */             if (flag)
/*     */             {
/* 163 */               switch (enumfacing.getAxis()) {
/*     */                 
/*     */                 case null:
/* 166 */                   entity.motionX = enumfacing.getFrontOffsetX();
/*     */                   break;
/*     */                 
/*     */                 case Y:
/* 170 */                   entity.motionY = enumfacing.getFrontOffsetY();
/*     */                   break;
/*     */                 
/*     */                 case Z:
/* 174 */                   entity.motionZ = enumfacing.getFrontOffsetZ();
/*     */                   break;
/*     */               } 
/*     */             }
/* 178 */             double d1 = 0.0D;
/*     */             
/* 180 */             for (int j = 0; j < list.size(); j++) {
/*     */               
/* 182 */               AxisAlignedBB axisalignedbb1 = func_190610_a(func_190607_a(list.get(j)), enumfacing, d0);
/* 183 */               AxisAlignedBB axisalignedbb2 = entity.getEntityBoundingBox();
/*     */               
/* 185 */               if (axisalignedbb1.intersectsWith(axisalignedbb2)) {
/*     */                 
/* 187 */                 d1 = Math.max(d1, func_190612_a(axisalignedbb1, enumfacing, axisalignedbb2));
/*     */                 
/* 189 */                 if (d1 >= d0) {
/*     */                   break;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 196 */             if (d1 > 0.0D) {
/*     */               
/* 198 */               d1 = Math.min(d1, d0) + 0.01D;
/* 199 */               field_190613_i.set(enumfacing);
/* 200 */               entity.moveEntity(MoverType.PISTON, d1 * enumfacing.getFrontOffsetX(), d1 * enumfacing.getFrontOffsetY(), d1 * enumfacing.getFrontOffsetZ());
/* 201 */               field_190613_i.set(null);
/*     */               
/* 203 */               if (!this.extending && this.shouldHeadBeRendered)
/*     */               {
/* 205 */                 func_190605_a(entity, enumfacing, d0);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private AxisAlignedBB func_191515_a(List<AxisAlignedBB> p_191515_1_) {
/* 216 */     double d0 = 0.0D;
/* 217 */     double d1 = 0.0D;
/* 218 */     double d2 = 0.0D;
/* 219 */     double d3 = 1.0D;
/* 220 */     double d4 = 1.0D;
/* 221 */     double d5 = 1.0D;
/*     */     
/* 223 */     for (AxisAlignedBB axisalignedbb : p_191515_1_) {
/*     */       
/* 225 */       d0 = Math.min(axisalignedbb.minX, d0);
/* 226 */       d1 = Math.min(axisalignedbb.minY, d1);
/* 227 */       d2 = Math.min(axisalignedbb.minZ, d2);
/* 228 */       d3 = Math.max(axisalignedbb.maxX, d3);
/* 229 */       d4 = Math.max(axisalignedbb.maxY, d4);
/* 230 */       d5 = Math.max(axisalignedbb.maxZ, d5);
/*     */     } 
/*     */     
/* 233 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */   
/*     */   private double func_190612_a(AxisAlignedBB p_190612_1_, EnumFacing p_190612_2_, AxisAlignedBB p_190612_3_) {
/* 238 */     switch (p_190612_2_.getAxis()) {
/*     */       
/*     */       case null:
/* 241 */         return func_190611_b(p_190612_1_, p_190612_2_, p_190612_3_);
/*     */ 
/*     */       
/*     */       default:
/* 245 */         return func_190608_c(p_190612_1_, p_190612_2_, p_190612_3_);
/*     */       case Z:
/*     */         break;
/* 248 */     }  return func_190604_d(p_190612_1_, p_190612_2_, p_190612_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AxisAlignedBB func_190607_a(AxisAlignedBB p_190607_1_) {
/* 254 */     double d0 = getExtendedProgress(this.progress);
/* 255 */     return p_190607_1_.offset(this.pos.getX() + d0 * this.pistonFacing.getFrontOffsetX(), this.pos.getY() + d0 * this.pistonFacing.getFrontOffsetY(), this.pos.getZ() + d0 * this.pistonFacing.getFrontOffsetZ());
/*     */   }
/*     */ 
/*     */   
/*     */   private AxisAlignedBB func_190610_a(AxisAlignedBB p_190610_1_, EnumFacing p_190610_2_, double p_190610_3_) {
/* 260 */     double d0 = p_190610_3_ * p_190610_2_.getAxisDirection().getOffset();
/* 261 */     double d1 = Math.min(d0, 0.0D);
/* 262 */     double d2 = Math.max(d0, 0.0D);
/*     */     
/* 264 */     switch (p_190610_2_) {
/*     */       
/*     */       case WEST:
/* 267 */         return new AxisAlignedBB(p_190610_1_.minX + d1, p_190610_1_.minY, p_190610_1_.minZ, p_190610_1_.minX + d2, p_190610_1_.maxY, p_190610_1_.maxZ);
/*     */       
/*     */       case EAST:
/* 270 */         return new AxisAlignedBB(p_190610_1_.maxX + d1, p_190610_1_.minY, p_190610_1_.minZ, p_190610_1_.maxX + d2, p_190610_1_.maxY, p_190610_1_.maxZ);
/*     */       
/*     */       case null:
/* 273 */         return new AxisAlignedBB(p_190610_1_.minX, p_190610_1_.minY + d1, p_190610_1_.minZ, p_190610_1_.maxX, p_190610_1_.minY + d2, p_190610_1_.maxZ);
/*     */ 
/*     */       
/*     */       default:
/* 277 */         return new AxisAlignedBB(p_190610_1_.minX, p_190610_1_.maxY + d1, p_190610_1_.minZ, p_190610_1_.maxX, p_190610_1_.maxY + d2, p_190610_1_.maxZ);
/*     */       
/*     */       case NORTH:
/* 280 */         return new AxisAlignedBB(p_190610_1_.minX, p_190610_1_.minY, p_190610_1_.minZ + d1, p_190610_1_.maxX, p_190610_1_.maxY, p_190610_1_.minZ + d2);
/*     */       case SOUTH:
/*     */         break;
/* 283 */     }  return new AxisAlignedBB(p_190610_1_.minX, p_190610_1_.minY, p_190610_1_.maxZ + d1, p_190610_1_.maxX, p_190610_1_.maxY, p_190610_1_.maxZ + d2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_190605_a(Entity p_190605_1_, EnumFacing p_190605_2_, double p_190605_3_) {
/* 289 */     AxisAlignedBB axisalignedbb = p_190605_1_.getEntityBoundingBox();
/* 290 */     AxisAlignedBB axisalignedbb1 = Block.FULL_BLOCK_AABB.offset(this.pos);
/*     */     
/* 292 */     if (axisalignedbb.intersectsWith(axisalignedbb1)) {
/*     */       
/* 294 */       EnumFacing enumfacing = p_190605_2_.getOpposite();
/* 295 */       double d0 = func_190612_a(axisalignedbb1, enumfacing, axisalignedbb) + 0.01D;
/* 296 */       double d1 = func_190612_a(axisalignedbb1, enumfacing, axisalignedbb.func_191500_a(axisalignedbb1)) + 0.01D;
/*     */       
/* 298 */       if (Math.abs(d0 - d1) < 0.01D) {
/*     */         
/* 300 */         d0 = Math.min(d0, p_190605_3_) + 0.01D;
/* 301 */         field_190613_i.set(p_190605_2_);
/* 302 */         p_190605_1_.moveEntity(MoverType.PISTON, d0 * enumfacing.getFrontOffsetX(), d0 * enumfacing.getFrontOffsetY(), d0 * enumfacing.getFrontOffsetZ());
/* 303 */         field_190613_i.set(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static double func_190611_b(AxisAlignedBB p_190611_0_, EnumFacing p_190611_1_, AxisAlignedBB p_190611_2_) {
/* 310 */     return (p_190611_1_.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) ? (p_190611_0_.maxX - p_190611_2_.minX) : (p_190611_2_.maxX - p_190611_0_.minX);
/*     */   }
/*     */ 
/*     */   
/*     */   private static double func_190608_c(AxisAlignedBB p_190608_0_, EnumFacing p_190608_1_, AxisAlignedBB p_190608_2_) {
/* 315 */     return (p_190608_1_.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) ? (p_190608_0_.maxY - p_190608_2_.minY) : (p_190608_2_.maxY - p_190608_0_.minY);
/*     */   }
/*     */ 
/*     */   
/*     */   private static double func_190604_d(AxisAlignedBB p_190604_0_, EnumFacing p_190604_1_, AxisAlignedBB p_190604_2_) {
/* 320 */     return (p_190604_1_.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) ? (p_190604_0_.maxZ - p_190604_2_.minZ) : (p_190604_2_.maxZ - p_190604_0_.minZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPistonTileEntity() {
/* 328 */     if (this.lastProgress < 1.0F && this.world != null) {
/*     */       
/* 330 */       this.progress = 1.0F;
/* 331 */       this.lastProgress = this.progress;
/* 332 */       this.world.removeTileEntity(this.pos);
/* 333 */       invalidate();
/*     */       
/* 335 */       if (this.world.getBlockState(this.pos).getBlock() == Blocks.PISTON_EXTENSION) {
/*     */         
/* 337 */         this.world.setBlockState(this.pos, this.pistonState, 3);
/* 338 */         this.world.func_190524_a(this.pos, this.pistonState.getBlock(), this.pos);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 348 */     this.lastProgress = this.progress;
/*     */     
/* 350 */     if (this.lastProgress >= 1.0F) {
/*     */       
/* 352 */       this.world.removeTileEntity(this.pos);
/* 353 */       invalidate();
/*     */       
/* 355 */       if (this.world.getBlockState(this.pos).getBlock() == Blocks.PISTON_EXTENSION)
/*     */       {
/* 357 */         this.world.setBlockState(this.pos, this.pistonState, 3);
/* 358 */         this.world.func_190524_a(this.pos, this.pistonState.getBlock(), this.pos);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 363 */       float f = this.progress + 0.5F;
/* 364 */       moveCollidedEntities(f);
/* 365 */       this.progress = f;
/*     */       
/* 367 */       if (this.progress >= 1.0F)
/*     */       {
/* 369 */         this.progress = 1.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFixesPiston(DataFixer fixer) {}
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 380 */     super.readFromNBT(compound);
/* 381 */     this.pistonState = Block.getBlockById(compound.getInteger("blockId")).getStateFromMeta(compound.getInteger("blockData"));
/* 382 */     this.pistonFacing = EnumFacing.getFront(compound.getInteger("facing"));
/* 383 */     this.progress = compound.getFloat("progress");
/* 384 */     this.lastProgress = this.progress;
/* 385 */     this.extending = compound.getBoolean("extending");
/* 386 */     this.shouldHeadBeRendered = compound.getBoolean("source");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 391 */     super.writeToNBT(compound);
/* 392 */     compound.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
/* 393 */     compound.setInteger("blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
/* 394 */     compound.setInteger("facing", this.pistonFacing.getIndex());
/* 395 */     compound.setFloat("progress", this.lastProgress);
/* 396 */     compound.setBoolean("extending", this.extending);
/* 397 */     compound.setBoolean("source", this.shouldHeadBeRendered);
/* 398 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190609_a(World p_190609_1_, BlockPos p_190609_2_, AxisAlignedBB p_190609_3_, List<AxisAlignedBB> p_190609_4_, @Nullable Entity p_190609_5_) {
/* 403 */     if (!this.extending && this.shouldHeadBeRendered)
/*     */     {
/* 405 */       this.pistonState.withProperty((IProperty)BlockPistonBase.EXTENDED, Boolean.valueOf(true)).addCollisionBoxToList(p_190609_1_, p_190609_2_, p_190609_3_, p_190609_4_, p_190609_5_, false);
/*     */     }
/*     */     
/* 408 */     EnumFacing enumfacing = field_190613_i.get();
/*     */     
/* 410 */     if (this.progress >= 1.0D || enumfacing != (this.extending ? this.pistonFacing : this.pistonFacing.getOpposite())) {
/*     */       IBlockState iblockstate;
/* 412 */       int i = p_190609_4_.size();
/*     */ 
/*     */       
/* 415 */       if (shouldPistonHeadBeRendered()) {
/*     */         
/* 417 */         iblockstate = Blocks.PISTON_HEAD.getDefaultState().withProperty((IProperty)BlockPistonExtension.FACING, (Comparable)this.pistonFacing).withProperty((IProperty)BlockPistonExtension.SHORT, Boolean.valueOf(this.extending ^ ((1.0F - this.progress < 0.25F))));
/*     */       }
/*     */       else {
/*     */         
/* 421 */         iblockstate = this.pistonState;
/*     */       } 
/*     */       
/* 424 */       float f = getExtendedProgress(this.progress);
/* 425 */       double d0 = (this.pistonFacing.getFrontOffsetX() * f);
/* 426 */       double d1 = (this.pistonFacing.getFrontOffsetY() * f);
/* 427 */       double d2 = (this.pistonFacing.getFrontOffsetZ() * f);
/* 428 */       iblockstate.addCollisionBoxToList(p_190609_1_, p_190609_2_, p_190609_3_.offset(-d0, -d1, -d2), p_190609_4_, p_190609_5_, true);
/*     */       
/* 430 */       for (int j = i; j < p_190609_4_.size(); j++)
/*     */       {
/* 432 */         p_190609_4_.set(j, ((AxisAlignedBB)p_190609_4_.get(j)).offset(d0, d1, d2));
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityPiston.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */