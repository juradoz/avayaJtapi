/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class ChargeError extends ASNEnumerated
/*    */ {
/*    */   public static final short CE_NONE = 0;
/*    */   public static final short CE_NO_FINAL_CHARGE = 1;
/*    */   public static final short CE_LESS_FINAL_CHARGE = 2;
/*    */   public static final short CE_CHARGE_TOO_LARGE = 3;
/*    */   public static final short CE_NETWORK_BUSY = 4;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 35 */     switch (value)
/*    */     {
/*    */     case 0:
/* 38 */       str = "CE_NONE";
/* 39 */       break;
/*    */     case 1:
/* 42 */       str = "CE_NO_FINAL_CHARGE";
/* 43 */       break;
/*    */     case 2:
/* 46 */       str = "CE_LESS_FINAL_CHARGE";
/* 47 */       break;
/*    */     case 3:
/* 50 */       str = "CE_CHARGE_TOO_LARGE";
/* 51 */       break;
/*    */     case 4:
/* 54 */       str = "CE_NETWORK_BUSY";
/* 55 */       break;
/*    */     default:
/* 58 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 62 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.ChargeError
 * JD-Core Version:    0.5.4
 */