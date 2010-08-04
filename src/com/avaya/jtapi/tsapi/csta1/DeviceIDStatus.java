/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class DeviceIDStatus extends ASNEnumerated
/*    */ {
/*    */   public static final short ID_PROVIDED = 0;
/*    */   public static final short ID_NOT_KNOWN = 1;
/*    */   public static final short ID_NOT_REQUIRED = 2;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 17 */     switch (value)
/*    */     {
/*    */     case 0:
/* 20 */       str = "ID_PROVIDED";
/* 21 */       break;
/*    */     case 1:
/* 24 */       str = "ID_NOT_KNOWN";
/* 25 */       break;
/*    */     case 2:
/* 28 */       str = "ID_NOT_REQUIRED";
/* 29 */       break;
/*    */     default:
/* 32 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 36 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.DeviceIDStatus
 * JD-Core Version:    0.5.4
 */