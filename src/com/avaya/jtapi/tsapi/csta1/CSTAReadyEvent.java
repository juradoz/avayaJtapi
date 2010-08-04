/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAReadyEvent extends CSTAUnsolicited
/*    */ {
/*    */   CSTAExtendedDeviceID agentDevice;
/*    */   String agentID;
/*    */   public static final int PDU = 75;
/*    */ 
/*    */   public static CSTAReadyEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTAReadyEvent _this = new CSTAReadyEvent();
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
/* 32 */     lines.add("CSTAReadyEvent ::=");
/* 33 */     lines.add("{");
/*    */ 
/* 35 */     String indent = "  ";
/* 36 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/* 37 */     lines.addAll(CSTAExtendedDeviceID.print(this.agentDevice, "agentDevice", indent));
/* 38 */     lines.addAll(AgentID.print(this.agentID, "agentID", indent));
/*    */ 
/* 40 */     lines.add("}");
/* 41 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 46 */     return 75;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getAgentDevice()
/*    */   {
/* 52 */     return this.agentDevice;
/*    */   }
/*    */ 
/*    */   public void setAgentDevice(CSTAExtendedDeviceID agentDevice)
/*    */   {
/* 60 */     this.agentDevice = agentDevice;
/*    */   }
/*    */ 
/*    */   public void setAgentID(String agentID)
/*    */   {
/* 68 */     this.agentID = agentID;
/*    */   }
/*    */ 
/*    */   public String getAgentID()
/*    */   {
/* 76 */     return this.agentID;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAReadyEvent
 * JD-Core Version:    0.5.4
 */