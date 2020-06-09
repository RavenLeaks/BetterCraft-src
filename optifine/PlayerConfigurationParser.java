/*     */ package optifine;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class PlayerConfigurationParser
/*     */ {
/*  17 */   private String player = null;
/*     */   
/*     */   public static final String CONFIG_ITEMS = "items";
/*     */   public static final String ITEM_TYPE = "type";
/*     */   public static final String ITEM_ACTIVE = "active";
/*     */   
/*     */   public PlayerConfigurationParser(String p_i70_1_) {
/*  24 */     this.player = p_i70_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerConfiguration parsePlayerConfiguration(JsonElement p_parsePlayerConfiguration_1_) {
/*  29 */     if (p_parsePlayerConfiguration_1_ == null)
/*     */     {
/*  31 */       throw new JsonParseException("JSON object is null, player: " + this.player);
/*     */     }
/*     */ 
/*     */     
/*  35 */     JsonObject jsonobject = (JsonObject)p_parsePlayerConfiguration_1_;
/*  36 */     PlayerConfiguration playerconfiguration = new PlayerConfiguration();
/*  37 */     JsonArray jsonarray = (JsonArray)jsonobject.get("items");
/*     */     
/*  39 */     if (jsonarray != null)
/*     */     {
/*  41 */       for (int i = 0; i < jsonarray.size(); i++) {
/*     */         
/*  43 */         JsonObject jsonobject1 = (JsonObject)jsonarray.get(i);
/*  44 */         boolean flag = Json.getBoolean(jsonobject1, "active", true);
/*     */         
/*  46 */         if (flag) {
/*     */           
/*  48 */           String s = Json.getString(jsonobject1, "type");
/*     */           
/*  50 */           if (s == null) {
/*     */             
/*  52 */             Config.warn("Item type is null, player: " + this.player);
/*     */             
/*     */             continue;
/*     */           } 
/*  56 */           String s1 = Json.getString(jsonobject1, "model");
/*     */           
/*  58 */           if (s1 == null)
/*     */           {
/*  60 */             s1 = "items/" + s + "/model.cfg";
/*     */           }
/*     */           
/*  63 */           PlayerItemModel playeritemmodel = downloadModel(s1);
/*     */           
/*  65 */           if (playeritemmodel != null) {
/*     */             
/*  67 */             if (!playeritemmodel.isUsePlayerTexture()) {
/*     */               
/*  69 */               String s2 = Json.getString(jsonobject1, "texture");
/*     */               
/*  71 */               if (s2 == null)
/*     */               {
/*  73 */                 s2 = "items/" + s + "/users/" + this.player + ".png";
/*     */               }
/*     */               
/*  76 */               BufferedImage bufferedimage = downloadTextureImage(s2);
/*     */               
/*  78 */               if (bufferedimage == null) {
/*     */                 continue;
/*     */               }
/*     */ 
/*     */               
/*  83 */               playeritemmodel.setTextureImage(bufferedimage);
/*  84 */               ResourceLocation resourcelocation = new ResourceLocation("optifine.net", s2);
/*  85 */               playeritemmodel.setTextureLocation(resourcelocation);
/*     */             } 
/*     */             
/*  88 */             playerconfiguration.addPlayerItemModel(playeritemmodel);
/*     */           } 
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */     }
/*  95 */     return playerconfiguration;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage downloadTextureImage(String p_downloadTextureImage_1_) {
/* 101 */     String s = String.valueOf(HttpUtils.getPlayerItemsUrl()) + "/" + p_downloadTextureImage_1_;
/*     */ 
/*     */     
/*     */     try {
/* 105 */       byte[] abyte = HttpPipeline.get(s, Minecraft.getMinecraft().getProxy());
/* 106 */       BufferedImage bufferedimage = ImageIO.read(new ByteArrayInputStream(abyte));
/* 107 */       return bufferedimage;
/*     */     }
/* 109 */     catch (IOException ioexception) {
/*     */       
/* 111 */       Config.warn("Error loading item texture " + p_downloadTextureImage_1_ + ": " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 112 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private PlayerItemModel downloadModel(String p_downloadModel_1_) {
/* 118 */     String s = String.valueOf(HttpUtils.getPlayerItemsUrl()) + "/" + p_downloadModel_1_;
/*     */ 
/*     */     
/*     */     try {
/* 122 */       byte[] abyte = HttpPipeline.get(s, Minecraft.getMinecraft().getProxy());
/* 123 */       String s1 = new String(abyte, "ASCII");
/* 124 */       JsonParser jsonparser = new JsonParser();
/* 125 */       JsonObject jsonobject = (JsonObject)jsonparser.parse(s1);
/* 126 */       PlayerItemModel playeritemmodel = PlayerItemParser.parseItemModel(jsonobject);
/* 127 */       return playeritemmodel;
/*     */     }
/* 129 */     catch (Exception exception) {
/*     */       
/* 131 */       Config.warn("Error loading item model " + p_downloadModel_1_ + ": " + exception.getClass().getName() + ": " + exception.getMessage());
/* 132 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\PlayerConfigurationParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */