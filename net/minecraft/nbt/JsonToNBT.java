/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class JsonToNBT
/*     */ {
/*  10 */   private static final Pattern field_193615_a = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
/*  11 */   private static final Pattern field_193616_b = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
/*  12 */   private static final Pattern field_193617_c = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
/*  13 */   private static final Pattern field_193618_d = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
/*  14 */   private static final Pattern field_193619_e = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
/*  15 */   private static final Pattern field_193620_f = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
/*  16 */   private static final Pattern field_193621_g = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
/*     */   
/*     */   private final String field_193622_h;
/*     */   private int field_193623_i;
/*     */   
/*     */   public static NBTTagCompound getTagFromJson(String jsonString) throws NBTException {
/*  22 */     return (new JsonToNBT(jsonString)).func_193609_a();
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   NBTTagCompound func_193609_a() throws NBTException {
/*  28 */     NBTTagCompound nbttagcompound = func_193593_f();
/*  29 */     func_193607_l();
/*     */     
/*  31 */     if (func_193612_g()) {
/*     */       
/*  33 */       this.field_193623_i++;
/*  34 */       throw func_193602_b("Trailing data found");
/*     */     } 
/*     */ 
/*     */     
/*  38 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   JsonToNBT(String p_i47522_1_) {
/*  45 */     this.field_193622_h = p_i47522_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String func_193601_b() throws NBTException {
/*  50 */     func_193607_l();
/*     */     
/*  52 */     if (!func_193612_g())
/*     */     {
/*  54 */       throw func_193602_b("Expected key");
/*     */     }
/*     */ 
/*     */     
/*  58 */     return (func_193598_n() == '"') ? func_193595_h() : func_193614_i();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private NBTException func_193602_b(String p_193602_1_) {
/*  64 */     return new NBTException(p_193602_1_, this.field_193622_h, this.field_193623_i);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NBTBase func_193611_c() throws NBTException {
/*  69 */     func_193607_l();
/*     */     
/*  71 */     if (func_193598_n() == '"')
/*     */     {
/*  73 */       return new NBTTagString(func_193595_h());
/*     */     }
/*     */ 
/*     */     
/*  77 */     String s = func_193614_i();
/*     */     
/*  79 */     if (s.isEmpty())
/*     */     {
/*  81 */       throw func_193602_b("Expected value");
/*     */     }
/*     */ 
/*     */     
/*  85 */     return func_193596_c(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NBTBase func_193596_c(String p_193596_1_) {
/*     */     try {
/*  94 */       if (field_193617_c.matcher(p_193596_1_).matches())
/*     */       {
/*  96 */         return new NBTTagFloat(Float.parseFloat(p_193596_1_.substring(0, p_193596_1_.length() - 1)));
/*     */       }
/*     */       
/*  99 */       if (field_193618_d.matcher(p_193596_1_).matches())
/*     */       {
/* 101 */         return new NBTTagByte(Byte.parseByte(p_193596_1_.substring(0, p_193596_1_.length() - 1)));
/*     */       }
/*     */       
/* 104 */       if (field_193619_e.matcher(p_193596_1_).matches())
/*     */       {
/* 106 */         return new NBTTagLong(Long.parseLong(p_193596_1_.substring(0, p_193596_1_.length() - 1)));
/*     */       }
/*     */       
/* 109 */       if (field_193620_f.matcher(p_193596_1_).matches())
/*     */       {
/* 111 */         return new NBTTagShort(Short.parseShort(p_193596_1_.substring(0, p_193596_1_.length() - 1)));
/*     */       }
/*     */       
/* 114 */       if (field_193621_g.matcher(p_193596_1_).matches())
/*     */       {
/* 116 */         return new NBTTagInt(Integer.parseInt(p_193596_1_));
/*     */       }
/*     */       
/* 119 */       if (field_193616_b.matcher(p_193596_1_).matches())
/*     */       {
/* 121 */         return new NBTTagDouble(Double.parseDouble(p_193596_1_.substring(0, p_193596_1_.length() - 1)));
/*     */       }
/*     */       
/* 124 */       if (field_193615_a.matcher(p_193596_1_).matches())
/*     */       {
/* 126 */         return new NBTTagDouble(Double.parseDouble(p_193596_1_));
/*     */       }
/*     */       
/* 129 */       if ("true".equalsIgnoreCase(p_193596_1_))
/*     */       {
/* 131 */         return new NBTTagByte((byte)1);
/*     */       }
/*     */       
/* 134 */       if ("false".equalsIgnoreCase(p_193596_1_))
/*     */       {
/* 136 */         return new NBTTagByte((byte)0);
/*     */       }
/*     */     }
/* 139 */     catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     return new NBTTagString(p_193596_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private String func_193595_h() throws NBTException {
/* 149 */     int i = ++this.field_193623_i;
/* 150 */     StringBuilder stringbuilder = null;
/* 151 */     boolean flag = false;
/*     */     
/* 153 */     while (func_193612_g()) {
/*     */       
/* 155 */       char c0 = func_193594_o();
/*     */       
/* 157 */       if (flag) {
/*     */         
/* 159 */         if (c0 != '\\' && c0 != '"')
/*     */         {
/* 161 */           throw func_193602_b("Invalid escape of '" + c0 + "'");
/*     */         }
/*     */         
/* 164 */         flag = false;
/*     */       }
/*     */       else {
/*     */         
/* 168 */         if (c0 == '\\') {
/*     */           
/* 170 */           flag = true;
/*     */           
/* 172 */           if (stringbuilder == null)
/*     */           {
/* 174 */             stringbuilder = new StringBuilder(this.field_193622_h.substring(i, this.field_193623_i - 1));
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 180 */         if (c0 == '"')
/*     */         {
/* 182 */           return (stringbuilder == null) ? this.field_193622_h.substring(i, this.field_193623_i - 1) : stringbuilder.toString();
/*     */         }
/*     */       } 
/*     */       
/* 186 */       if (stringbuilder != null)
/*     */       {
/* 188 */         stringbuilder.append(c0);
/*     */       }
/*     */     } 
/*     */     
/* 192 */     throw func_193602_b("Missing termination quote");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String func_193614_i() {
/*     */     int i;
/* 199 */     for (i = this.field_193623_i; func_193612_g() && func_193599_a(func_193598_n()); this.field_193623_i++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     return this.field_193622_h.substring(i, this.field_193623_i);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NBTBase func_193610_d() throws NBTException {
/* 209 */     func_193607_l();
/*     */     
/* 211 */     if (!func_193612_g())
/*     */     {
/* 213 */       throw func_193602_b("Expected value");
/*     */     }
/*     */ 
/*     */     
/* 217 */     char c0 = func_193598_n();
/*     */     
/* 219 */     if (c0 == '{')
/*     */     {
/* 221 */       return func_193593_f();
/*     */     }
/*     */ 
/*     */     
/* 225 */     return (c0 == '[') ? func_193605_e() : func_193611_c();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NBTBase func_193605_e() throws NBTException {
/* 232 */     return (func_193608_a(2) && func_193597_b(1) != '"' && func_193597_b(2) == ';') ? func_193606_k() : func_193600_j();
/*     */   }
/*     */ 
/*     */   
/*     */   protected NBTTagCompound func_193593_f() throws NBTException {
/* 237 */     func_193604_b('{');
/* 238 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 239 */     func_193607_l();
/*     */     
/* 241 */     while (func_193612_g() && func_193598_n() != '}') {
/*     */       
/* 243 */       String s = func_193601_b();
/*     */       
/* 245 */       if (s.isEmpty())
/*     */       {
/* 247 */         throw func_193602_b("Expected non-empty key");
/*     */       }
/*     */       
/* 250 */       func_193604_b(':');
/* 251 */       nbttagcompound.setTag(s, func_193610_d());
/*     */       
/* 253 */       if (!func_193613_m()) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 258 */       if (!func_193612_g())
/*     */       {
/* 260 */         throw func_193602_b("Expected key");
/*     */       }
/*     */     } 
/*     */     
/* 264 */     func_193604_b('}');
/* 265 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   private NBTBase func_193600_j() throws NBTException {
/* 270 */     func_193604_b('[');
/* 271 */     func_193607_l();
/*     */     
/* 273 */     if (!func_193612_g())
/*     */     {
/* 275 */       throw func_193602_b("Expected value");
/*     */     }
/*     */ 
/*     */     
/* 279 */     NBTTagList nbttaglist = new NBTTagList();
/* 280 */     int i = -1;
/*     */     
/* 282 */     while (func_193598_n() != ']') {
/*     */       
/* 284 */       NBTBase nbtbase = func_193610_d();
/* 285 */       int j = nbtbase.getId();
/*     */       
/* 287 */       if (i < 0) {
/*     */         
/* 289 */         i = j;
/*     */       }
/* 291 */       else if (j != i) {
/*     */         
/* 293 */         throw func_193602_b("Unable to insert " + NBTBase.func_193581_j(j) + " into ListTag of type " + NBTBase.func_193581_j(i));
/*     */       } 
/*     */       
/* 296 */       nbttaglist.appendTag(nbtbase);
/*     */       
/* 298 */       if (!func_193613_m()) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 303 */       if (!func_193612_g())
/*     */       {
/* 305 */         throw func_193602_b("Expected value");
/*     */       }
/*     */     } 
/*     */     
/* 309 */     func_193604_b(']');
/* 310 */     return nbttaglist;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private NBTBase func_193606_k() throws NBTException {
/* 316 */     func_193604_b('[');
/* 317 */     char c0 = func_193594_o();
/* 318 */     func_193594_o();
/* 319 */     func_193607_l();
/*     */     
/* 321 */     if (!func_193612_g())
/*     */     {
/* 323 */       throw func_193602_b("Expected value");
/*     */     }
/* 325 */     if (c0 == 'B')
/*     */     {
/* 327 */       return new NBTTagByteArray(func_193603_a((byte)7, (byte)1));
/*     */     }
/* 329 */     if (c0 == 'L')
/*     */     {
/* 331 */       return new NBTTagLongArray(func_193603_a((byte)12, (byte)4));
/*     */     }
/* 333 */     if (c0 == 'I')
/*     */     {
/* 335 */       return new NBTTagIntArray(func_193603_a((byte)11, (byte)3));
/*     */     }
/*     */ 
/*     */     
/* 339 */     throw func_193602_b("Invalid array type '" + c0 + "' found");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <T extends Number> List<T> func_193603_a(byte p_193603_1_, byte p_193603_2_) throws NBTException {
/* 345 */     List<T> list = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */     
/* 349 */     while (func_193598_n() != ']') {
/*     */       
/* 351 */       NBTBase nbtbase = func_193610_d();
/* 352 */       int i = nbtbase.getId();
/*     */       
/* 354 */       if (i != p_193603_2_)
/*     */       {
/* 356 */         throw func_193602_b("Unable to insert " + NBTBase.func_193581_j(i) + " into " + NBTBase.func_193581_j(p_193603_1_));
/*     */       }
/*     */       
/* 359 */       if (p_193603_2_ == 1) {
/*     */         
/* 361 */         list.add((T)Byte.valueOf(((NBTPrimitive)nbtbase).getByte()));
/*     */       }
/* 363 */       else if (p_193603_2_ == 4) {
/*     */         
/* 365 */         list.add((T)Long.valueOf(((NBTPrimitive)nbtbase).getLong()));
/*     */       }
/*     */       else {
/*     */         
/* 369 */         list.add((T)Integer.valueOf(((NBTPrimitive)nbtbase).getInt()));
/*     */       } 
/*     */       
/* 372 */       if (func_193613_m())
/*     */       {
/* 374 */         if (!func_193612_g())
/*     */         {
/* 376 */           throw func_193602_b("Expected value");
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 383 */     func_193604_b(']');
/* 384 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_193607_l() {
/* 390 */     while (func_193612_g() && Character.isWhitespace(func_193598_n()))
/*     */     {
/* 392 */       this.field_193623_i++;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_193613_m() {
/* 398 */     func_193607_l();
/*     */     
/* 400 */     if (func_193612_g() && func_193598_n() == ',') {
/*     */       
/* 402 */       this.field_193623_i++;
/* 403 */       func_193607_l();
/* 404 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 408 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_193604_b(char p_193604_1_) throws NBTException {
/* 414 */     func_193607_l();
/* 415 */     boolean flag = func_193612_g();
/*     */     
/* 417 */     if (flag && func_193598_n() == p_193604_1_) {
/*     */       
/* 419 */       this.field_193623_i++;
/*     */     }
/*     */     else {
/*     */       
/* 423 */       throw new NBTException("Expected '" + p_193604_1_ + "' but got '" + (flag ? Character.valueOf(func_193598_n()) : "<EOF>") + "'", this.field_193622_h, this.field_193623_i + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_193599_a(char p_193599_1_) {
/* 429 */     return !((p_193599_1_ < '0' || p_193599_1_ > '9') && (p_193599_1_ < 'A' || p_193599_1_ > 'Z') && (p_193599_1_ < 'a' || p_193599_1_ > 'z') && p_193599_1_ != '_' && p_193599_1_ != '-' && p_193599_1_ != '.' && p_193599_1_ != '+');
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_193608_a(int p_193608_1_) {
/* 434 */     return (this.field_193623_i + p_193608_1_ < this.field_193622_h.length());
/*     */   }
/*     */ 
/*     */   
/*     */   boolean func_193612_g() {
/* 439 */     return func_193608_a(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private char func_193597_b(int p_193597_1_) {
/* 444 */     return this.field_193622_h.charAt(this.field_193623_i + p_193597_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private char func_193598_n() {
/* 449 */     return func_193597_b(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private char func_193594_o() {
/* 454 */     return this.field_193622_h.charAt(this.field_193623_i++);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\JsonToNBT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */