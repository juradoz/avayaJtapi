/*    */ package com.avaya.jtapi.tsapi.impl;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.util.TsapiTrace;
/*    */ 
/*    */ public final class TsapiCallControlForwarding
/*    */ {
/*    */   String destAddress;
/*    */   int type;
/*    */   int whichCalls;
/*    */ 
/*    */   public String getDestinationAddress()
/*    */   {
/* 21 */     TsapiTrace.traceEntry("getDestinationAddress[]", this);
/* 22 */     TsapiTrace.traceExit("getDestinationAddress[]", this);
/* 23 */     return this.destAddress;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 28 */     TsapiTrace.traceEntry("getType[]", this);
/* 29 */     TsapiTrace.traceExit("getType[]", this);
/* 30 */     return this.type;
/*    */   }
/*    */ 
/*    */   public int getFilter()
/*    */   {
/* 35 */     TsapiTrace.traceEntry("getFilter[]", this);
/* 36 */     TsapiTrace.traceExit("getFilter[]", this);
/* 37 */     return this.whichCalls;
/*    */   }
/*    */ 
/*    */   public TsapiCallControlForwarding(String _destAddress, int _type)
/*    */   {
/* 42 */     this.destAddress = _destAddress;
/* 43 */     this.type = _type;
/* 44 */     this.whichCalls = 1;
/* 45 */     TsapiTrace.traceConstruction(this, TsapiCallControlForwarding.class);
/*    */   }
/*    */ 
/*    */   public TsapiCallControlForwarding(String _destAddress, int _type, boolean internalCalls)
/*    */   {
/* 51 */     this.destAddress = _destAddress;
/* 52 */     this.type = _type;
/* 53 */     if (internalCalls)
/*    */     {
/* 55 */       this.whichCalls = 2;
/*    */     }
/*    */     else
/*    */     {
/* 59 */       this.whichCalls = 3;
/*    */     }
/* 61 */     TsapiTrace.traceConstruction(this, TsapiCallControlForwarding.class);
/*    */   }
/*    */ 
/*    */   public boolean equals(TsapiCallControlForwarding other)
/*    */   {
/* 66 */     return (this.destAddress == other.destAddress) && (this.type == other.type) && (this.whichCalls == other.whichCalls);
/*    */   }
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 73 */     super.finalize();
/* 74 */     TsapiTrace.traceDestruction(this, TsapiCallControlForwarding.class);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.TsapiCallControlForwarding
 * JD-Core Version:    0.5.4
 */