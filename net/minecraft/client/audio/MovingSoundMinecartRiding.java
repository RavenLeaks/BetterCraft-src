/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class MovingSoundMinecartRiding
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityPlayer player;
/*    */   private final EntityMinecart minecart;
/*    */   
/*    */   public MovingSoundMinecartRiding(EntityPlayer playerRiding, EntityMinecart minecart) {
/* 16 */     super(SoundEvents.ENTITY_MINECART_INSIDE, SoundCategory.NEUTRAL);
/* 17 */     this.player = playerRiding;
/* 18 */     this.minecart = minecart;
/* 19 */     this.attenuationType = ISound.AttenuationType.NONE;
/* 20 */     this.repeat = true;
/* 21 */     this.repeatDelay = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 29 */     if (!this.minecart.isDead && this.player.isRiding() && this.player.getRidingEntity() == this.minecart) {
/*    */       
/* 31 */       float f = MathHelper.sqrt(this.minecart.motionX * this.minecart.motionX + this.minecart.motionZ * this.minecart.motionZ);
/*    */       
/* 33 */       if (f >= 0.01D)
/*    */       {
/* 35 */         this.volume = 0.0F + MathHelper.clamp(f, 0.0F, 1.0F) * 0.75F;
/*    */       }
/*    */       else
/*    */       {
/* 39 */         this.volume = 0.0F;
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 44 */       this.donePlaying = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\MovingSoundMinecartRiding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */