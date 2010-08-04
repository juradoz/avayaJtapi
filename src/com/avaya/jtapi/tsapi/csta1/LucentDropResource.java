/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentDropResource extends ASNEnumerated
/*    */ {
/*    */   public static final short DR_NONE = -1;
/*    */   public static final short DR_CALL_CLASSIFIER = 0;
/*    */   public static final short DR_TONE_GENERATOR = 1;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 17 */     switch (value)
/*    */     {
/*    */     case -1:
/* 20 */       str = "DR_NONE";
/* 21 */       break;
/*    */     case 0:
/* 24 */       str = "DR_CALL_CLASSIFIER";
/* 25 */       break;
/*    */     case 1:
/* 28 */       str = "DR_TONE_GENERATOR";
/* 29 */       break;
/*    */     default:
/* 32 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 36 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentDropResource
 * JD-Core Version:    0.5.4
 */