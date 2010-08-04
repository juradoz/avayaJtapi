/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTARetrieveCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID heldCall;
/*    */   public static final int PDU = 41;
/*    */ 
/*    */   public CSTARetrieveCall(CSTAConnectionID _heldCall)
/*    */   {
/* 17 */     this.heldCall = _heldCall;
/*    */   }
/*    */ 
/*    */   public CSTARetrieveCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 25 */     CSTAConnectionID.encode(this.heldCall, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTARetrieveCall decode(InputStream in)
/*    */   {
/* 30 */     CSTARetrieveCall _this = new CSTARetrieveCall();
/* 31 */     _this.doDecode(in);
/*    */ 
/* 33 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 38 */     this.heldCall = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 43 */     Collection lines = new ArrayList();
/*    */ 
/* 45 */     lines.add("CSTARetrieveCall ::=");
/* 46 */     lines.add("{");
/*    */ 
/* 48 */     String indent = "  ";
/*    */ 
/* 50 */     lines.addAll(CSTAConnectionID.print(this.heldCall, "heldCall", indent));
/*    */ 
/* 52 */     lines.add("}");
/* 53 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 58 */     return 41;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getHeldCall()
/*    */   {
/* 64 */     return this.heldCall;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTARetrieveCall
 * JD-Core Version:    0.5.4
 */