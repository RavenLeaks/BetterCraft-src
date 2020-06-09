/*    */ package optifine;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpriteDependencies
/*    */ {
/*    */   public static TextureAtlasSprite resolveDependencies(List<TextureAtlasSprite> p_resolveDependencies_0_, int p_resolveDependencies_1_, TextureMap p_resolveDependencies_2_) {
/*    */     TextureAtlasSprite textureatlassprite;
/* 14 */     for (textureatlassprite = p_resolveDependencies_0_.get(p_resolveDependencies_1_); resolveOne(p_resolveDependencies_0_, p_resolveDependencies_1_, textureatlassprite, p_resolveDependencies_2_); textureatlassprite = p_resolveDependencies_0_.get(p_resolveDependencies_1_));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 19 */     textureatlassprite.isDependencyParent = false;
/* 20 */     return textureatlassprite;
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean resolveOne(List<TextureAtlasSprite> p_resolveOne_0_, int p_resolveOne_1_, TextureAtlasSprite p_resolveOne_2_, TextureMap p_resolveOne_3_) {
/* 25 */     int i = 0;
/*    */     
/* 27 */     for (ResourceLocation resourcelocation : p_resolveOne_2_.getDependencies()) {
/*    */       
/* 29 */       Config.dbg("Sprite dependency: " + p_resolveOne_2_.getIconName() + " <- " + resourcelocation);
/* 30 */       TextureAtlasSprite textureatlassprite = p_resolveOne_3_.getRegisteredSprite(resourcelocation);
/*    */       
/* 32 */       if (textureatlassprite == null) {
/*    */         
/* 34 */         textureatlassprite = p_resolveOne_3_.registerSprite(resourcelocation);
/*    */       }
/*    */       else {
/*    */         
/* 38 */         int j = p_resolveOne_0_.indexOf(textureatlassprite);
/*    */         
/* 40 */         if (j <= p_resolveOne_1_ + i) {
/*    */           continue;
/*    */         }
/*    */ 
/*    */         
/* 45 */         if (textureatlassprite.isDependencyParent) {
/*    */           
/* 47 */           String s = "circular dependency: " + p_resolveOne_2_.getIconName() + " -> " + textureatlassprite.getIconName();
/* 48 */           ResourceLocation resourcelocation1 = p_resolveOne_3_.getResourceLocation(p_resolveOne_2_);
/* 49 */           ReflectorForge.FMLClientHandler_trackBrokenTexture(resourcelocation1, s);
/*    */           
/*    */           break;
/*    */         } 
/* 53 */         p_resolveOne_0_.remove(j);
/*    */       } 
/*    */       
/* 56 */       p_resolveOne_2_.isDependencyParent = true;
/* 57 */       p_resolveOne_0_.add(p_resolveOne_1_ + i, textureatlassprite);
/* 58 */       i++;
/*    */     } 
/*    */     
/* 61 */     return (i > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\SpriteDependencies.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */