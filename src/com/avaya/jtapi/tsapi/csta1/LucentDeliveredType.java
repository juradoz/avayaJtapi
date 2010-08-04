/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentDeliveredType extends ASNEnumerated
/*    */ {
/*    */   public static final short DELIVERED_TO_ACD = 1;
/*    */   public static final short DELIVERED_TO_STATION = 2;
/*    */   public static final short DELIVERED_OTHER = 3;
/*    */ 
/*    */   public static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 17 */     switch (value)
/*    */     {
/*    */     case 1:
/* 20 */       str = "DELIVERED_TO_ACD";
/* 21 */       break;
/*    */     case 2:
/* 24 */       str = "DELIVERED_TO_STATION";
/* 25 */       break;
/*    */     case 3:
/* 28 */       str = "DELIVERED_OTHER";
/* 29 */       break;
/*    */     default:
/* 32 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 36 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentDeliveredType
 * JD-Core Version:    0.5.4
 */