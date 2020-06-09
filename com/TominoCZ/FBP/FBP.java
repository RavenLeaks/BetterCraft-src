/*     */ package com.TominoCZ.FBP;
/*     */ 
/*     */ import com.TominoCZ.FBP.block.FBPAnimationDummyBlock;
/*     */ import com.TominoCZ.FBP.handler.FBPEventHandler;
/*     */ import com.TominoCZ.FBP.particle.FBPParticleManager;
/*     */ import com.TominoCZ.FBP.util.FBPReflectionHelper;
/*     */ import com.google.common.base.Throwables;
/*     */ import java.io.File;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.SplittableRandom;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.ParticleDigging;
/*     */ import net.minecraft.client.particle.ParticleManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FBP
/*     */ {
/*     */   public static FBP INSTANCE;
/*     */   public static final String MODID = "fbp";
/*  38 */   public static final ResourceLocation LOCATION_PARTICLE_TEXTURE = new ResourceLocation(
/*  39 */       "textures/particle/particles.png");
/*     */   
/*  41 */   public static File animBlacklistFile = null;
/*  42 */   public static File particleBlacklistFile = null;
/*  43 */   public static File floatingMaterialsFile = null;
/*  44 */   public static File config = null;
/*     */   
/*     */   public static int minAge;
/*     */   public static int maxAge;
/*     */   public static int particlesPerAxis;
/*     */   public static double scaleMult;
/*     */   public static double gravityMult;
/*     */   public static double rotationMult;
/*     */   public static double weatherParticleDensity;
/*     */   public static boolean enabled = false;
/*     */   public static boolean showInMillis = false;
/*     */   public static boolean infiniteDuration = false;
/*     */   public static boolean randomRotation;
/*     */   public static boolean cartoonMode;
/*     */   public static boolean spawnWhileFrozen;
/*     */   public static boolean spawnRedstoneBlockParticles;
/*     */   public static boolean randomizedScale;
/*     */   public static boolean randomFadingSpeed;
/*  62 */   public static SplittableRandom random = new SplittableRandom(); public static boolean entityCollision; public static boolean bounceOffWalls; public static boolean lowTraction; public static boolean smartBreaking; public static boolean fancyPlaceAnim; public static boolean animSmoothLighting; public static boolean spawnPlaceParticles; public static boolean fancyRain; public static boolean fancySnow; public static boolean fancyFlame; public static boolean fancySmoke; public static boolean waterPhysics; public static boolean restOnFloor; public static boolean frozen; public List<String> blockParticleBlacklist; public List<String> blockAnimBlacklist;
/*     */   public List<Material> floatingMaterials;
/*  64 */   public static final Vec3d[] CUBE = new Vec3d[] {
/*     */       
/*  66 */       new Vec3d(1.0D, 1.0D, -1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, -1.0D), 
/*     */ 
/*     */       
/*  69 */       new Vec3d(-1.0D, -1.0D, -1.0D), new Vec3d(-1.0D, -1.0D, 1.0D), new Vec3d(1.0D, -1.0D, 1.0D), new Vec3d(1.0D, -1.0D, -1.0D), 
/*     */ 
/*     */       
/*  72 */       new Vec3d(-1.0D, -1.0D, 1.0D), new Vec3d(-1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(1.0D, -1.0D, 1.0D), 
/*     */ 
/*     */       
/*  75 */       new Vec3d(1.0D, -1.0D, -1.0D), new Vec3d(1.0D, 1.0D, -1.0D), new Vec3d(-1.0D, 1.0D, -1.0D), new Vec3d(-1.0D, -1.0D, -1.0D), 
/*     */ 
/*     */       
/*  78 */       new Vec3d(-1.0D, -1.0D, -1.0D), new Vec3d(-1.0D, 1.0D, -1.0D), new Vec3d(-1.0D, 1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 1.0D),
/*     */ 
/*     */       
/*  81 */       new Vec3d(1.0D, -1.0D, 1.0D), new Vec3d(1.0D, 1.0D, 1.0D), new Vec3d(1.0D, 1.0D, -1.0D), new Vec3d(1.0D, -1.0D, -1.0D)
/*     */     };
/*  83 */   public static final Vec3d[] CUBE_NORMALS = new Vec3d[] { new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(0.0D, -1.0D, 0.0D), 
/*     */       
/*  85 */       new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(0.0D, 0.0D, -1.0D), 
/*     */       
/*  87 */       new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D) };
/*     */   
/*     */   public static VertexFormat POSITION_TEX_COLOR_LMAP_NORMAL;
/*     */   
/*     */   public static MethodHandle setSourcePos;
/*     */   
/*  93 */   public static FBPAnimationDummyBlock FBPBlock = new FBPAnimationDummyBlock();
/*     */   
/*     */   public static FBPParticleManager fancyEffectRenderer;
/*     */   
/*     */   public static ParticleManager originalEffectRenderer;
/*     */   
/*  99 */   public FBPEventHandler eventHandler = new FBPEventHandler();
/*     */ 
/*     */ 
/*     */   
/*     */   public FBP() {
/* 104 */     INSTANCE = this;
/*     */     
/* 106 */     POSITION_TEX_COLOR_LMAP_NORMAL = new VertexFormat();
/*     */     
/* 108 */     POSITION_TEX_COLOR_LMAP_NORMAL.addElement(DefaultVertexFormats.POSITION_3F);
/* 109 */     POSITION_TEX_COLOR_LMAP_NORMAL.addElement(DefaultVertexFormats.TEX_2F);
/* 110 */     POSITION_TEX_COLOR_LMAP_NORMAL.addElement(DefaultVertexFormats.COLOR_4UB);
/* 111 */     POSITION_TEX_COLOR_LMAP_NORMAL.addElement(DefaultVertexFormats.TEX_2S);
/* 112 */     POSITION_TEX_COLOR_LMAP_NORMAL.addElement(DefaultVertexFormats.NORMAL_3B);
/*     */     
/* 114 */     this.blockParticleBlacklist = Collections.synchronizedList(new ArrayList<>());
/* 115 */     this.blockAnimBlacklist = Collections.synchronizedList(new ArrayList<>());
/* 116 */     this.floatingMaterials = Collections.synchronizedList(new ArrayList<>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/* 126 */     syncWithModule();
/*     */     
/* 128 */     MethodHandles.Lookup lookup = MethodHandles.publicLookup();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 134 */       setSourcePos = lookup
/* 135 */         .unreflectSetter(FBPReflectionHelper.findField(ParticleDigging.class, new String[] { "field_181019_az", "sourcePos" }));
/* 136 */     } catch (Exception e) {
/*     */       
/* 138 */       throw Throwables.propagate(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEnabled() {
/* 146 */     boolean result = enabled;
/*     */     
/* 148 */     if (!result) {
/* 149 */       frozen = false;
/*     */     }
/* 151 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setEnabled(boolean enabled) {
/* 156 */     if (FBP.enabled != enabled)
/*     */     {
/* 158 */       if (enabled) {
/*     */         
/* 160 */         fancyEffectRenderer.carryOver();
/*     */         
/* 162 */         (Minecraft.getMinecraft()).effectRenderer = (ParticleManager)fancyEffectRenderer;
/*     */       }
/*     */       else {
/*     */         
/* 166 */         (Minecraft.getMinecraft()).effectRenderer = originalEffectRenderer;
/*     */       } 
/*     */     }
/*     */     
/* 170 */     FBP.enabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlacklisted(Block b, boolean particle) {
/* 175 */     if (b == null) {
/* 176 */       return true;
/*     */     }
/* 178 */     return (particle ? this.blockParticleBlacklist : this.blockAnimBlacklist).contains(b.getUnlocalizedName());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesMaterialFloat(Material mat) {
/* 183 */     return this.floatingMaterials.contains(mat);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addToBlacklist(Block b, boolean particle) {
/* 188 */     if (b == null) {
/*     */       return;
/*     */     }
/* 191 */     String name = b.getUnlocalizedName().toString();
/*     */     
/* 193 */     if (!(particle ? this.blockParticleBlacklist : this.blockAnimBlacklist).contains(name)) {
/* 194 */       (particle ? this.blockParticleBlacklist : this.blockAnimBlacklist).add(name);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeFromBlacklist(Block b, boolean particle) {
/* 199 */     if (b == null) {
/*     */       return;
/*     */     }
/* 202 */     String name = b.getUnlocalizedName().toString();
/*     */     
/* 204 */     if ((particle ? this.blockParticleBlacklist : this.blockAnimBlacklist).contains(name)) {
/* 205 */       (particle ? this.blockParticleBlacklist : this.blockAnimBlacklist).remove(name);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addToBlacklist(String name, boolean particle) {
/* 210 */     if (StringUtils.isEmpty(name)) {
/*     */       return;
/*     */     }
/* 213 */     Iterator<ResourceLocation> it = Block.REGISTRY.getKeys().iterator();
/*     */     
/* 215 */     while (it.hasNext()) {
/*     */       
/* 217 */       ResourceLocation rl = it.next();
/* 218 */       String s = rl.toString();
/*     */       
/* 220 */       if (s.equals(name)) {
/*     */         
/* 222 */         Block b = (Block)Block.REGISTRY.getObject(rl);
/*     */         
/* 224 */         if (b == Blocks.REDSTONE_BLOCK) {
/*     */           break;
/*     */         }
/* 227 */         addToBlacklist(b, particle);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetBlacklist(boolean particle) {
/* 235 */     if (particle) {
/* 236 */       this.blockParticleBlacklist.clear();
/*     */     } else {
/* 238 */       this.blockAnimBlacklist.clear();
/*     */     } 
/*     */   }
/*     */   public void syncWithModule() {
/* 242 */     enabled = true;
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
/* 255 */     minAge = 10;
/* 256 */     maxAge = 55;
/* 257 */     infiniteDuration = false;
/* 258 */     particlesPerAxis = 4;
/* 259 */     scaleMult = 0.7D;
/* 260 */     gravityMult = 1.0D;
/* 261 */     rotationMult = 1.0D;
/*     */     
/* 263 */     randomRotation = true;
/* 264 */     cartoonMode = false;
/* 265 */     randomizedScale = true;
/* 266 */     randomFadingSpeed = true;
/* 267 */     spawnRedstoneBlockParticles = false;
/*     */     
/* 269 */     entityCollision = false;
/* 270 */     bounceOffWalls = true;
/* 271 */     lowTraction = false;
/* 272 */     smartBreaking = true;
/*     */     
/* 274 */     fancyFlame = true;
/* 275 */     fancySmoke = true;
/* 276 */     waterPhysics = true;
/* 277 */     restOnFloor = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\FBP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */