/*    */ package com.avaya.jtapi.tsapi.acs;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class ACSAuthType extends ASNEnumerated
/*    */ {
/*    */   static final short REQUIRES_EXTERNAL_AUTH = -1;
/*    */   static final short AUTH_LOGIN_ID_ONLY = 0;
/*    */   static final short AUTH_LOGIN_ID_IS_DEFAULT = 1;
/*    */   static final short NEED_LOGIN_ID_AND_PASSWD = 2;
/*    */   static final short ANY_LOGIN_ID = 3;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/* 18 */     Collection lines = new ArrayList();
/*    */     String str;
/* 21 */     switch (value)
/*    */     {
/*    */     case -1:
/* 24 */       str = "REQUIRES_EXTERNAL_AUTH";
/* 25 */       break;
/*    */     case 0:
/* 28 */       str = "AUTH_LOGIN_ID_ONLY";
/* 29 */       break;
/*    */     case 1:
/* 32 */       str = "AUTH_LOGIN_ID_IS_DEFAULT";
/* 33 */       break;
/*    */     case 2:
/* 36 */       str = "NEED_LOGIN_ID_AND_PASSWD";
/* 37 */       break;
/*    */     case 3:
/* 40 */       str = "ANY_LOGIN_ID";
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
 * Qualified Name:     com.avaya.jtapi.tsapi.acs.ACSAuthType
 * JD-Core Version:    0.5.4
 */