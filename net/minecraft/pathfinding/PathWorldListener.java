/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IWorldEventListener;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class PathWorldListener implements IWorldEventListener {
/*  19 */   private final List<PathNavigate> navigations = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
/*  23 */     if (didBlockChange(worldIn, pos, oldState, newState)) {
/*     */       
/*  25 */       int i = 0;
/*     */       
/*  27 */       for (int j = this.navigations.size(); i < j; i++) {
/*     */         
/*  29 */         PathNavigate pathnavigate = this.navigations.get(i);
/*     */         
/*  31 */         if (pathnavigate != null && !pathnavigate.canUpdatePathOnTimeout()) {
/*     */           
/*  33 */           Path path = pathnavigate.getPath();
/*     */           
/*  35 */           if (path != null && !path.isFinished() && path.getCurrentPathLength() != 0) {
/*     */             
/*  37 */             PathPoint pathpoint = pathnavigate.currentPath.getFinalPathPoint();
/*  38 */             double d0 = pos.distanceSq((pathpoint.xCoord + pathnavigate.theEntity.posX) / 2.0D, (pathpoint.yCoord + pathnavigate.theEntity.posY) / 2.0D, (pathpoint.zCoord + pathnavigate.theEntity.posZ) / 2.0D);
/*  39 */             int k = (path.getCurrentPathLength() - path.getCurrentPathIndex()) * (path.getCurrentPathLength() - path.getCurrentPathIndex());
/*     */             
/*  41 */             if (d0 < k)
/*     */             {
/*  43 */               pathnavigate.updatePath();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean didBlockChange(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState) {
/*  53 */     AxisAlignedBB axisalignedbb = oldState.getCollisionBoundingBox((IBlockAccess)worldIn, pos);
/*  54 */     AxisAlignedBB axisalignedbb1 = newState.getCollisionBoundingBox((IBlockAccess)worldIn, pos);
/*  55 */     return (axisalignedbb != axisalignedbb1 && (axisalignedbb == null || !axisalignedbb.equals(axisalignedbb1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyLightSet(BlockPos pos) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSoundToAllNearExcept(@Nullable EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_190570_a(int p_190570_1_, boolean p_190570_2_, boolean p_190570_3_, double p_190570_4_, double p_190570_6_, double p_190570_8_, double p_190570_10_, double p_190570_12_, double p_190570_14_, int... p_190570_16_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityAdded(Entity entityIn) {
/*  87 */     if (entityIn instanceof EntityLiving)
/*     */     {
/*  89 */       this.navigations.add(((EntityLiving)entityIn).getNavigator());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemoved(Entity entityIn) {
/*  99 */     if (entityIn instanceof EntityLiving)
/*     */     {
/* 101 */       this.navigations.remove(((EntityLiving)entityIn).getNavigator());
/*     */     }
/*     */   }
/*     */   
/*     */   public void playRecord(SoundEvent soundIn, BlockPos pos) {}
/*     */   
/*     */   public void broadcastSound(int soundID, BlockPos pos, int data) {}
/*     */   
/*     */   public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) {}
/*     */   
/*     */   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\PathWorldListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */