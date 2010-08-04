/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ProgressDescription extends ASNEnumerated
/*    */ {
/*    */   public static final short PD_CALL_OFF_ISDN = 1;
/*    */   public static final short PD_DEST_NOT_ISDN = 2;
/*    */   public static final short PD_ORIG_NOT_ISDN = 3;
/*    */   public static final short PD_CALL_ON_ISDN = 4;
/*    */   public static final short PD_INBAND = 8;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 18 */     switch (value)
/*    */     {
/*    */     case 1:
/* 20 */       str = "PD_CALL_OFF_ISDN";
/* 21 */       break;
/*    */     case 2:
/* 24 */       str = "PD_DEST_NOT_ISDN";
/* 25 */       break;
/*    */     case 3:
/* 28 */       str = "PD_ORIG_NOT_ISDN";
/* 29 */       break;
/*    */     case 4:
/* 32 */       str = "PD_CALL_ON_ISDN";
/* 33 */       break;
/*    */     case 8:
/* 36 */       str = "PD_INBAND";
/* 37 */       break;
/*    */     case 5:
/*    */     case 6:
/*    */     case 7:
/*    */     default:
/* 40 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 44 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.ProgressDescription
 * JD-Core Version:    0.5.4
 */