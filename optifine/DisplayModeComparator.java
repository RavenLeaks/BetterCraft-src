/*    */ package optifine;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import org.lwjgl.opengl.DisplayMode;
/*    */ 
/*    */ public class DisplayModeComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object p_compare_1_, Object p_compare_2_) {
/* 10 */     DisplayMode displaymode = (DisplayMode)p_compare_1_;
/* 11 */     DisplayMode displaymode1 = (DisplayMode)p_compare_2_;
/*    */     
/* 13 */     if (displaymode.getWidth() != displaymode1.getWidth())
/*    */     {
/* 15 */       return displaymode.getWidth() - displaymode1.getWidth();
/*    */     }
/* 17 */     if (displaymode.getHeight() != displaymode1.getHeight())
/*    */     {
/* 19 */       return displaymode.getHeight() - displaymode1.getHeight();
/*    */     }
/* 21 */     if (displaymode.getBitsPerPixel() != displaymode1.getBitsPerPixel())
/*    */     {
/* 23 */       return displaymode.getBitsPerPixel() - displaymode1.getBitsPerPixel();
/*    */     }
/*    */ 
/*    */     
/* 27 */     return (displaymode.getFrequency() != displaymode1.getFrequency()) ? (displaymode.getFrequency() - displaymode1.getFrequency()) : 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\DisplayModeComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */