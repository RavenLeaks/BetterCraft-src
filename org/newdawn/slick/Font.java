package org.newdawn.slick;

public interface Font {
  int getWidth(String paramString);
  
  int getHeight(String paramString);
  
  int getLineHeight();
  
  void drawString(float paramFloat1, float paramFloat2, String paramString);
  
  void drawString(float paramFloat1, float paramFloat2, String paramString, Color paramColor);
  
  void drawString(float paramFloat1, float paramFloat2, String paramString, Color paramColor, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\Font.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */