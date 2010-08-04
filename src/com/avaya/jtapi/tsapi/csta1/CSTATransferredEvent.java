/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTATransferredEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID primaryOldCall;
/*     */   CSTAConnectionID secondaryOldCall;
/*     */   CSTAExtendedDeviceID transferringDevice;
/*     */   CSTAExtendedDeviceID transferredDevice;
/*     */   CSTAConnection[] transferredConnections;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 67;
/*     */ 
/*     */   public static CSTATransferredEvent decode(InputStream in)
/*     */   {
/*  23 */     CSTATransferredEvent _this = new CSTATransferredEvent();
/*  24 */     _this.doDecode(in);
/*     */ 
/*  26 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  30 */     CSTAConnectionID.encode(this.primaryOldCall, memberStream);
/*  31 */     CSTAConnectionID.encode(this.secondaryOldCall, memberStream);
/*  32 */     CSTAExtendedDeviceID.encode(this.transferringDevice, memberStream);
/*  33 */     CSTAExtendedDeviceID.encode(this.transferredDevice, memberStream);
/*  34 */     ConnectionList.encode(this.transferredConnections, memberStream);
/*  35 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  36 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream) {
/*  40 */     this.primaryOldCall = CSTAConnectionID.decode(memberStream);
/*  41 */     this.secondaryOldCall = CSTAConnectionID.decode(memberStream);
/*  42 */     this.transferringDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  43 */     this.transferredDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  44 */     this.transferredConnections = ConnectionList.decode(memberStream);
/*  45 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  46 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  51 */     Collection lines = new ArrayList();
/*     */ 
/*  53 */     lines.add("CSTATransferredEvent ::=");
/*  54 */     lines.add("{");
/*     */ 
/*  56 */     String indent = "  ";
/*  57 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  58 */     lines.addAll(CSTAConnectionID.print(this.primaryOldCall, "primaryOldCall", indent));
/*  59 */     lines.addAll(CSTAConnectionID.print(this.secondaryOldCall, "secondaryOldCall", indent));
/*  60 */     lines.addAll(CSTAExtendedDeviceID.print(this.transferringDevice, "transferringDevice", indent));
/*  61 */     lines.addAll(CSTAExtendedDeviceID.print(this.transferredDevice, "transferredDevice", indent));
/*  62 */     lines.addAll(ConnectionList.print(this.transferredConnections, "transferredConnections", indent));
/*  63 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  64 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  66 */     lines.add("}");
/*  67 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  72 */     return 67;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/*  78 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/*  86 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getPrimaryOldCall()
/*     */   {
/*  94 */     return this.primaryOldCall;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getSecondaryOldCall()
/*     */   {
/* 102 */     return this.secondaryOldCall;
/*     */   }
/*     */ 
/*     */   public CSTAConnection[] getTransferredConnections()
/*     */   {
/* 110 */     return this.transferredConnections;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getTransferredDevice()
/*     */   {
/* 118 */     return this.transferredDevice;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getTransferringDevice()
/*     */   {
/* 126 */     return this.transferringDevice;
/*     */   }
/*     */ 
/*     */   public void setCause(short _cause) {
/* 130 */     this.cause = _cause;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short _localConnectionInfo) {
/* 134 */     this.localConnectionInfo = _localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setPrimaryOldCall(CSTAConnectionID _primaryOldCall) {
/* 138 */     this.primaryOldCall = _primaryOldCall;
/*     */   }
/*     */ 
/*     */   public void setSecondaryOldCall(CSTAConnectionID _secondaryOldCall) {
/* 142 */     this.secondaryOldCall = _secondaryOldCall;
/*     */   }
/*     */ 
/*     */   public void setTransferredConnections(CSTAConnection[] _transferredConnections) {
/* 146 */     this.transferredConnections = _transferredConnections;
/*     */   }
/*     */ 
/*     */   public void setTransferredDevice(CSTAExtendedDeviceID _transferredDevice) {
/* 150 */     this.transferredDevice = _transferredDevice;
/*     */   }
/*     */ 
/*     */   public void setTransferringDevice(CSTAExtendedDeviceID _transferringDevice) {
/* 154 */     this.transferringDevice = _transferringDevice;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTATransferredEvent
 * JD-Core Version:    0.5.4
 */