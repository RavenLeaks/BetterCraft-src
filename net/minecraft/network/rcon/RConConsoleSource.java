/*    */ package net.minecraft.network.rcon;
/*    */ 
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class RConConsoleSource
/*    */   implements ICommandSender
/*    */ {
/* 11 */   private final StringBuffer buffer = new StringBuffer();
/*    */   
/*    */   private final MinecraftServer server;
/*    */   
/*    */   public RConConsoleSource(MinecraftServer serverIn) {
/* 16 */     this.server = serverIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 24 */     return "Rcon";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addChatMessage(ITextComponent component) {
/* 32 */     this.buffer.append(component.getUnformattedText());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public World getEntityWorld() {
/* 49 */     return this.server.getEntityWorld();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean sendCommandFeedback() {
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MinecraftServer getServer() {
/* 65 */     return this.server;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\rcon\RConConsoleSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */