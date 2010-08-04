/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAMakeCall extends CSTARequest
/*    */ {
/*    */   String callingDevice;
/*    */   String calledDevice;
/*    */   public static final int PDU = 23;
/*    */ 
/*    */   public CSTAMakeCall(String _callingDevice, String _calledDevice)
/*    */   {
/* 19 */     this.callingDevice = _callingDevice;
/* 20 */     this.calledDevice = _calledDevice;
/*    */   }
/*    */ 
/*    */   public CSTAMakeCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 28 */     DeviceID.encode(this.callingDevice, memberStream);
/* 29 */     DeviceID.encode(this.calledDevice, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAMakeCall decode(InputStream in)
/*    */   {
/* 34 */     CSTAMakeCall _this = new CSTAMakeCall();
/* 35 */     _this.doDecode(in);
/*    */ 
/* 37 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 44 */     this.callingDevice = DeviceID.decode(memberStream);
/* 45 */     this.calledDevice = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 50 */     Collection lines = new ArrayList();
/* 51 */     lines.add("CSTAMakeCall ::=");
/* 52 */     lines.add("{");
/*    */ 
/* 54 */     String indent = "  ";
/*    */ 
/* 56 */     lines.addAll(DeviceID.print(this.callingDevice, "callingDevice", indent));
/* 57 */     lines.addAll(DeviceID.print(this.calledDevice, "calledDevice", indent));
/*    */ 
/* 59 */     lines.add("}");
/* 60 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 65 */     return 23;
/*    */   }
/*    */ 
/*    */   public String getCalledDevice()
/*    */   {
/* 71 */     return this.calledDevice;
/*    */   }
/*    */ 
/*    */   public String getCallingDevice()
/*    */   {
/* 79 */     return this.callingDevice;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMakeCall
 * JD-Core Version:    0.5.4
 */