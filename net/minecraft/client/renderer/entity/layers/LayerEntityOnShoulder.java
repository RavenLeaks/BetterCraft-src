/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelParrot;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RenderParrot;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityParrot;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class LayerEntityOnShoulder
/*     */   implements LayerRenderer<EntityPlayer> {
/*     */   private final RenderManager field_192867_c;
/*     */   protected RenderLivingBase<? extends EntityLivingBase> field_192865_a;
/*     */   private ModelBase field_192868_d;
/*     */   private ResourceLocation field_192869_e;
/*     */   private UUID field_192870_f;
/*     */   private Class<?> field_192871_g;
/*     */   protected RenderLivingBase<? extends EntityLivingBase> field_192866_b;
/*     */   private ModelBase field_192872_h;
/*     */   private ResourceLocation field_192873_i;
/*     */   private UUID field_192874_j;
/*     */   private Class<?> field_192875_k;
/*     */   
/*     */   public LayerEntityOnShoulder(RenderManager p_i47370_1_) {
/*  34 */     this.field_192867_c = p_i47370_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doRenderLayer(EntityPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  39 */     if (entitylivingbaseIn.func_192023_dk() != null || entitylivingbaseIn.func_192025_dl() != null) {
/*     */       
/*  41 */       GlStateManager.enableRescaleNormal();
/*  42 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  43 */       NBTTagCompound nbttagcompound = entitylivingbaseIn.func_192023_dk();
/*     */       
/*  45 */       if (!nbttagcompound.hasNoTags()) {
/*     */         
/*  47 */         DataHolder layerentityonshoulder$dataholder = func_192864_a(entitylivingbaseIn, this.field_192870_f, nbttagcompound, this.field_192865_a, this.field_192868_d, this.field_192869_e, this.field_192871_g, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, true);
/*  48 */         this.field_192870_f = layerentityonshoulder$dataholder.field_192882_a;
/*  49 */         this.field_192865_a = layerentityonshoulder$dataholder.field_192883_b;
/*  50 */         this.field_192869_e = layerentityonshoulder$dataholder.field_192885_d;
/*  51 */         this.field_192868_d = layerentityonshoulder$dataholder.field_192884_c;
/*  52 */         this.field_192871_g = layerentityonshoulder$dataholder.field_192886_e;
/*     */       } 
/*     */       
/*  55 */       NBTTagCompound nbttagcompound1 = entitylivingbaseIn.func_192025_dl();
/*     */       
/*  57 */       if (!nbttagcompound1.hasNoTags()) {
/*     */         
/*  59 */         DataHolder layerentityonshoulder$dataholder1 = func_192864_a(entitylivingbaseIn, this.field_192874_j, nbttagcompound1, this.field_192866_b, this.field_192872_h, this.field_192873_i, this.field_192875_k, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, false);
/*  60 */         this.field_192874_j = layerentityonshoulder$dataholder1.field_192882_a;
/*  61 */         this.field_192866_b = layerentityonshoulder$dataholder1.field_192883_b;
/*  62 */         this.field_192873_i = layerentityonshoulder$dataholder1.field_192885_d;
/*  63 */         this.field_192872_h = layerentityonshoulder$dataholder1.field_192884_c;
/*  64 */         this.field_192875_k = layerentityonshoulder$dataholder1.field_192886_e;
/*     */       } 
/*     */       
/*  67 */       GlStateManager.disableRescaleNormal();
/*     */     } 
/*     */   }
/*     */   private DataHolder func_192864_a(EntityPlayer p_192864_1_, @Nullable UUID p_192864_2_, NBTTagCompound p_192864_3_, RenderLivingBase<? extends EntityLivingBase> p_192864_4_, ModelBase p_192864_5_, ResourceLocation p_192864_6_, Class<?> p_192864_7_, float p_192864_8_, float p_192864_9_, float p_192864_10_, float p_192864_11_, float p_192864_12_, float p_192864_13_, float p_192864_14_, boolean p_192864_15_) {
/*     */     RenderParrot renderParrot;
/*     */     ModelParrot modelParrot;
/*  73 */     if (p_192864_2_ == null || !p_192864_2_.equals(p_192864_3_.getUniqueId("UUID"))) {
/*     */       
/*  75 */       p_192864_2_ = p_192864_3_.getUniqueId("UUID");
/*  76 */       p_192864_7_ = EntityList.func_192839_a(p_192864_3_.getString("id"));
/*     */       
/*  78 */       if (p_192864_7_ == EntityParrot.class) {
/*     */         
/*  80 */         renderParrot = new RenderParrot(this.field_192867_c);
/*  81 */         modelParrot = new ModelParrot();
/*  82 */         p_192864_6_ = RenderParrot.field_192862_a[p_192864_3_.getInteger("Variant")];
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     renderParrot.bindTexture(p_192864_6_);
/*  87 */     GlStateManager.pushMatrix();
/*  88 */     float f = p_192864_1_.isSneaking() ? -1.3F : -1.5F;
/*  89 */     float f1 = p_192864_15_ ? 0.4F : -0.4F;
/*  90 */     GlStateManager.translate(f1, f, 0.0F);
/*     */     
/*  92 */     if (p_192864_7_ == EntityParrot.class)
/*     */     {
/*  94 */       p_192864_11_ = 0.0F;
/*     */     }
/*     */     
/*  97 */     modelParrot.setLivingAnimations((EntityLivingBase)p_192864_1_, p_192864_8_, p_192864_9_, p_192864_10_);
/*  98 */     modelParrot.setRotationAngles(p_192864_8_, p_192864_9_, p_192864_11_, p_192864_12_, p_192864_13_, p_192864_14_, (Entity)p_192864_1_);
/*  99 */     modelParrot.render((Entity)p_192864_1_, p_192864_8_, p_192864_9_, p_192864_11_, p_192864_12_, p_192864_13_, p_192864_14_);
/* 100 */     GlStateManager.popMatrix();
/* 101 */     return new DataHolder(p_192864_2_, (RenderLivingBase<? extends EntityLivingBase>)renderParrot, (ModelBase)modelParrot, p_192864_6_, p_192864_7_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCombineTextures() {
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   class DataHolder
/*     */   {
/*     */     public UUID field_192882_a;
/*     */     public RenderLivingBase<? extends EntityLivingBase> field_192883_b;
/*     */     public ModelBase field_192884_c;
/*     */     public ResourceLocation field_192885_d;
/*     */     public Class<?> field_192886_e;
/*     */     
/*     */     public DataHolder(UUID p_i47463_2_, RenderLivingBase<? extends EntityLivingBase> p_i47463_3_, ModelBase p_i47463_4_, ResourceLocation p_i47463_5_, Class<?> p_i47463_6_) {
/* 119 */       this.field_192882_a = p_i47463_2_;
/* 120 */       this.field_192883_b = p_i47463_3_;
/* 121 */       this.field_192884_c = p_i47463_4_;
/* 122 */       this.field_192885_d = p_i47463_5_;
/* 123 */       this.field_192886_e = p_i47463_6_;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerEntityOnShoulder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */