/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.google.common.collect.Maps;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelHorse;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.LayeredTexture;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderHorse extends RenderLiving<EntityHorse> {
/* 13 */   private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   public RenderHorse(RenderManager p_i47205_1_) {
/* 17 */     super(p_i47205_1_, (ModelBase)new ModelHorse(), 0.75F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityHorse entity) {
/* 25 */     String s = entity.getHorseTexture();
/* 26 */     ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);
/*    */     
/* 28 */     if (resourcelocation == null) {
/*    */       
/* 30 */       resourcelocation = new ResourceLocation(s);
/* 31 */       Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, (ITextureObject)new LayeredTexture(entity.getVariantTexturePaths()));
/* 32 */       LAYERED_LOCATION_CACHE.put(s, resourcelocation);
/*    */     } 
/*    */     
/* 35 */     return resourcelocation;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */