/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTADeflectCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID deflectCall;
/*    */   String calledDevice;
/*    */   public static final int PDU = 15;
/*    */ 
/*    */   public CSTADeflectCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTADeflectCall(CSTAConnectionID _deflectCall, String _calledDevice)
/*    */   {
/* 20 */     this.deflectCall = _deflectCall;
/* 21 */     this.calledDevice = _calledDevice;
/*    */   }
/*    */ 
/*    */   public static CSTADeflectCall decode(InputStream in)
/*    */   {
/* 26 */     CSTADeflectCall _this = new CSTADeflectCall();
/* 27 */     _this.doDecode(in);
/*    */ 
/* 29 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 33 */     this.deflectCall = CSTAConnectionID.decode(memberStream);
/* 34 */     this.calledDevice = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 38 */     CSTAConnectionID.encode(this.deflectCall, memberStream);
/* 39 */     DeviceID.encode(this.calledDevice, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 44 */     Collection lines = new ArrayList();
/* 45 */     lines.add("CSTADeflectCall ::=");
/* 46 */     lines.add("{");
/*    */ 
/* 48 */     String indent = "  ";
/*    */ 
/* 50 */     lines.addAll(CSTAConnectionID.print(this.deflectCall, "deflectCall", indent));
/* 51 */     lines.addAll(DeviceID.print(this.calledDevice, "calledDevice", indent));
/*    */ 
/* 53 */     lines.add("}");
/* 54 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 59 */     return 15;
/*    */   }
/*    */ 
/*    */   public String getCalledDevice()
/*    */   {
/* 65 */     return this.calledDevice;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getDeflectCall()
/*    */   {
/* 73 */     return this.deflectCall;
/*    */   }
/*    */ 
/*    */   public void setCalledDevice(String _calledDevice) {
/* 77 */     this.calledDevice = _calledDevice;
/*    */   }
/*    */ 
/*    */   public void setDeflectCall(CSTAConnectionID _deflectCall) {
/* 81 */     this.deflectCall = _deflectCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTADeflectCall
 * JD-Core Version:    0.5.4
 */