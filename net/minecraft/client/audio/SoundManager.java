/*     */ package net.minecraft.client.audio;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.collect.Sets;
/*     */ import io.netty.util.internal.ThreadLocalRandom;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLStreamHandler;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.MarkerManager;
/*     */ import paulscode.sound.SoundSystem;
/*     */ import paulscode.sound.SoundSystemConfig;
/*     */ import paulscode.sound.SoundSystemException;
/*     */ import paulscode.sound.SoundSystemLogger;
/*     */ import paulscode.sound.Source;
/*     */ import paulscode.sound.codecs.CodecJOrbis;
/*     */ import paulscode.sound.libraries.LibraryLWJGLOpenAL;
/*     */ 
/*     */ 
/*     */ public class SoundManager
/*     */ {
/*  44 */   private static final Marker LOG_MARKER = MarkerManager.getMarker("SOUNDS");
/*  45 */   private static final Logger LOGGER = LogManager.getLogger();
/*  46 */   private static final Set<ResourceLocation> UNABLE_TO_PLAY = Sets.newHashSet();
/*     */ 
/*     */   
/*     */   private final SoundHandler sndHandler;
/*     */ 
/*     */   
/*     */   private final GameSettings options;
/*     */ 
/*     */   
/*     */   private SoundSystemStarterThread sndSystem;
/*     */ 
/*     */   
/*     */   private boolean loaded;
/*     */   
/*     */   private int playTime;
/*     */   
/*  62 */   private final Map<String, ISound> playingSounds = (Map<String, ISound>)HashBiMap.create();
/*     */   
/*     */   private final Map<ISound, String> invPlayingSounds;
/*     */   private final Multimap<SoundCategory, String> categorySounds;
/*     */   private final List<ITickableSound> tickableSounds;
/*     */   private final Map<ISound, Integer> delayedSounds;
/*     */   private final Map<String, Integer> playingSoundsStopTime;
/*     */   private final List<ISoundEventListener> listeners;
/*     */   private final List<String> pausedChannels;
/*     */   
/*     */   public SoundManager(SoundHandler p_i45119_1_, GameSettings p_i45119_2_) {
/*  73 */     this.invPlayingSounds = (Map<ISound, String>)((BiMap)this.playingSounds).inverse();
/*  74 */     this.categorySounds = (Multimap<SoundCategory, String>)HashMultimap.create();
/*  75 */     this.tickableSounds = Lists.newArrayList();
/*  76 */     this.delayedSounds = Maps.newHashMap();
/*  77 */     this.playingSoundsStopTime = Maps.newHashMap();
/*  78 */     this.listeners = Lists.newArrayList();
/*  79 */     this.pausedChannels = Lists.newArrayList();
/*  80 */     this.sndHandler = p_i45119_1_;
/*  81 */     this.options = p_i45119_2_;
/*     */ 
/*     */     
/*     */     try {
/*  85 */       SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
/*  86 */       SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
/*     */     }
/*  88 */     catch (SoundSystemException soundsystemexception) {
/*     */       
/*  90 */       LOGGER.error(LOG_MARKER, "Error linking with the LibraryJavaSound plug-in", (Throwable)soundsystemexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadSoundSystem() {
/*  96 */     UNABLE_TO_PLAY.clear();
/*     */     
/*  98 */     for (SoundEvent soundevent : SoundEvent.REGISTRY) {
/*     */       
/* 100 */       ResourceLocation resourcelocation = soundevent.getSoundName();
/*     */       
/* 102 */       if (this.sndHandler.getAccessor(resourcelocation) == null) {
/*     */         
/* 104 */         LOGGER.warn("Missing sound for event: {}", SoundEvent.REGISTRY.getNameForObject(soundevent));
/* 105 */         UNABLE_TO_PLAY.add(resourcelocation);
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     unloadSoundSystem();
/* 110 */     loadSoundSystem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void loadSoundSystem() {
/* 118 */     if (!this.loaded) {
/*     */       
/*     */       try {
/*     */         
/* 122 */         (new Thread(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 126 */                 SoundSystemConfig.setLogger(new SoundSystemLogger()
/*     */                     {
/*     */                       public void message(String p_message_1_, int p_message_2_)
/*     */                       {
/* 130 */                         if (!p_message_1_.isEmpty())
/*     */                         {
/* 132 */                           SoundManager.LOGGER.info(p_message_1_);
/*     */                         }
/*     */                       }
/*     */                       
/*     */                       public void importantMessage(String p_importantMessage_1_, int p_importantMessage_2_) {
/* 137 */                         if (!p_importantMessage_1_.isEmpty())
/*     */                         {
/* 139 */                           SoundManager.LOGGER.warn(p_importantMessage_1_);
/*     */                         }
/*     */                       }
/*     */                       
/*     */                       public void errorMessage(String p_errorMessage_1_, String p_errorMessage_2_, int p_errorMessage_3_) {
/* 144 */                         if (!p_errorMessage_2_.isEmpty()) {
/*     */                           
/* 146 */                           SoundManager.LOGGER.error("Error in class '{}'", p_errorMessage_1_);
/* 147 */                           SoundManager.LOGGER.error(p_errorMessage_2_);
/*     */                         } 
/*     */                       }
/*     */                     });
/* 151 */                 SoundManager.this.getClass(); SoundManager.this.sndSystem = new SoundManager.SoundSystemStarterThread(null);
/* 152 */                 SoundManager.this.loaded = true;
/* 153 */                 SoundManager.this.sndSystem.setMasterVolume(SoundManager.this.options.getSoundLevel(SoundCategory.MASTER));
/* 154 */                 SoundManager.LOGGER.info(SoundManager.LOG_MARKER, "Sound engine started");
/*     */               }
/* 156 */             }"Sound Library Loader")).start();
/*     */       }
/* 158 */       catch (RuntimeException runtimeexception) {
/*     */         
/* 160 */         LOGGER.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", runtimeexception);
/* 161 */         this.options.setSoundLevel(SoundCategory.MASTER, 0.0F);
/* 162 */         this.options.saveOptions();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private float getVolume(SoundCategory category) {
/* 169 */     return (category != null && category != SoundCategory.MASTER) ? this.options.getSoundLevel(category) : 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVolume(SoundCategory category, float volume) {
/* 174 */     if (this.loaded)
/*     */     {
/* 176 */       if (category == SoundCategory.MASTER) {
/*     */         
/* 178 */         this.sndSystem.setMasterVolume(volume);
/*     */       }
/*     */       else {
/*     */         
/* 182 */         for (String s : this.categorySounds.get(category)) {
/*     */           
/* 184 */           ISound isound = this.playingSounds.get(s);
/* 185 */           float f = getClampedVolume(isound);
/*     */           
/* 187 */           if (f <= 0.0F) {
/*     */             
/* 189 */             stopSound(isound);
/*     */             
/*     */             continue;
/*     */           } 
/* 193 */           this.sndSystem.setVolume(s, f);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadSoundSystem() {
/* 205 */     if (this.loaded) {
/*     */       
/* 207 */       stopAllSounds();
/* 208 */       this.sndSystem.cleanup();
/* 209 */       this.loaded = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopAllSounds() {
/* 218 */     if (this.loaded) {
/*     */       
/* 220 */       for (String s : this.playingSounds.keySet())
/*     */       {
/* 222 */         this.sndSystem.stop(s);
/*     */       }
/*     */       
/* 225 */       this.playingSounds.clear();
/* 226 */       this.delayedSounds.clear();
/* 227 */       this.tickableSounds.clear();
/* 228 */       this.categorySounds.clear();
/* 229 */       this.playingSoundsStopTime.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(ISoundEventListener listener) {
/* 235 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeListener(ISoundEventListener listener) {
/* 240 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAllSounds() {
/* 245 */     this.playTime++;
/*     */     
/* 247 */     for (ITickableSound itickablesound : this.tickableSounds) {
/*     */       
/* 249 */       itickablesound.update();
/*     */       
/* 251 */       if (itickablesound.isDonePlaying()) {
/*     */         
/* 253 */         stopSound(itickablesound);
/*     */         
/*     */         continue;
/*     */       } 
/* 257 */       String s = this.invPlayingSounds.get(itickablesound);
/* 258 */       this.sndSystem.setVolume(s, getClampedVolume(itickablesound));
/* 259 */       this.sndSystem.setPitch(s, getClampedPitch(itickablesound));
/* 260 */       this.sndSystem.setPosition(s, itickablesound.getXPosF(), itickablesound.getYPosF(), itickablesound.getZPosF());
/*     */     } 
/*     */ 
/*     */     
/* 264 */     Iterator<Map.Entry<String, ISound>> iterator = this.playingSounds.entrySet().iterator();
/*     */     
/* 266 */     while (iterator.hasNext()) {
/*     */       
/* 268 */       Map.Entry<String, ISound> entry = iterator.next();
/* 269 */       String s1 = entry.getKey();
/* 270 */       ISound isound = entry.getValue();
/*     */       
/* 272 */       if (!this.sndSystem.playing(s1)) {
/*     */         
/* 274 */         int i = ((Integer)this.playingSoundsStopTime.get(s1)).intValue();
/*     */         
/* 276 */         if (i <= this.playTime) {
/*     */           
/* 278 */           int j = isound.getRepeatDelay();
/*     */           
/* 280 */           if (isound.canRepeat() && j > 0)
/*     */           {
/* 282 */             this.delayedSounds.put(isound, Integer.valueOf(this.playTime + j));
/*     */           }
/*     */           
/* 285 */           iterator.remove();
/* 286 */           LOGGER.debug(LOG_MARKER, "Removed channel {} because it's not playing anymore", s1);
/* 287 */           this.sndSystem.removeSource(s1);
/* 288 */           this.playingSoundsStopTime.remove(s1);
/*     */ 
/*     */           
/*     */           try {
/* 292 */             this.categorySounds.remove(isound.getCategory(), s1);
/*     */           }
/* 294 */           catch (RuntimeException runtimeException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 299 */           if (isound instanceof ITickableSound)
/*     */           {
/* 301 */             this.tickableSounds.remove(isound);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 307 */     Iterator<Map.Entry<ISound, Integer>> iterator1 = this.delayedSounds.entrySet().iterator();
/*     */     
/* 309 */     while (iterator1.hasNext()) {
/*     */       
/* 311 */       Map.Entry<ISound, Integer> entry1 = iterator1.next();
/*     */       
/* 313 */       if (this.playTime >= ((Integer)entry1.getValue()).intValue()) {
/*     */         
/* 315 */         ISound isound1 = entry1.getKey();
/*     */         
/* 317 */         if (isound1 instanceof ITickableSound)
/*     */         {
/* 319 */           ((ITickableSound)isound1).update();
/*     */         }
/*     */         
/* 322 */         playSound(isound1);
/* 323 */         iterator1.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSoundPlaying(ISound sound) {
/* 333 */     if (!this.loaded)
/*     */     {
/* 335 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 339 */     String s = this.invPlayingSounds.get(sound);
/*     */     
/* 341 */     if (s == null)
/*     */     {
/* 343 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 347 */     return !(!this.sndSystem.playing(s) && (!this.playingSoundsStopTime.containsKey(s) || ((Integer)this.playingSoundsStopTime.get(s)).intValue() > this.playTime));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopSound(ISound sound) {
/* 354 */     if (this.loaded) {
/*     */       
/* 356 */       String s = this.invPlayingSounds.get(sound);
/*     */       
/* 358 */       if (s != null)
/*     */       {
/* 360 */         this.sndSystem.stop(s);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(ISound p_sound) {
/* 367 */     if (this.loaded) {
/*     */       
/* 369 */       SoundEventAccessor soundeventaccessor = p_sound.createAccessor(this.sndHandler);
/* 370 */       ResourceLocation resourcelocation = p_sound.getSoundLocation();
/*     */       
/* 372 */       if (soundeventaccessor == null) {
/*     */         
/* 374 */         if (UNABLE_TO_PLAY.add(resourcelocation))
/*     */         {
/* 376 */           LOGGER.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}", resourcelocation);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 381 */         if (!this.listeners.isEmpty())
/*     */         {
/* 383 */           for (ISoundEventListener isoundeventlistener : this.listeners)
/*     */           {
/* 385 */             isoundeventlistener.soundPlay(p_sound, soundeventaccessor);
/*     */           }
/*     */         }
/*     */         
/* 389 */         if (this.sndSystem.getMasterVolume() <= 0.0F) {
/*     */           
/* 391 */           LOGGER.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", resourcelocation);
/*     */         }
/*     */         else {
/*     */           
/* 395 */           Sound sound = p_sound.getSound();
/*     */           
/* 397 */           if (sound == SoundHandler.MISSING_SOUND) {
/*     */             
/* 399 */             if (UNABLE_TO_PLAY.add(resourcelocation))
/*     */             {
/* 401 */               LOGGER.warn(LOG_MARKER, "Unable to play empty soundEvent: {}", resourcelocation);
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 406 */             float f3 = p_sound.getVolume();
/* 407 */             float f = 16.0F;
/*     */             
/* 409 */             if (f3 > 1.0F)
/*     */             {
/* 411 */               f *= f3;
/*     */             }
/*     */             
/* 414 */             SoundCategory soundcategory = p_sound.getCategory();
/* 415 */             float f1 = getClampedVolume(p_sound);
/* 416 */             float f2 = getClampedPitch(p_sound);
/*     */             
/* 418 */             if (f1 == 0.0F) {
/*     */               
/* 420 */               LOGGER.debug(LOG_MARKER, "Skipped playing sound {}, volume was zero.", sound.getSoundLocation());
/*     */             }
/*     */             else {
/*     */               
/* 424 */               boolean flag = (p_sound.canRepeat() && p_sound.getRepeatDelay() == 0);
/* 425 */               String s = MathHelper.getRandomUUID((Random)ThreadLocalRandom.current()).toString();
/* 426 */               ResourceLocation resourcelocation1 = sound.getSoundAsOggLocation();
/*     */               
/* 428 */               if (sound.isStreaming()) {
/*     */                 
/* 430 */                 this.sndSystem.newStreamingSource(false, s, getURLForSoundResource(resourcelocation1), resourcelocation1.toString(), flag, p_sound.getXPosF(), p_sound.getYPosF(), p_sound.getZPosF(), p_sound.getAttenuationType().getTypeInt(), f);
/*     */               }
/*     */               else {
/*     */                 
/* 434 */                 this.sndSystem.newSource(false, s, getURLForSoundResource(resourcelocation1), resourcelocation1.toString(), flag, p_sound.getXPosF(), p_sound.getYPosF(), p_sound.getZPosF(), p_sound.getAttenuationType().getTypeInt(), f);
/*     */               } 
/*     */               
/* 437 */               LOGGER.debug(LOG_MARKER, "Playing sound {} for event {} as channel {}", sound.getSoundLocation(), resourcelocation, s);
/* 438 */               this.sndSystem.setPitch(s, f2);
/* 439 */               this.sndSystem.setVolume(s, f1);
/* 440 */               this.sndSystem.play(s);
/* 441 */               this.playingSoundsStopTime.put(s, Integer.valueOf(this.playTime + 20));
/* 442 */               this.playingSounds.put(s, p_sound);
/* 443 */               this.categorySounds.put(soundcategory, s);
/*     */               
/* 445 */               if (p_sound instanceof ITickableSound)
/*     */               {
/* 447 */                 this.tickableSounds.add((ITickableSound)p_sound);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float getClampedPitch(ISound soundIn) {
/* 458 */     return MathHelper.clamp(soundIn.getPitch(), 0.5F, 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private float getClampedVolume(ISound soundIn) {
/* 463 */     return MathHelper.clamp(soundIn.getVolume() * getVolume(soundIn.getCategory()), 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pauseAllSounds() {
/* 471 */     for (Map.Entry<String, ISound> entry : this.playingSounds.entrySet()) {
/*     */       
/* 473 */       String s = entry.getKey();
/* 474 */       boolean flag = isSoundPlaying(entry.getValue());
/*     */       
/* 476 */       if (flag) {
/*     */         
/* 478 */         LOGGER.debug(LOG_MARKER, "Pausing channel {}", s);
/* 479 */         this.sndSystem.pause(s);
/* 480 */         this.pausedChannels.add(s);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeAllSounds() {
/* 490 */     for (String s : this.pausedChannels) {
/*     */       
/* 492 */       LOGGER.debug(LOG_MARKER, "Resuming channel {}", s);
/* 493 */       this.sndSystem.play(s);
/*     */     } 
/*     */     
/* 496 */     this.pausedChannels.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDelayedSound(ISound sound, int delay) {
/* 504 */     this.delayedSounds.put(sound, Integer.valueOf(this.playTime + delay));
/*     */   }
/*     */ 
/*     */   
/*     */   private static URL getURLForSoundResource(final ResourceLocation p_148612_0_) {
/* 509 */     String s = String.format("%s:%s:%s", new Object[] { "mcsounddomain", p_148612_0_.getResourceDomain(), p_148612_0_.getResourcePath() });
/* 510 */     URLStreamHandler urlstreamhandler = new URLStreamHandler()
/*     */       {
/*     */         protected URLConnection openConnection(URL p_openConnection_1_)
/*     */         {
/* 514 */           return new URLConnection(p_openConnection_1_)
/*     */             {
/*     */               public void connect() throws IOException {}
/*     */ 
/*     */ 
/*     */               
/*     */               public InputStream getInputStream() throws IOException {
/* 521 */                 return Minecraft.getMinecraft().getResourceManager().getResource(p_148612_0_).getInputStream();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     try {
/* 529 */       return new URL(null, s, urlstreamhandler);
/*     */     }
/* 531 */     catch (MalformedURLException var4) {
/*     */       
/* 533 */       throw new Error("TODO: Sanely handle url exception! :D");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setListener(EntityPlayer player, float p_148615_2_) {
/* 542 */     if (this.loaded && player != null) {
/*     */       
/* 544 */       float f = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * p_148615_2_;
/* 545 */       float f1 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * p_148615_2_;
/* 546 */       double d0 = player.prevPosX + (player.posX - player.prevPosX) * p_148615_2_;
/* 547 */       double d1 = player.prevPosY + (player.posY - player.prevPosY) * p_148615_2_ + player.getEyeHeight();
/* 548 */       double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * p_148615_2_;
/* 549 */       float f2 = MathHelper.cos((f1 + 90.0F) * 0.017453292F);
/* 550 */       float f3 = MathHelper.sin((f1 + 90.0F) * 0.017453292F);
/* 551 */       float f4 = MathHelper.cos(-f * 0.017453292F);
/* 552 */       float f5 = MathHelper.sin(-f * 0.017453292F);
/* 553 */       float f6 = MathHelper.cos((-f + 90.0F) * 0.017453292F);
/* 554 */       float f7 = MathHelper.sin((-f + 90.0F) * 0.017453292F);
/* 555 */       float f8 = f2 * f4;
/* 556 */       float f9 = f3 * f4;
/* 557 */       float f10 = f2 * f6;
/* 558 */       float f11 = f3 * f6;
/* 559 */       this.sndSystem.setListenerPosition((float)d0, (float)d1, (float)d2);
/* 560 */       this.sndSystem.setListenerOrientation(f8, f5, f9, f10, f7, f11);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop(String p_189567_1_, SoundCategory p_189567_2_) {
/* 566 */     if (p_189567_2_ != null) {
/*     */       
/* 568 */       for (String s : this.categorySounds.get(p_189567_2_))
/*     */       {
/* 570 */         ISound isound = this.playingSounds.get(s);
/*     */         
/* 572 */         if (p_189567_1_.isEmpty()) {
/*     */           
/* 574 */           stopSound(isound); continue;
/*     */         } 
/* 576 */         if (isound.getSoundLocation().equals(new ResourceLocation(p_189567_1_)))
/*     */         {
/* 578 */           stopSound(isound);
/*     */         }
/*     */       }
/*     */     
/* 582 */     } else if (p_189567_1_.isEmpty()) {
/*     */       
/* 584 */       stopAllSounds();
/*     */     }
/*     */     else {
/*     */       
/* 588 */       for (ISound isound1 : this.playingSounds.values()) {
/*     */         
/* 590 */         if (isound1.getSoundLocation().equals(new ResourceLocation(p_189567_1_)))
/*     */         {
/* 592 */           stopSound(isound1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   class SoundSystemStarterThread
/*     */     extends SoundSystem
/*     */   {
/*     */     private SoundSystemStarterThread() {}
/*     */ 
/*     */     
/*     */     public boolean playing(String p_playing_1_) {
/* 606 */       synchronized (SoundSystemConfig.THREAD_SYNC) {
/*     */         
/* 608 */         if (this.soundLibrary == null)
/*     */         {
/* 610 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 614 */         Source source = (Source)this.soundLibrary.getSources().get(p_playing_1_);
/*     */         
/* 616 */         if (source == null)
/*     */         {
/* 618 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 622 */         return !(!source.playing() && !source.paused() && !source.preLoad);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\SoundManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */