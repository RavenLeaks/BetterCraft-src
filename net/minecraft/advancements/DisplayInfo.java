/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ 
/*     */ public class DisplayInfo
/*     */ {
/*     */   private final ITextComponent field_192300_a;
/*     */   private final ITextComponent field_193225_b;
/*     */   private final ItemStack field_192301_b;
/*     */   private final ResourceLocation field_192302_c;
/*     */   private final FrameType field_192303_d;
/*     */   private final boolean field_193226_f;
/*     */   private final boolean field_193227_g;
/*     */   private final boolean field_193228_h;
/*     */   private float field_192304_e;
/*     */   private float field_192305_f;
/*     */   
/*     */   public DisplayInfo(ItemStack p_i47586_1_, ITextComponent p_i47586_2_, ITextComponent p_i47586_3_, @Nullable ResourceLocation p_i47586_4_, FrameType p_i47586_5_, boolean p_i47586_6_, boolean p_i47586_7_, boolean p_i47586_8_) {
/*  30 */     this.field_192300_a = p_i47586_2_;
/*  31 */     this.field_193225_b = p_i47586_3_;
/*  32 */     this.field_192301_b = p_i47586_1_;
/*  33 */     this.field_192302_c = p_i47586_4_;
/*  34 */     this.field_192303_d = p_i47586_5_;
/*  35 */     this.field_193226_f = p_i47586_6_;
/*  36 */     this.field_193227_g = p_i47586_7_;
/*  37 */     this.field_193228_h = p_i47586_8_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192292_a(float p_192292_1_, float p_192292_2_) {
/*  42 */     this.field_192304_e = p_192292_1_;
/*  43 */     this.field_192305_f = p_192292_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent func_192297_a() {
/*  48 */     return this.field_192300_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextComponent func_193222_b() {
/*  53 */     return this.field_193225_b;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_192298_b() {
/*  58 */     return this.field_192301_b;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation func_192293_c() {
/*  64 */     return this.field_192302_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public FrameType func_192291_d() {
/*  69 */     return this.field_192303_d;
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_192299_e() {
/*  74 */     return this.field_192304_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_192296_f() {
/*  79 */     return this.field_192305_f;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193223_h() {
/*  84 */     return this.field_193226_f;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193220_i() {
/*  89 */     return this.field_193227_g;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193224_j() {
/*  94 */     return this.field_193228_h;
/*     */   }
/*     */ 
/*     */   
/*     */   public static DisplayInfo func_192294_a(JsonObject p_192294_0_, JsonDeserializationContext p_192294_1_) {
/*  99 */     ITextComponent itextcomponent = (ITextComponent)JsonUtils.deserializeClass(p_192294_0_, "title", p_192294_1_, ITextComponent.class);
/* 100 */     ITextComponent itextcomponent1 = (ITextComponent)JsonUtils.deserializeClass(p_192294_0_, "description", p_192294_1_, ITextComponent.class);
/*     */     
/* 102 */     if (itextcomponent != null && itextcomponent1 != null) {
/*     */       
/* 104 */       ItemStack itemstack = func_193221_a(JsonUtils.getJsonObject(p_192294_0_, "icon"));
/* 105 */       ResourceLocation resourcelocation = p_192294_0_.has("background") ? new ResourceLocation(JsonUtils.getString(p_192294_0_, "background")) : null;
/* 106 */       FrameType frametype = p_192294_0_.has("frame") ? FrameType.func_192308_a(JsonUtils.getString(p_192294_0_, "frame")) : FrameType.TASK;
/* 107 */       boolean flag = JsonUtils.getBoolean(p_192294_0_, "show_toast", true);
/* 108 */       boolean flag1 = JsonUtils.getBoolean(p_192294_0_, "announce_to_chat", true);
/* 109 */       boolean flag2 = JsonUtils.getBoolean(p_192294_0_, "hidden", false);
/* 110 */       return new DisplayInfo(itemstack, itextcomponent, itextcomponent1, resourcelocation, frametype, flag, flag1, flag2);
/*     */     } 
/*     */ 
/*     */     
/* 114 */     throw new JsonSyntaxException("Both title and description must be set");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ItemStack func_193221_a(JsonObject p_193221_0_) {
/* 120 */     if (!p_193221_0_.has("item"))
/*     */     {
/* 122 */       throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
/*     */     }
/*     */ 
/*     */     
/* 126 */     Item item = JsonUtils.getItem(p_193221_0_, "item");
/* 127 */     int i = JsonUtils.getInt(p_193221_0_, "data", 0);
/* 128 */     return new ItemStack(item, 1, i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_192290_a(PacketBuffer p_192290_1_) {
/* 134 */     p_192290_1_.writeTextComponent(this.field_192300_a);
/* 135 */     p_192290_1_.writeTextComponent(this.field_193225_b);
/* 136 */     p_192290_1_.writeItemStackToBuffer(this.field_192301_b);
/* 137 */     p_192290_1_.writeEnumValue(this.field_192303_d);
/* 138 */     int i = 0;
/*     */     
/* 140 */     if (this.field_192302_c != null)
/*     */     {
/* 142 */       i |= 0x1;
/*     */     }
/*     */     
/* 145 */     if (this.field_193226_f)
/*     */     {
/* 147 */       i |= 0x2;
/*     */     }
/*     */     
/* 150 */     if (this.field_193228_h)
/*     */     {
/* 152 */       i |= 0x4;
/*     */     }
/*     */     
/* 155 */     p_192290_1_.writeInt(i);
/*     */     
/* 157 */     if (this.field_192302_c != null)
/*     */     {
/* 159 */       p_192290_1_.func_192572_a(this.field_192302_c);
/*     */     }
/*     */     
/* 162 */     p_192290_1_.writeFloat(this.field_192304_e);
/* 163 */     p_192290_1_.writeFloat(this.field_192305_f);
/*     */   }
/*     */ 
/*     */   
/*     */   public static DisplayInfo func_192295_b(PacketBuffer p_192295_0_) throws IOException {
/* 168 */     ITextComponent itextcomponent = p_192295_0_.readTextComponent();
/* 169 */     ITextComponent itextcomponent1 = p_192295_0_.readTextComponent();
/* 170 */     ItemStack itemstack = p_192295_0_.readItemStackFromBuffer();
/* 171 */     FrameType frametype = (FrameType)p_192295_0_.readEnumValue(FrameType.class);
/* 172 */     int i = p_192295_0_.readInt();
/* 173 */     ResourceLocation resourcelocation = ((i & 0x1) != 0) ? p_192295_0_.func_192575_l() : null;
/* 174 */     boolean flag = ((i & 0x2) != 0);
/* 175 */     boolean flag1 = ((i & 0x4) != 0);
/* 176 */     DisplayInfo displayinfo = new DisplayInfo(itemstack, itextcomponent, itextcomponent1, resourcelocation, frametype, flag, false, flag1);
/* 177 */     displayinfo.func_192292_a(p_192295_0_.readFloat(), p_192295_0_.readFloat());
/* 178 */     return displayinfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\DisplayInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */