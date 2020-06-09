/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.InventoryBasic;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class EntityAIVillagerInteract
/*    */   extends EntityAIWatchClosest2 {
/*    */   private int interactionDelay;
/*    */   private final EntityVillager villager;
/*    */   
/*    */   public EntityAIVillagerInteract(EntityVillager villagerIn) {
/* 19 */     super((EntityLiving)villagerIn, (Class)EntityVillager.class, 3.0F, 0.02F);
/* 20 */     this.villager = villagerIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 28 */     super.startExecuting();
/*    */     
/* 30 */     if (this.villager.canAbondonItems() && this.closestEntity instanceof EntityVillager && ((EntityVillager)this.closestEntity).wantsMoreFood()) {
/*    */       
/* 32 */       this.interactionDelay = 10;
/*    */     }
/*    */     else {
/*    */       
/* 36 */       this.interactionDelay = 0;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 45 */     super.updateTask();
/*    */     
/* 47 */     if (this.interactionDelay > 0) {
/*    */       
/* 49 */       this.interactionDelay--;
/*    */       
/* 51 */       if (this.interactionDelay == 0) {
/*    */         
/* 53 */         InventoryBasic inventorybasic = this.villager.getVillagerInventory();
/*    */         
/* 55 */         for (int i = 0; i < inventorybasic.getSizeInventory(); i++) {
/*    */           
/* 57 */           ItemStack itemstack = inventorybasic.getStackInSlot(i);
/* 58 */           ItemStack itemstack1 = ItemStack.field_190927_a;
/*    */           
/* 60 */           if (!itemstack.func_190926_b()) {
/*    */             
/* 62 */             Item item = itemstack.getItem();
/*    */             
/* 64 */             if ((item == Items.BREAD || item == Items.POTATO || item == Items.CARROT || item == Items.BEETROOT) && itemstack.func_190916_E() > 3) {
/*    */               
/* 66 */               int l = itemstack.func_190916_E() / 2;
/* 67 */               itemstack.func_190918_g(l);
/* 68 */               itemstack1 = new ItemStack(item, l, itemstack.getMetadata());
/*    */             }
/* 70 */             else if (item == Items.WHEAT && itemstack.func_190916_E() > 5) {
/*    */               
/* 72 */               int j = itemstack.func_190916_E() / 2 / 3 * 3;
/* 73 */               int k = j / 3;
/* 74 */               itemstack.func_190918_g(j);
/* 75 */               itemstack1 = new ItemStack(Items.BREAD, k, 0);
/*    */             } 
/*    */             
/* 78 */             if (itemstack.func_190926_b())
/*    */             {
/* 80 */               inventorybasic.setInventorySlotContents(i, ItemStack.field_190927_a);
/*    */             }
/*    */           } 
/*    */           
/* 84 */           if (!itemstack1.func_190926_b()) {
/*    */             
/* 86 */             double d0 = this.villager.posY - 0.30000001192092896D + this.villager.getEyeHeight();
/* 87 */             EntityItem entityitem = new EntityItem(this.villager.world, this.villager.posX, d0, this.villager.posZ, itemstack1);
/* 88 */             float f = 0.3F;
/* 89 */             float f1 = this.villager.rotationYawHead;
/* 90 */             float f2 = this.villager.rotationPitch;
/* 91 */             entityitem.motionX = (-MathHelper.sin(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F) * 0.3F);
/* 92 */             entityitem.motionZ = (MathHelper.cos(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F) * 0.3F);
/* 93 */             entityitem.motionY = (-MathHelper.sin(f2 * 0.017453292F) * 0.3F + 0.1F);
/* 94 */             entityitem.setDefaultPickupDelay();
/* 95 */             this.villager.world.spawnEntityInWorld((Entity)entityitem);
/*    */             break;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIVillagerInteract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */