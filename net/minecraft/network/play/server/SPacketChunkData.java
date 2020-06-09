/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ 
/*     */ 
/*     */ public class SPacketChunkData
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int chunkX;
/*     */   private int chunkZ;
/*     */   private int availableSections;
/*     */   private byte[] buffer;
/*     */   private List<NBTTagCompound> tileEntityTags;
/*     */   private boolean loadChunk;
/*     */   
/*     */   public SPacketChunkData() {}
/*     */   
/*     */   public SPacketChunkData(Chunk p_i47124_1_, int p_i47124_2_) {
/*  33 */     this.chunkX = p_i47124_1_.xPosition;
/*  34 */     this.chunkZ = p_i47124_1_.zPosition;
/*  35 */     this.loadChunk = (p_i47124_2_ == 65535);
/*  36 */     boolean flag = (p_i47124_1_.getWorld()).provider.func_191066_m();
/*  37 */     this.buffer = new byte[calculateChunkSize(p_i47124_1_, flag, p_i47124_2_)];
/*  38 */     this.availableSections = extractChunkData(new PacketBuffer(getWriteBuffer()), p_i47124_1_, flag, p_i47124_2_);
/*  39 */     this.tileEntityTags = Lists.newArrayList();
/*     */     
/*  41 */     for (Map.Entry<BlockPos, TileEntity> entry : (Iterable<Map.Entry<BlockPos, TileEntity>>)p_i47124_1_.getTileEntityMap().entrySet()) {
/*     */       
/*  43 */       BlockPos blockpos = entry.getKey();
/*  44 */       TileEntity tileentity = entry.getValue();
/*  45 */       int i = blockpos.getY() >> 4;
/*     */       
/*  47 */       if (doChunkLoad() || (p_i47124_2_ & 1 << i) != 0) {
/*     */         
/*  49 */         NBTTagCompound nbttagcompound = tileentity.getUpdateTag();
/*  50 */         this.tileEntityTags.add(nbttagcompound);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  60 */     this.chunkX = buf.readInt();
/*  61 */     this.chunkZ = buf.readInt();
/*  62 */     this.loadChunk = buf.readBoolean();
/*  63 */     this.availableSections = buf.readVarIntFromBuffer();
/*  64 */     int i = buf.readVarIntFromBuffer();
/*     */     
/*  66 */     if (i > 2097152)
/*     */     {
/*  68 */       throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
/*     */     }
/*     */ 
/*     */     
/*  72 */     this.buffer = new byte[i];
/*  73 */     buf.readBytes(this.buffer);
/*  74 */     int j = buf.readVarIntFromBuffer();
/*  75 */     this.tileEntityTags = Lists.newArrayList();
/*     */     
/*  77 */     for (int k = 0; k < j; k++)
/*     */     {
/*  79 */       this.tileEntityTags.add(buf.readNBTTagCompoundFromBuffer());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  89 */     buf.writeInt(this.chunkX);
/*  90 */     buf.writeInt(this.chunkZ);
/*  91 */     buf.writeBoolean(this.loadChunk);
/*  92 */     buf.writeVarIntToBuffer(this.availableSections);
/*  93 */     buf.writeVarIntToBuffer(this.buffer.length);
/*  94 */     buf.writeBytes(this.buffer);
/*  95 */     buf.writeVarIntToBuffer(this.tileEntityTags.size());
/*     */     
/*  97 */     for (NBTTagCompound nbttagcompound : this.tileEntityTags)
/*     */     {
/*  99 */       buf.writeNBTTagCompoundToBuffer(nbttagcompound);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 108 */     handler.handleChunkData(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketBuffer getReadBuffer() {
/* 113 */     return new PacketBuffer(Unpooled.wrappedBuffer(this.buffer));
/*     */   }
/*     */ 
/*     */   
/*     */   private ByteBuf getWriteBuffer() {
/* 118 */     ByteBuf bytebuf = Unpooled.wrappedBuffer(this.buffer);
/* 119 */     bytebuf.writerIndex(0);
/* 120 */     return bytebuf;
/*     */   }
/*     */ 
/*     */   
/*     */   public int extractChunkData(PacketBuffer p_189555_1_, Chunk p_189555_2_, boolean p_189555_3_, int p_189555_4_) {
/* 125 */     int i = 0;
/* 126 */     ExtendedBlockStorage[] aextendedblockstorage = p_189555_2_.getBlockStorageArray();
/* 127 */     int j = 0;
/*     */     
/* 129 */     for (int k = aextendedblockstorage.length; j < k; j++) {
/*     */       
/* 131 */       ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[j];
/*     */       
/* 133 */       if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE && (!doChunkLoad() || !extendedblockstorage.isEmpty()) && (p_189555_4_ & 1 << j) != 0) {
/*     */         
/* 135 */         i |= 1 << j;
/* 136 */         extendedblockstorage.getData().write(p_189555_1_);
/* 137 */         p_189555_1_.writeBytes(extendedblockstorage.getBlocklightArray().getData());
/*     */         
/* 139 */         if (p_189555_3_)
/*     */         {
/* 141 */           p_189555_1_.writeBytes(extendedblockstorage.getSkylightArray().getData());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     if (doChunkLoad())
/*     */     {
/* 148 */       p_189555_1_.writeBytes(p_189555_2_.getBiomeArray());
/*     */     }
/*     */     
/* 151 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int calculateChunkSize(Chunk chunkIn, boolean p_189556_2_, int p_189556_3_) {
/* 156 */     int i = 0;
/* 157 */     ExtendedBlockStorage[] aextendedblockstorage = chunkIn.getBlockStorageArray();
/* 158 */     int j = 0;
/*     */     
/* 160 */     for (int k = aextendedblockstorage.length; j < k; j++) {
/*     */       
/* 162 */       ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[j];
/*     */       
/* 164 */       if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE && (!doChunkLoad() || !extendedblockstorage.isEmpty()) && (p_189556_3_ & 1 << j) != 0) {
/*     */         
/* 166 */         i += extendedblockstorage.getData().getSerializedSize();
/* 167 */         i += (extendedblockstorage.getBlocklightArray().getData()).length;
/*     */         
/* 169 */         if (p_189556_2_)
/*     */         {
/* 171 */           i += (extendedblockstorage.getSkylightArray().getData()).length;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     if (doChunkLoad())
/*     */     {
/* 178 */       i += (chunkIn.getBiomeArray()).length;
/*     */     }
/*     */     
/* 181 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkX() {
/* 186 */     return this.chunkX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkZ() {
/* 191 */     return this.chunkZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getExtractedSize() {
/* 196 */     return this.availableSections;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doChunkLoad() {
/* 201 */     return this.loadChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<NBTTagCompound> getTileEntityTags() {
/* 206 */     return this.tileEntityTags;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketChunkData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */