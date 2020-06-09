/*    */ package net.minecraft.command;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ICommandSender
/*    */ {
/*    */   String getName();
/*    */   
/*    */   default ITextComponent getDisplayName() {
/* 21 */     return (ITextComponent)new TextComponentString(getName());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   default void addChatMessage(ITextComponent component) {}
/*    */ 
/*    */ 
/*    */   
/*    */   boolean canCommandSenderUseCommand(int paramInt, String paramString);
/*    */ 
/*    */ 
/*    */   
/*    */   default BlockPos getPosition() {
/* 35 */     return BlockPos.ORIGIN;
/*    */   }
/*    */ 
/*    */   
/*    */   default Vec3d getPositionVector() {
/* 40 */     return Vec3d.ZERO;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   World getEntityWorld();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   default Entity getCommandSenderEntity() {
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean sendCommandFeedback() {
/* 58 */     return false;
/*    */   }
/*    */   
/*    */   default void setCommandStat(CommandResultStats.Type type, int amount) {}
/*    */   
/*    */   @Nullable
/*    */   MinecraftServer getServer();
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\ICommandSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */