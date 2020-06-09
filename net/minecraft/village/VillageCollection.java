/*     */ package net.minecraft.village;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.storage.WorldSavedData;
/*     */ 
/*     */ public class VillageCollection extends WorldSavedData {
/*  22 */   private final List<BlockPos> villagerPositionsList = Lists.newArrayList(); private World worldObj;
/*  23 */   private final List<VillageDoorInfo> newDoors = Lists.newArrayList();
/*  24 */   private final List<Village> villageList = Lists.newArrayList();
/*     */   
/*     */   private int tickCounter;
/*     */   
/*     */   public VillageCollection(String name) {
/*  29 */     super(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public VillageCollection(World worldIn) {
/*  34 */     super(fileNameForProvider(worldIn.provider));
/*  35 */     this.worldObj = worldIn;
/*  36 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorldsForAll(World worldIn) {
/*  41 */     this.worldObj = worldIn;
/*     */     
/*  43 */     for (Village village : this.villageList)
/*     */     {
/*  45 */       village.setWorld(worldIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addToVillagerPositionList(BlockPos pos) {
/*  51 */     if (this.villagerPositionsList.size() <= 64)
/*     */     {
/*  53 */       if (!positionInList(pos))
/*     */       {
/*  55 */         this.villagerPositionsList.add(pos);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  65 */     this.tickCounter++;
/*     */     
/*  67 */     for (Village village : this.villageList)
/*     */     {
/*  69 */       village.tick(this.tickCounter);
/*     */     }
/*     */     
/*  72 */     removeAnnihilatedVillages();
/*  73 */     dropOldestVillagerPosition();
/*  74 */     addNewDoorsToVillageOrCreateVillage();
/*     */     
/*  76 */     if (this.tickCounter % 400 == 0)
/*     */     {
/*  78 */       markDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeAnnihilatedVillages() {
/*  84 */     Iterator<Village> iterator = this.villageList.iterator();
/*     */     
/*  86 */     while (iterator.hasNext()) {
/*     */       
/*  88 */       Village village = iterator.next();
/*     */       
/*  90 */       if (village.isAnnihilated()) {
/*     */         
/*  92 */         iterator.remove();
/*  93 */         markDirty();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Village> getVillageList() {
/* 100 */     return this.villageList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Village getNearestVillage(BlockPos doorBlock, int radius) {
/* 105 */     Village village = null;
/* 106 */     double d0 = 3.4028234663852886E38D;
/*     */     
/* 108 */     for (Village village1 : this.villageList) {
/*     */       
/* 110 */       double d1 = village1.getCenter().distanceSq((Vec3i)doorBlock);
/*     */       
/* 112 */       if (d1 < d0) {
/*     */         
/* 114 */         float f = (radius + village1.getVillageRadius());
/*     */         
/* 116 */         if (d1 <= (f * f)) {
/*     */           
/* 118 */           village = village1;
/* 119 */           d0 = d1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     return village;
/*     */   }
/*     */ 
/*     */   
/*     */   private void dropOldestVillagerPosition() {
/* 129 */     if (!this.villagerPositionsList.isEmpty())
/*     */     {
/* 131 */       addDoorsAround(this.villagerPositionsList.remove(0));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void addNewDoorsToVillageOrCreateVillage() {
/* 137 */     for (int i = 0; i < this.newDoors.size(); i++) {
/*     */       
/* 139 */       VillageDoorInfo villagedoorinfo = this.newDoors.get(i);
/* 140 */       Village village = getNearestVillage(villagedoorinfo.getDoorBlockPos(), 32);
/*     */       
/* 142 */       if (village == null) {
/*     */         
/* 144 */         village = new Village(this.worldObj);
/* 145 */         this.villageList.add(village);
/* 146 */         markDirty();
/*     */       } 
/*     */       
/* 149 */       village.addVillageDoorInfo(villagedoorinfo);
/*     */     } 
/*     */     
/* 152 */     this.newDoors.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private void addDoorsAround(BlockPos central) {
/* 157 */     int i = 16;
/* 158 */     int j = 4;
/* 159 */     int k = 16;
/*     */     
/* 161 */     for (int l = -16; l < 16; l++) {
/*     */       
/* 163 */       for (int i1 = -4; i1 < 4; i1++) {
/*     */         
/* 165 */         for (int j1 = -16; j1 < 16; j1++) {
/*     */           
/* 167 */           BlockPos blockpos = central.add(l, i1, j1);
/*     */           
/* 169 */           if (isWoodDoor(blockpos)) {
/*     */             
/* 171 */             VillageDoorInfo villagedoorinfo = checkDoorExistence(blockpos);
/*     */             
/* 173 */             if (villagedoorinfo == null) {
/*     */               
/* 175 */               addToNewDoorsList(blockpos);
/*     */             }
/*     */             else {
/*     */               
/* 179 */               villagedoorinfo.setLastActivityTimestamp(this.tickCounter);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private VillageDoorInfo checkDoorExistence(BlockPos doorBlock) {
/* 194 */     for (VillageDoorInfo villagedoorinfo : this.newDoors) {
/*     */       
/* 196 */       if (villagedoorinfo.getDoorBlockPos().getX() == doorBlock.getX() && villagedoorinfo.getDoorBlockPos().getZ() == doorBlock.getZ() && Math.abs(villagedoorinfo.getDoorBlockPos().getY() - doorBlock.getY()) <= 1)
/*     */       {
/* 198 */         return villagedoorinfo;
/*     */       }
/*     */     } 
/*     */     
/* 202 */     for (Village village : this.villageList) {
/*     */       
/* 204 */       VillageDoorInfo villagedoorinfo1 = village.getExistedDoor(doorBlock);
/*     */       
/* 206 */       if (villagedoorinfo1 != null)
/*     */       {
/* 208 */         return villagedoorinfo1;
/*     */       }
/*     */     } 
/*     */     
/* 212 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToNewDoorsList(BlockPos doorBlock) {
/* 217 */     EnumFacing enumfacing = BlockDoor.getFacing((IBlockAccess)this.worldObj, doorBlock);
/* 218 */     EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 219 */     int i = countBlocksCanSeeSky(doorBlock, enumfacing, 5);
/* 220 */     int j = countBlocksCanSeeSky(doorBlock, enumfacing1, i + 1);
/*     */     
/* 222 */     if (i != j)
/*     */     {
/* 224 */       this.newDoors.add(new VillageDoorInfo(doorBlock, (i < j) ? enumfacing : enumfacing1, this.tickCounter));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int countBlocksCanSeeSky(BlockPos centerPos, EnumFacing direction, int limitation) {
/* 233 */     int i = 0;
/*     */     
/* 235 */     for (int j = 1; j <= 5; j++) {
/*     */       
/* 237 */       if (this.worldObj.canSeeSky(centerPos.offset(direction, j))) {
/*     */         
/* 239 */         i++;
/*     */         
/* 241 */         if (i >= limitation)
/*     */         {
/* 243 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean positionInList(BlockPos pos) {
/* 253 */     for (BlockPos blockpos : this.villagerPositionsList) {
/*     */       
/* 255 */       if (blockpos.equals(pos))
/*     */       {
/* 257 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 261 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isWoodDoor(BlockPos doorPos) {
/* 266 */     IBlockState iblockstate = this.worldObj.getBlockState(doorPos);
/* 267 */     Block block = iblockstate.getBlock();
/*     */     
/* 269 */     if (block instanceof BlockDoor)
/*     */     {
/* 271 */       return (iblockstate.getMaterial() == Material.WOOD);
/*     */     }
/*     */ 
/*     */     
/* 275 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 284 */     this.tickCounter = nbt.getInteger("Tick");
/* 285 */     NBTTagList nbttaglist = nbt.getTagList("Villages", 10);
/*     */     
/* 287 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */       
/* 289 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 290 */       Village village = new Village();
/* 291 */       village.readVillageDataFromNBT(nbttagcompound);
/* 292 */       this.villageList.add(village);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 298 */     compound.setInteger("Tick", this.tickCounter);
/* 299 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 301 */     for (Village village : this.villageList) {
/*     */       
/* 303 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 304 */       village.writeVillageDataToNBT(nbttagcompound);
/* 305 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 308 */     compound.setTag("Villages", (NBTBase)nbttaglist);
/* 309 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fileNameForProvider(WorldProvider provider) {
/* 314 */     return "villages" + provider.getDimensionType().getSuffix();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\village\VillageCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */