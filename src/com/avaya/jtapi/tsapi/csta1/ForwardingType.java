/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ForwardingType extends ASNEnumerated
/*    */ {
/*    */   public static final short FWD_IMMEDIATE = 0;
/*    */   public static final short FWD_BUSY = 1;
/*    */   public static final short FWD_NO_ANS = 2;
/*    */   public static final short FWD_BUSY_INT = 3;
/*    */   public static final short FWD_BUSY_EXT = 4;
/*    */   public static final short FWD_NO_ANS_INT = 5;
/*    */   public static final short FWD_NO_ANS_EXT = 6;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 21 */     switch (value)
/*    */     {
/*    */     case 0:
/* 24 */       str = "FWD_IMMEDIATE";
/* 25 */       break;
/*    */     case 1:
/* 28 */       str = "FWD_BUSY";
/* 29 */       break;
/*    */     case 2:
/* 32 */       str = "FWD_NO_ANS";
/* 33 */       break;
/*    */     case 3:
/* 36 */       str = "FWD_BUSY_INT";
/* 37 */       break;
/*    */     case 4:
/* 40 */       str = "FWD_BUSY_EXT";
/* 41 */       break;
/*    */     case 5:
/* 44 */       str = "FWD_NO_ANS_INT";
/* 45 */       break;
/*    */     case 6:
/* 48 */       str = "FWD_NO_ANS_EXT";
/* 49 */       break;
/*    */     default:
/* 52 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 56 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.ForwardingType
 * JD-Core Version:    0.5.4
 */