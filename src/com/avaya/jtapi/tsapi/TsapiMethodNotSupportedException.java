/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ import javax.telephony.MethodNotSupportedException;
/*    */ 
/*    */ public final class TsapiMethodNotSupportedException extends MethodNotSupportedException
/*    */   implements ITsapiException
/*    */ {
/* 51 */   int errorType = 0;
/* 52 */   int errorCode = 0;
/*    */   static final long serialVersionUID = 1L;
/*    */ 
/*    */   public int getErrorType()
/*    */   {
/* 23 */     return this.errorType;
/*    */   }
/*    */ 
/*    */   public int getErrorCode()
/*    */   {
/* 29 */     return this.errorCode;
/*    */   }
/*    */ 
/*    */   public TsapiMethodNotSupportedException(int _errorType, int _errorCode)
/*    */   {
/* 38 */     this.errorType = _errorType;
/* 39 */     this.errorCode = _errorCode;
/*    */   }
/*    */ 
/*    */   public TsapiMethodNotSupportedException(int _errorType, int _errorCode, String s)
/*    */   {
/* 46 */     super(s);
/* 47 */     this.errorType = _errorType;
/* 48 */     this.errorCode = _errorCode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiMethodNotSupportedException
 * JD-Core Version:    0.5.4
 */