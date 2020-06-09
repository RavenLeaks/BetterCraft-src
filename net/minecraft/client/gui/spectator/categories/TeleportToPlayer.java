/*    */ package net.minecraft.client.gui.spectator.categories;
/*    */ 
/*    */ import com.google.common.collect.ComparisonChain;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Ordering;
/*    */ import java.util.Collection;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiSpectator;
/*    */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*    */ import net.minecraft.client.gui.spectator.ISpectatorMenuView;
/*    */ import net.minecraft.client.gui.spectator.PlayerMenuObject;
/*    */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*    */ import net.minecraft.client.network.NetworkPlayerInfo;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.world.GameType;
/*    */ 
/*    */ public class TeleportToPlayer
/*    */   implements ISpectatorMenuView, ISpectatorMenuObject {
/* 23 */   private static final Ordering<NetworkPlayerInfo> PROFILE_ORDER = Ordering.from(new Comparator<NetworkPlayerInfo>()
/*    */       {
/*    */         public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_)
/*    */         {
/* 27 */           return ComparisonChain.start().compare(p_compare_1_.getGameProfile().getId(), p_compare_2_.getGameProfile().getId()).result();
/*    */         }
/*    */       });
/*    */   
/*    */   private final List<ISpectatorMenuObject> items;
/*    */   
/*    */   public TeleportToPlayer() {
/* 34 */     this(PROFILE_ORDER.sortedCopy(Minecraft.getMinecraft().getConnection().getPlayerInfoMap()));
/*    */   }
/*    */ 
/*    */   
/*    */   public TeleportToPlayer(Collection<NetworkPlayerInfo> p_i45493_1_) {
/* 39 */     this.items = Lists.newArrayList();
/*    */     
/* 41 */     for (NetworkPlayerInfo networkplayerinfo : PROFILE_ORDER.sortedCopy(p_i45493_1_)) {
/*    */       
/* 43 */       if (networkplayerinfo.getGameType() != GameType.SPECTATOR)
/*    */       {
/* 45 */         this.items.add(new PlayerMenuObject(networkplayerinfo.getGameProfile()));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ISpectatorMenuObject> getItems() {
/* 52 */     return this.items;
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getPrompt() {
/* 57 */     return (ITextComponent)new TextComponentTranslation("spectatorMenu.teleport.prompt", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void selectItem(SpectatorMenu menu) {
/* 62 */     menu.selectCategory(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getSpectatorName() {
/* 67 */     return (ITextComponent)new TextComponentTranslation("spectatorMenu.teleport", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderIcon(float p_178663_1_, int alpha) {
/* 72 */     Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.SPECTATOR_WIDGETS);
/* 73 */     Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 78 */     return !this.items.isEmpty();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\spectator\categories\TeleportToPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */