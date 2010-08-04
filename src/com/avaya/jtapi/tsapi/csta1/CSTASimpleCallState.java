/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTASimpleCallState extends ASNEnumerated
/*    */ {
/*    */   static final short CALL_NULL = 0;
/*    */   static final short CALL_PENDING = 1;
/*    */   static final short CALL_ORIGINATED = 3;
/*    */   static final short CALL_DELIVERED = 35;
/*    */   static final short CALL_DELIVERED_HELD = 36;
/*    */   static final short CALL_RECEIVED = 50;
/*    */   static final short CALL_ESTABLISHED = 51;
/*    */   static final short CALL_ESTABLISHED_HELD = 52;
/*    */   static final short CALL_RECEIVED_ON_HOLD = 66;
/*    */   static final short CALL_ESTABLISHED_ON_HOLD = 67;
/*    */   static final short CALL_QUEUED = 83;
/*    */   static final short CALL_QUEUED_HELD = 84;
/*    */   static final short CALL_FAILED = 99;
/*    */   static final short CALL_FAILED_HELD = 100;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 28 */     switch (value)
/*    */     {
/*    */     case 0:
/* 31 */       str = "CALL_NULL";
/* 32 */       break;
/*    */     case 1:
/* 35 */       str = "CALL_PENDING";
/* 36 */       break;
/*    */     case 3:
/* 39 */       str = "CALL_ORIGINATED";
/* 40 */       break;
/*    */     case 35:
/* 43 */       str = "CALL_DELIVERED";
/* 44 */       break;
/*    */     case 36:
/* 47 */       str = "CALL_DELIVERED_HELD";
/* 48 */       break;
/*    */     case 50:
/* 51 */       str = "CALL_RECEIVED";
/* 52 */       break;
/*    */     case 51:
/* 55 */       str = "CALL_ESTABLISHED";
/* 56 */       break;
/*    */     case 52:
/* 59 */       str = "CALL_ESTABLISHED_HELD";
/* 60 */       break;
/*    */     case 66:
/* 63 */       str = "CALL_RECEIVED_ON_HOLD";
/* 64 */       break;
/*    */     case 67:
/* 67 */       str = "CALL_ESTABLISHED_ON_HOLD";
/* 68 */       break;
/*    */     case 83:
/* 71 */       str = "CALL_QUEUED";
/* 72 */       break;
/*    */     case 84:
/* 75 */       str = "CALL_QUEUED_HELD";
/* 76 */       break;
/*    */     case 99:
/* 79 */       str = "CALL_FAILED";
/* 80 */       break;
/*    */     case 100:
/* 83 */       str = "CALL_FAILED_HELD";
/* 84 */       break;
/*    */     default:
/* 87 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 91 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASimpleCallState
 * JD-Core Version:    0.5.4
 */