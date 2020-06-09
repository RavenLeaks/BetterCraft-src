/*     */ package wdl.api;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.io.File;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import wdl.EntityRealigner;
/*     */ import wdl.HologramHandler;
/*     */ import wdl.MessageTypeCategory;
/*     */ import wdl.WDL;
/*     */ import wdl.WDLMessages;
/*     */ import wdl.WDLPluginChannels;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WDLApi
/*     */ {
/*  29 */   private static Logger logger = LogManager.getLogger();
/*     */   
/*  31 */   private static Map<String, ModInfo<?>> wdlMods = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveTileEntity(BlockPos pos, TileEntity te) {
/*  40 */     if (!WDLPluginChannels.canSaveTileEntities(pos.getX() << 16, 
/*  41 */         pos.getZ() << 16)) {
/*  42 */       logger.warn("API attempted to call saveTileEntity when saving TileEntities is not allowed!  Pos: " + 
/*  43 */           pos + 
/*  44 */           ", te: " + te + ".  StackTrace: ");
/*  45 */       logStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  50 */     WDL.saveTileEntity(pos, te);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addWDLMod(String id, String version, IWDLMod mod) {
/*  57 */     if (id == null) {
/*  58 */       throw new IllegalArgumentException("id must not be null!  (mod=" + 
/*  59 */           mod + ", version=" + version + ")");
/*     */     }
/*  61 */     if (version == null) {
/*  62 */       throw new IllegalArgumentException("version must not be null!  (mod=" + 
/*  63 */           mod + ", id=" + version + ")");
/*     */     }
/*  65 */     if (mod == null) {
/*  66 */       throw new IllegalArgumentException("mod must not be null!  (id=" + 
/*  67 */           id + ", version=" + version + ")");
/*     */     }
/*     */     
/*  70 */     ModInfo<IWDLMod> info = new ModInfo<>(id, version, mod, null);
/*  71 */     if (wdlMods.containsKey(id)) {
/*  72 */       throw new IllegalArgumentException("A mod by the name of '" + 
/*  73 */           id + "' is already registered by " + 
/*  74 */           wdlMods.get(id) + " (tried to register " + 
/*  75 */           info + " over it)");
/*     */     }
/*  77 */     if (!mod.isValidEnvironment("1.11a-beta1")) {
/*  78 */       String errorMessage = mod.getEnvironmentErrorMessage("1.11a-beta1");
/*  79 */       if (errorMessage != null) {
/*  80 */         throw new IllegalArgumentException(errorMessage);
/*     */       }
/*  82 */       throw new IllegalArgumentException("Environment for " + info + 
/*  83 */           " is incorrect!  Perhaps it is for a different" + 
/*  84 */           " version of WDL?  You are running " + "1.11a-beta1" + ".");
/*     */     } 
/*     */ 
/*     */     
/*  88 */     wdlMods.put(id, info);
/*     */ 
/*     */     
/*  91 */     if (mod instanceof IMessageTypeAdder) {
/*  92 */       Map<String, IWDLMessageType> types = (
/*  93 */         (IMessageTypeAdder)mod).getMessageTypes();
/*     */       
/*  95 */       ModMessageTypeCategory category = new ModMessageTypeCategory(info);
/*     */       
/*  97 */       for (Map.Entry<String, IWDLMessageType> e : types.entrySet()) {
/*  98 */         WDLMessages.registerMessage(e.getKey(), e.getValue(), category);
/*     */       }
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
/*     */   
/*     */   public static <T extends IWDLMod> List<ModInfo<T>> getImplementingExtensions(Class<T> clazz) {
/* 113 */     if (clazz == null) {
/* 114 */       throw new IllegalArgumentException("clazz must not be null!");
/*     */     }
/* 116 */     List<ModInfo<T>> returned = new ArrayList<>();
/*     */     
/* 118 */     for (ModInfo<?> info : wdlMods.values()) {
/* 119 */       if (!info.isEnabled()) {
/*     */         continue;
/*     */       }
/*     */       
/* 123 */       if (clazz.isAssignableFrom(info.mod.getClass())) {
/*     */ 
/*     */ 
/*     */         
/* 127 */         ModInfo<T> infoCasted = (ModInfo)info;
/* 128 */         returned.add(infoCasted);
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     return returned;
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
/*     */   public static <T extends IWDLMod> List<ModInfo<T>> getAllImplementingExtensions(Class<T> clazz) {
/* 145 */     if (clazz == null) {
/* 146 */       throw new IllegalArgumentException("clazz must not be null!");
/*     */     }
/* 148 */     List<ModInfo<T>> returned = new ArrayList<>();
/*     */     
/* 150 */     for (ModInfo<?> info : wdlMods.values()) {
/* 151 */       if (clazz.isAssignableFrom(info.mod.getClass())) {
/*     */ 
/*     */ 
/*     */         
/* 155 */         ModInfo<T> infoCasted = (ModInfo)info;
/* 156 */         returned.add(infoCasted);
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     return returned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, ModInfo<?>> getWDLMods() {
/* 167 */     return (Map<String, ModInfo<?>>)ImmutableMap.copyOf(wdlMods);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getModInfo(String name) {
/* 176 */     if (!wdlMods.containsKey(name)) {
/* 177 */       return null;
/*     */     }
/*     */     
/* 180 */     return ((ModInfo)wdlMods.get(name)).getInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void logStackTrace() {
/* 187 */     StackTraceElement[] elements = Thread.currentThread().getStackTrace(); byte b; int i; StackTraceElement[] arrayOfStackTraceElement1;
/* 188 */     for (i = (arrayOfStackTraceElement1 = elements).length, b = 0; b < i; ) { StackTraceElement e = arrayOfStackTraceElement1[b];
/* 189 */       logger.warn(e.toString());
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   private static class ModMessageTypeCategory
/*     */     extends MessageTypeCategory
/*     */   {
/*     */     private WDLApi.ModInfo<?> mod;
/*     */     
/*     */     public ModMessageTypeCategory(WDLApi.ModInfo<?> mod) {
/* 200 */       super(mod.id);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getDisplayName() {
/* 205 */       return this.mod.getDisplayName();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ModInfo<T extends IWDLMod>
/*     */   {
/*     */     public final String id;
/*     */     
/*     */     public final String version;
/*     */     public final T mod;
/*     */     
/*     */     private ModInfo(String id, String version, T mod) {
/* 218 */       this.id = id;
/* 219 */       this.version = version;
/* 220 */       this.mod = mod;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 225 */       return String.valueOf(this.id) + "v" + this.version + " (" + this.mod.toString() + "/" + 
/* 226 */         this.mod.getClass().getName() + ")";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDisplayName() {
/* 234 */       if (this.mod instanceof IWDLModDescripted) {
/* 235 */         String name = ((IWDLModDescripted)this.mod).getDisplayName();
/* 236 */         if (name != null && !name.isEmpty()) {
/* 237 */           return name;
/*     */         }
/*     */       } 
/* 240 */       return this.id;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getInfo() {
/* 247 */       StringBuilder info = new StringBuilder();
/*     */       
/* 249 */       info.append("Id: ").append(this.id).append('\n');
/* 250 */       info.append("Version: ").append(this.version).append('\n');
/* 251 */       if (this.mod instanceof IWDLModDescripted) {
/* 252 */         IWDLModDescripted dmod = (IWDLModDescripted)this.mod;
/*     */         
/* 254 */         String displayName = dmod.getDisplayName();
/* 255 */         String mainAuthor = dmod.getMainAuthor();
/* 256 */         String[] authors = dmod.getAuthors();
/* 257 */         String url = dmod.getURL();
/* 258 */         String description = dmod.getDescription();
/*     */         
/* 260 */         if (displayName != null && !displayName.isEmpty()) {
/* 261 */           info.append("Display name: ").append(displayName).append('\n');
/*     */         }
/* 263 */         if (mainAuthor != null && !mainAuthor.isEmpty()) {
/* 264 */           info.append("Main author: ").append(mainAuthor).append('\n');
/*     */         }
/* 266 */         if (authors != null && authors.length > 0) {
/* 267 */           info.append("Authors: ");
/*     */           
/* 269 */           for (int k = 0; k < authors.length; k++) {
/* 270 */             if (!authors[k].equals(mainAuthor))
/*     */             {
/*     */ 
/*     */               
/* 274 */               if (k <= authors.length - 2) {
/* 275 */                 info.append(", ");
/* 276 */               } else if (k == authors.length - 1) {
/* 277 */                 info.append(", and ");
/*     */               } else {
/* 279 */                 info.append('\n');
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/* 284 */         if (url != null && !url.isEmpty()) {
/* 285 */           info.append("URL: ").append(url).append('\n');
/*     */         }
/* 287 */         if (description != null && !description.isEmpty()) {
/* 288 */           info.append("Description: \n").append(description).append('\n');
/*     */         }
/*     */       } 
/*     */       
/* 292 */       info.append("Main class: ").append(this.mod.getClass().getName()).append('\n');
/* 293 */       info.append("Containing file: ");
/*     */       
/*     */       try {
/* 296 */         String path = (new File(this.mod.getClass().getProtectionDomain()
/* 297 */             .getCodeSource().getLocation().toURI())).getPath();
/*     */ 
/*     */         
/* 300 */         String username = System.getProperty("user.name");
/* 301 */         path = path.replace(username, "<USERNAME>");
/*     */         
/* 303 */         info.append(path);
/* 304 */       } catch (Exception e) {
/* 305 */         info.append("Unknown (").append(e.toString()).append(')');
/*     */       } 
/* 307 */       info.append('\n');
/* 308 */       Class[] interfaces = this.mod.getClass().getInterfaces();
/* 309 */       info.append("Implemented interfaces (").append(interfaces.length)
/* 310 */         .append(")\n");
/* 311 */       for (int i = 0; i < interfaces.length; i++) {
/* 312 */         info.append(i).append(": ").append(interfaces[i].getName())
/* 313 */           .append('\n');
/*     */       }
/* 315 */       info.append("Superclass: ")
/* 316 */         .append(this.mod.getClass().getSuperclass().getName()).append('\n');
/* 317 */       ClassLoader loader = this.mod.getClass().getClassLoader();
/* 318 */       info.append("Classloader: ").append(loader);
/* 319 */       if (loader != null) {
/* 320 */         info.append(" (").append(loader.getClass().getName()).append(')');
/*     */       }
/* 322 */       info.append('\n');
/* 323 */       Annotation[] annotations = this.mod.getClass().getAnnotations();
/* 324 */       info.append("Annotations (").append(annotations.length)
/* 325 */         .append(")\n");
/* 326 */       for (int j = 0; j < annotations.length; j++) {
/* 327 */         info.append(j).append(": ").append(annotations[j].toString())
/* 328 */           .append(" (")
/* 329 */           .append(annotations[j].annotationType().getName())
/* 330 */           .append(")\n");
/*     */       }
/*     */       
/* 333 */       return info.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEnabled() {
/* 341 */       return WDL.globalProps.getProperty("Extensions." + this.id + ".enabled", 
/* 342 */           "true").equals("true");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setEnabled(boolean enabled) {
/* 351 */       WDL.globalProps.setProperty("Extensions." + this.id + ".enabled", 
/* 352 */           Boolean.toString(enabled));
/* 353 */       WDL.saveGlobalProps();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void toggleEnabled() {
/* 361 */       setEnabled(!isEnabled());
/*     */     }
/*     */   }
/*     */   
/*     */   static {
/* 366 */     logger.info("Loading default WDL extensions");
/* 367 */     addWDLMod("Hologram", "1.0", (IWDLMod)new HologramHandler());
/* 368 */     addWDLMod("EntityRealigner", "1.0", (IWDLMod)new EntityRealigner());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\WDLApi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */