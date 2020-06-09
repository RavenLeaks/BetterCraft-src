package net.minecraft.network.datasync;

import java.io.IOException;
import net.minecraft.network.PacketBuffer;

public interface DataSerializer<T> {
  void write(PacketBuffer paramPacketBuffer, T paramT);
  
  T read(PacketBuffer paramPacketBuffer) throws IOException;
  
  DataParameter<T> createKey(int paramInt);
  
  T func_192717_a(T paramT);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\datasync\DataSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */