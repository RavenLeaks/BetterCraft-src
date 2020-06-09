package javazoom.jl.decoder;

import java.io.IOException;

public interface Source {
  public static final long LENGTH_UNKNOWN = -1L;
  
  int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
  
  boolean willReadBlock();
  
  boolean isSeekable();
  
  long length();
  
  long tell();
  
  long seek(long paramLong);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\Source.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */