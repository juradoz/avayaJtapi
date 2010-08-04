/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentTalkState extends ASNEnumerated
/*    */ {
/*    */   public static final short TS_ON_CALL = 0;
/*    */   public static final short TS_IDLE = 1;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 16 */     switch (value)
/*    */     {
/*    */     case 0:
/* 19 */       str = "TS_ON_CALL";
/* 20 */       break;
/*    */     case 1:
/* 23 */       str = "TS_IDLE";
/* 24 */       break;
/*    */     default:
/* 27 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 31 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentTalkState
 * JD-Core Version:    0.5.4
 */