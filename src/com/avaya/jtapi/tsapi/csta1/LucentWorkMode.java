/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentWorkMode extends ASNEnumerated
/*    */ {
/*    */   public static final short WM_NONE = -1;
/*    */   public static final short WM_AUX_WORK = 1;
/*    */   public static final short WM_AFTCAL_WK = 2;
/*    */   public static final short WM_AUTO_IN = 3;
/*    */   public static final short WM_MANUAL_IN = 4;
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 19 */     switch (value)
/*    */     {
/*    */     case -1:
/* 22 */       str = "WM_NONE";
/* 23 */       break;
/*    */     case 1:
/* 26 */       str = "WM_AUX_WORK";
/* 27 */       break;
/*    */     case 2:
/* 30 */       str = "WM_AFTCAL_WK";
/* 31 */       break;
/*    */     case 3:
/* 34 */       str = "WM_AUTO_IN";
/* 35 */       break;
/*    */     case 4:
/* 38 */       str = "WM_MANUAL_IN";
/* 39 */       break;
/*    */     case 0:
/*    */     default:
/* 42 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 46 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentWorkMode
 * JD-Core Version:    0.5.4
 */