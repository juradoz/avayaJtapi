/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAConsultationCallConfEvent extends CSTAConfirmation
/*    */ {
/*    */   CSTAConnectionID newCall;
/*    */   public static final int PDU = 14;
/*    */ 
/*    */   public CSTAConsultationCallConfEvent()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAConsultationCallConfEvent(CSTAConnectionID _newCall)
/*    */   {
/* 16 */     this.newCall = _newCall;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 21 */     CSTAConnectionID.encode(this.newCall, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAConsultationCallConfEvent decode(InputStream in)
/*    */   {
/* 26 */     CSTAConsultationCallConfEvent _this = new CSTAConsultationCallConfEvent();
/* 27 */     _this.doDecode(in);
/*    */ 
/* 29 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 34 */     this.newCall = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 39 */     Collection lines = new ArrayList();
/* 40 */     lines.add("CSTAConsultationCallConfEvent ::=");
/* 41 */     lines.add("{");
/*    */ 
/* 43 */     String indent = "  ";
/*    */ 
/* 45 */     lines.addAll(CSTAConnectionID.print(this.newCall, "newCall", indent));
/*    */ 
/* 47 */     lines.add("}");
/* 48 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 53 */     return 14;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getNewCall()
/*    */   {
/* 59 */     return this.newCall;
/*    */   }
/*    */ 
/*    */   public void setNewCall(CSTAConnectionID newCall) {
/* 63 */     this.newCall = newCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConsultationCallConfEvent
 * JD-Core Version:    0.5.4
 */