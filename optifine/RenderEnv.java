/*     */ package optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.BlockModelRenderer;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderEnv
/*     */ {
/*     */   private IBlockAccess blockAccess;
/*     */   private IBlockState blockState;
/*     */   private BlockPos blockPos;
/*  24 */   private int blockId = -1;
/*  25 */   private int metadata = -1;
/*  26 */   private int breakingAnimation = -1;
/*  27 */   private int smartLeaves = -1;
/*  28 */   private float[] quadBounds = new float[EnumFacing.VALUES.length * 2];
/*  29 */   private BitSet boundsFlags = new BitSet(3);
/*  30 */   private BlockModelRenderer.AmbientOcclusionFace aoFace = new BlockModelRenderer.AmbientOcclusionFace();
/*  31 */   private BlockPosM colorizerBlockPosM = null;
/*  32 */   private boolean[] borderFlags = null;
/*  33 */   private boolean[] borderFlags2 = null;
/*  34 */   private boolean[] borderFlags3 = null;
/*  35 */   private EnumFacing[] borderDirections = null;
/*  36 */   private List<BakedQuad> listQuadsCustomizer = new ArrayList<>();
/*  37 */   private List<BakedQuad> listQuadsCtmMultipass = new ArrayList<>();
/*  38 */   private BakedQuad[] arrayQuadsCtm1 = new BakedQuad[1];
/*  39 */   private BakedQuad[] arrayQuadsCtm2 = new BakedQuad[2];
/*  40 */   private BakedQuad[] arrayQuadsCtm3 = new BakedQuad[3];
/*  41 */   private BakedQuad[] arrayQuadsCtm4 = new BakedQuad[4];
/*  42 */   private RegionRenderCacheBuilder regionRenderCacheBuilder = null;
/*  43 */   private ListQuadsOverlay[] listsQuadsOverlay = new ListQuadsOverlay[(BlockRenderLayer.values()).length];
/*     */   
/*     */   private boolean overlaysRendered = false;
/*     */   private static final int UNKNOWN = -1;
/*     */   private static final int FALSE = 0;
/*     */   private static final int TRUE = 1;
/*     */   
/*     */   public RenderEnv(IBlockAccess p_i96_1_, IBlockState p_i96_2_, BlockPos p_i96_3_) {
/*  51 */     this.blockAccess = p_i96_1_;
/*  52 */     this.blockState = p_i96_2_;
/*  53 */     this.blockPos = p_i96_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset(IBlockAccess p_reset_1_, IBlockState p_reset_2_, BlockPos p_reset_3_) {
/*  58 */     if (this.blockAccess != p_reset_1_ || this.blockState != p_reset_2_ || this.blockPos != p_reset_3_) {
/*     */       
/*  60 */       this.blockAccess = p_reset_1_;
/*  61 */       this.blockState = p_reset_2_;
/*  62 */       this.blockPos = p_reset_3_;
/*  63 */       this.blockId = -1;
/*  64 */       this.metadata = -1;
/*  65 */       this.breakingAnimation = -1;
/*  66 */       this.smartLeaves = -1;
/*  67 */       this.boundsFlags.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockId() {
/*  73 */     if (this.blockId < 0)
/*     */     {
/*  75 */       if (this.blockState instanceof BlockStateBase) {
/*     */         
/*  77 */         BlockStateBase blockstatebase = (BlockStateBase)this.blockState;
/*  78 */         this.blockId = blockstatebase.getBlockId();
/*     */       }
/*     */       else {
/*     */         
/*  82 */         this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
/*     */       } 
/*     */     }
/*     */     
/*  86 */     return this.blockId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMetadata() {
/*  91 */     if (this.metadata < 0)
/*     */     {
/*  93 */       if (this.blockState instanceof BlockStateBase) {
/*     */         
/*  95 */         BlockStateBase blockstatebase = (BlockStateBase)this.blockState;
/*  96 */         this.metadata = blockstatebase.getMetadata();
/*     */       }
/*     */       else {
/*     */         
/* 100 */         this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
/*     */       } 
/*     */     }
/*     */     
/* 104 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getQuadBounds() {
/* 109 */     return this.quadBounds;
/*     */   }
/*     */ 
/*     */   
/*     */   public BitSet getBoundsFlags() {
/* 114 */     return this.boundsFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockModelRenderer.AmbientOcclusionFace getAoFace() {
/* 119 */     return this.aoFace;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakingAnimation(List p_isBreakingAnimation_1_) {
/* 124 */     if (this.breakingAnimation == -1 && p_isBreakingAnimation_1_.size() > 0)
/*     */     {
/* 126 */       if (p_isBreakingAnimation_1_.get(0) instanceof net.minecraft.client.renderer.block.model.BakedQuadRetextured) {
/*     */         
/* 128 */         this.breakingAnimation = 1;
/*     */       }
/*     */       else {
/*     */         
/* 132 */         this.breakingAnimation = 0;
/*     */       } 
/*     */     }
/*     */     
/* 136 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakingAnimation(BakedQuad p_isBreakingAnimation_1_) {
/* 141 */     if (this.breakingAnimation < 0)
/*     */     {
/* 143 */       if (p_isBreakingAnimation_1_ instanceof net.minecraft.client.renderer.block.model.BakedQuadRetextured) {
/*     */         
/* 145 */         this.breakingAnimation = 1;
/*     */       }
/*     */       else {
/*     */         
/* 149 */         this.breakingAnimation = 0;
/*     */       } 
/*     */     }
/*     */     
/* 153 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakingAnimation() {
/* 158 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState getBlockState() {
/* 163 */     return this.blockState;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPosM getColorizerBlockPosM() {
/* 168 */     if (this.colorizerBlockPosM == null)
/*     */     {
/* 170 */       this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
/*     */     }
/*     */     
/* 173 */     return this.colorizerBlockPosM;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getBorderFlags() {
/* 178 */     if (this.borderFlags == null)
/*     */     {
/* 180 */       this.borderFlags = new boolean[4];
/*     */     }
/*     */     
/* 183 */     return this.borderFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getBorderFlags2() {
/* 188 */     if (this.borderFlags2 == null)
/*     */     {
/* 190 */       this.borderFlags2 = new boolean[4];
/*     */     }
/*     */     
/* 193 */     return this.borderFlags2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getBorderFlags3() {
/* 198 */     if (this.borderFlags3 == null)
/*     */     {
/* 200 */       this.borderFlags3 = new boolean[4];
/*     */     }
/*     */     
/* 203 */     return this.borderFlags3;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing[] getBorderDirections() {
/* 208 */     if (this.borderDirections == null)
/*     */     {
/* 210 */       this.borderDirections = new EnumFacing[4];
/*     */     }
/*     */     
/* 213 */     return this.borderDirections;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing[] getBorderDirections(EnumFacing p_getBorderDirections_1_, EnumFacing p_getBorderDirections_2_, EnumFacing p_getBorderDirections_3_, EnumFacing p_getBorderDirections_4_) {
/* 218 */     EnumFacing[] aenumfacing = getBorderDirections();
/* 219 */     aenumfacing[0] = p_getBorderDirections_1_;
/* 220 */     aenumfacing[1] = p_getBorderDirections_2_;
/* 221 */     aenumfacing[2] = p_getBorderDirections_3_;
/* 222 */     aenumfacing[3] = p_getBorderDirections_4_;
/* 223 */     return aenumfacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSmartLeaves() {
/* 228 */     if (this.smartLeaves == -1)
/*     */     {
/* 230 */       if (Config.isTreesSmart() && this.blockState.getBlock() instanceof net.minecraft.block.BlockLeaves) {
/*     */         
/* 232 */         this.smartLeaves = 1;
/*     */       }
/*     */       else {
/*     */         
/* 236 */         this.smartLeaves = 0;
/*     */       } 
/*     */     }
/*     */     
/* 240 */     return (this.smartLeaves == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getListQuadsCustomizer() {
/* 245 */     return this.listQuadsCustomizer;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad p_getArrayQuadsCtm_1_) {
/* 250 */     this.arrayQuadsCtm1[0] = p_getArrayQuadsCtm_1_;
/* 251 */     return this.arrayQuadsCtm1;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad p_getArrayQuadsCtm_1_, BakedQuad p_getArrayQuadsCtm_2_) {
/* 256 */     this.arrayQuadsCtm2[0] = p_getArrayQuadsCtm_1_;
/* 257 */     this.arrayQuadsCtm2[1] = p_getArrayQuadsCtm_2_;
/* 258 */     return this.arrayQuadsCtm2;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad p_getArrayQuadsCtm_1_, BakedQuad p_getArrayQuadsCtm_2_, BakedQuad p_getArrayQuadsCtm_3_) {
/* 263 */     this.arrayQuadsCtm3[0] = p_getArrayQuadsCtm_1_;
/* 264 */     this.arrayQuadsCtm3[1] = p_getArrayQuadsCtm_2_;
/* 265 */     this.arrayQuadsCtm3[2] = p_getArrayQuadsCtm_3_;
/* 266 */     return this.arrayQuadsCtm3;
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad p_getArrayQuadsCtm_1_, BakedQuad p_getArrayQuadsCtm_2_, BakedQuad p_getArrayQuadsCtm_3_, BakedQuad p_getArrayQuadsCtm_4_) {
/* 271 */     this.arrayQuadsCtm4[0] = p_getArrayQuadsCtm_1_;
/* 272 */     this.arrayQuadsCtm4[1] = p_getArrayQuadsCtm_2_;
/* 273 */     this.arrayQuadsCtm4[2] = p_getArrayQuadsCtm_3_;
/* 274 */     this.arrayQuadsCtm4[3] = p_getArrayQuadsCtm_4_;
/* 275 */     return this.arrayQuadsCtm4;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getListQuadsCtmMultipass(BakedQuad[] p_getListQuadsCtmMultipass_1_) {
/* 280 */     this.listQuadsCtmMultipass.clear();
/*     */     
/* 282 */     if (p_getListQuadsCtmMultipass_1_ != null)
/*     */     {
/* 284 */       for (int i = 0; i < p_getListQuadsCtmMultipass_1_.length; i++) {
/*     */         
/* 286 */         BakedQuad bakedquad = p_getListQuadsCtmMultipass_1_[i];
/* 287 */         this.listQuadsCtmMultipass.add(bakedquad);
/*     */       } 
/*     */     }
/*     */     
/* 291 */     return this.listQuadsCtmMultipass;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
/* 296 */     return this.regionRenderCacheBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder p_setRegionRenderCacheBuilder_1_) {
/* 301 */     this.regionRenderCacheBuilder = p_setRegionRenderCacheBuilder_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListQuadsOverlay getListQuadsOverlay(BlockRenderLayer p_getListQuadsOverlay_1_) {
/* 306 */     ListQuadsOverlay listquadsoverlay = this.listsQuadsOverlay[p_getListQuadsOverlay_1_.ordinal()];
/*     */     
/* 308 */     if (listquadsoverlay == null) {
/*     */       
/* 310 */       listquadsoverlay = new ListQuadsOverlay();
/* 311 */       this.listsQuadsOverlay[p_getListQuadsOverlay_1_.ordinal()] = listquadsoverlay;
/*     */     } 
/*     */     
/* 314 */     return listquadsoverlay;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOverlaysRendered() {
/* 319 */     return this.overlaysRendered;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOverlaysRendered(boolean p_setOverlaysRendered_1_) {
/* 324 */     this.overlaysRendered = p_setOverlaysRendered_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\RenderEnv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */