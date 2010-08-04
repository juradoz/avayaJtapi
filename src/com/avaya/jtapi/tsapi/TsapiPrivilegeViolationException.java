/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ import javax.telephony.PrivilegeViolationException;
/*    */ 
/*    */ public final class TsapiPrivilegeViolationException extends PrivilegeViolationException
/*    */   implements ITsapiException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 46 */   int errorType = 0;
/* 47 */   int errorCode = 0;
/*    */ 
/*    */   public int getErrorType()
/*    */   {
/* 17 */     return this.errorType;
/*    */   }
/*    */ 
/*    */   public int getErrorCode()
/*    */   {
/* 23 */     return this.errorCode;
/*    */   }
/*    */ 
/*    */   public TsapiPrivilegeViolationException(int _errorType, int _errorCode, int type)
/*    */   {
/* 32 */     super(type);
/* 33 */     this.errorType = _errorType;
/* 34 */     this.errorCode = _errorCode;
/*    */   }
/*    */ 
/*    */   public TsapiPrivilegeViolationException(int _errorType, int _errorCode, int type, String s)
/*    */   {
/* 41 */     super(type, s);
/* 42 */     this.errorType = _errorType;
/* 43 */     this.errorCode = _errorCode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiPrivilegeViolationException
 * JD-Core Version:    0.5.4
 */