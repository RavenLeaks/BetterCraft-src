/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.registry.RegistryNamespaced;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CraftingManager
/*     */ {
/*  37 */   private static final Logger field_192422_a = LogManager.getLogger();
/*     */   private static int field_193381_c;
/*  39 */   public static final RegistryNamespaced<ResourceLocation, IRecipe> field_193380_a = new RegistryNamespaced();
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean func_193377_a() {
/*     */     try {
/*  45 */       func_193379_a("armordye", new RecipesArmorDyes());
/*  46 */       func_193379_a("bookcloning", new RecipeBookCloning());
/*  47 */       func_193379_a("mapcloning", new RecipesMapCloning());
/*  48 */       func_193379_a("mapextending", new RecipesMapExtending());
/*  49 */       func_193379_a("fireworks", new RecipeFireworks());
/*  50 */       func_193379_a("repairitem", new RecipeRepairItem());
/*  51 */       func_193379_a("tippedarrow", new RecipeTippedArrow());
/*  52 */       func_193379_a("bannerduplicate", new RecipesBanners.RecipeDuplicatePattern());
/*  53 */       func_193379_a("banneraddpattern", new RecipesBanners.RecipeAddPattern());
/*  54 */       func_193379_a("shielddecoration", new ShieldRecipes.Decoration());
/*  55 */       func_193379_a("shulkerboxcoloring", new ShulkerBoxRecipes.ShulkerBoxColoring());
/*  56 */       return func_192420_c();
/*     */     }
/*  58 */     catch (Throwable var1) {
/*     */       
/*  60 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean func_192420_c() {
/*     */     boolean flag1;
/*  66 */     FileSystem filesystem = null;
/*  67 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     try { URL url = CraftingManager.class.getResource("/assets/.mcassetsroot");
/*     */       
/*  74 */       if (url != null) {
/*     */         Path path;
/*  76 */         URI uri = url.toURI();
/*     */ 
/*     */         
/*  79 */         if ("file".equals(uri.getScheme())) {
/*     */           
/*  81 */           path = Paths.get(CraftingManager.class.getResource("/assets/minecraft/recipes").toURI());
/*     */         }
/*     */         else {
/*     */           
/*  85 */           if (!"jar".equals(uri.getScheme())) {
/*     */             
/*  87 */             field_192422_a.error("Unsupported scheme " + uri + " trying to list all recipes");
/*  88 */             boolean flag2 = false;
/*  89 */             return flag2;
/*     */           } 
/*     */           
/*  92 */           filesystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
/*  93 */           path = filesystem.getPath("/assets/minecraft/recipes", new String[0]);
/*     */         } 
/*     */         
/*  96 */         Iterator<Path> iterator = Files.walk(path, new java.nio.file.FileVisitOption[0]).iterator();
/*     */         
/*  98 */         while (iterator.hasNext()) {
/*     */           
/* 100 */           Path path1 = iterator.next();
/*     */           
/* 102 */           if ("json".equals(FilenameUtils.getExtension(path1.toString()))) {
/*     */             
/* 104 */             Path path2 = path.relativize(path1);
/* 105 */             String s = FilenameUtils.removeExtension(path2.toString()).replaceAll("\\\\", "/");
/* 106 */             ResourceLocation resourcelocation = new ResourceLocation(s);
/* 107 */             BufferedReader bufferedreader = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 115 */               bufferedreader = Files.newBufferedReader(path1);
/* 116 */               func_193379_a(s, func_193376_a((JsonObject)JsonUtils.func_193839_a(gson, bufferedreader, JsonObject.class)));
/*     */             }
/* 118 */             catch (JsonParseException jsonparseexception) {
/*     */               
/* 120 */               field_192422_a.error("Parsing error loading recipe " + resourcelocation, (Throwable)jsonparseexception);
/* 121 */               boolean flag = false;
/* 122 */               return flag;
/*     */             }
/* 124 */             catch (IOException ioexception) {
/*     */               
/* 126 */               field_192422_a.error("Couldn't read recipe " + resourcelocation + " from " + path1, ioexception);
/* 127 */               boolean flag = false;
/* 128 */               return flag;
/*     */             
/*     */             }
/*     */             finally {
/*     */               
/* 133 */               IOUtils.closeQuietly(bufferedreader);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 138 */         return true;
/*     */       } 
/*     */       
/* 141 */       field_192422_a.error("Couldn't find .mcassetsroot");
/*     */        }
/*     */     
/* 144 */     catch (IOException|java.net.URISyntaxException urisyntaxexception)
/*     */     
/* 146 */     { field_192422_a.error("Couldn't get a list of all recipe files", urisyntaxexception);
/* 147 */       flag1 = false;
/* 148 */       return flag1; }
/*     */     
/*     */     finally
/*     */     
/* 152 */     { IOUtils.closeQuietly(filesystem); }  IOUtils.closeQuietly(filesystem);
/*     */ 
/*     */     
/* 155 */     return flag1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static IRecipe func_193376_a(JsonObject p_193376_0_) {
/* 160 */     String s = JsonUtils.getString(p_193376_0_, "type");
/*     */     
/* 162 */     if ("crafting_shaped".equals(s))
/*     */     {
/* 164 */       return ShapedRecipes.func_193362_a(p_193376_0_);
/*     */     }
/* 166 */     if ("crafting_shapeless".equals(s))
/*     */     {
/* 168 */       return ShapelessRecipes.func_193363_a(p_193376_0_);
/*     */     }
/*     */ 
/*     */     
/* 172 */     throw new JsonSyntaxException("Invalid or unsupported recipe type '" + s + "'");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void func_193379_a(String p_193379_0_, IRecipe p_193379_1_) {
/* 178 */     func_193372_a(new ResourceLocation(p_193379_0_), p_193379_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_193372_a(ResourceLocation p_193372_0_, IRecipe p_193372_1_) {
/* 183 */     if (field_193380_a.containsKey(p_193372_0_))
/*     */     {
/* 185 */       throw new IllegalStateException("Duplicate recipe ignored with ID " + p_193372_0_);
/*     */     }
/*     */ 
/*     */     
/* 189 */     field_193380_a.register(field_193381_c++, p_193372_0_, p_193372_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack findMatchingRecipe(InventoryCrafting p_82787_0_, World craftMatrix) {
/* 198 */     for (IRecipe irecipe : field_193380_a) {
/*     */       
/* 200 */       if (irecipe.matches(p_82787_0_, craftMatrix))
/*     */       {
/* 202 */         return irecipe.getCraftingResult(p_82787_0_);
/*     */       }
/*     */     } 
/*     */     
/* 206 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static IRecipe func_192413_b(InventoryCrafting p_192413_0_, World p_192413_1_) {
/* 212 */     for (IRecipe irecipe : field_193380_a) {
/*     */       
/* 214 */       if (irecipe.matches(p_192413_0_, p_192413_1_))
/*     */       {
/* 216 */         return irecipe;
/*     */       }
/*     */     } 
/*     */     
/* 220 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static NonNullList<ItemStack> getRemainingItems(InventoryCrafting p_180303_0_, World craftMatrix) {
/* 225 */     for (IRecipe irecipe : field_193380_a) {
/*     */       
/* 227 */       if (irecipe.matches(p_180303_0_, craftMatrix))
/*     */       {
/* 229 */         return irecipe.getRemainingItems(p_180303_0_);
/*     */       }
/*     */     } 
/*     */     
/* 233 */     NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(p_180303_0_.getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/* 235 */     for (int i = 0; i < nonnulllist.size(); i++)
/*     */     {
/* 237 */       nonnulllist.set(i, p_180303_0_.getStackInSlot(i));
/*     */     }
/*     */     
/* 240 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static IRecipe func_193373_a(ResourceLocation p_193373_0_) {
/* 246 */     return (IRecipe)field_193380_a.getObject(p_193373_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_193375_a(IRecipe p_193375_0_) {
/* 251 */     return field_193380_a.getIDForObject(p_193375_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static IRecipe func_193374_a(int p_193374_0_) {
/* 257 */     return (IRecipe)field_193380_a.getObjectById(p_193374_0_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\CraftingManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */