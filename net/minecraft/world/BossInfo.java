/*     */ package net.minecraft.world;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ 
/*     */ public abstract class BossInfo
/*     */ {
/*     */   private final UUID uniqueId;
/*     */   protected ITextComponent name;
/*     */   protected float percent;
/*     */   protected Color color;
/*     */   protected Overlay overlay;
/*     */   protected boolean darkenSky;
/*     */   protected boolean playEndBossMusic;
/*     */   protected boolean createFog;
/*     */   
/*     */   public BossInfo(UUID uniqueIdIn, ITextComponent nameIn, Color colorIn, Overlay overlayIn) {
/*  19 */     this.uniqueId = uniqueIdIn;
/*  20 */     this.name = nameIn;
/*  21 */     this.color = colorIn;
/*  22 */     this.overlay = overlayIn;
/*  23 */     this.percent = 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/*  28 */     return this.uniqueId;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent getName() {
/*  33 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(ITextComponent nameIn) {
/*  38 */     this.name = nameIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPercent() {
/*  43 */     return this.percent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPercent(float percentIn) {
/*  48 */     this.percent = percentIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getColor() {
/*  53 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(Color colorIn) {
/*  58 */     this.color = colorIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Overlay getOverlay() {
/*  63 */     return this.overlay;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOverlay(Overlay overlayIn) {
/*  68 */     this.overlay = overlayIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDarkenSky() {
/*  73 */     return this.darkenSky;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossInfo setDarkenSky(boolean darkenSkyIn) {
/*  78 */     this.darkenSky = darkenSkyIn;
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldPlayEndBossMusic() {
/*  84 */     return this.playEndBossMusic;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossInfo setPlayEndBossMusic(boolean playEndBossMusicIn) {
/*  89 */     this.playEndBossMusic = playEndBossMusicIn;
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BossInfo setCreateFog(boolean createFogIn) {
/*  95 */     this.createFog = createFogIn;
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCreateFog() {
/* 101 */     return this.createFog;
/*     */   }
/*     */   
/*     */   public enum Color
/*     */   {
/* 106 */     PINK,
/* 107 */     BLUE,
/* 108 */     RED,
/* 109 */     GREEN,
/* 110 */     YELLOW,
/* 111 */     PURPLE,
/* 112 */     WHITE;
/*     */   }
/*     */   
/*     */   public enum Overlay
/*     */   {
/* 117 */     PROGRESS,
/* 118 */     NOTCHED_6,
/* 119 */     NOTCHED_10,
/* 120 */     NOTCHED_12,
/* 121 */     NOTCHED_20;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\BossInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */