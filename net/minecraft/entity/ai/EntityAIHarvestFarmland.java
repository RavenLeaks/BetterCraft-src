/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockCrops;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryBasic;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityAIHarvestFarmland
/*     */   extends EntityAIMoveToBlock
/*     */ {
/*     */   private final EntityVillager theVillager;
/*     */   private boolean hasFarmItem;
/*     */   private boolean wantsToReapStuff;
/*     */   private int currentTask;
/*     */   
/*     */   public EntityAIHarvestFarmland(EntityVillager theVillagerIn, double speedIn) {
/*  25 */     super((EntityCreature)theVillagerIn, speedIn, 16);
/*  26 */     this.theVillager = theVillagerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  34 */     if (this.runDelay <= 0) {
/*     */       
/*  36 */       if (!this.theVillager.world.getGameRules().getBoolean("mobGriefing"))
/*     */       {
/*  38 */         return false;
/*     */       }
/*     */       
/*  41 */       this.currentTask = -1;
/*  42 */       this.hasFarmItem = this.theVillager.isFarmItemInInventory();
/*  43 */       this.wantsToReapStuff = this.theVillager.wantsMoreFood();
/*     */     } 
/*     */     
/*  46 */     return super.shouldExecute();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  54 */     return (this.currentTask >= 0 && super.continueExecuting());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  62 */     super.updateTask();
/*  63 */     this.theVillager.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, (this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5D, 10.0F, this.theVillager.getVerticalFaceSpeed());
/*     */     
/*  65 */     if (getIsAboveDestination()) {
/*     */       
/*  67 */       World world = this.theVillager.world;
/*  68 */       BlockPos blockpos = this.destinationBlock.up();
/*  69 */       IBlockState iblockstate = world.getBlockState(blockpos);
/*  70 */       Block block = iblockstate.getBlock();
/*     */       
/*  72 */       if (this.currentTask == 0 && block instanceof BlockCrops && ((BlockCrops)block).isMaxAge(iblockstate)) {
/*     */         
/*  74 */         world.destroyBlock(blockpos, true);
/*     */       }
/*  76 */       else if (this.currentTask == 1 && iblockstate.getMaterial() == Material.AIR) {
/*     */         
/*  78 */         InventoryBasic inventorybasic = this.theVillager.getVillagerInventory();
/*     */         
/*  80 */         for (int i = 0; i < inventorybasic.getSizeInventory(); i++) {
/*     */           
/*  82 */           ItemStack itemstack = inventorybasic.getStackInSlot(i);
/*  83 */           boolean flag = false;
/*     */           
/*  85 */           if (!itemstack.func_190926_b())
/*     */           {
/*  87 */             if (itemstack.getItem() == Items.WHEAT_SEEDS) {
/*     */               
/*  89 */               world.setBlockState(blockpos, Blocks.WHEAT.getDefaultState(), 3);
/*  90 */               flag = true;
/*     */             }
/*  92 */             else if (itemstack.getItem() == Items.POTATO) {
/*     */               
/*  94 */               world.setBlockState(blockpos, Blocks.POTATOES.getDefaultState(), 3);
/*  95 */               flag = true;
/*     */             }
/*  97 */             else if (itemstack.getItem() == Items.CARROT) {
/*     */               
/*  99 */               world.setBlockState(blockpos, Blocks.CARROTS.getDefaultState(), 3);
/* 100 */               flag = true;
/*     */             }
/* 102 */             else if (itemstack.getItem() == Items.BEETROOT_SEEDS) {
/*     */               
/* 104 */               world.setBlockState(blockpos, Blocks.BEETROOTS.getDefaultState(), 3);
/* 105 */               flag = true;
/*     */             } 
/*     */           }
/*     */           
/* 109 */           if (flag) {
/*     */             
/* 111 */             itemstack.func_190918_g(1);
/*     */             
/* 113 */             if (itemstack.func_190926_b())
/*     */             {
/* 115 */               inventorybasic.setInventorySlotContents(i, ItemStack.field_190927_a);
/*     */             }
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 123 */       this.currentTask = -1;
/* 124 */       this.runDelay = 10;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
/* 133 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 135 */     if (block == Blocks.FARMLAND) {
/*     */       
/* 137 */       pos = pos.up();
/* 138 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/* 139 */       block = iblockstate.getBlock();
/*     */       
/* 141 */       if (block instanceof BlockCrops && ((BlockCrops)block).isMaxAge(iblockstate) && this.wantsToReapStuff && (this.currentTask == 0 || this.currentTask < 0)) {
/*     */         
/* 143 */         this.currentTask = 0;
/* 144 */         return true;
/*     */       } 
/*     */       
/* 147 */       if (iblockstate.getMaterial() == Material.AIR && this.hasFarmItem && (this.currentTask == 1 || this.currentTask < 0)) {
/*     */         
/* 149 */         this.currentTask = 1;
/* 150 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIHarvestFarmland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */