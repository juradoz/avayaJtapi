/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class SelectValue extends ASNEnumerated
/*    */ {
/*    */   public static final short SV_NORMAL = 0;
/*    */   public static final short SV_LEAST_COST = 1;
/*    */   public static final short SV_EMERGENCY = 2;
/*    */   public static final short SV_ACD = 3;
/*    */   public static final short SV_USER_DEFINED = 4;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 19 */     switch (value)
/*    */     {
/*    */     case 0:
/* 22 */       str = "SV_NORMAL";
/* 23 */       break;
/*    */     case 1:
/* 26 */       str = "SV_LEAST_COST";
/* 27 */       break;
/*    */     case 2:
/* 30 */       str = "SV_EMERGENCY";
/* 31 */       break;
/*    */     case 3:
/* 34 */       str = "SV_ACD";
/* 35 */       break;
/*    */     case 4:
/* 38 */       str = "SV_USER_DEFINED";
/* 39 */       break;
/*    */     default:
/* 42 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 46 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.SelectValue
 * JD-Core Version:    0.5.4
 */