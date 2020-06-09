/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import optifine.Config;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExpressionParser
/*     */ {
/*     */   private IModelResolver modelResolver;
/*     */   
/*     */   public ExpressionParser(IModelResolver modelResolver) {
/*  22 */     this.modelResolver = modelResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IExpression parse(String str) throws ParseException {
/*     */     try {
/*  29 */       Token[] atoken = TokenParser.parse(str);
/*     */       
/*  31 */       if (atoken == null)
/*     */       {
/*  33 */         return null;
/*     */       }
/*     */ 
/*     */       
/*  37 */       Deque<Token> deque = new ArrayDeque<>(Arrays.asList(atoken));
/*  38 */       return parseInfix(deque);
/*     */     
/*     */     }
/*  41 */     catch (IOException ioexception) {
/*     */       
/*  43 */       throw new ParseException(ioexception.getMessage(), ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IExpression parseInfix(Deque<Token> deque) throws ParseException {
/*  49 */     if (deque.isEmpty())
/*     */     {
/*  51 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  55 */     List<IExpression> list = new LinkedList<>();
/*  56 */     List<Token> list1 = new LinkedList<>();
/*  57 */     IExpression iexpression = parseExpression(deque);
/*  58 */     checkNull(iexpression, "Missing expression");
/*  59 */     list.add(iexpression);
/*     */ 
/*     */     
/*     */     while (true) {
/*  63 */       Token token = deque.poll();
/*     */       
/*  65 */       if (token == null)
/*     */       {
/*  67 */         return makeInfix(list, list1);
/*     */       }
/*     */       
/*  70 */       if (token.getType() != EnumTokenType.OPERATOR)
/*     */       {
/*  72 */         throw new ParseException("Invalid operator: " + token);
/*     */       }
/*     */       
/*  75 */       IExpression iexpression1 = parseExpression(deque);
/*  76 */       checkNull(iexpression1, "Missing expression");
/*  77 */       list1.add(token);
/*  78 */       list.add(iexpression1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeInfix(List<IExpression> listExpr, List<Token> listOper) throws ParseException {
/*  85 */     List<EnumFunctionType> list = new LinkedList<>();
/*     */     
/*  87 */     for (Token token : listOper) {
/*     */       
/*  89 */       EnumFunctionType enumfunctiontype = EnumFunctionType.parse(token.getText());
/*  90 */       checkNull(enumfunctiontype, "Invalid operator: " + token);
/*  91 */       list.add(enumfunctiontype);
/*     */     } 
/*     */     
/*  94 */     return makeInfixFunc(listExpr, list);
/*     */   }
/*     */ 
/*     */   
/*     */   private IExpression makeInfixFunc(List<IExpression> listExpr, List<EnumFunctionType> listFunc) throws ParseException {
/*  99 */     if (listExpr.size() != listFunc.size() + 1)
/*     */     {
/* 101 */       throw new ParseException("Invalid infix expression, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
/*     */     }
/* 103 */     if (listExpr.size() == 1)
/*     */     {
/* 105 */       return listExpr.get(0);
/*     */     }
/*     */ 
/*     */     
/* 109 */     int i = Integer.MAX_VALUE;
/* 110 */     int j = Integer.MIN_VALUE;
/*     */     
/* 112 */     for (EnumFunctionType enumfunctiontype : listFunc) {
/*     */       
/* 114 */       i = Math.min(enumfunctiontype.getPrecedence(), i);
/* 115 */       j = Math.max(enumfunctiontype.getPrecedence(), j);
/*     */     } 
/*     */     
/* 118 */     if (j >= i && j - i <= 10) {
/*     */       
/* 120 */       for (int k = j; k >= i; k--)
/*     */       {
/* 122 */         mergeOperators(listExpr, listFunc, k);
/*     */       }
/*     */       
/* 125 */       if (listExpr.size() == 1 && listFunc.size() == 0)
/*     */       {
/* 127 */         return listExpr.get(0);
/*     */       }
/*     */ 
/*     */       
/* 131 */       throw new ParseException("Error merging operators, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 136 */     throw new ParseException("Invalid infix precedence, min: " + i + ", max: " + j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mergeOperators(List<IExpression> listExpr, List<EnumFunctionType> listFuncs, int precedence) {
/* 143 */     for (int i = 0; i < listFuncs.size(); i++) {
/*     */       
/* 145 */       EnumFunctionType enumfunctiontype = listFuncs.get(i);
/*     */       
/* 147 */       if (enumfunctiontype.getPrecedence() == precedence) {
/*     */         
/* 149 */         listFuncs.remove(i);
/* 150 */         IExpression iexpression = listExpr.remove(i);
/* 151 */         IExpression iexpression1 = listExpr.remove(i);
/* 152 */         IExpression iexpression2 = new Function(enumfunctiontype, new IExpression[] { iexpression, iexpression1 });
/* 153 */         listExpr.add(i, iexpression2);
/* 154 */         i--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private IExpression parseExpression(Deque<Token> deque) throws ParseException {
/*     */     EnumFunctionType enumfunctiontype, enumfunctiontype1;
/* 161 */     Token token = deque.poll();
/* 162 */     checkNull(token, "Missing expression");
/*     */     
/* 164 */     switch (token.getType()) {
/*     */       
/*     */       case CONSTANT:
/* 167 */         return makeConstant(token);
/*     */       
/*     */       case IDENTIFIER:
/* 170 */         enumfunctiontype = getFunctionType(token, deque);
/*     */         
/* 172 */         if (enumfunctiontype != null)
/*     */         {
/* 174 */           return makeFunction(enumfunctiontype, deque);
/*     */         }
/*     */         
/* 177 */         return makeVariable(token);
/*     */       
/*     */       case BRACKET_OPEN:
/* 180 */         return makeBracketed(token, deque);
/*     */       
/*     */       case OPERATOR:
/* 183 */         enumfunctiontype1 = EnumFunctionType.parse(token.getText());
/* 184 */         checkNull(enumfunctiontype1, "Invalid operator: " + token);
/*     */         
/* 186 */         if (enumfunctiontype1 == EnumFunctionType.PLUS)
/*     */         {
/* 188 */           return parseExpression(deque);
/*     */         }
/* 190 */         if (enumfunctiontype1 == EnumFunctionType.MINUS) {
/*     */           
/* 192 */           IExpression iexpression = parseExpression(deque);
/* 193 */           return new Function(EnumFunctionType.NEG, new IExpression[] { iexpression });
/*     */         } 
/*     */         break;
/*     */     } 
/* 197 */     throw new ParseException("Invalid expression: " + token);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IExpression makeConstant(Token token) throws ParseException {
/* 203 */     float f = Config.parseFloat(token.getText(), Float.NaN);
/*     */     
/* 205 */     if (f == Float.NaN)
/*     */     {
/* 207 */       throw new ParseException("Invalid float value: " + token);
/*     */     }
/*     */ 
/*     */     
/* 211 */     return new Constant(f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumFunctionType getFunctionType(Token token, Deque<Token> deque) throws ParseException {
/* 217 */     Token token1 = deque.peek();
/*     */     
/* 219 */     if (token1 != null && token1.getType() == EnumTokenType.BRACKET_OPEN) {
/*     */       
/* 221 */       EnumFunctionType enumfunctiontype1 = EnumFunctionType.parse(token1.getText());
/* 222 */       checkNull(enumfunctiontype1, "Unknown function: " + token1);
/* 223 */       return enumfunctiontype1;
/*     */     } 
/*     */ 
/*     */     
/* 227 */     EnumFunctionType enumfunctiontype = EnumFunctionType.parse(token1.getText());
/*     */     
/* 229 */     if (enumfunctiontype == null)
/*     */     {
/* 231 */       return null;
/*     */     }
/* 233 */     if (enumfunctiontype.getCountArguments() > 0)
/*     */     {
/* 235 */       throw new ParseException("Missing arguments: " + enumfunctiontype);
/*     */     }
/*     */ 
/*     */     
/* 239 */     return enumfunctiontype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeFunction(EnumFunctionType type, Deque<Token> deque) throws ParseException {
/* 246 */     if (type.getCountArguments() == 0)
/*     */     {
/* 248 */       return makeFunction(type, new IExpression[0]);
/*     */     }
/*     */ 
/*     */     
/* 252 */     Token token = deque.poll();
/* 253 */     Deque<Token> deque1 = getGroup(deque, EnumTokenType.BRACKET_CLOSE, true);
/* 254 */     IExpression[] aiexpression = parseExpressions(deque1);
/* 255 */     return makeFunction(type, aiexpression);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression[] parseExpressions(Deque<Token> deque) throws ParseException {
/* 261 */     List<IExpression> list = new ArrayList<>();
/*     */ 
/*     */     
/*     */     while (true) {
/* 265 */       Deque<Token> deque1 = getGroup(deque, EnumTokenType.COMMA, false);
/* 266 */       IExpression iexpression = parseInfix(deque1);
/*     */       
/* 268 */       if (iexpression == null) {
/*     */         
/* 270 */         IExpression[] aiexpression = list.<IExpression>toArray(new IExpression[list.size()]);
/* 271 */         return aiexpression;
/*     */       } 
/*     */       
/* 274 */       list.add(iexpression);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static IExpression makeFunction(EnumFunctionType type, IExpression[] exprs) throws ParseException {
/* 280 */     if (type.getCountArguments() != exprs.length)
/*     */     {
/* 282 */       throw new ParseException("Invalid number of arguments: " + exprs.length + ", should be: " + type.getCountArguments() + ", function: " + type.getName());
/*     */     }
/*     */ 
/*     */     
/* 286 */     return new Function(type, exprs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeVariable(Token token) throws ParseException {
/* 292 */     if (this.modelResolver == null)
/*     */     {
/* 294 */       throw new ParseException("Model variable not found: " + token);
/*     */     }
/*     */ 
/*     */     
/* 298 */     IExpression iexpression = this.modelResolver.getExpression(token.getText());
/*     */     
/* 300 */     if (iexpression == null)
/*     */     {
/* 302 */       throw new ParseException("Model variable not found: " + token);
/*     */     }
/*     */ 
/*     */     
/* 306 */     return iexpression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeBracketed(Token token, Deque<Token> deque) throws ParseException {
/* 313 */     Deque<Token> deque1 = getGroup(deque, EnumTokenType.BRACKET_CLOSE, true);
/* 314 */     return parseInfix(deque1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Deque<Token> getGroup(Deque<Token> deque, EnumTokenType tokenTypeEnd, boolean tokenEndRequired) throws ParseException {
/* 319 */     Deque<Token> deque1 = new ArrayDeque<>();
/* 320 */     int i = 0;
/* 321 */     Iterator<Token> iterator = deque1.iterator();
/*     */     
/* 323 */     while (iterator.hasNext()) {
/*     */       
/* 325 */       Token token = iterator.next();
/* 326 */       iterator.remove();
/*     */       
/* 328 */       if (i == 0 && token.getType() == tokenTypeEnd)
/*     */       {
/* 330 */         return deque1;
/*     */       }
/*     */       
/* 333 */       deque1.add(token);
/*     */       
/* 335 */       if (token.getType() == EnumTokenType.BRACKET_OPEN)
/*     */       {
/* 337 */         i++;
/*     */       }
/*     */       
/* 340 */       if (token.getType() == EnumTokenType.BRACKET_CLOSE)
/*     */       {
/* 342 */         i--;
/*     */       }
/*     */     } 
/*     */     
/* 346 */     if (tokenEndRequired)
/*     */     {
/* 348 */       throw new ParseException("Missing end token: " + tokenTypeEnd);
/*     */     }
/*     */ 
/*     */     
/* 352 */     return deque1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String message) throws ParseException {
/* 358 */     if (obj == null)
/*     */     {
/* 360 */       throw new ParseException(message);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/* 366 */     ExpressionParser expressionparser = new ExpressionParser(null);
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*     */       try {
/* 372 */         InputStreamReader inputstreamreader = new InputStreamReader(System.in);
/* 373 */         BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 374 */         String s = bufferedreader.readLine();
/*     */         
/* 376 */         if (s.length() <= 0) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 381 */         IExpression iexpression = expressionparser.parse(s);
/* 382 */         float f = iexpression.eval();
/* 383 */         Config.dbg(s + " = " + f);
/*     */       }
/* 385 */       catch (Exception exception) {
/*     */         
/* 387 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\ExpressionParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */