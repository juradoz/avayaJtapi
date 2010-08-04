/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class Level extends ASNEnumerated
/*    */ {
/*    */   public static final short ACS_LEVEL1 = 1;
/*    */   public static final short ACS_LEVEL2 = 2;
/*    */   public static final short ACS_LEVEL3 = 3;
/*    */   public static final short ACS_LEVEL4 = 4;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/* 17 */     Collection lines = new ArrayList();
/*    */     String str;
/* 20 */     switch (value)
/*    */     {
/*    */     case 1:
/* 23 */       str = "ACS_LEVEL1";
/* 24 */       break;
/*    */     case 2:
/* 27 */       str = "ACS_LEVEL2";
/* 28 */       break;
/*    */     case 3:
/* 31 */       str = "ACS_LEVEL3";
/* 32 */       break;
/*    */     case 4:
/* 35 */       str = "ACS_LEVEL4";
/* 36 */       break;
/*    */     default:
/* 39 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 43 */     lines.addAll(print(value, str, name, indent));
/* 44 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.Level
 * JD-Core Version:    0.5.4
 */