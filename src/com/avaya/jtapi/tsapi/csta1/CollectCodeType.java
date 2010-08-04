/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CollectCodeType extends ASNEnumerated
/*    */ {
/*    */   public static final short UC_NONE = 0;
/*    */   public static final short UC_TONE_DETECTOR = 32;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 16 */     switch (value)
/*    */     {
/*    */     case 0:
/* 19 */       str = "UC_NONE";
/* 20 */       break;
/*    */     case 32:
/* 23 */       str = "UC_TONE_DETECTOR";
/* 24 */       break;
/*    */     default:
/* 27 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 31 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CollectCodeType
 * JD-Core Version:    0.5.4
 */