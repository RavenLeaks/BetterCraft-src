/*     */ package net.minecraft.init;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Random;
/*     */ import net.minecraft.advancements.AdvancementManager;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.BlockFire;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.BlockPumpkin;
/*     */ import net.minecraft.block.BlockShulkerBox;
/*     */ import net.minecraft.block.BlockSkull;
/*     */ import net.minecraft.block.BlockTNT;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.BehaviorProjectileDispense;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.dispenser.IPosition;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.entity.projectile.EntitySpectralArrow;
/*     */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemBucket;
/*     */ import net.minecraft.item.ItemDye;
/*     */ import net.minecraft.item.ItemMonsterPlacer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionHelper;
/*     */ import net.minecraft.potion.PotionType;
/*     */ import net.minecraft.server.DebugLoggingPrintStream;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.LoggingPrintStream;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Bootstrap
/*     */ {
/*  75 */   public static final PrintStream SYSOUT = System.out;
/*     */   
/*     */   private static boolean alreadyRegistered;
/*     */   
/*     */   public static boolean field_194219_b;
/*  80 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRegistered() {
/*  87 */     return alreadyRegistered;
/*     */   }
/*     */ 
/*     */   
/*     */   static void registerDispenserBehaviors() {
/*  92 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.ARROW, new BehaviorProjectileDispense()
/*     */         {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
/*     */           {
/*  96 */             EntityTippedArrow entitytippedarrow = new EntityTippedArrow(worldIn, position.getX(), position.getY(), position.getZ());
/*  97 */             entitytippedarrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
/*  98 */             return (IProjectile)entitytippedarrow;
/*     */           }
/*     */         });
/* 101 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.TIPPED_ARROW, new BehaviorProjectileDispense()
/*     */         {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
/*     */           {
/* 105 */             EntityTippedArrow entitytippedarrow = new EntityTippedArrow(worldIn, position.getX(), position.getY(), position.getZ());
/* 106 */             entitytippedarrow.setPotionEffect(stackIn);
/* 107 */             entitytippedarrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
/* 108 */             return (IProjectile)entitytippedarrow;
/*     */           }
/*     */         });
/* 111 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SPECTRAL_ARROW, new BehaviorProjectileDispense()
/*     */         {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
/*     */           {
/* 115 */             EntitySpectralArrow entitySpectralArrow = new EntitySpectralArrow(worldIn, position.getX(), position.getY(), position.getZ());
/* 116 */             ((EntityArrow)entitySpectralArrow).pickupStatus = EntityArrow.PickupStatus.ALLOWED;
/* 117 */             return (IProjectile)entitySpectralArrow;
/*     */           }
/*     */         });
/* 120 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.EGG, new BehaviorProjectileDispense()
/*     */         {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
/*     */           {
/* 124 */             return (IProjectile)new EntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
/*     */           }
/*     */         });
/* 127 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SNOWBALL, new BehaviorProjectileDispense()
/*     */         {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
/*     */           {
/* 131 */             return (IProjectile)new EntitySnowball(worldIn, position.getX(), position.getY(), position.getZ());
/*     */           }
/*     */         });
/* 134 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.EXPERIENCE_BOTTLE, new BehaviorProjectileDispense()
/*     */         {
/*     */           protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
/*     */           {
/* 138 */             return (IProjectile)new EntityExpBottle(worldIn, position.getX(), position.getY(), position.getZ());
/*     */           }
/*     */           
/*     */           protected float getProjectileInaccuracy() {
/* 142 */             return super.getProjectileInaccuracy() * 0.5F;
/*     */           }
/*     */           
/*     */           protected float getProjectileVelocity() {
/* 146 */             return super.getProjectileVelocity() * 1.25F;
/*     */           }
/*     */         });
/* 149 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SPLASH_POTION, new IBehaviorDispenseItem()
/*     */         {
/*     */           public ItemStack dispense(IBlockSource source, final ItemStack stack)
/*     */           {
/* 153 */             return (new BehaviorProjectileDispense()
/*     */               {
/*     */                 protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
/*     */                 {
/* 157 */                   return (IProjectile)new EntityPotion(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy());
/*     */                 }
/*     */                 
/*     */                 protected float getProjectileInaccuracy() {
/* 161 */                   return super.getProjectileInaccuracy() * 0.5F;
/*     */                 }
/*     */                 
/*     */                 protected float getProjectileVelocity() {
/* 165 */                   return super.getProjectileVelocity() * 1.25F;
/*     */                 }
/* 167 */               }).dispense(source, stack);
/*     */           }
/*     */         });
/* 170 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.LINGERING_POTION, new IBehaviorDispenseItem()
/*     */         {
/*     */           public ItemStack dispense(IBlockSource source, final ItemStack stack)
/*     */           {
/* 174 */             return (new BehaviorProjectileDispense()
/*     */               {
/*     */                 protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
/*     */                 {
/* 178 */                   return (IProjectile)new EntityPotion(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy());
/*     */                 }
/*     */                 
/*     */                 protected float getProjectileInaccuracy() {
/* 182 */                   return super.getProjectileInaccuracy() * 0.5F;
/*     */                 }
/*     */                 
/*     */                 protected float getProjectileVelocity() {
/* 186 */                   return super.getProjectileVelocity() * 1.25F;
/*     */                 }
/* 188 */               }).dispense(source, stack);
/*     */           }
/*     */         });
/* 191 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SPAWN_EGG, new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 195 */             EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING);
/* 196 */             double d0 = source.getX() + enumfacing.getFrontOffsetX();
/* 197 */             double d1 = ((source.getBlockPos().getY() + enumfacing.getFrontOffsetY()) + 0.2F);
/* 198 */             double d2 = source.getZ() + enumfacing.getFrontOffsetZ();
/* 199 */             Entity entity = ItemMonsterPlacer.spawnCreature(source.getWorld(), ItemMonsterPlacer.func_190908_h(stack), d0, d1, d2);
/*     */             
/* 201 */             if (entity instanceof net.minecraft.entity.EntityLivingBase && stack.hasDisplayName())
/*     */             {
/* 203 */               entity.setCustomNameTag(stack.getDisplayName());
/*     */             }
/*     */             
/* 206 */             ItemMonsterPlacer.applyItemEntityDataToEntity(source.getWorld(), null, stack, entity);
/* 207 */             stack.func_190918_g(1);
/* 208 */             return stack;
/*     */           }
/*     */         });
/* 211 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.FIREWORKS, new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 215 */             EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING);
/* 216 */             double d0 = source.getX() + enumfacing.getFrontOffsetX();
/* 217 */             double d1 = (source.getBlockPos().getY() + 0.2F);
/* 218 */             double d2 = source.getZ() + enumfacing.getFrontOffsetZ();
/* 219 */             EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(source.getWorld(), d0, d1, d2, stack);
/* 220 */             source.getWorld().spawnEntityInWorld((Entity)entityfireworkrocket);
/* 221 */             stack.func_190918_g(1);
/* 222 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 226 */             source.getWorld().playEvent(1004, source.getBlockPos(), 0);
/*     */           }
/*     */         });
/* 229 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.FIRE_CHARGE, new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 233 */             EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING);
/* 234 */             IPosition iposition = BlockDispenser.getDispensePosition(source);
/* 235 */             double d0 = iposition.getX() + (enumfacing.getFrontOffsetX() * 0.3F);
/* 236 */             double d1 = iposition.getY() + (enumfacing.getFrontOffsetY() * 0.3F);
/* 237 */             double d2 = iposition.getZ() + (enumfacing.getFrontOffsetZ() * 0.3F);
/* 238 */             World world = source.getWorld();
/* 239 */             Random random = world.rand;
/* 240 */             double d3 = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetX();
/* 241 */             double d4 = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetY();
/* 242 */             double d5 = random.nextGaussian() * 0.05D + enumfacing.getFrontOffsetZ();
/* 243 */             world.spawnEntityInWorld((Entity)new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5));
/* 244 */             stack.func_190918_g(1);
/* 245 */             return stack;
/*     */           }
/*     */           
/*     */           protected void playDispenseSound(IBlockSource source) {
/* 249 */             source.getWorld().playEvent(1018, source.getBlockPos(), 0);
/*     */           }
/*     */         });
/* 252 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.BOAT, new BehaviorDispenseBoat(EntityBoat.Type.OAK));
/* 253 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SPRUCE_BOAT, new BehaviorDispenseBoat(EntityBoat.Type.SPRUCE));
/* 254 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.BIRCH_BOAT, new BehaviorDispenseBoat(EntityBoat.Type.BIRCH));
/* 255 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.JUNGLE_BOAT, new BehaviorDispenseBoat(EntityBoat.Type.JUNGLE));
/* 256 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.DARK_OAK_BOAT, new BehaviorDispenseBoat(EntityBoat.Type.DARK_OAK));
/* 257 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.ACACIA_BOAT, new BehaviorDispenseBoat(EntityBoat.Type.ACACIA));
/* 258 */     BehaviorDefaultDispenseItem behaviorDefaultDispenseItem = new BehaviorDefaultDispenseItem()
/*     */       {
/* 260 */         private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();
/*     */         
/*     */         public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 263 */           ItemBucket itembucket = (ItemBucket)stack.getItem();
/* 264 */           BlockPos blockpos = source.getBlockPos().offset((EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING));
/* 265 */           return itembucket.tryPlaceContainedLiquid(null, source.getWorld(), blockpos) ? new ItemStack(Items.BUCKET) : this.dispenseBehavior.dispense(source, stack);
/*     */         }
/*     */       };
/* 268 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.LAVA_BUCKET, behaviorDefaultDispenseItem);
/* 269 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.WATER_BUCKET, behaviorDefaultDispenseItem);
/* 270 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.BUCKET, new BehaviorDefaultDispenseItem()
/*     */         {
/* 272 */           private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();
/*     */           public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/*     */             Item item;
/* 275 */             World world = source.getWorld();
/* 276 */             BlockPos blockpos = source.getBlockPos().offset((EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING));
/* 277 */             IBlockState iblockstate = world.getBlockState(blockpos);
/* 278 */             Block block = iblockstate.getBlock();
/* 279 */             Material material = iblockstate.getMaterial();
/*     */ 
/*     */             
/* 282 */             if (Material.WATER.equals(material) && block instanceof BlockLiquid && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/*     */               
/* 284 */               item = Items.WATER_BUCKET;
/*     */             }
/*     */             else {
/*     */               
/* 288 */               if (!Material.LAVA.equals(material) || !(block instanceof BlockLiquid) || ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() != 0)
/*     */               {
/* 290 */                 return super.dispenseStack(source, stack);
/*     */               }
/*     */               
/* 293 */               item = Items.LAVA_BUCKET;
/*     */             } 
/*     */             
/* 296 */             world.setBlockToAir(blockpos);
/* 297 */             stack.func_190918_g(1);
/*     */             
/* 299 */             if (stack.func_190926_b())
/*     */             {
/* 301 */               return new ItemStack(item);
/*     */             }
/*     */ 
/*     */             
/* 305 */             if (((TileEntityDispenser)source.getBlockTileEntity()).addItemStack(new ItemStack(item)) < 0)
/*     */             {
/* 307 */               this.dispenseBehavior.dispense(source, new ItemStack(item));
/*     */             }
/*     */             
/* 310 */             return stack;
/*     */           }
/*     */         });
/*     */     
/* 314 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.FLINT_AND_STEEL, new BehaviorDispenseOptional()
/*     */         {
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 318 */             World world = source.getWorld();
/* 319 */             this.field_190911_b = true;
/* 320 */             BlockPos blockpos = source.getBlockPos().offset((EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING));
/*     */             
/* 322 */             if (world.isAirBlock(blockpos)) {
/*     */               
/* 324 */               world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
/*     */               
/* 326 */               if (stack.attemptDamageItem(1, world.rand, null))
/*     */               {
/* 328 */                 stack.func_190920_e(0);
/*     */               }
/*     */             }
/* 331 */             else if (world.getBlockState(blockpos).getBlock() == Blocks.TNT) {
/*     */               
/* 333 */               Blocks.TNT.onBlockDestroyedByPlayer(world, blockpos, Blocks.TNT.getDefaultState().withProperty((IProperty)BlockTNT.EXPLODE, Boolean.valueOf(true)));
/* 334 */               world.setBlockToAir(blockpos);
/*     */             }
/*     */             else {
/*     */               
/* 338 */               this.field_190911_b = false;
/*     */             } 
/*     */             
/* 341 */             return stack;
/*     */           }
/*     */         });
/* 344 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.DYE, new BehaviorDispenseOptional()
/*     */         {
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 348 */             this.field_190911_b = true;
/*     */             
/* 350 */             if (EnumDyeColor.WHITE == EnumDyeColor.byDyeDamage(stack.getMetadata())) {
/*     */               
/* 352 */               World world = source.getWorld();
/* 353 */               BlockPos blockpos = source.getBlockPos().offset((EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING));
/*     */               
/* 355 */               if (ItemDye.applyBonemeal(stack, world, blockpos)) {
/*     */                 
/* 357 */                 if (!world.isRemote)
/*     */                 {
/* 359 */                   world.playEvent(2005, blockpos, 0);
/*     */                 }
/*     */               }
/*     */               else {
/*     */                 
/* 364 */                 this.field_190911_b = false;
/*     */               } 
/*     */               
/* 367 */               return stack;
/*     */             } 
/*     */ 
/*     */             
/* 371 */             return super.dispenseStack(source, stack);
/*     */           }
/*     */         });
/*     */     
/* 375 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Item.getItemFromBlock(Blocks.TNT), new BehaviorDefaultDispenseItem()
/*     */         {
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 379 */             World world = source.getWorld();
/* 380 */             BlockPos blockpos = source.getBlockPos().offset((EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING));
/* 381 */             EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D, null);
/* 382 */             world.spawnEntityInWorld((Entity)entitytntprimed);
/* 383 */             world.playSound(null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
/* 384 */             stack.func_190918_g(1);
/* 385 */             return stack;
/*     */           }
/*     */         });
/* 388 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SKULL, new BehaviorDispenseOptional()
/*     */         {
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 392 */             World world = source.getWorld();
/* 393 */             EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING);
/* 394 */             BlockPos blockpos = source.getBlockPos().offset(enumfacing);
/* 395 */             BlockSkull blockskull = Blocks.SKULL;
/* 396 */             this.field_190911_b = true;
/*     */             
/* 398 */             if (world.isAirBlock(blockpos) && blockskull.canDispenserPlace(world, blockpos, stack)) {
/*     */               
/* 400 */               if (!world.isRemote)
/*     */               {
/* 402 */                 world.setBlockState(blockpos, blockskull.getDefaultState().withProperty((IProperty)BlockSkull.FACING, (Comparable)EnumFacing.UP), 3);
/* 403 */                 TileEntity tileentity = world.getTileEntity(blockpos);
/*     */                 
/* 405 */                 if (tileentity instanceof TileEntitySkull) {
/*     */                   
/* 407 */                   if (stack.getMetadata() == 3) {
/*     */                     
/* 409 */                     GameProfile gameprofile = null;
/*     */                     
/* 411 */                     if (stack.hasTagCompound()) {
/*     */                       
/* 413 */                       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */                       
/* 415 */                       if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*     */                         
/* 417 */                         gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*     */                       }
/* 419 */                       else if (nbttagcompound.hasKey("SkullOwner", 8)) {
/*     */                         
/* 421 */                         String s = nbttagcompound.getString("SkullOwner");
/*     */                         
/* 423 */                         if (!StringUtils.isNullOrEmpty(s))
/*     */                         {
/* 425 */                           gameprofile = new GameProfile(null, s);
/*     */                         }
/*     */                       } 
/*     */                     } 
/*     */                     
/* 430 */                     ((TileEntitySkull)tileentity).setPlayerProfile(gameprofile);
/*     */                   }
/*     */                   else {
/*     */                     
/* 434 */                     ((TileEntitySkull)tileentity).setType(stack.getMetadata());
/*     */                   } 
/*     */                   
/* 437 */                   ((TileEntitySkull)tileentity).setSkullRotation(enumfacing.getOpposite().getHorizontalIndex() * 4);
/* 438 */                   Blocks.SKULL.checkWitherSpawn(world, blockpos, (TileEntitySkull)tileentity);
/*     */                 } 
/*     */                 
/* 441 */                 stack.func_190918_g(1);
/*     */               }
/*     */             
/* 444 */             } else if (ItemArmor.dispenseArmor(source, stack).func_190926_b()) {
/*     */               
/* 446 */               this.field_190911_b = false;
/*     */             } 
/*     */             
/* 449 */             return stack;
/*     */           }
/*     */         });
/* 452 */     BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Item.getItemFromBlock(Blocks.PUMPKIN), new BehaviorDispenseOptional()
/*     */         {
/*     */           protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */           {
/* 456 */             World world = source.getWorld();
/* 457 */             BlockPos blockpos = source.getBlockPos().offset((EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING));
/* 458 */             BlockPumpkin blockpumpkin = (BlockPumpkin)Blocks.PUMPKIN;
/* 459 */             this.field_190911_b = true;
/*     */             
/* 461 */             if (world.isAirBlock(blockpos) && blockpumpkin.canDispenserPlace(world, blockpos)) {
/*     */               
/* 463 */               if (!world.isRemote)
/*     */               {
/* 465 */                 world.setBlockState(blockpos, blockpumpkin.getDefaultState(), 3);
/*     */               }
/*     */               
/* 468 */               stack.func_190918_g(1);
/*     */             }
/*     */             else {
/*     */               
/* 472 */               ItemStack itemstack = ItemArmor.dispenseArmor(source, stack);
/*     */               
/* 474 */               if (itemstack.func_190926_b())
/*     */               {
/* 476 */                 this.field_190911_b = false;
/*     */               }
/*     */             } 
/*     */             
/* 480 */             return stack; } });
/*     */     byte b;
/*     */     int i;
/*     */     EnumDyeColor[] arrayOfEnumDyeColor;
/* 484 */     for (i = (arrayOfEnumDyeColor = EnumDyeColor.values()).length, b = 0; b < i; ) { EnumDyeColor enumdyecolor = arrayOfEnumDyeColor[b];
/*     */       
/* 486 */       BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Item.getItemFromBlock(BlockShulkerBox.func_190952_a(enumdyecolor)), new BehaviorDispenseShulkerBox(null));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register() {
/* 495 */     if (!alreadyRegistered) {
/*     */       
/* 497 */       alreadyRegistered = true;
/* 498 */       redirectOutputToLog();
/* 499 */       SoundEvent.registerSounds();
/* 500 */       Block.registerBlocks();
/* 501 */       BlockFire.init();
/* 502 */       Potion.registerPotions();
/* 503 */       Enchantment.registerEnchantments();
/* 504 */       Item.registerItems();
/* 505 */       PotionType.registerPotionTypes();
/* 506 */       PotionHelper.init();
/* 507 */       EntityList.init();
/* 508 */       Biome.registerBiomes();
/* 509 */       registerDispenserBehaviors();
/*     */       
/* 511 */       if (!CraftingManager.func_193377_a()) {
/*     */         
/* 513 */         field_194219_b = true;
/* 514 */         LOGGER.error("Errors with built-in recipes!");
/*     */       } 
/*     */       
/* 517 */       StatList.init();
/*     */       
/* 519 */       if (LOGGER.isDebugEnabled()) {
/*     */         
/* 521 */         if ((new AdvancementManager(null)).func_193767_b()) {
/*     */           
/* 523 */           field_194219_b = true;
/* 524 */           LOGGER.error("Errors with built-in advancements!");
/*     */         } 
/*     */         
/* 527 */         if (!LootTableList.func_193579_b()) {
/*     */           
/* 529 */           field_194219_b = true;
/* 530 */           LOGGER.error("Errors with built-in loot tables");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void redirectOutputToLog() {
/* 541 */     if (LOGGER.isDebugEnabled()) {
/*     */       
/* 543 */       System.setErr((PrintStream)new DebugLoggingPrintStream("STDERR", System.err));
/* 544 */       System.setOut((PrintStream)new DebugLoggingPrintStream("STDOUT", SYSOUT));
/*     */     }
/*     */     else {
/*     */       
/* 548 */       System.setErr((PrintStream)new LoggingPrintStream("STDERR", System.err));
/* 549 */       System.setOut((PrintStream)new LoggingPrintStream("STDOUT", SYSOUT));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void printToSYSOUT(String message) {
/* 555 */     SYSOUT.println(message);
/*     */   }
/*     */   
/*     */   public static class BehaviorDispenseBoat
/*     */     extends BehaviorDefaultDispenseItem {
/* 560 */     private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();
/*     */     
/*     */     private final EntityBoat.Type boatType;
/*     */     
/*     */     public BehaviorDispenseBoat(EntityBoat.Type boatTypeIn) {
/* 565 */       this.boatType = boatTypeIn;
/*     */     }
/*     */     
/*     */     public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/*     */       double d3;
/* 570 */       EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING);
/* 571 */       World world = source.getWorld();
/* 572 */       double d0 = source.getX() + (enumfacing.getFrontOffsetX() * 1.125F);
/* 573 */       double d1 = source.getY() + (enumfacing.getFrontOffsetY() * 1.125F);
/* 574 */       double d2 = source.getZ() + (enumfacing.getFrontOffsetZ() * 1.125F);
/* 575 */       BlockPos blockpos = source.getBlockPos().offset(enumfacing);
/* 576 */       Material material = world.getBlockState(blockpos).getMaterial();
/*     */ 
/*     */       
/* 579 */       if (Material.WATER.equals(material)) {
/*     */         
/* 581 */         d3 = 1.0D;
/*     */       }
/*     */       else {
/*     */         
/* 585 */         if (!Material.AIR.equals(material) || !Material.WATER.equals(world.getBlockState(blockpos.down()).getMaterial()))
/*     */         {
/* 587 */           return this.dispenseBehavior.dispense(source, stack);
/*     */         }
/*     */         
/* 590 */         d3 = 0.0D;
/*     */       } 
/*     */       
/* 593 */       EntityBoat entityboat = new EntityBoat(world, d0, d1 + d3, d2);
/* 594 */       entityboat.setBoatType(this.boatType);
/* 595 */       entityboat.rotationYaw = enumfacing.getHorizontalAngle();
/* 596 */       world.spawnEntityInWorld((Entity)entityboat);
/* 597 */       stack.func_190918_g(1);
/* 598 */       return stack;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void playDispenseSound(IBlockSource source) {
/* 603 */       source.getWorld().playEvent(1000, source.getBlockPos(), 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class BehaviorDispenseOptional extends BehaviorDefaultDispenseItem {
/*     */     public BehaviorDispenseOptional() {
/* 609 */       this.field_190911_b = true;
/*     */     }
/*     */     protected boolean field_190911_b;
/*     */     protected void playDispenseSound(IBlockSource source) {
/* 613 */       source.getWorld().playEvent(this.field_190911_b ? 1000 : 1001, source.getBlockPos(), 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class BehaviorDispenseShulkerBox
/*     */     extends BehaviorDispenseOptional
/*     */   {
/*     */     private BehaviorDispenseShulkerBox() {}
/*     */ 
/*     */     
/*     */     protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 625 */       Block block = Block.getBlockFromItem(stack.getItem());
/* 626 */       World world = source.getWorld();
/* 627 */       EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING);
/* 628 */       BlockPos blockpos = source.getBlockPos().offset(enumfacing);
/* 629 */       this.field_190911_b = world.func_190527_a(block, blockpos, false, EnumFacing.DOWN, null);
/*     */       
/* 631 */       if (this.field_190911_b) {
/*     */         
/* 633 */         EnumFacing enumfacing1 = world.isAirBlock(blockpos.down()) ? enumfacing : EnumFacing.UP;
/* 634 */         IBlockState iblockstate = block.getDefaultState().withProperty((IProperty)BlockShulkerBox.field_190957_a, (Comparable)enumfacing1);
/* 635 */         world.setBlockState(blockpos, iblockstate);
/* 636 */         TileEntity tileentity = world.getTileEntity(blockpos);
/* 637 */         ItemStack itemstack = stack.splitStack(1);
/*     */         
/* 639 */         if (itemstack.hasTagCompound())
/*     */         {
/* 641 */           ((TileEntityShulkerBox)tileentity).func_190586_e(itemstack.getTagCompound().getCompoundTag("BlockEntityTag"));
/*     */         }
/*     */         
/* 644 */         if (itemstack.hasDisplayName())
/*     */         {
/* 646 */           ((TileEntityShulkerBox)tileentity).func_190575_a(itemstack.getDisplayName());
/*     */         }
/*     */         
/* 649 */         world.updateComparatorOutputLevel(blockpos, iblockstate.getBlock());
/*     */       } 
/*     */       
/* 652 */       return stack;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\init\Bootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */