/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.CharArrayReader;
/*     */ import java.io.CharArrayWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import optifine.Config;
/*     */ import optifine.StrUtils;
/*     */ 
/*     */ 
/*     */ public class ShaderPackParser
/*     */ {
/*  30 */   private static final Pattern PATTERN_VERSION = Pattern.compile("^\\s*#version\\s+.*$");
/*  31 */   private static final Pattern PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
/*  32 */   private static final Set<String> setConstNames = makeSetConstNames();
/*     */ 
/*     */   
/*     */   public static ShaderOption[] parseShaderPackOptions(IShaderPack shaderPack, String[] programNames, List<Integer> listDimensions) {
/*  36 */     if (shaderPack == null)
/*     */     {
/*  38 */       return new ShaderOption[0];
/*     */     }
/*     */ 
/*     */     
/*  42 */     Map<String, ShaderOption> map = new HashMap<>();
/*  43 */     collectShaderOptions(shaderPack, "/shaders", programNames, map);
/*  44 */     Iterator<Integer> iterator = listDimensions.iterator();
/*     */     
/*  46 */     while (iterator.hasNext()) {
/*     */       
/*  48 */       int i = ((Integer)iterator.next()).intValue();
/*  49 */       String s = "/shaders/world" + i;
/*  50 */       collectShaderOptions(shaderPack, s, programNames, map);
/*     */     } 
/*     */     
/*  53 */     Collection<ShaderOption> collection = map.values();
/*  54 */     ShaderOption[] ashaderoption = collection.<ShaderOption>toArray(new ShaderOption[collection.size()]);
/*  55 */     Comparator<ShaderOption> comparator = new Comparator<ShaderOption>()
/*     */       {
/*     */         public int compare(ShaderOption o1, ShaderOption o2)
/*     */         {
/*  59 */           return o1.getName().compareToIgnoreCase(o2.getName());
/*     */         }
/*     */       };
/*  62 */     Arrays.sort(ashaderoption, comparator);
/*  63 */     return ashaderoption;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void collectShaderOptions(IShaderPack shaderPack, String dir, String[] programNames, Map<String, ShaderOption> mapOptions) {
/*  69 */     for (int i = 0; i < programNames.length; i++) {
/*     */       
/*  71 */       String s = programNames[i];
/*     */       
/*  73 */       if (!s.equals("")) {
/*     */         
/*  75 */         String s1 = String.valueOf(dir) + "/" + s + ".vsh";
/*  76 */         String s2 = String.valueOf(dir) + "/" + s + ".fsh";
/*  77 */         collectShaderOptions(shaderPack, s1, mapOptions);
/*  78 */         collectShaderOptions(shaderPack, s2, mapOptions);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void collectShaderOptions(IShaderPack sp, String path, Map<String, ShaderOption> mapOptions) {
/*  85 */     String[] astring = getLines(sp, path);
/*     */     
/*  87 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  89 */       String s = astring[i];
/*  90 */       ShaderOption shaderoption = getShaderOption(s, path);
/*     */       
/*  92 */       if (shaderoption != null && !shaderoption.getName().startsWith(ShaderMacros.getPrefixMacro()) && (!shaderoption.checkUsed() || isOptionUsed(shaderoption, astring))) {
/*     */         
/*  94 */         String s1 = shaderoption.getName();
/*  95 */         ShaderOption shaderoption1 = mapOptions.get(s1);
/*     */         
/*  97 */         if (shaderoption1 != null) {
/*     */           
/*  99 */           if (!Config.equals(shaderoption1.getValueDefault(), shaderoption.getValueDefault())) {
/*     */             
/* 101 */             Config.warn("Ambiguous shader option: " + shaderoption.getName());
/* 102 */             Config.warn(" - in " + Config.arrayToString((Object[])shaderoption1.getPaths()) + ": " + shaderoption1.getValueDefault());
/* 103 */             Config.warn(" - in " + Config.arrayToString((Object[])shaderoption.getPaths()) + ": " + shaderoption.getValueDefault());
/* 104 */             shaderoption1.setEnabled(false);
/*     */           } 
/*     */           
/* 107 */           if (shaderoption1.getDescription() == null || shaderoption1.getDescription().length() <= 0)
/*     */           {
/* 109 */             shaderoption1.setDescription(shaderoption.getDescription());
/*     */           }
/*     */           
/* 112 */           shaderoption1.addPaths(shaderoption.getPaths());
/*     */         }
/*     */         else {
/*     */           
/* 116 */           mapOptions.put(s1, shaderoption);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isOptionUsed(ShaderOption so, String[] lines) {
/* 124 */     for (int i = 0; i < lines.length; i++) {
/*     */       
/* 126 */       String s = lines[i];
/*     */       
/* 128 */       if (so.isUsedInLine(s))
/*     */       {
/* 130 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getLines(IShaderPack sp, String path) {
/*     */     try {
/* 141 */       List<String> list = new ArrayList<>();
/* 142 */       String s = loadFile(path, sp, 0, list, 0);
/*     */       
/* 144 */       if (s == null)
/*     */       {
/* 146 */         return new String[0];
/*     */       }
/*     */ 
/*     */       
/* 150 */       ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(s.getBytes());
/* 151 */       String[] astring = Config.readLines(bytearrayinputstream);
/* 152 */       return astring;
/*     */     
/*     */     }
/* 155 */     catch (IOException ioexception) {
/*     */       
/* 157 */       Config.dbg(String.valueOf(ioexception.getClass().getName()) + ": " + ioexception.getMessage());
/* 158 */       return new String[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static ShaderOption getShaderOption(String line, String path) {
/* 164 */     ShaderOption shaderoption = null;
/*     */     
/* 166 */     if (shaderoption == null)
/*     */     {
/* 168 */       shaderoption = ShaderOptionSwitch.parseOption(line, path);
/*     */     }
/*     */     
/* 171 */     if (shaderoption == null)
/*     */     {
/* 173 */       shaderoption = ShaderOptionVariable.parseOption(line, path);
/*     */     }
/*     */     
/* 176 */     if (shaderoption != null)
/*     */     {
/* 178 */       return shaderoption;
/*     */     }
/*     */ 
/*     */     
/* 182 */     if (shaderoption == null)
/*     */     {
/* 184 */       shaderoption = ShaderOptionSwitchConst.parseOption(line, path);
/*     */     }
/*     */     
/* 187 */     if (shaderoption == null)
/*     */     {
/* 189 */       shaderoption = ShaderOptionVariableConst.parseOption(line, path);
/*     */     }
/*     */     
/* 192 */     return (shaderoption != null && setConstNames.contains(shaderoption.getName())) ? shaderoption : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Set<String> makeSetConstNames() {
/* 198 */     Set<String> set = new HashSet<>();
/* 199 */     set.add("shadowMapResolution");
/* 200 */     set.add("shadowMapFov");
/* 201 */     set.add("shadowDistance");
/* 202 */     set.add("shadowDistanceRenderMul");
/* 203 */     set.add("shadowIntervalSize");
/* 204 */     set.add("generateShadowMipmap");
/* 205 */     set.add("generateShadowColorMipmap");
/* 206 */     set.add("shadowHardwareFiltering");
/* 207 */     set.add("shadowHardwareFiltering0");
/* 208 */     set.add("shadowHardwareFiltering1");
/* 209 */     set.add("shadowtex0Mipmap");
/* 210 */     set.add("shadowtexMipmap");
/* 211 */     set.add("shadowtex1Mipmap");
/* 212 */     set.add("shadowcolor0Mipmap");
/* 213 */     set.add("shadowColor0Mipmap");
/* 214 */     set.add("shadowcolor1Mipmap");
/* 215 */     set.add("shadowColor1Mipmap");
/* 216 */     set.add("shadowtex0Nearest");
/* 217 */     set.add("shadowtexNearest");
/* 218 */     set.add("shadow0MinMagNearest");
/* 219 */     set.add("shadowtex1Nearest");
/* 220 */     set.add("shadow1MinMagNearest");
/* 221 */     set.add("shadowcolor0Nearest");
/* 222 */     set.add("shadowColor0Nearest");
/* 223 */     set.add("shadowColor0MinMagNearest");
/* 224 */     set.add("shadowcolor1Nearest");
/* 225 */     set.add("shadowColor1Nearest");
/* 226 */     set.add("shadowColor1MinMagNearest");
/* 227 */     set.add("wetnessHalflife");
/* 228 */     set.add("drynessHalflife");
/* 229 */     set.add("eyeBrightnessHalflife");
/* 230 */     set.add("centerDepthHalflife");
/* 231 */     set.add("sunPathRotation");
/* 232 */     set.add("ambientOcclusionLevel");
/* 233 */     set.add("superSamplingLevel");
/* 234 */     set.add("noiseTextureResolution");
/* 235 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShaderProfile[] parseProfiles(Properties props, ShaderOption[] shaderOptions) {
/* 240 */     String s = "profile.";
/* 241 */     List<ShaderProfile> list = new ArrayList<>();
/*     */     
/* 243 */     for (Object s10 : props.keySet()) {
/*     */       
/* 245 */       String s1 = (String)s10;
/* 246 */       if (s1.startsWith(s)) {
/*     */         
/* 248 */         String s2 = s1.substring(s.length());
/* 249 */         props.getProperty(s1);
/* 250 */         Set<String> set = new HashSet<>();
/* 251 */         ShaderProfile shaderprofile = parseProfile(s2, props, set, shaderOptions);
/*     */         
/* 253 */         if (shaderprofile != null)
/*     */         {
/* 255 */           list.add(shaderprofile);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 260 */     if (list.size() <= 0)
/*     */     {
/* 262 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 266 */     ShaderProfile[] ashaderprofile = list.<ShaderProfile>toArray(new ShaderProfile[list.size()]);
/* 267 */     return ashaderprofile;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<String> parseOptionSliders(Properties props, ShaderOption[] shaderOptions) {
/* 273 */     Set<String> set = new HashSet<>();
/* 274 */     String s = props.getProperty("sliders");
/*     */     
/* 276 */     if (s == null)
/*     */     {
/* 278 */       return set;
/*     */     }
/*     */ 
/*     */     
/* 282 */     String[] astring = Config.tokenize(s, " ");
/*     */     
/* 284 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 286 */       String s1 = astring[i];
/* 287 */       ShaderOption shaderoption = ShaderUtils.getShaderOption(s1, shaderOptions);
/*     */       
/* 289 */       if (shaderoption == null) {
/*     */         
/* 291 */         Config.warn("Invalid shader option: " + s1);
/*     */       }
/*     */       else {
/*     */         
/* 295 */         set.add(s1);
/*     */       } 
/*     */     } 
/*     */     
/* 299 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ShaderProfile parseProfile(String name, Properties props, Set<String> parsedProfiles, ShaderOption[] shaderOptions) {
/* 305 */     String s = "profile.";
/* 306 */     String s1 = String.valueOf(s) + name;
/*     */     
/* 308 */     if (parsedProfiles.contains(s1)) {
/*     */       
/* 310 */       Config.warn("[Shaders] Profile already parsed: " + name);
/* 311 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 315 */     parsedProfiles.add(name);
/* 316 */     ShaderProfile shaderprofile = new ShaderProfile(name);
/* 317 */     String s2 = props.getProperty(s1);
/* 318 */     String[] astring = Config.tokenize(s2, " ");
/*     */     
/* 320 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 322 */       String s3 = astring[i];
/*     */       
/* 324 */       if (s3.startsWith(s)) {
/*     */         
/* 326 */         String s6 = s3.substring(s.length());
/* 327 */         ShaderProfile shaderprofile1 = parseProfile(s6, props, parsedProfiles, shaderOptions);
/*     */         
/* 329 */         if (shaderprofile != null)
/*     */         {
/* 331 */           shaderprofile.addOptionValues(shaderprofile1);
/* 332 */           shaderprofile.addDisabledPrograms(shaderprofile1.getDisabledPrograms());
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 337 */         String[] astring1 = Config.tokenize(s3, ":=");
/*     */         
/* 339 */         if (astring1.length == 1) {
/*     */           
/* 341 */           String s7 = astring1[0];
/* 342 */           boolean flag = true;
/*     */           
/* 344 */           if (s7.startsWith("!")) {
/*     */             
/* 346 */             flag = false;
/* 347 */             s7 = s7.substring(1);
/*     */           } 
/*     */           
/* 350 */           String s8 = "program.";
/*     */           
/* 352 */           if (!flag && s7.startsWith("program.")) {
/*     */             
/* 354 */             String s9 = s7.substring(s8.length());
/*     */             
/* 356 */             if (!Shaders.isProgramPath(s9))
/*     */             {
/* 358 */               Config.warn("Invalid program: " + s9 + " in profile: " + shaderprofile.getName());
/*     */             }
/*     */             else
/*     */             {
/* 362 */               shaderprofile.addDisabledProgram(s9);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 367 */             ShaderOption shaderoption1 = ShaderUtils.getShaderOption(s7, shaderOptions);
/*     */             
/* 369 */             if (!(shaderoption1 instanceof ShaderOptionSwitch))
/*     */             {
/* 371 */               Config.warn("[Shaders] Invalid option: " + s7);
/*     */             }
/*     */             else
/*     */             {
/* 375 */               shaderprofile.addOptionValue(s7, String.valueOf(flag));
/* 376 */               shaderoption1.setVisible(true);
/*     */             }
/*     */           
/*     */           } 
/* 380 */         } else if (astring1.length != 2) {
/*     */           
/* 382 */           Config.warn("[Shaders] Invalid option value: " + s3);
/*     */         }
/*     */         else {
/*     */           
/* 386 */           String s4 = astring1[0];
/* 387 */           String s5 = astring1[1];
/* 388 */           ShaderOption shaderoption = ShaderUtils.getShaderOption(s4, shaderOptions);
/*     */           
/* 390 */           if (shaderoption == null) {
/*     */             
/* 392 */             Config.warn("[Shaders] Invalid option: " + s3);
/*     */           }
/* 394 */           else if (!shaderoption.isValidValue(s5)) {
/*     */             
/* 396 */             Config.warn("[Shaders] Invalid value: " + s3);
/*     */           }
/*     */           else {
/*     */             
/* 400 */             shaderoption.setVisible(true);
/* 401 */             shaderprofile.addOptionValue(s4, s5);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 407 */     return shaderprofile;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, ScreenShaderOptions> parseGuiScreens(Properties props, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
/* 413 */     Map<String, ScreenShaderOptions> map = new HashMap<>();
/* 414 */     parseGuiScreen("screen", props, map, shaderProfiles, shaderOptions);
/* 415 */     return map.isEmpty() ? null : map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean parseGuiScreen(String key, Properties props, Map<String, ScreenShaderOptions> map, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
/* 420 */     String s = props.getProperty(key);
/*     */     
/* 422 */     if (s == null)
/*     */     {
/* 424 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 428 */     List<ShaderOption> list = new ArrayList<>();
/* 429 */     Set<String> set = new HashSet<>();
/* 430 */     String[] astring = Config.tokenize(s, " ");
/*     */     
/* 432 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 434 */       String s1 = astring[i];
/*     */       
/* 436 */       if (s1.equals("<empty>")) {
/*     */         
/* 438 */         list.add(null);
/*     */       }
/* 440 */       else if (set.contains(s1)) {
/*     */         
/* 442 */         Config.warn("[Shaders] Duplicate option: " + s1 + ", key: " + key);
/*     */       }
/*     */       else {
/*     */         
/* 446 */         set.add(s1);
/*     */         
/* 448 */         if (s1.equals("<profile>")) {
/*     */           
/* 450 */           if (shaderProfiles == null)
/*     */           {
/* 452 */             Config.warn("[Shaders] Option profile can not be used, no profiles defined: " + s1 + ", key: " + key);
/*     */           }
/*     */           else
/*     */           {
/* 456 */             ShaderOptionProfile shaderoptionprofile = new ShaderOptionProfile(shaderProfiles, shaderOptions);
/* 457 */             list.add(shaderoptionprofile);
/*     */           }
/*     */         
/* 460 */         } else if (s1.equals("*")) {
/*     */           
/* 462 */           ShaderOption shaderoption1 = new ShaderOptionRest("<rest>");
/* 463 */           list.add(shaderoption1);
/*     */         }
/* 465 */         else if (s1.startsWith("[") && s1.endsWith("]")) {
/*     */           
/* 467 */           String s3 = StrUtils.removePrefixSuffix(s1, "[", "]");
/*     */           
/* 469 */           if (!s3.matches("^[a-zA-Z0-9_]+$"))
/*     */           {
/* 471 */             Config.warn("[Shaders] Invalid screen: " + s1 + ", key: " + key);
/*     */           }
/* 473 */           else if (!parseGuiScreen("screen." + s3, props, map, shaderProfiles, shaderOptions))
/*     */           {
/* 475 */             Config.warn("[Shaders] Invalid screen: " + s1 + ", key: " + key);
/*     */           }
/*     */           else
/*     */           {
/* 479 */             ShaderOptionScreen shaderoptionscreen = new ShaderOptionScreen(s3);
/* 480 */             list.add(shaderoptionscreen);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 485 */           ShaderOption shaderoption = ShaderUtils.getShaderOption(s1, shaderOptions);
/*     */           
/* 487 */           if (shaderoption == null) {
/*     */             
/* 489 */             Config.warn("[Shaders] Invalid option: " + s1 + ", key: " + key);
/* 490 */             list.add(null);
/*     */           }
/*     */           else {
/*     */             
/* 494 */             shaderoption.setVisible(true);
/* 495 */             list.add(shaderoption);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 501 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 502 */     String s2 = props.getProperty(String.valueOf(key) + ".columns");
/* 503 */     int j = Config.parseInt(s2, 2);
/* 504 */     ScreenShaderOptions screenshaderoptions = new ScreenShaderOptions(key, ashaderoption, j);
/* 505 */     map.put(key, screenshaderoptions);
/* 506 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedReader resolveIncludes(BufferedReader reader, String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException {
/* 512 */     String s = "/";
/* 513 */     int i = filePath.lastIndexOf("/");
/*     */     
/* 515 */     if (i >= 0)
/*     */     {
/* 517 */       s = filePath.substring(0, i);
/*     */     }
/*     */     
/* 520 */     CharArrayWriter chararraywriter = new CharArrayWriter();
/* 521 */     int j = -1;
/* 522 */     Set<String> set = new LinkedHashSet<>();
/* 523 */     int k = 1;
/*     */ 
/*     */     
/*     */     while (true) {
/* 527 */       String s1 = reader.readLine();
/*     */       
/* 529 */       if (s1 == null) {
/*     */         
/* 531 */         char[] achar = chararraywriter.toCharArray();
/*     */         
/* 533 */         if (j >= 0 && set.size() > 0) {
/*     */           
/* 535 */           StringBuilder stringbuilder = new StringBuilder();
/*     */           
/* 537 */           for (String s7 : set) {
/*     */             
/* 539 */             stringbuilder.append("#define ");
/* 540 */             stringbuilder.append(s7);
/* 541 */             stringbuilder.append("\n");
/*     */           } 
/*     */           
/* 544 */           String s6 = stringbuilder.toString();
/* 545 */           StringBuilder stringbuilder1 = new StringBuilder(new String(achar));
/* 546 */           stringbuilder1.insert(j, s6);
/* 547 */           String s10 = stringbuilder1.toString();
/* 548 */           achar = s10.toCharArray();
/*     */         } 
/*     */         
/* 551 */         CharArrayReader chararrayreader = new CharArrayReader(achar);
/* 552 */         return new BufferedReader(chararrayreader);
/*     */       } 
/*     */       
/* 555 */       if (j < 0) {
/*     */         
/* 557 */         Matcher matcher = PATTERN_VERSION.matcher(s1);
/*     */         
/* 559 */         if (matcher.matches()) {
/*     */           
/* 561 */           String s2 = ShaderMacros.getMacroLines();
/* 562 */           String s3 = String.valueOf(s1) + "\n" + s2;
/* 563 */           String s4 = "#line " + (k + 1) + " " + fileIndex;
/* 564 */           s1 = String.valueOf(s3) + s4;
/* 565 */           j = chararraywriter.size() + s3.length();
/*     */         } 
/*     */       } 
/*     */       
/* 569 */       Matcher matcher1 = PATTERN_INCLUDE.matcher(s1);
/*     */       
/* 571 */       if (matcher1.matches()) {
/*     */         
/* 573 */         String s5 = matcher1.group(1);
/* 574 */         boolean flag = s5.startsWith("/");
/* 575 */         String s8 = flag ? ("/shaders" + s5) : (String.valueOf(s) + "/" + s5);
/*     */         
/* 577 */         if (!listFiles.contains(s8))
/*     */         {
/* 579 */           listFiles.add(s8);
/*     */         }
/*     */         
/* 582 */         int l = listFiles.indexOf(s8) + 1;
/* 583 */         s1 = loadFile(s8, shaderPack, l, listFiles, includeLevel);
/*     */         
/* 585 */         if (s1 == null)
/*     */         {
/* 587 */           throw new IOException("Included file not found: " + filePath);
/*     */         }
/*     */         
/* 590 */         if (s1.endsWith("\n"))
/*     */         {
/* 592 */           s1 = s1.substring(0, s1.length() - 1);
/*     */         }
/*     */         
/* 595 */         s1 = "#line 1 " + l + "\n" + s1 + "\n#line " + (k + 1) + " " + fileIndex;
/*     */       } 
/*     */       
/* 598 */       if (j >= 0 && s1.contains(ShaderMacros.getPrefixMacro())) {
/*     */         
/* 600 */         String[] astring = findExtensions(s1, ShaderMacros.getExtensions());
/*     */         
/* 602 */         for (int i1 = 0; i1 < astring.length; i1++) {
/*     */           
/* 604 */           String s9 = astring[i1];
/* 605 */           set.add(s9);
/*     */         } 
/*     */       } 
/*     */       
/* 609 */       chararraywriter.write(s1);
/* 610 */       chararraywriter.write("\n");
/* 611 */       k++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] findExtensions(String line, String[] extensions) {
/* 617 */     List<String> list = new ArrayList<>();
/*     */     
/* 619 */     for (int i = 0; i < extensions.length; i++) {
/*     */       
/* 621 */       String s = extensions[i];
/*     */       
/* 623 */       if (line.contains(s))
/*     */       {
/* 625 */         list.add(s);
/*     */       }
/*     */     } 
/*     */     
/* 629 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 630 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String loadFile(String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException {
/* 635 */     if (includeLevel >= 10)
/*     */     {
/* 637 */       throw new IOException("#include depth exceeded: " + includeLevel + ", file: " + filePath);
/*     */     }
/*     */ 
/*     */     
/* 641 */     includeLevel++;
/* 642 */     InputStream inputstream = shaderPack.getResourceAsStream(filePath);
/*     */     
/* 644 */     if (inputstream == null)
/*     */     {
/* 646 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 650 */     InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "ASCII");
/* 651 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 652 */     bufferedreader = resolveIncludes(bufferedreader, filePath, shaderPack, fileIndex, listFiles, includeLevel);
/* 653 */     CharArrayWriter chararraywriter = new CharArrayWriter();
/*     */ 
/*     */     
/*     */     while (true) {
/* 657 */       String s = bufferedreader.readLine();
/*     */       
/* 659 */       if (s == null)
/*     */       {
/* 661 */         return chararraywriter.toString();
/*     */       }
/*     */       
/* 664 */       chararraywriter.write(s);
/* 665 */       chararraywriter.write("\n");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderPackParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */