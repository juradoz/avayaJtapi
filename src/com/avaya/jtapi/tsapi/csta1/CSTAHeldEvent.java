/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAHeldEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAConnectionID heldConnection;
/*     */   CSTAExtendedDeviceID holdingDevice;
/*     */   short localConnectionInfo;
/*     */   short cause;
/*     */   public static final int PDU = 61;
/*     */ 
/*     */   public static CSTAHeldEvent decode(InputStream in)
/*     */   {
/*  20 */     CSTAHeldEvent _this = new CSTAHeldEvent();
/*  21 */     _this.doDecode(in);
/*     */ 
/*  23 */     return _this;
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream) {
/*  27 */     CSTAConnectionID.encode(this.heldConnection, memberStream);
/*  28 */     CSTAExtendedDeviceID.encode(this.holdingDevice, memberStream);
/*  29 */     LocalConnectionState.encode(this.localConnectionInfo, memberStream);
/*  30 */     CSTAEventCause.encode(this.cause, memberStream);
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream) {
/*  34 */     this.heldConnection = CSTAConnectionID.decode(memberStream);
/*  35 */     this.holdingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  36 */     this.localConnectionInfo = LocalConnectionState.decode(memberStream);
/*  37 */     this.cause = CSTAEventCause.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  42 */     Collection lines = new ArrayList();
/*  43 */     lines.add("CSTAHeldEvent ::=");
/*  44 */     lines.add("{");
/*     */ 
/*  46 */     String indent = "  ";
/*  47 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  48 */     lines.addAll(CSTAConnectionID.print(this.heldConnection, "heldConnection", indent));
/*  49 */     lines.addAll(CSTAExtendedDeviceID.print(this.holdingDevice, "holdingDevice", indent));
/*  50 */     lines.addAll(LocalConnectionState.print(this.localConnectionInfo, "localConnectionInfo", indent));
/*  51 */     lines.addAll(CSTAEventCause.print(this.cause, "cause", indent));
/*     */ 
/*  53 */     lines.add("}");
/*  54 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  59 */     return 61;
/*     */   }
/*     */ 
/*     */   public short getCause()
/*     */   {
/*  65 */     return this.cause;
/*     */   }
/*     */ 
/*     */   public CSTAConnectionID getHeldConnection()
/*     */   {
/*  73 */     return this.heldConnection;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getHoldingDevice()
/*     */   {
/*  81 */     return this.holdingDevice;
/*     */   }
/*     */ 
/*     */   public short getLocalConnectionInfo()
/*     */   {
/*  89 */     return this.localConnectionInfo;
/*     */   }
/*     */ 
/*     */   public void setCause(short cause) {
/*  93 */     this.cause = cause;
/*     */   }
/*     */ 
/*     */   public void setHeldConnection(CSTAConnectionID heldConnection) {
/*  97 */     this.heldConnection = heldConnection;
/*     */   }
/*     */ 
/*     */   public void setHoldingDevice(CSTAExtendedDeviceID holdingDevice) {
/* 101 */     this.holdingDevice = holdingDevice;
/*     */   }
/*     */ 
/*     */   public void setLocalConnectionInfo(short localConnectionInfo) {
/* 105 */     this.localConnectionInfo = localConnectionInfo;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAHeldEvent
 * JD-Core Version:    0.5.4
 */