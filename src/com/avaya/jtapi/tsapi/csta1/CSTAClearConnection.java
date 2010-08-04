/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAClearConnection extends CSTARequest
/*    */ {
/*    */   CSTAConnectionID call;
/*    */   public static final int PDU = 9;
/*    */ 
/*    */   public CSTAClearConnection(CSTAConnectionID _call)
/*    */   {
/* 17 */     this.call = _call;
/*    */   }
/*    */ 
/*    */   public CSTAClearConnection()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream) {
/* 25 */     CSTAConnectionID.encode(this.call, memberStream);
/*    */   }
/*    */ 
/*    */   public static CSTAClearConnection decode(InputStream in)
/*    */   {
/* 30 */     CSTAClearConnection _this = new CSTAClearConnection();
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
/* 44 */     lines.add("CSTAClearConnection ::=");
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
/* 57 */     return 9;
/*    */   }
/*    */ 
/*    */   public CSTAConnectionID getCall()
/*    */   {
/* 63 */     return this.call;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAClearConnection
 * JD-Core Version:    0.5.4
 */