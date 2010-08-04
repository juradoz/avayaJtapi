/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ReasonForCallInfo extends ASNEnumerated
/*    */ {
/*    */   public static final short OR_NONE = 0;
/*    */   public static final short OR_CONSULTATION = 1;
/*    */   public static final short OR_CONFERENCED = 2;
/*    */   public static final short OR_TRANSFERRED = 3;
/*    */   public static final short OR_NEW_CALL = 4;
/*    */ 
/*    */   public static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 19 */     switch (value)
/*    */     {
/*    */     case 0:
/* 22 */       str = "OR_NONE";
/* 23 */       break;
/*    */     case 1:
/* 26 */       str = "OR_CONSULTATION";
/* 27 */       break;
/*    */     case 2:
/* 30 */       str = "OR_CONFERENCED";
/* 31 */       break;
/*    */     case 3:
/* 34 */       str = "OR_TRANSFERRED";
/* 35 */       break;
/*    */     case 4:
/* 38 */       str = "OR_NEW_CALL";
/* 39 */       break;
/*    */     default:
/* 42 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 46 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.ReasonForCallInfo
 * JD-Core Version:    0.5.4
 */