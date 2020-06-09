/*    */ package wdl.api;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ITileEntityEditor
/*    */   extends IWDLMod
/*    */ {
/*    */   boolean shouldEdit(BlockPos paramBlockPos, NBTTagCompound paramNBTTagCompound, TileEntityCreationMode paramTileEntityCreationMode);
/*    */   
/*    */   void editTileEntity(BlockPos paramBlockPos, NBTTagCompound paramNBTTagCompound, TileEntityCreationMode paramTileEntityCreationMode);
/*    */   
/*    */   public enum TileEntityCreationMode
/*    */   {
/* 83 */     IMPORTED,
/*    */ 
/*    */ 
/*    */     
/* 87 */     EXISTING,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 92 */     NEW;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\ITileEntityEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */