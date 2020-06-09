/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityAnimal extends EntityAgeable implements IAnimals {
/*  22 */   protected Block spawnableBlock = (Block)Blocks.GRASS;
/*     */   
/*     */   private int inLove;
/*     */   private UUID playerInLove;
/*     */   
/*     */   public EntityAnimal(World worldIn) {
/*  28 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/*  33 */     if (getGrowingAge() != 0)
/*     */     {
/*  35 */       this.inLove = 0;
/*     */     }
/*     */     
/*  38 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  47 */     super.onLivingUpdate();
/*     */     
/*  49 */     if (getGrowingAge() != 0)
/*     */     {
/*  51 */       this.inLove = 0;
/*     */     }
/*     */     
/*  54 */     if (this.inLove > 0) {
/*     */       
/*  56 */       this.inLove--;
/*     */       
/*  58 */       if (this.inLove % 10 == 0) {
/*     */         
/*  60 */         double d0 = this.rand.nextGaussian() * 0.02D;
/*  61 */         double d1 = this.rand.nextGaussian() * 0.02D;
/*  62 */         double d2 = this.rand.nextGaussian() * 0.02D;
/*  63 */         this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  73 */     if (isEntityInvulnerable(source))
/*     */     {
/*  75 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  79 */     this.inLove = 0;
/*  80 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/*  86 */     return (this.world.getBlockState(pos.down()).getBlock() == this.spawnableBlock) ? 10.0F : (this.world.getLightBrightness(pos) - 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  94 */     super.writeEntityToNBT(compound);
/*  95 */     compound.setInteger("InLove", this.inLove);
/*     */     
/*  97 */     if (this.playerInLove != null)
/*     */     {
/*  99 */       compound.setUniqueId("LoveCause", this.playerInLove);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/* 108 */     return 0.14D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 116 */     super.readEntityFromNBT(compound);
/* 117 */     this.inLove = compound.getInteger("InLove");
/* 118 */     this.playerInLove = compound.hasUniqueId("LoveCause") ? compound.getUniqueId("LoveCause") : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 126 */     int i = MathHelper.floor(this.posX);
/* 127 */     int j = MathHelper.floor((getEntityBoundingBox()).minY);
/* 128 */     int k = MathHelper.floor(this.posZ);
/* 129 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 130 */     return (this.world.getBlockState(blockpos.down()).getBlock() == this.spawnableBlock && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTalkInterval() {
/* 138 */     return 120;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getExperiencePoints(EntityPlayer player) {
/* 154 */     return 1 + this.world.rand.nextInt(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 163 */     return (stack.getItem() == Items.WHEAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 168 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/* 170 */     if (!itemstack.func_190926_b()) {
/*     */       
/* 172 */       if (isBreedingItem(itemstack) && getGrowingAge() == 0 && this.inLove <= 0) {
/*     */         
/* 174 */         consumeItemFromStack(player, itemstack);
/* 175 */         setInLove(player);
/* 176 */         return true;
/*     */       } 
/*     */       
/* 179 */       if (isChild() && isBreedingItem(itemstack)) {
/*     */         
/* 181 */         consumeItemFromStack(player, itemstack);
/* 182 */         ageUp((int)((-getGrowingAge() / 20) * 0.1F), true);
/* 183 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     return super.processInteract(player, hand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void consumeItemFromStack(EntityPlayer player, ItemStack stack) {
/* 195 */     if (!player.capabilities.isCreativeMode)
/*     */     {
/* 197 */       stack.func_190918_g(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInLove(@Nullable EntityPlayer player) {
/* 203 */     this.inLove = 600;
/*     */     
/* 205 */     if (player != null)
/*     */     {
/* 207 */       this.playerInLove = player.getUniqueID();
/*     */     }
/*     */     
/* 210 */     this.world.setEntityState((Entity)this, (byte)18);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityPlayerMP func_191993_do() {
/* 216 */     if (this.playerInLove == null)
/*     */     {
/* 218 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 222 */     EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.playerInLove);
/* 223 */     return (entityplayer instanceof EntityPlayerMP) ? (EntityPlayerMP)entityplayer : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInLove() {
/* 232 */     return (this.inLove > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetInLove() {
/* 237 */     this.inLove = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 245 */     if (otherAnimal == this)
/*     */     {
/* 247 */       return false;
/*     */     }
/* 249 */     if (otherAnimal.getClass() != getClass())
/*     */     {
/* 251 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 255 */     return (isInLove() && otherAnimal.isInLove());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 261 */     if (id == 18) {
/*     */       
/* 263 */       for (int i = 0; i < 7; i++)
/*     */       {
/* 265 */         double d0 = this.rand.nextGaussian() * 0.02D;
/* 266 */         double d1 = this.rand.nextGaussian() * 0.02D;
/* 267 */         double d2 = this.rand.nextGaussian() * 0.02D;
/* 268 */         this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 273 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityAnimal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */