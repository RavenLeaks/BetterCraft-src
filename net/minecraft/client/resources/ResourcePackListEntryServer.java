/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.data.PackMetadataSection;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ResourcePackListEntryServer
/*     */   extends ResourcePackListEntry {
/*  16 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final IResourcePack resourcePack;
/*     */   private final ResourceLocation resourcePackIcon;
/*     */   
/*     */   public ResourcePackListEntryServer(GuiScreenResourcePacks p_i46594_1_, IResourcePack p_i46594_2_) {
/*  22 */     super(p_i46594_1_); DynamicTexture dynamictexture;
/*  23 */     this.resourcePack = p_i46594_2_;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  28 */       dynamictexture = new DynamicTexture(p_i46594_2_.getPackImage());
/*     */     }
/*  30 */     catch (IOException var5) {
/*     */       
/*  32 */       dynamictexture = TextureUtil.MISSING_TEXTURE;
/*     */     } 
/*     */     
/*  35 */     this.resourcePackIcon = this.mc.getTextureManager().getDynamicTextureLocation("texturepackicon", dynamictexture);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getResourcePackFormat() {
/*  40 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getResourcePackDescription() {
/*     */     try {
/*  47 */       PackMetadataSection packmetadatasection = this.resourcePack.<PackMetadataSection>getPackMetadata((this.mc.getResourcePackRepository()).rprMetadataSerializer, "pack");
/*     */       
/*  49 */       if (packmetadatasection != null)
/*     */       {
/*  51 */         return packmetadatasection.getPackDescription().getFormattedText();
/*     */       }
/*     */     }
/*  54 */     catch (JsonParseException jsonparseexception) {
/*     */       
/*  56 */       LOGGER.error("Couldn't load metadata info", (Throwable)jsonparseexception);
/*     */     }
/*  58 */     catch (IOException ioexception) {
/*     */       
/*  60 */       LOGGER.error("Couldn't load metadata info", ioexception);
/*     */     } 
/*     */     
/*  63 */     return TextFormatting.RED + "Missing " + "pack.mcmeta" + " :(";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canMoveRight() {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canMoveLeft() {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canMoveUp() {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canMoveDown() {
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getResourcePackName() {
/*  88 */     return "Server";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bindResourcePackIcon() {
/*  93 */     this.mc.getTextureManager().bindTexture(this.resourcePackIcon);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean showHoverOverlay() {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isServerPack() {
/* 103 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\ResourcePackListEntryServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */