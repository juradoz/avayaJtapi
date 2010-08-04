/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ProgressLocation extends ASNEnumerated
/*    */ {
/*    */   public static final short PL_USER = 0;
/*    */   public static final short PL_PUB_LOCAL = 1;
/*    */   public static final short PL_PUB_REMOTE = 4;
/*    */   public static final short PL_PRIV_REMOTE = 5;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 18 */     switch (value)
/*    */     {
/*    */     case 0:
/* 21 */       str = "PL_USER";
/* 22 */       break;
/*    */     case 1:
/* 25 */       str = "PL_PUB_LOCAL";
/* 26 */       break;
/*    */     case 4:
/* 29 */       str = "PL_PUB_REMOTE";
/* 30 */       break;
/*    */     case 5:
/* 33 */       str = "PL_PRIV_REMOTE";
/* 34 */       break;
/*    */     case 2:
/*    */     case 3:
/*    */     default:
/* 37 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 41 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.ProgressLocation
 * JD-Core Version:    0.5.4
 */