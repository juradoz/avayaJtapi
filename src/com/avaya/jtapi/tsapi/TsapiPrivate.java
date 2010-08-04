/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ public final class TsapiPrivate
/*    */ {
/*    */   public String vendor;
/*    */   public byte[] data;
/*    */   public int tsType;
/*    */ 
/*    */   public TsapiPrivate(byte[] _data)
/*    */   {
/* 41 */     this(_data, false);
/*    */   }
/*    */ 
/*    */   public TsapiPrivate(byte[] _data, boolean waitForResponse)
/*    */   {
/* 59 */     this.data = _data;
/* 60 */     if (waitForResponse)
/*    */     {
/* 62 */       this.tsType = 89;
/*    */     }
/*    */     else
/*    */     {
/* 66 */       this.tsType = 95;
/*    */     }
/*    */   }
/*    */ 
/*    */   public TsapiPrivate(String _vendor, byte[] _data, int _tsType)
/*    */   {
/* 73 */     this.vendor = _vendor;
/* 74 */     this.data = _data;
/* 75 */     this.tsType = _tsType;
/*    */   }
/*    */ 
/*    */   public byte[] getData()
/*    */   {
/* 83 */     return this.data;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiPrivate
 * JD-Core Version:    0.5.4
 */