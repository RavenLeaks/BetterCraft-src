/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemBanner;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ 
/*     */ public class TileEntityBanner extends TileEntity implements IWorldNameable {
/*     */   private String field_190617_a;
/*  20 */   private EnumDyeColor baseColor = EnumDyeColor.BLACK;
/*     */ 
/*     */   
/*     */   private NBTTagList patterns;
/*     */   
/*     */   private boolean patternDataSet;
/*     */   
/*     */   private List<BannerPattern> patternList;
/*     */   
/*     */   private List<EnumDyeColor> colorList;
/*     */   
/*     */   private String patternResourceLocation;
/*     */ 
/*     */   
/*     */   public void setItemValues(ItemStack stack, boolean p_175112_2_) {
/*  35 */     this.patterns = null;
/*  36 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag");
/*     */     
/*  38 */     if (nbttagcompound != null && nbttagcompound.hasKey("Patterns", 9))
/*     */     {
/*  40 */       this.patterns = nbttagcompound.getTagList("Patterns", 10).copy();
/*     */     }
/*     */     
/*  43 */     this.baseColor = p_175112_2_ ? func_190616_d(stack) : ItemBanner.getBaseColor(stack);
/*  44 */     this.patternList = null;
/*  45 */     this.colorList = null;
/*  46 */     this.patternResourceLocation = "";
/*  47 */     this.patternDataSet = true;
/*  48 */     this.field_190617_a = stack.hasDisplayName() ? stack.getDisplayName() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  56 */     return hasCustomName() ? this.field_190617_a : "banner";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  64 */     return (this.field_190617_a != null && !this.field_190617_a.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getDisplayName() {
/*  72 */     return hasCustomName() ? (ITextComponent)new TextComponentString(getName()) : (ITextComponent)new TextComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  77 */     super.writeToNBT(compound);
/*  78 */     compound.setInteger("Base", this.baseColor.getDyeDamage());
/*     */     
/*  80 */     if (this.patterns != null)
/*     */     {
/*  82 */       compound.setTag("Patterns", (NBTBase)this.patterns);
/*     */     }
/*     */     
/*  85 */     if (hasCustomName())
/*     */     {
/*  87 */       compound.setString("CustomName", this.field_190617_a);
/*     */     }
/*     */     
/*  90 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  95 */     super.readFromNBT(compound);
/*     */     
/*  97 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/*  99 */       this.field_190617_a = compound.getString("CustomName");
/*     */     }
/*     */     
/* 102 */     this.baseColor = EnumDyeColor.byDyeDamage(compound.getInteger("Base"));
/* 103 */     this.patterns = compound.getTagList("Patterns", 10);
/* 104 */     this.patternList = null;
/* 105 */     this.colorList = null;
/* 106 */     this.patternResourceLocation = null;
/* 107 */     this.patternDataSet = true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SPacketUpdateTileEntity getUpdatePacket() {
/* 113 */     return new SPacketUpdateTileEntity(this.pos, 6, getUpdateTag());
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getUpdateTag() {
/* 118 */     return writeToNBT(new NBTTagCompound());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPatterns(ItemStack stack) {
/* 126 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag");
/* 127 */     return (nbttagcompound != null && nbttagcompound.hasKey("Patterns")) ? nbttagcompound.getTagList("Patterns", 10).tagCount() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BannerPattern> getPatternList() {
/* 132 */     initializeBannerData();
/* 133 */     return this.patternList;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<EnumDyeColor> getColorList() {
/* 138 */     initializeBannerData();
/* 139 */     return this.colorList;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPatternResourceLocation() {
/* 144 */     initializeBannerData();
/* 145 */     return this.patternResourceLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeBannerData() {
/* 154 */     if (this.patternList == null || this.colorList == null || this.patternResourceLocation == null)
/*     */     {
/* 156 */       if (!this.patternDataSet) {
/*     */         
/* 158 */         this.patternResourceLocation = "";
/*     */       }
/*     */       else {
/*     */         
/* 162 */         this.patternList = Lists.newArrayList();
/* 163 */         this.colorList = Lists.newArrayList();
/* 164 */         this.patternList.add(BannerPattern.BASE);
/* 165 */         this.colorList.add(this.baseColor);
/* 166 */         this.patternResourceLocation = "b" + this.baseColor.getDyeDamage();
/*     */         
/* 168 */         if (this.patterns != null)
/*     */         {
/* 170 */           for (int i = 0; i < this.patterns.tagCount(); i++) {
/*     */             
/* 172 */             NBTTagCompound nbttagcompound = this.patterns.getCompoundTagAt(i);
/* 173 */             BannerPattern bannerpattern = BannerPattern.func_190994_a(nbttagcompound.getString("Pattern"));
/*     */             
/* 175 */             if (bannerpattern != null) {
/*     */               
/* 177 */               this.patternList.add(bannerpattern);
/* 178 */               int j = nbttagcompound.getInteger("Color");
/* 179 */               this.colorList.add(EnumDyeColor.byDyeDamage(j));
/* 180 */               this.patternResourceLocation = String.valueOf(this.patternResourceLocation) + bannerpattern.func_190993_b() + j;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeBannerData(ItemStack stack) {
/* 193 */     NBTTagCompound nbttagcompound = stack.getSubCompound("BlockEntityTag");
/*     */     
/* 195 */     if (nbttagcompound != null && nbttagcompound.hasKey("Patterns", 9)) {
/*     */       
/* 197 */       NBTTagList nbttaglist = nbttagcompound.getTagList("Patterns", 10);
/*     */       
/* 199 */       if (!nbttaglist.hasNoTags()) {
/*     */         
/* 201 */         nbttaglist.removeTag(nbttaglist.tagCount() - 1);
/*     */         
/* 203 */         if (nbttaglist.hasNoTags()) {
/*     */           
/* 205 */           stack.getTagCompound().removeTag("BlockEntityTag");
/*     */           
/* 207 */           if (stack.getTagCompound().hasNoTags())
/*     */           {
/* 209 */             stack.setTagCompound(null);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_190615_l() {
/* 218 */     ItemStack itemstack = ItemBanner.func_190910_a(this.baseColor, this.patterns);
/*     */     
/* 220 */     if (hasCustomName())
/*     */     {
/* 222 */       itemstack.setStackDisplayName(getName());
/*     */     }
/*     */     
/* 225 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumDyeColor func_190616_d(ItemStack p_190616_0_) {
/* 230 */     NBTTagCompound nbttagcompound = p_190616_0_.getSubCompound("BlockEntityTag");
/* 231 */     return (nbttagcompound != null && nbttagcompound.hasKey("Base")) ? EnumDyeColor.byDyeDamage(nbttagcompound.getInteger("Base")) : EnumDyeColor.BLACK;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */