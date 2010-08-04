/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class DeviceType extends ASNEnumerated
/*    */ {
/*    */   public static final short DT_STATION = 0;
/*    */   public static final short DT_LINE = 1;
/*    */   public static final short DT_BUTTON = 2;
/*    */   public static final short DT_ACD = 3;
/*    */   public static final short DT_TRUNK = 4;
/*    */   public static final short DT_OPERATOR = 5;
/*    */   public static final short DT_STATION_GROUP = 16;
/*    */   public static final short DT_LINE_GROUP = 17;
/*    */   public static final short DT_BUTTON_GROUP = 18;
/*    */   public static final short DT_ACD_GROUP = 19;
/*    */   public static final short DT_TRUNK_GROUP = 20;
/*    */   public static final short DT_OPERATOR_GROUP = 21;
/*    */   public static final short DT_OTHER = 255;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 27 */     switch (value)
/*    */     {
/*    */     case 0:
/* 30 */       str = "DT_STATION";
/* 31 */       break;
/*    */     case 1:
/* 34 */       str = "DT_LINE";
/* 35 */       break;
/*    */     case 2:
/* 38 */       str = "DT_BUTTON";
/* 39 */       break;
/*    */     case 3:
/* 42 */       str = "DT_ACD";
/* 43 */       break;
/*    */     case 4:
/* 46 */       str = "DT_TRUNK";
/* 47 */       break;
/*    */     case 5:
/* 50 */       str = "DT_OPERATOR";
/* 51 */       break;
/*    */     case 16:
/* 54 */       str = "DT_STATION_GROUP";
/* 55 */       break;
/*    */     case 17:
/* 58 */       str = "DT_LINE_GROUP";
/* 59 */       break;
/*    */     case 18:
/* 62 */       str = "DT_BUTTON_GROUP";
/* 63 */       break;
/*    */     case 19:
/* 66 */       str = "DT_ACD_GROUP";
/* 67 */       break;
/*    */     case 20:
/* 70 */       str = "DT_TRUNK_GROUP";
/* 71 */       break;
/*    */     case 21:
/* 74 */       str = "DT_OPERATOR_GROUP";
/* 75 */       break;
/*    */     case 255:
/* 78 */       str = "DT_OTHER";
/* 79 */       break;
/*    */     default:
/* 82 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 86 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.DeviceType
 * JD-Core Version:    0.5.4
 */