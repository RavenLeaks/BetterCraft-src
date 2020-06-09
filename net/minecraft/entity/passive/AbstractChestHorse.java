/*     */ package net.minecraft.entity.passive;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class AbstractChestHorse extends AbstractHorse {
/*  25 */   private static final DataParameter<Boolean> field_190698_bG = EntityDataManager.createKey(AbstractChestHorse.class, DataSerializers.BOOLEAN);
/*     */ 
/*     */   
/*     */   public AbstractChestHorse(World p_i47300_1_) {
/*  29 */     super(p_i47300_1_);
/*  30 */     this.field_190688_bE = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  35 */     super.entityInit();
/*  36 */     this.dataManager.register(field_190698_bG, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  41 */     super.applyEntityAttributes();
/*  42 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getModifiedMaxHealth());
/*  43 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.17499999701976776D);
/*  44 */     getEntityAttribute(JUMP_STRENGTH).setBaseValue(0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190695_dh() {
/*  49 */     return ((Boolean)this.dataManager.get(field_190698_bG)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChested(boolean chested) {
/*  54 */     this.dataManager.set(field_190698_bG, Boolean.valueOf(chested));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int func_190686_di() {
/*  59 */     return func_190695_dh() ? 17 : super.func_190686_di();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMountedYOffset() {
/*  67 */     return super.getMountedYOffset() - 0.25D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAngrySound() {
/*  72 */     super.getAngrySound();
/*  73 */     return SoundEvents.ENTITY_DONKEY_ANGRY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/*  81 */     super.onDeath(cause);
/*     */     
/*  83 */     if (func_190695_dh()) {
/*     */       
/*  85 */       if (!this.world.isRemote)
/*     */       {
/*  87 */         dropItem(Item.getItemFromBlock((Block)Blocks.CHEST), 1);
/*     */       }
/*     */       
/*  90 */       setChested(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190694_b(DataFixer p_190694_0_, Class<?> p_190694_1_) {
/*  96 */     AbstractHorse.func_190683_c(p_190694_0_, p_190694_1_);
/*  97 */     p_190694_0_.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackDataLists(p_190694_1_, new String[] { "Items" }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 105 */     super.writeEntityToNBT(compound);
/* 106 */     compound.setBoolean("ChestedHorse", func_190695_dh());
/*     */     
/* 108 */     if (func_190695_dh()) {
/*     */       
/* 110 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 112 */       for (int i = 2; i < this.horseChest.getSizeInventory(); i++) {
/*     */         
/* 114 */         ItemStack itemstack = this.horseChest.getStackInSlot(i);
/*     */         
/* 116 */         if (!itemstack.func_190926_b()) {
/*     */           
/* 118 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 119 */           nbttagcompound.setByte("Slot", (byte)i);
/* 120 */           itemstack.writeToNBT(nbttagcompound);
/* 121 */           nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */         } 
/*     */       } 
/*     */       
/* 125 */       compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 134 */     super.readEntityFromNBT(compound);
/* 135 */     setChested(compound.getBoolean("ChestedHorse"));
/*     */     
/* 137 */     if (func_190695_dh()) {
/*     */       
/* 139 */       NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 140 */       initHorseChest();
/*     */       
/* 142 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 144 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 145 */         int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */         
/* 147 */         if (j >= 2 && j < this.horseChest.getSizeInventory())
/*     */         {
/* 149 */           this.horseChest.setInventorySlotContents(j, new ItemStack(nbttagcompound));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     updateHorseSlots();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 159 */     if (inventorySlot == 499) {
/*     */       
/* 161 */       if (func_190695_dh() && itemStackIn.func_190926_b()) {
/*     */         
/* 163 */         setChested(false);
/* 164 */         initHorseChest();
/* 165 */         return true;
/*     */       } 
/*     */       
/* 168 */       if (!func_190695_dh() && itemStackIn.getItem() == Item.getItemFromBlock((Block)Blocks.CHEST)) {
/*     */         
/* 170 */         setChested(true);
/* 171 */         initHorseChest();
/* 172 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     return super.replaceItemInInventory(inventorySlot, itemStackIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 181 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/* 183 */     if (itemstack.getItem() == Items.SPAWN_EGG)
/*     */     {
/* 185 */       return super.processInteract(player, hand);
/*     */     }
/*     */ 
/*     */     
/* 189 */     if (!isChild()) {
/*     */       
/* 191 */       if (isTame() && player.isSneaking()) {
/*     */         
/* 193 */         openGUI(player);
/* 194 */         return true;
/*     */       } 
/*     */       
/* 197 */       if (isBeingRidden())
/*     */       {
/* 199 */         return super.processInteract(player, hand);
/*     */       }
/*     */     } 
/*     */     
/* 203 */     if (!itemstack.func_190926_b()) {
/*     */       
/* 205 */       boolean flag = func_190678_b(player, itemstack);
/*     */       
/* 207 */       if (!flag && !isTame()) {
/*     */         
/* 209 */         if (itemstack.interactWithEntity(player, (EntityLivingBase)this, hand))
/*     */         {
/* 211 */           return true;
/*     */         }
/*     */         
/* 214 */         func_190687_dF();
/* 215 */         return true;
/*     */       } 
/*     */       
/* 218 */       if (!flag && !func_190695_dh() && itemstack.getItem() == Item.getItemFromBlock((Block)Blocks.CHEST)) {
/*     */         
/* 220 */         setChested(true);
/* 221 */         func_190697_dk();
/* 222 */         flag = true;
/* 223 */         initHorseChest();
/*     */       } 
/*     */       
/* 226 */       if (!flag && !isChild() && !isHorseSaddled() && itemstack.getItem() == Items.SADDLE) {
/*     */         
/* 228 */         openGUI(player);
/* 229 */         return true;
/*     */       } 
/*     */       
/* 232 */       if (flag) {
/*     */         
/* 234 */         if (!player.capabilities.isCreativeMode)
/*     */         {
/* 236 */           itemstack.func_190918_g(1);
/*     */         }
/*     */         
/* 239 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     if (isChild())
/*     */     {
/* 245 */       return super.processInteract(player, hand);
/*     */     }
/* 247 */     if (itemstack.interactWithEntity(player, (EntityLivingBase)this, hand))
/*     */     {
/* 249 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 253 */     mountTo(player);
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_190697_dk() {
/* 261 */     playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_190696_dl() {
/* 266 */     return 5;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\AbstractChestHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */