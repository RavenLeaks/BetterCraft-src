/*     */ package optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomGuis
/*     */ {
/*  35 */   private static Minecraft mc = Config.getMinecraft();
/*  36 */   private static PlayerControllerOF playerControllerOF = null;
/*  37 */   private static CustomGuiProperties[][] guiProperties = null;
/*  38 */   public static boolean isChristmas = isChristmas();
/*     */ 
/*     */   
/*     */   public static ResourceLocation getTextureLocation(ResourceLocation p_getTextureLocation_0_) {
/*  42 */     if (guiProperties == null)
/*     */     {
/*  44 */       return p_getTextureLocation_0_;
/*     */     }
/*     */ 
/*     */     
/*  48 */     GuiScreen guiscreen = mc.currentScreen;
/*     */     
/*  50 */     if (!(guiscreen instanceof net.minecraft.client.gui.inventory.GuiContainer))
/*     */     {
/*  52 */       return p_getTextureLocation_0_;
/*     */     }
/*  54 */     if (p_getTextureLocation_0_.getResourceDomain().equals("minecraft") && p_getTextureLocation_0_.getResourcePath().startsWith("textures/gui/")) {
/*     */       
/*  56 */       if (playerControllerOF == null)
/*     */       {
/*  58 */         return p_getTextureLocation_0_;
/*     */       }
/*     */ 
/*     */       
/*  62 */       WorldClient worldClient = mc.world;
/*     */       
/*  64 */       if (worldClient == null)
/*     */       {
/*  66 */         return p_getTextureLocation_0_;
/*     */       }
/*  68 */       if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiContainerCreative)
/*     */       {
/*  70 */         return getTexturePos(CustomGuiProperties.EnumContainer.CREATIVE, mc.player.getPosition(), (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */       }
/*  72 */       if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiInventory)
/*     */       {
/*  74 */         return getTexturePos(CustomGuiProperties.EnumContainer.INVENTORY, mc.player.getPosition(), (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */       }
/*     */ 
/*     */       
/*  78 */       BlockPos blockpos = playerControllerOF.getLastClickBlockPos();
/*     */       
/*  80 */       if (blockpos != null) {
/*     */         
/*  82 */         if (guiscreen instanceof net.minecraft.client.gui.GuiRepair)
/*     */         {
/*  84 */           return getTexturePos(CustomGuiProperties.EnumContainer.ANVIL, blockpos, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */         
/*  87 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiBeacon)
/*     */         {
/*  89 */           return getTexturePos(CustomGuiProperties.EnumContainer.BEACON, blockpos, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */         
/*  92 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiBrewingStand)
/*     */         {
/*  94 */           return getTexturePos(CustomGuiProperties.EnumContainer.BREWING_STAND, blockpos, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */         
/*  97 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiChest)
/*     */         {
/*  99 */           return getTexturePos(CustomGuiProperties.EnumContainer.CHEST, blockpos, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */         
/* 102 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiCrafting)
/*     */         {
/* 104 */           return getTexturePos(CustomGuiProperties.EnumContainer.CRAFTING, blockpos, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */         
/* 107 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiDispenser)
/*     */         {
/* 109 */           return getTexturePos(CustomGuiProperties.EnumContainer.DISPENSER, blockpos, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */         
/* 112 */         if (guiscreen instanceof net.minecraft.client.gui.GuiEnchantment)
/*     */         {
/* 114 */           return getTexturePos(CustomGuiProperties.EnumContainer.ENCHANTMENT, blockpos, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */         
/* 117 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiFurnace)
/*     */         {
/* 119 */           return getTexturePos(CustomGuiProperties.EnumContainer.FURNACE, blockpos, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */         
/* 122 */         if (guiscreen instanceof net.minecraft.client.gui.GuiHopper)
/*     */         {
/* 124 */           return getTexturePos(CustomGuiProperties.EnumContainer.HOPPER, blockpos, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */         
/* 127 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiShulkerBox)
/*     */         {
/* 129 */           return getTexturePos(CustomGuiProperties.EnumContainer.SHULKER_BOX, blockpos, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */       } 
/*     */       
/* 133 */       Entity entity = playerControllerOF.getLastClickEntity();
/*     */       
/* 135 */       if (entity != null) {
/*     */         
/* 137 */         if (guiscreen instanceof net.minecraft.client.gui.inventory.GuiScreenHorseInventory)
/*     */         {
/* 139 */           return getTextureEntity(CustomGuiProperties.EnumContainer.HORSE, entity, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */         
/* 142 */         if (guiscreen instanceof net.minecraft.client.gui.GuiMerchant)
/*     */         {
/* 144 */           return getTextureEntity(CustomGuiProperties.EnumContainer.VILLAGER, entity, (IBlockAccess)worldClient, p_getTextureLocation_0_);
/*     */         }
/*     */       } 
/*     */       
/* 148 */       return p_getTextureLocation_0_;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     return p_getTextureLocation_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation getTexturePos(CustomGuiProperties.EnumContainer p_getTexturePos_0_, BlockPos p_getTexturePos_1_, IBlockAccess p_getTexturePos_2_, ResourceLocation p_getTexturePos_3_) {
/* 161 */     CustomGuiProperties[] acustomguiproperties = guiProperties[p_getTexturePos_0_.ordinal()];
/*     */     
/* 163 */     if (acustomguiproperties == null)
/*     */     {
/* 165 */       return p_getTexturePos_3_;
/*     */     }
/*     */ 
/*     */     
/* 169 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/*     */       
/* 171 */       CustomGuiProperties customguiproperties = acustomguiproperties[i];
/*     */       
/* 173 */       if (customguiproperties.matchesPos(p_getTexturePos_0_, p_getTexturePos_1_, p_getTexturePos_2_))
/*     */       {
/* 175 */         return customguiproperties.getTextureLocation(p_getTexturePos_3_);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     return p_getTexturePos_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation getTextureEntity(CustomGuiProperties.EnumContainer p_getTextureEntity_0_, Entity p_getTextureEntity_1_, IBlockAccess p_getTextureEntity_2_, ResourceLocation p_getTextureEntity_3_) {
/* 185 */     CustomGuiProperties[] acustomguiproperties = guiProperties[p_getTextureEntity_0_.ordinal()];
/*     */     
/* 187 */     if (acustomguiproperties == null)
/*     */     {
/* 189 */       return p_getTextureEntity_3_;
/*     */     }
/*     */ 
/*     */     
/* 193 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/*     */       
/* 195 */       CustomGuiProperties customguiproperties = acustomguiproperties[i];
/*     */       
/* 197 */       if (customguiproperties.matchesEntity(p_getTextureEntity_0_, p_getTextureEntity_1_, p_getTextureEntity_2_))
/*     */       {
/* 199 */         return customguiproperties.getTextureLocation(p_getTextureEntity_3_);
/*     */       }
/*     */     } 
/*     */     
/* 203 */     return p_getTextureEntity_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/* 209 */     guiProperties = null;
/*     */     
/* 211 */     if (Config.isCustomGuis()) {
/*     */       
/* 213 */       List<List<CustomGuiProperties>> list = new ArrayList<>();
/* 214 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */       
/* 216 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/*     */         
/* 218 */         IResourcePack iresourcepack = airesourcepack[i];
/* 219 */         update(iresourcepack, list);
/*     */       } 
/*     */       
/* 222 */       guiProperties = propertyListToArray(list);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static CustomGuiProperties[][] propertyListToArray(List<List<CustomGuiProperties>> p_propertyListToArray_0_) {
/* 228 */     if (p_propertyListToArray_0_.isEmpty())
/*     */     {
/* 230 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 234 */     CustomGuiProperties[][] acustomguiproperties = new CustomGuiProperties[(CustomGuiProperties.EnumContainer.values()).length][];
/*     */     
/* 236 */     for (int i = 0; i < acustomguiproperties.length; i++) {
/*     */       
/* 238 */       if (p_propertyListToArray_0_.size() > i) {
/*     */         
/* 240 */         List<CustomGuiProperties> list = p_propertyListToArray_0_.get(i);
/*     */         
/* 242 */         if (list != null) {
/*     */           
/* 244 */           CustomGuiProperties[] acustomguiproperties1 = list.<CustomGuiProperties>toArray(new CustomGuiProperties[list.size()]);
/* 245 */           acustomguiproperties[i] = acustomguiproperties1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     return acustomguiproperties;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void update(IResourcePack p_update_0_, List<List<CustomGuiProperties>> p_update_1_) {
/* 256 */     String[] astring = ResUtils.collectFiles(p_update_0_, "optifine/gui/container/", ".properties", (String[])null);
/* 257 */     Arrays.sort((Object[])astring);
/*     */     
/* 259 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 261 */       String s = astring[i];
/* 262 */       Config.dbg("CustomGuis: " + s);
/*     */ 
/*     */       
/*     */       try {
/* 266 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 267 */         InputStream inputstream = p_update_0_.getInputStream(resourcelocation);
/*     */         
/* 269 */         if (inputstream == null)
/*     */         {
/* 271 */           Config.warn("CustomGuis file not found: " + s);
/*     */         }
/*     */         else
/*     */         {
/* 275 */           Properties properties = new Properties();
/* 276 */           properties.load(inputstream);
/* 277 */           inputstream.close();
/* 278 */           CustomGuiProperties customguiproperties = new CustomGuiProperties(properties, s);
/*     */           
/* 280 */           if (customguiproperties.isValid(s))
/*     */           {
/* 282 */             addToList(customguiproperties, p_update_1_);
/*     */           }
/*     */         }
/*     */       
/* 286 */       } catch (FileNotFoundException var9) {
/*     */         
/* 288 */         Config.warn("CustomGuis file not found: " + s);
/*     */       }
/* 290 */       catch (Exception exception) {
/*     */         
/* 292 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(CustomGuiProperties p_addToList_0_, List<List<CustomGuiProperties>> p_addToList_1_) {
/* 299 */     if (p_addToList_0_.getContainer() == null) {
/*     */       
/* 301 */       warn("Invalid container: " + p_addToList_0_.getContainer());
/*     */     }
/*     */     else {
/*     */       
/* 305 */       int i = p_addToList_0_.getContainer().ordinal();
/*     */       
/* 307 */       while (p_addToList_1_.size() <= i)
/*     */       {
/* 309 */         p_addToList_1_.add(null);
/*     */       }
/*     */       
/* 312 */       List<CustomGuiProperties> list = p_addToList_1_.get(i);
/*     */       
/* 314 */       if (list == null) {
/*     */         
/* 316 */         list = new ArrayList<>();
/* 317 */         p_addToList_1_.set(i, list);
/*     */       } 
/*     */       
/* 320 */       list.add(p_addToList_0_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static PlayerControllerOF getPlayerControllerOF() {
/* 326 */     return playerControllerOF;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setPlayerControllerOF(PlayerControllerOF p_setPlayerControllerOF_0_) {
/* 331 */     playerControllerOF = p_setPlayerControllerOF_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isChristmas() {
/* 336 */     Calendar calendar = Calendar.getInstance();
/* 337 */     return (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void warn(String p_warn_0_) {
/* 342 */     Config.warn("[CustomGuis] " + p_warn_0_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomGuis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */