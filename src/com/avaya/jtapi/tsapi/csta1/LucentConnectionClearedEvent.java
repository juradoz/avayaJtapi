 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentConnectionClearedEvent extends LucentPrivateData
 {
   LucentUserToUserInfo userInfo;
   static final int PDU = 36;
 
   static LucentConnectionClearedEvent decode(InputStream in)
   {
     LucentConnectionClearedEvent _this = new LucentConnectionClearedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.userInfo = LucentUserToUserInfo.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     LucentUserToUserInfo.encode(this.userInfo, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentConnectionClearedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentUserToUserInfo.print(this.userInfo, "userInfo", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 36;
   }
 
   public LucentUserToUserInfo getUserInfo()
   {
     return this.userInfo;
   }
   public void setUserInfo(LucentUserToUserInfo userInfo) {
     this.userInfo = userInfo;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentConnectionClearedEvent
 * JD-Core Version:    0.5.4
 */