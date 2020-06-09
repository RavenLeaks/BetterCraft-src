/*     */ package me.nzxter.bettercraft;
/*     */ 
/*     */ import com.TominoCZ.FBP.FBP;
/*     */ import me.nzxter.bettercraft.commands.CommandManager;
/*     */ import me.nzxter.bettercraft.mods.altmanager.AltManager;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.ChunkAnimator;
/*     */ import me.nzxter.bettercraft.mods.protocolhack.ProtocolHack;
/*     */ import me.nzxter.bettercraft.utils.FileManager;
/*     */ import me.nzxter.bettercraft.utils.SplashProgressUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum BetterCraft
/*     */ {
/*  46 */   INSTANCE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BetterCraft() {
/* 112 */     this.currentVersion = ProtocolHack.PROTOCOL_340;
/*     */   }
/*     */   public static String clientName; public static String clientVersion; public static String clientAuthor; public static String clientPrefix; public boolean updateShaderState;
/* 115 */   public ProtocolHack getCurrentVersion() { return this.currentVersion; }
/*     */   public static String shaderSource;
/*     */   private ProtocolHack currentVersion;
/*     */   static { clientName = "BetterCraft"; clientVersion = "v1.9.1"; clientAuthor = "NzxterMC"; clientPrefix = "§8[§6BetterCraft§8] "; shaderSource = ""; }
/* 119 */   public void onEnable() { SplashProgressUtils.setProgress(1, "BetterCraft - Startup"); try { FileManager.createFiles(); } catch (Exception exception) {} try { CommandManager.commands(); } catch (Exception exception) {} try { AltManager.loadAlts(); } catch (Exception exception) {} try { (new FBP()).initialize(); } catch (Exception exception) {} try { ChunkAnimator.INSTANCE.onStart(); } catch (Exception exception) {} try { setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_340); } catch (Exception exception) {} } public void onDisable() { try { AltManager.saveAlts(); } catch (Exception exception) {} } public void applyShader() { this.updateShaderState = true; } public ProtocolHack getCurrentMinecraftVersion() { return this.currentVersion; }
/*     */ 
/*     */   
/*     */   public void setCurrentMinecraftVersion(ProtocolHack currentVersion) {
/* 123 */     this.currentVersion = currentVersion;
/*     */   }
/*     */   
/*     */   public void moveVersionBackward() {
/* 127 */     String str = this.currentVersion.getName();
/* 128 */     switch (str.hashCode()) {
/*     */       case 48572:
/* 130 */         if (!str.equals("1.9"))
/* 131 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_47);
/*     */         break;
/*     */       
/*     */       case 1505535:
/* 135 */         if (!str.equals("1.13"))
/* 136 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_340);
/*     */         break;
/*     */       
/*     */       case 46678199:
/* 140 */         if (!str.equals("1.8.*"))
/* 141 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_5);
/*     */         break;
/*     */       
/*     */       case 46679167:
/* 145 */         if (!str.equals("1.9.1"))
/* 146 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_108);
/*     */         break;
/*     */       
/*     */       case 46679168:
/* 150 */         if (!str.equals("1.9.2"))
/* 151 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_107);
/*     */         break;
/*     */       
/*     */       case 46679169:
/* 155 */         if (!str.equals("1.9.3"))
/* 156 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_108);
/*     */         break;
/*     */       
/*     */       case 46679170:
/* 160 */         if (!str.equals("1.9.4"))
/* 161 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_109);
/*     */         break;
/*     */       
/*     */       case 1446817720:
/* 165 */         if (!str.equals("1.10.*"))
/* 166 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_110);
/*     */         break;
/*     */       
/*     */       case 1446818681:
/* 170 */         if (!str.equals("1.11.*"))
/* 171 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_210);
/*     */         break;
/*     */       
/*     */       case 1446819642:
/* 175 */         if (!str.equals("1.12.*"))
/* 176 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_316);
/*     */         break;
/*     */       
/*     */       case 1446994643:
/* 180 */         if (!str.equals("1.7.10"))
/* 181 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_393);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void moveVersionForward() {
/* 187 */     String str = this.currentVersion.getName();
/* 188 */     switch (str.hashCode()) {
/*     */       case 48572:
/* 190 */         if (!str.equals("1.9"))
/* 191 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_108);
/*     */         break;
/*     */       
/*     */       case 1505535:
/* 195 */         if (!str.equals("1.13"))
/* 196 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_5);
/*     */         break;
/*     */       
/*     */       case 46678199:
/* 200 */         if (!str.equals("1.8.*"))
/* 201 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_107);
/*     */         break;
/*     */       
/*     */       case 46679167:
/* 205 */         if (!str.equals("1.9.1"))
/* 206 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_109);
/*     */         break;
/*     */       
/*     */       case 46679168:
/* 210 */         if (!str.equals("1.9.2"))
/* 211 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_110);
/*     */         break;
/*     */       
/*     */       case 46679170:
/* 215 */         if (!str.equals("1.9.4"))
/* 216 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_210);
/*     */         break;
/*     */       
/*     */       case 1446817720:
/* 220 */         if (!str.equals("1.10.*"))
/* 221 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_316);
/*     */         break;
/*     */       
/*     */       case 1446818681:
/* 225 */         if (!str.equals("1.11.*"))
/* 226 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_340);
/*     */         break;
/*     */       
/*     */       case 1446819642:
/* 230 */         if (!str.equals("1.12.*"))
/* 231 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_393);
/*     */         break;
/*     */       
/*     */       case 1446994643:
/* 235 */         if (!str.equals("1.7.10"))
/* 236 */           break;  setCurrentMinecraftVersion(ProtocolHack.PROTOCOL_47);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\BetterCraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */