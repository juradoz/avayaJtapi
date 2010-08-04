/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ import javax.telephony.ResourceUnavailableException;
/*    */ 
/*    */ public final class TsapiResourceUnavailableException extends ResourceUnavailableException
/*    */   implements ITsapiException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 51 */   int errorType = 0;
/* 52 */   int errorCode = 0;
/*    */ 
/*    */   public int getErrorType()
/*    */   {
/* 22 */     return this.errorType;
/*    */   }
/*    */ 
/*    */   public int getErrorCode()
/*    */   {
/* 28 */     return this.errorCode;
/*    */   }
/*    */ 
/*    */   public TsapiResourceUnavailableException(int _errorType, int _errorCode, int type)
/*    */   {
/* 37 */     super(type);
/* 38 */     this.errorType = _errorType;
/* 39 */     this.errorCode = _errorCode;
/*    */   }
/*    */ 
/*    */   public TsapiResourceUnavailableException(int _errorType, int _errorCode, int type, String s)
/*    */   {
/* 46 */     super(type, s);
/* 47 */     this.errorType = _errorType;
/* 48 */     this.errorCode = _errorCode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiResourceUnavailableException
 * JD-Core Version:    0.5.4
 */