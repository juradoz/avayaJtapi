/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class SystemStatus extends ASNEnumerated
/*    */ {
/*    */   public static final short SS_INITIALIZING = 0;
/*    */   public static final short SS_ENABLED = 1;
/*    */   public static final short SS_NORMAL = 2;
/*    */   public static final short SS_MESSAGES_LOST = 3;
/*    */   public static final short SS_DISABLED = 4;
/*    */   public static final short SS_OVERLOAD_IMMINENT = 5;
/*    */   public static final short SS_OVERLOAD_REACHED = 6;
/*    */   public static final short SS_OVERLOAD_RELIEVED = 7;
/*    */ 
/*    */   public static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 28 */     switch (value)
/*    */     {
/*    */     case 0:
/* 31 */       str = "SS_INITIALIZING";
/* 32 */       break;
/*    */     case 1:
/* 35 */       str = "SS_ENABLED";
/* 36 */       break;
/*    */     case 2:
/* 39 */       str = "SS_NORMAL";
/* 40 */       break;
/*    */     case 3:
/* 43 */       str = "SS_MESSAGES_LOST";
/* 44 */       break;
/*    */     case 4:
/* 47 */       str = "SS_DISABLED";
/* 48 */       break;
/*    */     case 5:
/* 51 */       str = "SS_OVERLOAD_IMMINENT";
/* 52 */       break;
/*    */     case 6:
/* 55 */       str = "SS_OVERLOAD_REACHED";
/* 56 */       break;
/*    */     case 7:
/* 59 */       str = "SS_OVERLOAD_RELIEVED";
/* 60 */       break;
/*    */     default:
/* 62 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 66 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.SystemStatus
 * JD-Core Version:    0.5.4
 */