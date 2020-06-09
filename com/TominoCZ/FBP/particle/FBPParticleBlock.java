/*     */ package com.TominoCZ.FBP.particle;
/*     */ 
/*     */ import com.TominoCZ.FBP.FBP;
/*     */ import com.TominoCZ.FBP.util.FBPRenderUtil;
/*     */ import com.TominoCZ.FBP.vector.FBPVector3d;
/*     */ import javax.vecmath.Vector2d;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.BlockModelRenderer;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.Vector3d;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class FBPParticleBlock
/*     */   extends Particle
/*     */ {
/*     */   public BlockPos pos;
/*     */   Block block;
/*     */   IBlockState blockState;
/*     */   BlockModelRenderer mr;
/*     */   IBakedModel modelPrefab;
/*     */   Minecraft mc;
/*     */   EnumFacing facing;
/*     */   FBPVector3d prevRot;
/*     */   FBPVector3d rot;
/*     */   long textureSeed;
/*     */   float startingHeight;
/*     */   float startingAngle;
/*  58 */   float step = 0.00275F;
/*     */   
/*     */   float height;
/*     */   
/*     */   float prevHeight;
/*     */   
/*     */   float smoothHeight;
/*     */   boolean lookingUp;
/*     */   boolean spawned = false;
/*  67 */   long tick = -1L;
/*     */ 
/*     */   
/*     */   boolean blockSet = false;
/*     */   
/*     */   TileEntity tileEntity;
/*     */ 
/*     */   
/*     */   public FBPParticleBlock(World worldIn, double posXIn, double posYIn, double posZIn, IBlockState state, long rand) {
/*  76 */     super(worldIn, posXIn, posYIn, posZIn);
/*     */     
/*  78 */     this.pos = new BlockPos(posXIn, posYIn, posZIn);
/*     */     
/*  80 */     this.mc = Minecraft.getMinecraft();
/*     */     
/*  82 */     this.facing = this.mc.player.getHorizontalFacing();
/*     */     
/*  84 */     this.lookingUp = (Float.valueOf(MathHelper.wrapDegrees(this.mc.player.rotationPitch)).floatValue() <= 0.0F);
/*     */     
/*  86 */     this.prevHeight = this.height = this.startingHeight = (float)FBP.random.nextDouble(0.065D, 0.115D);
/*  87 */     this.startingAngle = (float)FBP.random.nextDouble(0.03125D, 0.0635D);
/*     */     
/*  89 */     this.prevRot = new FBPVector3d();
/*  90 */     this.rot = new FBPVector3d();
/*     */     
/*  92 */     switch (this.facing) {
/*     */       
/*     */       case EAST:
/*  95 */         this.rot.z = -this.startingAngle;
/*  96 */         this.rot.x = -this.startingAngle;
/*     */         break;
/*     */       case NORTH:
/*  99 */         this.rot.x = -this.startingAngle;
/* 100 */         this.rot.z = this.startingAngle;
/*     */         break;
/*     */       case SOUTH:
/* 103 */         this.rot.x = this.startingAngle;
/* 104 */         this.rot.z = -this.startingAngle;
/*     */         break;
/*     */       case WEST:
/* 107 */         this.rot.z = this.startingAngle;
/* 108 */         this.rot.x = this.startingAngle;
/*     */         break;
/*     */     } 
/*     */     
/* 112 */     this.textureSeed = rand;
/*     */     
/* 114 */     this.block = (this.blockState = state).getBlock();
/*     */     
/* 116 */     this.mr = this.mc.getBlockRendererDispatcher().getBlockModelRenderer();
/*     */     
/* 118 */     this.canCollide = false;
/*     */     
/* 120 */     this.modelPrefab = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state);
/*     */     
/* 122 */     if (this.modelPrefab == null) {
/*     */       
/* 124 */       this.canCollide = true;
/* 125 */       this.isExpired = true;
/*     */     } 
/*     */     
/* 128 */     this.tileEntity = worldIn.getTileEntity(this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 135 */     if (++this.particleAge >= 10) {
/* 136 */       killParticle();
/*     */     }
/* 138 */     if (!this.canCollide) {
/*     */       
/* 140 */       IBlockState s = this.mc.world.getBlockState(this.pos);
/*     */       
/* 142 */       if (s.getBlock() != FBP.FBPBlock || s.getBlock() == this.block) {
/*     */         
/* 144 */         if (this.blockSet && s.getBlock() == Blocks.AIR) {
/*     */ 
/*     */           
/* 147 */           killParticle();
/*     */           
/* 149 */           FBP.FBPBlock.onBlockDestroyedByPlayer((World)this.mc.world, this.pos, s);
/* 150 */           this.mc.world.setBlockState(this.pos, Blocks.AIR.getDefaultState(), 2);
/*     */           
/*     */           return;
/*     */         } 
/* 154 */         FBP.FBPBlock.copyState((World)this.mc.world, this.pos, this.blockState, this);
/* 155 */         this.mc.world.setBlockState(this.pos, FBP.FBPBlock.getDefaultState(), 2);
/*     */         
/* 157 */         Chunk c = this.mc.world.getChunkFromBlockCoords(this.pos);
/* 158 */         c.resetRelightChecks();
/* 159 */         c.setLightPopulated(true);
/*     */         
/* 161 */         FBPRenderUtil.markBlockForRender(this.pos);
/*     */         
/* 163 */         this.blockSet = true;
/*     */       } 
/*     */       
/* 166 */       this.spawned = true;
/*     */     } 
/*     */     
/* 169 */     if (this.isExpired || this.mc.isGamePaused()) {
/*     */       return;
/*     */     }
/* 172 */     this.prevHeight = this.height;
/*     */     
/* 174 */     this.prevRot.copyFrom((Vector3d)this.rot);
/*     */     
/* 176 */     switch (this.facing) {
/*     */       
/*     */       case EAST:
/* 179 */         this.rot.z += this.step;
/* 180 */         this.rot.x += this.step;
/*     */         break;
/*     */       case NORTH:
/* 183 */         this.rot.x += this.step;
/* 184 */         this.rot.z -= this.step;
/*     */         break;
/*     */       case SOUTH:
/* 187 */         this.rot.x -= this.step;
/* 188 */         this.rot.z += this.step;
/*     */         break;
/*     */       case WEST:
/* 191 */         this.rot.z -= this.step;
/* 192 */         this.rot.x -= this.step; break;
/*     */     } 
/* 194 */     this.height -= 
/*     */       
/* 196 */       this.step * 5.0F;
/*     */     
/* 198 */     this.step *= 1.5678982F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder buff, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 206 */     if (this.isExpired) {
/*     */       return;
/*     */     }
/* 209 */     if (this.canCollide) {
/*     */       
/* 211 */       Block b = this.mc.world.getBlockState(this.pos).getBlock();
/* 212 */       if (this.block != b && b != Blocks.AIR && this.mc.world.getBlockState(this.pos).getBlock() != this.blockState.getBlock()) {
/*     */         
/* 214 */         this.mc.world.setBlockState(this.pos, this.blockState, 2);
/*     */         
/* 216 */         if (this.tileEntity != null) {
/* 217 */           this.mc.world.setTileEntity(this.pos, this.tileEntity);
/*     */         }
/* 219 */         this.mc.world.sendPacketToServer((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.pos, this.facing));
/*     */         
/* 221 */         FBPRenderUtil.markBlockForRender(this.pos);
/*     */ 
/*     */         
/* 224 */         FBP.INSTANCE.eventHandler.removePosEntry(this.pos);
/*     */       } 
/* 226 */       if (this.tick >= 1L) {
/*     */         
/* 228 */         killParticle();
/*     */         
/*     */         return;
/*     */       } 
/* 232 */       this.tick++;
/*     */     } 
/* 234 */     if (!this.spawned) {
/*     */       return;
/*     */     }
/* 237 */     float f = 0.0F, f1 = 0.0F, f2 = 0.0F, f3 = 0.0F;
/*     */     
/* 239 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX) - 0.5F;
/* 240 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY) - 0.5F;
/* 241 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ) - 0.5F;
/*     */     
/* 243 */     this.smoothHeight = (float)(this.prevHeight + (this.height - this.prevHeight) * partialTicks);
/*     */     
/* 245 */     FBPVector3d smoothRot = this.rot.partialVec(this.prevRot, partialTicks);
/*     */     
/* 247 */     if (this.smoothHeight <= 0.0F) {
/* 248 */       this.smoothHeight = 0.0F;
/*     */     }
/* 250 */     FBPVector3d t = new FBPVector3d(0.0D, this.smoothHeight, 0.0D);
/* 251 */     FBPVector3d tRot = new FBPVector3d(0.0D, this.smoothHeight, 0.0D);
/*     */     
/* 253 */     switch (this.facing) {
/*     */       
/*     */       case EAST:
/* 256 */         if (smoothRot.z > 0.0D) {
/*     */           
/* 258 */           this.canCollide = true;
/* 259 */           smoothRot.z = 0.0D;
/* 260 */           smoothRot.x = 0.0D;
/*     */         } 
/*     */         
/* 263 */         t.x = -this.smoothHeight;
/* 264 */         t.z = this.smoothHeight;
/*     */         
/* 266 */         tRot.x = 1.0D;
/*     */         break;
/*     */       case NORTH:
/* 269 */         if (smoothRot.z < 0.0D) {
/*     */           
/* 271 */           this.canCollide = true;
/* 272 */           smoothRot.x = 0.0D;
/* 273 */           smoothRot.z = 0.0D;
/*     */         } 
/*     */         
/* 276 */         t.x = this.smoothHeight;
/* 277 */         t.z = this.smoothHeight;
/*     */         break;
/*     */       case SOUTH:
/* 280 */         if (smoothRot.x < 0.0D) {
/*     */           
/* 282 */           this.canCollide = true;
/* 283 */           smoothRot.x = 0.0D;
/* 284 */           smoothRot.z = 0.0D;
/*     */         } 
/*     */         
/* 287 */         t.x = -this.smoothHeight;
/* 288 */         t.z = -this.smoothHeight;
/*     */         
/* 290 */         tRot.x = 1.0D;
/* 291 */         tRot.z = 1.0D;
/*     */         break;
/*     */       case WEST:
/* 294 */         if (smoothRot.z < 0.0D) {
/*     */           
/* 296 */           this.canCollide = true;
/* 297 */           smoothRot.z = 0.0D;
/* 298 */           smoothRot.x = 0.0D;
/*     */         } 
/*     */         
/* 301 */         t.x = this.smoothHeight;
/* 302 */         t.z = -this.smoothHeight;
/*     */         
/* 304 */         tRot.z = 1.0D;
/*     */         break;
/*     */     } 
/*     */     
/* 308 */     if (FBP.spawnPlaceParticles && this.canCollide && this.tick == 0L)
/*     */     {
/* 310 */       if ((!FBP.frozen || FBP.spawnWhileFrozen) && (
/* 311 */         FBP.spawnRedstoneBlockParticles || this.block != Blocks.REDSTONE_BLOCK) && 
/* 312 */         this.mc.gameSettings.particleSetting < 2)
/*     */       {
/* 314 */         spawnParticles();
/*     */       }
/*     */     }
/* 317 */     buff.setTranslation(-this.pos.getX(), -this.pos.getY(), -this.pos.getZ());
/*     */     
/* 319 */     Tessellator.getInstance().draw();
/* 320 */     (this.mc.getRenderManager()).renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 321 */     buff.begin(7, DefaultVertexFormats.BLOCK);
/*     */     
/* 323 */     GlStateManager.pushMatrix();
/*     */     
/* 325 */     GlStateManager.enableCull();
/* 326 */     GlStateManager.enableColorMaterial();
/* 327 */     GL11.glColorMaterial(1028, 5634);
/*     */     
/* 329 */     GlStateManager.translate(f5, f6, f7);
/*     */     
/* 331 */     GlStateManager.translate(tRot.x, tRot.y, tRot.z);
/*     */     
/* 333 */     GlStateManager.rotate((float)Math.toDegrees(smoothRot.x), 1.0F, 0.0F, 0.0F);
/* 334 */     GlStateManager.rotate((float)Math.toDegrees(smoothRot.z), 0.0F, 0.0F, 1.0F);
/*     */     
/* 336 */     GlStateManager.translate(-tRot.x, -tRot.y, -tRot.z);
/* 337 */     GlStateManager.translate(t.x, t.y, t.z);
/*     */     
/* 339 */     if (FBP.animSmoothLighting) {
/* 340 */       this.mr.renderModelSmooth((IBlockAccess)this.mc.world, this.modelPrefab, this.blockState, this.pos, buff, false, this.textureSeed);
/*     */     } else {
/* 342 */       this.mr.renderModelFlat((IBlockAccess)this.mc.world, this.modelPrefab, this.blockState, this.pos, buff, false, this.textureSeed);
/*     */     } 
/* 344 */     buff.setTranslation(0.0D, 0.0D, 0.0D);
/*     */     
/* 346 */     Tessellator.getInstance().draw();
/* 347 */     GlStateManager.popMatrix();
/*     */     
/* 349 */     this.mc.getTextureManager().bindTexture(FBP.LOCATION_PARTICLE_TEXTURE);
/* 350 */     buff.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */   }
/*     */ 
/*     */   
/*     */   private void spawnParticles() {
/* 355 */     if (this.mc.world.getBlockState(this.pos.offset(EnumFacing.DOWN)).getBlock() instanceof net.minecraft.block.BlockAir) {
/*     */       return;
/*     */     }
/* 358 */     AxisAlignedBB aabb = this.block.getSelectedBoundingBox(this.blockState, (World)this.mc.world, this.pos);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 363 */     Vector2d[] corners = { new Vector2d(aabb.minX, aabb.minZ), new Vector2d(aabb.maxX, aabb.maxZ), 
/*     */         
/* 365 */         new Vector2d(aabb.minX, aabb.maxZ), new Vector2d(aabb.maxX, aabb.minZ) };
/*     */     
/* 367 */     Vector2d middle = new Vector2d((this.pos.getX() + 0.5F), (this.pos.getZ() + 0.5F)); byte b; int i;
/*     */     Vector2d[] arrayOfVector2d1;
/* 369 */     for (i = (arrayOfVector2d1 = corners).length, b = 0; b < i; ) { Vector2d corner = arrayOfVector2d1[b];
/*     */       
/* 371 */       double mX = middle.x - corner.x;
/* 372 */       double mZ = middle.y - corner.y;
/*     */       
/* 374 */       mX /= -0.5D;
/* 375 */       mZ /= -0.5D;
/*     */       
/* 377 */       this.mc.effectRenderer.addEffect((new FBPParticleDigging((World)this.mc.world, corner.x, (this.pos.getY() + 0.1F), corner.y, mX, 0.0D, 
/* 378 */             mZ, 0.6F, 1.0F, 1.0F, 1.0F, this.block.getActualState(this.blockState, (IBlockAccess)this.mc.world, this.pos), null, this.particleTexture))
/* 379 */           .multipleParticleScaleBy(0.5F).multiplyVelocity(0.5F));
/*     */       b++; }
/*     */     
/* 382 */     for (i = (arrayOfVector2d1 = corners).length, b = 0; b < i; ) { Vector2d corner = arrayOfVector2d1[b];
/*     */       
/* 384 */       if (corner != null) {
/*     */ 
/*     */         
/* 387 */         double mX = middle.x - corner.x;
/* 388 */         double mZ = middle.y - corner.y;
/*     */         
/* 390 */         mX /= -0.45D;
/* 391 */         mZ /= -0.45D;
/*     */         
/* 393 */         this.mc.effectRenderer.addEffect((
/* 394 */             new FBPParticleDigging((World)this.mc.world, corner.x, (this.pos.getY() + 0.1F), corner.y, mX / 3.0D, 0.0D, mZ / 3.0D, 0.6F, 1.0F, 
/* 395 */               1.0F, 1.0F, this.block.getActualState(this.blockState, (IBlockAccess)this.mc.world, this.pos), null, this.particleTexture))
/* 396 */             .multipleParticleScaleBy(0.75F).multiplyVelocity(0.75F));
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
/*     */   public void killParticle() {
/* 402 */     this.isExpired = true;
/*     */     
/* 404 */     FBP.FBPBlock.blockNodes.remove(this.pos);
/* 405 */     FBP.INSTANCE.eventHandler.removePosEntry(this.pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpired() {
/* 411 */     FBP.INSTANCE.eventHandler.removePosEntry(this.pos);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\particle\FBPParticleBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */