/*     */ package com.TominoCZ.FBP.handler;
/*     */ 
/*     */ import com.TominoCZ.FBP.FBP;
/*     */ import com.TominoCZ.FBP.model.FBPModelHelper;
/*     */ import com.TominoCZ.FBP.node.FBPBlockNode;
/*     */ import com.TominoCZ.FBP.node.FBPBlockPosNode;
/*     */ import com.TominoCZ.FBP.particle.FBPParticleBlock;
/*     */ import com.TominoCZ.FBP.particle.FBPParticleManager;
/*     */ import io.netty.util.internal.ConcurrentSet;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.BlockSlab;
/*     */ import net.minecraft.block.BlockTorch;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.IParticleFactory;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.particle.ParticleDigging;
/*     */ import net.minecraft.client.particle.ParticleManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IWorldEventListener;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FBPEventHandler
/*     */ {
/*  43 */   Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*  45 */   ConcurrentSet<FBPBlockPosNode> list = new ConcurrentSet();
/*     */   
/*  47 */   IWorldEventListener listener = new IWorldEventListener()
/*     */     {
/*     */       public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void broadcastSound(int soundID, BlockPos pos, int data) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void onEntityAdded(Entity entityIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void playSoundToAllNearExcept(EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void playRecord(SoundEvent soundIn, BlockPos pos) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void onEntityRemoved(Entity entityIn) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void notifyLightSet(BlockPos pos) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
/*  93 */         if (FBP.enabled && FBP.fancyPlaceAnim && (flags == 11 || flags == 3) && !oldState.equals(newState)) {
/*  94 */           FBPBlockPosNode node = FBPEventHandler.this.getNodeWithPos(pos);
/*     */           
/*  96 */           if (node != null && !node.checked) {
/*  97 */             if (newState.getBlock() == FBP.FBPBlock || newState.getBlock() == Blocks.AIR || 
/*  98 */               oldState.getBlock() == newState.getBlock()) {
/*  99 */               FBPEventHandler.this.removePosEntry(pos);
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 104 */             IBlockState state = newState.getActualState((IBlockAccess)worldIn, pos);
/*     */             
/* 106 */             if (state.getBlock() instanceof net.minecraft.block.BlockDoublePlant || !FBPModelHelper.isModelValid(state)) {
/* 107 */               FBPEventHandler.this.removePosEntry(pos);
/*     */               
/*     */               return;
/*     */             } 
/* 111 */             long seed = MathHelper.getPositionRandom((Vec3i)pos);
/*     */             
/* 113 */             boolean isNotFalling = true;
/*     */             
/* 115 */             if (state.getBlock() instanceof BlockFalling) {
/* 116 */               BlockFalling bf = (BlockFalling)state.getBlock();
/* 117 */               if (BlockFalling.canFallThrough(worldIn.getBlockState(pos.offset(EnumFacing.DOWN)))) {
/* 118 */                 isNotFalling = false;
/*     */               }
/*     */             } 
/* 121 */             if (!FBP.INSTANCE.isBlacklisted(state.getBlock(), false) && isNotFalling) {
/* 122 */               node.checked = true;
/*     */               
/* 124 */               FBPParticleBlock p = new FBPParticleBlock(worldIn, (pos.getX() + 0.5F), (pos.getY() + 0.5F), (
/* 125 */                   pos.getZ() + 0.5F), state, seed);
/*     */               
/* 127 */               FBPEventHandler.this.mc.effectRenderer.addEffect((Particle)p);
/*     */               
/* 129 */               FBP.FBPBlock.copyState(worldIn, pos, state, p);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void func_190570_a(int p_190570_1_, boolean p_190570_2_, boolean p_190570_3_, double p_190570_4_, double p_190570_6_, double p_190570_8_, double p_190570_10_, double p_190570_12_, double p_190570_14_, int... p_190570_16_) {}
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInteractionEvent(int mouseId) {
/*     */     try {
/* 147 */       if (mouseId != 1) {
/*     */         return;
/*     */       }
/* 150 */       RayTraceResult e = this.mc.objectMouseOver;
/* 151 */       if (e.hitVec == null || this.mc.player.getHeldItemMainhand() == null || !this.mc.world.isRemote || 
/* 152 */         !(this.mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBlock)) {
/*     */         return;
/*     */       }
/* 155 */       BlockPos pos = e.getBlockPos();
/* 156 */       BlockPos pos_o = e.getBlockPos().offset(e.sideHit);
/*     */       
/* 158 */       Block inHand = null;
/*     */       
/* 160 */       IBlockState atPos = this.mc.world.getBlockState(pos);
/* 161 */       IBlockState offset = this.mc.world.getBlockState(pos_o);
/*     */       
/* 163 */       boolean bool = false;
/*     */       
/* 165 */       float f = (float)(e.hitVec.xCoord - pos.getX());
/* 166 */       float f1 = (float)(e.hitVec.yCoord - pos.getY());
/* 167 */       float f2 = (float)(e.hitVec.zCoord - pos.getZ());
/*     */       
/* 169 */       if (atPos.getBlock() == FBP.FBPBlock) {
/* 170 */         FBPBlockNode n = (FBPBlockNode)FBP.FBPBlock.blockNodes.get(pos);
/*     */         
/* 172 */         if (n != null && n.state.getBlock() != null) {
/* 173 */           boolean activated = n.originalBlock.onBlockActivated((World)this.mc.world, pos, n.state, (EntityPlayer)this.mc.player, 
/* 174 */               EnumHand.MAIN_HAND, e.sideHit, f, f1, f2);
/*     */           
/* 176 */           if (activated) {
/*     */             return;
/*     */           }
/* 179 */           atPos = n.state;
/*     */         } 
/*     */ 
/*     */         
/* 183 */         if (atPos.getBlock() instanceof BlockSlab) {
/* 184 */           BlockSlab.EnumBlockHalf half = (BlockSlab.EnumBlockHalf)atPos.getValue((IProperty)BlockSlab.HALF);
/*     */           
/* 186 */           if (e.sideHit == EnumFacing.UP) {
/* 187 */             if (half == BlockSlab.EnumBlockHalf.BOTTOM) {
/* 188 */               bool = true;
/*     */             }
/* 190 */           } else if (e.sideHit == EnumFacing.DOWN && 
/* 191 */             half == BlockSlab.EnumBlockHalf.TOP) {
/* 192 */             bool = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 197 */       if (offset.getBlock() == FBP.FBPBlock) {
/* 198 */         FBPBlockNode n = (FBPBlockNode)FBP.FBPBlock.blockNodes.get(pos_o);
/*     */         
/* 200 */         if (n != null && n.state.getBlock() != null) {
/* 201 */           offset = n.state;
/*     */         }
/*     */       } 
/* 204 */       if (this.mc.player.getHeldItemMainhand() != null && this.mc.player.getHeldItemMainhand().getItem() != null) {
/* 205 */         inHand = Block.getBlockFromItem(this.mc.player.getHeldItemMainhand().getItem());
/*     */       }
/* 207 */       boolean addedOffset = false;
/*     */       
/* 209 */       FBPBlockPosNode node = new FBPBlockPosNode();
/*     */       
/*     */       try {
/* 212 */         if (!bool && inHand != null && offset.getMaterial().isReplaceable() && 
/* 213 */           !atPos.getBlock().isReplaceable((IBlockAccess)this.mc.world, pos) && inHand.canPlaceBlockAt((World)this.mc.world, pos_o)) {
/* 214 */           node.add(pos_o);
/* 215 */           addedOffset = true;
/*     */         } else {
/* 217 */           node.add(pos);
/*     */         } 
/* 219 */         boolean okToAdd = (inHand != null && inHand != Blocks.AIR && 
/* 220 */           inHand.canPlaceBlockAt((World)this.mc.world, addedOffset ? pos_o : pos));
/*     */ 
/*     */         
/* 223 */         if (inHand != null && inHand instanceof BlockTorch) {
/* 224 */           BlockTorch bt = (BlockTorch)inHand;
/*     */           
/* 226 */           if (!bt.canPlaceBlockAt((World)this.mc.world, pos_o)) {
/* 227 */             okToAdd = false;
/*     */           }
/* 229 */           if (atPos.getBlock() == Blocks.TORCH) {
/* 230 */             byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < i; ) { EnumFacing fc = arrayOfEnumFacing[b];
/* 231 */               BlockPos p = pos_o.offset(fc);
/* 232 */               Block bl = this.mc.world.getBlockState(p).getBlock();
/*     */               
/* 234 */               if (bl != Blocks.TORCH && bl != FBP.FBPBlock && 
/* 235 */                 bl
/* 236 */                 .isFullCube(bl.getDefaultState())) {
/* 237 */                 okToAdd = true;
/*     */                 break;
/*     */               } 
/* 240 */               okToAdd = false;
/*     */               b++; }
/*     */           
/*     */           } 
/*     */         } 
/* 245 */         FBPBlockPosNode last = getNodeWithPos(pos);
/* 246 */         FBPBlockPosNode last_o = getNodeWithPos(pos_o);
/*     */ 
/*     */         
/* 249 */         if (okToAdd) {
/* 250 */           boolean replaceable = (addedOffset ? offset : atPos).getBlock().isReplaceable((IBlockAccess)this.mc.world, 
/* 251 */               addedOffset ? pos_o : pos);
/*     */           
/* 253 */           if (last != null && !addedOffset && last.checked)
/*     */             return; 
/* 255 */           if (last_o != null && addedOffset && (last_o.checked || replaceable)) {
/*     */             return;
/*     */           }
/* 258 */           Chunk c = this.mc.world.getChunkFromBlockCoords(addedOffset ? pos_o : pos);
/* 259 */           c.resetRelightChecks();
/* 260 */           c.setLightPopulated(true);
/*     */           
/* 262 */           this.list.add(node);
/*     */         } 
/* 264 */       } catch (Throwable t) {
/* 265 */         this.list.clear();
/*     */       } 
/* 267 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onWorldLoadEvent() {
/* 274 */     this.mc.world.addEventListener(this.listener);
/* 275 */     this.list.clear();
/*     */   }
/*     */   
/*     */   public void onEntityJoinWorldEvent(Entity entity) {
/* 279 */     if (entity == this.mc.player) {
/* 280 */       FBP.fancyEffectRenderer = new FBPParticleManager((World)this.mc.world, this.mc.getTextureManager(), (IParticleFactory)new ParticleDigging.Factory());
/* 281 */       if ((FBP.originalEffectRenderer == null || (FBP.originalEffectRenderer != this.mc.effectRenderer && 
/* 282 */         FBP.originalEffectRenderer != FBP.fancyEffectRenderer)) && this.mc.effectRenderer != null) {
/* 283 */         FBP.originalEffectRenderer = this.mc.effectRenderer;
/*     */       }
/* 285 */       if (FBP.enabled) {
/* 286 */         this.mc.effectRenderer = (ParticleManager)FBP.fancyEffectRenderer;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   FBPBlockPosNode getNodeWithPos(BlockPos pos) {
/* 292 */     for (FBPBlockPosNode n : this.list) {
/* 293 */       if (n.hasPos(pos))
/* 294 */         return n; 
/*     */     } 
/* 296 */     return null;
/*     */   }
/*     */   
/*     */   public void removePosEntry(BlockPos pos) {
/* 300 */     for (int i = 0; i < this.list.size(); i++) {
/* 301 */       FBPBlockPosNode n = getNodeWithPos(pos);
/*     */       
/* 303 */       if (n != null)
/* 304 */         this.list.remove(n); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\handler\FBPEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */