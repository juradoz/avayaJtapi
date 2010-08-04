/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ import javax.telephony.ProviderUnavailableException;
/*    */ 
/*    */ public final class TsapiProviderUnavailableException extends ProviderUnavailableException
/*    */   implements ITsapiException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 55 */   int errorType = 0;
/* 56 */   int errorCode = 0;
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
/*    */   public TsapiProviderUnavailableException(int _errorType, int _errorCode)
/*    */   {
/* 37 */     this.errorCode = _errorCode;
/* 38 */     this.errorType = _errorType;
/*    */   }
/*    */ 
/*    */   public TsapiProviderUnavailableException(int _errorType, int _errorCode, String s)
/*    */   {
/* 44 */     super(s);
/* 45 */     this.errorCode = _errorCode;
/* 46 */     this.errorType = _errorType;
/*    */   }
/*    */ 
/*    */   public TsapiProviderUnavailableException(int cause, String s)
/*    */   {
/* 52 */     super(cause, s);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiProviderUnavailableException
 * JD-Core Version:    0.5.4
 */