/*    */ package net.minecraft.client.renderer.vertex;
/*    */ 
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ import shadersmod.client.SVertexFormat;
/*    */ 
/*    */ public class DefaultVertexFormats
/*    */ {
/*  9 */   public static VertexFormat BLOCK = new VertexFormat();
/* 10 */   public static VertexFormat ITEM = new VertexFormat();
/* 11 */   private static final VertexFormat BLOCK_VANILLA = BLOCK;
/* 12 */   private static final VertexFormat ITEM_VANILLA = ITEM;
/* 13 */   public static final VertexFormat OLDMODEL_POSITION_TEX_NORMAL = new VertexFormat();
/* 14 */   public static final VertexFormat PARTICLE_POSITION_TEX_COLOR_LMAP = new VertexFormat();
/* 15 */   public static final VertexFormat POSITION = new VertexFormat();
/* 16 */   public static final VertexFormat POSITION_COLOR = new VertexFormat();
/* 17 */   public static final VertexFormat POSITION_TEX = new VertexFormat();
/* 18 */   public static final VertexFormat POSITION_NORMAL = new VertexFormat();
/* 19 */   public static final VertexFormat POSITION_TEX_COLOR = new VertexFormat();
/* 20 */   public static final VertexFormat POSITION_TEX_NORMAL = new VertexFormat();
/* 21 */   public static final VertexFormat POSITION_TEX_LMAP_COLOR = new VertexFormat();
/* 22 */   public static final VertexFormat POSITION_TEX_COLOR_NORMAL = new VertexFormat();
/* 23 */   public static final VertexFormatElement POSITION_3F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3);
/* 24 */   public static final VertexFormatElement COLOR_4UB = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 4);
/* 25 */   public static final VertexFormatElement TEX_2F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2);
/* 26 */   public static final VertexFormatElement TEX_2S = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, 2);
/* 27 */   public static final VertexFormatElement NORMAL_3B = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3);
/* 28 */   public static final VertexFormatElement PADDING_1B = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1);
/*    */ 
/*    */   
/*    */   public static void updateVertexFormats() {
/* 32 */     if (Config.isShaders()) {
/*    */       
/* 34 */       BLOCK = SVertexFormat.makeDefVertexFormatBlock();
/* 35 */       ITEM = SVertexFormat.makeDefVertexFormatItem();
/*    */     }
/*    */     else {
/*    */       
/* 39 */       BLOCK = BLOCK_VANILLA;
/* 40 */       ITEM = ITEM_VANILLA;
/*    */     } 
/*    */     
/* 43 */     if (Reflector.Attributes_DEFAULT_BAKED_FORMAT.exists()) {
/*    */       
/* 45 */       VertexFormat vertexformat = ITEM;
/* 46 */       VertexFormat vertexformat1 = (VertexFormat)Reflector.getFieldValue(Reflector.Attributes_DEFAULT_BAKED_FORMAT);
/* 47 */       vertexformat1.clear();
/*    */       
/* 49 */       for (int i = 0; i < vertexformat.getElementCount(); i++)
/*    */       {
/* 51 */         vertexformat1.addElement(vertexformat.getElement(i));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 58 */     BLOCK.addElement(POSITION_3F);
/* 59 */     BLOCK.addElement(COLOR_4UB);
/* 60 */     BLOCK.addElement(TEX_2F);
/* 61 */     BLOCK.addElement(TEX_2S);
/* 62 */     ITEM.addElement(POSITION_3F);
/* 63 */     ITEM.addElement(COLOR_4UB);
/* 64 */     ITEM.addElement(TEX_2F);
/* 65 */     ITEM.addElement(NORMAL_3B);
/* 66 */     ITEM.addElement(PADDING_1B);
/* 67 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(POSITION_3F);
/* 68 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(TEX_2F);
/* 69 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(NORMAL_3B);
/* 70 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(PADDING_1B);
/* 71 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(POSITION_3F);
/* 72 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(TEX_2F);
/* 73 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(COLOR_4UB);
/* 74 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(TEX_2S);
/* 75 */     POSITION.addElement(POSITION_3F);
/* 76 */     POSITION_COLOR.addElement(POSITION_3F);
/* 77 */     POSITION_COLOR.addElement(COLOR_4UB);
/* 78 */     POSITION_TEX.addElement(POSITION_3F);
/* 79 */     POSITION_TEX.addElement(TEX_2F);
/* 80 */     POSITION_NORMAL.addElement(POSITION_3F);
/* 81 */     POSITION_NORMAL.addElement(NORMAL_3B);
/* 82 */     POSITION_NORMAL.addElement(PADDING_1B);
/* 83 */     POSITION_TEX_COLOR.addElement(POSITION_3F);
/* 84 */     POSITION_TEX_COLOR.addElement(TEX_2F);
/* 85 */     POSITION_TEX_COLOR.addElement(COLOR_4UB);
/* 86 */     POSITION_TEX_NORMAL.addElement(POSITION_3F);
/* 87 */     POSITION_TEX_NORMAL.addElement(TEX_2F);
/* 88 */     POSITION_TEX_NORMAL.addElement(NORMAL_3B);
/* 89 */     POSITION_TEX_NORMAL.addElement(PADDING_1B);
/* 90 */     POSITION_TEX_LMAP_COLOR.addElement(POSITION_3F);
/* 91 */     POSITION_TEX_LMAP_COLOR.addElement(TEX_2F);
/* 92 */     POSITION_TEX_LMAP_COLOR.addElement(TEX_2S);
/* 93 */     POSITION_TEX_LMAP_COLOR.addElement(COLOR_4UB);
/* 94 */     POSITION_TEX_COLOR_NORMAL.addElement(POSITION_3F);
/* 95 */     POSITION_TEX_COLOR_NORMAL.addElement(TEX_2F);
/* 96 */     POSITION_TEX_COLOR_NORMAL.addElement(COLOR_4UB);
/* 97 */     POSITION_TEX_COLOR_NORMAL.addElement(NORMAL_3B);
/* 98 */     POSITION_TEX_COLOR_NORMAL.addElement(PADDING_1B);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\vertex\DefaultVertexFormats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */