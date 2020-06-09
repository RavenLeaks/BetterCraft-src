/*   */ package net.optifine.entity.model.anim;
/*   */ 
/*   */ public class RenderResolverEntity
/*   */   implements IRenderResolver
/*   */ {
/*   */   public IExpression getParameter(String name) {
/* 7 */     EnumRenderParameterEntity enumrenderparameterentity = EnumRenderParameterEntity.parse(name);
/* 8 */     return enumrenderparameterentity;
/*   */   }
/*   */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\RenderResolverEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */