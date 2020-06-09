/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.BlockStructure;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ import net.minecraft.world.gen.structure.template.PlacementSettings;
/*     */ import net.minecraft.world.gen.structure.template.Template;
/*     */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*     */ 
/*     */ public class TileEntityStructure
/*     */   extends TileEntity {
/*  37 */   private String name = "";
/*  38 */   private String author = "";
/*  39 */   private String metadata = "";
/*  40 */   private BlockPos position = new BlockPos(0, 1, 0);
/*  41 */   private BlockPos size = BlockPos.ORIGIN;
/*  42 */   private Mirror mirror = Mirror.NONE;
/*  43 */   private Rotation rotation = Rotation.NONE;
/*  44 */   private Mode mode = Mode.DATA;
/*     */   private boolean ignoreEntities = true;
/*     */   private boolean powered;
/*     */   private boolean showAir;
/*     */   private boolean showBoundingBox = true;
/*  49 */   private float integrity = 1.0F;
/*     */   
/*     */   private long seed;
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  54 */     super.writeToNBT(compound);
/*  55 */     compound.setString("name", this.name);
/*  56 */     compound.setString("author", this.author);
/*  57 */     compound.setString("metadata", this.metadata);
/*  58 */     compound.setInteger("posX", this.position.getX());
/*  59 */     compound.setInteger("posY", this.position.getY());
/*  60 */     compound.setInteger("posZ", this.position.getZ());
/*  61 */     compound.setInteger("sizeX", this.size.getX());
/*  62 */     compound.setInteger("sizeY", this.size.getY());
/*  63 */     compound.setInteger("sizeZ", this.size.getZ());
/*  64 */     compound.setString("rotation", this.rotation.toString());
/*  65 */     compound.setString("mirror", this.mirror.toString());
/*  66 */     compound.setString("mode", this.mode.toString());
/*  67 */     compound.setBoolean("ignoreEntities", this.ignoreEntities);
/*  68 */     compound.setBoolean("powered", this.powered);
/*  69 */     compound.setBoolean("showair", this.showAir);
/*  70 */     compound.setBoolean("showboundingbox", this.showBoundingBox);
/*  71 */     compound.setFloat("integrity", this.integrity);
/*  72 */     compound.setLong("seed", this.seed);
/*  73 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  78 */     super.readFromNBT(compound);
/*  79 */     setName(compound.getString("name"));
/*  80 */     this.author = compound.getString("author");
/*  81 */     this.metadata = compound.getString("metadata");
/*  82 */     int i = MathHelper.clamp(compound.getInteger("posX"), -32, 32);
/*  83 */     int j = MathHelper.clamp(compound.getInteger("posY"), -32, 32);
/*  84 */     int k = MathHelper.clamp(compound.getInteger("posZ"), -32, 32);
/*  85 */     this.position = new BlockPos(i, j, k);
/*  86 */     int l = MathHelper.clamp(compound.getInteger("sizeX"), 0, 32);
/*  87 */     int i1 = MathHelper.clamp(compound.getInteger("sizeY"), 0, 32);
/*  88 */     int j1 = MathHelper.clamp(compound.getInteger("sizeZ"), 0, 32);
/*  89 */     this.size = new BlockPos(l, i1, j1);
/*     */ 
/*     */     
/*     */     try {
/*  93 */       this.rotation = Rotation.valueOf(compound.getString("rotation"));
/*     */     }
/*  95 */     catch (IllegalArgumentException var11) {
/*     */       
/*  97 */       this.rotation = Rotation.NONE;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 102 */       this.mirror = Mirror.valueOf(compound.getString("mirror"));
/*     */     }
/* 104 */     catch (IllegalArgumentException var10) {
/*     */       
/* 106 */       this.mirror = Mirror.NONE;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 111 */       this.mode = Mode.valueOf(compound.getString("mode"));
/*     */     }
/* 113 */     catch (IllegalArgumentException var9) {
/*     */       
/* 115 */       this.mode = Mode.DATA;
/*     */     } 
/*     */     
/* 118 */     this.ignoreEntities = compound.getBoolean("ignoreEntities");
/* 119 */     this.powered = compound.getBoolean("powered");
/* 120 */     this.showAir = compound.getBoolean("showair");
/* 121 */     this.showBoundingBox = compound.getBoolean("showboundingbox");
/*     */     
/* 123 */     if (compound.hasKey("integrity")) {
/*     */       
/* 125 */       this.integrity = compound.getFloat("integrity");
/*     */     }
/*     */     else {
/*     */       
/* 129 */       this.integrity = 1.0F;
/*     */     } 
/*     */     
/* 132 */     this.seed = compound.getLong("seed");
/* 133 */     updateBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateBlockState() {
/* 138 */     if (this.world != null) {
/*     */       
/* 140 */       BlockPos blockpos = getPos();
/* 141 */       IBlockState iblockstate = this.world.getBlockState(blockpos);
/*     */       
/* 143 */       if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK)
/*     */       {
/* 145 */         this.world.setBlockState(blockpos, iblockstate.withProperty((IProperty)BlockStructure.MODE, this.mode), 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SPacketUpdateTileEntity getUpdatePacket() {
/* 153 */     return new SPacketUpdateTileEntity(this.pos, 7, getUpdateTag());
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getUpdateTag() {
/* 158 */     return writeToNBT(new NBTTagCompound());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean usedBy(EntityPlayer player) {
/* 163 */     if (!player.canUseCommandBlock())
/*     */     {
/* 165 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 169 */     if ((player.getEntityWorld()).isRemote)
/*     */     {
/* 171 */       player.openEditStructure(this);
/*     */     }
/*     */     
/* 174 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 180 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String nameIn) {
/* 185 */     String s = nameIn; byte b; int i;
/*     */     char[] arrayOfChar;
/* 187 */     for (i = (arrayOfChar = ChatAllowedCharacters.ILLEGAL_STRUCTURE_CHARACTERS).length, b = 0; b < i; ) { char c0 = arrayOfChar[b];
/*     */       
/* 189 */       s = s.replace(c0, '_');
/*     */       b++; }
/*     */     
/* 192 */     this.name = s;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createdBy(EntityLivingBase p_189720_1_) {
/* 197 */     if (!StringUtils.isNullOrEmpty(p_189720_1_.getName()))
/*     */     {
/* 199 */       this.author = p_189720_1_.getName();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 205 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(BlockPos posIn) {
/* 210 */     this.position = posIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getStructureSize() {
/* 215 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(BlockPos sizeIn) {
/* 220 */     this.size = sizeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mirror getMirror() {
/* 225 */     return this.mirror;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMirror(Mirror mirrorIn) {
/* 230 */     this.mirror = mirrorIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotation getRotation() {
/* 235 */     return this.rotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRotation(Rotation rotationIn) {
/* 240 */     this.rotation = rotationIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMetadata() {
/* 245 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMetadata(String metadataIn) {
/* 250 */     this.metadata = metadataIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mode getMode() {
/* 255 */     return this.mode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMode(Mode modeIn) {
/* 260 */     this.mode = modeIn;
/* 261 */     IBlockState iblockstate = this.world.getBlockState(getPos());
/*     */     
/* 263 */     if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK)
/*     */     {
/* 265 */       this.world.setBlockState(getPos(), iblockstate.withProperty((IProperty)BlockStructure.MODE, modeIn), 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextMode() {
/* 271 */     switch (getMode()) {
/*     */       
/*     */       case SAVE:
/* 274 */         setMode(Mode.LOAD);
/*     */         break;
/*     */       
/*     */       case LOAD:
/* 278 */         setMode(Mode.CORNER);
/*     */         break;
/*     */       
/*     */       case null:
/* 282 */         setMode(Mode.DATA);
/*     */         break;
/*     */       
/*     */       case DATA:
/* 286 */         setMode(Mode.SAVE);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean ignoresEntities() {
/* 292 */     return this.ignoreEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIgnoresEntities(boolean ignoreEntitiesIn) {
/* 297 */     this.ignoreEntities = ignoreEntitiesIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIntegrity() {
/* 302 */     return this.integrity;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIntegrity(float integrityIn) {
/* 307 */     this.integrity = integrityIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSeed() {
/* 312 */     return this.seed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSeed(long seedIn) {
/* 317 */     this.seed = seedIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean detectSize() {
/* 322 */     if (this.mode != Mode.SAVE)
/*     */     {
/* 324 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 328 */     BlockPos blockpos = getPos();
/* 329 */     int i = 80;
/* 330 */     BlockPos blockpos1 = new BlockPos(blockpos.getX() - 80, 0, blockpos.getZ() - 80);
/* 331 */     BlockPos blockpos2 = new BlockPos(blockpos.getX() + 80, 255, blockpos.getZ() + 80);
/* 332 */     List<TileEntityStructure> list = getNearbyCornerBlocks(blockpos1, blockpos2);
/* 333 */     List<TileEntityStructure> list1 = filterRelatedCornerBlocks(list);
/*     */     
/* 335 */     if (list1.size() < 1)
/*     */     {
/* 337 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 341 */     StructureBoundingBox structureboundingbox = calculateEnclosingBoundingBox(blockpos, list1);
/*     */     
/* 343 */     if (structureboundingbox.maxX - structureboundingbox.minX > 1 && structureboundingbox.maxY - structureboundingbox.minY > 1 && structureboundingbox.maxZ - structureboundingbox.minZ > 1) {
/*     */       
/* 345 */       this.position = new BlockPos(structureboundingbox.minX - blockpos.getX() + 1, structureboundingbox.minY - blockpos.getY() + 1, structureboundingbox.minZ - blockpos.getZ() + 1);
/* 346 */       this.size = new BlockPos(structureboundingbox.maxX - structureboundingbox.minX - 1, structureboundingbox.maxY - structureboundingbox.minY - 1, structureboundingbox.maxZ - structureboundingbox.minZ - 1);
/* 347 */       markDirty();
/* 348 */       IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 349 */       this.world.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
/* 350 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 354 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<TileEntityStructure> filterRelatedCornerBlocks(List<TileEntityStructure> p_184415_1_) {
/* 362 */     Iterable<TileEntityStructure> iterable = Iterables.filter(p_184415_1_, new Predicate<TileEntityStructure>()
/*     */         {
/*     */           public boolean apply(@Nullable TileEntityStructure p_apply_1_)
/*     */           {
/* 366 */             return (p_apply_1_.mode == TileEntityStructure.Mode.CORNER && TileEntityStructure.this.name.equals(p_apply_1_.name));
/*     */           }
/*     */         });
/* 369 */     return Lists.newArrayList(iterable);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<TileEntityStructure> getNearbyCornerBlocks(BlockPos p_184418_1_, BlockPos p_184418_2_) {
/* 374 */     List<TileEntityStructure> list = Lists.newArrayList();
/*     */     
/* 376 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(p_184418_1_, p_184418_2_)) {
/*     */       
/* 378 */       IBlockState iblockstate = this.world.getBlockState((BlockPos)blockpos$mutableblockpos);
/*     */       
/* 380 */       if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK) {
/*     */         
/* 382 */         TileEntity tileentity = this.world.getTileEntity((BlockPos)blockpos$mutableblockpos);
/*     */         
/* 384 */         if (tileentity != null && tileentity instanceof TileEntityStructure)
/*     */         {
/* 386 */           list.add((TileEntityStructure)tileentity);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 391 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private StructureBoundingBox calculateEnclosingBoundingBox(BlockPos p_184416_1_, List<TileEntityStructure> p_184416_2_) {
/*     */     StructureBoundingBox structureboundingbox;
/* 398 */     if (p_184416_2_.size() > 1) {
/*     */       
/* 400 */       BlockPos blockpos = ((TileEntityStructure)p_184416_2_.get(0)).getPos();
/* 401 */       structureboundingbox = new StructureBoundingBox((Vec3i)blockpos, (Vec3i)blockpos);
/*     */     }
/*     */     else {
/*     */       
/* 405 */       structureboundingbox = new StructureBoundingBox((Vec3i)p_184416_1_, (Vec3i)p_184416_1_);
/*     */     } 
/*     */     
/* 408 */     for (TileEntityStructure tileentitystructure : p_184416_2_) {
/*     */       
/* 410 */       BlockPos blockpos1 = tileentitystructure.getPos();
/*     */       
/* 412 */       if (blockpos1.getX() < structureboundingbox.minX) {
/*     */         
/* 414 */         structureboundingbox.minX = blockpos1.getX();
/*     */       }
/* 416 */       else if (blockpos1.getX() > structureboundingbox.maxX) {
/*     */         
/* 418 */         structureboundingbox.maxX = blockpos1.getX();
/*     */       } 
/*     */       
/* 421 */       if (blockpos1.getY() < structureboundingbox.minY) {
/*     */         
/* 423 */         structureboundingbox.minY = blockpos1.getY();
/*     */       }
/* 425 */       else if (blockpos1.getY() > structureboundingbox.maxY) {
/*     */         
/* 427 */         structureboundingbox.maxY = blockpos1.getY();
/*     */       } 
/*     */       
/* 430 */       if (blockpos1.getZ() < structureboundingbox.minZ) {
/*     */         
/* 432 */         structureboundingbox.minZ = blockpos1.getZ(); continue;
/*     */       } 
/* 434 */       if (blockpos1.getZ() > structureboundingbox.maxZ)
/*     */       {
/* 436 */         structureboundingbox.maxZ = blockpos1.getZ();
/*     */       }
/*     */     } 
/*     */     
/* 440 */     return structureboundingbox;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCoordinates(ByteBuf buf) {
/* 445 */     buf.writeInt(this.pos.getX());
/* 446 */     buf.writeInt(this.pos.getY());
/* 447 */     buf.writeInt(this.pos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean save() {
/* 452 */     return save(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean save(boolean p_189712_1_) {
/* 457 */     if (this.mode == Mode.SAVE && !this.world.isRemote && !StringUtils.isNullOrEmpty(this.name)) {
/*     */       
/* 459 */       BlockPos blockpos = getPos().add((Vec3i)this.position);
/* 460 */       WorldServer worldserver = (WorldServer)this.world;
/* 461 */       MinecraftServer minecraftserver = this.world.getMinecraftServer();
/* 462 */       TemplateManager templatemanager = worldserver.getStructureTemplateManager();
/* 463 */       Template template = templatemanager.getTemplate(minecraftserver, new ResourceLocation(this.name));
/* 464 */       template.takeBlocksFromWorld(this.world, blockpos, this.size, !this.ignoreEntities, Blocks.STRUCTURE_VOID);
/* 465 */       template.setAuthor(this.author);
/* 466 */       return !(p_189712_1_ && !templatemanager.writeTemplate(minecraftserver, new ResourceLocation(this.name)));
/*     */     } 
/*     */ 
/*     */     
/* 470 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean load() {
/* 476 */     return load(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean load(boolean p_189714_1_) {
/* 481 */     if (this.mode == Mode.LOAD && !this.world.isRemote && !StringUtils.isNullOrEmpty(this.name)) {
/*     */       
/* 483 */       BlockPos blockpos = getPos();
/* 484 */       BlockPos blockpos1 = blockpos.add((Vec3i)this.position);
/* 485 */       WorldServer worldserver = (WorldServer)this.world;
/* 486 */       MinecraftServer minecraftserver = this.world.getMinecraftServer();
/* 487 */       TemplateManager templatemanager = worldserver.getStructureTemplateManager();
/* 488 */       Template template = templatemanager.get(minecraftserver, new ResourceLocation(this.name));
/*     */       
/* 490 */       if (template == null)
/*     */       {
/* 492 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 496 */       if (!StringUtils.isNullOrEmpty(template.getAuthor()))
/*     */       {
/* 498 */         this.author = template.getAuthor();
/*     */       }
/*     */       
/* 501 */       BlockPos blockpos2 = template.getSize();
/* 502 */       boolean flag = this.size.equals(blockpos2);
/*     */       
/* 504 */       if (!flag) {
/*     */         
/* 506 */         this.size = blockpos2;
/* 507 */         markDirty();
/* 508 */         IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 509 */         this.world.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
/*     */       } 
/*     */       
/* 512 */       if (p_189714_1_ && !flag)
/*     */       {
/* 514 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 518 */       PlacementSettings placementsettings = (new PlacementSettings()).setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);
/*     */       
/* 520 */       if (this.integrity < 1.0F)
/*     */       {
/* 522 */         placementsettings.setIntegrity(MathHelper.clamp(this.integrity, 0.0F, 1.0F)).setSeed(Long.valueOf(this.seed));
/*     */       }
/*     */       
/* 525 */       template.addBlocksToWorldChunk(this.world, blockpos1, placementsettings);
/* 526 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 532 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadStructure() {
/* 538 */     WorldServer worldserver = (WorldServer)this.world;
/* 539 */     TemplateManager templatemanager = worldserver.getStructureTemplateManager();
/* 540 */     templatemanager.remove(new ResourceLocation(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStructureLoadable() {
/* 545 */     if (this.mode == Mode.LOAD && !this.world.isRemote) {
/*     */       
/* 547 */       WorldServer worldserver = (WorldServer)this.world;
/* 548 */       MinecraftServer minecraftserver = this.world.getMinecraftServer();
/* 549 */       TemplateManager templatemanager = worldserver.getStructureTemplateManager();
/* 550 */       return (templatemanager.get(minecraftserver, new ResourceLocation(this.name)) != null);
/*     */     } 
/*     */ 
/*     */     
/* 554 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPowered() {
/* 560 */     return this.powered;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPowered(boolean poweredIn) {
/* 565 */     this.powered = poweredIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean showsAir() {
/* 570 */     return this.showAir;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShowAir(boolean showAirIn) {
/* 575 */     this.showAir = showAirIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean showsBoundingBox() {
/* 580 */     return this.showBoundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShowBoundingBox(boolean showBoundingBoxIn) {
/* 585 */     this.showBoundingBox = showBoundingBoxIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ITextComponent getDisplayName() {
/* 595 */     return (ITextComponent)new TextComponentTranslation("structure_block.hover." + this.mode.modeName, new Object[] { (this.mode == Mode.DATA) ? this.metadata : this.name });
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */     implements IStringSerializable {
/* 600 */     SAVE("save", 0),
/* 601 */     LOAD("load", 1),
/* 602 */     CORNER("corner", 2),
/* 603 */     DATA("data", 3);
/*     */     
/* 605 */     private static final Mode[] MODES = new Mode[(values()).length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String modeName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int modeId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       Mode[] arrayOfMode;
/* 631 */       for (i = (arrayOfMode = values()).length, b = 0; b < i; ) { Mode tileentitystructure$mode = arrayOfMode[b];
/*     */         
/* 633 */         MODES[tileentitystructure$mode.getModeId()] = tileentitystructure$mode;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     Mode(String modeNameIn, int modeIdIn) {
/*     */       this.modeName = modeNameIn;
/*     */       this.modeId = modeIdIn;
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.modeName;
/*     */     }
/*     */     
/*     */     public int getModeId() {
/*     */       return this.modeId;
/*     */     }
/*     */     
/*     */     public static Mode getById(int id) {
/*     */       return (id >= 0 && id < MODES.length) ? MODES[id] : MODES[0];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityStructure.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */