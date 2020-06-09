/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ 
/*    */ public class SoundType
/*    */ {
/*  8 */   public static final SoundType WOOD = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_WOOD_BREAK, SoundEvents.BLOCK_WOOD_STEP, SoundEvents.BLOCK_WOOD_PLACE, SoundEvents.BLOCK_WOOD_HIT, SoundEvents.BLOCK_WOOD_FALL);
/*  9 */   public static final SoundType GROUND = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_GRAVEL_BREAK, SoundEvents.BLOCK_GRAVEL_STEP, SoundEvents.BLOCK_GRAVEL_PLACE, SoundEvents.BLOCK_GRAVEL_HIT, SoundEvents.BLOCK_GRAVEL_FALL);
/* 10 */   public static final SoundType PLANT = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_GRASS_BREAK, SoundEvents.BLOCK_GRASS_STEP, SoundEvents.BLOCK_GRASS_PLACE, SoundEvents.BLOCK_GRASS_HIT, SoundEvents.BLOCK_GRASS_FALL);
/* 11 */   public static final SoundType STONE = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_STONE_BREAK, SoundEvents.BLOCK_STONE_STEP, SoundEvents.BLOCK_STONE_PLACE, SoundEvents.BLOCK_STONE_HIT, SoundEvents.BLOCK_STONE_FALL);
/* 12 */   public static final SoundType METAL = new SoundType(1.0F, 1.5F, SoundEvents.BLOCK_METAL_BREAK, SoundEvents.BLOCK_METAL_STEP, SoundEvents.BLOCK_METAL_PLACE, SoundEvents.BLOCK_METAL_HIT, SoundEvents.BLOCK_METAL_FALL);
/* 13 */   public static final SoundType GLASS = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_GLASS_BREAK, SoundEvents.BLOCK_GLASS_STEP, SoundEvents.BLOCK_GLASS_PLACE, SoundEvents.BLOCK_GLASS_HIT, SoundEvents.BLOCK_GLASS_FALL);
/* 14 */   public static final SoundType CLOTH = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_CLOTH_BREAK, SoundEvents.BLOCK_CLOTH_STEP, SoundEvents.BLOCK_CLOTH_PLACE, SoundEvents.BLOCK_CLOTH_HIT, SoundEvents.BLOCK_CLOTH_FALL);
/* 15 */   public static final SoundType SAND = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_SAND_BREAK, SoundEvents.BLOCK_SAND_STEP, SoundEvents.BLOCK_SAND_PLACE, SoundEvents.BLOCK_SAND_HIT, SoundEvents.BLOCK_SAND_FALL);
/* 16 */   public static final SoundType SNOW = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_SNOW_BREAK, SoundEvents.BLOCK_SNOW_STEP, SoundEvents.BLOCK_SNOW_PLACE, SoundEvents.BLOCK_SNOW_HIT, SoundEvents.BLOCK_SNOW_FALL);
/* 17 */   public static final SoundType LADDER = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_LADDER_BREAK, SoundEvents.BLOCK_LADDER_STEP, SoundEvents.BLOCK_LADDER_PLACE, SoundEvents.BLOCK_LADDER_HIT, SoundEvents.BLOCK_LADDER_FALL);
/* 18 */   public static final SoundType ANVIL = new SoundType(0.3F, 1.0F, SoundEvents.BLOCK_ANVIL_BREAK, SoundEvents.BLOCK_ANVIL_STEP, SoundEvents.BLOCK_ANVIL_PLACE, SoundEvents.BLOCK_ANVIL_HIT, SoundEvents.BLOCK_ANVIL_FALL);
/* 19 */   public static final SoundType SLIME = new SoundType(1.0F, 1.0F, SoundEvents.BLOCK_SLIME_BREAK, SoundEvents.BLOCK_SLIME_STEP, SoundEvents.BLOCK_SLIME_PLACE, SoundEvents.BLOCK_SLIME_HIT, SoundEvents.BLOCK_SLIME_FALL);
/*    */ 
/*    */   
/*    */   public final float volume;
/*    */ 
/*    */   
/*    */   public final float pitch;
/*    */ 
/*    */   
/*    */   private final SoundEvent breakSound;
/*    */   
/*    */   private final SoundEvent stepSound;
/*    */   
/*    */   private final SoundEvent placeSound;
/*    */   
/*    */   private final SoundEvent hitSound;
/*    */   
/*    */   private final SoundEvent fallSound;
/*    */ 
/*    */   
/*    */   public SoundType(float volumeIn, float pitchIn, SoundEvent breakSoundIn, SoundEvent stepSoundIn, SoundEvent placeSoundIn, SoundEvent hitSoundIn, SoundEvent fallSoundIn) {
/* 40 */     this.volume = volumeIn;
/* 41 */     this.pitch = pitchIn;
/* 42 */     this.breakSound = breakSoundIn;
/* 43 */     this.stepSound = stepSoundIn;
/* 44 */     this.placeSound = placeSoundIn;
/* 45 */     this.hitSound = hitSoundIn;
/* 46 */     this.fallSound = fallSoundIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getVolume() {
/* 51 */     return this.volume;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getPitch() {
/* 56 */     return this.pitch;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundEvent getBreakSound() {
/* 61 */     return this.breakSound;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundEvent getStepSound() {
/* 66 */     return this.stepSound;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundEvent getPlaceSound() {
/* 71 */     return this.placeSound;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundEvent getHitSound() {
/* 76 */     return this.hitSound;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundEvent getFallSound() {
/* 81 */     return this.fallSound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\SoundType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */