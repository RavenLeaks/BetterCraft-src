/*      */ package javazoom.jl.decoder;
/*      */ 
/*      */ import java.io.IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class SynthesisFilter
/*      */ {
/*      */   private float[] v1;
/*      */   private float[] v2;
/*      */   private float[] actual_v;
/*      */   private int actual_write_pos;
/*      */   private float[] samples;
/*      */   private int channel;
/*      */   private float scalefactor;
/*      */   private float[] eq;
/*      */   
/*      */   public SynthesisFilter(int channelnumber, float factor, float[] eq0) {
/*   73 */     if (d == null) {
/*      */       
/*   75 */       d = load_d();
/*   76 */       d16 = splitArray(d, 16);
/*      */     } 
/*      */     
/*   79 */     this.v1 = new float[512];
/*   80 */     this.v2 = new float[512];
/*   81 */     this.samples = new float[32];
/*   82 */     this.channel = channelnumber;
/*   83 */     this.scalefactor = factor;
/*   84 */     setEQ(this.eq);
/*      */ 
/*      */     
/*   87 */     reset();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEQ(float[] eq0) {
/*   92 */     this.eq = eq0;
/*   93 */     if (this.eq == null) {
/*      */       
/*   95 */       this.eq = new float[32];
/*   96 */       for (int i = 0; i < 32; i++)
/*   97 */         this.eq[i] = 1.0F; 
/*      */     } 
/*   99 */     if (this.eq.length < 32)
/*      */     {
/*  101 */       throw new IllegalArgumentException("eq0");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  139 */     for (int p = 0; p < 512; p++) {
/*  140 */       this.v2[p] = 0.0F; this.v1[p] = 0.0F;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  145 */     for (int p2 = 0; p2 < 32; p2++) {
/*  146 */       this.samples[p2] = 0.0F;
/*      */     }
/*  148 */     this.actual_v = this.v1;
/*  149 */     this.actual_write_pos = 15;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void input_sample(float sample, int subbandnumber) {
/*  158 */     this.samples[subbandnumber] = this.eq[subbandnumber] * sample;
/*      */   }
/*      */ 
/*      */   
/*      */   public void input_samples(float[] s) {
/*  163 */     for (int i = 31; i >= 0; i--)
/*      */     {
/*  165 */       this.samples[i] = s[i] * this.eq[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void compute_new_v() {
/*  200 */     float new_v31 = 0.0F, new_v30 = new_v31; float new_v29 = new_v30, new_v28 = new_v29, new_v27 = new_v28, new_v26 = new_v27, new_v25 = new_v26, new_v24 = new_v25, new_v23 = new_v24, new_v22 = new_v23, new_v21 = new_v22, new_v20 = new_v21;
/*      */ 
/*      */     
/*      */     float new_v19 = new_v20, new_v18 = new_v19, new_v17 = new_v18, new_v16 = new_v17, new_v15 = new_v16, new_v14 = new_v15, new_v13 = new_v14, new_v12 = new_v13, new_v11 = new_v12, new_v10 = new_v11;
/*      */     
/*      */     float new_v9 = new_v10, new_v8 = new_v9, new_v7 = new_v8, new_v6 = new_v7, new_v5 = new_v6, new_v4 = new_v5, new_v3 = new_v4, new_v2 = new_v3, new_v1 = new_v2, new_v0 = new_v1;
/*      */     
/*  207 */     float[] s = this.samples;
/*      */     
/*  209 */     float s0 = s[0];
/*  210 */     float s1 = s[1];
/*  211 */     float s2 = s[2];
/*  212 */     float s3 = s[3];
/*  213 */     float s4 = s[4];
/*  214 */     float s5 = s[5];
/*  215 */     float s6 = s[6];
/*  216 */     float s7 = s[7];
/*  217 */     float s8 = s[8];
/*  218 */     float s9 = s[9];
/*  219 */     float s10 = s[10];
/*  220 */     float s11 = s[11];
/*  221 */     float s12 = s[12];
/*  222 */     float s13 = s[13];
/*  223 */     float s14 = s[14];
/*  224 */     float s15 = s[15];
/*  225 */     float s16 = s[16];
/*  226 */     float s17 = s[17];
/*  227 */     float s18 = s[18];
/*  228 */     float s19 = s[19];
/*  229 */     float s20 = s[20];
/*  230 */     float s21 = s[21];
/*  231 */     float s22 = s[22];
/*  232 */     float s23 = s[23];
/*  233 */     float s24 = s[24];
/*  234 */     float s25 = s[25];
/*  235 */     float s26 = s[26];
/*  236 */     float s27 = s[27];
/*  237 */     float s28 = s[28];
/*  238 */     float s29 = s[29];
/*  239 */     float s30 = s[30];
/*  240 */     float s31 = s[31];
/*      */     
/*  242 */     float p0 = s0 + s31;
/*  243 */     float p1 = s1 + s30;
/*  244 */     float p2 = s2 + s29;
/*  245 */     float p3 = s3 + s28;
/*  246 */     float p4 = s4 + s27;
/*  247 */     float p5 = s5 + s26;
/*  248 */     float p6 = s6 + s25;
/*  249 */     float p7 = s7 + s24;
/*  250 */     float p8 = s8 + s23;
/*  251 */     float p9 = s9 + s22;
/*  252 */     float p10 = s10 + s21;
/*  253 */     float p11 = s11 + s20;
/*  254 */     float p12 = s12 + s19;
/*  255 */     float p13 = s13 + s18;
/*  256 */     float p14 = s14 + s17;
/*  257 */     float p15 = s15 + s16;
/*      */     
/*  259 */     float pp0 = p0 + p15;
/*  260 */     float pp1 = p1 + p14;
/*  261 */     float pp2 = p2 + p13;
/*  262 */     float pp3 = p3 + p12;
/*  263 */     float pp4 = p4 + p11;
/*  264 */     float pp5 = p5 + p10;
/*  265 */     float pp6 = p6 + p9;
/*  266 */     float pp7 = p7 + p8;
/*  267 */     float pp8 = (p0 - p15) * cos1_32;
/*  268 */     float pp9 = (p1 - p14) * cos3_32;
/*  269 */     float pp10 = (p2 - p13) * cos5_32;
/*  270 */     float pp11 = (p3 - p12) * cos7_32;
/*  271 */     float pp12 = (p4 - p11) * cos9_32;
/*  272 */     float pp13 = (p5 - p10) * cos11_32;
/*  273 */     float pp14 = (p6 - p9) * cos13_32;
/*  274 */     float pp15 = (p7 - p8) * cos15_32;
/*      */     
/*  276 */     p0 = pp0 + pp7;
/*  277 */     p1 = pp1 + pp6;
/*  278 */     p2 = pp2 + pp5;
/*  279 */     p3 = pp3 + pp4;
/*  280 */     p4 = (pp0 - pp7) * cos1_16;
/*  281 */     p5 = (pp1 - pp6) * cos3_16;
/*  282 */     p6 = (pp2 - pp5) * cos5_16;
/*  283 */     p7 = (pp3 - pp4) * cos7_16;
/*  284 */     p8 = pp8 + pp15;
/*  285 */     p9 = pp9 + pp14;
/*  286 */     p10 = pp10 + pp13;
/*  287 */     p11 = pp11 + pp12;
/*  288 */     p12 = (pp8 - pp15) * cos1_16;
/*  289 */     p13 = (pp9 - pp14) * cos3_16;
/*  290 */     p14 = (pp10 - pp13) * cos5_16;
/*  291 */     p15 = (pp11 - pp12) * cos7_16;
/*      */ 
/*      */     
/*  294 */     pp0 = p0 + p3;
/*  295 */     pp1 = p1 + p2;
/*  296 */     pp2 = (p0 - p3) * cos1_8;
/*  297 */     pp3 = (p1 - p2) * cos3_8;
/*  298 */     pp4 = p4 + p7;
/*  299 */     pp5 = p5 + p6;
/*  300 */     pp6 = (p4 - p7) * cos1_8;
/*  301 */     pp7 = (p5 - p6) * cos3_8;
/*  302 */     pp8 = p8 + p11;
/*  303 */     pp9 = p9 + p10;
/*  304 */     pp10 = (p8 - p11) * cos1_8;
/*  305 */     pp11 = (p9 - p10) * cos3_8;
/*  306 */     pp12 = p12 + p15;
/*  307 */     pp13 = p13 + p14;
/*  308 */     pp14 = (p12 - p15) * cos1_8;
/*  309 */     pp15 = (p13 - p14) * cos3_8;
/*      */     
/*  311 */     p0 = pp0 + pp1;
/*  312 */     p1 = (pp0 - pp1) * cos1_4;
/*  313 */     p2 = pp2 + pp3;
/*  314 */     p3 = (pp2 - pp3) * cos1_4;
/*  315 */     p4 = pp4 + pp5;
/*  316 */     p5 = (pp4 - pp5) * cos1_4;
/*  317 */     p6 = pp6 + pp7;
/*  318 */     p7 = (pp6 - pp7) * cos1_4;
/*  319 */     p8 = pp8 + pp9;
/*  320 */     p9 = (pp8 - pp9) * cos1_4;
/*  321 */     p10 = pp10 + pp11;
/*  322 */     p11 = (pp10 - pp11) * cos1_4;
/*  323 */     p12 = pp12 + pp13;
/*  324 */     p13 = (pp12 - pp13) * cos1_4;
/*  325 */     p14 = pp14 + pp15;
/*  326 */     p15 = (pp14 - pp15) * cos1_4;
/*      */ 
/*      */ 
/*      */     
/*  330 */     new_v19 = -(new_v4 = (new_v12 = p7) + p5) - p6;
/*  331 */     new_v27 = -p6 - p7 - p4;
/*  332 */     new_v6 = (new_v10 = (new_v14 = p15) + p11) + p13;
/*  333 */     new_v17 = -(new_v2 = p15 + p13 + p9) - p14; float tmp1;
/*  334 */     new_v21 = (tmp1 = -p14 - p15 - p10 - p11) - p13;
/*  335 */     new_v29 = -p14 - p15 - p12 - p8;
/*  336 */     new_v25 = tmp1 - p12;
/*  337 */     new_v31 = -p0;
/*  338 */     new_v0 = p1;
/*  339 */     new_v23 = -(new_v8 = p3) - p2;
/*      */     
/*  341 */     p0 = (s0 - s31) * cos1_64;
/*  342 */     p1 = (s1 - s30) * cos3_64;
/*  343 */     p2 = (s2 - s29) * cos5_64;
/*  344 */     p3 = (s3 - s28) * cos7_64;
/*  345 */     p4 = (s4 - s27) * cos9_64;
/*  346 */     p5 = (s5 - s26) * cos11_64;
/*  347 */     p6 = (s6 - s25) * cos13_64;
/*  348 */     p7 = (s7 - s24) * cos15_64;
/*  349 */     p8 = (s8 - s23) * cos17_64;
/*  350 */     p9 = (s9 - s22) * cos19_64;
/*  351 */     p10 = (s10 - s21) * cos21_64;
/*  352 */     p11 = (s11 - s20) * cos23_64;
/*  353 */     p12 = (s12 - s19) * cos25_64;
/*  354 */     p13 = (s13 - s18) * cos27_64;
/*  355 */     p14 = (s14 - s17) * cos29_64;
/*  356 */     p15 = (s15 - s16) * cos31_64;
/*      */ 
/*      */     
/*  359 */     pp0 = p0 + p15;
/*  360 */     pp1 = p1 + p14;
/*  361 */     pp2 = p2 + p13;
/*  362 */     pp3 = p3 + p12;
/*  363 */     pp4 = p4 + p11;
/*  364 */     pp5 = p5 + p10;
/*  365 */     pp6 = p6 + p9;
/*  366 */     pp7 = p7 + p8;
/*  367 */     pp8 = (p0 - p15) * cos1_32;
/*  368 */     pp9 = (p1 - p14) * cos3_32;
/*  369 */     pp10 = (p2 - p13) * cos5_32;
/*  370 */     pp11 = (p3 - p12) * cos7_32;
/*  371 */     pp12 = (p4 - p11) * cos9_32;
/*  372 */     pp13 = (p5 - p10) * cos11_32;
/*  373 */     pp14 = (p6 - p9) * cos13_32;
/*  374 */     pp15 = (p7 - p8) * cos15_32;
/*      */ 
/*      */     
/*  377 */     p0 = pp0 + pp7;
/*  378 */     p1 = pp1 + pp6;
/*  379 */     p2 = pp2 + pp5;
/*  380 */     p3 = pp3 + pp4;
/*  381 */     p4 = (pp0 - pp7) * cos1_16;
/*  382 */     p5 = (pp1 - pp6) * cos3_16;
/*  383 */     p6 = (pp2 - pp5) * cos5_16;
/*  384 */     p7 = (pp3 - pp4) * cos7_16;
/*  385 */     p8 = pp8 + pp15;
/*  386 */     p9 = pp9 + pp14;
/*  387 */     p10 = pp10 + pp13;
/*  388 */     p11 = pp11 + pp12;
/*  389 */     p12 = (pp8 - pp15) * cos1_16;
/*  390 */     p13 = (pp9 - pp14) * cos3_16;
/*  391 */     p14 = (pp10 - pp13) * cos5_16;
/*  392 */     p15 = (pp11 - pp12) * cos7_16;
/*      */ 
/*      */     
/*  395 */     pp0 = p0 + p3;
/*  396 */     pp1 = p1 + p2;
/*  397 */     pp2 = (p0 - p3) * cos1_8;
/*  398 */     pp3 = (p1 - p2) * cos3_8;
/*  399 */     pp4 = p4 + p7;
/*  400 */     pp5 = p5 + p6;
/*  401 */     pp6 = (p4 - p7) * cos1_8;
/*  402 */     pp7 = (p5 - p6) * cos3_8;
/*  403 */     pp8 = p8 + p11;
/*  404 */     pp9 = p9 + p10;
/*  405 */     pp10 = (p8 - p11) * cos1_8;
/*  406 */     pp11 = (p9 - p10) * cos3_8;
/*  407 */     pp12 = p12 + p15;
/*  408 */     pp13 = p13 + p14;
/*  409 */     pp14 = (p12 - p15) * cos1_8;
/*  410 */     pp15 = (p13 - p14) * cos3_8;
/*      */ 
/*      */     
/*  413 */     p0 = pp0 + pp1;
/*  414 */     p1 = (pp0 - pp1) * cos1_4;
/*  415 */     p2 = pp2 + pp3;
/*  416 */     p3 = (pp2 - pp3) * cos1_4;
/*  417 */     p4 = pp4 + pp5;
/*  418 */     p5 = (pp4 - pp5) * cos1_4;
/*  419 */     p6 = pp6 + pp7;
/*  420 */     p7 = (pp6 - pp7) * cos1_4;
/*  421 */     p8 = pp8 + pp9;
/*  422 */     p9 = (pp8 - pp9) * cos1_4;
/*  423 */     p10 = pp10 + pp11;
/*  424 */     p11 = (pp10 - pp11) * cos1_4;
/*  425 */     p12 = pp12 + pp13;
/*  426 */     p13 = (pp12 - pp13) * cos1_4;
/*  427 */     p14 = pp14 + pp15;
/*  428 */     p15 = (pp14 - pp15) * cos1_4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  434 */     new_v5 = (new_v11 = (new_v13 = (new_v15 = p15) + p7) + p11) + 
/*  435 */       p5 + p13;
/*  436 */     new_v7 = (new_v9 = p15 + p11 + p3) + p13;
/*  437 */     new_v16 = -(new_v1 = (tmp1 = p13 + p15 + p9) + p1) - p14;
/*  438 */     new_v18 = -(new_v3 = tmp1 + p5 + p7) - p6 - p14;
/*      */     
/*  440 */     new_v22 = (tmp1 = -p10 - p11 - p14 - p15) - 
/*  441 */       p13 - p2 - p3;
/*  442 */     new_v20 = tmp1 - p13 - p5 - p6 - p7;
/*  443 */     new_v24 = tmp1 - p12 - p2 - p3; float tmp2;
/*  444 */     new_v26 = tmp1 - p12 - (tmp2 = p4 + p6 + p7);
/*  445 */     new_v30 = (tmp1 = -p8 - p12 - p14 - p15) - p0;
/*  446 */     new_v28 = tmp1 - tmp2;
/*      */ 
/*      */ 
/*      */     
/*  450 */     float[] dest = this.actual_v;
/*      */     
/*  452 */     int pos = this.actual_write_pos;
/*      */     
/*  454 */     dest[0 + pos] = new_v0;
/*  455 */     dest[16 + pos] = new_v1;
/*  456 */     dest[32 + pos] = new_v2;
/*  457 */     dest[48 + pos] = new_v3;
/*  458 */     dest[64 + pos] = new_v4;
/*  459 */     dest[80 + pos] = new_v5;
/*  460 */     dest[96 + pos] = new_v6;
/*  461 */     dest[112 + pos] = new_v7;
/*  462 */     dest[128 + pos] = new_v8;
/*  463 */     dest[144 + pos] = new_v9;
/*  464 */     dest[160 + pos] = new_v10;
/*  465 */     dest[176 + pos] = new_v11;
/*  466 */     dest[192 + pos] = new_v12;
/*  467 */     dest[208 + pos] = new_v13;
/*  468 */     dest[224 + pos] = new_v14;
/*  469 */     dest[240 + pos] = new_v15;
/*      */ 
/*      */     
/*  472 */     dest[256 + pos] = 0.0F;
/*      */ 
/*      */     
/*  475 */     dest[272 + pos] = -new_v15;
/*  476 */     dest[288 + pos] = -new_v14;
/*  477 */     dest[304 + pos] = -new_v13;
/*  478 */     dest[320 + pos] = -new_v12;
/*  479 */     dest[336 + pos] = -new_v11;
/*  480 */     dest[352 + pos] = -new_v10;
/*  481 */     dest[368 + pos] = -new_v9;
/*  482 */     dest[384 + pos] = -new_v8;
/*  483 */     dest[400 + pos] = -new_v7;
/*  484 */     dest[416 + pos] = -new_v6;
/*  485 */     dest[432 + pos] = -new_v5;
/*  486 */     dest[448 + pos] = -new_v4;
/*  487 */     dest[464 + pos] = -new_v3;
/*  488 */     dest[480 + pos] = -new_v2;
/*  489 */     dest[496 + pos] = -new_v1;
/*      */ 
/*      */     
/*  492 */     dest = (this.actual_v == this.v1) ? this.v2 : this.v1;
/*      */     
/*  494 */     dest[0 + pos] = -new_v0;
/*      */     
/*  496 */     dest[16 + pos] = new_v16;
/*  497 */     dest[32 + pos] = new_v17;
/*  498 */     dest[48 + pos] = new_v18;
/*  499 */     dest[64 + pos] = new_v19;
/*  500 */     dest[80 + pos] = new_v20;
/*  501 */     dest[96 + pos] = new_v21;
/*  502 */     dest[112 + pos] = new_v22;
/*  503 */     dest[128 + pos] = new_v23;
/*  504 */     dest[144 + pos] = new_v24;
/*  505 */     dest[160 + pos] = new_v25;
/*  506 */     dest[176 + pos] = new_v26;
/*  507 */     dest[192 + pos] = new_v27;
/*  508 */     dest[208 + pos] = new_v28;
/*  509 */     dest[224 + pos] = new_v29;
/*  510 */     dest[240 + pos] = new_v30;
/*  511 */     dest[256 + pos] = new_v31;
/*      */ 
/*      */     
/*  514 */     dest[272 + pos] = new_v30;
/*  515 */     dest[288 + pos] = new_v29;
/*  516 */     dest[304 + pos] = new_v28;
/*  517 */     dest[320 + pos] = new_v27;
/*  518 */     dest[336 + pos] = new_v26;
/*  519 */     dest[352 + pos] = new_v25;
/*  520 */     dest[368 + pos] = new_v24;
/*  521 */     dest[384 + pos] = new_v23;
/*  522 */     dest[400 + pos] = new_v22;
/*  523 */     dest[416 + pos] = new_v21;
/*  524 */     dest[432 + pos] = new_v20;
/*  525 */     dest[448 + pos] = new_v19;
/*  526 */     dest[464 + pos] = new_v18;
/*  527 */     dest[480 + pos] = new_v17;
/*  528 */     dest[496 + pos] = new_v16;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void compute_new_v_old() {
/*  584 */     float[] new_v = new float[32];
/*  585 */     float[] p = new float[16];
/*  586 */     float[] pp = new float[16];
/*      */ 
/*      */     
/*  589 */     for (int i = 31; i >= 0; i--)
/*      */     {
/*  591 */       new_v[i] = 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  598 */     float[] x1 = this.samples;
/*      */     
/*  600 */     p[0] = x1[0] + x1[31];
/*  601 */     p[1] = x1[1] + x1[30];
/*  602 */     p[2] = x1[2] + x1[29];
/*  603 */     p[3] = x1[3] + x1[28];
/*  604 */     p[4] = x1[4] + x1[27];
/*  605 */     p[5] = x1[5] + x1[26];
/*  606 */     p[6] = x1[6] + x1[25];
/*  607 */     p[7] = x1[7] + x1[24];
/*  608 */     p[8] = x1[8] + x1[23];
/*  609 */     p[9] = x1[9] + x1[22];
/*  610 */     p[10] = x1[10] + x1[21];
/*  611 */     p[11] = x1[11] + x1[20];
/*  612 */     p[12] = x1[12] + x1[19];
/*  613 */     p[13] = x1[13] + x1[18];
/*  614 */     p[14] = x1[14] + x1[17];
/*  615 */     p[15] = x1[15] + x1[16];
/*      */     
/*  617 */     pp[0] = p[0] + p[15];
/*  618 */     pp[1] = p[1] + p[14];
/*  619 */     pp[2] = p[2] + p[13];
/*  620 */     pp[3] = p[3] + p[12];
/*  621 */     pp[4] = p[4] + p[11];
/*  622 */     pp[5] = p[5] + p[10];
/*  623 */     pp[6] = p[6] + p[9];
/*  624 */     pp[7] = p[7] + p[8];
/*  625 */     pp[8] = (p[0] - p[15]) * cos1_32;
/*  626 */     pp[9] = (p[1] - p[14]) * cos3_32;
/*  627 */     pp[10] = (p[2] - p[13]) * cos5_32;
/*  628 */     pp[11] = (p[3] - p[12]) * cos7_32;
/*  629 */     pp[12] = (p[4] - p[11]) * cos9_32;
/*  630 */     pp[13] = (p[5] - p[10]) * cos11_32;
/*  631 */     pp[14] = (p[6] - p[9]) * cos13_32;
/*  632 */     pp[15] = (p[7] - p[8]) * cos15_32;
/*      */     
/*  634 */     p[0] = pp[0] + pp[7];
/*  635 */     p[1] = pp[1] + pp[6];
/*  636 */     p[2] = pp[2] + pp[5];
/*  637 */     p[3] = pp[3] + pp[4];
/*  638 */     p[4] = (pp[0] - pp[7]) * cos1_16;
/*  639 */     p[5] = (pp[1] - pp[6]) * cos3_16;
/*  640 */     p[6] = (pp[2] - pp[5]) * cos5_16;
/*  641 */     p[7] = (pp[3] - pp[4]) * cos7_16;
/*  642 */     p[8] = pp[8] + pp[15];
/*  643 */     p[9] = pp[9] + pp[14];
/*  644 */     p[10] = pp[10] + pp[13];
/*  645 */     p[11] = pp[11] + pp[12];
/*  646 */     p[12] = (pp[8] - pp[15]) * cos1_16;
/*  647 */     p[13] = (pp[9] - pp[14]) * cos3_16;
/*  648 */     p[14] = (pp[10] - pp[13]) * cos5_16;
/*  649 */     p[15] = (pp[11] - pp[12]) * cos7_16;
/*      */ 
/*      */     
/*  652 */     pp[0] = p[0] + p[3];
/*  653 */     pp[1] = p[1] + p[2];
/*  654 */     pp[2] = (p[0] - p[3]) * cos1_8;
/*  655 */     pp[3] = (p[1] - p[2]) * cos3_8;
/*  656 */     pp[4] = p[4] + p[7];
/*  657 */     pp[5] = p[5] + p[6];
/*  658 */     pp[6] = (p[4] - p[7]) * cos1_8;
/*  659 */     pp[7] = (p[5] - p[6]) * cos3_8;
/*  660 */     pp[8] = p[8] + p[11];
/*  661 */     pp[9] = p[9] + p[10];
/*  662 */     pp[10] = (p[8] - p[11]) * cos1_8;
/*  663 */     pp[11] = (p[9] - p[10]) * cos3_8;
/*  664 */     pp[12] = p[12] + p[15];
/*  665 */     pp[13] = p[13] + p[14];
/*  666 */     pp[14] = (p[12] - p[15]) * cos1_8;
/*  667 */     pp[15] = (p[13] - p[14]) * cos3_8;
/*      */     
/*  669 */     p[0] = pp[0] + pp[1];
/*  670 */     p[1] = (pp[0] - pp[1]) * cos1_4;
/*  671 */     p[2] = pp[2] + pp[3];
/*  672 */     p[3] = (pp[2] - pp[3]) * cos1_4;
/*  673 */     p[4] = pp[4] + pp[5];
/*  674 */     p[5] = (pp[4] - pp[5]) * cos1_4;
/*  675 */     p[6] = pp[6] + pp[7];
/*  676 */     p[7] = (pp[6] - pp[7]) * cos1_4;
/*  677 */     p[8] = pp[8] + pp[9];
/*  678 */     p[9] = (pp[8] - pp[9]) * cos1_4;
/*  679 */     p[10] = pp[10] + pp[11];
/*  680 */     p[11] = (pp[10] - pp[11]) * cos1_4;
/*  681 */     p[12] = pp[12] + pp[13];
/*  682 */     p[13] = (pp[12] - pp[13]) * cos1_4;
/*  683 */     p[14] = pp[14] + pp[15];
/*  684 */     p[15] = (pp[14] - pp[15]) * cos1_4;
/*      */ 
/*      */ 
/*      */     
/*  688 */     new_v[12] = p[7]; new_v[4] = p[7] + p[5]; new_v[19] = -(p[7] + p[5]) - p[6];
/*  689 */     new_v[27] = -p[6] - p[7] - p[4];
/*  690 */     new_v[14] = p[15]; new_v[10] = p[15] + p[11]; new_v[6] = p[15] + p[11] + p[13];
/*  691 */     new_v[2] = p[15] + p[13] + p[9]; new_v[17] = -(p[15] + p[13] + p[9]) - p[14]; float tmp1;
/*  692 */     new_v[21] = (tmp1 = -p[14] - p[15] - p[10] - p[11]) - p[13];
/*  693 */     new_v[29] = -p[14] - p[15] - p[12] - p[8];
/*  694 */     new_v[25] = tmp1 - p[12];
/*  695 */     new_v[31] = -p[0];
/*  696 */     new_v[0] = p[1];
/*  697 */     new_v[8] = p[3]; new_v[23] = -p[3] - p[2];
/*      */     
/*  699 */     p[0] = (x1[0] - x1[31]) * cos1_64;
/*  700 */     p[1] = (x1[1] - x1[30]) * cos3_64;
/*  701 */     p[2] = (x1[2] - x1[29]) * cos5_64;
/*  702 */     p[3] = (x1[3] - x1[28]) * cos7_64;
/*  703 */     p[4] = (x1[4] - x1[27]) * cos9_64;
/*  704 */     p[5] = (x1[5] - x1[26]) * cos11_64;
/*  705 */     p[6] = (x1[6] - x1[25]) * cos13_64;
/*  706 */     p[7] = (x1[7] - x1[24]) * cos15_64;
/*  707 */     p[8] = (x1[8] - x1[23]) * cos17_64;
/*  708 */     p[9] = (x1[9] - x1[22]) * cos19_64;
/*  709 */     p[10] = (x1[10] - x1[21]) * cos21_64;
/*  710 */     p[11] = (x1[11] - x1[20]) * cos23_64;
/*  711 */     p[12] = (x1[12] - x1[19]) * cos25_64;
/*  712 */     p[13] = (x1[13] - x1[18]) * cos27_64;
/*  713 */     p[14] = (x1[14] - x1[17]) * cos29_64;
/*  714 */     p[15] = (x1[15] - x1[16]) * cos31_64;
/*      */ 
/*      */     
/*  717 */     pp[0] = p[0] + p[15];
/*  718 */     pp[1] = p[1] + p[14];
/*  719 */     pp[2] = p[2] + p[13];
/*  720 */     pp[3] = p[3] + p[12];
/*  721 */     pp[4] = p[4] + p[11];
/*  722 */     pp[5] = p[5] + p[10];
/*  723 */     pp[6] = p[6] + p[9];
/*  724 */     pp[7] = p[7] + p[8];
/*  725 */     pp[8] = (p[0] - p[15]) * cos1_32;
/*  726 */     pp[9] = (p[1] - p[14]) * cos3_32;
/*  727 */     pp[10] = (p[2] - p[13]) * cos5_32;
/*  728 */     pp[11] = (p[3] - p[12]) * cos7_32;
/*  729 */     pp[12] = (p[4] - p[11]) * cos9_32;
/*  730 */     pp[13] = (p[5] - p[10]) * cos11_32;
/*  731 */     pp[14] = (p[6] - p[9]) * cos13_32;
/*  732 */     pp[15] = (p[7] - p[8]) * cos15_32;
/*      */ 
/*      */     
/*  735 */     p[0] = pp[0] + pp[7];
/*  736 */     p[1] = pp[1] + pp[6];
/*  737 */     p[2] = pp[2] + pp[5];
/*  738 */     p[3] = pp[3] + pp[4];
/*  739 */     p[4] = (pp[0] - pp[7]) * cos1_16;
/*  740 */     p[5] = (pp[1] - pp[6]) * cos3_16;
/*  741 */     p[6] = (pp[2] - pp[5]) * cos5_16;
/*  742 */     p[7] = (pp[3] - pp[4]) * cos7_16;
/*  743 */     p[8] = pp[8] + pp[15];
/*  744 */     p[9] = pp[9] + pp[14];
/*  745 */     p[10] = pp[10] + pp[13];
/*  746 */     p[11] = pp[11] + pp[12];
/*  747 */     p[12] = (pp[8] - pp[15]) * cos1_16;
/*  748 */     p[13] = (pp[9] - pp[14]) * cos3_16;
/*  749 */     p[14] = (pp[10] - pp[13]) * cos5_16;
/*  750 */     p[15] = (pp[11] - pp[12]) * cos7_16;
/*      */ 
/*      */     
/*  753 */     pp[0] = p[0] + p[3];
/*  754 */     pp[1] = p[1] + p[2];
/*  755 */     pp[2] = (p[0] - p[3]) * cos1_8;
/*  756 */     pp[3] = (p[1] - p[2]) * cos3_8;
/*  757 */     pp[4] = p[4] + p[7];
/*  758 */     pp[5] = p[5] + p[6];
/*  759 */     pp[6] = (p[4] - p[7]) * cos1_8;
/*  760 */     pp[7] = (p[5] - p[6]) * cos3_8;
/*  761 */     pp[8] = p[8] + p[11];
/*  762 */     pp[9] = p[9] + p[10];
/*  763 */     pp[10] = (p[8] - p[11]) * cos1_8;
/*  764 */     pp[11] = (p[9] - p[10]) * cos3_8;
/*  765 */     pp[12] = p[12] + p[15];
/*  766 */     pp[13] = p[13] + p[14];
/*  767 */     pp[14] = (p[12] - p[15]) * cos1_8;
/*  768 */     pp[15] = (p[13] - p[14]) * cos3_8;
/*      */ 
/*      */     
/*  771 */     p[0] = pp[0] + pp[1];
/*  772 */     p[1] = (pp[0] - pp[1]) * cos1_4;
/*  773 */     p[2] = pp[2] + pp[3];
/*  774 */     p[3] = (pp[2] - pp[3]) * cos1_4;
/*  775 */     p[4] = pp[4] + pp[5];
/*  776 */     p[5] = (pp[4] - pp[5]) * cos1_4;
/*  777 */     p[6] = pp[6] + pp[7];
/*  778 */     p[7] = (pp[6] - pp[7]) * cos1_4;
/*  779 */     p[8] = pp[8] + pp[9];
/*  780 */     p[9] = (pp[8] - pp[9]) * cos1_4;
/*  781 */     p[10] = pp[10] + pp[11];
/*  782 */     p[11] = (pp[10] - pp[11]) * cos1_4;
/*  783 */     p[12] = pp[12] + pp[13];
/*  784 */     p[13] = (pp[12] - pp[13]) * cos1_4;
/*  785 */     p[14] = pp[14] + pp[15];
/*  786 */     p[15] = (pp[14] - pp[15]) * cos1_4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  792 */     new_v[15] = p[15]; new_v[13] = p[15] + p[7]; new_v[11] = p[15] + p[7] + p[11]; new_v[5] = p[15] + p[7] + p[11] + 
/*  793 */       p[5] + p[13];
/*  794 */     new_v[9] = p[15] + p[11] + p[3]; new_v[7] = p[15] + p[11] + p[3] + p[13];
/*  795 */     new_v[1] = (tmp1 = p[13] + p[15] + p[9]) + p[1]; new_v[16] = -((tmp1 = p[13] + p[15] + p[9]) + p[1]) - p[14];
/*  796 */     new_v[3] = tmp1 + p[5] + p[7]; new_v[18] = -(tmp1 + p[5] + p[7]) - p[6] - p[14];
/*      */     
/*  798 */     new_v[22] = (tmp1 = -p[10] - p[11] - p[14] - p[15]) - 
/*  799 */       p[13] - p[2] - p[3];
/*  800 */     new_v[20] = tmp1 - p[13] - p[5] - p[6] - p[7];
/*  801 */     new_v[24] = tmp1 - p[12] - p[2] - p[3]; float tmp2;
/*  802 */     new_v[26] = tmp1 - p[12] - (tmp2 = p[4] + p[6] + p[7]);
/*  803 */     new_v[30] = (tmp1 = -p[8] - p[12] - p[14] - p[15]) - p[0];
/*  804 */     new_v[28] = tmp1 - tmp2;
/*      */ 
/*      */     
/*  807 */     x1 = new_v;
/*      */     
/*  809 */     float[] dest = this.actual_v;
/*      */     
/*  811 */     dest[0 + this.actual_write_pos] = x1[0];
/*  812 */     dest[16 + this.actual_write_pos] = x1[1];
/*  813 */     dest[32 + this.actual_write_pos] = x1[2];
/*  814 */     dest[48 + this.actual_write_pos] = x1[3];
/*  815 */     dest[64 + this.actual_write_pos] = x1[4];
/*  816 */     dest[80 + this.actual_write_pos] = x1[5];
/*  817 */     dest[96 + this.actual_write_pos] = x1[6];
/*  818 */     dest[112 + this.actual_write_pos] = x1[7];
/*  819 */     dest[128 + this.actual_write_pos] = x1[8];
/*  820 */     dest[144 + this.actual_write_pos] = x1[9];
/*  821 */     dest[160 + this.actual_write_pos] = x1[10];
/*  822 */     dest[176 + this.actual_write_pos] = x1[11];
/*  823 */     dest[192 + this.actual_write_pos] = x1[12];
/*  824 */     dest[208 + this.actual_write_pos] = x1[13];
/*  825 */     dest[224 + this.actual_write_pos] = x1[14];
/*  826 */     dest[240 + this.actual_write_pos] = x1[15];
/*      */ 
/*      */     
/*  829 */     dest[256 + this.actual_write_pos] = 0.0F;
/*      */ 
/*      */     
/*  832 */     dest[272 + this.actual_write_pos] = -x1[15];
/*  833 */     dest[288 + this.actual_write_pos] = -x1[14];
/*  834 */     dest[304 + this.actual_write_pos] = -x1[13];
/*  835 */     dest[320 + this.actual_write_pos] = -x1[12];
/*  836 */     dest[336 + this.actual_write_pos] = -x1[11];
/*  837 */     dest[352 + this.actual_write_pos] = -x1[10];
/*  838 */     dest[368 + this.actual_write_pos] = -x1[9];
/*  839 */     dest[384 + this.actual_write_pos] = -x1[8];
/*  840 */     dest[400 + this.actual_write_pos] = -x1[7];
/*  841 */     dest[416 + this.actual_write_pos] = -x1[6];
/*  842 */     dest[432 + this.actual_write_pos] = -x1[5];
/*  843 */     dest[448 + this.actual_write_pos] = -x1[4];
/*  844 */     dest[464 + this.actual_write_pos] = -x1[3];
/*  845 */     dest[480 + this.actual_write_pos] = -x1[2];
/*  846 */     dest[496 + this.actual_write_pos] = -x1[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  856 */   private float[] _tmpOut = new float[32];
/*      */   
/*      */   private static final double MY_PI = 3.141592653589793D;
/*      */   
/*      */   private void compute_pcm_samples0(Obuffer buffer) {
/*  861 */     float[] vp = this.actual_v;
/*      */     
/*  863 */     float[] tmpOut = this._tmpOut;
/*  864 */     int dvp = 0;
/*      */ 
/*      */     
/*  867 */     for (int i = 0; i < 32; i++) {
/*      */ 
/*      */       
/*  870 */       float[] dp = d16[i];
/*  871 */       float pcm_sample = (vp[0 + dvp] * dp[0] + 
/*  872 */         vp[15 + dvp] * dp[1] + 
/*  873 */         vp[14 + dvp] * dp[2] + 
/*  874 */         vp[13 + dvp] * dp[3] + 
/*  875 */         vp[12 + dvp] * dp[4] + 
/*  876 */         vp[11 + dvp] * dp[5] + 
/*  877 */         vp[10 + dvp] * dp[6] + 
/*  878 */         vp[9 + dvp] * dp[7] + 
/*  879 */         vp[8 + dvp] * dp[8] + 
/*  880 */         vp[7 + dvp] * dp[9] + 
/*  881 */         vp[6 + dvp] * dp[10] + 
/*  882 */         vp[5 + dvp] * dp[11] + 
/*  883 */         vp[4 + dvp] * dp[12] + 
/*  884 */         vp[3 + dvp] * dp[13] + 
/*  885 */         vp[2 + dvp] * dp[14] + 
/*  886 */         vp[1 + dvp] * dp[15]) * 
/*  887 */         this.scalefactor;
/*      */       
/*  889 */       tmpOut[i] = pcm_sample;
/*      */       
/*  891 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void compute_pcm_samples1(Obuffer buffer) {
/*  897 */     float[] vp = this.actual_v;
/*      */     
/*  899 */     float[] tmpOut = this._tmpOut;
/*  900 */     int dvp = 0;
/*      */ 
/*      */     
/*  903 */     for (int i = 0; i < 32; i++) {
/*      */       
/*  905 */       float[] dp = d16[i];
/*      */ 
/*      */       
/*  908 */       float pcm_sample = (vp[1 + dvp] * dp[0] + 
/*  909 */         vp[0 + dvp] * dp[1] + 
/*  910 */         vp[15 + dvp] * dp[2] + 
/*  911 */         vp[14 + dvp] * dp[3] + 
/*  912 */         vp[13 + dvp] * dp[4] + 
/*  913 */         vp[12 + dvp] * dp[5] + 
/*  914 */         vp[11 + dvp] * dp[6] + 
/*  915 */         vp[10 + dvp] * dp[7] + 
/*  916 */         vp[9 + dvp] * dp[8] + 
/*  917 */         vp[8 + dvp] * dp[9] + 
/*  918 */         vp[7 + dvp] * dp[10] + 
/*  919 */         vp[6 + dvp] * dp[11] + 
/*  920 */         vp[5 + dvp] * dp[12] + 
/*  921 */         vp[4 + dvp] * dp[13] + 
/*  922 */         vp[3 + dvp] * dp[14] + 
/*  923 */         vp[2 + dvp] * dp[15]) * 
/*  924 */         this.scalefactor;
/*      */       
/*  926 */       tmpOut[i] = pcm_sample;
/*      */       
/*  928 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void compute_pcm_samples2(Obuffer buffer) {
/*  933 */     float[] vp = this.actual_v;
/*      */ 
/*      */     
/*  936 */     float[] tmpOut = this._tmpOut;
/*  937 */     int dvp = 0;
/*      */ 
/*      */     
/*  940 */     for (int i = 0; i < 32; i++) {
/*      */       
/*  942 */       float[] dp = d16[i];
/*      */ 
/*      */       
/*  945 */       float pcm_sample = (vp[2 + dvp] * dp[0] + 
/*  946 */         vp[1 + dvp] * dp[1] + 
/*  947 */         vp[0 + dvp] * dp[2] + 
/*  948 */         vp[15 + dvp] * dp[3] + 
/*  949 */         vp[14 + dvp] * dp[4] + 
/*  950 */         vp[13 + dvp] * dp[5] + 
/*  951 */         vp[12 + dvp] * dp[6] + 
/*  952 */         vp[11 + dvp] * dp[7] + 
/*  953 */         vp[10 + dvp] * dp[8] + 
/*  954 */         vp[9 + dvp] * dp[9] + 
/*  955 */         vp[8 + dvp] * dp[10] + 
/*  956 */         vp[7 + dvp] * dp[11] + 
/*  957 */         vp[6 + dvp] * dp[12] + 
/*  958 */         vp[5 + dvp] * dp[13] + 
/*  959 */         vp[4 + dvp] * dp[14] + 
/*  960 */         vp[3 + dvp] * dp[15]) * 
/*  961 */         this.scalefactor;
/*      */       
/*  963 */       tmpOut[i] = pcm_sample;
/*      */       
/*  965 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void compute_pcm_samples3(Obuffer buffer) {
/*  971 */     float[] vp = this.actual_v;
/*      */     
/*  973 */     int idx = 0;
/*      */     
/*  975 */     float[] tmpOut = this._tmpOut;
/*  976 */     int dvp = 0;
/*      */ 
/*      */     
/*  979 */     for (int i = 0; i < 32; i++) {
/*      */       
/*  981 */       float[] dp = d16[i];
/*      */ 
/*      */       
/*  984 */       float pcm_sample = (vp[3 + dvp] * dp[0] + 
/*  985 */         vp[2 + dvp] * dp[1] + 
/*  986 */         vp[1 + dvp] * dp[2] + 
/*  987 */         vp[0 + dvp] * dp[3] + 
/*  988 */         vp[15 + dvp] * dp[4] + 
/*  989 */         vp[14 + dvp] * dp[5] + 
/*  990 */         vp[13 + dvp] * dp[6] + 
/*  991 */         vp[12 + dvp] * dp[7] + 
/*  992 */         vp[11 + dvp] * dp[8] + 
/*  993 */         vp[10 + dvp] * dp[9] + 
/*  994 */         vp[9 + dvp] * dp[10] + 
/*  995 */         vp[8 + dvp] * dp[11] + 
/*  996 */         vp[7 + dvp] * dp[12] + 
/*  997 */         vp[6 + dvp] * dp[13] + 
/*  998 */         vp[5 + dvp] * dp[14] + 
/*  999 */         vp[4 + dvp] * dp[15]) * 
/* 1000 */         this.scalefactor;
/*      */       
/* 1002 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1004 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void compute_pcm_samples4(Obuffer buffer) {
/* 1010 */     float[] vp = this.actual_v;
/*      */ 
/*      */     
/* 1013 */     float[] tmpOut = this._tmpOut;
/* 1014 */     int dvp = 0;
/*      */ 
/*      */     
/* 1017 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1019 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1022 */       float pcm_sample = (vp[4 + dvp] * dp[0] + 
/* 1023 */         vp[3 + dvp] * dp[1] + 
/* 1024 */         vp[2 + dvp] * dp[2] + 
/* 1025 */         vp[1 + dvp] * dp[3] + 
/* 1026 */         vp[0 + dvp] * dp[4] + 
/* 1027 */         vp[15 + dvp] * dp[5] + 
/* 1028 */         vp[14 + dvp] * dp[6] + 
/* 1029 */         vp[13 + dvp] * dp[7] + 
/* 1030 */         vp[12 + dvp] * dp[8] + 
/* 1031 */         vp[11 + dvp] * dp[9] + 
/* 1032 */         vp[10 + dvp] * dp[10] + 
/* 1033 */         vp[9 + dvp] * dp[11] + 
/* 1034 */         vp[8 + dvp] * dp[12] + 
/* 1035 */         vp[7 + dvp] * dp[13] + 
/* 1036 */         vp[6 + dvp] * dp[14] + 
/* 1037 */         vp[5 + dvp] * dp[15]) * 
/* 1038 */         this.scalefactor;
/*      */       
/* 1040 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1042 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void compute_pcm_samples5(Obuffer buffer) {
/* 1048 */     float[] vp = this.actual_v;
/*      */ 
/*      */     
/* 1051 */     float[] tmpOut = this._tmpOut;
/* 1052 */     int dvp = 0;
/*      */ 
/*      */     
/* 1055 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1057 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1060 */       float pcm_sample = (vp[5 + dvp] * dp[0] + 
/* 1061 */         vp[4 + dvp] * dp[1] + 
/* 1062 */         vp[3 + dvp] * dp[2] + 
/* 1063 */         vp[2 + dvp] * dp[3] + 
/* 1064 */         vp[1 + dvp] * dp[4] + 
/* 1065 */         vp[0 + dvp] * dp[5] + 
/* 1066 */         vp[15 + dvp] * dp[6] + 
/* 1067 */         vp[14 + dvp] * dp[7] + 
/* 1068 */         vp[13 + dvp] * dp[8] + 
/* 1069 */         vp[12 + dvp] * dp[9] + 
/* 1070 */         vp[11 + dvp] * dp[10] + 
/* 1071 */         vp[10 + dvp] * dp[11] + 
/* 1072 */         vp[9 + dvp] * dp[12] + 
/* 1073 */         vp[8 + dvp] * dp[13] + 
/* 1074 */         vp[7 + dvp] * dp[14] + 
/* 1075 */         vp[6 + dvp] * dp[15]) * 
/* 1076 */         this.scalefactor;
/*      */       
/* 1078 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1080 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void compute_pcm_samples6(Obuffer buffer) {
/* 1086 */     float[] vp = this.actual_v;
/*      */     
/* 1088 */     float[] tmpOut = this._tmpOut;
/* 1089 */     int dvp = 0;
/*      */ 
/*      */     
/* 1092 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1094 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1097 */       float pcm_sample = (vp[6 + dvp] * dp[0] + 
/* 1098 */         vp[5 + dvp] * dp[1] + 
/* 1099 */         vp[4 + dvp] * dp[2] + 
/* 1100 */         vp[3 + dvp] * dp[3] + 
/* 1101 */         vp[2 + dvp] * dp[4] + 
/* 1102 */         vp[1 + dvp] * dp[5] + 
/* 1103 */         vp[0 + dvp] * dp[6] + 
/* 1104 */         vp[15 + dvp] * dp[7] + 
/* 1105 */         vp[14 + dvp] * dp[8] + 
/* 1106 */         vp[13 + dvp] * dp[9] + 
/* 1107 */         vp[12 + dvp] * dp[10] + 
/* 1108 */         vp[11 + dvp] * dp[11] + 
/* 1109 */         vp[10 + dvp] * dp[12] + 
/* 1110 */         vp[9 + dvp] * dp[13] + 
/* 1111 */         vp[8 + dvp] * dp[14] + 
/* 1112 */         vp[7 + dvp] * dp[15]) * 
/* 1113 */         this.scalefactor;
/*      */       
/* 1115 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1117 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void compute_pcm_samples7(Obuffer buffer) {
/* 1123 */     float[] vp = this.actual_v;
/*      */ 
/*      */     
/* 1126 */     float[] tmpOut = this._tmpOut;
/* 1127 */     int dvp = 0;
/*      */ 
/*      */     
/* 1130 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1132 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1135 */       float pcm_sample = (vp[7 + dvp] * dp[0] + 
/* 1136 */         vp[6 + dvp] * dp[1] + 
/* 1137 */         vp[5 + dvp] * dp[2] + 
/* 1138 */         vp[4 + dvp] * dp[3] + 
/* 1139 */         vp[3 + dvp] * dp[4] + 
/* 1140 */         vp[2 + dvp] * dp[5] + 
/* 1141 */         vp[1 + dvp] * dp[6] + 
/* 1142 */         vp[0 + dvp] * dp[7] + 
/* 1143 */         vp[15 + dvp] * dp[8] + 
/* 1144 */         vp[14 + dvp] * dp[9] + 
/* 1145 */         vp[13 + dvp] * dp[10] + 
/* 1146 */         vp[12 + dvp] * dp[11] + 
/* 1147 */         vp[11 + dvp] * dp[12] + 
/* 1148 */         vp[10 + dvp] * dp[13] + 
/* 1149 */         vp[9 + dvp] * dp[14] + 
/* 1150 */         vp[8 + dvp] * dp[15]) * 
/* 1151 */         this.scalefactor;
/*      */       
/* 1153 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1155 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void compute_pcm_samples8(Obuffer buffer) {
/* 1160 */     float[] vp = this.actual_v;
/*      */ 
/*      */     
/* 1163 */     float[] tmpOut = this._tmpOut;
/* 1164 */     int dvp = 0;
/*      */ 
/*      */     
/* 1167 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1169 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1172 */       float pcm_sample = (vp[8 + dvp] * dp[0] + 
/* 1173 */         vp[7 + dvp] * dp[1] + 
/* 1174 */         vp[6 + dvp] * dp[2] + 
/* 1175 */         vp[5 + dvp] * dp[3] + 
/* 1176 */         vp[4 + dvp] * dp[4] + 
/* 1177 */         vp[3 + dvp] * dp[5] + 
/* 1178 */         vp[2 + dvp] * dp[6] + 
/* 1179 */         vp[1 + dvp] * dp[7] + 
/* 1180 */         vp[0 + dvp] * dp[8] + 
/* 1181 */         vp[15 + dvp] * dp[9] + 
/* 1182 */         vp[14 + dvp] * dp[10] + 
/* 1183 */         vp[13 + dvp] * dp[11] + 
/* 1184 */         vp[12 + dvp] * dp[12] + 
/* 1185 */         vp[11 + dvp] * dp[13] + 
/* 1186 */         vp[10 + dvp] * dp[14] + 
/* 1187 */         vp[9 + dvp] * dp[15]) * 
/* 1188 */         this.scalefactor;
/*      */       
/* 1190 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1192 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void compute_pcm_samples9(Obuffer buffer) {
/* 1198 */     float[] vp = this.actual_v;
/*      */ 
/*      */     
/* 1201 */     float[] tmpOut = this._tmpOut;
/* 1202 */     int dvp = 0;
/*      */ 
/*      */     
/* 1205 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1207 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1210 */       float pcm_sample = (vp[9 + dvp] * dp[0] + 
/* 1211 */         vp[8 + dvp] * dp[1] + 
/* 1212 */         vp[7 + dvp] * dp[2] + 
/* 1213 */         vp[6 + dvp] * dp[3] + 
/* 1214 */         vp[5 + dvp] * dp[4] + 
/* 1215 */         vp[4 + dvp] * dp[5] + 
/* 1216 */         vp[3 + dvp] * dp[6] + 
/* 1217 */         vp[2 + dvp] * dp[7] + 
/* 1218 */         vp[1 + dvp] * dp[8] + 
/* 1219 */         vp[0 + dvp] * dp[9] + 
/* 1220 */         vp[15 + dvp] * dp[10] + 
/* 1221 */         vp[14 + dvp] * dp[11] + 
/* 1222 */         vp[13 + dvp] * dp[12] + 
/* 1223 */         vp[12 + dvp] * dp[13] + 
/* 1224 */         vp[11 + dvp] * dp[14] + 
/* 1225 */         vp[10 + dvp] * dp[15]) * 
/* 1226 */         this.scalefactor;
/*      */       
/* 1228 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1230 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void compute_pcm_samples10(Obuffer buffer) {
/* 1236 */     float[] vp = this.actual_v;
/*      */     
/* 1238 */     float[] tmpOut = this._tmpOut;
/* 1239 */     int dvp = 0;
/*      */ 
/*      */     
/* 1242 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1244 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1247 */       float pcm_sample = (vp[10 + dvp] * dp[0] + 
/* 1248 */         vp[9 + dvp] * dp[1] + 
/* 1249 */         vp[8 + dvp] * dp[2] + 
/* 1250 */         vp[7 + dvp] * dp[3] + 
/* 1251 */         vp[6 + dvp] * dp[4] + 
/* 1252 */         vp[5 + dvp] * dp[5] + 
/* 1253 */         vp[4 + dvp] * dp[6] + 
/* 1254 */         vp[3 + dvp] * dp[7] + 
/* 1255 */         vp[2 + dvp] * dp[8] + 
/* 1256 */         vp[1 + dvp] * dp[9] + 
/* 1257 */         vp[0 + dvp] * dp[10] + 
/* 1258 */         vp[15 + dvp] * dp[11] + 
/* 1259 */         vp[14 + dvp] * dp[12] + 
/* 1260 */         vp[13 + dvp] * dp[13] + 
/* 1261 */         vp[12 + dvp] * dp[14] + 
/* 1262 */         vp[11 + dvp] * dp[15]) * 
/* 1263 */         this.scalefactor;
/*      */       
/* 1265 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1267 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void compute_pcm_samples11(Obuffer buffer) {
/* 1272 */     float[] vp = this.actual_v;
/*      */ 
/*      */     
/* 1275 */     float[] tmpOut = this._tmpOut;
/* 1276 */     int dvp = 0;
/*      */ 
/*      */     
/* 1279 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1281 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1284 */       float pcm_sample = (vp[11 + dvp] * dp[0] + 
/* 1285 */         vp[10 + dvp] * dp[1] + 
/* 1286 */         vp[9 + dvp] * dp[2] + 
/* 1287 */         vp[8 + dvp] * dp[3] + 
/* 1288 */         vp[7 + dvp] * dp[4] + 
/* 1289 */         vp[6 + dvp] * dp[5] + 
/* 1290 */         vp[5 + dvp] * dp[6] + 
/* 1291 */         vp[4 + dvp] * dp[7] + 
/* 1292 */         vp[3 + dvp] * dp[8] + 
/* 1293 */         vp[2 + dvp] * dp[9] + 
/* 1294 */         vp[1 + dvp] * dp[10] + 
/* 1295 */         vp[0 + dvp] * dp[11] + 
/* 1296 */         vp[15 + dvp] * dp[12] + 
/* 1297 */         vp[14 + dvp] * dp[13] + 
/* 1298 */         vp[13 + dvp] * dp[14] + 
/* 1299 */         vp[12 + dvp] * dp[15]) * 
/* 1300 */         this.scalefactor;
/*      */       
/* 1302 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1304 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void compute_pcm_samples12(Obuffer buffer) {
/* 1309 */     float[] vp = this.actual_v;
/*      */     
/* 1311 */     float[] tmpOut = this._tmpOut;
/* 1312 */     int dvp = 0;
/*      */ 
/*      */     
/* 1315 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1317 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1320 */       float pcm_sample = (vp[12 + dvp] * dp[0] + 
/* 1321 */         vp[11 + dvp] * dp[1] + 
/* 1322 */         vp[10 + dvp] * dp[2] + 
/* 1323 */         vp[9 + dvp] * dp[3] + 
/* 1324 */         vp[8 + dvp] * dp[4] + 
/* 1325 */         vp[7 + dvp] * dp[5] + 
/* 1326 */         vp[6 + dvp] * dp[6] + 
/* 1327 */         vp[5 + dvp] * dp[7] + 
/* 1328 */         vp[4 + dvp] * dp[8] + 
/* 1329 */         vp[3 + dvp] * dp[9] + 
/* 1330 */         vp[2 + dvp] * dp[10] + 
/* 1331 */         vp[1 + dvp] * dp[11] + 
/* 1332 */         vp[0 + dvp] * dp[12] + 
/* 1333 */         vp[15 + dvp] * dp[13] + 
/* 1334 */         vp[14 + dvp] * dp[14] + 
/* 1335 */         vp[13 + dvp] * dp[15]) * 
/* 1336 */         this.scalefactor;
/*      */       
/* 1338 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1340 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void compute_pcm_samples13(Obuffer buffer) {
/* 1345 */     float[] vp = this.actual_v;
/*      */ 
/*      */     
/* 1348 */     float[] tmpOut = this._tmpOut;
/* 1349 */     int dvp = 0;
/*      */ 
/*      */     
/* 1352 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1354 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1357 */       float pcm_sample = (vp[13 + dvp] * dp[0] + 
/* 1358 */         vp[12 + dvp] * dp[1] + 
/* 1359 */         vp[11 + dvp] * dp[2] + 
/* 1360 */         vp[10 + dvp] * dp[3] + 
/* 1361 */         vp[9 + dvp] * dp[4] + 
/* 1362 */         vp[8 + dvp] * dp[5] + 
/* 1363 */         vp[7 + dvp] * dp[6] + 
/* 1364 */         vp[6 + dvp] * dp[7] + 
/* 1365 */         vp[5 + dvp] * dp[8] + 
/* 1366 */         vp[4 + dvp] * dp[9] + 
/* 1367 */         vp[3 + dvp] * dp[10] + 
/* 1368 */         vp[2 + dvp] * dp[11] + 
/* 1369 */         vp[1 + dvp] * dp[12] + 
/* 1370 */         vp[0 + dvp] * dp[13] + 
/* 1371 */         vp[15 + dvp] * dp[14] + 
/* 1372 */         vp[14 + dvp] * dp[15]) * 
/* 1373 */         this.scalefactor;
/*      */       
/* 1375 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1377 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void compute_pcm_samples14(Obuffer buffer) {
/* 1382 */     float[] vp = this.actual_v;
/*      */ 
/*      */     
/* 1385 */     float[] tmpOut = this._tmpOut;
/* 1386 */     int dvp = 0;
/*      */ 
/*      */     
/* 1389 */     for (int i = 0; i < 32; i++) {
/*      */       
/* 1391 */       float[] dp = d16[i];
/*      */ 
/*      */       
/* 1394 */       float pcm_sample = (vp[14 + dvp] * dp[0] + 
/* 1395 */         vp[13 + dvp] * dp[1] + 
/* 1396 */         vp[12 + dvp] * dp[2] + 
/* 1397 */         vp[11 + dvp] * dp[3] + 
/* 1398 */         vp[10 + dvp] * dp[4] + 
/* 1399 */         vp[9 + dvp] * dp[5] + 
/* 1400 */         vp[8 + dvp] * dp[6] + 
/* 1401 */         vp[7 + dvp] * dp[7] + 
/* 1402 */         vp[6 + dvp] * dp[8] + 
/* 1403 */         vp[5 + dvp] * dp[9] + 
/* 1404 */         vp[4 + dvp] * dp[10] + 
/* 1405 */         vp[3 + dvp] * dp[11] + 
/* 1406 */         vp[2 + dvp] * dp[12] + 
/* 1407 */         vp[1 + dvp] * dp[13] + 
/* 1408 */         vp[0 + dvp] * dp[14] + 
/* 1409 */         vp[15 + dvp] * dp[15]) * 
/* 1410 */         this.scalefactor;
/*      */       
/* 1412 */       tmpOut[i] = pcm_sample;
/*      */       
/* 1414 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void compute_pcm_samples15(Obuffer buffer) {
/* 1419 */     float[] vp = this.actual_v;
/*      */ 
/*      */     
/* 1422 */     float[] tmpOut = this._tmpOut;
/* 1423 */     int dvp = 0;
/*      */ 
/*      */     
/* 1426 */     for (int i = 0; i < 32; i++) {
/*      */ 
/*      */       
/* 1429 */       float[] dp = d16[i];
/* 1430 */       float pcm_sample = (vp[15 + dvp] * dp[0] + 
/* 1431 */         vp[14 + dvp] * dp[1] + 
/* 1432 */         vp[13 + dvp] * dp[2] + 
/* 1433 */         vp[12 + dvp] * dp[3] + 
/* 1434 */         vp[11 + dvp] * dp[4] + 
/* 1435 */         vp[10 + dvp] * dp[5] + 
/* 1436 */         vp[9 + dvp] * dp[6] + 
/* 1437 */         vp[8 + dvp] * dp[7] + 
/* 1438 */         vp[7 + dvp] * dp[8] + 
/* 1439 */         vp[6 + dvp] * dp[9] + 
/* 1440 */         vp[5 + dvp] * dp[10] + 
/* 1441 */         vp[4 + dvp] * dp[11] + 
/* 1442 */         vp[3 + dvp] * dp[12] + 
/* 1443 */         vp[2 + dvp] * dp[13] + 
/* 1444 */         vp[1 + dvp] * dp[14] + 
/* 1445 */         vp[0 + dvp] * dp[15]) * 
/* 1446 */         this.scalefactor;
/*      */       
/* 1448 */       tmpOut[i] = pcm_sample;
/* 1449 */       dvp += 16;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void compute_pcm_samples(Obuffer buffer) {
/* 1456 */     switch (this.actual_write_pos) {
/*      */       
/*      */       case 0:
/* 1459 */         compute_pcm_samples0(buffer);
/*      */         break;
/*      */       case 1:
/* 1462 */         compute_pcm_samples1(buffer);
/*      */         break;
/*      */       case 2:
/* 1465 */         compute_pcm_samples2(buffer);
/*      */         break;
/*      */       case 3:
/* 1468 */         compute_pcm_samples3(buffer);
/*      */         break;
/*      */       case 4:
/* 1471 */         compute_pcm_samples4(buffer);
/*      */         break;
/*      */       case 5:
/* 1474 */         compute_pcm_samples5(buffer);
/*      */         break;
/*      */       case 6:
/* 1477 */         compute_pcm_samples6(buffer);
/*      */         break;
/*      */       case 7:
/* 1480 */         compute_pcm_samples7(buffer);
/*      */         break;
/*      */       case 8:
/* 1483 */         compute_pcm_samples8(buffer);
/*      */         break;
/*      */       case 9:
/* 1486 */         compute_pcm_samples9(buffer);
/*      */         break;
/*      */       case 10:
/* 1489 */         compute_pcm_samples10(buffer);
/*      */         break;
/*      */       case 11:
/* 1492 */         compute_pcm_samples11(buffer);
/*      */         break;
/*      */       case 12:
/* 1495 */         compute_pcm_samples12(buffer);
/*      */         break;
/*      */       case 13:
/* 1498 */         compute_pcm_samples13(buffer);
/*      */         break;
/*      */       case 14:
/* 1501 */         compute_pcm_samples14(buffer);
/*      */         break;
/*      */       case 15:
/* 1504 */         compute_pcm_samples15(buffer);
/*      */         break;
/*      */     } 
/*      */     
/* 1508 */     if (buffer != null)
/*      */     {
/* 1510 */       buffer.appendSamples(this.channel, this._tmpOut);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void calculate_pcm_samples(Obuffer buffer) {
/* 1549 */     compute_new_v();
/* 1550 */     compute_pcm_samples(buffer);
/*      */     
/* 1552 */     this.actual_write_pos = this.actual_write_pos + 1 & 0xF;
/* 1553 */     this.actual_v = (this.actual_v == this.v1) ? this.v2 : this.v1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1561 */     for (int p = 0; p < 32; p++) {
/* 1562 */       this.samples[p] = 0.0F;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 1567 */   private static final float cos1_64 = (float)(1.0D / 2.0D * Math.cos(0.04908738521234052D));
/* 1568 */   private static final float cos3_64 = (float)(1.0D / 2.0D * Math.cos(0.14726215563702155D));
/* 1569 */   private static final float cos5_64 = (float)(1.0D / 2.0D * Math.cos(0.2454369260617026D));
/* 1570 */   private static final float cos7_64 = (float)(1.0D / 2.0D * Math.cos(0.3436116964863836D));
/* 1571 */   private static final float cos9_64 = (float)(1.0D / 2.0D * Math.cos(0.44178646691106466D));
/* 1572 */   private static final float cos11_64 = (float)(1.0D / 2.0D * Math.cos(0.5399612373357456D));
/* 1573 */   private static final float cos13_64 = (float)(1.0D / 2.0D * Math.cos(0.6381360077604268D));
/* 1574 */   private static final float cos15_64 = (float)(1.0D / 2.0D * Math.cos(0.7363107781851077D));
/* 1575 */   private static final float cos17_64 = (float)(1.0D / 2.0D * Math.cos(0.8344855486097889D));
/* 1576 */   private static final float cos19_64 = (float)(1.0D / 2.0D * Math.cos(0.9326603190344698D));
/* 1577 */   private static final float cos21_64 = (float)(1.0D / 2.0D * Math.cos(1.030835089459151D));
/* 1578 */   private static final float cos23_64 = (float)(1.0D / 2.0D * Math.cos(1.1290098598838318D));
/* 1579 */   private static final float cos25_64 = (float)(1.0D / 2.0D * Math.cos(1.227184630308513D));
/* 1580 */   private static final float cos27_64 = (float)(1.0D / 2.0D * Math.cos(1.325359400733194D));
/* 1581 */   private static final float cos29_64 = (float)(1.0D / 2.0D * Math.cos(1.423534171157875D));
/* 1582 */   private static final float cos31_64 = (float)(1.0D / 2.0D * Math.cos(1.521708941582556D));
/* 1583 */   private static final float cos1_32 = (float)(1.0D / 2.0D * Math.cos(0.09817477042468103D));
/* 1584 */   private static final float cos3_32 = (float)(1.0D / 2.0D * Math.cos(0.2945243112740431D));
/* 1585 */   private static final float cos5_32 = (float)(1.0D / 2.0D * Math.cos(0.4908738521234052D));
/* 1586 */   private static final float cos7_32 = (float)(1.0D / 2.0D * Math.cos(0.6872233929727672D));
/* 1587 */   private static final float cos9_32 = (float)(1.0D / 2.0D * Math.cos(0.8835729338221293D));
/* 1588 */   private static final float cos11_32 = (float)(1.0D / 2.0D * Math.cos(1.0799224746714913D));
/* 1589 */   private static final float cos13_32 = (float)(1.0D / 2.0D * Math.cos(1.2762720155208536D));
/* 1590 */   private static final float cos15_32 = (float)(1.0D / 2.0D * Math.cos(1.4726215563702154D));
/* 1591 */   private static final float cos1_16 = (float)(1.0D / 2.0D * Math.cos(0.19634954084936207D));
/* 1592 */   private static final float cos3_16 = (float)(1.0D / 2.0D * Math.cos(0.5890486225480862D));
/* 1593 */   private static final float cos5_16 = (float)(1.0D / 2.0D * Math.cos(0.9817477042468103D));
/* 1594 */   private static final float cos7_16 = (float)(1.0D / 2.0D * Math.cos(1.3744467859455345D));
/* 1595 */   private static final float cos1_8 = (float)(1.0D / 2.0D * Math.cos(0.39269908169872414D));
/* 1596 */   private static final float cos3_8 = (float)(1.0D / 2.0D * Math.cos(1.1780972450961724D));
/* 1597 */   private static final float cos1_4 = (float)(1.0D / 2.0D * Math.cos(0.7853981633974483D));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1603 */   private static float[] d = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1610 */   private static float[][] d16 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[] load_d() {
/*      */     try {
/* 1620 */       Class<float> elemType = float.class;
/* 1621 */       Object o = JavaLayerUtils.deserializeArrayResource("sfd.ser", elemType, 512);
/* 1622 */       return (float[])o;
/*      */     }
/* 1624 */     catch (IOException ex) {
/*      */       
/* 1626 */       throw new ExceptionInInitializerError(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[][] splitArray(float[] array, int blockSize) {
/* 1645 */     int size = array.length / blockSize;
/* 1646 */     float[][] split = new float[size][];
/* 1647 */     for (int i = 0; i < size; i++)
/*      */     {
/* 1649 */       split[i] = subArray(array, i * blockSize, blockSize);
/*      */     }
/* 1651 */     return split;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float[] subArray(float[] array, int offs, int len) {
/* 1665 */     if (offs + len > array.length)
/*      */     {
/* 1667 */       len = array.length - offs;
/*      */     }
/*      */     
/* 1670 */     if (len < 0) {
/* 1671 */       len = 0;
/*      */     }
/* 1673 */     float[] subarray = new float[len];
/* 1674 */     for (int i = 0; i < len; i++)
/*      */     {
/* 1676 */       subarray[i] = array[offs + i];
/*      */     }
/*      */     
/* 1679 */     return subarray;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\SynthesisFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */