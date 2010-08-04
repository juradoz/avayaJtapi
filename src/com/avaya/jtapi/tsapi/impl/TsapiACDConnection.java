/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException;
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSConnection;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ import javax.telephony.callcenter.ACDConnection;
/*    */ import javax.telephony.callcenter.ACDManagerConnection;
/*    */ 
/*    */ class TsapiACDConnection extends TsapiConnection
/*    */   implements ACDConnection
/*    */ {
/*    */   public final ACDManagerConnection getACDManagerConnection()
/*    */     throws TsapiMethodNotSupportedException
/*    */   {
/* 22 */     TsapiTrace.traceEntry("getACDManagerConnection[]", this);
/*    */     try
/*    */     {
/* 25 */       TSConnection tsConn = this.tsConnection.getACDManagerConn();
/* 26 */       if (tsConn == null) {
/* 27 */         TsapiTrace.traceExit("getACDManagerConnection[]", this);
/* 28 */         return null;
/*    */       }
/*    */ 
/* 32 */       ACDManagerConnection conn = (ACDManagerConnection)TsapiCreateObject.getTsapiObject(tsConn, true);
/* 33 */       TsapiTrace.traceExit("getACDManagerConnection[]", this);
/* 34 */       return conn;
/*    */     }
/*    */     finally
/*    */     {
/* 39 */       this.privData = null;
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 46 */     if (obj instanceof TsapiACDConnection)
/*    */     {
/* 48 */       return this.tsConnection.equals(((TsapiACDConnection)obj).tsConnection);
/*    */     }
/*    */ 
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */   TsapiACDConnection(TSConnection _tsConnection)
/*    */   {
/* 59 */     super(_tsConnection);
/* 60 */     TsapiTrace.traceConstruction(this, TsapiACDConnection.class);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 66 */     super.finalize();
/* 67 */     TsapiTrace.traceDestruction(this, TsapiACDConnection.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiACDConnection
 * JD-Core Version:    0.5.4
 */