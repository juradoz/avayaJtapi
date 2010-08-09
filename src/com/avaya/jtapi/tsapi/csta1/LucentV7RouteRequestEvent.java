 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentV7RouteRequestEvent extends LucentV6RouteRequestEvent
 {
   LucentDeviceHistoryEntry[] deviceHistory;
   public static final int PDU = 131;
 
   public static LucentRouteRequestEvent decode(InputStream in)
   {
     LucentV7RouteRequestEvent _this = new LucentV7RouteRequestEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     super.decodeMembers(memberStream);
     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     super.encodeMembers(memberStream);
     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentV7RouteRequestEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.trunkGroup, "trunkGroup", indent));
     lines.addAll(LucentV5LookaheadInfo.print((LucentV5LookaheadInfo)this.lookaheadInfo, "lookaheadInfo", indent));
     lines.addAll(LucentUserEnteredCode.print(this.userEnteredCode, "userEnteredCode", indent));
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
     lines.addAll(UCID.print(this.ucid, "ucid", indent));
     lines.addAll(CSTACallOriginatorInfo.print(this.callOriginatorInfo, "callOriginatorInfo", indent));
     lines.addAll(ASNBoolean.print(this.flexibleBilling, "flexibleBilling", indent));
     lines.addAll(DeviceID.print(this.trunkMember, "trunkMember", indent));
     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 131;
   }
 
   public LucentDeviceHistoryEntry[] getDeviceHistory()
   {
     return this.deviceHistory;
   }
 
   public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
     this.deviceHistory = deviceHistory;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7RouteRequestEvent
 * JD-Core Version:    0.5.4
 */