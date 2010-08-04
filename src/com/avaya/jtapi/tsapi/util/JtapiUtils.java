/*    */ package com.avaya.jtapi.tsapi.util;
/*    */ 
/*    */ import java.net.InetSocketAddress;
/*    */ import java.util.Collection;
/*    */ import java.util.Enumeration;
/*    */ import java.util.LinkedHashSet;
/*    */ import org.apache.log4j.LogManager;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class JtapiUtils
/*    */ {
/* 12 */   private static Logger log = Logger.getLogger(JtapiUtils.class);
/*    */ 
/* 14 */   public static Collection<InetSocketAddress> parseTelephonyServerEntry(String value, int defaultPort) { Collection telephonyServers = new LinkedHashSet();
/* 15 */     String[] entries = value.split(",");
/* 16 */     for (String entry : entries) {
/* 17 */       String[] tokens = entry.split(":");
/* 18 */       if (tokens.length > 0) {
/* 19 */         int port = defaultPort;
/* 20 */         if (tokens.length > 1) {
/*    */           try {
/* 22 */             port = Integer.parseInt(tokens[1]);
/*    */           } catch (NumberFormatException e) {
/* 24 */             log.error("Error getting port from string fragment'" + value + "':" + e.getMessage(), e);
/*    */           }
/*    */         }
/*    */ 
/* 28 */         String server = tokens[0];
/* 29 */         server = (server != null) ? server.trim() : server;
/* 30 */         telephonyServers.add(new InetSocketAddress(server, port));
/*    */       }
/*    */     }
/* 33 */     return telephonyServers; }
/*    */ 
/*    */   public static boolean isLog4jConfigured() {
/* 36 */     Enumeration appenders = Logger.getRootLogger().getAllAppenders();
/* 37 */     if (appenders.hasMoreElements()) {
/* 38 */       return true;
/*    */     }
/*    */ 
/* 41 */     Enumeration loggers = LogManager.getCurrentLoggers();
/* 42 */     while (loggers.hasMoreElements()) {
/* 43 */       Logger c = (Logger)loggers.nextElement();
/* 44 */       if (c.getAllAppenders().hasMoreElements()) {
/* 45 */         return true;
/*    */       }
/*    */     }
/* 48 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.util.JtapiUtils
 * JD-Core Version:    0.5.4
 */