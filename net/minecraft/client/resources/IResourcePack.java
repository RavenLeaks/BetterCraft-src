package net.minecraft.client.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;

public interface IResourcePack {
  InputStream getInputStream(ResourceLocation paramResourceLocation) throws IOException;
  
  boolean resourceExists(ResourceLocation paramResourceLocation);
  
  Set<String> getResourceDomains();
  
  @Nullable
  <T extends net.minecraft.client.resources.data.IMetadataSection> T getPackMetadata(MetadataSerializer paramMetadataSerializer, String paramString) throws IOException;
  
  BufferedImage getPackImage() throws IOException;
  
  String getPackName();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\IResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */