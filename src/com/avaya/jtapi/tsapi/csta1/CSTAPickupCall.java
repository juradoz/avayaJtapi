/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAPickupCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID deflectCall;
/*    */   String calledDevice;
/*    */   public static final int PDU = 17;
/*    */ 
/*    */   public CSTAPickupCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAPickupCall(CSTAConnectionID _deflectCall, String _calledDevice)
/*    */   {
/* 20 */     this.deflectCall = _deflectCall;
/* 21 */     this.calledDevice = _calledDevice;
/*    */   }
/*    */ 
/*    */   public static CSTAPickupCall decode(InputStream in) {
/* 25 */     CSTAPickupCall _this = new CSTAPickupCall();
/* 26 */     _this.doDecode(in);
/*    */ 
/* 28 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 32 */     this.deflectCall = CSTAConnectionID.decode(memberStream);
/* 33 */     this.calledDevice = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 37 */     CSTAConnectionID.encode(this.deflectCall, memberStream);
/* 38 */     DeviceID.encode(this.calledDevice, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 43 */     Collection lines = new ArrayList();
/*    */ 
/* 45 */     lines.add("CSTAPickupCall ::=");
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
/* 59 */     return 17;
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
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAPickupCall
 * JD-Core Version:    0.5.4
 */