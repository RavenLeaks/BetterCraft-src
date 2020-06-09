/*     */ package optifine;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagDouble;
/*     */ import net.minecraft.nbt.NBTTagFloat;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagLong;
/*     */ import net.minecraft.nbt.NBTTagShort;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ 
/*     */ public class NbtTagValue
/*     */ {
/*  19 */   private String[] parents = null;
/*  20 */   private String name = null;
/*     */   private boolean negative = false;
/*  22 */   private int type = 0;
/*  23 */   private String value = null;
/*  24 */   private int valueFormat = 0;
/*     */   private static final int TYPE_TEXT = 0;
/*     */   private static final int TYPE_PATTERN = 1;
/*     */   private static final int TYPE_IPATTERN = 2;
/*     */   private static final int TYPE_REGEX = 3;
/*     */   private static final int TYPE_IREGEX = 4;
/*     */   private static final String PREFIX_PATTERN = "pattern:";
/*     */   private static final String PREFIX_IPATTERN = "ipattern:";
/*     */   private static final String PREFIX_REGEX = "regex:";
/*     */   private static final String PREFIX_IREGEX = "iregex:";
/*     */   private static final int FORMAT_DEFAULT = 0;
/*     */   private static final int FORMAT_HEX_COLOR = 1;
/*     */   private static final String PREFIX_HEX_COLOR = "#";
/*  37 */   private static final Pattern PATTERN_HEX_COLOR = Pattern.compile("^#[0-9a-f]{6}+$");
/*     */ 
/*     */   
/*     */   public NbtTagValue(String p_i69_1_, String p_i69_2_) {
/*  41 */     String[] astring = Config.tokenize(p_i69_1_, ".");
/*  42 */     this.parents = Arrays.<String>copyOfRange(astring, 0, astring.length - 1);
/*  43 */     this.name = astring[astring.length - 1];
/*     */     
/*  45 */     if (p_i69_2_.startsWith("!")) {
/*     */       
/*  47 */       this.negative = true;
/*  48 */       p_i69_2_ = p_i69_2_.substring(1);
/*     */     } 
/*     */     
/*  51 */     if (p_i69_2_.startsWith("pattern:")) {
/*     */       
/*  53 */       this.type = 1;
/*  54 */       p_i69_2_ = p_i69_2_.substring("pattern:".length());
/*     */     }
/*  56 */     else if (p_i69_2_.startsWith("ipattern:")) {
/*     */       
/*  58 */       this.type = 2;
/*  59 */       p_i69_2_ = p_i69_2_.substring("ipattern:".length()).toLowerCase();
/*     */     }
/*  61 */     else if (p_i69_2_.startsWith("regex:")) {
/*     */       
/*  63 */       this.type = 3;
/*  64 */       p_i69_2_ = p_i69_2_.substring("regex:".length());
/*     */     }
/*  66 */     else if (p_i69_2_.startsWith("iregex:")) {
/*     */       
/*  68 */       this.type = 4;
/*  69 */       p_i69_2_ = p_i69_2_.substring("iregex:".length()).toLowerCase();
/*     */     }
/*     */     else {
/*     */       
/*  73 */       this.type = 0;
/*     */     } 
/*     */     
/*  76 */     p_i69_2_ = StringEscapeUtils.unescapeJava(p_i69_2_);
/*     */     
/*  78 */     if (this.type == 0 && PATTERN_HEX_COLOR.matcher(p_i69_2_).matches())
/*     */     {
/*  80 */       this.valueFormat = 1;
/*     */     }
/*     */     
/*  83 */     this.value = p_i69_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(NBTTagCompound p_matches_1_) {
/*  88 */     if (this.negative)
/*     */     {
/*  90 */       return !matchesCompound(p_matches_1_);
/*     */     }
/*     */ 
/*     */     
/*  94 */     return matchesCompound(p_matches_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesCompound(NBTTagCompound p_matchesCompound_1_) {
/* 100 */     if (p_matchesCompound_1_ == null)
/*     */     {
/* 102 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 106 */     NBTTagCompound nBTTagCompound = p_matchesCompound_1_;
/*     */     
/* 108 */     for (int i = 0; i < this.parents.length; i++) {
/*     */       
/* 110 */       String s = this.parents[i];
/* 111 */       nBTBase = getChildTag((NBTBase)nBTTagCompound, s);
/*     */       
/* 113 */       if (nBTBase == null)
/*     */       {
/* 115 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 119 */     if (this.name.equals("*"))
/*     */     {
/* 121 */       return matchesAnyChild(nBTBase);
/*     */     }
/*     */ 
/*     */     
/* 125 */     NBTBase nBTBase = getChildTag(nBTBase, this.name);
/*     */     
/* 127 */     if (nBTBase == null)
/*     */     {
/* 129 */       return false;
/*     */     }
/* 131 */     if (matchesBase(nBTBase))
/*     */     {
/* 133 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesAnyChild(NBTBase p_matchesAnyChild_1_) {
/* 145 */     if (p_matchesAnyChild_1_ instanceof NBTTagCompound) {
/*     */       
/* 147 */       NBTTagCompound nbttagcompound = (NBTTagCompound)p_matchesAnyChild_1_;
/*     */       
/* 149 */       for (String s : nbttagcompound.getKeySet()) {
/*     */         
/* 151 */         NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */         
/* 153 */         if (matchesBase(nbtbase))
/*     */         {
/* 155 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     if (p_matchesAnyChild_1_ instanceof NBTTagList) {
/*     */       
/* 162 */       NBTTagList nbttaglist = (NBTTagList)p_matchesAnyChild_1_;
/* 163 */       int i = nbttaglist.tagCount();
/*     */       
/* 165 */       for (int j = 0; j < i; j++) {
/*     */         
/* 167 */         NBTBase nbtbase1 = nbttaglist.get(j);
/*     */         
/* 169 */         if (matchesBase(nbtbase1))
/*     */         {
/* 171 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static NBTBase getChildTag(NBTBase p_getChildTag_0_, String p_getChildTag_1_) {
/* 181 */     if (p_getChildTag_0_ instanceof NBTTagCompound) {
/*     */       
/* 183 */       NBTTagCompound nbttagcompound = (NBTTagCompound)p_getChildTag_0_;
/* 184 */       return nbttagcompound.getTag(p_getChildTag_1_);
/*     */     } 
/* 186 */     if (p_getChildTag_0_ instanceof NBTTagList) {
/*     */       
/* 188 */       NBTTagList nbttaglist = (NBTTagList)p_getChildTag_0_;
/*     */       
/* 190 */       if (p_getChildTag_1_.equals("count"))
/*     */       {
/* 192 */         return (NBTBase)new NBTTagInt(nbttaglist.tagCount());
/*     */       }
/*     */ 
/*     */       
/* 196 */       int i = Config.parseInt(p_getChildTag_1_, -1);
/* 197 */       return (i < 0) ? null : nbttaglist.get(i);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 202 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesBase(NBTBase p_matchesBase_1_) {
/* 208 */     if (p_matchesBase_1_ == null)
/*     */     {
/* 210 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 214 */     String s = getNbtString(p_matchesBase_1_, this.valueFormat);
/* 215 */     return matchesValue(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesValue(String p_matchesValue_1_) {
/* 221 */     if (p_matchesValue_1_ == null)
/*     */     {
/* 223 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 227 */     switch (this.type) {
/*     */       
/*     */       case 0:
/* 230 */         return p_matchesValue_1_.equals(this.value);
/*     */       
/*     */       case 1:
/* 233 */         return matchesPattern(p_matchesValue_1_, this.value);
/*     */       
/*     */       case 2:
/* 236 */         return matchesPattern(p_matchesValue_1_.toLowerCase(), this.value);
/*     */       
/*     */       case 3:
/* 239 */         return matchesRegex(p_matchesValue_1_, this.value);
/*     */       
/*     */       case 4:
/* 242 */         return matchesRegex(p_matchesValue_1_.toLowerCase(), this.value);
/*     */     } 
/*     */     
/* 245 */     throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesPattern(String p_matchesPattern_1_, String p_matchesPattern_2_) {
/* 252 */     return StrUtils.equalsMask(p_matchesPattern_1_, p_matchesPattern_2_, '*', '?');
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchesRegex(String p_matchesRegex_1_, String p_matchesRegex_2_) {
/* 257 */     return p_matchesRegex_1_.matches(p_matchesRegex_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getNbtString(NBTBase p_getNbtString_0_, int p_getNbtString_1_) {
/* 262 */     if (p_getNbtString_0_ == null)
/*     */     {
/* 264 */       return null;
/*     */     }
/* 266 */     if (p_getNbtString_0_ instanceof NBTTagString) {
/*     */       
/* 268 */       NBTTagString nbttagstring = (NBTTagString)p_getNbtString_0_;
/* 269 */       return nbttagstring.getString();
/*     */     } 
/* 271 */     if (p_getNbtString_0_ instanceof NBTTagInt) {
/*     */       
/* 273 */       NBTTagInt nbttagint = (NBTTagInt)p_getNbtString_0_;
/* 274 */       return (p_getNbtString_1_ == 1) ? ("#" + StrUtils.fillLeft(Integer.toHexString(nbttagint.getInt()), 6, '0')) : Integer.toString(nbttagint.getInt());
/*     */     } 
/* 276 */     if (p_getNbtString_0_ instanceof NBTTagByte) {
/*     */       
/* 278 */       NBTTagByte nbttagbyte = (NBTTagByte)p_getNbtString_0_;
/* 279 */       return Byte.toString(nbttagbyte.getByte());
/*     */     } 
/* 281 */     if (p_getNbtString_0_ instanceof NBTTagShort) {
/*     */       
/* 283 */       NBTTagShort nbttagshort = (NBTTagShort)p_getNbtString_0_;
/* 284 */       return Short.toString(nbttagshort.getShort());
/*     */     } 
/* 286 */     if (p_getNbtString_0_ instanceof NBTTagLong) {
/*     */       
/* 288 */       NBTTagLong nbttaglong = (NBTTagLong)p_getNbtString_0_;
/* 289 */       return Long.toString(nbttaglong.getLong());
/*     */     } 
/* 291 */     if (p_getNbtString_0_ instanceof NBTTagFloat) {
/*     */       
/* 293 */       NBTTagFloat nbttagfloat = (NBTTagFloat)p_getNbtString_0_;
/* 294 */       return Float.toString(nbttagfloat.getFloat());
/*     */     } 
/* 296 */     if (p_getNbtString_0_ instanceof NBTTagDouble) {
/*     */       
/* 298 */       NBTTagDouble nbttagdouble = (NBTTagDouble)p_getNbtString_0_;
/* 299 */       return Double.toString(nbttagdouble.getDouble());
/*     */     } 
/*     */ 
/*     */     
/* 303 */     return p_getNbtString_0_.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 309 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/* 311 */     for (int i = 0; i < this.parents.length; i++) {
/*     */       
/* 313 */       String s = this.parents[i];
/*     */       
/* 315 */       if (i > 0)
/*     */       {
/* 317 */         stringbuffer.append(".");
/*     */       }
/*     */       
/* 320 */       stringbuffer.append(s);
/*     */     } 
/*     */     
/* 323 */     if (stringbuffer.length() > 0)
/*     */     {
/* 325 */       stringbuffer.append(".");
/*     */     }
/*     */     
/* 328 */     stringbuffer.append(this.name);
/* 329 */     stringbuffer.append(" = ");
/* 330 */     stringbuffer.append(this.value);
/* 331 */     return stringbuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\NbtTagValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */