/*     */ package net.minecraft.world.gen.structure.template;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagDouble;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityStructure;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.ObjectIntIdentityMap;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataFixer;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.IFixType;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ public class Template {
/*  41 */   private final List<BlockInfo> blocks = Lists.newArrayList();
/*  42 */   private final List<EntityInfo> entities = Lists.newArrayList();
/*     */ 
/*     */   
/*  45 */   private BlockPos size = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*  48 */   private String author = "?";
/*     */ 
/*     */   
/*     */   public BlockPos getSize() {
/*  52 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAuthor(String authorIn) {
/*  57 */     this.author = authorIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAuthor() {
/*  62 */     return this.author;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void takeBlocksFromWorld(World worldIn, BlockPos startPos, BlockPos endPos, boolean takeEntities, @Nullable Block toIgnore) {
/*  70 */     if (endPos.getX() >= 1 && endPos.getY() >= 1 && endPos.getZ() >= 1) {
/*     */       
/*  72 */       BlockPos blockpos = startPos.add((Vec3i)endPos).add(-1, -1, -1);
/*  73 */       List<BlockInfo> list = Lists.newArrayList();
/*  74 */       List<BlockInfo> list1 = Lists.newArrayList();
/*  75 */       List<BlockInfo> list2 = Lists.newArrayList();
/*  76 */       BlockPos blockpos1 = new BlockPos(Math.min(startPos.getX(), blockpos.getX()), Math.min(startPos.getY(), blockpos.getY()), Math.min(startPos.getZ(), blockpos.getZ()));
/*  77 */       BlockPos blockpos2 = new BlockPos(Math.max(startPos.getX(), blockpos.getX()), Math.max(startPos.getY(), blockpos.getY()), Math.max(startPos.getZ(), blockpos.getZ()));
/*  78 */       this.size = endPos;
/*     */       
/*  80 */       for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos1, blockpos2)) {
/*     */         
/*  82 */         BlockPos blockpos3 = blockpos$mutableblockpos.subtract((Vec3i)blockpos1);
/*  83 */         IBlockState iblockstate = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos);
/*     */         
/*  85 */         if (toIgnore == null || toIgnore != iblockstate.getBlock()) {
/*     */           
/*  87 */           TileEntity tileentity = worldIn.getTileEntity((BlockPos)blockpos$mutableblockpos);
/*     */           
/*  89 */           if (tileentity != null) {
/*     */             
/*  91 */             NBTTagCompound nbttagcompound = tileentity.writeToNBT(new NBTTagCompound());
/*  92 */             nbttagcompound.removeTag("x");
/*  93 */             nbttagcompound.removeTag("y");
/*  94 */             nbttagcompound.removeTag("z");
/*  95 */             list1.add(new BlockInfo(blockpos3, iblockstate, nbttagcompound)); continue;
/*     */           } 
/*  97 */           if (!iblockstate.isFullBlock() && !iblockstate.isFullCube()) {
/*     */             
/*  99 */             list2.add(new BlockInfo(blockpos3, iblockstate, null));
/*     */             
/*     */             continue;
/*     */           } 
/* 103 */           list.add(new BlockInfo(blockpos3, iblockstate, null));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 108 */       this.blocks.clear();
/* 109 */       this.blocks.addAll(list);
/* 110 */       this.blocks.addAll(list1);
/* 111 */       this.blocks.addAll(list2);
/*     */       
/* 113 */       if (takeEntities) {
/*     */         
/* 115 */         takeEntitiesFromWorld(worldIn, blockpos1, blockpos2.add(1, 1, 1));
/*     */       }
/*     */       else {
/*     */         
/* 119 */         this.entities.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void takeEntitiesFromWorld(World worldIn, BlockPos startPos, BlockPos endPos) {
/* 129 */     List<Entity> list = worldIn.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(startPos, endPos), new Predicate<Entity>()
/*     */         {
/*     */           public boolean apply(@Nullable Entity p_apply_1_)
/*     */           {
/* 133 */             return !(p_apply_1_ instanceof net.minecraft.entity.player.EntityPlayer);
/*     */           }
/*     */         });
/* 136 */     this.entities.clear();
/*     */     
/* 138 */     for (Entity entity : list) {
/*     */       BlockPos blockpos;
/* 140 */       Vec3d vec3d = new Vec3d(entity.posX - startPos.getX(), entity.posY - startPos.getY(), entity.posZ - startPos.getZ());
/* 141 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 142 */       entity.writeToNBTOptional(nbttagcompound);
/*     */ 
/*     */       
/* 145 */       if (entity instanceof EntityPainting) {
/*     */         
/* 147 */         blockpos = ((EntityPainting)entity).getHangingPosition().subtract((Vec3i)startPos);
/*     */       }
/*     */       else {
/*     */         
/* 151 */         blockpos = new BlockPos(vec3d);
/*     */       } 
/*     */       
/* 154 */       this.entities.add(new EntityInfo(vec3d, blockpos, nbttagcompound));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<BlockPos, String> getDataBlocks(BlockPos pos, PlacementSettings placementIn) {
/* 160 */     Map<BlockPos, String> map = Maps.newHashMap();
/* 161 */     StructureBoundingBox structureboundingbox = placementIn.getBoundingBox();
/*     */     
/* 163 */     for (BlockInfo template$blockinfo : this.blocks) {
/*     */       
/* 165 */       BlockPos blockpos = transformedBlockPos(placementIn, template$blockinfo.pos).add((Vec3i)pos);
/*     */       
/* 167 */       if (structureboundingbox == null || structureboundingbox.isVecInside((Vec3i)blockpos)) {
/*     */         
/* 169 */         IBlockState iblockstate = template$blockinfo.blockState;
/*     */         
/* 171 */         if (iblockstate.getBlock() == Blocks.STRUCTURE_BLOCK && template$blockinfo.tileentityData != null) {
/*     */           
/* 173 */           TileEntityStructure.Mode tileentitystructure$mode = TileEntityStructure.Mode.valueOf(template$blockinfo.tileentityData.getString("mode"));
/*     */           
/* 175 */           if (tileentitystructure$mode == TileEntityStructure.Mode.DATA)
/*     */           {
/* 177 */             map.put(blockpos, template$blockinfo.tileentityData.getString("metadata"));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 183 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos calculateConnectedPos(PlacementSettings placementIn, BlockPos p_186262_2_, PlacementSettings p_186262_3_, BlockPos p_186262_4_) {
/* 188 */     BlockPos blockpos = transformedBlockPos(placementIn, p_186262_2_);
/* 189 */     BlockPos blockpos1 = transformedBlockPos(p_186262_3_, p_186262_4_);
/* 190 */     return blockpos.subtract((Vec3i)blockpos1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockPos transformedBlockPos(PlacementSettings placementIn, BlockPos p_186266_1_) {
/* 195 */     return transformedBlockPos(p_186266_1_, placementIn.getMirror(), placementIn.getRotation());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBlocksToWorldChunk(World worldIn, BlockPos pos, PlacementSettings placementIn) {
/* 200 */     placementIn.setBoundingBoxFromChunk();
/* 201 */     addBlocksToWorld(worldIn, pos, placementIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlocksToWorld(World worldIn, BlockPos pos, PlacementSettings placementIn) {
/* 209 */     addBlocksToWorld(worldIn, pos, new BlockRotationProcessor(pos, placementIn), placementIn, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlocksToWorld(World worldIn, BlockPos pos, PlacementSettings placementIn, int flags) {
/* 219 */     addBlocksToWorld(worldIn, pos, new BlockRotationProcessor(pos, placementIn), placementIn, flags);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBlocksToWorld(World p_189960_1_, BlockPos p_189960_2_, @Nullable ITemplateProcessor p_189960_3_, PlacementSettings p_189960_4_, int p_189960_5_) {
/* 224 */     if ((!this.blocks.isEmpty() || (!p_189960_4_.getIgnoreEntities() && !this.entities.isEmpty())) && this.size.getX() >= 1 && this.size.getY() >= 1 && this.size.getZ() >= 1) {
/*     */       
/* 226 */       Block block = p_189960_4_.getReplacedBlock();
/* 227 */       StructureBoundingBox structureboundingbox = p_189960_4_.getBoundingBox();
/*     */       
/* 229 */       for (BlockInfo template$blockinfo : this.blocks) {
/*     */         
/* 231 */         BlockPos blockpos = transformedBlockPos(p_189960_4_, template$blockinfo.pos).add((Vec3i)p_189960_2_);
/* 232 */         BlockInfo template$blockinfo1 = (p_189960_3_ != null) ? p_189960_3_.processBlock(p_189960_1_, blockpos, template$blockinfo) : template$blockinfo;
/*     */         
/* 234 */         if (template$blockinfo1 != null) {
/*     */           
/* 236 */           Block block1 = template$blockinfo1.blockState.getBlock();
/*     */           
/* 238 */           if ((block == null || block != block1) && (!p_189960_4_.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) && (structureboundingbox == null || structureboundingbox.isVecInside((Vec3i)blockpos))) {
/*     */             
/* 240 */             IBlockState iblockstate = template$blockinfo1.blockState.withMirror(p_189960_4_.getMirror());
/* 241 */             IBlockState iblockstate1 = iblockstate.withRotation(p_189960_4_.getRotation());
/*     */             
/* 243 */             if (template$blockinfo1.tileentityData != null) {
/*     */               
/* 245 */               TileEntity tileentity = p_189960_1_.getTileEntity(blockpos);
/*     */               
/* 247 */               if (tileentity != null) {
/*     */                 
/* 249 */                 if (tileentity instanceof IInventory)
/*     */                 {
/* 251 */                   ((IInventory)tileentity).clear();
/*     */                 }
/*     */                 
/* 254 */                 p_189960_1_.setBlockState(blockpos, Blocks.BARRIER.getDefaultState(), 4);
/*     */               } 
/*     */             } 
/*     */             
/* 258 */             if (p_189960_1_.setBlockState(blockpos, iblockstate1, p_189960_5_) && template$blockinfo1.tileentityData != null) {
/*     */               
/* 260 */               TileEntity tileentity2 = p_189960_1_.getTileEntity(blockpos);
/*     */               
/* 262 */               if (tileentity2 != null) {
/*     */                 
/* 264 */                 template$blockinfo1.tileentityData.setInteger("x", blockpos.getX());
/* 265 */                 template$blockinfo1.tileentityData.setInteger("y", blockpos.getY());
/* 266 */                 template$blockinfo1.tileentityData.setInteger("z", blockpos.getZ());
/* 267 */                 tileentity2.readFromNBT(template$blockinfo1.tileentityData);
/* 268 */                 tileentity2.mirror(p_189960_4_.getMirror());
/* 269 */                 tileentity2.rotate(p_189960_4_.getRotation());
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 276 */       for (BlockInfo template$blockinfo2 : this.blocks) {
/*     */         
/* 278 */         if (block == null || block != template$blockinfo2.blockState.getBlock()) {
/*     */           
/* 280 */           BlockPos blockpos1 = transformedBlockPos(p_189960_4_, template$blockinfo2.pos).add((Vec3i)p_189960_2_);
/*     */           
/* 282 */           if (structureboundingbox == null || structureboundingbox.isVecInside((Vec3i)blockpos1)) {
/*     */             
/* 284 */             p_189960_1_.notifyNeighborsRespectDebug(blockpos1, template$blockinfo2.blockState.getBlock(), false);
/*     */             
/* 286 */             if (template$blockinfo2.tileentityData != null) {
/*     */               
/* 288 */               TileEntity tileentity1 = p_189960_1_.getTileEntity(blockpos1);
/*     */               
/* 290 */               if (tileentity1 != null)
/*     */               {
/* 292 */                 tileentity1.markDirty();
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 299 */       if (!p_189960_4_.getIgnoreEntities())
/*     */       {
/* 301 */         addEntitiesToWorld(p_189960_1_, p_189960_2_, p_189960_4_.getMirror(), p_189960_4_.getRotation(), structureboundingbox);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addEntitiesToWorld(World worldIn, BlockPos pos, Mirror mirrorIn, Rotation rotationIn, @Nullable StructureBoundingBox aabb) {
/* 308 */     for (EntityInfo template$entityinfo : this.entities) {
/*     */       
/* 310 */       BlockPos blockpos = transformedBlockPos(template$entityinfo.blockPos, mirrorIn, rotationIn).add((Vec3i)pos);
/*     */       
/* 312 */       if (aabb == null || aabb.isVecInside((Vec3i)blockpos)) {
/*     */         Entity entity;
/* 314 */         NBTTagCompound nbttagcompound = template$entityinfo.entityData;
/* 315 */         Vec3d vec3d = transformedVec3d(template$entityinfo.pos, mirrorIn, rotationIn);
/* 316 */         Vec3d vec3d1 = vec3d.addVector(pos.getX(), pos.getY(), pos.getZ());
/* 317 */         NBTTagList nbttaglist = new NBTTagList();
/* 318 */         nbttaglist.appendTag((NBTBase)new NBTTagDouble(vec3d1.xCoord));
/* 319 */         nbttaglist.appendTag((NBTBase)new NBTTagDouble(vec3d1.yCoord));
/* 320 */         nbttaglist.appendTag((NBTBase)new NBTTagDouble(vec3d1.zCoord));
/* 321 */         nbttagcompound.setTag("Pos", (NBTBase)nbttaglist);
/* 322 */         nbttagcompound.setUniqueId("UUID", UUID.randomUUID());
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 327 */           entity = EntityList.createEntityFromNBT(nbttagcompound, worldIn);
/*     */         }
/* 329 */         catch (Exception var15) {
/*     */           
/* 331 */           entity = null;
/*     */         } 
/*     */         
/* 334 */         if (entity != null) {
/*     */           
/* 336 */           float f = entity.getMirroredYaw(mirrorIn);
/* 337 */           f += entity.rotationYaw - entity.getRotatedYaw(rotationIn);
/* 338 */           entity.setLocationAndAngles(vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, f, entity.rotationPitch);
/* 339 */           worldIn.spawnEntityInWorld(entity);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos transformedSize(Rotation rotationIn) {
/* 347 */     switch (rotationIn) {
/*     */       
/*     */       case CLOCKWISE_90:
/*     */       case COUNTERCLOCKWISE_90:
/* 351 */         return new BlockPos(this.size.getZ(), this.size.getY(), this.size.getX());
/*     */     } 
/*     */     
/* 354 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BlockPos transformedBlockPos(BlockPos pos, Mirror mirrorIn, Rotation rotationIn) {
/* 360 */     int i = pos.getX();
/* 361 */     int j = pos.getY();
/* 362 */     int k = pos.getZ();
/* 363 */     boolean flag = true;
/*     */     
/* 365 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 368 */         k = -k;
/*     */         break;
/*     */       
/*     */       case null:
/* 372 */         i = -i;
/*     */         break;
/*     */       
/*     */       default:
/* 376 */         flag = false;
/*     */         break;
/*     */     } 
/* 379 */     switch (rotationIn) {
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 382 */         return new BlockPos(k, j, -i);
/*     */       
/*     */       case CLOCKWISE_90:
/* 385 */         return new BlockPos(-k, j, i);
/*     */       
/*     */       case null:
/* 388 */         return new BlockPos(-i, j, -k);
/*     */     } 
/*     */     
/* 391 */     return flag ? new BlockPos(i, j, k) : pos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Vec3d transformedVec3d(Vec3d vec, Mirror mirrorIn, Rotation rotationIn) {
/* 397 */     double d0 = vec.xCoord;
/* 398 */     double d1 = vec.yCoord;
/* 399 */     double d2 = vec.zCoord;
/* 400 */     boolean flag = true;
/*     */     
/* 402 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 405 */         d2 = 1.0D - d2;
/*     */         break;
/*     */       
/*     */       case null:
/* 409 */         d0 = 1.0D - d0;
/*     */         break;
/*     */       
/*     */       default:
/* 413 */         flag = false;
/*     */         break;
/*     */     } 
/* 416 */     switch (rotationIn) {
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 419 */         return new Vec3d(d2, d1, 1.0D - d0);
/*     */       
/*     */       case CLOCKWISE_90:
/* 422 */         return new Vec3d(1.0D - d2, d1, d0);
/*     */       
/*     */       case null:
/* 425 */         return new Vec3d(1.0D - d0, d1, 1.0D - d2);
/*     */     } 
/*     */     
/* 428 */     return flag ? new Vec3d(d0, d1, d2) : vec;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getZeroPositionWithTransform(BlockPos p_189961_1_, Mirror p_189961_2_, Rotation p_189961_3_) {
/* 434 */     return func_191157_a(p_189961_1_, p_189961_2_, p_189961_3_, getSize().getX(), getSize().getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockPos func_191157_a(BlockPos p_191157_0_, Mirror p_191157_1_, Rotation p_191157_2_, int p_191157_3_, int p_191157_4_) {
/* 439 */     p_191157_3_--;
/* 440 */     p_191157_4_--;
/* 441 */     int i = (p_191157_1_ == Mirror.FRONT_BACK) ? p_191157_3_ : 0;
/* 442 */     int j = (p_191157_1_ == Mirror.LEFT_RIGHT) ? p_191157_4_ : 0;
/* 443 */     BlockPos blockpos = p_191157_0_;
/*     */     
/* 445 */     switch (p_191157_2_) {
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 448 */         blockpos = p_191157_0_.add(j, 0, p_191157_3_ - i);
/*     */         break;
/*     */       
/*     */       case CLOCKWISE_90:
/* 452 */         blockpos = p_191157_0_.add(p_191157_4_ - j, 0, i);
/*     */         break;
/*     */       
/*     */       case null:
/* 456 */         blockpos = p_191157_0_.add(p_191157_3_ - i, 0, p_191157_4_ - j);
/*     */         break;
/*     */       
/*     */       case NONE:
/* 460 */         blockpos = p_191157_0_.add(i, 0, j);
/*     */         break;
/*     */     } 
/* 463 */     return blockpos;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_191158_a(DataFixer p_191158_0_) {
/* 468 */     p_191158_0_.registerWalker(FixTypes.STRUCTURE, new IDataWalker()
/*     */         {
/*     */           public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
/*     */           {
/* 472 */             if (compound.hasKey("entities", 9)) {
/*     */               
/* 474 */               NBTTagList nbttaglist = compound.getTagList("entities", 10);
/*     */               
/* 476 */               for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */                 
/* 478 */                 NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.get(i);
/*     */                 
/* 480 */                 if (nbttagcompound.hasKey("nbt", 10))
/*     */                 {
/* 482 */                   nbttagcompound.setTag("nbt", (NBTBase)fixer.process((IFixType)FixTypes.ENTITY, nbttagcompound.getCompoundTag("nbt"), versionIn));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 487 */             if (compound.hasKey("blocks", 9)) {
/*     */               
/* 489 */               NBTTagList nbttaglist1 = compound.getTagList("blocks", 10);
/*     */               
/* 491 */               for (int j = 0; j < nbttaglist1.tagCount(); j++) {
/*     */                 
/* 493 */                 NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist1.get(j);
/*     */                 
/* 495 */                 if (nbttagcompound1.hasKey("nbt", 10))
/*     */                 {
/* 497 */                   nbttagcompound1.setTag("nbt", (NBTBase)fixer.process((IFixType)FixTypes.BLOCK_ENTITY, nbttagcompound1.getCompoundTag("nbt"), versionIn));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 502 */             return compound;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
/* 509 */     BasicPalette template$basicpalette = new BasicPalette(null);
/* 510 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 512 */     for (BlockInfo template$blockinfo : this.blocks) {
/*     */       
/* 514 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 515 */       nbttagcompound.setTag("pos", (NBTBase)writeInts(new int[] { template$blockinfo.pos.getX(), template$blockinfo.pos.getY(), template$blockinfo.pos.getZ() }));
/* 516 */       nbttagcompound.setInteger("state", template$basicpalette.idFor(template$blockinfo.blockState));
/*     */       
/* 518 */       if (template$blockinfo.tileentityData != null)
/*     */       {
/* 520 */         nbttagcompound.setTag("nbt", (NBTBase)template$blockinfo.tileentityData);
/*     */       }
/*     */       
/* 523 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 526 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 528 */     for (EntityInfo template$entityinfo : this.entities) {
/*     */       
/* 530 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 531 */       nbttagcompound1.setTag("pos", (NBTBase)writeDoubles(new double[] { template$entityinfo.pos.xCoord, template$entityinfo.pos.yCoord, template$entityinfo.pos.zCoord }));
/* 532 */       nbttagcompound1.setTag("blockPos", (NBTBase)writeInts(new int[] { template$entityinfo.blockPos.getX(), template$entityinfo.blockPos.getY(), template$entityinfo.blockPos.getZ() }));
/*     */       
/* 534 */       if (template$entityinfo.entityData != null)
/*     */       {
/* 536 */         nbttagcompound1.setTag("nbt", (NBTBase)template$entityinfo.entityData);
/*     */       }
/*     */       
/* 539 */       nbttaglist1.appendTag((NBTBase)nbttagcompound1);
/*     */     } 
/*     */     
/* 542 */     NBTTagList nbttaglist2 = new NBTTagList();
/*     */     
/* 544 */     for (IBlockState iblockstate : template$basicpalette)
/*     */     {
/* 546 */       nbttaglist2.appendTag((NBTBase)NBTUtil.writeBlockState(new NBTTagCompound(), iblockstate));
/*     */     }
/*     */     
/* 549 */     nbt.setTag("palette", (NBTBase)nbttaglist2);
/* 550 */     nbt.setTag("blocks", (NBTBase)nbttaglist);
/* 551 */     nbt.setTag("entities", (NBTBase)nbttaglist1);
/* 552 */     nbt.setTag("size", (NBTBase)writeInts(new int[] { this.size.getX(), this.size.getY(), this.size.getZ() }));
/* 553 */     nbt.setString("author", this.author);
/* 554 */     nbt.setInteger("DataVersion", 1343);
/* 555 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(NBTTagCompound compound) {
/* 560 */     this.blocks.clear();
/* 561 */     this.entities.clear();
/* 562 */     NBTTagList nbttaglist = compound.getTagList("size", 3);
/* 563 */     this.size = new BlockPos(nbttaglist.getIntAt(0), nbttaglist.getIntAt(1), nbttaglist.getIntAt(2));
/* 564 */     this.author = compound.getString("author");
/* 565 */     BasicPalette template$basicpalette = new BasicPalette(null);
/* 566 */     NBTTagList nbttaglist1 = compound.getTagList("palette", 10);
/*     */     
/* 568 */     for (int i = 0; i < nbttaglist1.tagCount(); i++)
/*     */     {
/* 570 */       template$basicpalette.addMapping(NBTUtil.readBlockState(nbttaglist1.getCompoundTagAt(i)), i);
/*     */     }
/*     */     
/* 573 */     NBTTagList nbttaglist3 = compound.getTagList("blocks", 10);
/*     */     
/* 575 */     for (int j = 0; j < nbttaglist3.tagCount(); j++) {
/*     */       
/* 577 */       NBTTagCompound nbttagcompound1, nbttagcompound = nbttaglist3.getCompoundTagAt(j);
/* 578 */       NBTTagList nbttaglist2 = nbttagcompound.getTagList("pos", 3);
/* 579 */       BlockPos blockpos = new BlockPos(nbttaglist2.getIntAt(0), nbttaglist2.getIntAt(1), nbttaglist2.getIntAt(2));
/* 580 */       IBlockState iblockstate = template$basicpalette.stateFor(nbttagcompound.getInteger("state"));
/*     */ 
/*     */       
/* 583 */       if (nbttagcompound.hasKey("nbt")) {
/*     */         
/* 585 */         nbttagcompound1 = nbttagcompound.getCompoundTag("nbt");
/*     */       }
/*     */       else {
/*     */         
/* 589 */         nbttagcompound1 = null;
/*     */       } 
/*     */       
/* 592 */       this.blocks.add(new BlockInfo(blockpos, iblockstate, nbttagcompound1));
/*     */     } 
/*     */     
/* 595 */     NBTTagList nbttaglist4 = compound.getTagList("entities", 10);
/*     */     
/* 597 */     for (int k = 0; k < nbttaglist4.tagCount(); k++) {
/*     */       
/* 599 */       NBTTagCompound nbttagcompound3 = nbttaglist4.getCompoundTagAt(k);
/* 600 */       NBTTagList nbttaglist5 = nbttagcompound3.getTagList("pos", 6);
/* 601 */       Vec3d vec3d = new Vec3d(nbttaglist5.getDoubleAt(0), nbttaglist5.getDoubleAt(1), nbttaglist5.getDoubleAt(2));
/* 602 */       NBTTagList nbttaglist6 = nbttagcompound3.getTagList("blockPos", 3);
/* 603 */       BlockPos blockpos1 = new BlockPos(nbttaglist6.getIntAt(0), nbttaglist6.getIntAt(1), nbttaglist6.getIntAt(2));
/*     */       
/* 605 */       if (nbttagcompound3.hasKey("nbt")) {
/*     */         
/* 607 */         NBTTagCompound nbttagcompound2 = nbttagcompound3.getCompoundTag("nbt");
/* 608 */         this.entities.add(new EntityInfo(vec3d, blockpos1, nbttagcompound2));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private NBTTagList writeInts(int... values) {
/* 615 */     NBTTagList nbttaglist = new NBTTagList(); byte b;
/*     */     int i, arrayOfInt[];
/* 617 */     for (i = (arrayOfInt = values).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/*     */       
/* 619 */       nbttaglist.appendTag((NBTBase)new NBTTagInt(j));
/*     */       b++; }
/*     */     
/* 622 */     return nbttaglist;
/*     */   }
/*     */ 
/*     */   
/*     */   private NBTTagList writeDoubles(double... values) {
/* 627 */     NBTTagList nbttaglist = new NBTTagList(); byte b; int i;
/*     */     double[] arrayOfDouble;
/* 629 */     for (i = (arrayOfDouble = values).length, b = 0; b < i; ) { double d0 = arrayOfDouble[b];
/*     */       
/* 631 */       nbttaglist.appendTag((NBTBase)new NBTTagDouble(d0));
/*     */       b++; }
/*     */     
/* 634 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   static class BasicPalette
/*     */     implements Iterable<IBlockState> {
/* 639 */     public static final IBlockState DEFAULT_BLOCK_STATE = Blocks.AIR.getDefaultState();
/*     */     
/*     */     final ObjectIntIdentityMap<IBlockState> ids;
/*     */     private int lastId;
/*     */     
/*     */     private BasicPalette() {
/* 645 */       this.ids = new ObjectIntIdentityMap(16);
/*     */     }
/*     */ 
/*     */     
/*     */     public int idFor(IBlockState p_189954_1_) {
/* 650 */       int i = this.ids.get(p_189954_1_);
/*     */       
/* 652 */       if (i == -1) {
/*     */         
/* 654 */         i = this.lastId++;
/* 655 */         this.ids.put(p_189954_1_, i);
/*     */       } 
/*     */       
/* 658 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public IBlockState stateFor(int p_189955_1_) {
/* 664 */       IBlockState iblockstate = (IBlockState)this.ids.getByValue(p_189955_1_);
/* 665 */       return (iblockstate == null) ? DEFAULT_BLOCK_STATE : iblockstate;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<IBlockState> iterator() {
/* 670 */       return this.ids.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addMapping(IBlockState p_189956_1_, int p_189956_2_) {
/* 675 */       this.ids.put(p_189956_1_, p_189956_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class BlockInfo
/*     */   {
/*     */     public final BlockPos pos;
/*     */     public final IBlockState blockState;
/*     */     public final NBTTagCompound tileentityData;
/*     */     
/*     */     public BlockInfo(BlockPos posIn, IBlockState stateIn, @Nullable NBTTagCompound compoundIn) {
/* 687 */       this.pos = posIn;
/* 688 */       this.blockState = stateIn;
/* 689 */       this.tileentityData = compoundIn;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EntityInfo
/*     */   {
/*     */     public final Vec3d pos;
/*     */     public final BlockPos blockPos;
/*     */     public final NBTTagCompound entityData;
/*     */     
/*     */     public EntityInfo(Vec3d vecIn, BlockPos posIn, NBTTagCompound compoundIn) {
/* 701 */       this.pos = vecIn;
/* 702 */       this.blockPos = posIn;
/* 703 */       this.entityData = compoundIn;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\template\Template.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */