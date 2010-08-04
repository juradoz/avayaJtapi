/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTADeliveredEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID connection;
/*     */   CSTAExtendedDeviceID alertingDevice;
/*     */   CSTAExtendedDeviceID callingDevice;
/*     */   CSTAExtendedDeviceID calledDevice;
/*     */   CSTAExtendedDeviceID lastRedirectionDevice;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 57;
/*     */ 
/*     */   public static CSTADeliveredEvent decode(InputStream in)
/*     */   {
/*  23 */     CSTADeliveredEvent _this = new CSTADeliveredEvent();
/*  24 */     _this.doDecode(in);
/*     */ 
/*  26 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  31 */     CSTAConnectionID.encode(this.connection, memberStream);
/*  32 */     CSTAExtendedDeviceID.encode(this.alertingDevice, memberStream);
/*  33 */     CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
/*  34 */     CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
/*  35 */     CSTAExtendedDeviceID.encode(this.lastRedirectionDevice, memberStream);
/*  36 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  37 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  43 */     this.connection = CSTAConnectionID.decode(memberStream);
/*  44 */     this.alertingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  45 */     this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  46 */     this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  47 */     this.lastRedirectionDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  48 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  49 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  54 */     Collection lines = new ArrayList();
/*  55 */     lines.add("CSTADeliveredEvent ::=");
/*  56 */     lines.add("{");
/*     */ 
/*  58 */     String indent = "  ";
/*  59 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  60 */     lines.addAll(CSTAConnectionID.print(this.connection, "connection", indent));
/*  61 */     lines.addAll(CSTAExtendedDeviceID.print(this.alertingDevice, "alertingDevice", indent));
/*  62 */     lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice, "callingDevice", indent));
/*  63 */     lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice, "calledDevice", indent));
/*  64 */     lines.addAll(CSTAExtendedDeviceID.print(this.lastRedirectionDevice, "lastRedirectionDevice", indent));
/*  65 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  66 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  68 */     lines.add("}");
/*  69 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  74 */     return 57;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getAlertingDevice()
/*     */   {
/*  80 */     return this.alertingDevice;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCalledDevice()
/*     */   {
/*  88 */     return this.calledDevice;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCallingDevice()
/*     */   {
/*  96 */     return this.callingDevice;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/* 104 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getConnection()
/*     */   {
/* 112 */     return this.connection;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getLastRedirectionDevice()
/*     */   {
/* 120 */     return this.lastRedirectionDevice;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/* 128 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setAlertingDevice(CSTAExtendedDeviceID alertingDevice) {
/* 132 */     this.alertingDevice = alertingDevice;
/*     */   }
/*     */ 
/*     */   public void setCalledDevice(CSTAExtendedDeviceID calledDevice) {
/* 136 */     this.calledDevice = calledDevice;
/*     */   }
/*     */ 
/*     */   public void setCallingDevice(CSTAExtendedDeviceID callingDevice) {
/* 140 */     this.callingDevice = callingDevice;
/*     */   }
/*     */ 
/*     */   public void setCause(short cause) {
/* 144 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */   public void setConnection(CSTAConnectionID connection) {
/* 148 */     this.connection = connection;
/*     */   }
/*     */ 
/*     */   public void setLastRedirectionDevice(CSTAExtendedDeviceID lastRedirectionDevice) {
/* 152 */     this.lastRedirectionDevice = lastRedirectionDevice;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo) {
/* 156 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTADeliveredEvent
 * JD-Core Version:    0.5.4
 */