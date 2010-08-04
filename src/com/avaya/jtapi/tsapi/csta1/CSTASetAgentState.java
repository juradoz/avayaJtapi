/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTASetAgentState extends CSTARequest
/*     */ {
/*     */   String device;
/*     */   short agentMode;
/*     */   String agentID;
/*     */   String agentGroup;
/*     */   String agentPassword;
/*     */   public static final int PDU = 49;
/*     */ 
/*     */   public CSTASetAgentState()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CSTASetAgentState(String _device, short _agentMode, String _agentID, String _agentGroup, String _agentPassword)
/*     */   {
/*  26 */     this.device = _device;
/*  27 */     this.agentMode = _agentMode;
/*  28 */     this.agentID = _agentID;
/*  29 */     this.agentGroup = _agentGroup;
/*  30 */     this.agentPassword = _agentPassword;
/*     */   }
/*     */ 
/*     */   public static CSTASetAgentState decode(InputStream in) {
/*  34 */     CSTASetAgentState _this = new CSTASetAgentState();
/*  35 */     _this.doDecode(in);
/*     */ 
/*  37 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  41 */     DeviceID.encode(this.device, memberStream);
/*  42 */     AgentMode.encode(this.agentMode, memberStream);
/*  43 */     AgentID.encode(this.agentID, memberStream);
/*  44 */     DeviceID.encode(this.agentGroup, memberStream);
/*  45 */     AgentPassword.encode(this.agentPassword, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  50 */     this.device = DeviceID.decode(memberStream);
/*  51 */     this.agentMode = AgentMode.decode(memberStream);
/*  52 */     this.agentID = AgentID.decode(memberStream);
/*  53 */     this.agentGroup = DeviceID.decode(memberStream);
/*  54 */     this.agentPassword = AgentPassword.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  59 */     Collection lines = new ArrayList();
/*     */ 
/*  61 */     lines.add("CSTASetAgentState ::=");
/*  62 */     lines.add("{");
/*     */ 
/*  64 */     String indent = "  ";
/*     */ 
/*  66 */     lines.addAll(DeviceID.print(this.device, "device", indent));
/*  67 */     lines.addAll(AgentMode.print(this.agentMode, "agentMode", indent));
/*  68 */     lines.addAll(AgentID.print(this.agentID, "agentID", indent));
/*  69 */     lines.addAll(DeviceID.print(this.agentGroup, "agentGroup", indent));
/*  70 */     lines.addAll(AgentPassword.print(this.agentPassword, "agentPassword", indent));
/*     */ 
/*  72 */     lines.add("}");
/*  73 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  78 */     return 49;
/*     */   }
/*     */ 
/*     */   public String getAgentGroup()
/*     */   {
/*  84 */     return this.agentGroup;
/*     */   }
/*     */ 
/*     */   public String getAgentID()
/*     */   {
/*  92 */     return this.agentID;
/*     */   }
/*     */ 
/*     */   public short getAgentMode()
/*     */   {
/* 100 */     return this.agentMode;
/*     */   }
/*     */ 
/*     */   public String getAgentPassword()
/*     */   {
/* 108 */     return this.agentPassword;
/*     */   }
/*     */ 
/*     */   public String getDevice()
/*     */   {
/* 116 */     return this.device;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTASetAgentState
 * JD-Core Version:    0.5.4
 */