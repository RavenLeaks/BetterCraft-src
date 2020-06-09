/*     */ package net.minecraft.entity.item;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityHanging;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemMap;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackData;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ public class EntityItemFrame extends EntityHanging {
/*  28 */   private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntityItemFrame.class, DataSerializers.OPTIONAL_ITEM_STACK);
/*  29 */   private static final DataParameter<Integer> ROTATION = EntityDataManager.createKey(EntityItemFrame.class, DataSerializers.VARINT);
/*     */ 
/*     */   
/*  32 */   private float itemDropChance = 1.0F;
/*     */ 
/*     */   
/*     */   public EntityItemFrame(World worldIn) {
/*  36 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityItemFrame(World worldIn, BlockPos p_i45852_2_, EnumFacing p_i45852_3_) {
/*  41 */     super(worldIn, p_i45852_2_);
/*  42 */     updateFacingWithBoundingBox(p_i45852_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  47 */     getDataManager().register(ITEM, ItemStack.field_190927_a);
/*  48 */     getDataManager().register(ROTATION, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCollisionBorderSize() {
/*  53 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  61 */     if (isEntityInvulnerable(source))
/*     */     {
/*  63 */       return false;
/*     */     }
/*  65 */     if (!source.isExplosion() && !getDisplayedItem().func_190926_b()) {
/*     */       
/*  67 */       if (!this.world.isRemote) {
/*     */         
/*  69 */         dropItemOrSelf(source.getEntity(), false);
/*  70 */         playSound(SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, 1.0F, 1.0F);
/*  71 */         setDisplayedItem(ItemStack.field_190927_a);
/*     */       } 
/*     */       
/*  74 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  78 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidthPixels() {
/*  84 */     return 12;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeightPixels() {
/*  89 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  97 */     double d0 = 16.0D;
/*  98 */     d0 = d0 * 64.0D * getRenderDistanceWeight();
/*  99 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBroken(@Nullable Entity brokenEntity) {
/* 107 */     playSound(SoundEvents.ENTITY_ITEMFRAME_BREAK, 1.0F, 1.0F);
/* 108 */     dropItemOrSelf(brokenEntity, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playPlaceSound() {
/* 113 */     playSound(SoundEvents.ENTITY_ITEMFRAME_PLACE, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropItemOrSelf(@Nullable Entity entityIn, boolean p_146065_2_) {
/* 118 */     if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/*     */       
/* 120 */       ItemStack itemstack = getDisplayedItem();
/*     */       
/* 122 */       if (entityIn instanceof EntityPlayer) {
/*     */         
/* 124 */         EntityPlayer entityplayer = (EntityPlayer)entityIn;
/*     */         
/* 126 */         if (entityplayer.capabilities.isCreativeMode) {
/*     */           
/* 128 */           removeFrameFromMap(itemstack);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 133 */       if (p_146065_2_)
/*     */       {
/* 135 */         entityDropItem(new ItemStack(Items.ITEM_FRAME), 0.0F);
/*     */       }
/*     */       
/* 138 */       if (!itemstack.func_190926_b() && this.rand.nextFloat() < this.itemDropChance) {
/*     */         
/* 140 */         itemstack = itemstack.copy();
/* 141 */         removeFrameFromMap(itemstack);
/* 142 */         entityDropItem(itemstack, 0.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeFrameFromMap(ItemStack stack) {
/* 152 */     if (!stack.func_190926_b()) {
/*     */       
/* 154 */       if (stack.getItem() == Items.FILLED_MAP) {
/*     */         
/* 156 */         MapData mapdata = ((ItemMap)stack.getItem()).getMapData(stack, this.world);
/* 157 */         mapdata.mapDecorations.remove("frame-" + getEntityId());
/*     */       } 
/*     */       
/* 160 */       stack.setItemFrame(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getDisplayedItem() {
/* 166 */     return (ItemStack)getDataManager().get(ITEM);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisplayedItem(ItemStack stack) {
/* 171 */     setDisplayedItemWithUpdate(stack, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDisplayedItemWithUpdate(ItemStack stack, boolean p_174864_2_) {
/* 176 */     if (!stack.func_190926_b()) {
/*     */       
/* 178 */       stack = stack.copy();
/* 179 */       stack.func_190920_e(1);
/* 180 */       stack.setItemFrame(this);
/*     */     } 
/*     */     
/* 183 */     getDataManager().set(ITEM, stack);
/* 184 */     getDataManager().setDirty(ITEM);
/*     */     
/* 186 */     if (!stack.func_190926_b())
/*     */     {
/* 188 */       playSound(SoundEvents.ENTITY_ITEMFRAME_ADD_ITEM, 1.0F, 1.0F);
/*     */     }
/*     */     
/* 191 */     if (p_174864_2_ && this.hangingPosition != null)
/*     */     {
/* 193 */       this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.AIR);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 199 */     if (key.equals(ITEM)) {
/*     */       
/* 201 */       ItemStack itemstack = getDisplayedItem();
/*     */       
/* 203 */       if (!itemstack.func_190926_b() && itemstack.getItemFrame() != this)
/*     */       {
/* 205 */         itemstack.setItemFrame(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRotation() {
/* 215 */     return ((Integer)getDataManager().get(ROTATION)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItemRotation(int rotationIn) {
/* 220 */     setRotation(rotationIn, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setRotation(int rotationIn, boolean p_174865_2_) {
/* 225 */     getDataManager().set(ROTATION, Integer.valueOf(rotationIn % 8));
/*     */     
/* 227 */     if (p_174865_2_ && this.hangingPosition != null)
/*     */     {
/* 229 */       this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.AIR);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesItemFrame(DataFixer fixer) {
/* 235 */     fixer.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackData(EntityItemFrame.class, new String[] { "Item" }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 243 */     if (!getDisplayedItem().func_190926_b()) {
/*     */       
/* 245 */       compound.setTag("Item", (NBTBase)getDisplayedItem().writeToNBT(new NBTTagCompound()));
/* 246 */       compound.setByte("ItemRotation", (byte)getRotation());
/* 247 */       compound.setFloat("ItemDropChance", this.itemDropChance);
/*     */     } 
/*     */     
/* 250 */     super.writeEntityToNBT(compound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 258 */     NBTTagCompound nbttagcompound = compound.getCompoundTag("Item");
/*     */     
/* 260 */     if (nbttagcompound != null && !nbttagcompound.hasNoTags()) {
/*     */       
/* 262 */       setDisplayedItemWithUpdate(new ItemStack(nbttagcompound), false);
/* 263 */       setRotation(compound.getByte("ItemRotation"), false);
/*     */       
/* 265 */       if (compound.hasKey("ItemDropChance", 99))
/*     */       {
/* 267 */         this.itemDropChance = compound.getFloat("ItemDropChance");
/*     */       }
/*     */     } 
/*     */     
/* 271 */     super.readEntityFromNBT(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
/* 276 */     ItemStack itemstack = player.getHeldItem(stack);
/*     */     
/* 278 */     if (!this.world.isRemote)
/*     */     {
/* 280 */       if (getDisplayedItem().func_190926_b()) {
/*     */         
/* 282 */         if (!itemstack.func_190926_b())
/*     */         {
/* 284 */           setDisplayedItem(itemstack);
/*     */           
/* 286 */           if (!player.capabilities.isCreativeMode)
/*     */           {
/* 288 */             itemstack.func_190918_g(1);
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 294 */         playSound(SoundEvents.ENTITY_ITEMFRAME_ROTATE_ITEM, 1.0F, 1.0F);
/* 295 */         setItemRotation(getRotation() + 1);
/*     */       } 
/*     */     }
/*     */     
/* 299 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutput() {
/* 304 */     return getDisplayedItem().func_190926_b() ? 0 : (getRotation() % 8 + 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityItemFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */