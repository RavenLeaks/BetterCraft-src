package org.newdawn.slick.opengl;

import java.nio.ByteBuffer;

public interface ImageData {
  int getDepth();
  
  int getWidth();
  
  int getHeight();
  
  int getTexWidth();
  
  int getTexHeight();
  
  ByteBuffer getImageBufferData();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\opengl\ImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */