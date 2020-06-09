/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.world.GameType;
/*     */ 
/*     */ public class SPacketPlayerListItem implements Packet<INetHandlerPlayClient> {
/*     */   private Action action;
/*  20 */   private final List<AddPlayerData> players = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SPacketPlayerListItem(Action actionIn, EntityPlayerMP... playersIn) {
/*  28 */     this.action = actionIn; byte b; int i;
/*     */     EntityPlayerMP[] arrayOfEntityPlayerMP;
/*  30 */     for (i = (arrayOfEntityPlayerMP = playersIn).length, b = 0; b < i; ) { EntityPlayerMP entityplayermp = arrayOfEntityPlayerMP[b];
/*     */       
/*  32 */       this.players.add(new AddPlayerData(entityplayermp.getGameProfile(), entityplayermp.ping, entityplayermp.interactionManager.getGameType(), entityplayermp.getTabListDisplayName()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public SPacketPlayerListItem(Action actionIn, Iterable<EntityPlayerMP> playersIn) {
/*  38 */     this.action = actionIn;
/*     */     
/*  40 */     for (EntityPlayerMP entityplayermp : playersIn)
/*     */     {
/*  42 */       this.players.add(new AddPlayerData(entityplayermp.getGameProfile(), entityplayermp.ping, entityplayermp.interactionManager.getGameType(), entityplayermp.getTabListDisplayName()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  51 */     this.action = (Action)buf.readEnumValue(Action.class);
/*  52 */     int i = buf.readVarIntFromBuffer();
/*     */     
/*  54 */     for (int j = 0; j < i; j++) {
/*     */       int l, i1;
/*  56 */       GameProfile gameprofile = null;
/*  57 */       int k = 0;
/*  58 */       GameType gametype = null;
/*  59 */       ITextComponent itextcomponent = null;
/*     */       
/*  61 */       switch (this.action) {
/*     */         
/*     */         case null:
/*  64 */           gameprofile = new GameProfile(buf.readUuid(), buf.readStringFromBuffer(16));
/*  65 */           l = buf.readVarIntFromBuffer();
/*  66 */           i1 = 0;
/*     */           
/*  68 */           for (; i1 < l; i1++) {
/*     */             
/*  70 */             String s = buf.readStringFromBuffer(32767);
/*  71 */             String s1 = buf.readStringFromBuffer(32767);
/*     */             
/*  73 */             if (buf.readBoolean()) {
/*     */               
/*  75 */               gameprofile.getProperties().put(s, new Property(s, s1, buf.readStringFromBuffer(32767)));
/*     */             }
/*     */             else {
/*     */               
/*  79 */               gameprofile.getProperties().put(s, new Property(s, s1));
/*     */             } 
/*     */           } 
/*     */           
/*  83 */           gametype = GameType.getByID(buf.readVarIntFromBuffer());
/*  84 */           k = buf.readVarIntFromBuffer();
/*     */           
/*  86 */           if (buf.readBoolean())
/*     */           {
/*  88 */             itextcomponent = buf.readTextComponent();
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case UPDATE_GAME_MODE:
/*  94 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*  95 */           gametype = GameType.getByID(buf.readVarIntFromBuffer());
/*     */           break;
/*     */         
/*     */         case UPDATE_LATENCY:
/*  99 */           gameprofile = new GameProfile(buf.readUuid(), null);
/* 100 */           k = buf.readVarIntFromBuffer();
/*     */           break;
/*     */         
/*     */         case UPDATE_DISPLAY_NAME:
/* 104 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*     */           
/* 106 */           if (buf.readBoolean())
/*     */           {
/* 108 */             itextcomponent = buf.readTextComponent();
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case REMOVE_PLAYER:
/* 114 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*     */           break;
/*     */       } 
/* 117 */       this.players.add(new AddPlayerData(gameprofile, k, gametype, itextcomponent));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 126 */     buf.writeEnumValue(this.action);
/* 127 */     buf.writeVarIntToBuffer(this.players.size());
/*     */     
/* 129 */     for (AddPlayerData spacketplayerlistitem$addplayerdata : this.players) {
/*     */       
/* 131 */       switch (this.action) {
/*     */         
/*     */         case null:
/* 134 */           buf.writeUuid(spacketplayerlistitem$addplayerdata.getProfile().getId());
/* 135 */           buf.writeString(spacketplayerlistitem$addplayerdata.getProfile().getName());
/* 136 */           buf.writeVarIntToBuffer(spacketplayerlistitem$addplayerdata.getProfile().getProperties().size());
/*     */           
/* 138 */           for (Property property : spacketplayerlistitem$addplayerdata.getProfile().getProperties().values()) {
/*     */             
/* 140 */             buf.writeString(property.getName());
/* 141 */             buf.writeString(property.getValue());
/*     */             
/* 143 */             if (property.hasSignature()) {
/*     */               
/* 145 */               buf.writeBoolean(true);
/* 146 */               buf.writeString(property.getSignature());
/*     */               
/*     */               continue;
/*     */             } 
/* 150 */             buf.writeBoolean(false);
/*     */           } 
/*     */ 
/*     */           
/* 154 */           buf.writeVarIntToBuffer(spacketplayerlistitem$addplayerdata.getGameMode().getID());
/* 155 */           buf.writeVarIntToBuffer(spacketplayerlistitem$addplayerdata.getPing());
/*     */           
/* 157 */           if (spacketplayerlistitem$addplayerdata.getDisplayName() == null) {
/*     */             
/* 159 */             buf.writeBoolean(false);
/*     */             
/*     */             continue;
/*     */           } 
/* 163 */           buf.writeBoolean(true);
/* 164 */           buf.writeTextComponent(spacketplayerlistitem$addplayerdata.getDisplayName());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case UPDATE_GAME_MODE:
/* 170 */           buf.writeUuid(spacketplayerlistitem$addplayerdata.getProfile().getId());
/* 171 */           buf.writeVarIntToBuffer(spacketplayerlistitem$addplayerdata.getGameMode().getID());
/*     */ 
/*     */         
/*     */         case UPDATE_LATENCY:
/* 175 */           buf.writeUuid(spacketplayerlistitem$addplayerdata.getProfile().getId());
/* 176 */           buf.writeVarIntToBuffer(spacketplayerlistitem$addplayerdata.getPing());
/*     */ 
/*     */         
/*     */         case UPDATE_DISPLAY_NAME:
/* 180 */           buf.writeUuid(spacketplayerlistitem$addplayerdata.getProfile().getId());
/*     */           
/* 182 */           if (spacketplayerlistitem$addplayerdata.getDisplayName() == null) {
/*     */             
/* 184 */             buf.writeBoolean(false);
/*     */             
/*     */             continue;
/*     */           } 
/* 188 */           buf.writeBoolean(true);
/* 189 */           buf.writeTextComponent(spacketplayerlistitem$addplayerdata.getDisplayName());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case REMOVE_PLAYER:
/* 195 */           buf.writeUuid(spacketplayerlistitem$addplayerdata.getProfile().getId());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 205 */     handler.handlePlayerListItem(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<AddPlayerData> getEntries() {
/* 210 */     return this.players;
/*     */   }
/*     */ 
/*     */   
/*     */   public Action getAction() {
/* 215 */     return this.action;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 220 */     return MoreObjects.toStringHelper(this).add("action", this.action).add("entries", this.players).toString();
/*     */   }
/*     */   
/*     */   public SPacketPlayerListItem() {}
/*     */   
/* 225 */   public enum Action { ADD_PLAYER,
/* 226 */     UPDATE_GAME_MODE,
/* 227 */     UPDATE_LATENCY,
/* 228 */     UPDATE_DISPLAY_NAME,
/* 229 */     REMOVE_PLAYER; }
/*     */ 
/*     */ 
/*     */   
/*     */   public class AddPlayerData
/*     */   {
/*     */     private final int ping;
/*     */     private final GameType gamemode;
/*     */     private final GameProfile profile;
/*     */     private final ITextComponent displayName;
/*     */     
/*     */     public AddPlayerData(GameProfile profileIn, int latencyIn, @Nullable GameType gameModeIn, ITextComponent displayNameIn) {
/* 241 */       this.profile = profileIn;
/* 242 */       this.ping = latencyIn;
/* 243 */       this.gamemode = gameModeIn;
/* 244 */       this.displayName = displayNameIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile getProfile() {
/* 249 */       return this.profile;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getPing() {
/* 254 */       return this.ping;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameType getGameMode() {
/* 259 */       return this.gamemode;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public ITextComponent getDisplayName() {
/* 265 */       return this.displayName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 270 */       return MoreObjects.toStringHelper(this).add("latency", this.ping).add("gameMode", this.gamemode).add("profile", this.profile).add("displayName", (this.displayName == null) ? null : ITextComponent.Serializer.componentToJson(this.displayName)).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketPlayerListItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */