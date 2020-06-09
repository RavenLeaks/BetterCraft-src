/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockAnvil;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityFallingBlock
/*     */   extends Entity {
/*     */   private IBlockState fallTile;
/*     */   public int fallTime;
/*     */   public boolean shouldDropItem = true;
/*     */   private boolean canSetAsBlock;
/*     */   private boolean hurtEntities;
/*  40 */   private int fallHurtMax = 40;
/*  41 */   private float fallHurtAmount = 2.0F;
/*     */   public NBTTagCompound tileEntityData;
/*  43 */   protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(EntityFallingBlock.class, DataSerializers.BLOCK_POS);
/*     */ 
/*     */   
/*     */   public EntityFallingBlock(World worldIn) {
/*  47 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFallingBlock(World worldIn, double x, double y, double z, IBlockState fallingBlockState) {
/*  52 */     super(worldIn);
/*  53 */     this.fallTile = fallingBlockState;
/*  54 */     this.preventEntitySpawning = true;
/*  55 */     setSize(0.98F, 0.98F);
/*  56 */     setPosition(x, y + ((1.0F - this.height) / 2.0F), z);
/*  57 */     this.motionX = 0.0D;
/*  58 */     this.motionY = 0.0D;
/*  59 */     this.motionZ = 0.0D;
/*  60 */     this.prevPosX = x;
/*  61 */     this.prevPosY = y;
/*  62 */     this.prevPosZ = z;
/*  63 */     setOrigin(new BlockPos(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeAttackedWithItem() {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOrigin(BlockPos p_184530_1_) {
/*  76 */     this.dataManager.set(ORIGIN, p_184530_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getOrigin() {
/*  81 */     return (BlockPos)this.dataManager.get(ORIGIN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  95 */     this.dataManager.register(ORIGIN, BlockPos.ORIGIN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 103 */     return !this.isDead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 111 */     Block block = this.fallTile.getBlock();
/*     */     
/* 113 */     if (this.fallTile.getMaterial() == Material.AIR) {
/*     */       
/* 115 */       setDead();
/*     */     }
/*     */     else {
/*     */       
/* 119 */       this.prevPosX = this.posX;
/* 120 */       this.prevPosY = this.posY;
/* 121 */       this.prevPosZ = this.posZ;
/*     */       
/* 123 */       if (this.fallTime++ == 0) {
/*     */         
/* 125 */         BlockPos blockpos = new BlockPos(this);
/*     */         
/* 127 */         if (this.world.getBlockState(blockpos).getBlock() == block) {
/*     */           
/* 129 */           this.world.setBlockToAir(blockpos);
/*     */         }
/* 131 */         else if (!this.world.isRemote) {
/*     */           
/* 133 */           setDead();
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 138 */       if (!hasNoGravity())
/*     */       {
/* 140 */         this.motionY -= 0.03999999910593033D;
/*     */       }
/*     */       
/* 143 */       moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/*     */       
/* 145 */       if (!this.world.isRemote) {
/*     */         
/* 147 */         BlockPos blockpos1 = new BlockPos(this);
/* 148 */         boolean flag = (this.fallTile.getBlock() == Blocks.field_192444_dS);
/* 149 */         boolean flag1 = (flag && this.world.getBlockState(blockpos1).getMaterial() == Material.WATER);
/* 150 */         double d0 = this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ;
/*     */         
/* 152 */         if (flag && d0 > 1.0D) {
/*     */           
/* 154 */           RayTraceResult raytraceresult = this.world.rayTraceBlocks(new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ), new Vec3d(this.posX, this.posY, this.posZ), true);
/*     */           
/* 156 */           if (raytraceresult != null && this.world.getBlockState(raytraceresult.getBlockPos()).getMaterial() == Material.WATER) {
/*     */             
/* 158 */             blockpos1 = raytraceresult.getBlockPos();
/* 159 */             flag1 = true;
/*     */           } 
/*     */         } 
/*     */         
/* 163 */         if (!this.onGround && !flag1) {
/*     */           
/* 165 */           if ((this.fallTime > 100 && !this.world.isRemote && (blockpos1.getY() < 1 || blockpos1.getY() > 256)) || this.fallTime > 600)
/*     */           {
/* 167 */             if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops"))
/*     */             {
/* 169 */               entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
/*     */             }
/*     */             
/* 172 */             setDead();
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 177 */           IBlockState iblockstate = this.world.getBlockState(blockpos1);
/*     */           
/* 179 */           if (!flag1 && BlockFalling.canFallThrough(this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.009999999776482582D, this.posZ)))) {
/*     */             
/* 181 */             this.onGround = false;
/*     */             
/*     */             return;
/*     */           } 
/* 185 */           this.motionX *= 0.699999988079071D;
/* 186 */           this.motionZ *= 0.699999988079071D;
/* 187 */           this.motionY *= -0.5D;
/*     */           
/* 189 */           if (iblockstate.getBlock() != Blocks.PISTON_EXTENSION) {
/*     */             
/* 191 */             setDead();
/*     */             
/* 193 */             if (!this.canSetAsBlock) {
/*     */               
/* 195 */               if (this.world.func_190527_a(block, blockpos1, true, EnumFacing.UP, null) && (flag1 || !BlockFalling.canFallThrough(this.world.getBlockState(blockpos1.down()))) && this.world.setBlockState(blockpos1, this.fallTile, 3)) {
/*     */                 
/* 197 */                 if (block instanceof BlockFalling)
/*     */                 {
/* 199 */                   ((BlockFalling)block).onEndFalling(this.world, blockpos1, this.fallTile, iblockstate);
/*     */                 }
/*     */                 
/* 202 */                 if (this.tileEntityData != null && block instanceof net.minecraft.block.ITileEntityProvider) {
/*     */                   
/* 204 */                   TileEntity tileentity = this.world.getTileEntity(blockpos1);
/*     */                   
/* 206 */                   if (tileentity != null)
/*     */                   {
/* 208 */                     NBTTagCompound nbttagcompound = tileentity.writeToNBT(new NBTTagCompound());
/*     */                     
/* 210 */                     for (String s : this.tileEntityData.getKeySet()) {
/*     */                       
/* 212 */                       NBTBase nbtbase = this.tileEntityData.getTag(s);
/*     */                       
/* 214 */                       if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s))
/*     */                       {
/* 216 */                         nbttagcompound.setTag(s, nbtbase.copy());
/*     */                       }
/*     */                     } 
/*     */                     
/* 220 */                     tileentity.readFromNBT(nbttagcompound);
/* 221 */                     tileentity.markDirty();
/*     */                   }
/*     */                 
/*     */                 } 
/* 225 */               } else if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
/*     */                 
/* 227 */                 entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
/*     */               }
/*     */             
/* 230 */             } else if (block instanceof BlockFalling) {
/*     */               
/* 232 */               ((BlockFalling)block).func_190974_b(this.world, blockpos1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 238 */       this.motionX *= 0.9800000190734863D;
/* 239 */       this.motionY *= 0.9800000190734863D;
/* 240 */       this.motionZ *= 0.9800000190734863D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/* 246 */     Block block = this.fallTile.getBlock();
/*     */     
/* 248 */     if (this.hurtEntities) {
/*     */       
/* 250 */       int i = MathHelper.ceil(distance - 1.0F);
/*     */       
/* 252 */       if (i > 0) {
/*     */         
/* 254 */         List<Entity> list = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()));
/* 255 */         boolean flag = (block == Blocks.ANVIL);
/* 256 */         DamageSource damagesource = flag ? DamageSource.anvil : DamageSource.fallingBlock;
/*     */         
/* 258 */         for (Entity entity : list)
/*     */         {
/* 260 */           entity.attackEntityFrom(damagesource, Math.min(MathHelper.floor(i * this.fallHurtAmount), this.fallHurtMax));
/*     */         }
/*     */         
/* 263 */         if (flag && this.rand.nextFloat() < 0.05000000074505806D + i * 0.05D) {
/*     */           
/* 265 */           int j = ((Integer)this.fallTile.getValue((IProperty)BlockAnvil.DAMAGE)).intValue();
/* 266 */           j++;
/*     */           
/* 268 */           if (j > 2) {
/*     */             
/* 270 */             this.canSetAsBlock = true;
/*     */           }
/*     */           else {
/*     */             
/* 274 */             this.fallTile = this.fallTile.withProperty((IProperty)BlockAnvil.DAMAGE, Integer.valueOf(j));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFixesFallingBlock(DataFixer fixer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/* 290 */     Block block = (this.fallTile != null) ? this.fallTile.getBlock() : Blocks.AIR;
/* 291 */     ResourceLocation resourcelocation = (ResourceLocation)Block.REGISTRY.getNameForObject(block);
/* 292 */     compound.setString("Block", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 293 */     compound.setByte("Data", (byte)block.getMetaFromState(this.fallTile));
/* 294 */     compound.setInteger("Time", this.fallTime);
/* 295 */     compound.setBoolean("DropItem", this.shouldDropItem);
/* 296 */     compound.setBoolean("HurtEntities", this.hurtEntities);
/* 297 */     compound.setFloat("FallHurtAmount", this.fallHurtAmount);
/* 298 */     compound.setInteger("FallHurtMax", this.fallHurtMax);
/*     */     
/* 300 */     if (this.tileEntityData != null)
/*     */     {
/* 302 */       compound.setTag("TileEntityData", (NBTBase)this.tileEntityData);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/* 311 */     int i = compound.getByte("Data") & 0xFF;
/*     */     
/* 313 */     if (compound.hasKey("Block", 8)) {
/*     */       
/* 315 */       this.fallTile = Block.getBlockFromName(compound.getString("Block")).getStateFromMeta(i);
/*     */     }
/* 317 */     else if (compound.hasKey("TileID", 99)) {
/*     */       
/* 319 */       this.fallTile = Block.getBlockById(compound.getInteger("TileID")).getStateFromMeta(i);
/*     */     }
/*     */     else {
/*     */       
/* 323 */       this.fallTile = Block.getBlockById(compound.getByte("Tile") & 0xFF).getStateFromMeta(i);
/*     */     } 
/*     */     
/* 326 */     this.fallTime = compound.getInteger("Time");
/* 327 */     Block block = this.fallTile.getBlock();
/*     */     
/* 329 */     if (compound.hasKey("HurtEntities", 99)) {
/*     */       
/* 331 */       this.hurtEntities = compound.getBoolean("HurtEntities");
/* 332 */       this.fallHurtAmount = compound.getFloat("FallHurtAmount");
/* 333 */       this.fallHurtMax = compound.getInteger("FallHurtMax");
/*     */     }
/* 335 */     else if (block == Blocks.ANVIL) {
/*     */       
/* 337 */       this.hurtEntities = true;
/*     */     } 
/*     */     
/* 340 */     if (compound.hasKey("DropItem", 99))
/*     */     {
/* 342 */       this.shouldDropItem = compound.getBoolean("DropItem");
/*     */     }
/*     */     
/* 345 */     if (compound.hasKey("TileEntityData", 10))
/*     */     {
/* 347 */       this.tileEntityData = compound.getCompoundTag("TileEntityData");
/*     */     }
/*     */     
/* 350 */     if (block == null || block.getDefaultState().getMaterial() == Material.AIR)
/*     */     {
/* 352 */       this.fallTile = Blocks.SAND.getDefaultState();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorldObj() {
/* 358 */     return this.world;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHurtEntities(boolean p_145806_1_) {
/* 363 */     this.hurtEntities = p_145806_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRenderOnFire() {
/* 371 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEntityCrashInfo(CrashReportCategory category) {
/* 376 */     super.addEntityCrashInfo(category);
/*     */     
/* 378 */     if (this.fallTile != null) {
/*     */       
/* 380 */       Block block = this.fallTile.getBlock();
/* 381 */       category.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(block)));
/* 382 */       category.addCrashSection("Immitating block data", Integer.valueOf(block.getMetaFromState(this.fallTile)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IBlockState getBlock() {
/* 389 */     return this.fallTile;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean ignoreItemEntityData() {
/* 394 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityFallingBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */