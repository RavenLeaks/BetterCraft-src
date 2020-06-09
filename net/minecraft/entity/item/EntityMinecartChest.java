/*    */ package net.minecraft.entity.item;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockChest;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerChest;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.datafix.DataFixer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityMinecartChest extends EntityMinecartContainer {
/*    */   public EntityMinecartChest(World worldIn) {
/* 20 */     super(worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMinecartChest(World worldIn, double x, double y, double z) {
/* 25 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerFixesMinecartChest(DataFixer fixer) {
/* 30 */     EntityMinecartContainer.func_190574_b(fixer, EntityMinecartChest.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void killMinecart(DamageSource source) {
/* 35 */     super.killMinecart(source);
/*    */     
/* 37 */     if (this.world.getGameRules().getBoolean("doEntityDrops"))
/*    */     {
/* 39 */       dropItemWithOffset(Item.getItemFromBlock((Block)Blocks.CHEST), 1, 0.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSizeInventory() {
/* 48 */     return 27;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMinecart.Type getType() {
/* 53 */     return EntityMinecart.Type.CHEST;
/*    */   }
/*    */ 
/*    */   
/*    */   public IBlockState getDefaultDisplayTile() {
/* 58 */     return Blocks.CHEST.getDefaultState().withProperty((IProperty)BlockChest.FACING, (Comparable)EnumFacing.NORTH);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDefaultDisplayTileOffset() {
/* 63 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getGuiID() {
/* 68 */     return "minecraft:chest";
/*    */   }
/*    */ 
/*    */   
/*    */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 73 */     addLoot(playerIn);
/* 74 */     return (Container)new ContainerChest((IInventory)playerInventory, (IInventory)this, playerIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityMinecartChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */