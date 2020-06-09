/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.profiler.Profiler;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.village.VillageCollection;
/*    */ import net.minecraft.world.border.IBorderListener;
/*    */ import net.minecraft.world.border.WorldBorder;
/*    */ import net.minecraft.world.storage.DerivedWorldInfo;
/*    */ import net.minecraft.world.storage.ISaveHandler;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ import net.minecraft.world.storage.WorldSavedData;
/*    */ 
/*    */ public class WorldServerMulti extends WorldServer {
/*    */   private final WorldServer delegate;
/*    */   
/*    */   public WorldServerMulti(MinecraftServer server, ISaveHandler saveHandlerIn, int dimensionId, WorldServer delegate, Profiler profilerIn) {
/* 17 */     super(server, saveHandlerIn, (WorldInfo)new DerivedWorldInfo(delegate.getWorldInfo()), dimensionId, profilerIn);
/* 18 */     this.delegate = delegate;
/* 19 */     delegate.getWorldBorder().addListener(new IBorderListener()
/*    */         {
/*    */           public void onSizeChanged(WorldBorder border, double newSize)
/*    */           {
/* 23 */             WorldServerMulti.this.getWorldBorder().setTransition(newSize);
/*    */           }
/*    */           
/*    */           public void onTransitionStarted(WorldBorder border, double oldSize, double newSize, long time) {
/* 27 */             WorldServerMulti.this.getWorldBorder().setTransition(oldSize, newSize, time);
/*    */           }
/*    */           
/*    */           public void onCenterChanged(WorldBorder border, double x, double z) {
/* 31 */             WorldServerMulti.this.getWorldBorder().setCenter(x, z);
/*    */           }
/*    */           
/*    */           public void onWarningTimeChanged(WorldBorder border, int newTime) {
/* 35 */             WorldServerMulti.this.getWorldBorder().setWarningTime(newTime);
/*    */           }
/*    */           
/*    */           public void onWarningDistanceChanged(WorldBorder border, int newDistance) {
/* 39 */             WorldServerMulti.this.getWorldBorder().setWarningDistance(newDistance);
/*    */           }
/*    */           
/*    */           public void onDamageAmountChanged(WorldBorder border, double newAmount) {
/* 43 */             WorldServerMulti.this.getWorldBorder().setDamageAmount(newAmount);
/*    */           }
/*    */           
/*    */           public void onDamageBufferChanged(WorldBorder border, double newSize) {
/* 47 */             WorldServerMulti.this.getWorldBorder().setDamageBuffer(newSize);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveLevel() throws MinecraftException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public World init() {
/* 61 */     this.mapStorage = this.delegate.getMapStorage();
/* 62 */     this.worldScoreboard = this.delegate.getScoreboard();
/* 63 */     this.lootTable = this.delegate.getLootTableManager();
/* 64 */     this.field_191951_C = this.delegate.func_191952_z();
/* 65 */     String s = VillageCollection.fileNameForProvider(this.provider);
/* 66 */     VillageCollection villagecollection = (VillageCollection)this.mapStorage.getOrLoadData(VillageCollection.class, s);
/*    */     
/* 68 */     if (villagecollection == null) {
/*    */       
/* 70 */       this.villageCollectionObj = new VillageCollection(this);
/* 71 */       this.mapStorage.setData(s, (WorldSavedData)this.villageCollectionObj);
/*    */     }
/*    */     else {
/*    */       
/* 75 */       this.villageCollectionObj = villagecollection;
/* 76 */       this.villageCollectionObj.setWorldsForAll(this);
/*    */     } 
/*    */     
/* 79 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveAdditionalData() {
/* 88 */     this.provider.onWorldSave();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\WorldServerMulti.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */