/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandEffect
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  20 */     return "effect";
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
/*  36 */     return "commands.effect.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  44 */     if (args.length < 2)
/*     */     {
/*  46 */       throw new WrongUsageException("commands.effect.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  50 */     EntityLivingBase entitylivingbase = getEntity(server, sender, args[0], EntityLivingBase.class);
/*     */     
/*  52 */     if ("clear".equals(args[1])) {
/*     */       
/*  54 */       if (entitylivingbase.getActivePotionEffects().isEmpty())
/*     */       {
/*  56 */         throw new CommandException("commands.effect.failure.notActive.all", new Object[] { entitylivingbase.getName() });
/*     */       }
/*     */ 
/*     */       
/*  60 */       entitylivingbase.clearActivePotions();
/*  61 */       notifyCommandListener(sender, this, "commands.effect.success.removed.all", new Object[] { entitylivingbase.getName() });
/*     */     } else {
/*     */       Potion potion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  70 */         potion = Potion.getPotionById(parseInt(args[1], 1));
/*     */       }
/*  72 */       catch (NumberInvalidException var11) {
/*     */         
/*  74 */         potion = Potion.getPotionFromResourceLocation(args[1]);
/*     */       } 
/*     */       
/*  77 */       if (potion == null)
/*     */       {
/*  79 */         throw new NumberInvalidException("commands.effect.notFound", new Object[] { args[1] });
/*     */       }
/*     */ 
/*     */       
/*  83 */       int i = 600;
/*  84 */       int j = 30;
/*  85 */       int k = 0;
/*     */       
/*  87 */       if (args.length >= 3) {
/*     */         
/*  89 */         j = parseInt(args[2], 0, 1000000);
/*     */         
/*  91 */         if (potion.isInstant())
/*     */         {
/*  93 */           i = j;
/*     */         }
/*     */         else
/*     */         {
/*  97 */           i = j * 20;
/*     */         }
/*     */       
/* 100 */       } else if (potion.isInstant()) {
/*     */         
/* 102 */         i = 1;
/*     */       } 
/*     */       
/* 105 */       if (args.length >= 4)
/*     */       {
/* 107 */         k = parseInt(args[3], 0, 255);
/*     */       }
/*     */       
/* 110 */       boolean flag = true;
/*     */       
/* 112 */       if (args.length >= 5 && "true".equalsIgnoreCase(args[4]))
/*     */       {
/* 114 */         flag = false;
/*     */       }
/*     */       
/* 117 */       if (j > 0) {
/*     */         
/* 119 */         PotionEffect potioneffect = new PotionEffect(potion, i, k, false, flag);
/* 120 */         entitylivingbase.addPotionEffect(potioneffect);
/* 121 */         notifyCommandListener(sender, this, "commands.effect.success", new Object[] { new TextComponentTranslation(potioneffect.getEffectName(), new Object[0]), Integer.valueOf(Potion.getIdFromPotion(potion)), Integer.valueOf(k), entitylivingbase.getName(), Integer.valueOf(j) });
/*     */       }
/* 123 */       else if (entitylivingbase.isPotionActive(potion)) {
/*     */         
/* 125 */         entitylivingbase.removePotionEffect(potion);
/* 126 */         notifyCommandListener(sender, this, "commands.effect.success.removed", new Object[] { new TextComponentTranslation(potion.getName(), new Object[0]), entitylivingbase.getName() });
/*     */       }
/*     */       else {
/*     */         
/* 130 */         throw new CommandException("commands.effect.failure.notActive", new Object[] { new TextComponentTranslation(potion.getName(), new Object[0]), entitylivingbase.getName() });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 139 */     if (args.length == 1)
/*     */     {
/* 141 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/* 143 */     if (args.length == 2)
/*     */     {
/* 145 */       return getListOfStringsMatchingLastWord(args, Potion.REGISTRY.getKeys());
/*     */     }
/*     */ 
/*     */     
/* 149 */     return (args.length == 5) ? getListOfStringsMatchingLastWord(args, new String[] { "true", "false" }) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 158 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */