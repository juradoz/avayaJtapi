/*    */ package com.avaya.jtapi.tsapi.csta1;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public final class CSTAQueryFwdConfEvent extends CSTAConfirmation
/*    */ {
/*    */   CSTAForwardingInfo[] forward;
/*    */   public static final int PDU = 32;
/*    */ 
/*    */   public static CSTAQueryFwdConfEvent decode(InputStream in)
/*    */   {
/* 17 */     CSTAQueryFwdConfEvent _this = new CSTAQueryFwdConfEvent();
/* 18 */     _this.doDecode(in);
/*    */ 
/* 20 */     return _this;
/*    */   }
/*    */ 
/*    */   public void decodeMembers(InputStream memberStream)
/*    */   {
/* 25 */     this.forward = ListForwardParameters.decode(memberStream);
/*    */   }
/*    */ 
/*    */   public void encodeMembers(OutputStream memberStream)
/*    */   {
/* 30 */     ListForwardParameters.encode(this.forward, memberStream);
/*    */   }
/*    */ 
/*    */   public Collection<String> print()
/*    */   {
/* 35 */     Collection lines = new ArrayList();
/*    */ 
/* 37 */     lines.add("CSTAQueryFwdConfEvent ::=");
/* 38 */     lines.add("{");
/*    */ 
/* 40 */     String indent = "  ";
/*    */ 
/* 42 */     lines.addAll(ListForwardParameters.print(this.forward, "forward", indent));
/*    */ 
/* 44 */     lines.add("}");
/* 45 */     return lines;
/*    */   }
/*    */ 
/*    */   public int getPDU()
/*    */   {
/* 50 */     return 32;
/*    */   }
/*    */ 
/*    */   public CSTAForwardingInfo[] getForward()
/*    */   {
/* 56 */     return this.forward;
/*    */   }
/*    */ 
/*    */   public void setForward(CSTAForwardingInfo[] forward) {
/* 60 */     this.forward = forward;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTAQueryFwdConfEvent
 * JD-Core Version:    0.5.4
 */