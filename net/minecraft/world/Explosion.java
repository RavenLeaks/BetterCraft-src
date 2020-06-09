/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentProtection;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Explosion
/*     */ {
/*     */   private final boolean isFlaming;
/*     */   private final boolean isSmoking;
/*     */   private final Random explosionRNG;
/*     */   private final World worldObj;
/*     */   private final double explosionX;
/*     */   private final double explosionY;
/*     */   private final double explosionZ;
/*     */   private final Entity exploder;
/*     */   private final float explosionSize;
/*     */   private final List<BlockPos> affectedBlockPositions;
/*     */   private final Map<EntityPlayer, Vec3d> playerKnockbackMap;
/*     */   
/*     */   public Explosion(World worldIn, Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
/*  48 */     this(worldIn, entityIn, x, y, z, size, false, true, affectedPositions);
/*     */   }
/*     */ 
/*     */   
/*     */   public Explosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean smoking, List<BlockPos> affectedPositions) {
/*  53 */     this(worldIn, entityIn, x, y, z, size, flaming, smoking);
/*  54 */     this.affectedBlockPositions.addAll(affectedPositions);
/*     */   }
/*     */ 
/*     */   
/*     */   public Explosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean smoking) {
/*  59 */     this.explosionRNG = new Random();
/*  60 */     this.affectedBlockPositions = Lists.newArrayList();
/*  61 */     this.playerKnockbackMap = Maps.newHashMap();
/*  62 */     this.worldObj = worldIn;
/*  63 */     this.exploder = entityIn;
/*  64 */     this.explosionSize = size;
/*  65 */     this.explosionX = x;
/*  66 */     this.explosionY = y;
/*  67 */     this.explosionZ = z;
/*  68 */     this.isFlaming = flaming;
/*  69 */     this.isSmoking = smoking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doExplosionA() {
/*  77 */     Set<BlockPos> set = Sets.newHashSet();
/*  78 */     int i = 16;
/*     */     
/*  80 */     for (int j = 0; j < 16; j++) {
/*     */       
/*  82 */       for (int k = 0; k < 16; k++) {
/*     */         
/*  84 */         for (int l = 0; l < 16; l++) {
/*     */           
/*  86 */           if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
/*     */             
/*  88 */             double d0 = (j / 15.0F * 2.0F - 1.0F);
/*  89 */             double d1 = (k / 15.0F * 2.0F - 1.0F);
/*  90 */             double d2 = (l / 15.0F * 2.0F - 1.0F);
/*  91 */             double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*  92 */             d0 /= d3;
/*  93 */             d1 /= d3;
/*  94 */             d2 /= d3;
/*  95 */             float f = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
/*  96 */             double d4 = this.explosionX;
/*  97 */             double d6 = this.explosionY;
/*  98 */             double d8 = this.explosionZ;
/*     */             
/* 100 */             for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
/*     */               
/* 102 */               BlockPos blockpos = new BlockPos(d4, d6, d8);
/* 103 */               IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/*     */               
/* 105 */               if (iblockstate.getMaterial() != Material.AIR) {
/*     */                 
/* 107 */                 float f2 = (this.exploder != null) ? this.exploder.getExplosionResistance(this, this.worldObj, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance(null);
/* 108 */                 f -= (f2 + 0.3F) * 0.3F;
/*     */               } 
/*     */               
/* 111 */               if (f > 0.0F && (this.exploder == null || this.exploder.verifyExplosion(this, this.worldObj, blockpos, iblockstate, f)))
/*     */               {
/* 113 */                 set.add(blockpos);
/*     */               }
/*     */               
/* 116 */               d4 += d0 * 0.30000001192092896D;
/* 117 */               d6 += d1 * 0.30000001192092896D;
/* 118 */               d8 += d2 * 0.30000001192092896D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     this.affectedBlockPositions.addAll(set);
/* 126 */     float f3 = this.explosionSize * 2.0F;
/* 127 */     int k1 = MathHelper.floor(this.explosionX - f3 - 1.0D);
/* 128 */     int l1 = MathHelper.floor(this.explosionX + f3 + 1.0D);
/* 129 */     int i2 = MathHelper.floor(this.explosionY - f3 - 1.0D);
/* 130 */     int i1 = MathHelper.floor(this.explosionY + f3 + 1.0D);
/* 131 */     int j2 = MathHelper.floor(this.explosionZ - f3 - 1.0D);
/* 132 */     int j1 = MathHelper.floor(this.explosionZ + f3 + 1.0D);
/* 133 */     List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
/* 134 */     Vec3d vec3d = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);
/*     */     
/* 136 */     for (int k2 = 0; k2 < list.size(); k2++) {
/*     */       
/* 138 */       Entity entity = list.get(k2);
/*     */       
/* 140 */       if (!entity.isImmuneToExplosions()) {
/*     */         
/* 142 */         double d12 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / f3;
/*     */         
/* 144 */         if (d12 <= 1.0D) {
/*     */           
/* 146 */           double d5 = entity.posX - this.explosionX;
/* 147 */           double d7 = entity.posY + entity.getEyeHeight() - this.explosionY;
/* 148 */           double d9 = entity.posZ - this.explosionZ;
/* 149 */           double d13 = MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
/*     */           
/* 151 */           if (d13 != 0.0D) {
/*     */             
/* 153 */             d5 /= d13;
/* 154 */             d7 /= d13;
/* 155 */             d9 /= d13;
/* 156 */             double d14 = this.worldObj.getBlockDensity(vec3d, entity.getEntityBoundingBox());
/* 157 */             double d10 = (1.0D - d12) * d14;
/* 158 */             entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (int)((d10 * d10 + d10) / 2.0D * 7.0D * f3 + 1.0D));
/* 159 */             double d11 = d10;
/*     */             
/* 161 */             if (entity instanceof EntityLivingBase)
/*     */             {
/* 163 */               d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase)entity, d10);
/*     */             }
/*     */             
/* 166 */             entity.motionX += d5 * d11;
/* 167 */             entity.motionY += d7 * d11;
/* 168 */             entity.motionZ += d9 * d11;
/*     */             
/* 170 */             if (entity instanceof EntityPlayer) {
/*     */               
/* 172 */               EntityPlayer entityplayer = (EntityPlayer)entity;
/*     */               
/* 174 */               if (!entityplayer.isSpectator() && (!entityplayer.isCreative() || !entityplayer.capabilities.isFlying))
/*     */               {
/* 176 */                 this.playerKnockbackMap.put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doExplosionB(boolean spawnParticles) {
/* 190 */     this.worldObj.playSound((EntityPlayer)null, this.explosionX, this.explosionY, this.explosionZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
/*     */     
/* 192 */     if (this.explosionSize >= 2.0F && this.isSmoking) {
/*     */       
/* 194 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */     else {
/*     */       
/* 198 */       this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */     
/* 201 */     if (this.isSmoking)
/*     */     {
/* 203 */       for (BlockPos blockpos : this.affectedBlockPositions) {
/*     */         
/* 205 */         IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 206 */         Block block = iblockstate.getBlock();
/*     */         
/* 208 */         if (spawnParticles) {
/*     */           
/* 210 */           double d0 = (blockpos.getX() + this.worldObj.rand.nextFloat());
/* 211 */           double d1 = (blockpos.getY() + this.worldObj.rand.nextFloat());
/* 212 */           double d2 = (blockpos.getZ() + this.worldObj.rand.nextFloat());
/* 213 */           double d3 = d0 - this.explosionX;
/* 214 */           double d4 = d1 - this.explosionY;
/* 215 */           double d5 = d2 - this.explosionZ;
/* 216 */           double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/* 217 */           d3 /= d6;
/* 218 */           d4 /= d6;
/* 219 */           d5 /= d6;
/* 220 */           double d7 = 0.5D / (d6 / this.explosionSize + 0.1D);
/* 221 */           d7 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
/* 222 */           d3 *= d7;
/* 223 */           d4 *= d7;
/* 224 */           d5 *= d7;
/* 225 */           this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.explosionX) / 2.0D, (d1 + this.explosionY) / 2.0D, (d2 + this.explosionZ) / 2.0D, d3, d4, d5, new int[0]);
/* 226 */           this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */         } 
/*     */         
/* 229 */         if (iblockstate.getMaterial() != Material.AIR) {
/*     */           
/* 231 */           if (block.canDropFromExplosion(this))
/*     */           {
/* 233 */             block.dropBlockAsItemWithChance(this.worldObj, blockpos, this.worldObj.getBlockState(blockpos), 1.0F / this.explosionSize, 0);
/*     */           }
/*     */           
/* 236 */           this.worldObj.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
/* 237 */           block.onBlockDestroyedByExplosion(this.worldObj, blockpos, this);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 242 */     if (this.isFlaming)
/*     */     {
/* 244 */       for (BlockPos blockpos1 : this.affectedBlockPositions) {
/*     */         
/* 246 */         if (this.worldObj.getBlockState(blockpos1).getMaterial() == Material.AIR && this.worldObj.getBlockState(blockpos1.down()).isFullBlock() && this.explosionRNG.nextInt(3) == 0)
/*     */         {
/* 248 */           this.worldObj.setBlockState(blockpos1, Blocks.FIRE.getDefaultState());
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<EntityPlayer, Vec3d> getPlayerKnockbackMap() {
/* 256 */     return this.playerKnockbackMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityLivingBase getExplosivePlacedBy() {
/* 266 */     if (this.exploder == null)
/*     */     {
/* 268 */       return null;
/*     */     }
/* 270 */     if (this.exploder instanceof EntityTNTPrimed)
/*     */     {
/* 272 */       return ((EntityTNTPrimed)this.exploder).getTntPlacedBy();
/*     */     }
/*     */ 
/*     */     
/* 276 */     return (this.exploder instanceof EntityLivingBase) ? (EntityLivingBase)this.exploder : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAffectedBlockPositions() {
/* 282 */     this.affectedBlockPositions.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPos> getAffectedBlockPositions() {
/* 287 */     return this.affectedBlockPositions;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\Explosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */