/*     */ package wdl.update;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.util.text.event.ClickEvent;
/*     */ import wdl.WDL;
/*     */ import wdl.WDLMessageTypes;
/*     */ import wdl.WDLMessages;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WDLUpdateChecker
/*     */   extends Thread
/*     */ {
/*     */   private static volatile boolean started = false;
/*     */   private static volatile boolean finished = false;
/*     */   private static volatile boolean failed = false;
/*  36 */   private static volatile String failReason = null;
/*     */ 
/*     */   
/*     */   private static volatile List<Release> releases;
/*     */   
/*     */   private static volatile Release runningRelease;
/*     */   
/*     */   private static final String FORUMS_THREAD_USAGE_LINK = "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465#Usage";
/*     */   
/*     */   private static final String WIKI_LINK = "https://github.com/pokechu22/WorldDownloader/wiki";
/*     */   
/*     */   private static final String GITHUB_LINK = "https://github.com/pokechu22/WorldDownloader";
/*     */   
/*     */   private static final String REDISTRIBUTION_LINK = "http://pokechu22.github.io/WorldDownloader/redistribution";
/*     */   
/*     */   private static final String SMR_LINK = "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/mods-discussion/2314237";
/*     */ 
/*     */   
/*     */   public static List<Release> getReleases() {
/*  55 */     return releases;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Release getRunningRelease() {
/*  63 */     return runningRelease;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Release getRecomendedRelease() {
/*  72 */     if (releases == null) {
/*  73 */       return null;
/*     */     }
/*  75 */     if (runningRelease == null) {
/*  76 */       return null;
/*     */     }
/*     */     
/*  79 */     String mcVersion = WDL.getMinecraftVersion();
/*     */     
/*  81 */     boolean usePrereleases = WDL.globalProps.getProperty(
/*  82 */         "UpdateAllowBetas").equals("true");
/*  83 */     boolean versionMustBeExact = WDL.globalProps.getProperty(
/*  84 */         "UpdateMinecraftVersion").equals("client");
/*  85 */     boolean versionMustBeCompatible = WDL.globalProps.getProperty(
/*  86 */         "UpdateMinecraftVersion").equals("server");
/*     */     
/*  88 */     for (Release release : releases) {
/*  89 */       if (release.hiddenInfo == null || (
/*  90 */         release.prerelease && !usePrereleases)) {
/*     */         continue;
/*     */       }
/*     */       
/*  94 */       if (versionMustBeExact) {
/*     */         
/*  96 */         if (!release.hiddenInfo.mainMinecraftVersion.equals(mcVersion)) {
/*     */           continue;
/*     */         }
/*  99 */       } else if (versionMustBeCompatible) {
/* 100 */         boolean foundCompatible = false;
/*     */         String[] arrayOfString;
/* 102 */         int i = (arrayOfString = release.hiddenInfo.supportedMinecraftVersions).length; byte b = 0; for (; b < i; b++) { String version = arrayOfString[b];
/* 103 */           if (version.equals(mcVersion)) {
/* 104 */             foundCompatible = true;
/*     */             
/*     */             break;
/*     */           }  }
/*     */         
/* 109 */         if (!foundCompatible) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 114 */       if (releases.indexOf(release) > releases.indexOf(runningRelease)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 119 */       return release;
/*     */     } 
/*     */ 
/*     */     
/* 123 */     return null;
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
/*     */   public static boolean hasNewVersion() {
/* 136 */     if (runningRelease == null) {
/* 137 */       return false;
/*     */     }
/* 139 */     Release recomendedRelease = getRecomendedRelease();
/* 140 */     if (recomendedRelease == null) {
/* 141 */       return false;
/*     */     }
/* 143 */     return (runningRelease != recomendedRelease);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void startIfNeeded() {
/* 151 */     if (!started) {
/* 152 */       started = true;
/*     */       
/* 154 */       (new WDLUpdateChecker()).start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasFinishedUpdateCheck() {
/* 162 */     return finished;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasUpdateCheckFailed() {
/* 169 */     return failed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getUpdateCheckFailReason() {
/* 175 */     return failReason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WDLUpdateChecker() {
/* 185 */     super("WorldDownloader update check thread");
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 191 */       if (!WDL.globalProps.getProperty("TutorialShown").equals("true")) {
/* 192 */         sleep(5000L);
/*     */         
/* 194 */         TextComponentTranslation success = new TextComponentTranslation(
/* 195 */             "wdl.intro.success", new Object[0]);
/* 196 */         TextComponentTranslation mcfThread = new TextComponentTranslation(
/* 197 */             "wdl.intro.forumsLink", new Object[0]);
/* 198 */         mcfThread.getStyle().setColor(TextFormatting.BLUE)
/* 199 */           .setUnderlined(Boolean.valueOf(true)).setClickEvent(
/* 200 */             new ClickEvent(ClickEvent.Action.OPEN_URL, 
/* 201 */               "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465#Usage"));
/* 202 */         TextComponentTranslation wikiLink = new TextComponentTranslation(
/* 203 */             "wdl.intro.wikiLink", new Object[0]);
/* 204 */         wikiLink.getStyle().setColor(TextFormatting.BLUE)
/* 205 */           .setUnderlined(Boolean.valueOf(true)).setClickEvent(
/* 206 */             new ClickEvent(ClickEvent.Action.OPEN_URL, 
/* 207 */               "https://github.com/pokechu22/WorldDownloader/wiki"));
/* 208 */         TextComponentTranslation usage = new TextComponentTranslation(
/* 209 */             "wdl.intro.usage", new Object[] { mcfThread, wikiLink });
/* 210 */         TextComponentTranslation githubRepo = new TextComponentTranslation(
/* 211 */             "wdl.intro.githubRepo", new Object[0]);
/* 212 */         githubRepo.getStyle().setColor(TextFormatting.BLUE)
/* 213 */           .setUnderlined(Boolean.valueOf(true)).setClickEvent(
/* 214 */             new ClickEvent(ClickEvent.Action.OPEN_URL, 
/* 215 */               "https://github.com/pokechu22/WorldDownloader"));
/* 216 */         TextComponentTranslation contribute = new TextComponentTranslation(
/* 217 */             "wdl.intro.contribute", new Object[] { githubRepo });
/* 218 */         TextComponentTranslation redistributionList = new TextComponentTranslation(
/* 219 */             "wdl.intro.redistributionList", new Object[0]);
/* 220 */         redistributionList.getStyle().setColor(TextFormatting.BLUE)
/* 221 */           .setUnderlined(Boolean.valueOf(true)).setClickEvent(
/* 222 */             new ClickEvent(ClickEvent.Action.OPEN_URL, 
/* 223 */               "http://pokechu22.github.io/WorldDownloader/redistribution"));
/* 224 */         TextComponentTranslation warning = new TextComponentTranslation(
/* 225 */             "wdl.intro.warning", new Object[0]);
/* 226 */         warning.getStyle().setColor(TextFormatting.DARK_RED)
/* 227 */           .setBold(Boolean.valueOf(true));
/* 228 */         TextComponentTranslation illegally = new TextComponentTranslation(
/* 229 */             "wdl.intro.illegally", new Object[0]);
/* 230 */         illegally.getStyle().setColor(TextFormatting.DARK_RED)
/* 231 */           .setBold(Boolean.valueOf(true));
/* 232 */         TextComponentTranslation stolen = new TextComponentTranslation(
/* 233 */             "wdl.intro.stolen", new Object[] { warning, redistributionList, illegally });
/* 234 */         TextComponentTranslation smr = new TextComponentTranslation(
/* 235 */             "wdl.intro.stopModReposts", new Object[0]);
/* 236 */         smr.getStyle().setColor(TextFormatting.BLUE)
/* 237 */           .setUnderlined(Boolean.valueOf(true)).setClickEvent(
/* 238 */             new ClickEvent(ClickEvent.Action.OPEN_URL, 
/* 239 */               "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/mods-discussion/2314237"));
/* 240 */         TextComponentTranslation stolenBeware = new TextComponentTranslation(
/* 241 */             "wdl.intro.stolenBeware", new Object[] { smr });
/*     */         
/* 243 */         WDLMessages.chatMessage((IWDLMessageType)WDLMessageTypes.UPDATES, (ITextComponent)success);
/* 244 */         WDLMessages.chatMessage((IWDLMessageType)WDLMessageTypes.UPDATES, (ITextComponent)usage);
/* 245 */         WDLMessages.chatMessage((IWDLMessageType)WDLMessageTypes.UPDATES, (ITextComponent)contribute);
/* 246 */         WDLMessages.chatMessage((IWDLMessageType)WDLMessageTypes.UPDATES, (ITextComponent)stolen);
/* 247 */         WDLMessages.chatMessage((IWDLMessageType)WDLMessageTypes.UPDATES, (ITextComponent)stolenBeware);
/*     */         
/* 249 */         WDL.globalProps.setProperty("TutorialShown", "true");
/* 250 */         WDL.saveGlobalProps();
/*     */       } 
/*     */       
/* 253 */       sleep(5000L);
/*     */       
/* 255 */       releases = GithubInfoGrabber.getReleases();
/* 256 */       WDLMessages.chatMessageTranslated((IWDLMessageType)WDLMessageTypes.UPDATE_DEBUG, 
/* 257 */           "wdl.messages.updates.releaseCount", new Object[] { Integer.valueOf(releases.size()) });
/*     */       
/* 259 */       if (releases.isEmpty()) {
/* 260 */         WDLUpdateChecker.failed = true;
/* 261 */         failReason = "No releases found.";
/*     */         
/*     */         return;
/*     */       } 
/* 265 */       for (int i = 0; i < releases.size(); i++) {
/* 266 */         Release release = releases.get(i);
/*     */         
/* 268 */         if (release.tag.equalsIgnoreCase("1.11a-beta1")) {
/* 269 */           runningRelease = release;
/*     */         }
/*     */       } 
/*     */       
/* 273 */       if (runningRelease == null) {
/* 274 */         WDLMessages.chatMessageTranslated((IWDLMessageType)WDLMessageTypes.UPDATE_DEBUG, 
/* 275 */             "wdl.messages.updates.failedToFindMatchingRelease", new Object[] {
/* 276 */               "1.11a-beta1"
/*     */             });
/*     */         return;
/*     */       } 
/* 280 */       if (hasNewVersion()) {
/* 281 */         Release recomendedRelease = getRecomendedRelease();
/*     */         
/* 283 */         TextComponentTranslation updateLink = new TextComponentTranslation(
/* 284 */             "wdl.messages.updates.newRelease.updateLink", new Object[0]);
/* 285 */         updateLink.getStyle().setColor(TextFormatting.BLUE)
/* 286 */           .setUnderlined(Boolean.valueOf(true)).setClickEvent(
/* 287 */             new ClickEvent(ClickEvent.Action.OPEN_URL, 
/* 288 */               recomendedRelease.URL));
/*     */ 
/*     */         
/* 291 */         WDLMessages.chatMessageTranslated((IWDLMessageType)WDLMessageTypes.UPDATES, 
/* 292 */             "wdl.messages.updates.newRelease", new Object[] { runningRelease.tag, 
/* 293 */               recomendedRelease.tag, updateLink });
/*     */       } 
/*     */       
/* 296 */       if (runningRelease.hiddenInfo == null) {
/* 297 */         WDLMessages.chatMessageTranslated((IWDLMessageType)WDLMessageTypes.UPDATE_DEBUG, 
/* 298 */             "wdl.messages.updates.failedToFindMetadata", new Object[] {
/* 299 */               "1.11a-beta1"
/*     */             });
/*     */         return;
/*     */       } 
/* 303 */       Map<Release.HashData, Object> failed = new HashMap<>(); byte b; int j;
/*     */       Release.HashData[] arrayOfHashData;
/* 305 */       for (j = (arrayOfHashData = runningRelease.hiddenInfo.hashes).length, b = 0; b < j; ) { Release.HashData data = arrayOfHashData[b];
/*     */         try {
/* 307 */           String hash = ClassHasher.hash(data.relativeTo, data.file); byte b1; int k;
/*     */           String[] arrayOfString;
/* 309 */           for (k = (arrayOfString = data.validHashes).length, b1 = 0; b1 < k; ) { String validHash = arrayOfString[b1];
/* 310 */             if (validHash.equalsIgnoreCase(hash)) {
/*     */               // Byte code: goto -> 963
/*     */             }
/*     */             
/*     */             b1++; }
/*     */ 
/*     */           
/* 317 */           WDLMessages.chatMessageTranslated(
/* 318 */               (IWDLMessageType)WDLMessageTypes.UPDATE_DEBUG, 
/* 319 */               "wdl.messages.updates.incorrectHash", new Object[] { data.file, 
/* 320 */                 data.relativeTo, Arrays.toString((Object[])data.validHashes), 
/* 321 */                 hash });
/*     */           
/* 323 */           failed.put(data, hash);
/*     */         }
/* 325 */         catch (Exception e) {
/* 326 */           WDLMessages.chatMessageTranslated(
/* 327 */               (IWDLMessageType)WDLMessageTypes.UPDATE_DEBUG, 
/* 328 */               "wdl.messages.updates.hashException", new Object[] { data.file, 
/* 329 */                 data.relativeTo, Arrays.toString((Object[])data.validHashes), 
/* 330 */                 e });
/*     */           
/* 332 */           failed.put(data, e);
/*     */         } 
/*     */         b++; }
/*     */       
/* 336 */       if (failed.size() > 0) {
/* 337 */         TextComponentTranslation mcfThread = new TextComponentTranslation(
/* 338 */             "wdl.intro.forumsLink", new Object[0]);
/* 339 */         mcfThread.getStyle().setColor(TextFormatting.BLUE)
/* 340 */           .setUnderlined(Boolean.valueOf(true)).setClickEvent(
/* 341 */             new ClickEvent(ClickEvent.Action.OPEN_URL, 
/* 342 */               "http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465#Usage"));
/* 343 */         WDLMessages.chatMessageTranslated((IWDLMessageType)WDLMessageTypes.UPDATES, 
/* 344 */             "wdl.messages.updates.badHashesFound", new Object[] { mcfThread });
/*     */       } 
/* 346 */     } catch (Exception e) {
/* 347 */       WDLMessages.chatMessageTranslated((IWDLMessageType)WDLMessageTypes.UPDATE_DEBUG, 
/* 348 */           "wdl.messages.updates.updateCheckError", new Object[] { e });
/*     */       
/* 350 */       WDLUpdateChecker.failed = true;
/* 351 */       failReason = e.toString();
/*     */     } finally {
/* 353 */       finished = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wd\\update\WDLUpdateChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */