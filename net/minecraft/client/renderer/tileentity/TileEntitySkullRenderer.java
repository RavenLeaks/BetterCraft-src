/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelDragonHead;
/*     */ import net.minecraft.client.model.ModelHumanoidHead;
/*     */ import net.minecraft.client.model.ModelSkeletonHead;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class TileEntitySkullRenderer
/*     */   extends TileEntitySpecialRenderer<TileEntitySkull>
/*     */ {
/*  24 */   private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
/*  25 */   private static final ResourceLocation WITHER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
/*  26 */   private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
/*  27 */   private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");
/*  28 */   private static final ResourceLocation DRAGON_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon.png");
/*  29 */   private final ModelDragonHead dragonHead = new ModelDragonHead(0.0F);
/*     */   public static TileEntitySkullRenderer instance;
/*  31 */   private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);
/*  32 */   private final ModelSkeletonHead humanoidHead = (ModelSkeletonHead)new ModelHumanoidHead();
/*     */ 
/*     */   
/*     */   public void func_192841_a(TileEntitySkull p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/*  36 */     EnumFacing enumfacing = EnumFacing.getFront(p_192841_1_.getBlockMetadata() & 0x7);
/*  37 */     float f = p_192841_1_.getAnimationProgress(p_192841_8_);
/*  38 */     renderSkull((float)p_192841_2_, (float)p_192841_4_, (float)p_192841_6_, enumfacing, (p_192841_1_.getSkullRotation() * 360) / 16.0F, p_192841_1_.getSkullType(), p_192841_1_.getPlayerProfile(), p_192841_9_, f);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
/*  43 */     super.setRendererDispatcher(rendererDispatcherIn);
/*  44 */     instance = this;
/*     */   }
/*     */   
/*     */   public void renderSkull(float x, float y, float z, EnumFacing facing, float p_188190_5_, int skullType, @Nullable GameProfile profile, int destroyStage, float animateTicks) {
/*     */     ModelDragonHead modelDragonHead;
/*  49 */     ModelSkeletonHead modelSkeletonHead = this.skeletonHead;
/*     */     
/*  51 */     if (destroyStage >= 0) {
/*     */       
/*  53 */       bindTexture(DESTROY_STAGES[destroyStage]);
/*  54 */       GlStateManager.matrixMode(5890);
/*  55 */       GlStateManager.pushMatrix();
/*  56 */       GlStateManager.scale(4.0F, 2.0F, 1.0F);
/*  57 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  58 */       GlStateManager.matrixMode(5888);
/*     */     } else {
/*     */       ResourceLocation resourcelocation;
/*     */       
/*  62 */       switch (skullType) {
/*     */ 
/*     */         
/*     */         default:
/*  66 */           bindTexture(SKELETON_TEXTURES);
/*     */           break;
/*     */         
/*     */         case 1:
/*  70 */           bindTexture(WITHER_SKELETON_TEXTURES);
/*     */           break;
/*     */         
/*     */         case 2:
/*  74 */           bindTexture(ZOMBIE_TEXTURES);
/*  75 */           modelSkeletonHead = this.humanoidHead;
/*     */           break;
/*     */         
/*     */         case 3:
/*  79 */           modelSkeletonHead = this.humanoidHead;
/*  80 */           resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();
/*     */           
/*  82 */           if (profile != null) {
/*     */             
/*  84 */             Minecraft minecraft = Minecraft.getMinecraft();
/*  85 */             Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);
/*     */             
/*  87 */             if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
/*     */               
/*  89 */               resourcelocation = minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
/*     */             }
/*     */             else {
/*     */               
/*  93 */               UUID uuid = EntityPlayer.getUUID(profile);
/*  94 */               resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
/*     */             } 
/*     */           } 
/*     */           
/*  98 */           bindTexture(resourcelocation);
/*     */           break;
/*     */         
/*     */         case 4:
/* 102 */           bindTexture(CREEPER_TEXTURES);
/*     */           break;
/*     */         
/*     */         case 5:
/* 106 */           bindTexture(DRAGON_TEXTURES);
/* 107 */           modelDragonHead = this.dragonHead;
/*     */           break;
/*     */       } 
/*     */     } 
/* 111 */     GlStateManager.pushMatrix();
/* 112 */     GlStateManager.disableCull();
/*     */     
/* 114 */     if (facing == EnumFacing.UP) {
/*     */       
/* 116 */       GlStateManager.translate(x + 0.5F, y, z + 0.5F);
/*     */     }
/*     */     else {
/*     */       
/* 120 */       switch (facing) {
/*     */         
/*     */         case NORTH:
/* 123 */           GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.74F);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 127 */           GlStateManager.translate(x + 0.5F, y + 0.25F, z + 0.26F);
/* 128 */           p_188190_5_ = 180.0F;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 132 */           GlStateManager.translate(x + 0.74F, y + 0.25F, z + 0.5F);
/* 133 */           p_188190_5_ = 270.0F;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 138 */           GlStateManager.translate(x + 0.26F, y + 0.25F, z + 0.5F);
/* 139 */           p_188190_5_ = 90.0F;
/*     */           break;
/*     */       } 
/*     */     } 
/* 143 */     float f = 0.0625F;
/* 144 */     GlStateManager.enableRescaleNormal();
/* 145 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 146 */     GlStateManager.enableAlpha();
/*     */     
/* 148 */     if (skullType == 3)
/*     */     {
/* 150 */       GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
/*     */     }
/*     */     
/* 153 */     modelDragonHead.render(null, animateTicks, 0.0F, 0.0F, p_188190_5_, 0.0F, 0.0625F);
/* 154 */     GlStateManager.popMatrix();
/*     */     
/* 156 */     if (destroyStage >= 0) {
/*     */       
/* 158 */       GlStateManager.matrixMode(5890);
/* 159 */       GlStateManager.popMatrix();
/* 160 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntitySkullRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */