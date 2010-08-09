 package com.avaya.jtapi.tsapi;
 
 public class TsapiSocketException extends TsapiPlatformException
 {
   private static final long serialVersionUID = 1L;
 
   public TsapiSocketException(int _errorType, int _errorCode, String s)
   {
     super(_errorType, _errorCode, s);
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.TsapiSocketException
 * JD-Core Version:    0.5.4
 */