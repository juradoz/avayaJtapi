/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV5TransferredEvent extends LucentTransferredEvent
/*    */   implements HasUCID
/*    */ {
/*    */   String ucid;
/*    */   static final int PDU = 82;
/*    */ 
/*    */   public static LucentTransferredEvent decode(InputStream in)
/*    */   {
/* 15 */     LucentV5TransferredEvent _this = new LucentV5TransferredEvent();
/* 16 */     _this.doDecode(in);
/*    */ 
/* 18 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 22 */     super.encodeMembers(memberStream);
/* 23 */     UCID.encode(this.ucid, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 27 */     super.decodeMembers(memberStream);
/* 28 */     this.ucid = UCID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeOCI(LucentOriginalCallInfo callInfo, OutputStream memberStream) {
/* 32 */     LucentV5OriginalCallInfo.encode(callInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public LucentOriginalCallInfo decodeOCI(InputStream memberStream) {
/* 36 */     return LucentV5OriginalCallInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 41 */     Collection lines = new ArrayList();
/*    */ 
/* 43 */     lines.add("LucentV5TransferredEvent ::=");
/* 44 */     lines.add("{");
/*    */ 
/* 46 */     String indent = "  ";
/*    */ 
/* 48 */     lines.addAll(LucentV5OriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
/* 49 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/* 50 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/*    */ 
/* 52 */     lines.add("}");
/* 53 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 58 */     return 82;
/*    */   }
/*    */ 
/*    */   public String getUcid()
/*    */   {
/* 64 */     return this.ucid;
/*    */   }
/*    */   public void setUcid(String ucid) {
/* 67 */     this.ucid = ucid;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5TransferredEvent
 * JD-Core Version:    0.5.4
 */