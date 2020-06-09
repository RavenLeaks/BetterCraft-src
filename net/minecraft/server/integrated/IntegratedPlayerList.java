/*    */ package net.minecraft.server.integrated;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.net.SocketAddress;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.management.PlayerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntegratedPlayerList
/*    */   extends PlayerList
/*    */ {
/*    */   private NBTTagCompound hostPlayerData;
/*    */   
/*    */   public IntegratedPlayerList(IntegratedServer server) {
/* 18 */     super(server);
/* 19 */     setViewDistance(10);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writePlayerData(EntityPlayerMP playerIn) {
/* 27 */     if (playerIn.getName().equals(getServerInstance().getServerOwner()))
/*    */     {
/* 29 */       this.hostPlayerData = playerIn.writeToNBT(new NBTTagCompound());
/*    */     }
/*    */     
/* 32 */     super.writePlayerData(playerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String allowUserToConnect(SocketAddress address, GameProfile profile) {
/* 40 */     return (profile.getName().equalsIgnoreCase(getServerInstance().getServerOwner()) && getPlayerByUsername(profile.getName()) != null) ? "That name is already taken." : super.allowUserToConnect(address, profile);
/*    */   }
/*    */ 
/*    */   
/*    */   public IntegratedServer getServerInstance() {
/* 45 */     return (IntegratedServer)super.getServerInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTTagCompound getHostPlayerData() {
/* 53 */     return this.hostPlayerData;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\integrated\IntegratedPlayerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */