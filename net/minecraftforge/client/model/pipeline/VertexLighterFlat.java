/*     */ package net.minecraftforge.client.model.pipeline;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import javax.vecmath.Tuple3f;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.color.BlockColors;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VertexLighterFlat
/*     */   extends QuadGatheringTransformer
/*     */ {
/*  39 */   protected static final VertexFormatElement NORMAL_4F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.NORMAL, 4);
/*     */   
/*     */   protected final BlockInfo blockInfo;
/*  42 */   private int tint = -1;
/*     */   
/*     */   private boolean diffuse = true;
/*  45 */   protected int posIndex = -1;
/*  46 */   protected int normalIndex = -1;
/*  47 */   protected int colorIndex = -1;
/*  48 */   protected int lightmapIndex = -1;
/*     */   
/*     */   protected VertexFormat baseFormat;
/*     */ 
/*     */   
/*     */   public VertexLighterFlat(BlockColors colors) {
/*  54 */     this.blockInfo = new BlockInfo(colors);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(IVertexConsumer parent) {
/*  60 */     super.setParent(parent);
/*  61 */     setVertexFormat(parent.getVertexFormat());
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateIndices() {
/*  66 */     for (int i = 0; i < getVertexFormat().getElementCount(); i++) {
/*     */       
/*  68 */       switch (getVertexFormat().getElement(i).getUsage()) {
/*     */         
/*     */         case POSITION:
/*  71 */           this.posIndex = i;
/*     */           break;
/*     */         case NORMAL:
/*  74 */           this.normalIndex = i;
/*     */           break;
/*     */         case COLOR:
/*  77 */           this.colorIndex = i;
/*     */           break;
/*     */         case UV:
/*  80 */           if (getVertexFormat().getElement(i).getIndex() == 1)
/*     */           {
/*  82 */             this.lightmapIndex = i;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/*  88 */     if (this.posIndex == -1)
/*     */     {
/*  90 */       throw new IllegalArgumentException("vertex lighter needs format with position");
/*     */     }
/*  92 */     if (this.lightmapIndex == -1)
/*     */     {
/*  94 */       throw new IllegalArgumentException("vertex lighter needs format with lightmap");
/*     */     }
/*  96 */     if (this.colorIndex == -1)
/*     */     {
/*  98 */       throw new IllegalArgumentException("vertex lighter needs format with color");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVertexFormat(VertexFormat format) {
/* 105 */     if (Objects.equals(format, this.baseFormat))
/* 106 */       return;  this.baseFormat = format;
/* 107 */     super.setVertexFormat(withNormal(format));
/* 108 */     updateIndices();
/*     */   }
/*     */   
/* 111 */   private static final VertexFormat BLOCK_WITH_NORMAL = withNormalUncached(DefaultVertexFormats.BLOCK);
/*     */ 
/*     */   
/*     */   static VertexFormat withNormal(VertexFormat format) {
/* 115 */     if (format == DefaultVertexFormats.BLOCK)
/* 116 */       return BLOCK_WITH_NORMAL; 
/* 117 */     return withNormalUncached(format);
/*     */   }
/*     */ 
/*     */   
/*     */   private static VertexFormat withNormalUncached(VertexFormat format) {
/* 122 */     if (format == null || format.hasNormal()) return format; 
/* 123 */     return (new VertexFormat(format)).addElement(NORMAL_4F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processQuad() {
/* 129 */     float[][] position = this.quadData[this.posIndex];
/* 130 */     float[][] normal = null;
/* 131 */     float[][] lightmap = this.quadData[this.lightmapIndex];
/* 132 */     float[][] color = this.quadData[this.colorIndex];
/*     */     
/* 134 */     if (this.dataLength[this.normalIndex] >= 3 && (
/* 135 */       this.quadData[this.normalIndex][0][0] != -1.0F || 
/* 136 */       this.quadData[this.normalIndex][0][1] != -1.0F || 
/* 137 */       this.quadData[this.normalIndex][0][2] != -1.0F)) {
/*     */       
/* 139 */       normal = this.quadData[this.normalIndex];
/*     */     }
/*     */     else {
/*     */       
/* 143 */       normal = new float[4][4];
/* 144 */       Vector3f v1 = new Vector3f(position[3]);
/* 145 */       Vector3f t = new Vector3f(position[1]);
/* 146 */       Vector3f v2 = new Vector3f(position[2]);
/* 147 */       v1.sub((Tuple3f)t);
/* 148 */       t.set(position[0]);
/* 149 */       v2.sub((Tuple3f)t);
/* 150 */       v1.cross(v2, v1);
/* 151 */       v1.normalize();
/* 152 */       for (int i = 0; i < 4; i++) {
/*     */         
/* 154 */         normal[i][0] = v1.x;
/* 155 */         normal[i][1] = v1.y;
/* 156 */         normal[i][2] = v1.z;
/* 157 */         normal[i][3] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     int multiplier = -1;
/* 162 */     if (this.tint != -1)
/*     */     {
/* 164 */       multiplier = this.blockInfo.getColorMultiplier(this.tint);
/*     */     }
/*     */     
/* 167 */     VertexFormat format = this.parent.getVertexFormat();
/* 168 */     int count = format.getElementCount();
/*     */     
/* 170 */     for (int v = 0; v < 4; v++) {
/*     */       
/* 172 */       position[v][0] = position[v][0] + this.blockInfo.getShx();
/* 173 */       position[v][1] = position[v][1] + this.blockInfo.getShy();
/* 174 */       position[v][2] = position[v][2] + this.blockInfo.getShz();
/*     */       
/* 176 */       float x = position[v][0] - 0.5F;
/* 177 */       float y = position[v][1] - 0.5F;
/* 178 */       float z = position[v][2] - 0.5F;
/*     */ 
/*     */ 
/*     */       
/* 182 */       x += normal[v][0] * 0.5F;
/* 183 */       y += normal[v][1] * 0.5F;
/* 184 */       z += normal[v][2] * 0.5F;
/*     */ 
/*     */       
/* 187 */       float blockLight = lightmap[v][0], skyLight = lightmap[v][1];
/* 188 */       updateLightmap(normal[v], lightmap[v], x, y, z);
/* 189 */       if (this.dataLength[this.lightmapIndex] > 1) {
/*     */         
/* 191 */         if (blockLight > lightmap[v][0]) lightmap[v][0] = blockLight; 
/* 192 */         if (skyLight > lightmap[v][1]) lightmap[v][1] = skyLight; 
/*     */       } 
/* 194 */       updateColor(normal[v], color[v], x, y, z, this.tint, multiplier);
/* 195 */       if (this.diffuse) {
/*     */         
/* 197 */         float d = LightUtil.diffuseLight(normal[v][0], normal[v][1], normal[v][2]);
/* 198 */         for (int i = 0; i < 3; i++)
/*     */         {
/* 200 */           color[v][i] = color[v][i] * d;
/*     */         }
/*     */       } 
/* 203 */       if (EntityRenderer.anaglyphEnable)
/*     */       {
/* 205 */         applyAnaglyph(color[v]);
/*     */       }
/*     */ 
/*     */       
/* 209 */       for (int e = 0; e < count; e++) {
/*     */         
/* 211 */         VertexFormatElement element = format.getElement(e);
/* 212 */         switch (element.getUsage()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case POSITION:
/* 221 */             this.parent.put(e, position[v]);
/*     */             break;
/*     */           case NORMAL:
/* 224 */             this.parent.put(e, normal[v]);
/*     */             break;
/*     */           case COLOR:
/* 227 */             this.parent.put(e, color[v]);
/*     */             break;
/*     */           case UV:
/* 230 */             if (element.getIndex() == 1) {
/*     */               
/* 232 */               this.parent.put(e, lightmap[v]);
/*     */               break;
/*     */             } 
/*     */           
/*     */           default:
/* 237 */             this.parent.put(e, this.quadData[e][v]); break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 241 */     this.tint = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyAnaglyph(float[] color) {
/* 246 */     float r = color[0];
/* 247 */     color[0] = (r * 30.0F + color[1] * 59.0F + color[2] * 11.0F) / 100.0F;
/* 248 */     color[1] = (r * 3.0F + color[1] * 7.0F) / 10.0F;
/* 249 */     color[2] = (r * 3.0F + color[2] * 7.0F) / 10.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateLightmap(float[] normal, float[] lightmap, float x, float y, float z) {
/* 254 */     float e1 = 0.99F;
/* 255 */     float e2 = 0.95F;
/*     */     
/* 257 */     boolean full = this.blockInfo.isFullCube();
/* 258 */     EnumFacing side = null;
/*     */     
/* 260 */     if ((full || y < -0.99F) && normal[1] < -0.95F) { side = EnumFacing.DOWN; }
/* 261 */     else if ((full || y > 0.99F) && normal[1] > 0.95F) { side = EnumFacing.UP; }
/* 262 */     else if ((full || z < -0.99F) && normal[2] < -0.95F) { side = EnumFacing.NORTH; }
/* 263 */     else if ((full || z > 0.99F) && normal[2] > 0.95F) { side = EnumFacing.SOUTH; }
/* 264 */     else if ((full || x < -0.99F) && normal[0] < -0.95F) { side = EnumFacing.WEST; }
/* 265 */     else if ((full || x > 0.99F) && normal[0] > 0.95F) { side = EnumFacing.EAST; }
/*     */     
/* 267 */     int i = (side == null) ? 0 : (side.ordinal() + 1);
/* 268 */     int brightness = this.blockInfo.getPackedLight()[i];
/*     */     
/* 270 */     lightmap[0] = (brightness >> 4 & 0xF) * 32.0F / 65535.0F;
/* 271 */     lightmap[1] = (brightness >> 20 & 0xF) * 32.0F / 65535.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateColor(float[] normal, float[] color, float x, float y, float z, float tint, int multiplier) {
/* 276 */     if (tint != -1.0F) {
/*     */       
/* 278 */       color[0] = color[0] * (multiplier >> 16 & 0xFF) / 255.0F;
/* 279 */       color[1] = color[1] * (multiplier >> 8 & 0xFF) / 255.0F;
/* 280 */       color[2] = color[2] * (multiplier & 0xFF) / 255.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQuadTint(int tint) {
/* 287 */     this.tint = tint;
/*     */   }
/*     */   
/*     */   public void setQuadOrientation(EnumFacing orientation) {}
/*     */   
/*     */   public void setQuadCulled() {}
/*     */   
/*     */   public void setWorld(IBlockAccess world) {
/* 295 */     this.blockInfo.setWorld(world);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(IBlockState state) {
/* 300 */     this.blockInfo.setState(state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockPos(BlockPos blockPos) {
/* 305 */     this.blockInfo.setBlockPos(blockPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetBlockInfo() {
/* 310 */     this.blockInfo.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBlockInfo() {
/* 315 */     this.blockInfo.updateShift();
/* 316 */     this.blockInfo.updateFlatLighting();
/*     */   }
/*     */   
/*     */   public void setQuadColored() {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraftforge\client\model\pipeline\VertexLighterFlat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */