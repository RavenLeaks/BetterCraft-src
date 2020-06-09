/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataFixer;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.IFixType;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartMobSpawner
/*     */   extends EntityMinecart {
/*  21 */   private final MobSpawnerBaseLogic mobSpawnerLogic = new MobSpawnerBaseLogic()
/*     */     {
/*     */       public void broadcastEvent(int id)
/*     */       {
/*  25 */         EntityMinecartMobSpawner.this.world.setEntityState(EntityMinecartMobSpawner.this, (byte)id);
/*     */       }
/*     */       
/*     */       public World getSpawnerWorld() {
/*  29 */         return EntityMinecartMobSpawner.this.world;
/*     */       }
/*     */       
/*     */       public BlockPos getSpawnerPosition() {
/*  33 */         return new BlockPos(EntityMinecartMobSpawner.this);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public EntityMinecartMobSpawner(World worldIn) {
/*  39 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecartMobSpawner(World worldIn, double x, double y, double z) {
/*  44 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesMinecartMobSpawner(DataFixer fixer) {
/*  49 */     registerFixesMinecart(fixer, EntityMinecartMobSpawner.class);
/*  50 */     fixer.registerWalker(FixTypes.ENTITY, new IDataWalker()
/*     */         {
/*     */           public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
/*     */           {
/*  54 */             String s = compound.getString("id");
/*     */             
/*  56 */             if (EntityList.func_191306_a(EntityMinecartMobSpawner.class).equals(new ResourceLocation(s))) {
/*     */               
/*  58 */               compound.setString("id", TileEntity.func_190559_a(TileEntityMobSpawner.class).toString());
/*  59 */               fixer.process((IFixType)FixTypes.BLOCK_ENTITY, compound, versionIn);
/*  60 */               compound.setString("id", s);
/*     */             } 
/*     */             
/*  63 */             return compound;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecart.Type getType() {
/*  70 */     return EntityMinecart.Type.SPAWNER;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/*  75 */     return Blocks.MOB_SPAWNER.getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/*  83 */     super.readEntityFromNBT(compound);
/*  84 */     this.mobSpawnerLogic.readFromNBT(compound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/*  92 */     super.writeEntityToNBT(compound);
/*  93 */     this.mobSpawnerLogic.writeToNBT(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/*  98 */     this.mobSpawnerLogic.setDelayToMin(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 106 */     super.onUpdate();
/* 107 */     this.mobSpawnerLogic.updateSpawner();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityMinecartMobSpawner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */