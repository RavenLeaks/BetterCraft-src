/*    */ package me.nzxter.bettercraft.mods.rcon;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class RconClientCli
/*    */ {
/*    */   private static final int EXIT_CODE_SUCCESS = 0;
/*    */   private static final int EXIT_CODE_INVALID_ARGUMENTS = 1;
/*    */   private static final int EXIT_CODE_AUTH_FAILURE = 2;
/*    */   private static final int DEFAULT_PORT = 25575;
/*    */   private static final String QUIT_COMMAND = "\\quit";
/*    */   
/*    */   public static void main(String[] args) {
/* 17 */     int exitCode = run(args);
/* 18 */     if (exitCode != 0) {
/* 19 */       System.exit(exitCode);
/*    */     }
/*    */   }
/*    */   
/*    */   private static int run(String[] args) {
/* 24 */     if (args.length < 3) {
/* 25 */       return printUsage();
/*    */     }
/*    */     
/* 28 */     String[] hostAndPort = args[0].split(":");
/* 29 */     if (hostAndPort.length > 2) {
/* 30 */       return printUsage();
/*    */     }
/*    */     
/* 33 */     String host = hostAndPort[0];
/* 34 */     int port = (hostAndPort.length == 2) ? Integer.parseInt(hostAndPort[1]) : 25575;
/* 35 */     String password = args[1];
/*    */     
/* 37 */     List<String> commands = new ArrayList<>(Arrays.<String>asList(args).subList(2, args.length));
/*    */     
/* 39 */     boolean terminalMode = commands.contains("-t");
/* 40 */     if (terminalMode && commands.size() != 1) {
/* 41 */       return printUsage();
/*    */     }
/*    */     try {
/* 44 */       Exception exception2, exception1 = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 71 */     catch (AuthFailureException e) {
/* 72 */       System.err.println("Authentication failure");
/* 73 */       return 2;
/*    */     } 
/*    */     
/* 76 */     return 0;
/*    */   }
/*    */   
/*    */   private static int printUsage() {
/* 80 */     System.out.println("Usage: java -jar minecraft-rcon-client-<version>.jar <host[:port]> <password> <-t|commands>");
/* 81 */     System.out.println();
/* 82 */     System.out.println("Example 1: java -jar minecraft-rcon-client-1.0.0.jar localhost:12345 hunter2 'say Hello, world' 'teleport Notch 0 0 0'");
/* 83 */     System.out.println("Example 2: java -jar minecraft-rcon-client-1.0.0.jar localhost:12345 hunter2 -t");
/* 84 */     System.out.println();
/* 85 */     System.out.println("The port can be omitted, the default is 25575.");
/* 86 */     System.out.println("\"-t\" enables terminal mode, to enter commands in an interactive terminal.");
/* 87 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\rcon\RconClientCli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */