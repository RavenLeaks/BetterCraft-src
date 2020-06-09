/*     */ package me.nzxter.bettercraft.commands.impl;
/*     */ 
/*     */ import me.nzxter.bettercraft.commands.Command;
/*     */ import me.nzxter.bettercraft.commands.CommandManager;
/*     */ import me.nzxter.bettercraft.mods.crasher.AnimationCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.FlyCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.GiveBookCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.GiveFireworkCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.MassChunkLoadCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.MoveCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.MultiVerseCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.NettyCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.OnePacketCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.SkriptCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.WorldEditCrasher;
/*     */ import me.nzxter.bettercraft.mods.crasher.cpc.CustomPayloadCrasher1;
/*     */ import me.nzxter.bettercraft.mods.crasher.cpc.CustomPayloadCrasher2;
/*     */ import me.nzxter.bettercraft.mods.crasher.cpc.CustomPayloadCrasher3;
/*     */ import me.nzxter.bettercraft.mods.crasher.cpc.CustomPayloadCrasher4;
/*     */ import me.nzxter.bettercraft.mods.crasher.obc.OpenBookCrasher1;
/*     */ import me.nzxter.bettercraft.mods.crasher.obc.OpenBookCrasher2;
/*     */ import me.nzxter.bettercraft.mods.crasher.obc.OpenBookCrasher3;
/*     */ import me.nzxter.bettercraft.mods.crasher.pc.PermissionsExCrasher1;
/*     */ import me.nzxter.bettercraft.mods.crasher.pc.PermissionsExCrasher2;
/*     */ 
/*     */ 
/*     */ public class CrashCommand
/*     */   extends Command
/*     */ {
/*     */   public void execute(String[] args) {
/*  31 */     if (args.length == 0) {
/*  32 */       msg("", false);
/*  33 */       msg("§e" + CommandManager.syntax + "crasher worldedit", true);
/*  34 */       msg("§e" + CommandManager.syntax + "crasher multiverse", true);
/*  35 */       msg("§e" + CommandManager.syntax + "crasher skript", true);
/*  36 */       msg("§e" + CommandManager.syntax + "crasher onepacket", true);
/*  37 */       msg("§e" + CommandManager.syntax + "crasher netty", true);
/*  38 */       msg("§e" + CommandManager.syntax + "crasher move", true);
/*  39 */       msg("§e" + CommandManager.syntax + "crasher masschunk", true);
/*  40 */       msg("§e" + CommandManager.syntax + "crasher givefirework", true);
/*  41 */       msg("§e" + CommandManager.syntax + "crasher fly", true);
/*  42 */       msg("§e" + CommandManager.syntax + "crasher givebook", true);
/*  43 */       msg("§e" + CommandManager.syntax + "crasher anim", true);
/*  44 */       msg("", false);
/*  45 */       msg("§e" + CommandManager.syntax + "crasher pex1", true);
/*  46 */       msg("§e" + CommandManager.syntax + "crasher pex2", true);
/*  47 */       msg("", false);
/*  48 */       msg("§e" + CommandManager.syntax + "crasher openbook1", true);
/*  49 */       msg("§e" + CommandManager.syntax + "crasher openbook2", true);
/*  50 */       msg("§e" + CommandManager.syntax + "crasher openbook3", true);
/*  51 */       msg("", false);
/*  52 */       msg("§e" + CommandManager.syntax + "crasher custompayload1", true);
/*  53 */       msg("§e" + CommandManager.syntax + "crasher custompayload2", true);
/*  54 */       msg("§e" + CommandManager.syntax + "crasher custompayload3", true);
/*  55 */       msg("§e" + CommandManager.syntax + "crasher custompayload4", true);
/*     */     }
/*  57 */     else if (args.length == 1) {
/*     */       
/*  59 */       if (args[0].equalsIgnoreCase("worldedit")) {
/*  60 */         msg("Try to crash...", true);
/*  61 */         WorldEditCrasher.start();
/*     */       }
/*  63 */       else if (args[0].equalsIgnoreCase("multiverse")) {
/*  64 */         msg("Try to crash...", true);
/*  65 */         MultiVerseCrasher.start();
/*     */       }
/*  67 */       else if (args[0].equalsIgnoreCase("skript")) {
/*  68 */         msg("Try to crash...", true);
/*  69 */         SkriptCrasher.start();
/*     */       }
/*  71 */       else if (args[0].equalsIgnoreCase("onepacket")) {
/*  72 */         msg("Try to crash...", true);
/*  73 */         OnePacketCrasher.start();
/*     */       }
/*  75 */       else if (args[0].equalsIgnoreCase("netty")) {
/*  76 */         msg("Try to crash...", true);
/*  77 */         NettyCrasher.start();
/*     */       }
/*  79 */       else if (args[0].equalsIgnoreCase("move")) {
/*  80 */         msg("Try to crash...", true);
/*  81 */         MoveCrasher.start();
/*     */       }
/*  83 */       else if (args[0].equalsIgnoreCase("masschunk")) {
/*  84 */         msg("Try to crash...", true);
/*  85 */         MassChunkLoadCrasher.start();
/*     */       }
/*  87 */       else if (args[0].equalsIgnoreCase("givefirework")) {
/*  88 */         msg("Try to crash...", true);
/*  89 */         GiveFireworkCrasher.start();
/*     */       }
/*  91 */       else if (args[0].equalsIgnoreCase("fly")) {
/*  92 */         msg("Try to crash...", true);
/*  93 */         FlyCrasher.start();
/*     */       }
/*  95 */       else if (args[0].equalsIgnoreCase("givebook")) {
/*  96 */         msg("Try to crash...", true);
/*  97 */         GiveBookCrasher.start();
/*     */       }
/*  99 */       else if (args[0].equalsIgnoreCase("anim")) {
/* 100 */         msg("Try to crash...", true);
/* 101 */         AnimationCrasher.start();
/*     */       }
/* 103 */       else if (args[0].equalsIgnoreCase("pex1")) {
/* 104 */         msg("Try to crash...", true);
/* 105 */         PermissionsExCrasher1.start();
/*     */       }
/* 107 */       else if (args[0].equalsIgnoreCase("pex2")) {
/* 108 */         msg("Try to crash...", true);
/* 109 */         PermissionsExCrasher2.start();
/*     */       }
/* 111 */       else if (args[0].equalsIgnoreCase("openbook1")) {
/* 112 */         msg("Try to crash...", true);
/* 113 */         OpenBookCrasher1.start();
/*     */       }
/* 115 */       else if (args[0].equalsIgnoreCase("openbook2")) {
/* 116 */         msg("Try to crash...", true);
/* 117 */         OpenBookCrasher2.start();
/*     */       }
/* 119 */       else if (args[0].equalsIgnoreCase("openbook3")) {
/* 120 */         msg("Try to crash...", true);
/* 121 */         OpenBookCrasher3.start();
/*     */       }
/* 123 */       else if (args[0].equalsIgnoreCase("custompayload1")) {
/* 124 */         msg("Try to crash...", true);
/* 125 */         CustomPayloadCrasher1.start();
/*     */       }
/* 127 */       else if (args[0].equalsIgnoreCase("custompayload2")) {
/* 128 */         msg("Try to crash...", true);
/* 129 */         CustomPayloadCrasher2.start();
/*     */       }
/* 131 */       else if (args[0].equalsIgnoreCase("custompayload3")) {
/* 132 */         msg("Try to crash...", true);
/* 133 */         CustomPayloadCrasher3.start();
/*     */       }
/* 135 */       else if (args[0].equalsIgnoreCase("custompayload4")) {
/* 136 */         msg("Try to crash...", true);
/* 137 */         CustomPayloadCrasher4.start();
/*     */       } else {
/*     */         
/* 140 */         msg("§cType crasher <text>", true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 147 */     return "crasher";
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\impl\CrashCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */