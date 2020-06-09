/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class EntityHealth
/*    */   implements IFixableData {
/* 10 */   private static final Set<String> ENTITY_LIST = Sets.newHashSet((Object[])new String[] { "ArmorStand", "Bat", "Blaze", "CaveSpider", "Chicken", "Cow", "Creeper", "EnderDragon", "Enderman", "Endermite", "EntityHorse", "Ghast", "Giant", "Guardian", "LavaSlime", "MushroomCow", "Ozelot", "Pig", "PigZombie", "Rabbit", "Sheep", "Shulker", "Silverfish", "Skeleton", "Slime", "SnowMan", "Spider", "Squid", "Villager", "VillagerGolem", "Witch", "WitherBoss", "Wolf", "Zombie" });
/*    */ 
/*    */   
/*    */   public int getFixVersion() {
/* 14 */     return 109;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 19 */     if (ENTITY_LIST.contains(compound.getString("id"))) {
/*    */       float f;
/*    */ 
/*    */       
/* 23 */       if (compound.hasKey("HealF", 99)) {
/*    */         
/* 25 */         f = compound.getFloat("HealF");
/* 26 */         compound.removeTag("HealF");
/*    */       }
/*    */       else {
/*    */         
/* 30 */         if (!compound.hasKey("Health", 99))
/*    */         {
/* 32 */           return compound;
/*    */         }
/*    */         
/* 35 */         f = compound.getFloat("Health");
/*    */       } 
/*    */       
/* 38 */       compound.setFloat("Health", f);
/*    */     } 
/*    */     
/* 41 */     return compound;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\EntityHealth.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */