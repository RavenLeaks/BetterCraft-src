/*    */ package optifine;
/*    */ 
/*    */ import java.util.Properties;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class CustomPanoramaProperties
/*    */ {
/*    */   private String path;
/*    */   private ResourceLocation[] panoramaLocations;
/* 10 */   private int weight = 1;
/* 11 */   private int blur1 = 64;
/* 12 */   private int blur2 = 3;
/* 13 */   private int blur3 = 3;
/* 14 */   private int overlay1Top = -2130706433;
/* 15 */   private int overlay1Bottom = 16777215;
/* 16 */   private int overlay2Top = 0;
/* 17 */   private int overlay2Bottom = Integer.MIN_VALUE;
/*    */ 
/*    */   
/*    */   public CustomPanoramaProperties(String p_i34_1_, Properties p_i34_2_) {
/* 21 */     ConnectedParser connectedparser = new ConnectedParser("CustomPanorama");
/* 22 */     this.path = p_i34_1_;
/* 23 */     this.panoramaLocations = new ResourceLocation[6];
/*    */     
/* 25 */     for (int i = 0; i < this.panoramaLocations.length; i++)
/*    */     {
/* 27 */       this.panoramaLocations[i] = new ResourceLocation(String.valueOf(p_i34_1_) + "/panorama_" + i + ".png");
/*    */     }
/*    */     
/* 30 */     this.weight = connectedparser.parseInt(p_i34_2_.getProperty("weight"), 1);
/* 31 */     this.blur1 = connectedparser.parseInt(p_i34_2_.getProperty("blur1"), 64);
/* 32 */     this.blur2 = connectedparser.parseInt(p_i34_2_.getProperty("blur2"), 3);
/* 33 */     this.blur3 = connectedparser.parseInt(p_i34_2_.getProperty("blur3"), 3);
/* 34 */     this.overlay1Top = ConnectedParser.parseColor4(p_i34_2_.getProperty("overlay1.top"), -2130706433);
/* 35 */     this.overlay1Bottom = ConnectedParser.parseColor4(p_i34_2_.getProperty("overlay1.bottom"), 16777215);
/* 36 */     this.overlay2Top = ConnectedParser.parseColor4(p_i34_2_.getProperty("overlay2.top"), 0);
/* 37 */     this.overlay2Bottom = ConnectedParser.parseColor4(p_i34_2_.getProperty("overlay2.bottom"), -2147483648);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation[] getPanoramaLocations() {
/* 42 */     return this.panoramaLocations;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeight() {
/* 47 */     return this.weight;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlur1() {
/* 52 */     return this.blur1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlur2() {
/* 57 */     return this.blur2;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlur3() {
/* 62 */     return this.blur3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay1Top() {
/* 67 */     return this.overlay1Top;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay1Bottom() {
/* 72 */     return this.overlay1Bottom;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay2Top() {
/* 77 */     return this.overlay2Top;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay2Bottom() {
/* 82 */     return this.overlay2Bottom;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 87 */     return this.path + ", weight: " + this.weight + ", blur: " + this.blur1 + " " + this.blur2 + " " + this.blur3 + ", overlay: " + this.overlay1Top + " " + this.overlay1Bottom + " " + this.overlay2Top + " " + this.overlay2Bottom;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomPanoramaProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */