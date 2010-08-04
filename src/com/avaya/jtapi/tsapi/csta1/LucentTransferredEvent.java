/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentTransferredEvent extends LucentPrivateData
/*    */ {
/*    */   LucentOriginalCallInfo originalCallInfo;
/*    */   CSTAExtendedDeviceID distributingDevice_asn;
/*    */   static final int PDU = 62;
/*    */ 
/*    */   public static LucentTransferredEvent decode(InputStream in)
/*    */   {
/* 18 */     LucentTransferredEvent _this = new LucentTransferredEvent();
/* 19 */     _this.doDecode(in);
/*    */ 
/* 21 */     return _this;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 25 */     encodeOCI(this.originalCallInfo, memberStream);
/* 26 */     CSTAExtendedDeviceID.encode(this.distributingDevice_asn, memberStream);
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 30 */     this.originalCallInfo = decodeOCI(memberStream);
/* 31 */     this.distributingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 36 */     Collection lines = new ArrayList();
/*    */ 
/* 38 */     lines.add("LucentTransferredEvent ::=");
/* 39 */     lines.add("{");
/*    */ 
/* 41 */     String indent = "  ";
/*    */ 
/* 43 */     lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
/* 44 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/*    */ 
/* 46 */     lines.add("}");
/* 47 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 52 */     return 62;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getDistributingDevice_asn()
/*    */   {
/* 58 */     return this.distributingDevice_asn;
/*    */   }
/*    */   public void setDistributingDevice_asn(CSTAExtendedDeviceID _distributingDevice_asn) {
/* 61 */     this.distributingDevice_asn = _distributingDevice_asn;
/*    */   }
/*    */ 
/*    */   public LucentOriginalCallInfo getOriginalCallInfo()
/*    */   {
/* 68 */     return this.originalCallInfo;
/*    */   }
/*    */   public void setOriginalCallInfo(LucentOriginalCallInfo _info) {
/* 71 */     this.originalCallInfo = _info;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentTransferredEvent
 * JD-Core Version:    0.5.4
 */