/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ public class ActiveRenderInfo
/*     */ {
/*  18 */   private static final IntBuffer VIEWPORT = GLAllocation.createDirectIntBuffer(16);
/*     */ 
/*     */   
/*  21 */   private static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
/*     */ 
/*     */   
/*  24 */   private static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
/*     */ 
/*     */   
/*  27 */   private static final FloatBuffer OBJECTCOORDS = GLAllocation.createDirectFloatBuffer(3);
/*  28 */   private static Vec3d position = new Vec3d(0.0D, 0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */   
/*     */   private static float rotationX;
/*     */ 
/*     */ 
/*     */   
/*     */   private static float rotationXZ;
/*     */ 
/*     */ 
/*     */   
/*     */   private static float rotationZ;
/*     */ 
/*     */ 
/*     */   
/*     */   private static float rotationYZ;
/*     */ 
/*     */ 
/*     */   
/*     */   private static float rotationXY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateRenderInfo(EntityPlayer entityplayerIn, boolean p_74583_1_) {
/*  54 */     GlStateManager.getFloat(2982, MODELVIEW);
/*  55 */     GlStateManager.getFloat(2983, PROJECTION);
/*  56 */     GlStateManager.glGetInteger(2978, VIEWPORT);
/*  57 */     float f = ((VIEWPORT.get(0) + VIEWPORT.get(2)) / 2);
/*  58 */     float f1 = ((VIEWPORT.get(1) + VIEWPORT.get(3)) / 2);
/*  59 */     GLU.gluUnProject(f, f1, 0.0F, MODELVIEW, PROJECTION, VIEWPORT, OBJECTCOORDS);
/*  60 */     position = new Vec3d(OBJECTCOORDS.get(0), OBJECTCOORDS.get(1), OBJECTCOORDS.get(2));
/*  61 */     int i = p_74583_1_ ? 1 : 0;
/*  62 */     float f2 = entityplayerIn.rotationPitch;
/*  63 */     float f3 = entityplayerIn.rotationYaw;
/*  64 */     rotationX = MathHelper.cos(f3 * 0.017453292F) * (1 - i * 2);
/*  65 */     rotationZ = MathHelper.sin(f3 * 0.017453292F) * (1 - i * 2);
/*  66 */     rotationYZ = -rotationZ * MathHelper.sin(f2 * 0.017453292F) * (1 - i * 2);
/*  67 */     rotationXY = rotationX * MathHelper.sin(f2 * 0.017453292F) * (1 - i * 2);
/*  68 */     rotationXZ = MathHelper.cos(f2 * 0.017453292F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d projectViewFromEntity(Entity entityIn, double p_178806_1_) {
/*  73 */     double d0 = entityIn.prevPosX + (entityIn.posX - entityIn.prevPosX) * p_178806_1_;
/*  74 */     double d1 = entityIn.prevPosY + (entityIn.posY - entityIn.prevPosY) * p_178806_1_;
/*  75 */     double d2 = entityIn.prevPosZ + (entityIn.posZ - entityIn.prevPosZ) * p_178806_1_;
/*  76 */     double d3 = d0 + position.xCoord;
/*  77 */     double d4 = d1 + position.yCoord;
/*  78 */     double d5 = d2 + position.zCoord;
/*  79 */     return new Vec3d(d3, d4, d5);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBlockState getBlockStateAtEntityViewpoint(World worldIn, Entity entityIn, float p_186703_2_) {
/*  84 */     Vec3d vec3d = projectViewFromEntity(entityIn, p_186703_2_);
/*  85 */     BlockPos blockpos = new BlockPos(vec3d);
/*  86 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */     
/*  88 */     if (iblockstate.getMaterial().isLiquid()) {
/*     */       
/*  90 */       float f = 0.0F;
/*     */       
/*  92 */       if (iblockstate.getBlock() instanceof BlockLiquid)
/*     */       {
/*  94 */         f = BlockLiquid.getLiquidHeightPercent(((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue()) - 0.11111111F;
/*     */       }
/*     */       
/*  97 */       float f1 = (blockpos.getY() + 1) - f;
/*     */       
/*  99 */       if (vec3d.yCoord >= f1)
/*     */       {
/* 101 */         iblockstate = worldIn.getBlockState(blockpos.up());
/*     */       }
/*     */     } 
/*     */     
/* 105 */     return iblockstate;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getRotationX() {
/* 110 */     return rotationX;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getRotationXZ() {
/* 115 */     return rotationXZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getRotationZ() {
/* 120 */     return rotationZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getRotationYZ() {
/* 125 */     return rotationYZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getRotationXY() {
/* 130 */     return rotationXY;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\ActiveRenderInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */