/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.datafix.IFixableData;
/*     */ 
/*     */ public class SpawnEggNames implements IFixableData {
/*   8 */   private static final String[] ENTITY_IDS = new String[256];
/*     */ 
/*     */   
/*     */   public int getFixVersion() {
/*  12 */     return 105;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/*  17 */     if ("minecraft:spawn_egg".equals(compound.getString("id"))) {
/*     */       
/*  19 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("tag");
/*  20 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("EntityTag");
/*  21 */       short short1 = compound.getShort("Damage");
/*     */       
/*  23 */       if (!nbttagcompound1.hasKey("id", 8)) {
/*     */         
/*  25 */         String s = ENTITY_IDS[short1 & 0xFF];
/*     */         
/*  27 */         if (s != null) {
/*     */           
/*  29 */           nbttagcompound1.setString("id", s);
/*  30 */           nbttagcompound.setTag("EntityTag", (NBTBase)nbttagcompound1);
/*  31 */           compound.setTag("tag", (NBTBase)nbttagcompound);
/*     */         } 
/*     */       } 
/*     */       
/*  35 */       if (short1 != 0)
/*     */       {
/*  37 */         compound.setShort("Damage", (short)0);
/*     */       }
/*     */     } 
/*     */     
/*  41 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*  46 */     String[] astring = ENTITY_IDS;
/*  47 */     astring[1] = "Item";
/*  48 */     astring[2] = "XPOrb";
/*  49 */     astring[7] = "ThrownEgg";
/*  50 */     astring[8] = "LeashKnot";
/*  51 */     astring[9] = "Painting";
/*  52 */     astring[10] = "Arrow";
/*  53 */     astring[11] = "Snowball";
/*  54 */     astring[12] = "Fireball";
/*  55 */     astring[13] = "SmallFireball";
/*  56 */     astring[14] = "ThrownEnderpearl";
/*  57 */     astring[15] = "EyeOfEnderSignal";
/*  58 */     astring[16] = "ThrownPotion";
/*  59 */     astring[17] = "ThrownExpBottle";
/*  60 */     astring[18] = "ItemFrame";
/*  61 */     astring[19] = "WitherSkull";
/*  62 */     astring[20] = "PrimedTnt";
/*  63 */     astring[21] = "FallingSand";
/*  64 */     astring[22] = "FireworksRocketEntity";
/*  65 */     astring[23] = "TippedArrow";
/*  66 */     astring[24] = "SpectralArrow";
/*  67 */     astring[25] = "ShulkerBullet";
/*  68 */     astring[26] = "DragonFireball";
/*  69 */     astring[30] = "ArmorStand";
/*  70 */     astring[41] = "Boat";
/*  71 */     astring[42] = "MinecartRideable";
/*  72 */     astring[43] = "MinecartChest";
/*  73 */     astring[44] = "MinecartFurnace";
/*  74 */     astring[45] = "MinecartTNT";
/*  75 */     astring[46] = "MinecartHopper";
/*  76 */     astring[47] = "MinecartSpawner";
/*  77 */     astring[40] = "MinecartCommandBlock";
/*  78 */     astring[48] = "Mob";
/*  79 */     astring[49] = "Monster";
/*  80 */     astring[50] = "Creeper";
/*  81 */     astring[51] = "Skeleton";
/*  82 */     astring[52] = "Spider";
/*  83 */     astring[53] = "Giant";
/*  84 */     astring[54] = "Zombie";
/*  85 */     astring[55] = "Slime";
/*  86 */     astring[56] = "Ghast";
/*  87 */     astring[57] = "PigZombie";
/*  88 */     astring[58] = "Enderman";
/*  89 */     astring[59] = "CaveSpider";
/*  90 */     astring[60] = "Silverfish";
/*  91 */     astring[61] = "Blaze";
/*  92 */     astring[62] = "LavaSlime";
/*  93 */     astring[63] = "EnderDragon";
/*  94 */     astring[64] = "WitherBoss";
/*  95 */     astring[65] = "Bat";
/*  96 */     astring[66] = "Witch";
/*  97 */     astring[67] = "Endermite";
/*  98 */     astring[68] = "Guardian";
/*  99 */     astring[69] = "Shulker";
/* 100 */     astring[90] = "Pig";
/* 101 */     astring[91] = "Sheep";
/* 102 */     astring[92] = "Cow";
/* 103 */     astring[93] = "Chicken";
/* 104 */     astring[94] = "Squid";
/* 105 */     astring[95] = "Wolf";
/* 106 */     astring[96] = "MushroomCow";
/* 107 */     astring[97] = "SnowMan";
/* 108 */     astring[98] = "Ozelot";
/* 109 */     astring[99] = "VillagerGolem";
/* 110 */     astring[100] = "EntityHorse";
/* 111 */     astring[101] = "Rabbit";
/* 112 */     astring[120] = "Villager";
/* 113 */     astring[200] = "EnderCrystal";
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\SpawnEggNames.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */