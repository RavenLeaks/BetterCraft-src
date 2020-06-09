/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandReplaceItem
/*     */   extends CommandBase {
/*  25 */   private static final Map<String, Integer> SHORTCUTS = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  32 */     return "replaceitem";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  40 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  48 */     return "commands.replaceitem.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*     */     boolean flag;
/*     */     int i;
/*     */     Item item;
/*  56 */     if (args.length < 1)
/*     */     {
/*  58 */       throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     if ("entity".equals(args[0])) {
/*     */       
/*  66 */       flag = false;
/*     */     }
/*     */     else {
/*     */       
/*  70 */       if (!"block".equals(args[0]))
/*     */       {
/*  72 */         throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
/*     */       }
/*     */       
/*  75 */       flag = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  80 */     if (flag) {
/*     */       
/*  82 */       if (args.length < 6)
/*     */       {
/*  84 */         throw new WrongUsageException("commands.replaceitem.block.usage", new Object[0]);
/*     */       }
/*     */       
/*  87 */       i = 4;
/*     */     }
/*     */     else {
/*     */       
/*  91 */       if (args.length < 4)
/*     */       {
/*  93 */         throw new WrongUsageException("commands.replaceitem.entity.usage", new Object[0]);
/*     */       }
/*     */       
/*  96 */       i = 2;
/*     */     } 
/*     */     
/*  99 */     String s = args[i];
/* 100 */     int j = getSlotForShortcut(args[i++]);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 105 */       item = getItemByText(sender, args[i]);
/*     */     }
/* 107 */     catch (NumberInvalidException numberinvalidexception) {
/*     */       
/* 109 */       if (Block.getBlockFromName(args[i]) != Blocks.AIR)
/*     */       {
/* 111 */         throw numberinvalidexception;
/*     */       }
/*     */       
/* 114 */       item = null;
/*     */     } 
/*     */     
/* 117 */     i++;
/* 118 */     int k = (args.length > i) ? parseInt(args[i++], 1, item.getItemStackLimit()) : 1;
/* 119 */     int l = (args.length > i) ? parseInt(args[i++]) : 0;
/* 120 */     ItemStack itemstack = new ItemStack(item, k, l);
/*     */     
/* 122 */     if (args.length > i) {
/*     */       
/* 124 */       String s1 = buildString(args, i);
/*     */ 
/*     */       
/*     */       try {
/* 128 */         itemstack.setTagCompound(JsonToNBT.getTagFromJson(s1));
/*     */       }
/* 130 */       catch (NBTException nbtexception) {
/*     */         
/* 132 */         throw new CommandException("commands.replaceitem.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     if (flag) {
/*     */       
/* 138 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/* 139 */       BlockPos blockpos = parseBlockPos(sender, args, 1, false);
/* 140 */       World world = sender.getEntityWorld();
/* 141 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 143 */       if (tileentity == null || !(tileentity instanceof IInventory))
/*     */       {
/* 145 */         throw new CommandException("commands.replaceitem.noContainer", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 148 */       IInventory iinventory = (IInventory)tileentity;
/*     */       
/* 150 */       if (j >= 0 && j < iinventory.getSizeInventory())
/*     */       {
/* 152 */         iinventory.setInventorySlotContents(j, itemstack);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 157 */       Entity entity = getEntity(server, sender, args[1]);
/* 158 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
/*     */       
/* 160 */       if (entity instanceof EntityPlayer)
/*     */       {
/* 162 */         ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
/*     */       }
/*     */       
/* 165 */       if (!entity.replaceItemInInventory(j, itemstack))
/*     */       {
/* 167 */         throw new CommandException("commands.replaceitem.failed", new Object[] { s, Integer.valueOf(k), itemstack.func_190926_b() ? "Air" : itemstack.getTextComponent() });
/*     */       }
/*     */       
/* 170 */       if (entity instanceof EntityPlayer)
/*     */       {
/* 172 */         ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
/*     */       }
/*     */     } 
/*     */     
/* 176 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, k);
/* 177 */     notifyCommandListener(sender, this, "commands.replaceitem.success", new Object[] { s, Integer.valueOf(k), itemstack.func_190926_b() ? "Air" : itemstack.getTextComponent() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getSlotForShortcut(String shortcut) throws CommandException {
/* 183 */     if (!SHORTCUTS.containsKey(shortcut))
/*     */     {
/* 185 */       throw new CommandException("commands.generic.parameter.invalid", new Object[] { shortcut });
/*     */     }
/*     */ 
/*     */     
/* 189 */     return ((Integer)SHORTCUTS.get(shortcut)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 195 */     if (args.length == 1)
/*     */     {
/* 197 */       return getListOfStringsMatchingLastWord(args, new String[] { "entity", "block" });
/*     */     }
/* 199 */     if (args.length == 2 && "entity".equals(args[0]))
/*     */     {
/* 201 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/* 203 */     if (args.length >= 2 && args.length <= 4 && "block".equals(args[0]))
/*     */     {
/* 205 */       return getTabCompletionCoordinate(args, 1, pos);
/*     */     }
/* 207 */     if ((args.length != 3 || !"entity".equals(args[0])) && (args.length != 5 || !"block".equals(args[0])))
/*     */     {
/* 209 */       return ((args.length != 4 || !"entity".equals(args[0])) && (args.length != 6 || !"block".equals(args[0]))) ? Collections.<String>emptyList() : getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys());
/*     */     }
/*     */ 
/*     */     
/* 213 */     return getListOfStringsMatchingLastWord(args, SHORTCUTS.keySet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 222 */     return (args.length > 0 && "entity".equals(args[0]) && index == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 227 */     for (int i = 0; i < 54; i++)
/*     */     {
/* 229 */       SHORTCUTS.put("slot.container." + i, Integer.valueOf(i));
/*     */     }
/*     */     
/* 232 */     for (int j = 0; j < 9; j++)
/*     */     {
/* 234 */       SHORTCUTS.put("slot.hotbar." + j, Integer.valueOf(j));
/*     */     }
/*     */     
/* 237 */     for (int k = 0; k < 27; k++)
/*     */     {
/* 239 */       SHORTCUTS.put("slot.inventory." + k, Integer.valueOf(9 + k));
/*     */     }
/*     */     
/* 242 */     for (int l = 0; l < 27; l++)
/*     */     {
/* 244 */       SHORTCUTS.put("slot.enderchest." + l, Integer.valueOf(200 + l));
/*     */     }
/*     */     
/* 247 */     for (int i1 = 0; i1 < 8; i1++)
/*     */     {
/* 249 */       SHORTCUTS.put("slot.villager." + i1, Integer.valueOf(300 + i1));
/*     */     }
/*     */     
/* 252 */     for (int j1 = 0; j1 < 15; j1++)
/*     */     {
/* 254 */       SHORTCUTS.put("slot.horse." + j1, Integer.valueOf(500 + j1));
/*     */     }
/*     */     
/* 257 */     SHORTCUTS.put("slot.weapon", Integer.valueOf(98));
/* 258 */     SHORTCUTS.put("slot.weapon.mainhand", Integer.valueOf(98));
/* 259 */     SHORTCUTS.put("slot.weapon.offhand", Integer.valueOf(99));
/* 260 */     SHORTCUTS.put("slot.armor.head", Integer.valueOf(100 + EntityEquipmentSlot.HEAD.getIndex()));
/* 261 */     SHORTCUTS.put("slot.armor.chest", Integer.valueOf(100 + EntityEquipmentSlot.CHEST.getIndex()));
/* 262 */     SHORTCUTS.put("slot.armor.legs", Integer.valueOf(100 + EntityEquipmentSlot.LEGS.getIndex()));
/* 263 */     SHORTCUTS.put("slot.armor.feet", Integer.valueOf(100 + EntityEquipmentSlot.FEET.getIndex()));
/* 264 */     SHORTCUTS.put("slot.horse.saddle", Integer.valueOf(400));
/* 265 */     SHORTCUTS.put("slot.horse.armor", Integer.valueOf(401));
/* 266 */     SHORTCUTS.put("slot.horse.chest", Integer.valueOf(499));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandReplaceItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */