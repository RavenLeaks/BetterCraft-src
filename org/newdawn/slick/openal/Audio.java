package org.newdawn.slick.openal;

public interface Audio {
  void stop();
  
  int getBufferID();
  
  boolean isPlaying();
  
  int playAsSoundEffect(float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  int playAsSoundEffect(float paramFloat1, float paramFloat2, boolean paramBoolean, float paramFloat3, float paramFloat4, float paramFloat5);
  
  int playAsMusic(float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  boolean setPosition(float paramFloat);
  
  float getPosition();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\openal\Audio.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */