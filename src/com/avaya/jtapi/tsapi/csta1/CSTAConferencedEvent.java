/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAConferencedEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID primaryOldCall;
/*     */   CSTAConnectionID secondaryOldCall;
/*     */   CSTAExtendedDeviceID confController;
/*     */   CSTAExtendedDeviceID addedParty;
/*     */   CSTAConnection[] conferenceConnections;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 55;
/*     */ 
/*     */   public static CSTAConferencedEvent decode(InputStream in)
/*     */   {
/*  23 */     CSTAConferencedEvent _this = new CSTAConferencedEvent();
/*  24 */     _this.doDecode(in);
/*     */ 
/*  26 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  31 */     CSTAConnectionID.encode(this.primaryOldCall, memberStream);
/*  32 */     CSTAConnectionID.encode(this.secondaryOldCall, memberStream);
/*  33 */     CSTAExtendedDeviceID.encode(this.confController, memberStream);
/*  34 */     CSTAExtendedDeviceID.encode(this.addedParty, memberStream);
/*  35 */     ConnectionList.encode(this.conferenceConnections, memberStream);
/*  36 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  37 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream) {
/*  41 */     this.primaryOldCall = CSTAConnectionID.decode(memberStream);
/*  42 */     this.secondaryOldCall = CSTAConnectionID.decode(memberStream);
/*  43 */     this.confController = CSTAExtendedDeviceID.decode(memberStream);
/*  44 */     this.addedParty = CSTAExtendedDeviceID.decode(memberStream);
/*  45 */     this.conferenceConnections = ConnectionList.decode(memberStream);
/*  46 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  47 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  52 */     Collection lines = new ArrayList();
/*  53 */     lines.add("CSTAConferencedEvent ::=");
/*  54 */     lines.add("{");
/*     */ 
/*  56 */     String indent = "  ";
/*  57 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  58 */     lines.addAll(CSTAConnectionID.print(this.primaryOldCall, "primaryOldCall", indent));
/*  59 */     lines.addAll(CSTAConnectionID.print(this.secondaryOldCall, "secondaryOldCall", indent));
/*  60 */     lines.addAll(CSTAExtendedDeviceID.print(this.confController, "confController", indent));
/*  61 */     lines.addAll(CSTAExtendedDeviceID.print(this.addedParty, "addedParty", indent));
/*  62 */     lines.addAll(ConnectionList.print(this.conferenceConnections, "conferenceConnections", indent));
/*  63 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  64 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  66 */     lines.add("}");
/*  67 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  72 */     return 55;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getAddedParty()
/*     */   {
/*  78 */     return this.addedParty;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/*  86 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getConfController()
/*     */   {
/*  94 */     return this.confController;
/*     */   }
/*     */ 
/*     */   public CSTAConnection[] getConferenceConnections()
/*     */   {
/* 102 */     return this.conferenceConnections;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/* 110 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getPrimaryOldCall()
/*     */   {
/* 118 */     return this.primaryOldCall;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getSecondaryOldCall()
/*     */   {
/* 126 */     return this.secondaryOldCall;
/*     */   }
/*     */ 
/*     */   public void setAddedParty(CSTAExtendedDeviceID _conferencedDevice)
/*     */   {
/* 131 */     this.addedParty = _conferencedDevice;
/*     */   }
/*     */ 
/*     */   public void setConfController(CSTAExtendedDeviceID _conferencingDevice) {
/* 135 */     this.confController = _conferencingDevice;
/*     */   }
/*     */ 
/*     */   public void setCause(short _cause) {
/* 139 */     this.cause = _cause;
/*     */   }
/*     */ 
/*     */   public void setConferencedConnections(CSTAConnection[] _conferenceConnections)
/*     */   {
/* 144 */     this.conferenceConnections = _conferenceConnections;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short _localConnectionInfo)
/*     */   {
/* 149 */     this.localConnectionInfo = _localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setPrimaryOldCall(CSTAConnectionID _primaryOldCall)
/*     */   {
/* 154 */     this.primaryOldCall = _primaryOldCall;
/*     */   }
/*     */ 
/*     */   public void setSecondaryOldCall(CSTAConnectionID _secondaryOldCall)
/*     */   {
/* 159 */     this.secondaryOldCall = _secondaryOldCall;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConferencedEvent
 * JD-Core Version:    0.5.4
 */