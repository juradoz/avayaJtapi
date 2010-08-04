/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class StreamType extends ASNEnumerated
/*    */ {
/*    */   public static final short ST_CSTA = 1;
/*    */   public static final short ST_OAM = 2;
/*    */   public static final short ST_DIRECTORY = 3;
/*    */   public static final short ST_NMSRV = 4;
/*    */   public static final short ST_CSTA_TRUSTED_APP = 5;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/* 18 */     Collection lines = new ArrayList();
/*    */     String str;
/* 21 */     switch (value)
/*    */     {
/*    */     case 1:
/* 24 */       str = "ST_CSTA";
/* 25 */       break;
/*    */     case 2:
/* 28 */       str = "ST_OAM";
/* 29 */       break;
/*    */     case 3:
/* 32 */       str = "ST_DIRECTORY";
/* 33 */       break;
/*    */     case 4:
/* 36 */       str = "ST_NMSRV";
/* 37 */       break;
/*    */     case 5:
/* 40 */       str = "ST_CSTA_TRUSTED_APP";
/* 41 */       break;
/*    */     default:
/* 44 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 48 */     lines.addAll(print(value, str, name, indent));
/* 49 */     return lines;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.StreamType
 * JD-Core Version:    0.5.4
 */