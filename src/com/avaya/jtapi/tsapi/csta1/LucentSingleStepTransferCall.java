/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class LucentSingleStepTransferCall extends LucentPrivateData
/*    */ {
/*    */   CSTAConnectionID activeCall;
/*    */   String transferredTo;
/*    */   public static final int PDU = 142;
/*    */ 
/*    */   public LucentSingleStepTransferCall(CSTAConnectionID _activeCall, String _transferredTo)
/*    */   {
/* 18 */     this.activeCall = _activeCall;
/* 19 */     this.transferredTo = _transferredTo;
/*    */   }
/*    */ 
/*    */   public LucentSingleStepTransferCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 27 */     CSTAConnectionID.encode(this.activeCall, memberStream);
/* 28 */     DeviceID.encode(this.transferredTo, memberStream);
/*    */   }
/*    */ 
/*    */   public static LucentSingleStepTransferCall decode(InputStream in)
/*    */   {
/* 33 */     LucentSingleStepTransferCall _this = new LucentSingleStepTransferCall();
/*    */ 
/* 35 */     _this.doDecode(in);
/*    */ 
/* 37 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 42 */     this.activeCall = CSTAConnectionID.decode(memberStream);
/* 43 */     this.transferredTo = DeviceID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 48 */     Collection lines = new ArrayList();
/*    */ 
/* 50 */     lines.add("LucentSingleStepTransferCall ::=");
/* 51 */     lines.add("{");
/*    */ 
/* 53 */     String indent = "  ";
/*    */ 
/* 55 */     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
/* 56 */     lines.addAll(DeviceID.print(this.transferredTo, "transferredTo", indent));
/*    */ 
/* 58 */     lines.add("}");
/* 59 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 65 */     return 142;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getActiveCall()
/*    */   {
/* 71 */     return this.activeCall;
/*    */   }
/*    */ 
/*    */   public String getTransferredTo() {
/* 75 */     return this.transferredTo;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSingleStepTransferCall
 * JD-Core Version:    0.5.4
 */