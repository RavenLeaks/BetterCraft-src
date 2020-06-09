/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class MovingSoundMinecart
/*    */   extends MovingSound {
/*    */   private final EntityMinecart minecart;
/* 11 */   private float distance = 0.0F;
/*    */ 
/*    */   
/*    */   public MovingSoundMinecart(EntityMinecart minecartIn) {
/* 15 */     super(SoundEvents.ENTITY_MINECART_RIDING, SoundCategory.NEUTRAL);
/* 16 */     this.minecart = minecartIn;
/* 17 */     this.repeat = true;
/* 18 */     this.repeatDelay = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 26 */     if (this.minecart.isDead) {
/*    */       
/* 28 */       this.donePlaying = true;
/*    */     }
/*    */     else {
/*    */       
/* 32 */       this.xPosF = (float)this.minecart.posX;
/* 33 */       this.yPosF = (float)this.minecart.posY;
/* 34 */       this.zPosF = (float)this.minecart.posZ;
/* 35 */       float f = MathHelper.sqrt(this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
/*    */       
/* 37 */       if (f >= 0.01D) {
/*    */         
/* 39 */         this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 1.0F);
/* 40 */         this.volume = 0.0F + MathHelper.clamp(f, 0.0F, 0.5F) * 0.7F;
/*    */       }
/*    */       else {
/*    */         
/* 44 */         this.distance = 0.0F;
/* 45 */         this.volume = 0.0F;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\MovingSoundMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */