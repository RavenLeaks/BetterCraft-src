/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerData
/*     */ {
/*     */   public String serverName;
/*     */   public String serverIP;
/*     */   public String populationInfo;
/*     */   public String serverMOTD;
/*     */   public long pingToServer;
/*  26 */   public int version = 340;
/*     */ 
/*     */   
/*  29 */   public String gameVersion = "1.12.2";
/*     */   public boolean pinged;
/*     */   public String playerList;
/*  32 */   private ServerResourceMode resourceMode = ServerResourceMode.PROMPT;
/*     */   
/*     */   private String serverIcon;
/*     */   
/*     */   private boolean lanServer;
/*     */ 
/*     */   
/*     */   public ServerData(String name, String ip, boolean isLan) {
/*  40 */     this.serverName = name;
/*  41 */     this.serverIP = ip;
/*  42 */     this.lanServer = isLan;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getNBTCompound() {
/*  50 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  51 */     nbttagcompound.setString("name", this.serverName);
/*  52 */     nbttagcompound.setString("ip", this.serverIP);
/*     */     
/*  54 */     if (this.serverIcon != null)
/*     */     {
/*  56 */       nbttagcompound.setString("icon", this.serverIcon);
/*     */     }
/*     */     
/*  59 */     if (this.resourceMode == ServerResourceMode.ENABLED) {
/*     */       
/*  61 */       nbttagcompound.setBoolean("acceptTextures", true);
/*     */     }
/*  63 */     else if (this.resourceMode == ServerResourceMode.DISABLED) {
/*     */       
/*  65 */       nbttagcompound.setBoolean("acceptTextures", false);
/*     */     } 
/*     */     
/*  68 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerResourceMode getResourceMode() {
/*  73 */     return this.resourceMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResourceMode(ServerResourceMode mode) {
/*  78 */     this.resourceMode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ServerData getServerDataFromNBTCompound(NBTTagCompound nbtCompound) {
/*  86 */     ServerData serverdata = new ServerData(nbtCompound.getString("name"), nbtCompound.getString("ip"), false);
/*     */     
/*  88 */     if (nbtCompound.hasKey("icon", 8))
/*     */     {
/*  90 */       serverdata.setBase64EncodedIconData(nbtCompound.getString("icon"));
/*     */     }
/*     */     
/*  93 */     if (nbtCompound.hasKey("acceptTextures", 1)) {
/*     */       
/*  95 */       if (nbtCompound.getBoolean("acceptTextures"))
/*     */       {
/*  97 */         serverdata.setResourceMode(ServerResourceMode.ENABLED);
/*     */       }
/*     */       else
/*     */       {
/* 101 */         serverdata.setResourceMode(ServerResourceMode.DISABLED);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 106 */       serverdata.setResourceMode(ServerResourceMode.PROMPT);
/*     */     } 
/*     */     
/* 109 */     return serverdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBase64EncodedIconData() {
/* 117 */     return this.serverIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBase64EncodedIconData(String icon) {
/* 122 */     this.serverIcon = icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnLAN() {
/* 130 */     return this.lanServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyFrom(ServerData serverDataIn) {
/* 135 */     this.serverIP = serverDataIn.serverIP;
/* 136 */     this.serverName = serverDataIn.serverName;
/* 137 */     setResourceMode(serverDataIn.getResourceMode());
/* 138 */     this.serverIcon = serverDataIn.serverIcon;
/* 139 */     this.lanServer = serverDataIn.lanServer;
/*     */   }
/*     */   
/*     */   public enum ServerResourceMode
/*     */   {
/* 144 */     ENABLED("enabled"),
/* 145 */     DISABLED("disabled"),
/* 146 */     PROMPT("prompt");
/*     */     
/*     */     private final ITextComponent motd;
/*     */ 
/*     */     
/*     */     ServerResourceMode(String name) {
/* 152 */       this.motd = (ITextComponent)new TextComponentTranslation("addServer.resourcePack." + name, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public ITextComponent getMotd() {
/* 157 */       return this.motd;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\multiplayer\ServerData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */