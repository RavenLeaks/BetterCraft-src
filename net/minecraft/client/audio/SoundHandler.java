/*     */ package net.minecraft.client.audio;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import java.io.Closeable;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SoundHandler
/*     */   implements IResourceManagerReloadListener, ITickable
/*     */ {
/*  35 */   public static final Sound MISSING_SOUND = new Sound("meta:missing_sound", 1.0F, 1.0F, 1, Sound.Type.FILE, false);
/*  36 */   private static final Logger LOGGER = LogManager.getLogger();
/*  37 */   private static final Gson GSON = (new GsonBuilder()).registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer()).registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
/*  38 */   private static final ParameterizedType TYPE = new ParameterizedType()
/*     */     {
/*     */       public Type[] getActualTypeArguments()
/*     */       {
/*  42 */         return new Type[] { String.class, SoundList.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  46 */         return Map.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  50 */         return null;
/*     */       }
/*     */     };
/*  53 */   private final SoundRegistry soundRegistry = new SoundRegistry();
/*     */   
/*     */   private final SoundManager sndManager;
/*     */   private final IResourceManager mcResourceManager;
/*     */   
/*     */   public SoundHandler(IResourceManager manager, GameSettings gameSettingsIn) {
/*  59 */     this.mcResourceManager = manager;
/*  60 */     this.sndManager = new SoundManager(this, gameSettingsIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  65 */     this.soundRegistry.clearMap();
/*     */     
/*  67 */     for (String s : resourceManager.getResourceDomains()) {
/*     */ 
/*     */       
/*     */       try {
/*  71 */         for (IResource iresource : resourceManager.getAllResources(new ResourceLocation(s, "sounds.json"))) {
/*     */           
/*     */           try
/*     */           {
/*  75 */             Map<String, SoundList> map = getSoundMap(iresource.getInputStream());
/*     */             
/*  77 */             for (Map.Entry<String, SoundList> entry : map.entrySet())
/*     */             {
/*  79 */               loadSoundResource(new ResourceLocation(s, entry.getKey()), entry.getValue());
/*     */             }
/*     */           }
/*  82 */           catch (RuntimeException runtimeexception)
/*     */           {
/*  84 */             LOGGER.warn("Invalid sounds.json", runtimeexception);
/*     */           }
/*     */         
/*     */         } 
/*  88 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     for (ResourceLocation resourcelocation : this.soundRegistry.getKeys()) {
/*     */       
/*  96 */       SoundEventAccessor soundeventaccessor = (SoundEventAccessor)this.soundRegistry.getObject(resourcelocation);
/*     */       
/*  98 */       if (soundeventaccessor.getSubtitle() instanceof TextComponentTranslation) {
/*     */         
/* 100 */         String s1 = ((TextComponentTranslation)soundeventaccessor.getSubtitle()).getKey();
/*     */         
/* 102 */         if (!I18n.hasKey(s1))
/*     */         {
/* 104 */           LOGGER.debug("Missing subtitle {} for event: {}", s1, resourcelocation);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     for (ResourceLocation resourcelocation1 : this.soundRegistry.getKeys()) {
/*     */       
/* 111 */       if (SoundEvent.REGISTRY.getObject(resourcelocation1) == null)
/*     */       {
/* 113 */         LOGGER.debug("Not having sound event for: {}", resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 117 */     this.sndManager.reloadSoundSystem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Map<String, SoundList> getSoundMap(InputStream stream) {
/*     */     Map<String, SoundList> map;
/*     */     try {
/* 127 */       map = (Map)JsonUtils.func_193841_a(GSON, new InputStreamReader(stream, StandardCharsets.UTF_8), TYPE);
/*     */     }
/*     */     finally {
/*     */       
/* 131 */       IOUtils.closeQuietly(stream);
/*     */     } 
/*     */     
/* 134 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadSoundResource(ResourceLocation location, SoundList sounds) {
/* 139 */     SoundEventAccessor soundeventaccessor = (SoundEventAccessor)this.soundRegistry.getObject(location);
/* 140 */     boolean flag = (soundeventaccessor == null);
/*     */     
/* 142 */     if (flag || sounds.canReplaceExisting()) {
/*     */       
/* 144 */       if (!flag)
/*     */       {
/* 146 */         LOGGER.debug("Replaced sound event location {}", location);
/*     */       }
/*     */       
/* 149 */       soundeventaccessor = new SoundEventAccessor(location, sounds.getSubtitle());
/* 150 */       this.soundRegistry.add(soundeventaccessor);
/*     */     } 
/*     */     
/* 153 */     for (Sound sound : sounds.getSounds()) {
/*     */       ISoundEventAccessor<Sound> isoundeventaccessor;
/* 155 */       final ResourceLocation resourcelocation = sound.getSoundLocation();
/*     */ 
/*     */       
/* 158 */       switch (sound.getType()) {
/*     */         
/*     */         case null:
/* 161 */           if (!validateSoundResource(sound, location)) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 166 */           isoundeventaccessor = sound;
/*     */           break;
/*     */         
/*     */         case SOUND_EVENT:
/* 170 */           isoundeventaccessor = new ISoundEventAccessor<Sound>()
/*     */             {
/*     */               public int getWeight()
/*     */               {
/* 174 */                 SoundEventAccessor soundeventaccessor1 = (SoundEventAccessor)SoundHandler.this.soundRegistry.getObject(resourcelocation);
/* 175 */                 return (soundeventaccessor1 == null) ? 0 : soundeventaccessor1.getWeight();
/*     */               }
/*     */               
/*     */               public Sound cloneEntry() {
/* 179 */                 SoundEventAccessor soundeventaccessor1 = (SoundEventAccessor)SoundHandler.this.soundRegistry.getObject(resourcelocation);
/*     */                 
/* 181 */                 if (soundeventaccessor1 == null)
/*     */                 {
/* 183 */                   return SoundHandler.MISSING_SOUND;
/*     */                 }
/*     */ 
/*     */                 
/* 187 */                 Sound sound1 = soundeventaccessor1.cloneEntry();
/* 188 */                 return new Sound(sound1.getSoundLocation().toString(), sound1.getVolume() * sound.getVolume(), sound1.getPitch() * sound.getPitch(), sound.getWeight(), Sound.Type.FILE, !(!sound1.isStreaming() && !sound.isStreaming()));
/*     */               }
/*     */             };
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 195 */           throw new IllegalStateException("Unknown SoundEventRegistration type: " + sound.getType());
/*     */       } 
/*     */       
/* 198 */       soundeventaccessor.addSound(isoundeventaccessor);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean validateSoundResource(Sound p_184401_1_, ResourceLocation p_184401_2_) {
/*     */     boolean flag;
/* 204 */     ResourceLocation resourcelocation = p_184401_1_.getSoundAsOggLocation();
/* 205 */     IResource iresource = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 210 */       iresource = this.mcResourceManager.getResource(resourcelocation);
/* 211 */       iresource.getInputStream();
/* 212 */       return true;
/*     */     }
/* 214 */     catch (FileNotFoundException var11) {
/*     */       
/* 216 */       LOGGER.warn("File {} does not exist, cannot add it to event {}", resourcelocation, p_184401_2_);
/* 217 */       flag = false;
/*     */     }
/* 219 */     catch (IOException ioexception) {
/*     */       
/* 221 */       LOGGER.warn("Could not load sound file {}, cannot add it to event {}", resourcelocation, p_184401_2_, ioexception);
/* 222 */       flag = false;
/* 223 */       return flag;
/*     */     }
/*     */     finally {
/*     */       
/* 227 */       IOUtils.closeQuietly((Closeable)iresource);
/*     */     } 
/*     */     
/* 230 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SoundEventAccessor getAccessor(ResourceLocation location) {
/* 236 */     return (SoundEventAccessor)this.soundRegistry.getObject(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSound(ISound sound) {
/* 244 */     this.sndManager.playSound(sound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDelayedSound(ISound sound, int delay) {
/* 252 */     this.sndManager.playDelayedSound(sound, delay);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setListener(EntityPlayer player, float p_147691_2_) {
/* 257 */     this.sndManager.setListener(player, p_147691_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pauseSounds() {
/* 262 */     this.sndManager.pauseAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopSounds() {
/* 267 */     this.sndManager.stopAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloadSounds() {
/* 272 */     this.sndManager.unloadSoundSystem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 280 */     this.sndManager.updateAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resumeSounds() {
/* 285 */     this.sndManager.resumeAllSounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSoundLevel(SoundCategory category, float volume) {
/* 290 */     if (category == SoundCategory.MASTER && volume <= 0.0F)
/*     */     {
/* 292 */       stopSounds();
/*     */     }
/*     */     
/* 295 */     this.sndManager.setVolume(category, volume);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopSound(ISound soundIn) {
/* 300 */     this.sndManager.stopSound(soundIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSoundPlaying(ISound sound) {
/* 305 */     return this.sndManager.isSoundPlaying(sound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(ISoundEventListener listener) {
/* 310 */     this.sndManager.addListener(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeListener(ISoundEventListener listener) {
/* 315 */     this.sndManager.removeListener(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop(String p_189520_1_, SoundCategory p_189520_2_) {
/* 320 */     this.sndManager.stop(p_189520_1_, p_189520_2_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\SoundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */