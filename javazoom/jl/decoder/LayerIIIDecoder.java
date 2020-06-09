/*      */ package javazoom.jl.decoder;
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
/*      */ final class LayerIIIDecoder
/*      */   implements FrameDecoder
/*      */ {
/*   44 */   final double d43 = 1.3333333333333333D;
/*      */ 
/*      */   
/*      */   public int[] scalefac_buffer;
/*      */ 
/*      */   
/*   50 */   private int CheckSumHuff = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] is_1d;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float[][][] ro;
/*      */ 
/*      */ 
/*      */   
/*      */   private float[][][] lr;
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] out_1d;
/*      */ 
/*      */ 
/*      */   
/*      */   private float[][] prevblck;
/*      */ 
/*      */ 
/*      */   
/*      */   private float[][] k;
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] nonzero;
/*      */ 
/*      */ 
/*      */   
/*      */   private Bitstream stream;
/*      */ 
/*      */ 
/*      */   
/*      */   private Header header;
/*      */ 
/*      */ 
/*      */   
/*      */   private SynthesisFilter filter1;
/*      */ 
/*      */ 
/*      */   
/*      */   private SynthesisFilter filter2;
/*      */ 
/*      */ 
/*      */   
/*      */   private Obuffer buffer;
/*      */ 
/*      */ 
/*      */   
/*      */   private int which_channels;
/*      */ 
/*      */ 
/*      */   
/*      */   private BitReserve br;
/*      */ 
/*      */ 
/*      */   
/*      */   private III_side_info_t si;
/*      */ 
/*      */ 
/*      */   
/*      */   private temporaire2[] III_scalefac_t;
/*      */ 
/*      */ 
/*      */   
/*      */   private temporaire2[] scalefac;
/*      */ 
/*      */ 
/*      */   
/*      */   private int max_gr;
/*      */ 
/*      */ 
/*      */   
/*      */   private int frame_start;
/*      */ 
/*      */ 
/*      */   
/*      */   private int part2_start;
/*      */ 
/*      */ 
/*      */   
/*      */   private int channels;
/*      */ 
/*      */ 
/*      */   
/*      */   private int first_channel;
/*      */ 
/*      */ 
/*      */   
/*      */   private int last_channel;
/*      */ 
/*      */ 
/*      */   
/*      */   private int sfreq;
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] samples1;
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] samples2;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[] new_slen;
/*      */ 
/*      */ 
/*      */   
/*      */   int[] x;
/*      */ 
/*      */ 
/*      */   
/*      */   int[] y;
/*      */ 
/*      */ 
/*      */   
/*      */   int[] v;
/*      */ 
/*      */ 
/*      */   
/*      */   int[] w;
/*      */ 
/*      */ 
/*      */   
/*      */   int[] is_pos;
/*      */ 
/*      */ 
/*      */   
/*      */   float[] is_ratio;
/*      */ 
/*      */ 
/*      */   
/*      */   float[] tsOutCopy;
/*      */ 
/*      */ 
/*      */   
/*      */   float[] rawout;
/*      */ 
/*      */ 
/*      */   
/*      */   private int counter;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SSLIMIT = 18;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int SBLIMIT = 32;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void seek_notify() {
/*  210 */     this.frame_start = 0;
/*  211 */     for (int ch = 0; ch < 2; ch++) {
/*  212 */       for (int j = 0; j < 576; j++)
/*  213 */         this.prevblck[ch][j] = 0.0F; 
/*  214 */     }  this.br = new BitReserve();
/*      */   }
/*      */ 
/*      */   
/*      */   public void decodeFrame() {
/*  219 */     decode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LayerIIIDecoder(Bitstream stream0, Header header0, SynthesisFilter filtera, SynthesisFilter filterb, Obuffer buffer0, int which_ch0) {
/*  228 */     this.samples1 = new float[32];
/*  229 */     this.samples2 = new float[32];
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
/*  586 */     this.new_slen = new int[4];
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
/*  743 */     this.x = new int[1];
/*  744 */     this.y = new int[1];
/*  745 */     this.v = new int[1];
/*  746 */     this.w = new int[1];
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
/* 1115 */     this.is_pos = new int[576];
/* 1116 */     this.is_ratio = new float[576];
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
/* 1441 */     this.tsOutCopy = new float[18];
/* 1442 */     this.rawout = new float[36];
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
/* 1816 */     this.counter = 0; huffcodetab.inithuff(); this.is_1d = new int[580]; this.ro = new float[2][32][18]; this.lr = new float[2][32][18]; this.out_1d = new float[576]; this.prevblck = new float[2][576]; this.k = new float[2][576]; this.nonzero = new int[2]; this.III_scalefac_t = new temporaire2[2]; this.III_scalefac_t[0] = new temporaire2(); this.III_scalefac_t[1] = new temporaire2(); this.scalefac = this.III_scalefac_t; this.sfBandIndex = new SBI[9]; int[] l0 = { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464, 522, 576 }; int[] s0 = { 0, 4, 8, 12, 18, 24, 32, 42, 56, 74, 100, 132, 174, 192 }; int[] l1 = { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 114, 136, 162, 194, 232, 278, 330, 394, 464, 540, 576 }; int[] s1 = { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 136, 180, 192 }; int[] l2 = { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464, 522, 576 }; int[] s2 = { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 134, 174, 192 }; int[] l3 = { 0, 4, 8, 12, 16, 20, 24, 30, 36, 44, 52, 62, 74, 90, 110, 134, 162, 196, 238, 288, 342, 418, 576 }; int[] s3 = { 0, 4, 8, 12, 16, 22, 30, 40, 52, 66, 84, 106, 136, 192 }; int[] l4 = { 0, 4, 8, 12, 16, 20, 24, 30, 36, 42, 50, 60, 72, 88, 106, 128, 156, 190, 230, 276, 330, 384, 576 }; int[] s4 = { 0, 4, 8, 12, 16, 22, 28, 38, 50, 64, 80, 100, 126, 192 }; int[] l5 = { 0, 4, 8, 12, 16, 20, 24, 30, 36, 44, 54, 66, 82, 102, 126, 156, 194, 240, 296, 364, 448, 550, 576 }; int[] s5 = { 0, 4, 8, 12, 16, 22, 30, 42, 58, 78, 104, 138, 180, 192 }; int[] l6 = { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464, 522, 576 }; int[] s6 = { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 134, 174, 192 }; int[] l7 = { 0, 6, 12, 18, 24, 30, 36, 44, 54, 66, 80, 96, 116, 140, 168, 200, 238, 284, 336, 396, 464, 522, 576 }; int[] s7 = { 0, 4, 8, 12, 18, 26, 36, 48, 62, 80, 104, 134, 174, 192 }; int[] l8 = { 0, 12, 24, 36, 48, 60, 72, 88, 108, 132, 
/*      */         160, 192, 232, 280, 336, 400, 476, 566, 568, 570, 
/*      */         572, 574, 576 }; int[] s8 = { 
/*      */         0, 8, 16, 24, 36, 52, 72, 96, 124, 160, 
/*      */         162, 164, 166, 192 }; this.sfBandIndex[0] = new SBI(l0, s0); this.sfBandIndex[1] = new SBI(l1, s1); this.sfBandIndex[2] = new SBI(l2, s2); this.sfBandIndex[3] = new SBI(l3, s3); this.sfBandIndex[4] = new SBI(l4, s4); this.sfBandIndex[5] = new SBI(l5, s5); this.sfBandIndex[6] = new SBI(l6, s6); this.sfBandIndex[7] = new SBI(l7, s7); this.sfBandIndex[8] = new SBI(l8, s8); if (reorder_table == null) { reorder_table = new int[9][]; for (int i = 0; i < 9; i++) reorder_table[i] = reorder((this.sfBandIndex[i]).s);  }  int[] ll0 = { 0, 6, 11, 16, 21 }; int[] ss0 = { 0, 6, 12 }; this.sftable = new Sftable(ll0, ss0); this.scalefac_buffer = new int[54]; this.stream = stream0; this.header = header0; this.filter1 = filtera; this.filter2 = filterb; this.buffer = buffer0; this.which_channels = which_ch0; this.frame_start = 0; this.channels = (this.header.mode() == 3) ? 1 : 2; this.max_gr = (this.header.version() == 1) ? 2 : 1; this.sfreq = this.header.sample_frequency() + ((this.header.version() == 1) ? 3 : ((this.header.version() == 2) ? 6 : 0)); if (this.channels == 2) { switch (this.which_channels) { case 1: case 3: this.first_channel = this.last_channel = 0; break;case 2: this.first_channel = this.last_channel = 1; break;default: this.first_channel = 0; this.last_channel = 1; break; }  } else { this.first_channel = this.last_channel = 0; }  for (int ch = 0; ch < 2; ch++) { for (int j = 0; j < 576; j++) this.prevblck[ch][j] = 0.0F;  }  this.nonzero[1] = 576; this.nonzero[0] = 576; this.br = new BitReserve(); this.si = new III_side_info_t();
/*      */   }
/*      */   public void decode() { int nSlots = this.header.slots(); get_side_info(); for (int i = 0; i < nSlots; i++) this.br.hputbuf(this.stream.get_bits(8));  int main_data_end = this.br.hsstell() >>> 3; int flush_main; if ((flush_main = this.br.hsstell() & 0x7) != 0) { this.br.hgetbits(8 - flush_main); main_data_end++; }  int bytes_to_discard = this.frame_start - main_data_end - this.si.main_data_begin; this.frame_start += nSlots; if (bytes_to_discard < 0) return;  if (main_data_end > 4096) { this.frame_start -= 4096; this.br.rewindNbytes(4096); }  for (; bytes_to_discard > 0; bytes_to_discard--) this.br.hgetbits(8);  for (int gr = 0; gr < this.max_gr; gr++) { int ch; for (ch = 0; ch < this.channels; ch++) { this.part2_start = this.br.hsstell(); if (this.header.version() == 1) { get_scale_factors(ch, gr); } else { get_LSF_scale_factors(ch, gr); }  huffman_decode(ch, gr); dequantize_sample(this.ro[ch], ch, gr); }  stereo(gr); if (this.which_channels == 3 && this.channels > 1) do_downmix();  for (ch = this.first_channel; ch <= this.last_channel; ch++) { reorder(this.lr[ch], ch, gr); antialias(ch, gr); hybrid(ch, gr); int sb18; for (sb18 = 18; sb18 < 576; sb18 += 36) { for (int ss = 1; ss < 18; ss += 2) this.out_1d[sb18 + ss] = -this.out_1d[sb18 + ss];  }  if (ch == 0 || this.which_channels == 2) { for (int ss = 0; ss < 18; ss++) { int sb = 0; for (sb18 = 0; sb18 < 576; sb18 += 18) { this.samples1[sb] = this.out_1d[sb18 + ss]; sb++; }  this.filter1.input_samples(this.samples1); this.filter1.calculate_pcm_samples(this.buffer); }  } else { for (int ss = 0; ss < 18; ss++) { int sb = 0; for (sb18 = 0; sb18 < 576; sb18 += 18) { this.samples2[sb] = this.out_1d[sb18 + ss]; sb++; }  this.filter2.input_samples(this.samples2); this.filter2.calculate_pcm_samples(this.buffer); }  }  }  }  this.counter++; this.buffer.write_buffer(1); }
/*      */   private boolean get_side_info() { if (this.header.version() == 1) { this.si.main_data_begin = this.stream.get_bits(9); if (this.channels == 1) { this.si.private_bits = this.stream.get_bits(5); } else { this.si.private_bits = this.stream.get_bits(3); }  int ch; for (ch = 0; ch < this.channels; ch++) { (this.si.ch[ch]).scfsi[0] = this.stream.get_bits(1); (this.si.ch[ch]).scfsi[1] = this.stream.get_bits(1); (this.si.ch[ch]).scfsi[2] = this.stream.get_bits(1); (this.si.ch[ch]).scfsi[3] = this.stream.get_bits(1); }  for (int gr = 0; gr < 2; gr++) { for (ch = 0; ch < this.channels; ch++) { ((this.si.ch[ch]).gr[gr]).part2_3_length = this.stream.get_bits(12); ((this.si.ch[ch]).gr[gr]).big_values = this.stream.get_bits(9); ((this.si.ch[ch]).gr[gr]).global_gain = this.stream.get_bits(8); ((this.si.ch[ch]).gr[gr]).scalefac_compress = this.stream.get_bits(4); ((this.si.ch[ch]).gr[gr]).window_switching_flag = this.stream.get_bits(1); if (((this.si.ch[ch]).gr[gr]).window_switching_flag != 0) { ((this.si.ch[ch]).gr[gr]).block_type = this.stream.get_bits(2); ((this.si.ch[ch]).gr[gr]).mixed_block_flag = this.stream.get_bits(1); ((this.si.ch[ch]).gr[gr]).table_select[0] = this.stream.get_bits(5); ((this.si.ch[ch]).gr[gr]).table_select[1] = this.stream.get_bits(5); ((this.si.ch[ch]).gr[gr]).subblock_gain[0] = this.stream.get_bits(3); ((this.si.ch[ch]).gr[gr]).subblock_gain[1] = this.stream.get_bits(3); ((this.si.ch[ch]).gr[gr]).subblock_gain[2] = this.stream.get_bits(3); if (((this.si.ch[ch]).gr[gr]).block_type == 0) return false;  if (((this.si.ch[ch]).gr[gr]).block_type == 2 && ((this.si.ch[ch]).gr[gr]).mixed_block_flag == 0) { ((this.si.ch[ch]).gr[gr]).region0_count = 8; } else { ((this.si.ch[ch]).gr[gr]).region0_count = 7; }  ((this.si.ch[ch]).gr[gr]).region1_count = 20 - ((this.si.ch[ch]).gr[gr]).region0_count; } else { ((this.si.ch[ch]).gr[gr]).table_select[0] = this.stream.get_bits(5); ((this.si.ch[ch]).gr[gr]).table_select[1] = this.stream.get_bits(5); ((this.si.ch[ch]).gr[gr]).table_select[2] = this.stream.get_bits(5); ((this.si.ch[ch]).gr[gr]).region0_count = this.stream.get_bits(4); ((this.si.ch[ch]).gr[gr]).region1_count = this.stream.get_bits(3); ((this.si.ch[ch]).gr[gr]).block_type = 0; }  ((this.si.ch[ch]).gr[gr]).preflag = this.stream.get_bits(1); ((this.si.ch[ch]).gr[gr]).scalefac_scale = this.stream.get_bits(1); ((this.si.ch[ch]).gr[gr]).count1table_select = this.stream.get_bits(1); }  }  } else { this.si.main_data_begin = this.stream.get_bits(8); if (this.channels == 1) { this.si.private_bits = this.stream.get_bits(1); } else { this.si.private_bits = this.stream.get_bits(2); }  for (int ch = 0; ch < this.channels; ch++) { ((this.si.ch[ch]).gr[0]).part2_3_length = this.stream.get_bits(12); ((this.si.ch[ch]).gr[0]).big_values = this.stream.get_bits(9); ((this.si.ch[ch]).gr[0]).global_gain = this.stream.get_bits(8); ((this.si.ch[ch]).gr[0]).scalefac_compress = this.stream.get_bits(9); ((this.si.ch[ch]).gr[0]).window_switching_flag = this.stream.get_bits(1); if (((this.si.ch[ch]).gr[0]).window_switching_flag != 0) { ((this.si.ch[ch]).gr[0]).block_type = this.stream.get_bits(2); ((this.si.ch[ch]).gr[0]).mixed_block_flag = this.stream.get_bits(1); ((this.si.ch[ch]).gr[0]).table_select[0] = this.stream.get_bits(5); ((this.si.ch[ch]).gr[0]).table_select[1] = this.stream.get_bits(5); ((this.si.ch[ch]).gr[0]).subblock_gain[0] = this.stream.get_bits(3); ((this.si.ch[ch]).gr[0]).subblock_gain[1] = this.stream.get_bits(3); ((this.si.ch[ch]).gr[0]).subblock_gain[2] = this.stream.get_bits(3); if (((this.si.ch[ch]).gr[0]).block_type == 0) return false;  if (((this.si.ch[ch]).gr[0]).block_type == 2 && ((this.si.ch[ch]).gr[0]).mixed_block_flag == 0) { ((this.si.ch[ch]).gr[0]).region0_count = 8; } else { ((this.si.ch[ch]).gr[0]).region0_count = 7; ((this.si.ch[ch]).gr[0]).region1_count = 20 - ((this.si.ch[ch]).gr[0]).region0_count; }  } else { ((this.si.ch[ch]).gr[0]).table_select[0] = this.stream.get_bits(5); ((this.si.ch[ch]).gr[0]).table_select[1] = this.stream.get_bits(5); ((this.si.ch[ch]).gr[0]).table_select[2] = this.stream.get_bits(5); ((this.si.ch[ch]).gr[0]).region0_count = this.stream.get_bits(4); ((this.si.ch[ch]).gr[0]).region1_count = this.stream.get_bits(3); ((this.si.ch[ch]).gr[0]).block_type = 0; }  ((this.si.ch[ch]).gr[0]).scalefac_scale = this.stream.get_bits(1); ((this.si.ch[ch]).gr[0]).count1table_select = this.stream.get_bits(1); }  }  return true; }
/*      */   private void get_scale_factors(int ch, int gr) { gr_info_s gr_info = (this.si.ch[ch]).gr[gr]; int scale_comp = gr_info.scalefac_compress; int length0 = slen[0][scale_comp]; int length1 = slen[1][scale_comp]; if (gr_info.window_switching_flag != 0 && gr_info.block_type == 2) { if (gr_info.mixed_block_flag != 0) { int sfb; for (sfb = 0; sfb < 8; sfb++) (this.scalefac[ch]).l[sfb] = this.br.hgetbits(slen[0][gr_info.scalefac_compress]);  for (sfb = 3; sfb < 6; sfb++) { for (int i = 0; i < 3; i++) (this.scalefac[ch]).s[i][sfb] = this.br.hgetbits(slen[0][gr_info.scalefac_compress]);  }  for (sfb = 6; sfb < 12; sfb++) { for (int i = 0; i < 3; i++) (this.scalefac[ch]).s[i][sfb] = this.br.hgetbits(slen[1][gr_info.scalefac_compress]);  }  int window; for (sfb = 12, window = 0; window < 3; window++) (this.scalefac[ch]).s[window][sfb] = 0;  } else { (this.scalefac[ch]).s[0][0] = this.br.hgetbits(length0); (this.scalefac[ch]).s[1][0] = this.br.hgetbits(length0); (this.scalefac[ch]).s[2][0] = this.br.hgetbits(length0); (this.scalefac[ch]).s[0][1] = this.br.hgetbits(length0); (this.scalefac[ch]).s[1][1] = this.br.hgetbits(length0); (this.scalefac[ch]).s[2][1] = this.br.hgetbits(length0); (this.scalefac[ch]).s[0][2] = this.br.hgetbits(length0); (this.scalefac[ch]).s[1][2] = this.br.hgetbits(length0); (this.scalefac[ch]).s[2][2] = this.br.hgetbits(length0); (this.scalefac[ch]).s[0][3] = this.br.hgetbits(length0); (this.scalefac[ch]).s[1][3] = this.br.hgetbits(length0); (this.scalefac[ch]).s[2][3] = this.br.hgetbits(length0); (this.scalefac[ch]).s[0][4] = this.br.hgetbits(length0); (this.scalefac[ch]).s[1][4] = this.br.hgetbits(length0); (this.scalefac[ch]).s[2][4] = this.br.hgetbits(length0); (this.scalefac[ch]).s[0][5] = this.br.hgetbits(length0); (this.scalefac[ch]).s[1][5] = this.br.hgetbits(length0); (this.scalefac[ch]).s[2][5] = this.br.hgetbits(length0); (this.scalefac[ch]).s[0][6] = this.br.hgetbits(length1); (this.scalefac[ch]).s[1][6] = this.br.hgetbits(length1); (this.scalefac[ch]).s[2][6] = this.br.hgetbits(length1); (this.scalefac[ch]).s[0][7] = this.br.hgetbits(length1); (this.scalefac[ch]).s[1][7] = this.br.hgetbits(length1); (this.scalefac[ch]).s[2][7] = this.br.hgetbits(length1); (this.scalefac[ch]).s[0][8] = this.br.hgetbits(length1); (this.scalefac[ch]).s[1][8] = this.br.hgetbits(length1); (this.scalefac[ch]).s[2][8] = this.br.hgetbits(length1); (this.scalefac[ch]).s[0][9] = this.br.hgetbits(length1); (this.scalefac[ch]).s[1][9] = this.br.hgetbits(length1); (this.scalefac[ch]).s[2][9] = this.br.hgetbits(length1); (this.scalefac[ch]).s[0][10] = this.br.hgetbits(length1); (this.scalefac[ch]).s[1][10] = this.br.hgetbits(length1); (this.scalefac[ch]).s[2][10] = this.br.hgetbits(length1); (this.scalefac[ch]).s[0][11] = this.br.hgetbits(length1); (this.scalefac[ch]).s[1][11] = this.br.hgetbits(length1); (this.scalefac[ch]).s[2][11] = this.br.hgetbits(length1); (this.scalefac[ch]).s[0][12] = 0; (this.scalefac[ch]).s[1][12] = 0; (this.scalefac[ch]).s[2][12] = 0; }  } else { if ((this.si.ch[ch]).scfsi[0] == 0 || gr == 0) { (this.scalefac[ch]).l[0] = this.br.hgetbits(length0); (this.scalefac[ch]).l[1] = this.br.hgetbits(length0); (this.scalefac[ch]).l[2] = this.br.hgetbits(length0); (this.scalefac[ch]).l[3] = this.br.hgetbits(length0); (this.scalefac[ch]).l[4] = this.br.hgetbits(length0); (this.scalefac[ch]).l[5] = this.br.hgetbits(length0); }  if ((this.si.ch[ch]).scfsi[1] == 0 || gr == 0) { (this.scalefac[ch]).l[6] = this.br.hgetbits(length0); (this.scalefac[ch]).l[7] = this.br.hgetbits(length0); (this.scalefac[ch]).l[8] = this.br.hgetbits(length0); (this.scalefac[ch]).l[9] = this.br.hgetbits(length0); (this.scalefac[ch]).l[10] = this.br.hgetbits(length0); }  if ((this.si.ch[ch]).scfsi[2] == 0 || gr == 0) { (this.scalefac[ch]).l[11] = this.br.hgetbits(length1); (this.scalefac[ch]).l[12] = this.br.hgetbits(length1); (this.scalefac[ch]).l[13] = this.br.hgetbits(length1); (this.scalefac[ch]).l[14] = this.br.hgetbits(length1); (this.scalefac[ch]).l[15] = this.br.hgetbits(length1); }  if ((this.si.ch[ch]).scfsi[3] == 0 || gr == 0) { (this.scalefac[ch]).l[16] = this.br.hgetbits(length1); (this.scalefac[ch]).l[17] = this.br.hgetbits(length1); (this.scalefac[ch]).l[18] = this.br.hgetbits(length1); (this.scalefac[ch]).l[19] = this.br.hgetbits(length1); (this.scalefac[ch]).l[20] = this.br.hgetbits(length1); }  (this.scalefac[ch]).l[21] = 0; (this.scalefac[ch]).l[22] = 0; }  }
/*      */   private void get_LSF_scale_data(int ch, int gr) { int blocktypenumber, mode_ext = this.header.mode_extension(); int blocknumber = 0; gr_info_s gr_info = (this.si.ch[ch]).gr[gr]; int scalefac_comp = gr_info.scalefac_compress; if (gr_info.block_type == 2) { if (gr_info.mixed_block_flag == 0) { blocktypenumber = 1; } else if (gr_info.mixed_block_flag == 1) { blocktypenumber = 2; } else { blocktypenumber = 0; }  } else { blocktypenumber = 0; }  if ((mode_ext != 1 && mode_ext != 3) || ch != 1) if (scalefac_comp < 400) { this.new_slen[0] = (scalefac_comp >>> 4) / 5; this.new_slen[1] = (scalefac_comp >>> 4) % 5; this.new_slen[2] = (scalefac_comp & 0xF) >>> 2; this.new_slen[3] = scalefac_comp & 0x3; ((this.si.ch[ch]).gr[gr]).preflag = 0; blocknumber = 0; } else if (scalefac_comp < 500) { this.new_slen[0] = (scalefac_comp - 400 >>> 2) / 5; this.new_slen[1] = (scalefac_comp - 400 >>> 2) % 5; this.new_slen[2] = scalefac_comp - 400 & 0x3; this.new_slen[3] = 0; ((this.si.ch[ch]).gr[gr]).preflag = 0; blocknumber = 1; } else if (scalefac_comp < 512) { this.new_slen[0] = (scalefac_comp - 500) / 3; this.new_slen[1] = (scalefac_comp - 500) % 3; this.new_slen[2] = 0; this.new_slen[3] = 0; ((this.si.ch[ch]).gr[gr]).preflag = 1; blocknumber = 2; }   if ((mode_ext == 1 || mode_ext == 3) && ch == 1) { int int_scalefac_comp = scalefac_comp >>> 1; if (int_scalefac_comp < 180) { this.new_slen[0] = int_scalefac_comp / 36; this.new_slen[1] = int_scalefac_comp % 36 / 6; this.new_slen[2] = int_scalefac_comp % 36 % 6; this.new_slen[3] = 0; ((this.si.ch[ch]).gr[gr]).preflag = 0; blocknumber = 3; } else if (int_scalefac_comp < 244) { this.new_slen[0] = (int_scalefac_comp - 180 & 0x3F) >>> 4; this.new_slen[1] = (int_scalefac_comp - 180 & 0xF) >>> 2; this.new_slen[2] = int_scalefac_comp - 180 & 0x3; this.new_slen[3] = 0; ((this.si.ch[ch]).gr[gr]).preflag = 0; blocknumber = 4; } else if (int_scalefac_comp < 255) { this.new_slen[0] = (int_scalefac_comp - 244) / 3; this.new_slen[1] = (int_scalefac_comp - 244) % 3; this.new_slen[2] = 0; this.new_slen[3] = 0; ((this.si.ch[ch]).gr[gr]).preflag = 0; blocknumber = 5; }  }  for (int x = 0; x < 45; x++) this.scalefac_buffer[x] = 0;  int m = 0; for (int i = 0; i < 4; i++) { for (int j = 0; j < nr_of_sfb_block[blocknumber][blocktypenumber][i]; j++) { this.scalefac_buffer[m] = (this.new_slen[i] == 0) ? 0 : this.br.hgetbits(this.new_slen[i]); m++; }  }  }
/*      */   private void get_LSF_scale_factors(int ch, int gr) { int m = 0; gr_info_s gr_info = (this.si.ch[ch]).gr[gr]; get_LSF_scale_data(ch, gr); if (gr_info.window_switching_flag != 0 && gr_info.block_type == 2) { if (gr_info.mixed_block_flag != 0) { int sfb; for (sfb = 0; sfb < 8; sfb++) { (this.scalefac[ch]).l[sfb] = this.scalefac_buffer[m]; m++; }  for (sfb = 3; sfb < 12; sfb++) { for (int i = 0; i < 3; i++) { (this.scalefac[ch]).s[i][sfb] = this.scalefac_buffer[m]; m++; }  }  for (int window = 0; window < 3; window++) (this.scalefac[ch]).s[window][12] = 0;  } else { for (int sfb = 0; sfb < 12; sfb++) { for (int i = 0; i < 3; i++) { (this.scalefac[ch]).s[i][sfb] = this.scalefac_buffer[m]; m++; }  }  for (int window = 0; window < 3; window++) (this.scalefac[ch]).s[window][12] = 0;  }  } else { for (int sfb = 0; sfb < 21; sfb++) { (this.scalefac[ch]).l[sfb] = this.scalefac_buffer[m]; m++; }  (this.scalefac[ch]).l[21] = 0; (this.scalefac[ch]).l[22] = 0; }  }
/*      */   private void huffman_decode(int ch, int gr) { int region1Start, region2Start; this.x[0] = 0; this.y[0] = 0; this.v[0] = 0; this.w[0] = 0; int part2_3_end = this.part2_start + ((this.si.ch[ch]).gr[gr]).part2_3_length; if (((this.si.ch[ch]).gr[gr]).window_switching_flag != 0 && ((this.si.ch[ch]).gr[gr]).block_type == 2) { region1Start = (this.sfreq == 8) ? 72 : 36; region2Start = 576; } else { int buf = ((this.si.ch[ch]).gr[gr]).region0_count + 1; int buf1 = buf + ((this.si.ch[ch]).gr[gr]).region1_count + 1; if (buf1 > (this.sfBandIndex[this.sfreq]).l.length - 1) buf1 = (this.sfBandIndex[this.sfreq]).l.length - 1;  region1Start = (this.sfBandIndex[this.sfreq]).l[buf]; region2Start = (this.sfBandIndex[this.sfreq]).l[buf1]; }  int index = 0; for (int i = 0; i < ((this.si.ch[ch]).gr[gr]).big_values << 1; i += 2) { huffcodetab huffcodetab; if (i < region1Start) { huffcodetab = huffcodetab.ht[((this.si.ch[ch]).gr[gr]).table_select[0]]; } else if (i < region2Start) { huffcodetab = huffcodetab.ht[((this.si.ch[ch]).gr[gr]).table_select[1]]; } else { huffcodetab = huffcodetab.ht[((this.si.ch[ch]).gr[gr]).table_select[2]]; }  huffcodetab.huffman_decoder(huffcodetab, this.x, this.y, this.v, this.w, this.br); this.is_1d[index++] = this.x[0]; this.is_1d[index++] = this.y[0]; this.CheckSumHuff = this.CheckSumHuff + this.x[0] + this.y[0]; }  huffcodetab h = huffcodetab.ht[((this.si.ch[ch]).gr[gr]).count1table_select + 32]; int num_bits = this.br.hsstell(); while (num_bits < part2_3_end && index < 576) { huffcodetab.huffman_decoder(h, this.x, this.y, this.v, this.w, this.br); this.is_1d[index++] = this.v[0]; this.is_1d[index++] = this.w[0]; this.is_1d[index++] = this.x[0]; this.is_1d[index++] = this.y[0]; this.CheckSumHuff = this.CheckSumHuff + this.v[0] + this.w[0] + this.x[0] + this.y[0]; num_bits = this.br.hsstell(); }  if (num_bits > part2_3_end) { this.br.rewindNbits(num_bits - part2_3_end); index -= 4; }  num_bits = this.br.hsstell(); if (num_bits < part2_3_end) this.br.hgetbits(part2_3_end - num_bits);  if (index < 576) { this.nonzero[ch] = index; } else { this.nonzero[ch] = 576; }  if (index < 0) index = 0;  for (; index < 576; index++) this.is_1d[index] = 0;  }
/*      */   private void i_stereo_k_values(int is_pos, int io_type, int i) { if (is_pos == 0) { this.k[0][i] = 1.0F; this.k[1][i] = 1.0F; } else if ((is_pos & 0x1) != 0) { this.k[0][i] = io[io_type][is_pos + 1 >>> 1]; this.k[1][i] = 1.0F; } else { this.k[0][i] = 1.0F; this.k[1][i] = io[io_type][is_pos >>> 1]; }  }
/*      */   private void dequantize_sample(float[][] xr, int ch, int gr) { int next_cb_boundary; gr_info_s gr_info = (this.si.ch[ch]).gr[gr]; int cb = 0; int cb_begin = 0; int cb_width = 0; int index = 0; float[][] xr_1d = xr; if (gr_info.window_switching_flag != 0 && gr_info.block_type == 2) { if (gr_info.mixed_block_flag != 0) { next_cb_boundary = (this.sfBandIndex[this.sfreq]).l[1]; } else { cb_width = (this.sfBandIndex[this.sfreq]).s[1]; next_cb_boundary = (cb_width << 2) - cb_width; cb_begin = 0; }  } else { next_cb_boundary = (this.sfBandIndex[this.sfreq]).l[1]; }  float g_gain = (float)Math.pow(2.0D, 0.25D * (gr_info.global_gain - 210.0D)); int j; for (j = 0; j < this.nonzero[ch]; j++) { int reste = j % 18; int quotien = (j - reste) / 18; if (this.is_1d[j] == 0) { xr_1d[quotien][reste] = 0.0F; } else { int abv = this.is_1d[j]; if (abv < t_43.length) { if (this.is_1d[j] > 0) { xr_1d[quotien][reste] = g_gain * t_43[abv]; } else if (-abv < t_43.length) { xr_1d[quotien][reste] = -g_gain * t_43[-abv]; } else { xr_1d[quotien][reste] = -g_gain * (float)Math.pow(-abv, 1.3333333333333333D); }  } else if (this.is_1d[j] > 0) { xr_1d[quotien][reste] = g_gain * (float)Math.pow(abv, 1.3333333333333333D); } else { xr_1d[quotien][reste] = -g_gain * (float)Math.pow(-abv, 1.3333333333333333D); }  }  }  for (j = 0; j < this.nonzero[ch]; j++) { int reste = j % 18; int quotien = (j - reste) / 18; if (index == next_cb_boundary) if (gr_info.window_switching_flag != 0 && gr_info.block_type == 2) { if (gr_info.mixed_block_flag != 0) { if (index == (this.sfBandIndex[this.sfreq]).l[8]) { next_cb_boundary = (this.sfBandIndex[this.sfreq]).s[4]; next_cb_boundary = (next_cb_boundary << 2) - next_cb_boundary; cb = 3; cb_width = (this.sfBandIndex[this.sfreq]).s[4] - (this.sfBandIndex[this.sfreq]).s[3]; cb_begin = (this.sfBandIndex[this.sfreq]).s[3]; cb_begin = (cb_begin << 2) - cb_begin; } else if (index < (this.sfBandIndex[this.sfreq]).l[8]) { next_cb_boundary = (this.sfBandIndex[this.sfreq]).l[++cb + 1]; } else { next_cb_boundary = (this.sfBandIndex[this.sfreq]).s[++cb + 1]; next_cb_boundary = (next_cb_boundary << 2) - next_cb_boundary; cb_begin = (this.sfBandIndex[this.sfreq]).s[cb]; cb_width = (this.sfBandIndex[this.sfreq]).s[cb + 1] - cb_begin; cb_begin = (cb_begin << 2) - cb_begin; }  } else { next_cb_boundary = (this.sfBandIndex[this.sfreq]).s[++cb + 1]; next_cb_boundary = (next_cb_boundary << 2) - next_cb_boundary; cb_begin = (this.sfBandIndex[this.sfreq]).s[cb]; cb_width = (this.sfBandIndex[this.sfreq]).s[cb + 1] - cb_begin; cb_begin = (cb_begin << 2) - cb_begin; }  } else { next_cb_boundary = (this.sfBandIndex[this.sfreq]).l[++cb + 1]; }   if (gr_info.window_switching_flag != 0 && ((gr_info.block_type == 2 && gr_info.mixed_block_flag == 0) || (gr_info.block_type == 2 && gr_info.mixed_block_flag != 0 && j >= 36))) { int t_index = (index - cb_begin) / cb_width; int idx = (this.scalefac[ch]).s[t_index][cb] << gr_info.scalefac_scale; idx += gr_info.subblock_gain[t_index] << 2; xr_1d[quotien][reste] = xr_1d[quotien][reste] * two_to_negative_half_pow[idx]; } else { int idx = (this.scalefac[ch]).l[cb]; if (gr_info.preflag != 0) idx += pretab[cb];  idx <<= gr_info.scalefac_scale; xr_1d[quotien][reste] = xr_1d[quotien][reste] * two_to_negative_half_pow[idx]; }  index++; }  for (j = this.nonzero[ch]; j < 576; j++) { int reste = j % 18; int quotien = (j - reste) / 18; if (reste < 0) reste = 0;  if (quotien < 0) quotien = 0;  xr_1d[quotien][reste] = 0.0F; }  }
/*      */   private void reorder(float[][] xr, int ch, int gr) { gr_info_s gr_info = (this.si.ch[ch]).gr[gr]; float[][] xr_1d = xr; if (gr_info.window_switching_flag != 0 && gr_info.block_type == 2) { int index; for (index = 0; index < 576; index++) this.out_1d[index] = 0.0F;  if (gr_info.mixed_block_flag != 0) { for (index = 0; index < 36; index++) { int reste = index % 18; int quotien = (index - reste) / 18; this.out_1d[index] = xr_1d[quotien][reste]; }  for (int sfb = 3; sfb < 13; sfb++) { int sfb_start = (this.sfBandIndex[this.sfreq]).s[sfb]; int sfb_lines = (this.sfBandIndex[this.sfreq]).s[sfb + 1] - sfb_start; int sfb_start3 = (sfb_start << 2) - sfb_start; for (int freq = 0, freq3 = 0; freq < sfb_lines; freq++, freq3 += 3) { int src_line = sfb_start3 + freq; int des_line = sfb_start3 + freq3; int reste = src_line % 18; int quotien = (src_line - reste) / 18; this.out_1d[des_line] = xr_1d[quotien][reste]; src_line += sfb_lines; des_line++; reste = src_line % 18; quotien = (src_line - reste) / 18; this.out_1d[des_line] = xr_1d[quotien][reste]; src_line += sfb_lines; des_line++; reste = src_line % 18; quotien = (src_line - reste) / 18; this.out_1d[des_line] = xr_1d[quotien][reste]; }  }  } else { for (index = 0; index < 576; index++) { int j = reorder_table[this.sfreq][index]; int reste = j % 18; int quotien = (j - reste) / 18; this.out_1d[index] = xr_1d[quotien][reste]; }  }  } else { for (int index = 0; index < 576; index++) { int reste = index % 18; int quotien = (index - reste) / 18; this.out_1d[index] = xr_1d[quotien][reste]; }  }  }
/*      */   private void stereo(int gr) { if (this.channels == 1) { for (int sb = 0; sb < 32; sb++) { for (int ss = 0; ss < 18; ss += 3) { this.lr[0][sb][ss] = this.ro[0][sb][ss]; this.lr[0][sb][ss + 1] = this.ro[0][sb][ss + 1]; this.lr[0][sb][ss + 2] = this.ro[0][sb][ss + 2]; }  }  } else { gr_info_s gr_info = (this.si.ch[0]).gr[gr]; int mode_ext = this.header.mode_extension(); boolean ms_stereo = (this.header.mode() == 1 && (mode_ext & 0x2) != 0); boolean i_stereo = (this.header.mode() == 1 && (mode_ext & 0x1) != 0); boolean lsf = !(this.header.version() != 0 && this.header.version() != 2); int io_type = gr_info.scalefac_compress & 0x1; int i; for (i = 0; i < 576; i++) { this.is_pos[i] = 7; this.is_ratio[i] = 0.0F; }  if (i_stereo) if (gr_info.window_switching_flag != 0 && gr_info.block_type == 2) { if (gr_info.mixed_block_flag != 0) { int max_sfb = 0; for (int j = 0; j < 3; j++) { int sfbcnt = 2; int sfb; for (sfb = 12; sfb >= 3; sfb--) { i = (this.sfBandIndex[this.sfreq]).s[sfb]; int lines = (this.sfBandIndex[this.sfreq]).s[sfb + 1] - i; i = (i << 2) - i + (j + 1) * lines - 1; while (lines > 0) { if (this.ro[1][i / 18][i % 18] != 0.0F) { sfbcnt = sfb; sfb = -10; lines = -10; }  lines--; i--; }  }  sfb = sfbcnt + 1; if (sfb > max_sfb) max_sfb = sfb;  while (sfb < 12) { int n = (this.sfBandIndex[this.sfreq]).s[sfb]; int m = (this.sfBandIndex[this.sfreq]).s[sfb + 1] - n; i = (n << 2) - n + j * m; for (; m > 0; m--) { this.is_pos[i] = (this.scalefac[1]).s[j][sfb]; if (this.is_pos[i] != 7) if (lsf) { i_stereo_k_values(this.is_pos[i], io_type, i); } else { this.is_ratio[i] = TAN12[this.is_pos[i]]; }   i++; }  sfb++; }  sfb = (this.sfBandIndex[this.sfreq]).s[10]; int k = (this.sfBandIndex[this.sfreq]).s[11] - sfb; sfb = (sfb << 2) - sfb + j * k; int temp = (this.sfBandIndex[this.sfreq]).s[11]; k = (this.sfBandIndex[this.sfreq]).s[12] - temp; i = (temp << 2) - temp + j * k; for (; k > 0; k--) { this.is_pos[i] = this.is_pos[sfb]; if (lsf) { this.k[0][i] = this.k[0][sfb]; this.k[1][i] = this.k[1][sfb]; } else { this.is_ratio[i] = this.is_ratio[sfb]; }  i++; }  }  if (max_sfb <= 3) { i = 2; int ss = 17; int k = -1; while (i >= 0) { if (this.ro[1][i][ss] != 0.0F) { k = (i << 4) + (i << 1) + ss; i = -1; continue; }  ss--; if (ss < 0) { i--; ss = 17; }  }  i = 0; while ((this.sfBandIndex[this.sfreq]).l[i] <= k) i++;  int sfb = i; i = (this.sfBandIndex[this.sfreq]).l[i]; for (; sfb < 8; sfb++) { k = (this.sfBandIndex[this.sfreq]).l[sfb + 1] - (this.sfBandIndex[this.sfreq]).l[sfb]; for (; k > 0; k--) { this.is_pos[i] = (this.scalefac[1]).l[sfb]; if (this.is_pos[i] != 7) if (lsf) { i_stereo_k_values(this.is_pos[i], io_type, i); } else { this.is_ratio[i] = TAN12[this.is_pos[i]]; }   i++; }  }  }  } else { for (int j = 0; j < 3; j++) { int sfbcnt = -1; int sfb; for (sfb = 12; sfb >= 0; sfb--) { int m = (this.sfBandIndex[this.sfreq]).s[sfb]; int lines = (this.sfBandIndex[this.sfreq]).s[sfb + 1] - m; i = (m << 2) - m + (j + 1) * lines - 1; while (lines > 0) { if (this.ro[1][i / 18][i % 18] != 0.0F) { sfbcnt = sfb; sfb = -10; lines = -10; }  lines--; i--; }  }  sfb = sfbcnt + 1; while (sfb < 12) { int n = (this.sfBandIndex[this.sfreq]).s[sfb]; int m = (this.sfBandIndex[this.sfreq]).s[sfb + 1] - n; i = (n << 2) - n + j * m; for (; m > 0; m--) { this.is_pos[i] = (this.scalefac[1]).s[j][sfb]; if (this.is_pos[i] != 7) if (lsf) { i_stereo_k_values(this.is_pos[i], io_type, i); } else { this.is_ratio[i] = TAN12[this.is_pos[i]]; }   i++; }  sfb++; }  int temp = (this.sfBandIndex[this.sfreq]).s[10]; int temp2 = (this.sfBandIndex[this.sfreq]).s[11]; int k = temp2 - temp; sfb = (temp << 2) - temp + j * k; k = (this.sfBandIndex[this.sfreq]).s[12] - temp2; i = (temp2 << 2) - temp2 + j * k; for (; k > 0; k--) { this.is_pos[i] = this.is_pos[sfb]; if (lsf) { this.k[0][i] = this.k[0][sfb]; this.k[1][i] = this.k[1][sfb]; } else { this.is_ratio[i] = this.is_ratio[sfb]; }  i++; }  }  }  } else { i = 31; int ss = 17; int j = 0; while (i >= 0) { if (this.ro[1][i][ss] != 0.0F) { j = (i << 4) + (i << 1) + ss; i = -1; continue; }  ss--; if (ss < 0) { i--; ss = 17; }  }  i = 0; while ((this.sfBandIndex[this.sfreq]).l[i] <= j) i++;  int sfb = i; i = (this.sfBandIndex[this.sfreq]).l[i]; for (; sfb < 21; sfb++) { j = (this.sfBandIndex[this.sfreq]).l[sfb + 1] - (this.sfBandIndex[this.sfreq]).l[sfb]; for (; j > 0; j--) { this.is_pos[i] = (this.scalefac[1]).l[sfb]; if (this.is_pos[i] != 7) if (lsf) { i_stereo_k_values(this.is_pos[i], io_type, i); } else { this.is_ratio[i] = TAN12[this.is_pos[i]]; }   i++; }  }  sfb = (this.sfBandIndex[this.sfreq]).l[20]; for (j = 576 - (this.sfBandIndex[this.sfreq]).l[21]; j > 0 && i < 576; j--) { this.is_pos[i] = this.is_pos[sfb]; if (lsf) { this.k[0][i] = this.k[0][sfb]; this.k[1][i] = this.k[1][sfb]; } else { this.is_ratio[i] = this.is_ratio[sfb]; }  i++; }  }   i = 0; for (int sb = 0; sb < 32; sb++) { for (int ss = 0; ss < 18; ss++) { if (this.is_pos[i] == 7) { if (ms_stereo) { this.lr[0][sb][ss] = (this.ro[0][sb][ss] + this.ro[1][sb][ss]) * 0.70710677F; this.lr[1][sb][ss] = (this.ro[0][sb][ss] - this.ro[1][sb][ss]) * 0.70710677F; } else { this.lr[0][sb][ss] = this.ro[0][sb][ss]; this.lr[1][sb][ss] = this.ro[1][sb][ss]; }  } else if (i_stereo) { if (lsf) { this.lr[0][sb][ss] = this.ro[0][sb][ss] * this.k[0][i]; this.lr[1][sb][ss] = this.ro[0][sb][ss] * this.k[1][i]; } else { this.lr[1][sb][ss] = this.ro[0][sb][ss] / (1.0F + this.is_ratio[i]); this.lr[0][sb][ss] = this.lr[1][sb][ss] * this.is_ratio[i]; }  }  i++; }  }  }  }
/*      */   private void antialias(int ch, int gr) { int sb18lim; gr_info_s gr_info = (this.si.ch[ch]).gr[gr]; if (gr_info.window_switching_flag != 0 && gr_info.block_type == 2 && gr_info.mixed_block_flag == 0) return;  if (gr_info.window_switching_flag != 0 && gr_info.mixed_block_flag != 0 && gr_info.block_type == 2) { sb18lim = 18; } else { sb18lim = 558; }  for (int sb18 = 0; sb18 < sb18lim; sb18 += 18) { for (int ss = 0; ss < 8; ss++) { int src_idx1 = sb18 + 17 - ss; int src_idx2 = sb18 + 18 + ss; float bu = this.out_1d[src_idx1]; float bd = this.out_1d[src_idx2]; this.out_1d[src_idx1] = bu * cs[ss] - bd * ca[ss]; this.out_1d[src_idx2] = bd * cs[ss] + bu * ca[ss]; }  }  } private void hybrid(int ch, int gr) { gr_info_s gr_info = (this.si.ch[ch]).gr[gr]; for (int sb18 = 0; sb18 < 576; sb18 += 18) { int bt = (gr_info.window_switching_flag != 0 && gr_info.mixed_block_flag != 0 && sb18 < 36) ? 0 : gr_info.block_type; float[] tsOut = this.out_1d; int cc; for (cc = 0; cc < 18; cc++) this.tsOutCopy[cc] = tsOut[cc + sb18];  inv_mdct(this.tsOutCopy, this.rawout, bt); for (cc = 0; cc < 18; cc++) tsOut[cc + sb18] = this.tsOutCopy[cc];  float[][] prvblk = this.prevblck; tsOut[0 + sb18] = this.rawout[0] + prvblk[ch][sb18 + 0]; prvblk[ch][sb18 + 0] = this.rawout[18]; tsOut[1 + sb18] = this.rawout[1] + prvblk[ch][sb18 + 1]; prvblk[ch][sb18 + 1] = this.rawout[19]; tsOut[2 + sb18] = this.rawout[2] + prvblk[ch][sb18 + 2]; prvblk[ch][sb18 + 2] = this.rawout[20]; tsOut[3 + sb18] = this.rawout[3] + prvblk[ch][sb18 + 3]; prvblk[ch][sb18 + 3] = this.rawout[21]; tsOut[4 + sb18] = this.rawout[4] + prvblk[ch][sb18 + 4]; prvblk[ch][sb18 + 4] = this.rawout[22]; tsOut[5 + sb18] = this.rawout[5] + prvblk[ch][sb18 + 5]; prvblk[ch][sb18 + 5] = this.rawout[23]; tsOut[6 + sb18] = this.rawout[6] + prvblk[ch][sb18 + 6]; prvblk[ch][sb18 + 6] = this.rawout[24]; tsOut[7 + sb18] = this.rawout[7] + prvblk[ch][sb18 + 7]; prvblk[ch][sb18 + 7] = this.rawout[25]; tsOut[8 + sb18] = this.rawout[8] + prvblk[ch][sb18 + 8]; prvblk[ch][sb18 + 8] = this.rawout[26]; tsOut[9 + sb18] = this.rawout[9] + prvblk[ch][sb18 + 9]; prvblk[ch][sb18 + 9] = this.rawout[27]; tsOut[10 + sb18] = this.rawout[10] + prvblk[ch][sb18 + 10]; prvblk[ch][sb18 + 10] = this.rawout[28]; tsOut[11 + sb18] = this.rawout[11] + prvblk[ch][sb18 + 11]; prvblk[ch][sb18 + 11] = this.rawout[29]; tsOut[12 + sb18] = this.rawout[12] + prvblk[ch][sb18 + 12]; prvblk[ch][sb18 + 12] = this.rawout[30]; tsOut[13 + sb18] = this.rawout[13] + prvblk[ch][sb18 + 13]; prvblk[ch][sb18 + 13] = this.rawout[31]; tsOut[14 + sb18] = this.rawout[14] + prvblk[ch][sb18 + 14]; prvblk[ch][sb18 + 14] = this.rawout[32]; tsOut[15 + sb18] = this.rawout[15] + prvblk[ch][sb18 + 15]; prvblk[ch][sb18 + 15] = this.rawout[33]; tsOut[16 + sb18] = this.rawout[16] + prvblk[ch][sb18 + 16]; prvblk[ch][sb18 + 16] = this.rawout[34]; tsOut[17 + sb18] = this.rawout[17] + prvblk[ch][sb18 + 17]; prvblk[ch][sb18 + 17] = this.rawout[35]; }  } private void do_downmix() { for (int sb = 0; sb < 18; sb++) { for (int ss = 0; ss < 18; ss += 3) { this.lr[0][sb][ss] = (this.lr[0][sb][ss] + this.lr[1][sb][ss]) * 0.5F; this.lr[0][sb][ss + 1] = (this.lr[0][sb][ss + 1] + this.lr[1][sb][ss + 1]) * 0.5F; this.lr[0][sb][ss + 2] = (this.lr[0][sb][ss + 2] + this.lr[1][sb][ss + 2]) * 0.5F; }  }  } public void inv_mdct(float[] in, float[] out, int block_type) { float tmpf_17 = 0.0F, tmpf_16 = tmpf_17, tmpf_15 = tmpf_16, tmpf_14 = tmpf_15, tmpf_13 = tmpf_14, tmpf_12 = tmpf_13, tmpf_11 = tmpf_12, tmpf_10 = tmpf_11; float tmpf_9 = tmpf_10, tmpf_8 = tmpf_9, tmpf_7 = tmpf_8, tmpf_6 = tmpf_7, tmpf_5 = tmpf_6, tmpf_4 = tmpf_5, tmpf_3 = tmpf_4, tmpf_2 = tmpf_3, tmpf_1 = tmpf_2, tmpf_0 = tmpf_1; if (block_type == 2) { out[0] = 0.0F; out[1] = 0.0F; out[2] = 0.0F; out[3] = 0.0F; out[4] = 0.0F; out[5] = 0.0F; out[6] = 0.0F; out[7] = 0.0F; out[8] = 0.0F; out[9] = 0.0F; out[10] = 0.0F; out[11] = 0.0F; out[12] = 0.0F; out[13] = 0.0F; out[14] = 0.0F; out[15] = 0.0F; out[16] = 0.0F; out[17] = 0.0F; out[18] = 0.0F; out[19] = 0.0F; out[20] = 0.0F; out[21] = 0.0F; out[22] = 0.0F; out[23] = 0.0F; out[24] = 0.0F; out[25] = 0.0F; out[26] = 0.0F; out[27] = 0.0F; out[28] = 0.0F; out[29] = 0.0F; out[30] = 0.0F; out[31] = 0.0F; out[32] = 0.0F; out[33] = 0.0F; out[34] = 0.0F; out[35] = 0.0F; int six_i = 0; for (int i = 0; i < 3; i++) { in[15 + i] = in[15 + i] + in[12 + i]; in[12 + i] = in[12 + i] + in[9 + i]; in[9 + i] = in[9 + i] + in[6 + i]; in[6 + i] = in[6 + i] + in[3 + i]; in[3 + i] = in[3 + i] + in[0 + i]; in[15 + i] = in[15 + i] + in[9 + i]; in[9 + i] = in[9 + i] + in[3 + i]; float pp2 = in[12 + i] * 0.5F; float pp1 = in[6 + i] * 0.8660254F; float sum = in[0 + i] + pp2; tmpf_1 = in[0 + i] - in[12 + i]; tmpf_0 = sum + pp1; tmpf_2 = sum - pp1; pp2 = in[15 + i] * 0.5F; pp1 = in[9 + i] * 0.8660254F; sum = in[3 + i] + pp2; tmpf_4 = in[3 + i] - in[15 + i]; tmpf_5 = sum + pp1; tmpf_3 = sum - pp1; tmpf_3 *= 1.9318516F; tmpf_4 *= 0.70710677F; tmpf_5 *= 0.5176381F; float save = tmpf_0; tmpf_0 += tmpf_5; tmpf_5 = save - tmpf_5; save = tmpf_1; tmpf_1 += tmpf_4; tmpf_4 = save - tmpf_4; save = tmpf_2; tmpf_2 += tmpf_3; tmpf_3 = save - tmpf_3; tmpf_0 *= 0.5043145F; tmpf_1 *= 0.5411961F; tmpf_2 *= 0.6302362F; tmpf_3 *= 0.8213398F; tmpf_4 *= 1.306563F; tmpf_5 *= 3.830649F; tmpf_8 = -tmpf_0 * 0.7933533F; tmpf_9 = -tmpf_0 * 0.6087614F; tmpf_7 = -tmpf_1 * 0.9238795F; tmpf_10 = -tmpf_1 * 0.38268343F; tmpf_6 = -tmpf_2 * 0.9914449F; tmpf_11 = -tmpf_2 * 0.13052619F; tmpf_0 = tmpf_3; tmpf_1 = tmpf_4 * 0.38268343F; tmpf_2 = tmpf_5 * 0.6087614F; tmpf_3 = -tmpf_5 * 0.7933533F; tmpf_4 = -tmpf_4 * 0.9238795F; tmpf_5 = -tmpf_0 * 0.9914449F; tmpf_0 *= 0.13052619F; out[six_i + 6] = out[six_i + 6] + tmpf_0; out[six_i + 7] = out[six_i + 7] + tmpf_1; out[six_i + 8] = out[six_i + 8] + tmpf_2; out[six_i + 9] = out[six_i + 9] + tmpf_3; out[six_i + 10] = out[six_i + 10] + tmpf_4; out[six_i + 11] = out[six_i + 11] + tmpf_5; out[six_i + 12] = out[six_i + 12] + tmpf_6; out[six_i + 13] = out[six_i + 13] + tmpf_7; out[six_i + 14] = out[six_i + 14] + tmpf_8; out[six_i + 15] = out[six_i + 15] + tmpf_9; out[six_i + 16] = out[six_i + 16] + tmpf_10; out[six_i + 17] = out[six_i + 17] + tmpf_11; six_i += 6; }  } else { in[17] = in[17] + in[16]; in[16] = in[16] + in[15]; in[15] = in[15] + in[14]; in[14] = in[14] + in[13]; in[13] = in[13] + in[12]; in[12] = in[12] + in[11]; in[11] = in[11] + in[10]; in[10] = in[10] + in[9]; in[9] = in[9] + in[8]; in[8] = in[8] + in[7]; in[7] = in[7] + in[6]; in[6] = in[6] + in[5]; in[5] = in[5] + in[4]; in[4] = in[4] + in[3]; in[3] = in[3] + in[2]; in[2] = in[2] + in[1]; in[1] = in[1] + in[0]; in[17] = in[17] + in[15]; in[15] = in[15] + in[13]; in[13] = in[13] + in[11]; in[11] = in[11] + in[9]; in[9] = in[9] + in[7]; in[7] = in[7] + in[5]; in[5] = in[5] + in[3]; in[3] = in[3] + in[1]; float i00 = in[0] + in[0]; float iip12 = i00 + in[12]; float tmp0 = iip12 + in[4] * 1.8793852F + in[8] * 1.5320889F + in[16] * 0.34729636F; float tmp1 = i00 + in[4] - in[8] - in[12] - in[12] - in[16]; float tmp2 = iip12 - in[4] * 0.34729636F - in[8] * 1.8793852F + in[16] * 1.5320889F; float tmp3 = iip12 - in[4] * 1.5320889F + in[8] * 0.34729636F - in[16] * 1.8793852F; float tmp4 = in[0] - in[4] + in[8] - in[12] + in[16]; float i66_ = in[6] * 1.7320508F; float tmp0_ = in[2] * 1.9696155F + i66_ + in[10] * 1.2855753F + in[14] * 0.6840403F; float tmp1_ = (in[2] - in[10] - in[14]) * 1.7320508F; float tmp2_ = in[2] * 1.2855753F - i66_ - in[10] * 0.6840403F + in[14] * 1.9696155F; float tmp3_ = in[2] * 0.6840403F - i66_ + in[10] * 1.9696155F - in[14] * 1.2855753F; float i0 = in[1] + in[1]; float i0p12 = i0 + in[13]; float tmp0o = i0p12 + in[5] * 1.8793852F + in[9] * 1.5320889F + in[17] * 0.34729636F; float tmp1o = i0 + in[5] - in[9] - in[13] - in[13] - in[17]; float tmp2o = i0p12 - in[5] * 0.34729636F - in[9] * 1.8793852F + in[17] * 1.5320889F; float tmp3o = i0p12 - in[5] * 1.5320889F + in[9] * 0.34729636F - in[17] * 1.8793852F; float tmp4o = (in[1] - in[5] + in[9] - in[13] + in[17]) * 0.70710677F; float i6_ = in[7] * 1.7320508F; float tmp0_o = in[3] * 1.9696155F + i6_ + in[11] * 1.2855753F + in[15] * 0.6840403F; float tmp1_o = (in[3] - in[11] - in[15]) * 1.7320508F; float tmp2_o = in[3] * 1.2855753F - i6_ - in[11] * 0.6840403F + in[15] * 1.9696155F; float tmp3_o = in[3] * 0.6840403F - i6_ + in[11] * 1.9696155F - in[15] * 1.2855753F; float e = tmp0 + tmp0_, o = (tmp0o + tmp0_o) * 0.5019099F; tmpf_0 = e + o; tmpf_17 = e - o; e = tmp1 + tmp1_; o = (tmp1o + tmp1_o) * 0.5176381F; tmpf_1 = e + o; tmpf_16 = e - o; e = tmp2 + tmp2_; o = (tmp2o + tmp2_o) * 0.55168897F; tmpf_2 = e + o; tmpf_15 = e - o; e = tmp3 + tmp3_; o = (tmp3o + tmp3_o) * 0.61038727F; tmpf_3 = e + o; tmpf_14 = e - o; tmpf_4 = tmp4 + tmp4o; tmpf_13 = tmp4 - tmp4o; e = tmp3 - tmp3_; o = (tmp3o - tmp3_o) * 0.8717234F; tmpf_5 = e + o; tmpf_12 = e - o; e = tmp2 - tmp2_; o = (tmp2o - tmp2_o) * 1.1831008F; tmpf_6 = e + o; tmpf_11 = e - o; e = tmp1 - tmp1_; o = (tmp1o - tmp1_o) * 1.9318516F; tmpf_7 = e + o; tmpf_10 = e - o; e = tmp0 - tmp0_; o = (tmp0o - tmp0_o) * 5.7368565F; tmpf_8 = e + o; tmpf_9 = e - o; float[] win_bt = win[block_type]; out[0] = -tmpf_9 * win_bt[0]; out[1] = -tmpf_10 * win_bt[1]; out[2] = -tmpf_11 * win_bt[2]; out[3] = -tmpf_12 * win_bt[3]; out[4] = -tmpf_13 * win_bt[4]; out[5] = -tmpf_14 * win_bt[5]; out[6] = -tmpf_15 * win_bt[6]; out[7] = -tmpf_16 * win_bt[7]; out[8] = -tmpf_17 * win_bt[8]; out[9] = tmpf_17 * win_bt[9]; out[10] = tmpf_16 * win_bt[10]; out[11] = tmpf_15 * win_bt[11]; out[12] = tmpf_14 * win_bt[12]; out[13] = tmpf_13 * win_bt[13]; out[14] = tmpf_12 * win_bt[14]; out[15] = tmpf_11 * win_bt[15]; out[16] = tmpf_10 * win_bt[16]; out[17] = tmpf_9 * win_bt[17]; out[18] = tmpf_8 * win_bt[18]; out[19] = tmpf_7 * win_bt[19]; out[20] = tmpf_6 * win_bt[20]; out[21] = tmpf_5 * win_bt[21]; out[22] = tmpf_4 * win_bt[22]; out[23] = tmpf_3 * win_bt[23]; out[24] = tmpf_2 * win_bt[24]; out[25] = tmpf_1 * win_bt[25]; out[26] = tmpf_0 * win_bt[26]; out[27] = tmpf_0 * win_bt[27]; out[28] = tmpf_1 * win_bt[28]; out[29] = tmpf_2 * win_bt[29]; out[30] = tmpf_3 * win_bt[30]; out[31] = tmpf_4 * win_bt[31]; out[32] = tmpf_5 * win_bt[32]; out[33] = tmpf_6 * win_bt[33]; out[34] = tmpf_7 * win_bt[34]; out[35] = tmpf_8 * win_bt[35]; }  } static class SBI
/*      */   {
/* 1834 */     public int[] l; public SBI() { this.l = new int[23];
/* 1835 */       this.s = new int[14]; }
/*      */     
/*      */     public int[] s;
/*      */     public SBI(int[] thel, int[] thes) {
/* 1839 */       this.l = thel;
/* 1840 */       this.s = thes;
/*      */     }
/*      */   }
/*      */   
/*      */   static class gr_info_s
/*      */   {
/* 1846 */     public int part2_3_length = 0;
/* 1847 */     public int big_values = 0;
/* 1848 */     public int global_gain = 0;
/* 1849 */     public int scalefac_compress = 0;
/* 1850 */     public int window_switching_flag = 0;
/* 1851 */     public int block_type = 0;
/* 1852 */     public int mixed_block_flag = 0;
/*      */     public int[] table_select;
/*      */     public int[] subblock_gain;
/* 1855 */     public int region0_count = 0;
/* 1856 */     public int region1_count = 0;
/* 1857 */     public int preflag = 0;
/* 1858 */     public int scalefac_scale = 0;
/* 1859 */     public int count1table_select = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public gr_info_s() {
/* 1866 */       this.table_select = new int[3];
/* 1867 */       this.subblock_gain = new int[3];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class temporaire
/*      */   {
/*      */     public int[] scfsi;
/*      */     
/*      */     public LayerIIIDecoder.gr_info_s[] gr;
/*      */ 
/*      */     
/*      */     public temporaire() {
/* 1881 */       this.scfsi = new int[4];
/* 1882 */       this.gr = new LayerIIIDecoder.gr_info_s[2];
/* 1883 */       this.gr[0] = new LayerIIIDecoder.gr_info_s();
/* 1884 */       this.gr[1] = new LayerIIIDecoder.gr_info_s();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class III_side_info_t
/*      */   {
/* 1891 */     public int main_data_begin = 0;
/* 1892 */     public int private_bits = 0;
/*      */ 
/*      */     
/*      */     public LayerIIIDecoder.temporaire[] ch;
/*      */ 
/*      */     
/*      */     public III_side_info_t() {
/* 1899 */       this.ch = new LayerIIIDecoder.temporaire[2];
/* 1900 */       this.ch[0] = new LayerIIIDecoder.temporaire();
/* 1901 */       this.ch[1] = new LayerIIIDecoder.temporaire();
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
/*      */   static class temporaire2
/*      */   {
/* 1915 */     public int[] l = new int[23];
/* 1916 */     public int[][] s = new int[3][13];
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
/* 1932 */   private static final int[][] slen = new int[][] { { 
/* 1933 */         0, 0, 0, 0, 3, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4 }, { 
/* 1934 */         0, 1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3, 2, 3 } };
/*      */ 
/*      */ 
/*      */   
/* 1938 */   public static final int[] pretab = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 3, 3, 3, 2 };
/*      */ 
/*      */   
/*      */   private SBI[] sfBandIndex;
/*      */   
/* 1943 */   public static final float[] two_to_negative_half_pow = new float[] { 1.0F, 0.70710677F, 0.5F, 0.35355338F, 
/* 1944 */       0.25F, 0.17677669F, 0.125F, 0.088388346F, 
/* 1945 */       0.0625F, 0.044194173F, 0.03125F, 0.022097087F, 
/* 1946 */       0.015625F, 0.011048543F, 0.0078125F, 0.0055242716F, 
/* 1947 */       0.00390625F, 0.0027621358F, 0.001953125F, 0.0013810679F, 
/* 1948 */       9.765625E-4F, 6.9053395E-4F, 4.8828125E-4F, 3.4526698E-4F, 
/* 1949 */       2.4414062E-4F, 1.7263349E-4F, 1.2207031E-4F, 8.6316744E-5F, 
/* 1950 */       6.1035156E-5F, 4.3158372E-5F, 3.0517578E-5F, 2.1579186E-5F, 
/* 1951 */       1.5258789E-5F, 1.0789593E-5F, 7.6293945E-6F, 5.3947965E-6F, 
/* 1952 */       3.8146973E-6F, 2.6973983E-6F, 1.9073486E-6F, 1.3486991E-6F, 
/* 1953 */       9.536743E-7F, 6.7434956E-7F, 4.7683716E-7F, 3.3717478E-7F, 
/* 1954 */       2.3841858E-7F, 1.6858739E-7F, 1.1920929E-7F, 8.4293696E-8F, 
/* 1955 */       5.9604645E-8F, 4.2146848E-8F, 2.9802322E-8F, 2.1073424E-8F, 
/* 1956 */       1.4901161E-8F, 1.0536712E-8F, 7.4505806E-9F, 5.268356E-9F, 
/* 1957 */       3.7252903E-9F, 2.634178E-9F, 1.8626451E-9F, 1.317089E-9F, 
/* 1958 */       9.313226E-10F, 6.585445E-10F, 4.656613E-10F, 3.2927225E-10F };
/*      */ 
/*      */ 
/*      */   
/* 1962 */   public static final float[] t_43 = create_t_43();
/*      */ 
/*      */   
/*      */   private static float[] create_t_43() {
/* 1966 */     float[] t43 = new float[8192];
/* 1967 */     double d43 = 1.3333333333333333D;
/*      */     
/* 1969 */     for (int i = 0; i < 8192; i++)
/*      */     {
/* 1971 */       t43[i] = (float)Math.pow(i, 1.3333333333333333D);
/*      */     }
/* 1973 */     return t43;
/*      */   }
/*      */ 
/*      */   
/* 1977 */   public static final float[][] io = new float[][] { { 
/* 1978 */         1.0F, 0.8408964F, 0.70710677F, 0.59460354F, 
/* 1979 */         0.5F, 0.4204482F, 0.35355338F, 0.29730177F, 
/* 1980 */         0.25F, 0.2102241F, 0.17677669F, 0.14865088F, 
/* 1981 */         0.125F, 0.10511205F, 0.088388346F, 0.07432544F, 
/* 1982 */         0.0625F, 0.052556027F, 0.044194173F, 0.03716272F, 
/* 1983 */         0.03125F, 0.026278013F, 0.022097087F, 0.01858136F, 
/* 1984 */         0.015625F, 0.013139007F, 0.011048543F, 0.00929068F, 
/* 1985 */         0.0078125F, 0.0065695033F, 0.0055242716F, 0.00464534F }, { 
/* 1986 */         1.0F, 0.70710677F, 0.5F, 0.35355338F, 
/* 1987 */         0.25F, 0.17677669F, 0.125F, 0.088388346F, 
/* 1988 */         0.0625F, 0.044194173F, 0.03125F, 0.022097087F, 
/* 1989 */         0.015625F, 0.011048543F, 0.0078125F, 0.0055242716F, 
/* 1990 */         0.00390625F, 0.0027621358F, 0.001953125F, 0.0013810679F, 
/* 1991 */         9.765625E-4F, 6.9053395E-4F, 4.8828125E-4F, 3.4526698E-4F, 
/* 1992 */         2.4414062E-4F, 1.7263349E-4F, 1.2207031E-4F, 8.6316744E-5F, 
/* 1993 */         6.1035156E-5F, 4.3158372E-5F, 3.0517578E-5F, 2.1579186E-5F } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1999 */   public static final float[] TAN12 = new float[] { 
/* 2000 */       0.0F, 0.2679492F, 0.57735026F, 1.0F, 
/* 2001 */       1.7320508F, 3.732051F, 9.9999998E10F, -3.732051F, 
/* 2002 */       -1.7320508F, -1.0F, -0.57735026F, -0.2679492F, 
/* 2003 */       0.0F, 0.2679492F, 0.57735026F, 1.0F };
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
/*      */   private static int[][] reorder_table;
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
/*      */   static int[] reorder(int[] scalefac_band) {
/* 2103 */     int j = 0;
/* 2104 */     int[] ix = new int[576];
/* 2105 */     for (int sfb = 0; sfb < 13; sfb++) {
/* 2106 */       int start = scalefac_band[sfb];
/* 2107 */       int end = scalefac_band[sfb + 1];
/* 2108 */       for (int window = 0; window < 3; window++) {
/* 2109 */         for (int i = start; i < end; i++)
/* 2110 */           ix[3 * i + window] = j++; 
/*      */       } 
/* 2112 */     }  return ix;
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
/* 2337 */   private static final float[] cs = new float[] {
/* 2338 */       0.8574929F, 0.881742F, 0.94962865F, 0.9833146F, 
/* 2339 */       0.9955178F, 0.9991606F, 0.9998992F, 0.99999315F
/*      */     };
/*      */ 
/*      */   
/* 2343 */   private static final float[] ca = new float[] {
/* 2344 */       -0.51449573F, -0.47173196F, -0.31337744F, -0.1819132F, 
/* 2345 */       -0.09457419F, -0.040965583F, -0.014198569F, -0.0036999746F
/*      */     };
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
/* 2365 */   public static final float[][] win = new float[][] { { 
/* 2366 */         -0.016141215F, -0.05360318F, -0.100707136F, -0.16280818F, 
/* 2367 */         -0.5F, -0.38388735F, -0.6206114F, -1.1659756F, 
/* 2368 */         -3.8720753F, -4.225629F, -1.519529F, -0.97416484F, 
/* 2369 */         -0.73744076F, -1.2071068F, -0.5163616F, -0.45426053F, 
/* 2370 */         -0.40715656F, -0.3696946F, -0.3387627F, -0.31242222F, 
/* 2371 */         -0.28939587F, -0.26880082F, -0.5F, -0.23251417F, 
/* 2372 */         -0.21596715F, -0.20004979F, -0.18449493F, -0.16905846F, 
/* 2373 */         -0.15350361F, -0.13758625F, -0.12103922F, -0.20710678F, 
/* 2374 */         -0.084752575F, -0.06415752F, -0.041131172F, -0.014790705F
/*      */       }, { 
/* 2376 */         -0.016141215F, -0.05360318F, -0.100707136F, -0.16280818F, 
/* 2377 */         -0.5F, -0.38388735F, -0.6206114F, -1.1659756F, 
/* 2378 */         -3.8720753F, -4.225629F, -1.519529F, -0.97416484F, 
/* 2379 */         -0.73744076F, -1.2071068F, -0.5163616F, -0.45426053F, 
/* 2380 */         -0.40715656F, -0.3696946F, -0.33908543F, -0.3151181F, 
/* 2381 */         -0.29642227F, -0.28184548F, -0.5411961F, -0.2621323F, 
/* 2382 */         -0.25387916F, -0.2329629F, -0.19852729F, -0.15233535F, 
/* 2383 */         -0.0964964F, -0.03342383F, 0.0F, 0.0F, 
/* 2384 */         0.0F, 0.0F, 0.0F, 0.0F
/*      */       }, { 
/* 2386 */         -0.0483008F, -0.15715657F, -0.28325045F, -0.42953748F, 
/* 2387 */         -1.2071068F, -0.8242648F, -1.1451749F, -1.769529F, 
/* 2388 */         -4.5470223F, -3.489053F, -0.7329629F, -0.15076515F, 
/* 2389 */         0.0F, 0.0F, 0.0F, 0.0F, 
/* 2390 */         0.0F, 0.0F, 0.0F, 0.0F, 
/* 2391 */         0.0F, 0.0F, 0.0F, 0.0F, 
/* 2392 */         0.0F, 0.0F, 0.0F, 0.0F, 
/* 2393 */         0.0F, 0.0F, 0.0F, 0.0F, 
/* 2394 */         0.0F, 0.0F, 0.0F, 0.0F
/*      */       }, { 
/* 2396 */         0.0F, 0.0F, 0.0F, 0.0F, 
/* 2397 */         0.0F, 0.0F, -0.15076514F, -0.7329629F, 
/* 2398 */         -3.489053F, -4.5470223F, -1.769529F, -1.1451749F, 
/* 2399 */         -0.8313774F, -1.306563F, -0.54142016F, -0.46528974F, 
/* 2400 */         -0.4106699F, -0.3700468F, -0.3387627F, -0.31242222F, 
/* 2401 */         -0.28939587F, -0.26880082F, -0.5F, -0.23251417F, 
/* 2402 */         -0.21596715F, -0.20004979F, -0.18449493F, -0.16905846F, 
/* 2403 */         -0.15350361F, -0.13758625F, -0.12103922F, -0.20710678F, 
/* 2404 */         -0.084752575F, -0.06415752F, -0.041131172F, -0.014790705F } };
/*      */   
/*      */   public Sftable sftable;
/*      */ 
/*      */   
/*      */   class Sftable
/*      */   {
/*      */     public int[] l;
/*      */     
/*      */     public int[] s;
/*      */ 
/*      */     
/*      */     public Sftable() {
/* 2417 */       this.l = new int[5];
/* 2418 */       this.s = new int[3];
/*      */     }
/*      */ 
/*      */     
/*      */     public Sftable(int[] thel, int[] thes) {
/* 2423 */       this.l = thel;
/* 2424 */       this.s = thes;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 2431 */   public static final int[][][] nr_of_sfb_block = new int[][][] { { { 6, 5, 5, 5 }, { 9, 9, 9, 9 }, { 6, 9, 9, 9
/* 2432 */         } }, { { 6, 5, 7, 3 }, { 9, 9, 12, 6 }, { 6, 9, 12, 6
/* 2433 */         } }, { { 11, 10 }, { 18, 18 }, { 15, 18
/* 2434 */         } }, { { 7, 7, 7 }, { 12, 12, 12 }, { 6, 15, 12
/* 2435 */         } }, { { 6, 6, 6, 3 }, { 12, 9, 9, 6 }, { 6, 12, 9, 6
/* 2436 */         } }, { { 8, 8, 5 }, { 15, 12, 9 }, { 6, 18, 9 } } };
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\LayerIIIDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */