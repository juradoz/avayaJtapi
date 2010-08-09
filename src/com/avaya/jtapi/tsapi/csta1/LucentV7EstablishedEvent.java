 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentV7EstablishedEvent extends LucentV5EstablishedEvent
 {
   private LucentDeviceHistoryEntry[] deviceHistory;
   CSTAExtendedDeviceID distributingVDN_asn;
   static final int PDU = 129;
 
   public static LucentEstablishedEvent decode(InputStream in)
   {
     LucentV7EstablishedEvent _this = new LucentV7EstablishedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     super.decodeMembers(memberStream);
     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
     this.distributingVDN_asn = CSTAExtendedDeviceID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     super.encodeMembers(memberStream);
     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
     CSTAExtendedDeviceID.encode(this.distributingVDN_asn, memberStream);
   }
 
   public LucentOriginalCallInfo decodeOCI(InputStream memberStream)
   {
     return LucentV7OriginalCallInfo.decode(memberStream);
   }
 
   public void encodeOCI(LucentOriginalCallInfo callInfo, OutputStream memberStream) {
     LucentV7OriginalCallInfo.encode(callInfo, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentV7EstablishedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
     lines.addAll(DeviceID.print(this.split_asn, "split", indent));
     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)this.lookaheadInfo, "lookaheadInfo", indent));
     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
     lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
     lines.addAll(LucentV7OriginalCallInfo.print((LucentV7OriginalCallInfo)this.originalCallInfo, "originalCallInfo", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
     lines.addAll(UCID.print(this.ucid, "ucid", indent));
     lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo, "callOriginatorInfo", indent));
     lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling", indent));
     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.distributingVDN_asn, "distributingVDN", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 129;
   }
 
   public LucentDeviceHistoryEntry[] getDeviceHistory()
   {
     return this.deviceHistory;
   }
   public CSTAExtendedDeviceID getDistributingVDN_asn() {
     return this.distributingVDN_asn;
   }
 
   public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
     this.deviceHistory = deviceHistory;
   }
 
   public void setDistributingVDN_asn(CSTAExtendedDeviceID distributingVDN_asn) {
     this.distributingVDN_asn = distributingVDN_asn;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7EstablishedEvent
 * JD-Core Version:    0.5.4
 */