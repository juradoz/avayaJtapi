/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAConsultationCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID activeCall;
/*    */   String calledDevice;
/*    */   public static final int PDU = 13;
/*    */ 
/*    */   public CSTAConsultationCall(CSTAConnectionID _activeCall, String _calledDevice)
/*    */   {
/* 19 */     this.activeCall = _activeCall;
/* 20 */     this.calledDevice = _calledDevice;
/*    */   }
/*    */ 
/*    */   public CSTAConsultationCall() {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 27 */     CSTAConnectionID.encode(this.activeCall, memberStream);
/* 28 */     DeviceID.encode(this.calledDevice, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAConsultationCall decode(InputStream in)
/*    */   {
/* 33 */     CSTAConsultationCall _this = new CSTAConsultationCall();
/* 34 */     _this.doDecode(in);
/*    */ 
/* 36 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 41 */     this.activeCall = CSTAConnectionID.decode(memberStream);
/* 42 */     this.calledDevice = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print() {
/* 46 */     Collection lines = new ArrayList();
/* 47 */     lines.add("CSTAConsultationCall ::=");
/* 48 */     lines.add("{");
/*    */ 
/* 50 */     String indent = "  ";
/*    */ 
/* 52 */     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
/* 53 */     lines.addAll(DeviceID.print(this.calledDevice, "calledDevice", indent));
/*    */ 
/* 55 */     lines.add("}");
/* 56 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 61 */     return 13;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getActiveCall()
/*    */   {
/* 67 */     return this.activeCall;
/*    */   }
/*    */ 
/*    */   public String getCalledDevice()
/*    */   {
/* 75 */     return this.calledDevice;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConsultationCall
 * JD-Core Version:    0.5.4
 */