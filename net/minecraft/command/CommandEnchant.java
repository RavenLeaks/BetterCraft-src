/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandEnchant
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  20 */     return "enchant";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  28 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  36 */     return "commands.enchant.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*     */     Enchantment enchantment;
/*  44 */     if (args.length < 2)
/*     */     {
/*  46 */       throw new WrongUsageException("commands.enchant.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  50 */     EntityLivingBase entitylivingbase = getEntity(server, sender, args[0], EntityLivingBase.class);
/*  51 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  56 */       enchantment = Enchantment.getEnchantmentByID(parseInt(args[1], 0));
/*     */     }
/*  58 */     catch (NumberInvalidException var12) {
/*     */       
/*  60 */       enchantment = Enchantment.getEnchantmentByLocation(args[1]);
/*     */     } 
/*     */     
/*  63 */     if (enchantment == null)
/*     */     {
/*  65 */       throw new NumberInvalidException("commands.enchant.notFound", new Object[] { args[1] });
/*     */     }
/*     */ 
/*     */     
/*  69 */     int i = 1;
/*  70 */     ItemStack itemstack = entitylivingbase.getHeldItemMainhand();
/*     */     
/*  72 */     if (itemstack.func_190926_b())
/*     */     {
/*  74 */       throw new CommandException("commands.enchant.noItem", new Object[0]);
/*     */     }
/*  76 */     if (!enchantment.canApply(itemstack))
/*     */     {
/*  78 */       throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  82 */     if (args.length >= 3)
/*     */     {
/*  84 */       i = parseInt(args[2], enchantment.getMinLevel(), enchantment.getMaxLevel());
/*     */     }
/*     */     
/*  87 */     if (itemstack.hasTagCompound()) {
/*     */       
/*  89 */       NBTTagList nbttaglist = itemstack.getEnchantmentTagList();
/*     */       
/*  91 */       for (int j = 0; j < nbttaglist.tagCount(); j++) {
/*     */         
/*  93 */         int k = nbttaglist.getCompoundTagAt(j).getShort("id");
/*     */         
/*  95 */         if (Enchantment.getEnchantmentByID(k) != null) {
/*     */           
/*  97 */           Enchantment enchantment1 = Enchantment.getEnchantmentByID(k);
/*     */           
/*  99 */           if (!enchantment.func_191560_c(enchantment1))
/*     */           {
/* 101 */             throw new CommandException("commands.enchant.cantCombine", new Object[] { enchantment.getTranslatedName(i), enchantment1.getTranslatedName(nbttaglist.getCompoundTagAt(j).getShort("lvl")) });
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     itemstack.addEnchantment(enchantment, i);
/* 108 */     notifyCommandListener(sender, this, "commands.enchant.success", new Object[0]);
/* 109 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 117 */     if (args.length == 1)
/*     */     {
/* 119 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/*     */ 
/*     */     
/* 123 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, Enchantment.REGISTRY.getKeys()) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 132 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandEnchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */