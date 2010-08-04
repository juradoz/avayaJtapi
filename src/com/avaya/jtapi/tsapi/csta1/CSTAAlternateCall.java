/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAAlternateCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID activeCall;
/*    */   CSTAConnectionID otherCall;
/*    */   public static final int PDU = 1;
/*    */ 
/*    */   public CSTAConnectionID getActiveCall()
/*    */   {
/* 18 */     return this.activeCall;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getOtherCall()
/*    */   {
/* 26 */     return this.otherCall;
/*    */   }
/*    */ 
/*    */   public CSTAAlternateCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CSTAAlternateCall(CSTAConnectionID _activeCall, CSTAConnectionID _otherCall)
/*    */   {
/* 36 */     this.activeCall = _activeCall;
/* 37 */     this.otherCall = _otherCall;
/*    */   }
/*    */ 
/*    */   public static CSTAAlternateCall decode(InputStream in)
/*    */   {
/* 42 */     CSTAAlternateCall _this = new CSTAAlternateCall();
/* 43 */     _this.doDecode(in);
/*    */ 
/* 45 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 50 */     this.activeCall = CSTAConnectionID.decode(memberStream);
/* 51 */     this.otherCall = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 56 */     CSTAConnectionID.encode(this.activeCall, memberStream);
/* 57 */     CSTAConnectionID.encode(this.otherCall, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 62 */     Collection lines = new ArrayList();
/* 63 */     lines.add("CSTAAlternateCall ::=");
/* 64 */     lines.add("{");
/*    */ 
/* 66 */     String indent = "  ";
/*    */ 
/* 68 */     lines.addAll(CSTAConnectionID.print(this.activeCall, "activeCall", indent));
/* 69 */     lines.addAll(CSTAConnectionID.print(this.otherCall, "otherCall", indent));
/*    */ 
/* 71 */     lines.add("}");
/* 72 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 77 */     return 1;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAAlternateCall
 * JD-Core Version:    0.5.4
 */