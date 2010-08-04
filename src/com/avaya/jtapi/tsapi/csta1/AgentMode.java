/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class AgentMode extends ASNEnumerated
/*    */ {
/*    */   public static final short AM_LOG_IN = 0;
/*    */   public static final short AM_LOG_OUT = 1;
/*    */   public static final short AM_NOT_READY = 2;
/*    */   public static final short AM_READY = 3;
/*    */   public static final short AM_WORK_NOT_READY = 4;
/*    */   public static final short AM_WORK_READY = 5;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 20 */     switch (value)
/*    */     {
/*    */     case 0:
/* 23 */       str = "AM_LOG_IN";
/* 24 */       break;
/*    */     case 1:
/* 27 */       str = "AM_LOG_OUT";
/* 28 */       break;
/*    */     case 2:
/* 31 */       str = "AM_NOT_READY";
/* 32 */       break;
/*    */     case 3:
/* 35 */       str = "AM_READY";
/* 36 */       break;
/*    */     case 4:
/* 39 */       str = "AM_WORK_NOT_READY";
/* 40 */       break;
/*    */     case 5:
/* 43 */       str = "AM_WORK_READY";
/* 44 */       break;
/*    */     default:
/* 47 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 51 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.AgentMode
 * JD-Core Version:    0.5.4
 */