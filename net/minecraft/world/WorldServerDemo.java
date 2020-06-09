/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.profiler.Profiler;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.storage.ISaveHandler;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ public class WorldServerDemo
/*    */   extends WorldServer {
/* 10 */   private static final long DEMO_WORLD_SEED = "North Carolina".hashCode();
/* 11 */   public static final WorldSettings DEMO_WORLD_SETTINGS = (new WorldSettings(DEMO_WORLD_SEED, GameType.SURVIVAL, true, false, WorldType.DEFAULT)).enableBonusChest();
/*    */ 
/*    */   
/*    */   public WorldServerDemo(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo worldInfoIn, int dimensionId, Profiler profilerIn) {
/* 15 */     super(server, saveHandlerIn, worldInfoIn, dimensionId, profilerIn);
/* 16 */     this.worldInfo.populateFromWorldSettings(DEMO_WORLD_SETTINGS);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\WorldServerDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */