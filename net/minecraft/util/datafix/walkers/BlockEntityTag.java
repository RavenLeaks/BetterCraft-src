/*     */ package net.minecraft.util.datafix.walkers;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataFixer;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.IFixType;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class BlockEntityTag implements IDataWalker {
/*  16 */   private static final Logger LOGGER = LogManager.getLogger();
/*  17 */   private static final Map<String, String> field_190892_b = Maps.newHashMap();
/*  18 */   private static final Map<String, String> ITEM_ID_TO_BLOCK_ENTITY_ID = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static String getBlockEntityID(int blockID, String p_188267_1_) {
/*  23 */     return (blockID < 515) ? field_190892_b.get((new ResourceLocation(p_188267_1_)).toString()) : ITEM_ID_TO_BLOCK_ENTITY_ID.get((new ResourceLocation(p_188267_1_)).toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
/*  28 */     if (!compound.hasKey("tag", 10))
/*     */     {
/*  30 */       return compound;
/*     */     }
/*     */ 
/*     */     
/*  34 */     NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
/*     */     
/*  36 */     if (nbttagcompound.hasKey("BlockEntityTag", 10)) {
/*     */       boolean flag;
/*  38 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("BlockEntityTag");
/*  39 */       String s = compound.getString("id");
/*  40 */       String s1 = getBlockEntityID(versionIn, s);
/*     */ 
/*     */       
/*  43 */       if (s1 == null) {
/*     */         
/*  45 */         LOGGER.warn("Unable to resolve BlockEntity for ItemInstance: {}", s);
/*  46 */         flag = false;
/*     */       }
/*     */       else {
/*     */         
/*  50 */         flag = !nbttagcompound1.hasKey("id");
/*  51 */         nbttagcompound1.setString("id", s1);
/*     */       } 
/*     */       
/*  54 */       fixer.process((IFixType)FixTypes.BLOCK_ENTITY, nbttagcompound1, versionIn);
/*     */       
/*  56 */       if (flag)
/*     */       {
/*  58 */         nbttagcompound1.removeTag("id");
/*     */       }
/*     */     } 
/*     */     
/*  62 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  68 */     Map<String, String> map = field_190892_b;
/*  69 */     map.put("minecraft:furnace", "Furnace");
/*  70 */     map.put("minecraft:lit_furnace", "Furnace");
/*  71 */     map.put("minecraft:chest", "Chest");
/*  72 */     map.put("minecraft:trapped_chest", "Chest");
/*  73 */     map.put("minecraft:ender_chest", "EnderChest");
/*  74 */     map.put("minecraft:jukebox", "RecordPlayer");
/*  75 */     map.put("minecraft:dispenser", "Trap");
/*  76 */     map.put("minecraft:dropper", "Dropper");
/*  77 */     map.put("minecraft:sign", "Sign");
/*  78 */     map.put("minecraft:mob_spawner", "MobSpawner");
/*  79 */     map.put("minecraft:noteblock", "Music");
/*  80 */     map.put("minecraft:brewing_stand", "Cauldron");
/*  81 */     map.put("minecraft:enhanting_table", "EnchantTable");
/*  82 */     map.put("minecraft:command_block", "CommandBlock");
/*  83 */     map.put("minecraft:beacon", "Beacon");
/*  84 */     map.put("minecraft:skull", "Skull");
/*  85 */     map.put("minecraft:daylight_detector", "DLDetector");
/*  86 */     map.put("minecraft:hopper", "Hopper");
/*  87 */     map.put("minecraft:banner", "Banner");
/*  88 */     map.put("minecraft:flower_pot", "FlowerPot");
/*  89 */     map.put("minecraft:repeating_command_block", "CommandBlock");
/*  90 */     map.put("minecraft:chain_command_block", "CommandBlock");
/*  91 */     map.put("minecraft:standing_sign", "Sign");
/*  92 */     map.put("minecraft:wall_sign", "Sign");
/*  93 */     map.put("minecraft:piston_head", "Piston");
/*  94 */     map.put("minecraft:daylight_detector_inverted", "DLDetector");
/*  95 */     map.put("minecraft:unpowered_comparator", "Comparator");
/*  96 */     map.put("minecraft:powered_comparator", "Comparator");
/*  97 */     map.put("minecraft:wall_banner", "Banner");
/*  98 */     map.put("minecraft:standing_banner", "Banner");
/*  99 */     map.put("minecraft:structure_block", "Structure");
/* 100 */     map.put("minecraft:end_portal", "Airportal");
/* 101 */     map.put("minecraft:end_gateway", "EndGateway");
/* 102 */     map.put("minecraft:shield", "Shield");
/* 103 */     map = ITEM_ID_TO_BLOCK_ENTITY_ID;
/* 104 */     map.put("minecraft:furnace", "minecraft:furnace");
/* 105 */     map.put("minecraft:lit_furnace", "minecraft:furnace");
/* 106 */     map.put("minecraft:chest", "minecraft:chest");
/* 107 */     map.put("minecraft:trapped_chest", "minecraft:chest");
/* 108 */     map.put("minecraft:ender_chest", "minecraft:enderchest");
/* 109 */     map.put("minecraft:jukebox", "minecraft:jukebox");
/* 110 */     map.put("minecraft:dispenser", "minecraft:dispenser");
/* 111 */     map.put("minecraft:dropper", "minecraft:dropper");
/* 112 */     map.put("minecraft:sign", "minecraft:sign");
/* 113 */     map.put("minecraft:mob_spawner", "minecraft:mob_spawner");
/* 114 */     map.put("minecraft:noteblock", "minecraft:noteblock");
/* 115 */     map.put("minecraft:brewing_stand", "minecraft:brewing_stand");
/* 116 */     map.put("minecraft:enhanting_table", "minecraft:enchanting_table");
/* 117 */     map.put("minecraft:command_block", "minecraft:command_block");
/* 118 */     map.put("minecraft:beacon", "minecraft:beacon");
/* 119 */     map.put("minecraft:skull", "minecraft:skull");
/* 120 */     map.put("minecraft:daylight_detector", "minecraft:daylight_detector");
/* 121 */     map.put("minecraft:hopper", "minecraft:hopper");
/* 122 */     map.put("minecraft:banner", "minecraft:banner");
/* 123 */     map.put("minecraft:flower_pot", "minecraft:flower_pot");
/* 124 */     map.put("minecraft:repeating_command_block", "minecraft:command_block");
/* 125 */     map.put("minecraft:chain_command_block", "minecraft:command_block");
/* 126 */     map.put("minecraft:shulker_box", "minecraft:shulker_box");
/* 127 */     map.put("minecraft:white_shulker_box", "minecraft:shulker_box");
/* 128 */     map.put("minecraft:orange_shulker_box", "minecraft:shulker_box");
/* 129 */     map.put("minecraft:magenta_shulker_box", "minecraft:shulker_box");
/* 130 */     map.put("minecraft:light_blue_shulker_box", "minecraft:shulker_box");
/* 131 */     map.put("minecraft:yellow_shulker_box", "minecraft:shulker_box");
/* 132 */     map.put("minecraft:lime_shulker_box", "minecraft:shulker_box");
/* 133 */     map.put("minecraft:pink_shulker_box", "minecraft:shulker_box");
/* 134 */     map.put("minecraft:gray_shulker_box", "minecraft:shulker_box");
/* 135 */     map.put("minecraft:silver_shulker_box", "minecraft:shulker_box");
/* 136 */     map.put("minecraft:cyan_shulker_box", "minecraft:shulker_box");
/* 137 */     map.put("minecraft:purple_shulker_box", "minecraft:shulker_box");
/* 138 */     map.put("minecraft:blue_shulker_box", "minecraft:shulker_box");
/* 139 */     map.put("minecraft:brown_shulker_box", "minecraft:shulker_box");
/* 140 */     map.put("minecraft:green_shulker_box", "minecraft:shulker_box");
/* 141 */     map.put("minecraft:red_shulker_box", "minecraft:shulker_box");
/* 142 */     map.put("minecraft:black_shulker_box", "minecraft:shulker_box");
/* 143 */     map.put("minecraft:bed", "minecraft:bed");
/* 144 */     map.put("minecraft:standing_sign", "minecraft:sign");
/* 145 */     map.put("minecraft:wall_sign", "minecraft:sign");
/* 146 */     map.put("minecraft:piston_head", "minecraft:piston");
/* 147 */     map.put("minecraft:daylight_detector_inverted", "minecraft:daylight_detector");
/* 148 */     map.put("minecraft:unpowered_comparator", "minecraft:comparator");
/* 149 */     map.put("minecraft:powered_comparator", "minecraft:comparator");
/* 150 */     map.put("minecraft:wall_banner", "minecraft:banner");
/* 151 */     map.put("minecraft:standing_banner", "minecraft:banner");
/* 152 */     map.put("minecraft:structure_block", "minecraft:structure_block");
/* 153 */     map.put("minecraft:end_portal", "minecraft:end_portal");
/* 154 */     map.put("minecraft:end_gateway", "minecraft:end_gateway");
/* 155 */     map.put("minecraft:shield", "minecraft:shield");
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\walkers\BlockEntityTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */