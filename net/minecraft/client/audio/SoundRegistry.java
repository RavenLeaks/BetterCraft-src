/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.registry.RegistrySimple;
/*    */ 
/*    */ public class SoundRegistry
/*    */   extends RegistrySimple<ResourceLocation, SoundEventAccessor>
/*    */ {
/*    */   private Map<ResourceLocation, SoundEventAccessor> soundRegistry;
/*    */   
/*    */   protected Map<ResourceLocation, SoundEventAccessor> createUnderlyingMap() {
/* 14 */     this.soundRegistry = Maps.newHashMap();
/* 15 */     return this.soundRegistry;
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(SoundEventAccessor accessor) {
/* 20 */     putObject(accessor.getLocation(), accessor);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clearMap() {
/* 28 */     this.soundRegistry.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\SoundRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */