 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentSupervisorAssistCall extends LucentPrivateData
 {
   String split;
   LucentUserToUserInfo userInfo;
   static final int PDU = 6;
 
   public LucentSupervisorAssistCall()
   {
   }
 
   public LucentSupervisorAssistCall(String _split, LucentUserToUserInfo _userInfo)
   {
     this.split = _split;
     this.userInfo = _userInfo;
   }
 
   public static LucentSupervisorAssistCall decode(InputStream in) {
     LucentSupervisorAssistCall _this = new LucentSupervisorAssistCall();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.split = DeviceID.decode(memberStream);
     this.userInfo = LucentUserToUserInfo.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     DeviceID.encode(this.split, memberStream);
     LucentUserToUserInfo.encode(this.userInfo, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentSupervisorAssistCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.split, "split", indent));
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 6;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSupervisorAssistCall
 * JD-Core Version:    0.5.4
 */