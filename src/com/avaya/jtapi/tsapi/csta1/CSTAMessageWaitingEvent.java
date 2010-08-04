/*     */ package com.avaya.jtapi.tsapi.csta1;
/*     */ 
/*     */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ public final class CSTAMessageWaitingEvent extends CSTAUnsolicited
/*     */ {
/*     */   CSTAExtendedDeviceID deviceForMessage;
/*     */   CSTAExtendedDeviceID invokingDevice;
/*     */   boolean messageWaitingOn;
/*     */   public static final int PDU = 71;
/*     */ 
/*     */   public static CSTAMessageWaitingEvent decode(InputStream in)
/*     */   {
/*  22 */     CSTAMessageWaitingEvent _this = new CSTAMessageWaitingEvent();
/*  23 */     _this.doDecode(in);
/*     */ 
/*  25 */     return _this;
/*     */   }
/*     */ 
/*     */   public void decodeMembers(InputStream memberStream)
/*     */   {
/*  30 */     this.deviceForMessage = CSTAExtendedDeviceID.decode(memberStream);
/*  31 */     this.invokingDevice = CSTAExtendedDeviceID.decode(memberStream);
/*  32 */     this.messageWaitingOn = ASNBoolean.decode(memberStream);
/*     */   }
/*     */ 
/*     */   public void encodeMembers(OutputStream memberStream)
/*     */   {
/*  37 */     CSTAExtendedDeviceID.encode(this.deviceForMessage, memberStream);
/*  38 */     CSTAExtendedDeviceID.encode(this.invokingDevice, memberStream);
/*  39 */     ASNBoolean.encode(this.messageWaitingOn, memberStream);
/*     */   }
/*     */ 
/*     */   public Collection<String> print()
/*     */   {
/*  44 */     Collection lines = new ArrayList();
/*     */ 
/*  46 */     lines.add("CSTAMessageWaitingEvent ::=");
/*  47 */     lines.add("{");
/*     */ 
/*  49 */     String indent = "  ";
/*  50 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/*  51 */     lines.addAll(CSTAExtendedDeviceID.print(this.deviceForMessage, "deviceForMessage", indent));
/*     */ 
/*  55 */     lines.addAll(CSTAExtendedDeviceID.print(this.invokingDevice, "invokingDevice", indent));
/*     */ 
/*  59 */     lines.addAll(ASNBoolean.print(this.messageWaitingOn, "messageWaitingOn", indent));
/*     */ 
/*  61 */     lines.add("}");
/*  62 */     return lines;
/*     */   }
/*     */ 
/*     */   public int getPDU()
/*     */   {
/*  69 */     return 71;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getDeviceForMessage()
/*     */   {
/*  77 */     return this.deviceForMessage;
/*     */   }
/*     */ 
/*     */   public CSTAExtendedDeviceID getInvokingDevice()
/*     */   {
/*  85 */     return this.invokingDevice;
/*     */   }
/*     */ 
/*     */   public boolean isMessageWaitingOn()
/*     */   {
/*  93 */     return this.messageWaitingOn;
/*     */   }
/*     */ 
/*     */   public void setDeviceForMessage(CSTAExtendedDeviceID deviceForMessage)
/*     */   {
/* 103 */     this.deviceForMessage = deviceForMessage;
/*     */   }
/*     */ 
/*     */   public void setInvokingDevice(CSTAExtendedDeviceID invokingDevice)
/*     */   {
/* 114 */     this.invokingDevice = invokingDevice;
/*     */   }
/*     */ 
/*     */   public void setMessageWaitingOn(boolean messageWaitingOn)
/*     */   {
/* 124 */     this.messageWaitingOn = messageWaitingOn;
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMessageWaitingEvent
 * JD-Core Version:    0.5.4
 */