/*     */ package me.nzxter.bettercraft.mods.checkhost;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Map;
/*     */ import me.nzxter.bettercraft.mods.checkhost.results.CheckHostHttpResult;
/*     */ import me.nzxter.bettercraft.mods.checkhost.results.CheckHostTcpResult;
/*     */ import me.nzxter.bettercraft.mods.checkhost.results.CheckHostUdpResult;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiCheckHost
/*     */   extends GuiScreen
/*     */ {
/*     */   public GuiTextField inputIp;
/*     */   public String mode;
/*     */   public CheckHostResult<Map<CheckHostServer, CheckHostHttpResult>> httpResult;
/*     */   public CheckHostResult<Map<CheckHostServer, CheckHostTcpResult>> tcpResult;
/*     */   public CheckHostResult<Map<CheckHostServer, CheckHostUdpResult>> udpResult;
/*     */   public int time;
/*     */   public GuiScreen before;
/*     */   
/*     */   public GuiCheckHost(GuiScreen screen) {
/*  32 */     this.mode = "";
/*  33 */     this.time = 0;
/*  34 */     this.before = screen;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  39 */     this.inputIp.updateCursorCounter();
/*  40 */     ((GuiButton)this.buttonList.get(1)).displayString = (this.mode == "tcp") ? "§aTCP" : "§cTCP";
/*  41 */     ((GuiButton)this.buttonList.get(2)).displayString = (this.mode == "http") ? "§aHTTP" : "§cHTTP";
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  46 */     Keyboard.enableRepeatEvents(true);
/*  47 */     this.buttonList.clear();
/*  48 */     this.buttonList.add(new GuiButton(1, this.width - 70, this.height - 30, 60, 20, "Back"));
/*  49 */     this.buttonList.add(new GuiButton(2, this.width - 144 + 74, 54, 60, 20, (this.mode == "tcp") ? "§aTCP" : "§cTCP"));
/*  50 */     this.buttonList.add(new GuiButton(3, this.width - 144 + 10, 54, 60, 20, (this.mode == "http") ? "§aHTTP" : "§cHTTP"));
/*  51 */     (this.inputIp = new GuiTextField(0, this.fontRendererObj, this.width - 134, 30, 124, 20)).setMaxStringLength(65535);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  56 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton clickedButton) {
/*  61 */     switch (clickedButton.id) {
/*     */       case 1:
/*  63 */         this.mc.displayGuiScreen(this.before);
/*     */         break;
/*     */       
/*     */       case 2:
/*  67 */         this.mode = "tcp";
/*     */         break;
/*     */       
/*     */       case 3:
/*  71 */         this.mode = "http";
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char par1, int par2) {
/*  79 */     if (par2 == 28 || par2 == 156) {
/*  80 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*  82 */     this.inputIp.textboxKeyTyped(par1, par2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int par1, int par2, int par3) throws IOException {
/*  87 */     super.mouseClicked(par1, par2, par3);
/*  88 */     this.inputIp.mouseClicked(par1, par2, par3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int par1, int par2, float par3) {
/*  93 */     drawDefaultBackground();
/*  94 */     this.inputIp.drawTextBox();
/*  95 */     if (this.time <= 550) {
/*  96 */       this.time += 5;
/*     */     } else {
/*     */       
/*  99 */       this.time = 0;
/*     */     } 
/* 101 */     if (this.time == 550) {
/* 102 */       (new Thread(new Runnable() {
/*     */             public void run() {
/*     */               String mode;
/*     */               String str1;
/* 106 */               switch ((str1 = mode = GuiCheckHost.this.mode).hashCode()) { case 114657: if (!str1.equals("tcp"))
/*     */                     break; 
/* 108 */                   if (GuiCheckHost.this.inputIp.getText().isEmpty()) {
/*     */                     return;
/*     */                   }
/*     */                   try {
/* 112 */                     GuiCheckHost.this.tcpResult = CheckHostAPI.createTcpRequest(GuiCheckHost.this.inputIp.getText(), 100);
/*     */                   }
/* 114 */                   catch (IOException iOException) {}
/*     */                   break;
/*     */                 case 3213448:
/*     */                   if (!str1.equals("http"))
/*     */                     break; 
/* 119 */                   if (GuiCheckHost.this.inputIp.getText().isEmpty()) {
/*     */                     return;
/*     */                   }
/*     */                   try {
/* 123 */                     GuiCheckHost.this.httpResult = CheckHostAPI.createHttpRequest(GuiCheckHost.this.inputIp.getText(), 100);
/*     */                   }
/* 125 */                   catch (IOException e) {
/* 126 */                     e.printStackTrace();
/*     */                   } 
/*     */                   break; }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 134 */           })).start();
/*     */     }
/* 136 */     int y = 31;
/* 137 */     if (this.mode.isEmpty()) {
/* 138 */       (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§6Waiting...", 10.0F, 18.0F, -1);
/*     */     }
/* 140 */     else if (this.mode.equals("http") && this.httpResult != null) {
/* 141 */       for (CheckHostServer r : this.httpResult.getServers()) {
/* 142 */         CheckHostHttpResult endResult = (CheckHostHttpResult)((Map)this.httpResult.getResult()).get(r);
/* 143 */         if (endResult != null) {
/* 144 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§6City§8/§6Country", 10.0F, 18.0F, -1);
/* 145 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§6Time", 150.0F, 18.0F, -1);
/* 146 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§6Code", 265.0F, 18.0F, -1);
/* 147 */           DecimalFormat fm = new DecimalFormat("00.##");
/* 148 */           String pingFormat = fm.format(endResult.getPing());
/* 149 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§e" + r.getCity() + "," + r.getCountryCode(), 10.0F, y, -1);
/* 150 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§e" + pingFormat + " seconds", 150.0F, y, -1);
/* 151 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§e" + endResult.getStatus(), 265.0F, y, -1);
/* 152 */           String file = "textures/gui/flags/" + r.getCountryCode() + ".png";
/* 153 */           Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(file));
/* 154 */           Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 100, y - 5, 0.0F, 0.0F, 18, 18, 18.0F, 18.0F);
/* 155 */           y += 20;
/*     */         }
/*     */       
/*     */       } 
/* 159 */     } else if (this.mode.equals("tcp") && this.tcpResult != null) {
/* 160 */       for (CheckHostServer r : this.tcpResult.getServers()) {
/* 161 */         CheckHostTcpResult endResult3 = (CheckHostTcpResult)((Map)this.tcpResult.getResult()).get(r);
/* 162 */         if (endResult3 != null) {
/* 163 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§6City§8/§6Country", 10.0F, 18.0F, -1);
/* 164 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§6Time", 150.0F, 18.0F, -1);
/* 165 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§6Server", 265.0F, 18.0F, -1);
/* 166 */           DecimalFormat fm = new DecimalFormat("00.##");
/* 167 */           String pingFormat = fm.format(endResult3.getPing());
/* 168 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§e" + r.getCity() + "," + r.getCountryCode(), 10.0F, y, -1);
/* 169 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§e" + pingFormat + " seconds", 150.0F, y, -1);
/* 170 */           (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow("§e" + r.getName(), 265.0F, y, -1);
/* 171 */           String file = "textures/gui/flags/" + r.getCountryCode() + ".png";
/* 172 */           Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(file));
/* 173 */           Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 85, y - 5, 0.0F, 0.0F, 18, 18, 18.0F, 18.0F);
/* 174 */           y += 20;
/*     */         } 
/*     */       } 
/*     */     } 
/* 178 */     this.mc.fontRendererObj.drawString("§7Server IP", this.width - 100, 20, -1);
/* 179 */     super.drawScreen(par1, par2, par3);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\checkhost\GuiCheckHost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */