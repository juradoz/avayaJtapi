/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentReasonCode extends ASNEnumerated
/*    */ {
/*    */   public static final short AR_NONE = 0;
/*    */   public static final short AR_ANSWER_NORMAL = 1;
/*    */   public static final short AR_ANSWER_TIMED = 2;
/*    */   public static final short AR_ANSWER_VOICE_ENERGY = 3;
/*    */   public static final short AR_ANSWER_MACHINE_DETECTED = 4;
/*    */   public static final short AR_SIT_REORDER = 5;
/*    */   public static final short AR_SIT_NO_CIRCUIT = 6;
/*    */   public static final short AR_SIT_INTERCEPT = 7;
/*    */   public static final short AR_SIT_VACANT_CODE = 8;
/*    */   public static final short AR_SIT_INEFFECTIVE_OTHER = 9;
/*    */   public static final short AR_SIT_UNKNOWN = 10;
/*    */   public static final short AR_IN_QUEUE = 11;
/*    */   public static final short AR_SERVICE_OBSERVER = 12;
/*    */ 
/*    */   public static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 27 */     switch (value)
/*    */     {
/*    */     case 0:
/* 30 */       str = "AR_NONE";
/* 31 */       break;
/*    */     case 1:
/* 34 */       str = "AR_ANSWER_NORMAL";
/* 35 */       break;
/*    */     case 2:
/* 38 */       str = "AR_ANSWER_TIMED";
/* 39 */       break;
/*    */     case 3:
/* 42 */       str = "AR_ANSWER_VOICE_ENERGY";
/* 43 */       break;
/*    */     case 4:
/* 46 */       str = "AR_ANSWER_MACHINE_DETECTED";
/* 47 */       break;
/*    */     case 5:
/* 50 */       str = "AR_SIT_REORDER";
/* 51 */       break;
/*    */     case 6:
/* 54 */       str = "AR_SIT_NO_CIRCUIT";
/* 55 */       break;
/*    */     case 7:
/* 58 */       str = "AR_SIT_INTERCEPT";
/* 59 */       break;
/*    */     case 8:
/* 62 */       str = "AR_SIT_VACANT_CODE";
/* 63 */       break;
/*    */     case 9:
/* 66 */       str = "AR_SIT_INEFFECTIVE_OTHER";
/* 67 */       break;
/*    */     case 10:
/* 70 */       str = "AR_SIT_UNKNOWN";
/* 71 */       break;
/*    */     case 11:
/* 74 */       str = "AR_IN_QUEUE";
/* 75 */       break;
/*    */     case 12:
/* 78 */       str = "AR_SERVICE_OBSERVER";
/* 79 */       break;
/*    */     default:
/* 82 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 86 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentReasonCode
 * JD-Core Version:    0.5.4
 */