/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class TileEntityId
/*    */   implements IFixableData {
/* 10 */   private static final Map<String, String> field_191275_a = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   public int getFixVersion() {
/* 14 */     return 704;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 19 */     String s = field_191275_a.get(compound.getString("id"));
/*    */     
/* 21 */     if (s != null)
/*    */     {
/* 23 */       compound.setString("id", s);
/*    */     }
/*    */     
/* 26 */     return compound;
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 31 */     field_191275_a.put("Airportal", "minecraft:end_portal");
/* 32 */     field_191275_a.put("Banner", "minecraft:banner");
/* 33 */     field_191275_a.put("Beacon", "minecraft:beacon");
/* 34 */     field_191275_a.put("Cauldron", "minecraft:brewing_stand");
/* 35 */     field_191275_a.put("Chest", "minecraft:chest");
/* 36 */     field_191275_a.put("Comparator", "minecraft:comparator");
/* 37 */     field_191275_a.put("Control", "minecraft:command_block");
/* 38 */     field_191275_a.put("DLDetector", "minecraft:daylight_detector");
/* 39 */     field_191275_a.put("Dropper", "minecraft:dropper");
/* 40 */     field_191275_a.put("EnchantTable", "minecraft:enchanting_table");
/* 41 */     field_191275_a.put("EndGateway", "minecraft:end_gateway");
/* 42 */     field_191275_a.put("EnderChest", "minecraft:ender_chest");
/* 43 */     field_191275_a.put("FlowerPot", "minecraft:flower_pot");
/* 44 */     field_191275_a.put("Furnace", "minecraft:furnace");
/* 45 */     field_191275_a.put("Hopper", "minecraft:hopper");
/* 46 */     field_191275_a.put("MobSpawner", "minecraft:mob_spawner");
/* 47 */     field_191275_a.put("Music", "minecraft:noteblock");
/* 48 */     field_191275_a.put("Piston", "minecraft:piston");
/* 49 */     field_191275_a.put("RecordPlayer", "minecraft:jukebox");
/* 50 */     field_191275_a.put("Sign", "minecraft:sign");
/* 51 */     field_191275_a.put("Skull", "minecraft:skull");
/* 52 */     field_191275_a.put("Structure", "minecraft:structure_block");
/* 53 */     field_191275_a.put("Trap", "minecraft:dispenser");
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\TileEntityId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */