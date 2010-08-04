/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAFailedEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID failedConnection;
/*     */   CSTAExtendedDeviceID failingDevice;
/*     */   CSTAExtendedDeviceID calledDevice;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 60;
/*     */ 
/*     */   public static CSTAFailedEvent decode(InputStream in)
/*     */   {
/*  21 */     CSTAFailedEvent _this = new CSTAFailedEvent();
/*  22 */     _this.doDecode(in);
/*     */ 
/*  24 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  29 */     CSTAConnectionID.encode(this.failedConnection, memberStream);
/*  30 */     CSTAExtendedDeviceID.encode(this.failingDevice, memberStream);
/*  31 */     CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
/*  32 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  33 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  38 */     this.failedConnection = CSTAConnectionID.decode(memberStream);
/*  39 */     this.failingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  40 */     this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  41 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  42 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  47 */     Collection lines = new ArrayList();
/*  48 */     lines.add("CSTAFailedEvent ::=");
/*  49 */     lines.add("{");
/*     */ 
/*  51 */     String indent = "  ";
/*  52 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  53 */     lines.addAll(CSTAConnectionID.print(this.failedConnection, "failedConnection", indent));
/*  54 */     lines.addAll(CSTAExtendedDeviceID.print(this.failingDevice, "failingDevice", indent));
/*  55 */     lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice, "calledDevice", indent));
/*  56 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  57 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  59 */     lines.add("}");
/*  60 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  65 */     return 60;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCalledDevice()
/*     */   {
/*  71 */     return this.calledDevice;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/*  79 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getFailedConnection()
/*     */   {
/*  87 */     return this.failedConnection;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getFailingDevice()
/*     */   {
/*  95 */     return this.failingDevice;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/* 103 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setFailingDevice(CSTAExtendedDeviceID alertingDevice)
/*     */   {
/* 108 */     this.failingDevice = alertingDevice;
/*     */   }
/*     */ 
/*     */   public void setCalledDevice(CSTAExtendedDeviceID calledDevice) {
/* 112 */     this.calledDevice = calledDevice;
/*     */   }
/*     */ 
/*     */   public void setCause(short cause) {
/* 116 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */   public void setFailedConnection(CSTAConnectionID connection) {
/* 120 */     this.failedConnection = connection;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo) {
/* 124 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAFailedEvent
 * JD-Core Version:    0.5.4
 */