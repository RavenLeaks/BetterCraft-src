/*     */ package net.minecraft.village;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.server.management.PlayerProfileCache;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class Village {
/*  32 */   private final List<VillageDoorInfo> villageDoorInfoList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private World worldObj;
/*     */ 
/*     */   
/*  38 */   private BlockPos centerHelper = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*  41 */   private BlockPos center = BlockPos.ORIGIN;
/*     */   
/*     */   private int villageRadius;
/*     */   
/*     */   private int lastAddDoorTimestamp;
/*     */   private int tickCounter;
/*     */   private int numVillagers;
/*     */   private int noBreedTicks;
/*  49 */   private final Map<String, Integer> playerReputation = Maps.newHashMap();
/*  50 */   private final List<VillageAggressor> villageAgressors = Lists.newArrayList();
/*     */   
/*     */   private int numIronGolems;
/*     */ 
/*     */   
/*     */   public Village() {}
/*     */ 
/*     */   
/*     */   public Village(World worldIn) {
/*  59 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorld(World worldIn) {
/*  64 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(int tickCounterIn) {
/*  72 */     this.tickCounter = tickCounterIn;
/*  73 */     removeDeadAndOutOfRangeDoors();
/*  74 */     removeDeadAndOldAgressors();
/*     */     
/*  76 */     if (tickCounterIn % 20 == 0)
/*     */     {
/*  78 */       updateNumVillagers();
/*     */     }
/*     */     
/*  81 */     if (tickCounterIn % 30 == 0)
/*     */     {
/*  83 */       updateNumIronGolems();
/*     */     }
/*     */     
/*  86 */     int i = this.numVillagers / 10;
/*     */     
/*  88 */     if (this.numIronGolems < i && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0) {
/*     */       
/*  90 */       Vec3d vec3d = findRandomSpawnPos(this.center, 2, 4, 2);
/*     */       
/*  92 */       if (vec3d != null) {
/*     */         
/*  94 */         EntityIronGolem entityirongolem = new EntityIronGolem(this.worldObj);
/*  95 */         entityirongolem.setPosition(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
/*  96 */         this.worldObj.spawnEntityInWorld((Entity)entityirongolem);
/*  97 */         this.numIronGolems++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Vec3d findRandomSpawnPos(BlockPos pos, int x, int y, int z) {
/* 104 */     for (int i = 0; i < 10; i++) {
/*     */       
/* 106 */       BlockPos blockpos = pos.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
/*     */       
/* 108 */       if (isBlockPosWithinSqVillageRadius(blockpos) && isAreaClearAround(new BlockPos(x, y, z), blockpos))
/*     */       {
/* 110 */         return new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAreaClearAround(BlockPos blockSize, BlockPos blockLocation) {
/* 122 */     if (!this.worldObj.getBlockState(blockLocation.down()).isFullyOpaque())
/*     */     {
/* 124 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 128 */     int i = blockLocation.getX() - blockSize.getX() / 2;
/* 129 */     int j = blockLocation.getZ() - blockSize.getZ() / 2;
/*     */     
/* 131 */     for (int k = i; k < i + blockSize.getX(); k++) {
/*     */       
/* 133 */       for (int l = blockLocation.getY(); l < blockLocation.getY() + blockSize.getY(); l++) {
/*     */         
/* 135 */         for (int i1 = j; i1 < j + blockSize.getZ(); i1++) {
/*     */           
/* 137 */           if (this.worldObj.getBlockState(new BlockPos(k, l, i1)).isNormalCube())
/*     */           {
/* 139 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateNumIronGolems() {
/* 151 */     List<EntityIronGolem> list = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, new AxisAlignedBB((this.center.getX() - this.villageRadius), (this.center.getY() - 4), (this.center.getZ() - this.villageRadius), (this.center.getX() + this.villageRadius), (this.center.getY() + 4), (this.center.getZ() + this.villageRadius)));
/* 152 */     this.numIronGolems = list.size();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateNumVillagers() {
/* 157 */     List<EntityVillager> list = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB((this.center.getX() - this.villageRadius), (this.center.getY() - 4), (this.center.getZ() - this.villageRadius), (this.center.getX() + this.villageRadius), (this.center.getY() + 4), (this.center.getZ() + this.villageRadius)));
/* 158 */     this.numVillagers = list.size();
/*     */     
/* 160 */     if (this.numVillagers == 0)
/*     */     {
/* 162 */       this.playerReputation.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getCenter() {
/* 168 */     return this.center;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVillageRadius() {
/* 173 */     return this.villageRadius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumVillageDoors() {
/* 182 */     return this.villageDoorInfoList.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTicksSinceLastDoorAdding() {
/* 187 */     return this.tickCounter - this.lastAddDoorTimestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumVillagers() {
/* 192 */     return this.numVillagers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockPosWithinSqVillageRadius(BlockPos pos) {
/* 201 */     return (this.center.distanceSq((Vec3i)pos) < (this.villageRadius * this.villageRadius));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<VillageDoorInfo> getVillageDoorInfoList() {
/* 206 */     return this.villageDoorInfoList;
/*     */   }
/*     */ 
/*     */   
/*     */   public VillageDoorInfo getNearestDoor(BlockPos pos) {
/* 211 */     VillageDoorInfo villagedoorinfo = null;
/* 212 */     int i = Integer.MAX_VALUE;
/*     */     
/* 214 */     for (VillageDoorInfo villagedoorinfo1 : this.villageDoorInfoList) {
/*     */       
/* 216 */       int j = villagedoorinfo1.getDistanceToDoorBlockSq(pos);
/*     */       
/* 218 */       if (j < i) {
/*     */         
/* 220 */         villagedoorinfo = villagedoorinfo1;
/* 221 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     return villagedoorinfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VillageDoorInfo getDoorInfo(BlockPos pos) {
/* 233 */     VillageDoorInfo villagedoorinfo = null;
/* 234 */     int i = Integer.MAX_VALUE;
/*     */     
/* 236 */     for (VillageDoorInfo villagedoorinfo1 : this.villageDoorInfoList) {
/*     */       
/* 238 */       int j = villagedoorinfo1.getDistanceToDoorBlockSq(pos);
/*     */       
/* 240 */       if (j > 256) {
/*     */         
/* 242 */         j *= 1000;
/*     */       }
/*     */       else {
/*     */         
/* 246 */         j = villagedoorinfo1.getDoorOpeningRestrictionCounter();
/*     */       } 
/*     */       
/* 249 */       if (j < i) {
/*     */         
/* 251 */         BlockPos blockpos = villagedoorinfo1.getDoorBlockPos();
/* 252 */         EnumFacing enumfacing = villagedoorinfo1.getInsideDirection();
/*     */         
/* 254 */         if (this.worldObj.getBlockState(blockpos.offset(enumfacing, 1)).getBlock().isPassable((IBlockAccess)this.worldObj, blockpos.offset(enumfacing, 1)) && this.worldObj.getBlockState(blockpos.offset(enumfacing, -1)).getBlock().isPassable((IBlockAccess)this.worldObj, blockpos.offset(enumfacing, -1)) && this.worldObj.getBlockState(blockpos.up().offset(enumfacing, 1)).getBlock().isPassable((IBlockAccess)this.worldObj, blockpos.up().offset(enumfacing, 1)) && this.worldObj.getBlockState(blockpos.up().offset(enumfacing, -1)).getBlock().isPassable((IBlockAccess)this.worldObj, blockpos.up().offset(enumfacing, -1))) {
/*     */           
/* 256 */           villagedoorinfo = villagedoorinfo1;
/* 257 */           i = j;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 262 */     return villagedoorinfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public VillageDoorInfo getExistedDoor(BlockPos doorBlock) {
/* 272 */     if (this.center.distanceSq((Vec3i)doorBlock) > (this.villageRadius * this.villageRadius))
/*     */     {
/* 274 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 278 */     for (VillageDoorInfo villagedoorinfo : this.villageDoorInfoList) {
/*     */       
/* 280 */       if (villagedoorinfo.getDoorBlockPos().getX() == doorBlock.getX() && villagedoorinfo.getDoorBlockPos().getZ() == doorBlock.getZ() && Math.abs(villagedoorinfo.getDoorBlockPos().getY() - doorBlock.getY()) <= 1)
/*     */       {
/* 282 */         return villagedoorinfo;
/*     */       }
/*     */     } 
/*     */     
/* 286 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addVillageDoorInfo(VillageDoorInfo doorInfo) {
/* 292 */     this.villageDoorInfoList.add(doorInfo);
/* 293 */     this.centerHelper = this.centerHelper.add((Vec3i)doorInfo.getDoorBlockPos());
/* 294 */     updateVillageRadiusAndCenter();
/* 295 */     this.lastAddDoorTimestamp = doorInfo.getInsidePosY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnnihilated() {
/* 303 */     return this.villageDoorInfoList.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOrRenewAgressor(EntityLivingBase entitylivingbaseIn) {
/* 308 */     for (VillageAggressor village$villageaggressor : this.villageAgressors) {
/*     */       
/* 310 */       if (village$villageaggressor.agressor == entitylivingbaseIn) {
/*     */         
/* 312 */         village$villageaggressor.agressionTime = this.tickCounter;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 317 */     this.villageAgressors.add(new VillageAggressor(entitylivingbaseIn, this.tickCounter));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityLivingBase findNearestVillageAggressor(EntityLivingBase entitylivingbaseIn) {
/* 323 */     double d0 = Double.MAX_VALUE;
/* 324 */     VillageAggressor village$villageaggressor = null;
/*     */     
/* 326 */     for (int i = 0; i < this.villageAgressors.size(); i++) {
/*     */       
/* 328 */       VillageAggressor village$villageaggressor1 = this.villageAgressors.get(i);
/* 329 */       double d1 = village$villageaggressor1.agressor.getDistanceSqToEntity((Entity)entitylivingbaseIn);
/*     */       
/* 331 */       if (d1 <= d0) {
/*     */         
/* 333 */         village$villageaggressor = village$villageaggressor1;
/* 334 */         d0 = d1;
/*     */       } 
/*     */     } 
/*     */     
/* 338 */     return (village$villageaggressor == null) ? null : village$villageaggressor.agressor;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPlayer getNearestTargetPlayer(EntityLivingBase villageDefender) {
/* 343 */     double d0 = Double.MAX_VALUE;
/* 344 */     EntityPlayer entityplayer = null;
/*     */     
/* 346 */     for (String s : this.playerReputation.keySet()) {
/*     */       
/* 348 */       if (isPlayerReputationTooLow(s)) {
/*     */         
/* 350 */         EntityPlayer entityplayer1 = this.worldObj.getPlayerEntityByName(s);
/*     */         
/* 352 */         if (entityplayer1 != null) {
/*     */           
/* 354 */           double d1 = entityplayer1.getDistanceSqToEntity((Entity)villageDefender);
/*     */           
/* 356 */           if (d1 <= d0) {
/*     */             
/* 358 */             entityplayer = entityplayer1;
/* 359 */             d0 = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 365 */     return entityplayer;
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeDeadAndOldAgressors() {
/* 370 */     Iterator<VillageAggressor> iterator = this.villageAgressors.iterator();
/*     */     
/* 372 */     while (iterator.hasNext()) {
/*     */       
/* 374 */       VillageAggressor village$villageaggressor = iterator.next();
/*     */       
/* 376 */       if (!village$villageaggressor.agressor.isEntityAlive() || Math.abs(this.tickCounter - village$villageaggressor.agressionTime) > 300)
/*     */       {
/* 378 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeDeadAndOutOfRangeDoors() {
/* 385 */     boolean flag = false;
/* 386 */     boolean flag1 = (this.worldObj.rand.nextInt(50) == 0);
/* 387 */     Iterator<VillageDoorInfo> iterator = this.villageDoorInfoList.iterator();
/*     */     
/* 389 */     while (iterator.hasNext()) {
/*     */       
/* 391 */       VillageDoorInfo villagedoorinfo = iterator.next();
/*     */       
/* 393 */       if (flag1)
/*     */       {
/* 395 */         villagedoorinfo.resetDoorOpeningRestrictionCounter();
/*     */       }
/*     */       
/* 398 */       if (!isWoodDoor(villagedoorinfo.getDoorBlockPos()) || Math.abs(this.tickCounter - villagedoorinfo.getInsidePosY()) > 1200) {
/*     */         
/* 400 */         this.centerHelper = this.centerHelper.subtract((Vec3i)villagedoorinfo.getDoorBlockPos());
/* 401 */         flag = true;
/* 402 */         villagedoorinfo.setIsDetachedFromVillageFlag(true);
/* 403 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 407 */     if (flag)
/*     */     {
/* 409 */       updateVillageRadiusAndCenter();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isWoodDoor(BlockPos pos) {
/* 415 */     IBlockState iblockstate = this.worldObj.getBlockState(pos);
/* 416 */     Block block = iblockstate.getBlock();
/*     */     
/* 418 */     if (block instanceof net.minecraft.block.BlockDoor)
/*     */     {
/* 420 */       return (iblockstate.getMaterial() == Material.WOOD);
/*     */     }
/*     */ 
/*     */     
/* 424 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateVillageRadiusAndCenter() {
/* 430 */     int i = this.villageDoorInfoList.size();
/*     */     
/* 432 */     if (i == 0) {
/*     */       
/* 434 */       this.center = BlockPos.ORIGIN;
/* 435 */       this.villageRadius = 0;
/*     */     }
/*     */     else {
/*     */       
/* 439 */       this.center = new BlockPos(this.centerHelper.getX() / i, this.centerHelper.getY() / i, this.centerHelper.getZ() / i);
/* 440 */       int j = 0;
/*     */       
/* 442 */       for (VillageDoorInfo villagedoorinfo : this.villageDoorInfoList)
/*     */       {
/* 444 */         j = Math.max(villagedoorinfo.getDistanceToDoorBlockSq(this.center), j);
/*     */       }
/*     */       
/* 447 */       this.villageRadius = Math.max(32, (int)Math.sqrt(j) + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPlayerReputation(String playerName) {
/* 456 */     Integer integer = this.playerReputation.get(playerName);
/* 457 */     return (integer == null) ? 0 : integer.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int modifyPlayerReputation(String playerName, int reputation) {
/* 466 */     int i = getPlayerReputation(playerName);
/* 467 */     int j = MathHelper.clamp(i + reputation, -30, 10);
/* 468 */     this.playerReputation.put(playerName, Integer.valueOf(j));
/* 469 */     return j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerReputationTooLow(String playerName) {
/* 477 */     return (getPlayerReputation(playerName) <= -15);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readVillageDataFromNBT(NBTTagCompound compound) {
/* 485 */     this.numVillagers = compound.getInteger("PopSize");
/* 486 */     this.villageRadius = compound.getInteger("Radius");
/* 487 */     this.numIronGolems = compound.getInteger("Golems");
/* 488 */     this.lastAddDoorTimestamp = compound.getInteger("Stable");
/* 489 */     this.tickCounter = compound.getInteger("Tick");
/* 490 */     this.noBreedTicks = compound.getInteger("MTick");
/* 491 */     this.center = new BlockPos(compound.getInteger("CX"), compound.getInteger("CY"), compound.getInteger("CZ"));
/* 492 */     this.centerHelper = new BlockPos(compound.getInteger("ACX"), compound.getInteger("ACY"), compound.getInteger("ACZ"));
/* 493 */     NBTTagList nbttaglist = compound.getTagList("Doors", 10);
/*     */     
/* 495 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 497 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 498 */       VillageDoorInfo villagedoorinfo = new VillageDoorInfo(new BlockPos(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Y"), nbttagcompound.getInteger("Z")), nbttagcompound.getInteger("IDX"), nbttagcompound.getInteger("IDZ"), nbttagcompound.getInteger("TS"));
/* 499 */       this.villageDoorInfoList.add(villagedoorinfo);
/*     */     } 
/*     */     
/* 502 */     NBTTagList nbttaglist1 = compound.getTagList("Players", 10);
/*     */     
/* 504 */     for (int j = 0; j < nbttaglist1.tagCount(); j++) {
/*     */       
/* 506 */       NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(j);
/*     */       
/* 508 */       if (nbttagcompound1.hasKey("UUID") && this.worldObj != null && this.worldObj.getMinecraftServer() != null) {
/*     */         
/* 510 */         PlayerProfileCache playerprofilecache = this.worldObj.getMinecraftServer().getPlayerProfileCache();
/* 511 */         GameProfile gameprofile = playerprofilecache.getProfileByUUID(UUID.fromString(nbttagcompound1.getString("UUID")));
/*     */         
/* 513 */         if (gameprofile != null)
/*     */         {
/* 515 */           this.playerReputation.put(gameprofile.getName(), Integer.valueOf(nbttagcompound1.getInteger("S")));
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 520 */         this.playerReputation.put(nbttagcompound1.getString("Name"), Integer.valueOf(nbttagcompound1.getInteger("S")));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeVillageDataToNBT(NBTTagCompound compound) {
/* 530 */     compound.setInteger("PopSize", this.numVillagers);
/* 531 */     compound.setInteger("Radius", this.villageRadius);
/* 532 */     compound.setInteger("Golems", this.numIronGolems);
/* 533 */     compound.setInteger("Stable", this.lastAddDoorTimestamp);
/* 534 */     compound.setInteger("Tick", this.tickCounter);
/* 535 */     compound.setInteger("MTick", this.noBreedTicks);
/* 536 */     compound.setInteger("CX", this.center.getX());
/* 537 */     compound.setInteger("CY", this.center.getY());
/* 538 */     compound.setInteger("CZ", this.center.getZ());
/* 539 */     compound.setInteger("ACX", this.centerHelper.getX());
/* 540 */     compound.setInteger("ACY", this.centerHelper.getY());
/* 541 */     compound.setInteger("ACZ", this.centerHelper.getZ());
/* 542 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 544 */     for (VillageDoorInfo villagedoorinfo : this.villageDoorInfoList) {
/*     */       
/* 546 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 547 */       nbttagcompound.setInteger("X", villagedoorinfo.getDoorBlockPos().getX());
/* 548 */       nbttagcompound.setInteger("Y", villagedoorinfo.getDoorBlockPos().getY());
/* 549 */       nbttagcompound.setInteger("Z", villagedoorinfo.getDoorBlockPos().getZ());
/* 550 */       nbttagcompound.setInteger("IDX", villagedoorinfo.getInsideOffsetX());
/* 551 */       nbttagcompound.setInteger("IDZ", villagedoorinfo.getInsideOffsetZ());
/* 552 */       nbttagcompound.setInteger("TS", villagedoorinfo.getInsidePosY());
/* 553 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 556 */     compound.setTag("Doors", (NBTBase)nbttaglist);
/* 557 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 559 */     for (String s : this.playerReputation.keySet()) {
/*     */       
/* 561 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 562 */       PlayerProfileCache playerprofilecache = this.worldObj.getMinecraftServer().getPlayerProfileCache();
/*     */ 
/*     */       
/*     */       try {
/* 566 */         GameProfile gameprofile = playerprofilecache.getGameProfileForUsername(s);
/*     */         
/* 568 */         if (gameprofile != null)
/*     */         {
/* 570 */           nbttagcompound1.setString("UUID", gameprofile.getId().toString());
/* 571 */           nbttagcompound1.setInteger("S", ((Integer)this.playerReputation.get(s)).intValue());
/* 572 */           nbttaglist1.appendTag((NBTBase)nbttagcompound1);
/*     */         }
/*     */       
/* 575 */       } catch (RuntimeException runtimeException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 581 */     compound.setTag("Players", (NBTBase)nbttaglist1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endMatingSeason() {
/* 589 */     this.noBreedTicks = this.tickCounter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMatingSeason() {
/* 597 */     return !(this.noBreedTicks != 0 && this.tickCounter - this.noBreedTicks < 3600);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultPlayerReputation(int defaultReputation) {
/* 602 */     for (String s : this.playerReputation.keySet())
/*     */     {
/* 604 */       modifyPlayerReputation(s, defaultReputation);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class VillageAggressor
/*     */   {
/*     */     public EntityLivingBase agressor;
/*     */     public int agressionTime;
/*     */     
/*     */     VillageAggressor(EntityLivingBase agressorIn, int agressionTimeIn) {
/* 615 */       this.agressor = agressorIn;
/* 616 */       this.agressionTime = agressionTimeIn;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\village\Village.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */