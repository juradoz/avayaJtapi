/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentExtensionClass extends ASNEnumerated
/*    */ {
/*    */   public static final short EC_VDN = 0;
/*    */   public static final short EC_ACD_SPLIT = 1;
/*    */   public static final short EC_ANNOUNCEMENT = 2;
/*    */   public static final short EC_DATA = 4;
/*    */   public static final short EC_ANALOG = 5;
/*    */   public static final short EC_PROPRIETARY = 6;
/*    */   public static final short EC_BRI = 7;
/*    */   public static final short EC_CTI = 8;
/*    */   public static final short EC_LOGICAL_AGENT = 9;
/*    */   public static final short EC_OTHER = 10;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 24 */     switch (value)
/*    */     {
/*    */     case 0:
/* 27 */       str = "EC_VDN";
/* 28 */       break;
/*    */     case 1:
/* 31 */       str = "EC_ACD_SPLIT";
/* 32 */       break;
/*    */     case 2:
/* 35 */       str = "EC_ANNOUNCEMENT";
/* 36 */       break;
/*    */     case 4:
/* 39 */       str = "EC_DATA";
/* 40 */       break;
/*    */     case 5:
/* 43 */       str = "EC_ANALOG";
/* 44 */       break;
/*    */     case 6:
/* 47 */       str = "EC_PROPRIETARY";
/* 48 */       break;
/*    */     case 7:
/* 51 */       str = "EC_BRI";
/* 52 */       break;
/*    */     case 8:
/* 55 */       str = "EC_CTI";
/* 56 */       break;
/*    */     case 9:
/* 59 */       str = "EC_LOGICAL_AGENT";
/* 60 */       break;
/*    */     case 10:
/* 63 */       str = "EC_OTHER";
/* 64 */       break;
/*    */     case 3:
/*    */     default:
/* 67 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 71 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentExtensionClass
 * JD-Core Version:    0.5.4
 */