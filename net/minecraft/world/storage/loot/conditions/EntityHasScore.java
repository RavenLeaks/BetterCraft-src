/*     */ package net.minecraft.world.storage.loot.conditions;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.storage.loot.LootContext;
/*     */ import net.minecraft.world.storage.loot.RandomValueRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityHasScore
/*     */   implements LootCondition
/*     */ {
/*     */   private final Map<String, RandomValueRange> scores;
/*     */   private final LootContext.EntityTarget target;
/*     */   
/*     */   public EntityHasScore(Map<String, RandomValueRange> scoreIn, LootContext.EntityTarget targetIn) {
/*  28 */     this.scores = scoreIn;
/*  29 */     this.target = targetIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean testCondition(Random rand, LootContext context) {
/*  34 */     Entity entity = context.getEntity(this.target);
/*     */     
/*  36 */     if (entity == null)
/*     */     {
/*  38 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  42 */     Scoreboard scoreboard = entity.world.getScoreboard();
/*     */     
/*  44 */     for (Map.Entry<String, RandomValueRange> entry : this.scores.entrySet()) {
/*     */       
/*  46 */       if (!entityScoreMatch(entity, scoreboard, entry.getKey(), entry.getValue()))
/*     */       {
/*  48 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean entityScoreMatch(Entity entityIn, Scoreboard scoreboardIn, String objectiveStr, RandomValueRange rand) {
/*  58 */     ScoreObjective scoreobjective = scoreboardIn.getObjective(objectiveStr);
/*     */     
/*  60 */     if (scoreobjective == null)
/*     */     {
/*  62 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  66 */     String s = (entityIn instanceof net.minecraft.entity.player.EntityPlayerMP) ? entityIn.getName() : entityIn.getCachedUniqueIdString();
/*  67 */     return !scoreboardIn.entityHasObjective(s, scoreobjective) ? false : rand.isInRange(scoreboardIn.getOrCreateScore(s, scoreobjective).getScorePoints());
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Serializer
/*     */     extends LootCondition.Serializer<EntityHasScore>
/*     */   {
/*     */     protected Serializer() {
/*  75 */       super(new ResourceLocation("entity_scores"), EntityHasScore.class);
/*     */     }
/*     */ 
/*     */     
/*     */     public void serialize(JsonObject json, EntityHasScore value, JsonSerializationContext context) {
/*  80 */       JsonObject jsonobject = new JsonObject();
/*     */       
/*  82 */       for (Map.Entry<String, RandomValueRange> entry : (Iterable<Map.Entry<String, RandomValueRange>>)value.scores.entrySet())
/*     */       {
/*  84 */         jsonobject.add(entry.getKey(), context.serialize(entry.getValue()));
/*     */       }
/*     */       
/*  87 */       json.add("scores", (JsonElement)jsonobject);
/*  88 */       json.add("entity", context.serialize(value.target));
/*     */     }
/*     */ 
/*     */     
/*     */     public EntityHasScore deserialize(JsonObject json, JsonDeserializationContext context) {
/*  93 */       Set<Map.Entry<String, JsonElement>> set = JsonUtils.getJsonObject(json, "scores").entrySet();
/*  94 */       Map<String, RandomValueRange> map = Maps.newLinkedHashMap();
/*     */       
/*  96 */       for (Map.Entry<String, JsonElement> entry : set)
/*     */       {
/*  98 */         map.put(entry.getKey(), (RandomValueRange)JsonUtils.deserializeClass(entry.getValue(), "score", context, RandomValueRange.class));
/*     */       }
/*     */       
/* 101 */       return new EntityHasScore(map, (LootContext.EntityTarget)JsonUtils.deserializeClass(json, "entity", context, LootContext.EntityTarget.class));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\conditions\EntityHasScore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */