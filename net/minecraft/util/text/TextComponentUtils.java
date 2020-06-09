/*     */ package net.minecraft.util.text;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.EntityNotFoundException;
/*     */ import net.minecraft.command.EntitySelector;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextComponentUtils
/*     */ {
/*     */   public static ITextComponent processComponent(ICommandSender commandSender, ITextComponent component, Entity entityIn) throws CommandException {
/*     */     ITextComponent itextcomponent;
/*  17 */     if (component instanceof TextComponentScore) {
/*     */       
/*  19 */       TextComponentScore textcomponentscore = (TextComponentScore)component;
/*  20 */       String s = textcomponentscore.getName();
/*     */       
/*  22 */       if (EntitySelector.hasArguments(s)) {
/*     */         
/*  24 */         List<Entity> list = EntitySelector.matchEntities(commandSender, s, Entity.class);
/*     */         
/*  26 */         if (list.size() != 1)
/*     */         {
/*  28 */           throw new EntityNotFoundException("commands.generic.selector.notFound", new Object[] { s });
/*     */         }
/*     */         
/*  31 */         Entity entity = list.get(0);
/*     */         
/*  33 */         if (entity instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */           
/*  35 */           s = entity.getName();
/*     */         }
/*     */         else {
/*     */           
/*  39 */           s = entity.getCachedUniqueIdString();
/*     */         } 
/*     */       } 
/*     */       
/*  43 */       String s2 = (entityIn != null && s.equals("*")) ? entityIn.getName() : s;
/*  44 */       itextcomponent = new TextComponentScore(s2, textcomponentscore.getObjective());
/*  45 */       ((TextComponentScore)itextcomponent).setValue(textcomponentscore.getUnformattedComponentText());
/*  46 */       ((TextComponentScore)itextcomponent).resolve(commandSender);
/*     */     }
/*  48 */     else if (component instanceof TextComponentSelector) {
/*     */       
/*  50 */       String s1 = ((TextComponentSelector)component).getSelector();
/*  51 */       itextcomponent = EntitySelector.matchEntitiesToTextComponent(commandSender, s1);
/*     */       
/*  53 */       if (itextcomponent == null)
/*     */       {
/*  55 */         itextcomponent = new TextComponentString("");
/*     */       }
/*     */     }
/*  58 */     else if (component instanceof TextComponentString) {
/*     */       
/*  60 */       itextcomponent = new TextComponentString(((TextComponentString)component).getText());
/*     */     }
/*  62 */     else if (component instanceof TextComponentKeybind) {
/*     */       
/*  64 */       itextcomponent = new TextComponentKeybind(((TextComponentKeybind)component).func_193633_h());
/*     */     }
/*     */     else {
/*     */       
/*  68 */       if (!(component instanceof TextComponentTranslation))
/*     */       {
/*  70 */         return component;
/*     */       }
/*     */       
/*  73 */       Object[] aobject = ((TextComponentTranslation)component).getFormatArgs();
/*     */       
/*  75 */       for (int i = 0; i < aobject.length; i++) {
/*     */         
/*  77 */         Object object = aobject[i];
/*     */         
/*  79 */         if (object instanceof ITextComponent)
/*     */         {
/*  81 */           aobject[i] = processComponent(commandSender, (ITextComponent)object, entityIn);
/*     */         }
/*     */       } 
/*     */       
/*  85 */       itextcomponent = new TextComponentTranslation(((TextComponentTranslation)component).getKey(), aobject);
/*     */     } 
/*     */     
/*  88 */     Style style = component.getStyle();
/*     */     
/*  90 */     if (style != null)
/*     */     {
/*  92 */       itextcomponent.setStyle(style.createShallowCopy());
/*     */     }
/*     */     
/*  95 */     for (ITextComponent itextcomponent1 : component.getSiblings())
/*     */     {
/*  97 */       itextcomponent.appendSibling(processComponent(commandSender, itextcomponent1, entityIn));
/*     */     }
/*     */     
/* 100 */     return itextcomponent;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\TextComponentUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */