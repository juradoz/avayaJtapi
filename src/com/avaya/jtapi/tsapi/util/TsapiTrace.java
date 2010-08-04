/*    */ package com.avaya.jtapi.tsapi.util;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class TsapiTrace
/*    */ {
/* 14 */   public static int TRACELEVEL = 1;
/* 15 */   protected static int callDepth = 0;
/*    */ 
/*    */   public static void traceEntry(String str, Object o) {
/* 18 */     if (TRACELEVEL == 0) return;
/* 19 */     if (TRACELEVEL == 2) callDepth += 1;
/* 20 */     printEntering(str + ": ", o);
/*    */   }
/*    */ 
/*    */   private static String print(Object o) {
/* 24 */     if (o != null) {
/* 25 */       if (o instanceof Class) {
/* 26 */         return ((Class)o).getName();
/*    */       }
/*    */       try
/*    */       {
/* 30 */         return o.toString();
/*    */       }
/*    */       catch (Exception e) {
/* 33 */         return o.getClass().getName();
/*    */       }
/*    */     }
/*    */ 
/* 37 */     return "";
/*    */   }
/*    */ 
/*    */   public static void traceExit(String str, Object o) {
/* 41 */     if (TRACELEVEL == 0) return;
/* 42 */     printExiting(str + ": ", o);
/* 43 */     if (TRACELEVEL != 2) return; callDepth -= 1;
/*    */   }
/*    */ 
/*    */   private static void printEntering(String str, Object o) {
/* 47 */     if (o != null) {
/* 48 */       Logger log = Logger.getLogger(o.getClass());
/* 49 */       log.trace(getIndent() + "--> " + str + print(o));
/*    */     }
/*    */   }
/*    */ 
/*    */   private static void printExiting(String str, Object o) {
/* 54 */     if (o != null) {
/* 55 */       Logger log = Logger.getLogger(o.getClass());
/* 56 */       log.trace(getIndent() + "<-- " + str + print(o));
/*    */     }
/*    */   }
/*    */ 
/*    */   private static String getIndent()
/*    */   {
/* 62 */     StringBuffer buffer = new StringBuffer();
/* 63 */     for (int i = 0; i < callDepth; ++i) {
/* 64 */       buffer.append("  ");
/*    */     }
/* 66 */     return buffer.toString();
/*    */   }
/*    */ 
/*    */   public static void traceConstruction(Object obj, Class<?> clazz) {
/* 70 */     if (obj.getClass().equals(clazz)) {
/* 71 */       Logger log = Logger.getLogger(obj.getClass());
/* 72 */       log.trace(getIndent() + obj + " constructed.");
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void traceDestruction(Object obj, Class<?> clazz) {
/* 77 */     if (obj.getClass().equals(clazz)) {
/* 78 */       Logger log = Logger.getLogger(obj.getClass());
/* 79 */       log.trace(getIndent() + obj + " destroyed.");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.util.TsapiTrace
 * JD-Core Version:    0.5.4
 */