/*    */ package net.minecraft.entity.passive;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityAgeable;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.storage.loot.LootTableList;
/*    */ 
/*    */ public class EntityMooshroom extends EntityCow {
/*    */   public EntityMooshroom(World worldIn) {
/* 23 */     super(worldIn);
/* 24 */     setSize(0.9F, 1.4F);
/* 25 */     this.spawnableBlock = (Block)Blocks.MYCELIUM;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesMooshroom(DataFixer fixer) {
/* 30 */     EntityLiving.registerFixesMob(fixer, EntityMooshroom.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 35 */     ItemStack itemstack = player.getHeldItem(hand);
/*    */     
/* 37 */     if (itemstack.getItem() == Items.BOWL && getGrowingAge() >= 0 && !player.capabilities.isCreativeMode) {
/*    */       
/* 39 */       itemstack.func_190918_g(1);
/*    */       
/* 41 */       if (itemstack.func_190926_b()) {
/*    */         
/* 43 */         player.setHeldItem(hand, new ItemStack(Items.MUSHROOM_STEW));
/*    */       }
/* 45 */       else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.MUSHROOM_STEW))) {
/*    */         
/* 47 */         player.dropItem(new ItemStack(Items.MUSHROOM_STEW), false);
/*    */       } 
/*    */       
/* 50 */       return true;
/*    */     } 
/* 52 */     if (itemstack.getItem() == Items.SHEARS && getGrowingAge() >= 0) {
/*    */       
/* 54 */       setDead();
/* 55 */       this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + (this.height / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*    */       
/* 57 */       if (!this.world.isRemote) {
/*    */         
/* 59 */         EntityCow entitycow = new EntityCow(this.world);
/* 60 */         entitycow.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 61 */         entitycow.setHealth(getHealth());
/* 62 */         entitycow.renderYawOffset = this.renderYawOffset;
/*    */         
/* 64 */         if (hasCustomName())
/*    */         {
/* 66 */           entitycow.setCustomNameTag(getCustomNameTag());
/*    */         }
/*    */         
/* 69 */         this.world.spawnEntityInWorld((Entity)entitycow);
/*    */         
/* 71 */         for (int i = 0; i < 5; i++)
/*    */         {
/* 73 */           this.world.spawnEntityInWorld((Entity)new EntityItem(this.world, this.posX, this.posY + this.height, this.posZ, new ItemStack((Block)Blocks.RED_MUSHROOM)));
/*    */         }
/*    */         
/* 76 */         itemstack.damageItem(1, (EntityLivingBase)player);
/* 77 */         playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
/*    */       } 
/*    */       
/* 80 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 84 */     return super.processInteract(player, hand);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityMooshroom createChild(EntityAgeable ageable) {
/* 90 */     return new EntityMooshroom(this.world);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getLootTable() {
/* 96 */     return LootTableList.ENTITIES_MUSHROOM_COW;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityMooshroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */