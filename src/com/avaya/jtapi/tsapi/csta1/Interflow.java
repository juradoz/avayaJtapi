/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class Interflow extends ASNEnumerated
/*    */ {
/*    */   public static final short LAI_NO_INTERFLOW = -1;
/*    */   public static final short LAI_ALL_INTERFLOW = 0;
/*    */   public static final short LAI_THRESHOLD_INTERFLOW = 1;
/*    */   public static final short LAI_VECTORING_INTERFLOW = 2;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 18 */     switch (value)
/*    */     {
/*    */     case -1:
/* 21 */       str = "LAI_NO_INTERFLOW";
/* 22 */       break;
/*    */     case 0:
/* 25 */       str = "LAI_ALL_INTERFLOW";
/* 26 */       break;
/*    */     case 1:
/* 29 */       str = "LAI_THRESHOLD_INTERFLOW";
/* 30 */       break;
/*    */     case 2:
/* 33 */       str = "LAI_VECTORING_INTERFLOW";
/* 34 */       break;
/*    */     default:
/* 37 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 41 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.Interflow
 * JD-Core Version:    0.5.4
 */