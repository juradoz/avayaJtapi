/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTALoggedOnEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAExtendedDeviceID agentDevice;
/*     */   String agentID;
/*     */   String agentGroup;
/*     */   String password;
/*     */   public static final int PDU = 72;
/*     */ 
/*     */   public static CSTALoggedOnEvent decode(InputStream in)
/*     */   {
/*  20 */     CSTALoggedOnEvent _this = new CSTALoggedOnEvent();
/*  21 */     _this.doDecode(in);
/*     */ 
/*  23 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  28 */     this.agentDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  29 */     this.agentID = AgentID.decode(memberStream);
/*  30 */     this.agentGroup = DeviceID.decode(memberStream);
/*  31 */     this.password = AgentPassword.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  36 */     CSTAExtendedDeviceID.encode(this.agentDevice, memberStream);
/*  37 */     AgentID.encode(this.agentID, memberStream);
/*  38 */     DeviceID.encode(this.agentGroup, memberStream);
/*  39 */     AgentPassword.encode(this.password, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  44 */     Collection lines = new ArrayList();
/*  45 */     lines.add("CSTALoggedOnEvent ::=");
/*  46 */     lines.add("{");
/*     */ 
/*  48 */     String indent = "  ";
/*  49 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  50 */     lines.addAll(CSTAExtendedDeviceID.print(this.agentDevice, "agentDevice", indent));
/*  51 */     lines.addAll(AgentID.print(this.agentID, "agentID", indent));
/*  52 */     lines.addAll(DeviceID.print(this.agentGroup, "agentGroup", indent));
/*  53 */     lines.addAll(AgentPassword.print(this.password, "password", indent));
/*     */ 
/*  55 */     lines.add("}");
/*  56 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  61 */     return 72;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getAgentDevice()
/*     */   {
/*  67 */     return this.agentDevice;
/*     */   }
/*     */ 
/*     */   public String getAgentGroup()
/*     */   {
/*  75 */     return this.agentGroup;
/*     */   }
/*     */ 
/*     */   public String getAgentID()
/*     */   {
/*  83 */     return this.agentID;
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/*  91 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setAgentDevice(CSTAExtendedDeviceID agentDevice) {
/*  95 */     this.agentDevice = agentDevice;
/*     */   }
/*     */ 
/*     */   public void setAgentGroup(String agentGroup) {
/*  99 */     this.agentGroup = agentGroup;
/*     */   }
/*     */ 
/*     */   public void setAgentID(String agentID) {
/* 103 */     this.agentID = agentID;
/*     */   }
/*     */ 
/*     */   public void setPassword(String password) {
/* 107 */     this.password = password;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTALoggedOnEvent
 * JD-Core Version:    0.5.4
 */