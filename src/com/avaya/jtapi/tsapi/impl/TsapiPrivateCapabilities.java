/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.impl.core.TSCapabilities;
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ import javax.telephony.privatedata.capabilities.PrivateDataCapabilities;
/*    */ 
/*    */ final class TsapiPrivateCapabilities
/*    */   implements PrivateDataCapabilities
/*    */ {
/* 16 */   TSCapabilities tsCaps = null;
/*    */ 
/*    */   public boolean canSetPrivateData()
/*    */   {
/* 20 */     TsapiTrace.traceEntry("canSetPrivateData[]", this);
/* 21 */     TsapiTrace.traceExit("canSetPrivateData[]", this);
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean canGetPrivateData()
/*    */   {
/* 27 */     TsapiTrace.traceEntry("canGetPrivateData[]", this);
/* 28 */     TsapiTrace.traceExit("canGetPrivateData[]", this);
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean canSendPrivateData()
/*    */   {
/* 34 */     TsapiTrace.traceEntry("canSendPrivateData[]", this);
/* 35 */     TsapiTrace.traceExit("canSendPrivateData[]", this);
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   TsapiPrivateCapabilities(TSCapabilities _tsCaps)
/*    */   {
/* 42 */     this.tsCaps = _tsCaps;
/* 43 */     TsapiTrace.traceConstruction(this, TsapiPrivateCapabilities.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiPrivateCapabilities
 * JD-Core Version:    0.5.4
 */