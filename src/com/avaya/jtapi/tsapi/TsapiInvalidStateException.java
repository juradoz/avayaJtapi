/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ import javax.telephony.InvalidStateException;
/*    */ 
/*    */ public final class TsapiInvalidStateException extends InvalidStateException
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
/*    */   public TsapiInvalidStateException(int _errorType, int _errorCode, Object object, int type, int state)
/*    */   {
/* 37 */     super(object, type, state);
/* 38 */     this.errorType = _errorType;
/* 39 */     this.errorCode = _errorCode;
/*    */   }
/*    */ 
/*    */   public TsapiInvalidStateException(int _errorType, int _errorCode, Object object, int type, int state, String s)
/*    */   {
/* 46 */     super(object, type, state, s);
/* 47 */     this.errorType = _errorType;
/* 48 */     this.errorCode = _errorCode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiInvalidStateException
 * JD-Core Version:    0.5.4
 */