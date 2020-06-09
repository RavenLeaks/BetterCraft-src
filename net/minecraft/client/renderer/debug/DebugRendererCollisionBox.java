/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderGlobal;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class DebugRendererCollisionBox
/*    */   implements DebugRenderer.IDebugRenderer {
/*    */   private final Minecraft field_191312_a;
/*    */   private EntityPlayer field_191313_b;
/*    */   private double field_191314_c;
/*    */   private double field_191315_d;
/*    */   private double field_191316_e;
/*    */   
/*    */   public DebugRendererCollisionBox(Minecraft p_i47215_1_) {
/* 21 */     this.field_191312_a = p_i47215_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(float p_190060_1_, long p_190060_2_) {
/* 26 */     this.field_191313_b = (EntityPlayer)this.field_191312_a.player;
/* 27 */     this.field_191314_c = this.field_191313_b.lastTickPosX + (this.field_191313_b.posX - this.field_191313_b.lastTickPosX) * p_190060_1_;
/* 28 */     this.field_191315_d = this.field_191313_b.lastTickPosY + (this.field_191313_b.posY - this.field_191313_b.lastTickPosY) * p_190060_1_;
/* 29 */     this.field_191316_e = this.field_191313_b.lastTickPosZ + (this.field_191313_b.posZ - this.field_191313_b.lastTickPosZ) * p_190060_1_;
/* 30 */     World world = this.field_191312_a.player.world;
/* 31 */     List<AxisAlignedBB> list = world.getCollisionBoxes((Entity)this.field_191313_b, this.field_191313_b.getEntityBoundingBox().expandXyz(6.0D));
/* 32 */     GlStateManager.enableBlend();
/* 33 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 34 */     GlStateManager.glLineWidth(2.0F);
/* 35 */     GlStateManager.disableTexture2D();
/* 36 */     GlStateManager.depthMask(false);
/*    */     
/* 38 */     for (AxisAlignedBB axisalignedbb : list)
/*    */     {
/* 40 */       RenderGlobal.drawSelectionBoundingBox(axisalignedbb.expandXyz(0.002D).offset(-this.field_191314_c, -this.field_191315_d, -this.field_191316_e), 1.0F, 1.0F, 1.0F, 1.0F);
/*    */     }
/*    */     
/* 43 */     GlStateManager.depthMask(true);
/* 44 */     GlStateManager.enableTexture2D();
/* 45 */     GlStateManager.disableBlend();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\debug\DebugRendererCollisionBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */