/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ProvidedCodeType extends ASNEnumerated
/*    */ {
/*    */   public static final short UP_NONE = 0;
/*    */   public static final short UP_DATA_BASE_PROVIDED = 17;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 16 */     switch (value)
/*    */     {
/*    */     case 0:
/* 19 */       str = "UP_NONE";
/* 20 */       break;
/*    */     case 17:
/* 23 */       str = "UP_DATA_BASE_PROVIDED";
/* 24 */       break;
/*    */     default:
/* 27 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 31 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.ProvidedCodeType
 * JD-Core Version:    0.5.4
 */