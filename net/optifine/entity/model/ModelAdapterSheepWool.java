/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSheep1;
/*    */ import net.minecraft.client.model.ModelSheep2;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSheep;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ import optifine.Config;
/*    */ 
/*    */ public class ModelAdapterSheepWool
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterSheepWool() {
/* 21 */     super(EntitySheep.class, "sheep_wool", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 26 */     return (ModelBase)new ModelSheep1();
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     RenderSheep renderSheep1;
/* 31 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 32 */     Render render = (Render)rendermanager.getEntityRenderMap().get(EntitySheep.class);
/*    */     
/* 34 */     if (!(render instanceof RenderSheep)) {
/*    */       
/* 36 */       Config.warn("Not a RenderSheep: " + render);
/* 37 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 41 */     if (render.getEntityClass() == null) {
/*    */       
/* 43 */       RenderSheep rendersheep = new RenderSheep(rendermanager);
/* 44 */       rendersheep.mainModel = (ModelBase)new ModelSheep2();
/* 45 */       rendersheep.shadowSize = 0.7F;
/* 46 */       renderSheep1 = rendersheep;
/*    */     } 
/*    */     
/* 49 */     RenderSheep rendersheep1 = renderSheep1;
/* 50 */     List<LayerRenderer<EntitySheep>> list = rendersheep1.getLayerRenderers();
/* 51 */     Iterator<LayerRenderer<EntitySheep>> iterator = list.iterator();
/*    */     
/* 53 */     while (iterator.hasNext()) {
/*    */       
/* 55 */       LayerRenderer layerrenderer = iterator.next();
/*    */       
/* 57 */       if (layerrenderer instanceof LayerSheepWool)
/*    */       {
/* 59 */         iterator.remove();
/*    */       }
/*    */     } 
/*    */     
/* 63 */     LayerSheepWool layersheepwool = new LayerSheepWool(rendersheep1);
/* 64 */     layersheepwool.sheepModel = (ModelSheep1)modelBase;
/* 65 */     rendersheep1.addLayer((LayerRenderer)layersheepwool);
/* 66 */     return (IEntityRenderer)rendersheep1;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterSheepWool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */