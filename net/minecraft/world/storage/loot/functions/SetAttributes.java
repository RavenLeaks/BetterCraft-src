/*     */ package net.minecraft.world.storage.loot.functions;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.storage.loot.LootContext;
/*     */ import net.minecraft.world.storage.loot.RandomValueRange;
/*     */ import net.minecraft.world.storage.loot.conditions.LootCondition;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SetAttributes
/*     */   extends LootFunction {
/*  26 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final Modifier[] modifiers;
/*     */   
/*     */   public SetAttributes(LootCondition[] conditionsIn, Modifier[] modifiersIn) {
/*  31 */     super(conditionsIn);
/*  32 */     this.modifiers = modifiersIn;
/*     */   } public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
/*     */     byte b;
/*     */     int i;
/*     */     Modifier[] arrayOfModifier;
/*  37 */     for (i = (arrayOfModifier = this.modifiers).length, b = 0; b < i; ) { Modifier setattributes$modifier = arrayOfModifier[b];
/*     */       
/*  39 */       UUID uuid = setattributes$modifier.uuid;
/*     */       
/*  41 */       if (uuid == null)
/*     */       {
/*  43 */         uuid = UUID.randomUUID();
/*     */       }
/*     */       
/*  46 */       EntityEquipmentSlot entityequipmentslot = setattributes$modifier.slots[rand.nextInt(setattributes$modifier.slots.length)];
/*  47 */       stack.addAttributeModifier(setattributes$modifier.attributeName, new AttributeModifier(uuid, setattributes$modifier.modifierName, setattributes$modifier.amount.generateFloat(rand), setattributes$modifier.operation), entityequipmentslot);
/*     */       b++; }
/*     */     
/*  50 */     return stack;
/*     */   }
/*     */ 
/*     */   
/*     */   static class Modifier
/*     */   {
/*     */     private final String modifierName;
/*     */     private final String attributeName;
/*     */     private final int operation;
/*     */     private final RandomValueRange amount;
/*     */     @Nullable
/*     */     private final UUID uuid;
/*     */     private final EntityEquipmentSlot[] slots;
/*     */     
/*     */     private Modifier(String modifName, String attrName, int operationIn, RandomValueRange randomAmount, EntityEquipmentSlot[] slotsIn, @Nullable UUID uuidIn) {
/*  65 */       this.modifierName = modifName;
/*  66 */       this.attributeName = attrName;
/*  67 */       this.operation = operationIn;
/*  68 */       this.amount = randomAmount;
/*  69 */       this.uuid = uuidIn;
/*  70 */       this.slots = slotsIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonObject serialize(JsonSerializationContext context) {
/*  75 */       JsonObject jsonobject = new JsonObject();
/*  76 */       jsonobject.addProperty("name", this.modifierName);
/*  77 */       jsonobject.addProperty("attribute", this.attributeName);
/*  78 */       jsonobject.addProperty("operation", getOperationFromStr(this.operation));
/*  79 */       jsonobject.add("amount", context.serialize(this.amount));
/*     */       
/*  81 */       if (this.uuid != null)
/*     */       {
/*  83 */         jsonobject.addProperty("id", this.uuid.toString());
/*     */       }
/*     */       
/*  86 */       if (this.slots.length == 1) {
/*     */         
/*  88 */         jsonobject.addProperty("slot", this.slots[0].getName());
/*     */       }
/*     */       else {
/*     */         
/*  92 */         JsonArray jsonarray = new JsonArray(); byte b; int i;
/*     */         EntityEquipmentSlot[] arrayOfEntityEquipmentSlot;
/*  94 */         for (i = (arrayOfEntityEquipmentSlot = this.slots).length, b = 0; b < i; ) { EntityEquipmentSlot entityequipmentslot = arrayOfEntityEquipmentSlot[b];
/*     */           
/*  96 */           jsonarray.add((JsonElement)new JsonPrimitive(entityequipmentslot.getName()));
/*     */           b++; }
/*     */         
/*  99 */         jsonobject.add("slot", (JsonElement)jsonarray);
/*     */       } 
/*     */       
/* 102 */       return jsonobject;
/*     */     }
/*     */     
/*     */     public static Modifier deserialize(JsonObject jsonObj, JsonDeserializationContext context) {
/*     */       EntityEquipmentSlot[] aentityequipmentslot;
/* 107 */       String s = JsonUtils.getString(jsonObj, "name");
/* 108 */       String s1 = JsonUtils.getString(jsonObj, "attribute");
/* 109 */       int i = getOperationFromInt(JsonUtils.getString(jsonObj, "operation"));
/* 110 */       RandomValueRange randomvaluerange = (RandomValueRange)JsonUtils.deserializeClass(jsonObj, "amount", context, RandomValueRange.class);
/* 111 */       UUID uuid = null;
/*     */ 
/*     */       
/* 114 */       if (JsonUtils.isString(jsonObj, "slot")) {
/*     */         
/* 116 */         aentityequipmentslot = new EntityEquipmentSlot[] { EntityEquipmentSlot.fromString(JsonUtils.getString(jsonObj, "slot")) };
/*     */       }
/*     */       else {
/*     */         
/* 120 */         if (!JsonUtils.isJsonArray(jsonObj, "slot"))
/*     */         {
/* 122 */           throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
/*     */         }
/*     */         
/* 125 */         JsonArray jsonarray = JsonUtils.getJsonArray(jsonObj, "slot");
/* 126 */         aentityequipmentslot = new EntityEquipmentSlot[jsonarray.size()];
/* 127 */         int j = 0;
/*     */         
/* 129 */         for (JsonElement jsonelement : jsonarray)
/*     */         {
/* 131 */           aentityequipmentslot[j++] = EntityEquipmentSlot.fromString(JsonUtils.getString(jsonelement, "slot"));
/*     */         }
/*     */         
/* 134 */         if (aentityequipmentslot.length == 0)
/*     */         {
/* 136 */           throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
/*     */         }
/*     */       } 
/*     */       
/* 140 */       if (jsonObj.has("id")) {
/*     */         
/* 142 */         String s2 = JsonUtils.getString(jsonObj, "id");
/*     */ 
/*     */         
/*     */         try {
/* 146 */           uuid = UUID.fromString(s2);
/*     */         }
/* 148 */         catch (IllegalArgumentException var12) {
/*     */           
/* 150 */           throw new JsonSyntaxException("Invalid attribute modifier id '" + s2 + "' (must be UUID format, with dashes)");
/*     */         } 
/*     */       } 
/*     */       
/* 154 */       return new Modifier(s, s1, i, randomvaluerange, aentityequipmentslot, uuid);
/*     */     }
/*     */ 
/*     */     
/*     */     private static String getOperationFromStr(int operationIn) {
/* 159 */       switch (operationIn) {
/*     */         
/*     */         case 0:
/* 162 */           return "addition";
/*     */         
/*     */         case 1:
/* 165 */           return "multiply_base";
/*     */         
/*     */         case 2:
/* 168 */           return "multiply_total";
/*     */       } 
/*     */       
/* 171 */       throw new IllegalArgumentException("Unknown operation " + operationIn);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static int getOperationFromInt(String operationIn) {
/* 177 */       if ("addition".equals(operationIn))
/*     */       {
/* 179 */         return 0;
/*     */       }
/* 181 */       if ("multiply_base".equals(operationIn))
/*     */       {
/* 183 */         return 1;
/*     */       }
/* 185 */       if ("multiply_total".equals(operationIn))
/*     */       {
/* 187 */         return 2;
/*     */       }
/*     */ 
/*     */       
/* 191 */       throw new JsonSyntaxException("Unknown attribute modifier operation " + operationIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Serializer
/*     */     extends LootFunction.Serializer<SetAttributes>
/*     */   {
/*     */     public Serializer() {
/* 200 */       super(new ResourceLocation("set_attributes"), SetAttributes.class);
/*     */     }
/*     */ 
/*     */     
/*     */     public void serialize(JsonObject object, SetAttributes functionClazz, JsonSerializationContext serializationContext) {
/* 205 */       JsonArray jsonarray = new JsonArray(); byte b; int i;
/*     */       SetAttributes.Modifier[] arrayOfModifier;
/* 207 */       for (i = (arrayOfModifier = functionClazz.modifiers).length, b = 0; b < i; ) { SetAttributes.Modifier setattributes$modifier = arrayOfModifier[b];
/*     */         
/* 209 */         jsonarray.add((JsonElement)setattributes$modifier.serialize(serializationContext));
/*     */         b++; }
/*     */       
/* 212 */       object.add("modifiers", (JsonElement)jsonarray);
/*     */     }
/*     */ 
/*     */     
/*     */     public SetAttributes deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
/* 217 */       JsonArray jsonarray = JsonUtils.getJsonArray(object, "modifiers");
/* 218 */       SetAttributes.Modifier[] asetattributes$modifier = new SetAttributes.Modifier[jsonarray.size()];
/* 219 */       int i = 0;
/*     */       
/* 221 */       for (JsonElement jsonelement : jsonarray)
/*     */       {
/* 223 */         asetattributes$modifier[i++] = SetAttributes.Modifier.deserialize(JsonUtils.getJsonObject(jsonelement, "modifier"), deserializationContext);
/*     */       }
/*     */       
/* 226 */       if (asetattributes$modifier.length == 0)
/*     */       {
/* 228 */         throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
/*     */       }
/*     */ 
/*     */       
/* 232 */       return new SetAttributes(conditionsIn, asetattributes$modifier);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\loot\functions\SetAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */