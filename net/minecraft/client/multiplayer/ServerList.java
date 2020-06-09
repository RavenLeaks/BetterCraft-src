/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import me.nzxter.bettercraft.utils.ServerDataFeaturedUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ServerList
/*     */ {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*  22 */   private final List<ServerData> servers = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public ServerList(Minecraft mcIn) {
/*  26 */     this.mc = mcIn;
/*  27 */     loadServerList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadFeaturedServers() {
/*  37 */     addServerData((ServerData)new ServerDataFeaturedUtils("Awesome Featured Server", "nzxter.tk"));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFeaturedServerCount() {
/*  42 */     int count = 0;
/*  43 */     for (ServerData sd : this.servers) {
/*  44 */       if (sd instanceof ServerDataFeaturedUtils) {
/*  45 */         count++;
/*     */       }
/*     */     } 
/*  48 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadServerList() {
/*     */     try {
/*  56 */       this.servers.clear();
/*     */ 
/*     */       
/*  59 */       loadFeaturedServers();
/*     */ 
/*     */       
/*  62 */       NBTTagCompound nbttagcompound = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));
/*     */       
/*  64 */       if (nbttagcompound == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  69 */       NBTTagList nbttaglist = nbttagcompound.getTagList("servers", 10);
/*     */       
/*  71 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */       {
/*  73 */         this.servers.add(ServerData.getServerDataFromNBTCompound(nbttaglist.getCompoundTagAt(i)));
/*     */       }
/*     */     }
/*  76 */     catch (Exception exception) {
/*     */       
/*  78 */       LOGGER.error("Couldn't load server list", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveServerList() {
/*     */     try {
/*  92 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/*  94 */       for (ServerData serverdata : this.servers) {
/*     */ 
/*     */         
/*  97 */         if (!(serverdata instanceof ServerDataFeaturedUtils)) {
/*  98 */           nbttaglist.appendTag((NBTBase)serverdata.getNBTCompound());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 103 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 104 */       nbttagcompound.setTag("servers", (NBTBase)nbttaglist);
/* 105 */       CompressedStreamTools.safeWrite(nbttagcompound, new File(this.mc.mcDataDir, "servers.dat"));
/*     */     }
/* 107 */     catch (Exception exception) {
/*     */       
/* 109 */       LOGGER.error("Couldn't save server list", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerData getServerData(int index) {
/* 118 */     return this.servers.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeServerData(int index) {
/* 126 */     this.servers.remove(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addServerData(ServerData server) {
/* 134 */     this.servers.add(server);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int countServers() {
/* 142 */     return this.servers.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void swapServers(int pos1, int pos2) {
/* 150 */     ServerData serverdata = getServerData(pos1);
/* 151 */     this.servers.set(pos1, getServerData(pos2));
/* 152 */     this.servers.set(pos2, serverdata);
/* 153 */     saveServerList();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int index, ServerData server) {
/* 158 */     this.servers.set(index, server);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveSingleServer(ServerData server) {
/* 163 */     ServerList serverlist = new ServerList(Minecraft.getMinecraft());
/* 164 */     serverlist.loadServerList();
/*     */     
/* 166 */     for (int i = 0; i < serverlist.countServers(); i++) {
/*     */       
/* 168 */       ServerData serverdata = serverlist.getServerData(i);
/*     */       
/* 170 */       if (serverdata.serverName.equals(server.serverName) && serverdata.serverIP.equals(server.serverIP)) {
/*     */         
/* 172 */         serverlist.set(i, server);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 177 */     serverlist.saveServerList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\multiplayer\ServerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */