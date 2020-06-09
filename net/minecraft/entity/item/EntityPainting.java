/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityHanging;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityPainting
/*     */   extends EntityHanging
/*     */ {
/*     */   public EnumArt art;
/*     */   
/*     */   public EntityPainting(World worldIn) {
/*  24 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPainting(World worldIn, BlockPos pos, EnumFacing facing) {
/*  29 */     super(worldIn, pos);
/*  30 */     List<EnumArt> list = Lists.newArrayList();
/*  31 */     int i = 0; byte b; int j;
/*     */     EnumArt[] arrayOfEnumArt;
/*  33 */     for (j = (arrayOfEnumArt = EnumArt.values()).length, b = 0; b < j; ) { EnumArt entitypainting$enumart = arrayOfEnumArt[b];
/*     */       
/*  35 */       this.art = entitypainting$enumart;
/*  36 */       updateFacingWithBoundingBox(facing);
/*     */       
/*  38 */       if (onValidSurface()) {
/*     */         
/*  40 */         list.add(entitypainting$enumart);
/*  41 */         int k = entitypainting$enumart.sizeX * entitypainting$enumart.sizeY;
/*     */         
/*  43 */         if (k > i)
/*     */         {
/*  45 */           i = k;
/*     */         }
/*     */       } 
/*     */       b++; }
/*     */     
/*  50 */     if (!list.isEmpty()) {
/*     */       
/*  52 */       Iterator<EnumArt> iterator = list.iterator();
/*     */       
/*  54 */       while (iterator.hasNext()) {
/*     */         
/*  56 */         EnumArt entitypainting$enumart1 = iterator.next();
/*     */         
/*  58 */         if (entitypainting$enumart1.sizeX * entitypainting$enumart1.sizeY < i)
/*     */         {
/*  60 */           iterator.remove();
/*     */         }
/*     */       } 
/*     */       
/*  64 */       this.art = list.get(this.rand.nextInt(list.size()));
/*     */     } 
/*     */     
/*  67 */     updateFacingWithBoundingBox(facing);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPainting(World worldIn, BlockPos pos, EnumFacing facing, String title) {
/*  72 */     this(worldIn, pos, facing); byte b; int i;
/*     */     EnumArt[] arrayOfEnumArt;
/*  74 */     for (i = (arrayOfEnumArt = EnumArt.values()).length, b = 0; b < i; ) { EnumArt entitypainting$enumart = arrayOfEnumArt[b];
/*     */       
/*  76 */       if (entitypainting$enumart.title.equals(title)) {
/*     */         
/*  78 */         this.art = entitypainting$enumart;
/*     */         break;
/*     */       } 
/*     */       b++; }
/*     */     
/*  83 */     updateFacingWithBoundingBox(facing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  91 */     compound.setString("Motive", this.art.title);
/*  92 */     super.writeEntityToNBT(compound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 100 */     String s = compound.getString("Motive"); byte b; int i;
/*     */     EnumArt[] arrayOfEnumArt;
/* 102 */     for (i = (arrayOfEnumArt = EnumArt.values()).length, b = 0; b < i; ) { EnumArt entitypainting$enumart = arrayOfEnumArt[b];
/*     */       
/* 104 */       if (entitypainting$enumart.title.equals(s))
/*     */       {
/* 106 */         this.art = entitypainting$enumart;
/*     */       }
/*     */       b++; }
/*     */     
/* 110 */     if (this.art == null)
/*     */     {
/* 112 */       this.art = EnumArt.KEBAB;
/*     */     }
/*     */     
/* 115 */     super.readEntityFromNBT(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidthPixels() {
/* 120 */     return this.art.sizeX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeightPixels() {
/* 125 */     return this.art.sizeY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBroken(@Nullable Entity brokenEntity) {
/* 133 */     if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/*     */       
/* 135 */       playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0F, 1.0F);
/*     */       
/* 137 */       if (brokenEntity instanceof EntityPlayer) {
/*     */         
/* 139 */         EntityPlayer entityplayer = (EntityPlayer)brokenEntity;
/*     */         
/* 141 */         if (entityplayer.capabilities.isCreativeMode) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 147 */       entityDropItem(new ItemStack(Items.PAINTING), 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void playPlaceSound() {
/* 153 */     playSound(SoundEvents.ENTITY_PAINTING_PLACE, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
/* 161 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/* 169 */     BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
/* 170 */     setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */   }
/*     */   
/*     */   public enum EnumArt
/*     */   {
/* 175 */     KEBAB("Kebab", 16, 16, 0, 0),
/* 176 */     AZTEC("Aztec", 16, 16, 16, 0),
/* 177 */     ALBAN("Alban", 16, 16, 32, 0),
/* 178 */     AZTEC_2("Aztec2", 16, 16, 48, 0),
/* 179 */     BOMB("Bomb", 16, 16, 64, 0),
/* 180 */     PLANT("Plant", 16, 16, 80, 0),
/* 181 */     WASTELAND("Wasteland", 16, 16, 96, 0),
/* 182 */     POOL("Pool", 32, 16, 0, 32),
/* 183 */     COURBET("Courbet", 32, 16, 32, 32),
/* 184 */     SEA("Sea", 32, 16, 64, 32),
/* 185 */     SUNSET("Sunset", 32, 16, 96, 32),
/* 186 */     CREEBET("Creebet", 32, 16, 128, 32),
/* 187 */     WANDERER("Wanderer", 16, 32, 0, 64),
/* 188 */     GRAHAM("Graham", 16, 32, 16, 64),
/* 189 */     MATCH("Match", 32, 32, 0, 128),
/* 190 */     BUST("Bust", 32, 32, 32, 128),
/* 191 */     STAGE("Stage", 32, 32, 64, 128),
/* 192 */     VOID("Void", 32, 32, 96, 128),
/* 193 */     SKULL_AND_ROSES("SkullAndRoses", 32, 32, 128, 128),
/* 194 */     WITHER("Wither", 32, 32, 160, 128),
/* 195 */     FIGHTERS("Fighters", 64, 32, 0, 96),
/* 196 */     POINTER("Pointer", 64, 64, 0, 192),
/* 197 */     PIGSCENE("Pigscene", 64, 64, 64, 192),
/* 198 */     BURNING_SKULL("BurningSkull", 64, 64, 128, 192),
/* 199 */     SKELETON("Skeleton", 64, 48, 192, 64),
/* 200 */     DONKEY_KONG("DonkeyKong", 64, 48, 192, 112);
/*     */     
/* 202 */     public static final int MAX_NAME_LENGTH = "SkullAndRoses".length();
/*     */     
/*     */     public final String title;
/*     */     
/*     */     public final int sizeX;
/*     */     
/*     */     public final int sizeY;
/*     */     
/*     */     EnumArt(String titleIn, int width, int height, int textureU, int textureV) {
/* 211 */       this.title = titleIn;
/* 212 */       this.sizeX = width;
/* 213 */       this.sizeY = height;
/* 214 */       this.offsetX = textureU;
/* 215 */       this.offsetY = textureV;
/*     */     }
/*     */     
/*     */     public final int offsetX;
/*     */     public final int offsetY;
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityPainting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */