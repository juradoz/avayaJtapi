/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTALoggedOffEvent extends CSTAUnsolicited
/*    */ {
/*    */   CSTAExtendedDeviceID agentDevice;
/*    */   String agentID;
/*    */   String agentGroup;
/*    */   public static final int PDU = 73;
/*    */ 
/*    */   public static CSTALoggedOffEvent decode(InputStream in)
/*    */   {
/* 19 */     CSTALoggedOffEvent _this = new CSTALoggedOffEvent();
/* 20 */     _this.doDecode(in);
/*    */ 
/* 22 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 27 */     this.agentDevice = CSTAExtendedDeviceID.decode(memberStream);
/* 28 */     this.agentID = AgentID.decode(memberStream);
/* 29 */     this.agentGroup = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 33 */     CSTAExtendedDeviceID.encode(this.agentDevice, memberStream);
/* 34 */     AgentID.encode(this.agentID, memberStream);
/* 35 */     DeviceID.encode(this.agentGroup, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 40 */     Collection lines = new ArrayList();
/* 41 */     lines.add("CSTALoggedOffEvent ::=");
/* 42 */     lines.add("{");
/*    */ 
/* 44 */     String indent = "  ";
/* 45 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/* 46 */     lines.addAll(CSTAExtendedDeviceID.print(this.agentDevice, "agentDevice", indent));
/* 47 */     lines.addAll(AgentID.print(this.agentID, "agentID", indent));
/* 48 */     lines.addAll(DeviceID.print(this.agentGroup, "agentGroup", indent));
/*    */ 
/* 50 */     lines.add("}");
/* 51 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 56 */     return 73;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getAgentDevice()
/*    */   {
/* 62 */     return this.agentDevice;
/*    */   }
/*    */ 
/*    */   public String getAgentGroup()
/*    */   {
/* 70 */     return this.agentGroup;
/*    */   }
/*    */ 
/*    */   public String getAgentID()
/*    */   {
/* 78 */     return this.agentID;
/*    */   }
/*    */ 
/*    */   public void setAgentDevice(CSTAExtendedDeviceID agentDevice) {
/* 82 */     this.agentDevice = agentDevice;
/*    */   }
/*    */ 
/*    */   public void setAgentGroup(String agentGroup) {
/* 86 */     this.agentGroup = agentGroup;
/*    */   }
/*    */ 
/*    */   public void setAgentID(String agentID) {
/* 90 */     this.agentID = agentID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTALoggedOffEvent
 * JD-Core Version:    0.5.4
 */