/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LucentV7EstablishedEvent extends LucentV5EstablishedEvent
/*    */ {
/*    */   private LucentDeviceHistoryEntry[] deviceHistory;
/*    */   CSTAExtendedDeviceID distributingVDN_asn;
/*    */   static final int PDU = 129;
/*    */ 
/*    */   public static LucentEstablishedEvent decode(InputStream in)
/*    */   {
/* 19 */     LucentV7EstablishedEvent _this = new LucentV7EstablishedEvent();
/* 20 */     _this.doDecode(in);
/*    */ 
/* 23 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 28 */     super.decodeMembers(memberStream);
/* 29 */     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
/* 30 */     this.distributingVDN_asn = CSTAExtendedDeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 35 */     super.encodeMembers(memberStream);
/* 36 */     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
/* 37 */     CSTAExtendedDeviceID.encode(this.distributingVDN_asn, memberStream);
/*    */   }
/*    */ 
/*    */   public LucentOriginalCallInfo decodeOCI(InputStream memberStream)
/*    */   {
/* 42 */     return LucentV7OriginalCallInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeOCI(LucentOriginalCallInfo callInfo, OutputStream memberStream) {
/* 46 */     LucentV7OriginalCallInfo.encode(callInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 51 */     Collection lines = new ArrayList();
/*    */ 
/* 53 */     lines.add("LucentV7EstablishedEvent ::=");
/* 54 */     lines.add("{");
/*    */ 
/* 56 */     String indent = "  ";
/*    */ 
/* 58 */     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
/* 59 */     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
/* 60 */     lines.addAll(DeviceID.print(this.split_asn, "split", indent));
/* 61 */     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)this.lookaheadInfo, "lookaheadInfo", indent));
/* 62 */     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
/* 63 */     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
/* 64 */     lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
/* 65 */     lines.addAll(LucentV7OriginalCallInfo.print((LucentV7OriginalCallInfo)this.originalCallInfo, "originalCallInfo", indent));
/* 66 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/* 67 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/* 68 */     lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo, "callOriginatorInfo", indent));
/* 69 */     lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling", indent));
/* 70 */     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
/* 71 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingVDN_asn, "distributingVDN", indent));
/*    */ 
/* 73 */     lines.add("}");
/* 74 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 79 */     return 129;
/*    */   }
/*    */ 
/*    */   public LucentDeviceHistoryEntry[] getDeviceHistory()
/*    */   {
/* 85 */     return this.deviceHistory;
/*    */   }
/*    */   public CSTAExtendedDeviceID getDistributingVDN_asn() {
/* 88 */     return this.distributingVDN_asn;
/*    */   }
/*    */ 
/*    */   public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
/* 92 */     this.deviceHistory = deviceHistory;
/*    */   }
/*    */ 
/*    */   public void setDistributingVDN_asn(CSTAExtendedDeviceID distributingVDN_asn) {
/* 96 */     this.distributingVDN_asn = distributingVDN_asn;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7EstablishedEvent
 * JD-Core Version:    0.5.4
 */