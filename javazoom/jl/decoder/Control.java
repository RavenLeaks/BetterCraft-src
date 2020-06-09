package javazoom.jl.decoder;

public interface Control {
  void start();
  
  void stop();
  
  boolean isPlaying();
  
  void pause();
  
  boolean isRandomAccess();
  
  double getPosition();
  
  void setPosition(double paramDouble);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\Control.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */