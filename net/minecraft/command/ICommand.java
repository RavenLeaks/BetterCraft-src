package net.minecraft.command;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public interface ICommand extends Comparable<ICommand> {
  String getCommandName();
  
  String getCommandUsage(ICommandSender paramICommandSender);
  
  List<String> getCommandAliases();
  
  void execute(MinecraftServer paramMinecraftServer, ICommandSender paramICommandSender, String[] paramArrayOfString) throws CommandException;
  
  boolean checkPermission(MinecraftServer paramMinecraftServer, ICommandSender paramICommandSender);
  
  List<String> getTabCompletionOptions(MinecraftServer paramMinecraftServer, ICommandSender paramICommandSender, String[] paramArrayOfString, BlockPos paramBlockPos);
  
  boolean isUsernameIndex(String[] paramArrayOfString, int paramInt);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\ICommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */