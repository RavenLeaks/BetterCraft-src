/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class SPacketMultiBlockChange
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private ChunkPos chunkPos;
/*     */   private BlockUpdateData[] changedBlocks;
/*     */   
/*     */   public SPacketMultiBlockChange() {}
/*     */   
/*     */   public SPacketMultiBlockChange(int p_i46959_1_, short[] p_i46959_2_, Chunk p_i46959_3_) {
/*  24 */     this.chunkPos = new ChunkPos(p_i46959_3_.xPosition, p_i46959_3_.zPosition);
/*  25 */     this.changedBlocks = new BlockUpdateData[p_i46959_1_];
/*     */     
/*  27 */     for (int i = 0; i < this.changedBlocks.length; i++)
/*     */     {
/*  29 */       this.changedBlocks[i] = new BlockUpdateData(p_i46959_2_[i], p_i46959_3_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  38 */     this.chunkPos = new ChunkPos(buf.readInt(), buf.readInt());
/*  39 */     this.changedBlocks = new BlockUpdateData[buf.readVarIntFromBuffer()];
/*     */     
/*  41 */     for (int i = 0; i < this.changedBlocks.length; i++)
/*     */     {
/*  43 */       this.changedBlocks[i] = new BlockUpdateData(buf.readShort(), (IBlockState)Block.BLOCK_STATE_IDS.getByValue(buf.readVarIntFromBuffer()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  52 */     buf.writeInt(this.chunkPos.chunkXPos);
/*  53 */     buf.writeInt(this.chunkPos.chunkZPos);
/*  54 */     buf.writeVarIntToBuffer(this.changedBlocks.length); byte b; int i;
/*     */     BlockUpdateData[] arrayOfBlockUpdateData;
/*  56 */     for (i = (arrayOfBlockUpdateData = this.changedBlocks).length, b = 0; b < i; ) { BlockUpdateData spacketmultiblockchange$blockupdatedata = arrayOfBlockUpdateData[b];
/*     */       
/*  58 */       buf.writeShort(spacketmultiblockchange$blockupdatedata.getOffset());
/*  59 */       buf.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(spacketmultiblockchange$blockupdatedata.getBlockState()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  68 */     handler.handleMultiBlockChange(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockUpdateData[] getChangedBlocks() {
/*  73 */     return this.changedBlocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public class BlockUpdateData
/*     */   {
/*     */     private final short offset;
/*     */     private final IBlockState blockState;
/*     */     
/*     */     public BlockUpdateData(short p_i46544_2_, IBlockState p_i46544_3_) {
/*  83 */       this.offset = p_i46544_2_;
/*  84 */       this.blockState = p_i46544_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockUpdateData(short p_i46545_2_, Chunk p_i46545_3_) {
/*  89 */       this.offset = p_i46545_2_;
/*  90 */       this.blockState = p_i46545_3_.getBlockState(getPos());
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos getPos() {
/*  95 */       return new BlockPos((Vec3i)SPacketMultiBlockChange.this.chunkPos.getBlock(this.offset >> 12 & 0xF, this.offset & 0xFF, this.offset >> 8 & 0xF));
/*     */     }
/*     */ 
/*     */     
/*     */     public short getOffset() {
/* 100 */       return this.offset;
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getBlockState() {
/* 105 */       return this.blockState;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketMultiBlockChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */