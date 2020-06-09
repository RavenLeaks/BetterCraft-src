/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Ordering;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.scoreboard.IScoreCriteria;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.GameType;
/*     */ 
/*     */ public class GuiPlayerTabOverlay extends Gui {
/*  26 */   private static final Ordering<NetworkPlayerInfo> ENTRY_ORDERING = Ordering.from(new PlayerComparator(null));
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private final GuiIngame guiIngame;
/*     */   
/*     */   private ITextComponent footer;
/*     */   
/*     */   private ITextComponent header;
/*     */   
/*     */   private long lastTimeOpened;
/*     */   
/*     */   private boolean isBeingRendered;
/*     */ 
/*     */   
/*     */   public GuiPlayerTabOverlay(Minecraft mcIn, GuiIngame guiIngameIn) {
/*  42 */     this.mc = mcIn;
/*  43 */     this.guiIngame = guiIngameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
/*  51 */     return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePlayerList(boolean willBeRendered) {
/*  60 */     if (willBeRendered && !this.isBeingRendered)
/*     */     {
/*  62 */       this.lastTimeOpened = Minecraft.getSystemTime();
/*     */     }
/*     */     
/*  65 */     this.isBeingRendered = willBeRendered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPlayerlist(int width, Scoreboard scoreboardIn, @Nullable ScoreObjective scoreObjectiveIn) {
/*     */     int l;
/*  73 */     NetHandlerPlayClient nethandlerplayclient = this.mc.player.connection;
/*  74 */     List<NetworkPlayerInfo> list = ENTRY_ORDERING.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
/*  75 */     int i = 0;
/*  76 */     int j = 0;
/*     */     
/*  78 */     for (NetworkPlayerInfo networkplayerinfo : list) {
/*     */       
/*  80 */       int k = this.mc.fontRendererObj.getStringWidth(getPlayerName(networkplayerinfo));
/*  81 */       i = Math.max(i, k);
/*     */       
/*  83 */       if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreCriteria.EnumRenderType.HEARTS) {
/*     */         
/*  85 */         k = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getOrCreateScore(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
/*  86 */         j = Math.max(j, k);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     list = list.subList(0, Math.min(list.size(), 80));
/*  91 */     int l3 = list.size();
/*  92 */     int i4 = l3;
/*     */     
/*     */     int j4;
/*  95 */     for (j4 = 1; i4 > 20; i4 = (l3 + j4 - 1) / j4)
/*     */     {
/*  97 */       j4++;
/*     */     }
/*     */     
/* 100 */     boolean flag = !(!this.mc.isIntegratedServerRunning() && !this.mc.getConnection().getNetworkManager().isEncrypted());
/*     */ 
/*     */     
/* 103 */     if (scoreObjectiveIn != null) {
/*     */       
/* 105 */       if (scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS)
/*     */       {
/* 107 */         l = 90;
/*     */       }
/*     */       else
/*     */       {
/* 111 */         l = j;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 116 */       l = 0;
/*     */     } 
/*     */     
/* 119 */     int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
/* 120 */     int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
/* 121 */     int k1 = 10;
/* 122 */     int l1 = i1 * j4 + (j4 - 1) * 5;
/* 123 */     List<String> list1 = null;
/*     */     
/* 125 */     if (this.header != null) {
/*     */       
/* 127 */       list1 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);
/*     */       
/* 129 */       for (String s : list1)
/*     */       {
/* 131 */         l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s));
/*     */       }
/*     */     } 
/*     */     
/* 135 */     List<String> list2 = null;
/*     */     
/* 137 */     if (this.footer != null) {
/*     */       
/* 139 */       list2 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);
/*     */       
/* 141 */       for (String s1 : list2)
/*     */       {
/* 143 */         l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s1));
/*     */       }
/*     */     } 
/*     */     
/* 147 */     if (list1 != null) {
/*     */       
/* 149 */       drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list1.size() * this.mc.fontRendererObj.FONT_HEIGHT, -2147483648);
/*     */       
/* 151 */       for (String s2 : list1) {
/*     */         
/* 153 */         int i2 = this.mc.fontRendererObj.getStringWidth(s2);
/* 154 */         this.mc.fontRendererObj.drawStringWithShadow(s2, (width / 2 - i2 / 2), k1, -1);
/* 155 */         k1 += this.mc.fontRendererObj.FONT_HEIGHT;
/*     */       } 
/*     */       
/* 158 */       k1++;
/*     */     } 
/*     */     
/* 161 */     drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + i4 * 9, -2147483648);
/*     */     
/* 163 */     for (int k4 = 0; k4 < l3; k4++) {
/*     */       
/* 165 */       int l4 = k4 / i4;
/* 166 */       int i5 = k4 % i4;
/* 167 */       int j2 = j1 + l4 * i1 + l4 * 5;
/* 168 */       int k2 = k1 + i5 * 9;
/* 169 */       drawRect(j2, k2, j2 + i1, k2 + 8, 553648127);
/* 170 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 171 */       GlStateManager.enableAlpha();
/* 172 */       GlStateManager.enableBlend();
/* 173 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */       
/* 175 */       if (k4 < list.size()) {
/*     */         
/* 177 */         NetworkPlayerInfo networkplayerinfo1 = list.get(k4);
/* 178 */         GameProfile gameprofile = networkplayerinfo1.getGameProfile();
/*     */         
/* 180 */         if (flag) {
/*     */           
/* 182 */           EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());
/* 183 */           boolean flag1 = (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName())));
/* 184 */           this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
/* 185 */           int l2 = 8 + (flag1 ? 8 : 0);
/* 186 */           int i3 = 8 * (flag1 ? -1 : 1);
/* 187 */           Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0F, l2, 8, i3, 8, 8, 64.0F, 64.0F);
/*     */           
/* 189 */           if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
/*     */             
/* 191 */             int j3 = 8 + (flag1 ? 8 : 0);
/* 192 */             int k3 = 8 * (flag1 ? -1 : 1);
/* 193 */             Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0F, j3, 8, k3, 8, 8, 64.0F, 64.0F);
/*     */           } 
/*     */           
/* 196 */           j2 += 9;
/*     */         } 
/*     */         
/* 199 */         String s4 = getPlayerName(networkplayerinfo1);
/*     */         
/* 201 */         if (networkplayerinfo1.getGameType() == GameType.SPECTATOR) {
/*     */           
/* 203 */           this.mc.fontRendererObj.drawStringWithShadow(TextFormatting.ITALIC + s4, j2, k2, -1862270977);
/*     */         }
/*     */         else {
/*     */           
/* 207 */           this.mc.fontRendererObj.drawStringWithShadow(s4, j2, k2, -1);
/*     */         } 
/*     */         
/* 210 */         if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != GameType.SPECTATOR) {
/*     */           
/* 212 */           int k5 = j2 + i + 1;
/* 213 */           int l5 = k5 + l;
/*     */           
/* 215 */           if (l5 - k5 > 5)
/*     */           {
/* 217 */             drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
/*     */           }
/*     */         } 
/*     */         
/* 221 */         drawPing(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1);
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     if (list2 != null) {
/*     */       
/* 227 */       k1 = k1 + i4 * 9 + 1;
/* 228 */       drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT, -2147483648);
/*     */       
/* 230 */       for (String s3 : list2) {
/*     */         
/* 232 */         int j5 = this.mc.fontRendererObj.getStringWidth(s3);
/* 233 */         this.mc.fontRendererObj.drawStringWithShadow(s3, (width / 2 - j5 / 2), k1, -1);
/* 234 */         k1 += this.mc.fontRendererObj.FONT_HEIGHT;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawPing(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn) {
/*     */     int j;
/* 241 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 242 */     this.mc.getTextureManager().bindTexture(ICONS);
/* 243 */     int i = 0;
/*     */ 
/*     */     
/* 246 */     if (networkPlayerInfoIn.getResponseTime() < 0) {
/*     */       
/* 248 */       j = 5;
/*     */     }
/* 250 */     else if (networkPlayerInfoIn.getResponseTime() < 150) {
/*     */       
/* 252 */       j = 0;
/*     */     }
/* 254 */     else if (networkPlayerInfoIn.getResponseTime() < 300) {
/*     */       
/* 256 */       j = 1;
/*     */     }
/* 258 */     else if (networkPlayerInfoIn.getResponseTime() < 600) {
/*     */       
/* 260 */       j = 2;
/*     */     }
/* 262 */     else if (networkPlayerInfoIn.getResponseTime() < 1000) {
/*     */       
/* 264 */       j = 3;
/*     */     }
/*     */     else {
/*     */       
/* 268 */       j = 4;
/*     */     } 
/*     */     
/* 271 */     this.zLevel += 100.0F;
/* 272 */     drawTexturedModalRect(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0, 176 + j * 8, 10, 8);
/* 273 */     this.zLevel -= 100.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawScoreboardValues(ScoreObjective objective, int p_175247_2_, String name, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo info) {
/* 278 */     int i = objective.getScoreboard().getOrCreateScore(name, objective).getScorePoints();
/*     */     
/* 280 */     if (objective.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) {
/*     */       
/* 282 */       this.mc.getTextureManager().bindTexture(ICONS);
/*     */       
/* 284 */       if (this.lastTimeOpened == info.getRenderVisibilityId())
/*     */       {
/* 286 */         if (i < info.getLastHealth()) {
/*     */           
/* 288 */           info.setLastHealthTime(Minecraft.getSystemTime());
/* 289 */           info.setHealthBlinkTime((this.guiIngame.getUpdateCounter() + 20));
/*     */         }
/* 291 */         else if (i > info.getLastHealth()) {
/*     */           
/* 293 */           info.setLastHealthTime(Minecraft.getSystemTime());
/* 294 */           info.setHealthBlinkTime((this.guiIngame.getUpdateCounter() + 10));
/*     */         } 
/*     */       }
/*     */       
/* 298 */       if (Minecraft.getSystemTime() - info.getLastHealthTime() > 1000L || this.lastTimeOpened != info.getRenderVisibilityId()) {
/*     */         
/* 300 */         info.setLastHealth(i);
/* 301 */         info.setDisplayHealth(i);
/* 302 */         info.setLastHealthTime(Minecraft.getSystemTime());
/*     */       } 
/*     */       
/* 305 */       info.setRenderVisibilityId(this.lastTimeOpened);
/* 306 */       info.setLastHealth(i);
/* 307 */       int j = MathHelper.ceil(Math.max(i, info.getDisplayHealth()) / 2.0F);
/* 308 */       int k = Math.max(MathHelper.ceil((i / 2)), Math.max(MathHelper.ceil((info.getDisplayHealth() / 2)), 10));
/* 309 */       boolean flag = (info.getHealthBlinkTime() > this.guiIngame.getUpdateCounter() && (info.getHealthBlinkTime() - this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L);
/*     */       
/* 311 */       if (j > 0) {
/*     */         
/* 313 */         float f = Math.min((p_175247_5_ - p_175247_4_ - 4) / k, 9.0F);
/*     */         
/* 315 */         if (f > 3.0F) {
/*     */           
/* 317 */           for (int l = j; l < k; l++)
/*     */           {
/* 319 */             drawTexturedModalRect(p_175247_4_ + l * f, p_175247_2_, flag ? 25 : 16, 0, 9, 9);
/*     */           }
/*     */           
/* 322 */           for (int j1 = 0; j1 < j; j1++) {
/*     */             
/* 324 */             drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, flag ? 25 : 16, 0, 9, 9);
/*     */             
/* 326 */             if (flag) {
/*     */               
/* 328 */               if (j1 * 2 + 1 < info.getDisplayHealth())
/*     */               {
/* 330 */                 drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, 70, 0, 9, 9);
/*     */               }
/*     */               
/* 333 */               if (j1 * 2 + 1 == info.getDisplayHealth())
/*     */               {
/* 335 */                 drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, 79, 0, 9, 9);
/*     */               }
/*     */             } 
/*     */             
/* 339 */             if (j1 * 2 + 1 < i)
/*     */             {
/* 341 */               drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, (j1 >= 10) ? 160 : 52, 0, 9, 9);
/*     */             }
/*     */             
/* 344 */             if (j1 * 2 + 1 == i)
/*     */             {
/* 346 */               drawTexturedModalRect(p_175247_4_ + j1 * f, p_175247_2_, (j1 >= 10) ? 169 : 61, 0, 9, 9);
/*     */             }
/*     */           } 
/*     */         } else {
/*     */           String str;
/*     */           
/* 352 */           float f1 = MathHelper.clamp(i / 20.0F, 0.0F, 1.0F);
/* 353 */           int i1 = (int)((1.0F - f1) * 255.0F) << 16 | (int)(f1 * 255.0F) << 8;
/* 354 */           float f2 = i / 2.0F;
/*     */           
/* 356 */           if (p_175247_5_ - this.mc.fontRendererObj.getStringWidth(String.valueOf(f2) + "hp") >= p_175247_4_)
/*     */           {
/* 358 */             str = String.valueOf(f2) + "hp";
/*     */           }
/*     */           
/* 361 */           this.mc.fontRendererObj.drawStringWithShadow(str, ((p_175247_5_ + p_175247_4_) / 2 - this.mc.fontRendererObj.getStringWidth(str) / 2), p_175247_2_, i1);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 367 */       String s1 = TextFormatting.YELLOW + i;
/* 368 */       this.mc.fontRendererObj.drawStringWithShadow(s1, (p_175247_5_ - this.mc.fontRendererObj.getStringWidth(s1)), p_175247_2_, 16777215);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFooter(@Nullable ITextComponent footerIn) {
/* 374 */     this.footer = footerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeader(@Nullable ITextComponent headerIn) {
/* 379 */     this.header = headerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetFooterHeader() {
/* 384 */     this.header = null;
/* 385 */     this.footer = null;
/*     */   }
/*     */ 
/*     */   
/*     */   static class PlayerComparator
/*     */     implements Comparator<NetworkPlayerInfo>
/*     */   {
/*     */     private PlayerComparator() {}
/*     */ 
/*     */     
/*     */     public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
/* 396 */       ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
/* 397 */       ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
/* 398 */       return ComparisonChain.start().compareTrueFirst((p_compare_1_.getGameType() != GameType.SPECTATOR), (p_compare_2_.getGameType() != GameType.SPECTATOR)).compare((scoreplayerteam != null) ? scoreplayerteam.getRegisteredName() : "", (scoreplayerteam1 != null) ? scoreplayerteam1.getRegisteredName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiPlayerTabOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */