/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class ChargeType extends ASNEnumerated
/*    */ {
/*    */   public static final short CT_INTERMEDIATE_CHARGE = 1;
/*    */   public static final short CT_FINAL_CHARGE = 2;
/*    */   public static final short CT_SPLIT_CHARGE = 3;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 49 */     switch (value)
/*    */     {
/*    */     case 1:
/* 52 */       str = "CT_INTERMEDIATE_CHARGE";
/* 53 */       break;
/*    */     case 2:
/* 56 */       str = "CT_FINAL_CHARGE";
/* 57 */       break;
/*    */     case 3:
/* 60 */       str = "CT_SPLIT_CHARGE";
/* 61 */       break;
/*    */     default:
/* 64 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 68 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.ChargeType
 * JD-Core Version:    0.5.4
 */