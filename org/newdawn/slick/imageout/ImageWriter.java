package org.newdawn.slick.imageout;

import java.io.IOException;
import java.io.OutputStream;
import org.newdawn.slick.Image;

public interface ImageWriter {
  void saveImage(Image paramImage, String paramString, OutputStream paramOutputStream, boolean paramBoolean) throws IOException;
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\imageout\ImageWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */