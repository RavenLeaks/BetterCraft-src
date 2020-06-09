/*     */ package optifine;
/*     */ 
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ 
/*     */ public class Matches
/*     */ {
/*     */   public static boolean block(BlockStateBase p_block_0_, MatchBlock[] p_block_1_) {
/*  11 */     if (p_block_1_ == null)
/*     */     {
/*  13 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  17 */     for (int i = 0; i < p_block_1_.length; i++) {
/*     */       
/*  19 */       MatchBlock matchblock = p_block_1_[i];
/*     */       
/*  21 */       if (matchblock.matches(p_block_0_))
/*     */       {
/*  23 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  27 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean block(int p_block_0_, int p_block_1_, MatchBlock[] p_block_2_) {
/*  33 */     if (p_block_2_ == null)
/*     */     {
/*  35 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  39 */     for (int i = 0; i < p_block_2_.length; i++) {
/*     */       
/*  41 */       MatchBlock matchblock = p_block_2_[i];
/*     */       
/*  43 */       if (matchblock.matches(p_block_0_, p_block_1_))
/*     */       {
/*  45 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean blockId(int p_blockId_0_, MatchBlock[] p_blockId_1_) {
/*  55 */     if (p_blockId_1_ == null)
/*     */     {
/*  57 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  61 */     for (int i = 0; i < p_blockId_1_.length; i++) {
/*     */       
/*  63 */       MatchBlock matchblock = p_blockId_1_[i];
/*     */       
/*  65 */       if (matchblock.getBlockId() == p_blockId_0_)
/*     */       {
/*  67 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean metadata(int p_metadata_0_, int[] p_metadata_1_) {
/*  77 */     if (p_metadata_1_ == null)
/*     */     {
/*  79 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  83 */     for (int i = 0; i < p_metadata_1_.length; i++) {
/*     */       
/*  85 */       if (p_metadata_1_[i] == p_metadata_0_)
/*     */       {
/*  87 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean sprite(TextureAtlasSprite p_sprite_0_, TextureAtlasSprite[] p_sprite_1_) {
/*  97 */     if (p_sprite_1_ == null)
/*     */     {
/*  99 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 103 */     for (int i = 0; i < p_sprite_1_.length; i++) {
/*     */       
/* 105 */       if (p_sprite_1_[i] == p_sprite_0_)
/*     */       {
/* 107 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean biome(Biome p_biome_0_, Biome[] p_biome_1_) {
/* 117 */     if (p_biome_1_ == null)
/*     */     {
/* 119 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 123 */     for (int i = 0; i < p_biome_1_.length; i++) {
/*     */       
/* 125 */       if (p_biome_1_[i] == p_biome_0_)
/*     */       {
/* 127 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 131 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\Matches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */