/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public enum SoundCategory
/*    */ {
/*  9 */   MASTER("master"),
/* 10 */   MUSIC("music"),
/* 11 */   RECORDS("record"),
/* 12 */   WEATHER("weather"),
/* 13 */   BLOCKS("block"),
/* 14 */   HOSTILE("hostile"),
/* 15 */   NEUTRAL("neutral"),
/* 16 */   PLAYERS("player"),
/* 17 */   AMBIENT("ambient"),
/* 18 */   VOICE("voice");
/*    */   static {
/* 20 */     SOUND_CATEGORIES = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     byte b;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     int i;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     SoundCategory[] arrayOfSoundCategory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     for (i = (arrayOfSoundCategory = values()).length, b = 0; b < i; ) { SoundCategory soundcategory = arrayOfSoundCategory[b];
/*    */       
/* 46 */       if (SOUND_CATEGORIES.containsKey(soundcategory.getName()))
/*    */       {
/* 48 */         throw new Error("Clash in Sound Category name pools! Cannot insert " + soundcategory);
/*    */       }
/*    */       
/* 51 */       SOUND_CATEGORIES.put(soundcategory.getName(), soundcategory);
/*    */       b++; }
/*    */   
/*    */   }
/*    */   
/*    */   private static final Map<String, SoundCategory> SOUND_CATEGORIES;
/*    */   private final String name;
/*    */   
/*    */   SoundCategory(String nameIn) {
/*    */     this.name = nameIn;
/*    */   }
/*    */   
/*    */   public String getName() {
/*    */     return this.name;
/*    */   }
/*    */   
/*    */   public static SoundCategory getByName(String categoryName) {
/*    */     return SOUND_CATEGORIES.get(categoryName);
/*    */   }
/*    */   
/*    */   public static Set<String> getSoundCategoryNames() {
/*    */     return SOUND_CATEGORIES.keySet();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\SoundCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */