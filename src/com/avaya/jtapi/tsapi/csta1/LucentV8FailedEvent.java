/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentV8FailedEvent extends LucentFailedEvent
/*    */ {
/*    */   CSTAExtendedDeviceID callingDevice_asn;
/*    */   static final int PDU = 141;
/*    */ 
/*    */   public LucentV8FailedEvent()
/*    */   {
/* 16 */     this.callingDevice_asn = null;
/*    */   }
/*    */ 
/*    */   static LucentFailedEvent decode(InputStream in)
/*    */   {
/* 22 */     LucentV8FailedEvent _this = new LucentV8FailedEvent();
/* 23 */     _this.doDecode(in);
/*    */ 
/* 26 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 31 */     super.decodeMembers(memberStream);
/* 32 */     this.callingDevice_asn = CSTAExtendedDeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 39 */     super.encodeMembers(memberStream);
/* 40 */     CSTAExtendedDeviceID.encode(this.callingDevice_asn, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 46 */     Collection lines = new ArrayList();
/* 47 */     lines.add("LucentV8FailedEvent ::=");
/* 48 */     lines.add("{");
/*    */ 
/* 50 */     String indent = "  ";
/*    */ 
/* 52 */     lines.addAll(CSTAExtendedDeviceID.print(this.callingDevice_asn, "callingDevice", indent));
/* 53 */     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
/*    */ 
/* 55 */     lines.add("}");
/* 56 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 61 */     return 141;
/*    */   }
/*    */ 
/*    */   public CSTAExtendedDeviceID getCallingDevice()
/*    */   {
/* 67 */     return this.callingDevice_asn;
/*    */   }
/*    */   public void setCallingDevice(CSTAExtendedDeviceID device) {
/* 70 */     this.callingDevice_asn = device;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV8FailedEvent
 * JD-Core Version:    0.5.4
 */