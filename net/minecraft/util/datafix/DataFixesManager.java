/*     */ package net.minecraft.util.datafix;
/*     */ 
/*     */ import net.minecraft.block.BlockJukebox;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityEnderPearl;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecartChest;
/*     */ import net.minecraft.entity.item.EntityMinecartCommandBlock;
/*     */ import net.minecraft.entity.item.EntityMinecartEmpty;
/*     */ import net.minecraft.entity.item.EntityMinecartFurnace;
/*     */ import net.minecraft.entity.item.EntityMinecartHopper;
/*     */ import net.minecraft.entity.item.EntityMinecartMobSpawner;
/*     */ import net.minecraft.entity.item.EntityMinecartTNT;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityElderGuardian;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityEndermite;
/*     */ import net.minecraft.entity.monster.EntityEvoker;
/*     */ import net.minecraft.entity.monster.EntityGhast;
/*     */ import net.minecraft.entity.monster.EntityGiantZombie;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.entity.monster.EntityHusk;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntityShulker;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySnowman;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityStray;
/*     */ import net.minecraft.entity.monster.EntityVex;
/*     */ import net.minecraft.entity.monster.EntityVindicator;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityWitherSkeleton;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.monster.EntityZombieVillager;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityDonkey;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityMooshroom;
/*     */ import net.minecraft.entity.passive.EntityMule;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntitySkeletonHorse;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.passive.EntityZombieHorse;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityDragonFireball;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.entity.projectile.EntitySpectralArrow;
/*     */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.tileentity.TileEntityDropper;
/*     */ import net.minecraft.tileentity.TileEntityFlowerPot;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ import net.minecraft.tileentity.TileEntityHopper;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*     */ import net.minecraft.util.datafix.fixes.AddBedTileEntity;
/*     */ import net.minecraft.util.datafix.fixes.ArmorStandSilent;
/*     */ import net.minecraft.util.datafix.fixes.BannerItemColor;
/*     */ import net.minecraft.util.datafix.fixes.BedItemColor;
/*     */ import net.minecraft.util.datafix.fixes.BookPagesStrictJSON;
/*     */ import net.minecraft.util.datafix.fixes.CookedFishIDTypo;
/*     */ import net.minecraft.util.datafix.fixes.ElderGuardianSplit;
/*     */ import net.minecraft.util.datafix.fixes.EntityArmorAndHeld;
/*     */ import net.minecraft.util.datafix.fixes.EntityHealth;
/*     */ import net.minecraft.util.datafix.fixes.EntityId;
/*     */ import net.minecraft.util.datafix.fixes.ForceVBOOn;
/*     */ import net.minecraft.util.datafix.fixes.HorseSaddle;
/*     */ import net.minecraft.util.datafix.fixes.HorseSplit;
/*     */ import net.minecraft.util.datafix.fixes.ItemIntIDToString;
/*     */ import net.minecraft.util.datafix.fixes.MinecartEntityTypes;
/*     */ import net.minecraft.util.datafix.fixes.OptionsLowerCaseLanguage;
/*     */ import net.minecraft.util.datafix.fixes.PaintingDirection;
/*     */ import net.minecraft.util.datafix.fixes.PotionItems;
/*     */ import net.minecraft.util.datafix.fixes.PotionWater;
/*     */ import net.minecraft.util.datafix.fixes.RedundantChanceTags;
/*     */ import net.minecraft.util.datafix.fixes.RidingToPassengers;
/*     */ import net.minecraft.util.datafix.fixes.ShulkerBoxEntityColor;
/*     */ import net.minecraft.util.datafix.fixes.ShulkerBoxItemColor;
/*     */ import net.minecraft.util.datafix.fixes.ShulkerBoxTileColor;
/*     */ import net.minecraft.util.datafix.fixes.SignStrictJSON;
/*     */ import net.minecraft.util.datafix.fixes.SkeletonSplit;
/*     */ import net.minecraft.util.datafix.fixes.SpawnEggNames;
/*     */ import net.minecraft.util.datafix.fixes.SpawnerEntityTypes;
/*     */ import net.minecraft.util.datafix.fixes.StringToUUID;
/*     */ import net.minecraft.util.datafix.fixes.TileEntityId;
/*     */ import net.minecraft.util.datafix.fixes.TotemItemRename;
/*     */ import net.minecraft.util.datafix.fixes.ZombieProfToType;
/*     */ import net.minecraft.util.datafix.fixes.ZombieSplit;
/*     */ import net.minecraft.world.chunk.storage.AnvilChunkLoader;
/*     */ import net.minecraft.world.gen.structure.template.Template;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ 
/*     */ public class DataFixesManager
/*     */ {
/*     */   private static void registerFixes(DataFixer fixer) {
/* 130 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new EntityArmorAndHeld());
/* 131 */     fixer.registerFix(FixTypes.BLOCK_ENTITY, (IFixableData)new SignStrictJSON());
/* 132 */     fixer.registerFix(FixTypes.ITEM_INSTANCE, (IFixableData)new ItemIntIDToString());
/* 133 */     fixer.registerFix(FixTypes.ITEM_INSTANCE, (IFixableData)new PotionItems());
/* 134 */     fixer.registerFix(FixTypes.ITEM_INSTANCE, (IFixableData)new SpawnEggNames());
/* 135 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new MinecartEntityTypes());
/* 136 */     fixer.registerFix(FixTypes.BLOCK_ENTITY, (IFixableData)new SpawnerEntityTypes());
/* 137 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new StringToUUID());
/* 138 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new EntityHealth());
/* 139 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new HorseSaddle());
/* 140 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new PaintingDirection());
/* 141 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new RedundantChanceTags());
/* 142 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new RidingToPassengers());
/* 143 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new ArmorStandSilent());
/* 144 */     fixer.registerFix(FixTypes.ITEM_INSTANCE, (IFixableData)new BookPagesStrictJSON());
/* 145 */     fixer.registerFix(FixTypes.ITEM_INSTANCE, (IFixableData)new CookedFishIDTypo());
/* 146 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new ZombieProfToType());
/* 147 */     fixer.registerFix(FixTypes.OPTIONS, (IFixableData)new ForceVBOOn());
/* 148 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new ElderGuardianSplit());
/* 149 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new SkeletonSplit());
/* 150 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new ZombieSplit());
/* 151 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new HorseSplit());
/* 152 */     fixer.registerFix(FixTypes.BLOCK_ENTITY, (IFixableData)new TileEntityId());
/* 153 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new EntityId());
/* 154 */     fixer.registerFix(FixTypes.ITEM_INSTANCE, (IFixableData)new BannerItemColor());
/* 155 */     fixer.registerFix(FixTypes.ITEM_INSTANCE, (IFixableData)new PotionWater());
/* 156 */     fixer.registerFix(FixTypes.ENTITY, (IFixableData)new ShulkerBoxEntityColor());
/* 157 */     fixer.registerFix(FixTypes.ITEM_INSTANCE, (IFixableData)new ShulkerBoxItemColor());
/* 158 */     fixer.registerFix(FixTypes.BLOCK_ENTITY, (IFixableData)new ShulkerBoxTileColor());
/* 159 */     fixer.registerFix(FixTypes.OPTIONS, (IFixableData)new OptionsLowerCaseLanguage());
/* 160 */     fixer.registerFix(FixTypes.ITEM_INSTANCE, (IFixableData)new TotemItemRename());
/* 161 */     fixer.registerFix(FixTypes.CHUNK, (IFixableData)new AddBedTileEntity());
/* 162 */     fixer.registerFix(FixTypes.ITEM_INSTANCE, (IFixableData)new BedItemColor());
/*     */   }
/*     */ 
/*     */   
/*     */   public static DataFixer createFixer() {
/* 167 */     DataFixer datafixer = new DataFixer(1343);
/* 168 */     WorldInfo.registerFixes(datafixer);
/* 169 */     EntityPlayerMP.func_191522_a(datafixer);
/* 170 */     EntityPlayer.registerFixesPlayer(datafixer);
/* 171 */     AnvilChunkLoader.registerFixes(datafixer);
/* 172 */     ItemStack.registerFixes(datafixer);
/* 173 */     Template.func_191158_a(datafixer);
/* 174 */     Entity.func_190533_a(datafixer);
/* 175 */     EntityArmorStand.registerFixesArmorStand(datafixer);
/* 176 */     EntityArrow.registerFixesArrow(datafixer);
/* 177 */     EntityBat.registerFixesBat(datafixer);
/* 178 */     EntityBlaze.registerFixesBlaze(datafixer);
/* 179 */     EntityCaveSpider.registerFixesCaveSpider(datafixer);
/* 180 */     EntityChicken.registerFixesChicken(datafixer);
/* 181 */     EntityCow.registerFixesCow(datafixer);
/* 182 */     EntityCreeper.registerFixesCreeper(datafixer);
/* 183 */     EntityDonkey.func_190699_b(datafixer);
/* 184 */     EntityDragonFireball.registerFixesDragonFireball(datafixer);
/* 185 */     EntityElderGuardian.func_190768_b(datafixer);
/* 186 */     EntityDragon.registerFixesDragon(datafixer);
/* 187 */     EntityEnderman.registerFixesEnderman(datafixer);
/* 188 */     EntityEndermite.registerFixesEndermite(datafixer);
/* 189 */     EntityEvoker.func_190759_b(datafixer);
/* 190 */     EntityFallingBlock.registerFixesFallingBlock(datafixer);
/* 191 */     EntityFireworkRocket.registerFixesFireworkRocket(datafixer);
/* 192 */     EntityGhast.registerFixesGhast(datafixer);
/* 193 */     EntityGiantZombie.registerFixesGiantZombie(datafixer);
/* 194 */     EntityGuardian.registerFixesGuardian(datafixer);
/* 195 */     EntityHorse.registerFixesHorse(datafixer);
/* 196 */     EntityHusk.func_190740_b(datafixer);
/* 197 */     EntityItem.registerFixesItem(datafixer);
/* 198 */     EntityItemFrame.registerFixesItemFrame(datafixer);
/* 199 */     EntityLargeFireball.registerFixesLargeFireball(datafixer);
/* 200 */     EntityMagmaCube.registerFixesMagmaCube(datafixer);
/* 201 */     EntityMinecartChest.registerFixesMinecartChest(datafixer);
/* 202 */     EntityMinecartCommandBlock.registerFixesMinecartCommand(datafixer);
/* 203 */     EntityMinecartFurnace.registerFixesMinecartFurnace(datafixer);
/* 204 */     EntityMinecartHopper.registerFixesMinecartHopper(datafixer);
/* 205 */     EntityMinecartEmpty.registerFixesMinecartEmpty(datafixer);
/* 206 */     EntityMinecartMobSpawner.registerFixesMinecartMobSpawner(datafixer);
/* 207 */     EntityMinecartTNT.registerFixesMinecartTNT(datafixer);
/* 208 */     EntityMule.func_190700_b(datafixer);
/* 209 */     EntityMooshroom.registerFixesMooshroom(datafixer);
/* 210 */     EntityOcelot.registerFixesOcelot(datafixer);
/* 211 */     EntityPig.registerFixesPig(datafixer);
/* 212 */     EntityPigZombie.registerFixesPigZombie(datafixer);
/* 213 */     EntityRabbit.registerFixesRabbit(datafixer);
/* 214 */     EntitySheep.registerFixesSheep(datafixer);
/* 215 */     EntityShulker.registerFixesShulker(datafixer);
/* 216 */     EntitySilverfish.registerFixesSilverfish(datafixer);
/* 217 */     EntitySkeleton.registerFixesSkeleton(datafixer);
/* 218 */     EntitySkeletonHorse.func_190692_b(datafixer);
/* 219 */     EntitySlime.registerFixesSlime(datafixer);
/* 220 */     EntitySmallFireball.registerFixesSmallFireball(datafixer);
/* 221 */     EntitySnowman.registerFixesSnowman(datafixer);
/* 222 */     EntitySnowball.registerFixesSnowball(datafixer);
/* 223 */     EntitySpectralArrow.registerFixesSpectralArrow(datafixer);
/* 224 */     EntitySpider.registerFixesSpider(datafixer);
/* 225 */     EntitySquid.registerFixesSquid(datafixer);
/* 226 */     EntityStray.func_190728_b(datafixer);
/* 227 */     EntityEgg.registerFixesEgg(datafixer);
/* 228 */     EntityEnderPearl.registerFixesEnderPearl(datafixer);
/* 229 */     EntityExpBottle.registerFixesExpBottle(datafixer);
/* 230 */     EntityPotion.registerFixesPotion(datafixer);
/* 231 */     EntityTippedArrow.registerFixesTippedArrow(datafixer);
/* 232 */     EntityVex.func_190663_b(datafixer);
/* 233 */     EntityVillager.registerFixesVillager(datafixer);
/* 234 */     EntityIronGolem.registerFixesIronGolem(datafixer);
/* 235 */     EntityVindicator.func_190641_b(datafixer);
/* 236 */     EntityWitch.registerFixesWitch(datafixer);
/* 237 */     EntityWither.registerFixesWither(datafixer);
/* 238 */     EntityWitherSkeleton.func_190729_b(datafixer);
/* 239 */     EntityWitherSkull.registerFixesWitherSkull(datafixer);
/* 240 */     EntityWolf.registerFixesWolf(datafixer);
/* 241 */     EntityZombie.registerFixesZombie(datafixer);
/* 242 */     EntityZombieHorse.func_190693_b(datafixer);
/* 243 */     EntityZombieVillager.func_190737_b(datafixer);
/* 244 */     TileEntityPiston.registerFixesPiston(datafixer);
/* 245 */     TileEntityFlowerPot.registerFixesFlowerPot(datafixer);
/* 246 */     TileEntityFurnace.registerFixesFurnace(datafixer);
/* 247 */     TileEntityChest.registerFixesChest(datafixer);
/* 248 */     TileEntityDispenser.registerFixes(datafixer);
/* 249 */     TileEntityDropper.registerFixesDropper(datafixer);
/* 250 */     TileEntityBrewingStand.registerFixesBrewingStand(datafixer);
/* 251 */     TileEntityHopper.registerFixesHopper(datafixer);
/* 252 */     BlockJukebox.registerFixesJukebox(datafixer);
/* 253 */     TileEntityMobSpawner.registerFixesMobSpawner(datafixer);
/* 254 */     TileEntityShulkerBox.func_190593_a(datafixer);
/* 255 */     registerFixes(datafixer);
/* 256 */     return datafixer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static NBTTagCompound processItemStack(IDataFixer fixer, NBTTagCompound compound, int version, String key) {
/* 261 */     if (compound.hasKey(key, 10))
/*     */     {
/* 263 */       compound.setTag(key, (NBTBase)fixer.process(FixTypes.ITEM_INSTANCE, compound.getCompoundTag(key), version));
/*     */     }
/*     */     
/* 266 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public static NBTTagCompound processInventory(IDataFixer fixer, NBTTagCompound compound, int version, String key) {
/* 271 */     if (compound.hasKey(key, 9)) {
/*     */       
/* 273 */       NBTTagList nbttaglist = compound.getTagList(key, 10);
/*     */       
/* 275 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */       {
/* 277 */         nbttaglist.set(i, (NBTBase)fixer.process(FixTypes.ITEM_INSTANCE, nbttaglist.getCompoundTagAt(i), version));
/*     */       }
/*     */     } 
/*     */     
/* 281 */     return compound;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\DataFixesManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */