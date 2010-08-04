/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAEstablishedEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID establishedConnection;
/*     */   CSTAExtendedDeviceID answeringDevice;
/*     */   CSTAExtendedDeviceID callingDevice;
/*     */   CSTAExtendedDeviceID calledDevice;
/*     */   CSTAExtendedDeviceID lastRedirectionDevice;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 59;
/*     */ 
/*     */   public static CSTAEstablishedEvent decode(InputStream in)
/*     */   {
/*  23 */     CSTAEstablishedEvent _this = new CSTAEstablishedEvent();
/*  24 */     _this.doDecode(in);
/*     */ 
/*  26 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  31 */     CSTAConnectionID.encode(this.establishedConnection, memberStream);
/*  32 */     CSTAExtendedDeviceID.encode(this.answeringDevice, memberStream);
/*  33 */     CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
/*  34 */     CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
/*  35 */     CSTAExtendedDeviceID.encode(this.lastRedirectionDevice, memberStream);
/*  36 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  37 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  42 */     this.establishedConnection = CSTAConnectionID.decode(memberStream);
/*  43 */     this.answeringDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  44 */     this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  45 */     this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  46 */     this.lastRedirectionDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  47 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  48 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  53 */     Collection lines = new ArrayList();
/*  54 */     lines.add("CSTAEstablishedEvent ::=");
/*  55 */     lines.add("{");
/*     */ 
/*  57 */     String indent = "  ";
/*  58 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  59 */     lines.addAll(CSTAConnectionID.print(this.establishedConnection, "establishedConnection", indent));
/*  60 */     lines.addAll(CSTAExtendedDeviceID.print(this.answeringDevice, "answeringDevice", indent));
/*  61 */     lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice, "callingDevice", indent));
/*  62 */     lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice, "calledDevice", indent));
/*  63 */     lines.addAll(CSTAExtendedDeviceID.print(this.lastRedirectionDevice, "lastRedirectionDevice", indent));
/*  64 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  65 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  67 */     lines.add("}");
/*  68 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  73 */     return 59;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getAnsweringDevice()
/*     */   {
/*  79 */     return this.answeringDevice;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCalledDevice()
/*     */   {
/*  87 */     return this.calledDevice;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCallingDevice()
/*     */   {
/*  95 */     return this.callingDevice;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/* 103 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getEstablishedConnection()
/*     */   {
/* 111 */     return this.establishedConnection;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getLastRedirectionDevice()
/*     */   {
/* 119 */     return this.lastRedirectionDevice;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/* 127 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setAnsweringDevice(CSTAExtendedDeviceID answeringDevice) {
/* 131 */     this.answeringDevice = answeringDevice;
/*     */   }
/*     */ 
/*     */   public void setCalledDevice(CSTAExtendedDeviceID calledDevice) {
/* 135 */     this.calledDevice = calledDevice;
/*     */   }
/*     */ 
/*     */   public void setCallingDevice(CSTAExtendedDeviceID callingDevice) {
/* 139 */     this.callingDevice = callingDevice;
/*     */   }
/*     */ 
/*     */   public void setCause(short cause) {
/* 143 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */   public void setEstablishedConnection(CSTAConnectionID establishedConnection) {
/* 147 */     this.establishedConnection = establishedConnection;
/*     */   }
/*     */ 
/*     */   public void setLastRedirectionDevice(CSTAExtendedDeviceID lastRedirectionDevice) {
/* 151 */     this.lastRedirectionDevice = lastRedirectionDevice;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo) {
/* 155 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAEstablishedEvent
 * JD-Core Version:    0.5.4
 */