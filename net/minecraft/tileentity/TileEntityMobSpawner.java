/*     */ package net.minecraft.tileentity;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.WeightedSpawnerEntity;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataFixer;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.IFixType;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntityMobSpawner extends TileEntity implements ITickable {
/*  21 */   private final MobSpawnerBaseLogic spawnerLogic = new MobSpawnerBaseLogic()
/*     */     {
/*     */       public void broadcastEvent(int id)
/*     */       {
/*  25 */         TileEntityMobSpawner.this.world.addBlockEvent(TileEntityMobSpawner.this.pos, Blocks.MOB_SPAWNER, id, 0);
/*     */       }
/*     */       
/*     */       public World getSpawnerWorld() {
/*  29 */         return TileEntityMobSpawner.this.world;
/*     */       }
/*     */       
/*     */       public BlockPos getSpawnerPosition() {
/*  33 */         return TileEntityMobSpawner.this.pos;
/*     */       }
/*     */       
/*     */       public void setNextSpawnData(WeightedSpawnerEntity p_184993_1_) {
/*  37 */         super.setNextSpawnData(p_184993_1_);
/*     */         
/*  39 */         if (getSpawnerWorld() != null) {
/*     */           
/*  41 */           IBlockState iblockstate = getSpawnerWorld().getBlockState(getSpawnerPosition());
/*  42 */           getSpawnerWorld().notifyBlockUpdate(TileEntityMobSpawner.this.pos, iblockstate, iblockstate, 4);
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public static void registerFixesMobSpawner(DataFixer fixer) {
/*  49 */     fixer.registerWalker(FixTypes.BLOCK_ENTITY, new IDataWalker()
/*     */         {
/*     */           public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
/*     */           {
/*  53 */             if (TileEntity.func_190559_a((Class)TileEntityMobSpawner.class).equals(new ResourceLocation(compound.getString("id")))) {
/*     */               
/*  55 */               if (compound.hasKey("SpawnPotentials", 9)) {
/*     */                 
/*  57 */                 NBTTagList nbttaglist = compound.getTagList("SpawnPotentials", 10);
/*     */                 
/*  59 */                 for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */                   
/*  61 */                   NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  62 */                   nbttagcompound.setTag("Entity", (NBTBase)fixer.process((IFixType)FixTypes.ENTITY, nbttagcompound.getCompoundTag("Entity"), versionIn));
/*     */                 } 
/*     */               } 
/*     */               
/*  66 */               compound.setTag("SpawnData", (NBTBase)fixer.process((IFixType)FixTypes.ENTITY, compound.getCompoundTag("SpawnData"), versionIn));
/*     */             } 
/*     */             
/*  69 */             return compound;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  76 */     super.readFromNBT(compound);
/*  77 */     this.spawnerLogic.readFromNBT(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  82 */     super.writeToNBT(compound);
/*  83 */     this.spawnerLogic.writeToNBT(compound);
/*  84 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  92 */     this.spawnerLogic.updateSpawner();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SPacketUpdateTileEntity getUpdatePacket() {
/*  98 */     return new SPacketUpdateTileEntity(this.pos, 1, getUpdateTag());
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getUpdateTag() {
/* 103 */     NBTTagCompound nbttagcompound = writeToNBT(new NBTTagCompound());
/* 104 */     nbttagcompound.removeTag("SpawnPotentials");
/* 105 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 110 */     return this.spawnerLogic.setDelayToMin(id) ? true : super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onlyOpsCanSetNbt() {
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public MobSpawnerBaseLogic getSpawnerBaseLogic() {
/* 120 */     return this.spawnerLogic;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityMobSpawner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */