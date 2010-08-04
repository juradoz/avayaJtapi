/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSEncodeType extends ASNEnumerated
/*    */ {
/*    */   static final short CAN_USE_BINDERY_ENCRYPTION = 1;
/*    */   static final short NDS_AUTH_CONNID = 2;
/*    */   static final short WIN_NT_LOCAL = 3;
/*    */   static final short WIN_NT_NAMED_PIPE = 4;
/*    */   static final short WIN_NT_WRITE_DATA = 5;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/* 18 */     Collection lines = new ArrayList();
/*    */     String str;
/* 21 */     switch (value)
/*    */     {
/*    */     case 1:
/* 24 */       str = "CAN_USE_BINDERY_ENCRYPTION";
/* 25 */       break;
/*    */     case 2:
/* 28 */       str = "NDS_AUTH_CONNID";
/* 29 */       break;
/*    */     case 3:
/* 32 */       str = "WIN_NT_LOCAL";
/* 33 */       break;
/*    */     case 4:
/* 36 */       str = "WIN_NT_NAMED_PIPE";
/* 37 */       break;
/*    */     case 5:
/* 40 */       str = "WIN_NT_WRITE_DATA";
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
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSEncodeType
 * JD-Core Version:    0.5.4
 */