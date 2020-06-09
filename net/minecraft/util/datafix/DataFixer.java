/*     */ package net.minecraft.util.datafix;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.Util;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class DataFixer
/*     */   implements IDataFixer {
/*  14 */   private static final Logger LOGGER = LogManager.getLogger();
/*  15 */   private final Map<IFixType, List<IDataWalker>> walkerMap = Maps.newHashMap();
/*  16 */   private final Map<IFixType, List<IFixableData>> fixMap = Maps.newHashMap();
/*     */   
/*     */   private final int version;
/*     */   
/*     */   public DataFixer(int versionIn) {
/*  21 */     this.version = versionIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound process(IFixType type, NBTTagCompound compound) {
/*  26 */     int i = compound.hasKey("DataVersion", 99) ? compound.getInteger("DataVersion") : -1;
/*  27 */     return (i >= 1343) ? compound : process(type, compound, i);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound process(IFixType type, NBTTagCompound compound, int versionIn) {
/*  32 */     if (versionIn < this.version) {
/*     */       
/*  34 */       compound = processFixes(type, compound, versionIn);
/*  35 */       compound = processWalkers(type, compound, versionIn);
/*     */     } 
/*     */     
/*  38 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   private NBTTagCompound processFixes(IFixType type, NBTTagCompound compound, int versionIn) {
/*  43 */     List<IFixableData> list = this.fixMap.get(type);
/*     */     
/*  45 */     if (list != null)
/*     */     {
/*  47 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/*  49 */         IFixableData ifixabledata = list.get(i);
/*     */         
/*  51 */         if (ifixabledata.getFixVersion() > versionIn)
/*     */         {
/*  53 */           compound = ifixabledata.fixTagCompound(compound);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  58 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   private NBTTagCompound processWalkers(IFixType type, NBTTagCompound compound, int versionIn) {
/*  63 */     List<IDataWalker> list = this.walkerMap.get(type);
/*     */     
/*  65 */     if (list != null)
/*     */     {
/*  67 */       for (int i = 0; i < list.size(); i++)
/*     */       {
/*  69 */         compound = ((IDataWalker)list.get(i)).process(this, compound, versionIn);
/*     */       }
/*     */     }
/*     */     
/*  73 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWalker(FixTypes type, IDataWalker walker) {
/*  78 */     registerWalkerAdd(type, walker);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWalkerAdd(IFixType type, IDataWalker walker) {
/*  83 */     getTypeList(this.walkerMap, type).add(walker);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerFix(IFixType type, IFixableData fixable) {
/*  88 */     List<IFixableData> list = getTypeList(this.fixMap, type);
/*  89 */     int i = fixable.getFixVersion();
/*     */     
/*  91 */     if (i > this.version) {
/*     */       
/*  93 */       LOGGER.warn("Ignored fix registered for version: {} as the DataVersion of the game is: {}", Integer.valueOf(i), Integer.valueOf(this.version));
/*     */ 
/*     */     
/*     */     }
/*  97 */     else if (!list.isEmpty() && ((IFixableData)Util.getLastElement(list)).getFixVersion() > i) {
/*     */       
/*  99 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/* 101 */         if (((IFixableData)list.get(j)).getFixVersion() > i) {
/*     */           
/* 103 */           list.add(j, fixable);
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 110 */       list.add(fixable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <V> List<V> getTypeList(Map<IFixType, List<V>> map, IFixType type) {
/* 117 */     List<V> list = map.get(type);
/*     */     
/* 119 */     if (list == null) {
/*     */       
/* 121 */       list = Lists.newArrayList();
/* 122 */       map.put(type, list);
/*     */     } 
/*     */     
/* 125 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\datafix\DataFixer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */