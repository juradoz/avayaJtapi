 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTASetAgentState extends CSTARequest
 {
   String device;
   short agentMode;
   String agentID;
   String agentGroup;
   String agentPassword;
   public static final int PDU = 49;
 
   public CSTASetAgentState()
   {
   }
 
   public CSTASetAgentState(String _device, short _agentMode, String _agentID, String _agentGroup, String _agentPassword)
   {
     this.device = _device;
     this.agentMode = _agentMode;
     this.agentID = _agentID;
     this.agentGroup = _agentGroup;
     this.agentPassword = _agentPassword;
   }
 
   public static CSTASetAgentState decode(InputStream in) {
     CSTASetAgentState _this = new CSTASetAgentState();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream) {
     DeviceID.encode(this.device, memberStream);
     AgentMode.encode(this.agentMode, memberStream);
     AgentID.encode(this.agentID, memberStream);
     DeviceID.encode(this.agentGroup, memberStream);
     AgentPassword.encode(this.agentPassword, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.device = DeviceID.decode(memberStream);
     this.agentMode = AgentMode.decode(memberStream);
     this.agentID = AgentID.decode(memberStream);
     this.agentGroup = DeviceID.decode(memberStream);
     this.agentPassword = AgentPassword.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("CSTASetAgentState ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(DeviceID.print(this.device, "device", indent));
     lines.addAll(AgentMode.print(this.agentMode, "agentMode", indent));
     lines.addAll(AgentID.print(this.agentID, "agentID", indent));
     lines.addAll(DeviceID.print(this.agentGroup, "agentGroup", indent));
     lines.addAll(AgentPassword.print(this.agentPassword, "agentPassword", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 49;
   }
 
   public String getAgentGroup()
   {
     return this.agentGroup;
   }
 
   public String getAgentID()
   {
     return this.agentID;
   }
 
   public short getAgentMode()
   {
     return this.agentMode;
   }
 
   public String getAgentPassword()
   {
     return this.agentPassword;
   }
 
   public String getDevice()
   {
     return this.device;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASetAgentState
 * JD-Core Version:    0.5.4
 */