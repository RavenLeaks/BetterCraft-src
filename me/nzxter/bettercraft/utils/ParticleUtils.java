/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ 
/*    */ public class ParticleUtils
/*    */ {
/*    */   private final List<Particle> particles;
/*    */   private int width;
/*    */   private int height;
/*    */   private int count;
/*    */   
/*    */   public ParticleUtils(int width, int height) {
/* 16 */     this.width = width;
/* 17 */     this.height = height;
/* 18 */     this.count = 150;
/* 19 */     this.particles = new ArrayList<>();
/* 20 */     for (int count = 0; count <= this.count; count++) {
/* 21 */       this.particles.add(new Particle((new Random()).nextInt(width), (new Random()).nextInt(height)));
/*    */     }
/*    */   }
/*    */   
/*    */   public void drawParticles() {
/* 26 */     this.particles.forEach(particle -> particle.drawParticle());
/*    */   }
/*    */   
/*    */   public class Particle {
/*    */     private int xPos;
/*    */     private int yPos;
/*    */     
/*    */     public Particle(int xPos, int yPos) {
/* 34 */       this.xPos = xPos;
/* 35 */       this.yPos = yPos;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void drawParticle() {
/* 41 */       this.xPos += (new Random()).nextInt(2);
/* 42 */       this.yPos += (new Random()).nextInt(2);
/* 43 */       int particleSize = 1;
/*    */       
/* 45 */       if (this.xPos > ParticleUtils.this.width) {
/* 46 */         this.xPos = -1;
/*    */       }
/* 48 */       if (this.yPos > ParticleUtils.this.height) {
/* 49 */         this.yPos = -1;
/*    */       }
/*    */       
/* 52 */       Gui.drawRect(this.xPos, this.yPos, this.xPos + 1, this.yPos + 1, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\ParticleUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */