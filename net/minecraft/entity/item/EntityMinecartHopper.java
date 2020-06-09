/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerHopper;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.IHopper;
/*     */ import net.minecraft.tileentity.TileEntityHopper;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartHopper
/*     */   extends EntityMinecartContainer implements IHopper {
/*     */   private boolean isBlocked = true;
/*  26 */   private int transferTicker = -1;
/*  27 */   private final BlockPos lastPosition = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*     */   public EntityMinecartHopper(World worldIn) {
/*  31 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecartHopper(World worldIn, double x, double y, double z) {
/*  36 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecart.Type getType() {
/*  41 */     return EntityMinecart.Type.HOPPER;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/*  46 */     return Blocks.HOPPER.getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDefaultDisplayTileOffset() {
/*  51 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  59 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
/*  64 */     if (!this.world.isRemote)
/*     */     {
/*  66 */       player.displayGUIChest((IInventory)this);
/*     */     }
/*     */     
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
/*  77 */     boolean flag = !receivingPower;
/*     */     
/*  79 */     if (flag != getBlocked())
/*     */     {
/*  81 */       setBlocked(flag);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBlocked() {
/*  90 */     return this.isBlocked;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlocked(boolean p_96110_1_) {
/*  98 */     this.isBlocked = p_96110_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 106 */     return this.world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXPos() {
/* 114 */     return this.posX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYPos() {
/* 122 */     return this.posY + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZPos() {
/* 130 */     return this.posZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 138 */     super.onUpdate();
/*     */     
/* 140 */     if (!this.world.isRemote && isEntityAlive() && getBlocked()) {
/*     */       
/* 142 */       BlockPos blockpos = new BlockPos(this);
/*     */       
/* 144 */       if (blockpos.equals(this.lastPosition)) {
/*     */         
/* 146 */         this.transferTicker--;
/*     */       }
/*     */       else {
/*     */         
/* 150 */         setTransferTicker(0);
/*     */       } 
/*     */       
/* 153 */       if (!canTransfer()) {
/*     */         
/* 155 */         setTransferTicker(0);
/*     */         
/* 157 */         if (captureDroppedItems()) {
/*     */           
/* 159 */           setTransferTicker(4);
/* 160 */           markDirty();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean captureDroppedItems() {
/* 168 */     if (TileEntityHopper.captureDroppedItems(this))
/*     */     {
/* 170 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 174 */     List<EntityItem> list = this.world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(0.25D, 0.0D, 0.25D), EntitySelectors.IS_ALIVE);
/*     */     
/* 176 */     if (!list.isEmpty())
/*     */     {
/* 178 */       TileEntityHopper.putDropInInventoryAllSlots(null, (IInventory)this, list.get(0));
/*     */     }
/*     */     
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/* 187 */     super.killMinecart(source);
/*     */     
/* 189 */     if (this.world.getGameRules().getBoolean("doEntityDrops"))
/*     */     {
/* 191 */       dropItemWithOffset(Item.getItemFromBlock((Block)Blocks.HOPPER), 1, 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesMinecartHopper(DataFixer fixer) {
/* 197 */     EntityMinecartContainer.func_190574_b(fixer, EntityMinecartHopper.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/* 205 */     super.writeEntityToNBT(compound);
/* 206 */     compound.setInteger("TransferCooldown", this.transferTicker);
/* 207 */     compound.setBoolean("Enabled", this.isBlocked);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/* 215 */     super.readEntityFromNBT(compound);
/* 216 */     this.transferTicker = compound.getInteger("TransferCooldown");
/* 217 */     this.isBlocked = compound.hasKey("Enabled") ? compound.getBoolean("Enabled") : true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransferTicker(int p_98042_1_) {
/* 225 */     this.transferTicker = p_98042_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTransfer() {
/* 233 */     return (this.transferTicker > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 238 */     return "minecraft:hopper";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 243 */     return (Container)new ContainerHopper(playerInventory, (IInventory)this, playerIn);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityMinecartHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */