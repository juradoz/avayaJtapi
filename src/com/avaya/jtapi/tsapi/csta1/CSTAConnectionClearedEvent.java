/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAConnectionClearedEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID droppedConnection;
/*     */   CSTAExtendedDeviceID releasingDevice;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 56;
/*     */ 
/*     */   public static CSTAConnectionClearedEvent decode(InputStream in)
/*     */   {
/*  20 */     CSTAConnectionClearedEvent _this = new CSTAConnectionClearedEvent();
/*  21 */     _this.doDecode(in);
/*     */ 
/*  23 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  28 */     this.droppedConnection = CSTAConnectionID.decode(memberStream);
/*  29 */     this.releasingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  30 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  31 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  35 */     CSTAConnectionID.encode(this.droppedConnection, memberStream);
/*  36 */     CSTAExtendedDeviceID.encode(this.releasingDevice, memberStream);
/*  37 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  38 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  43 */     Collection lines = new ArrayList();
/*  44 */     lines.add("CSTAConnectionClearedEvent ::=");
/*  45 */     lines.add("{");
/*     */ 
/*  47 */     String indent = "  ";
/*  48 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  49 */     lines.addAll(CSTAConnectionID.print(this.droppedConnection, "droppedConnection", indent));
/*  50 */     lines.addAll(CSTAExtendedDeviceID.print(this.releasingDevice, "releasingDevice", indent));
/*  51 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  52 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  54 */     lines.add("}");
/*  55 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  60 */     return 56;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/*  66 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getDroppedConnection()
/*     */   {
/*  74 */     return this.droppedConnection;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/*  82 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getReleasingDevice()
/*     */   {
/*  90 */     return this.releasingDevice;
/*     */   }
/*     */ 
/*     */   public void setCause(short cause) {
/*  94 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */   public void setDroppedConnection(CSTAConnectionID droppedConnection) {
/*  98 */     this.droppedConnection = droppedConnection;
/*     */   }
/*     */ 
/*     */   public void setReleasingDevice(CSTAExtendedDeviceID releasingDevice) {
/* 102 */     this.releasingDevice = releasingDevice;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo) {
/* 106 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConnectionClearedEvent
 * JD-Core Version:    0.5.4
 */