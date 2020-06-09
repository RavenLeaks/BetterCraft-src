/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.BlockSkull;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.server.management.PlayerProfileCache;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ public class TileEntitySkull
/*     */   extends TileEntity implements ITickable {
/*     */   private int skullType;
/*     */   private int skullRotation;
/*     */   private GameProfile playerProfile;
/*     */   private int dragonAnimatedTicks;
/*     */   private boolean dragonAnimated;
/*     */   private static PlayerProfileCache profileCache;
/*     */   private static MinecraftSessionService sessionService;
/*     */   
/*     */   public static void setProfileCache(PlayerProfileCache profileCacheIn) {
/*  32 */     profileCache = profileCacheIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setSessionService(MinecraftSessionService sessionServiceIn) {
/*  37 */     sessionService = sessionServiceIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  42 */     super.writeToNBT(compound);
/*  43 */     compound.setByte("SkullType", (byte)(this.skullType & 0xFF));
/*  44 */     compound.setByte("Rot", (byte)(this.skullRotation & 0xFF));
/*     */     
/*  46 */     if (this.playerProfile != null) {
/*     */       
/*  48 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  49 */       NBTUtil.writeGameProfile(nbttagcompound, this.playerProfile);
/*  50 */       compound.setTag("Owner", (NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/*  53 */     return compound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  58 */     super.readFromNBT(compound);
/*  59 */     this.skullType = compound.getByte("SkullType");
/*  60 */     this.skullRotation = compound.getByte("Rot");
/*     */     
/*  62 */     if (this.skullType == 3)
/*     */     {
/*  64 */       if (compound.hasKey("Owner", 10)) {
/*     */         
/*  66 */         this.playerProfile = NBTUtil.readGameProfileFromNBT(compound.getCompoundTag("Owner"));
/*     */       }
/*  68 */       else if (compound.hasKey("ExtraType", 8)) {
/*     */         
/*  70 */         String s = compound.getString("ExtraType");
/*     */         
/*  72 */         if (!StringUtils.isNullOrEmpty(s)) {
/*     */           
/*  74 */           this.playerProfile = new GameProfile(null, s);
/*  75 */           updatePlayerProfile();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  86 */     if (this.skullType == 5)
/*     */     {
/*  88 */       if (this.world.isBlockPowered(this.pos)) {
/*     */         
/*  90 */         this.dragonAnimated = true;
/*  91 */         this.dragonAnimatedTicks++;
/*     */       }
/*     */       else {
/*     */         
/*  95 */         this.dragonAnimated = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAnimationProgress(float p_184295_1_) {
/* 102 */     return this.dragonAnimated ? (this.dragonAnimatedTicks + p_184295_1_) : this.dragonAnimatedTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GameProfile getPlayerProfile() {
/* 108 */     return this.playerProfile;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SPacketUpdateTileEntity getUpdatePacket() {
/* 114 */     return new SPacketUpdateTileEntity(this.pos, 4, getUpdateTag());
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getUpdateTag() {
/* 119 */     return writeToNBT(new NBTTagCompound());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setType(int type) {
/* 124 */     this.skullType = type;
/* 125 */     this.playerProfile = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerProfile(@Nullable GameProfile playerProfile) {
/* 130 */     this.skullType = 3;
/* 131 */     this.playerProfile = playerProfile;
/* 132 */     updatePlayerProfile();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePlayerProfile() {
/* 137 */     this.playerProfile = updateGameprofile(this.playerProfile);
/* 138 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public static GameProfile updateGameprofile(GameProfile input) {
/* 143 */     if (input != null && !StringUtils.isNullOrEmpty(input.getName())) {
/*     */       
/* 145 */       if (input.isComplete() && input.getProperties().containsKey("textures"))
/*     */       {
/* 147 */         return input;
/*     */       }
/* 149 */       if (profileCache != null && sessionService != null) {
/*     */         
/* 151 */         GameProfile gameprofile = profileCache.getGameProfileForUsername(input.getName());
/*     */         
/* 153 */         if (gameprofile == null)
/*     */         {
/* 155 */           return input;
/*     */         }
/*     */ 
/*     */         
/* 159 */         Property property = (Property)Iterables.getFirst(gameprofile.getProperties().get("textures"), null);
/*     */         
/* 161 */         if (property == null)
/*     */         {
/* 163 */           gameprofile = sessionService.fillProfileProperties(gameprofile, true);
/*     */         }
/*     */         
/* 166 */         return gameprofile;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 171 */       return input;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 176 */     return input;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkullType() {
/* 182 */     return this.skullType;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSkullRotation() {
/* 187 */     return this.skullRotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSkullRotation(int rotation) {
/* 192 */     this.skullRotation = rotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mirror(Mirror p_189668_1_) {
/* 197 */     if (this.world != null && this.world.getBlockState(getPos()).getValue((IProperty)BlockSkull.FACING) == EnumFacing.UP)
/*     */     {
/* 199 */       this.skullRotation = p_189668_1_.mirrorRotation(this.skullRotation, 16);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotate(Rotation p_189667_1_) {
/* 205 */     if (this.world != null && this.world.getBlockState(getPos()).getValue((IProperty)BlockSkull.FACING) == EnumFacing.UP)
/*     */     {
/* 207 */       this.skullRotation = p_189667_1_.rotate(this.skullRotation, 16);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntitySkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */