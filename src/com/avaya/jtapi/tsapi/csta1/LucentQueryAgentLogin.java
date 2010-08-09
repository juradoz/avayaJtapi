 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentQueryAgentLogin extends LucentPrivateData
 {
   String device;
   public static final int PDU = 13;
 
   public LucentQueryAgentLogin()
   {
   }
 
   public LucentQueryAgentLogin(String _device)
   {
     this.device = _device;
   }
 
   public static LucentQueryAgentLogin decode(InputStream in) {
     LucentQueryAgentLogin _this = new LucentQueryAgentLogin();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream) {
     this.device = DeviceID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     DeviceID.encode(this.device, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentQueryAgentLogin ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.device, "device", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 13;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLogin
 * JD-Core Version:    0.5.4
 */