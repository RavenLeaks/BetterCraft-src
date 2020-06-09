package javazoom.jl.player;

import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;

public interface AudioDevice {
  void open(Decoder paramDecoder) throws JavaLayerException;
  
  boolean isOpen();
  
  void write(short[] paramArrayOfshort, int paramInt1, int paramInt2) throws JavaLayerException;
  
  void close();
  
  void flush();
  
  int getPosition();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\AudioDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */