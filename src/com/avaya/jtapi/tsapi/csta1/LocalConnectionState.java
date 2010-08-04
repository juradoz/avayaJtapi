/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LocalConnectionState extends ASNEnumerated
/*    */ {
/*    */   public static final short CS_NONE = -1;
/*    */   public static final short CS_NULL = 0;
/*    */   public static final short CS_INITIATE = 1;
/*    */   public static final short CS_ALERTING = 2;
/*    */   public static final short CS_CONNECT = 3;
/*    */   public static final short CS_HOLD = 4;
/*    */   public static final short CS_QUEUED = 5;
/*    */   public static final short CS_FAIL = 6;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 22 */     switch (value)
/*    */     {
/*    */     case -1:
/* 25 */       str = "CS_NONE";
/* 26 */       break;
/*    */     case 0:
/* 29 */       str = "CS_NULL";
/* 30 */       break;
/*    */     case 1:
/* 33 */       str = "CS_INITIATE";
/* 34 */       break;
/*    */     case 2:
/* 37 */       str = "CS_ALERTING";
/* 38 */       break;
/*    */     case 3:
/* 41 */       str = "CS_CONNECT";
/* 42 */       break;
/*    */     case 4:
/* 45 */       str = "CS_HOLD";
/* 46 */       break;
/*    */     case 5:
/* 49 */       str = "CS_QUEUED";
/* 50 */       break;
/*    */     case 6:
/* 53 */       str = "CS_FAIL";
/* 54 */       break;
/*    */     default:
/* 57 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 61 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LocalConnectionState
 * JD-Core Version:    0.5.4
 */