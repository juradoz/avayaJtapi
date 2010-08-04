/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV7ConferencedEvent extends LucentV6ConferencedEvent
/*    */ {
/*    */   private LucentDeviceHistoryEntry[] deviceHistory;
/*    */   CSTAExtendedDeviceID distributingVDN_asn;
/*    */   static final int PDU = 133;
/*    */ 
/*    */   public static LucentConferencedEvent decode(InputStream in)
/*    */   {
/* 28 */     LucentV7ConferencedEvent _this = new LucentV7ConferencedEvent();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 32 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 37 */     super.decodeMembers(memberStream);
/* 38 */     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
/* 39 */     this.distributingVDN_asn = CSTAExtendedDeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 44 */     super.encodeMembers(memberStream);
/* 45 */     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
/* 46 */     CSTAExtendedDeviceID.encode(this.distributingVDN_asn, memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeOCI(LucentOriginalCallInfo callInfo, OutputStream memberStream) {
/* 50 */     LucentV7OriginalCallInfo.encode(callInfo, memberStream);
/*    */   }
/*    */ 
/*    */   public LucentOriginalCallInfo decodeOCI(InputStream memberStream)
/*    */   {
/* 55 */     return LucentV7OriginalCallInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 60 */     Collection lines = new ArrayList();
/*    */ 
/* 62 */     lines.add("LucentV7ConferencedEvent ::=");
/* 63 */     lines.add("{");
/*    */ 
/* 65 */     String indent = "  ";
/*    */ 
/* 67 */     lines.addAll(LucentV7OriginalCallInfo.print((LucentV7OriginalCallInfo)this.originalCallInfo, "originalCallInfo", indent));
/* 68 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
/* 69 */     lines.addAll(UCID.print(this.ucid, "ucid", indent));
/* 70 */     lines.addAll(LucentTrunkInfoList.print(this.lucentTrunkInfo, "lucentTrunkInfo", indent));
/* 71 */     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
/* 72 */     lines.addAll(CSTAExtendedDeviceID.print(this.distributingVDN_asn, "distributingVDN", indent));
/*    */ 
/* 74 */     lines.add("}");
/* 75 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 80 */     return 133;
/*    */   }
/*    */ 
/*    */   public LucentDeviceHistoryEntry[] getDeviceHistory()
/*    */   {
/* 86 */     return this.deviceHistory;
/*    */   }
/*    */   public CSTAExtendedDeviceID getDistributingVDN_asn() {
/* 89 */     return this.distributingVDN_asn;
/*    */   }
/*    */ 
/*    */   public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
/* 93 */     this.deviceHistory = deviceHistory;
/*    */   }
/*    */ 
/*    */   public void setDistributingVDN_asn(CSTAExtendedDeviceID distributingVDN_asn) {
/* 97 */     this.distributingVDN_asn = distributingVDN_asn;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7ConferencedEvent
 * JD-Core Version:    0.5.4
 */