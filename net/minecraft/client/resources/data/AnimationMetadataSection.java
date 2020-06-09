/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class AnimationMetadataSection
/*    */   implements IMetadataSection
/*    */ {
/*    */   private final List<AnimationFrame> animationFrames;
/*    */   private final int frameWidth;
/*    */   private final int frameHeight;
/*    */   private final int frameTime;
/*    */   private final boolean interpolate;
/*    */   
/*    */   public AnimationMetadataSection(List<AnimationFrame> animationFramesIn, int frameWidthIn, int frameHeightIn, int frameTimeIn, boolean interpolateIn) {
/* 17 */     this.animationFrames = animationFramesIn;
/* 18 */     this.frameWidth = frameWidthIn;
/* 19 */     this.frameHeight = frameHeightIn;
/* 20 */     this.frameTime = frameTimeIn;
/* 21 */     this.interpolate = interpolateIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameHeight() {
/* 26 */     return this.frameHeight;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameWidth() {
/* 31 */     return this.frameWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameCount() {
/* 36 */     return this.animationFrames.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameTime() {
/* 41 */     return this.frameTime;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInterpolate() {
/* 46 */     return this.interpolate;
/*    */   }
/*    */ 
/*    */   
/*    */   private AnimationFrame getAnimationFrame(int frame) {
/* 51 */     return this.animationFrames.get(frame);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameTimeSingle(int frame) {
/* 56 */     AnimationFrame animationframe = getAnimationFrame(frame);
/* 57 */     return animationframe.hasNoTime() ? this.frameTime : animationframe.getFrameTime();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean frameHasTime(int frame) {
/* 62 */     return !((AnimationFrame)this.animationFrames.get(frame)).hasNoTime();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameIndex(int frame) {
/* 67 */     return ((AnimationFrame)this.animationFrames.get(frame)).getFrameIndex();
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Integer> getFrameIndexSet() {
/* 72 */     Set<Integer> set = Sets.newHashSet();
/*    */     
/* 74 */     for (AnimationFrame animationframe : this.animationFrames)
/*    */     {
/* 76 */       set.add(Integer.valueOf(animationframe.getFrameIndex()));
/*    */     }
/*    */     
/* 79 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\data\AnimationMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */