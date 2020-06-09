/*     */ package net.minecraft.world.chunk;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.BitArray;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class BlockStateContainer
/*     */   implements IBlockStatePaletteResizer {
/*  13 */   private static final IBlockStatePalette REGISTRY_BASED_PALETTE = new BlockStatePaletteRegistry();
/*  14 */   protected static final IBlockState AIR_BLOCK_STATE = Blocks.AIR.getDefaultState();
/*     */   
/*     */   protected BitArray storage;
/*     */   protected IBlockStatePalette palette;
/*     */   private int bits;
/*     */   
/*     */   public BlockStateContainer() {
/*  21 */     setBits(4);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getIndex(int x, int y, int z) {
/*  26 */     return y << 8 | z << 4 | x;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setBits(int bitsIn) {
/*  31 */     if (bitsIn != this.bits) {
/*     */       
/*  33 */       this.bits = bitsIn;
/*     */       
/*  35 */       if (this.bits <= 4) {
/*     */         
/*  37 */         this.bits = 4;
/*  38 */         this.palette = new BlockStatePaletteLinear(this.bits, this);
/*     */       }
/*  40 */       else if (this.bits <= 8) {
/*     */         
/*  42 */         this.palette = new BlockStatePaletteHashMap(this.bits, this);
/*     */       }
/*     */       else {
/*     */         
/*  46 */         this.palette = REGISTRY_BASED_PALETTE;
/*  47 */         this.bits = MathHelper.log2DeBruijn(Block.BLOCK_STATE_IDS.size());
/*     */       } 
/*     */       
/*  50 */       this.palette.idFor(AIR_BLOCK_STATE);
/*  51 */       this.storage = new BitArray(this.bits, 4096);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int onResize(int p_186008_1_, IBlockState state) {
/*  57 */     BitArray bitarray = this.storage;
/*  58 */     IBlockStatePalette iblockstatepalette = this.palette;
/*  59 */     setBits(p_186008_1_);
/*     */     
/*  61 */     for (int i = 0; i < bitarray.size(); i++) {
/*     */       
/*  63 */       IBlockState iblockstate = iblockstatepalette.getBlockState(bitarray.getAt(i));
/*     */       
/*  65 */       if (iblockstate != null)
/*     */       {
/*  67 */         set(i, iblockstate);
/*     */       }
/*     */     } 
/*     */     
/*  71 */     return this.palette.idFor(state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int x, int y, int z, IBlockState state) {
/*  76 */     set(getIndex(x, y, z), state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void set(int index, IBlockState state) {
/*  81 */     int i = this.palette.idFor(state);
/*  82 */     this.storage.setAt(index, i);
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState get(int x, int y, int z) {
/*  87 */     return get(getIndex(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBlockState get(int index) {
/*  92 */     IBlockState iblockstate = this.palette.getBlockState(this.storage.getAt(index));
/*  93 */     return (iblockstate == null) ? AIR_BLOCK_STATE : iblockstate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(PacketBuffer buf) {
/*  98 */     int i = buf.readByte();
/*     */     
/* 100 */     if (this.bits != i)
/*     */     {
/* 102 */       setBits(i);
/*     */     }
/*     */     
/* 105 */     this.palette.read(buf);
/* 106 */     buf.readLongArray(this.storage.getBackingLongArray());
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(PacketBuffer buf) {
/* 111 */     buf.writeByte(this.bits);
/* 112 */     this.palette.write(buf);
/* 113 */     buf.writeLongArray(this.storage.getBackingLongArray());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public NibbleArray getDataForNBT(byte[] p_186017_1_, NibbleArray p_186017_2_) {
/* 119 */     NibbleArray nibblearray = null;
/*     */     
/* 121 */     for (int i = 0; i < 4096; i++) {
/*     */       
/* 123 */       int j = Block.BLOCK_STATE_IDS.get(get(i));
/* 124 */       int k = i & 0xF;
/* 125 */       int l = i >> 8 & 0xF;
/* 126 */       int i1 = i >> 4 & 0xF;
/*     */       
/* 128 */       if ((j >> 12 & 0xF) != 0) {
/*     */         
/* 130 */         if (nibblearray == null)
/*     */         {
/* 132 */           nibblearray = new NibbleArray();
/*     */         }
/*     */         
/* 135 */         nibblearray.set(k, l, i1, j >> 12 & 0xF);
/*     */       } 
/*     */       
/* 138 */       p_186017_1_[i] = (byte)(j >> 4 & 0xFF);
/* 139 */       p_186017_2_.set(k, l, i1, j & 0xF);
/*     */     } 
/*     */     
/* 142 */     return nibblearray;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDataFromNBT(byte[] p_186019_1_, NibbleArray p_186019_2_, @Nullable NibbleArray p_186019_3_) {
/* 147 */     for (int i = 0; i < 4096; i++) {
/*     */       
/* 149 */       int j = i & 0xF;
/* 150 */       int k = i >> 8 & 0xF;
/* 151 */       int l = i >> 4 & 0xF;
/* 152 */       int i1 = (p_186019_3_ == null) ? 0 : p_186019_3_.get(j, k, l);
/* 153 */       int j1 = i1 << 12 | (p_186019_1_[i] & 0xFF) << 4 | p_186019_2_.get(j, k, l);
/* 154 */       set(i, (IBlockState)Block.BLOCK_STATE_IDS.getByValue(j1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 160 */     return 1 + this.palette.getSerializedState() + PacketBuffer.getVarIntSize(this.storage.size()) + (this.storage.getBackingLongArray()).length * 8;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\BlockStateContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */