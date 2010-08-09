 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentEstablishedEvent extends LucentPrivateData
 {
   String trunkGroup;
   String trunkMember;
   String split_asn;
   LucentLookaheadInfo lookaheadInfo;
   LucentUserEnteredCode userEnteredCode;
   LucentUserToUserInfo userInfo;
   short reason;
   LucentOriginalCallInfo originalCallInfo;
   CSTAExtendedDeviceID distributingDevice_asn;
   static final int PDU = 61;
 
   static LucentEstablishedEvent decode(InputStream in)
   {
     LucentEstablishedEvent _this = new LucentEstablishedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.trunkGroup = DeviceID.decode(memberStream);
     this.trunkMember = DeviceID.decode(memberStream);
     this.split_asn = DeviceID.decode(memberStream);
     this.lookaheadInfo = decodeLookahead(memberStream);
     this.userEnteredCode = LucentUserEnteredCode.decode(memberStream);
     this.userInfo = LucentUserToUserInfo.decode(memberStream);
     this.reason = LucentReasonCode.decode(memberStream);
     this.originalCallInfo = decodeOCI(memberStream);
     this.distributingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     DeviceID.encode(this.trunkGroup, memberStream);
     DeviceID.encode(this.trunkMember, memberStream);
     DeviceID.encode(this.split_asn, memberStream);
     encodeLookahead(this.lookaheadInfo, memberStream);
     LucentUserEnteredCode.encode(this.userEnteredCode, memberStream);
     LucentUserToUserInfo.encode(this.userInfo, memberStream);
     LucentReasonCode.encode(this.reason, memberStream);
     encodeOCI(this.originalCallInfo, memberStream);
     CSTAExtendedDeviceID.encode(this.distributingDevice_asn, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentEstablishedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
     lines.addAll(DeviceID.print(this.split_asn, "split", indent));
     lines.addAll(LucentLookaheadInfo.print(this.lookaheadInfo, "lookaheadInfo", indent));
     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
     lines.addAll(LucentReasonCode.print(this.reason, "reason", indent));
     lines.addAll(LucentOriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 61;
   }
 
   public CSTAExtendedDeviceID getDistributingDevice_asn()
   {
     return this.distributingDevice_asn;
   }
 
   public LucentLookaheadInfo getLookaheadInfo()
   {
     return this.lookaheadInfo;
   }
 
   public LucentOriginalCallInfo getOriginalCallInfo()
   {
     return this.originalCallInfo;
   }
 
   public short getReason()
   {
     return this.reason;
   }
 
   public String getSplit_asn()
   {
     return this.split_asn;
   }
 
   public String getTrunkGroup()
   {
     return this.trunkGroup;
   }
 
   public String getTrunkMember()
   {
     return this.trunkMember;
   }
 
   public LucentUserEnteredCode getUserEnteredCode()
   {
     return this.userEnteredCode;
   }
 
   public LucentUserToUserInfo getUserInfo()
   {
     return this.userInfo;
   }
 
   public void setDistributingDevice_asn(CSTAExtendedDeviceID distributingDevice_asn) {
     this.distributingDevice_asn = distributingDevice_asn;
   }
 
   public void setLookaheadInfo(LucentLookaheadInfo lookaheadInfo) {
     this.lookaheadInfo = lookaheadInfo;
   }
 
   public void setOriginalCallInfo(LucentOriginalCallInfo originalCallInfo) {
     this.originalCallInfo = originalCallInfo;
   }
 
   public void setReason(short reason) {
     this.reason = reason;
   }
 
   public void setSplit_asn(String split_asn) {
     this.split_asn = split_asn;
   }
 
   public void setTrunkGroup(String trunkGroup) {
     this.trunkGroup = trunkGroup;
   }
 
   public void setTrunkMember(String trunkMember) {
     this.trunkMember = trunkMember;
   }
 
   public void setUserEnteredCode(LucentUserEnteredCode userEnteredCode) {
     this.userEnteredCode = userEnteredCode;
   }
 
   public void setUserInfo(LucentUserToUserInfo userInfo) {
     this.userInfo = userInfo;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentEstablishedEvent
 * JD-Core Version:    0.5.4
 */