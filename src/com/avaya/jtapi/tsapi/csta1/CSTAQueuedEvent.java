/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNInteger;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAQueuedEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID queuedConnection;
/*     */   CSTAExtendedDeviceID queueingDevice;
/*     */   CSTAExtendedDeviceID callingDevice;
/*     */   CSTAExtendedDeviceID calledDevice;
/*     */   CSTAExtendedDeviceID lastRedirectionDevice;
/*     */   int numberQueued;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 64;
/*     */ 
/*     */   public static CSTAQueuedEvent decode(InputStream in)
/*     */   {
/*  25 */     CSTAQueuedEvent _this = new CSTAQueuedEvent();
/*  26 */     _this.doDecode(in);
/*     */ 
/*  28 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  33 */     this.queuedConnection = CSTAConnectionID.decode(memberStream);
/*  34 */     this.queueingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  35 */     this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  36 */     this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  37 */     this.lastRedirectionDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  38 */     this.numberQueued = ASNInteger.decode(memberStream);
/*  39 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  40 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  45 */     CSTAConnectionID.encode(this.queuedConnection, memberStream);
/*  46 */     CSTAExtendedDeviceID.encode(this.queueingDevice, memberStream);
/*  47 */     CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
/*  48 */     CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
/*  49 */     CSTAExtendedDeviceID.encode(this.lastRedirectionDevice, memberStream);
/*  50 */     ASNInteger.encode(this.numberQueued, memberStream);
/*  51 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  52 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  57 */     Collection lines = new ArrayList();
/*     */ 
/*  59 */     lines.add("CSTAQueuedEvent ::=");
/*  60 */     lines.add("{");
/*     */ 
/*  62 */     String indent = "  ";
/*  63 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  64 */     lines.addAll(CSTAConnectionID.print(this.queuedConnection, "queuedConnection", indent));
/*  65 */     lines.addAll(CSTAExtendedDeviceID.print(this.queueingDevice, "queueingDevice", indent));
/*  66 */     lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice, "callingDevice", indent));
/*  67 */     lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice, "calledDevice", indent));
/*  68 */     lines.addAll(CSTAExtendedDeviceID.print(this.lastRedirectionDevice, "lastRedirectionDevice", indent));
/*  69 */     lines.addAll(ASNInteger.print(this.numberQueued, "numberQueued", indent));
/*  70 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  71 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  73 */     lines.add("}");
/*  74 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  79 */     return 64;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCalledDevice()
/*     */   {
/*  85 */     return this.calledDevice;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCallingDevice()
/*     */   {
/*  93 */     return this.callingDevice;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/* 101 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getLastRedirectionDevice()
/*     */   {
/* 109 */     return this.lastRedirectionDevice;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/* 117 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public int getNumberQueued()
/*     */   {
/* 125 */     return this.numberQueued;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getQueuedConnection()
/*     */   {
/* 133 */     return this.queuedConnection;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getQueueingDevice()
/*     */   {
/* 141 */     return this.queueingDevice;
/*     */   }
/*     */ 
/*     */   public void setCalledDevice(CSTAExtendedDeviceID calledDevice) {
/* 145 */     this.calledDevice = calledDevice;
/*     */   }
/*     */ 
/*     */   public void setCallingDevice(CSTAExtendedDeviceID callingDevice) {
/* 149 */     this.callingDevice = callingDevice;
/*     */   }
/*     */ 
/*     */   public void setCause(short cause) {
/* 153 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */   public void setLastRedirectionDevice(CSTAExtendedDeviceID lastRedirectionDevice) {
/* 157 */     this.lastRedirectionDevice = lastRedirectionDevice;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo) {
/* 161 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setNumberQueued(int numberQueued) {
/* 165 */     this.numberQueued = numberQueued;
/*     */   }
/*     */ 
/*     */   public void setQueuedConnection(CSTAConnectionID queuedConnection) {
/* 169 */     this.queuedConnection = queuedConnection;
/*     */   }
/*     */ 
/*     */   public void setQueueingDevice(CSTAExtendedDeviceID queueingDevice) {
/* 173 */     this.queueingDevice = queueingDevice;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueuedEvent
 * JD-Core Version:    0.5.4
 */