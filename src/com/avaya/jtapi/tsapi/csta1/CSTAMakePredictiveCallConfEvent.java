/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAMakePredictiveCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   CSTAConnectionID newCall;
/*    */   public static final int PDU = 26;
/*    */ 
/*    */   public static CSTAMakePredictiveCallConfEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTAMakePredictiveCallConfEvent _this = new CSTAMakePredictiveCallConfEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.newCall = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 29 */     CSTAConnectionID.encode(this.newCall, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 34 */     Collection lines = new ArrayList();
/* 35 */     lines.add("CSTAMakePredictiveCallConfEvent ::=");
/* 36 */     lines.add("{");
/*    */ 
/* 38 */     String indent = "  ";
/*    */ 
/* 40 */     lines.addAll(CSTAConnectionID.print(this.newCall, "newCall", indent));
/*    */ 
/* 42 */     lines.add("}");
/* 43 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 48 */     return 26;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getNewCall()
/*    */   {
/* 54 */     return this.newCall;
/*    */   }
/*    */ 
/*    */   public void setNewCall(CSTAConnectionID newCall) {
/* 58 */     this.newCall = newCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAMakePredictiveCallConfEvent
 * JD-Core Version:    0.5.4
 */