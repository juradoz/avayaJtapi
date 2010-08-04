/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LinkState extends ASNEnumerated
/*    */ {
/*    */   public static final short LS_LINK_UNAVAIL = 0;
/*    */   public static final short LS_LINK_UP = 1;
/*    */   public static final short LS_LINK_DOWN = 2;
/*    */ 
/*    */   public static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 18 */     switch (value)
/*    */     {
/*    */     case 0:
/* 20 */       str = "LS_LINK_UNAVAIL";
/* 21 */       break;
/*    */     case 1:
/* 23 */       str = "LS_LINK_UP";
/* 24 */       break;
/*    */     case 2:
/* 26 */       str = "LS_LINK_DOWN";
/* 27 */       break;
/*    */     default:
/* 29 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 32 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LinkState
 * JD-Core Version:    0.5.4
 */