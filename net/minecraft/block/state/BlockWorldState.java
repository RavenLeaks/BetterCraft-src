/*    */ package net.minecraft.block.state;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class BlockWorldState
/*    */ {
/*    */   private final World world;
/*    */   private final BlockPos pos;
/*    */   private final boolean forceLoad;
/*    */   private IBlockState state;
/*    */   private TileEntity tileEntity;
/*    */   private boolean tileEntityInitialized;
/*    */   
/*    */   public BlockWorldState(World worldIn, BlockPos posIn, boolean forceLoadIn) {
/* 20 */     this.world = worldIn;
/* 21 */     this.pos = posIn;
/* 22 */     this.forceLoad = forceLoadIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getBlockState() {
/* 32 */     if (this.state == null && (this.forceLoad || this.world.isBlockLoaded(this.pos)))
/*    */     {
/* 34 */       this.state = this.world.getBlockState(this.pos);
/*    */     }
/*    */     
/* 37 */     return this.state;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public TileEntity getTileEntity() {
/* 47 */     if (this.tileEntity == null && !this.tileEntityInitialized) {
/*    */       
/* 49 */       this.tileEntity = this.world.getTileEntity(this.pos);
/* 50 */       this.tileEntityInitialized = true;
/*    */     } 
/*    */     
/* 53 */     return this.tileEntity;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPos() {
/* 58 */     return this.pos;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Predicate<BlockWorldState> hasState(final Predicate<IBlockState> predicatesIn) {
/* 63 */     return new Predicate<BlockWorldState>()
/*    */       {
/*    */         public boolean apply(@Nullable BlockWorldState p_apply_1_)
/*    */         {
/* 67 */           return (p_apply_1_ != null && predicatesIn.apply(p_apply_1_.getBlockState()));
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\BlockWorldState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */