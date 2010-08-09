 package com.avaya.jtapi.tsapi.csta1;
 
 import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentDirectAgentCall extends LucentPrivateData
 {
   String split;
   boolean priorityCalling;
   LucentUserToUserInfo userInfo;
   static final int PDU = 4;
 
   public LucentDirectAgentCall()
   {
   }
 
   public LucentDirectAgentCall(String _split, boolean _priorityCalling, LucentUserToUserInfo _userInfo)
   {
     this.split = _split;
     this.priorityCalling = _priorityCalling;
     this.userInfo = _userInfo;
   }
 
   public static LucentDirectAgentCall decode(InputStream in) {
     LucentDirectAgentCall _this = new LucentDirectAgentCall();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.split = DeviceID.decode(memberStream);
     this.priorityCalling = ASNBoolean.decode(memberStream);
     this.userInfo = LucentUserToUserInfo.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     DeviceID.encode(this.split, memberStream);
     ASNBoolean.encode(this.priorityCalling, memberStream);
     LucentUserToUserInfo.encode(this.userInfo, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentDirectAgentCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.split, "split", indent));
     lines.addAll(ASNBoolean.print(this.priorityCalling, "priorityCalling", indent));
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 4;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentDirectAgentCall
 * JD-Core Version:    0.5.4
 */