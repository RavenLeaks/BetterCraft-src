/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockJukebox;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.registry.RegistryNamespaced;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TileEntity
/*     */ {
/*     */   public TileEntity() {
/*  29 */     this.pos = BlockPos.ORIGIN;
/*     */     
/*  31 */     this.blockMetadata = -1;
/*     */   }
/*     */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private static final RegistryNamespaced<ResourceLocation, Class<? extends TileEntity>> field_190562_f = new RegistryNamespaced();
/*     */   protected World world;
/*     */   
/*     */   private static void func_190560_a(String p_190560_0_, Class<? extends TileEntity> p_190560_1_) {
/*  38 */     field_190562_f.putObject(new ResourceLocation(p_190560_0_), p_190560_1_);
/*     */   }
/*     */   protected BlockPos pos; protected boolean tileEntityInvalid; private int blockMetadata; protected Block blockType;
/*     */   
/*     */   @Nullable
/*     */   public static ResourceLocation func_190559_a(Class<? extends TileEntity> p_190559_0_) {
/*  44 */     return (ResourceLocation)field_190562_f.getNameForObject(p_190559_0_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  52 */     return this.world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldObj(World worldIn) {
/*  60 */     this.world = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasWorldObj() {
/*  68 */     return (this.world != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  73 */     this.pos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  78 */     return writeInternal(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   private NBTTagCompound writeInternal(NBTTagCompound compound) {
/*  83 */     ResourceLocation resourcelocation = (ResourceLocation)field_190562_f.getNameForObject(getClass());
/*     */     
/*  85 */     if (resourcelocation == null)
/*     */     {
/*  87 */       throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
/*     */     }
/*     */ 
/*     */     
/*  91 */     compound.setString("id", resourcelocation.toString());
/*  92 */     compound.setInteger("x", this.pos.getX());
/*  93 */     compound.setInteger("y", this.pos.getY());
/*  94 */     compound.setInteger("z", this.pos.getZ());
/*  95 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static TileEntity create(World worldIn, NBTTagCompound compound) {
/* 102 */     TileEntity tileentity = null;
/* 103 */     String s = compound.getString("id");
/*     */ 
/*     */     
/*     */     try {
/* 107 */       Class<? extends TileEntity> oclass = (Class<? extends TileEntity>)field_190562_f.getObject(new ResourceLocation(s));
/*     */       
/* 109 */       if (oclass != null)
/*     */       {
/* 111 */         tileentity = oclass.newInstance();
/*     */       }
/*     */     }
/* 114 */     catch (Throwable throwable1) {
/*     */       
/* 116 */       LOGGER.error("Failed to create block entity {}", s, throwable1);
/*     */     } 
/*     */     
/* 119 */     if (tileentity != null) {
/*     */       
/*     */       try
/*     */       {
/* 123 */         tileentity.setWorldCreate(worldIn);
/* 124 */         tileentity.readFromNBT(compound);
/*     */       }
/* 126 */       catch (Throwable throwable)
/*     */       {
/* 128 */         LOGGER.error("Failed to load data for block entity {}", s, throwable);
/* 129 */         tileentity = null;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 134 */       LOGGER.warn("Skipping BlockEntity with id {}", s);
/*     */     } 
/*     */     
/* 137 */     return tileentity;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setWorldCreate(World worldIn) {}
/*     */ 
/*     */   
/*     */   public int getBlockMetadata() {
/* 146 */     if (this.blockMetadata == -1) {
/*     */       
/* 148 */       IBlockState iblockstate = this.world.getBlockState(this.pos);
/* 149 */       this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */     } 
/*     */     
/* 152 */     return this.blockMetadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 161 */     if (this.world != null) {
/*     */       
/* 163 */       IBlockState iblockstate = this.world.getBlockState(this.pos);
/* 164 */       this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
/* 165 */       this.world.markChunkDirty(this.pos, this);
/*     */       
/* 167 */       if (getBlockType() != Blocks.AIR)
/*     */       {
/* 169 */         this.world.updateComparatorOutputLevel(this.pos, getBlockType());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistanceSq(double x, double y, double z) {
/* 179 */     double d0 = this.pos.getX() + 0.5D - x;
/* 180 */     double d1 = this.pos.getY() + 0.5D - y;
/* 181 */     double d2 = this.pos.getZ() + 0.5D - z;
/* 182 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxRenderDistanceSquared() {
/* 187 */     return 4096.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPos() {
/* 192 */     return this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBlockType() {
/* 200 */     if (this.blockType == null && this.world != null)
/*     */     {
/* 202 */       this.blockType = this.world.getBlockState(this.pos).getBlock();
/*     */     }
/*     */     
/* 205 */     return this.blockType;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SPacketUpdateTileEntity getUpdatePacket() {
/* 211 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getUpdateTag() {
/* 216 */     return writeInternal(new NBTTagCompound());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvalid() {
/* 221 */     return this.tileEntityInvalid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 229 */     this.tileEntityInvalid = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 237 */     this.tileEntityInvalid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateContainingBlockInfo() {
/* 247 */     this.blockType = null;
/* 248 */     this.blockMetadata = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addInfoToCrashReport(CrashReportCategory reportCategory) {
/* 253 */     reportCategory.setDetail("Name", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 257 */             return TileEntity.field_190562_f.getNameForObject(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
/*     */           }
/*     */         });
/*     */     
/* 261 */     if (this.world != null) {
/*     */       
/* 263 */       CrashReportCategory.addBlockInfo(reportCategory, this.pos, getBlockType(), getBlockMetadata());
/* 264 */       reportCategory.setDetail("Actual block type", new ICrashReportDetail<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 268 */               int i = Block.getIdFromBlock(TileEntity.this.world.getBlockState(TileEntity.this.pos).getBlock());
/*     */ 
/*     */               
/*     */               try {
/* 272 */                 return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(i), Block.getBlockById(i).getUnlocalizedName(), Block.getBlockById(i).getClass().getCanonicalName() });
/*     */               }
/* 274 */               catch (Throwable var3) {
/*     */                 
/* 276 */                 return "ID #" + i;
/*     */               } 
/*     */             }
/*     */           });
/* 280 */       reportCategory.setDetail("Actual block data value", new ICrashReportDetail<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 284 */               IBlockState iblockstate = TileEntity.this.world.getBlockState(TileEntity.this.pos);
/* 285 */               int i = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */               
/* 287 */               if (i < 0)
/*     */               {
/* 289 */                 return "Unknown? (Got " + i + ")";
/*     */               }
/*     */ 
/*     */               
/* 293 */               String s = String.format("%4s", new Object[] { Integer.toBinaryString(i) }).replace(" ", "0");
/* 294 */               return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(i), s });
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPos(BlockPos posIn) {
/* 303 */     this.pos = posIn.toImmutable();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onlyOpsCanSetNbt() {
/* 308 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ITextComponent getDisplayName() {
/* 318 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rotate(Rotation p_189667_1_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void mirror(Mirror p_189668_1_) {}
/*     */ 
/*     */   
/*     */   static {
/* 331 */     func_190560_a("furnace", (Class)TileEntityFurnace.class);
/* 332 */     func_190560_a("chest", (Class)TileEntityChest.class);
/* 333 */     func_190560_a("ender_chest", (Class)TileEntityEnderChest.class);
/* 334 */     func_190560_a("jukebox", (Class)BlockJukebox.TileEntityJukebox.class);
/* 335 */     func_190560_a("dispenser", (Class)TileEntityDispenser.class);
/* 336 */     func_190560_a("dropper", (Class)TileEntityDropper.class);
/* 337 */     func_190560_a("sign", (Class)TileEntitySign.class);
/* 338 */     func_190560_a("mob_spawner", (Class)TileEntityMobSpawner.class);
/* 339 */     func_190560_a("noteblock", (Class)TileEntityNote.class);
/* 340 */     func_190560_a("piston", (Class)TileEntityPiston.class);
/* 341 */     func_190560_a("brewing_stand", (Class)TileEntityBrewingStand.class);
/* 342 */     func_190560_a("enchanting_table", (Class)TileEntityEnchantmentTable.class);
/* 343 */     func_190560_a("end_portal", (Class)TileEntityEndPortal.class);
/* 344 */     func_190560_a("beacon", (Class)TileEntityBeacon.class);
/* 345 */     func_190560_a("skull", (Class)TileEntitySkull.class);
/* 346 */     func_190560_a("daylight_detector", (Class)TileEntityDaylightDetector.class);
/* 347 */     func_190560_a("hopper", (Class)TileEntityHopper.class);
/* 348 */     func_190560_a("comparator", (Class)TileEntityComparator.class);
/* 349 */     func_190560_a("flower_pot", (Class)TileEntityFlowerPot.class);
/* 350 */     func_190560_a("banner", (Class)TileEntityBanner.class);
/* 351 */     func_190560_a("structure_block", (Class)TileEntityStructure.class);
/* 352 */     func_190560_a("end_gateway", (Class)TileEntityEndGateway.class);
/* 353 */     func_190560_a("command_block", (Class)TileEntityCommandBlock.class);
/* 354 */     func_190560_a("shulker_box", (Class)TileEntityShulkerBox.class);
/* 355 */     func_190560_a("bed", (Class)TileEntityBed.class);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */