/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockRenderLayer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class CompiledChunk
/*    */ {
/* 12 */   public static final CompiledChunk DUMMY = new CompiledChunk()
/*    */     {
/*    */       protected void setLayerUsed(BlockRenderLayer layer)
/*    */       {
/* 16 */         throw new UnsupportedOperationException();
/*    */       }
/*    */       
/*    */       public void setLayerStarted(BlockRenderLayer layer) {
/* 20 */         throw new UnsupportedOperationException();
/*    */       }
/*    */       
/*    */       public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/* 24 */         return false;
/*    */       }
/*    */     };
/* 27 */   private final boolean[] layersUsed = new boolean[(BlockRenderLayer.values()).length];
/* 28 */   private final boolean[] layersStarted = new boolean[(BlockRenderLayer.values()).length];
/*    */   private boolean empty = true;
/* 30 */   private final List<TileEntity> tileEntities = Lists.newArrayList();
/* 31 */   private SetVisibility setVisibility = new SetVisibility();
/*    */   
/*    */   private BufferBuilder.State state;
/*    */   
/*    */   public boolean isEmpty() {
/* 36 */     return this.empty;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setLayerUsed(BlockRenderLayer layer) {
/* 41 */     this.empty = false;
/* 42 */     this.layersUsed[layer.ordinal()] = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLayerEmpty(BlockRenderLayer layer) {
/* 47 */     return !this.layersUsed[layer.ordinal()];
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLayerStarted(BlockRenderLayer layer) {
/* 52 */     this.layersStarted[layer.ordinal()] = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLayerStarted(BlockRenderLayer layer) {
/* 57 */     return this.layersStarted[layer.ordinal()];
/*    */   }
/*    */ 
/*    */   
/*    */   public List<TileEntity> getTileEntities() {
/* 62 */     return this.tileEntities;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addTileEntity(TileEntity tileEntityIn) {
/* 67 */     this.tileEntities.add(tileEntityIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
/* 72 */     return this.setVisibility.isVisible(facing, facing2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVisibility(SetVisibility visibility) {
/* 77 */     this.setVisibility = visibility;
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferBuilder.State getState() {
/* 82 */     return this.state;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setState(BufferBuilder.State stateIn) {
/* 87 */     this.state = stateIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\chunk\CompiledChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */