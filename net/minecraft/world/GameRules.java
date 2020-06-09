/*     */ package net.minecraft.world;
/*     */ 
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ public class GameRules
/*     */ {
/*   9 */   private final TreeMap<String, Value> theGameRules = new TreeMap<>();
/*     */ 
/*     */   
/*     */   public GameRules() {
/*  13 */     addGameRule("doFireTick", "true", ValueType.BOOLEAN_VALUE);
/*  14 */     addGameRule("mobGriefing", "true", ValueType.BOOLEAN_VALUE);
/*  15 */     addGameRule("keepInventory", "false", ValueType.BOOLEAN_VALUE);
/*  16 */     addGameRule("doMobSpawning", "true", ValueType.BOOLEAN_VALUE);
/*  17 */     addGameRule("doMobLoot", "true", ValueType.BOOLEAN_VALUE);
/*  18 */     addGameRule("doTileDrops", "true", ValueType.BOOLEAN_VALUE);
/*  19 */     addGameRule("doEntityDrops", "true", ValueType.BOOLEAN_VALUE);
/*  20 */     addGameRule("commandBlockOutput", "true", ValueType.BOOLEAN_VALUE);
/*  21 */     addGameRule("naturalRegeneration", "true", ValueType.BOOLEAN_VALUE);
/*  22 */     addGameRule("doDaylightCycle", "true", ValueType.BOOLEAN_VALUE);
/*  23 */     addGameRule("logAdminCommands", "true", ValueType.BOOLEAN_VALUE);
/*  24 */     addGameRule("showDeathMessages", "true", ValueType.BOOLEAN_VALUE);
/*  25 */     addGameRule("randomTickSpeed", "3", ValueType.NUMERICAL_VALUE);
/*  26 */     addGameRule("sendCommandFeedback", "true", ValueType.BOOLEAN_VALUE);
/*  27 */     addGameRule("reducedDebugInfo", "false", ValueType.BOOLEAN_VALUE);
/*  28 */     addGameRule("spectatorsGenerateChunks", "true", ValueType.BOOLEAN_VALUE);
/*  29 */     addGameRule("spawnRadius", "10", ValueType.NUMERICAL_VALUE);
/*  30 */     addGameRule("disableElytraMovementCheck", "false", ValueType.BOOLEAN_VALUE);
/*  31 */     addGameRule("maxEntityCramming", "24", ValueType.NUMERICAL_VALUE);
/*  32 */     addGameRule("doWeatherCycle", "true", ValueType.BOOLEAN_VALUE);
/*  33 */     addGameRule("doLimitedCrafting", "false", ValueType.BOOLEAN_VALUE);
/*  34 */     addGameRule("maxCommandChainLength", "65536", ValueType.NUMERICAL_VALUE);
/*  35 */     addGameRule("announceAdvancements", "true", ValueType.BOOLEAN_VALUE);
/*  36 */     addGameRule("gameLoopFunction", "-", ValueType.FUNCTION);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addGameRule(String key, String value, ValueType type) {
/*  41 */     this.theGameRules.put(key, new Value(value, type));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOrCreateGameRule(String key, String ruleValue) {
/*  46 */     Value gamerules$value = this.theGameRules.get(key);
/*     */     
/*  48 */     if (gamerules$value != null) {
/*     */       
/*  50 */       gamerules$value.setValue(ruleValue);
/*     */     }
/*     */     else {
/*     */       
/*  54 */       addGameRule(key, ruleValue, ValueType.ANY_VALUE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String name) {
/*  63 */     Value gamerules$value = this.theGameRules.get(name);
/*  64 */     return (gamerules$value != null) ? gamerules$value.getString() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String name) {
/*  72 */     Value gamerules$value = this.theGameRules.get(name);
/*  73 */     return (gamerules$value != null) ? gamerules$value.getBoolean() : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(String name) {
/*  78 */     Value gamerules$value = this.theGameRules.get(name);
/*  79 */     return (gamerules$value != null) ? gamerules$value.getInt() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT() {
/*  87 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/*  89 */     for (String s : this.theGameRules.keySet()) {
/*     */       
/*  91 */       Value gamerules$value = this.theGameRules.get(s);
/*  92 */       nbttagcompound.setString(s, gamerules$value.getString());
/*     */     } 
/*     */     
/*  95 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 103 */     for (String s : nbt.getKeySet())
/*     */     {
/* 105 */       setOrCreateGameRule(s, nbt.getString(s));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRules() {
/* 114 */     Set<String> set = this.theGameRules.keySet();
/* 115 */     return set.<String>toArray(new String[set.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRule(String name) {
/* 123 */     return this.theGameRules.containsKey(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean areSameType(String key, ValueType otherValue) {
/* 128 */     Value gamerules$value = this.theGameRules.get(key);
/* 129 */     return (gamerules$value != null && (gamerules$value.getType() == otherValue || otherValue == ValueType.ANY_VALUE));
/*     */   }
/*     */ 
/*     */   
/*     */   static class Value
/*     */   {
/*     */     private String valueString;
/*     */     private boolean valueBoolean;
/*     */     private int valueInteger;
/*     */     private double valueDouble;
/*     */     private final GameRules.ValueType type;
/*     */     
/*     */     public Value(String value, GameRules.ValueType type) {
/* 142 */       this.type = type;
/* 143 */       setValue(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(String value) {
/* 148 */       this.valueString = value;
/*     */       
/* 150 */       if (value != null) {
/*     */         
/* 152 */         if (value.equals("false")) {
/*     */           
/* 154 */           this.valueBoolean = false;
/*     */           
/*     */           return;
/*     */         } 
/* 158 */         if (value.equals("true")) {
/*     */           
/* 160 */           this.valueBoolean = true;
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 165 */       this.valueBoolean = Boolean.parseBoolean(value);
/* 166 */       this.valueInteger = this.valueBoolean ? 1 : 0;
/*     */ 
/*     */       
/*     */       try {
/* 170 */         this.valueInteger = Integer.parseInt(value);
/*     */       }
/* 172 */       catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 179 */         this.valueDouble = Double.parseDouble(value);
/*     */       }
/* 181 */       catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getString() {
/* 189 */       return this.valueString;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getBoolean() {
/* 194 */       return this.valueBoolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getInt() {
/* 199 */       return this.valueInteger;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameRules.ValueType getType() {
/* 204 */       return this.type;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ValueType
/*     */   {
/* 210 */     ANY_VALUE,
/* 211 */     BOOLEAN_VALUE,
/* 212 */     NUMERICAL_VALUE,
/* 213 */     FUNCTION;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\GameRules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */