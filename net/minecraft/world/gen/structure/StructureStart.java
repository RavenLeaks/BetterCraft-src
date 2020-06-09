/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class StructureStart {
/*  14 */   protected List<StructureComponent> components = Lists.newLinkedList();
/*     */   
/*     */   protected StructureBoundingBox boundingBox;
/*     */   
/*     */   private int chunkPosX;
/*     */   
/*     */   private int chunkPosZ;
/*     */   
/*     */   public StructureStart() {}
/*     */   
/*     */   public StructureStart(int chunkX, int chunkZ) {
/*  25 */     this.chunkPosX = chunkX;
/*  26 */     this.chunkPosZ = chunkZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public StructureBoundingBox getBoundingBox() {
/*  31 */     return this.boundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<StructureComponent> getComponents() {
/*  36 */     return this.components;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb) {
/*  44 */     Iterator<StructureComponent> iterator = this.components.iterator();
/*     */     
/*  46 */     while (iterator.hasNext()) {
/*     */       
/*  48 */       StructureComponent structurecomponent = iterator.next();
/*     */       
/*  50 */       if (structurecomponent.getBoundingBox().intersectsWith(structurebb) && !structurecomponent.addComponentParts(worldIn, rand, structurebb))
/*     */       {
/*  52 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateBoundingBox() {
/*  62 */     this.boundingBox = StructureBoundingBox.getNewBoundingBox();
/*     */     
/*  64 */     for (StructureComponent structurecomponent : this.components)
/*     */     {
/*  66 */       this.boundingBox.expandTo(structurecomponent.getBoundingBox());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeStructureComponentsToNBT(int chunkX, int chunkZ) {
/*  72 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  73 */     nbttagcompound.setString("id", MapGenStructureIO.getStructureStartName(this));
/*  74 */     nbttagcompound.setInteger("ChunkX", chunkX);
/*  75 */     nbttagcompound.setInteger("ChunkZ", chunkZ);
/*  76 */     nbttagcompound.setTag("BB", (NBTBase)this.boundingBox.toNBTTagIntArray());
/*  77 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  79 */     for (StructureComponent structurecomponent : this.components)
/*     */     {
/*  81 */       nbttaglist.appendTag((NBTBase)structurecomponent.createStructureBaseNBT());
/*     */     }
/*     */     
/*  84 */     nbttagcompound.setTag("Children", (NBTBase)nbttaglist);
/*  85 */     writeToNBT(nbttagcompound);
/*  86 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */   
/*     */   public void readStructureComponentsFromNBT(World worldIn, NBTTagCompound tagCompound) {
/*  95 */     this.chunkPosX = tagCompound.getInteger("ChunkX");
/*  96 */     this.chunkPosZ = tagCompound.getInteger("ChunkZ");
/*     */     
/*  98 */     if (tagCompound.hasKey("BB"))
/*     */     {
/* 100 */       this.boundingBox = new StructureBoundingBox(tagCompound.getIntArray("BB"));
/*     */     }
/*     */     
/* 103 */     NBTTagList nbttaglist = tagCompound.getTagList("Children", 10);
/*     */     
/* 105 */     for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */     {
/* 107 */       this.components.add(MapGenStructureIO.getStructureComponent(nbttaglist.getCompoundTagAt(i), worldIn));
/*     */     }
/*     */     
/* 110 */     readFromNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void markAvailableHeight(World worldIn, Random rand, int p_75067_3_) {
/* 122 */     int i = worldIn.getSeaLevel() - p_75067_3_;
/* 123 */     int j = this.boundingBox.getYSize() + 1;
/*     */     
/* 125 */     if (j < i)
/*     */     {
/* 127 */       j += rand.nextInt(i - j);
/*     */     }
/*     */     
/* 130 */     int k = j - this.boundingBox.maxY;
/* 131 */     this.boundingBox.offset(0, k, 0);
/*     */     
/* 133 */     for (StructureComponent structurecomponent : this.components)
/*     */     {
/* 135 */       structurecomponent.offset(0, k, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setRandomHeight(World worldIn, Random rand, int p_75070_3_, int p_75070_4_) {
/* 141 */     int j, i = p_75070_4_ - p_75070_3_ + 1 - this.boundingBox.getYSize();
/*     */ 
/*     */     
/* 144 */     if (i > 1) {
/*     */       
/* 146 */       j = p_75070_3_ + rand.nextInt(i);
/*     */     }
/*     */     else {
/*     */       
/* 150 */       j = p_75070_3_;
/*     */     } 
/*     */     
/* 153 */     int k = j - this.boundingBox.minY;
/* 154 */     this.boundingBox.offset(0, k, 0);
/*     */     
/* 156 */     for (StructureComponent structurecomponent : this.components)
/*     */     {
/* 158 */       structurecomponent.offset(0, k, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSizeableStructure() {
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidForPostProcess(ChunkPos pair) {
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyPostProcessAt(ChunkPos pair) {}
/*     */ 
/*     */   
/*     */   public int getChunkPosX() {
/* 181 */     return this.chunkPosX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChunkPosZ() {
/* 186 */     return this.chunkPosZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureStart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */