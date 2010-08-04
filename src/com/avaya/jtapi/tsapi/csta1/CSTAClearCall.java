/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAClearCall extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID call;
/*    */   public static final int PDU = 7;
/*    */ 
/*    */   public CSTAClearCall(CSTAConnectionID _call)
/*    */   {
/* 17 */     this.call = _call;
/*    */   }
/*    */ 
/*    */   public CSTAClearCall()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 25 */     CSTAConnectionID.encode(this.call, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAClearCall decode(InputStream in)
/*    */   {
/* 30 */     CSTAClearCall _this = new CSTAClearCall();
/* 31 */     _this.doDecode(in);
/*    */ 
/* 33 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 38 */     this.call = CSTAConnectionID.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 43 */     Collection lines = new ArrayList();
/* 44 */     lines.add("CSTAClearCall ::=");
/* 45 */     lines.add("{");
/*    */ 
/* 47 */     String indent = "  ";
/*    */ 
/* 49 */     lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
/*    */ 
/* 51 */     lines.add("}");
/* 52 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 57 */     return 7;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getCall()
/*    */   {
/* 63 */     return this.call;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAClearCall
 * JD-Core Version:    0.5.4
 */