/*    */ package com.avaya.jtapi.tsapi.asn1;
/*    */ 
/*    */ public class ASN1Exception extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   public ASN1Exception()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ASN1Exception(String message, Throwable cause)
/*    */   {
/* 11 */     super(message, cause);
/*    */   }
/*    */ 
/*    */   public ASN1Exception(String message) {
/* 15 */     super(message);
/*    */   }
/*    */ 
/*    */   public ASN1Exception(Throwable cause) {
/* 19 */     super(cause);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.asn1.ASN1Exception
 * JD-Core Version:    0.5.4
 */