/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAForwardingEvent extends CSTAUnsolicited
/*    */ {
/*    */   CSTAExtendedDeviceID device;
/*    */   CSTAForwardingInfo forwardingInformation;
/*    */   public static final int PDU = 70;
/*    */ 
/*    */   public static CSTAForwardingEvent decode(InputStream in)
/*    */   {
/* 18 */     CSTAForwardingEvent _this = new CSTAForwardingEvent();
/* 19 */     _this.doDecode(in);
/*    */ 
/* 21 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 26 */     this.device = CSTAExtendedDeviceID.decode(memberStream);
/* 27 */     this.forwardingInformation = CSTAForwardingInfo.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 31 */     CSTAExtendedDeviceID.encode(this.device, memberStream);
/* 32 */     CSTAForwardingInfo.encode(this.forwardingInformation, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 36 */     Collection lines = new ArrayList();
/* 37 */     lines.add("CSTAForwardingEvent ::=");
/* 38 */     lines.add("{");
/*    */ 
/* 40 */     String indent = "  ";
/* 41 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/* 42 */     lines.addAll(CSTAExtendedDeviceID.print(this.device, "device", indent));
/* 43 */     lines.addAll(CSTAForwardingInfo.print(this.forwardingInformation, "forwardingInformation", indent));
/*    */ 
/* 45 */     lines.add("}");
/* 46 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 51 */     return 70;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getDevice()
/*    */   {
/* 57 */     return this.device;
/*    */   }
/*    */   public void setDevice(CSTAExtendedDeviceID device) {
/* 60 */     this.device = device;
/*    */   }
/*    */ 
/*    */   public CSTAForwardingInfo getForwardingInformation()
/*    */   {
/* 67 */     return this.forwardingInformation;
/*    */   }
/*    */   public void setForwardingInformation(CSTAForwardingInfo fwdInfo) {
/* 70 */     this.forwardingInformation = fwdInfo;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAForwardingEvent
 * JD-Core Version:    0.5.4
 */