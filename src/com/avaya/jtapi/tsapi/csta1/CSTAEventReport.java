 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.TsapiPDU;
 
 public abstract class CSTAEventReport extends TsapiPDU
 {
   public int getPDUClass()
   {
     return 6;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAEventReport
 * JD-Core Version:    0.5.4
 */