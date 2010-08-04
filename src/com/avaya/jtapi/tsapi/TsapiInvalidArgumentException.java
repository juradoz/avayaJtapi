/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ import javax.telephony.InvalidArgumentException;
/*    */ 
/*    */ public final class TsapiInvalidArgumentException extends InvalidArgumentException
/*    */   implements ITsapiException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 50 */   int errorType = 0;
/* 51 */   int errorCode = 0;
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
/*    */   public TsapiInvalidArgumentException(int _errorType, int _errorCode)
/*    */   {
/* 37 */     this.errorType = _errorType;
/* 38 */     this.errorCode = _errorCode;
/*    */   }
/*    */ 
/*    */   public TsapiInvalidArgumentException(int _errorType, int _errorCode, String s)
/*    */   {
/* 45 */     super(s);
/* 46 */     this.errorType = _errorType;
/* 47 */     this.errorCode = _errorCode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiInvalidArgumentException
 * JD-Core Version:    0.5.4
 */