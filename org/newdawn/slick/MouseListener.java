package org.newdawn.slick;

public interface MouseListener extends ControlledInputReciever {
  void mouseWheelMoved(int paramInt);
  
  void mouseClicked(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void mousePressed(int paramInt1, int paramInt2, int paramInt3);
  
  void mouseReleased(int paramInt1, int paramInt2, int paramInt3);
  
  void mouseMoved(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  void mouseDragged(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\MouseListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */