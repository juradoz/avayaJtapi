/*    */ package com.avaya.jtapi.tsapi;
/*    */ 
/*    */ import javax.telephony.PlatformException;
/*    */ 
/*    */ public class TsapiPlatformException extends PlatformException
/*    */   implements ITsapiException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 41 */   int errorType = 0;
/* 42 */   int errorCode = 0;
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
/*    */   public TsapiPlatformException(int _errorType, int _errorCode, String s)
/*    */   {
/* 36 */     super(s);
/* 37 */     this.errorType = _errorType;
/* 38 */     this.errorCode = _errorCode;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiPlatformException
 * JD-Core Version:    0.5.4
 */