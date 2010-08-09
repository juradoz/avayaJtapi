 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentV6RouteRequestEvent extends LucentV5RouteRequestEvent
 {
   String trunkMember;
   static final int PDU = 105;
 
   public static LucentRouteRequestEvent decode(InputStream in)
   {
     LucentV6RouteRequestEvent _this = new LucentV6RouteRequestEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     super.decodeMembers(memberStream);
     this.trunkMember = DeviceID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     super.encodeMembers(memberStream);
     DeviceID.encode(this.trunkMember, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentV6RouteRequestEvent ::=");
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
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 105;
   }
   public String getTrunkMember() {
     return this.trunkMember;
   }
 
   public void setTrunkMember(String trunkMember) {
     this.trunkMember = trunkMember;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6RouteRequestEvent
 * JD-Core Version:    0.5.4
 */