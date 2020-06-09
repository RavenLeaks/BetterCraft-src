package net.minecraft.client.resources;

import java.io.Closeable;
import java.io.InputStream;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;

public interface IResource extends Closeable {
  ResourceLocation getResourceLocation();
  
  InputStream getInputStream();
  
  boolean hasMetadata();
  
  @Nullable
  <T extends net.minecraft.client.resources.data.IMetadataSection> T getMetadata(String paramString);
  
  String getResourcePackName();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\IResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */