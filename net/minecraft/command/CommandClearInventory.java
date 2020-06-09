/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandClearInventory
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  22 */     return "clear";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  30 */     return "commands.clear.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  38 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  46 */     EntityPlayerMP entityplayermp = (args.length == 0) ? getCommandSenderAsPlayer(sender) : getPlayer(server, sender, args[0]);
/*  47 */     Item item = (args.length >= 2) ? getItemByText(sender, args[1]) : null;
/*  48 */     int i = (args.length >= 3) ? parseInt(args[2], -1) : -1;
/*  49 */     int j = (args.length >= 4) ? parseInt(args[3], -1) : -1;
/*  50 */     NBTTagCompound nbttagcompound = null;
/*     */     
/*  52 */     if (args.length >= 5) {
/*     */       
/*     */       try {
/*     */         
/*  56 */         nbttagcompound = JsonToNBT.getTagFromJson(buildString(args, 4));
/*     */       }
/*  58 */       catch (NBTException nbtexception) {
/*     */         
/*  60 */         throw new CommandException("commands.clear.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     }
/*     */     
/*  64 */     if (args.length >= 2 && item == null)
/*     */     {
/*  66 */       throw new CommandException("commands.clear.failure", new Object[] { entityplayermp.getName() });
/*     */     }
/*     */ 
/*     */     
/*  70 */     int k = entityplayermp.inventory.clearMatchingItems(item, i, j, nbttagcompound);
/*  71 */     entityplayermp.inventoryContainer.detectAndSendChanges();
/*     */     
/*  73 */     if (!entityplayermp.capabilities.isCreativeMode)
/*     */     {
/*  75 */       entityplayermp.updateHeldItem();
/*     */     }
/*     */     
/*  78 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
/*     */     
/*  80 */     if (k == 0)
/*     */     {
/*  82 */       throw new CommandException("commands.clear.failure", new Object[] { entityplayermp.getName() });
/*     */     }
/*     */ 
/*     */     
/*  86 */     if (j == 0) {
/*     */       
/*  88 */       sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.clear.testing", new Object[] { entityplayermp.getName(), Integer.valueOf(k) }));
/*     */     }
/*     */     else {
/*     */       
/*  92 */       notifyCommandListener(sender, this, "commands.clear.success", new Object[] { entityplayermp.getName(), Integer.valueOf(k) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 100 */     if (args.length == 1)
/*     */     {
/* 102 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/*     */ 
/*     */     
/* 106 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys()) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 115 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandClearInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */