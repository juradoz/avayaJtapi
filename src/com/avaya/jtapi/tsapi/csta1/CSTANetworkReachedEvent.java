/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTANetworkReachedEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID connection;
/*     */   CSTAExtendedDeviceID trunkUsed;
/*     */   CSTAExtendedDeviceID calledDevice;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 62;
/*     */ 
/*     */   public static CSTANetworkReachedEvent decode(InputStream in)
/*     */   {
/*  21 */     CSTANetworkReachedEvent _this = new CSTANetworkReachedEvent();
/*  22 */     _this.doDecode(in);
/*     */ 
/*  24 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  29 */     this.connection = CSTAConnectionID.decode(memberStream);
/*  30 */     this.trunkUsed = CSTAExtendedDeviceID.decode(memberStream);
/*  31 */     this.calledDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  32 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  33 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  38 */     CSTAConnectionID.encode(this.connection, memberStream);
/*  39 */     CSTAExtendedDeviceID.encode(this.trunkUsed, memberStream);
/*  40 */     CSTAExtendedDeviceID.encode(this.calledDevice, memberStream);
/*  41 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  42 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  47 */     Collection lines = new ArrayList();
/*  48 */     lines.add("CSTANetworkReachedEvent ::=");
/*  49 */     lines.add("{");
/*     */ 
/*  51 */     String indent = "  ";
/*  52 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  53 */     lines.addAll(CSTAConnectionID.print(this.connection, "connection", indent));
/*  54 */     lines.addAll(CSTAExtendedDeviceID.print(this.trunkUsed, "trunkUsed", indent));
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
/*  65 */     return 62;
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
/*     */   public CSTAConnectionID getConnection()
/*     */   {
/*  87 */     return this.connection;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/*  95 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getTrunkUsed()
/*     */   {
/* 103 */     return this.trunkUsed;
/*     */   }
/*     */ 
/*     */   public void setCalledDevice(CSTAExtendedDeviceID calledDevice) {
/* 107 */     this.calledDevice = calledDevice;
/*     */   }
/*     */ 
/*     */   public void setCause(short cause) {
/* 111 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */   public void setConnection(CSTAConnectionID connection) {
/* 115 */     this.connection = connection;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo) {
/* 119 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setTrunkUsed(CSTAExtendedDeviceID trunkUsed) {
/* 123 */     this.trunkUsed = trunkUsed;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTANetworkReachedEvent
 * JD-Core Version:    0.5.4
 */