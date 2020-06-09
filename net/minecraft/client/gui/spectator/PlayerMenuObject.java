/*    */ package net.minecraft.client.gui.spectator;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketSpectate;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ 
/*    */ public class PlayerMenuObject
/*    */   implements ISpectatorMenuObject {
/*    */   private final GameProfile profile;
/*    */   private final ResourceLocation resourceLocation;
/*    */   
/*    */   public PlayerMenuObject(GameProfile profileIn) {
/* 20 */     this.profile = profileIn;
/* 21 */     this.resourceLocation = AbstractClientPlayer.getLocationSkin(profileIn.getName());
/* 22 */     AbstractClientPlayer.getDownloadImageSkin(this.resourceLocation, profileIn.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public void selectItem(SpectatorMenu menu) {
/* 27 */     Minecraft.getMinecraft().getConnection().sendPacket((Packet)new CPacketSpectate(this.profile.getId()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getSpectatorName() {
/* 32 */     return (ITextComponent)new TextComponentString(this.profile.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderIcon(float p_178663_1_, int alpha) {
/* 37 */     Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
/* 38 */     GlStateManager.color(1.0F, 1.0F, 1.0F, alpha / 255.0F);
/* 39 */     Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
/* 40 */     Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\spectator\PlayerMenuObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */