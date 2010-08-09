 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentV5OriginalCallInfo extends LucentOriginalCallInfo
 {
   String ucid;
   CSTACallOriginatorInfo callOriginatorInfo;
   boolean flexibleBilling;
 
   public String getUCID()
   {
     return this.ucid;
   }
 
   public void setUCID(String _ucid)
   {
     this.ucid = _ucid;
   }
 
   public int getCallOriginatorType()
   {
     if (hasCallOriginatorType()) {
       return this.callOriginatorInfo.getCallOriginatorType();
     }
     return -1;
   }
 
   public boolean hasCallOriginatorType()
   {
     return this.callOriginatorInfo != null;
   }
 
   public boolean canSetBillRate()
   {
     return this.flexibleBilling;
   }
 
   public static LucentOriginalCallInfo decode(InputStream in)
   {
     LucentV5OriginalCallInfo _this = new LucentV5OriginalCallInfo();
     _this.doDecode(in);
     if ((_this.callingDevice_asn == null) && (_this.calledDevice_asn == null) && (_this.trunkGroup == null) && (_this.trunkMember == null) && (_this.lookaheadInfo == null) && (_this.userEnteredCode == null) && (_this.userInfo == null) && (_this.ucid == null) && (_this.callOriginatorInfo == null))
     {
       return null;
     }
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     super.encodeMembers(memberStream);
     UCID.encode(this.ucid, memberStream);
     CSTACallOriginatorInfo.encode(this.callOriginatorInfo, memberStream);
     ASNBoolean.encode(this.flexibleBilling, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     super.decodeMembers(memberStream);
     this.ucid = UCID.decode(memberStream);
     this.callOriginatorInfo = CSTACallOriginatorInfo.decode(memberStream);
     this.flexibleBilling = ASNBoolean.decode(memberStream);
   }
 
   public LucentLookaheadInfo decodeLookahead(InputStream memberStream)
   {
     return LucentV5LookaheadInfo.decode(memberStream);
   }
 
   public static Collection<String> print(LucentV5OriginalCallInfo _this, String name, String _indent)
   {
     Collection lines = new ArrayList();
 
     if (_this == null)
     {
       lines.add(_indent + name + " <null>");
       return lines;
     }
     if (name != null) {
       lines.add(_indent + name);
     }
     lines.add(_indent + "{");
 
     String indent = _indent + "  ";
 
     lines.addAll(ReasonForCallInfo.print(_this.reason, "reason", indent));
     lines.addAll(CSTAExtendedDeviceID.print(_this.callingDevice_asn, "callingDevice", indent));
     lines.addAll(CSTAExtendedDeviceID.print(_this.calledDevice_asn, "calledDevice", indent));
     lines.addAll(DeviceID.print(_this.trunkGroup, "trunkGroup", indent));
     lines.addAll(DeviceID.print(_this.trunkMember, "trunkMember", indent));
     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)_this.lookaheadInfo, "lookaheadInfo", indent));
     lines.addAll(LucentUserEnteredCode.print(_this.userEnteredCode, "userEnteredCode", indent));
     lines.addAll(LucentUserToUserInfo.print(_this.userInfo, "userInfo", indent));
     lines.addAll(UCID.print(_this.ucid, "ucid", indent));
     lines.addAll(CSTACallOriginatorInfo.print(_this.callOriginatorInfo, "callOriginatorInfo", indent));
     lines.addAll(ASNBoolean.print(_this.flexibleBilling, "flexibleBilling", indent));
 
     lines.add(_indent + "}");
     return lines;
   }
 
   public void setCallOriginatorInfo(CSTACallOriginatorInfo _callOriginatorInfo) {
     this.callOriginatorInfo = _callOriginatorInfo;
   }
 
   public CSTACallOriginatorInfo getCallOriginatorInfo()
   {
     return this.callOriginatorInfo;
   }
 
   public void setFlexibleBilling(boolean flexibleBilling)
   {
     this.flexibleBilling = flexibleBilling;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV5OriginalCallInfo
 * JD-Core Version:    0.5.4
 */