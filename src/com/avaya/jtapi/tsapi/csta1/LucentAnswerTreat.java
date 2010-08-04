/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentAnswerTreat extends ASNEnumerated
/*    */ {
/*    */   public static final short AT_NO_TREATMENT = 0;
/*    */   public static final short AT_NONE = 1;
/*    */   public static final short AT_DROP = 2;
/*    */   public static final short AT_CONNECT = 3;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 18 */     switch (value)
/*    */     {
/*    */     case 0:
/* 21 */       str = "AT_NO_TREATMENT";
/* 22 */       break;
/*    */     case 1:
/* 25 */       str = "AT_NONE";
/* 26 */       break;
/*    */     case 2:
/* 29 */       str = "AT_DROP";
/* 30 */       break;
/*    */     case 3:
/* 33 */       str = "AT_CONNECT";
/* 34 */       break;
/*    */     default:
/* 37 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 41 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentAnswerTreat
 * JD-Core Version:    0.5.4
 */