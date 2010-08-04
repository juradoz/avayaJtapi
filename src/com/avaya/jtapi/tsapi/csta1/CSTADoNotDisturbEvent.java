/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import com.avaya.jtapi.tsapi.asn1.ASNBoolean;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTADoNotDisturbEvent extends CSTAUnsolicited
/*    */ {
/*    */   CSTAExtendedDeviceID device;
/*    */   boolean doNotDisturbOn;
/*    */   public static final int PDU = 69;
/*    */ 
/*    */   public static CSTADoNotDisturbEvent decode(InputStream in)
/*    */   {
/* 19 */     CSTADoNotDisturbEvent _this = new CSTADoNotDisturbEvent();
/* 20 */     _this.doDecode(in);
/*    */ 
/* 22 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 27 */     this.device = CSTAExtendedDeviceID.decode(memberStream);
/* 28 */     this.doNotDisturbOn = ASNBoolean.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 33 */     CSTAExtendedDeviceID.encode(this.device, memberStream);
/* 34 */     ASNBoolean.encode(this.doNotDisturbOn, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 39 */     Collection lines = new ArrayList();
/* 40 */     lines.add("CSTADoNotDisturbEvent ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/* 44 */     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
/* 45 */     lines.addAll(CSTAExtendedDeviceID.print(this.device, "device", indent));
/* 46 */     lines.addAll(ASNBoolean.print(this.doNotDisturbOn, "doNotDisturbOn", indent));
/*    */ 
/* 48 */     lines.add("}");
/* 49 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 54 */     return 69;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getDevice()
/*    */   {
/* 60 */     return this.device;
/*    */   }
/*    */   public void setDevice(CSTAExtendedDeviceID device) {
/* 63 */     this.device = device;
/*    */   }
/*    */ 
/*    */   public boolean isDoNotDisturbOn()
/*    */   {
/* 70 */     return this.doNotDisturbOn;
/*    */   }
/*    */   public void setDoNotDisturbOn(boolean doNotDisturb) {
/* 73 */     this.doNotDisturbOn = doNotDisturb;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTADoNotDisturbEvent
 * JD-Core Version:    0.5.4
 */