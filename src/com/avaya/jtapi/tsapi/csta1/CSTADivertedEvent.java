/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTADivertedEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID connection;
/*     */   CSTAExtendedDeviceID divertingDevice;
/*     */   CSTAExtendedDeviceID newDestination;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 58;
/*     */ 
/*     */   public static CSTADivertedEvent decode(InputStream in)
/*     */   {
/*  21 */     CSTADivertedEvent _this = new CSTADivertedEvent();
/*  22 */     _this.doDecode(in);
/*     */ 
/*  24 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  28 */     CSTAConnectionID.encode(this.connection, memberStream);
/*  29 */     CSTAExtendedDeviceID.encode(this.divertingDevice, memberStream);
/*  30 */     CSTAExtendedDeviceID.encode(this.newDestination, memberStream);
/*  31 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  32 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream) {
/*  36 */     this.connection = CSTAConnectionID.decode(memberStream);
/*  37 */     this.divertingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  38 */     this.newDestination = CSTAExtendedDeviceID.decode(memberStream);
/*  39 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  40 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  45 */     Collection lines = new ArrayList();
/*  46 */     lines.add("CSTADivertedEvent ::=");
/*  47 */     lines.add("{");
/*     */ 
/*  49 */     String indent = "  ";
/*  50 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  51 */     lines.addAll(CSTAConnectionID.print(this.connection, "connection", indent));
/*  52 */     lines.addAll(CSTAExtendedDeviceID.print(this.divertingDevice, "divertingDevice", indent));
/*  53 */     lines.addAll(CSTAExtendedDeviceID.print(this.newDestination, "newDestination", indent));
/*  54 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  55 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  57 */     lines.add("}");
/*  58 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  63 */     return 58;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/*  69 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getConnection()
/*     */   {
/*  77 */     return this.connection;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getDivertingDevice()
/*     */   {
/*  85 */     return this.divertingDevice;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/*  93 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getNewDestination()
/*     */   {
/* 101 */     return this.newDestination;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo)
/*     */   {
/* 108 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setCause(short _cause) {
/* 112 */     this.cause = _cause;
/*     */   }
/*     */ 
/*     */   public void setConnection(CSTAConnectionID _connection) {
/* 116 */     this.connection = _connection;
/*     */   }
/*     */ 
/*     */   public void setDivertingDevice(CSTAExtendedDeviceID _divertingDevice) {
/* 120 */     this.divertingDevice = _divertingDevice;
/*     */   }
/*     */ 
/*     */   public void setNewDestination(CSTAExtendedDeviceID _newDestination) {
/* 124 */     this.newDestination = _newDestination;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTADivertedEvent
 * JD-Core Version:    0.5.4
 */