/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ 
/*    */ public class EntityAIWanderAvoidWaterFlying
/*    */   extends EntityAIWanderAvoidWater {
/*    */   public EntityAIWanderAvoidWaterFlying(EntityCreature p_i47413_1_, double p_i47413_2_) {
/* 18 */     super(p_i47413_1_, p_i47413_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Vec3d func_190864_f() {
/* 24 */     Vec3d vec3d = null;
/*    */     
/* 26 */     if (this.entity.isInWater() || this.entity.func_191953_am())
/*    */     {
/* 28 */       vec3d = RandomPositionGenerator.func_191377_b(this.entity, 15, 15);
/*    */     }
/*    */     
/* 31 */     if (this.entity.getRNG().nextFloat() >= this.field_190865_h)
/*    */     {
/* 33 */       vec3d = func_192385_j();
/*    */     }
/*    */     
/* 36 */     return (vec3d == null) ? super.func_190864_f() : vec3d;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private Vec3d func_192385_j() {
/* 42 */     BlockPos blockpos1, blockpos = new BlockPos((Entity)this.entity);
/* 43 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/* 44 */     BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/* 45 */     Iterable<BlockPos.MutableBlockPos> iterable = BlockPos.MutableBlockPos.func_191531_b(MathHelper.floor(this.entity.posX - 3.0D), MathHelper.floor(this.entity.posY - 6.0D), MathHelper.floor(this.entity.posZ - 3.0D), MathHelper.floor(this.entity.posX + 3.0D), MathHelper.floor(this.entity.posY + 6.0D), MathHelper.floor(this.entity.posZ + 3.0D));
/* 46 */     Iterator<BlockPos.MutableBlockPos> iterator = iterable.iterator();
/*    */ 
/*    */ 
/*    */     
/*    */     while (true) {
/* 51 */       if (!iterator.hasNext())
/*    */       {
/* 53 */         return null;
/*    */       }
/*    */       
/* 56 */       blockpos1 = (BlockPos)iterator.next();
/*    */       
/* 58 */       if (!blockpos.equals(blockpos1)) {
/*    */         
/* 60 */         Block block = this.entity.world.getBlockState((BlockPos)blockpos$mutableblockpos1.setPos((Vec3i)blockpos1).move(EnumFacing.DOWN)).getBlock();
/* 61 */         boolean flag = !(!(block instanceof net.minecraft.block.BlockLeaves) && block != Blocks.LOG && block != Blocks.LOG2);
/*    */         
/* 63 */         if (flag && this.entity.world.isAirBlock(blockpos1) && this.entity.world.isAirBlock((BlockPos)blockpos$mutableblockpos.setPos((Vec3i)blockpos1).move(EnumFacing.UP))) {
/*    */           break;
/*    */         }
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 70 */     return new Vec3d(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIWanderAvoidWaterFlying.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */