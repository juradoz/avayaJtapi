/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentUseUnifiedDesktopLicenseConfEvent extends LucentPrivateData
/*    */ {
/*    */   static final int PDU = 140;
/*    */ 
/*    */   public static LucentUseUnifiedDesktopLicenseConfEvent decode(InputStream in)
/*    */   {
/* 28 */     LucentUseUnifiedDesktopLicenseConfEvent _this = new LucentUseUnifiedDesktopLicenseConfEvent();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 38 */     Collection lines = new ArrayList();
/* 39 */     lines.add("LucentUseUnifiedDesktopLicenseConfEvent ::=");
/* 40 */     lines.add("{");
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 48 */     return 140;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentUseUnifiedDesktopLicenseConfEvent
 * JD-Core Version:    0.5.4
 */