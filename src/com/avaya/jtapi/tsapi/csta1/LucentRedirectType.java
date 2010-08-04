/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentRedirectType extends ASNEnumerated
/*    */ {
/*    */   public static final short VDN = 0;
/*    */   public static final short NETWORK = 1;
/*    */ 
/*    */   public static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 30 */     switch (value)
/*    */     {
/*    */     case 1:
/* 33 */       str = "NETWORK";
/* 34 */       break;
/*    */     case 0:
/* 37 */       str = "VDN";
/* 38 */       break;
/*    */     default:
/* 41 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 45 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentRedirectType
 * JD-Core Version:    0.5.4
 */