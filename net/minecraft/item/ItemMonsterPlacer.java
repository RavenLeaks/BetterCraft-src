/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemMonsterPlacer
/*     */   extends Item
/*     */ {
/*     */   public ItemMonsterPlacer() {
/*  40 */     setCreativeTab(CreativeTabs.MISC);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  45 */     String s = I18n.translateToLocal(String.valueOf(getUnlocalizedName()) + ".name").trim();
/*  46 */     String s1 = EntityList.func_191302_a(func_190908_h(stack));
/*     */     
/*  48 */     if (s1 != null)
/*     */     {
/*  50 */       s = String.valueOf(s) + " " + I18n.translateToLocal("entity." + s1 + ".name");
/*     */     }
/*     */     
/*  53 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
/*  61 */     ItemStack itemstack = stack.getHeldItem(pos);
/*     */     
/*  63 */     if (playerIn.isRemote)
/*     */     {
/*  65 */       return EnumActionResult.SUCCESS;
/*     */     }
/*  67 */     if (!stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack))
/*     */     {
/*  69 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/*  73 */     IBlockState iblockstate = playerIn.getBlockState(worldIn);
/*  74 */     Block block = iblockstate.getBlock();
/*     */     
/*  76 */     if (block == Blocks.MOB_SPAWNER) {
/*     */       
/*  78 */       TileEntity tileentity = playerIn.getTileEntity(worldIn);
/*     */       
/*  80 */       if (tileentity instanceof TileEntityMobSpawner) {
/*     */         
/*  82 */         MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
/*  83 */         mobspawnerbaselogic.func_190894_a(func_190908_h(itemstack));
/*  84 */         tileentity.markDirty();
/*  85 */         playerIn.notifyBlockUpdate(worldIn, iblockstate, iblockstate, 3);
/*     */         
/*  87 */         if (!stack.capabilities.isCreativeMode)
/*     */         {
/*  89 */           itemstack.func_190918_g(1);
/*     */         }
/*     */         
/*  92 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     BlockPos blockpos = worldIn.offset(hand);
/*  97 */     double d0 = func_190909_a(playerIn, blockpos);
/*  98 */     Entity entity = spawnCreature(playerIn, func_190908_h(itemstack), blockpos.getX() + 0.5D, blockpos.getY() + d0, blockpos.getZ() + 0.5D);
/*     */     
/* 100 */     if (entity != null) {
/*     */       
/* 102 */       if (entity instanceof net.minecraft.entity.EntityLivingBase && itemstack.hasDisplayName())
/*     */       {
/* 104 */         entity.setCustomNameTag(itemstack.getDisplayName());
/*     */       }
/*     */       
/* 107 */       applyItemEntityDataToEntity(playerIn, stack, itemstack, entity);
/*     */       
/* 109 */       if (!stack.capabilities.isCreativeMode)
/*     */       {
/* 111 */         itemstack.func_190918_g(1);
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return EnumActionResult.SUCCESS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double func_190909_a(World p_190909_1_, BlockPos p_190909_2_) {
/* 121 */     AxisAlignedBB axisalignedbb = (new AxisAlignedBB(p_190909_2_)).addCoord(0.0D, -1.0D, 0.0D);
/* 122 */     List<AxisAlignedBB> list = p_190909_1_.getCollisionBoxes(null, axisalignedbb);
/*     */     
/* 124 */     if (list.isEmpty())
/*     */     {
/* 126 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/* 130 */     double d0 = axisalignedbb.minY;
/*     */     
/* 132 */     for (AxisAlignedBB axisalignedbb1 : list)
/*     */     {
/* 134 */       d0 = Math.max(axisalignedbb1.maxY, d0);
/*     */     }
/*     */     
/* 137 */     return d0 - p_190909_2_.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void applyItemEntityDataToEntity(World entityWorld, @Nullable EntityPlayer player, ItemStack stack, @Nullable Entity targetEntity) {
/* 146 */     MinecraftServer minecraftserver = entityWorld.getMinecraftServer();
/*     */     
/* 148 */     if (minecraftserver != null && targetEntity != null) {
/*     */       
/* 150 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/* 152 */       if (nbttagcompound != null && nbttagcompound.hasKey("EntityTag", 10)) {
/*     */         
/* 154 */         if (!entityWorld.isRemote && targetEntity.ignoreItemEntityData() && (player == null || !minecraftserver.getPlayerList().canSendCommands(player.getGameProfile()))) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 159 */         NBTTagCompound nbttagcompound1 = targetEntity.writeToNBT(new NBTTagCompound());
/* 160 */         UUID uuid = targetEntity.getUniqueID();
/* 161 */         nbttagcompound1.merge(nbttagcompound.getCompoundTag("EntityTag"));
/* 162 */         targetEntity.setUniqueId(uuid);
/* 163 */         targetEntity.readFromNBT(nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
/* 170 */     ItemStack itemstack = worldIn.getHeldItem(playerIn);
/*     */     
/* 172 */     if (itemStackIn.isRemote)
/*     */     {
/* 174 */       return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */ 
/*     */     
/* 178 */     RayTraceResult raytraceresult = rayTrace(itemStackIn, worldIn, true);
/*     */     
/* 180 */     if (raytraceresult != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
/*     */       
/* 182 */       BlockPos blockpos = raytraceresult.getBlockPos();
/*     */       
/* 184 */       if (!(itemStackIn.getBlockState(blockpos).getBlock() instanceof net.minecraft.block.BlockLiquid))
/*     */       {
/* 186 */         return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */       }
/* 188 */       if (itemStackIn.isBlockModifiable(worldIn, blockpos) && worldIn.canPlayerEdit(blockpos, raytraceresult.sideHit, itemstack)) {
/*     */         
/* 190 */         Entity entity = spawnCreature(itemStackIn, func_190908_h(itemstack), blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D);
/*     */         
/* 192 */         if (entity == null)
/*     */         {
/* 194 */           return new ActionResult(EnumActionResult.PASS, itemstack);
/*     */         }
/*     */ 
/*     */         
/* 198 */         if (entity instanceof net.minecraft.entity.EntityLivingBase && itemstack.hasDisplayName())
/*     */         {
/* 200 */           entity.setCustomNameTag(itemstack.getDisplayName());
/*     */         }
/*     */         
/* 203 */         applyItemEntityDataToEntity(itemStackIn, worldIn, itemstack, entity);
/*     */         
/* 205 */         if (!worldIn.capabilities.isCreativeMode)
/*     */         {
/* 207 */           itemstack.func_190918_g(1);
/*     */         }
/*     */         
/* 210 */         worldIn.addStat(StatList.getObjectUseStats(this));
/* 211 */         return new ActionResult(EnumActionResult.SUCCESS, itemstack);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 216 */       return new ActionResult(EnumActionResult.FAIL, itemstack);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 221 */     return new ActionResult(EnumActionResult.PASS, itemstack);
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
/*     */   @Nullable
/*     */   public static Entity spawnCreature(World worldIn, @Nullable ResourceLocation entityID, double x, double y, double z) {
/* 234 */     if (entityID != null && EntityList.ENTITY_EGGS.containsKey(entityID)) {
/*     */       
/* 236 */       Entity entity = null;
/*     */       
/* 238 */       for (int i = 0; i < 1; i++) {
/*     */         
/* 240 */         entity = EntityList.createEntityByIDFromName(entityID, worldIn);
/*     */         
/* 242 */         if (entity instanceof EntityLiving) {
/*     */           
/* 244 */           EntityLiving entityliving = (EntityLiving)entity;
/* 245 */           entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
/* 246 */           entityliving.rotationYawHead = entityliving.rotationYaw;
/* 247 */           entityliving.renderYawOffset = entityliving.rotationYaw;
/* 248 */           entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), null);
/* 249 */           worldIn.spawnEntityInWorld(entity);
/* 250 */           entityliving.playLivingSound();
/*     */         } 
/*     */       } 
/*     */       
/* 254 */       return entity;
/*     */     } 
/*     */ 
/*     */     
/* 258 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 267 */     if (func_194125_a(itemIn))
/*     */     {
/* 269 */       for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.ENTITY_EGGS.values()) {
/*     */         
/* 271 */         ItemStack itemstack = new ItemStack(this, 1);
/* 272 */         applyEntityIdToItemStack(itemstack, entitylist$entityegginfo.spawnedID);
/* 273 */         tab.add(itemstack);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void applyEntityIdToItemStack(ItemStack stack, ResourceLocation entityId) {
/* 283 */     NBTTagCompound nbttagcompound = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
/* 284 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 285 */     nbttagcompound1.setString("id", entityId.toString());
/* 286 */     nbttagcompound.setTag("EntityTag", (NBTBase)nbttagcompound1);
/* 287 */     stack.setTagCompound(nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ResourceLocation func_190908_h(ItemStack p_190908_0_) {
/* 293 */     NBTTagCompound nbttagcompound = p_190908_0_.getTagCompound();
/*     */     
/* 295 */     if (nbttagcompound == null)
/*     */     {
/* 297 */       return null;
/*     */     }
/* 299 */     if (!nbttagcompound.hasKey("EntityTag", 10))
/*     */     {
/* 301 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 305 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("EntityTag");
/*     */     
/* 307 */     if (!nbttagcompound1.hasKey("id", 8))
/*     */     {
/* 309 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 313 */     String s = nbttagcompound1.getString("id");
/* 314 */     ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */     
/* 316 */     if (!s.contains(":"))
/*     */     {
/* 318 */       nbttagcompound1.setString("id", resourcelocation.toString());
/*     */     }
/*     */     
/* 321 */     return resourcelocation;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\ItemMonsterPlacer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */