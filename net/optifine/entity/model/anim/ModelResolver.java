/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.optifine.entity.model.CustomModelRenderer;
/*     */ import net.optifine.entity.model.ModelAdapter;
/*     */ import optifine.Config;
/*     */ 
/*     */ public class ModelResolver
/*     */   implements IModelResolver
/*     */ {
/*     */   private ModelAdapter modelAdapter;
/*     */   private ModelBase model;
/*     */   private CustomModelRenderer[] customModelRenderers;
/*     */   private ModelRenderer thisModelRenderer;
/*     */   private ModelRenderer partModelRenderer;
/*     */   private IRenderResolver renderResolver;
/*     */   
/*     */   public ModelResolver(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer[] customModelRenderers) {
/*  21 */     this.modelAdapter = modelAdapter;
/*  22 */     this.model = model;
/*  23 */     this.customModelRenderers = customModelRenderers;
/*  24 */     Class<?> oclass = modelAdapter.getEntityClass();
/*     */     
/*  26 */     if (TileEntity.class.isAssignableFrom(oclass)) {
/*     */       
/*  28 */       this.renderResolver = new RenderResolverTileEntity();
/*     */     }
/*     */     else {
/*     */       
/*  32 */       this.renderResolver = new RenderResolverEntity();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IExpression getExpression(String name) {
/*  38 */     ModelVariable modelvariable = getModelVariable(name);
/*     */     
/*  40 */     if (modelvariable != null)
/*     */     {
/*  42 */       return modelvariable;
/*     */     }
/*     */ 
/*     */     
/*  46 */     IExpression iexpression = this.renderResolver.getParameter(name);
/*  47 */     return (iexpression != null) ? iexpression : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelRenderer getModelRenderer(String name) {
/*  53 */     if (name == null)
/*     */     {
/*  55 */       return null;
/*     */     }
/*  57 */     if (name.indexOf(":") >= 0) {
/*     */       
/*  59 */       String[] astring = Config.tokenize(name, ":");
/*  60 */       ModelRenderer modelrenderer3 = getModelRenderer(astring[0]);
/*     */       
/*  62 */       for (int j = 1; j < astring.length; j++) {
/*     */         
/*  64 */         String s = astring[j];
/*  65 */         ModelRenderer modelrenderer4 = modelrenderer3.getChildDeep(s);
/*     */         
/*  67 */         if (modelrenderer4 == null)
/*     */         {
/*  69 */           return null;
/*     */         }
/*     */         
/*  72 */         modelrenderer3 = modelrenderer4;
/*     */       } 
/*     */       
/*  75 */       return modelrenderer3;
/*     */     } 
/*  77 */     if (this.thisModelRenderer != null && name.equals("this"))
/*     */     {
/*  79 */       return this.thisModelRenderer;
/*     */     }
/*  81 */     if (this.partModelRenderer != null && name.equals("part"))
/*     */     {
/*  83 */       return this.partModelRenderer;
/*     */     }
/*     */ 
/*     */     
/*  87 */     ModelRenderer modelrenderer = this.modelAdapter.getModelRenderer(this.model, name);
/*     */     
/*  89 */     if (modelrenderer != null)
/*     */     {
/*  91 */       return modelrenderer;
/*     */     }
/*     */ 
/*     */     
/*  95 */     for (int i = 0; i < this.customModelRenderers.length; i++) {
/*     */       
/*  97 */       CustomModelRenderer custommodelrenderer = this.customModelRenderers[i];
/*  98 */       ModelRenderer modelrenderer1 = custommodelrenderer.getModelRenderer();
/*     */       
/* 100 */       if (name.equals(modelrenderer1.getId()))
/*     */       {
/* 102 */         return modelrenderer1;
/*     */       }
/*     */       
/* 105 */       ModelRenderer modelrenderer2 = modelrenderer1.getChildDeep(name);
/*     */       
/* 107 */       if (modelrenderer2 != null)
/*     */       {
/* 109 */         return modelrenderer2;
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelVariable getModelVariable(String name) {
/* 120 */     String[] astring = Config.tokenize(name, ".");
/*     */     
/* 122 */     if (astring.length != 2)
/*     */     {
/* 124 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 128 */     String s = astring[0];
/* 129 */     String s1 = astring[1];
/* 130 */     ModelRenderer modelrenderer = getModelRenderer(s);
/*     */     
/* 132 */     if (modelrenderer == null)
/*     */     {
/* 134 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 138 */     EnumModelVariable enummodelvariable = EnumModelVariable.parse(s1);
/* 139 */     return (enummodelvariable == null) ? null : new ModelVariable(name, modelrenderer, enummodelvariable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPartModelRenderer(ModelRenderer partModelRenderer) {
/* 146 */     this.partModelRenderer = partModelRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThisModelRenderer(ModelRenderer thisModelRenderer) {
/* 151 */     this.thisModelRenderer = thisModelRenderer;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\ModelResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */