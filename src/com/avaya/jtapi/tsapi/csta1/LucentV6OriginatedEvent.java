 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentV6OriginatedEvent extends LucentOriginatedEvent
 {
   static final int PDU = 119;
 
   public static LucentOriginatedEvent decode(InputStream in)
   {
     LucentV6OriginatedEvent _this = new LucentV6OriginatedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentV6OriginatedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.physicalTerminal_asn, "physicalTerminal", indent));
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 119;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6OriginatedEvent
 * JD-Core Version:    0.5.4
 */