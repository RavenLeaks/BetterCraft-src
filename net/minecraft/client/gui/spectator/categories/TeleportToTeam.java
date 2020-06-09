/*     */ package net.minecraft.client.gui.spectator.categories;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiSpectator;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuView;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ public class TeleportToTeam
/*     */   implements ISpectatorMenuView, ISpectatorMenuObject {
/*  26 */   private final List<ISpectatorMenuObject> items = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public TeleportToTeam() {
/*  30 */     Minecraft minecraft = Minecraft.getMinecraft();
/*     */     
/*  32 */     for (ScorePlayerTeam scoreplayerteam : minecraft.world.getScoreboard().getTeams())
/*     */     {
/*  34 */       this.items.add(new TeamSelectionObject(scoreplayerteam));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ISpectatorMenuObject> getItems() {
/*  40 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent getPrompt() {
/*  45 */     return (ITextComponent)new TextComponentTranslation("spectatorMenu.team_teleport.prompt", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectItem(SpectatorMenu menu) {
/*  50 */     menu.selectCategory(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent getSpectatorName() {
/*  55 */     return (ITextComponent)new TextComponentTranslation("spectatorMenu.team_teleport", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderIcon(float p_178663_1_, int alpha) {
/*  60 */     Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.SPECTATOR_WIDGETS);
/*  61 */     Gui.drawModalRectWithCustomSizedTexture(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  66 */     for (ISpectatorMenuObject ispectatormenuobject : this.items) {
/*     */       
/*  68 */       if (ispectatormenuobject.isEnabled())
/*     */       {
/*  70 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   class TeamSelectionObject
/*     */     implements ISpectatorMenuObject
/*     */   {
/*     */     private final ScorePlayerTeam team;
/*     */     private final ResourceLocation location;
/*     */     private final List<NetworkPlayerInfo> players;
/*     */     
/*     */     public TeamSelectionObject(ScorePlayerTeam p_i45492_2_) {
/*  85 */       this.team = p_i45492_2_;
/*  86 */       this.players = Lists.newArrayList();
/*     */       
/*  88 */       for (String s : p_i45492_2_.getMembershipCollection()) {
/*     */         
/*  90 */         NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(s);
/*     */         
/*  92 */         if (networkplayerinfo != null)
/*     */         {
/*  94 */           this.players.add(networkplayerinfo);
/*     */         }
/*     */       } 
/*     */       
/*  98 */       if (this.players.isEmpty()) {
/*     */         
/* 100 */         this.location = DefaultPlayerSkin.getDefaultSkinLegacy();
/*     */       }
/*     */       else {
/*     */         
/* 104 */         String s1 = ((NetworkPlayerInfo)this.players.get((new Random()).nextInt(this.players.size()))).getGameProfile().getName();
/* 105 */         this.location = AbstractClientPlayer.getLocationSkin(s1);
/* 106 */         AbstractClientPlayer.getDownloadImageSkin(this.location, s1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void selectItem(SpectatorMenu menu) {
/* 112 */       menu.selectCategory(new TeleportToPlayer(this.players));
/*     */     }
/*     */ 
/*     */     
/*     */     public ITextComponent getSpectatorName() {
/* 117 */       return (ITextComponent)new TextComponentString(this.team.getTeamName());
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderIcon(float p_178663_1_, int alpha) {
/* 122 */       int i = -1;
/* 123 */       String s = FontRenderer.getFormatFromString(this.team.getColorPrefix());
/*     */       
/* 125 */       if (s.length() >= 2)
/*     */       {
/* 127 */         i = (Minecraft.getMinecraft()).fontRendererObj.getColorCode(s.charAt(1));
/*     */       }
/*     */       
/* 130 */       if (i >= 0) {
/*     */         
/* 132 */         float f = (i >> 16 & 0xFF) / 255.0F;
/* 133 */         float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 134 */         float f2 = (i & 0xFF) / 255.0F;
/* 135 */         Gui.drawRect(1, 1, 15, 15, MathHelper.rgb(f * p_178663_1_, f1 * p_178663_1_, f2 * p_178663_1_) | alpha << 24);
/*     */       } 
/*     */       
/* 138 */       Minecraft.getMinecraft().getTextureManager().bindTexture(this.location);
/* 139 */       GlStateManager.color(p_178663_1_, p_178663_1_, p_178663_1_, alpha / 255.0F);
/* 140 */       Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
/* 141 */       Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEnabled() {
/* 146 */       return !this.players.isEmpty();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\spectator\categories\TeleportToTeam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */