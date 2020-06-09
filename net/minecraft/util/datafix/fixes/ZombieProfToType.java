/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.datafix.IFixableData;
/*    */ 
/*    */ public class ZombieProfToType
/*    */   implements IFixableData {
/*  9 */   private static final Random RANDOM = new Random();
/*    */ 
/*    */   
/*    */   public int getFixVersion() {
/* 13 */     return 502;
/*    */   }
/*    */ 
/*    */   
/*    */   public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
/* 18 */     if ("Zombie".equals(compound.getString("id")) && compound.getBoolean("IsVillager")) {
/*    */       
/* 20 */       if (!compound.hasKey("ZombieType", 99)) {
/*    */         
/* 22 */         int i = -1;
/*    */         
/* 24 */         if (compound.hasKey("VillagerProfession", 99)) {
/*    */           
/*    */           try {
/*    */             
/* 28 */             i = func_191277_a(compound.getInteger("VillagerProfession"));
/*    */           }
/* 30 */           catch (RuntimeException runtimeException) {}
/*    */         }
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 36 */         if (i == -1)
/*    */         {
/* 38 */           i = func_191277_a(RANDOM.nextInt(6));
/*    */         }
/*    */         
/* 41 */         compound.setInteger("ZombieType", i);
/*    */       } 
/*    */       
/* 44 */       compound.removeTag("IsVillager");
/*    */     } 
/*    */     
/* 47 */     return compound;
/*    */   }
/*    */ 
/*    */   
/*    */   private int func_191277_a(int p_191277_1_) {
/* 52 */     return (p_191277_1_ >= 0 && p_191277_1_ < 6) ? p_191277_1_ : -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\fixes\ZombieProfToType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */