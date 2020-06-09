/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayerCustomHead
/*     */   implements LayerRenderer<EntityLivingBase>
/*     */ {
/*     */   private final ModelRenderer modelRenderer;
/*     */   
/*     */   public LayerCustomHead(ModelRenderer p_i46120_1_) {
/*  30 */     this.modelRenderer = p_i46120_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  35 */     ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
/*     */     
/*  37 */     if (!itemstack.func_190926_b()) {
/*     */       
/*  39 */       Item item = itemstack.getItem();
/*  40 */       Minecraft minecraft = Minecraft.getMinecraft();
/*  41 */       GlStateManager.pushMatrix();
/*     */       
/*  43 */       if (entitylivingbaseIn.isSneaking())
/*     */       {
/*  45 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/*  48 */       boolean flag = !(!(entitylivingbaseIn instanceof net.minecraft.entity.passive.EntityVillager) && !(entitylivingbaseIn instanceof net.minecraft.entity.monster.EntityZombieVillager));
/*     */       
/*  50 */       if (entitylivingbaseIn.isChild() && !(entitylivingbaseIn instanceof net.minecraft.entity.passive.EntityVillager)) {
/*     */         
/*  52 */         float f = 2.0F;
/*  53 */         float f1 = 1.4F;
/*  54 */         GlStateManager.translate(0.0F, 0.5F * scale, 0.0F);
/*  55 */         GlStateManager.scale(0.7F, 0.7F, 0.7F);
/*  56 */         GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
/*     */       } 
/*     */       
/*  59 */       this.modelRenderer.postRender(0.0625F);
/*  60 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/*  62 */       if (item == Items.SKULL) {
/*     */         
/*  64 */         float f2 = 1.1875F;
/*  65 */         GlStateManager.scale(1.1875F, -1.1875F, -1.1875F);
/*     */         
/*  67 */         if (flag)
/*     */         {
/*  69 */           GlStateManager.translate(0.0F, 0.0625F, 0.0F);
/*     */         }
/*     */         
/*  72 */         GameProfile gameprofile = null;
/*     */         
/*  74 */         if (itemstack.hasTagCompound()) {
/*     */           
/*  76 */           NBTTagCompound nbttagcompound = itemstack.getTagCompound();
/*     */           
/*  78 */           if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*     */             
/*  80 */             gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*     */           }
/*  82 */           else if (nbttagcompound.hasKey("SkullOwner", 8)) {
/*     */             
/*  84 */             String s = nbttagcompound.getString("SkullOwner");
/*     */             
/*  86 */             if (!StringUtils.isBlank(s)) {
/*     */               
/*  88 */               gameprofile = TileEntitySkull.updateGameprofile(new GameProfile(null, s));
/*  89 */               nbttagcompound.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  94 */         TileEntitySkullRenderer.instance.renderSkull(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, itemstack.getMetadata(), gameprofile, -1, limbSwing);
/*     */       }
/*  96 */       else if (!(item instanceof ItemArmor) || ((ItemArmor)item).getEquipmentSlot() != EntityEquipmentSlot.HEAD) {
/*     */         
/*  98 */         float f3 = 0.625F;
/*  99 */         GlStateManager.translate(0.0F, -0.25F, 0.0F);
/* 100 */         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 101 */         GlStateManager.scale(0.625F, -0.625F, -0.625F);
/*     */         
/* 103 */         if (flag)
/*     */         {
/* 105 */           GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*     */         }
/*     */         
/* 108 */         minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.HEAD);
/*     */       } 
/*     */       
/* 111 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCombineTextures() {
/* 117 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerCustomHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */