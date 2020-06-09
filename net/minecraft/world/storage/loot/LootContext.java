/*     */ package net.minecraft.world.storage.loot;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.TypeAdapter;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class LootContext
/*     */ {
/*     */   private final float luck;
/*     */   private final WorldServer worldObj;
/*     */   private final LootTableManager lootTableManager;
/*     */   @Nullable
/*     */   private final Entity lootedEntity;
/*     */   @Nullable
/*     */   private final EntityPlayer player;
/*     */   @Nullable
/*     */   private final DamageSource damageSource;
/*  26 */   private final Set<LootTable> lootTables = Sets.newLinkedHashSet();
/*     */ 
/*     */   
/*     */   public LootContext(float luckIn, WorldServer worldIn, LootTableManager lootTableManagerIn, @Nullable Entity lootedEntityIn, @Nullable EntityPlayer playerIn, @Nullable DamageSource damageSourceIn) {
/*  30 */     this.luck = luckIn;
/*  31 */     this.worldObj = worldIn;
/*  32 */     this.lootTableManager = lootTableManagerIn;
/*  33 */     this.lootedEntity = lootedEntityIn;
/*  34 */     this.player = playerIn;
/*  35 */     this.damageSource = damageSourceIn;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getLootedEntity() {
/*  41 */     return this.lootedEntity;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getKillerPlayer() {
/*  47 */     return (Entity)this.player;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getKiller() {
/*  53 */     return (this.damageSource == null) ? null : this.damageSource.getEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addLootTable(LootTable lootTableIn) {
/*  58 */     return this.lootTables.add(lootTableIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeLootTable(LootTable lootTableIn) {
/*  63 */     this.lootTables.remove(lootTableIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public LootTableManager getLootTableManager() {
/*  68 */     return this.lootTableManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getLuck() {
/*  73 */     return this.luck;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getEntity(EntityTarget target) {
/*  79 */     switch (target) {
/*     */       
/*     */       case THIS:
/*  82 */         return getLootedEntity();
/*     */       
/*     */       case null:
/*  85 */         return getKiller();
/*     */       
/*     */       case KILLER_PLAYER:
/*  88 */         return getKillerPlayer();
/*     */     } 
/*     */     
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private final WorldServer worldObj;
/*     */     
/*     */     private float luck;
/*     */     private Entity lootedEntity;
/*     */     private EntityPlayer player;
/*     */     private DamageSource damageSource;
/*     */     
/*     */     public Builder(WorldServer worldIn) {
/* 105 */       this.worldObj = worldIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withLuck(float luckIn) {
/* 110 */       this.luck = luckIn;
/* 111 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withLootedEntity(Entity entityIn) {
/* 116 */       this.lootedEntity = entityIn;
/* 117 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withPlayer(EntityPlayer playerIn) {
/* 122 */       this.player = playerIn;
/* 123 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder withDamageSource(DamageSource dmgSource) {
/* 128 */       this.damageSource = dmgSource;
/* 129 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootContext build() {
/* 134 */       return new LootContext(this.luck, this.worldObj, this.worldObj.getLootTableManager(), this.lootedEntity, this.player, this.damageSource);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EntityTarget
/*     */   {
/* 140 */     THIS("this"),
/* 141 */     KILLER("killer"),
/* 142 */     KILLER_PLAYER("killer_player");
/*     */     
/*     */     private final String targetType;
/*     */ 
/*     */     
/*     */     EntityTarget(String type) {
/* 148 */       this.targetType = type;
/*     */     } public static EntityTarget fromString(String type) {
/*     */       byte b;
/*     */       int i;
/*     */       EntityTarget[] arrayOfEntityTarget;
/* 153 */       for (i = (arrayOfEntityTarget = values()).length, b = 0; b < i; ) { EntityTarget lootcontext$entitytarget = arrayOfEntityTarget[b];
/*     */         
/* 155 */         if (lootcontext$entitytarget.targetType.equals(type))
/*     */         {
/* 157 */           return lootcontext$entitytarget;
/*     */         }
/*     */         b++; }
/*     */       
/* 161 */       throw new IllegalArgumentException("Invalid entity target " + type);
/*     */     }
/*     */     
/*     */     public static class Serializer extends TypeAdapter<EntityTarget> {
/*     */       public void write(JsonWriter p_write_1_, LootContext.EntityTarget p_write_2_) throws IOException {
/* 166 */         p_write_1_.value(p_write_2_.targetType);
/*     */       }
/*     */       
/*     */       public LootContext.EntityTarget read(JsonReader p_read_1_) throws IOException {
/* 170 */         return LootContext.EntityTarget.fromString(p_read_1_.nextString());
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\LootContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */