/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandGive
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  24 */     return "give";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  32 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  40 */     return "commands.give.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  48 */     if (args.length < 2)
/*     */     {
/*  50 */       throw new WrongUsageException("commands.give.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  54 */     EntityPlayerMP entityPlayerMP = getPlayer(server, sender, args[0]);
/*  55 */     Item item = getItemByText(sender, args[1]);
/*  56 */     int i = (args.length >= 3) ? parseInt(args[2], 1, item.getItemStackLimit()) : 1;
/*  57 */     int j = (args.length >= 4) ? parseInt(args[3]) : 0;
/*  58 */     ItemStack itemstack = new ItemStack(item, i, j);
/*     */     
/*  60 */     if (args.length >= 5) {
/*     */       
/*  62 */       String s = buildString(args, 4);
/*     */ 
/*     */       
/*     */       try {
/*  66 */         itemstack.setTagCompound(JsonToNBT.getTagFromJson(s));
/*     */       }
/*  68 */       catch (NBTException nbtexception) {
/*     */         
/*  70 */         throw new CommandException("commands.give.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     boolean flag = ((EntityPlayer)entityPlayerMP).inventory.addItemStackToInventory(itemstack);
/*     */     
/*  76 */     if (flag) {
/*     */       
/*  78 */       ((EntityPlayer)entityPlayerMP).world.playSound(null, ((EntityPlayer)entityPlayerMP).posX, ((EntityPlayer)entityPlayerMP).posY, ((EntityPlayer)entityPlayerMP).posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((entityPlayerMP.getRNG().nextFloat() - entityPlayerMP.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*  79 */       ((EntityPlayer)entityPlayerMP).inventoryContainer.detectAndSendChanges();
/*     */     } 
/*     */     
/*  82 */     if (flag && itemstack.func_190926_b()) {
/*     */       
/*  84 */       itemstack.func_190920_e(1);
/*  85 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, i);
/*  86 */       EntityItem entityitem1 = entityPlayerMP.dropItem(itemstack, false);
/*     */       
/*  88 */       if (entityitem1 != null)
/*     */       {
/*  90 */         entityitem1.makeFakeItem();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  95 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, i - itemstack.func_190916_E());
/*  96 */       EntityItem entityitem = entityPlayerMP.dropItem(itemstack, false);
/*     */       
/*  98 */       if (entityitem != null) {
/*     */         
/* 100 */         entityitem.setNoPickupDelay();
/* 101 */         entityitem.setOwner(entityPlayerMP.getName());
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     notifyCommandListener(sender, this, "commands.give.success", new Object[] { itemstack.getTextComponent(), Integer.valueOf(i), entityPlayerMP.getName() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 111 */     if (args.length == 1)
/*     */     {
/* 113 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/*     */ 
/*     */     
/* 117 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys()) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 126 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandGive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */