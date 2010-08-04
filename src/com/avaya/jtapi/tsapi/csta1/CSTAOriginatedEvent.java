/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAOriginatedEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID originatedConnection;
/*     */   CSTAExtendedDeviceID callingDevice;
/*     */   CSTAExtendedDeviceID calledDevice;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 63;
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  20 */     CSTAConnectionID.encode(this.originatedConnection, memberStream);
/*  21 */     CSTAExtendedDeviceID.encode(this.callingDevice, memberStream);
/*  22 */     CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
/*  23 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  24 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public static CSTAOriginatedEvent decode(InputStream in) {
/*  28 */     CSTAOriginatedEvent _this = new CSTAOriginatedEvent();
/*  29 */     _this.doDecode(in);
/*     */ 
/*  31 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  36 */     this.originatedConnection = CSTAConnectionID.decode(memberStream);
/*  37 */     this.callingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  38 */     this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  39 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  40 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  45 */     Collection lines = new ArrayList();
/*  46 */     lines.add("CSTAOriginatedEvent ::=");
/*  47 */     lines.add("{");
/*     */ 
/*  49 */     String indent = "  ";
/*  50 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  51 */     lines.addAll(CSTAConnectionID.print(this.originatedConnection, "originatedConnection", indent));
/*  52 */     lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice, "callingDevice", indent));
/*  53 */     lines.addAll(CSTAExtendedDeviceID.print(this.calledDevice, "calledDevice", indent));
/*  54 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  55 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  57 */     lines.add("}");
/*  58 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  63 */     return 63;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCalledDevice()
/*     */   {
/*  69 */     return this.calledDevice;
/*     */   }
/*     */ 
/*     */   public void setCalledDevice(CSTAExtendedDeviceID deviceId)
/*     */   {
/*  77 */     this.calledDevice = deviceId;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getCallingDevice()
/*     */   {
/*  84 */     return this.callingDevice;
/*     */   }
/*     */ 
/*     */   public void setCallingDevice(CSTAExtendedDeviceID deviceId)
/*     */   {
/*  92 */     this.callingDevice = deviceId;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/* 100 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public void setCause(short cause) {
/* 104 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/* 111 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo) {
/* 115 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getOriginatedConnection()
/*     */   {
/* 122 */     return this.originatedConnection;
/*     */   }
/*     */ 
/*     */   public void setOriginatedConnection(CSTAConnectionID connectionId)
/*     */   {
/* 129 */     this.originatedConnection = connectionId;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAOriginatedEvent
 * JD-Core Version:    0.5.4
 */