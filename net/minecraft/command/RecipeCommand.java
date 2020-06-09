/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipeCommand
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "recipe";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  29 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  37 */     return "commands.recipe.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  45 */     if (args.length < 2)
/*     */     {
/*  47 */       throw new WrongUsageException("commands.recipe.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  51 */     boolean flag = "give".equalsIgnoreCase(args[0]);
/*  52 */     boolean flag1 = "take".equalsIgnoreCase(args[0]);
/*     */     
/*  54 */     if (!flag && !flag1)
/*     */     {
/*  56 */       throw new WrongUsageException("commands.recipe.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  60 */     for (EntityPlayerMP entityplayermp : func_193513_a(server, sender, args[1])) {
/*     */       
/*  62 */       if ("*".equals(args[2])) {
/*     */         
/*  64 */         if (flag) {
/*     */           
/*  66 */           entityplayermp.func_192021_a(func_192556_d());
/*  67 */           notifyCommandListener(sender, this, "commands.recipe.give.success.all", new Object[] { entityplayermp.getName() });
/*     */           
/*     */           continue;
/*     */         } 
/*  71 */         entityplayermp.func_192022_b(func_192556_d());
/*  72 */         notifyCommandListener(sender, this, "commands.recipe.take.success.all", new Object[] { entityplayermp.getName() });
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  77 */       IRecipe irecipe = CraftingManager.func_193373_a(new ResourceLocation(args[2]));
/*     */       
/*  79 */       if (irecipe == null)
/*     */       {
/*  81 */         throw new CommandException("commands.recipe.unknownrecipe", new Object[] { args[2] });
/*     */       }
/*     */       
/*  84 */       if (irecipe.func_192399_d())
/*     */       {
/*  86 */         throw new CommandException("commands.recipe.unsupported", new Object[] { args[2] });
/*     */       }
/*     */       
/*  89 */       List<IRecipe> list = Lists.newArrayList((Object[])new IRecipe[] { irecipe });
/*     */       
/*  91 */       if (flag == entityplayermp.func_192037_E().func_193830_f(irecipe)) {
/*     */         
/*  93 */         String s = flag ? "commands.recipe.alreadyHave" : "commands.recipe.dontHave";
/*  94 */         throw new CommandException(s, new Object[] { entityplayermp.getName(), irecipe.getRecipeOutput().getDisplayName() });
/*     */       } 
/*     */       
/*  97 */       if (flag) {
/*     */         
/*  99 */         entityplayermp.func_192021_a(list);
/* 100 */         notifyCommandListener(sender, this, "commands.recipe.give.success.one", new Object[] { entityplayermp.getName(), irecipe.getRecipeOutput().getDisplayName() });
/*     */         
/*     */         continue;
/*     */       } 
/* 104 */       entityplayermp.func_192022_b(list);
/* 105 */       notifyCommandListener(sender, this, "commands.recipe.take.success.one", new Object[] { irecipe.getRecipeOutput().getDisplayName(), entityplayermp.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<IRecipe> func_192556_d() {
/* 115 */     return Lists.newArrayList((Iterable)CraftingManager.field_193380_a);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 120 */     if (args.length == 1)
/*     */     {
/* 122 */       return getListOfStringsMatchingLastWord(args, new String[] { "give", "take" });
/*     */     }
/* 124 */     if (args.length == 2)
/*     */     {
/* 126 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/*     */ 
/*     */     
/* 130 */     return (args.length == 3) ? getListOfStringsMatchingLastWord(args, CraftingManager.field_193380_a.getKeys()) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\RecipeCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */