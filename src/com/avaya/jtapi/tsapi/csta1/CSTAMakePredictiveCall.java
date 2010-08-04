/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAMakePredictiveCall extends CSTARequest
/*    */ {
/*    */   String callingDevice;
/*    */   String calledDevice;
/*    */   short allocationState;
/*    */   public static final int PDU = 25;
/*    */ 
/*    */   public CSTAMakePredictiveCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAMakePredictiveCall(String _callingDevice, String _calledDevice, short _allocationState)
/*    */   {
/* 22 */     this.callingDevice = _callingDevice;
/* 23 */     this.calledDevice = _calledDevice;
/* 24 */     this.allocationState = _allocationState;
/*    */   }
/*    */ 
/*    */   public static CSTAMakePredictiveCall decode(InputStream in) {
/* 28 */     CSTAMakePredictiveCall _this = new CSTAMakePredictiveCall();
/* 29 */     _this.doDecode(in);
/*    */ 
/* 31 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 35 */     this.callingDevice = DeviceID.decode(memberStream);
/* 36 */     this.calledDevice = DeviceID.decode(memberStream);
/* 37 */     this.allocationState = AllocationState.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 41 */     DeviceID.encode(this.callingDevice, memberStream);
/* 42 */     DeviceID.encode(this.calledDevice, memberStream);
/* 43 */     AllocationState.encode(this.allocationState, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 48 */     Collection lines = new ArrayList();
/* 49 */     lines.add("CSTAMakePredictiveCall ::=");
/* 50 */     lines.add("{");
/*    */ 
/* 52 */     String indent = "  ";
/*    */ 
/* 54 */     lines.addAll(DeviceID.print(this.callingDevice, "callingDevice", indent));
/* 55 */     lines.addAll(DeviceID.print(this.calledDevice, "calledDevice", indent));
/* 56 */     lines.addAll(AllocationState.print(this.allocationState, "allocationState", indent));
/*    */ 
/* 58 */     lines.add("}");
/* 59 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 64 */     return 25;
/*    */   }
/*    */ 
/*    */   public short getAllocationState()
/*    */   {
/* 70 */     return this.allocationState;
/*    */   }
/*    */ 
/*    */   public String getCalledDevice()
/*    */   {
/* 78 */     return this.calledDevice;
/*    */   }
/*    */ 
/*    */   public String getCallingDevice()
/*    */   {
/* 86 */     return this.callingDevice;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMakePredictiveCall
 * JD-Core Version:    0.5.4
 */