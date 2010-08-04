/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAReconnectCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID activeCall;
/*    */   CSTAConnectionID heldCall;
/*    */   public static final int PDU = 39;
/*    */ 
/*    */   public CSTAReconnectCall(CSTAConnectionID _activeCall, CSTAConnectionID _heldCall)
/*    */   {
/* 19 */     this.activeCall = _activeCall;
/* 20 */     this.heldCall = _heldCall;
/*    */   }
/*    */ 
/*    */   protected CSTAReconnectCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public static CSTAReconnectCall decode(InputStream in)
/*    */   {
/* 29 */     CSTAReconnectCall _this = new CSTAReconnectCall();
/* 30 */     _this.doDecode(in);
/*    */ 
/* 32 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 37 */     this.activeCall = CSTAConnectionID.decode(memberStream);
/* 38 */     this.heldCall = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 43 */     CSTAConnectionID.encode(this.activeCall, memberStream);
/* 44 */     CSTAConnectionID.encode(this.heldCall, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 49 */     Collection lines = new ArrayList();
/*    */ 
/* 51 */     lines.add("CSTAReconnectCall ::=");
/* 52 */     lines.add("{");
/*    */ 
/* 54 */     String indent = "  ";
/*    */ 
/* 56 */     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
/* 57 */     lines.addAll(CSTAConnectionID.print(this.heldCall, "heldCall", indent));
/*    */ 
/* 59 */     lines.add("}");
/* 60 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 65 */     return 39;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getActiveCall()
/*    */   {
/* 71 */     return this.activeCall;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getHeldCall()
/*    */   {
/* 79 */     return this.heldCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAReconnectCall
 * JD-Core Version:    0.5.4
 */