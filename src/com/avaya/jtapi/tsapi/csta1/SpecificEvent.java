/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class SpecificEvent extends ASNEnumerated
/*    */ {
/*    */   public static final short SE_ANSWER = 11;
/*    */   public static final short SE_DISCONNECT = 4;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 16 */     switch (value)
/*    */     {
/*    */     case 11:
/* 19 */       str = "SE_ANSWER";
/* 20 */       break;
/*    */     case 4:
/* 23 */       str = "SE_DISCONNECT";
/* 24 */       break;
/*    */     default:
/* 27 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 31 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.SpecificEvent
 * JD-Core Version:    0.5.4
 */