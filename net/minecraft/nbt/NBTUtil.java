/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Optional;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public final class NBTUtil
/*     */ {
/*  24 */   private static final Logger field_193591_a = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static GameProfile readGameProfileFromNBT(NBTTagCompound compound) {
/*  33 */     String s = null;
/*  34 */     String s1 = null;
/*     */     
/*  36 */     if (compound.hasKey("Name", 8))
/*     */     {
/*  38 */       s = compound.getString("Name");
/*     */     }
/*     */     
/*  41 */     if (compound.hasKey("Id", 8))
/*     */     {
/*  43 */       s1 = compound.getString("Id");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*     */       UUID uuid;
/*     */ 
/*     */       
/*     */       try {
/*  52 */         uuid = UUID.fromString(s1);
/*     */       }
/*  54 */       catch (Throwable var12) {
/*     */         
/*  56 */         uuid = null;
/*     */       } 
/*     */       
/*  59 */       GameProfile gameprofile = new GameProfile(uuid, s);
/*     */       
/*  61 */       if (compound.hasKey("Properties", 10)) {
/*     */         
/*  63 */         NBTTagCompound nbttagcompound = compound.getCompoundTag("Properties");
/*     */         
/*  65 */         for (String s2 : nbttagcompound.getKeySet()) {
/*     */           
/*  67 */           NBTTagList nbttaglist = nbttagcompound.getTagList(s2, 10);
/*     */           
/*  69 */           for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */             
/*  71 */             NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
/*  72 */             String s3 = nbttagcompound1.getString("Value");
/*     */             
/*  74 */             if (nbttagcompound1.hasKey("Signature", 8)) {
/*     */               
/*  76 */               gameprofile.getProperties().put(s2, new Property(s2, s3, nbttagcompound1.getString("Signature")));
/*     */             }
/*     */             else {
/*     */               
/*  80 */               gameprofile.getProperties().put(s2, new Property(s2, s3));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  86 */       return gameprofile;
/*     */     }
/*  88 */     catch (Throwable var13) {
/*     */       
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound writeGameProfile(NBTTagCompound tagCompound, GameProfile profile) {
/*  99 */     if (!StringUtils.isNullOrEmpty(profile.getName()))
/*     */     {
/* 101 */       tagCompound.setString("Name", profile.getName());
/*     */     }
/*     */     
/* 104 */     if (profile.getId() != null)
/*     */     {
/* 106 */       tagCompound.setString("Id", profile.getId().toString());
/*     */     }
/*     */     
/* 109 */     if (!profile.getProperties().isEmpty()) {
/*     */       
/* 111 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 113 */       for (String s : profile.getProperties().keySet()) {
/*     */         
/* 115 */         NBTTagList nbttaglist = new NBTTagList();
/*     */         
/* 117 */         for (Property property : profile.getProperties().get(s)) {
/*     */           
/* 119 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 120 */           nbttagcompound1.setString("Value", property.getValue());
/*     */           
/* 122 */           if (property.hasSignature())
/*     */           {
/* 124 */             nbttagcompound1.setString("Signature", property.getSignature());
/*     */           }
/*     */           
/* 127 */           nbttaglist.appendTag(nbttagcompound1);
/*     */         } 
/*     */         
/* 130 */         nbttagcompound.setTag(s, nbttaglist);
/*     */       } 
/*     */       
/* 133 */       tagCompound.setTag("Properties", nbttagcompound);
/*     */     } 
/*     */     
/* 136 */     return tagCompound;
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   public static boolean areNBTEquals(NBTBase nbt1, NBTBase nbt2, boolean compareTagList) {
/* 142 */     if (nbt1 == nbt2)
/*     */     {
/* 144 */       return true;
/*     */     }
/* 146 */     if (nbt1 == null)
/*     */     {
/* 148 */       return true;
/*     */     }
/* 150 */     if (nbt2 == null)
/*     */     {
/* 152 */       return false;
/*     */     }
/* 154 */     if (!nbt1.getClass().equals(nbt2.getClass()))
/*     */     {
/* 156 */       return false;
/*     */     }
/* 158 */     if (nbt1 instanceof NBTTagCompound) {
/*     */       
/* 160 */       NBTTagCompound nbttagcompound = (NBTTagCompound)nbt1;
/* 161 */       NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbt2;
/*     */       
/* 163 */       for (String s : nbttagcompound.getKeySet()) {
/*     */         
/* 165 */         NBTBase nbtbase1 = nbttagcompound.getTag(s);
/*     */         
/* 167 */         if (!areNBTEquals(nbtbase1, nbttagcompound1.getTag(s), compareTagList))
/*     */         {
/* 169 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 173 */       return true;
/*     */     } 
/* 175 */     if (nbt1 instanceof NBTTagList && compareTagList) {
/*     */       
/* 177 */       NBTTagList nbttaglist = (NBTTagList)nbt1;
/* 178 */       NBTTagList nbttaglist1 = (NBTTagList)nbt2;
/*     */       
/* 180 */       if (nbttaglist.hasNoTags())
/*     */       {
/* 182 */         return nbttaglist1.hasNoTags();
/*     */       }
/*     */ 
/*     */       
/* 186 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 188 */         NBTBase nbtbase = nbttaglist.get(i);
/* 189 */         boolean flag = false;
/*     */         
/* 191 */         for (int j = 0; j < nbttaglist1.tagCount(); j++) {
/*     */           
/* 193 */           if (areNBTEquals(nbtbase, nbttaglist1.get(j), compareTagList)) {
/*     */             
/* 195 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 200 */         if (!flag)
/*     */         {
/* 202 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 206 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 211 */     return nbt1.equals(nbt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound createUUIDTag(UUID uuid) {
/* 220 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 221 */     nbttagcompound.setLong("M", uuid.getMostSignificantBits());
/* 222 */     nbttagcompound.setLong("L", uuid.getLeastSignificantBits());
/* 223 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UUID getUUIDFromTag(NBTTagCompound tag) {
/* 231 */     return new UUID(tag.getLong("M"), tag.getLong("L"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos getPosFromTag(NBTTagCompound tag) {
/* 239 */     return new BlockPos(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound createPosTag(BlockPos pos) {
/* 247 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 248 */     nbttagcompound.setInteger("X", pos.getX());
/* 249 */     nbttagcompound.setInteger("Y", pos.getY());
/* 250 */     nbttagcompound.setInteger("Z", pos.getZ());
/* 251 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IBlockState readBlockState(NBTTagCompound tag) {
/* 261 */     if (!tag.hasKey("Name", 8))
/*     */     {
/* 263 */       return Blocks.AIR.getDefaultState();
/*     */     }
/*     */ 
/*     */     
/* 267 */     Block block = (Block)Block.REGISTRY.getObject(new ResourceLocation(tag.getString("Name")));
/* 268 */     IBlockState iblockstate = block.getDefaultState();
/*     */     
/* 270 */     if (tag.hasKey("Properties", 10)) {
/*     */       
/* 272 */       NBTTagCompound nbttagcompound = tag.getCompoundTag("Properties");
/* 273 */       BlockStateContainer blockstatecontainer = block.getBlockState();
/*     */       
/* 275 */       for (String s : nbttagcompound.getKeySet()) {
/*     */         
/* 277 */         IProperty<?> iproperty = blockstatecontainer.getProperty(s);
/*     */         
/* 279 */         if (iproperty != null)
/*     */         {
/* 281 */           iblockstate = func_193590_a(iblockstate, iproperty, s, nbttagcompound, tag);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T extends Comparable<T>> IBlockState func_193590_a(IBlockState p_193590_0_, IProperty<T> p_193590_1_, String p_193590_2_, NBTTagCompound p_193590_3_, NBTTagCompound p_193590_4_) {
/* 292 */     Optional<T> optional = p_193590_1_.parseValue(p_193590_3_.getString(p_193590_2_));
/*     */     
/* 294 */     if (optional.isPresent())
/*     */     {
/* 296 */       return p_193590_0_.withProperty(p_193590_1_, (Comparable)optional.get());
/*     */     }
/*     */ 
/*     */     
/* 300 */     field_193591_a.warn("Unable to read property: {} with value: {} for blockstate: {}", p_193590_2_, p_193590_3_.getString(p_193590_2_), p_193590_4_.toString());
/* 301 */     return p_193590_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound writeBlockState(NBTTagCompound tag, IBlockState state) {
/* 313 */     tag.setString("Name", ((ResourceLocation)Block.REGISTRY.getNameForObject(state.getBlock())).toString());
/*     */     
/* 315 */     if (!state.getProperties().isEmpty()) {
/*     */       
/* 317 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 318 */       UnmodifiableIterator unmodifiableiterator = state.getProperties().entrySet().iterator();
/*     */       
/* 320 */       while (unmodifiableiterator.hasNext()) {
/*     */         
/* 322 */         Map.Entry<IProperty<?>, Comparable<?>> entry = (Map.Entry<IProperty<?>, Comparable<?>>)unmodifiableiterator.next();
/* 323 */         IProperty<?> iproperty = entry.getKey();
/* 324 */         nbttagcompound.setString(iproperty.getName(), getName(iproperty, entry.getValue()));
/*     */       } 
/*     */       
/* 327 */       tag.setTag("Properties", nbttagcompound);
/*     */     } 
/*     */     
/* 330 */     return tag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T extends Comparable<T>> String getName(IProperty<T> p_190010_0_, Comparable<?> p_190010_1_) {
/* 336 */     return p_190010_0_.getName(p_190010_1_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */