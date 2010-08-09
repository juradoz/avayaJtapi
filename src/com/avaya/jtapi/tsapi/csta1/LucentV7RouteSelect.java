 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import com.avaya.jtapi.tsapi.asn1.ASNEnumerated;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentV7RouteSelect extends LucentRouteSelect
 {
   short useNCR;
   public static final int PDU = 126;
 
   public LucentV7RouteSelect()
   {
   }
 
   public LucentV7RouteSelect(String _callingDevice, String _directAgentCallSplit, boolean _priorityCalling, String _destRoute, LucentUserCollectCode _collectCode, LucentUserProvidedCode _userProvidedCode, LucentUserToUserInfo _userInfo, short _useNCR)
   {
     super(_callingDevice, _directAgentCallSplit, _priorityCalling, _destRoute, _collectCode, _userProvidedCode, _userInfo);
     this.useNCR = _useNCR;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     super.encodeMembers(memberStream);
     ASNEnumerated.encode(this.useNCR, memberStream);
   }
 
   public static LucentV7RouteSelect decode(InputStream in) {
     LucentV7RouteSelect _this = new LucentV7RouteSelect();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     super.decodeMembers(memberStream);
     this.useNCR = ASNEnumerated.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentV7RouteSelect ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.callingDevice, "callingDevice", indent));
     lines.addAll(DeviceID.print(this.directAgentCallSplit, "directAgentCallSplit", indent));
     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
     lines.addAll(DeviceID.print(this.destRoute, "destRoute", indent));
     lines.addAll(LucentUserCollectCode.print(this.collectCode, "collectCode", indent));
     lines.addAll(LucentUserProvidedCode.print(this.userProvidedCode, "userProvidedCode", indent));
     lines.addAll(LucentRedirectType.print(this.useNCR, "useNCR", indent));
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 126;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7RouteSelect
 * JD-Core Version:    0.5.4
 */