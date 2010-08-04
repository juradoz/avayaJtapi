/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class AgentState extends ASNEnumerated
/*    */ {
/*    */   public static final short AG_NOT_READY = 0;
/*    */   public static final short AG_NULL = 1;
/*    */   public static final short AG_READY = 2;
/*    */   public static final short AG_WORK_NOT_READY = 3;
/*    */   public static final short AG_WORK_READY = 4;
/*    */   public static final short AG_NOT_INITIALIZED = -1;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 24 */     switch (value)
/*    */     {
/*    */     case 0:
/* 27 */       str = "AG_NOT_READY";
/* 28 */       break;
/*    */     case 1:
/* 31 */       str = "AG_NULL";
/* 32 */       break;
/*    */     case 2:
/* 35 */       str = "AG_READY";
/* 36 */       break;
/*    */     case 3:
/* 39 */       str = "AG_WORK_NOT_READY";
/* 40 */       break;
/*    */     case 4:
/* 43 */       str = "AG_WORK_READY";
/* 44 */       break;
/*    */     default:
/* 47 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 51 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.AgentState
 * JD-Core Version:    0.5.4
 */