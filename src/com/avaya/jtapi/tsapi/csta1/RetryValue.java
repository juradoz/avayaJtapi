/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class RetryValue extends ASNInteger
/*    */ {
/*    */   public static final short noListAvailable = -1;
/*    */   public static final short noCountAvailable = -2;
/*    */ 
/*    */   public static Collection<String> print(int value, String name, String indent)
/*    */   {
/*    */     String str;
/* 16 */     switch (value)
/*    */     {
/*    */     case -1:
/* 19 */       str = "noListAvailable";
/* 20 */       break;
/*    */     case -2:
/* 23 */       str = "noCountAvailable";
/* 24 */       break;
/*    */     default:
/* 27 */       str = String.valueOf(value);
/*    */     }
/*    */ 
/* 31 */     return print(str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.RetryValue
 * JD-Core Version:    0.5.4
 */