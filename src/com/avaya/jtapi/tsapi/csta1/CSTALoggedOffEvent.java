 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTALoggedOffEvent extends CSTAUnsolicited
 {
   CSTAExtendedDeviceID agentDevice;
   String agentID;
   String agentGroup;
   public static final int PDU = 73;
 
   public static CSTALoggedOffEvent decode(InputStream in)
   {
     CSTALoggedOffEvent _this = new CSTALoggedOffEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.agentDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.agentID = AgentID.decode(memberStream);
     this.agentGroup = DeviceID.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTAExtendedDeviceID.encode(this.agentDevice, memberStream);
     AgentID.encode(this.agentID, memberStream);
     DeviceID.encode(this.agentGroup, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTALoggedOffEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAExtendedDeviceID.print(this.agentDevice, "agentDevice", indent));
     lines.addAll(AgentID.print(this.agentID, "agentID", indent));
     lines.addAll(DeviceID.print(this.agentGroup, "agentGroup", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 73;
   }
 
   public CSTAExtendedDeviceID getAgentDevice()
   {
     return this.agentDevice;
   }
 
   public String getAgentGroup()
   {
     return this.agentGroup;
   }
 
   public String getAgentID()
   {
     return this.agentID;
   }
 
   public void setAgentDevice(CSTAExtendedDeviceID agentDevice) {
     this.agentDevice = agentDevice;
   }
 
   public void setAgentGroup(String agentGroup) {
     this.agentGroup = agentGroup;
   }
 
   public void setAgentID(String agentID) {
     this.agentID = agentID;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTALoggedOffEvent
 * JD-Core Version:    0.5.4
 */