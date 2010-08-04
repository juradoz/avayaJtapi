/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
/*    */ import java.io.InputStream;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class UUIProtocolType extends ASNEnumerated
/*    */ {
/*    */   public static final short UUI_NONE = -1;
/*    */   public static final short UUI_USER_SPECIFIC = 0;
/*    */   public static final short UUI_IA5_ASCII = 4;
/*    */   public static final short UUI_Q931_CALLCTRL = 8;
/*    */ 
/*    */   public static LucentUserToUserInfo decodeUserToUserInfo(InputStream memberStream, CSTATSProvider provider)
/*    */   {
/* 33 */     LucentUserToUserInfo _this = new LucentUserToUserInfo();
/* 34 */     _this.doDecode(memberStream);
/*    */ 
/* 36 */     switch (_this.getType())
/*    */     {
/*    */     case -1:
/* 40 */       _this = null;
/* 41 */       break;
/*    */     case 0:
/*    */     case 1:
/*    */     case 2:
/*    */     case 3:
/*    */     case 4:
/*    */     case 5:
/*    */     case 6:
/*    */     case 7:
/*    */     default:
/* 48 */       break;
/*    */     case 8:
/* 52 */       LucentQ931UserToUserInfo _alternate = new LucentQ931UserToUserInfo(_this.getBytes());
/*    */ 
/* 54 */       _this = _alternate;
/*    */     }
/*    */ 
/* 57 */     return _this;
/*    */   }
/*    */ 
/*    */   static Collection<String> print(short value, String name, String indent)
/*    */   {
/*    */     String str;
/* 64 */     switch (value)
/*    */     {
/*    */     case -1:
/* 67 */       str = "UUI_NONE";
/* 68 */       break;
/*    */     case 0:
/* 71 */       str = "UUI_USER_SPECIFIC";
/* 72 */       break;
/*    */     case 4:
/* 75 */       str = "UUI_IA5_ASCII";
/* 76 */       break;
/*    */     case 8:
/* 79 */       str = "UUI_Q931_CALLCTRL";
/* 80 */       break;
/*    */     case 1:
/*    */     case 2:
/*    */     case 3:
/*    */     case 5:
/*    */     case 6:
/*    */     case 7:
/*    */     default:
/* 83 */       str = "?? " + value + " ??";
/*    */     }
/*    */ 
/* 87 */     return print(value, str, name, indent);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.UUIProtocolType
 * JD-Core Version:    0.5.4
 */