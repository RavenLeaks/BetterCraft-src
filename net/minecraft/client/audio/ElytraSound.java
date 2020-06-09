/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ElytraSound
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityPlayerSP player;
/*    */   private int time;
/*    */   
/*    */   public ElytraSound(EntityPlayerSP p_i47113_1_) {
/* 15 */     super(SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.PLAYERS);
/* 16 */     this.player = p_i47113_1_;
/* 17 */     this.repeat = true;
/* 18 */     this.repeatDelay = 0;
/* 19 */     this.volume = 0.1F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 27 */     this.time++;
/*    */     
/* 29 */     if (!this.player.isDead && (this.time <= 20 || this.player.isElytraFlying())) {
/*    */       
/* 31 */       this.xPosF = (float)this.player.posX;
/* 32 */       this.yPosF = (float)this.player.posY;
/* 33 */       this.zPosF = (float)this.player.posZ;
/* 34 */       float f = MathHelper.sqrt(this.player.motionX * this.player.motionX + this.player.motionZ * this.player.motionZ + this.player.motionY * this.player.motionY);
/* 35 */       float f1 = f / 2.0F;
/*    */       
/* 37 */       if (f >= 0.01D) {
/*    */         
/* 39 */         this.volume = MathHelper.clamp(f1 * f1, 0.0F, 1.0F);
/*    */       }
/*    */       else {
/*    */         
/* 43 */         this.volume = 0.0F;
/*    */       } 
/*    */       
/* 46 */       if (this.time < 20) {
/*    */         
/* 48 */         this.volume = 0.0F;
/*    */       }
/* 50 */       else if (this.time < 40) {
/*    */         
/* 52 */         this.volume = (float)(this.volume * (this.time - 20) / 20.0D);
/*    */       } 
/*    */       
/* 55 */       float f2 = 0.8F;
/*    */       
/* 57 */       if (this.volume > 0.8F)
/*    */       {
/* 59 */         this.pitch = 1.0F + this.volume - 0.8F;
/*    */       }
/*    */       else
/*    */       {
/* 63 */         this.pitch = 1.0F;
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 68 */       this.donePlaying = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\ElytraSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */