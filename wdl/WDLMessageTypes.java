/*     */ package wdl;
/*     */ 
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import wdl.api.IWDLMessageType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum WDLMessageTypes
/*     */   implements IWDLMessageType
/*     */ {
/*  18 */   INFO("wdl.messages.message.info", TextFormatting.RED, 
/*  19 */     TextFormatting.GOLD, true, MessageTypeCategory.CORE_RECOMMENDED),
/*  20 */   ERROR("wdl.messages.message.error", TextFormatting.DARK_GREEN, 
/*  21 */     TextFormatting.DARK_RED, true, 
/*  22 */     MessageTypeCategory.CORE_RECOMMENDED),
/*  23 */   UPDATES("wdl.messages.message.updates", TextFormatting.RED, 
/*  24 */     TextFormatting.GOLD, true, MessageTypeCategory.CORE_RECOMMENDED),
/*  25 */   LOAD_TILE_ENTITY("wdl.messages.message.loadingTileEntity", false),
/*  26 */   ON_WORLD_LOAD("wdl.messages.message.onWorldLoad", false),
/*  27 */   ON_BLOCK_EVENT("wdl.messages.message.blockEvent", true),
/*  28 */   ON_MAP_SAVED("wdl.messages.message.mapDataSaved", false),
/*  29 */   ON_CHUNK_NO_LONGER_NEEDED("wdl.messages.message.chunkUnloaded", false),
/*  30 */   ON_GUI_CLOSED_INFO("wdl.messages.message.guiClosedInfo", true),
/*  31 */   ON_GUI_CLOSED_WARNING("wdl.messages.message.guiClosedWarning", true),
/*  32 */   SAVING("wdl.messages.message.saving", true),
/*  33 */   REMOVE_ENTITY("wdl.messages.message.removeEntity", false),
/*  34 */   PLUGIN_CHANNEL_MESSAGE("wdl.messages.message.pluginChannel", false),
/*  35 */   UPDATE_DEBUG("wdl.messages.message.updateDebug", false);
/*     */ 
/*     */   
/*     */   private final String displayTextKey;
/*     */ 
/*     */   
/*     */   private final TextFormatting titleColor;
/*     */ 
/*     */   
/*     */   private final TextFormatting textColor;
/*     */   
/*     */   private final String descriptionKey;
/*     */   
/*     */   private final boolean enabledByDefault;
/*     */ 
/*     */   
/*     */   WDLMessageTypes(String i18nKey, TextFormatting titleColor, TextFormatting textColor, boolean enabledByDefault, MessageTypeCategory category) {
/*  52 */     this.displayTextKey = String.valueOf(i18nKey) + ".text";
/*  53 */     this.titleColor = titleColor;
/*  54 */     this.textColor = textColor;
/*  55 */     this.descriptionKey = String.valueOf(i18nKey) + ".description";
/*  56 */     this.enabledByDefault = enabledByDefault;
/*     */     
/*  58 */     WDLMessages.registerMessage(name(), this, category);
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
/*     */   public String getDisplayName() {
/*  83 */     return I18n.format(this.displayTextKey, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextFormatting getTitleColor() {
/*  88 */     return this.titleColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextFormatting getTextColor() {
/*  93 */     return this.textColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  98 */     return I18n.format(this.descriptionKey, new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabledByDefault() {
/* 103 */     return this.enabledByDefault;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\WDLMessageTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */