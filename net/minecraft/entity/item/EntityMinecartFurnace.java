/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.block.BlockFurnace;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartFurnace extends EntityMinecart {
/*  24 */   private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(EntityMinecartFurnace.class, DataSerializers.BOOLEAN);
/*     */   
/*     */   private int fuel;
/*     */   public double pushX;
/*     */   public double pushZ;
/*     */   
/*     */   public EntityMinecartFurnace(World worldIn) {
/*  31 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecartFurnace(World worldIn, double x, double y, double z) {
/*  36 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesMinecartFurnace(DataFixer fixer) {
/*  41 */     EntityMinecart.registerFixesMinecart(fixer, EntityMinecartFurnace.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecart.Type getType() {
/*  46 */     return EntityMinecart.Type.FURNACE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  51 */     super.entityInit();
/*  52 */     this.dataManager.register(POWERED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  60 */     super.onUpdate();
/*     */     
/*  62 */     if (this.fuel > 0)
/*     */     {
/*  64 */       this.fuel--;
/*     */     }
/*     */     
/*  67 */     if (this.fuel <= 0) {
/*     */       
/*  69 */       this.pushX = 0.0D;
/*  70 */       this.pushZ = 0.0D;
/*     */     } 
/*     */     
/*  73 */     setMinecartPowered((this.fuel > 0));
/*     */     
/*  75 */     if (isMinecartPowered() && this.rand.nextInt(4) == 0)
/*     */     {
/*  77 */       this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getMaximumSpeed() {
/*  86 */     return 0.2D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/*  91 */     super.killMinecart(source);
/*     */     
/*  93 */     if (!source.isExplosion() && this.world.getGameRules().getBoolean("doEntityDrops"))
/*     */     {
/*  95 */       entityDropItem(new ItemStack(Blocks.FURNACE, 1), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void moveAlongTrack(BlockPos pos, IBlockState state) {
/* 101 */     super.moveAlongTrack(pos, state);
/* 102 */     double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
/*     */     
/* 104 */     if (d0 > 1.0E-4D && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001D) {
/*     */       
/* 106 */       d0 = MathHelper.sqrt(d0);
/* 107 */       this.pushX /= d0;
/* 108 */       this.pushZ /= d0;
/*     */       
/* 110 */       if (this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0D) {
/*     */         
/* 112 */         this.pushX = 0.0D;
/* 113 */         this.pushZ = 0.0D;
/*     */       }
/*     */       else {
/*     */         
/* 117 */         double d1 = d0 / getMaximumSpeed();
/* 118 */         this.pushX *= d1;
/* 119 */         this.pushZ *= d1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyDrag() {
/* 126 */     double d0 = this.pushX * this.pushX + this.pushZ * this.pushZ;
/*     */     
/* 128 */     if (d0 > 1.0E-4D) {
/*     */       
/* 130 */       d0 = MathHelper.sqrt(d0);
/* 131 */       this.pushX /= d0;
/* 132 */       this.pushZ /= d0;
/* 133 */       double d1 = 1.0D;
/* 134 */       this.motionX *= 0.800000011920929D;
/* 135 */       this.motionY *= 0.0D;
/* 136 */       this.motionZ *= 0.800000011920929D;
/* 137 */       this.motionX += this.pushX * 1.0D;
/* 138 */       this.motionZ += this.pushZ * 1.0D;
/*     */     }
/*     */     else {
/*     */       
/* 142 */       this.motionX *= 0.9800000190734863D;
/* 143 */       this.motionY *= 0.0D;
/* 144 */       this.motionZ *= 0.9800000190734863D;
/*     */     } 
/*     */     
/* 147 */     super.applyDrag();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
/* 152 */     ItemStack itemstack = player.getHeldItem(stack);
/*     */     
/* 154 */     if (itemstack.getItem() == Items.COAL && this.fuel + 3600 <= 32000) {
/*     */       
/* 156 */       if (!player.capabilities.isCreativeMode)
/*     */       {
/* 158 */         itemstack.func_190918_g(1);
/*     */       }
/*     */       
/* 161 */       this.fuel += 3600;
/*     */     } 
/*     */     
/* 164 */     this.pushX = this.posX - player.posX;
/* 165 */     this.pushZ = this.posZ - player.posZ;
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/* 174 */     super.writeEntityToNBT(compound);
/* 175 */     compound.setDouble("PushX", this.pushX);
/* 176 */     compound.setDouble("PushZ", this.pushZ);
/* 177 */     compound.setShort("Fuel", (short)this.fuel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/* 185 */     super.readEntityFromNBT(compound);
/* 186 */     this.pushX = compound.getDouble("PushX");
/* 187 */     this.pushZ = compound.getDouble("PushZ");
/* 188 */     this.fuel = compound.getShort("Fuel");
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isMinecartPowered() {
/* 193 */     return ((Boolean)this.dataManager.get(POWERED)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setMinecartPowered(boolean p_94107_1_) {
/* 198 */     this.dataManager.set(POWERED, Boolean.valueOf(p_94107_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/* 203 */     return (isMinecartPowered() ? Blocks.LIT_FURNACE : Blocks.FURNACE).getDefaultState().withProperty((IProperty)BlockFurnace.FACING, (Comparable)EnumFacing.NORTH);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityMinecartFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */