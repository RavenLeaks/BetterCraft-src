package org.newdawn.slick;

public interface ControlledInputReciever {
  void setInput(Input paramInput);
  
  boolean isAcceptingInput();
  
  void inputEnded();
  
  void inputStarted();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\ControlledInputReciever.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */