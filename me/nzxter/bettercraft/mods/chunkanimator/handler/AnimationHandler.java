/*     */ package me.nzxter.bettercraft.mods.chunkanimator.handler;
/*     */ 
/*     */ import java.util.WeakHashMap;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.ChunkAnimator;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Back;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Bounce;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Circ;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Cubic;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Elastic;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Expo;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Linear;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Quad;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Quart;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Quint;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.easing.Sine;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnimationHandler
/*     */ {
/*  30 */   WeakHashMap<RenderChunk, AnimationData> timeStamps = new WeakHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void preRender(RenderChunk renderChunk) {
/*  35 */     if (this.timeStamps.containsKey(renderChunk)) {
/*     */       
/*  37 */       AnimationData animationData = this.timeStamps.get(renderChunk);
/*  38 */       long time = animationData.timeStamp;
/*  39 */       int mode = ChunkAnimator.INSTANCE.mode;
/*     */       
/*  41 */       if (time == -1L) {
/*     */         
/*  43 */         time = System.currentTimeMillis();
/*     */         
/*  45 */         animationData.timeStamp = time;
/*     */ 
/*     */         
/*  48 */         if (mode == 4) {
/*     */           EnumFacing chunkFacing;
/*  50 */           BlockPos zeroedPlayerPosition = (Minecraft.getMinecraft()).player.getPosition();
/*  51 */           zeroedPlayerPosition = zeroedPlayerPosition.add(0, -zeroedPlayerPosition.getY(), 0);
/*     */           
/*  53 */           BlockPos zeroedCenteredChunkPos = renderChunk.getPosition().add(8, -renderChunk.getPosition().getY(), 8);
/*     */           
/*  55 */           BlockPos blockPos1 = zeroedPlayerPosition.subtract((Vec3i)zeroedCenteredChunkPos);
/*     */           
/*  57 */           int difX = Math.abs(blockPos1.getX());
/*  58 */           int difZ = Math.abs(blockPos1.getZ());
/*     */ 
/*     */ 
/*     */           
/*  62 */           if (difX > difZ) {
/*     */             
/*  64 */             if (blockPos1.getX() > 0)
/*     */             {
/*  66 */               chunkFacing = EnumFacing.EAST;
/*     */             }
/*     */             else
/*     */             {
/*  70 */               chunkFacing = EnumFacing.WEST;
/*     */             
/*     */             }
/*     */           
/*     */           }
/*  75 */           else if (blockPos1.getZ() > 0) {
/*     */             
/*  77 */             chunkFacing = EnumFacing.SOUTH;
/*     */           }
/*     */           else {
/*     */             
/*  81 */             chunkFacing = EnumFacing.NORTH;
/*     */           } 
/*     */ 
/*     */           
/*  85 */           animationData.chunkFacing = chunkFacing;
/*     */         } 
/*     */       } 
/*     */       
/*  89 */       long timeDif = System.currentTimeMillis() - time;
/*     */       
/*  91 */       int animationDuration = ChunkAnimator.INSTANCE.animationDuration;
/*     */       
/*  93 */       if (timeDif < animationDuration) {
/*     */         EnumFacing chunkFacing;
/*  95 */         int chunkY = renderChunk.getPosition().getY();
/*     */ 
/*     */         
/*  98 */         if (mode == 2)
/*     */         {
/* 100 */           if (chunkY < (Minecraft.getMinecraft()).world.getHorizon()) {
/*     */             
/* 102 */             mode = 0;
/*     */           }
/*     */           else {
/*     */             
/* 106 */             mode = 1;
/*     */           } 
/*     */         }
/*     */         
/* 110 */         if (mode == 4)
/*     */         {
/* 112 */           mode = 3;
/*     */         }
/*     */         
/* 115 */         switch (mode) {
/*     */           
/*     */           case 0:
/* 118 */             GlStateManager.translate(0.0F, -chunkY + getFunctionValue((float)timeDif, 0.0F, chunkY, animationDuration), 0.0F);
/*     */             break;
/*     */           case 1:
/* 121 */             GlStateManager.translate(0.0F, (256 - chunkY) - getFunctionValue((float)timeDif, 0.0F, (256 - chunkY), animationDuration), 0.0F);
/*     */             break;
/*     */           case 3:
/* 124 */             chunkFacing = animationData.chunkFacing;
/*     */             
/* 126 */             if (chunkFacing != null) {
/*     */               
/* 128 */               Vec3i vec = chunkFacing.getDirectionVec();
/* 129 */               double mod = -(200.0D - 200.0D / animationDuration * timeDif);
/*     */               
/* 131 */               mod = -(200.0F - getFunctionValue((float)timeDif, 0.0F, 200.0F, animationDuration));
/*     */               
/* 133 */               GlStateManager.translate(vec.getX() * mod, 0.0D, vec.getZ() * mod);
/*     */             } 
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } else {
/* 140 */         this.timeStamps.remove(renderChunk);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float getFunctionValue(float t, float b, float c, float d) {
/* 147 */     switch (ChunkAnimator.INSTANCE.easingFunction) {
/*     */       
/*     */       case 0:
/* 150 */         return Linear.easeOut(t, b, c, d);
/*     */       case 1:
/* 152 */         return Quad.easeOut(t, b, c, d);
/*     */       case 2:
/* 154 */         return Cubic.easeOut(t, b, c, d);
/*     */       case 3:
/* 156 */         return Quart.easeOut(t, b, c, d);
/*     */       case 4:
/* 158 */         return Quint.easeOut(t, b, c, d);
/*     */       case 5:
/* 160 */         return Expo.easeOut(t, b, c, d);
/*     */       case 6:
/* 162 */         return Sine.easeOut(t, b, c, d);
/*     */       case 7:
/* 164 */         return Circ.easeOut(t, b, c, d);
/*     */       case 8:
/* 166 */         return Back.easeOut(t, b, c, d);
/*     */       case 9:
/* 168 */         return Bounce.easeOut(t, b, c, d);
/*     */       case 10:
/* 170 */         return Elastic.easeOut(t, b, c, d);
/*     */     } 
/*     */     
/* 173 */     return Sine.easeOut(t, b, c, d);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOrigin(RenderChunk renderChunk, BlockPos position) {
/* 178 */     if ((Minecraft.getMinecraft()).player != null) {
/*     */       
/* 180 */       boolean flag = true;
/* 181 */       BlockPos zeroedPlayerPosition = (Minecraft.getMinecraft()).player.getPosition();
/* 182 */       zeroedPlayerPosition = zeroedPlayerPosition.add(0, -zeroedPlayerPosition.getY(), 0);
/* 183 */       BlockPos zeroedCenteredChunkPos = position.add(8, -position.getY(), 8);
/*     */       
/* 185 */       if (ChunkAnimator.INSTANCE.disableAroundPlayer)
/*     */       {
/* 187 */         flag = (zeroedPlayerPosition.distanceSq((Vec3i)zeroedCenteredChunkPos) > 4096.0D);
/*     */       }
/*     */       
/* 190 */       if (flag) {
/*     */         
/* 192 */         EnumFacing chunkFacing = null;
/*     */         
/* 194 */         if (ChunkAnimator.INSTANCE.mode == 3) {
/*     */           
/* 196 */           BlockPos blockPos = zeroedPlayerPosition.subtract((Vec3i)zeroedCenteredChunkPos);
/*     */           
/* 198 */           int difX = Math.abs(blockPos.getX());
/* 199 */           int difZ = Math.abs(blockPos.getZ());
/*     */           
/* 201 */           if (difX > difZ) {
/*     */             
/* 203 */             if (blockPos.getX() > 0)
/*     */             {
/* 205 */               chunkFacing = EnumFacing.EAST;
/*     */             }
/*     */             else
/*     */             {
/* 209 */               chunkFacing = EnumFacing.WEST;
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 214 */           else if (blockPos.getZ() > 0) {
/*     */             
/* 216 */             chunkFacing = EnumFacing.SOUTH;
/*     */           }
/*     */           else {
/*     */             
/* 220 */             chunkFacing = EnumFacing.NORTH;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 225 */         AnimationData animationData = new AnimationData(-1L, chunkFacing);
/* 226 */         this.timeStamps.put(renderChunk, animationData);
/*     */ 
/*     */       
/*     */       }
/* 230 */       else if (this.timeStamps.containsKey(renderChunk)) {
/*     */         
/* 232 */         this.timeStamps.remove(renderChunk);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class AnimationData
/*     */   {
/*     */     public long timeStamp;
/*     */     
/*     */     public EnumFacing chunkFacing;
/*     */ 
/*     */     
/*     */     public AnimationData(long timeStamp, EnumFacing chunkFacing) {
/* 247 */       this.timeStamp = timeStamp;
/* 248 */       this.chunkFacing = chunkFacing;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\chunkanimator\handler\AnimationHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */