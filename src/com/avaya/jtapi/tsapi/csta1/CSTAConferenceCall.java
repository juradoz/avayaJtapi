/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAConferenceCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID heldCall;
/*    */   CSTAConnectionID activeCall;
/*    */   public static final int PDU = 11;
/*    */ 
/*    */   public CSTAConferenceCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAConferenceCall(CSTAConnectionID _heldCall, CSTAConnectionID _activeCall)
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
/*    */   public static CSTAConferenceCall decode(InputStream in)
/*    */   {
/* 32 */     CSTAConferenceCall _this = new CSTAConferenceCall();
/* 33 */     _this.doDecode(in);
/*    */ 
/* 35 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 40 */     this.heldCall = CSTAConnectionID.decode(memberStream);
/* 41 */     this.activeCall = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 47 */     Collection lines = new ArrayList();
/* 48 */     lines.add("CSTAConferenceCall ::=");
/* 49 */     lines.add("{");
/*    */ 
/* 51 */     String indent = "  ";
/*    */ 
/* 53 */     lines.addAll(CSTAConnectionID.print(this.heldCall, "heldCall", indent));
/* 54 */     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
/*    */ 
/* 56 */     lines.add("}");
/* 57 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 62 */     return 11;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getActiveCall()
/*    */   {
/* 68 */     return this.activeCall;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getHeldCall()
/*    */   {
/* 76 */     return this.heldCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAConferenceCall
 * JD-Core Version:    0.5.4
 */