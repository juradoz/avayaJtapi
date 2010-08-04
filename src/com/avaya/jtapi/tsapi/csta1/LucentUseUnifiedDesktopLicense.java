/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentUseUnifiedDesktopLicense extends LucentPrivateData
/*    */ {
/*    */   public static final int PDU = 139;
/*    */ 
/*    */   public static LucentUseUnifiedDesktopLicense decode(InputStream in)
/*    */   {
/* 30 */     LucentUseUnifiedDesktopLicense _this = new LucentUseUnifiedDesktopLicense();
/* 31 */     _this.doDecode(in);
/*    */ 
/* 33 */     return _this;
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 37 */     Collection lines = new ArrayList();
/* 38 */     lines.add("LucentUseUnifiedDesktopLicense ::=");
/* 39 */     lines.add("{");
/* 40 */     lines.add("}");
/* 41 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 139;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentUseUnifiedDesktopLicense
 * JD-Core Version:    0.5.4
 */