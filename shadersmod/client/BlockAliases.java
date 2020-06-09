/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import optifine.Config;
/*     */ import optifine.ConnectedParser;
/*     */ import optifine.MatchBlock;
/*     */ import optifine.PropertiesOrdered;
/*     */ import optifine.StrUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockAliases
/*     */ {
/*  17 */   private static BlockAlias[][] blockAliases = null;
/*     */ 
/*     */   
/*     */   public static int getMappedBlockId(int blockId, int metadata) {
/*  21 */     if (blockAliases == null)
/*     */     {
/*  23 */       return blockId;
/*     */     }
/*  25 */     if (blockId >= 0 && blockId < blockAliases.length) {
/*     */       
/*  27 */       BlockAlias[] ablockalias = blockAliases[blockId];
/*     */       
/*  29 */       if (ablockalias == null)
/*     */       {
/*  31 */         return blockId;
/*     */       }
/*     */ 
/*     */       
/*  35 */       for (int i = 0; i < ablockalias.length; i++) {
/*     */         
/*  37 */         BlockAlias blockalias = ablockalias[i];
/*     */         
/*  39 */         if (blockalias.matches(blockId, metadata))
/*     */         {
/*  41 */           return blockalias.getBlockId();
/*     */         }
/*     */       } 
/*     */       
/*  45 */       return blockId;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  50 */     return blockId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  56 */     reset();
/*  57 */     String s = "/shaders/block.properties";
/*     */ 
/*     */     
/*     */     try {
/*  61 */       InputStream inputstream = shaderPack.getResourceAsStream(s);
/*     */       
/*  63 */       if (inputstream == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  68 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  69 */       propertiesOrdered.load(inputstream);
/*  70 */       inputstream.close();
/*  71 */       Config.dbg("[Shaders] Parsing block mappings: " + s);
/*  72 */       List<List<BlockAlias>> list = new ArrayList<>();
/*  73 */       ConnectedParser connectedparser = new ConnectedParser("Shaders");
/*     */       
/*  75 */       for (Object s10 : propertiesOrdered.keySet()) {
/*     */         
/*  77 */         String s1 = (String)s10;
/*  78 */         String s2 = propertiesOrdered.getProperty(s1);
/*  79 */         String s3 = "block.";
/*     */         
/*  81 */         if (!s1.startsWith(s3)) {
/*     */           
/*  83 */           Config.warn("[Shaders] Invalid block ID: " + s1);
/*     */           
/*     */           continue;
/*     */         } 
/*  87 */         String s4 = StrUtils.removePrefix(s1, s3);
/*  88 */         int i = Config.parseInt(s4, -1);
/*     */         
/*  90 */         if (i < 0) {
/*     */           
/*  92 */           Config.warn("[Shaders] Invalid block ID: " + s1);
/*     */           
/*     */           continue;
/*     */         } 
/*  96 */         MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s2);
/*     */         
/*  98 */         if (amatchblock != null && amatchblock.length >= 1) {
/*     */           
/* 100 */           BlockAlias blockalias = new BlockAlias(i, amatchblock);
/* 101 */           addToList(list, blockalias);
/*     */           
/*     */           continue;
/*     */         } 
/* 105 */         Config.warn("[Shaders] Invalid block ID mapping: " + s1 + "=" + s2);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       if (list.size() <= 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 116 */       blockAliases = toArrays(list);
/*     */     }
/* 118 */     catch (IOException var15) {
/*     */       
/* 120 */       Config.warn("[Shaders] Error reading: " + s);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(List<List<BlockAlias>> blocksAliases, BlockAlias ba) {
/* 126 */     int[] aint = ba.getMatchBlockIds();
/*     */     
/* 128 */     for (int i = 0; i < aint.length; i++) {
/*     */       
/* 130 */       int j = aint[i];
/*     */       
/* 132 */       while (j >= blocksAliases.size())
/*     */       {
/* 134 */         blocksAliases.add(null);
/*     */       }
/*     */       
/* 137 */       List<BlockAlias> list = blocksAliases.get(j);
/*     */       
/* 139 */       if (list == null) {
/*     */         
/* 141 */         list = new ArrayList<>();
/* 142 */         blocksAliases.set(j, list);
/*     */       } 
/*     */       
/* 145 */       list.add(ba);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static BlockAlias[][] toArrays(List<List<BlockAlias>> listBlocksAliases) {
/* 151 */     BlockAlias[][] ablockalias = new BlockAlias[listBlocksAliases.size()][];
/*     */     
/* 153 */     for (int i = 0; i < ablockalias.length; i++) {
/*     */       
/* 155 */       List<BlockAlias> list = listBlocksAliases.get(i);
/*     */       
/* 157 */       if (list != null)
/*     */       {
/* 159 */         ablockalias[i] = list.<BlockAlias>toArray(new BlockAlias[list.size()]);
/*     */       }
/*     */     } 
/*     */     
/* 163 */     return ablockalias;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 168 */     blockAliases = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\BlockAliases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */