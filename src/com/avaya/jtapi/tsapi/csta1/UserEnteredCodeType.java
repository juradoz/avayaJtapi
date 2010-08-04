/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class UserEnteredCodeType extends ASNEnumerated
/*    */ {
/*    */   static final short UE_NONE = -1;
/*    */   public static final short UE_ANY = 0;
/*    */   public static final short UE_LOGIN_DIGITS = 2;
/*    */   public static final short UE_CALL_PROMPTER = 5;
/*    */   public static final short UE_DATA_BASE_PROVIDED = 17;
/*    */   public static final short UE_TONE_DETECTOR = 32;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 20 */     switch (value)
/*    */     {
/*    */     case -1:
/* 23 */       str = "UE_NONE";
/* 24 */       break;
/*    */     case 0:
/* 27 */       str = "UE_ANY";
/* 28 */       break;
/*    */     case 2:
/* 31 */       str = "UE_LOGIN_DIGITS";
/* 32 */       break;
/*    */     case 5:
/* 35 */       str = "UE_CALL_PROMPTER";
/* 36 */       break;
/*    */     case 17:
/* 39 */       str = "UE_DATA_BASE_PROVIDED";
/* 40 */       break;
/*    */     case 32:
/* 43 */       str = "UE_TONE_DETECTOR";
/* 44 */       break;
/*    */     default:
/* 47 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 51 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.UserEnteredCodeType
 * JD-Core Version:    0.5.4
 */