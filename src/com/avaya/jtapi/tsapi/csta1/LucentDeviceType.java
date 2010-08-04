/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentDeviceType extends ASNEnumerated
/*    */ {
/*    */   public static final short ATT_DT_ACD_SPLIT = 1;
/*    */   public static final short ATT_DT_ANNOUNCEMENT = 2;
/*    */   public static final short ATT_DT_DATA = 3;
/*    */   public static final short ATT_DT_LOGICAL_AGENT = 4;
/*    */   public static final short ATT_DT_STATION = 5;
/*    */   public static final short ATT_DT_TRUNK_ACCESS_CODE = 6;
/*    */   public static final short ATT_DT_VDN = 7;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 21 */     switch (value)
/*    */     {
/*    */     case 1:
/* 24 */       str = "ATT_DT_ACD_SPLIT";
/* 25 */       break;
/*    */     case 2:
/* 28 */       str = "ATT_DT_ANNOUNCEMENT";
/* 29 */       break;
/*    */     case 3:
/* 32 */       str = "ATT_DT_DATA";
/* 33 */       break;
/*    */     case 4:
/* 36 */       str = "ATT_DT_LOGICAL_AGENT";
/* 37 */       break;
/*    */     case 5:
/* 40 */       str = "ATT_DT_STATION";
/* 41 */       break;
/*    */     case 6:
/* 44 */       str = "ATT_DT_TRUNK_ACCESS_CODE";
/* 45 */       break;
/*    */     case 7:
/* 48 */       str = "ATT_DT_VDN";
/* 49 */       break;
/*    */     default:
/* 52 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 56 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentDeviceType
 * JD-Core Version:    0.5.4
 */