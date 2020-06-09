/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class MapGenStructureIO
/*     */ {
/*  13 */   private static final Logger LOGGER = LogManager.getLogger();
/*  14 */   private static final Map<String, Class<? extends StructureStart>> startNameToClassMap = Maps.newHashMap();
/*  15 */   private static final Map<Class<? extends StructureStart>, String> startClassToNameMap = Maps.newHashMap();
/*  16 */   private static final Map<String, Class<? extends StructureComponent>> componentNameToClassMap = Maps.newHashMap();
/*  17 */   private static final Map<Class<? extends StructureComponent>, String> componentClassToNameMap = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private static void registerStructure(Class<? extends StructureStart> startClass, String structureName) {
/*  21 */     startNameToClassMap.put(structureName, startClass);
/*  22 */     startClassToNameMap.put(startClass, structureName);
/*     */   }
/*     */ 
/*     */   
/*     */   static void registerStructureComponent(Class<? extends StructureComponent> componentClass, String componentName) {
/*  27 */     componentNameToClassMap.put(componentName, componentClass);
/*  28 */     componentClassToNameMap.put(componentClass, componentName);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getStructureStartName(StructureStart start) {
/*  33 */     return startClassToNameMap.get(start.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getStructureComponentName(StructureComponent component) {
/*  38 */     return componentClassToNameMap.get(component.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static StructureStart getStructureStart(NBTTagCompound tagCompound, World worldIn) {
/*  44 */     StructureStart structurestart = null;
/*     */ 
/*     */     
/*     */     try {
/*  48 */       Class<? extends StructureStart> oclass = startNameToClassMap.get(tagCompound.getString("id"));
/*     */       
/*  50 */       if (oclass != null)
/*     */       {
/*  52 */         structurestart = oclass.newInstance();
/*     */       }
/*     */     }
/*  55 */     catch (Exception exception) {
/*     */       
/*  57 */       LOGGER.warn("Failed Start with id {}", tagCompound.getString("id"));
/*  58 */       exception.printStackTrace();
/*     */     } 
/*     */     
/*  61 */     if (structurestart != null) {
/*     */       
/*  63 */       structurestart.readStructureComponentsFromNBT(worldIn, tagCompound);
/*     */     }
/*     */     else {
/*     */       
/*  67 */       LOGGER.warn("Skipping Structure with id {}", tagCompound.getString("id"));
/*     */     } 
/*     */     
/*  70 */     return structurestart;
/*     */   }
/*     */ 
/*     */   
/*     */   public static StructureComponent getStructureComponent(NBTTagCompound tagCompound, World worldIn) {
/*  75 */     StructureComponent structurecomponent = null;
/*     */ 
/*     */     
/*     */     try {
/*  79 */       Class<? extends StructureComponent> oclass = componentNameToClassMap.get(tagCompound.getString("id"));
/*     */       
/*  81 */       if (oclass != null)
/*     */       {
/*  83 */         structurecomponent = oclass.newInstance();
/*     */       }
/*     */     }
/*  86 */     catch (Exception exception) {
/*     */       
/*  88 */       LOGGER.warn("Failed Piece with id {}", tagCompound.getString("id"));
/*  89 */       exception.printStackTrace();
/*     */     } 
/*     */     
/*  92 */     if (structurecomponent != null) {
/*     */       
/*  94 */       structurecomponent.readStructureBaseNBT(worldIn, tagCompound);
/*     */     }
/*     */     else {
/*     */       
/*  98 */       LOGGER.warn("Skipping Piece with id {}", tagCompound.getString("id"));
/*     */     } 
/*     */     
/* 101 */     return structurecomponent;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 106 */     registerStructure((Class)StructureMineshaftStart.class, "Mineshaft");
/* 107 */     registerStructure((Class)MapGenVillage.Start.class, "Village");
/* 108 */     registerStructure((Class)MapGenNetherBridge.Start.class, "Fortress");
/* 109 */     registerStructure((Class)MapGenStronghold.Start.class, "Stronghold");
/* 110 */     registerStructure((Class)MapGenScatteredFeature.Start.class, "Temple");
/* 111 */     registerStructure((Class)StructureOceanMonument.StartMonument.class, "Monument");
/* 112 */     registerStructure((Class)MapGenEndCity.Start.class, "EndCity");
/* 113 */     registerStructure((Class)WoodlandMansion.Start.class, "Mansion");
/* 114 */     StructureMineshaftPieces.registerStructurePieces();
/* 115 */     StructureVillagePieces.registerVillagePieces();
/* 116 */     StructureNetherBridgePieces.registerNetherFortressPieces();
/* 117 */     StructureStrongholdPieces.registerStrongholdPieces();
/* 118 */     ComponentScatteredFeaturePieces.registerScatteredFeaturePieces();
/* 119 */     StructureOceanMonumentPieces.registerOceanMonumentPieces();
/* 120 */     StructureEndCityPieces.registerPieces();
/* 121 */     WoodlandMansionPieces.func_191153_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\MapGenStructureIO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */