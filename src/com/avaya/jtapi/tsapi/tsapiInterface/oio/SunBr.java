/*    */ package com.avaya.jtapi.tsapi.tsapiInterface.oio;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.net.InetAddress;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Socket;
/*    */ import java.net.SocketPermission;
/*    */ import java.net.UnknownHostException;
/*    */ import java.security.AccessController;
/*    */ import javax.net.SocketFactory;
/*    */ 
/*    */ final class SunBr extends GenericBrowser
/*    */ {
/*    */   SunBr()
/*    */   {
/* 13 */     super("Sun JVM");
/*    */   }
/*    */ 
/*    */   Socket trySocket(InetSocketAddress addr, SocketFactory sf)
/*    */     throws UnknownHostException, IOException
/*    */   {
/* 19 */     System.out.println("in SunBr  checkPermission\n");
/* 20 */     AccessController.checkPermission(new SocketPermission(addr.getAddress().getHostAddress() + ":" + addr.getPort(), "connect"));
/*    */ 
/* 22 */     System.out.println("in SunBr  trySocket\n");
/* 23 */     return super.trySocket(addr, sf);
/*    */   }
/*    */ 
/*    */   InputStream findProperties()
/*    */   {
/* 30 */     return super.findProperties();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.SunBr
 * JD-Core Version:    0.5.4
 */