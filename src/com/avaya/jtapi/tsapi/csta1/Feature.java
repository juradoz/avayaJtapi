/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class Feature extends ASNEnumerated
/*    */ {
/*    */   static final short FT_CAMP_ON = 0;
/*    */   static final short FT_CALL_BACK = 1;
/*    */   static final short FT_INTRUDE = 2;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 17 */     switch (value)
/*    */     {
/*    */     case 0:
/* 20 */       str = "FT_CAMP_ON";
/* 21 */       break;
/*    */     case 1:
/* 24 */       str = "FT_CALL_BACK";
/* 25 */       break;
/*    */     case 2:
/* 28 */       str = "FT_INTRUDE";
/* 29 */       break;
/*    */     default:
/* 32 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 36 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.Feature
 * JD-Core Version:    0.5.4
 */