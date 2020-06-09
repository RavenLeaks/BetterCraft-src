/*     */ package wdl;
/*     */ 
/*     */ import com.google.common.collect.ImmutableListMultimap;
/*     */ import com.google.common.collect.LinkedListMultimap;
/*     */ import com.google.common.collect.ListMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.event.HoverEvent;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import wdl.api.IWDLMessageType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WDLMessages
/*     */ {
/*  30 */   private static Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MessageRegistration
/*     */   {
/*     */     public final String name;
/*     */ 
/*     */ 
/*     */     
/*     */     public final IWDLMessageType type;
/*     */ 
/*     */     
/*     */     public final MessageTypeCategory category;
/*     */ 
/*     */ 
/*     */     
/*     */     public MessageRegistration(String name, IWDLMessageType type, MessageTypeCategory category) {
/*  49 */       this.name = name;
/*  50 */       this.type = type;
/*  51 */       this.category = category;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  56 */       return "MessageRegistration [name=" + this.name + ", type=" + this.type + 
/*  57 */         ", category=" + this.category + "]";
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  62 */       int prime = 31;
/*  63 */       int result = 1;
/*  64 */       result = 31 * result + ((this.name == null) ? 0 : this.name.hashCode());
/*  65 */       result = 31 * result + ((this.category == null) ? 0 : this.category.hashCode());
/*  66 */       result = 31 * result + ((this.type == null) ? 0 : this.type.hashCode());
/*  67 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/*  72 */       if (this == obj) {
/*  73 */         return true;
/*     */       }
/*  75 */       if (obj == null) {
/*  76 */         return false;
/*     */       }
/*  78 */       if (!(obj instanceof MessageRegistration)) {
/*  79 */         return false;
/*     */       }
/*  81 */       MessageRegistration other = (MessageRegistration)obj;
/*  82 */       if (this.name == null) {
/*  83 */         if (other.name != null) {
/*  84 */           return false;
/*     */         }
/*  86 */       } else if (!this.name.equals(other.name)) {
/*  87 */         return false;
/*     */       } 
/*  89 */       if (this.category == null) {
/*  90 */         if (other.category != null) {
/*  91 */           return false;
/*     */         }
/*  93 */       } else if (!this.category.equals(other.category)) {
/*  94 */         return false;
/*     */       } 
/*  96 */       if (this.type == null) {
/*  97 */         if (other.type != null) {
/*  98 */           return false;
/*     */         }
/* 100 */       } else if (!this.type.equals(other.type)) {
/* 101 */         return false;
/*     */       } 
/* 103 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableAllMessages = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   private static List<MessageRegistration> registrations = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MessageRegistration getRegistration(String name) {
/* 126 */     for (MessageRegistration r : registrations) {
/* 127 */       if (r.name.equals(name)) {
/* 128 */         return r;
/*     */       }
/*     */     } 
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MessageRegistration getRegistration(IWDLMessageType type) {
/* 140 */     for (MessageRegistration r : registrations) {
/* 141 */       if (r.type.equals(type)) {
/* 142 */         return r;
/*     */       }
/*     */     } 
/* 145 */     return null;
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
/*     */   public static void registerMessage(String name, IWDLMessageType type, MessageTypeCategory category) {
/* 157 */     registrations.add(new MessageRegistration(name, type, category));
/*     */     
/* 159 */     WDL.defaultProps.setProperty("Messages." + name, 
/* 160 */         Boolean.toString(type.isEnabledByDefault()));
/* 161 */     WDL.defaultProps.setProperty("MessageGroup." + category.internalName, 
/* 162 */         "true");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEnabled(IWDLMessageType type) {
/* 169 */     if (type == null) {
/* 170 */       return false;
/*     */     }
/* 172 */     if (!enableAllMessages) {
/* 173 */       return false;
/*     */     }
/* 175 */     MessageRegistration r = getRegistration(type);
/* 176 */     if (r == null) {
/* 177 */       return false;
/*     */     }
/*     */     
/* 180 */     if (!isGroupEnabled(r.category)) {
/* 181 */       return false;
/*     */     }
/*     */     
/* 184 */     if (!WDL.baseProps.containsKey("Messages." + r.name)) {
/* 185 */       if (WDL.baseProps.containsKey("Debug." + r.name)) {
/*     */         
/* 187 */         WDL.baseProps.put("Messages." + r.name, 
/* 188 */             WDL.baseProps.remove("Debug." + r.name));
/*     */       } else {
/* 190 */         WDL.baseProps.setProperty("Messages." + r.name, 
/* 191 */             Boolean.toString(r.type.isEnabledByDefault()));
/*     */       } 
/*     */     }
/* 194 */     return WDL.baseProps.getProperty("Messages." + r.name).equals("true");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void toggleEnabled(IWDLMessageType type) {
/* 202 */     MessageRegistration r = getRegistration(type);
/*     */     
/* 204 */     if (r != null) {
/* 205 */       WDL.baseProps.setProperty("Messages." + r.name, 
/* 206 */           Boolean.toString(!isEnabled(type)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isGroupEnabled(MessageTypeCategory group) {
/* 214 */     if (!enableAllMessages) {
/* 215 */       return false;
/*     */     }
/*     */     
/* 218 */     return WDL.baseProps.getProperty(
/* 219 */         "MessageGroup." + group.internalName, "true").equals(
/* 220 */         "true");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void toggleGroupEnabled(MessageTypeCategory group) {
/* 227 */     WDL.baseProps.setProperty("MessageGroup." + group.internalName, 
/* 228 */         Boolean.toString(!isGroupEnabled(group)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListMultimap<MessageTypeCategory, IWDLMessageType> getTypes() {
/* 236 */     LinkedListMultimap linkedListMultimap = 
/* 237 */       LinkedListMultimap.create();
/*     */     
/* 239 */     for (MessageRegistration r : registrations) {
/* 240 */       linkedListMultimap.put(r.category, r.type);
/*     */     }
/*     */     
/* 243 */     return (ListMultimap<MessageTypeCategory, IWDLMessageType>)ImmutableListMultimap.copyOf((Multimap)linkedListMultimap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resetEnabledToDefaults() {
/* 250 */     WDL.baseProps.setProperty("Messages.enableAll", "true");
/* 251 */     enableAllMessages = WDL.globalProps.getProperty("Messages.enableAll", 
/* 252 */         "true").equals("true");
/*     */     
/* 254 */     for (MessageRegistration r : registrations) {
/* 255 */       WDL.baseProps.setProperty(
/* 256 */           "MessageGroup." + r.category.internalName, 
/* 257 */           WDL.globalProps.getProperty("MessageGroup." + 
/* 258 */             r.category.internalName, "true"));
/* 259 */       WDL.baseProps.setProperty(
/* 260 */           "Messages." + r.name, 
/* 261 */           WDL.globalProps.getProperty("Messages." + r.name));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onNewServer() {
/* 269 */     if (!WDL.baseProps.containsKey("Messages.enableAll")) {
/* 270 */       if (WDL.baseProps.containsKey("Debug.globalDebugEnabled")) {
/*     */         
/* 272 */         WDL.baseProps.put("Messages.enableAll", 
/* 273 */             WDL.baseProps.remove("Debug.globalDebugEnabled"));
/*     */       } else {
/* 275 */         WDL.baseProps.setProperty("Messages.enableAll", 
/* 276 */             WDL.globalProps.getProperty("Messages.enableAll", "true"));
/*     */       } 
/*     */     }
/*     */     
/* 280 */     enableAllMessages = WDL.baseProps.getProperty("Messages.enableAll")
/* 281 */       .equals("true");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void chatMessage(IWDLMessageType type, String message) {
/* 291 */     chatMessage(type, (ITextComponent)new TextComponentString(message));
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
/*     */   public static void chatMessageTranslated(IWDLMessageType type, String translationKey, Object... args) {
/* 309 */     List<Throwable> exceptionsToPrint = new ArrayList<>();
/*     */     int i;
/* 311 */     for (i = 0; i < args.length; i++) {
/* 312 */       if (args[i] instanceof Entity) {
/* 313 */         TextComponentString textComponentString; Entity e = (Entity)args[i];
/* 314 */         String entityType = EntityUtils.getEntityType(e);
/* 315 */         HoverEvent event = null;
/* 316 */         String customName = null;
/*     */         
/*     */         try {
/* 319 */           event = e.getDisplayName().getStyle().getHoverEvent();
/*     */           
/* 321 */           if (e.hasCustomName()) {
/* 322 */             customName = e.getCustomNameTag();
/*     */           }
/* 324 */         } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 330 */         if (customName != null) {
/* 331 */           TextComponentTranslation textComponentTranslation = new TextComponentTranslation(
/* 332 */               "wdl.messages.entityTypeAndCustomName", new Object[] { entityType, 
/* 333 */                 customName });
/*     */         } else {
/* 335 */           textComponentString = new TextComponentString(entityType);
/*     */         } 
/* 337 */         textComponentString.setStyle(textComponentString.getStyle()
/* 338 */             .setHoverEvent(event));
/*     */         
/* 340 */         args[i] = textComponentString;
/* 341 */       } else if (args[i] instanceof Throwable) {
/* 342 */         Throwable t = (Throwable)args[i];
/* 343 */         TextComponentString textComponentString = new TextComponentString(t.toString());
/*     */ 
/*     */         
/* 346 */         StringWriter sw = new StringWriter();
/* 347 */         t.printStackTrace(new PrintWriter(sw));
/* 348 */         String exceptionAsString = sw.toString();
/*     */         
/* 350 */         exceptionAsString = exceptionAsString.replace("\r", "")
/* 351 */           .replace("\t", "    ");
/*     */         
/* 353 */         HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
/* 354 */             (ITextComponent)new TextComponentString(exceptionAsString));
/*     */         
/* 356 */         textComponentString.setStyle(textComponentString.getStyle()
/* 357 */             .setHoverEvent(event));
/*     */ 
/*     */         
/* 360 */         logger.warn(t);
/*     */         
/* 362 */         args[i] = textComponentString;
/*     */         
/* 364 */         exceptionsToPrint.add(t);
/*     */       } 
/*     */     } 
/*     */     
/* 368 */     chatMessage(type, (ITextComponent)new TextComponentTranslation(translationKey, args));
/*     */     
/* 370 */     for (i = 0; i < exceptionsToPrint.size(); i++) {
/* 371 */       logger.warn("Exception #" + (i + 1) + ": ", exceptionsToPrint.get(i));
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
/*     */   public static void chatMessage(IWDLMessageType type, ITextComponent message) {
/* 383 */     String tooltipText = I18n.format("wdl.messages.tooltip", new Object[] {
/* 384 */           type.getDisplayName() }).replace("\r", "");
/* 385 */     TextComponentString textComponentString1 = new TextComponentString(tooltipText);
/*     */     
/* 387 */     TextComponentString textComponentString2 = new TextComponentString("");
/*     */     
/* 389 */     TextComponentString textComponentString3 = new TextComponentString("[WorldDL]");
/* 390 */     textComponentString3.getStyle().setColor(type.getTitleColor());
/* 391 */     textComponentString3.getStyle().setHoverEvent(
/* 392 */         new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)textComponentString1));
/*     */ 
/*     */ 
/*     */     
/* 396 */     TextComponentString messageFormat = new TextComponentString(" ");
/* 397 */     messageFormat.getStyle().setColor(type.getTextColor());
/*     */     
/* 399 */     messageFormat.appendSibling(message);
/* 400 */     textComponentString2.appendSibling((ITextComponent)textComponentString3);
/* 401 */     textComponentString2.appendSibling((ITextComponent)messageFormat);
/* 402 */     if (isEnabled(type)) {
/* 403 */       (Minecraft.getMinecraft()).ingameGUI.getChatGUI().printChatMessage(
/* 404 */           (ITextComponent)textComponentString2);
/*     */     } else {
/* 406 */       logger.info(textComponentString2.getUnformattedText());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\WDLMessages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */