/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import javax.vecmath.Matrix4f;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.common.model.IModelPart;
/*     */ import net.minecraftforge.common.model.IModelState;
/*     */ import net.minecraftforge.common.model.ITransformation;
/*     */ import net.minecraftforge.common.model.TRSRTransformation;
/*     */ import optifine.Reflector;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ public enum ModelRotation
/*     */   implements IModelState, ITransformation
/*     */ {
/*  20 */   X0_Y0(0, 0),
/*  21 */   X0_Y90(0, 90),
/*  22 */   X0_Y180(0, 180),
/*  23 */   X0_Y270(0, 270),
/*  24 */   X90_Y0(90, 0),
/*  25 */   X90_Y90(90, 90),
/*  26 */   X90_Y180(90, 180),
/*  27 */   X90_Y270(90, 270),
/*  28 */   X180_Y0(180, 0),
/*  29 */   X180_Y90(180, 90),
/*  30 */   X180_Y180(180, 180),
/*  31 */   X180_Y270(180, 270),
/*  32 */   X270_Y0(270, 0),
/*  33 */   X270_Y90(270, 90),
/*  34 */   X270_Y180(270, 180),
/*  35 */   X270_Y270(270, 270);
/*     */   static {
/*  37 */     MAP_ROTATIONS = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     byte b;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ModelRotation[] arrayOfModelRotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     for (i = (arrayOfModelRotation = values()).length, b = 0; b < i; ) { ModelRotation modelrotation = arrayOfModelRotation[b];
/*     */       
/* 140 */       MAP_ROTATIONS.put(Integer.valueOf(modelrotation.combinedXY), modelrotation);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   private static final Map<Integer, ModelRotation> MAP_ROTATIONS;
/*     */   private final int combinedXY;
/*     */   private final Matrix4f matrix4d;
/*     */   private final int quartersX;
/*     */   private final int quartersY;
/*     */   
/*     */   private static int combineXY(int p_177521_0_, int p_177521_1_) {
/*     */     return p_177521_0_ * 360 + p_177521_1_;
/*     */   }
/*     */   
/*     */   ModelRotation(int x, int y) {
/*     */     this.combinedXY = combineXY(x, y);
/*     */     this.matrix4d = new Matrix4f();
/*     */     Matrix4f matrix4f = new Matrix4f();
/*     */     matrix4f.setIdentity();
/*     */     Matrix4f.rotate(-x * 0.017453292F, new Vector3f(1.0F, 0.0F, 0.0F), matrix4f, matrix4f);
/*     */     this.quartersX = MathHelper.abs(x / 90);
/*     */     Matrix4f matrix4f1 = new Matrix4f();
/*     */     matrix4f1.setIdentity();
/*     */     Matrix4f.rotate(-y * 0.017453292F, new Vector3f(0.0F, 1.0F, 0.0F), matrix4f1, matrix4f1);
/*     */     this.quartersY = MathHelper.abs(y / 90);
/*     */     Matrix4f.mul(matrix4f1, matrix4f, this.matrix4d);
/*     */   }
/*     */   
/*     */   public Matrix4f getMatrix4d() {
/*     */     return this.matrix4d;
/*     */   }
/*     */   
/*     */   public EnumFacing rotateFace(EnumFacing facing) {
/*     */     EnumFacing enumfacing = facing;
/*     */     for (int i = 0; i < this.quartersX; i++)
/*     */       enumfacing = enumfacing.rotateAround(EnumFacing.Axis.X); 
/*     */     if (enumfacing.getAxis() != EnumFacing.Axis.Y)
/*     */       for (int j = 0; j < this.quartersY; j++)
/*     */         enumfacing = enumfacing.rotateAround(EnumFacing.Axis.Y);  
/*     */     return enumfacing;
/*     */   }
/*     */   
/*     */   public int rotateVertex(EnumFacing facing, int vertexIndex) {
/*     */     int i = vertexIndex;
/*     */     if (facing.getAxis() == EnumFacing.Axis.X)
/*     */       i = (vertexIndex + this.quartersX) % 4; 
/*     */     EnumFacing enumfacing = facing;
/*     */     for (int j = 0; j < this.quartersX; j++)
/*     */       enumfacing = enumfacing.rotateAround(EnumFacing.Axis.X); 
/*     */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */       i = (i + this.quartersY) % 4; 
/*     */     return i;
/*     */   }
/*     */   
/*     */   public static ModelRotation getModelRotation(int x, int y) {
/*     */     return MAP_ROTATIONS.get(Integer.valueOf(combineXY(MathHelper.normalizeAngle(x, 360), MathHelper.normalizeAngle(y, 360))));
/*     */   }
/*     */   
/*     */   public Optional<TRSRTransformation> apply(Optional<? extends IModelPart> p_apply_1_) {
/*     */     return (Optional<TRSRTransformation>)Reflector.call(Reflector.ForgeHooksClient_applyTransform, new Object[] { getMatrix(), p_apply_1_ });
/*     */   }
/*     */   
/*     */   public Matrix4f getMatrix() {
/*     */     return Reflector.ForgeHooksClient_getMatrix.exists() ? (Matrix4f)Reflector.call(Reflector.ForgeHooksClient_getMatrix, new Object[] { this }) : new Matrix4f();
/*     */   }
/*     */   
/*     */   public EnumFacing rotate(EnumFacing p_rotate_1_) {
/*     */     return rotateFace(p_rotate_1_);
/*     */   }
/*     */   
/*     */   public int rotate(EnumFacing p_rotate_1_, int p_rotate_2_) {
/*     */     return rotateVertex(p_rotate_1_, p_rotate_2_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\ModelRotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */