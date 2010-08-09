 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentQueryAgentLoginResp extends LucentPrivateData
 {
   int privEventCrossRefID;
   String[] devices;
   public static final int PDU = 15;
 
   public static LucentQueryAgentLoginResp decode(InputStream in)
   {
     LucentQueryAgentLoginResp _this = new LucentQueryAgentLoginResp();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.privEventCrossRefID = LucentPrivEventCrossRefID.decode(memberStream);
     this.devices = LucentQueryAgentLoginRespList.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     LucentPrivEventCrossRefID.encode(this.privEventCrossRefID, memberStream);
     LucentQueryAgentLoginRespList.encode(this.devices, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentQueryAgentLoginResp ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentPrivEventCrossRefID.print(this.privEventCrossRefID, "privEventCrossRefID", indent));
     lines.addAll(LucentQueryAgentLoginRespList.print(this.devices, "devices", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 15;
   }
 
   public String[] getDevices()
   {
     return this.devices;
   }
 
   public int getPrivEventCrossRefID()
   {
     return this.privEventCrossRefID;
   }
 
   public void setDevices(String[] devices) {
     this.devices = devices;
   }
 
   public void setPrivEventCrossRefID(int privEventCrossRefID) {
     this.privEventCrossRefID = privEventCrossRefID;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentQueryAgentLoginResp
 * JD-Core Version:    0.5.4
 */