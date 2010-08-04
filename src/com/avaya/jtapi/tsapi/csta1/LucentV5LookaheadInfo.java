/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public final class LucentV5LookaheadInfo extends LucentLookaheadInfo
/*    */ {
/*    */   public static LucentLookaheadInfo decode(InputStream in)
/*    */   {
/* 12 */     LucentV5LookaheadInfo _this = new LucentV5LookaheadInfo();
/* 13 */     _this.doDecode(in);
/* 14 */     if (_this.getType() == -1)
/*    */     {
/* 16 */       return null;
/*    */     }
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 23 */     super.encodeMembers(memberStream);
/* 24 */     UnicodeDeviceID.encode(this.sourceVDN, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 28 */     super.decodeMembers(memberStream);
/*    */ 
/* 30 */     this.sourceVDN = UnicodeDeviceID.decode(memberStream);
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5LookaheadInfo
 * JD-Core Version:    0.5.4
 */