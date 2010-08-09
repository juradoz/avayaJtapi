 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentV6ReconnectCall extends LucentReconnectCall
 {
   public static final int PDU = 114;
 
   LucentV6ReconnectCall()
   {
   }
 
   public LucentV6ReconnectCall(short _dropResource, LucentUserToUserInfo _userInfo)
   {
     super(_dropResource, _userInfo);
   }
 
   public static LucentReconnectCall decode(InputStream in)
   {
     LucentV6ReconnectCall _this = new LucentV6ReconnectCall();
     _this.doDecode(in);
 
     return _this;
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentV6ReconnectCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentDropResource.print(this.dropResource, "dropResource", indent));
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 114;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6ReconnectCall
 * JD-Core Version:    0.5.4
 */