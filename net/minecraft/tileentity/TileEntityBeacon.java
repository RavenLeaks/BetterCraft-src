/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockStainedGlass;
/*     */ import net.minecraft.block.BlockStainedGlassPane;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerBeacon;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class TileEntityBeacon
/*     */   extends TileEntityLockable implements ITickable, ISidedInventory {
/*  38 */   public static final Potion[][] EFFECTS_LIST = new Potion[][] { { MobEffects.SPEED, MobEffects.HASTE }, { MobEffects.RESISTANCE, MobEffects.JUMP_BOOST }, { MobEffects.STRENGTH }, { MobEffects.REGENERATION } };
/*  39 */   private static final Set<Potion> VALID_EFFECTS = Sets.newHashSet();
/*  40 */   private final List<BeamSegment> beamSegments = Lists.newArrayList();
/*     */   
/*     */   private long beamRenderCounter;
/*     */   
/*     */   private float beamRenderScale;
/*     */   private boolean isComplete;
/*  46 */   private int levels = -1;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Potion primaryEffect;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Potion secondaryEffect;
/*     */ 
/*     */   
/*  57 */   private ItemStack payment = ItemStack.field_190927_a;
/*     */ 
/*     */   
/*     */   private String customName;
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  65 */     if (this.world.getTotalWorldTime() % 80L == 0L)
/*     */     {
/*  67 */       updateBeacon();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBeacon() {
/*  73 */     if (this.world != null) {
/*     */       
/*  75 */       updateSegmentColors();
/*  76 */       addEffectsToPlayers();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addEffectsToPlayers() {
/*  82 */     if (this.isComplete && this.levels > 0 && !this.world.isRemote && this.primaryEffect != null) {
/*     */       
/*  84 */       double d0 = (this.levels * 10 + 10);
/*  85 */       int i = 0;
/*     */       
/*  87 */       if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect)
/*     */       {
/*  89 */         i = 1;
/*     */       }
/*     */       
/*  92 */       int j = (9 + this.levels * 2) * 20;
/*  93 */       int k = this.pos.getX();
/*  94 */       int l = this.pos.getY();
/*  95 */       int i1 = this.pos.getZ();
/*  96 */       AxisAlignedBB axisalignedbb = (new AxisAlignedBB(k, l, i1, (k + 1), (l + 1), (i1 + 1))).expandXyz(d0).addCoord(0.0D, this.world.getHeight(), 0.0D);
/*  97 */       List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
/*     */       
/*  99 */       for (EntityPlayer entityplayer : list)
/*     */       {
/* 101 */         entityplayer.addPotionEffect(new PotionEffect(this.primaryEffect, j, i, true, true));
/*     */       }
/*     */       
/* 104 */       if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect != null)
/*     */       {
/* 106 */         for (EntityPlayer entityplayer1 : list)
/*     */         {
/* 108 */           entityplayer1.addPotionEffect(new PotionEffect(this.secondaryEffect, j, 0, true, true));
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateSegmentColors() {
/* 116 */     int i = this.pos.getX();
/* 117 */     int j = this.pos.getY();
/* 118 */     int k = this.pos.getZ();
/* 119 */     int l = this.levels;
/* 120 */     this.levels = 0;
/* 121 */     this.beamSegments.clear();
/* 122 */     this.isComplete = true;
/* 123 */     BeamSegment tileentitybeacon$beamsegment = new BeamSegment(EnumDyeColor.WHITE.func_193349_f());
/* 124 */     this.beamSegments.add(tileentitybeacon$beamsegment);
/* 125 */     boolean flag = true;
/* 126 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 128 */     for (int i1 = j + 1; i1 < 256; ) {
/*     */       float[] afloat;
/* 130 */       IBlockState iblockstate = this.world.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(i, i1, k));
/*     */ 
/*     */       
/* 133 */       if (iblockstate.getBlock() == Blocks.STAINED_GLASS)
/*     */       
/* 135 */       { afloat = ((EnumDyeColor)iblockstate.getValue((IProperty)BlockStainedGlass.COLOR)).func_193349_f(); }
/*     */       
/*     */       else
/*     */       
/* 139 */       { if (iblockstate.getBlock() != Blocks.STAINED_GLASS_PANE)
/*     */         
/* 141 */         { if (iblockstate.getLightOpacity() >= 15 && iblockstate.getBlock() != Blocks.BEDROCK) {
/*     */             
/* 143 */             this.isComplete = false;
/* 144 */             this.beamSegments.clear();
/*     */             
/*     */             break;
/*     */           } 
/* 148 */           tileentitybeacon$beamsegment.incrementHeight(); }
/*     */         
/*     */         else
/*     */         
/* 152 */         { afloat = ((EnumDyeColor)iblockstate.getValue((IProperty)BlockStainedGlassPane.COLOR)).func_193349_f();
/*     */ 
/*     */           
/* 155 */           if (!flag)
/*     */           {
/* 157 */             afloat = new float[] { (tileentitybeacon$beamsegment.getColors()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[2] + afloat[2]) / 2.0F }; }  }  i1++; }  if (!flag) afloat = new float[] { (tileentitybeacon$beamsegment.getColors()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beamsegment.getColors()[2] + afloat[2]) / 2.0F };
/*     */     
/*     */     } 
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
/* 173 */     if (this.isComplete) {
/*     */       
/* 175 */       for (int l1 = 1; l1 <= 4; this.levels = l1++) {
/*     */         
/* 177 */         int i2 = j - l1;
/*     */         
/* 179 */         if (i2 < 0) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 184 */         boolean flag1 = true;
/*     */         
/* 186 */         for (int j1 = i - l1; j1 <= i + l1 && flag1; j1++) {
/*     */           
/* 188 */           for (int k1 = k - l1; k1 <= k + l1; k1++) {
/*     */             
/* 190 */             Block block = this.world.getBlockState(new BlockPos(j1, i2, k1)).getBlock();
/*     */             
/* 192 */             if (block != Blocks.EMERALD_BLOCK && block != Blocks.GOLD_BLOCK && block != Blocks.DIAMOND_BLOCK && block != Blocks.IRON_BLOCK) {
/*     */               
/* 194 */               flag1 = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 200 */         if (!flag1) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 206 */       if (this.levels == 0)
/*     */       {
/* 208 */         this.isComplete = false;
/*     */       }
/*     */     } 
/*     */     
/* 212 */     if (!this.world.isRemote && l < this.levels)
/*     */     {
/* 214 */       for (EntityPlayerMP entityplayermp : this.world.getEntitiesWithinAABB(EntityPlayerMP.class, (new AxisAlignedBB(i, j, k, i, (j - 4), k)).expand(10.0D, 5.0D, 10.0D)))
/*     */       {
/* 216 */         CriteriaTriggers.field_192131_k.func_192180_a(entityplayermp, this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BeamSegment> getBeamSegments() {
/* 223 */     return this.beamSegments;
/*     */   }
/*     */ 
/*     */   
/*     */   public float shouldBeamRender() {
/* 228 */     if (!this.isComplete)
/*     */     {
/* 230 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 234 */     int i = (int)(this.world.getTotalWorldTime() - this.beamRenderCounter);
/* 235 */     this.beamRenderCounter = this.world.getTotalWorldTime();
/*     */     
/* 237 */     if (i > 1) {
/*     */       
/* 239 */       this.beamRenderScale -= i / 40.0F;
/*     */       
/* 241 */       if (this.beamRenderScale < 0.0F)
/*     */       {
/* 243 */         this.beamRenderScale = 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 247 */     this.beamRenderScale += 0.025F;
/*     */     
/* 249 */     if (this.beamRenderScale > 1.0F)
/*     */     {
/* 251 */       this.beamRenderScale = 1.0F;
/*     */     }
/*     */     
/* 254 */     return this.beamRenderScale;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_191979_s() {
/* 260 */     return this.levels;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SPacketUpdateTileEntity getUpdatePacket() {
/* 266 */     return new SPacketUpdateTileEntity(this.pos, 3, getUpdateTag());
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getUpdateTag() {
/* 271 */     return writeToNBT(new NBTTagCompound());
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxRenderDistanceSquared() {
/* 276 */     return 65536.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Potion isBeaconEffect(int p_184279_0_) {
/* 282 */     Potion potion = Potion.getPotionById(p_184279_0_);
/* 283 */     return VALID_EFFECTS.contains(potion) ? potion : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 288 */     super.readFromNBT(compound);
/* 289 */     this.primaryEffect = isBeaconEffect(compound.getInteger("Primary"));
/* 290 */     this.secondaryEffect = isBeaconEffect(compound.getInteger("Secondary"));
/* 291 */     this.levels = compound.getInteger("Levels");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 296 */     super.writeToNBT(compound);
/* 297 */     compound.setInteger("Primary", Potion.getIdFromPotion(this.primaryEffect));
/* 298 */     compound.setInteger("Secondary", Potion.getIdFromPotion(this.secondaryEffect));
/* 299 */     compound.setInteger("Levels", this.levels);
/* 300 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 308 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/* 313 */     return this.payment.func_190926_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 321 */     return (index == 0) ? this.payment : ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 329 */     if (index == 0 && !this.payment.func_190926_b()) {
/*     */       
/* 331 */       if (count >= this.payment.func_190916_E()) {
/*     */         
/* 333 */         ItemStack itemstack = this.payment;
/* 334 */         this.payment = ItemStack.field_190927_a;
/* 335 */         return itemstack;
/*     */       } 
/*     */ 
/*     */       
/* 339 */       return this.payment.splitStack(count);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 344 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 353 */     if (index == 0) {
/*     */       
/* 355 */       ItemStack itemstack = this.payment;
/* 356 */       this.payment = ItemStack.field_190927_a;
/* 357 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 361 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 370 */     if (index == 0)
/*     */     {
/* 372 */       this.payment = stack;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 381 */     return hasCustomName() ? this.customName : "container.beacon";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 389 */     return (this.customName != null && !this.customName.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 394 */     this.customName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 402 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 410 */     if (this.world.getTileEntity(this.pos) != this)
/*     */     {
/* 412 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 416 */     return (player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 434 */     return !(stack.getItem() != Items.EMERALD && stack.getItem() != Items.DIAMOND && stack.getItem() != Items.GOLD_INGOT && stack.getItem() != Items.IRON_INGOT);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 439 */     return "minecraft:beacon";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 444 */     return (Container)new ContainerBeacon((IInventory)playerInventory, (IInventory)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 449 */     switch (id) {
/*     */       
/*     */       case 0:
/* 452 */         return this.levels;
/*     */       
/*     */       case 1:
/* 455 */         return Potion.getIdFromPotion(this.primaryEffect);
/*     */       
/*     */       case 2:
/* 458 */         return Potion.getIdFromPotion(this.secondaryEffect);
/*     */     } 
/*     */     
/* 461 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 467 */     switch (id) {
/*     */       
/*     */       case 0:
/* 470 */         this.levels = value;
/*     */         break;
/*     */       
/*     */       case 1:
/* 474 */         this.primaryEffect = isBeaconEffect(value);
/*     */         break;
/*     */       
/*     */       case 2:
/* 478 */         this.secondaryEffect = isBeaconEffect(value);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFieldCount() {
/* 484 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 489 */     this.payment = ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 494 */     if (id == 1) {
/*     */       
/* 496 */       updateBeacon();
/* 497 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 501 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side) {
/* 507 */     return new int[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
/* 515 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
/* 523 */     return false;
/*     */   } static {
/*     */     byte b;
/*     */     int i;
/*     */     Potion[][] arrayOfPotion;
/* 528 */     for (i = (arrayOfPotion = EFFECTS_LIST).length, b = 0; b < i; ) { Potion[] apotion = arrayOfPotion[b];
/*     */       
/* 530 */       Collections.addAll(VALID_EFFECTS, apotion);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public static class BeamSegment
/*     */   {
/*     */     private final float[] colors;
/*     */     private int height;
/*     */     
/*     */     public BeamSegment(float[] colorsIn) {
/* 541 */       this.colors = colorsIn;
/* 542 */       this.height = 1;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void incrementHeight() {
/* 547 */       this.height++;
/*     */     }
/*     */ 
/*     */     
/*     */     public float[] getColors() {
/* 552 */       return this.colors;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 557 */       return this.height;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */