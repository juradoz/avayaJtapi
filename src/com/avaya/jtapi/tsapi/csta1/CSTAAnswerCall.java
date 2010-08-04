/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAAnswerCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID alertingCall;
/*    */   public static final int PDU = 3;
/*    */ 
/*    */   public CSTAConnectionID getAlertingCall()
/*    */   {
/* 17 */     return this.alertingCall;
/*    */   }
/*    */ 
/*    */   public CSTAAnswerCall(CSTAConnectionID _alertingCall)
/*    */   {
/* 25 */     this.alertingCall = _alertingCall;
/*    */   }
/*    */ 
/*    */   public CSTAAnswerCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 34 */     CSTAConnectionID.encode(this.alertingCall, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAAnswerCall decode(InputStream in)
/*    */   {
/* 39 */     CSTAAnswerCall _this = new CSTAAnswerCall();
/* 40 */     _this.doDecode(in);
/*    */ 
/* 42 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 47 */     this.alertingCall = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 52 */     Collection lines = new ArrayList();
/* 53 */     lines.add("CSTAAnswerCall ::=");
/* 54 */     lines.add("{");
/*    */ 
/* 56 */     String indent = "  ";
/*    */ 
/* 58 */     lines.addAll(CSTAConnectionID.print(this.alertingCall, "alertingCall", indent));
/*    */ 
/* 60 */     lines.add("}");
/* 61 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 66 */     return 3;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAAnswerCall
 * JD-Core Version:    0.5.4
 */