package org.newdawn.slick.opengl.renderer;

public interface LineStripRenderer {
  boolean applyGLLineFixes();
  
  void start();
  
  void end();
  
  void vertex(float paramFloat1, float paramFloat2);
  
  void color(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  void setWidth(float paramFloat);
  
  void setAntiAlias(boolean paramBoolean);
  
  void setLineCaps(boolean paramBoolean);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\opengl\renderer\LineStripRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */