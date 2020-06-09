/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderman;
/*    */ import net.minecraft.client.renderer.entity.RenderEnderman;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ 
/*    */ public class ModelAdapterEnderman
/*    */   extends ModelAdapterBiped
/*    */ {
/*    */   public ModelAdapterEnderman() {
/* 14 */     super(EntityEnderman.class, "enderman", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelEnderman(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     RenderEnderman renderenderman = new RenderEnderman(rendermanager);
/* 26 */     renderenderman.mainModel = modelBase;
/* 27 */     renderenderman.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderenderman;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterEnderman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */