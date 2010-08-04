/*    */ package com.avaya.jtapi.tsapi.tsapiInterface.oio;
/*    */ 
/*    */ import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;
/*    */ 
/*    */ final class IeBr extends GenericBrowser
/*    */ {
/*    */   IeBr()
/*    */   {
/* 15 */     super("IE 4.X");
/*    */   }
/*    */ 
/*    */   Socket trySocket(InetSocketAddress addr, SocketFactory sf)
/*    */     throws UnknownHostException, IOException
/*    */   {
///* 21 */     PolicyEngine.assertPermission(PermissionID.NETIO);
/*    */ 
/* 23 */     return super.trySocket(addr, sf);
/*    */   }
/*    */ 
/*    */   InputStream findProperties()
/*    */   {
///* 28 */     PolicyEngine.assertPermission(PermissionID.FILEIO);
/*    */ 
/* 30 */     return super.findProperties();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.IeBr
 * JD-Core Version:    0.5.4
 */