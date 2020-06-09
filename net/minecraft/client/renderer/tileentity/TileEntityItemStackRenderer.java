/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.block.BlockShulkerBox;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelShield;
/*     */ import net.minecraft.client.renderer.BannerTextures;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.tileentity.TileEntityBed;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class TileEntityItemStackRenderer {
/*  30 */   private static final TileEntityShulkerBox[] field_191274_b = new TileEntityShulkerBox[16];
/*     */   
/*  32 */   private final TileEntityChest chestBasic = new TileEntityChest(BlockChest.Type.BASIC);
/*  33 */   private final TileEntityChest chestTrap = new TileEntityChest(BlockChest.Type.TRAP);
/*  34 */   private final TileEntityEnderChest enderChest = new TileEntityEnderChest();
/*  35 */   private final TileEntityBanner banner = new TileEntityBanner();
/*  36 */   private final TileEntityBed field_193843_g = new TileEntityBed();
/*  37 */   private final TileEntitySkull skull = new TileEntitySkull();
/*  38 */   private final ModelShield modelShield = new ModelShield();
/*     */ 
/*     */   
/*     */   public void renderByItem(ItemStack itemStackIn) {
/*  42 */     func_192838_a(itemStackIn, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192838_a(ItemStack p_192838_1_, float p_192838_2_) {
/*  47 */     Item item = p_192838_1_.getItem();
/*     */     
/*  49 */     if (item == Items.BANNER) {
/*     */       
/*  51 */       this.banner.setItemValues(p_192838_1_, false);
/*  52 */       TileEntityRendererDispatcher.instance.func_192855_a((TileEntity)this.banner, 0.0D, 0.0D, 0.0D, 0.0F, p_192838_2_);
/*     */     }
/*  54 */     else if (item == Items.BED) {
/*     */       
/*  56 */       this.field_193843_g.func_193051_a(p_192838_1_);
/*  57 */       TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.field_193843_g, 0.0D, 0.0D, 0.0D, 0.0F);
/*     */     }
/*  59 */     else if (item == Items.SHIELD) {
/*     */       
/*  61 */       if (p_192838_1_.getSubCompound("BlockEntityTag") != null) {
/*     */         
/*  63 */         this.banner.setItemValues(p_192838_1_, true);
/*  64 */         Minecraft.getMinecraft().getTextureManager().bindTexture(BannerTextures.SHIELD_DESIGNS.getResourceLocation(this.banner.getPatternResourceLocation(), this.banner.getPatternList(), this.banner.getColorList()));
/*     */       }
/*     */       else {
/*     */         
/*  68 */         Minecraft.getMinecraft().getTextureManager().bindTexture(BannerTextures.SHIELD_BASE_TEXTURE);
/*     */       } 
/*     */       
/*  71 */       GlStateManager.pushMatrix();
/*  72 */       GlStateManager.scale(1.0F, -1.0F, -1.0F);
/*  73 */       this.modelShield.render();
/*  74 */       GlStateManager.popMatrix();
/*     */     }
/*  76 */     else if (item == Items.SKULL) {
/*     */       
/*  78 */       GameProfile gameprofile = null;
/*     */       
/*  80 */       if (p_192838_1_.hasTagCompound()) {
/*     */         
/*  82 */         NBTTagCompound nbttagcompound = p_192838_1_.getTagCompound();
/*     */         
/*  84 */         if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*     */           
/*  86 */           gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*     */         }
/*  88 */         else if (nbttagcompound.hasKey("SkullOwner", 8) && !StringUtils.isBlank(nbttagcompound.getString("SkullOwner"))) {
/*     */           
/*  90 */           GameProfile gameprofile1 = new GameProfile(null, nbttagcompound.getString("SkullOwner"));
/*  91 */           gameprofile = TileEntitySkull.updateGameprofile(gameprofile1);
/*  92 */           nbttagcompound.removeTag("SkullOwner");
/*  93 */           nbttagcompound.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
/*     */         } 
/*     */       } 
/*     */       
/*  97 */       if (TileEntitySkullRenderer.instance != null)
/*     */       {
/*  99 */         GlStateManager.pushMatrix();
/* 100 */         GlStateManager.disableCull();
/* 101 */         TileEntitySkullRenderer.instance.renderSkull(0.0F, 0.0F, 0.0F, EnumFacing.UP, 180.0F, p_192838_1_.getMetadata(), gameprofile, -1, 0.0F);
/* 102 */         GlStateManager.enableCull();
/* 103 */         GlStateManager.popMatrix();
/*     */       }
/*     */     
/* 106 */     } else if (item == Item.getItemFromBlock(Blocks.ENDER_CHEST)) {
/*     */       
/* 108 */       TileEntityRendererDispatcher.instance.func_192855_a((TileEntity)this.enderChest, 0.0D, 0.0D, 0.0D, 0.0F, p_192838_2_);
/*     */     }
/* 110 */     else if (item == Item.getItemFromBlock(Blocks.TRAPPED_CHEST)) {
/*     */       
/* 112 */       TileEntityRendererDispatcher.instance.func_192855_a((TileEntity)this.chestTrap, 0.0D, 0.0D, 0.0D, 0.0F, p_192838_2_);
/*     */     }
/* 114 */     else if (Block.getBlockFromItem(item) instanceof BlockShulkerBox) {
/*     */       
/* 116 */       TileEntityRendererDispatcher.instance.func_192855_a((TileEntity)field_191274_b[BlockShulkerBox.func_190955_b(item).getMetadata()], 0.0D, 0.0D, 0.0D, 0.0F, p_192838_2_);
/*     */     }
/*     */     else {
/*     */       
/* 120 */       TileEntityRendererDispatcher.instance.func_192855_a((TileEntity)this.chestBasic, 0.0D, 0.0D, 0.0D, 0.0F, p_192838_2_);
/*     */     } 
/*     */   } static {
/*     */     byte b;
/*     */     int i;
/*     */     EnumDyeColor[] arrayOfEnumDyeColor;
/* 126 */     for (i = (arrayOfEnumDyeColor = EnumDyeColor.values()).length, b = 0; b < i; ) { EnumDyeColor enumdyecolor = arrayOfEnumDyeColor[b];
/*     */       
/* 128 */       field_191274_b[enumdyecolor.getMetadata()] = new TileEntityShulkerBox(enumdyecolor);
/*     */       b++; }
/*     */   
/* 131 */   } public static TileEntityItemStackRenderer instance = new TileEntityItemStackRenderer();
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityItemStackRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */