/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.FunctionObject;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootContext;
/*     */ 
/*     */ 
/*     */ public class AdvancementRewards
/*     */ {
/*  35 */   public static final AdvancementRewards field_192114_a = new AdvancementRewards(0, new ResourceLocation[0], new ResourceLocation[0], FunctionObject.CacheableFunction.field_193519_a);
/*     */   
/*     */   private final int field_192115_b;
/*     */   private final ResourceLocation[] field_192116_c;
/*     */   private final ResourceLocation[] field_192117_d;
/*     */   private final FunctionObject.CacheableFunction field_193129_e;
/*     */   
/*     */   public AdvancementRewards(int p_i47587_1_, ResourceLocation[] p_i47587_2_, ResourceLocation[] p_i47587_3_, FunctionObject.CacheableFunction p_i47587_4_) {
/*  43 */     this.field_192115_b = p_i47587_1_;
/*  44 */     this.field_192116_c = p_i47587_2_;
/*  45 */     this.field_192117_d = p_i47587_3_;
/*  46 */     this.field_193129_e = p_i47587_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192113_a(final EntityPlayerMP p_192113_1_) {
/*  51 */     p_192113_1_.addExperience(this.field_192115_b);
/*  52 */     LootContext lootcontext = (new LootContext.Builder(p_192113_1_.getServerWorld())).withLootedEntity((Entity)p_192113_1_).build();
/*  53 */     boolean flag = false; byte b; int i;
/*     */     ResourceLocation[] arrayOfResourceLocation;
/*  55 */     for (i = (arrayOfResourceLocation = this.field_192116_c).length, b = 0; b < i; ) { ResourceLocation resourcelocation = arrayOfResourceLocation[b];
/*     */       
/*  57 */       for (ItemStack itemstack : p_192113_1_.world.getLootTableManager().getLootTableFromLocation(resourcelocation).generateLootForPools(p_192113_1_.getRNG(), lootcontext)) {
/*     */         
/*  59 */         if (p_192113_1_.func_191521_c(itemstack)) {
/*     */           
/*  61 */           p_192113_1_.world.playSound(null, p_192113_1_.posX, p_192113_1_.posY, p_192113_1_.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((p_192113_1_.getRNG().nextFloat() - p_192113_1_.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*  62 */           flag = true;
/*     */           
/*     */           continue;
/*     */         } 
/*  66 */         EntityItem entityitem = p_192113_1_.dropItem(itemstack, false);
/*     */         
/*  68 */         if (entityitem != null) {
/*     */           
/*  70 */           entityitem.setNoPickupDelay();
/*  71 */           entityitem.setOwner(p_192113_1_.getName());
/*     */         } 
/*     */       } 
/*     */       
/*     */       b++; }
/*     */     
/*  77 */     if (flag)
/*     */     {
/*  79 */       p_192113_1_.inventoryContainer.detectAndSendChanges();
/*     */     }
/*     */     
/*  82 */     if (this.field_192117_d.length > 0)
/*     */     {
/*  84 */       p_192113_1_.func_193102_a(this.field_192117_d);
/*     */     }
/*     */     
/*  87 */     final MinecraftServer minecraftserver = p_192113_1_.mcServer;
/*  88 */     FunctionObject functionobject = this.field_193129_e.func_193518_a(minecraftserver.func_193030_aL());
/*     */     
/*  90 */     if (functionobject != null) {
/*     */       
/*  92 */       ICommandSender icommandsender = new ICommandSender()
/*     */         {
/*     */           public String getName()
/*     */           {
/*  96 */             return p_192113_1_.getName();
/*     */           }
/*     */           
/*     */           public ITextComponent getDisplayName() {
/* 100 */             return p_192113_1_.getDisplayName();
/*     */           }
/*     */ 
/*     */           
/*     */           public void addChatMessage(ITextComponent component) {}
/*     */           
/*     */           public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 107 */             return (permLevel <= 2);
/*     */           }
/*     */           
/*     */           public BlockPos getPosition() {
/* 111 */             return p_192113_1_.getPosition();
/*     */           }
/*     */           
/*     */           public Vec3d getPositionVector() {
/* 115 */             return p_192113_1_.getPositionVector();
/*     */           }
/*     */           
/*     */           public World getEntityWorld() {
/* 119 */             return p_192113_1_.world;
/*     */           }
/*     */           
/*     */           public Entity getCommandSenderEntity() {
/* 123 */             return (Entity)p_192113_1_;
/*     */           }
/*     */           
/*     */           public boolean sendCommandFeedback() {
/* 127 */             return minecraftserver.worldServers[0].getGameRules().getBoolean("commandBlockOutput");
/*     */           }
/*     */           
/*     */           public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 131 */             p_192113_1_.setCommandStat(type, amount);
/*     */           }
/*     */           
/*     */           public MinecraftServer getServer() {
/* 135 */             return p_192113_1_.getServer();
/*     */           }
/*     */         };
/* 138 */       minecraftserver.func_193030_aL().func_194019_a(functionobject, icommandsender);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     return "AdvancementRewards{experience=" + this.field_192115_b + ", loot=" + Arrays.toString((Object[])this.field_192116_c) + ", recipes=" + Arrays.toString((Object[])this.field_192117_d) + ", function=" + this.field_193129_e + '}';
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<AdvancementRewards> {
/*     */     public AdvancementRewards deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*     */       FunctionObject.CacheableFunction functionobject$cacheablefunction;
/* 151 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "rewards");
/* 152 */       int i = JsonUtils.getInt(jsonobject, "experience", 0);
/* 153 */       JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "loot", new JsonArray());
/* 154 */       ResourceLocation[] aresourcelocation = new ResourceLocation[jsonarray.size()];
/*     */       
/* 156 */       for (int j = 0; j < aresourcelocation.length; j++)
/*     */       {
/* 158 */         aresourcelocation[j] = new ResourceLocation(JsonUtils.getString(jsonarray.get(j), "loot[" + j + "]"));
/*     */       }
/*     */       
/* 161 */       JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonobject, "recipes", new JsonArray());
/* 162 */       ResourceLocation[] aresourcelocation1 = new ResourceLocation[jsonarray1.size()];
/*     */       
/* 164 */       for (int k = 0; k < aresourcelocation1.length; k++) {
/*     */         
/* 166 */         aresourcelocation1[k] = new ResourceLocation(JsonUtils.getString(jsonarray1.get(k), "recipes[" + k + "]"));
/* 167 */         IRecipe irecipe = CraftingManager.func_193373_a(aresourcelocation1[k]);
/*     */         
/* 169 */         if (irecipe == null)
/*     */         {
/* 171 */           throw new JsonSyntaxException("Unknown recipe '" + aresourcelocation1[k] + "'");
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 177 */       if (jsonobject.has("function")) {
/*     */         
/* 179 */         functionobject$cacheablefunction = new FunctionObject.CacheableFunction(new ResourceLocation(JsonUtils.getString(jsonobject, "function")));
/*     */       }
/*     */       else {
/*     */         
/* 183 */         functionobject$cacheablefunction = FunctionObject.CacheableFunction.field_193519_a;
/*     */       } 
/*     */       
/* 186 */       return new AdvancementRewards(i, aresourcelocation, aresourcelocation1, functionobject$cacheablefunction);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\AdvancementRewards.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */