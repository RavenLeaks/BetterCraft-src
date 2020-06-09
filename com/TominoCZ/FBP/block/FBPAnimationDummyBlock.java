/*     */ package com.TominoCZ.FBP.block;
/*     */ 
/*     */ import com.TominoCZ.FBP.FBP;
/*     */ import com.TominoCZ.FBP.material.FBPMaterial;
/*     */ import com.TominoCZ.FBP.node.FBPBlockNode;
/*     */ import com.TominoCZ.FBP.particle.FBPParticleBlock;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FBPAnimationDummyBlock
/*     */   extends Block
/*     */ {
/*  33 */   public ConcurrentHashMap<BlockPos, FBPBlockNode> blockNodes = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FBPAnimationDummyBlock() {
/*  39 */     super((Material)new FBPMaterial());
/*     */     
/*  41 */     setUnlocalizedName("FBPPlaceholderBlock");
/*     */     
/*  43 */     this.translucent = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyState(World w, BlockPos pos, IBlockState state, FBPParticleBlock p) {
/*  48 */     if (this.blockNodes.containsKey(pos)) {
/*     */       return;
/*     */     }
/*  51 */     this.blockNodes.put(pos, new FBPBlockNode(state, p));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  58 */     if (this.blockNodes.containsKey(pos)) {
/*     */       
/*  60 */       FBPBlockNode n = this.blockNodes.get(pos);
/*     */ 
/*     */       
/*     */       try {
/*  64 */         return n.originalBlock.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
/*  65 */       } catch (Throwable t) {
/*     */         
/*  67 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAir() {
/*  76 */     return (this == Blocks.AIR);
/*     */   }
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
/*     */   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
/*     */     try {
/* 103 */       if (this.blockNodes.containsKey(pos)) {
/*     */         
/* 105 */         FBPBlockNode n = this.blockNodes.get(pos);
/*     */         
/* 107 */         return n.state.getMaterial().isReplaceable();
/*     */       } 
/* 109 */     } catch (Throwable t) {
/*     */       
/* 111 */       t.printStackTrace();
/*     */     } 
/*     */     
/* 114 */     return this.blockMaterial.isReplaceable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*     */     try {
/* 122 */       if (this.blockNodes.containsKey(pos)) {
/*     */         
/* 124 */         FBPBlockNode n = this.blockNodes.get(pos);
/*     */         
/* 126 */         return n.originalBlock.isPassable(worldIn, pos);
/*     */       } 
/* 128 */     } catch (Throwable t) {
/*     */       
/* 130 */       t.printStackTrace();
/*     */     } 
/* 132 */     return !this.blockMaterial.blocksMovement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/*     */     try {
/* 140 */       if (this.blockNodes.containsKey(pos)) {
/*     */         
/* 142 */         FBPBlockNode n = this.blockNodes.get(pos);
/*     */         
/* 144 */         n.originalBlock.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
/*     */       } 
/* 146 */     } catch (Throwable t) {
/*     */       
/* 148 */       t.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*     */     try {
/* 157 */       if (this.blockNodes.containsKey(pos)) {
/*     */         
/* 159 */         FBPBlockNode n = this.blockNodes.get(pos);
/*     */         
/* 161 */         return n.state.getCollisionBoundingBox(worldIn, pos);
/*     */       } 
/* 163 */     } catch (Throwable t) {
/*     */       
/* 165 */       t.printStackTrace();
/*     */     } 
/*     */     
/* 168 */     return Block.FULL_BLOCK_AABB.offset(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*     */     try {
/* 176 */       if (this.blockNodes.containsKey(pos)) {
/*     */         
/* 178 */         FBPBlockNode n = this.blockNodes.get(pos);
/*     */         
/* 180 */         return n.state.getBoundingBox(worldIn, pos);
/*     */       } 
/* 182 */     } catch (Throwable t) {
/*     */       
/* 184 */       t.printStackTrace();
/*     */     } 
/*     */     
/* 187 */     return Block.FULL_BLOCK_AABB.offset(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
/* 193 */     return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBlockHardness(IBlockState blockState, World w, BlockPos pos) {
/*     */     try {
/* 201 */       if (this.blockNodes.containsKey(pos)) {
/*     */         
/* 203 */         FBPBlockNode n = this.blockNodes.get(pos);
/*     */         
/* 205 */         return n.state.getBlockHardness(w, pos);
/*     */       } 
/* 207 */     } catch (Throwable t) {
/*     */       
/* 209 */       t.printStackTrace();
/*     */     } 
/*     */     
/* 212 */     return blockState.getBlockHardness(w, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/*     */     try {
/* 220 */       FBPBlockNode node = FBP.FBPBlock.blockNodes.get(pos);
/*     */       
/* 222 */       if (node == null) {
/*     */         return;
/*     */       }
/* 225 */       if (worldIn.isRemote && state.getBlock() != node.originalBlock && (
/* 226 */         worldIn.getBlockState(pos).getBlock() instanceof FBPAnimationDummyBlock || 
/* 227 */         state.getBlock() instanceof FBPAnimationDummyBlock)) {
/* 228 */         (Minecraft.getMinecraft()).effectRenderer.addBlockDestroyEffects(pos, 
/* 229 */             node.originalBlock.getStateFromMeta(node.meta));
/*     */       }
/* 231 */       if (node.particle != null) {
/* 232 */         node.particle.killParticle();
/*     */       }
/*     */       
/* 235 */       FBP.INSTANCE.eventHandler.removePosEntry(pos);
/* 236 */     } catch (Throwable t) {
/*     */       
/* 238 */       t.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean b) {
/*     */     try {
/* 248 */       if (this.blockNodes.containsKey(pos))
/* 249 */         ((FBPBlockNode)this.blockNodes.get(pos)).state.addCollisionBoxToList(worldIn, pos, entityBox, collidingBoxes, entityIn, b); 
/* 250 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(Entity e) {
/* 259 */     if (this.blockNodes.containsKey(e.getPosition())) {
/* 260 */       return ((FBPBlockNode)this.blockNodes.get(e.getPosition())).originalBlock.getExplosionResistance(e);
/*     */     }
/* 262 */     return super.getExplosionResistance(e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 268 */     if (this.blockNodes.containsKey(pos)) {
/* 269 */       return ((FBPBlockNode)this.blockNodes.get(pos)).state.getWeakPower(blockAccess, pos, side);
/*     */     }
/* 271 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 277 */     if (this.blockNodes.containsKey(pos)) {
/* 278 */       return ((FBPBlockNode)this.blockNodes.get(pos)).originalBlock.canPlaceBlockAt(worldIn, pos);
/*     */     }
/* 280 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 286 */     if (this.blockNodes.containsKey(pos)) {
/* 287 */       return ((FBPBlockNode)this.blockNodes.get(pos)).originalBlock.canPlaceBlockOnSide(worldIn, pos, side);
/*     */     }
/* 289 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/*     */     try {
/* 297 */       if (this.blockNodes.containsKey(pos));
/*     */ 
/*     */       
/* 300 */       return new ItemStack(Item.getItemFromBlock(this), 1, damageDropped(state));
/* 301 */     } catch (Throwable t) {
/*     */       
/* 303 */       t.printStackTrace();
/*     */ 
/*     */       
/* 306 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 312 */     if (this.blockNodes.containsKey(pos)) {
/* 313 */       ((FBPBlockNode)this.blockNodes.get(pos)).originalBlock.onBlockAdded(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random r, int i) {
/* 319 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 325 */     return EnumBlockRenderType.INVISIBLE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 331 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAmbientOcclusionLightValue(IBlockState state) {
/* 337 */     return 1.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\block\FBPAnimationDummyBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */