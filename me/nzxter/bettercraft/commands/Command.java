/*    */ package me.nzxter.bettercraft.commands;
/*    */ 
/*    */ import me.nzxter.bettercraft.BetterCraft;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ 
/*    */ public abstract class Command {
/*    */   public abstract void execute(String[] paramArrayOfString);
/*    */   
/*    */   public abstract String getName();
/*    */   
/*    */   public static void msg(String s, boolean prefix) {
/* 14 */     s = String.valueOf(String.valueOf(prefix ? ("&8&l[&6" + BetterCraft.clientName + "&8&l] &7") : "")) + s;
/* 15 */     (Minecraft.getMinecraft()).player.addChatMessage((ITextComponent)new TextComponentString(s.replace("&", "§")));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */