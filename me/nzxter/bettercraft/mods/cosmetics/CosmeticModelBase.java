/*    */ package me.nzxter.bettercraft.mods.cosmetics;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ 
/*    */ public class CosmeticModelBase
/*    */   extends ModelBase {
/*    */   protected final ModelBiped playerModel;
/*    */   
/*    */   public CosmeticModelBase(RenderPlayer player) {
/* 12 */     this.playerModel = (ModelBiped)player.getMainModel();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\cosmetics\CosmeticModelBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */