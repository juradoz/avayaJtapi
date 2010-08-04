/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAWorkNotReadyEvent extends CSTAUnsolicited
/*    */ {
/*    */   CSTAExtendedDeviceID agentDevice;
/*    */   String agentID;
/*    */   public static final int PDU = 76;
/*    */ 
/*    */   public static CSTAWorkNotReadyEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTAWorkNotReadyEvent _this = new CSTAWorkNotReadyEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.agentDevice = CSTAExtendedDeviceID.decode(memberStream);
/* 26 */     this.agentID = AgentID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 31 */     Collection lines = new ArrayList();
/*    */ 
/* 33 */     lines.add("CSTAWorkNotReadyEvent ::=");
/* 34 */     lines.add("{");
/*    */ 
/* 36 */     String indent = "  ";
/* 37 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/* 38 */     lines.addAll(CSTAExtendedDeviceID.print(this.agentDevice, "agentDevice", indent));
/* 39 */     lines.addAll(AgentID.print(this.agentID, "agentID", indent));
/*    */ 
/* 41 */     lines.add("}");
/* 42 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 47 */     return 76;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getAgentDevice()
/*    */   {
/* 53 */     return this.agentDevice;
/*    */   }
/*    */ 
/*    */   public String getAgentID()
/*    */   {
/* 61 */     return this.agentID;
/*    */   }
/*    */ 
/*    */   public void setAgentDevice(CSTAExtendedDeviceID agentDevice)
/*    */   {
/* 66 */     this.agentDevice = agentDevice;
/*    */   }
/*    */ 
/*    */   public void setAgentID(String agentID)
/*    */   {
/* 71 */     this.agentID = agentID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAWorkNotReadyEvent
 * JD-Core Version:    0.5.4
 */