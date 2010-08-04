/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class SDBLevel extends ASNEnumerated
/*    */ {
/*    */   static final short NO_SDB_CHECKING = -1;
/*    */   static final short ACS_ONLY = 1;
/*    */   static final short ACS_AND_CSTA_CHECKING = 0;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 17 */     switch (value)
/*    */     {
/*    */     case -1:
/* 20 */       str = "NO_SDB_CHECKING";
/* 21 */       break;
/*    */     case 1:
/* 24 */       str = "ACS_ONLY";
/* 25 */       break;
/*    */     case 0:
/* 28 */       str = "ACS_AND_CSTA_CHECKING";
/* 29 */       break;
/*    */     default:
/* 32 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 36 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.SDBLevel
 * JD-Core Version:    0.5.4
 */