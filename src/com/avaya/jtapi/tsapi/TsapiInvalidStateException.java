 package com.avaya.jtapi.tsapi;
 
 import javax.telephony.InvalidStateException;
 
 public final class TsapiInvalidStateException extends InvalidStateException
   implements ITsapiException
 {
   private static final long serialVersionUID = 1L;
   int errorType = 0;
   int errorCode = 0;
 
   public int getErrorType()
   {
     return this.errorType;
   }
 
   public int getErrorCode()
   {
     return this.errorCode;
   }
 
   public TsapiInvalidStateException(int _errorType, int _errorCode, Object object, int type, int state)
   {
     super(object, type, state);
     this.errorType = _errorType;
     this.errorCode = _errorCode;
   }
 
   public TsapiInvalidStateException(int _errorType, int _errorCode, Object object, int type, int state, String s)
   {
     super(object, type, state, s);
     this.errorType = _errorType;
     this.errorCode = _errorCode;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiInvalidStateException
 * JD-Core Version:    0.5.4
 */