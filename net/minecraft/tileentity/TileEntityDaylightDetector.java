/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.block.BlockDaylightDetector;
/*    */ import net.minecraft.util.ITickable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityDaylightDetector
/*    */   extends TileEntity
/*    */   implements ITickable
/*    */ {
/*    */   public void update() {
/* 13 */     if (this.world != null && !this.world.isRemote && this.world.getTotalWorldTime() % 20L == 0L) {
/*    */       
/* 15 */       this.blockType = getBlockType();
/*    */       
/* 17 */       if (this.blockType instanceof BlockDaylightDetector)
/*    */       {
/* 19 */         ((BlockDaylightDetector)this.blockType).updatePower(this.world, this.pos);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityDaylightDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */