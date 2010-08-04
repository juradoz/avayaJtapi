/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.TsapiRequest;
/*    */ 
/*    */ public abstract class ACSConfirmation extends TsapiRequest
/*    */ {
/*    */   public int getPDUClass()
/*    */   {
/* 10 */     return 2;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSConfirmation
 * JD-Core Version:    0.5.4
 */