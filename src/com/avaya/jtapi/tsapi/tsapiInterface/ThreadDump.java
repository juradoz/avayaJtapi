/*    */ package com.avaya.jtapi.tsapi.tsapiInterface;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class ThreadDump extends Thread
/*    */ {
/* 12 */   private static int counter = 0;
/*    */ 
/* 18 */   private static Logger log = Logger.getLogger(ThreadDump.class);
/*    */ 
/*    */   public ThreadDump()
/*    */   {
/* 15 */     super("JTAPI ThreadDump thread#" + ++counter);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 27 */     dumpJavaThreadsByAPI();
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try {
/* 33 */       dumpJavaThreadsByAPI();
/*    */     }
/*    */     catch (Exception ex) {
/* 36 */       log.error("Exception when doing thread dump:" + ex.getMessage(), ex);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void dumpJavaThreadsByAPI()
/*    */   {
/* 43 */     Map map = Thread.getAllStackTraces();
/* 44 */     log.info(Integer.valueOf(map.size()));
/*    */ 
/* 46 */     Set keySet = map.keySet();
/* 47 */     Thread[] threads = new Thread[keySet.size()];
/* 48 */     keySet.toArray(threads);
/*    */ 
/* 50 */     for (int i = 0; i < threads.length; ++i) {
/* 51 */       Thread t = threads[i];
/* 52 */       String daemon = (t.isDaemon()) ? "Daemon" : "";
/* 53 */       String alive = (t.isAlive()) ? "Alive" : "Dead";
/* 54 */       String interrupted = (t.isInterrupted()) ? "Interrupted" : "";
/* 55 */       String heading = t.getName() + ": " + daemon + " prio=" + t.getPriority() + " tid=" + t.getId() + " state=" + t.getState() + " " + alive + " " + interrupted;
/* 56 */       log.info(heading);
/* 57 */       StackTraceElement[] stes = (StackTraceElement[])map.get(t);
/* 58 */       for (int j = 0; j < stes.length; ++j) {
/* 59 */         StackTraceElement ste = stes[j];
/* 60 */         String line = ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
/*    */ 
/* 62 */         log.info("\t at " + line);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void dumpJavaThreadsBySignal()
/*    */   {
/* 69 */     Runtime rt = Runtime.getRuntime();
/*    */     try
/*    */     {
/* 72 */       String[] cmd = { "bash", "-c", "echo $PPID" };
/* 73 */       Process p = rt.exec(cmd);
/*    */ 
/* 75 */       BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
/*    */ 
/* 77 */       String line = null;
/* 78 */       if ((line = br.readLine()) != null) {
/* 79 */         log.info("The parent PID is " + line);
/*    */ 
/* 81 */         p = rt.exec("kill -QUIT " + line);
/* 82 */         br = new BufferedReader(new InputStreamReader(p.getInputStream()));
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 87 */       log.error(e.getMessage(), e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.ThreadDump
 * JD-Core Version:    0.5.4
 */