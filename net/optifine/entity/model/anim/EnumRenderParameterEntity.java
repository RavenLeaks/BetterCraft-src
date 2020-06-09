/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ 
/*    */ public enum EnumRenderParameterEntity
/*    */   implements IExpression {
/* 10 */   LIMB_SWING("limb_swing"),
/* 11 */   LIMB_SWING_SPEED("limb_speed"),
/* 12 */   AGE("age"),
/* 13 */   HEAD_YAW("head_yaw"),
/* 14 */   HEAD_PITCH("head_pitch"),
/* 15 */   SCALE("scale");
/*    */   private String name;
/*    */   
/*    */   static {
/* 19 */     VALUES = values();
/*    */   }
/*    */   private RenderManager renderManager; private static final EnumRenderParameterEntity[] VALUES;
/*    */   EnumRenderParameterEntity(String name) {
/* 23 */     this.name = name;
/* 24 */     this.renderManager = Minecraft.getMinecraft().getRenderManager();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 29 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public float eval() {
/* 34 */     Render render = this.renderManager.renderRender;
/*    */     
/* 36 */     if (render == null)
/*    */     {
/* 38 */       return 0.0F;
/*    */     }
/*    */ 
/*    */     
/* 42 */     if (render instanceof RenderLivingBase) {
/*    */       
/* 44 */       RenderLivingBase renderlivingbase = (RenderLivingBase)render;
/*    */       
/* 46 */       switch (this) {
/*    */         
/*    */         case LIMB_SWING:
/* 49 */           return renderlivingbase.renderLimbSwing;
/*    */         
/*    */         case LIMB_SWING_SPEED:
/* 52 */           return renderlivingbase.renderLimbSwingAmount;
/*    */         
/*    */         case null:
/* 55 */           return renderlivingbase.renderAgeInTicks;
/*    */         
/*    */         case HEAD_YAW:
/* 58 */           return renderlivingbase.renderHeadYaw;
/*    */         
/*    */         case HEAD_PITCH:
/* 61 */           return renderlivingbase.renderHeadPitch;
/*    */         
/*    */         case SCALE:
/* 64 */           return renderlivingbase.renderScaleFactor;
/*    */       } 
/*    */     
/*    */     } 
/* 68 */     return 0.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static EnumRenderParameterEntity parse(String str) {
/* 74 */     if (str == null)
/*    */     {
/* 76 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 80 */     for (int i = 0; i < VALUES.length; i++) {
/*    */       
/* 82 */       EnumRenderParameterEntity enumrenderparameterentity = VALUES[i];
/*    */       
/* 84 */       if (enumrenderparameterentity.getName().equals(str))
/*    */       {
/* 86 */         return enumrenderparameterentity;
/*    */       }
/*    */     } 
/*    */     
/* 90 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\EnumRenderParameterEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */