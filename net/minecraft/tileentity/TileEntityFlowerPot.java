/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityFlowerPot
/*    */   extends TileEntity
/*    */ {
/*    */   private Item flowerPotItem;
/*    */   private int flowerPotData;
/*    */   
/*    */   public TileEntityFlowerPot() {}
/*    */   
/*    */   public TileEntityFlowerPot(Item potItem, int potData) {
/* 22 */     this.flowerPotItem = potItem;
/* 23 */     this.flowerPotData = potData;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerFixesFlowerPot(DataFixer fixer) {}
/*    */ 
/*    */   
/*    */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 32 */     super.writeToNBT(compound);
/* 33 */     ResourceLocation resourcelocation = (ResourceLocation)Item.REGISTRY.getNameForObject(this.flowerPotItem);
/* 34 */     compound.setString("Item", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 35 */     compound.setInteger("Data", this.flowerPotData);
/* 36 */     return compound;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 41 */     super.readFromNBT(compound);
/*    */     
/* 43 */     if (compound.hasKey("Item", 8)) {
/*    */       
/* 45 */       this.flowerPotItem = Item.getByNameOrId(compound.getString("Item"));
/*    */     }
/*    */     else {
/*    */       
/* 49 */       this.flowerPotItem = Item.getItemById(compound.getInteger("Item"));
/*    */     } 
/*    */     
/* 52 */     this.flowerPotData = compound.getInteger("Data");
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SPacketUpdateTileEntity getUpdatePacket() {
/* 58 */     return new SPacketUpdateTileEntity(this.pos, 5, getUpdateTag());
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound getUpdateTag() {
/* 63 */     return writeToNBT(new NBTTagCompound());
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_190614_a(ItemStack p_190614_1_) {
/* 68 */     this.flowerPotItem = p_190614_1_.getItem();
/* 69 */     this.flowerPotData = p_190614_1_.getMetadata();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getFlowerItemStack() {
/* 74 */     return (this.flowerPotItem == null) ? ItemStack.field_190927_a : new ItemStack(this.flowerPotItem, 1, this.flowerPotData);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Item getFlowerPotItem() {
/* 80 */     return this.flowerPotItem;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFlowerPotData() {
/* 85 */     return this.flowerPotData;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityFlowerPot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */