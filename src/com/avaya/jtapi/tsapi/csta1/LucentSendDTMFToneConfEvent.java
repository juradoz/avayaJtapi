/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNNull;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentSendDTMFToneConfEvent extends LucentPrivateData
/*    */ {
/*    */   static final int PDU = 9;
/*    */ 
/*    */   static LucentSendDTMFToneConfEvent decode(InputStream in)
/*    */   {
/* 14 */     LucentSendDTMFToneConfEvent _this = new LucentSendDTMFToneConfEvent();
/* 15 */     _this.doDecode(in);
/*    */ 
/* 17 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 21 */     ASNNull.encode(memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 25 */     ASNNull.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 30 */     Collection lines = new ArrayList();
/*    */ 
/* 32 */     lines.add("LucentSendDTMFToneConfEvent ::=");
/* 33 */     lines.add("{");
/*    */ 
/* 35 */     String indent = "  ";
/*    */ 
/* 37 */     lines.addAll(ASNNull.print(indent));
/*    */ 
/* 39 */     lines.add("}");
/* 40 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 45 */     return 9;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSendDTMFToneConfEvent
 * JD-Core Version:    0.5.4
 */