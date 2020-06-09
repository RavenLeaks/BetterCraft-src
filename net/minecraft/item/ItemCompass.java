/*    */ package net.minecraft.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityItemFrame;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemCompass
/*    */   extends Item
/*    */ {
/*    */   public ItemCompass() {
/* 16 */     addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter() {
/*    */           double rotation;
/*    */           double rota;
/*    */           long lastUpdateTick;
/*    */           
/*    */           public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
/*    */             double d0;
/* 23 */             if (entityIn == null && !stack.isOnItemFrame())
/*    */             {
/* 25 */               return 0.0F;
/*    */             }
/*    */ 
/*    */             
/* 29 */             boolean flag = (entityIn != null);
/* 30 */             Entity entity = flag ? (Entity)entityIn : (Entity)stack.getItemFrame();
/*    */             
/* 32 */             if (worldIn == null)
/*    */             {
/* 34 */               worldIn = entity.world;
/*    */             }
/*    */ 
/*    */ 
/*    */             
/* 39 */             if (worldIn.provider.isSurfaceWorld()) {
/*    */               
/* 41 */               double d1 = flag ? entity.rotationYaw : getFrameRotation((EntityItemFrame)entity);
/* 42 */               d1 = MathHelper.func_191273_b(d1 / 360.0D, 1.0D);
/* 43 */               double d2 = getSpawnToAngle(worldIn, entity) / 6.283185307179586D;
/* 44 */               d0 = 0.5D - d1 - 0.25D - d2;
/*    */             }
/*    */             else {
/*    */               
/* 48 */               d0 = Math.random();
/*    */             } 
/*    */             
/* 51 */             if (flag)
/*    */             {
/* 53 */               d0 = wobble(worldIn, d0);
/*    */             }
/*    */             
/* 56 */             return MathHelper.positiveModulo((float)d0, 1.0F);
/*    */           }
/*    */ 
/*    */           
/*    */           private double wobble(World worldIn, double p_185093_2_) {
/* 61 */             if (worldIn.getTotalWorldTime() != this.lastUpdateTick) {
/*    */               
/* 63 */               this.lastUpdateTick = worldIn.getTotalWorldTime();
/* 64 */               double d0 = p_185093_2_ - this.rotation;
/* 65 */               d0 = MathHelper.func_191273_b(d0 + 0.5D, 1.0D) - 0.5D;
/* 66 */               this.rota += d0 * 0.1D;
/* 67 */               this.rota *= 0.8D;
/* 68 */               this.rotation = MathHelper.func_191273_b(this.rotation + this.rota, 1.0D);
/*    */             } 
/*    */             
/* 71 */             return this.rotation;
/*    */           }
/*    */           
/*    */           private double getFrameRotation(EntityItemFrame p_185094_1_) {
/* 75 */             return MathHelper.clampAngle(180 + p_185094_1_.facingDirection.getHorizontalIndex() * 90);
/*    */           }
/*    */           
/*    */           private double getSpawnToAngle(World p_185092_1_, Entity p_185092_2_) {
/* 79 */             BlockPos blockpos = p_185092_1_.getSpawnPoint();
/* 80 */             return Math.atan2(blockpos.getZ() - p_185092_2_.posZ, blockpos.getX() - p_185092_2_.posX);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemCompass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */