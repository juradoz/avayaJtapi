/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentLocalCallState extends ASNEnumerated
/*    */ {
/*    */   static final short ATT_CS_INITIATED = 1;
/*    */   static final short ATT_CS_ALERTING = 2;
/*    */   static final short ATT_CS_CONNECTED = 3;
/*    */   static final short ATT_CS_HELD = 4;
/*    */   static final short ATT_CS_BRIDGED = 5;
/*    */   static final short ATT_CS_OTHER = 6;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 20 */     switch (value)
/*    */     {
/*    */     case 1:
/* 23 */       str = "ATT_CS_INITIATED";
/* 24 */       break;
/*    */     case 2:
/* 27 */       str = "ATT_CS_ALERTING";
/* 28 */       break;
/*    */     case 3:
/* 31 */       str = "ATT_CS_CONNECTED";
/* 32 */       break;
/*    */     case 4:
/* 35 */       str = "ATT_CS_HELD";
/* 36 */       break;
/*    */     case 5:
/* 39 */       str = "ATT_CS_BRIDGED";
/* 40 */       break;
/*    */     case 6:
/* 43 */       str = "ATT_CS_OTHER";
/* 44 */       break;
/*    */     default:
/* 47 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 51 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentLocalCallState
 * JD-Core Version:    0.5.4
 */