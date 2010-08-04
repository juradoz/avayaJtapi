/*     */ package com.avaya.jtapi.tsapi.impl.core;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class Tt
/*     */ {
/*  14 */   private static Logger log = Logger.getLogger(Tt.class);
/*     */   private static boolean m_enabled;
/*     */   private static File m_trigger_file;
/*     */   private static String m_trigger_file_name;
/*     */   private static final String m_TRIGGER_DEFAULT_FILE_NAME = "/tmp/enable_jtapi_tracing.txt";
/*     */ 
/*     */   public static void printlnStatus(PrintStream p)
/*     */   {
/*  34 */     p.println("Avaya JTAPI triggered tracing: " + ((isTriggered()) ? "on" : "off") + " - trigger file: " + ((m_trigger_file_name == null) ? "<null>" : m_trigger_file_name));
/*     */   }
/*     */ 
/*     */   public static boolean isEnabled()
/*     */   {
/*  48 */     return m_enabled;
/*     */   }
/*     */ 
/*     */   public static boolean isTriggered()
/*     */   {
/*  58 */     if (m_trigger_file == null)
/*     */     {
/*  61 */       m_enabled = false;
/*     */     }
/*  65 */     else if (m_trigger_file.exists() == true)
/*     */     {
/*  67 */       m_enabled = true;
/*     */     }
/*     */ 
/*  71 */     return m_enabled;
/*     */   }
/*     */ 
/*     */   public static void println(String s)
/*     */   {
/*  81 */     if (!isTriggered()) {
/*     */       return;
/*     */     }
/*  84 */     log.info(s);
/*     */   }
/*     */ 
/*     */   public static void setTriggerFileName(String trigger_path)
/*     */   {
/*  97 */     if (trigger_path == null)
/*     */     {
/*  99 */       m_trigger_file_name = null;
/* 100 */       m_trigger_file = null;
/*     */     }
/*     */     else
/*     */     {
/* 104 */       m_trigger_file_name = trigger_path;
/* 105 */       m_trigger_file = new File(m_trigger_file_name);
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  20 */     setTriggerFileName(m_trigger_file_name);
/*     */ 
/*  23 */     isTriggered();
/*     */ 
/* 116 */     m_enabled = false;
/*     */ 
/* 123 */     m_trigger_file = null;
/*     */ 
/* 130 */     m_trigger_file_name = "/tmp/enable_jtapi_tracing.txt";
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.Tt
 * JD-Core Version:    0.5.4
 */