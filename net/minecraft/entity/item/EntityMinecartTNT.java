/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.block.BlockRailBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartTNT
/*     */   extends EntityMinecart
/*     */ {
/*  22 */   private int minecartTNTFuse = -1;
/*     */ 
/*     */   
/*     */   public EntityMinecartTNT(World worldIn) {
/*  26 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecartTNT(World worldIn, double x, double y, double z) {
/*  31 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesMinecartTNT(DataFixer fixer) {
/*  36 */     EntityMinecart.registerFixesMinecart(fixer, EntityMinecartTNT.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecart.Type getType() {
/*  41 */     return EntityMinecart.Type.TNT;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/*  46 */     return Blocks.TNT.getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  54 */     super.onUpdate();
/*     */     
/*  56 */     if (this.minecartTNTFuse > 0) {
/*     */       
/*  58 */       this.minecartTNTFuse--;
/*  59 */       this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*  61 */     else if (this.minecartTNTFuse == 0) {
/*     */       
/*  63 */       explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */     } 
/*     */     
/*  66 */     if (this.isCollidedHorizontally) {
/*     */       
/*  68 */       double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*     */       
/*  70 */       if (d0 >= 0.009999999776482582D)
/*     */       {
/*  72 */         explodeCart(d0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  82 */     Entity entity = source.getSourceOfDamage();
/*     */     
/*  84 */     if (entity instanceof EntityArrow) {
/*     */       
/*  86 */       EntityArrow entityarrow = (EntityArrow)entity;
/*     */       
/*  88 */       if (entityarrow.isBurning())
/*     */       {
/*  90 */         explodeCart(entityarrow.motionX * entityarrow.motionX + entityarrow.motionY * entityarrow.motionY + entityarrow.motionZ * entityarrow.motionZ);
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/*  99 */     double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*     */     
/* 101 */     if (!source.isFireDamage() && !source.isExplosion() && d0 < 0.009999999776482582D) {
/*     */       
/* 103 */       super.killMinecart(source);
/*     */       
/* 105 */       if (!source.isExplosion() && this.world.getGameRules().getBoolean("doEntityDrops"))
/*     */       {
/* 107 */         entityDropItem(new ItemStack(Blocks.TNT, 1), 0.0F);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 112 */     else if (this.minecartTNTFuse < 0) {
/*     */       
/* 114 */       ignite();
/* 115 */       this.minecartTNTFuse = this.rand.nextInt(20) + this.rand.nextInt(20);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void explodeCart(double p_94103_1_) {
/* 125 */     if (!this.world.isRemote) {
/*     */       
/* 127 */       double d0 = Math.sqrt(p_94103_1_);
/*     */       
/* 129 */       if (d0 > 5.0D)
/*     */       {
/* 131 */         d0 = 5.0D;
/*     */       }
/*     */       
/* 134 */       this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0D + this.rand.nextDouble() * 1.5D * d0), true);
/* 135 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/* 141 */     if (distance >= 3.0F) {
/*     */       
/* 143 */       float f = distance / 10.0F;
/* 144 */       explodeCart((f * f));
/*     */     } 
/*     */     
/* 147 */     super.fall(distance, damageMultiplier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
/* 155 */     if (receivingPower && this.minecartTNTFuse < 0)
/*     */     {
/* 157 */       ignite();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 163 */     if (id == 10) {
/*     */       
/* 165 */       ignite();
/*     */     }
/*     */     else {
/*     */       
/* 169 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignite() {
/* 178 */     this.minecartTNTFuse = 80;
/*     */     
/* 180 */     if (!this.world.isRemote) {
/*     */       
/* 182 */       this.world.setEntityState(this, (byte)10);
/*     */       
/* 184 */       if (!isSilent())
/*     */       {
/* 186 */         this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFuseTicks() {
/* 196 */     return this.minecartTNTFuse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIgnited() {
/* 204 */     return (this.minecartTNTFuse > -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
/* 212 */     return (!isIgnited() || (!BlockRailBase.isRailBlock(blockStateIn) && !BlockRailBase.isRailBlock(worldIn, pos.up()))) ? super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn) : 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean verifyExplosion(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_) {
/* 217 */     return (!isIgnited() || (!BlockRailBase.isRailBlock(blockStateIn) && !BlockRailBase.isRailBlock(worldIn, pos.up()))) ? super.verifyExplosion(explosionIn, worldIn, pos, blockStateIn, p_174816_5_) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/* 225 */     super.readEntityFromNBT(compound);
/*     */     
/* 227 */     if (compound.hasKey("TNTFuse", 99))
/*     */     {
/* 229 */       this.minecartTNTFuse = compound.getInteger("TNTFuse");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/* 238 */     super.writeEntityToNBT(compound);
/* 239 */     compound.setInteger("TNTFuse", this.minecartTNTFuse);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityMinecartTNT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */