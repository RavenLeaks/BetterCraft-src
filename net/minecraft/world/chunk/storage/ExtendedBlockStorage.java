/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.world.chunk.BlockStateContainer;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ import optifine.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtendedBlockStorage
/*     */ {
/*     */   private final int yBase;
/*     */   private int blockRefCount;
/*     */   private int tickRefCount;
/*     */   private final BlockStateContainer data;
/*     */   private NibbleArray blocklightArray;
/*     */   private NibbleArray skylightArray;
/*     */   
/*     */   public ExtendedBlockStorage(int y, boolean storeSkylight) {
/*  37 */     this.yBase = y;
/*  38 */     this.data = new BlockStateContainer();
/*  39 */     this.blocklightArray = new NibbleArray();
/*     */     
/*  41 */     if (storeSkylight)
/*     */     {
/*  43 */       this.skylightArray = new NibbleArray();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState get(int x, int y, int z) {
/*  49 */     return this.data.get(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int x, int y, int z, IBlockState state) {
/*  54 */     if (Reflector.IExtendedBlockState.isInstance(state))
/*     */     {
/*  56 */       state = (IBlockState)Reflector.call(state, Reflector.IExtendedBlockState_getClean, new Object[0]);
/*     */     }
/*     */     
/*  59 */     IBlockState iblockstate = get(x, y, z);
/*  60 */     Block block = iblockstate.getBlock();
/*  61 */     Block block1 = state.getBlock();
/*     */     
/*  63 */     if (block != Blocks.AIR) {
/*     */       
/*  65 */       this.blockRefCount--;
/*     */       
/*  67 */       if (block.getTickRandomly())
/*     */       {
/*  69 */         this.tickRefCount--;
/*     */       }
/*     */     } 
/*     */     
/*  73 */     if (block1 != Blocks.AIR) {
/*     */       
/*  75 */       this.blockRefCount++;
/*     */       
/*  77 */       if (block1.getTickRandomly())
/*     */       {
/*  79 */         this.tickRefCount++;
/*     */       }
/*     */     } 
/*     */     
/*  83 */     this.data.set(x, y, z, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  91 */     return (this.blockRefCount == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getNeedsRandomTick() {
/* 100 */     return (this.tickRefCount > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYLocation() {
/* 108 */     return this.yBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtSkylightValue(int x, int y, int z, int value) {
/* 116 */     this.skylightArray.set(x, y, z, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtSkylightValue(int x, int y, int z) {
/* 124 */     return this.skylightArray.get(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtBlocklightValue(int x, int y, int z, int value) {
/* 132 */     this.blocklightArray.set(x, y, z, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtBlocklightValue(int x, int y, int z) {
/* 140 */     return this.blocklightArray.get(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeInvalidBlocks() {
/* 145 */     IBlockState iblockstate = Blocks.AIR.getDefaultState();
/* 146 */     int i = 0;
/* 147 */     int j = 0;
/*     */     
/* 149 */     for (int k = 0; k < 16; k++) {
/*     */       
/* 151 */       for (int l = 0; l < 16; l++) {
/*     */         
/* 153 */         for (int i1 = 0; i1 < 16; i1++) {
/*     */           
/* 155 */           IBlockState iblockstate1 = this.data.get(i1, k, l);
/*     */           
/* 157 */           if (iblockstate1 != iblockstate) {
/*     */             
/* 159 */             i++;
/* 160 */             Block block = iblockstate1.getBlock();
/*     */             
/* 162 */             if (block.getTickRandomly())
/*     */             {
/* 164 */               j++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     this.blockRefCount = i;
/* 172 */     this.tickRefCount = j;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockStateContainer getData() {
/* 177 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NibbleArray getBlocklightArray() {
/* 185 */     return this.blocklightArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NibbleArray getSkylightArray() {
/* 193 */     return this.skylightArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlocklightArray(NibbleArray newBlocklightArray) {
/* 201 */     this.blocklightArray = newBlocklightArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkylightArray(NibbleArray newSkylightArray) {
/* 209 */     this.skylightArray = newSkylightArray;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\storage\ExtendedBlockStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */