/*     */ package net.minecraft.tileentity;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.gen.feature.WorldGenEndGateway;
/*     */ import net.minecraft.world.gen.feature.WorldGenEndIsland;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class TileEntityEndGateway extends TileEntityEndPortal implements ITickable {
/*  28 */   private static final Logger LOG = LogManager.getLogger();
/*     */   
/*     */   private long age;
/*     */   private int teleportCooldown;
/*     */   private BlockPos exitPortal;
/*     */   private boolean exactTeleport;
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  36 */     super.writeToNBT(compound);
/*  37 */     compound.setLong("Age", this.age);
/*     */     
/*  39 */     if (this.exitPortal != null)
/*     */     {
/*  41 */       compound.setTag("ExitPortal", (NBTBase)NBTUtil.createPosTag(this.exitPortal));
/*     */     }
/*     */     
/*  44 */     if (this.exactTeleport)
/*     */     {
/*  46 */       compound.setBoolean("ExactTeleport", this.exactTeleport);
/*     */     }
/*     */     
/*  49 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  54 */     super.readFromNBT(compound);
/*  55 */     this.age = compound.getLong("Age");
/*     */     
/*  57 */     if (compound.hasKey("ExitPortal", 10))
/*     */     {
/*  59 */       this.exitPortal = NBTUtil.getPosFromTag(compound.getCompoundTag("ExitPortal"));
/*     */     }
/*     */     
/*  62 */     this.exactTeleport = compound.getBoolean("ExactTeleport");
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxRenderDistanceSquared() {
/*  67 */     return 65536.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  75 */     boolean flag = isSpawning();
/*  76 */     boolean flag1 = isCoolingDown();
/*  77 */     this.age++;
/*     */     
/*  79 */     if (flag1) {
/*     */       
/*  81 */       this.teleportCooldown--;
/*     */     }
/*  83 */     else if (!this.world.isRemote) {
/*     */       
/*  85 */       List<Entity> list = this.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPos()));
/*     */       
/*  87 */       if (!list.isEmpty())
/*     */       {
/*  89 */         teleportEntity(list.get(0));
/*     */       }
/*     */       
/*  92 */       if (this.age % 2400L == 0L)
/*     */       {
/*  94 */         triggerCooldown();
/*     */       }
/*     */     } 
/*     */     
/*  98 */     if (flag != isSpawning() || flag1 != isCoolingDown())
/*     */     {
/* 100 */       markDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSpawning() {
/* 106 */     return (this.age < 200L);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCoolingDown() {
/* 111 */     return (this.teleportCooldown > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSpawnPercent(float p_184302_1_) {
/* 116 */     return MathHelper.clamp(((float)this.age + p_184302_1_) / 200.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCooldownPercent(float p_184305_1_) {
/* 121 */     return 1.0F - MathHelper.clamp((this.teleportCooldown - p_184305_1_) / 40.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SPacketUpdateTileEntity getUpdatePacket() {
/* 127 */     return new SPacketUpdateTileEntity(this.pos, 8, getUpdateTag());
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getUpdateTag() {
/* 132 */     return writeToNBT(new NBTTagCompound());
/*     */   }
/*     */ 
/*     */   
/*     */   public void triggerCooldown() {
/* 137 */     if (!this.world.isRemote) {
/*     */       
/* 139 */       this.teleportCooldown = 40;
/* 140 */       this.world.addBlockEvent(getPos(), getBlockType(), 1, 0);
/* 141 */       markDirty();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 147 */     if (id == 1) {
/*     */       
/* 149 */       this.teleportCooldown = 40;
/* 150 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 154 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void teleportEntity(Entity entityIn) {
/* 160 */     if (!this.world.isRemote && !isCoolingDown()) {
/*     */       
/* 162 */       this.teleportCooldown = 100;
/*     */       
/* 164 */       if (this.exitPortal == null && this.world.provider instanceof net.minecraft.world.WorldProviderEnd)
/*     */       {
/* 166 */         findExitPortal();
/*     */       }
/*     */       
/* 169 */       if (this.exitPortal != null) {
/*     */         
/* 171 */         BlockPos blockpos = this.exactTeleport ? this.exitPortal : findExitPosition();
/* 172 */         entityIn.setPositionAndUpdate(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D);
/*     */       } 
/*     */       
/* 175 */       triggerCooldown();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockPos findExitPosition() {
/* 181 */     BlockPos blockpos = findHighestBlock(this.world, this.exitPortal, 5, false);
/* 182 */     LOG.debug("Best exit position for portal at {} is {}", this.exitPortal, blockpos);
/* 183 */     return blockpos.up();
/*     */   }
/*     */ 
/*     */   
/*     */   private void findExitPortal() {
/* 188 */     Vec3d vec3d = (new Vec3d(getPos().getX(), 0.0D, getPos().getZ())).normalize();
/* 189 */     Vec3d vec3d1 = vec3d.scale(1024.0D);
/*     */     
/* 191 */     for (int i = 16; getChunk(this.world, vec3d1).getTopFilledSegment() > 0 && i-- > 0; vec3d1 = vec3d1.add(vec3d.scale(-16.0D)))
/*     */     {
/* 193 */       LOG.debug("Skipping backwards past nonempty chunk at {}", vec3d1);
/*     */     }
/*     */     
/* 196 */     for (int j = 16; getChunk(this.world, vec3d1).getTopFilledSegment() == 0 && j-- > 0; vec3d1 = vec3d1.add(vec3d.scale(16.0D)))
/*     */     {
/* 198 */       LOG.debug("Skipping forward past empty chunk at {}", vec3d1);
/*     */     }
/*     */     
/* 201 */     LOG.debug("Found chunk at {}", vec3d1);
/* 202 */     Chunk chunk = getChunk(this.world, vec3d1);
/* 203 */     this.exitPortal = findSpawnpointInChunk(chunk);
/*     */     
/* 205 */     if (this.exitPortal == null) {
/*     */       
/* 207 */       this.exitPortal = new BlockPos(vec3d1.xCoord + 0.5D, 75.0D, vec3d1.zCoord + 0.5D);
/* 208 */       LOG.debug("Failed to find suitable block, settling on {}", this.exitPortal);
/* 209 */       (new WorldGenEndIsland()).generate(this.world, new Random(this.exitPortal.toLong()), this.exitPortal);
/*     */     }
/*     */     else {
/*     */       
/* 213 */       LOG.debug("Found block at {}", this.exitPortal);
/*     */     } 
/*     */     
/* 216 */     this.exitPortal = findHighestBlock(this.world, this.exitPortal, 16, true);
/* 217 */     LOG.debug("Creating portal at {}", this.exitPortal);
/* 218 */     this.exitPortal = this.exitPortal.up(10);
/* 219 */     createExitPortal(this.exitPortal);
/* 220 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   private static BlockPos findHighestBlock(World p_184308_0_, BlockPos p_184308_1_, int p_184308_2_, boolean p_184308_3_) {
/* 225 */     BlockPos blockpos = null;
/*     */     
/* 227 */     for (int i = -p_184308_2_; i <= p_184308_2_; i++) {
/*     */       
/* 229 */       for (int j = -p_184308_2_; j <= p_184308_2_; j++) {
/*     */         
/* 231 */         if (i != 0 || j != 0 || p_184308_3_)
/*     */         {
/* 233 */           for (int k = 255; k > ((blockpos == null) ? 0 : blockpos.getY()); k--) {
/*     */             
/* 235 */             BlockPos blockpos1 = new BlockPos(p_184308_1_.getX() + i, k, p_184308_1_.getZ() + j);
/* 236 */             IBlockState iblockstate = p_184308_0_.getBlockState(blockpos1);
/*     */             
/* 238 */             if (iblockstate.isBlockNormalCube() && (p_184308_3_ || iblockstate.getBlock() != Blocks.BEDROCK)) {
/*     */               
/* 240 */               blockpos = blockpos1;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 248 */     return (blockpos == null) ? p_184308_1_ : blockpos;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Chunk getChunk(World worldIn, Vec3d vec3) {
/* 253 */     return worldIn.getChunkFromChunkCoords(MathHelper.floor(vec3.xCoord / 16.0D), MathHelper.floor(vec3.zCoord / 16.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static BlockPos findSpawnpointInChunk(Chunk chunkIn) {
/* 259 */     BlockPos blockpos = new BlockPos(chunkIn.xPosition * 16, 30, chunkIn.zPosition * 16);
/* 260 */     int i = chunkIn.getTopFilledSegment() + 16 - 1;
/* 261 */     BlockPos blockpos1 = new BlockPos(chunkIn.xPosition * 16 + 16 - 1, i, chunkIn.zPosition * 16 + 16 - 1);
/* 262 */     BlockPos blockpos2 = null;
/* 263 */     double d0 = 0.0D;
/*     */     
/* 265 */     for (BlockPos blockpos3 : BlockPos.getAllInBox(blockpos, blockpos1)) {
/*     */       
/* 267 */       IBlockState iblockstate = chunkIn.getBlockState(blockpos3);
/*     */       
/* 269 */       if (iblockstate.getBlock() == Blocks.END_STONE && !chunkIn.getBlockState(blockpos3.up(1)).isBlockNormalCube() && !chunkIn.getBlockState(blockpos3.up(2)).isBlockNormalCube()) {
/*     */         
/* 271 */         double d1 = blockpos3.distanceSqToCenter(0.0D, 0.0D, 0.0D);
/*     */         
/* 273 */         if (blockpos2 == null || d1 < d0) {
/*     */           
/* 275 */           blockpos2 = blockpos3;
/* 276 */           d0 = d1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     return blockpos2;
/*     */   }
/*     */ 
/*     */   
/*     */   private void createExitPortal(BlockPos posIn) {
/* 286 */     (new WorldGenEndGateway()).generate(this.world, new Random(), posIn);
/* 287 */     TileEntity tileentity = this.world.getTileEntity(posIn);
/*     */     
/* 289 */     if (tileentity instanceof TileEntityEndGateway) {
/*     */       
/* 291 */       TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;
/* 292 */       tileentityendgateway.exitPortal = new BlockPos((Vec3i)getPos());
/* 293 */       tileentityendgateway.markDirty();
/*     */     }
/*     */     else {
/*     */       
/* 297 */       LOG.warn("Couldn't save exit portal at {}", posIn);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderFace(EnumFacing p_184313_1_) {
/* 303 */     return getBlockType().getDefaultState().shouldSideBeRendered((IBlockAccess)this.world, getPos(), p_184313_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getParticleAmount() {
/* 308 */     int i = 0; byte b; int j;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 310 */     for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 312 */       i += shouldRenderFace(enumfacing) ? 1 : 0;
/*     */       b++; }
/*     */     
/* 315 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190603_b(BlockPos p_190603_1_) {
/* 320 */     this.exactTeleport = true;
/* 321 */     this.exitPortal = p_190603_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityEndGateway.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */