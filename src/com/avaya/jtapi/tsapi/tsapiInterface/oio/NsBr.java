/*    */ package com.avaya.jtapi.tsapi.tsapiInterface.oio;
/*    */ 
/*    */ import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;
/*    */ 
/*    */ final class NsBr extends GenericBrowser
/*    */ {
/* 16 */   static boolean grantedUniversalConnect = false;
/* 17 */   static boolean askedForUniversalConnect = false;
/*    */ 
/*    */   NsBr()
/*    */   {
/* 21 */     super("NS 4.X");
/*    */   }
/*    */ 
/*    */   Socket trySocket(InetSocketAddress addr, SocketFactory sf)
/*    */     throws UnknownHostException, IOException
/*    */   {
/*    */     try
/*    */     {
///* 29 */       if (grantedUniversalConnect)
///* 30 */         PrivilegeManager.enablePrivilege("UniversalConnect");
/* 31 */       return super.trySocket(addr, sf);
/*    */     }
///*    */     catch (AppletSecurityException e)
catch(Exception e)
/*    */     {
///* 35 */       if (!askedForUniversalConnect)
/*    */       {
///* 37 */         askedForUniversalConnect = true;
///* 38 */         PrivilegeManager.enablePrivilege("UniversalConnect");
///* 39 */         grantedUniversalConnect = true;
/* 40 */         return super.trySocket(addr, sf);
/*    */       }
///* 42 */       throw e;
/*    */     }
/*    */   }
/*    */ 
/*    */   InputStream findProperties()
/*    */   {
/*    */     try
/*    */     {
/* 50 */       return super.findProperties();
/*    */     }
///*    */     catch (AppletSecurityException e)
catch(Exception e)
/*    */     {
///* 54 */       PrivilegeManager.enablePrivilege("UniversalPropertyRead");
///* 55 */       PrivilegeManager.enablePrivilege("UniversalFileRead");
/* 56 */     }return super.findProperties();
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.tsapiInterface.oio.NsBr
 * JD-Core Version:    0.5.4
 */