/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class BillType extends ASNEnumerated
/*    */ {
/*    */   public static final short BT_NEW_RATE = 16;
/*    */   public static final short BT_FLAT_RATE = 17;
/*    */   public static final short BT_PREMIUM_CHARGE = 18;
/*    */   public static final short BT_PREMIUM_CREDIT = 19;
/*    */   public static final short BT_FREE_CALL = 24;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 37 */     switch (value)
/*    */     {
/*    */     case 16:
/* 40 */       str = "BT_NEW_RATE";
/* 41 */       break;
/*    */     case 17:
/* 44 */       str = "BT_FLAT_RATE";
/* 45 */       break;
/*    */     case 18:
/* 48 */       str = "BT_PREMIUM_CHARGE";
/* 49 */       break;
/*    */     case 19:
/* 52 */       str = "BT_PREMIUM_CREDIT";
/* 53 */       break;
/*    */     case 24:
/* 56 */       str = "BT_FREE_CALL";
/* 57 */       break;
/*    */     case 20:
/*    */     case 21:
/*    */     case 22:
/*    */     case 23:
/*    */     default:
/* 60 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 64 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.BillType
 * JD-Core Version:    0.5.4
 */