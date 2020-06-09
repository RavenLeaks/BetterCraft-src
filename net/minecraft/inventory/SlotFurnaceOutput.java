/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityXPOrb;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.FurnaceRecipes;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class SlotFurnaceOutput
/*    */   extends Slot
/*    */ {
/*    */   private final EntityPlayer thePlayer;
/*    */   private int removeCount;
/*    */   
/*    */   public SlotFurnaceOutput(EntityPlayer player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
/* 17 */     super(inventoryIn, slotIndex, xPosition, yPosition);
/* 18 */     this.thePlayer = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isItemValid(ItemStack stack) {
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack decrStackSize(int amount) {
/* 35 */     if (getHasStack())
/*    */     {
/* 37 */       this.removeCount += Math.min(amount, getStack().func_190916_E());
/*    */     }
/*    */     
/* 40 */     return super.decrStackSize(amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
/* 45 */     onCrafting(p_190901_2_);
/* 46 */     super.func_190901_a(p_190901_1_, p_190901_2_);
/* 47 */     return p_190901_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onCrafting(ItemStack stack, int amount) {
/* 56 */     this.removeCount += amount;
/* 57 */     onCrafting(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onCrafting(ItemStack stack) {
/* 65 */     stack.onCrafting(this.thePlayer.world, this.thePlayer, this.removeCount);
/*    */     
/* 67 */     if (!this.thePlayer.world.isRemote) {
/*    */       
/* 69 */       int i = this.removeCount;
/* 70 */       float f = FurnaceRecipes.instance().getSmeltingExperience(stack);
/*    */       
/* 72 */       if (f == 0.0F) {
/*    */         
/* 74 */         i = 0;
/*    */       }
/* 76 */       else if (f < 1.0F) {
/*    */         
/* 78 */         int j = MathHelper.floor(i * f);
/*    */         
/* 80 */         if (j < MathHelper.ceil(i * f) && Math.random() < (i * f - j))
/*    */         {
/* 82 */           j++;
/*    */         }
/*    */         
/* 85 */         i = j;
/*    */       } 
/*    */       
/* 88 */       while (i > 0) {
/*    */         
/* 90 */         int k = EntityXPOrb.getXPSplit(i);
/* 91 */         i -= k;
/* 92 */         this.thePlayer.world.spawnEntityInWorld((Entity)new EntityXPOrb(this.thePlayer.world, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, k));
/*    */       } 
/*    */     } 
/*    */     
/* 96 */     this.removeCount = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\SlotFurnaceOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */