 package com.avaya.jtapi.tsapi;
 
 import javax.telephony.ProviderUnavailableException;
 
 public final class TsapiProviderUnavailableException extends ProviderUnavailableException
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
 
   public TsapiProviderUnavailableException(int _errorType, int _errorCode)
   {
     this.errorCode = _errorCode;
     this.errorType = _errorType;
   }
 
   public TsapiProviderUnavailableException(int _errorType, int _errorCode, String s)
   {
     super(s);
     this.errorCode = _errorCode;
     this.errorType = _errorType;
   }
 
   public TsapiProviderUnavailableException(int cause, String s)
   {
     super(cause, s);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiProviderUnavailableException
 * JD-Core Version:    0.5.4
 */