package org.newdawn.slick.opengl;

public interface Texture {
  boolean hasAlpha();
  
  String getTextureRef();
  
  void bind();
  
  int getImageHeight();
  
  int getImageWidth();
  
  float getHeight();
  
  float getWidth();
  
  int getTextureHeight();
  
  int getTextureWidth();
  
  void release();
  
  int getTextureID();
  
  byte[] getTextureData();
  
  void setTextureFilter(int paramInt);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\opengl\Texture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */