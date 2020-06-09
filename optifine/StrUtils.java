/*     */ package optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ public class StrUtils
/*     */ {
/*     */   public static boolean equalsMask(String p_equalsMask_0_, String p_equalsMask_1_, char p_equalsMask_2_, char p_equalsMask_3_) {
/*  11 */     if (p_equalsMask_1_ != null && p_equalsMask_0_ != null) {
/*     */       
/*  13 */       if (p_equalsMask_1_.indexOf(p_equalsMask_2_) < 0)
/*     */       {
/*  15 */         return (p_equalsMask_1_.indexOf(p_equalsMask_3_) < 0) ? p_equalsMask_1_.equals(p_equalsMask_0_) : equalsMaskSingle(p_equalsMask_0_, p_equalsMask_1_, p_equalsMask_3_);
/*     */       }
/*     */ 
/*     */       
/*  19 */       List<String> list = new ArrayList();
/*  20 */       char c = p_equalsMask_2_;
/*     */       
/*  22 */       if (p_equalsMask_1_.startsWith(c))
/*     */       {
/*  24 */         list.add("");
/*     */       }
/*     */       
/*  27 */       StringTokenizer stringtokenizer = new StringTokenizer(p_equalsMask_1_, c);
/*     */       
/*  29 */       while (stringtokenizer.hasMoreElements())
/*     */       {
/*  31 */         list.add(stringtokenizer.nextToken());
/*     */       }
/*     */       
/*  34 */       if (p_equalsMask_1_.endsWith(c))
/*     */       {
/*  36 */         list.add("");
/*     */       }
/*     */       
/*  39 */       String s1 = list.get(0);
/*     */       
/*  41 */       if (!startsWithMaskSingle(p_equalsMask_0_, s1, p_equalsMask_3_))
/*     */       {
/*  43 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  47 */       String s2 = list.get(list.size() - 1);
/*     */       
/*  49 */       if (!endsWithMaskSingle(p_equalsMask_0_, s2, p_equalsMask_3_))
/*     */       {
/*  51 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  55 */       int i = 0;
/*     */       
/*  57 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/*  59 */         String s3 = list.get(j);
/*     */         
/*  61 */         if (s3.length() > 0) {
/*     */           
/*  63 */           int k = indexOfMaskSingle(p_equalsMask_0_, s3, i, p_equalsMask_3_);
/*     */           
/*  65 */           if (k < 0)
/*     */           {
/*  67 */             return false;
/*     */           }
/*     */           
/*  70 */           i = k + s3.length();
/*     */         } 
/*     */       } 
/*     */       
/*  74 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     return (p_equalsMask_1_ == p_equalsMask_0_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equalsMaskSingle(String p_equalsMaskSingle_0_, String p_equalsMaskSingle_1_, char p_equalsMaskSingle_2_) {
/*  87 */     if (p_equalsMaskSingle_0_ != null && p_equalsMaskSingle_1_ != null) {
/*     */       
/*  89 */       if (p_equalsMaskSingle_0_.length() != p_equalsMaskSingle_1_.length())
/*     */       {
/*  91 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  95 */       for (int i = 0; i < p_equalsMaskSingle_1_.length(); i++) {
/*     */         
/*  97 */         char c0 = p_equalsMaskSingle_1_.charAt(i);
/*     */         
/*  99 */         if (c0 != p_equalsMaskSingle_2_ && p_equalsMaskSingle_0_.charAt(i) != c0)
/*     */         {
/* 101 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 105 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 110 */     return (p_equalsMaskSingle_0_ == p_equalsMaskSingle_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexOfMaskSingle(String p_indexOfMaskSingle_0_, String p_indexOfMaskSingle_1_, int p_indexOfMaskSingle_2_, char p_indexOfMaskSingle_3_) {
/* 116 */     if (p_indexOfMaskSingle_0_ != null && p_indexOfMaskSingle_1_ != null) {
/*     */       
/* 118 */       if (p_indexOfMaskSingle_2_ >= 0 && p_indexOfMaskSingle_2_ <= p_indexOfMaskSingle_0_.length()) {
/*     */         
/* 120 */         if (p_indexOfMaskSingle_0_.length() < p_indexOfMaskSingle_2_ + p_indexOfMaskSingle_1_.length())
/*     */         {
/* 122 */           return -1;
/*     */         }
/*     */ 
/*     */         
/* 126 */         for (int i = p_indexOfMaskSingle_2_; i + p_indexOfMaskSingle_1_.length() <= p_indexOfMaskSingle_0_.length(); i++) {
/*     */           
/* 128 */           String s = p_indexOfMaskSingle_0_.substring(i, i + p_indexOfMaskSingle_1_.length());
/*     */           
/* 130 */           if (equalsMaskSingle(s, p_indexOfMaskSingle_1_, p_indexOfMaskSingle_3_))
/*     */           {
/* 132 */             return i;
/*     */           }
/*     */         } 
/*     */         
/* 136 */         return -1;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 141 */       return -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 146 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean endsWithMaskSingle(String p_endsWithMaskSingle_0_, String p_endsWithMaskSingle_1_, char p_endsWithMaskSingle_2_) {
/* 152 */     if (p_endsWithMaskSingle_0_ != null && p_endsWithMaskSingle_1_ != null) {
/*     */       
/* 154 */       if (p_endsWithMaskSingle_0_.length() < p_endsWithMaskSingle_1_.length())
/*     */       {
/* 156 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 160 */       String s = p_endsWithMaskSingle_0_.substring(p_endsWithMaskSingle_0_.length() - p_endsWithMaskSingle_1_.length(), p_endsWithMaskSingle_0_.length());
/* 161 */       return equalsMaskSingle(s, p_endsWithMaskSingle_1_, p_endsWithMaskSingle_2_);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     return (p_endsWithMaskSingle_0_ == p_endsWithMaskSingle_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean startsWithMaskSingle(String p_startsWithMaskSingle_0_, String p_startsWithMaskSingle_1_, char p_startsWithMaskSingle_2_) {
/* 172 */     if (p_startsWithMaskSingle_0_ != null && p_startsWithMaskSingle_1_ != null) {
/*     */       
/* 174 */       if (p_startsWithMaskSingle_0_.length() < p_startsWithMaskSingle_1_.length())
/*     */       {
/* 176 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 180 */       String s = p_startsWithMaskSingle_0_.substring(0, p_startsWithMaskSingle_1_.length());
/* 181 */       return equalsMaskSingle(s, p_startsWithMaskSingle_1_, p_startsWithMaskSingle_2_);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 186 */     return (p_startsWithMaskSingle_0_ == p_startsWithMaskSingle_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equalsMask(String p_equalsMask_0_, String[] p_equalsMask_1_, char p_equalsMask_2_) {
/* 192 */     for (int i = 0; i < p_equalsMask_1_.length; i++) {
/*     */       
/* 194 */       String s = p_equalsMask_1_[i];
/*     */       
/* 196 */       if (equalsMask(p_equalsMask_0_, s, p_equalsMask_2_))
/*     */       {
/* 198 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsMask(String p_equalsMask_0_, String p_equalsMask_1_, char p_equalsMask_2_) {
/* 207 */     if (p_equalsMask_1_ != null && p_equalsMask_0_ != null) {
/*     */       
/* 209 */       if (p_equalsMask_1_.indexOf(p_equalsMask_2_) < 0)
/*     */       {
/* 211 */         return p_equalsMask_1_.equals(p_equalsMask_0_);
/*     */       }
/*     */ 
/*     */       
/* 215 */       List<String> list = new ArrayList();
/* 216 */       char c = p_equalsMask_2_;
/*     */       
/* 218 */       if (p_equalsMask_1_.startsWith(c))
/*     */       {
/* 220 */         list.add("");
/*     */       }
/*     */       
/* 223 */       StringTokenizer stringtokenizer = new StringTokenizer(p_equalsMask_1_, c);
/*     */       
/* 225 */       while (stringtokenizer.hasMoreElements())
/*     */       {
/* 227 */         list.add(stringtokenizer.nextToken());
/*     */       }
/*     */       
/* 230 */       if (p_equalsMask_1_.endsWith(c))
/*     */       {
/* 232 */         list.add("");
/*     */       }
/*     */       
/* 235 */       String s1 = list.get(0);
/*     */       
/* 237 */       if (!p_equalsMask_0_.startsWith(s1))
/*     */       {
/* 239 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 243 */       String s2 = list.get(list.size() - 1);
/*     */       
/* 245 */       if (!p_equalsMask_0_.endsWith(s2))
/*     */       {
/* 247 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 251 */       int i = 0;
/*     */       
/* 253 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/* 255 */         String s3 = list.get(j);
/*     */         
/* 257 */         if (s3.length() > 0) {
/*     */           
/* 259 */           int k = p_equalsMask_0_.indexOf(s3, i);
/*     */           
/* 261 */           if (k < 0)
/*     */           {
/* 263 */             return false;
/*     */           }
/*     */           
/* 266 */           i = k + s3.length();
/*     */         } 
/*     */       } 
/*     */       
/* 270 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     return (p_equalsMask_1_ == p_equalsMask_0_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] split(String p_split_0_, String p_split_1_) {
/* 283 */     if (p_split_0_ != null && p_split_0_.length() > 0) {
/*     */       
/* 285 */       if (p_split_1_ == null)
/*     */       {
/* 287 */         return new String[] { p_split_0_ };
/*     */       }
/*     */ 
/*     */       
/* 291 */       List<String> list = new ArrayList();
/* 292 */       int i = 0;
/*     */       
/* 294 */       for (int j = 0; j < p_split_0_.length(); j++) {
/*     */         
/* 296 */         char c0 = p_split_0_.charAt(j);
/*     */         
/* 298 */         if (equals(c0, p_split_1_)) {
/*     */           
/* 300 */           list.add(p_split_0_.substring(i, j));
/* 301 */           i = j + 1;
/*     */         } 
/*     */       } 
/*     */       
/* 305 */       list.add(p_split_0_.substring(i, p_split_0_.length()));
/* 306 */       return list.<String>toArray(new String[list.size()]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 311 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equals(char p_equals_0_, String p_equals_1_) {
/* 317 */     for (int i = 0; i < p_equals_1_.length(); i++) {
/*     */       
/* 319 */       if (p_equals_1_.charAt(i) == p_equals_0_)
/*     */       {
/* 321 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 325 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsTrim(String p_equalsTrim_0_, String p_equalsTrim_1_) {
/* 330 */     if (p_equalsTrim_0_ != null)
/*     */     {
/* 332 */       p_equalsTrim_0_ = p_equalsTrim_0_.trim();
/*     */     }
/*     */     
/* 335 */     if (p_equalsTrim_1_ != null)
/*     */     {
/* 337 */       p_equalsTrim_1_ = p_equalsTrim_1_.trim();
/*     */     }
/*     */     
/* 340 */     return equals(p_equalsTrim_0_, p_equalsTrim_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEmpty(String p_isEmpty_0_) {
/* 345 */     if (p_isEmpty_0_ == null)
/*     */     {
/* 347 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 351 */     return (p_isEmpty_0_.trim().length() <= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String stringInc(String p_stringInc_0_) {
/* 357 */     int i = parseInt(p_stringInc_0_, -1);
/*     */     
/* 359 */     if (i == -1)
/*     */     {
/* 361 */       return "";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 366 */     int j = ++i;
/* 367 */     return (j.length() > p_stringInc_0_.length()) ? "" : fillLeft(i, p_stringInc_0_.length(), '0');
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int parseInt(String p_parseInt_0_, int p_parseInt_1_) {
/* 373 */     if (p_parseInt_0_ == null)
/*     */     {
/* 375 */       return p_parseInt_1_;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 381 */       return Integer.parseInt(p_parseInt_0_);
/*     */     }
/* 383 */     catch (NumberFormatException var3) {
/*     */       
/* 385 */       return p_parseInt_1_;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFilled(String p_isFilled_0_) {
/* 392 */     return !isEmpty(p_isFilled_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String addIfNotContains(String p_addIfNotContains_0_, String p_addIfNotContains_1_) {
/* 397 */     for (int i = 0; i < p_addIfNotContains_1_.length(); i++) {
/*     */       
/* 399 */       if (p_addIfNotContains_0_.indexOf(p_addIfNotContains_1_.charAt(i)) < 0)
/*     */       {
/* 401 */         p_addIfNotContains_0_ = String.valueOf(p_addIfNotContains_0_) + p_addIfNotContains_1_.charAt(i);
/*     */       }
/*     */     } 
/*     */     
/* 405 */     return p_addIfNotContains_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fillLeft(String p_fillLeft_0_, int p_fillLeft_1_, char p_fillLeft_2_) {
/* 410 */     if (p_fillLeft_0_ == null)
/*     */     {
/* 412 */       p_fillLeft_0_ = "";
/*     */     }
/*     */     
/* 415 */     if (p_fillLeft_0_.length() >= p_fillLeft_1_)
/*     */     {
/* 417 */       return p_fillLeft_0_;
/*     */     }
/*     */ 
/*     */     
/* 421 */     StringBuffer stringbuffer = new StringBuffer();
/* 422 */     int i = p_fillLeft_1_ - p_fillLeft_0_.length();
/*     */     
/* 424 */     while (stringbuffer.length() < i)
/*     */     {
/* 426 */       stringbuffer.append(p_fillLeft_2_);
/*     */     }
/*     */     
/* 429 */     return String.valueOf(stringbuffer.toString()) + p_fillLeft_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fillRight(String p_fillRight_0_, int p_fillRight_1_, char p_fillRight_2_) {
/* 435 */     if (p_fillRight_0_ == null)
/*     */     {
/* 437 */       p_fillRight_0_ = "";
/*     */     }
/*     */     
/* 440 */     if (p_fillRight_0_.length() >= p_fillRight_1_)
/*     */     {
/* 442 */       return p_fillRight_0_;
/*     */     }
/*     */ 
/*     */     
/* 446 */     StringBuffer stringbuffer = new StringBuffer(p_fillRight_0_);
/*     */     
/* 448 */     while (stringbuffer.length() < p_fillRight_1_)
/*     */     {
/* 450 */       stringbuffer.append(p_fillRight_2_);
/*     */     }
/*     */     
/* 453 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean equals(Object p_equals_0_, Object p_equals_1_) {
/* 459 */     if (p_equals_0_ == p_equals_1_)
/*     */     {
/* 461 */       return true;
/*     */     }
/* 463 */     if (p_equals_0_ != null && p_equals_0_.equals(p_equals_1_))
/*     */     {
/* 465 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 469 */     return (p_equals_1_ != null && p_equals_1_.equals(p_equals_0_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean startsWith(String p_startsWith_0_, String[] p_startsWith_1_) {
/* 475 */     if (p_startsWith_0_ == null)
/*     */     {
/* 477 */       return false;
/*     */     }
/* 479 */     if (p_startsWith_1_ == null)
/*     */     {
/* 481 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 485 */     for (int i = 0; i < p_startsWith_1_.length; i++) {
/*     */       
/* 487 */       String s = p_startsWith_1_[i];
/*     */       
/* 489 */       if (p_startsWith_0_.startsWith(s))
/*     */       {
/* 491 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 495 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean endsWith(String p_endsWith_0_, String[] p_endsWith_1_) {
/* 501 */     if (p_endsWith_0_ == null)
/*     */     {
/* 503 */       return false;
/*     */     }
/* 505 */     if (p_endsWith_1_ == null)
/*     */     {
/* 507 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 511 */     for (int i = 0; i < p_endsWith_1_.length; i++) {
/*     */       
/* 513 */       String s = p_endsWith_1_[i];
/*     */       
/* 515 */       if (p_endsWith_0_.endsWith(s))
/*     */       {
/* 517 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 521 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefix(String p_removePrefix_0_, String p_removePrefix_1_) {
/* 527 */     if (p_removePrefix_0_ != null && p_removePrefix_1_ != null) {
/*     */       
/* 529 */       if (p_removePrefix_0_.startsWith(p_removePrefix_1_))
/*     */       {
/* 531 */         p_removePrefix_0_ = p_removePrefix_0_.substring(p_removePrefix_1_.length());
/*     */       }
/*     */       
/* 534 */       return p_removePrefix_0_;
/*     */     } 
/*     */ 
/*     */     
/* 538 */     return p_removePrefix_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeSuffix(String p_removeSuffix_0_, String p_removeSuffix_1_) {
/* 544 */     if (p_removeSuffix_0_ != null && p_removeSuffix_1_ != null) {
/*     */       
/* 546 */       if (p_removeSuffix_0_.endsWith(p_removeSuffix_1_))
/*     */       {
/* 548 */         p_removeSuffix_0_ = p_removeSuffix_0_.substring(0, p_removeSuffix_0_.length() - p_removeSuffix_1_.length());
/*     */       }
/*     */       
/* 551 */       return p_removeSuffix_0_;
/*     */     } 
/*     */ 
/*     */     
/* 555 */     return p_removeSuffix_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replaceSuffix(String p_replaceSuffix_0_, String p_replaceSuffix_1_, String p_replaceSuffix_2_) {
/* 561 */     if (p_replaceSuffix_0_ != null && p_replaceSuffix_1_ != null) {
/*     */       
/* 563 */       if (!p_replaceSuffix_0_.endsWith(p_replaceSuffix_1_))
/*     */       {
/* 565 */         return p_replaceSuffix_0_;
/*     */       }
/*     */ 
/*     */       
/* 569 */       if (p_replaceSuffix_2_ == null)
/*     */       {
/* 571 */         p_replaceSuffix_2_ = "";
/*     */       }
/*     */       
/* 574 */       p_replaceSuffix_0_ = p_replaceSuffix_0_.substring(0, p_replaceSuffix_0_.length() - p_replaceSuffix_1_.length());
/* 575 */       return String.valueOf(p_replaceSuffix_0_) + p_replaceSuffix_2_;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 580 */     return p_replaceSuffix_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String replacePrefix(String p_replacePrefix_0_, String p_replacePrefix_1_, String p_replacePrefix_2_) {
/* 586 */     if (p_replacePrefix_0_ != null && p_replacePrefix_1_ != null) {
/*     */       
/* 588 */       if (!p_replacePrefix_0_.startsWith(p_replacePrefix_1_))
/*     */       {
/* 590 */         return p_replacePrefix_0_;
/*     */       }
/*     */ 
/*     */       
/* 594 */       if (p_replacePrefix_2_ == null)
/*     */       {
/* 596 */         p_replacePrefix_2_ = "";
/*     */       }
/*     */       
/* 599 */       p_replacePrefix_0_ = p_replacePrefix_0_.substring(p_replacePrefix_1_.length());
/* 600 */       return String.valueOf(p_replacePrefix_2_) + p_replacePrefix_0_;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 605 */     return p_replacePrefix_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int findPrefix(String[] p_findPrefix_0_, String p_findPrefix_1_) {
/* 611 */     if (p_findPrefix_0_ != null && p_findPrefix_1_ != null) {
/*     */       
/* 613 */       for (int i = 0; i < p_findPrefix_0_.length; i++) {
/*     */         
/* 615 */         String s = p_findPrefix_0_[i];
/*     */         
/* 617 */         if (s.startsWith(p_findPrefix_1_))
/*     */         {
/* 619 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 623 */       return -1;
/*     */     } 
/*     */ 
/*     */     
/* 627 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int findSuffix(String[] p_findSuffix_0_, String p_findSuffix_1_) {
/* 633 */     if (p_findSuffix_0_ != null && p_findSuffix_1_ != null) {
/*     */       
/* 635 */       for (int i = 0; i < p_findSuffix_0_.length; i++) {
/*     */         
/* 637 */         String s = p_findSuffix_0_[i];
/*     */         
/* 639 */         if (s.endsWith(p_findSuffix_1_))
/*     */         {
/* 641 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 645 */       return -1;
/*     */     } 
/*     */ 
/*     */     
/* 649 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] remove(String[] p_remove_0_, int p_remove_1_, int p_remove_2_) {
/* 655 */     if (p_remove_0_ == null)
/*     */     {
/* 657 */       return p_remove_0_;
/*     */     }
/* 659 */     if (p_remove_2_ > 0 && p_remove_1_ < p_remove_0_.length) {
/*     */       
/* 661 */       if (p_remove_1_ >= p_remove_2_)
/*     */       {
/* 663 */         return p_remove_0_;
/*     */       }
/*     */ 
/*     */       
/* 667 */       List<String> list = new ArrayList<>(p_remove_0_.length);
/*     */       
/* 669 */       for (int i = 0; i < p_remove_0_.length; i++) {
/*     */         
/* 671 */         String s = p_remove_0_[i];
/*     */         
/* 673 */         if (i < p_remove_1_ || i >= p_remove_2_)
/*     */         {
/* 675 */           list.add(s);
/*     */         }
/*     */       } 
/*     */       
/* 679 */       String[] astring = list.<String>toArray(new String[list.size()]);
/* 680 */       return astring;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 685 */     return p_remove_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeSuffix(String p_removeSuffix_0_, String[] p_removeSuffix_1_) {
/* 691 */     if (p_removeSuffix_0_ != null && p_removeSuffix_1_ != null) {
/*     */       
/* 693 */       int i = p_removeSuffix_0_.length();
/*     */       
/* 695 */       for (int j = 0; j < p_removeSuffix_1_.length; j++) {
/*     */         
/* 697 */         String s = p_removeSuffix_1_[j];
/* 698 */         p_removeSuffix_0_ = removeSuffix(p_removeSuffix_0_, s);
/*     */         
/* 700 */         if (p_removeSuffix_0_.length() != i) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 706 */       return p_removeSuffix_0_;
/*     */     } 
/*     */ 
/*     */     
/* 710 */     return p_removeSuffix_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefix(String p_removePrefix_0_, String[] p_removePrefix_1_) {
/* 716 */     if (p_removePrefix_0_ != null && p_removePrefix_1_ != null) {
/*     */       
/* 718 */       int i = p_removePrefix_0_.length();
/*     */       
/* 720 */       for (int j = 0; j < p_removePrefix_1_.length; j++) {
/*     */         
/* 722 */         String s = p_removePrefix_1_[j];
/* 723 */         p_removePrefix_0_ = removePrefix(p_removePrefix_0_, s);
/*     */         
/* 725 */         if (p_removePrefix_0_.length() != i) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 731 */       return p_removePrefix_0_;
/*     */     } 
/*     */ 
/*     */     
/* 735 */     return p_removePrefix_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removePrefixSuffix(String p_removePrefixSuffix_0_, String[] p_removePrefixSuffix_1_, String[] p_removePrefixSuffix_2_) {
/* 741 */     p_removePrefixSuffix_0_ = removePrefix(p_removePrefixSuffix_0_, p_removePrefixSuffix_1_);
/* 742 */     p_removePrefixSuffix_0_ = removeSuffix(p_removePrefixSuffix_0_, p_removePrefixSuffix_2_);
/* 743 */     return p_removePrefixSuffix_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String removePrefixSuffix(String p_removePrefixSuffix_0_, String p_removePrefixSuffix_1_, String p_removePrefixSuffix_2_) {
/* 748 */     return removePrefixSuffix(p_removePrefixSuffix_0_, new String[] { p_removePrefixSuffix_1_ }, new String[] { p_removePrefixSuffix_2_ });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSegment(String p_getSegment_0_, String p_getSegment_1_, String p_getSegment_2_) {
/* 753 */     if (p_getSegment_0_ != null && p_getSegment_1_ != null && p_getSegment_2_ != null) {
/*     */       
/* 755 */       int i = p_getSegment_0_.indexOf(p_getSegment_1_);
/*     */       
/* 757 */       if (i < 0)
/*     */       {
/* 759 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 763 */       int j = p_getSegment_0_.indexOf(p_getSegment_2_, i);
/* 764 */       return (j < 0) ? null : p_getSegment_0_.substring(i, j + p_getSegment_2_.length());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 769 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String addSuffixCheck(String p_addSuffixCheck_0_, String p_addSuffixCheck_1_) {
/* 775 */     if (p_addSuffixCheck_0_ != null && p_addSuffixCheck_1_ != null)
/*     */     {
/* 777 */       return p_addSuffixCheck_0_.endsWith(p_addSuffixCheck_1_) ? p_addSuffixCheck_0_ : (String.valueOf(p_addSuffixCheck_0_) + p_addSuffixCheck_1_);
/*     */     }
/*     */ 
/*     */     
/* 781 */     return p_addSuffixCheck_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String addPrefixCheck(String p_addPrefixCheck_0_, String p_addPrefixCheck_1_) {
/* 787 */     if (p_addPrefixCheck_0_ != null && p_addPrefixCheck_1_ != null)
/*     */     {
/* 789 */       return p_addPrefixCheck_0_.endsWith(p_addPrefixCheck_1_) ? p_addPrefixCheck_0_ : (String.valueOf(p_addPrefixCheck_1_) + p_addPrefixCheck_0_);
/*     */     }
/*     */ 
/*     */     
/* 793 */     return p_addPrefixCheck_0_;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\StrUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */