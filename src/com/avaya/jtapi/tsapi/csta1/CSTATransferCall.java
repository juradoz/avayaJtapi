/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTATransferCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID heldCall;
/*    */   CSTAConnectionID activeCall;
/*    */   public static final int PDU = 51;
/*    */ 
/*    */   public CSTATransferCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTATransferCall(CSTAConnectionID _heldCall, CSTAConnectionID _activeCall)
/*    */   {
/* 20 */     this.heldCall = _heldCall;
/* 21 */     this.activeCall = _activeCall;
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 26 */     CSTAConnectionID.encode(this.heldCall, memberStream);
/* 27 */     CSTAConnectionID.encode(this.activeCall, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTATransferCall decode(InputStream in)
/*    */   {
/* 32 */     CSTATransferCall _this = new CSTATransferCall();
/* 33 */     _this.doDecode(in);
/*    */ 
/* 35 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream) {
/* 39 */     this.heldCall = CSTAConnectionID.decode(memberStream);
/* 40 */     this.activeCall = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 45 */     Collection lines = new ArrayList();
/*    */ 
/* 47 */     lines.add("CSTATransferCall ::=");
/* 48 */     lines.add("{");
/*    */ 
/* 50 */     String indent = "  ";
/*    */ 
/* 52 */     lines.addAll(CSTAConnectionID.print(this.heldCall, "heldCall", indent));
/* 53 */     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
/*    */ 
/* 55 */     lines.add("}");
/* 56 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 61 */     return 51;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getActiveCall()
/*    */   {
/* 67 */     return this.activeCall;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getHeldCall()
/*    */   {
/* 75 */     return this.heldCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTATransferCall
 * JD-Core Version:    0.5.4
 */