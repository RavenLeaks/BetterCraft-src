/*     */ package net.minecraft.world.border;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ 
/*     */ public class WorldBorder
/*     */ {
/*  12 */   private final List<IBorderListener> listeners = Lists.newArrayList();
/*     */   private double centerX;
/*     */   private double centerZ;
/*  15 */   private double startDiameter = 6.0E7D;
/*     */   
/*     */   private double endDiameter;
/*     */   private long endTime;
/*     */   private long startTime;
/*     */   private int worldSize;
/*     */   private double damageAmount;
/*     */   private double damageBuffer;
/*     */   private int warningTime;
/*     */   private int warningDistance;
/*     */   
/*     */   public WorldBorder() {
/*  27 */     this.endDiameter = this.startDiameter;
/*  28 */     this.worldSize = 29999984;
/*  29 */     this.damageAmount = 0.2D;
/*  30 */     this.damageBuffer = 5.0D;
/*  31 */     this.warningTime = 15;
/*  32 */     this.warningDistance = 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(BlockPos pos) {
/*  37 */     return ((pos.getX() + 1) > minX() && pos.getX() < maxX() && (pos.getZ() + 1) > minZ() && pos.getZ() < maxZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(ChunkPos range) {
/*  42 */     return (range.getXEnd() > minX() && range.getXStart() < maxX() && range.getZEnd() > minZ() && range.getZStart() < maxZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(AxisAlignedBB bb) {
/*  47 */     return (bb.maxX > minX() && bb.minX < maxX() && bb.maxZ > minZ() && bb.minZ < maxZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public double getClosestDistance(Entity entityIn) {
/*  52 */     return getClosestDistance(entityIn.posX, entityIn.posZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getClosestDistance(double x, double z) {
/*  57 */     double d0 = z - minZ();
/*  58 */     double d1 = maxZ() - z;
/*  59 */     double d2 = x - minX();
/*  60 */     double d3 = maxX() - x;
/*  61 */     double d4 = Math.min(d2, d3);
/*  62 */     d4 = Math.min(d4, d0);
/*  63 */     return Math.min(d4, d1);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumBorderStatus getStatus() {
/*  68 */     if (this.endDiameter < this.startDiameter)
/*     */     {
/*  70 */       return EnumBorderStatus.SHRINKING;
/*     */     }
/*     */ 
/*     */     
/*  74 */     return (this.endDiameter > this.startDiameter) ? EnumBorderStatus.GROWING : EnumBorderStatus.STATIONARY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double minX() {
/*  80 */     double d0 = getCenterX() - getDiameter() / 2.0D;
/*     */     
/*  82 */     if (d0 < -this.worldSize)
/*     */     {
/*  84 */       d0 = -this.worldSize;
/*     */     }
/*     */     
/*  87 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double minZ() {
/*  92 */     double d0 = getCenterZ() - getDiameter() / 2.0D;
/*     */     
/*  94 */     if (d0 < -this.worldSize)
/*     */     {
/*  96 */       d0 = -this.worldSize;
/*     */     }
/*     */     
/*  99 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double maxX() {
/* 104 */     double d0 = getCenterX() + getDiameter() / 2.0D;
/*     */     
/* 106 */     if (d0 > this.worldSize)
/*     */     {
/* 108 */       d0 = this.worldSize;
/*     */     }
/*     */     
/* 111 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double maxZ() {
/* 116 */     double d0 = getCenterZ() + getDiameter() / 2.0D;
/*     */     
/* 118 */     if (d0 > this.worldSize)
/*     */     {
/* 120 */       d0 = this.worldSize;
/*     */     }
/*     */     
/* 123 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCenterX() {
/* 128 */     return this.centerX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCenterZ() {
/* 133 */     return this.centerZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCenter(double x, double z) {
/* 138 */     this.centerX = x;
/* 139 */     this.centerZ = z;
/*     */     
/* 141 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 143 */       iborderlistener.onCenterChanged(this, x, z);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDiameter() {
/* 149 */     if (getStatus() != EnumBorderStatus.STATIONARY) {
/*     */       
/* 151 */       double d0 = ((float)(System.currentTimeMillis() - this.startTime) / (float)(this.endTime - this.startTime));
/*     */       
/* 153 */       if (d0 < 1.0D)
/*     */       {
/* 155 */         return this.startDiameter + (this.endDiameter - this.startDiameter) * d0;
/*     */       }
/*     */       
/* 158 */       setTransition(this.endDiameter);
/*     */     } 
/*     */     
/* 161 */     return this.startDiameter;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTimeUntilTarget() {
/* 166 */     return (getStatus() == EnumBorderStatus.STATIONARY) ? 0L : (this.endTime - System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTargetSize() {
/* 171 */     return this.endDiameter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransition(double newSize) {
/* 176 */     this.startDiameter = newSize;
/* 177 */     this.endDiameter = newSize;
/* 178 */     this.endTime = System.currentTimeMillis();
/* 179 */     this.startTime = this.endTime;
/*     */     
/* 181 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 183 */       iborderlistener.onSizeChanged(this, newSize);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTransition(double oldSize, double newSize, long time) {
/* 189 */     this.startDiameter = oldSize;
/* 190 */     this.endDiameter = newSize;
/* 191 */     this.startTime = System.currentTimeMillis();
/* 192 */     this.endTime = this.startTime + time;
/*     */     
/* 194 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 196 */       iborderlistener.onTransitionStarted(this, oldSize, newSize, time);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<IBorderListener> getListeners() {
/* 202 */     return Lists.newArrayList(this.listeners);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(IBorderListener listener) {
/* 207 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSize(int size) {
/* 212 */     this.worldSize = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 217 */     return this.worldSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDamageBuffer() {
/* 222 */     return this.damageBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamageBuffer(double bufferSize) {
/* 227 */     this.damageBuffer = bufferSize;
/*     */     
/* 229 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 231 */       iborderlistener.onDamageBufferChanged(this, bufferSize);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDamageAmount() {
/* 237 */     return this.damageAmount;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamageAmount(double newAmount) {
/* 242 */     this.damageAmount = newAmount;
/*     */     
/* 244 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 246 */       iborderlistener.onDamageAmountChanged(this, newAmount);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getResizeSpeed() {
/* 252 */     return (this.endTime == this.startTime) ? 0.0D : (Math.abs(this.startDiameter - this.endDiameter) / (this.endTime - this.startTime));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWarningTime() {
/* 257 */     return this.warningTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWarningTime(int warningTime) {
/* 262 */     this.warningTime = warningTime;
/*     */     
/* 264 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 266 */       iborderlistener.onWarningTimeChanged(this, warningTime);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWarningDistance() {
/* 272 */     return this.warningDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWarningDistance(int warningDistance) {
/* 277 */     this.warningDistance = warningDistance;
/*     */     
/* 279 */     for (IBorderListener iborderlistener : getListeners())
/*     */     {
/* 281 */       iborderlistener.onWarningDistanceChanged(this, warningDistance);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\border\WorldBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */