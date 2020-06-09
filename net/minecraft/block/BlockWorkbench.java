/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerWorkbench;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.world.IInteractionObject;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockWorkbench
/*    */   extends Block
/*    */ {
/*    */   protected BlockWorkbench() {
/* 24 */     super(Material.WOOD);
/* 25 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 30 */     if (worldIn.isRemote)
/*    */     {
/* 32 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 36 */     playerIn.displayGui(new InterfaceCraftingTable(worldIn, pos));
/* 37 */     playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public static class InterfaceCraftingTable
/*    */     implements IInteractionObject
/*    */   {
/*    */     private final World world;
/*    */     private final BlockPos position;
/*    */     
/*    */     public InterfaceCraftingTable(World worldIn, BlockPos pos) {
/* 49 */       this.world = worldIn;
/* 50 */       this.position = pos;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getName() {
/* 55 */       return "crafting_table";
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean hasCustomName() {
/* 60 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public ITextComponent getDisplayName() {
/* 65 */       return (ITextComponent)new TextComponentTranslation(String.valueOf(Blocks.CRAFTING_TABLE.getUnlocalizedName()) + ".name", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/*    */     public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 70 */       return (Container)new ContainerWorkbench(playerInventory, this.world, this.position);
/*    */     }
/*    */ 
/*    */     
/*    */     public String getGuiID() {
/* 75 */       return "minecraft:crafting_table";
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockWorkbench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */