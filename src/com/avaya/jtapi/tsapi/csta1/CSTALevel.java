/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTALevel extends ASNEnumerated
/*    */ {
/*    */   public static final short CSTA_HOME_WORK_TOP = 1;
/*    */   public static final short CSTA_AWAY_WORK_TOP = 2;
/*    */   public static final short CSTA_DEVICE_DEVICE_MONITOR = 3;
/*    */   public static final short CSTA_CALL_DEVICE_MONITOR = 4;
/*    */   public static final short CSTA_CALL_CONTROL = 5;
/*    */   public static final short CSTA_ROUTING = 6;
/*    */   public static final short CSTA_CALL_CALL_MONITOR = 7;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 21 */     switch (value)
/*    */     {
/*    */     case 1:
/* 24 */       str = "CSTA_HOME_WORK_TOP";
/* 25 */       break;
/*    */     case 2:
/* 28 */       str = "CSTA_AWAY_WORK_TOP";
/* 29 */       break;
/*    */     case 3:
/* 32 */       str = "CSTA_DEVICE_DEVICE_MONITOR";
/* 33 */       break;
/*    */     case 4:
/* 36 */       str = "CSTA_CALL_DEVICE_MONITOR";
/* 37 */       break;
/*    */     case 5:
/* 40 */       str = "CSTA_CALL_CONTROL";
/* 41 */       break;
/*    */     case 6:
/* 44 */       str = "CSTA_ROUTING";
/* 45 */       break;
/*    */     case 7:
/* 48 */       str = "CSTA_CALL_CALL_MONITOR";
/* 49 */       break;
/*    */     default:
/* 52 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 56 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTALevel
 * JD-Core Version:    0.5.4
 */