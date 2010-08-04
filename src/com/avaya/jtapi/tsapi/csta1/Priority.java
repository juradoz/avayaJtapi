/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class Priority extends ASNEnumerated
/*    */ {
/*    */   public static final short LAI_NOT_IN_QUEUE = 0;
/*    */   public static final short LAI_LOW = 1;
/*    */   public static final short LAI_MEDIUM = 2;
/*    */   public static final short LAI_HIGH = 3;
/*    */   public static final short LAI_TOP = 4;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 19 */     switch (value)
/*    */     {
/*    */     case 0:
/* 22 */       str = "LAI_NOT_IN_QUEUE";
/* 23 */       break;
/*    */     case 1:
/* 26 */       str = "LAI_LOW";
/* 27 */       break;
/*    */     case 2:
/* 30 */       str = "LAI_MEDIUM";
/* 31 */       break;
/*    */     case 3:
/* 34 */       str = "LAI_HIGH";
/* 35 */       break;
/*    */     case 4:
/* 38 */       str = "LAI_TOP";
/* 39 */       break;
/*    */     default:
/* 42 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 46 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.Priority
 * JD-Core Version:    0.5.4
 */