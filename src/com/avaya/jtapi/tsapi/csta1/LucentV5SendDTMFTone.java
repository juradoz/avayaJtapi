/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public final class LucentV5SendDTMFTone extends LucentSendDTMFTone
/*    */ {
/*    */   static final int PDU = 71;
/*    */ 
/*    */   public LucentV5SendDTMFTone()
/*    */   {
/*    */   }
/*    */ 
/*    */   public LucentV5SendDTMFTone(CSTAConnectionID _sender, CSTAConnectionID[] _receivers, String _tones, int _toneDuration, int _pauseDuration)
/*    */   {
/* 17 */     super(_sender, _receivers, _tones, _toneDuration, _pauseDuration);
/*    */   }
/*    */ 
/*    */   public static LucentSendDTMFTone decode(InputStream in) {
/* 21 */     LucentV5SendDTMFTone _this = new LucentV5SendDTMFTone();
/* 22 */     _this.doDecode(in);
/*    */ 
/* 24 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 28 */     super.encodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 33 */     super.decodeMembers(memberStream);
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 39 */     return 71;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5SendDTMFTone
 * JD-Core Version:    0.5.4
 */