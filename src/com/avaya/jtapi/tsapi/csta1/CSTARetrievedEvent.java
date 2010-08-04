/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTARetrievedEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID retrievedConnection;
/*     */   CSTAExtendedDeviceID retrievingDevice;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 65;
/*     */ 
/*     */   public static CSTARetrievedEvent decode(InputStream in)
/*     */   {
/*  20 */     CSTARetrievedEvent _this = new CSTARetrievedEvent();
/*  21 */     _this.doDecode(in);
/*     */ 
/*  23 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  28 */     this.retrievedConnection = CSTAConnectionID.decode(memberStream);
/*  29 */     this.retrievingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  30 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  31 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  36 */     CSTAConnectionID.encode(this.retrievedConnection, memberStream);
/*  37 */     CSTAExtendedDeviceID.encode(this.retrievingDevice, memberStream);
/*  38 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  39 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  44 */     Collection lines = new ArrayList();
/*     */ 
/*  46 */     lines.add("CSTARetrievedEvent ::=");
/*  47 */     lines.add("{");
/*     */ 
/*  49 */     String indent = "  ";
/*  50 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  51 */     lines.addAll(CSTAConnectionID.print(this.retrievedConnection, "retrievedConnection", indent));
/*  52 */     lines.addAll(CSTAExtendedDeviceID.print(this.retrievingDevice, "retrievingDevice", indent));
/*  53 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  54 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  56 */     lines.add("}");
/*  57 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  62 */     return 65;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/*  68 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/*  76 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getRetrievedConnection()
/*     */   {
/*  84 */     return this.retrievedConnection;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getRetrievingDevice()
/*     */   {
/*  92 */     return this.retrievingDevice;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo) {
/*  96 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setRetrievedConnection(CSTAConnectionID retrievedConnection) {
/* 100 */     this.retrievedConnection = retrievedConnection;
/*     */   }
/*     */ 
/*     */   public void setRetrievingDevice(CSTAExtendedDeviceID retrievingDevice) {
/* 104 */     this.retrievingDevice = retrievingDevice;
/*     */   }
/*     */ 
/*     */   public void setCause(short cause) {
/* 108 */     this.cause = cause;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARetrievedEvent
 * JD-Core Version:    0.5.4
 */