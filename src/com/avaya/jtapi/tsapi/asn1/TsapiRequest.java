 package com.avaya.jtapi.tsapi.asn1;
 
 public abstract class TsapiRequest extends TsapiPDU
 {
   int invokeID;
 
   public int getInvokeID()
   {
     return this.invokeID;
   }
 
   public void setInvokeID(int invokeID)
   {
     this.invokeID = invokeID;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.TsapiRequest
 * JD-Core Version:    0.5.4
 */