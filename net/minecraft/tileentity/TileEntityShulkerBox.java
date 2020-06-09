/*     */ package net.minecraft.tileentity;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockShulkerBox;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerShulkerBox;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ 
/*     */ public class TileEntityShulkerBox extends TileEntityLockableLoot implements ITickable, ISidedInventory {
/*  33 */   private static final int[] field_190595_a = new int[27];
/*     */   
/*     */   private NonNullList<ItemStack> field_190596_f;
/*     */   private boolean field_190597_g;
/*     */   private int field_190598_h;
/*     */   private AnimationStatus field_190599_i;
/*     */   private float field_190600_j;
/*     */   private float field_190601_k;
/*     */   private EnumDyeColor field_190602_l;
/*     */   private boolean field_190594_p;
/*     */   
/*     */   public TileEntityShulkerBox() {
/*  45 */     this((EnumDyeColor)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityShulkerBox(@Nullable EnumDyeColor p_i47242_1_) {
/*  50 */     this.field_190596_f = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
/*  51 */     this.field_190599_i = AnimationStatus.CLOSED;
/*  52 */     this.field_190602_l = p_i47242_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  60 */     func_190583_o();
/*     */     
/*  62 */     if (this.field_190599_i == AnimationStatus.OPENING || this.field_190599_i == AnimationStatus.CLOSING)
/*     */     {
/*  64 */       func_190589_G();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_190583_o() {
/*  70 */     this.field_190601_k = this.field_190600_j;
/*     */     
/*  72 */     switch (this.field_190599_i) {
/*     */       
/*     */       case null:
/*  75 */         this.field_190600_j = 0.0F;
/*     */         break;
/*     */       
/*     */       case OPENING:
/*  79 */         this.field_190600_j += 0.1F;
/*     */         
/*  81 */         if (this.field_190600_j >= 1.0F) {
/*     */           
/*  83 */           func_190589_G();
/*  84 */           this.field_190599_i = AnimationStatus.OPENED;
/*  85 */           this.field_190600_j = 1.0F;
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case CLOSING:
/*  91 */         this.field_190600_j -= 0.1F;
/*     */         
/*  93 */         if (this.field_190600_j <= 0.0F) {
/*     */           
/*  95 */           this.field_190599_i = AnimationStatus.CLOSED;
/*  96 */           this.field_190600_j = 0.0F;
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case OPENED:
/* 102 */         this.field_190600_j = 1.0F;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public AnimationStatus func_190591_p() {
/* 108 */     return this.field_190599_i;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_190584_a(IBlockState p_190584_1_) {
/* 113 */     return func_190587_b((EnumFacing)p_190584_1_.getValue((IProperty)BlockShulkerBox.field_190957_a));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_190587_b(EnumFacing p_190587_1_) {
/* 118 */     return Block.FULL_BLOCK_AABB.addCoord((0.5F * func_190585_a(1.0F) * p_190587_1_.getFrontOffsetX()), (0.5F * func_190585_a(1.0F) * p_190587_1_.getFrontOffsetY()), (0.5F * func_190585_a(1.0F) * p_190587_1_.getFrontOffsetZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   private AxisAlignedBB func_190588_c(EnumFacing p_190588_1_) {
/* 123 */     EnumFacing enumfacing = p_190588_1_.getOpposite();
/* 124 */     return func_190587_b(p_190588_1_).func_191195_a(enumfacing.getFrontOffsetX(), enumfacing.getFrontOffsetY(), enumfacing.getFrontOffsetZ());
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190589_G() {
/* 129 */     IBlockState iblockstate = this.world.getBlockState(getPos());
/*     */     
/* 131 */     if (iblockstate.getBlock() instanceof BlockShulkerBox) {
/*     */       
/* 133 */       EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)BlockShulkerBox.field_190957_a);
/* 134 */       AxisAlignedBB axisalignedbb = func_190588_c(enumfacing).offset(this.pos);
/* 135 */       List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb);
/*     */       
/* 137 */       if (!list.isEmpty())
/*     */       {
/* 139 */         for (int i = 0; i < list.size(); i++) {
/*     */           
/* 141 */           Entity entity = list.get(i);
/*     */           
/* 143 */           if (entity.getPushReaction() != EnumPushReaction.IGNORE) {
/*     */             
/* 145 */             double d0 = 0.0D;
/* 146 */             double d1 = 0.0D;
/* 147 */             double d2 = 0.0D;
/* 148 */             AxisAlignedBB axisalignedbb1 = entity.getEntityBoundingBox();
/*     */             
/* 150 */             switch (enumfacing.getAxis()) {
/*     */               
/*     */               case null:
/* 153 */                 if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) {
/*     */                   
/* 155 */                   d0 = axisalignedbb.maxX - axisalignedbb1.minX;
/*     */                 }
/*     */                 else {
/*     */                   
/* 159 */                   d0 = axisalignedbb1.maxX - axisalignedbb.minX;
/*     */                 } 
/*     */                 
/* 162 */                 d0 += 0.01D;
/*     */                 break;
/*     */               
/*     */               case Y:
/* 166 */                 if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) {
/*     */                   
/* 168 */                   d1 = axisalignedbb.maxY - axisalignedbb1.minY;
/*     */                 }
/*     */                 else {
/*     */                   
/* 172 */                   d1 = axisalignedbb1.maxY - axisalignedbb.minY;
/*     */                 } 
/*     */                 
/* 175 */                 d1 += 0.01D;
/*     */                 break;
/*     */               
/*     */               case Z:
/* 179 */                 if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) {
/*     */                   
/* 181 */                   d2 = axisalignedbb.maxZ - axisalignedbb1.minZ;
/*     */                 }
/*     */                 else {
/*     */                   
/* 185 */                   d2 = axisalignedbb1.maxZ - axisalignedbb.minZ;
/*     */                 } 
/*     */                 
/* 188 */                 d2 += 0.01D;
/*     */                 break;
/*     */             } 
/* 191 */             entity.moveEntity(MoverType.SHULKER_BOX, d0 * enumfacing.getFrontOffsetX(), d1 * enumfacing.getFrontOffsetY(), d2 * enumfacing.getFrontOffsetZ());
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
/*     */   public int getSizeInventory() {
/* 203 */     return this.field_190596_f.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 211 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 216 */     if (id == 1) {
/*     */       
/* 218 */       this.field_190598_h = type;
/*     */       
/* 220 */       if (type == 0)
/*     */       {
/* 222 */         this.field_190599_i = AnimationStatus.CLOSING;
/*     */       }
/*     */       
/* 225 */       if (type == 1)
/*     */       {
/* 227 */         this.field_190599_i = AnimationStatus.OPENING;
/*     */       }
/*     */       
/* 230 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 234 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {
/* 240 */     if (!player.isSpectator()) {
/*     */       
/* 242 */       if (this.field_190598_h < 0)
/*     */       {
/* 244 */         this.field_190598_h = 0;
/*     */       }
/*     */       
/* 247 */       this.field_190598_h++;
/* 248 */       this.world.addBlockEvent(this.pos, getBlockType(), 1, this.field_190598_h);
/*     */       
/* 250 */       if (this.field_190598_h == 1)
/*     */       {
/* 252 */         this.world.playSound(null, this.pos, SoundEvents.field_191262_fB, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {
/* 259 */     if (!player.isSpectator()) {
/*     */       
/* 261 */       this.field_190598_h--;
/* 262 */       this.world.addBlockEvent(this.pos, getBlockType(), 1, this.field_190598_h);
/*     */       
/* 264 */       if (this.field_190598_h <= 0)
/*     */       {
/* 266 */         this.world.playSound(null, this.pos, SoundEvents.field_191261_fA, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 273 */     return (Container)new ContainerShulkerBox(playerInventory, (IInventory)this, playerIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 278 */     return "minecraft:shulker_box";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 286 */     return hasCustomName() ? this.field_190577_o : "container.shulkerBox";
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190593_a(DataFixer p_190593_0_) {
/* 291 */     p_190593_0_.registerWalker(FixTypes.BLOCK_ENTITY, (IDataWalker)new ItemStackDataLists(TileEntityShulkerBox.class, new String[] { "Items" }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 296 */     super.readFromNBT(compound);
/* 297 */     func_190586_e(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 302 */     super.writeToNBT(compound);
/* 303 */     return func_190580_f(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190586_e(NBTTagCompound p_190586_1_) {
/* 308 */     this.field_190596_f = NonNullList.func_191197_a(getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/* 310 */     if (!checkLootAndRead(p_190586_1_) && p_190586_1_.hasKey("Items", 9))
/*     */     {
/* 312 */       ItemStackHelper.func_191283_b(p_190586_1_, this.field_190596_f);
/*     */     }
/*     */     
/* 315 */     if (p_190586_1_.hasKey("CustomName", 8))
/*     */     {
/* 317 */       this.field_190577_o = p_190586_1_.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_190580_f(NBTTagCompound p_190580_1_) {
/* 323 */     if (!checkLootAndWrite(p_190580_1_))
/*     */     {
/* 325 */       ItemStackHelper.func_191281_a(p_190580_1_, this.field_190596_f, false);
/*     */     }
/*     */     
/* 328 */     if (hasCustomName())
/*     */     {
/* 330 */       p_190580_1_.setString("CustomName", this.field_190577_o);
/*     */     }
/*     */     
/* 333 */     if (!p_190580_1_.hasKey("Lock") && isLocked())
/*     */     {
/* 335 */       getLockCode().toNBT(p_190580_1_);
/*     */     }
/*     */     
/* 338 */     return p_190580_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NonNullList<ItemStack> func_190576_q() {
/* 343 */     return this.field_190596_f;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/* 348 */     for (ItemStack itemstack : this.field_190596_f) {
/*     */       
/* 350 */       if (!itemstack.func_190926_b())
/*     */       {
/* 352 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 356 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side) {
/* 361 */     return field_190595_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
/* 369 */     return !(Block.getBlockFromItem(itemStackIn.getItem()) instanceof BlockShulkerBox);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
/* 377 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 382 */     this.field_190597_g = true;
/* 383 */     super.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190590_r() {
/* 388 */     return this.field_190597_g;
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_190585_a(float p_190585_1_) {
/* 393 */     return this.field_190601_k + (this.field_190600_j - this.field_190601_k) * p_190585_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumDyeColor func_190592_s() {
/* 398 */     if (this.field_190602_l == null)
/*     */     {
/* 400 */       this.field_190602_l = BlockShulkerBox.func_190954_c(getBlockType());
/*     */     }
/*     */     
/* 403 */     return this.field_190602_l;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SPacketUpdateTileEntity getUpdatePacket() {
/* 409 */     return new SPacketUpdateTileEntity(this.pos, 10, getUpdateTag());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190581_E() {
/* 414 */     return this.field_190594_p;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190579_a(boolean p_190579_1_) {
/* 419 */     this.field_190594_p = p_190579_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190582_F() {
/* 424 */     return !(func_190581_E() && func_191420_l() && !hasCustomName() && this.lootTable == null);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 429 */     for (int i = 0; i < field_190595_a.length; field_190595_a[i] = i++);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum AnimationStatus
/*     */   {
/* 437 */     CLOSED,
/* 438 */     OPENING,
/* 439 */     OPENED,
/* 440 */     CLOSING;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityShulkerBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */